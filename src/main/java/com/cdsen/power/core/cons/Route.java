package com.cdsen.power.core.cons;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/9 16:30
 */
@Getter
public enum Route {
    ;

    private String path;
    private String component;
    private String redirect;
    private Boolean hidden;
    private Boolean alwaysShow;
    private Meta meta;
    private List<Route> children;

    Route(String path, String component, String redirect, Boolean hidden, Boolean alwaysShow, Meta meta, List<Route> children) {
        this.path = path;
        this.component = component;
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
    }
}
