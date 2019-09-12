package com.cdsen.power.server.money.controller;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.money.model.ao.InComeCreateAO;
import com.cdsen.power.server.money.model.ao.IncomeUpdateAO;
import com.cdsen.power.server.money.model.query.InComeQuery;
import com.cdsen.power.server.money.model.ao.IncomeUpdateInfoAO;
import com.cdsen.power.server.money.model.vo.IncomeVO;
import com.cdsen.power.server.money.service.IncomeService;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * create on 2019/9/3 10:44
 */
@RestController
@RequestMapping("/api/inCome")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    /**
     * 创建收入
     *
     * @param ao 创建收入数据模型
     * @return 创建结果
     */
    @PutMapping
    public JsonResult<IncomeVO> create(@RequestBody InComeCreateAO ao) {
        return incomeService.create(ao);
    }

    /**
     * 分页查询收入
     *
     * @param iPageRequest 分页请求
     * @return 分页结果
     */
    @PostMapping("/page")
    public JsonResult<PageResult<IncomeVO>> page(@RequestBody IPageRequest<InComeQuery> iPageRequest) {
        return incomeService.page(iPageRequest);
    }

    /**
     * 根据ID删除收入
     *
     * @param id ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<IncomeVO> delete(@PathVariable Long id) {
        return incomeService.delete(id);
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 收入信息
     */
    @GetMapping("/{id}")
    public JsonResult<IncomeUpdateInfoAO> findById(@PathVariable Long id) {
        return incomeService.findById(id);
    }

    /**
     * 修改收入
     *
     * @param ao 修改收入数据模型
     * @return 修改结果
     */
    @PostMapping
    public JsonResult<IncomeVO> update(@RequestBody IncomeUpdateAO ao) {
        return incomeService.update(ao);
    }
}
