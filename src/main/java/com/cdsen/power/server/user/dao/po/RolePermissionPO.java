package com.cdsen.power.server.user.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author HuSen
 * create on 2019/9/9 15:10
 */
@Getter
@Setter
@Entity
@Table(name = "tb_role_permission")
public class RolePermissionPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer roleId;

    private Integer permissionId;
}
