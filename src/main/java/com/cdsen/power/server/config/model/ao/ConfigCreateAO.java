package com.cdsen.power.server.config.model.ao;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/21
 */
@Getter
@Setter
public class ConfigCreateAO {

    @NotBlank
    private String name;

    private List<PropertyCreateAO> properties;
}
