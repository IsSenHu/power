package com.cdsen.power.server.health.service;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.health.model.ao.SleepCreateAO;
import com.cdsen.power.server.health.model.ao.SleepUpdateAO;
import com.cdsen.power.server.health.model.ao.SleepUpdateInfoAO;
import com.cdsen.power.server.health.model.query.SleepQuery;
import com.cdsen.power.server.health.model.vo.SleepVO;

/**
 * @author HuSen
 * create on 2019/9/25 10:26
 */
public interface SleepService {

    /**
     * 创建睡眠
     *
     * @param ao 创建睡眠数据模型
     * @return 创建结果
     */
    JsonResult<SleepVO> create(SleepCreateAO ao);

    /**
     * 根据ID删除睡眠
     *
     * @param id ID
     * @return 删除结果
     */
    JsonResult<SleepVO> delete(Long id);

    /**
     * 修改睡眠信息
     *
     * @param ao 修改睡眠信息数据模型
     * @return 修改结果
     */
    JsonResult<SleepVO> update(SleepUpdateAO ao);

    /**
     * 根据ID查询睡眠修改展示的信息
     *
     * @param id ID
     * @return 睡眠修改展示的信息
     */
    JsonResult<SleepUpdateInfoAO> findById(Long id);

    /**
     * 分页查询睡眠
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    JsonResult<PageResult<SleepVO>> page(IPageRequest<SleepQuery> iPageRequest);
}
