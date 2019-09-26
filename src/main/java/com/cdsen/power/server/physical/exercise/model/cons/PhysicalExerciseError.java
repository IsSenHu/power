package com.cdsen.power.server.physical.exercise.model.cons;

import com.cdsen.power.core.Error;
import lombok.Getter;

/**
 * @author HuSen
 * create on 2019/9/26 11:16
 */
@Getter
public enum PhysicalExerciseError implements Error {
    NOT_FOUND(50001, "没有该条数据");

    private Integer code;
    private String error;

    PhysicalExerciseError(Integer code, String error) {
        this.code = code;
        this.error = error;
    }
}
