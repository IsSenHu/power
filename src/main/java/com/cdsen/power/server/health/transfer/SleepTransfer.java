package com.cdsen.power.server.health.transfer;

import com.cdsen.power.server.health.dao.po.SleepPO;
import com.cdsen.power.server.health.model.ao.SleepCreateAO;
import com.cdsen.power.server.health.model.ao.SleepUpdateInfoAO;
import com.cdsen.power.server.health.model.vo.SleepVO;

import java.util.function.Function;

/**
 * @author HuSen
 * create on 2019/9/25 11:16
 */
public class SleepTransfer {

    public static final Function<SleepCreateAO, SleepPO> CREATE_TO_PO = ao -> {
        SleepPO po = new SleepPO();
        po.setSleepTime(ao.getSleepTime());
        po.setSleepingTime(ao.getSleepingTime());
        po.setSleepingTotalTime(ao.getSleepingTotalTime());
        po.setAmbientNoise(ao.getAmbientNoise());
        po.setSleepAge(ao.getSleepAge());
        po.setSleepScore(ao.getSleepScore());
        po.setBedtimeState(ao.getBedtimeState());
        po.setDreamState(ao.getDreamState());
        return po;
    };

    public static final Function<SleepPO, SleepVO> PO_TO_VO = po -> {
        SleepVO vo = new SleepVO();
        vo.setId(po.getId());
        vo.setUserId(po.getUserId());
        vo.setSleepTime(po.getSleepTime());
        Integer sleepingTime = po.getSleepingTime();
        vo.setSleepingTime(sleepingTime / 3600 + "小时 " + sleepingTime % 3600 / 60 + "分");
        Integer sleepingTotalTime = po.getSleepingTotalTime();
        vo.setSleepingTotalTime(sleepingTotalTime / 3600 + "小时 " + sleepingTotalTime % 3600 / 60 + "分");
        vo.setAmbientNoise(po.getAmbientNoise());
        vo.setSleepAge(po.getSleepAge());
        vo.setSleepScore(po.getSleepScore());
        vo.setBedtimeState(po.getBedtimeState());
        vo.setDreamState(po.getDreamState());
        return vo;
    };

    public static final Function<SleepPO, SleepUpdateInfoAO> PO_TO_UPDATE_INFO = po -> {
        SleepUpdateInfoAO ao = new SleepUpdateInfoAO();
        ao.setId(po.getId());
        ao.setSleepTime(po.getSleepTime());
        Integer sleepingTime = po.getSleepingTime();
        ao.setSleepingTime(sleepingTime / 3600 + ":" + sleepingTime % 3600 / 60);
        Integer sleepingTotalTime = po.getSleepingTotalTime();
        ao.setSleepingTotalTime(sleepingTotalTime / 3600 + ":" + sleepingTotalTime % 3600 / 60);
        ao.setAmbientNoise(po.getAmbientNoise());
        ao.setSleepAge(po.getSleepAge());
        ao.setSleepScore(po.getSleepScore());
        ao.setBedtimeState(po.getBedtimeState());
        ao.setDreamState(po.getDreamState());
        return ao;
    };
}
