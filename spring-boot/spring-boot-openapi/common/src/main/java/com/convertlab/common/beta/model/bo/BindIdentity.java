package com.convertlab.common.beta.model.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * DmHUb 绑定身份的信息-客户合并、解绑等用的
 *
 * @author LIUJUN
 * @date 2021-03-02 11:43:20
 */
@Data
public class BindIdentity implements Serializable {

    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 身份类型 */
    private String identityType;

    /** 身份值 */
    private String identityValue;
}