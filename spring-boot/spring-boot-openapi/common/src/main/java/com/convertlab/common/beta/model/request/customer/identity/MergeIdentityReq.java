package com.convertlab.common.beta.model.request.customer.identity;

import com.convertlab.common.beta.model.bo.BindIdentity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DmHUb 合并身份 接口入参通用字段
 *
 * @author LIUJUN
 * @date 2021-02-28 14:03:26
 */
@Data
public class MergeIdentityReq implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 源客户的客户身份信息 */
    @NotNull
    private BindIdentity from;

    /** 目标客户的客户身份信息 */
    @NotNull
    private BindIdentity to;

}
