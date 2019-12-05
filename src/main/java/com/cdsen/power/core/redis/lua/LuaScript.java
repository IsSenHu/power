package com.cdsen.power.core.redis.lua;


import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author HuSen
 * create on 2019/12/5 11:37
 */
@Getter
public enum LuaScript {
    READY_REGISTER_USER("readyRegisterUser");

    private String luaScriptName;
    private RedisScript<Long> redisScript;

    LuaScript(String luaScriptName) {
        this.luaScriptName = luaScriptName;
        ClassPathResource resource = new ClassPathResource("lua/".concat(luaScriptName).concat(".lua"));
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            this.redisScript = new DefaultRedisScript<>(new String(bytes, StandardCharsets.UTF_8), Long.TYPE);
        } catch (Exception e) {
            LuaScriptLogger.error("[{}] 脚本加载失败:", luaScriptName, e);
        }
    }
}
