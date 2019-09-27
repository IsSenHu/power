package com.cdsen.power.server.article.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.article.model.ao.ArticleCreateAO;
import com.cdsen.power.server.article.model.ao.ArticleUpdateAO;
import com.cdsen.power.server.article.model.query.ArticleQuery;
import com.cdsen.power.server.article.model.vo.ArticleVO;
import com.cdsen.power.server.article.service.ArticleService;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuSen
 * create on 2019/9/27 14:58
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 创建新文章
     *
     * @param ao 创建新文章数据模型
     * @return 创建结果
     */
    @PutMapping
    public JsonResult<ArticleVO> create(@RequestBody ArticleCreateAO ao) {
        return articleService.create(ao);
    }

    /**
     * 修改已有文章
     *
     * @param ao 修改已有文章文章数据模型
     * @return 修改结果
     */
    @PostMapping
    public JsonResult<ArticleVO> update(@RequestBody ArticleUpdateAO ao) {
        return articleService.update(ao);
    }

    /**
     * 根据ID删除文章
     *
     * @param id ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<ArticleVO> delete(@PathVariable Long id) {
        return articleService.delete(id);
    }

    /**
     * 分页查询文章
     *
     * @param iPageRequest 分页参数
     * @return 分页结果
     */
    @PostMapping("/page")
    public JsonResult<PageResult<ArticleVO>> page(@RequestBody IPageRequest<ArticleQuery> iPageRequest) {
        return articleService.page(iPageRequest);
    }

    /**
     * 获取指定文章的HtmlVO
     *
     * @param id 指定文章的ID
     * @return ArticleVO
     */
    @GetMapping("/html/{id}")
    public JsonResult<ArticleVO> html(@PathVariable Long id) {
        return articleService.html(id);
    }

    /**
     * 获取指定文章的ContentVO
     *
     * @param id 指定文章的ID
     * @return ArticleVO
     */
    @GetMapping("/content/{id}")
    public JsonResult<ArticleVO> content(@PathVariable long id) {
        return articleService.content(id);
    }
}
