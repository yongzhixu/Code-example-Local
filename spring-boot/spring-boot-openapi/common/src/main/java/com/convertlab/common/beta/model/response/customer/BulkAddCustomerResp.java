package com.convertlab.common.beta.model.response.customer;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * DmHUb 批量创建客户身份接口返回参数
 *
 * @author liujun
 * @date 2021-04-11 14:22:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BulkAddCustomerResp extends DmHubErrorResp {

    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 是否成功，ture：是 */
    private Boolean success;

    /** 消息 */
    private String message;
}
