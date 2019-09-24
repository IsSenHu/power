package com.cdsen.power.server.money.model.vo;

import com.cdsen.power.core.cons.TimeCons;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author HuSen
 * create on 2019/9/3 11:25
 */
@Data
public class IncomeVO {

    private Long id;

    /**
     * 收入
     */
    private String income;

    /**
     * 收入时间
     */
    @JsonFormat(pattern = TimeCons.F2, timezone = TimeCons.GMT8)
    private LocalDate time;

    /**
     * 收入说明
     */
    private String description;

    /**
     * 货币单位
     */
    private String currency;
}
