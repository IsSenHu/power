package com.cdsen.power.server.apptry.dao.repository;

import com.cdsen.power.server.apptry.dao.po.TryNotePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/10/10 10:59
 */
public interface TryNoteRepository extends JpaRepository<TryNotePO, Long> {
}
