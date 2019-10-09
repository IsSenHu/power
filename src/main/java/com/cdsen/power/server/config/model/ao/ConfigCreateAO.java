package com.cdsen.power.server.config.model.ao;

import com.cdsen.power.server.config.model.cons.ConfigType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author HuSen
 * create on 2019/9/26 17:10
 */
@Getter
@Setter
public class ConfigCreateAO {

    @NotBlank
    private String name;

    @NotNull
    private ConfigType type;

    private Map customInfo;
}
