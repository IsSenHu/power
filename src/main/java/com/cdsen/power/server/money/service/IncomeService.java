package com.cdsen.power.server.money.service;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.money.model.ao.InComeCreateAO;
import com.cdsen.power.server.money.model.vo.IncomeVO;

/**
 * @author HuSen
 * create on 2019/9/3 11:26
 */
public interface IncomeService {

    JsonResult<IncomeVO> create(InComeCreateAO ao);
}
