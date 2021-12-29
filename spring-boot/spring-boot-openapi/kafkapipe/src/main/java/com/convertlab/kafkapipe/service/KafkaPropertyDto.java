package com.convertlab.kafkapipe.service;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * RabbitMQ消息回调封装的pojo
 *
 * @author LIUJUN
 * @date 2021-04-21 00:26:13
 */
@Data
@Builder
public class KafkaPropertyDto implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** key 最好是能代表唯一性，即业务主键 */
    private String key;

    /** 主题 */
    private String topic;

    /** 消费者群id */
    private String group;

    /** 分区 */
    private Integer partition;

    /** 偏移量（消费者提交偏移量） */
    private Long offset;

    /** 时间戳 */
    private Long timestamp;

    /** 消息体 */
    private String message;

    /** 消息状态 参考 RabbitMsgStatusEnum */
    private Integer status;

    /** 原因 */
    private String cause;


}
