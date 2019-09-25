package com.cdsen.power.server.config.controller;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.security.annotation.Permission;
import com.cdsen.power.server.config.model.ao.ConfigCreateAO;
import com.cdsen.power.server.config.model.cons.PreAuthorizes;
import com.cdsen.power.server.config.model.vo.ConfigVO;
import com.cdsen.power.server.config.service.ConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuSen
 * create on 2019/9/17 17:11
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 创建新配置
     *
     * @param ao 创建新配置数据模型
     * @return 创建结果
     */
    @PreAuthorize(PreAuthorizes.Config.CREATE)
    @Permission("创建新配置")
    @PutMapping
    public JsonResult<ConfigVO> create(@RequestBody ConfigCreateAO ao) {
        return configService.create(ao);
    }
}
