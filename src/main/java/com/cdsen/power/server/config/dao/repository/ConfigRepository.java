package com.cdsen.power.server.config.dao.repository;

import com.cdsen.power.server.config.dao.po.ConfigPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/9/17 17:10
 */
public interface ConfigRepository extends JpaRepository<ConfigPO, Integer> {
}
