package com.cdsen.power.server.health.model.query;

import com.cdsen.power.core.cons.TimeCons;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author HuSen
 * create on 2019/9/25 11:45
 */
@Getter
@Setter
public class SleepQuery {

    @JsonFormat(pattern = TimeCons.F2, timezone = TimeCons.GMT8)
    private LocalDate start;

    @JsonFormat(pattern = TimeCons.F2, timezone = TimeCons.GMT8)
    private LocalDate end;
}
