package com.cdsen.power.server.physical.exercise.model.ao;

import com.cdsen.power.core.cons.TimeCons;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/26 10:35
 */
@Getter
@Setter
public class PhysicalExerciseCreateAO {

    @NotNull
    @JsonFormat(pattern = TimeCons.F2, timezone = TimeCons.GMT8)
    private LocalDate time;

    @NotNull
    private List<ExerciseItemCreateAO> items;
}
