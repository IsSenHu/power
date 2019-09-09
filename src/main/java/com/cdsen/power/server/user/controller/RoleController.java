package com.cdsen.power.server.user.controller;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.user.model.ao.RoleCreateAO;
import com.cdsen.power.server.user.model.ao.RoleUpdateAO;
import com.cdsen.power.server.user.model.vo.RoleVO;
import com.cdsen.power.server.user.service.RoleService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据ID查询角色
     *
     * @param id ID
     * @return 角色
     */
    @GetMapping("/{id}")
    public JsonResult<RoleVO> findById(@PathVariable Integer id) {
        return roleService.findById(id);
    }

    /**
     * 更新角色
     *
     * @param ao 角色更新数据模型
     * @return 更新结果
     */
    @PostMapping
    public JsonResult<RoleVO> update(@RequestBody RoleUpdateAO ao) {
        return roleService.update(ao);
    }

    /**
     * 根据ID删除角色
     *
     * @param id ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<RoleVO> delete(@PathVariable Integer id) {
        return roleService.delete(id);
    }
}
