package com.cdsen.power.server.config.service;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.config.model.ao.ConfigCreateAO;
import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.config.model.ao.ConfigUpdateAO;
import com.cdsen.power.server.config.model.vo.ConfigVO;

import java.util.List;
import java.util.Map;

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
    JsonResult create(ConfigCreateAO ao);

    /**
     * 根据ID删除配置
     *
     * @param id ID
     * @return 删除结果
     */
    JsonResult<ConfigVO> delete(Long id);

    /**
     * 修改配置
     *
     * @param ao 修改配置数据模型
     * @return 修改结果
     */
    JsonResult update(ConfigUpdateAO ao);

    /**
     * 分页查询配置
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    JsonResult<PageResult<ConfigVO>> page(IPageRequest<String> iPageRequest);

    /**
     * 根据ID查询配置
     *
     * @param id ID
     * @return 配置
     */
    JsonResult<ConfigVO> findById(Long id);

    /**
     * 查询所有的配置
     *
     * @return 所有的配置
     */
    JsonResult<Map<String, List<ConfigVO>>> findAll();

    /**
     * 查询所有的配置
     *
     * @param userId 用户ID
     * @return 所有的配置
     */
    Map<String, List<ConfigVO>> findAllByUserId(Long userId);
}
