package com.convertlab.rabbitmqpipe.service;

import com.convertlab.cache.enums.RedisKeyEnum;
import com.convertlab.cache.utils.RedisUtil;
import com.convertlab.common.beta.constants.LogConstants;
import com.convertlab.common.beta.enums.RabbitMsgStatusEnum;
import com.convertlab.common.beta.enums.YesOrNoEnum;
import com.convertlab.common.beta.model.dto.TenantDto;
import com.convertlab.common.beta.utils.*;
import com.convertlab.rabbitmqpipe.config.RabbitConfig;
import com.convertlab.rabbitmqpipe.model.RabbitPropertyDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Rabbit回调函数实现类
 *
 * @author liujun
 * @date 2021-04-20 21:03:33
 */
@Component
public class RabbitCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(RabbitCallback.class);

    @Autowired
    private RabbitConfig rabbitConfig;

    /**
     * 消息投递Broker失败或成功
     *
     * @param correlationData 消息的附加信息，即自定义id
     * @param isAck           代表消息是否被broker（MQ）接收 true 代表接收 false代表拒收。
     * @param cause           如果拒收cause则说明拒收的原因，帮助我们进行后续处理
     * @return RabbitTemplate.ConfirmCallback
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean isAck, String cause) {
        String thread = Thread.currentThread().getName();
        String correlationId = correlationData == null ? UUID.randomUUID().toString() : correlationData.getId();
        correlationId = StringUtils.isEmpty(correlationId) ? UUID.randomUUID().toString().replace("-", "")
                : correlationId;
        String sendMethod = rabbitConfig.getUpdateSendMethod();

        if (isAck) {
            LOG.info("线程{}======RabbitMQ消息发送成功【消息ID={},correlationData={}】", thread, correlationId, correlationData);
            // 不记录成功日志
            if (!YesOrNoEnum.Y.getCode().equals(rabbitConfig.getRecordSendSuccessFlag())) {
                return;
            }
            invokeMsgLogMethod4Send(sendMethod, correlationId, RabbitMsgStatusEnum.SEND_SUCCESS.getCode(), cause);
        } else {
            // 不记录成功日志时，失败日志是插入，否则是更新
            if (!YesOrNoEnum.Y.getCode().equals(rabbitConfig.getRecordSendSuccessFlag())) {
                sendMethod = rabbitConfig.getInitSendMethod();
            }
            // 失败则进行具体的后续操作:重试 或者补偿等手段
            LOG.error("线程" + thread + "======RabbitMQ消息发送失败【correlationId=" + correlationId + ",correlationData="
                    + correlationData + ",原因=" + cause + "】");
            // 调用 回调方法
            invokeMsgLogMethod4Send(sendMethod, correlationId, RabbitMsgStatusEnum.SEND_FAIL.getCode(), cause);
        }

    }

    /**
     * 未投递到queue退回模式
     *
     * @param returnedMessage 未投递到queue退回模式的消息
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        String thread = Thread.currentThread().getName();
        LOG.error("线程" + thread + "======RabbitMQ消息未投递到queue【returnedMessage=" + returnedMessage);
        // 调用 回调方法
        String correlationId = returnedMessage.getMessage().getMessageProperties().getCorrelationId();
        correlationId = StringUtils.isEmpty(correlationId) ? UUID.randomUUID().toString().replace("-", "")
                : correlationId;
        // 这里投递到broker一定是成功才会进来，所以是更新
        invokeMsgLogMethod4Send(rabbitConfig.getUpdateSendMethod(), correlationId, RabbitMsgStatusEnum.SEND_SUCCESS.getCode(),
                "RabbitMQ消息未投递到queue");
    }

    /**
     * 调用 回调方法
     *
     * @param methodName    方法名
     * @param correlationId 消息id
     * @param status        消息状态 @see  RabbitLogStatusEnum
     * @param cause         原因
     */
    public void invokeMsgLogMethod4Send(String methodName, String correlationId, Integer status, String cause) {
        String thread = Thread.currentThread().getName();
        String exchange = ThreadContext.get(LogConstants.RABBIT_EXCHANGE);
        String routingKey = ThreadContext.get(LogConstants.RABBIT_ROUTING_KEY);
        TenantDto tenantDto = ContextUtil.get();
        ThreadPoolTaskExecutor executorService = ThreadUtil.getTaskInstance();
        executorService.execute(() -> {
            // 添加主线程MDC日志到子线程
            MdcUtil.addMdcToThread(tenantDto);
            // 如果confirm返回成功 则进行更新
            String callbackService = rabbitConfig.getCallbackService();
            String initSendMethod = rabbitConfig.getInitSendMethod();
            if (StringUtils.isEmpty(callbackService) || ObjectUtils.isEmpty(initSendMethod)) {
                LOG.warn("线程" + thread + "======RabbitMQ发送消息投递时,记录消息日志警告：消息日志配置rabbit.send.callback.service={}," +
                        "rabbit.send.callback.method.init={}，如果不需要启用请忽略", callbackService, initSendMethod);
                return;
            }
            if (StringUtils.isEmpty(methodName)) {
                LOG.error("线程" + thread + "======RabbitMQ发送消息投递时,记录消息日志警告：消息日志配置rabbit.send.callback.method.confirm={}" +
                        "或rabbit.send.callback.method.init={}", callbackService, initSendMethod);
                return;
            }

            try {
                // 反射调用方法
                Class<?> clazz = Class.forName(callbackService);
                Class<?>[] paramTypes = {RabbitPropertyDto.class, String.class};
                Method method = clazz.getMethod(methodName, paramTypes);
                RabbitPropertyDto rabbitPropertyDto = RabbitPropertyDto.builder().build();
                rabbitPropertyDto.setCorrelationId(correlationId);
                rabbitPropertyDto.setStatus(status);

                if (StringUtils.equals(rabbitConfig.getInitSendMethod(), methodName)) {
                    // 新增
                    String message = RedisUtil.getString(RedisKeyEnum.RABBIT_MESSAGE.getKey(correlationId));
                    rabbitPropertyDto.setMessage(message);
                    rabbitPropertyDto.setExchange(exchange);
                    rabbitPropertyDto.setRoutingKey(routingKey);
                    rabbitPropertyDto.setQueueName(null);
                } else if (StringUtils.equals(rabbitConfig.getUpdateSendMethod(), methodName)) {
                    // 更新
                    rabbitPropertyDto.setCause(cause);
                }

                // 获取单例
                Object bean = SpringContextUtil.getBean(clazz);
                // 调用方法
                method.invoke(bean, rabbitPropertyDto, null);

                // 删除消息缓存
                RedisUtil.del(RedisKeyEnum.RABBIT_MESSAGE.getKey(correlationId));
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOG.error("线程" + thread + "======RabbitMQ消息投递时,记录消息日志异常:", e);
            }
            // 让CPU歇会儿
            try {
                TimeUnit.MILLISECONDS.sleep(10L);
            } catch (InterruptedException e) {
                LOG.error("线程" + thread + "======RabbitMQ消息投递时,记录消息日志 sleep异常:", e);
            }
        });
        // 关闭线程池
        //executorService.shutdown();

    }

}
