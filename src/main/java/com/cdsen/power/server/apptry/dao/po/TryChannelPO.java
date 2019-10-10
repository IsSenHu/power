package com.cdsen.power.server.apptry.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/10/10 10:17
 */
@Getter
@Setter
@Entity
@Table(name = "tb_try_channel")
public class TryChannelPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "display_name", nullable = false, unique = true)
    private String displayName;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "tryChannel")
    private List<TryDailyNotePO> tryDailyNotes;
}
