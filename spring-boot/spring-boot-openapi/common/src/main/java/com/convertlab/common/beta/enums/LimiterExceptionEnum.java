package com.convertlab.common.beta.enums;

import com.convertlab.common.beta.exception.ExceptionInfo;

/**
 * 限流异常枚举类
 *
 * @author LIUJUN
 * @date 2021-02-16 22:37
 */
public enum LimiterExceptionEnum implements ExceptionInfo {

    /** 499999-配置访问阈值必须大于0 */
    API_LIMIT_ZERO(499999, "配置访问阈值必须大于0"),

    /** 403-访问次数太快了,请稍后再访问! */
    FORBIDDEN_ACCESS(403, "访问次数太快了,请稍后再访问!"),

    ;

    /** 错误码 */
    private Integer code;

    /** 错误描述 */
    private String msg;

    LimiterExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return null;
    }


    @Override
    public String getMsg() {
        return this.msg;
    }
}
