package com.cdsen.power.server.money.dao.po;

import com.cdsen.power.core.jpa.BasePO;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author HuSen
 * create on 2019/9/2 17:07
 */
@Getter
@Setter
@Entity
@Table(name = "tb_in_come")
public class IncomePO extends BasePO<Long, Long> {

    /**
     * 收入
     */
    @Column(name = "income", nullable = false)
    private BigDecimal income;

    /**
     * 货币单位
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currency;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 收入时间
     */
    @Column(name = "time", nullable = false)
    private LocalDate time;

    /**
     * 收入说明
     */
    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
