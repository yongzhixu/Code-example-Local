package com.convertlab.common.beta.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户信息
 *
 * @author LIUJUN
 * @date 2021-01-26 21:48
 */
@Data
public class TenantDto implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 上游服务名  */
    private String upstream;

    /** 请求ID  */
    private String requestId;

    /** 租户ID */
    private Long tenantId;

    /** 登录用户ID */
    private Long userId;

    /** 登录用户 */
    private String userName;

    /** 部门ID */
    private Long deptId;

    /** 访问token */
    private String accessToken;

}
