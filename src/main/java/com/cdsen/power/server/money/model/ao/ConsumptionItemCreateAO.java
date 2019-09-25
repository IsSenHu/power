package com.cdsen.power.server.money.model.ao;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author HuSen
 * create on 2019/9/12 11:17
 */
@Getter
@Setter
public class ConsumptionItemCreateAO {

    @NotNull
    private Long consumptionId;

    /**
     * 消费金额
     */
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal money;

    /**
     * 消费说明
     */
    @NotBlank
    private String description;
}
