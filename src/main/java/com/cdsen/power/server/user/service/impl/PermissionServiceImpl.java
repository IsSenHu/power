package com.cdsen.power.server.user.service.impl;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.SpecificationFactory;
import com.cdsen.power.core.jpa.FindUtils;
import com.cdsen.power.core.security.annotation.Permission;
import com.cdsen.power.core.security.annotation.SecurityModule;
import com.cdsen.power.server.user.dao.po.PermissionPO;
import com.cdsen.power.server.user.dao.repository.PermissionRepository;
import com.cdsen.power.server.user.model.cons.PermissionType;
import com.cdsen.power.server.user.model.query.PermissionQuery;
import com.cdsen.power.server.user.model.vo.PermissionVO;
import com.cdsen.power.server.user.service.PermissionService;
import com.cdsen.power.server.user.transfer.PermissionTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<PermissionPO> old = permissionRepository.findAllByType(PermissionType.API);
        Map<String, PermissionPO> oldMap = old.stream().collect(Collectors.toMap(PermissionPO::getMark, po -> po));

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
                po.setType(PermissionType.API);
                willUpdate.add(po);
            } else {
                PermissionPO po = new PermissionPO();
                po.setMark(mark);
                po.setDescription(permission.value());
                po.setType(PermissionType.API);
                willAdd.add(po);
            }
        });

        permissionRepository.deleteInBatch(oldMap.values());
        permissionRepository.saveAll(willUpdate);
        permissionRepository.saveAll(willAdd);
    }

    @Override
    public JsonResult<PageResult<PermissionVO>> page(IPageRequest<PermissionQuery> iPageRequest) {
        Pageable pageable = iPageRequest.of();
        Page<PermissionPO> page = permissionRepository.findAll(SpecificationFactory.produce((predicates, permissionPORoot, criteriaBuilder) -> {
            PermissionQuery customParams = iPageRequest.getCustomParams();
            if (null != customParams) {
                String mark = customParams.getMark();
                if (StringUtils.isNotBlank(mark)) {
                    predicates.add(criteriaBuilder.like(permissionPORoot.get("mark").as(String.class), FindUtils.allMatch(mark)));
                }
            }
        }), pageable);
        return JsonResult.of(PageResult.of(page.getTotalElements(), PermissionTransfer.PO_TO_VO, page.getContent()));
    }
}
