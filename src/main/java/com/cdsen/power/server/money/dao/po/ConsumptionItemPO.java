package com.cdsen.power.server.money.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author HuSen
 * create on 2019/9/2 17:08
 */
@Getter
@Setter
@Entity
@Table(name = "tb_consumption_item")
public class ConsumptionItemPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 消费主记录
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumption_id")
    private ConsumptionPO consumption;

    /**
     * 消费金额
     */
    @Column(name = "money", nullable = false)
    private BigDecimal money;

    /**
     * 消费说明
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * 消费类型
     */
    @Column(name = "type", nullable = false)
    private Integer type;
}
