package com.cdsen.power.server.user.dao.repository;

import com.cdsen.power.server.user.dao.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author HuSen
 * create on 2019/9/5 11:28
 */
public interface UserRepository extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO> {

    @Modifying
    @Query("UPDATE UserPO user SET user.isEnabled = true WHERE user.username = :username")
    void enable(@Param("username") String username);

    UserPO findByUsername(String username);

    long countByUsername(String username);
}
