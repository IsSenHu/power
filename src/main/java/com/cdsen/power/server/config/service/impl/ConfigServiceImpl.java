package com.cdsen.power.server.config.service.impl;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.config.dao.repository.ConfigRepository;
import com.cdsen.power.server.config.model.ao.ConfigCreateAO;
import com.cdsen.power.server.config.model.ao.ConfigUpdateAO;
import com.cdsen.power.server.config.model.cons.ConfigType;
import com.cdsen.power.server.config.model.vo.ConfigVO;
import com.cdsen.power.server.config.service.ConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HuSen
 * create on 2019/9/17 17:12
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    public ConfigServiceImpl(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult create(ConfigCreateAO ao) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConfigVO> delete(Long id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult update(ConfigUpdateAO ao) {
        return null;
    }

    @Override
    public JsonResult<PageResult<ConfigVO>> page(IPageRequest<ConfigType> iPageRequest) {
        return null;
    }

    @Override
    public JsonResult<ConfigVO> findById(Long id) {
        return null;
    }
}
