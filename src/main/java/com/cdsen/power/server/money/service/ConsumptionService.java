package com.cdsen.power.server.money.service;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.money.model.ao.*;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.cdsen.power.server.money.model.query.ConsumptionQuery;
import com.cdsen.power.server.money.model.vo.ConsumptionItemVO;
import com.cdsen.power.server.money.model.vo.ConsumptionStatisticsVO;
import com.cdsen.power.server.money.model.vo.ConsumptionVO;
import com.cdsen.rabbit.model.ConsumptionCreateDTO;

/**
 * @author HuSen
 * create on 2019/9/12 11:11
 */
public interface ConsumptionService {

    /**
     * 创建消费记录
     *
     * @param ao 创建消费记录数据模型
     * @return 创建结果
     */
    JsonResult<ConsumptionVO> create(ConsumptionCreateAO ao);

    /**
     * 根据ID删除
     *
     * @param id ID
     * @return 删除结果
     */
    JsonResult<ConsumptionVO> delete(Long id);

    /**
     * 分页查询消费
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    JsonResult<PageResult<ConsumptionVO>> page(IPageRequest<ConsumptionQuery> iPageRequest);

    /**
     * 根据ID查询消费
     *
     * @param id ID
     * @return 消费
     */
    JsonResult<ConsumptionUpdateInfoAO> findById(Long id);

    /**
     * 根据消费条目ID删除
     *
     * @param id 消费条目ID
     * @return 删除结果
     */
    JsonResult<ConsumptionItemVO> deleteItem(Long id);

    /**
     * 修改消费
     *
     * @param ao 修改消费数据模型
     * @return 修改结果
     */
    JsonResult<ConsumptionVO> update(ConsumptionUpdateAO ao);

    /**
     * 新增消费明细
     *
     * @param ao 新增消费明细数据模型
     * @return 新增结果
     */
    JsonResult<ConsumptionItemVO> createItem(ConsumptionItemCreateAO ao);

    /**
     * 根据ID查询消费条目
     *
     * @param id ID
     * @return 消费条目
     */
    JsonResult<ConsumptionItemUpdateInfoAO> findItemById(Long id);

    /**
     * 修改消费明细
     *
     * @param ao 修改消费明细数据模型
     * @return 修改结果
     */
    JsonResult<ConsumptionItemVO> updateItem(ConsumptionItemUpdateAO ao);

    /**
     * 简单统计
     *
     * @param currency 货币类型
     * @param query    简单统计查询参数
     * @return 简单统计
     */
    JsonResult<ConsumptionStatisticsVO> statistics(CurrencyType currency, ConsumptionQuery query);

    /**
     * 创建消费记录
     *
     * @param userId 用户ID
     * @param dto    数据模型
     */
    void create(Long userId, ConsumptionCreateDTO dto);
}
