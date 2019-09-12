package com.cdsen.power.server.money.service.impl;

import com.cdsen.power.core.*;
import com.cdsen.power.core.security.model.Session;
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
        Session session = SecurityUtils.currentSession();
        if (session == null) {
            return JsonResult.of(CommonError.NOT_LOGIN);
        }

        IncomePO po = IncomeTransfer.CREATE_TO_PO.apply(ao);
        po.setUserId(session.getUserId());
        incomeRepository.save(po);

        return JsonResult.of(IncomeTransfer.PO_TO_VO.apply(po));
    }

    @Override
    public JsonResult<PageResult<IncomeVO>> page(IPageRequest<InComeQuery> iPageRequest) {
        Pageable pageable = iPageRequest.of();
        Page<IncomePO> page = incomeRepository.findAll(SpecificationFactory.produce((predicates, root, criteriaBuilder) -> {
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
        return incomeRepository.findById(ao.getId())
                .map(po -> {
                    po.setTime(ao.getTime());
                    po.setInCome(ao.getInCome());
                    po.setCurrency(ao.getCurrency());
                    po.setDescription(ao.getDescription());
                    incomeRepository.save(po);
                    return JsonResult.of(IncomeTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(MoneyError.NOT_FOUND));
    }
}
