package com.cdsen.power.server.user.dao.repository;

import com.cdsen.power.server.user.dao.po.RolePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author HuSen
 * create on 2019/9/9 9:46
 */
public interface RoleRepository extends JpaRepository<RolePO, Integer>, JpaSpecificationExecutor<RolePO> {

    long countByName(String name);

    RolePO findByName(String name);
}
