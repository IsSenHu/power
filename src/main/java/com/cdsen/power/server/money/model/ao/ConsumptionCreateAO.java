package com.cdsen.power.server.money.model.ao;

import com.cdsen.power.core.cons.TimeCons;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/12 11:16
 */
@Getter
@Setter
public class ConsumptionCreateAO {

    @NotNull
    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private LocalDateTime time;

    @NotNull
    private List<ConsumptionItemAO> items;

    @NotNull
    private CurrencyType currency;
}
