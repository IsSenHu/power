package com.cdsen.power.server.health.model.query;

import com.cdsen.power.server.health.model.cons.BedtimeState;
import com.cdsen.power.server.health.model.cons.DreamState;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/9/25 11:45
 */
@Getter
@Setter
public class SleepQuery {

    /**
     * 入睡时长 单位秒
     */
    private Integer sleepingTime;

    /**
     * 睡眠时长 单位秒
     */
    private Integer sleepingTotalTime;

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
    private BedtimeState bedtimeState;

    /**
     * 梦境状态
     * */
    private DreamState dreamState;
}
