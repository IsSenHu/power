package com.cdsen.power.server.health.dao.repository;

import com.cdsen.power.server.health.dao.po.SleepPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author HuSen
 * create on 2019/9/25 10:11
 */
public interface SleepRepository extends JpaRepository<SleepPO, Long>, JpaSpecificationExecutor<SleepPO> {
}
