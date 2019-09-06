package com.cdsen.power.core.security;

import com.cdsen.power.core.AppProperties;
import com.cdsen.power.core.security.model.Session;
import com.cdsen.power.core.security.session.SessionManage;
import com.cdsen.power.core.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author HuSen
 * create on 2019/9/6 14:45
 */
@Component("redisSessionManage")
public class RedisSessionManage implements SessionManage {

    private static final String SESSION_PREFIX = "session:";
    private final AppProperties appProperties;
    private final StringRedisTemplate redisTemplate;
    private final GuavaSessionManage guavaSessionManage;

    public RedisSessionManage(AppProperties appProperties, StringRedisTemplate redisTemplate, GuavaSessionManage guavaSessionManage) {
        this.appProperties = appProperties;
        this.redisTemplate = redisTemplate;
        this.guavaSessionManage = guavaSessionManage;
    }

    @Override
    public Session getSession(String username) {
        final String key = SESSION_PREFIX.concat(username);
        Session session = guavaSessionManage.getSession(key);
        if (session != null) {
            return session;
        }
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String sessionStr = value.get(key);
        session = JsonUtils.parseObj(sessionStr, Session.class);
        if (session == null) {
            return null;
        }
        // 再次缓存
        guavaSessionManage.save(key, session);
        return session;
    }

    @Override
    public void invalidate(String username) {
        final String key = SESSION_PREFIX.concat(username);
        redisTemplate.delete(key);
        guavaSessionManage.invalidate(key);
    }

    @Override
    public void save(String username, Session session) {
        final String key = SESSION_PREFIX.concat(username);
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String jsonString = JsonUtils.toJsonString(session);
        if (StringUtils.isNotBlank(jsonString)) {
            value.setIfAbsent(key, jsonString, Duration.ofMinutes(appProperties.getExpiration()));
        }
        guavaSessionManage.save(key, session);
    }
}
