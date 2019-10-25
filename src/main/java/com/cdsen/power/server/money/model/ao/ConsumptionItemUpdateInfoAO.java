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
public class ConsumptionItemUpdateInfoAO {

    private Long id;

    /**
     * 消费金额
     */
    private BigDecimal money;

    /**
     * 消费说明
     */
    private String description;

    /**
     * 消费类型
     */
    private Integer type;
}
