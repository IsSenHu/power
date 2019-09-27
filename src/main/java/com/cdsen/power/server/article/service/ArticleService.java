package com.cdsen.power.server.article.service;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.article.model.ao.ArticleCreateAO;
import com.cdsen.power.server.article.model.ao.ArticleUpdateAO;
import com.cdsen.power.server.article.model.query.ArticleQuery;
import com.cdsen.power.server.article.model.vo.ArticleVO;

/**
 * @author HuSen
 * create on 2019/9/27 14:59
 */
public interface ArticleService {

    /**
     * 创建新文章
     *
     * @param ao 创建新文章数据模型
     * @return 创建结果
     */
    JsonResult<ArticleVO> create(ArticleCreateAO ao);

    /**
     * 修改已有文章
     *
     * @param ao 修改已有文章文章数据模型
     * @return 修改结果
     */
    JsonResult<ArticleVO> update(ArticleUpdateAO ao);

    /**
     * 根据ID删除文章
     *
     * @param id ID
     * @return 删除结果
     */
    JsonResult<ArticleVO> delete(Long id);

    /**
     * 分页查询文章
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    JsonResult<PageResult<ArticleVO>> page(IPageRequest<ArticleQuery> iPageRequest);

    /**
     * 获取指定文章的Html文本
     *
     * @param id 指定文章的ID
     * @return ArticleVO
     */
    JsonResult<ArticleVO> html(Long id);

    /**
     * 获取指定文章的ContentVO
     *
     * @param id 指定文章的ID
     * @return ArticleVO
     */
    JsonResult<ArticleVO> content(long id);
}
