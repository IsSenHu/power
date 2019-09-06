package com.cdsen.power.server.money.dao.repository;

import com.cdsen.power.server.money.dao.po.ConsumptionPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/9/3 10:10
 */
public interface ConsumptionRepository extends JpaRepository<ConsumptionPO, Long> {
}
