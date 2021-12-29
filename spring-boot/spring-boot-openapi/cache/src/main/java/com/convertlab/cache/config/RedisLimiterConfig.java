package com.convertlab.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * redis分布式漏桶限流配置
 *
 * @author LIUJUN
 * @date 2021-02-16 22:37
 */
@Configuration
public class RedisLimiterConfig {

    @Bean(name = "limitRedisScript")
    public RedisScript<Boolean> limitRedisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean("rateRedisScript")
    public RedisScript<Boolean> rateRedisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("lua/rate.lua"));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}