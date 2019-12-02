package com.cdsen.power.server.plan.dao.po;

import com.cdsen.power.server.plan.model.cons.PlanType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/12/2 14:25
 */
@Getter
@Setter
@Entity
@Table(name = "tb_finish_plan_record")
public class FinishPlanRecordPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Column(name = "plan_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlanType planType;

    @Column(name = "finish_time", nullable = false)
    private LocalDateTime finishTime;

    @Column(name = "remark", nullable = false)
    private String remark;
}
