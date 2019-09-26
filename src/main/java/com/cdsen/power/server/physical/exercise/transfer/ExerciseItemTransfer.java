package com.cdsen.power.server.physical.exercise.transfer;

import com.cdsen.power.server.physical.exercise.dao.po.ExerciseItemPO;
import com.cdsen.power.server.physical.exercise.dao.po.PhysicalExercisePO;
import com.cdsen.power.server.physical.exercise.model.ao.ExerciseItemCreateAO;
import com.cdsen.power.server.physical.exercise.model.ao.ExerciseItemUpdateAO;
import com.cdsen.power.server.physical.exercise.model.ao.ExerciseItemUpdateInfoAO;
import com.cdsen.power.server.physical.exercise.model.vo.ExerciseItemVO;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/26 10:56
 */
public class ExerciseItemTransfer {

    public static final BiFunction<PhysicalExercisePO, List<ExerciseItemCreateAO>, List<ExerciseItemPO>> BATCH_CREATE_TO_PO = (p, aos) -> aos.stream().map(ao -> {
        ExerciseItemPO po = new ExerciseItemPO();
        po.setPhysicalExercise(p);
        po.setType(ao.getType());
        po.setUnit(ao.getUnit());
        po.setNumber(ao.getNumber());
        return po;
    }).collect(Collectors.toList());

    public static final BiFunction<PhysicalExercisePO, List<ExerciseItemUpdateAO>, List<ExerciseItemPO>> BATCH_UPDATE_TO_PO = (p, aos) -> aos.stream().map(ao -> {
        ExerciseItemPO po = new ExerciseItemPO();
        po.setId(ao.getId());
        po.setNumber(ao.getNumber());
        po.setType(ao.getType());
        po.setUnit(ao.getUnit());
        po.setPhysicalExercise(p);
        return po;
    }).collect(Collectors.toList());

    public static final Function<ExerciseItemPO, ExerciseItemVO> PO_TO_VO = po -> {
        ExerciseItemVO vo = new ExerciseItemVO();
        vo.setId(po.getId());
        if (null != po.getPhysicalExercise()) {
            vo.setPhysicalExerciseId(po.getPhysicalExercise().getId());
        }
        vo.setType(po.getType().getName());
        vo.setUnit(po.getUnit().getValue());
        vo.setNumber(po.getUnit().getFormat().format(po.getNumber()));
        return vo;
    };

    public static final Function<ExerciseItemPO, ExerciseItemUpdateInfoAO> PO_TO_UPDATE_INFO = item -> {
        ExerciseItemUpdateInfoAO itemAo = new ExerciseItemUpdateInfoAO();
        itemAo.setId(item.getId());
        itemAo.setNumber(item.getNumber());
        itemAo.setType(item.getType());
        itemAo.setUnit(item.getUnit());
        return itemAo;
    };
}
