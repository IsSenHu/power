package com.cdsen.power.server.config.model.vo;

import com.cdsen.power.server.config.model.cons.ConfigType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author HuSen
 * create on 2019/9/26 17:32
 */
@Getter
@Setter
@ToString
public class ConfigVO {

    private Long id;

    private String name;

    private String type;

    private ConfigType configType;

    private Map customInfo;
}
