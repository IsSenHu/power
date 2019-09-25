package com.cdsen.power.server.money.model.ao;

import com.cdsen.power.core.cons.TimeCons;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/12 11:16
 */
@Getter
@Setter
public class ConsumptionUpdateAO {

    @NotNull
    private Long id;

    @NotNull
    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private LocalDateTime time;

    @NotNull
    private CurrencyType currency;
}
