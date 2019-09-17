package com.cdsen.power.server.user.transfer;

import com.cdsen.power.server.user.dao.po.PermissionPO;
import com.cdsen.power.server.user.model.vo.PermissionVO;

import java.util.function.Function;

/**
 * @author HuSen
 * create on 2019/9/17 11:31
 */
public class PermissionTransfer {

    public static final Function<PermissionPO, PermissionVO> PO_TO_VO = po -> {
        PermissionVO vo = new PermissionVO();
        vo.setId(po.getId());
        vo.setMark(po.getMark());
        vo.setDescription(po.getDescription());
        return vo;
    };
}
