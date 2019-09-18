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
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
                .map(po -> JsonResult.of(RoleTransfer.PO_TO_VO.apply(po)))
                .orElseGet(() -> JsonResult.of(20002, "角色不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<RoleVO> update(RoleUpdateAO ao) {
        return roleRepository.findById(ao.getId())
                .map(po -> {
                    if (!StringUtils.equals(ao.getName(), po.getName())) {
                        long count = roleRepository.countByName(ao.getName());
                        if (count > 0) {
                            return JsonResult.of(20001, "角色名名称重复", RoleTransfer.PO_TO_VO.apply(po));
                        }
                    }
                    po.setName(ao.getName());
                    po.setDescription(ao.getDescription());
                    roleRepository.save(po);
                    return JsonResult.of(RoleTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(20002, "角色不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<RoleVO> delete(Integer id) {
        return roleRepository.findById(id)
                .map(po -> {
                    roleRepository.deleteById(id);
                    return JsonResult.of(RoleTransfer.PO_TO_VO.apply(po));
                })
                .orElseGet(() -> JsonResult.of(20002, "角色不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrRefreshSuperRole(AppProperties.AdminRole adminRole) {
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
