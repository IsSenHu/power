package com.cdsen.power.server.user.dao.repository;

import com.cdsen.power.server.user.dao.po.PermissionPO;
import com.cdsen.power.server.user.model.cons.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/9 15:09
 */
public interface PermissionRepository extends JpaRepository<PermissionPO, Integer>, JpaSpecificationExecutor<PermissionPO> {

    List<PermissionPO> findAllByType(PermissionType type);

    PermissionPO findByMark(String mark);
}
