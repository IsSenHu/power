package com.cdsen.power.core.listener;

import com.cdsen.power.core.AppProperties;
import com.cdsen.power.server.user.service.PermissionService;
import com.cdsen.power.server.user.service.RoleService;
import com.cdsen.power.server.user.service.UserService;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author HuSen
 * create on 2019/9/10 10:32
 */
@Component
public class StartedListener {

    private final PermissionService permissionService;
    private final RoleService roleService;
    private final AppProperties appProperties;
    private final UserService userService;

    public StartedListener(PermissionService permissionService, RoleService roleService, AppProperties appProperties, UserService userService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.appProperties = appProperties;
        this.userService = userService;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onStarted(ApplicationStartedEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        // 创建或刷新权限
        permissionService.createOrRefresh(context);
        // 创建或刷新超级角色
        roleService.createOrRefreshSuperRole(appProperties.getAdminRole());
        // 创建或刷新超级管理员
        userService.createOrRefreshSuperAdmin(appProperties.getAdminRole().getName(), appProperties.getAdmin());
    }
}
