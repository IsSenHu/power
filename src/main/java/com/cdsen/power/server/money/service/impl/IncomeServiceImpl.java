package com.cdsen.power.server.money.service.impl;

import com.cdsen.power.core.*;
import com.cdsen.power.core.cons.TimeCons;
import com.cdsen.power.core.security.model.UserDetailsImpl;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.core.util.DateTimeUtils;
import com.cdsen.power.server.money.dao.po.IncomePO;
import com.cdsen.power.server.money.dao.repository.IncomeRepository;
import com.cdsen.power.server.money.model.ao.InComeCreateAO;
import com.cdsen.power.server.money.model.ao.IncomeUpdateAO;
import com.cdsen.power.server.money.model.ao.IncomeUpdateInfoAO;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.cdsen.power.server.money.model.cons.MoneyError;
import com.cdsen.power.server.money.model.query.InComeQuery;
import com.cdsen.power.server.money.model.vo.IncomeStatisticsVO;
import com.cdsen.power.server.money.model.vo.IncomeVO;
import com.cdsen.power.server.money.service.IncomeService;
import com.cdsen.power.server.money.transfer.IncomeTransfer;
import com.cdsen.rabbit.model.InComeCreateDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();

        IncomePO po = IncomeTransfer.CREATE_TO_PO.apply(ao);
        po.setUserId(userDetails.getUserId());
        incomeRepository.save(po);

        return JsonResult.of(IncomeTransfer.PO_TO_VO.apply(po));
    }

    @Override
    public JsonResult<PageResult<IncomeVO>> page(IPageRequest<InComeQuery> iPageRequest) {
        // TODO 后期session 自动组装在参数里面 可以直接获取
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();
        Pageable pageable = iPageRequest.of();
        Page<IncomePO> page = incomeRepository.findAll(spec(true, userDetails.getUserId(), iPageRequest.getCustomParams()), pageable);

        return JsonResult.of(PageResult.of(page.getTotalElements(), IncomeTransfer.PO_TO_VO, page.getContent()));
    }

    private Specification<IncomePO> spec(boolean isPaging, Long userId, InComeQuery inComeQuery) {
        return SpecificationFactory.produce((predicates, root, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("userId").as(Long.class), userId));

            LocalDate start = null;
            LocalDate end = null;
            LocalDate now = LocalDate.now();
            if (inComeQuery != null) {
                start = inComeQuery.getStart();
                end = inComeQuery.getEnd();
            }

            if (!isPaging && (Objects.isNull(inComeQuery) || Objects.isNull(start))) {
                start = DateTimeUtils.getFirstDayOfMonth(now);
            }

            if (!isPaging && (Objects.isNull(inComeQuery) || Objects.isNull(end))) {
                end = DateTimeUtils.getLastDayOfMonth(now);
            }

            if (Objects.nonNull(start)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("time").as(LocalDate.class), start));
            }

            if (Objects.nonNull(end)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("time").as(LocalDate.class), end));
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<IncomeVO> delete(Long id) {
        return incomeRepository.findById(id)
                .map(po -> {
                    incomeRepository.delete(po);
                    return JsonResult.of(IncomeTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(JsonResult::success);
    }

    @Override
    public JsonResult<IncomeUpdateInfoAO> findById(Long id) {
        return incomeRepository.findById(id)
                .map(po -> JsonResult.of(IncomeTransfer.PO_TO_UPDATE_INFO.apply(po)))
                .orElseGet(() -> JsonResult.of(MoneyError.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<IncomeVO> update(IncomeUpdateAO ao) {
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();
        Optional<IncomePO> byId = incomeRepository.findById(ao.getId());
        if (byId.isPresent()) {
            IncomePO po = byId.get();
            po.setTime(ao.getTime());
            po.setDescription(ao.getDescription());
            po.setCurrency(ao.getCurrency());
            po.setIncome(ao.getIncome());
            po.setUserId(userDetails.getUserId());
            po.setChannel(ao.getChannel());
            incomeRepository.save(po);
            return JsonResult.of(IncomeTransfer.PO_TO_VO.apply(po));
        } else {
            return JsonResult.of(MoneyError.NOT_FOUND);
        }
    }

    @Override
    public JsonResult<IncomeStatisticsVO> statistics(CurrencyType currency, InComeQuery query) {
        Long userId = SecurityUtils.currentUserDetails().getUserId();
        List<IncomePO> incomes = incomeRepository.findAll(spec(false, userId, query));
        double totalSum = incomes.stream().mapToDouble(x -> x.getIncome().doubleValue()).sum();

        IncomeStatisticsVO vo = new IncomeStatisticsVO();
        vo.setTotal(currency.getFormat().format(totalSum));

        Map<Integer, List<IncomePO>> channelMap = incomes.stream().collect(Collectors.groupingBy(IncomePO::getChannel));
        Map<Integer, String> byChannel = new HashMap<>(channelMap.size());
        for (Map.Entry<Integer, List<IncomePO>> entry : channelMap.entrySet()) {
            double sum = entry.getValue().stream().mapToDouble(x -> x.getIncome().doubleValue()).sum();
            byChannel.put(entry.getKey(), currency.getFormat().format(sum));
        }
        vo.setByChannel(byChannel);
        return JsonResult.of(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Long userId, InComeCreateDTO data) {
        BigDecimal income = data.getIncome();
        if (null == income) {
            return;
        }
        String currency = data.getCurrency();
        if (StringUtils.isBlank(currency)) {
            return;
        }
        CurrencyType currencyType;
        try {
            currencyType = CurrencyType.valueOf(currency);
        } catch (Exception ignored) {
            return;
        }
        String description = data.getDescription();
        if (StringUtils.isBlank(description)) {
            return;
        }
        String time = data.getTime();
        if (StringUtils.isBlank(time)) {
            return;
        }
        LocalDate localDate;
        try {
            localDate = DateTimeUtils.parseLocalDate(time, TimeCons.F4);
        } catch (Exception e) {
            return;
        }
        Integer channel = data.getChannel();
        if (null == channel) {
            return;
        }
        IncomePO po = new IncomePO();
        po.setUserId(userId);
        po.setIncome(income);
        po.setCurrency(currencyType);
        po.setChannel(channel);
        po.setDescription(description);
        po.setTime(localDate);
        incomeRepository.save(po);
    }
}
