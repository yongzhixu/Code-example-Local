package com.convertlab.common.beta.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * URL工具类
 *
 * @author LIUJUN
 * @date 2021-01-20 16:23
 */
public final class UrlUtil {

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(UrlUtil.class);

    private UrlUtil() {

    }

    /**
     * 将url参数转换成map
     *
     * @param param aa=11&bb=22&cc=33
     * @return Map<String, Object>
     */
    public static Map<String, Object> getMapByUrlParams(String param) {
        if (StringUtils.isBlank(param)) {
            return new HashMap<>();
        }
        Map<String, Object> map = new HashMap<>(16);
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 将map转换成url参数
     *
     * @param map 参数
     * @return String, 如aa=11&bb=22&cc=33
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    /**
     * 将map转换成url参数
     *
     * @param pojo 实体对象
     * @return String, 如aa=11&bb=22&cc=33
     */
    public static <T> String getUrlParamsByPojo(T pojo) {

        if (Objects.isNull(pojo)) {
            return "";
        }
        // pojo转map
        Map<String, Object> map = JsonUtil.pojoToMap(pojo);
        // map转url的参数
        return getUrlParamsByMap(map);
    }

    /**
     * 从request获取post请求body参数
     * 注意：request.getReader()、request.getInputStream()读取一次就不能在用了
     *
     * @param request 请求
     * @return String
     */
    public static String getPostData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException e) {
            LOG.error("解析请求体body错误：", e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                LOG.error("解析请求体body，关闭流错误：", e);
            }
        }
        return data.toString();
    }
}
