package com.cdsen.power.server.money.service;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.money.model.ao.ConsumptionCreateAO;
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
}
