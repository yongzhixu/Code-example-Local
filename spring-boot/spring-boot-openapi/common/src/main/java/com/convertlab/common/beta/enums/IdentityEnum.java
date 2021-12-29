package com.convertlab.common.beta.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * DMHub 身份类型枚举
 *
 * @author liujun
 * @date 2021-01-20 16:02
 */
public enum IdentityEnum {

    /** c_crv_member_id: 万家会员ID */
    WAN_JIA_MEMBER("c_crv_member_id", "万家会员ID"),

    /** membershipId: 会员 */
    MEMBERSHIP_ID("membershipId", "会员"),

    /** mobile: 手机号码 */
    MOBILE("mobile", "手机号码"),

    /** wechat: 微信openid */
    WECHAT_OPENID("wechat", "微信openid"),

    /** wechat-unionid: 微信unionid */
    WECHAT_UNION("wechat-unionid", "微信unionid"),

    /** applet-wechat: 小程序 */
    APPLET_WECHAT("applet-wechat", "小程序"),

    /** taobao-account: 淘宝 */
    TAOBAO_ACCOUNT("taobao-account", "淘宝"),

    /** alipay-account: 支付宝 */
    ALIPAY_ACCOUNT("alipay-account", "支付宝"),

    /** alipay: 支付宝生活号 */
    ALIPAY("alipay", "支付宝生活号"),

    /** wechatcorp: 企业微信 */
    WECHATCORP("wechatcorp", "企业微信"),
    ;

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    IdentityEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return IdentityEnum
     */
    public static IdentityEnum getByCode(String code) {
        for (IdentityEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<IdentityEnum>
     */
    public List<IdentityEnum> getAllEnum() {
        List<IdentityEnum> list = new ArrayList<IdentityEnum>();
        for (IdentityEnum item : values()) {
            list.add(item);
        }
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public List<String> getAllEnumCode() {
        List<String> list = new ArrayList<String>();
        for (IdentityEnum item : values()) {
            list.add(item.getCode());
        }
        return list;
    }
}
