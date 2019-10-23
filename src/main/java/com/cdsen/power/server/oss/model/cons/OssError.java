package com.cdsen.power.server.oss.model.cons;

import com.cdsen.power.core.Error;

/**
 * @author HuSen
 * create on 2019/10/8 15:23
 */
public enum OssError implements Error {
    //
    SUCCESS(80000, "成功"),
    IS_NULL(80001, "上传的文件为空"),
    ERROR_TYPE(80002, "错误的格式"),
    BLANK_FILE_NAME(80003, "空的文件名");

    private int code;
    private String error;

    OssError(int code, String error) {
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
