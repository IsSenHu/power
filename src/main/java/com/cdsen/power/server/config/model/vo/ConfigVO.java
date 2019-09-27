package com.cdsen.power.server.config.model.vo;

import com.cdsen.power.server.config.model.cons.ConfigType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/9/26 17:32
 */
@Getter
@Setter
public class ConfigVO {

    private Long id;

    private String name;

    private String type;

    private ConfigType configType;
}
