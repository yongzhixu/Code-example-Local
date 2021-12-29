package com.convertlab.common.beta.enums;

import com.convertlab.common.beta.exception.ExceptionInfo;

/**
 * 通用异常枚举类
 *
 * @author LIUJUN
 * @date 2021-01-11 18:37
 */
public enum DmHubApiExceptionEnum implements ExceptionInfo {
    /** 200-成功 */
    SUCCESS(200, "成功!"),

    /** 400- 请求的数据格式不符 */
    NULL_POINT(400, "请求的数据格式不符!"),

    /** 500-服务器内部错误 */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),

    /** 400000-通用客户端错误 */
    COMMON_CUSTOMER_ERROR(400000, "通用客户端错误"),

    /** 400001-数据格式有误 */
    DATA_FORMAT_ERROR(400001, "数据格式有误"),

    /** 400002-访问的资源不存在 */
    ACCESS_NOT_EXISTS(400002, "访问的资源不存在"),

    /** 400003-存在相同主键的资源 */
    REPEAT_PK(400003, "存在相同主键的资源"),

    /** 400004-空指针 */
    NOT_UPD_DEL(400004, "无法更新、删除资源"),

    /** 400005-必填字段缺失 */
    EXISTS_NULL_FIELD(400005, "必填字段缺失"),

    /** 400006-请求体中字段的类型与系统定义不同 */
    FIELD_TYPE_ERROR(400006, "请求体中字段的类型与系统定义不同"),

    /** 400007-数据更新发生了乐观锁 */
    UPD_EXISTS_OPTIMISTIC_LOCK(400007, "数据更新发生了乐观锁"),

    /** 400008-接口未授权 */
    INTERFACE_NOT_PERMIT(400008, "接口未授权"),

    /** 400009-或接口不存在 */
    NO_ACCESS_OR_INTERFACE_NOT_EXISTS(400009, "无权访问该资源，应用未配置相应权限，或接口不存在"),

    /** 400010-接口授权失败 */
    INTERFACE_AUTH_FAILURE(400010, "接口授权失败"),

    /** 400012-URL参数缺失或不满足要求 */
    URL_PARAMS_LOSS(400012, "URL参数缺失或不满足要求"),

    /** 400013-API请求超过调用次数 */
    API_QPS_LIMIT(400013, "API请求超过调用次数"),

    ;

    /** 错误码 */
    private Integer code;

    /** 错误描述 */
    private String msg;

    DmHubApiExceptionEnum(Integer code, String msg) {
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
