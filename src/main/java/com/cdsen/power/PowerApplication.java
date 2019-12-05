package com.cdsen.power;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.cdsen.power.core.redis.annotation.EnableLoadRedisLuaModule;
import com.cdsen.power.core.redis.lua.LuaScript;
import com.cdsen.power.core.redis.lua.RedisLuaExecutor;
import com.google.common.collect.Lists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * rsql-jpa
 *
 * @author HuSen
 */
@EnableScheduling
@EnableDubbo
@EnableCaching
@SpringBootApplication
@EnableLoadRedisLuaModule
public class PowerApplication {

    private final RedisLuaExecutor luaExecutor;

    public PowerApplication(RedisLuaExecutor luaExecutor) {
        this.luaExecutor = luaExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(PowerApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000 * 60 * 30, initialDelay = 3000)
    public void test() {
        Long execute = luaExecutor.execute(LuaScript.READY_REGISTER_USER, Lists.newArrayList("sensen"), "sensen", "123456");
        System.out.println(execute);
    }

}
