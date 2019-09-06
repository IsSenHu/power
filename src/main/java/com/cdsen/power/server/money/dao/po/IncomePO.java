package com.cdsen.power.server.money.dao.po;

import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.cdsen.power.server.money.model.converter.CurrencyTypeConverter;
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
public class IncomePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 收入
     */
    @Column(name = "inCome", nullable = false)
    private BigDecimal inCome;

    /**
     * 货币单位
     */
    @Column(name = "currency", nullable = false)
    @Convert(converter = CurrencyTypeConverter.class)
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
