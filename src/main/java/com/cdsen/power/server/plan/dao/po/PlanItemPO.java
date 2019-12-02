package com.cdsen.power.server.plan.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author HuSen
 * create on 2019/11/29 14:08
 */
@Getter
@Setter
@Entity
@Table(name = "tb_plan_item")
public class PlanItemPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private PlanPO plan;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "target", nullable = false)
    private Double target;

    @Column(name = "unit", nullable = false)
    private String unit;
}
