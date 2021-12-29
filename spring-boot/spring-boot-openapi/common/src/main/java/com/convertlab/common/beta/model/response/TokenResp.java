package com.convertlab.common.beta.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Token返回的参数
 *
 * @author LIUJUN
 * @date 2021-01-19 17:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TokenResp extends DmHubErrorResp {

    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 访问TOKEN的有效时间 毫秒*/
    private Long expires_in;

    /** 访问TOKEN */
    private String access_token;
}
