package com.cdsen.power.server.money.dao.repository;

import com.cdsen.power.server.money.dao.po.IncomePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/9/3 9:47
 */
public interface IncomeRepository extends JpaRepository<IncomePO, Long> {
}
