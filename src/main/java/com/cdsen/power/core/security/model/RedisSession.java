package com.cdsen.power.core.security.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/16 15:07
 */
@Getter
@Setter
public class RedisSession {

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户说明和介绍
     */
    private String introduction;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 在 Vue Element admin 对应角色
     * 在 本系统中对应权限
     */
    private List<String> roles;
}
