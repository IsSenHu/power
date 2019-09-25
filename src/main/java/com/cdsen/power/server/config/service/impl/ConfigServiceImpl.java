package com.cdsen.power.server.config.service.impl;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.config.dao.po.ConfigPO;
import com.cdsen.power.server.config.dao.po.PropertyPO;
import com.cdsen.power.server.config.dao.repository.ConfigRepository;
import com.cdsen.power.server.config.dao.repository.PropertyRepository;
import com.cdsen.power.server.config.model.ao.ConfigCreateAO;
import com.cdsen.power.server.config.model.ao.PropertyCreateAO;
import com.cdsen.power.server.config.model.vo.ConfigVO;
import com.cdsen.power.server.config.service.ConfigService;
import com.cdsen.power.server.config.transfer.ConfigTransfer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/17 17:12
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;
    private final PropertyRepository propertyRepository;

    public ConfigServiceImpl(ConfigRepository configRepository, PropertyRepository propertyRepository) {
        this.configRepository = configRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConfigVO> create(ConfigCreateAO ao) {
        boolean existsByName = configRepository.existsByName(ao.getName());
        if (existsByName) {
            return JsonResult.of(30001, "该配置已存在");
        }
        ConfigPO config = new ConfigPO();
        config.setName(ao.getName());
        configRepository.save(config);

        List<PropertyCreateAO> properties = ao.getProperties();
        if (!CollectionUtils.isEmpty(properties)) {
            List<PropertyPO> saves = properties.stream().map(item -> {
                PropertyPO property = new PropertyPO();
                property.setConfig(config);
                property.setProp(item.getProp());
                property.setValue(item.getValue());
                return property;
            }).collect(Collectors.toList());
            propertyRepository.saveAll(saves);
            config.setProperties(saves);
        }

        return JsonResult.of(ConfigTransfer.PO_TO_VO.apply(config));
    }
}
