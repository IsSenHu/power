package com.cdsen.power.server.plan.dao.po;

import com.cdsen.power.server.plan.model.cons.PlanType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author HuSen
 * create on 2019/11/29 13:22
 */
@Getter
@Setter
@Entity
@Table(name = "tb_plan")
public class PlanPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "plan_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlanType planType;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "plan")
    private List<PlanItemPO> items;
}
