package com.cdsen.power.server.config.dao.po;

import com.cdsen.power.core.jpa.BasePO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author HuSen
 * create on 2019/9/17 16:55
 */
@Getter
@Setter
@Entity
@Table(name = "tb_config_property")
public class PropertyPO extends BasePO<Integer, Long> {

    /**
     * 配置键
     */
    @Column(nullable = false, unique = true)
    private String prop;

    /**
     * 配置值
     */
    @Column(nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_id")
    private ConfigPO config;
}
