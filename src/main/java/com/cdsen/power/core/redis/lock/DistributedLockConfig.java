package com.cdsen.power.core.redis.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuSen
 * create on 2019/9/6 11:08
 */
@Configuration
public class DistributedLockConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSentinelServers()
                .addSentinelAddress("redis://118.24.38.46:26379")
                .setMasterName("mymaster")
                .setPassword("521428Slyt")
                .setDatabase(3);
        return Redisson.create(config);
    }
}
