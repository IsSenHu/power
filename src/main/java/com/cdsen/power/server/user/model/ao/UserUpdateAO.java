package com.cdsen.power.server.user.model.ao;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author HuSen
 * create on 2019/9/5 11:28
 */
@Data
public class UserUpdateAO {

    private Long id;

    @Email
    private String email;

    @NotNull
    private Boolean isEnabled;

    private String introduction;
}
