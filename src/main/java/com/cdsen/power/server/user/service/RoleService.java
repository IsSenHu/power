package com.cdsen.power.server.user.service;

import com.cdsen.power.core.AppProperties;
import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.user.model.ao.RoleCreateAO;
import com.cdsen.power.server.user.model.ao.RoleUpdateAO;
import com.cdsen.power.server.user.model.query.RoleQuery;
import com.cdsen.power.server.user.model.vo.RoleVO;

/**
 * @author HuSen
 * create on 2019/9/9 10:06
 */
public interface RoleService {

    /**
     * 创建角色
     *
     * @param ao 创建角色数据模型
     * @return 创建结果
     */
    JsonResult<RoleVO> create(RoleCreateAO ao);

    /**
     * 根据ID查询角色
     *
     * @param id ID
     * @return 角色
     */
    JsonResult<RoleVO> findById(Integer id);

    /**
     * 更新角色
     *
     * @param ao 角色更新数据模型
     * @return 更新结果
     */
    JsonResult<RoleVO> update(RoleUpdateAO ao);

    /**
     * 根据ID删除角色
     *
     * @param id ID
     * @return 删除结果
     */
    JsonResult<RoleVO> delete(Integer id);

    /**
     * 创建或刷新超级角色
     *
     * @param adminRole 超级角色
     */
    void createOrRefreshSuperRole(AppProperties.AdminRole adminRole);

    /**
     * 分页查询角色
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    JsonResult<PageResult<RoleVO>> page(IPageRequest<RoleQuery> iPageRequest);
}
