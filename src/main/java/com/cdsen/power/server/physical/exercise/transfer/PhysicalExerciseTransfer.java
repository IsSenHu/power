package com.cdsen.power.server.physical.exercise.transfer;

import com.cdsen.power.server.physical.exercise.dao.po.ExerciseItemPO;
import com.cdsen.power.server.physical.exercise.dao.po.PhysicalExercisePO;
import com.cdsen.power.server.physical.exercise.model.ao.PhysicalExerciseCreateAO;
import com.cdsen.power.server.physical.exercise.model.ao.PhysicalExerciseUpdateInfoAO;
import com.cdsen.power.server.physical.exercise.model.vo.PhysicalExerciseVO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/26 10:51
 */
public class PhysicalExerciseTransfer {

    public static final Function<PhysicalExerciseCreateAO, PhysicalExercisePO> CREATE_TO_PO = ao -> {
        PhysicalExercisePO po = new PhysicalExercisePO();
        po.setTime(ao.getTime());
        return po;
    };

    public static final Function<PhysicalExercisePO, PhysicalExerciseVO> PO_TO_VO = po -> {
        PhysicalExerciseVO vo = new PhysicalExerciseVO();
        vo.setId(po.getId());
        vo.setLocalDate(po.getTime());
        List<ExerciseItemPO> items = po.getItems();
        if (!CollectionUtils.isEmpty(items)) {
            vo.setItems(items.stream().map(ExerciseItemTransfer.PO_TO_VO).collect(Collectors.toList()));
        }
        return vo;
    };

    public static final Function<PhysicalExercisePO, PhysicalExerciseUpdateInfoAO> PO_TO_UPDATE_INFO = po -> {
        PhysicalExerciseUpdateInfoAO ao = new PhysicalExerciseUpdateInfoAO();
        ao.setId(po.getId());
        ao.setTime(po.getTime());
        if (!CollectionUtils.isEmpty(po.getItems())) {
            ao.setItems(po.getItems().stream().map(ExerciseItemTransfer.PO_TO_UPDATE_INFO).collect(Collectors.toList()));
        }
        return ao;
    };
}
