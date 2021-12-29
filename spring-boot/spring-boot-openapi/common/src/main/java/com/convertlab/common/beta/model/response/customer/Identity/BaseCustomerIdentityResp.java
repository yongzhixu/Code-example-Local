package com.convertlab.common.beta.model.response.customer.Identity;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * DmHUb 客户身份接口返回参数-通用字段
 *
 * @author liujun
 * @date 2021-04-11 14:22:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseCustomerIdentityResp extends DmHubErrorResp {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 身份id，只读字段 */
    private Long id;

    /** 身份类型 */
    private String type;

    /** 身份值 */
    private String value;

    /** 身份名称 */
    private String name;

    /** 客户ID */
    private Long customerId;

    /** String	客户id，只读字段 */
    private String customerIdStr;

    /** 创建时间，UTC时间，格式为:“2017-06-01T12:12:12Z”，只读字段 */
    private String dateCreated;

    /** 最后更新时间，UTC时间，格式为:“2017-06-01T12:12:12Z”，只读字段 */
    private String lastUpdated;
}
