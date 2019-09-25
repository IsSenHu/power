package com.cdsen.power.server.config.transfer;

import com.cdsen.power.server.config.dao.po.ConfigPO;
import com.cdsen.power.server.config.dao.po.PropertyPO;
import com.cdsen.power.server.config.model.vo.ConfigVO;
import com.cdsen.power.server.config.model.vo.PropertyVO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/21
 */
public class ConfigTransfer {

    public static final Function<ConfigPO, ConfigVO> PO_TO_VO = po -> {
        ConfigVO config = new ConfigVO();
        config.setId(po.getId());
        config.setName(po.getName());
        List<PropertyPO> properties = po.getProperties();
        if (!CollectionUtils.isEmpty(properties)) {
            config.setProperties(properties.stream().map(item -> {
                PropertyVO property = new PropertyVO();
                property.setId(item.getId());
                property.setProp(item.getProp());
                property.setValue(item.getValue());
                return property;
            }).collect(Collectors.toList()));
        }
        return config;
    };
}
