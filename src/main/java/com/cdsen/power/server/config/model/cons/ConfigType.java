package com.cdsen.power.server.config.model.cons;

import lombok.Getter;

/**
 * @author HuSen
 * create on 2019/9/26 16:58
 */
@Getter
public enum ConfigType {
    ARTICLE("文章类型"),
    BEDTIME_STATE("睡前状态"),
    DREAM_STATE("梦境状态");

    private String name;

    ConfigType(String name) {
        this.name = name;
    }
}
