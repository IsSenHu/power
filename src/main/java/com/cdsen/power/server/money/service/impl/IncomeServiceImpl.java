package com.cdsen.power.server.money.service.impl;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.security.model.Session;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.server.money.dao.po.IncomePO;
import com.cdsen.power.server.money.dao.repository.IncomeRepository;
import com.cdsen.power.server.money.model.ao.InComeCreateAO;
import com.cdsen.power.server.money.model.vo.IncomeVO;
import com.cdsen.power.server.money.service.IncomeService;
import com.cdsen.power.server.money.transfer.IncomeTransfer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author HuSen
 * create on 2019/9/3 11:26
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<IncomeVO> create(InComeCreateAO ao) {
        Session session = SecurityUtils.currentSession();
        Assert.notNull(session, "不要搞事");

        IncomePO po = IncomeTransfer.CREATE_TO_PO.apply(ao);
        po.setUserId(session.getUserId());
        incomeRepository.save(po);

        return JsonResult.of(IncomeTransfer.PO_TO_VO.apply(po));
    }
}
