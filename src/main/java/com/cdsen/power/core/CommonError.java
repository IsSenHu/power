package com.cdsen.power.core;

/**
 * @author HuSen
 * create on 2019/9/10 17:25
 */
public enum CommonError implements BaseError {
    NOT_LOGIN(10001, "未登录不允许进行该操作");

    private Integer code;
    private String error;

    CommonError(Integer code, String error) {
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
