package com.cdsen.power.server.physical.exercise.dao.po;

import com.cdsen.power.server.physical.exercise.model.cons.ExerciseType;
import com.cdsen.power.server.physical.exercise.model.cons.ExerciseUnit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author HuSen
 * create on 2019/9/26 10:17
 */
@Getter
@Setter
@Entity
@Table(name = "tb_exercise_item")
public class ExerciseItemPO {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属每天的体育锻炼
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physical_exercise_id")
    private PhysicalExercisePO physicalExercise;

    /**
     * 锻炼种类
     * */
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    /**
     * 计数单位
     * */
    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExerciseUnit unit;

    /**
     * 数量
     * */
    @Column(name = "number", nullable = false)
    private Double number;
}
