package com.cdsen.power.server.apptry.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author HuSen
 * create on 2019/10/10 10:29
 */
@Getter
@Setter
@Entity
@Table(name = "tb_try_note")
public class TryNotePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_name", nullable = false)
    private String appName;

    @Column(name = "money")
    private BigDecimal money;

    @ManyToOne(fetch = FetchType.LAZY)
    private TryDailyNotePO tryDailyNote;
}
