package com.cdsen.power.server.user.model.vo;

import lombok.Data;

/**
 * @author HuSen
 * create on 2019/9/5 16:27
 */
@Data
public class UserVO {

    private Long id;

    private String username;

    private String password;

    private Boolean isAccountNonLocked;

    private Boolean isAccountNonExpired;

    private Boolean isEnabled;

    private Boolean isCredentialsNonExpired;

    private String introduction;

    private String avatar;

    private String email;

    private String createTime;
}
