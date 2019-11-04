package com.cdsen.power.server.email.model.cons;

import com.cdsen.power.core.Error;

/**
 * @author HuSen
 * create on 2019/11/4 14:34
 */
public enum EmailError implements Error {
    //
    CONFIG_ERROR(90001, "配置错误"),
    CONFIG_NOT_FOUNT(90002, "无法找到配置");

    private Integer code;
    private String error;

    EmailError(Integer code, String error) {
        this.code = code;
        this.error = error;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getError() {
        return this.error;
    }
}
