package com.cdsen.power.server.money.model.ao;

import com.cdsen.power.core.cons.TimeCons;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/24 15:51
 */
@Getter
@Setter
public class ConsumptionUpdateInfoAO {

    private Long id;

    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private LocalDateTime time;

    private CurrencyType currency;
}
