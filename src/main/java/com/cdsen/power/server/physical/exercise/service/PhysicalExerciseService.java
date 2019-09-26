package com.cdsen.power.server.physical.exercise.service;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.physical.exercise.model.ao.PhysicalExerciseCreateAO;
import com.cdsen.power.server.physical.exercise.model.ao.PhysicalExerciseUpdateAO;
import com.cdsen.power.server.physical.exercise.model.ao.PhysicalExerciseUpdateInfoAO;
import com.cdsen.power.server.physical.exercise.model.query.PhysicalExerciseQuery;
import com.cdsen.power.server.physical.exercise.model.vo.PhysicalExerciseVO;

/**
 * @author HuSen
 * create on 2019/9/26 10:34
 */
public interface PhysicalExerciseService {

    /**
     * 新增每日体育锻炼
     *
     * @param ao 新增每日体育锻炼数据模型
     * @return 新增结果
     */
    JsonResult<PhysicalExerciseVO> create(PhysicalExerciseCreateAO ao);

    /**
     * 根据ID删除体育锻炼
     *
     * @param id ID
     * @return 删除结果
     */
    JsonResult<PhysicalExerciseVO> delete(Long id);

    /**
     * 修改体育锻炼
     *
     * @param ao 修改体育锻炼数据模型
     * @return 修改结果
     */
    JsonResult<PhysicalExerciseVO> update(PhysicalExerciseUpdateAO ao);

    /**
     * 根据ID查询更新需要展示的信息
     *
     * @param id ID
     * @return 更新需要展示的信息
     */
    JsonResult<PhysicalExerciseUpdateInfoAO> findById(Long id);

    /**
     * 分页查询体育锻炼
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    JsonResult<PageResult<PhysicalExerciseVO>> page(IPageRequest<PhysicalExerciseQuery> iPageRequest);
}
