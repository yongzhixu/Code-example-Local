package com.convertlab.kafkapipe.service;

import com.convertlab.common.beta.enums.KafkaMsgStatusEnum;
import com.convertlab.common.beta.enums.YesOrNoEnum;
import com.convertlab.common.beta.exception.BizException;
import com.convertlab.kafkapipe.config.KafkaConfig;
import com.convertlab.kafkapipe.utils.KafkaMdcUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Kafka生产者
 *
 * @author liujun
 * @date 2021-01-05 17:55:10
 */
@Component
public class KafkaProducer {
    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    /** 当前对象指针 */
    private static KafkaProducer self;

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaCallback KafkaCallback;

    @Autowired
    private KafkaConfig kafkaConfig;

    @PostConstruct
    public void init() {
        self = this;
        self.kafkaTemplate = this.kafkaTemplate;
        self.KafkaCallback = this.KafkaCallback;
        self.kafkaConfig = this.kafkaConfig;
    }

    /**
     * 1. 发送Kafka消息-异步无阻塞且无返回体
     *
     * @param topic   主题 这里限制非空，实际Kafka可以为空
     * @param key     键，相同的键在同一个分区，保证顺序性,可以为空
     * @param message 消息体
     */
    public static void send(@NotNull String topic, String key, Object message) {
        String thread = Thread.currentThread().getName();
        //构造一个标准的消息进行发送
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, key, message);
        LOGGER.info("线程{}======发送Kafka消息开始...【topic={},key={},message={}】", thread, topic, key, message);
        self.kafkaTemplate.send(record);

    }

    /**
     * 2. 发送Kafka消息-异步无阻塞且有回调信息
     *
     * @param topic   主题 这里限制非空，实际Kafka可以为空
     * @param key     键，相同的键在同一个分区，保证顺序性,可以为空
     * @param message 消息体
     */
    public static void sendCallback(@NotNull String topic, String key, Object message) {

        String thread = Thread.currentThread().getName();
        // 1. 构建发送消息
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, key, message);

        // 2. 将MDC的信息添加header
        KafkaMdcUtil.setKafkaHeader(record);

        LOGGER.info("线程{}======异步回调发送Kafka消息开始...【topic={},key={},hashCode={},{}】", thread, topic, key,
                record.hashCode(), record.toString());

        // 尝试发送消息
        ListenableFuture<SendResult<String, Object>> future = self.kafkaTemplate.send(record);
        // 发送消息回调
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            /** 方法名  如果配置kafka自定义拦截器，则用更新方法，否则用初始化（新增）方法*/
            private String method = StringUtils.isNotEmpty(self.kafkaConfig.getSendInterceptors())
                    ? self.kafkaConfig.getUpdateSendMethod() : self.kafkaConfig.getInitSendMethod();

            /**
             * 发送成功回调方法
             * @param sendResultMap 发送返回结果
             */
            @Override
            public void onSuccess(SendResult<String, Object> sendResultMap) {
                LOGGER.info("线程{}======异步回调发送Kafka消息成功【topic={},key={},hashCode={}】", thread, topic, key,
                        sendResultMap.getProducerRecord().hashCode());
                // 不记录成功日志
                if (!YesOrNoEnum.Y.getCode().equals(self.kafkaConfig.getRecordSendSuccessFlag())) {
                    return;
                }
                // 如果没有拦截器初始化日志，则更新
                if (StringUtils.isNotEmpty(self.kafkaConfig.getSendInterceptors())) {
                    method = self.kafkaConfig.getUpdateSendMethod();
                }
                // 调用 回调方法
                String correlationId = key;
                correlationId = StringUtils.isEmpty(correlationId) ? UUID.randomUUID().toString().replace("-", "")
                        : correlationId;
                self.KafkaCallback.invokeMsgLogMethod4Producer(method, correlationId, KafkaMsgStatusEnum.SEND_SUCCESS.getCode(),
                        null, sendResultMap.getProducerRecord());
            }

            /**
             * 发送失败回调方法
             * @param throwable 异常
             */
            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.error("线程" + thread + "======异步回调发送Kafka消息失败【topic=" + topic + ",key=" + key + "】: ", throwable);
                // 调用 回调方法
                String correlationId = key;
                correlationId = StringUtils.isEmpty(correlationId) ? UUID.randomUUID().toString().replace("-", "")
                        : correlationId;
                String cause = throwable.toString();
                self.KafkaCallback.invokeMsgLogMethod4Producer(method, correlationId, KafkaMsgStatusEnum.SEND_FAIL.getCode(),
                        cause, record);
            }
        });
    }

    /**
     * 3. 发送Kafka消息-同步阻塞
     *
     * @param topic   主题 这里限制非空，实际Kafka可以为空
     * @param key     键，相同的键在同一个分区，保证顺序性,可以为空
     * @param message 消息体
     */
    public static void sendSync(@NotNull String topic, String key, Object message) {
        String thread = Thread.currentThread().getName();
        List<Header> headers = new ArrayList<>();
        new RecordHeaders(headers);
        // 1. 构建发送消息
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, key, message);
        // 2. 将MDC的信息添加header
        KafkaMdcUtil.setKafkaHeader(record);

        LOGGER.info("线程{}======同步阻塞发送Kafka消息【topic={},key={},{}】开始...", thread, topic, key, message);

        try {
            // 使用模板发送消息
            self.kafkaTemplate.send(record).get(10, TimeUnit.SECONDS);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.error("线程" + thread + "======同步阻塞发送Kafka消息异常【topic=" + topic + ",key=" + key + "】: ", e);
            throw new BizException("线程" + thread + "======同步阻塞发送Kafka消息异常:" + e.getMessage());
        }
    }

}
