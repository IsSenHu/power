package com.cdsen.power.server.user.model.cons;

import com.cdsen.power.core.Error;
import lombok.Getter;

/**
 * @author HuSen
 * create on 2019/12/9 15:55
 */
@Getter
public enum UserError implements Error {
    //
    USERNAME_OR_PASSWORD_ERROR(99901, "用户名或密码错误"),
    SYNC_USER_INFO_FAIL(99902, "同步用户登录信息失败"),
    NOT_FOUND(99903, "该用户不存在"),
    PASSWORD_ERROR(99904, "密码错误"),
    USERNAME_UNABLE(99905, "用户名不可用"),
    CODE_VERIFY_FAIL(99906, "校验失败");

    private Integer code;
    private String error;

    UserError(Integer code, String error) {
        this.code = code;
        this.error = error;
    }
}
