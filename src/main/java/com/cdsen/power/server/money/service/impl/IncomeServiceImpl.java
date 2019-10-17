package com.cdsen.power.server.money.service.impl;

import com.cdsen.power.core.*;
import com.cdsen.power.core.security.model.UserDetailsImpl;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.server.money.dao.po.IncomePO;
import com.cdsen.power.server.money.dao.repository.IncomeRepository;
import com.cdsen.power.server.money.model.ao.InComeCreateAO;
import com.cdsen.power.server.money.model.ao.IncomeUpdateAO;
import com.cdsen.power.server.money.model.ao.IncomeUpdateInfoAO;
import com.cdsen.power.server.money.model.cons.MoneyError;
import com.cdsen.power.server.money.model.query.InComeQuery;
import com.cdsen.power.server.money.model.vo.IncomeVO;
import com.cdsen.power.server.money.service.IncomeService;
import com.cdsen.power.server.money.transfer.IncomeTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

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
        Page<IncomePO> page = incomeRepository.findAll(SpecificationFactory.produce((predicates, root, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("userId").as(Long.class), userDetails.getUserId()));

            InComeQuery customParams = iPageRequest.getCustomParams();
            if (customParams == null) {
                return;
            }

            LocalDate start = customParams.getStart();
            if (Objects.nonNull(start)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("time").as(LocalDate.class), start));
            }

            LocalDate end = customParams.getEnd();
            if (Objects.nonNull(end)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("time").as(LocalDate.class), end));
            }
        }), pageable);

        return JsonResult.of(PageResult.of(page.getTotalElements(), IncomeTransfer.PO_TO_VO, page.getContent()));
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
            incomeRepository.save(po);
            return JsonResult.of(IncomeTransfer.PO_TO_VO.apply(po));
        } else {
            return JsonResult.of(MoneyError.NOT_FOUND);
        }
    }
}
