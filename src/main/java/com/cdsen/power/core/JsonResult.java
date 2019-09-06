package com.cdsen.power.core;

import lombok.Data;

/**
 * @author HuSen
 * create on 2019/8/29 9:58
 */
@Data
public class JsonResult<T> {
    public static final int SUCCESS = 0;

    private int code;
    private String error;
    private T data;
    private long timestamp;

    public static <T> JsonResult<T> of(T d) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(SUCCESS);
        jsonResult.setTimestamp(System.currentTimeMillis());
        jsonResult.setData(d);
        return jsonResult;
    }

    public static <T> JsonResult<T> of(int code, String error) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(code);
        jsonResult.setError(error);
        jsonResult.setTimestamp(System.currentTimeMillis());
        return jsonResult;
    }

    public static <T> JsonResult<T> success() {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(SUCCESS);
        jsonResult.setTimestamp(System.currentTimeMillis());
        return jsonResult;
    }
}
