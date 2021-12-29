package com.convertlab.common.beta.model.response.customer.Identity;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 注销客户App推送身份 根据客户身份注销返回值
 *
 * @author wade
 * @date 2021-05-09 9:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeregisterResp extends DmHubErrorResp implements Serializable {

    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 客户App推送身份集合 */
    private List<BasePushInfoResp> data;


}