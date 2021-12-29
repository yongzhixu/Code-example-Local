package com.convertlab.common.beta.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DmHub 开放接口错误返回
 *
 * @author LIUJUN
 * @date 2021-02-07 16:18
 */
@Data
public class DmHubErrorResp implements Serializable {

    /** 序列号 */
    private static final long serialVersionUID = 1L;

    /** 错误信息 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Error error;

    /** 错误的字段信息 */
    @Data
    public static final class Error implements Serializable {

        /** 序列号 */
        private static final long serialVersionUID = 1L;

        /** 错误码，全局错误码见下表，API特定错误码见API文档章节 */
        private String code;

        /** 错误信息具体描述 */
        private String message;

        /** 可选字段，非批量API可以将具体的错误字段信息放在该字段返回，官方文档应该需要返回List<FieldError>,但这个标签导入有bug,1.87+版本会修复 */
        private List<Object> fieldErrors;

        /** 可选字段，批量请求API可以把发生错误的请求条目放在该字段返回 */
        private List<String> failedItems;

        /** 错误的字段信息 */
        @Data
        public static final class FieldError implements Serializable {

            /** 序列号 */
            private static final long serialVersionUID = 1L;

            /** 错误字段名称 */
            private String fieldName;

            /** 错误信息描述 */
            private String message;
        }
    }
}

