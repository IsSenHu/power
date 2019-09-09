package com.cdsen.power.server.user.service.impl;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.user.dao.po.RolePO;
import com.cdsen.power.server.user.dao.repository.RoleRepository;
import com.cdsen.power.server.user.model.ao.RoleCreateAO;
import com.cdsen.power.server.user.model.ao.RoleUpdateAO;
import com.cdsen.power.server.user.model.vo.RoleVO;
import com.cdsen.power.server.user.service.RoleService;
import com.cdsen.power.server.user.transfer.RoleTransfer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HuSen
 * create on 2019/9/9 10:06
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
}
