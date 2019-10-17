package com.cdsen.power.server.physical.exercise.service.impl;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.SpecificationFactory;
import com.cdsen.power.core.security.model.UserDetailsImpl;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.server.physical.exercise.dao.po.ExerciseItemPO;
import com.cdsen.power.server.physical.exercise.dao.po.PhysicalExercisePO;
import com.cdsen.power.server.physical.exercise.dao.repository.ExerciseItemRepository;
import com.cdsen.power.server.physical.exercise.dao.repository.PhysicalExerciseRepository;
import com.cdsen.power.server.physical.exercise.model.ao.*;
import com.cdsen.power.server.physical.exercise.model.cons.PhysicalExerciseError;
import com.cdsen.power.server.physical.exercise.model.query.PhysicalExerciseQuery;
import com.cdsen.power.server.physical.exercise.model.vo.PhysicalExerciseVO;
import com.cdsen.power.server.physical.exercise.service.PhysicalExerciseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

import static com.cdsen.power.server.physical.exercise.transfer.ExerciseItemTransfer.BATCH_CREATE_TO_PO;
import static com.cdsen.power.server.physical.exercise.transfer.ExerciseItemTransfer.BATCH_UPDATE_TO_PO;
import static com.cdsen.power.server.physical.exercise.transfer.PhysicalExerciseTransfer.CREATE_TO_PO;
import static com.cdsen.power.server.physical.exercise.transfer.PhysicalExerciseTransfer.PO_TO_VO;
import static com.cdsen.power.server.physical.exercise.transfer.PhysicalExerciseTransfer.PO_TO_UPDATE_INFO;

/**
 * @author HuSen
 * create on 2019/9/26 10:34
 */
@Service
public class PhysicalExerciseServiceImpl implements PhysicalExerciseService {

    private final PhysicalExerciseRepository physicalExerciseRepository;
    private final ExerciseItemRepository exerciseItemRepository;

    public PhysicalExerciseServiceImpl(PhysicalExerciseRepository physicalExerciseRepository, ExerciseItemRepository exerciseItemRepository) {
        this.physicalExerciseRepository = physicalExerciseRepository;
        this.exerciseItemRepository = exerciseItemRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<PhysicalExerciseVO> create(PhysicalExerciseCreateAO ao) {
        Long userId = SecurityUtils.currentUserDetails().getUserId();
        PhysicalExercisePO physicalExercise = physicalExerciseRepository.findByUserIdAndTime(userId, ao.getTime());
        if (physicalExercise == null) {
            physicalExercise = CREATE_TO_PO.apply(ao);
            physicalExercise.setUserId(userId);
            physicalExerciseRepository.save(physicalExercise);
        }
        List<ExerciseItemPO> items = BATCH_CREATE_TO_PO.apply(physicalExercise, ao.getItems());
        if (!CollectionUtils.isEmpty(items)) {
            exerciseItemRepository.saveAll(items);
            if (physicalExercise.getItems() == null) {
                physicalExercise.setItems(items);
            } else {
                physicalExercise.getItems().addAll(items);
            }
        }
        return JsonResult.of(PO_TO_VO.apply(physicalExercise));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<PhysicalExerciseVO> delete(Long id) {
        return physicalExerciseRepository.findById(id)
                .map(po -> {
                    physicalExerciseRepository.delete(po);
                    return JsonResult.of(PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(PhysicalExerciseError.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<PhysicalExerciseVO> update(PhysicalExerciseUpdateAO ao) {
        return physicalExerciseRepository.findById(ao.getId())
                .map(po -> {
                    po.setTime(ao.getTime());
                    physicalExerciseRepository.save(po);
                    List<ExerciseItemCreateAO> creates = ao.getCreates();
                    if (!CollectionUtils.isEmpty(creates)) {
                        List<ExerciseItemPO> apply = BATCH_CREATE_TO_PO.apply(po, creates);
                        exerciseItemRepository.saveAll(apply);
                    }
                    List<ExerciseItemUpdateAO> updates = ao.getUpdates();
                    if (!CollectionUtils.isEmpty(updates)) {
                        exerciseItemRepository.saveAll(BATCH_UPDATE_TO_PO.apply(po, updates));
                    }
                    List<Long> deletes = ao.getDeletes();
                    if (!CollectionUtils.isEmpty(deletes)) {
                        exerciseItemRepository.deleteAllByIdIn(deletes);
                    }
                    return JsonResult.of(PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(PhysicalExerciseError.NOT_FOUND));
    }

    @Override
    public JsonResult<PhysicalExerciseUpdateInfoAO> findById(Long id) {
        return physicalExerciseRepository.findById(id)
                .map(po -> JsonResult.of(PO_TO_UPDATE_INFO.apply(po)))
                .orElseGet(() -> JsonResult.of(PhysicalExerciseError.NOT_FOUND));
    }

    @Override
    public JsonResult<PageResult<PhysicalExerciseVO>> page(IPageRequest<PhysicalExerciseQuery> iPageRequest) {
        Pageable pageable = iPageRequest.of();
        UserDetailsImpl userDetails = SecurityUtils.currentUserDetails();
        Page<PhysicalExercisePO> pageInfo = physicalExerciseRepository.findAll(SpecificationFactory.produce((predicates, root, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("userId").as(Long.class), userDetails.getUserId()));
            PhysicalExerciseQuery customParams = iPageRequest.getCustomParams();
            if (null != customParams) {
                LocalDate start = customParams.getStart();
                if (null != start) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("time").as(LocalDate.class), start));
                }
                LocalDate end = customParams.getEnd();
                if (null != end) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("time").as(LocalDate.class), end));
                }
            }
        }), pageable);
        return JsonResult.of(PageResult.of(pageInfo.getTotalElements(), PO_TO_VO, pageInfo.getContent()));
    }
}
