package com.cdsen.power.server.user.model.vo;

import com.cdsen.power.server.user.model.cons.PermissionType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/9/17 11:28
 */
@Getter
@Setter
public class PermissionVO {

    private Integer id;

    private String mark;

    private String description;

    private PermissionType type;

    private String classification;
}
