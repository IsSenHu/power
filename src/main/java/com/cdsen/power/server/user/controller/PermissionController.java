package com.cdsen.power.server.user.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.security.annotation.Permission;
import com.cdsen.power.core.security.annotation.SecurityModule;
import com.cdsen.power.server.user.model.cons.PreAuthorizes;
import com.cdsen.power.server.user.model.query.PermissionQuery;
import com.cdsen.power.server.user.model.vo.PermissionVO;
import com.cdsen.power.server.user.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuSen
 * create on 2019/9/17 11:15
 */
@RestController
@RequestMapping("/api/permission")
@SecurityModule(name = "权限")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 分页查询权限
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    @PreAuthorize(PreAuthorizes.Permission.PAGE)
    @Permission("分页查询权限")
    @PostMapping("/page")
    public JsonResult<PageResult<PermissionVO>> page(@RequestBody IPageRequest<PermissionQuery> iPageRequest) {
        return permissionService.page(iPageRequest);
    }
}
