package com.cdsen.power.server.config.model.cons;

import lombok.Getter;

/**
 * @author HuSen
 * create on 2019/9/26 16:58
 */
@Getter
public enum ConfigType {
    ;

    private String name;

    ConfigType(String name) {
        this.name = name;
    }
}