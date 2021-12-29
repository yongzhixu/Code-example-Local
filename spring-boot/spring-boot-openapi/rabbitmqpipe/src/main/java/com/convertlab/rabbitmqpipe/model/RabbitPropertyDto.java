package com.convertlab.rabbitmqpipe.model;

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
public class RabbitPropertyDto implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    /** 消息id */
    private String correlationId;

    /** 交换器名称 */
    private String exchange;

    /** 路由关键字 */
    private String routingKey;

    /** 消息队列名称 */
    private String queueName;

    /** 消息体 */
    private String message;

    /** 消息状态 参考 RabbitMsgStatusEnum */
    private Integer status;

    /** 原因 */
    private String cause;

}
