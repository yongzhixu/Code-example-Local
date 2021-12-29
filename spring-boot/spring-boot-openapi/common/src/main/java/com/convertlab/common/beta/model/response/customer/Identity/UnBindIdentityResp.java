package com.convertlab.common.beta.model.response.customer.Identity;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 客户身份、微信解绑返回参数
 *
 * @author LIUJUN
 * @date 2021-02-05 22:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UnBindIdentityResp extends DmHubErrorResp implements Serializable {

    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 解绑后新生成客户的客户ID */
    private Long customerId;

    /** 新客户所包括的身份。微信unionid身份和此unionid对应的所有openid身份。 */
    private List<BaseCustomerIdentityResp> identities;

}
