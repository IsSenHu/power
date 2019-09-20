package com.cdsen.power.core.redis.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * @author HuSen
 * create on 2019/9/6 11:20
 */
@Component
public class DistributedLock {

    private final RedissonClient redissonClient;

    public DistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RLock getLock(String resource) {
        return redissonClient.getLock(resource);
    }
}
