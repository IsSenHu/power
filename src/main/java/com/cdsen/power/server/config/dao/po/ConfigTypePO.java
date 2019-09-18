package com.cdsen.power.server.config.dao.po;

import com.cdsen.power.core.jpa.BasePO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/17 17:05
 */
@Getter
@Setter
@Entity
@Table(name = "tb_config_type")
@NamedEntityGraph(name = "ConfigTypePO.configs", attributeNodes = {
        @NamedAttributeNode("configs")
})
public class ConfigTypePO extends BasePO<Integer, Long> {

    /**
     * 配置名称
     */
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "configType")
    private List<ConfigPO> configs;
}
