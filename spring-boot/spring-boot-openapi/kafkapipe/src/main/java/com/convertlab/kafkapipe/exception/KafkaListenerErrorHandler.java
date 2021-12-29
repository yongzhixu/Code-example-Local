package com.convertlab.kafkapipe.exception;

import com.convertlab.common.beta.constants.BaseConstant;
import com.convertlab.common.beta.enums.KafkaMsgStatusEnum;
import com.convertlab.kafkapipe.config.KafkaConfig;
import com.convertlab.kafkapipe.service.KafkaCallback;
import com.convertlab.kafkapipe.service.KafkaPropertyDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;

/**
 * 自定义消费者异常处理器
 *
 * @author liujun
 * @date 2021-05-24 16:43:21
 */
@Configuration
public class KafkaListenerErrorHandler {

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(KafkaListenerErrorHandler.class);

    @Autowired
    private KafkaCallback kafkaCallback;

    @Autowired
    private KafkaConfig kafkaConfig;

    /**
     * 单次消费阻塞异常处理器（Cl代表Convertlab）
     *
     * @return ConsumerAwareListenerErrorHandler
     */
    @Bean("clErrorHandler")
    public ConsumerAwareListenerErrorHandler clErrorHandler() {
        return (message, exception, consumer) -> {
            LOG.info("--- KAFKA消费时发生异常 ---", exception);
            // 调用 回调方法
            Object payload = message.getPayload();
            Integer partition = null;
            String topic = null;
            Long offset = null;
            Long timestamp = null;
            String bizId = null;
            String body = null;
            if (payload instanceof ConsumerRecord) {
                ConsumerRecord record = (ConsumerRecord) payload;
                partition = record.partition();
                topic = record.topic();
                offset = record.offset();
                timestamp = record.timestamp();
                bizId = getValue(record, BaseConstant.MESSAGE_BIZ_ID, String.valueOf(record.key()));
                body = getValue(record, BaseConstant.MESSAGE_BIZ_BODY, (String) record.value());
            } /*else if (message.getHeaders() instanceof KafkaMessageHeaders) {
                KafkaMessageHeaders headers = (KafkaMessageHeaders) message.getHeaders();
                partition = headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class);
                topic = headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class);
                offset = headers.get(KafkaHeaders.OFFSET, Long.class);
                timestamp = headers.get(KafkaHeaders.RECEIVED_TIMESTAMP, Long.class);
                bizId = (String) headers.get(BaseConstant.MESSAGE_BIZ_ID);
                bizId = StringUtils.isEmpty(bizId) ? (String) headers.get(KafkaHeaders.RECEIVED_MESSAGE_KEY) : bizId;
                // body = String.valueOf(payload);
            } */ else {
                LOG.warn("kafka 消费者引用clErrorHandler Bean来处理消费异常问题时，消费者@KafkaListener监听类方法参数至少含有ConsumerRecord和@Header中一个！");
                return 0;
            }
            String method = kafkaConfig.getInitReceiveMethod();
            // 如果没有拦截器初始化日志，则更新
            /*if (StringUtils.isNotEmpty(kafkaConfig.getInitReceiveMethod())) {
                method = kafkaConfig.getUpdateSendMethod();
            }*/
            if (StringUtils.isEmpty(method)) {
                return 0;
            }
            KafkaPropertyDto kafkaPropertyDto = KafkaPropertyDto.builder().build();
            kafkaPropertyDto.setKey(bizId);
            kafkaPropertyDto.setMessage(body);
            kafkaPropertyDto.setTopic(topic);
            kafkaPropertyDto.setPartition(partition);
            kafkaPropertyDto.setTimestamp(timestamp);
            kafkaPropertyDto.setOffset(offset);
            kafkaPropertyDto.setStatus(KafkaMsgStatusEnum.CONSUME_FAIL.getCode());
            kafkaPropertyDto.setCause(exception.toString());
            kafkaCallback.invokeMsgLogMethod4Consumer(method, kafkaPropertyDto);
            return 1;
        };
    }

    /**
     * 批量消费阻塞异常处理器（Cl代表Convertlab）
     *
     * @return ConsumerAwareListenerErrorHandler
     */
    @Bean("clBatchErrorHandler")
    public ConsumerAwareListenerErrorHandler clBatchErrorHandler() {
        return (message, exception, consumer) -> {
            LOG.warn("--- KAFKA批量消费时发生异常,开始记录到日志表里 ---");
            // 如果是批量
            Object payload = message.getPayload();
            if (!(payload instanceof List)) {
                LOG.warn("kafka 消费者引用clBatchErrorHandler Bean来处理批量消费异常问题时，消费者@KafkaListener监听类方法参数至少含有List<ConsumerRecord>或ConsumerRecords其中一个！");
                return 0;
            }
            List<?> records = ((List) payload);
            for (Object data : records) {
                if (!(data instanceof ConsumerRecord)) {
                    continue;
                }

                String method = kafkaConfig.getInitReceiveMethod();
                // 如果没有拦截器初始化日志，则更新
                //if (StringUtils.isNotEmpty(kafkaConfig.getInitReceiveMethod())) {  }
                if (StringUtils.isEmpty(method)) {
                    return 0;
                }
                ConsumerRecord record = (ConsumerRecord) data;
                String key = (String) record.key();
                Object value = ((ConsumerRecord<?, ?>) data).value();
                String bizId = getValue(record, BaseConstant.MESSAGE_BIZ_ID, key);
                String body = getValue(record, BaseConstant.MESSAGE_BIZ_BODY, String.valueOf(value));

                KafkaPropertyDto kafkaPropertyDto = KafkaPropertyDto.builder().build();
                kafkaPropertyDto.setKey(bizId);
                kafkaPropertyDto.setMessage(body);
                kafkaPropertyDto.setTopic(record.topic());
                kafkaPropertyDto.setPartition(record.partition());
                kafkaPropertyDto.setTimestamp(record.timestamp());
                kafkaPropertyDto.setOffset(record.offset());
                kafkaPropertyDto.setStatus(KafkaMsgStatusEnum.CONSUME_FAIL.getCode());
                kafkaPropertyDto.setCause(exception.toString());
                kafkaCallback.invokeMsgLogMethod4Consumer(method, kafkaPropertyDto);
            }
            return 1;
        };
    }

    /**
     * 获取Headers里的某个key的value
     *
     * @param consumerRecord 消费者记录
     * @return value, 如果key对应的header没有值，则默认取defaultValue
     */
    public String getValue(ConsumerRecord consumerRecord, String key, String defaultValue) {
        Header header = consumerRecord.headers().lastHeader(key);
        if (header == null) {
            return defaultValue;
        }
        return new String(header.value());
    }


    /**
     * 消费者异常消息放入死信队列里
     *
     * @param template kafka发送模板类
     * @return SeekToCurrentErrorHandler
     */
    @Bean("dlHandler")
    public SeekToCurrentErrorHandler seekToCurrentErrorHandler(@Autowired(required = false) KafkaOperations<String, String> template) {
        if (template == null) {
            return null;
        }
        return new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(
                template/*,
                (cr, e) -> new TopicPartition(cr.topic() + ".DLT", cr.partition())*/),
                // 5分钟重试一次，重试次数不限制3次
                new FixedBackOff(5 * 60 * 1000L, 3L));
    }

}