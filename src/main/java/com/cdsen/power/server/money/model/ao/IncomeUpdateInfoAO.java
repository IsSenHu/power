package com.cdsen.power.server.money.model.ao;

import com.cdsen.power.server.money.model.cons.CurrencyType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author HuSen
 * create on 2019/9/12 10:50
 */
@Getter
@Setter
public class IncomeUpdateInfoAO {

    private Long id;

    /**
     * 收入
     */
    private BigDecimal income;

    /**
     * 货币单位
     */
    private CurrencyType currency;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 收入时间
     */
    private LocalDate time;

    /**
     * 收入说明
     */
    private String description;

    /**
     * 收入渠道
     */
    private Integer channel;
}
