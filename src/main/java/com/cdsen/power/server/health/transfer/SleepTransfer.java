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
        vo.setSleepingTime(po.getSleepingTime());
        vo.setSleepingTotalTime(po.getSleepingTotalTime());
        vo.setAmbientNoise(po.getAmbientNoise());
        vo.setSleepAge(po.getSleepAge());
        vo.setSleepScore(po.getSleepScore());
        vo.setBedtimeState(po.getBedtimeState().getValue());
        vo.setDreamState(po.getDreamState().getValue());
        return vo;
    };

    public static final Function<SleepPO, SleepUpdateInfoAO> PO_TO_UPDATE_INFO = po -> {
        SleepUpdateInfoAO ao = new SleepUpdateInfoAO();
        ao.setId(po.getId());
        ao.setSleepTime(po.getSleepTime());
        ao.setSleepingTime(po.getSleepingTime());
        ao.setSleepingTotalTime(po.getSleepingTotalTime());
        ao.setAmbientNoise(po.getAmbientNoise());
        ao.setSleepAge(po.getSleepAge());
        ao.setSleepScore(po.getSleepScore());
        ao.setBedtimeState(po.getBedtimeState());
        ao.setDreamState(po.getDreamState());
        return ao;
    };
}
