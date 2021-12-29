package com.convertlab.common.beta.model.response.customer.Identity;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * DmHUb 合并身份 接口出参通用字段
 *
 * @author LIUJUN
 * @date 2021-02-28 14:03:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MergeIdentityResp extends DmHubErrorResp implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 源客户的客户身份信息 */
    private Long customerId;

}
