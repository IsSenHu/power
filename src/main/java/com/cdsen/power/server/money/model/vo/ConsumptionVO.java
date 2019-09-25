package com.cdsen.power.server.money.model.vo;

import com.cdsen.power.core.cons.TimeCons;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/12 11:25
 */
@Getter
@Setter
public class ConsumptionVO {

    private Long id;

    /**
     * 消费时间
     */
    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private LocalDateTime time;

    /**
     * 总消费
     */
    private String total;

    /**
     * 货币单位
     */
    private String currency;

    /**
     * 消费明细
     */
    private List<ConsumptionItemVO> items;
}
