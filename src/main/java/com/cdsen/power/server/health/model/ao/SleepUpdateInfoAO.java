package com.cdsen.power.server.health.model.ao;

import com.cdsen.power.core.cons.TimeCons;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/25 10:26
 */
@Getter
@Setter
public class SleepUpdateInfoAO {

    private Long id;

    /**
     * 入睡时间
     */
    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private LocalDateTime sleepTime;

    /**
     * 入睡时长 单位秒
     */
    private String sleepingTime;

    /**
     * 睡眠时长 单位秒
     */
    private String sleepingTotalTime;

    /**
     * 环境噪音 分贝
     */
    private Integer ambientNoise;

    /**
     * 睡眠年龄
     */
    private Integer sleepAge;

    /**
     * 睡眠评分
     */
    private Integer sleepScore;

    /**
     * 睡前状态
     * */
    private Long bedtimeState;

    /**
     * 梦境状态
     * */
    private Long dreamState;
}
