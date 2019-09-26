package com.cdsen.power.server.physical.exercise.model.vo;

import com.cdsen.power.core.cons.TimeCons;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/26 10:36
 */
@Getter
@Setter
public class PhysicalExerciseVO {

    private Long id;

    @JsonFormat(pattern = TimeCons.F2, timezone = TimeCons.GMT8)
    private LocalDate localDate;

    private List<ExerciseItemVO> items;
}
