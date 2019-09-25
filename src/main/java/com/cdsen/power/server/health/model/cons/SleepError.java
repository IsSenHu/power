package com.cdsen.power.server.health.model.cons;

import com.cdsen.power.core.Error;

/**
 * @author HuSen
 * create on 2019/9/25 11:28
 */
public enum SleepError implements Error {
    NOT_FOUNT(40001, "没有找到该条记录");

    private Integer code;
    private String error;

    SleepError(Integer code, String error) {
        this.code = code;
        this.error = error;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getError() {
        return error;
    }
}
