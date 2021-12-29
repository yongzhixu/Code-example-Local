package com.convertlab.cache.enums;

import com.convertlab.cache.service.RedisKey;
import com.convertlab.common.beta.enums.BuEnum;
import com.convertlab.common.beta.utils.DateUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;

/**
 * Redis Key 枚举
 * 建议key的命名 "convertlab:cd:项目名:"开头
 *
 * @author wade
 * @date 2021-03-5 14:14:36
 */
public enum RedisKeyEnum implements RedisKey {

    /** 调度任务job 24个小时 （毫秒） */
    HANDLE_JOB("clab:cd:vanguard:job", 24 * 60 * 60 * 1000, "调度任务job"),

    /** 会员Redis分布式锁key（毫秒） */
    LOCK_MEMBER("clab:cd:vanguard:lock:Member:?", 60 * 1000, "会员Redis分布式锁key"),

    /** 会员处理完成 24个小时 （毫秒） */
    HANDLE_MEMBER_ITEM("clab:cd:vanguard:member:?", 24 * 60 * 60 * 1000, "会员处理完成"),

    /** 数据同步记录成功 24个小时 （毫秒） */
    HANDLE_RECORD_SUCCESS("clab:cd:vanguard:record:success?", 24 * 60 * 60 * 1000, "数据同步记录成功"),

    /** 保存RABBIT消息缓存时间 6秒（毫秒） */
    RABBIT_MESSAGE("clab:cd:vanguard:rabbit:message:?", 6 * 1000, "保存消息缓存时间"),

    /** 保存KAFKA消息缓存时间 6秒（毫秒） */
    KAFKA_MESSAGE("clab:cd:vanguard:kafka:message:?", 6 * 1000, "保存消息缓存时间"),
    ;

    /** Redis锁的key的后缀 */
    private static final String LOCK_SUFFIX = "?";

    /** key */
    private String key;

    /** 过期时长（毫秒） */
    private long expire;

    /** 描述 */
    private String desc;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public long getExpire() {
        return expire;
    }

    @Override
    public String getDesc() {
        return getDesc();
    }

    RedisKeyEnum(String key, long expire, String desc) {
        this.key = key;
        this.expire = expire;
        this.desc = desc;
    }

    /**
     * 获取redid lock key
     *
     * @param uniq 唯一业务唯一值
     * @return "?"被替换的字符串
     */
    @Override
    public String getKey(Object uniq) {
        if (!String.class.isInstance(uniq) && !Long.class.isInstance(uniq) && !Integer.class.isInstance(uniq)
                && !Double.class.isInstance(uniq) && !BigDecimal.class.isInstance(uniq)) {
            throw new RuntimeException("获取redid lock key参数unq类型不正确:" + uniq.getClass());
        }
        String lockKey = this.key;
        if (ObjectUtils.isEmpty(lockKey)) {
            return "";
        }
        if (ObjectUtils.isEmpty(uniq)) {
            uniq = "";
        }
        int lastIndexOf = lockKey.lastIndexOf(LOCK_SUFFIX);
        if (lockKey.length() - 1 < lastIndexOf) {
            return lockKey;
        }
        return lockKey.substring(0, lastIndexOf) + uniq;
    }

    /**
     * 根据租户 和唯一值获取redid lock key
     *
     * @param buEnum 唯一业务唯一值
     * @param uniq   唯一业务唯一值
     * @return "?"被替换的字符串
     */
    public String getDataSyncKey(BuEnum buEnum, Object uniq) {
        String yesterday = DateUtil.getOffsetDay(-1);
        String buEnumKey = buEnum == null ? null : buEnum.getKey();
        return getKey(buEnumKey + ":" + yesterday + ":" + uniq);
    }

    /**
     * 根据租户 和唯一值获取redid lock key
     *
     * @param buEnum 唯一业务唯一值
     * @return "?"被替换的字符串
     */
    public String getDataSyncCustomer(BuEnum buEnum) {
        String yesterday = DateUtil.getOffsetDay(-1);
        String buEnumKey = buEnum == null ? null : buEnum.getKey();
        return getKey(buEnumKey + ":" + yesterday + ":" + "Success");
    }
}
