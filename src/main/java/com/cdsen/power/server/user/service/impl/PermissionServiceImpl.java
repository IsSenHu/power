package com.cdsen.power.server.user.service.impl;

import com.cdsen.power.core.cons.Route;
import com.cdsen.power.core.security.annotation.Permission;
import com.cdsen.power.core.security.annotation.SecurityModule;
import com.cdsen.power.server.user.dao.po.PermissionPO;
import com.cdsen.power.server.user.dao.repository.PermissionRepository;
import com.cdsen.power.server.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/10 10:35
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrRefresh(ConfigurableApplicationContext context) {
        List<PermissionPO> willUpdate = new ArrayList<>();
        List<PermissionPO> willAdd = new ArrayList<>();
        List<PermissionPO> old = permissionRepository.findAll();
        Map<String, PermissionPO> oldMap = old.stream().collect(Collectors.toMap(PermissionPO::getMark, po -> po));

        // route权限
        Route[] routes = Route.values();
        for (Route route : routes) {
            List<Route> children = route.getChildren();
            if (null == children && !CollectionUtils.isEmpty(route.getMeta().getRoles())) {
                String mark = route.getMeta().getRoles().get(0);
                if (oldMap.containsKey(mark)) {
                    PermissionPO po = oldMap.remove(mark);
                    po.setDescription(route.getMeta().getTitle());
                    willUpdate.add(po);
                } else {
                    PermissionPO po = new PermissionPO();
                    po.setMark(mark);
                    po.setDescription(route.getMeta().getTitle());
                    willAdd.add(po);
                }
            }
        }

        // 接口权限
        Map<String, Permission> helper = new HashMap<>();
        Map<String, Object> securityModuleMap = context.getBeansWithAnnotation(SecurityModule.class);
        securityModuleMap.forEach((name, bean) -> {
            SecurityModule securityModule = AnnotationUtils.findAnnotation(bean.getClass(), SecurityModule.class);
            log.info("发现模块:{}-{}", securityModule, name);
            Method[] methods = bean.getClass().getDeclaredMethods();
            Method.setAccessible(methods, true);
            for (Method method : methods) {
                PreAuthorize preAuthorize = AnnotationUtils.findAnnotation(method, PreAuthorize.class);
                Permission permission = AnnotationUtils.findAnnotation(method, Permission.class);
                if (null == preAuthorize || null == permission) {
                    continue;
                }
                String value = preAuthorize.value();
                helper.putIfAbsent(value, permission);
            }
        });
        helper.forEach((authorize, permission) -> {
            String mark = StringUtils.substringBetween(authorize, "hasAuthority('", "')");
            if (oldMap.containsKey(mark)) {
                PermissionPO po = oldMap.remove(mark);
                po.setDescription(permission.value());
                willUpdate.add(po);
            } else {
                PermissionPO po = new PermissionPO();
                po.setMark(mark);
                po.setDescription(permission.value());
                willAdd.add(po);
            }
        });

        permissionRepository.deleteInBatch(oldMap.values());
        permissionRepository.saveAll(willUpdate);
        permissionRepository.saveAll(willAdd);
    }
}
