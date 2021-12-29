package com.convertlab.common.beta.model.request.customer;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DmHUb 客户事件接口入参通用字段
 * 自定义属性、自定义事件等非固定字段不要写在这里
 *
 * @author LIUJUN
 * @date 2021-02-21 14:03:26
 */
@Data
public class BulkAddCustomerEventReq<T extends BaseCustomerEventReq> implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 是	批量客户事件 */
    protected List<T> events;
}
