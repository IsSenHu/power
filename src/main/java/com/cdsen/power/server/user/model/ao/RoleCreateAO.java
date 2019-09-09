package com.cdsen.power.server.user.model.ao;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author HuSen
 * create on 2019/9/9 10:04
 */
@Data
public class RoleCreateAO {

    @NotBlank
    private String name;

    private String description;
}
