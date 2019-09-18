package com.cdsen.power.server.user.model.ao;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/9 10:04
 */
@Data
public class RoleCreateAO {

    @NotBlank
    private String name;

    private String description;

    private List<MenuPermissionAO> menuPermission;

    private List<Integer> apiPermission;
}
