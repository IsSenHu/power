package com.cdsen.power.server.money.transfer;

import com.cdsen.power.server.money.dao.po.ConsumptionItemPO;
import com.cdsen.power.server.money.dao.po.ConsumptionPO;
import com.cdsen.power.server.money.model.ao.ConsumptionItemCreateAO;
import com.cdsen.power.server.money.model.ao.ConsumptionItemUpdateInfoAO;
import com.cdsen.power.server.money.model.vo.ConsumptionItemVO;

import java.util.function.Function;

/**
 * @author HuSen
 * create on 2019/9/24 15:40
 */
public class ConsumptionItemTransfer {

    public static final Function<ConsumptionItemPO, ConsumptionItemVO> PO_TO_VO = po -> {
        ConsumptionItemVO vo = new ConsumptionItemVO();
        ConsumptionPO x = po.getConsumption();
        vo.setId(po.getId());
        vo.setCurrency(x.getCurrency().getCurrency().getDisplayName());
        vo.setConsumptionId(x.getId());
        vo.setDescription(po.getDescription());
        vo.setMoney(x.getCurrency().getFormat().format(po.getMoney()));
        vo.setType(po.getType());
        return vo;
    };

    public static final Function<ConsumptionItemCreateAO, ConsumptionItemPO> CREATE_TO_PO = ao -> {
        ConsumptionItemPO po = new ConsumptionItemPO();
        po.setMoney(ao.getMoney());
        po.setDescription(ao.getDescription());
        po.setType(ao.getType());
        return po;
    };

    public static final Function<ConsumptionItemPO, ConsumptionItemUpdateInfoAO> PO_TO_UPDATE_INFO = po -> {
        ConsumptionItemUpdateInfoAO ao = new ConsumptionItemUpdateInfoAO();
        ao.setId(po.getId());
        ao.setMoney(po.getMoney());
        ao.setDescription(po.getDescription());
        ao.setType(po.getType());
        return ao;
    };
}
