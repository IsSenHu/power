package com.cdsen.power.server.money.model.query;

import com.cdsen.power.core.cons.TimeCons;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/12 14:31
 */
@Getter
@Setter
public class ConsumptionQuery {

    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private LocalDateTime start;

    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private LocalDateTime end;
}
