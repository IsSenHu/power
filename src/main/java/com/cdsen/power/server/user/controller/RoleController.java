package com.cdsen.power.server.user.controller;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.user.model.ao.RoleCreateAO;
import com.cdsen.power.server.user.model.vo.RoleVO;
import com.cdsen.power.server.user.service.RoleService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuSen
 * create on 2019/9/9 10:05
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 创建角色
     *
     * @param ao 创建角色数据模型
     * @return 创建结果
     */
    @PutMapping("/create")
    public JsonResult<RoleVO> create(@RequestBody RoleCreateAO ao) {
        return roleService.create(ao);
    }
}
