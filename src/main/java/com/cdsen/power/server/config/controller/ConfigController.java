package com.cdsen.power.server.config.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.config.model.ao.ConfigCreateAO;
import com.cdsen.power.server.config.model.ao.ConfigUpdateAO;
import com.cdsen.power.server.config.model.cons.ConfigType;
import com.cdsen.power.server.config.model.vo.ConfigVO;
import com.cdsen.power.server.config.service.ConfigService;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping
    public JsonResult create(@RequestBody ConfigCreateAO ao) {
        return configService.create(ao);
    }

    /**
     * 根据ID删除配置
     *
     * @param id ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<ConfigVO> delete(@PathVariable Long id) {
        return configService.delete(id);
    }

    /**
     * 修改配置
     *
     * @param ao 修改配置数据模型
     * @return 修改结果
     */
    @PostMapping
    public JsonResult update(@RequestBody ConfigUpdateAO ao) {
        return configService.update(ao);
    }

    /**
     * 分页查询配置
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    @PostMapping("/page")
    public JsonResult<PageResult<ConfigVO>> page(@RequestBody IPageRequest<ConfigType> iPageRequest) {
        return configService.page(iPageRequest);
    }
}
