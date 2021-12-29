package com.convertlab.kafkapipe.service;

import com.convertlab.cache.enums.RedisKeyEnum;
import com.convertlab.cache.utils.RedisUtil;
import com.convertlab.common.beta.model.dto.TenantDto;
import com.convertlab.common.beta.utils.ContextUtil;
import com.convertlab.common.beta.utils.MdcUtil;
import com.convertlab.common.beta.utils.SpringContextUtil;
import com.convertlab.common.beta.utils.ThreadUtil;
import com.convertlab.kafkapipe.config.KafkaConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Kafka回调
 *
 * @author LIUJUN
 * @date 2021-11-24 13:50:11
 */
@Component
public class KafkaCallback {
    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(KafkaCallback.class);

    @Autowired
    private KafkaConfig kafkaConfig;

    /**
     * 调用 回调方法
     *
     * @param T             ProducerRecord或ConsumerRecord
     * @param methodName    方法名
     * @param correlationId 消息id
     * @param status        消息状态 @see  kafkaLogStatusEnum
     * @param cause         原因
     * @param record        生产消息体
     */
    public <T> void invokeMsgLogMethod4Producer(String methodName, String correlationId, Integer status, String cause,
                                                ProducerRecord record) {
        String thread = Thread.currentThread().getName();
        TenantDto tenantDto = ContextUtil.get();
        ThreadPoolTaskExecutor executorService = ThreadUtil.getTaskInstance();
        executorService.execute(() -> {
            // 添加主线程MDC日志到子线程
            MdcUtil.addMdcToThread(tenantDto);
            String operate = "";
            // 如果confirm返回成功 则进行更新
            if (StringUtils.isEmpty(kafkaConfig.getCallbackService()) || ObjectUtils.isEmpty(kafkaConfig.getInitSendMethod())) {
                LOG.warn("线程" + thread + "======Kafka消息发送时,记录消息日志警告：消息日志配置kafka.send.callback.service={}," +
                        "kafka.send.callback.method.init=消息发送，如果不需要启用请忽略", operate, kafkaConfig.getCallbackService(), kafkaConfig.getInitSendMethod());
                return;
            }
            if (StringUtils.isEmpty(methodName)) {
                LOG.error("线程" + thread + "======Kafka消息发送时,记录消息日志警告：消息日志配置kafka.send.callback.method.confirm={}" +
                        "或kafka.send.callback.method.init={}", operate, kafkaConfig.getCallbackService(), kafkaConfig.getInitSendMethod());
                return;
            }
            try {
                // 反射调用方法
                Class<?> clazz = Class.forName(kafkaConfig.getCallbackService());
                Class<?>[] paramTypes = {KafkaPropertyDto.class, String.class};
                Method method = clazz.getMethod(methodName, paramTypes);
                KafkaPropertyDto kafkaPropertyDto = KafkaPropertyDto.builder().build();
                kafkaPropertyDto.setKey(correlationId);
                kafkaPropertyDto.setStatus(status);

                if (StringUtils.equals(kafkaConfig.getInitSendMethod(), methodName)) {
                    // 新增
                    String message = RedisUtil.getString(RedisKeyEnum.KAFKA_MESSAGE.getKey(correlationId));
                    kafkaPropertyDto.setMessage(message);
                    String topic = record.topic();
                    Integer partition = record.partition();
                    Long timestamp = record.timestamp();
                    kafkaPropertyDto.setTopic(topic);
                    kafkaPropertyDto.setPartition(partition);
                    kafkaPropertyDto.setTimestamp(timestamp);
                    kafkaPropertyDto.setOffset(null);
                } else if (StringUtils.equals(kafkaConfig.getUpdateSendMethod(), methodName)) {
                    // 更新
                    kafkaPropertyDto.setCause(cause);
                } else {
                    return;
                }

                // 获取单例
                Object bean = SpringContextUtil.getBean(clazz);
                // 调用方法
                method.invoke(bean, kafkaPropertyDto, null);

                // 删除消息缓存
                RedisUtil.del(RedisKeyEnum.KAFKA_MESSAGE.getKey(correlationId));
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOG.error("线程" + thread + "======Kafka消息发送时,记录消息日志异常:", e);
            }
            // 让CPU歇会儿
            try {
                TimeUnit.MILLISECONDS.sleep(10L);
            } catch (InterruptedException e) {
                LOG.error("线程" + thread + "======Kafka消息发送时,记录消息日志 sleep异常:", e);
            }
        });
        // 关闭线程池
        //executorService.shutdown();

    }

    /**
     * 调用 回调方法
     *
     * @param methodName       方法名
     * @param kafkaPropertyDto 生产消息体
     */
    public <T> void invokeMsgLogMethod4Consumer(String methodName, KafkaPropertyDto kafkaPropertyDto) {

        String thread = Thread.currentThread().getName();
        TenantDto tenantDto = ContextUtil.get();
        ThreadPoolTaskExecutor executorService = ThreadUtil.getTaskInstance();
        executorService.execute(() -> {
            // 添加主线程MDC日志到子线程
            MdcUtil.addMdcToThread(tenantDto);
            String operate = "";
            // 如果confirm返回成功 则进行更新
            if (StringUtils.isEmpty(kafkaConfig.getReceiveCallbackService()) || ObjectUtils.isEmpty(kafkaConfig.getInitReceiveMethod())) {
                LOG.warn("线程" + thread + "======Kafka消息消费时,记录消息日志警告：消息日志配置kafka.receive.callback.service={}," +
                        "kafka.receive.callback.method.init=消息消费，如果不需要启用请忽略", operate, kafkaConfig.getReceiveCallbackService(),
                        kafkaConfig.getInitReceiveMethod());
                return;
            }
            if (StringUtils.isEmpty(methodName)) {
                LOG.error("线程" + thread + "======Kafka消息消费时,记录消息日志警告：消息日志配置kafka.receive.callback.method.confirm={}" +
                        "或kafka.receive.callback.method.init={}", operate, kafkaConfig.getInitReceiveMethod(),
                        kafkaConfig.getInitReceiveMethod());
                return;
            }
            try {
                // 反射调用方法
                Class<?> clazz = Class.forName(kafkaConfig.getReceiveCallbackService());
                Class<?>[] paramTypes = {KafkaPropertyDto.class, String.class};
                Method method = clazz.getMethod(methodName, paramTypes);
                // 获取单例
                Object bean = SpringContextUtil.getBean(clazz);
                // 调用方法
                method.invoke(bean, kafkaPropertyDto, null);

            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOG.error("线程" + thread + "======Kafka消息消费时,记录消息日志异常:", e);
            }
            // 让CPU歇会儿
            try {
                TimeUnit.MILLISECONDS.sleep(10L);
            } catch (InterruptedException e) {
                LOG.error("线程" + thread + "======Kafka消息消费时,记录消息日志 sleep异常:", e);
            }
        });
        // 关闭线程池
        //executorService.shutdown();

    }
}
