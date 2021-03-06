package com.cdsen.power.server.physical.exercise.model.ao;

import com.cdsen.power.server.physical.exercise.model.cons.ExerciseType;
import com.cdsen.power.server.physical.exercise.model.cons.ExerciseUnit;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author HuSen
 * create on 2019/9/26 10:46
 */
@Getter
@Setter
public class ExerciseItemCreateAO {

    /**
     * 锻炼种类
     * */
    @NotNull
    private ExerciseType type;

    /**
     * 计数单位
     * */
    @NotNull
    private ExerciseUnit unit;

    /**
     * 数量
     * */
    @NotNull
    @Min(0)
    private Double number;
}
