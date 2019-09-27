package com.cdsen.power.server.config.dao.po;

import com.cdsen.power.server.config.model.cons.ConfigType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author HuSen
 * create on 2019/9/26 16:58
 */
@Getter
@Setter
@Entity
@Table(name = "tb_config")
public class ConfigPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ConfigType type;
}
