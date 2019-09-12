package com.cdsen.power.server.money.dao.po;

import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.cdsen.power.server.money.model.converter.CurrencyTypeConverter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/2 17:07
 */
@Getter
@Setter
@Entity
@Table(name = "tb_consumption")
public class ConsumptionPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 消费时间
     */
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    /**
     * 货币单位
     */
    @Column(name = "currency", nullable = false)
    @Convert(converter = CurrencyTypeConverter.class)
    private CurrencyType currency;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
