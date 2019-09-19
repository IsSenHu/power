package com.cdsen.power.server.user.service.impl;

import com.cdsen.power.core.*;
import com.cdsen.power.core.jpa.FindUtils;
import com.cdsen.power.server.user.dao.po.PermissionPO;
import com.cdsen.power.server.user.dao.po.RolePO;
import com.cdsen.power.server.user.dao.po.RolePermissionPO;
import com.cdsen.power.server.user.dao.repository.PermissionRepository;
import com.cdsen.power.server.user.dao.repository.RolePermissionRepository;
import com.cdsen.power.server.user.dao.repository.RoleRepository;
import com.cdsen.power.server.user.model.ao.MenuPermissionAO;
import com.cdsen.power.server.user.model.ao.RoleCreateAO;
import com.cdsen.power.server.user.model.ao.RoleUpdateAO;
import com.cdsen.power.server.user.model.cons.PermissionType;
import com.cdsen.power.server.user.model.query.RoleQuery;
import com.cdsen.power.server.user.model.vo.RoleVO;
import com.cdsen.power.server.user.service.RoleService;
import com.cdsen.power.server.user.transfer.RoleTransfer;
import com.cdsen.power.server.user.util.TreeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/9 10:06
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private String adminRoleName;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository, RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<RoleVO> create(RoleCreateAO ao) {
        long count = roleRepository.countByName(ao.getName());
        if (count > 0) {
            return JsonResult.of(20001, "角色名名称重复");
        }

        RolePO po = RoleTransfer.CREATE_TO_PO.apply(ao);
        roleRepository.save(po);

        // API权限
        List<Integer> apiPermission = ao.getApiPermission();
        if (!CollectionUtils.isEmpty(apiPermission)) {
            List<RolePermissionPO> collect = apiPermission.stream().map(p -> {
                RolePermissionPO rp = new RolePermissionPO();
                rp.setRoleId(po.getId());
                rp.setPermissionId(p);
                return rp;
            }).collect(Collectors.toList());
            rolePermissionRepository.saveAll(collect);
        }

        // 菜单权限
        List<MenuPermissionAO> menuPermission = ao.getMenuPermission();
        if (!CollectionUtils.isEmpty(menuPermission)) {
            Map<String, PermissionPO> existedMap = permissionRepository.findAllByType(PermissionType.MENU).stream().collect(Collectors.toMap(PermissionPO::getMark, p -> p));
            List<MenuPermissionAO> mayAdd = menuPermission.stream().filter(x -> !existedMap.containsKey(x.getMark())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(mayAdd)) {
                List<PermissionPO> willAdd = mayAdd.stream().map(x -> {
                    PermissionPO p = new PermissionPO();
                    p.setMark(x.getMark());
                    p.setDescription(x.getName());
                    p.setType(PermissionType.MENU);
                    return p;
                }).collect(Collectors.toList());
                permissionRepository.saveAll(willAdd);

                rolePermissionRepository.saveAll(willAdd.stream().map(y -> {
                    RolePermissionPO rp = new RolePermissionPO();
                    rp.setRoleId(po.getId());
                    rp.setPermissionId(y.getId());
                    return rp;
                }).collect(Collectors.toList()));
            }

            rolePermissionRepository.saveAll(menuPermission.stream().filter(x -> existedMap.containsKey(x.getMark()))
                    .map(x -> existedMap.get(x.getMark()))
                    .map(PermissionPO::getId).
                            map(y -> {
                                RolePermissionPO rp = new RolePermissionPO();
                                rp.setRoleId(po.getId());
                                rp.setPermissionId(y);
                                return rp;
                            }).collect(Collectors.toList()));
        }

        return JsonResult.of(RoleTransfer.PO_TO_VO.apply(po));
    }

    @Override
    public JsonResult<RoleVO> findById(Integer id) {
        return roleRepository.findById(id)
                .map(po -> {
                    RoleVO vo = RoleTransfer.PO_TO_VO.apply(po);
                    List<Integer> pidList = rolePermissionRepository.findAllByRoleId(po.getId()).stream().map(RolePermissionPO::getPermissionId).collect(Collectors.toList());
                    Map<PermissionType, List<PermissionPO>> typeMap = permissionRepository.findAllById(pidList).stream().collect(Collectors.groupingBy(PermissionPO::getType));
                    List<PermissionPO> menuPermission = typeMap.get(PermissionType.MENU);
                    if (!CollectionUtils.isEmpty(menuPermission)) {
                        vo.setMenuPermission(menuPermission.stream().map(PermissionPO::getDescription).collect(Collectors.toList()));
                    }
                    List<PermissionPO> apiPermission = typeMap.get(PermissionType.API);
                    if (!CollectionUtils.isEmpty(apiPermission)) {
                        vo.setApiPermission(TreeUtils.buildList(apiPermission));
                    }
                    return JsonResult.of(vo);
                })
                .orElseGet(() -> JsonResult.of(20002, "角色不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<RoleVO> update(RoleUpdateAO ao) {
        Assert.isTrue(!StringUtils.equals(ao.getName(), adminRoleName), "不允许的操作");
        return roleRepository.findById(ao.getId())
                .map(po -> {
                    Assert.isTrue(!StringUtils.equals(po.getName(), adminRoleName), "不允许的操作");
                    if (!StringUtils.equals(ao.getName(), po.getName())) {
                        long count = roleRepository.countByName(ao.getName());
                        if (count > 0) {
                            return JsonResult.of(20001, "角色名名称重复", RoleTransfer.PO_TO_VO.apply(po));
                        }
                    }
                    po.setName(ao.getName());
                    po.setDescription(ao.getDescription());
                    roleRepository.save(po);

                    // TODO 待优化
                    List<Integer> apiPermission = ao.getApiPermission();
                    List<RolePermissionPO> willAdd = new ArrayList<>();
                    rolePermissionRepository.deleteAllByRoleId(po.getId());
                    willAdd(po, apiPermission, willAdd);
                    List<String> marks = ao.getMenuPermission().stream().map(MenuPermissionAO::getMark).collect(Collectors.toList());
                    List<Integer> menuPermission = permissionRepository.findAllByType(PermissionType.MENU)
                            .stream().filter(p -> marks.contains(p.getMark())).map(PermissionPO::getId).collect(Collectors.toList());
                    willAdd(po, menuPermission, willAdd);
                    rolePermissionRepository.saveAll(willAdd);
                    return JsonResult.of(RoleTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(20002, "角色不存在"));
    }

    private void willAdd(RolePO po, List<Integer> apiPermission, List<RolePermissionPO> willAdd) {
        apiPermission.forEach(pid -> {
            RolePermissionPO rp = new RolePermissionPO();
            rp.setRoleId(po.getId());
            rp.setPermissionId(pid);
            willAdd.add(rp);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<RoleVO> delete(Integer id) {
        return roleRepository.findById(id)
                .map(po -> {
                    if (!StringUtils.equals(adminRoleName, po.getName())) {
                        roleRepository.deleteById(id);
                        rolePermissionRepository.deleteAllByRoleId(id);
                        return JsonResult.of(RoleTransfer.PO_TO_VO.apply(po));
                    } else {
                        return JsonResult.of(new RoleVO());
                    }
                })
                .orElseGet(() -> JsonResult.of(20002, "角色不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrRefreshSuperRole(AppProperties.AdminRole adminRole) {
        adminRoleName = adminRole.getName();
        RolePO po = roleRepository.findByName(adminRole.getName());
        if (po == null) {
            po = new RolePO();
            po.setName(adminRole.getName());
        }
        po.setDescription(adminRole.getDescription());
        roleRepository.save(po);

        PermissionPO exclusive = permissionRepository.findByMark(adminRole.getExclusivePermission());
        if (null != exclusive) {
            exclusive.setDescription(adminRole.getExclusivePermissionName());
            exclusive.setType(PermissionType.MENU);
        } else {
            exclusive = new PermissionPO();
            exclusive.setMark(adminRole.getExclusivePermission());
            exclusive.setType(PermissionType.MENU);
            exclusive.setDescription(adminRole.getExclusivePermissionName());
        }
        permissionRepository.save(exclusive);

        List<PermissionPO> permissions = permissionRepository.findAll();

        Integer roleId = po.getId();
        rolePermissionRepository.deleteAllByRoleId(roleId);

        List<RolePermissionPO> rps = permissions.stream().map(p -> {
            RolePermissionPO rolePermissionPO = new RolePermissionPO();
            rolePermissionPO.setRoleId(roleId);
            rolePermissionPO.setPermissionId(p.getId());
            return rolePermissionPO;
        }).collect(Collectors.toList());

        rolePermissionRepository.saveAll(rps);
    }

    @Override
    public JsonResult<PageResult<RoleVO>> page(IPageRequest<RoleQuery> iPageRequest) {
        Pageable pageable = iPageRequest.of();
        Page<RolePO> page = roleRepository.findAll(SpecificationFactory.produce((predicates, rolePORoot, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.notEqual(rolePORoot.get("name").as(String.class), adminRoleName));
            RoleQuery customParams = iPageRequest.getCustomParams();
            if (null != customParams) {
                String name = customParams.getName();
                if (StringUtils.isNotBlank(name)) {
                    predicates.add(criteriaBuilder.like(rolePORoot.get("name").as(String.class), FindUtils.allMatch(name)));
                }
            }
        }), pageable);
        return JsonResult.of(PageResult.of(page.getTotalElements(), RoleTransfer.PO_TO_VO, page.getContent()));
    }
}
