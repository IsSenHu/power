package com.cdsen.power.core.security;

import com.cdsen.power.core.AppProperties;
import com.cdsen.power.core.security.model.Session;
import com.cdsen.power.core.security.session.SessionManage;
import com.google.common.cache.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author HuSen
 * create on 2019/8/27 18:08
 */
@Component
public class GuavaSessionManage implements SessionManage {

    /**
     * 未来最好存redis里 支持分布式 并且持久化 redis也能自动过期
     */
    private final Cache<String, Session> cache;

    public GuavaSessionManage(AppProperties appProperties) {
        cache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(appProperties.getMaxSessionInCache())
                .build();
    }

    @Override
    public Session getSession(String username) {
        return cache.getIfPresent(username);
    }

    @Override
    public void invalidate(String username) {
        cache.invalidate(username);
    }

    @Override
    public void save(String username, Session session) {
        cache.put(username, session);
    }
}
