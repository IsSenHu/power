package com.cdsen.power.server.money.service;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.money.model.ao.InComeCreateAO;
import com.cdsen.power.server.money.model.ao.IncomeUpdateAO;
import com.cdsen.power.server.money.model.ao.IncomeUpdateInfoAO;
import com.cdsen.power.server.money.model.query.InComeQuery;
import com.cdsen.power.server.money.model.vo.IncomeVO;

/**
 * @author HuSen
 * create on 2019/9/3 11:26
 */
public interface IncomeService {

    /**
     * 创建收入
     *
     * @param ao 创建收入数据模型
     * @return 创建结果
     */
    JsonResult<IncomeVO> create(InComeCreateAO ao);

    /**
     * 分页查询收入
     *
     * @param iPageRequest 分页请求
     * @return 分页结果
     */
    JsonResult<PageResult<IncomeVO>> page(IPageRequest<InComeQuery> iPageRequest);

    /**
     * 根据ID删除收入
     *
     * @param id ID
     * @return 删除结果
     */
    JsonResult<IncomeVO> delete(Long id);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 收入信息
     */
    JsonResult<IncomeUpdateInfoAO> findById(Long id);

    /**
     * 修改收入
     *
     * @param ao 修改收入数据模型
     * @return 修改结果
     */
    JsonResult<IncomeVO> update(IncomeUpdateAO ao);
}
