package com.cdsen.power.server.physical.exercise.dao.repository;

import com.cdsen.power.server.physical.exercise.dao.po.ExerciseItemPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/26 10:31
 */
public interface ExerciseItemRepository extends JpaRepository<ExerciseItemPO, Long>, JpaSpecificationExecutor<ExerciseItemPO> {

    void deleteAllByIdIn(List<Long> ids);
}
