package com.convertlab.common.beta.model.response;

import com.convertlab.common.beta.enums.DmHubApiExceptionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Controller返回结果
 *
 * @author LIUJUN
 * @date 2021-01-11 16:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseResult<T> extends DmHubErrorResp {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 返回状态码 支持String、Integer */
    private Object code;

    /** 返回提示 */
    private String msg;

    /** 请求结果 */
    private T data;

    public ResponseResult<T> ok() {
        this.code = "200";
        this.msg = "success";
        return this;
    }

    public ResponseResult<T> code(Object code) {
        this.code = code;
        return this;
    }

    public ResponseResult<T> data(T data) {
        this.data = data;
        return this;
    }

    /**
     * 返回错误的消息体
     *
     * @param code    编码
     * @param message 描述
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(Object code, String message) {
        ResponseResult<T> rb = new ResponseResult<>();
        rb.setCode(code);
        rb.setMsg(message);
        rb.setData(null);
        // 支持DmHub产品自己的要求返回格式，比如短信相关接口
        DmHubErrorResp.Error error = new DmHubErrorResp.Error();
        error.setCode(String.valueOf(code));
        error.setMessage(message);
        rb.setError(error);
        return rb;
    }

    /**
     * 返回错误的消息体
     *
     * @param exceptionEnum 异常
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error(DmHubApiExceptionEnum exceptionEnum) {
        ResponseResult<T> rb = new ResponseResult<>();
        rb.setCode(exceptionEnum.getCode());
        rb.setMsg(exceptionEnum.getMsg());
        rb.setData(null);
        // 支持DmHub产品自己的要求返回格式，比如短信相关接口
        DmHubErrorResp.Error error = new DmHubErrorResp.Error();
        error.setCode(String.valueOf(exceptionEnum.getCode()));
        error.setMessage(exceptionEnum.getMsg());
        rb.setError(error);
        return rb;
    }
}
