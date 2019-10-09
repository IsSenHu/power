package com.cdsen.power.server.config.service.impl;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.core.util.JsonUtils;
import com.cdsen.power.server.config.dao.po.ConfigPO;
import com.cdsen.power.server.config.dao.repository.ConfigRepository;
import com.cdsen.power.server.config.model.ao.ConfigCreateAO;
import com.cdsen.power.server.config.model.ao.ConfigUpdateAO;
import com.cdsen.power.server.config.model.cons.ConfigError;
import com.cdsen.power.server.config.model.cons.ConfigType;
import com.cdsen.power.server.config.model.vo.ConfigVO;
import com.cdsen.power.server.config.service.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cdsen.power.server.config.transfer.ConfigTransfer.PO_TO_VO;


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
        ConfigType type = ao.getType();
        String name = ao.getName();
        boolean exists = configRepository.existsByTypeAndName(type, name);
        if (exists) {
            return JsonResult.of(ConfigError.EXISTED);
        }
        ConfigPO po = new ConfigPO();
        Long userId = SecurityUtils.currentSession().getUserId();
        po.setName(name);
        po.setType(type);
        po.setUserId(userId);
        Map customInfo = ao.getCustomInfo();
        if (null != customInfo) {
            po.setCustomInfo(JsonUtils.toJsonString(customInfo));
        }
        configRepository.save(po);
        return JsonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConfigVO> delete(Long id) {
        return configRepository.findById(id)
                .map(po -> {
                    configRepository.delete(po);
                    return JsonResult.of(PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(ConfigError.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult update(ConfigUpdateAO ao) {
        return configRepository.findById(ao.getId())
                .map(po -> {
                    if (!StringUtils.equals(ao.getName(), po.getName())) {
                        boolean exists = configRepository.existsByTypeAndName(ao.getType(), ao.getName());
                        if (exists) {
                            return JsonResult.of(ConfigError.EXISTED);
                        }
                    }
                    po.setName(ao.getName());
                    Map customInfo = ao.getCustomInfo();
                    if (null != customInfo) {
                        po.setCustomInfo(JsonUtils.toJsonString(customInfo));
                    }
                    configRepository.save(po);
                    return JsonResult.success();
                })
                .orElseGet(() -> JsonResult.of(ConfigError.NOT_FOUND));
    }

    @Override
    public JsonResult<PageResult<ConfigVO>> page(IPageRequest<String> iPageRequest) {
        Pageable pageable = iPageRequest.of();
        Page<ConfigPO> pageInfo = StringUtils.isBlank(iPageRequest.getCustomParams()) ?
                configRepository.findAll(pageable) : configRepository.findAllByType(pageable, ConfigType.valueOf(iPageRequest.getCustomParams()));
        return JsonResult.of(PageResult.of(pageInfo.getTotalElements(), PO_TO_VO, pageInfo.getContent()));
    }

    @Override
    public JsonResult<ConfigVO> findById(Long id) {
        return configRepository.findById(id)
                .map(po -> JsonResult.of(PO_TO_VO.apply(po)))
                .orElseGet(() -> JsonResult.of(ConfigError.NOT_FOUND));
    }

    @Override
    public JsonResult<Map<String, List<ConfigVO>>> findAll() {
        return JsonResult.of(configRepository.findAll().stream().map(PO_TO_VO).collect(Collectors.groupingBy(vo -> vo.getConfigType().name())));
    }
}
