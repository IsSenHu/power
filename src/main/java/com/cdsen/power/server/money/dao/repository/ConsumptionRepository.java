package com.cdsen.power.server.money.dao.repository;

import com.cdsen.power.server.money.dao.po.ConsumptionPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * @author HuSen
 * create on 2019/9/3 10:10
 */
public interface ConsumptionRepository extends JpaRepository<ConsumptionPO, Long>, JpaSpecificationExecutor<ConsumptionPO> {

    @Override
    @EntityGraph("ConsumptionPO.items")
    Page<ConsumptionPO> findAll(Specification<ConsumptionPO> spec, Pageable pageable);

    @Override
    @EntityGraph("ConsumptionPO.items")
    Optional<ConsumptionPO> findById(Long id);

    @Override
    @EntityGraph("ConsumptionPO.items")
    List<ConsumptionPO> findAll(Specification<ConsumptionPO> spec);
}
