package com.cdsen.power.server.money.dao.repository;

import com.cdsen.power.server.money.dao.po.ConsumptionItemPO;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author HuSen
 * create on 2019/9/3 10:13
 */
public interface ConsumptionItemRepository extends JpaRepository<ConsumptionItemPO, Long> {
}
