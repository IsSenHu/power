package com.cdsen.power.server.config.service;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.config.model.ao.ConfigCreateAO;
import com.cdsen.power.server.config.model.vo.ConfigVO;

/**
 * @author HuSen
 * create on 2019/9/17 17:12
 */
public interface ConfigService {

    /**
     * 创建新配置
     *
     * @param ao 创建新配置数据模型
     * @return 创建结果
     */
    JsonResult<ConfigVO> create(ConfigCreateAO ao);
}
