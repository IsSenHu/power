package com.cdsen.power.core.security.model;

import lombok.Data;

/**
 * @author HuSen
 * create on 2019/8/29 9:57
 */
@Data
public class Token {
    public Token(String token, UserInfo info) {
        this.token = token;
        this.info = info;
    }

    private String token;
    private UserInfo info;
}
