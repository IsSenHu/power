package com.cdsen.power.server.user.transfer;

import com.cdsen.power.server.user.dao.po.RolePO;
import com.cdsen.power.server.user.model.ao.RoleCreateAO;
import com.cdsen.power.server.user.model.vo.RoleVO;

import java.util.function.Function;

/**
 * @author HuSen
 * create on 2019/9/9 10:20
 */
public class RoleTransfer {

    public static final Function<RoleCreateAO, RolePO> CREATE_TO_PO = ao -> {
        RolePO po = new RolePO();
        po.setName(ao.getName());
        po.setDescription(ao.getDescription());
        return po;
    };

    public static final Function<RolePO, RoleVO> PO_TO_VO = po -> {
        RoleVO vo = new RoleVO();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setDescription(po.getDescription());
        return vo;
    };
}
