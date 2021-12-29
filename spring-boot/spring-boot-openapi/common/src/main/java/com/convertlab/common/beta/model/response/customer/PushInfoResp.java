package com.convertlab.common.beta.model.response.customer;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * APP push 注册返回参数
 *
 * @author LIUJUN
 * @date 2021-02-05 22:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PushInfoResp extends DmHubErrorResp implements Serializable {

    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 客户ID，如果为0，则表示App推送身份是匿名身份（未与具体客户关联） */
    private String customerId;

    /** 客户ID字符串，如果为"0"，则表示App推送身份是匿名身份（未与具体客户关联） */
    private String customerIdStr;

    /** 推送服务商 */
    private String provider;

    /** 在推送服务商平台创建推送应用时得到的 */
    private String appKey;

    /** 客户端设备推送ID，如极光分配给客户端的Registration ID */
    private String pushId;

    /** 客户端设备操作系统，支持ios和android两种 */
    private String os;

    /** 数据创建时间格式化字符串，UTC时间，格式：yyyy-MM-dd'T'HH:mm:ss'Z' */
    private String dateCreated;

    /** 数据最后更新时间格式化字符串，UTC时间，格式：yyyy-MM-dd'T'HH:mm:ss'Z' */
    private String lastUpdated;

}
