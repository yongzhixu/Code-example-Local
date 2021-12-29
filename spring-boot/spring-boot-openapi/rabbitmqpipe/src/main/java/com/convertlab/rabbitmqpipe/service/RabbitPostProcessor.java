package com.convertlab.rabbitmqpipe.service;

import com.convertlab.cache.enums.RedisKeyEnum;
import com.convertlab.cache.utils.RedisUtil;
import com.convertlab.common.beta.constants.HeaderConstant;
import com.convertlab.common.beta.enums.RabbitMsgStatusEnum;
import com.convertlab.common.beta.model.dto.TenantDto;
import com.convertlab.common.beta.utils.ContextUtil;
import com.convertlab.common.beta.utils.MdcUtil;
import com.convertlab.common.beta.utils.SpringContextUtil;
import com.convertlab.rabbitmqpipe.config.RabbitConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

/**
 * RabbitMQ消息前置处理器
 *
 * @author LIUJUN
 * @date 2021-04-23 19:26:13
 */
public class RabbitPostProcessor {

    /**
     * 推送消息前置处理器
     */
    public final static MessagePostProcessor publishMessagePostProcessor = new MessagePostProcessor() {
        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            TenantDto tenantDto = ContextUtil.get();
            if (tenantDto == null) {
                return message;
            }
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setHeader("x_tenant_id", tenantDto.getTenantId());
            messageProperties.setHeader("x_user_id", tenantDto.getUserId());
            messageProperties.setHeader("x_user_name", tenantDto.getUserName());
            messageProperties.setHeader("x_request_id", tenantDto.getRequestId());
            messageProperties.setHeader("x_upstream", tenantDto.getUpstream());
            RabbitConfig rabbitConfig = SpringContextUtil.getBean(RabbitConfig.class);
            String initSendMethod = rabbitConfig == null ? null : rabbitConfig.getInitSendMethod();
            if (!ObjectUtils.isEmpty(initSendMethod)) {

                // 调用 回调方法-推送RabbitMq消息前，初始化消息日志
                RabbitCallback rabbitCallback = SpringContextUtil.getBean(RabbitCallback.class);

                if (rabbitCallback != null) {
                    String messageId = messageProperties.getCorrelationId();
                    messageId = StringUtils.isEmpty(messageId) ? UUID.randomUUID().toString().replace("-", "")
                            : messageId;
                    // 保存消息体一定时长
                    RedisUtil.set(RedisKeyEnum.RABBIT_MESSAGE.getKey(messageId), new String(message.getBody()),
                            RedisKeyEnum.RABBIT_MESSAGE.getExpire());

                    rabbitCallback.invokeMsgLogMethod4Send(initSendMethod, messageId, RabbitMsgStatusEnum.SENDING.getCode(), null);
                }
            }

            return message;
        }

        @Override
        public Message postProcessMessage(Message message, Correlation correlation) {
            String id = message.getMessageProperties().getMessageId();
            if (correlation instanceof CorrelationData) {
                id = ((CorrelationData) correlation).getId();
            }
            message.getMessageProperties().setCorrelationId(id);
            return this.postProcessMessage(message);
        }
    };

    /**
     * 接受消息前置处理器
     */
    public final static MessagePostProcessor receiveMessagePostProcessor = message -> {
        TenantDto tenantDto = ContextUtil.get();
        if (tenantDto == null) {
            tenantDto = new TenantDto();
            ContextUtil.set(tenantDto);
        }
        MessageProperties properties = message.getMessageProperties();
        tenantDto.setUserId(properties.getHeader(HeaderConstant.X_USER_ID));
        tenantDto.setUserName(properties.getHeader(HeaderConstant.LOGIN_NAME));
        tenantDto.setRequestId(properties.getHeader(HeaderConstant.X_REQUEST_ID));
        tenantDto.setUpstream(properties.getHeader(HeaderConstant.X_UPSTREAM));
        // 添加header到MDC
        MdcUtil.addRabbitConsumerToMdc(tenantDto);
        MdcUtil.addRabbitExchangeToMdc(properties.getReceivedExchange(), properties.getReceivedRoutingKey(), properties.getConsumerQueue());
        return message;
    };

}