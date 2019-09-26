package com.cdsen.power.server.config.dao.repository;

import com.cdsen.power.server.config.dao.po.ConfigPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/9/26 17:06
 */
public interface ConfigRepository extends JpaRepository<ConfigPO, Long> {
}
