package com.cdsen.power.server.health.model.cons;

import lombok.Getter;

/**
 * 睡前状态
 *
 * @author HuSen
 * create on 2019/9/25 10:08
 */
@Getter
public enum BedtimeState {
    ;

    private String value;

    BedtimeState(String value) {
        this.value = value;
    }
}
