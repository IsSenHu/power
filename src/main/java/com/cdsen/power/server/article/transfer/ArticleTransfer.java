package com.cdsen.power.server.article.transfer;

import com.cdsen.power.server.article.dao.po.ArticlePO;
import com.cdsen.power.server.article.model.vo.ArticleVO;

import java.util.function.Function;

/**
 * @author HuSen
 * create on 2019/9/27 15:16
 */
public class ArticleTransfer {

    public static final Function<ArticlePO, ArticleVO> PO_TO_SIMPLE_VO = po -> {
        ArticleVO vo = new ArticleVO();
        vo.setId(po.getId());
        vo.setTitle(po.getTitle());
        vo.setTime(po.getTime());
        vo.setType(po.getType());
        return vo;
    };

    public static final Function<ArticlePO, ArticleVO> PO_TO_VO = po -> {
        ArticleVO vo = new ArticleVO();
        vo.setId(po.getId());
        vo.setTitle(po.getTitle());
        vo.setTime(po.getTime());
        vo.setContent(po.getContent());
        vo.setHtml(po.getHtml());
        return vo;
    };

    public static final Function<ArticlePO, ArticleVO> PO_TO_HTML_VO = po -> {
        ArticleVO vo = new ArticleVO();
        vo.setId(po.getId());
        vo.setTitle(po.getTitle());
        vo.setTime(po.getTime());
        vo.setHtml(po.getHtml());
        return vo;
    };

    public static final Function<ArticlePO, ArticleVO> PO_TO_CONTENT_VO = po -> {
        ArticleVO vo = new ArticleVO();
        vo.setId(po.getId());
        vo.setTitle(po.getTitle());
        vo.setTime(po.getTime());
        vo.setType(po.getType());
        vo.setContent(po.getContent());
        return vo;
    };
}
