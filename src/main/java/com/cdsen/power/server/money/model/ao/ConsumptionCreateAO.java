package com.cdsen.power.server.money.model.ao;

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
    private LocalDateTime time;

    @NotNull
    private List<ConsumptionItemAO> items;
}
