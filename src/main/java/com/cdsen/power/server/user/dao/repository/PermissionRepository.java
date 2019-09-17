package com.cdsen.power.server.user.dao.repository;

import com.cdsen.power.server.user.dao.po.PermissionPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author HuSen
 * create on 2019/9/9 15:09
 */
public interface PermissionRepository extends JpaRepository<PermissionPO, Integer>, JpaSpecificationExecutor<PermissionPO> {
}
