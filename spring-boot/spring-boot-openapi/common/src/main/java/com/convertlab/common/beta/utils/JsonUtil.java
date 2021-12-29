package com.convertlab.common.beta.utils;

import com.convertlab.common.beta.exception.BizException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Jackson工具类
 *
 * @author LIUJUN
 * @date 2021-01-31 21:13
 */
@Slf4j
public class JsonUtil {

    private JsonUtil() {

    }

    private static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    private static ObjectMapper getInstanceNonNull() {
        return JacksonHolder.INSTANCE_NON_NULL;
    }

    private static class JacksonHolder {
        private static ObjectMapper INSTANCE = new JacksonObjectMapper();

        private static ObjectMapper INSTANCE_NON_NULL = new JacksonObjectMapper().setSerializationInclusion(
                JsonInclude.Include.NON_NULL);
    }


    public static class JacksonObjectMapper extends ObjectMapper {

        /** 时间格式 */
        private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        private static final long serialVersionUID = 4288193147502386170L;

        private static final Locale CHINA = Locale.CHINA;

        public JacksonObjectMapper() {
            super();
            //设置地点为中国
            // super.setLocale(CHINA);
            // 去掉默认的时间戳格式
            super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            // 设置为中国上海时区
            // super.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 序列化时，日期的统一格式
            super.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
            // 允许出现特殊字符和转义符
            super.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            // 该特性可以允许接受所有引号引起来的字符，使用‘反斜杠\’机制：如果不允许，只有JSON标准说明书中 列出来的字符可以被避开约束。
            super.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
            super.findAndRegisterModules();
            //失败处理
            super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //单引号处理
            super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            //反序列化时，属性不存在的兼容处理
            super.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //日期格式化
            //super.registerModule(new BladeJavaTimeModule());
            super.findAndRegisterModules();
        }


        @Override
        public ObjectMapper copy() {
            return super.copy();
        }
    }

    /**
     * 将对象序列化成json字符串
     *
     * @param value javaBean
     * @param <T>   T 泛型标记
     * @return jsonString json字符串
     */
    public static <T> String toJsonString(T value) {
        if (value == null) {
            return null;
        }
        try {
            return getInstance().writeValueAsString(value);
        } catch (Exception e) {
            log.error("将对象序列化成json字符串：", e);
        }
        return null;
    }

    /**
     * 将对象序列化成json字符串-null值的字段不序列化
     *
     * @param value javaBean
     * @param <T>   T 泛型标记
     * @return jsonString json字符串
     */
    public static <T> String toJsonStringNonNull(T value) {
        if (value == null) {
            return null;
        }
        try {
            return getInstanceNonNull().writeValueAsString(value);
        } catch (Exception e) {
            log.error("将对象序列化成json字符串-null值的字段不序列化：", e);
        }
        return null;
    }

