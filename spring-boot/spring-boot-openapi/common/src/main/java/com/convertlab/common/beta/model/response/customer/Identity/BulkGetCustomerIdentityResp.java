package com.convertlab.common.beta.model.response.customer.Identity;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 获取客户身份接口返回参数
 *
 * @author liujun
 * @date 2021-04-11 14:22:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BulkGetCustomerIdentityResp extends DmHubErrorResp implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 序列号 */
    List<BaseCustomerIdentityResp> data;

}
