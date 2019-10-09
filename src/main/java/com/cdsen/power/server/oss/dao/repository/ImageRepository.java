package com.cdsen.power.server.oss.dao.repository;

import com.cdsen.power.server.oss.dao.po.ImagePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/10/9 11:01
 */
public interface ImageRepository extends JpaRepository<ImagePO, Long> {
}
