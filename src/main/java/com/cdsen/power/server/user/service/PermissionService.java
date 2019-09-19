package com.cdsen.power.server.user.service;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.user.model.query.PermissionQuery;
import com.cdsen.power.server.user.model.vo.PermissionTreeVO;
import com.cdsen.power.server.user.model.vo.PermissionVO;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/10 10:35
 */
public interface PermissionService {

    void createOrRefresh(ConfigurableApplicationContext context);

    /**
     * 分页查询权限
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    JsonResult<PageResult<PermissionVO>> page(IPageRequest<PermissionQuery> iPageRequest);

    /**
     * 获取树形结构的权限
     *
     * @return 树形结果的权限
     */
    JsonResult<List<PermissionTreeVO>> permissionTreeView();
}
