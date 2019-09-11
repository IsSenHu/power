package com.cdsen.power.core.web;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.cons.Route;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuSen
 * create on 2019/9/11 11:24
 */
@RestController
@RequestMapping("/api/route")
public class RouteController {
    private static final String CONSTANT_ROUTES = "constantRoutes";
    private static final String ASYNC_ROUTES = "asyncRoutes";

    private Map<String, List<RouteVO>> cache;

    @PostConstruct
    public void init() {
        Route[] routes = Route.values();
        cache = new HashMap<>(2);
        List<RouteVO> constantRoutes = new ArrayList<>();
        List<RouteVO> asyncRoutes = new ArrayList<>();
        for (Route route : routes) {
            if (!route.getIsParent()) {
                continue;
            }
            List<Route> children = route.getChildren();
            Route.Meta meta = route.getMeta();
            RouteVO vo = build(route, children);
            if (meta == null) {
                constantRoutes.add(vo);
            } else {
                asyncRoutes.add(vo);
            }
        }
        cache.put(CONSTANT_ROUTES, constantRoutes);
        cache.put(ASYNC_ROUTES, asyncRoutes);
    }

    @GetMapping
    public JsonResult<Map<String, List<RouteVO>>> routes() {
        return JsonResult.of(cache);
    }

    private RouteVO build(Route route, List<Route> children) {
        RouteVO vo = new RouteVO();
        vo.setPath(route.getPath());
        vo.setComponent(route.getComponent());
        vo.setName(route.getName());
        vo.setRedirect(route.getRedirect());
        vo.setHidden(route.getHidden());
        vo.setAlwaysShow(route.getAlwaysShow());
        vo.setMeta(route.getMeta());
        if (CollectionUtils.isEmpty(children)) {
            vo.setChildren(null);
        } else {
            List<RouteVO> childList = new ArrayList<>();
            for (Route child : children) {
                childList.add(build(child, child.getChildren()));
            }
            vo.setChildren(childList);
        }
        return vo;
    }
}
