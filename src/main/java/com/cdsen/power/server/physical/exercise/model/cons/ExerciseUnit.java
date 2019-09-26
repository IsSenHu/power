package com.cdsen.power.server.physical.exercise.model.cons;

import lombok.Getter;

import java.text.NumberFormat;

/**
 * @author HuSen
 * create on 2019/9/26 10:23
 */
@Getter
public enum ExerciseUnit {
    ;

    private String value;
    private NumberFormat format;

    ExerciseUnit(String value, NumberFormat format) {
        this.value = value;
        this.format = format;
    }
}
