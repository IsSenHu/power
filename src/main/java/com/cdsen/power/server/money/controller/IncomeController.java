package com.cdsen.power.server.money.controller;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.money.model.ao.InComeCreateAO;
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

    @PutMapping
    public JsonResult<IncomeVO> create(@RequestBody InComeCreateAO ao) {
        return incomeService.create(ao);
    }

    @PostMapping("/page")
    public JsonResult<PageResult<IncomeVO>> page() {
        return incomeService.page();
    }
}
