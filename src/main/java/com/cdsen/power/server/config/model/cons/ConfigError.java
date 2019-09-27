package com.cdsen.power.server.config.model.cons;

import com.cdsen.power.core.Error;

/**
 * @author HuSen
 * create on 2019/9/27 10:32
 */
public enum ConfigError implements Error {
    EXISTED(60001, "配置已存在"),
    NOT_FOUND(60002, "没有该配置");

    ConfigError(Integer code, String error) {
        this.code = code;
        this.error = error;
    }

    private Integer code;
    private String error;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getError() {
        return error;
    }
}
