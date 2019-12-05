package com.cdsen.power.core.redis.config;

import com.cdsen.power.core.redis.annotation.EnableLoadRedisLuaModule;
import com.cdsen.power.core.redis.lua.RedisLuaExecutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author HuSen
 * create on 2019/9/6 11:36
 */
@Configuration
public class RedisConfig {

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnBean(annotation = EnableLoadRedisLuaModule.class)
    public RedisLuaExecutor redisLuaExecutor(@Qualifier("redisTemplate") StringRedisTemplate template) {
        return new RedisLuaExecutor(template);
    }
}
