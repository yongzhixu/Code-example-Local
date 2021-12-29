package com.convertlab.common.beta.model.request.customer;

import com.convertlab.common.beta.model.bo.PushIdentity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author wade
 */
@Data
public class PushInfoReq implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 客户ID，如果为0，则表示App推送身份是匿名身份（未与具体客户关联） */
    private Long customerId;

    /** 客户ID字符串，如果为"0"，则表示App推送身份是匿名身份（未与具体客户关联） */
    private String customerIdStr;

    /** 推送服务商 */
    @NotNull
    private String provider;

    /** 在推送服务商平台创建推送应用时得到的 AppKey */
    @NotNull
    private String appKey;

    /** 客户端设备推送ID，如极光分配给客户端的Registration ID */
    private String pushId;

    /** 客户端设备操作系统，支持ios和android两种 */
    @NotNull
    private String os;

    /** 客户身份列表，用于查找客户，如果传入多个身份会根据身份优先级查找到一个客户。如果找到客户，则将App推送身份注册给该客户；如果找不到客户，则将App推送身份注册为匿名身份 */
    private List<PushIdentity> customerIdentities;


}
