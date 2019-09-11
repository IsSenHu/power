package com.cdsen.power.core.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过注解和不通过注解
 *
 * @author HuSen
 * create on 2019/9/11 17:47
 */
@Configuration
public class LocalCacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheNames(Lists.newArrayList("test"));
        return cacheManager;
    }
}
