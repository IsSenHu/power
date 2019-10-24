package com.cdsen.power.server.config.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdsen.interfaces.config.service.SelfConfigApiService;
import com.cdsen.interfaces.config.vo.SelfConfig;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    @Reference(check = false)
    private SelfConfigApiService selfConfigApiService;

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
        Long userId = SecurityUtils.currentUserDetails().getUserId();
        po.setName(name);
        po.setType(type);
        po.setUserId(userId);
        Map customInfo = ao.getCustomInfo();
        if (null != customInfo) {
            po.setCustomInfo(JsonUtils.toJsonString(customInfo));
        }
        configRepository.save(po);
        pushChange(userId);
        return JsonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConfigVO> delete(Long id) {
        return configRepository.findById(id)
                .map(po -> {
                    configRepository.delete(po);
                    pushChange(po.getUserId());
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
                    pushChange(po.getUserId());
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
        Long userId = SecurityUtils.currentUserDetails().getUserId();
        return JsonResult.of(findAllByUserId(userId));
    }

    @Override
    public Map<String, List<ConfigVO>> findAllByUserId(Long userId) {
        return configRepository.findAllByUserId(userId).stream().map(PO_TO_VO).collect(Collectors.groupingBy(vo -> vo.getConfigType().name()));
    }

    private void pushChange(Long userId) {
        try {
            Map<String, List<SelfConfig>> all = configRepository.findAllByUserId(userId)
                    .stream()
                    .map(po -> {
                        SelfConfig selfConfig = new SelfConfig();
                        selfConfig.setId(po.getId());
                        selfConfig.setConfigType(po.getType().name());
                        selfConfig.setCustomInfo(JsonUtils.parseObj(po.getCustomInfo(), Map.class));
                        selfConfig.setName(po.getName());
                        selfConfig.setType(po.getType().getName());
                        return selfConfig;
                    })
                    .collect(Collectors.groupingBy(SelfConfig::getConfigType));
            selfConfigApiService.push(userId, all);
        } catch (Exception e) {
            log.error("推送用户:{} 配置变化失败:", userId, e);
        }
    }
}
