package com.cdsen.power.server.money.model.query;

import com.cdsen.power.server.money.model.cons.CurrencyType;
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
    private LocalDateTime start;
    private LocalDateTime end;
    private CurrencyType currency;
}
