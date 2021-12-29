package com.convertlab.common.beta.enums;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 客户端响应结果状态枚举
 *
 * @author LIUJUN
 * @date 2021-02-07 16:33
 */
public enum RespResultEnum {

    /** 1-成功 */
    SUCCESS(1, "成功"),

    /** 0-失败 */
    FAILURE(0, "失败");

    /** 编码 */
    private Integer code;

    /** 描述 */
    private String desc;

    RespResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
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
        Optional<RespResultEnum> first = Stream.of(values())
                .filter(it -> it.getDesc().equals(desc))
                .findFirst();
        return first.map(RespResultEnum::getCode).orElse(null);
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
        Optional<RespResultEnum> first = Stream.of(values())
                .filter(it -> it.getCode().equals(code))
                .findFirst();
        return first.map(RespResultEnum::getDesc).orElse(null);
    }
}
