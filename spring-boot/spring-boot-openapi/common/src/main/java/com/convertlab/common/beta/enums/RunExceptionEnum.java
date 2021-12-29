package com.convertlab.common.beta.enums;

import com.convertlab.common.beta.exception.ExceptionInfo;

/**
 * 运行异常枚举类
 *
 * @author LIUJUN
 * @date 2021-01-11 18:37
 */
public enum RunExceptionEnum implements ExceptionInfo {
    /** 500-数据校验异常 */
    DAO_VALIDATE(500, "数据校验异常:【%s】!"),

    ;

    /** 错误码 */
    private Integer code;

    /** 错误描述 */
    private String msg;

    RunExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return this.code;
    }


    @Override
    public String getMsg() {
        return this.msg;
    }
}
