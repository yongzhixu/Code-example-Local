package com.convertlab.common.beta.enums;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 品牌枚举类
 *
 * @author LIUJUN
 * @date 2021-01-26 20:36
 */
public enum BuEnum {
    /** 万家 */
    CRV("万家", "万家品牌"),

    /** OLE */
    OLE("Ole", "Ole品牌"),

    /** 万家,OLE */
    CRV_OLE("万家,Ole", "万家和Ole品牌"),

    /** 华润 */
    HUA_RUN("华润", "华润品牌"),
    ;

    /** 键 */
    private String key;

    /** 描述 */
    private String desc;

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    BuEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    /**
     * 根据品牌名称获取枚举
     *
     * @param key 品牌名称
     * @return BuEnum
     */
    public static BuEnum getByKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Optional<BuEnum> first = Arrays.stream(BuEnum.values()).filter(it -> it.getKey().equals(key)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        // 防止 "万家,OLE" 变成了  "OLE,万家"
        boolean allMatch = Arrays.stream(BuEnum.CRV_OLE.getKey().split(","))
                .allMatch(key::contains);
        if (allMatch) {
            return CRV_OLE;
        }
        return null;
    }
}
