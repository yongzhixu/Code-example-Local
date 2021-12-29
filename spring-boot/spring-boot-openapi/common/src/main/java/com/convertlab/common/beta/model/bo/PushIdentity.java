package com.convertlab.common.beta.model.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户APP push推送身份信息
 *
 * @author wade
 * @date 2021-04-01 11:33:22
 */
@Data
public class PushIdentity implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 身份类型 */
    private String type;

    /** 身份值 */
    private String value;
}
