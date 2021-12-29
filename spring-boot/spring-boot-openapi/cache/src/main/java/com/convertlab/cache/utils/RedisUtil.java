package com.convertlab.cache.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author LIUJUN
 * @date 2021-01-19 19:57
 */
@Component
public class RedisUtil {

    private RedisUtil() {

    }

    /** 当前对象指针 */
    private static RedisUtil self;

    /** RedisTemplate */
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        self = this;
        self.redisTemplate = this.redisTemplate;
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间 毫秒
     * @return true:设置成功；false:设置失败
     */
    public static boolean expire(String key, final long timeout) {

        return expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true:成功；false:失败
     */
    public static boolean expire(String key, long timeout, TimeUnit unit) {

        Boolean ret = self.redisTemplate.expire(key, timeout, unit);
        return ret != null && ret;
    }

    /**
     * 删除单个key
     *
     * @param key 键
     * @return true:删除成功；false:删除失败
     */
    public static boolean del(String key) {

        Boolean ret = self.redisTemplate.delete(key);
        return ret != null && ret;
    }

    /**
     * 删除多个key
     *
     * @param keys 键集合
     * @return 成功删除的个数
     */
    public static long del(final Collection<String> keys) {

        Long ret = self.redisTemplate.delete(keys);
        return ret == null ? 0 : ret;
    }

    /**
     * 存入普通对象
     *
     * @param key   Redis键
     * @param value 值
     */
    public static void set(String key, Object value) {
        self.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存入普通对象，且设置有效期
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效期，单位秒
     */
    public static void set(String key, Object value, final long timeout) {
        self.redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取普通对象
     *
     * @param key 键
     * @return 对象
     */
    public static Object get(String key) {
        return self.redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取普通对象-字符串类型
     *
     * @param key 键
     * @return 对象
     */
    public static String getString(String key) {
        Object object = self.redisTemplate.opsForValue().get(key);
        return object == null ? null : object.toString();
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public static void hPut(String key, String hKey, Object value) {

        self.redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 往Hash中存入多个数据
     *
     * @param key    Redis键
     * @param values Hash键值对
     */
    public static void hPutAll(String key, final Map<String, Object> values) {

        self.redisTemplate.opsForHash().putAll(key, values);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key   Redis键
     * @param field Hash键
     * @return Hash中的对象
     */
    public static Object hGet(String key, String field) {

        return self.redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key    Redis键
     * @param fields Hash键集合
     * @return Hash对象集合
     */
    public static List<Object> hMultiGet(String key, final Collection<Object> fields) {

        return self.redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * 删除Hash中的field，支持批量
     *
     * @param key    Redis键
     * @param fields Hash键
     * @return Hash中的对象
     */
    public static Long hDel(String key, String... fields) {

        return self.redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 往Set中存入数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 存入的个数
     */
    public static long sSet(String key, final Object... values) {
        Long count = self.redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 删除Set中的数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 移除的个数
     */
    public static long sDel(String key, final Object... values) {
        Long count = self.redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入数据
     *
     * @param key   Redis键
     * @param value 数据
     * @return 存入的个数
     */
    public static long lPush(String key, Object value) {
        Long count = self.redisTemplate.opsForList().rightPush(key, value);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key    Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public static long lPushAll(String key, final Collection<Object> values) {
        Long count = self.redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key    Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public static long lPushAll(String key, final Object... values) {
        Long count = self.redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 从List中获取begin到end之间的元素
     *
     * @param key   Redis键
     * @param start 开始位置
     * @param end   结束位置（start=0，end=-1表示获取全部元素）
     * @return List对象
     */
    public static List<Object> lGet(String key, final int start, final int end) {
        return self.redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 自增，从0开始累加
     *
     * @param key      Redis键
     * @param liveTime 存活时间
     * @return Long >=1
     */
    public static long incr(String key, long liveTime) {
        Long increment = self.redisTemplate.opsForValue().increment(key, 1L);
        // 初始设置过期时间
        if (liveTime > 0) {
            expire(key, liveTime);
        }

        return increment;
    }

    /**
     * 增加(自增长), 负数则为自减，从0开始累加
     *
     * @param key Redis键
     * @return Long >=1
     */
    public static Long incrBy(String key, long increment) {
        return self.redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * Redis 数据类型转换
     *
     * @param obj   要转换的数据
     * @param clazz 要转换的数据类型
     * @param <T>   要转换的数据类型
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseObject(Object obj, Class<T> clazz) {
        if (obj == null) {
            return null;
        }
        if (clazz.isInstance(obj)) {
            return (T) obj;
        } else if (clazz == Long.class && obj instanceof Integer) {
            Integer integerObj = (Integer) obj;
            return (T) Long.valueOf(integerObj.longValue());
        } else if (clazz == String.class) {
            return (T) String.valueOf(obj);
        }
        return null;
    }
}