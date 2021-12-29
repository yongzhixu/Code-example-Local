package com.convertlab.common.beta.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * DMP dm层数据job任务
 *
 * @author wade
 */
public enum JobStatusEnum {

    /** ncms-会员 */
    CUSTOMER("ncms", "会员"),

    /** coupon-优惠券 */
    COUPON("coupon", "优惠券"),

    /** couponacc-领券 */
    COUPON_CLAIM("couponacc", "领券"),

    /** item-商品 */
    GOODS("item", "商品"),

    /** shop -门店 */
    STORE("shop", "门店"),

    /** order_head-订单头 */
    ORDER_HEAD("order_head", "订单头"),

    /** order_line-订单行 */
    ORDER_LINE("order_line", "订单行");

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;


    /**
     * 构造一个<code>SeqTypeEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private JobStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Returns the code.
     */
    public String code() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String message() {
        return message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return SeqTypeEnum
     */
    public static JobStatusEnum getByCode(String code) {
        for (JobStatusEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<SeqTypeEnum>
     */
    public List<JobStatusEnum> getAllEnum() {
        List<JobStatusEnum> list = new ArrayList<JobStatusEnum>();
        for (JobStatusEnum item : values()) {
            list.add(item);
        }
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public List<String> getAllEnumCode() {
        List<String> list = new ArrayList<String>();
        for (JobStatusEnum item : values()) {
            list.add(item.code());
        }
        return list;
    }
}
