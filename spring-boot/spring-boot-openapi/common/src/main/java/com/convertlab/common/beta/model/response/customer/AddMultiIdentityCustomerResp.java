package com.convertlab.common.beta.model.response.customer;

import com.convertlab.common.beta.model.bo.CustomerIdentity;
import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 多身份创建或更新客户返回参数
 *
 * @author LIUJUN
 * @date 2021-02-07 16:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddMultiIdentityCustomerResp extends DmHubErrorResp implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 客户id */
    private Long customerId;

    /** 请求数据中绑定到目标客户的身份 */
    private List<CustomerIdentity> newBindIdentities;

    /** 请求数据中与目标客户身份有冲突的身份 */
    private List<CustomerIdentity> conflictIdentities;

    /** 合并到目标客户的客户ID */
    private List<Long> mergedCustomers;

}
