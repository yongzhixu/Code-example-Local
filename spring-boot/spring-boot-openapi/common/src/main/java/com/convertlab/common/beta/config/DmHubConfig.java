package com.convertlab.common.beta.config;

import com.convertlab.common.beta.enums.BuEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Convertlab通用配置
 *
 * @author LIUJUN
 * @date 2021-01-19 17:34
 */
@Configuration
@Data
public class DmHubConfig {

    /** 当前激活的环境 */
    @Value("${spring.profiles.active:dev}")
    private String env;

    /** 应用名称 */
    @Value("${spring.application.name}")
    private String applicationName;

    /** APP URL */
    @Value("${convertlab.apiUrl}")
    private String apiUrl;

    /** APP ID */
    @Value("${convertlab.crv.appId}")
    private String crvAppId;

    /** APP 密钥 */
    @Value("${convertlab.crv.appSecret}")
    private String crvAppSecret;

    /** 租户ID */
    @Value("${convertlab.crv.tenantId}")
    private Long crvTenantId;

    /** APP消息的 供应商 */
    @Value("${convertlab.crv.push.provider:}")
    private String crvPushProvider;

    /** APP消息的 AppKey */
    @Value("${convertlab.crv.push.appKey:}")
    private String crvPushAppKey;


    /** OLE APP ID */
    @Value("${convertlab.ole.appId}")
    private String oleAppId;

    /** OLE APP 密钥 */
    @Value("${convertlab.ole.appSecret}")
    private String oleAppSecret;

    /** Ole 租户ID */
    @Value("${convertlab.ole.tenantId}")
    private Long oleTenantId;

    /** Ole APP消息的 供应商 */
    @Value("${convertlab.ole.push.provider:}")
    private String olePushProvider;

    /** Ole APP消息的 AppKey */
    @Value("${convertlab.ole.push.appKey:}")
    private String olePushAppKey;

    /**
     * 根据品牌来获取APP ID
     *
     * @param buEnum 品牌的枚举，注意不传"万家,Ole"枚举
     * @return String
     */
    public String getAppId(BuEnum buEnum) {
        if (buEnum == BuEnum.CRV) {
            return crvAppId;
        } else if (buEnum == BuEnum.OLE) {
            return oleAppId;
        }
        return null;
    }

    /**
     * 根据品牌来获取APP Secret
     *
     * @param buEnum 品牌的枚举，注意不传"万家,Ole"枚举
     * @return String
     */
    public String getAppSecret(BuEnum buEnum) {
        if (buEnum == BuEnum.CRV) {
            return crvAppSecret;
        } else if (buEnum == BuEnum.OLE) {
            return oleAppSecret;
        }
        return null;
    }


    /**
     * 根据品牌来获取租户ID
     *
     * @param buEnum 品牌的枚举，注意不传"万家,Ole"枚举
     * @return String
     */
    public Long getTenantId(BuEnum buEnum) {
        if (buEnum == BuEnum.CRV) {
            return crvTenantId;
        } else if (buEnum == BuEnum.OLE) {
            return oleTenantId;
        }
        return null;
    }

    /**
     * 根据品牌来获取 APP消息的 供应商名称
     *
     * @param buEnum 品牌的枚举，注意不传"万家,Ole"枚举
     * @return String
     */
    public String getPushProvider(BuEnum buEnum) {
        if (buEnum == BuEnum.CRV) {
            return crvPushProvider;
        } else if (buEnum == BuEnum.OLE) {
            return olePushProvider;
        }
        return null;
    }

    /**
     * 根据品牌来获取 APP消息的 AppKey
     *
     * @param buEnum 品牌的枚举，比如注意不传"万家,Ole"枚举
     * @return String
     */
    public String getPushAppKey(BuEnum buEnum) {
        if (buEnum == BuEnum.CRV) {
            return crvPushAppKey;
        } else if (buEnum == BuEnum.OLE) {
            return olePushAppKey;
        }
        return null;
    }

    /**
     * 根据evn获取环境的kudu 库名
     * @return 库名
     */
    public String getKuduSchemeByEnv() {
        // 防止sql注入
        if (env != null && (env.contains("drop") || env.contains("delete")
                || env.contains("truncate") || env.contains("create"))) {
            return "";
        }
        if ("test".equals(env)) {
            return "cdp_test.";
        } else if ("sit".equals(env)) {
            return "cdp_sit.";
        } else if ("uat".equals(env)) {
            return "cdp_uat.";
        } else if ("prod".equals(env)) {
            return "cdp_prod.";
        }
        return "";
    }

    /**
     * 根据evn获取环境中文名
     * @return 环境中文名
     */
    public String getEnvName() {
        if ("test".equals(env)) {
            return "联调环境";
        } else if ("sit".equals(env)) {
            return "SIT环境";
        } else if ("uat".equals(env)) {
            return "UAT环境";
        } else if ("validate".equals(env)) {
            return "预发布环境";
        } else if ("prod".equals(env)) {
            return "生产环境";
        }
        return "";
    }
}
