package com.convertlab.kafkapipe.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka配置
 *
 * @author LIUJUN
 * @date 2021-11-29 17:07:56
 */
@Configuration
@Getter
public class KafkaConfig {

    /** 消息回调的业务类 */
    @Value("${kafka.send.callback.service:}")
    private String callbackService;

    /** 发送消息回调的业务类的方法-前初始化消息日志 */
    @Value("${kafka.send.callback.method.init:}")
    private String initSendMethod;

    /** 发送消息回调的业务类的方法--是否成功日志 1:是 */
    @Value("${kafka.send.callback.isRecordSuccess:0}")
    private Integer recordSendSuccessFlag;

    /** 发送消息回调的业务类的方法-消息投递失败或成功 */
    @Value("${kafka.send.callback.method.update:}")
    private String updateSendMethod;

    /** 发送消息拦截器 */
    @Value("${spring.kafka.producer.properties.interceptor.classes:}")
    private String sendInterceptors;

    /** 消费者消息拦截器 */
    @Value("${spring.kafka.consumer.properties.interceptor.classes:}")
    private String consumerInterceptors;

    /** 接收(消费)消息回调的业务类 */
    @Value("${kafka.receive.callback.service:}")
    private String receiveCallbackService;

    /** 接收(消费)消息回调的业务类的方法-前初始化消息日志 */
    @Value("${kafka.receive.callback.method.init:}")
    private String initReceiveMethod;

    /** 接收(消费)消息回调的业务类的方法-是否成功日志 1:是 */
    @Value("${kafka.receive.callback.isRecordSuccess:0}")
    private Integer recordReceiveSuccessFlag;
}
