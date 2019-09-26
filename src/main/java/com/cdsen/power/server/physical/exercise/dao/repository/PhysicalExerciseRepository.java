package com.cdsen.power.server.physical.exercise.dao.repository;

import com.cdsen.power.server.physical.exercise.dao.po.PhysicalExercisePO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author HuSen
 * create on 2019/9/26 10:29
 */
public interface PhysicalExerciseRepository extends JpaRepository<PhysicalExercisePO, Long>, JpaSpecificationExecutor<PhysicalExercisePO> {

    @Override
    @EntityGraph("PhysicalExercisePO.items")
    Optional<PhysicalExercisePO> findById(Long aLong);

    @Override
    @EntityGraph("PhysicalExercisePO.items")
    Page<PhysicalExercisePO> findAll(Specification<PhysicalExercisePO> spec, Pageable pageable);

    @EntityGraph("PhysicalExercisePO.items")
    PhysicalExercisePO findByUserIdAndTime(Long userId, LocalDate time);
}
