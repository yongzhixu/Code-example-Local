package com.convertlab.common.beta.model.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户APP 新增客户的身份信息
 *
 * @author liujun
 * @date 2021-05-06 14:33:22
 */
@Data
public class CustomerIdentityInfo implements Serializable {
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

    /** 客户ID字符串 kudu是字符串 */
    private String customerIdStr;

    /** 创建时间，UTC时间，格式为:“2017-06-01T12:12:12Z”，只读字段 */
    private String dateCreated;

    /** 最后更新时间，UTC时间，格式为:“2017-06-01T12:12:12Z”，只读字段 */
    private String lastUpdated;
    ;
}
