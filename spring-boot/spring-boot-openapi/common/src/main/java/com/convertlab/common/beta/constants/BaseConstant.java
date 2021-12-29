package com.convertlab.common.beta.constants;

/**
 * DmHub产品基础常量类
 *
 * @author LIUJUN
 * @date 2021-03-04 21:02:32
 */
public final class BaseConstant {

    /** DmHub检查机制 */
    public static final String DM_HUB_HEALTH = "/ping";

    /** DmHub插件授权 */
    public static final String DM_HUB_OAUTH2 = "/oauth2";

    /** DmHub检查授权回调 */
    public static final String DM_HUB_OAUTH2_CALLBACK = "/oauth2/callback";

    /** 数据来源：DMP数据同步 */
    public static final String SOURCE_DMP_DATA_SYNC = "DMP数据同步";

    /** DmHub版本1.87 */
    public static final Integer DM_HUB_VERSION_1_87 = 187;

    /** -999代表所有租户 */
    public static final String CONFIG_ALL_TENANT = "-999";

    /** DM Hub自定义属性/事件等前缀 */
    public static final String DM_HUB_CUSTOM_PREFIX = "c_";

    /** kafka/RabbitMQ等消息的业务主键id */
    public static final String MESSAGE_BIZ_ID = "message_biz_id";

    /** kafka/RabbitMQ等消息的业务主键主体（有时候不用原始的消息体，可能只存其中部分属性） */
    public static final String MESSAGE_BIZ_BODY = "message_biz_body";
}
