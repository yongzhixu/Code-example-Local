package com.convertlab.common.beta.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 手动执行Job的参数 实体 这个不通用，只适合万家项目
 *
 * @author LIUJUN
 * @date 2021-06-23 11:46:21
 */
@Data
public class JobParamDto implements Serializable {

    /** 是否手动执行标志 参考YesOrNoEnum枚举 */
    private Integer manualFlag;

    /** Dm层的job的occurdate ，即job执行哪一天的业务数据 */
    private String dmJobTransTime;

    /** 执行哪一天的业务数据 */
    private String transTime;

    /** 哪天执行的 */
    private String dataDate;

    /** 区分唯一的id集合 */
    private List<String> ids;

    /** 第N页开始 */
    private Integer startPage;

    /** 第N页结束 */
    private Integer endPage;

    /**  中断任务标志（1-是，0或空-否），可以手动中断正在进行中的任务，包括任务停止了但上次任务还在进行的任务， */
    private Integer suspendedTaskFlag;

}
