package com.cdsen.power.core.cons;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/9 16:30
 */
@Getter
public enum Route {
    LAYOUT_CHILD("/redirect/:path*", "views/redirect/index", null, null, null, null, null, null, false),
    LAYOUT("/redirect", "layout/Layout", null, null, true, null, null, Lists.newArrayList(Route.LAYOUT_CHILD), true),

    LOGIN("/login", "views/login/index", null, null, true, null, null, null, true),
    AUTH_REDIRECT("/auth-redirect", "views/login/auth-redirect", null, null, true, null, null, null, true),
    R404("/404", "views/error-page/404", null, null, true, null, null, null, true),
    R401("/401", "views/error-page/401", null, null, true, null, null, null, true),

    DASHBOARD("dashboard", "views/dashboard/index", "Dashboard", null, null, null,
            Meta.of("仪表板", "dashboard", null, null, true), null, false),
    EMPTY("", "layout/Layout", null, "dashboard", null, null, null, Lists.newArrayList(Route.DASHBOARD), true),

    GUIDE_INDEX("index", "views/guide/index", "Guide", null, null, null,
            Meta.of("导航", "guide", null, true, null), null, false),
    GUIDE("/guide", "layout/Layout", null, "/guide/index", null, null, null, Lists.newArrayList(Route.GUIDE_INDEX), true),

    // 用户列表
    USER("user", "/views/user/page", "pageUser", null, null, null,
            Meta.of("用户列表", null, Lists.newArrayList(RouteCons.USER_PAGE), null, null),
            null, false),
    // 角色列表
    ROLE("role", "/views/role/page", "pageRole", null, null, null,
            Meta.of("角色列表", null, Lists.newArrayList(RouteCons.ROLE_PAGE), null, null),
            null, false),
    PERMISSION("permission", "/views/permission/page", "pagePermission", null, null, null,
            Meta.of("权限列表", null, Lists.newArrayList(RouteCons.PERMISSION_PAGE), null, null),
            null, false),
    // 用户管理
    USER_MANAGE("/UserManage", RouteCons.LAYOUT, null, "/user/page", null, true,
            Meta.of("用户管理", "peoples", Lists.newArrayList(RouteCons.USER_PAGE, RouteCons.ROLE_PAGE, RouteCons.PERMISSION_PAGE), null, null),
            Lists.newArrayList(Route.USER, Route.ROLE, Route.PERMISSION), true);

    private String path;
    private String component;
    private String name;
    private String redirect;
    private Boolean hidden;
    private Boolean alwaysShow;
    private Meta meta;
    private List<Route> children;
    private Boolean isParent;

    Route(String path, String component, String name, String redirect, Boolean hidden, Boolean alwaysShow, Meta meta, List<Route> children, Boolean isParent) {
        this.path = path;
        this.component = component;
        this.name = name;
        this.redirect = redirect;
        this.hidden = hidden;
        this.alwaysShow = alwaysShow;
        this.meta = meta;
        this.children = children;
        this.isParent = isParent;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta {
        private String title;
        private String icon;
        private List<String> roles;
        private Boolean noCache;
        private Boolean breadcrumb;
        private Boolean affix;

        static Meta of(String title, String icon, List<String> roles, Boolean noCache, Boolean affix) {
            Meta meta = new Meta();
            meta.setTitle(title);
            meta.setIcon(icon);
            meta.setRoles(roles);
            meta.setNoCache(noCache);
            meta.setBreadcrumb(null);
            meta.setAffix(affix);
            return meta;
        }
    }
}
