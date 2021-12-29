package com.convertlab.common.beta.model.response.customer;

import com.convertlab.common.beta.model.response.DmHubErrorResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * DmHUb 客户事件接口返回参数-通用字段
 * 自定义属性、自定义事件等非固定字段不要写在这里
 * 只适合单条新增，不适合批量
 *
 * @author LIUJUN
 * @date 2021-02-21 14:03:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseCustomerEventResp extends DmHubErrorResp {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 否	客户事件ID，只读字段 */
    protected Long id;

    /** 是	客户ID或客户身份至少一个必填 1.87+版本客户ID为String类型 */
    private String customerId;

    /** 是	客户ID或客户身份至少一个必填 */
    private String identityType;

    /** 是	客户ID或客户身份至少一个必填 */
    private String identityValue;

    /** 是	事件ID，最长32个英文字符。可在设置中心>客户事件页面设置 */
    private String event;

    /** 是	行为所发生的时间，格式为:"2020-06-01T12:12:12Z"为UTC时间，可支持到毫秒级 */
    private String date;

    /**
     * 否	如果传入externalId，系统会根据externalId处理重复事件；
     * 如果未指定externalId的情况下分两种情况：
     * 第一种情况:创建客户事件请求体中增加externalIdGenerator字段(例:{"externalIdGenerator": "md5"}), 用事件内容的md5值作为externalId，不做去重处理;
     * 第二种情况: externalIdGenerator为空或为date或不填，externalId取事件date字段的unix毫秒数，做去重处理
     */
    private String externalId;

    /** 否	推广人ID */
    private String referrer;

    /** 否	事件发生后为客户添加的内容标签，多个标签可以用逗号隔开 */
    private String tag;

    /** 否	营销活动code，DM Hub创建的营销活动代码 */
    private String campaign;

    /** 否	来源，预先设定的来源，比如订单是从淘宝来的，则来源可以设置为淘宝 */
    private String source;

    /** 否	地理位置，经度 */
    private BigDecimal longitude;

    /** 否	地理位置，纬度 */
    private BigDecimal latitude;

    /** 否	地理位置，国家 */
    private String country;

    /** 否	地理位置，省份 */
    private String province;

    /** 否	地理位置，城市 */
    private String city;

    /** 否	地理位置，区县 */
    private String county;

    /** 否	地理位置，详细地址 */
    private String location;
}
