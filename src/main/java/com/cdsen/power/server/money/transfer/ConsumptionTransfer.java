package com.cdsen.power.server.money.transfer;

import com.cdsen.power.server.money.dao.po.ConsumptionItemPO;
import com.cdsen.power.server.money.dao.po.ConsumptionPO;
import com.cdsen.power.server.money.model.ao.ConsumptionCreateAO;
import com.cdsen.power.server.money.model.vo.ConsumptionItemVO;
import com.cdsen.power.server.money.model.vo.ConsumptionVO;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
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
        po.setCurrency(ao.getCurrency());

        List<ConsumptionItemPO> items = ao.getItems().stream().map(item -> {
            ConsumptionItemPO consumptionItem = new ConsumptionItemPO();
            consumptionItem.setMoney(item.getMoney());
            consumptionItem.setDescription(item.getDescription());
            return consumptionItem;
        }).collect(Collectors.toList());
        return Pair.of(po, items);
    };

    public static final BiFunction<ConsumptionPO, List<ConsumptionItemPO>, ConsumptionVO> PO_ITEMS_TO_VO = (po, items) -> {
        ConsumptionVO vo = new ConsumptionVO();
        vo.setId(po.getId());
        vo.setTime(po.getTime());

        String displayNameCurrency = po.getCurrency().getCurrency().getDisplayName();
        NumberFormat formatCurrency = po.getCurrency().getFormat();

        List<ConsumptionItemVO> itemVos = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (ConsumptionItemPO item : items) {
            ConsumptionItemVO itemVo = new ConsumptionItemVO();
            itemVo.setId(item.getId());
            itemVo.setConsumptionId(item.getConsumptionId());
            itemVo.setMoney(formatCurrency.format(item.getMoney()));
            itemVo.setCurrency(displayNameCurrency);
            itemVo.setDescription(item.getDescription());
            total = total.add(item.getMoney());
        }

        vo.setCurrency(displayNameCurrency);
        vo.setItems(itemVos);
        vo.setTotal(formatCurrency.format(total.setScale(2, RoundingMode.HALF_UP)));
        return vo;
    };
}
