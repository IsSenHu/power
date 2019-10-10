package com.cdsen.power.server.apptry.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/10/10 10:22
 */
@Getter
@Setter
@Entity
@Table(name = "tb_try_daily_note")
public class TryDailyNotePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private TryChannelPO tryChannel;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "tryDailyNote")
    private List<TryNotePO> tryNotes;
}
