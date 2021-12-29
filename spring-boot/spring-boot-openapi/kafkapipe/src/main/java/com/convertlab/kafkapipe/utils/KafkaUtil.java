package com.convertlab.kafkapipe.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Kafka工具类
 *
 * @author LIUJUN
 * @date 2021-07-02 13:21:12
 */
public class KafkaUtil {
    private KafkaUtil() {

    }

    /**
     * 这个不打印value，防止太长，如果要打印ConsumerRecord完整信息，直接toString就行了
     *
     * @param record ConsumerRecord
     * @param <K>    key
     * @param <V>    value
     * @return String
     */
    public static <K, V> String getRecord4NoValue(ConsumerRecord<K, V> record) {
        return "ConsumerRecord(topic = " + record.topic()
                + ", partition = " + record.partition()
                + ", leaderEpoch = " + record.leaderEpoch().orElse(null)
                + ", offset = " + record.offset()
                + ", " + record.timestampType() + " = " + record.timestamp()
                + ", serialized key size = " + record.serializedKeySize()
                + ", serialized value size = " + record.serializedValueSize()
                + ", headers = " + record.headers()
                + ", key = " + record.key()
                + ", value = ......)";
    }
}
