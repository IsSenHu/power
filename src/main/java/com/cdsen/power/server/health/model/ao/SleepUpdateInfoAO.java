package com.cdsen.power.server.health.model.ao;

import com.cdsen.power.core.cons.TimeCons;
import com.cdsen.power.server.health.model.cons.BedtimeState;
import com.cdsen.power.server.health.model.cons.DreamState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/25 10:26
 */
@Getter
@Setter
public class SleepUpdateInfoAO {

    @NotNull
    private Long id;

    /**
     * 入睡时间
     */
    @NotNull
    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private LocalDateTime sleepTime;

    /**
     * 入睡时长 单位秒
     */
    @NotNull
    private Integer sleepingTime;

    /**
     * 睡眠时长 单位秒
     */
    @NotNull
    private Integer sleepingTotalTime;

    /**
     * 环境噪音 分贝
     */
    @NotNull
    private Integer ambientNoise;

    /**
     * 睡眠年龄
     */
    @NotNull
    private Integer sleepAge;

    /**
     * 睡眠评分
     */
    @NotNull
    private Integer sleepScore;

    /**
     * 睡前状态
     * */
    @NotNull
    private BedtimeState bedtimeState;

    /**
     * 梦境状态
     * */
    @NotNull
    private DreamState dreamState;
}
