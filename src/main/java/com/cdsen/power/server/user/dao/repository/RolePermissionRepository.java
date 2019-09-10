package com.cdsen.power.server.user.dao.repository;

import com.cdsen.power.server.user.dao.po.RolePermissionPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/9 15:12
 */
public interface RolePermissionRepository extends JpaRepository<RolePermissionPO, Integer> {
    void deleteAllByRoleId(Integer roleId);
}
