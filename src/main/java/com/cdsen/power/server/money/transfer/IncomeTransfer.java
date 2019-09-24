package com.cdsen.power.server.money.transfer;

import com.cdsen.power.server.money.dao.po.IncomePO;
import com.cdsen.power.server.money.model.ao.InComeCreateAO;
import com.cdsen.power.server.money.model.ao.IncomeUpdateAO;
import com.cdsen.power.server.money.model.ao.IncomeUpdateInfoAO;
import com.cdsen.power.server.money.model.vo.IncomeVO;

import java.util.function.Function;

/**
 * @author HuSen
 * create on 2019/9/3 11:51
 */
public class IncomeTransfer {

    public static final Function<InComeCreateAO, IncomePO> CREATE_TO_PO = ao -> {
        IncomePO po = new IncomePO();
        po.setIncome(ao.getIncome());
        po.setTime(ao.getTime());
        po.setDescription(ao.getDescription());
        po.setCurrency(ao.getCurrency());
        return po;
    };

    public static final Function<IncomePO, IncomeVO> PO_TO_VO = po -> {
        IncomeVO vo = new IncomeVO();
        vo.setId(po.getId());
        vo.setDescription(po.getDescription());
        vo.setTime(po.getTime());
        vo.setIncome(po.getCurrency().getFormat().format(po.getIncome()));
        vo.setCurrency(po.getCurrency().getCurrency().getDisplayName());
        return vo;
    };

    public static final Function<IncomePO, IncomeUpdateInfoAO> PO_TO_UPDATE_INFO = po -> {
        IncomeUpdateInfoAO info = new IncomeUpdateInfoAO();
        info.setId(po.getId());
        info.setIncome(po.getIncome());
        info.setCurrency(po.getCurrency());
        info.setUserId(po.getUserId());
        info.setTime(po.getTime());
        info.setDescription(po.getDescription());
        return info;
    };

    public static final Function<IncomeUpdateAO, IncomePO> UPDATE_TO_PO = ao -> {
        IncomePO po = new IncomePO();
        po.setId(ao.getId());
        po.setIncome(ao.getIncome());
        po.setCurrency(ao.getCurrency());
        po.setTime(ao.getTime());
        po.setDescription(ao.getDescription());
        return po;
    };
}
