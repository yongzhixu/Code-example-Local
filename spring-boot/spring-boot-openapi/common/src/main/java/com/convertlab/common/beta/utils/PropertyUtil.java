package com.convertlab.common.beta.utils;

import com.convertlab.common.beta.constants.BaseConstant;
import com.convertlab.common.beta.enums.YesOrNoEnum;
import com.convertlab.common.beta.exception.BizException;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * 系统属性工具类
 *
 * @author LIUJUN
 * @date 2021-03-29 00:14:04
 */
public class PropertyUtil {

    private PropertyUtil() {

    }

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * 获取配置文件的属性值
     *
     * @param key 属性名
     * @return 属性对应的值
     */
    public static String getValue(String key) {
        String value = getPropertyValueJson("application.properties", key);
        if (value == null) {
            value = getValue("application.yml", key);
        }
        return value;
    }

    /**
     * 获取配置文件的属性值-支持 properties
     *
     * @param fileName 配置文件名
     * @param key      属性名
     * @return 属性对应的值
     */
    public static String getPropertyValueJson(String fileName, String key) {
        String value = null;
        try {
            //value = PropertiesLoaderUtils.loadAllProperties(fileName).getProperty(key);
            EncodedResource resource = new EncodedResource(new ClassPathResource(fileName), StandardCharsets.UTF_8);
            value = PropertiesLoaderUtils.loadProperties(resource).getProperty(key);
        } catch (IOException e) {
            return null;
        }
        return value;
    }

    /**
     * 获取配置文件的属性值-支持yaml、yml、properties
     *
     * @param fileName 配置文件名
     * @param key      属性名
     * @return 属性对应的值
     */
    public static String getValue(String fileName, String key) {
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        try {
            Resource resource = new ClassPathResource(fileName);
            yamlFactory.setResources(resource);
            Properties properties = yamlFactory.getObject();
            String property = properties.getProperty(key);
            if (property != null) {
                int start = property.indexOf("${");
                int end = property.indexOf("}");
                if (start > -1 && end > -1) {
                    // 环境变量的获取
                    String substring = property.substring(start + 2, end);
                    String[] split = substring.split(":");
                    String envValue = System.getenv(split[0]);
                    if (envValue == null && split.length > 1) {
                        return split[1];
                    }
                }
            }
            return property;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 配置值支持String字符串、Json字符串
     *
     * @param configValue 配置
     * @return HashMap，key=-999代表所有租户共用，即不区分租户
     */
    private static Map<String, String> toMap(String configValue) {
        try {
            Map<String, String> map = Maps.newHashMap();
            if (JsonUtil.isJsonObjectType(configValue)) {
                Gson gson = new Gson();
                map = gson.fromJson(configValue, new TypeToken<Map<String, String>>() {
                }.getType());
            } else {
                map.put(BaseConstant.CONFIG_ALL_TENANT, configValue);
            }
            return map;
        } catch (Exception e) {
            throw new BizException("系统配置的值【" + configValue + "】转换Map异常：" + e.getMessage());
        }
    }

    /**
     * 根据租户获取系统配置属性值（字符串）
     *
     * @param configValue 属性值，json字符串
     * @param key         目标key
     * @return 短信url
     */
    public static String getStringValue(String configValue, Object key) {
        // String值存到Map
        Map<String, String> stringToMap = toMap(configValue);
        String strKey = String.valueOf(key);
        // 如果配置是所有租户共用（租户Id为-999），取-999对应的value
        if (!stringToMap.containsKey(strKey) && stringToMap.containsKey(BaseConstant.CONFIG_ALL_TENANT)) {
            return stringToMap.get(BaseConstant.CONFIG_ALL_TENANT);
        }
        // 优先取自己的
        return stringToMap.get(strKey);
    }

    /**
     * 根据租户获取短信企业ID（长整型）
     *
     * @param configValue 属性值，json字符串
     * @param key         目标key
     * @return 短信企业ID
     */
    public static Long getLongValue(String configValue, Object key) {
        // String值存到Map
        Map<String, String> stringToMap = toMap(configValue);
        // 如果配置是所有租户共用（租户Id为-999），取-999对应的value
        String strKey = String.valueOf(key);
        if (!stringToMap.containsKey(strKey) && stringToMap.containsKey(BaseConstant.CONFIG_ALL_TENANT)) {
            String tid = stringToMap.get(BaseConstant.CONFIG_ALL_TENANT);
            return tid == null ? null : Long.valueOf(tid);
        }
        return stringToMap.get(strKey) == null ? null : Long.valueOf(stringToMap.get(strKey));
    }

    /**
     * 根据租户获取短信企业ID（布尔型）
     *
     * @param configValue 属性值，json字符串
     * @param key         目标key
     * @return 短信企业ID
     */
    public static Boolean getBooleanValue(String configValue, Object key) {
        // String值存到Map
        Map<String, String> stringToMap = toMap(configValue);
        // 如果配置是所有租户共用（租户Id为-999），取-999对应的value
        String strKey = String.valueOf(key);
        String flag;
        if (!stringToMap.containsKey(strKey) && stringToMap.containsKey(BaseConstant.CONFIG_ALL_TENANT)) {
            flag = stringToMap.getOrDefault(BaseConstant.CONFIG_ALL_TENANT, YesOrNoEnum.N.getCodeStr());
        } else {
            // 默认否
            flag = stringToMap.getOrDefault(strKey, YesOrNoEnum.N.getCodeStr());
        }
        return BooleanUtils.toBoolean(flag);
    }
}
