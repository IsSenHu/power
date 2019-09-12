package com.cdsen.power.server.money.transfer;

import com.cdsen.power.server.money.dao.po.ConsumptionItemPO;
import com.cdsen.power.server.money.dao.po.ConsumptionPO;
import com.cdsen.power.server.money.model.ao.ConsumptionCreateAO;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/12 11:39
 */
public class ConsumptionTransfer {

    public static final Function<ConsumptionCreateAO, Pair<ConsumptionPO, List<ConsumptionItemPO>>> AO_TO_PO_PAIR = ao -> {
        ConsumptionPO po = new ConsumptionPO();
        po.setTime(ao.getTime());

        List<ConsumptionItemPO> items = ao.getItems().stream().map(item -> {
            ConsumptionItemPO consumptionItem = new ConsumptionItemPO();
            consumptionItem.setMoney(item.getMoney());
            consumptionItem.setDescription(item.getDescription());
            consumptionItem.setCurrency(item.getCurrency());
            return consumptionItem;
        }).collect(Collectors.toList());
        return Pair.of(po, items);
    };
}
