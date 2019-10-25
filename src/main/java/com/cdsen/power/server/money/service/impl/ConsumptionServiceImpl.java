package com.cdsen.power.server.money.service.impl;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.SpecificationFactory;
import com.cdsen.power.core.cons.TimeCons;
import com.cdsen.power.core.security.model.UserDetailsImpl;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.core.util.DateTimeUtils;
import com.cdsen.power.server.money.dao.po.ConsumptionItemPO;
import com.cdsen.power.server.money.dao.po.ConsumptionPO;
import com.cdsen.power.server.money.dao.repository.ConsumptionItemRepository;
import com.cdsen.power.server.money.dao.repository.ConsumptionRepository;
import com.cdsen.power.server.money.model.ao.*;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.cdsen.power.server.money.model.cons.MoneyError;
import com.cdsen.power.server.money.model.query.ConsumptionQuery;
import com.cdsen.power.server.money.model.vo.ConsumptionItemVO;
import com.cdsen.power.server.money.model.vo.ConsumptionStatisticsVO;
import com.cdsen.power.server.money.model.vo.ConsumptionVO;
import com.cdsen.power.server.money.service.ConsumptionService;
import com.cdsen.power.server.money.transfer.ConsumptionItemTransfer;
import com.cdsen.power.server.money.transfer.ConsumptionTransfer;
import com.cdsen.rabbit.model.ConsumptionCreateDTO;
import com.cdsen.rabbit.model.ConsumptionItemCreateDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();

        ConsumptionPO po = pair.getFirst();
        po.setUserId(userDetails.getUserId());
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
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();
        Long userId = userDetails.getUserId();

        Pageable pageable = iPageRequest.of();
        ConsumptionQuery customParams = iPageRequest.getCustomParams();
        Page<ConsumptionPO> page = consumptionRepository.findAll(spec(userId, customParams), pageable);

        return JsonResult.of(PageResult.of(page.getTotalElements(), ConsumptionTransfer.PO_TO_VO, page.getContent()));
    }

    private Specification<ConsumptionPO> spec(Long userId, ConsumptionQuery customParams) {
        return SpecificationFactory.produce((predicates, root, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("userId").as(Long.class), userId));
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
        });
    }

    @Override
    public JsonResult<ConsumptionUpdateInfoAO> findById(Long id) {
        return consumptionRepository.findById(id)
                .map(po -> JsonResult.of(ConsumptionTransfer.PO_TO_UPDATE_INFO.apply(po)))
                .orElseGet(() -> JsonResult.of(MoneyError.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConsumptionItemVO> deleteItem(Long id) {
        return consumptionItemRepository.findById(id)
                .map(po -> {
                    consumptionItemRepository.delete(po);
                    return JsonResult.of(ConsumptionItemTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(MoneyError.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConsumptionVO> update(ConsumptionUpdateAO ao) {
        return consumptionRepository.findById(ao.getId())
                .map(po -> {
                    po.setTime(ao.getTime());
                    po.setCurrency(ao.getCurrency());
                    consumptionRepository.save(po);
                    return JsonResult.of(ConsumptionTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(MoneyError.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConsumptionItemVO> createItem(ConsumptionItemCreateAO ao) {
        ConsumptionItemPO po = ConsumptionItemTransfer.CREATE_TO_PO.apply(ao);
        consumptionRepository.findById(ao.getConsumptionId())
                .ifPresent(po::setConsumption);
        consumptionItemRepository.save(po);
        return JsonResult.of(ConsumptionItemTransfer.PO_TO_VO.apply(po));
    }

    @Override
    public JsonResult<ConsumptionItemUpdateInfoAO> findItemById(Long id) {
        return consumptionItemRepository.findById(id)
                .map(po -> JsonResult.of(ConsumptionItemTransfer.PO_TO_UPDATE_INFO.apply(po)))
                .orElseGet(() -> JsonResult.of(MoneyError.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ConsumptionItemVO> updateItem(ConsumptionItemUpdateAO ao) {
        return consumptionItemRepository.findById(ao.getId())
                .map(po -> {
                    po.setMoney(ao.getMoney());
                    po.setDescription(ao.getDescription());
                    po.setType(ao.getType());
                    consumptionItemRepository.save(po);
                    return JsonResult.of(ConsumptionItemTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(MoneyError.NOT_FOUND));
    }

    @Override
    public JsonResult<ConsumptionStatisticsVO> statistics(CurrencyType currency, ConsumptionQuery query) {
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();
        Long userId = userDetails.getUserId();
        List<ConsumptionPO> all = consumptionRepository.findAll(spec(userId, query));

        List<ConsumptionItemPO> allItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (ConsumptionPO po : all) {
            List<ConsumptionItemPO> items = po.getItems();
            allItems.addAll(items);
        }
        for (ConsumptionItemPO item : allItems) {
            total = total.add(item.getMoney());
        }

        ConsumptionStatisticsVO statistics = new ConsumptionStatisticsVO();
        List<LocalDate> collect = all.stream().map(c -> c.getTime().toLocalDate()).sorted().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            LocalDate start = collect.get(0);
            LocalDate end = collect.get(collect.size() - 1);
            long days = end.toEpochDay() - start.toEpochDay() + 1;
            statistics.setTotal(currency.getFormat().format(total));
            statistics.setAvgPerDay(currency.getFormat().format(total.divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP)));
            statistics.setTotalDay(days);
        }

        Map<Integer, List<ConsumptionItemPO>> typeMap = allItems.stream().collect(Collectors.groupingBy(ConsumptionItemPO::getType));
        Map<Integer, String> byType = new HashMap<>(typeMap.size());
        for (Map.Entry<Integer, List<ConsumptionItemPO>> entry : typeMap.entrySet()) {
            double sum = entry.getValue().stream().mapToDouble(x -> x.getMoney().doubleValue()).sum();
            byType.put(entry.getKey(), currency.getFormat().format(sum));
        }
        statistics.setByType(byType);
        return JsonResult.of(statistics);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Long userId, ConsumptionCreateDTO dto) {
        String time = dto.getTime();
        String currency = dto.getCurrency();
        List<ConsumptionItemCreateDTO> items = dto.getItems();
        if (StringUtils.isBlank(time) || StringUtils.isBlank(currency) || CollectionUtils.isEmpty(items)) {
            return;
        }

        CurrencyType currencyType;
        try {
            currencyType = CurrencyType.valueOf(currency);
        } catch (Exception e) {
            return;
        }

        LocalDateTime localDateTime;
        try {
            localDateTime = DateTimeUtils.parseLocalDateTime(time, TimeCons.F3);
        } catch (Exception e) {
            return;
        }

        for (ConsumptionItemCreateDTO item : items) {
            BigDecimal money = item.getMoney();
            String description = item.getDescription();
            if (null == money || StringUtils.isBlank(description)) {
                return;
            }
        }

        ConsumptionPO po = new ConsumptionPO();
        po.setUserId(userId);
        po.setCurrency(currencyType);
        po.setTime(localDateTime);
        consumptionRepository.save(po);

        List<ConsumptionItemPO> collect = items.stream().map(item -> {
            ConsumptionItemPO itemPo = new ConsumptionItemPO();
            itemPo.setConsumption(po);
            itemPo.setDescription(item.getDescription());
            itemPo.setMoney(item.getMoney());
            return itemPo;
        }).collect(Collectors.toList());
        consumptionItemRepository.saveAll(collect);
    }

}
