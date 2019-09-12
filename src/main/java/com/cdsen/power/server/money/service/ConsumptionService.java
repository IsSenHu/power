package com.cdsen.power.server.money.service;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.money.model.ao.ConsumptionCreateAO;
import com.cdsen.power.server.money.model.query.ConsumptionQuery;
import com.cdsen.power.server.money.model.vo.ConsumptionVO;

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
}
