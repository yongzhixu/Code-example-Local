package com.convertlab.common.beta.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池配置
 *
 * @author liujun
 * @date 2021-05-31 13:48:08
 */
@Component
public class ThreadUtil {

    /** 核心线程池数据 CPU可用核心数 */
    private static final int CORE_NUM = Runtime.getRuntime().availableProcessors();

    /** 最大线程数量 */
    private static final int MAX_NUM = CORE_NUM * 2;

    /** 超时时间 秒 */
    private static final long OUT_TIME = 60L;

    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

    /** 执行器 */
    private static ExecutorService executor;

    /** 当前对象指针 */
    private static ThreadUtil self;

    /** RedisTemplate */
    @Resource(name = "asyncThreadPoolTask")
    private ThreadPoolTaskExecutor asyncThreadPoolTask;

    @PostConstruct
    public void init() {
        self = this;
        self.asyncThreadPoolTask = this.asyncThreadPoolTask;
    }


    private static class NameTreadFactory implements ThreadFactory {
        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "clab-cd-thread-" + mThreadNum.getAndIncrement());
            logger.info(t.getName() + " has been created");
            return t;
        }
    }

    private static class MyIgnorePolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 日志记录
            logger.error(r.toString() + ",执行拒绝策略：", e);
        }
    }

    /**
     * 获取线程池
     *
     * @return ExecutorService
     */
    public static ExecutorService getInstance() {
        if (executor == null) {
            executor = new ThreadPoolExecutor(CORE_NUM, MAX_NUM, OUT_TIME,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(MAX_NUM < 500 ? 500 : 300 + MAX_NUM),
                    new NameTreadFactory(),
                    new MyIgnorePolicy()
            );
        }

        return executor;
    }

    /**
     * 获取线程池任务
     *
     * @return ThreadPoolTaskExecutor
     */
    public static ThreadPoolTaskExecutor getTaskInstance() {
        return self.asyncThreadPoolTask;
    }
}
