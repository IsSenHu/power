package com.cdsen.power.server.config.transfer;

import com.cdsen.power.core.util.JsonUtils;
import com.cdsen.power.server.config.dao.po.ConfigPO;
import com.cdsen.power.server.config.model.vo.ConfigVO;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Function;

/**
 * @author HuSen
 * create on 2019/9/27 10:57
 */
public class ConfigTransfer {

    public static final Function<ConfigPO, ConfigVO> PO_TO_VO = po -> {
        ConfigVO vo = new ConfigVO();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setType(po.getType().getName());
        vo.setConfigType(po.getType());
        String customInfo = po.getCustomInfo();
        if (StringUtils.isNotBlank(customInfo)) {
            vo.setCustomInfo(JsonUtils.parseObj(customInfo, Map.class));
        }
        return vo;
    };
}
