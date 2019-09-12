package com.cdsen.power.server.money.dao.po;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Column(name = "consumption_id", nullable = false)
    private Long consumptionId;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
