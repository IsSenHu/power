package com.cdsen.power.server.money.model.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author HuSen
 * create on 2019/10/25 14:53
 */
@Data
public class IncomeStatisticsVO {

    private String total;

    private Map<Integer, String> byChannel;
}
