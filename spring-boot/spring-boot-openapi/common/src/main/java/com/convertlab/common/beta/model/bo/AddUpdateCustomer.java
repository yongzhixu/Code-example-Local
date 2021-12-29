package com.convertlab.common.beta.model.bo;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 新增或更新的客户信息
 *
 * @author liujun
 * @date 2021-04-11 14:22:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddUpdateCustomer extends DmHubErrorResp {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 是否新增，ture：是 */
    private Boolean addFlag;

    /** 客户id */
    private Long id;

}

