package com.convertlab.common.beta.enums;

/**
 * DmHub产品的kafka消息topic枚举类
 *
 * @author LIUJUN
 * @date 2021-07-03 16:05:53
 */
public enum DmHubTopicEnum {

    /** customer-stat-update: 客户指标分析更新 */
    CUSTOMER_STAT_UPD("customer-stat-update", "客户指标分析更新"),

    ;

    /** 枚举值 */
    private String topic;

    /** 枚举描述 */
    private String desc;

    public String getTopic() {
        return topic;
    }

    public String getDesc() {
        return desc;
    }

    DmHubTopicEnum(String topic, String desc) {
        this.topic = topic;
        this.desc = desc;
    }

}
