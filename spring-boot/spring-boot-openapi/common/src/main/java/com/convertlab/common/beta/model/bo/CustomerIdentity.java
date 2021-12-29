package com.convertlab.common.beta.model.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户APP 新增客户的身份信息
 *
 * @author liujun
 * @date 2021-05-06 14:33:22
 */
@Data
public class CustomerIdentity implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 身份类型 */
    private String type;

    /** 身份值 */
    private String value;

    /** 身份昵称 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    ;
}
