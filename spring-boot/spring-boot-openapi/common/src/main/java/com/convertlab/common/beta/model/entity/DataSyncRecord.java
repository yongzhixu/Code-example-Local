package com.convertlab.common.beta.model.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * CDP数据同步记录表
 *
 * @author liujun
 * @date 2021-01-29 16:02:35
 */
@Data
public class DataSyncRecord implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 主键 */
    protected Long id;

    /** 租户ID */
    // @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;

    /** Job ID */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long jobId;

    /** Job 任务处理器 */
    private String jobHandler;

    /** 模块名称(1-客户,2-订单,3-商品,4-优惠券,5-领券,6-门店,7-事件采集) */
    private Integer moduleName;

    /** 同步方式(1-接口同步,2- ImpalaShell导入,3- SparkETL导入) */
    private Integer syncType;

    /** 接口url(不含?及其后面的参数) */
    private String apiUrl;

    /** 文件名称 */
    private String fileName;

    /** DMP数据量 */
    private BigDecimal dmpNum;

    /** CDP数据量 (失败数量=数据量-新增-修改) */
    private BigDecimal cdpNum;

    /** CDP新增数据量 */
    private BigDecimal cdpAddNum;

    /** CDP修改数据量 */
    private BigDecimal cdpUpdateNum;

    /** 同步状态：-1:处理中 1-成功，0-失败 */
    private Integer status;

    /** 失败原因 */
    private String failureCause;

    /** 重试次数 */
    private Integer retryNum;

    /** 同步开始时间 */
    private java.util.Date beginTime;

    /** 同步结束时间 */
    private java.util.Date endTime;

    /** 备注 */
    private String remark;

    /** 版本号 */
    protected Integer version;
}
