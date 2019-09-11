package com.cdsen.power.core.web;

import com.cdsen.power.core.cons.Route;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/11 11:22
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteVO {

    private String path;
    private String component;
    private String name;
    private String redirect;
    private Boolean hidden;
    private Boolean alwaysShow;
    private Route.Meta meta;
    private List<RouteVO> children;
}
