package com.cdsen.power;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.cdsen.power.core.redis.annotation.EnableLoadRedisLuaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * rsql-jpa
 *
 * @author HuSen
 */
@EnableDubbo
@EnableCaching
@SpringBootApplication
@EnableLoadRedisLuaModule
public class PowerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerApplication.class, args);
    }

}
