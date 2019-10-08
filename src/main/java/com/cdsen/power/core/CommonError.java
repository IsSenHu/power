package com.cdsen.power.core;

/**
 * @author HuSen
 * create on 2019/9/10 17:25
 */
public enum CommonError implements Error {
    NOT_LOGIN(10001, "未登录不允许进行该操作"),
    REDIS_ERROR(10002, "服务器内部异常"),
    INNER_ERROR(10003, "服务器内部异常");

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
