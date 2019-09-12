package com.cdsen.power.server.money.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/9/12 12:46
 */
@Getter
@Setter
public class ConsumptionItemVO {

    private Long id;

    private Long consumptionId;

    /**
     * 消费金额
     */
    private String money;

    /**
     * 货币单位
     */
    private String currency;

    /**
     * 消费说明
     */
    private String description;
}
