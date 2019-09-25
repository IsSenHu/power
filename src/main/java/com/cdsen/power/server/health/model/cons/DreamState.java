package com.cdsen.power.server.health.model.cons;

import lombok.Getter;

/**
 * 梦境状态
 *
 * @author HuSen
 * create on 2019/9/25 10:10
 */
@Getter
public enum DreamState {
    ;

    private String value;

    DreamState(String value) {
        this.value = value;
    }
}
