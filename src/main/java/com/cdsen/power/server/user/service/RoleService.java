package com.cdsen.power.server.user.service;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.user.model.ao.RoleCreateAO;
import com.cdsen.power.server.user.model.vo.RoleVO;

/**
 * @author HuSen
 * create on 2019/9/9 10:06
 */
public interface RoleService {

    /**
     * 创建角色
     *
     * @param ao 创建角色数据模型
     * @return 创建结果
     */
    JsonResult<RoleVO> create(RoleCreateAO ao);
}
