package com.cdsen.power.server.money.service.impl;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.SpecificationFactory;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        Session session = SecurityUtils.currentSession();

        ConsumptionPO po = pair.getFirst();
        po.setUserId(session.getUserId());
        consumptionRepository.save(po);

        List<ConsumptionItemPO> items = pair.getSecond();
        items.forEach(item -> item.setConsumption(po));
        consumptionItemRepository.saveAll(items);

        po.setItems(items);
        return JsonResult.of(ConsumptionTransfer.PO_TO_VO.apply(po));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConsumptionVO> delete(Long id) {
        return consumptionRepository.findById(id)
                .map(po -> {
                    consumptionRepository.delete(po);
                    return JsonResult.of(ConsumptionTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(JsonResult::success);
    }

    @Override
    public JsonResult<PageResult<ConsumptionVO>> page(IPageRequest<ConsumptionQuery> iPageRequest) {
        Session session = SecurityUtils.currentSession();
        Long userId = session.getUserId();

        Pageable pageable = iPageRequest.of();
        Page<ConsumptionPO> page = consumptionRepository.findAll(SpecificationFactory.produce((predicates, root, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("userId").as(Long.class), userId));
            ConsumptionQuery customParams = iPageRequest.getCustomParams();
            if (null != customParams) {
                LocalDateTime start = customParams.getStart();
                if (null != start) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("time").as(LocalDateTime.class), start));
                }
                LocalDateTime end = customParams.getEnd();
                if (null != end) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("time").as(LocalDateTime.class), end));
                }
            }
        }), pageable);

        return JsonResult.of(PageResult.of(page.getTotalElements(), ConsumptionTransfer.PO_TO_VO, page.getContent()));
    }
}
