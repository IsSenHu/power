package com.cdsen.power.server.physical.exercise.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/9/26 10:48
 */
@Getter
@Setter
public class ExerciseItemVO {

    private Long id;

    private Long physicalExerciseId;

    private String type;

    private String unit;

    private String number;
}
