package com.cdsen.power.server.plan.dao.repository;

import com.cdsen.power.server.plan.dao.po.PlanPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/12/2 14:17
 */
public interface PlanRepository extends JpaRepository<PlanPO, Long> {
}
