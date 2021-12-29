package com.convertlab.common.beta.enums;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 是否枚举
 *
 * @author LIUJUN
 * @date 2021-02-07 16:33
 */
public enum YesOrNoEnum {

    /** 1-是 */
    Y(1, "是"),

    /** 0-失败 */
    N(0, "否");

    /** 编码 */
    private Integer code;

    /** 编码 */
    private String codeStr;

    /** 描述 */
    private String desc;

    YesOrNoEnum(Integer code, String desc) {
        this.code = code;
        this.codeStr = code == null ? null : code.toString();
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getCodeStr() {
        return codeStr;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据描述获取编码
     *
     * @param desc 描述
     * @return 编码
     */
    public static Integer getCodeByDesc(String desc) {
        if (StringUtils.isEmpty(desc)) {
            return null;
        }
        Optional<YesOrNoEnum> first = Stream.of(values())
                .filter(it -> it.getDesc().equals(desc))
                .findFirst();
        return first.map(YesOrNoEnum::getCode).orElse(null);
    }

    /**
     * 根据编码获取描述
     *
     * @param code 编码
     * @return 描述
     */
    public static String getDescByCode(Integer code) {
        if (ObjectUtils.isEmpty(code)) {
            return null;
        }
        Optional<YesOrNoEnum> first = Stream.of(values())
                .filter(it -> it.getCode().equals(code))
                .findFirst();
        return first.map(YesOrNoEnum::getDesc).orElse(null);
    }
}
