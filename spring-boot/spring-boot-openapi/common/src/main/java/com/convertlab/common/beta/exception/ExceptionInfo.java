package com.convertlab.common.beta.exception;


/**
 * 通用异常接口
 *
 * @author LIUJUN
 * @date 2021-01-11 18:34
 */
public interface ExceptionInfo {

    /**
     * 错误码 支持返回String、 Integer类型，不要用POJO
     *
     * @return Object
     */
    Object getCode();

    /**
     * 错误描述
     *
     * @return Object
     */
    String getMsg();
}
