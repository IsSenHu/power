package com.cdsen.power.server.user.dao.po;

import com.cdsen.power.server.user.model.cons.PermissionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author HuSen
 * create on 2019/9/9 15:01
 */
@Getter
@Setter
@Entity
@Table(name = "tb_permission")
public class PermissionPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mark", unique = true, nullable = false)
    private String mark;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PermissionType type;
}
