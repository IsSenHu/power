package com.cdsen.power.core.redis.lock;

import com.cdsen.power.core.AppProperties;
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

    private final AppProperties appProperties;

    public DistributedLockConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public RedissonClient redissonClient() {

        AppProperties.Redisson.Sentinel sentinel = appProperties.getRedisson().getSentinel();
        Config config = new Config();
        config.useSentinelServers()
                .addSentinelAddress(sentinel.getAddress())
                .setMasterName(sentinel.getMasterName())
                .setPassword(sentinel.getPassword())
                .setDatabase(sentinel.getDatabase());
        return Redisson.create(config);
    }
}
