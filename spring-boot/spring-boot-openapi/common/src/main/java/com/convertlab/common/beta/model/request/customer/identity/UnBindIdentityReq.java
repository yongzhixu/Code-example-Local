package com.convertlab.common.beta.model.request.customer.identity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信身份与客户之间绑定关系接口入参
 *
 * @author wade
 * @date 2021-04-01 11:33:22
 */
@Data
public class UnBindIdentityReq implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** openid和unionid至少传入一个。优先根据unionid进行解绑，如果只有openid 会根据DMhub系统中openid对应的unionid进行解绑。 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String openid;

    /** wechat-unionid身份值。openid和unionid至少传入一个。 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String unionid;
}
