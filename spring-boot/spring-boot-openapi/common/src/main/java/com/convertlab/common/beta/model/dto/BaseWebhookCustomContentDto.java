package com.convertlab.common.beta.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DmHUb webhook向外发送的数据内容-自定义消息通用字段
 * 类似自定义等非固定字段不要写在这里
 *
 * @author LIUJUN
 * @date 2021-02-17 14:02:26
 */
@Data
public class BaseWebhookCustomContentDto implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 本次发送的消息ID，用于调试  这个命名产品那边定义的 大写命名有点坑 */
    //@NotNull
    private String MESSAGEID;

    /** 发送消息的租户ID */
    @NotNull
    private Long tenantId;

    /** 自动流程ID */
    @NotNull
    private String flowId;

    /** Webhook Id */
    @NotNull
    private String webhookId;

    /** 该次发送的批次号（不一定有）取值格式：flow-$flowId_$flowVersion_false@@$stepId，如flow-1673_2_false@@3 */
    private String batchId;

    /** 自动流程的步骤ID */
    @NotNull
    private String flowStep;

    /** 自动流程版本号 */
    @NotNull
    private String flowVersion;

    /** 营销活动编码 */
    private String campaign;
}
