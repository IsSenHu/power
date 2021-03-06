package com.cdsen.power.server.email.dao.repository;

import com.cdsen.power.server.email.dao.po.EmailPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/11/4 14:12
 */
public interface EmailRepository extends JpaRepository<EmailPO, Long>, JpaSpecificationExecutor<EmailPO> {

    List<EmailPO> findAllByUserIdAndHost(Long userId, String host);
}
