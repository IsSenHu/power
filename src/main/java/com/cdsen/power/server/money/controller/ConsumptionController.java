package com.cdsen.power.server.money.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.money.model.ao.ConsumptionCreateAO;
import com.cdsen.power.server.money.model.query.ConsumptionQuery;
import com.cdsen.power.server.money.model.vo.ConsumptionVO;
import com.cdsen.power.server.money.service.ConsumptionService;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * create on 2019/9/12 11:10
 */
@RestController
@RequestMapping("/api/consumption")
public class ConsumptionController {

    private final ConsumptionService consumptionService;

    public ConsumptionController(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    /**
     * 创建消费记录
     *
     * @param ao 创建消费记录数据模型
     * @return 创建结果
     */
    @PutMapping
    public JsonResult<ConsumptionVO> create(@RequestBody ConsumptionCreateAO ao) {
        return consumptionService.create(ao);
    }

    /**
     * 根据ID删除
     *
     * @param id ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<ConsumptionVO> delete(@PathVariable Long id) {
        return consumptionService.delete(id);
    }

    /**
     * 分页查询消费
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    @PostMapping("/page")
    public JsonResult<PageResult<ConsumptionVO>> page(IPageRequest<ConsumptionQuery> iPageRequest) {
        return consumptionService.page(iPageRequest);
    }
}
