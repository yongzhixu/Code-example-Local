package com.convertlab.common.beta.enums;

/**
 * 模块枚举
 *
 * @author chenyong
 * @date 2021-01-23 10:33:12
 */
public enum ModuleNameEnum {
    /** 1-客户 */
    CUSTOMER_CRV(1, "客户新增-万家", "根据客户身份&多身份新增", "syncCrvMemberHandler"),
    CUSTOMER_OLE(1, "客户新增-Ole", "根据客户身份&多身份新增", "syncOleMemberHandler"),
    CUSTOMER_UPDATE_CRV(1, "客户更新-万家", "批量更新&多身份更新", "syncCrvMemberUpdateHandler"),
    CUSTOMER_UPDATE_OLE(1, "客户更新-Ole", "批量更新&多身份更新客户", "syncOleMemberUpdateHandler"),
    ORDER(2, "订单", "Spark ETL", "syncOrderHandler"),
    GOODS_CRV(3, "商品-万家", "/objects/bulkAdd", "syncOleGoodsHandler"),
    GOODS_OLE(3, "商品-Ole", "/objects/bulkAdd", "syncOleGoodsHandler"),
    COUPON(4, "优惠券", "/objects/bulkAdd", "syncCouponHandler"),
    COUPON_CLAIM_CRV(5, "领券-万家", "/objects/bulkAdd", "syncCrvCouponClaimHandler"),
    COUPON_CLAIM_OLE(5, "领券-Ole", "/objects/bulkAdd", "syncOleCouponClaimHandler"),
    STORE(6, "门店", "/objects/bulkAdd", "syncStoreHandler"),
    EVENT(7, "事件采集", "/customerEvents", "syncEventHandler");

    /** 键 */
    private Integer key;

    /** URL */
    private String apiUrl;

    /** jobHandler */
    private String jobHandler;

    /** 描述 */
    private String desc;

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getJobHandler() {
        return jobHandler;
    }

    ModuleNameEnum(Integer key, String desc, String apiUrl, String jobHandler) {
        this.key = key;
        this.desc = desc;
        this.apiUrl = apiUrl;
        this.jobHandler = jobHandler;
    }

}


