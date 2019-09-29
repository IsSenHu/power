package com.cdsen.power.server.health.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/25 9:48
 */
@Getter
@Setter
@Entity
@Table(name = "tb_sleep")
public class SleepPO {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 入睡时间
     */
    @Column(name = "sleep_time", nullable = false)
    private LocalDateTime sleepTime;

    /**
     * 入睡时长 单位秒
     */
    @Column(name = "sleeping_time", nullable = false)
    private Integer sleepingTime;

    /**
     * 睡眠时长 单位秒
     */
    @Column(name = "sleeping_total_time", nullable = false)
    private Integer sleepingTotalTime;

    /**
     * 环境噪音 分贝
     */
    @Column(name = "ambient_noise", nullable = false)
    private Integer ambientNoise;

    /**
     * 睡眠年龄
     */
    @Column(name = "sleep_age", nullable = false)
    private Integer sleepAge;

    /**
     * 睡眠评分
     */
    @Column(name = "sleep_score", nullable = false)
    private Integer sleepScore;

    /**
     * 睡前状态
     * */
    @Column(name = "bedtime_state", nullable = false)
    private Long bedtimeState;

    /**
     * 梦境状态
     * */
    @Column(name = "dream_state", nullable = false)
    private Long dreamState;
}
