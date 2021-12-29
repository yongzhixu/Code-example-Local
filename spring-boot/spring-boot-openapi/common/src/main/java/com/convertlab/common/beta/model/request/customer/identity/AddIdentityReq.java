package com.convertlab.common.beta.model.request.customer.identity;

import lombok.Data;

import java.io.Serializable;

/**
 * 身份绑定接口入参
 *
 * @author wade
 * @date 2021-04-01 11:33:22
 */
@Data
public class AddIdentityReq implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 客户ID */
    private Long customerId;

    /** 客户身份类型 */
    private String type;

    /** 客户身份值 */
    private String value;

}
