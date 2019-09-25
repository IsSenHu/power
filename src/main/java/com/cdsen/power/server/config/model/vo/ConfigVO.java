package com.cdsen.power.server.config.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/21
 */
@Getter
@Setter
public class ConfigVO {

    private Integer id;

    private String name;

    private List<PropertyVO> properties;
}
