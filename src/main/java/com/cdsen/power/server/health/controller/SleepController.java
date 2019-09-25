package com.cdsen.power.server.health.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.health.model.ao.SleepCreateAO;
import com.cdsen.power.server.health.model.ao.SleepUpdateAO;
import com.cdsen.power.server.health.model.ao.SleepUpdateInfoAO;
import com.cdsen.power.server.health.model.query.SleepQuery;
import com.cdsen.power.server.health.model.vo.SleepVO;
import com.cdsen.power.server.health.service.SleepService;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * create on 2019/9/25 10:25
 */
@RestController
@RequestMapping("/api/sleep")
public class SleepController {

    private final SleepService sleepService;

    public SleepController(SleepService sleepService) {
        this.sleepService = sleepService;
    }

    /**
     * 创建睡眠
     *
     * @param ao 创建睡眠数据模型
     * @return 创建结果
     */
    @PutMapping
    public JsonResult<SleepVO> create(@RequestBody SleepCreateAO ao) {
        return sleepService.create(ao);
    }

    /**
     * 根据ID删除睡眠
     *
     * @param id ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<SleepVO> delete(@PathVariable Long id) {
        return sleepService.delete(id);
    }

    /**
     * 修改睡眠信息
     *
     * @param ao 修改睡眠信息数据模型
     * @return 修改结果
     */
    @PostMapping
    public JsonResult<SleepVO> update(@RequestBody SleepUpdateAO ao) {
        return sleepService.update(ao);
    }

    /**
     * 根据ID查询睡眠修改展示的信息
     *
     * @param id ID
     * @return 睡眠修改展示的信息
     */
    @GetMapping("/{id}")
    public JsonResult<SleepUpdateInfoAO> findById(@PathVariable Long id) {
        return sleepService.findById(id);
    }

    /**
     * 分页查询睡眠
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    @PostMapping("/page")
    public JsonResult<PageResult<SleepVO>> page(@RequestBody IPageRequest<SleepQuery> iPageRequest) {
        return sleepService.page(iPageRequest);
    }
}
