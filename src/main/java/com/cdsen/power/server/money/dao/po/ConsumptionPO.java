package com.cdsen.power.server.money.dao.po;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
