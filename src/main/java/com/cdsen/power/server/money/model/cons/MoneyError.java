package com.cdsen.power.server.money.model.cons;

import com.cdsen.power.core.Error;

/**
 * @author HuSen
 * create on 2019/9/10 17:23
 */
public enum MoneyError implements Error {
    NOT_FOUND(20001, "没有找到该条数据");

    private Integer code;
    private String error;

    MoneyError(Integer code, String error) {
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
