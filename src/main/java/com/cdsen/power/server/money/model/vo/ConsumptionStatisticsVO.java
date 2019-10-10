package com.cdsen.power.server.money.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/9/30 9:50
 */
@Getter
@Setter
public class ConsumptionStatisticsVO {

    private Long totalDay;
    private String total;
    private String avgPerDay;
}
