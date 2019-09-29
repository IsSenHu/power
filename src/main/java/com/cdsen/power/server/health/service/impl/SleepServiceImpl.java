package com.cdsen.power.server.health.service.impl;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.SpecificationFactory;
import com.cdsen.power.core.security.model.Session;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.server.health.dao.po.SleepPO;
import com.cdsen.power.server.health.dao.repository.SleepRepository;
import com.cdsen.power.server.health.model.ao.SleepCreateAO;
import com.cdsen.power.server.health.model.ao.SleepUpdateAO;
import com.cdsen.power.server.health.model.ao.SleepUpdateInfoAO;
import com.cdsen.power.server.health.model.cons.SleepError;
import com.cdsen.power.server.health.model.query.SleepQuery;
import com.cdsen.power.server.health.model.vo.SleepVO;
import com.cdsen.power.server.health.service.SleepService;
import com.cdsen.power.server.health.transfer.SleepTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author HuSen
 * create on 2019/9/25 10:26
 */
@Service
public class SleepServiceImpl implements SleepService {

    private final SleepRepository sleepRepository;

    public SleepServiceImpl(SleepRepository sleepRepository) {
        this.sleepRepository = sleepRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<SleepVO> create(SleepCreateAO ao) {
        Session session = SecurityUtils.currentSession();
        SleepPO po = SleepTransfer.CREATE_TO_PO.apply(ao);
        po.setUserId(session.getUserId());
        sleepRepository.save(po);
        return JsonResult.of(SleepTransfer.PO_TO_VO.apply(po));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<SleepVO> delete(Long id) {
        return sleepRepository.findById(id)
                .map(po -> {
                    sleepRepository.delete(po);
                    return JsonResult.of(SleepTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(SleepError.NOT_FOUNT));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<SleepVO> update(SleepUpdateAO ao) {
        return sleepRepository.findById(ao.getId())
                .map(po -> {
                    po.setAmbientNoise(ao.getAmbientNoise());
                    po.setDreamState(ao.getDreamState());
                    po.setBedtimeState(ao.getBedtimeState());
                    po.setSleepAge(ao.getSleepAge());
                    po.setSleepingTotalTime(ao.getSleepingTotalTime());
                    po.setSleepingTime(ao.getSleepingTime());
                    po.setSleepScore(ao.getSleepScore());
                    po.setSleepTime(ao.getSleepTime());
                    sleepRepository.save(po);
                    return JsonResult.of(SleepTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(SleepError.NOT_FOUNT));
    }

    @Override
    public JsonResult<SleepUpdateInfoAO> findById(Long id) {
        return sleepRepository.findById(id)
                .map(po -> JsonResult.of(SleepTransfer.PO_TO_UPDATE_INFO.apply(po)))
                .orElseGet(() -> JsonResult.of(SleepError.NOT_FOUNT));
    }

    @Override
    public JsonResult<PageResult<SleepVO>> page(IPageRequest<SleepQuery> iPageRequest) {
        Session session = SecurityUtils.currentSession();
        Pageable pageable = iPageRequest.of();
        Page<SleepPO> pageInfo = sleepRepository.findAll(SpecificationFactory.produce((predicates, sleepPORoot, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(sleepPORoot.get("userId").as(Long.class), session.getUserId()));
            SleepQuery customParams = iPageRequest.getCustomParams();
            if (null != customParams) {
                LocalDate start = customParams.getStart();
                if (null != start) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(sleepPORoot.get("sleepTime").as(LocalDate.class), start));
                }
                LocalDate end = customParams.getEnd();
                if (null != end) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(sleepPORoot.get("sleepTime").as(LocalDate.class), end));
                }
            }
        }), pageable);
        return JsonResult.of(PageResult.of(pageInfo.getTotalElements(), SleepTransfer.PO_TO_VO, pageInfo.getContent()));
    }
}
