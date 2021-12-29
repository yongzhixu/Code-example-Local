package com.convertlab.common.beta.model.request.customer;

import com.convertlab.common.beta.model.bo.CustomerIdentity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DmHUb客户接口：多身份创建或更新客户请求参数
 *
 * @author liujun
 * @date 2021-05-06 14:33:22
 */
@Data
public class MultiIdentityCustomerReq<T> implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 客户信息*/
    private T customer;

    /** 多个身份信息 */
    private List<CustomerIdentity> identities;

}
