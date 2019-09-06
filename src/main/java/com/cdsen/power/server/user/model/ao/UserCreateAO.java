package com.cdsen.power.server.user.model.ao;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author HuSen
 * create on 2019/9/5 11:28
 */
@Data
public class UserCreateAO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;
}
