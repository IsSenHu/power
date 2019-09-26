package com.cdsen.power.server.physical.exercise.model.cons;

import lombok.Getter;

/**
 * @author HuSen
 * create on 2019/9/26 10:23
 */
@Getter
public enum ExerciseType {
    ;

    private String name;

    ExerciseType(String name) {
        this.name = name;
    }
}
