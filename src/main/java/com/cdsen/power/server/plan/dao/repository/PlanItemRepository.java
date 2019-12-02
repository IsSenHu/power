package com.cdsen.power.server.plan.dao.repository;

import com.cdsen.power.server.plan.dao.po.PlanItemPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/12/2 14:17
 */
public interface PlanItemRepository extends JpaRepository<PlanItemPO, Long> {
}
