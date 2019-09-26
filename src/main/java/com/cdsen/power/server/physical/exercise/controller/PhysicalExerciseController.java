package com.cdsen.power.server.physical.exercise.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.physical.exercise.model.ao.PhysicalExerciseCreateAO;
import com.cdsen.power.server.physical.exercise.model.ao.PhysicalExerciseUpdateAO;
import com.cdsen.power.server.physical.exercise.model.ao.PhysicalExerciseUpdateInfoAO;
import com.cdsen.power.server.physical.exercise.model.query.PhysicalExerciseQuery;
import com.cdsen.power.server.physical.exercise.model.vo.PhysicalExerciseVO;
import com.cdsen.power.server.physical.exercise.service.PhysicalExerciseService;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * create on 2019/9/26 10:33
 */
@RestController
@RequestMapping("/api/physical/exercise")
public class PhysicalExerciseController {

    private final PhysicalExerciseService physicalExerciseService;

    public PhysicalExerciseController(PhysicalExerciseService physicalExerciseService) {
        this.physicalExerciseService = physicalExerciseService;
    }

    /**
     * 新增每日体育锻炼
     *
     * @param ao 新增每日体育锻炼数据模型
     * @return 新增结果
     */
    @PutMapping
    public JsonResult<PhysicalExerciseVO> create(@RequestBody PhysicalExerciseCreateAO ao) {
        return physicalExerciseService.create(ao);
    }

    /**
     * 根据ID删除体育锻炼
     *
     * @param id ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<PhysicalExerciseVO> delete(@PathVariable Long id) {
        return physicalExerciseService.delete(id);
    }

    /**
     * 修改体育锻炼
     *
     * @param ao 修改体育锻炼数据模型
     * @return 修改结果
     */
    @PostMapping
    public JsonResult<PhysicalExerciseVO> update(@RequestBody PhysicalExerciseUpdateAO ao) {
        return physicalExerciseService.update(ao);
    }

    /**
     * 根据ID查询更新需要展示的信息
     *
     * @param id ID
     * @return 更新需要展示的信息
     */
    @GetMapping("/{id}")
    public JsonResult<PhysicalExerciseUpdateInfoAO> findById(@PathVariable Long id) {
        return physicalExerciseService.findById(id);
    }

    /**
     * 分页查询体育锻炼
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    @PostMapping("/page")
    public JsonResult<PageResult<PhysicalExerciseVO>> page(@RequestBody IPageRequest<PhysicalExerciseQuery> iPageRequest) {
        return physicalExerciseService.page(iPageRequest);
    }
}
