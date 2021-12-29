package com.convertlab.cache.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Redis 分布式锁 服务实现类
 *
 * @author LIUJUN
 * @date 2021-01-19 17:33
 */
@Component
public class RedisLockUtil {

    private RedisLockUtil() {

    }

    /** 日志 */
    private static final Logger LOG = LoggerFactory.getLogger(RedisLockUtil.class);

    /** 当前对象指针 */
    private static RedisLockUtil self;

    /** 默认有效时长 1分钟 */
    private static final long DEFAULT_EXPIRE_UNUSED = 60 * 1000L;

    /** Redis 分布式锁 */
    private RedisLockRegistry redisLockRegistry;

    @Autowired
    public RedisLockUtil(final RedisLockRegistry redisLockRegistry) {
        this.redisLockRegistry = redisLockRegistry;
    }

    @PostConstruct
    public void init() {
        self = this;
        self.redisLockRegistry = this.redisLockRegistry;
    }

    /**
     * 尝试加锁，且加有效时长
     *
     * @param lockKey redis lock的key
     * @param seconds 有效时长 毫秒
     * @return true:成功，false:失败
     */
    private static boolean tryLock(String lockKey, long seconds) {
        Lock lock = obtainLock(lockKey);
        try {
            return lock.tryLock(seconds, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOG.error("分布式锁 [{}] 尝试加锁异常", lockKey, e);
            return false;
        }
    }


    /**
     * 获取当前lock
     *
     * @param lockKey redis lock的key
     * @return Lock
     */
    private static Lock obtainLock(String lockKey) {
        return self.redisLockRegistry.obtain(lockKey);
    }

    /**
     * 尝试加锁- 阻塞等待
     *
     * @param lockKey    redis的key，参考BaseRedisKeyEnum、或其他服务的RedisKeyEnum
     * @param doSupplier 业务处理函数
     * @param <T>        业务处理函数的返回参数
     */
    public static <T> T lock(String lockKey, Supplier<T> doSupplier) {
        // 1. 获取锁
        Lock lock = obtainLock(lockKey);
        try {
            // 2. 阻塞加锁
            lock.lock();
            // 3. 执行业务代码
            return doSupplier.get();
        } finally {
            // 4. 释放锁
            lock.unlock();
            try {
                // 5. 过期释放锁
                self.redisLockRegistry.expireUnusedOlderThan(DEFAULT_EXPIRE_UNUSED);
            } catch (Exception e) {
                LOG.error("分布式锁 [{}] 释放异常", lockKey, e);
            }
        }
    }

    /**
     * 尝试加锁-有失效时间之内阻塞等待
     *
     * @param lockKey    RedisKey的实现类枚举，参考BaseRedisKeyEnum、或其他服务的RedisKeyEnum
     * @param expire     有效时长 毫秒
     * @param doSupplier 业务处理函数,有法返回值
     * @param <T>        业务处理函数的返回参数
     */
    public static <T> T tryLock(String lockKey, long expire, Supplier<T> doSupplier) {
        Assert.notNull(lockKey, "Redis lock key不能为空");
        Assert.state(expire > 0, "Redis lock key expire time 必须大于0");
        // 1. 尝试加锁
        boolean isLock = tryLock(lockKey, expire);
        // 2. 加锁成功
        if (isLock) {
            try {
                // 3. 执行业务代码
                return doSupplier.get();
            } finally {
                try {
                    // 4. 释放锁
                    Lock lock = obtainLock(lockKey);
                    lock.unlock();
                    // 5. 过期释放锁
                    self.redisLockRegistry.expireUnusedOlderThan(DEFAULT_EXPIRE_UNUSED);
                } catch (Exception e) {
                    LOG.error("分布式锁 [{}] 释放异常", lockKey, e);
                }
            }
        }
        return null;
    }

    /**
     * 尝试加锁-有失效时间之内阻塞等待
     *
     * @param lockKey    RedisKey的实现类枚举，参考BaseRedisKeyEnum、或其他服务的RedisKeyEnum
     * @param expire     有效时长 毫秒
     * @param doConsumer 业务处理函数,无法返回值
     * @param <T>        业务处理函数的返回参数
     */
    public static <T> void tryLock(String lockKey, long expire, Consumer<T> doConsumer) {
        Assert.notNull(lockKey, "Redis lock key不能为空");
        Assert.state(expire > 0, "Redis lock key expire time 必须大于0");
        // 1. 尝试加锁
        boolean isLock = tryLock(lockKey, expire);
        // 2. 加锁成功
        if (isLock) {
            try {
                // 3. 执行业务代码
                doConsumer.accept(null);
            } finally {
                try {
                    // 4. 释放锁
                    Lock lock = obtainLock(lockKey);
                    lock.unlock();
                    // 5. 过期释放锁
                    self.redisLockRegistry.expireUnusedOlderThan(DEFAULT_EXPIRE_UNUSED);
                } catch (Exception e) {
                    LOG.error("分布式锁 [{}] 释放异常", lockKey, e);
                }
            }
        }
    }
}