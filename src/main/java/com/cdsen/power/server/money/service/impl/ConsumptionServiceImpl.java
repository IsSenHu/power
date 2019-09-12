package com.cdsen.power.server.money.service.impl;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.security.model.Session;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.server.money.dao.po.ConsumptionItemPO;
import com.cdsen.power.server.money.dao.po.ConsumptionPO;
import com.cdsen.power.server.money.dao.repository.ConsumptionItemRepository;
import com.cdsen.power.server.money.dao.repository.ConsumptionRepository;
import com.cdsen.power.server.money.model.ao.ConsumptionCreateAO;
import com.cdsen.power.server.money.model.query.ConsumptionQuery;
import com.cdsen.power.server.money.model.vo.ConsumptionVO;
import com.cdsen.power.server.money.service.ConsumptionService;
import com.cdsen.power.server.money.transfer.ConsumptionTransfer;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/12 11:11
 */
@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionItemRepository consumptionItemRepository;

    public ConsumptionServiceImpl(ConsumptionRepository consumptionRepository, ConsumptionItemRepository consumptionItemRepository) {
        this.consumptionRepository = consumptionRepository;
        this.consumptionItemRepository = consumptionItemRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConsumptionVO> create(ConsumptionCreateAO ao) {
        Pair<ConsumptionPO, List<ConsumptionItemPO>> pair = ConsumptionTransfer.AO_TO_PO_PAIR.apply(ao);

        ConsumptionPO po = pair.getFirst();
        consumptionRepository.save(po);

        List<ConsumptionItemPO> items = pair.getSecond();
        items.forEach(item -> item.setConsumptionId(po.getId()));
        consumptionItemRepository.saveAll(items);

        return JsonResult.of(ConsumptionTransfer.PO_ITEMS_TO_VO.apply(po, items));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConsumptionVO> delete(Long id) {
        return consumptionRepository.findById(id)
                .map(po -> {
                    consumptionRepository.delete(po);
                    List<ConsumptionItemPO> items = consumptionItemRepository.findAllByConsumptionId(id);
                    consumptionItemRepository.deleteAllByConsumptionId(id);
                    return JsonResult.of(ConsumptionTransfer.PO_ITEMS_TO_VO.apply(po, items));
                })
                .orElseGet(JsonResult::success);
    }

    @Override
    public JsonResult<PageResult<ConsumptionVO>> page(IPageRequest<ConsumptionQuery> iPageRequest) {
        Session session = SecurityUtils.currentSession();
        if (session == null) {
            return JsonResult.of(PageResult.empty());
        }

        return null;
    }
}
