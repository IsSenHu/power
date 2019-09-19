package com.cdsen.power.server.user.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/9 10:15
 */
@Data
public class RoleVO {

    private Integer id;
    private String name;
    private String description;
    private List<PermissionTreeVO> apiPermission;
    private List<String> menuPermission;
}
