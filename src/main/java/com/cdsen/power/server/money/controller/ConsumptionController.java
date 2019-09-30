package com.cdsen.power.server.money.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.money.model.ao.*;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.cdsen.power.server.money.model.query.ConsumptionQuery;
import com.cdsen.power.server.money.model.vo.ConsumptionItemVO;
import com.cdsen.power.server.money.model.vo.ConsumptionStatisticsVO;
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
    public JsonResult<PageResult<ConsumptionVO>> page(@RequestBody IPageRequest<ConsumptionQuery> iPageRequest) {
        return consumptionService.page(iPageRequest);
    }

    /**
     * 根据ID查询消费
     *
     * @param id ID
     * @return 消费
     */
    @GetMapping("/{id}")
    public JsonResult<ConsumptionUpdateInfoAO> findById(@PathVariable Long id) {
        return consumptionService.findById(id);
    }

    /**
     * 修改消费
     *
     * @param ao 修改消费数据模型
     * @return 修改结果
     */
    @PostMapping
    public JsonResult<ConsumptionVO> update(@RequestBody ConsumptionUpdateAO ao) {
        return consumptionService.update(ao);
    }

    /**
     * 根据消费条目ID删除
     *
     * @param id 消费条目ID
     * @return 删除结果
     */
    @DeleteMapping("/item/{id}")
    public JsonResult<ConsumptionItemVO> deleteItem(@PathVariable Long id) {
        return consumptionService.deleteItem(id);
    }

    /**
     * 新增消费明细
     *
     * @param ao 新增消费明细数据模型
     * @return 新增结果
     */
    @PutMapping("/item")
    public JsonResult<ConsumptionItemVO> createItem(@RequestBody ConsumptionItemCreateAO ao) {
        return consumptionService.createItem(ao);
    }

    /**
     * 根据ID查询消费条目
     *
     * @param id ID
     * @return 消费条目
     */
    @GetMapping("/item/{id}")
    public JsonResult<ConsumptionItemUpdateInfoAO> findItemById(@PathVariable Long id) {
        return consumptionService.findItemById(id);
    }

    /**
     * 修改消费明细
     *
     * @param ao 修改消费明细数据模型
     * @return 修改结果
     */
    @PostMapping("/item")
    public JsonResult<ConsumptionItemVO> updateItem(@RequestBody ConsumptionItemUpdateAO ao) {
        return consumptionService.updateItem(ao);
    }

    /**
     * 简单统计
     *
     * @param currency 货币类型
     * @param query    简单统计查询参数
     * @return 简单统计
     */
    @PostMapping("/statistics/{currency}")
    public JsonResult<ConsumptionStatisticsVO> statistics(@PathVariable CurrencyType currency, @RequestBody ConsumptionQuery query) {
        return consumptionService.statistics(currency, query);
    }
}
