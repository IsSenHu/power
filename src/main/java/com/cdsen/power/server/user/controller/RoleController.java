package com.cdsen.power.server.user.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.security.annotation.Permission;
import com.cdsen.power.core.security.annotation.SecurityModule;
import com.cdsen.power.server.user.model.ao.RoleCreateAO;
import com.cdsen.power.server.user.model.ao.RoleUpdateAO;
import com.cdsen.power.server.user.model.cons.PreAuthorizes;
import com.cdsen.power.server.user.model.query.RoleQuery;
import com.cdsen.power.server.user.model.vo.RoleVO;
import com.cdsen.power.server.user.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * create on 2019/9/9 10:05
 */
@RestController
@SecurityModule(name = "角色")
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
    @PreAuthorize(PreAuthorizes.Role.CREATE)
    @Permission("创建角色")
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
    @PreAuthorize(PreAuthorizes.Role.FIND_BY_ID)
    @Permission("根据ID查询角色")
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
    @PreAuthorize(PreAuthorizes.Role.UPDATE)
    @Permission("更新角色")
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
    @PreAuthorize(PreAuthorizes.Role.DELETE)
    @Permission("删除角色")
    @DeleteMapping("/{id}")
    public JsonResult<RoleVO> delete(@PathVariable Integer id) {
        return roleService.delete(id);
    }

    /**
     * 分页查询角色
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    @PreAuthorize(PreAuthorizes.Role.QUERY)
    @Permission("分页查询角色")
    @PostMapping("/page")
    public JsonResult<PageResult<RoleVO>> page(@RequestBody IPageRequest<RoleQuery> iPageRequest) {
        return roleService.page(iPageRequest);
    }
}
