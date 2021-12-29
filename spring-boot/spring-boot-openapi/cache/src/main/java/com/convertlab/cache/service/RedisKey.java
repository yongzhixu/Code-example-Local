package com.convertlab.cache.service;

import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;

/**
 * Redis Key接口
 *
 * @author LIUJUN
 * @date 2021-02-18 14:09:33
 */
public interface RedisKey {

    /** Redis锁的key的后缀 */
    String LOCK_SUFFIX = "?";

    /**
     * 获取Redis key
     *
     * @return String
     */
    String getKey();

    /**
     * 获取Redis 分布式锁key
     *
     * @param uniq 唯一业务唯一值
     * @return String
     */
    /**
     * 获取redid key
     *
     * @param uniq 唯一业务唯一值
     * @return "?"被替换的字符串
     */
    default String getKey(Object uniq) {
        if (!(uniq instanceof String) && !(uniq instanceof Long) && !(uniq instanceof Integer)
                && !(uniq instanceof Double) && !(uniq instanceof BigDecimal)) {
            throw new RuntimeException("获取redid key参数unq类型不正确:" + uniq.getClass());
        }
        String lockKey = getKey();
        if (ObjectUtils.isEmpty(lockKey)) {
            return "";
        }
        if (ObjectUtils.isEmpty(uniq)) {
            uniq = "";
        }
        int lastIndexOf = lockKey.lastIndexOf(LOCK_SUFFIX);
        if (lastIndexOf == -1 || lockKey.length() - 1 < lastIndexOf) {
            return lockKey;
        }
        return lockKey.substring(0, lastIndexOf) + uniq;
    }

    /**
     * 获取Redis 有效时长（毫秒）
     *
     * @return long
     */
    default long getExpire() {
        return 0;
    }

    /**
     * 获取Redis 的描述
     *
     * @return String
     */
    String getDesc();

}
