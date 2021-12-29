package com.convertlab.common.beta.model.request.customer;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * DmHUb客户接口：创建或更新客户客户信息请求参数
 *
 * @author liujun
 * @date 2021-05-06 14:33:22
 */
@Data
public class UpdateCustomerReq implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 更新的属性 */
    private Map<String, Object> update;

}
