package com.convertlab.rabbitmqpipe.service;

import com.convertlab.common.beta.utils.MdcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * RabbitMQ生产者
 *
 * @author liujun
 * @date 2021-04-05 19:55:44
 */
@Component
public class RabbitProducer {
    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(RabbitProducer.class);

    /** 当前对象指针 */
    private static RabbitProducer self;

    /** BatchingRabbitTemplate */
    private BatchingRabbitTemplate batchRabbitTemplate;

    /** RabbitTemplate */
    private RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate, @Qualifier("batchRabbitTemplate")
            BatchingRabbitTemplate batchRabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.batchRabbitTemplate = batchRabbitTemplate;
    }

    @PostConstruct
    public void init() {
        self = this;
        self.batchRabbitTemplate = this.batchRabbitTemplate;
        self.rabbitTemplate = this.rabbitTemplate;
    }

    /**
     * 1. 发送RabbitMQ消息-无回调信息
     *
     * @param exchange      交换器
     * @param routingKey    路由器key
     * @param message       消息体
     * @param correlationId 消息唯一值
     */
    public static void send(String exchange, String routingKey, Object message, String correlationId) {
        MdcUtil.addRabbitExchangeToMdc(exchange, routingKey, null);
        //CorrelationData对象的作用是作为消息的附加信息传递，通常我们用它来保存消息的自定义id
        CorrelationData correlationData = new CorrelationData(correlationId);
        LOG.info("发送RabbitMQ消息-无回调信息,exchange={},routingKey={},message={},correlationId={}", exchange, routingKey, message, correlationId);
        self.rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    /**
     * 2. 发送RabbitMQ消息-有回调信息
     *
     * @param exchange      交换器
     * @param routingKey    路由器key
     * @param message       消息体
     * @param correlationId 消息唯一值
     */
    public static void sendCallback(String exchange, String routingKey, Object message, String correlationId) {
        MdcUtil.addRabbitExchangeToMdc(exchange, routingKey, null);
        //CorrelationData对象的作用是作为消息的附加信息传递，通常我们用它来保存消息的自定义id
        CorrelationData correlationData = new CorrelationData(correlationId);
        LOG.info("发送RabbitMQ消息-有回调信息,exchange={},routingKey={},message={},correlationId={}", exchange, routingKey, message, correlationId);
        self.rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    /**
     * 3. 批量发送RabbitMQ消息-有回调信息，且可有补偿措施
     *
     * @param exchange      交换器
     * @param routingKey    路由器key
     * @param message       消息体
     * @param correlationId 消息唯一值
     */
    public static void batchSendCallback(String exchange, String routingKey, Object message, String correlationId) {
        MdcUtil.addRabbitExchangeToMdc(exchange, routingKey, null);
        //CorrelationData对象的作用是作为消息的附加信息传递，通常我们用它来保存消息的自定义id
        CorrelationData correlationData = new CorrelationData(correlationId);
        LOG.info("批量发送RabbitMQ消息-有回调信息,exchange={},routingKey={},message={},correlationId={}", exchange, routingKey, message, correlationId);
        self.batchRabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }
}
