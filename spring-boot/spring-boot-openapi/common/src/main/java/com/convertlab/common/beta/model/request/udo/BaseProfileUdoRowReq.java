package com.convertlab.common.beta.model.request.udo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DmHUb UDO-对象数据列表(行) 接口入参通用字段
 * 自定义属性、自定义事件等非固定字段不要写在这里
 *
 * @author LIUJUN
 * @date 2021-02-28 14:03:26
 */
@Data
public class BaseProfileUdoRowReq implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 主键: 行的主键 */
    @NotNull
    private String bk;

    /** 行的名称 */
    @NotNull
    private String name;

    /** 客户id */
    @NotNull
    private String customerId;
}
