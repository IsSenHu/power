package com.cdsen.power.core.cons;
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
    LAYOUT_CHILD("/redirect/:path*", "views/redirect/index", null, null, null, null, null, null),
    LAYOUT("/redirect", "layout/Layout", null, null, true, null, null, Lists.newArrayList(Route.LAYOUT_CHILD)),

    LOGIN("/login", "views/login/index", null, null, true, null, null, null),

    // 用户管理页面
    USER("user", "/views/user/page", "pageUser", null, null, null,
            Meta.of("用户列表", null, Lists.newArrayList(RouteCons.USER_PAGE), null, null),
            null),
    // 角色管理页面ss
    ROLE("role", "/views/role/page", "pageRole", null, null, null,
            Meta.of("角色列表", null, Lists.newArrayList(RouteCons.ROLE_PAGE), null, null),
            null),
    // 用户管理模块
    USER_MANAGE("/UserManage", RouteCons.LAYOUT, null, "/user/page", null, true,
            Meta.of("用户管理", "", Lists.newArrayList(RouteCons.USER_PAGE, RouteCons.ROLE_PAGE), null, null),
            Lists.newArrayList(Route.USER, Route.ROLE));

    private String path;
    private String component;
    private String name;
    private String redirect;
    private Boolean hidden;
    private Boolean alwaysShow;
    private Meta meta;
    private List<Route> children;

    Route(String path, String component, String name, String redirect, Boolean hidden, Boolean alwaysShow, Meta meta, List<Route> children) {
        this.path = path;
        this.component = component;
        this.name = name;
        this.redirect = redirect;
        this.hidden = hidden;
        this.alwaysShow = alwaysShow;
        this.meta = meta;
        this.children = children;
    }

    @Data
    public static class Meta {
        private String title;
        private String icon;
        private List<String> roles;
        private Boolean noCache;
        private Boolean breadcrumb;

        static Meta of(String title, String icon, List<String> roles, Boolean noCache, Boolean breadcrumb) {
            Meta meta = new Meta();
            meta.setTitle(title);
            meta.setIcon(icon);
            meta.setRoles(roles);
            meta.setNoCache(noCache);
            meta.setBreadcrumb(breadcrumb);
            return meta;
        }
    }
}
