package com.cdsen.power.server.config.dao.repository;

import com.cdsen.power.server.config.dao.po.ConfigPO;
import com.cdsen.power.server.config.model.cons.ConfigType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/26 17:06
 */
public interface ConfigRepository extends JpaRepository<ConfigPO, Long> {

    boolean existsByTypeAndName(ConfigType type, String name);

    Page<ConfigPO> findAllByType(Pageable pageable, ConfigType configType);

    List<ConfigPO> findAllByUserId(Long userId);
}
