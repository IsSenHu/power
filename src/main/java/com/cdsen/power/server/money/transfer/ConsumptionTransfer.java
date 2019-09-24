package com.cdsen.power.server.money.transfer;

import com.cdsen.power.server.money.dao.po.ConsumptionItemPO;
import com.cdsen.power.server.money.dao.po.ConsumptionPO;
import com.cdsen.power.server.money.model.ao.ConsumptionCreateAO;
import com.cdsen.power.server.money.model.ao.ConsumptionItemCreateAO;
import com.cdsen.power.server.money.model.ao.ConsumptionUpdateInfoAO;
import com.cdsen.power.server.money.model.vo.ConsumptionItemVO;
import com.cdsen.power.server.money.model.vo.ConsumptionVO;
import com.google.common.collect.Lists;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
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
        po.setCurrency(ao.getCurrency());
        List<ConsumptionItemCreateAO> itemAos = ao.getItems();
        List<ConsumptionItemPO> items = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(itemAos)) {
            items = itemAos.stream().map(item -> {
                ConsumptionItemPO consumptionItem = new ConsumptionItemPO();
                consumptionItem.setMoney(item.getMoney());
                consumptionItem.setDescription(item.getDescription());
                return consumptionItem;
            }).collect(Collectors.toList());
        }
        return Pair.of(po, items);
    };

    public static final Function<ConsumptionPO, ConsumptionUpdateInfoAO> PO_TO_UPDATE_INFO = po -> {
        ConsumptionUpdateInfoAO ao = new ConsumptionUpdateInfoAO();
        ao.setId(po.getId());
        ao.setCurrency(po.getCurrency());
        ao.setTime(po.getTime());
        return ao;
    };

    public static final Function<ConsumptionPO, ConsumptionVO> PO_TO_VO = po -> {
        ConsumptionVO vo = new ConsumptionVO();
        vo.setId(po.getId());
        vo.setTime(po.getTime());

        String displayNameCurrency = po.getCurrency().getCurrency().getDisplayName();
        NumberFormat formatCurrency = po.getCurrency().getFormat();

        List<ConsumptionItemPO> items = po.getItems();
        List<ConsumptionItemVO> itemVos = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(items)) {
            for (ConsumptionItemPO item : items) {
                ConsumptionItemVO itemVo = new ConsumptionItemVO();
                itemVo.setConsumptionId(po.getId());
                itemVo.setId(item.getId());
                itemVo.setMoney(formatCurrency.format(item.getMoney()));
                itemVo.setCurrency(displayNameCurrency);
                itemVo.setDescription(item.getDescription());
                total = total.add(item.getMoney());
                itemVos.add(itemVo);
            }
        }
        vo.setCurrency(displayNameCurrency);
        vo.setItems(itemVos);
        vo.setTotal(formatCurrency.format(total.setScale(2, RoundingMode.HALF_UP)));
        return vo;
    };
}
