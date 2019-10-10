package com.cdsen.power.server.apptry.dao.repository;

import com.cdsen.power.server.apptry.dao.po.TryDailyNotePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/10/10 11:01
 */
public interface TryDailyNoteRepository extends JpaRepository<TryDailyNotePO, Long> {
}
