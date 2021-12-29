package com.convertlab.common.beta.enums;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * RabbitMQ 消息的状态 枚举类
 *
 * @author LIUJUN
 * @date 2021-04-18 21:33
 */
public enum KafkaMsgStatusEnum {

    /** 0-投递中 */
    SENDING(0, "投递中"),

    /** 1-投递成功 */
    SEND_SUCCESS(1, "投递成功"),

    /** 2-投递失败 */
    SEND_FAIL(2, "投递失败"),

    /** 1-消费成功 */
    CONSUME_SUCCESS(1, "消费成功"),

    /** 0-消费失败 */
    CONSUME_FAIL(0, "消费失败"),
    ;

    /** 编码 */
    private Integer code;

    /** 描述 */
    private String desc;

    KafkaMsgStatusEnum(Integer code, String desc) {
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
        Optional<KafkaMsgStatusEnum> first = Stream.of(values())
                .filter(it -> it.getDesc().equals(desc))
                .findFirst();
        return first.map(KafkaMsgStatusEnum::getCode).orElse(null);
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
        Optional<KafkaMsgStatusEnum> first = Stream.of(values())
                .filter(it -> it.getCode().equals(code))
                .findFirst();
        return first.map(KafkaMsgStatusEnum::getDesc).orElse(null);
    }
}
