package com.cdsen.power.server.money.model.ao;

import com.cdsen.power.core.cons.TimeCons;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author HuSen
 * create on 2019/9/3 11:17
 */
@Data
public class InComeCreateAO {

    /**
     * 收入
     */
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal inCome;

    /**
     * 收入时间
     */
    @NotNull
    @JsonFormat(pattern = TimeCons.F2, timezone = TimeCons.GMT8)
    private LocalDate time;

    /**
     * 收入说明
     */
    @NotBlank
    private String description;

    /**
     * 货币类型
     */
    @NotNull
    private CurrencyType currency;
}
