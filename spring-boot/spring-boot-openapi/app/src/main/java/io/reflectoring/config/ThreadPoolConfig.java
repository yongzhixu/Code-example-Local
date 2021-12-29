package io.reflectoring.config;

import com.convertlab.common.beta.utils.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author LIUJUN
 * @date 2021-06-06 14:43:03
 */
@Configuration
public class ThreadPoolConfig {

    /** 核心线程池数据 CPU可用核心数 */
    private static final int CORE_NUM = Runtime.getRuntime().availableProcessors();

    /** 最大线程数量 */
    private static final int MAX_NUM = CORE_NUM * 2;

    /** 超时时间 秒 */
    private static final int OUT_TIME = 60;

    /** 缓冲队列数 */
    private static final int QUEUE_CAPACITY = 200;

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtil.class);

    @Bean("asyncThreadPoolTask")
    public ThreadPoolTaskExecutor asyncThreadPoolTask() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        int corePoolSize = Math.max(CORE_NUM, 5);
        taskExecutor.setCorePoolSize(corePoolSize);
        //设置最大线程数
        taskExecutor.setMaxPoolSize(taskExecutor.getCorePoolSize() * 2);
        //设置线程空闲等待时间
        taskExecutor.setKeepAliveSeconds(OUT_TIME);
        //设置任务等待队列的大小
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        // 设置线程池内线程名称的前缀-------阿里编码规约推荐--方便出错后进行调试
        taskExecutor.setThreadNamePrefix("asyncThreadPoolTask-");
        //设置任务的拒绝策略:丢弃任务，抛运行时异常
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //初始化
        taskExecutor.initialize();
        LOGGER.info("初始化asyncThreadPoolTask完成...,当前CPU{}核,核心线程池数：{}", CORE_NUM, corePoolSize);
        return taskExecutor;
    }

    @Bean("clExecutor")
    public Executor clExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置核心线程数 DmHub接口一般并发都是100个来做最低压测
        int corePoolSize = 10;
        taskExecutor.setCorePoolSize(corePoolSize);
        //设置最大线程数
        taskExecutor.setMaxPoolSize(100);
        //设置线程空闲等待时间
        taskExecutor.setKeepAliveSeconds(OUT_TIME);
        //设置任务等待队列的大小
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        // 设置线程池内线程名称的前缀-------阿里编码规约推荐--方便出错后进行调试
        taskExecutor.setThreadNamePrefix("clExecutor-");
        //设置任务的拒绝策略:当任务添加到线程池中被拒绝时，会在线程池当前正在运行的Thread线程池中处理被拒绝的任务
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化
        taskExecutor.initialize();
        LOGGER.info("初始化clExecutor完成...,当前CPU{}核,核心线程池数：{}", CORE_NUM, corePoolSize);
        return taskExecutor;
    }
}