    /**
     * 将对象序列化成 json byte 数组
     *
     * @param object javaBean
     * @return jsonString json字符串
     */
    public static byte[] toJsonAsBytes(Object object) {
        try {
            return getInstance().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("将json流反序列化成对象异常：", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 将json字符串反序列化成对象
     *
     * @param content   content
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(String content, Class<T> valueType) {
        if (content == null) {
            return null;
        }
        try {
            return getInstance().readValue(content, valueType);
        } catch (Exception e) {
            log.error("将json字符串反序列化成对象异常：", e);
        }
        return null;
    }

    /**
     * 将json字符串反序列化成对象
     *
     * @param content       content
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T parse(String content, TypeReference<?> typeReference) {
        if (content == null) {
            return null;
        }
        try {
            return (T) getInstance().readValue(content, typeReference);
        } catch (IOException e) {
            log.error("将json字符串反序列化成对象", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 将json byte数组反序列化成对象
     *
     * @param bytes     json bytes
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(byte[] bytes, Class<T> valueType) {
        if (bytes == null) {
            return null;
        }
        try {
            return getInstance().readValue(bytes, valueType);
        } catch (IOException e) {
            log.error("将json byte数组反序列化成对象异常：", e);
            throw new BizException(e.getMessage());
        }
    }


    /**
     * 将json byte数组 反序列化成对象
     *
     * @param bytes         bytes
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T parse(byte[] bytes, TypeReference<?> typeReference) {
        if (bytes == null) {
            return null;
        }
        try {
            return (T) getInstance().readValue(bytes, typeReference);
        } catch (IOException e) {
            log.error("将json byte数组 反序列化成对象异常：", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 将json流反序列化成对象
     *
     * @param in        InputStream
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return T
     */
    public static <T> T parse(InputStream in, Class<T> valueType) {
        if (in == null) {
            return null;
        }
        try {
            return getInstance().readValue(in, valueType);
        } catch (IOException e) {
            log.error("将json流反序列化成对象异常：", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 将json字符串输入流反序列化成对象
     *
     * @param in            InputStream
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T parse(InputStream in, TypeReference<?> typeReference) {
        if (in == null) {
            return null;
        }
        try {
            return (T) getInstance().readValue(in, typeReference);
        } catch (Exception e) {
            log.error("将json字符串输入流反序列化成对象异常：", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 将json数组字符串 反序列化成List对象
     *
     * @param content      content
     * @param valueTypeRef class
     * @param <T>          T 泛型标记
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseArray(String content, Class<T> valueTypeRef) {
        try {
            if (content == null) {
                return null;
            }
            List<Map<String, Object>> list = (List<Map<String, Object>>) getInstance().readValue(content,
                    new TypeReference<List<T>>() {
                    });
            List<T> result = new ArrayList<>();
            for (Map<String, Object> map : list) {
                result.add(mapToPojo(map, valueTypeRef));
            }
            return result;
        } catch (Exception e) {
            log.error("将json数组字符串 反序列化成List对象异常：", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * map转对象
     *
     * @param fromValue   被转化的map
     * @param toValueType 转换后的对象
     * @param <T>         转换后的对象类型
     * @return Bean
     */
    public static <T> T mapToPojo(Map<String, ?> fromValue, Class<T> toValueType) {
        return getInstance().convertValue(fromValue, toValueType);
    }

    /**
     * pojo转换Map
     *
     * @param <T>  转化后的类型
     * @param pojo 被转化的pojo
     * @return Map
     */
    public static <T> Map pojoToMap(T pojo) {
        ObjectMapper mapper = new ObjectMapper();
        // 对象转map
        Map map = null;
        try {
            map = mapper.readValue(mapper.writeValueAsString(pojo), Map.class);
        } catch (JsonProcessingException e) {
            log.error("pojo转换Map异常：", e);
            throw new BizException(e.getMessage());
        }
        return map;
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonString jsonString
     * @return jsonString json字符串
     */
    public static JsonNode readTree(String jsonString) {
        try {
            return getInstance().readTree(jsonString);
        } catch (IOException e) {
            log.error("将json字符串转成 JsonNode异常：", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 将json字符串输入流转成 JsonNode
     *
     * @param in InputStream
     * @return jsonString json字符串
     */
    public static JsonNode readTree(InputStream in) {
        try {
            return getInstance().readTree(in);
        } catch (IOException e) {
            log.error("将json字符串输入流转成 JsonNode：", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param content content
     * @return jsonString json字符串
     */
    public static JsonNode readTree(byte[] content) {
        try {
            return getInstance().readTree(content);
        } catch (IOException e) {
            log.error("将json字符串转成 JsonNode：", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 将json转成 JsonNode
     *
     * @param jsonParser JsonParser
     * @return jsonString json字符串
     */
    public static JsonNode readTree(JsonParser jsonParser) {
        try {
            return getInstance().readTree(jsonParser);
        } catch (IOException e) {
            log.error("将json转成 JsonNode异常：", e);
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 判断是否Json字符串类型
     *
     * @param str 字符串
     * @return true：是，false：否
     */
    public static boolean isJsonType(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }

        boolean result = false;
        str = str.trim();
        String jsonPrefix1 = "{";
        String suffixJson1 = "}";
        String jsonPrefix2 = "[";
        String suffixJson2 = "]";
        if (str.startsWith(jsonPrefix1) && str.endsWith(suffixJson1)) {
            result = true;
        } else if (str.startsWith(jsonPrefix2) && str.endsWith(suffixJson2)) {
            result = true;
        }
        return result;
    }

    /**
     * 判断是否Json字符串类型
     *
     * @param str 字符串
     * @return true：是，false：否
     */
    public static boolean isJsonObjectType(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }

        boolean result = false;
        str = str.trim();
        String jsonPrefix1 = "{";
        String suffixJson1 = "}";
        if (str.startsWith(jsonPrefix1) && str.endsWith(suffixJson1)) {
            result = true;
        }
        return result;
    }
}