package com.convertlab.cache.enums;

import com.convertlab.cache.service.RedisKey;
import com.convertlab.common.beta.enums.BuEnum;

/**
 * Redis 枚举类
 * 建议key的命名 "clab:cd:项目名:"开头
 *
 * @author LIUJUN
 * @date 2021-01-19 18:57
 */
public enum BaseRedisKeyEnum implements RedisKey {

    /** Convertlab应用访问令牌 */
    ACCESS_TOKEN_CRV("clab:cd:vanguard:accessToken:crv", "万家租户Convertlab应用访问令牌"),

    /** Convertlab应用访问令牌 */
    ACCESS_TOKEN_OLE("clab:cd:vanguard:accessToken:ole", "ole租户Convertlab应用访问令牌"),
    ;

    /** 键 */
    private String key;

    /** 描述 */
    private String desc;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    BaseRedisKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    /**
     * 根据品牌来获取token的redis key
     *
     * @param buEnum 品牌的枚举，注意不传"万家,Ole"枚举
     * @return redis key
     */
    public static String getToken(BuEnum buEnum) {
        if (buEnum == BuEnum.CRV) {
            return ACCESS_TOKEN_CRV.getKey();
        } else if (buEnum == BuEnum.OLE) {
            return ACCESS_TOKEN_OLE.getKey();
        }
        return null;
    }
}
