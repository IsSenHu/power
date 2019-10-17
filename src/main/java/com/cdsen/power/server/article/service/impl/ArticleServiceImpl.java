package com.cdsen.power.server.article.service.impl;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.core.SpecificationFactory;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.server.article.dao.po.ArticlePO;
import com.cdsen.power.server.article.dao.repository.ArticleRepository;
import com.cdsen.power.server.article.model.ao.ArticleCreateAO;
import com.cdsen.power.server.article.model.ao.ArticleUpdateAO;
import com.cdsen.power.server.article.model.cons.ArticleError;
import com.cdsen.power.server.article.model.query.ArticleQuery;
import com.cdsen.power.server.article.model.vo.ArticleVO;
import com.cdsen.power.server.article.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.cdsen.power.server.article.transfer.ArticleTransfer.*;

/**
 * @author HuSen
 * create on 2019/9/27 14:59
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ArticleVO> create(ArticleCreateAO ao) {
        Long userId = SecurityUtils.currentUserDetails().getUserId();
        if (articleRepository.existsByTitleAndUserId(ao.getTitle(), userId)) {
            return JsonResult.of(ArticleError.EXISTED);
        }
        ArticlePO po = new ArticlePO();
        po.setTitle(ao.getTitle());
        po.setUserId(userId);
        po.setType(ao.getType());
        po.setTime(LocalDateTime.now());
        po.setContent(ao.getContent());
        po.setHtml(ao.getHtml());
        articleRepository.save(po);
        return JsonResult.of(PO_TO_SIMPLE_VO.apply(po));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ArticleVO> update(ArticleUpdateAO ao) {
        Optional<ArticlePO> byId = articleRepository.findById(ao.getId());
        if (byId.isPresent()) {
            Long userId = SecurityUtils.currentUserDetails().getUserId();
            ArticlePO po = byId.get();
            if (!StringUtils.equals(po.getTitle(), ao.getTitle())) {
                if (articleRepository.existsByTitleAndUserId(ao.getTitle(), userId)) {
                    return JsonResult.of(ArticleError.EXISTED);
                }
            }
            po.setTitle(ao.getTitle());
            po.setType(ao.getType());
            po.setContent(ao.getContent());
            po.setHtml(ao.getHtml());
            articleRepository.save(po);
            return JsonResult.of(PO_TO_SIMPLE_VO.apply(po));
        } else {
            return JsonResult.of(ArticleError.NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<ArticleVO> delete(Long id) {
        return articleRepository.findById(id)
                .map(po -> {
                    articleRepository.delete(po);
                    return JsonResult.of(PO_TO_SIMPLE_VO.apply(po));
                })
                .orElseGet(JsonResult::success);
    }

    @Override
    public JsonResult<PageResult<ArticleVO>> page(IPageRequest<ArticleQuery> iPageRequest) {
        Pageable pageable = iPageRequest.of();
        Page<ArticlePO> pageInfo = articleRepository.findAll(SpecificationFactory.produce((predicates, root, criteriaBuilder) -> {
            Long userId = SecurityUtils.currentUserDetails().getUserId();
            predicates.add(criteriaBuilder.equal(root.get("userId").as(Long.class), userId));
        }), pageable);
        return JsonResult.of(PageResult.of(pageInfo.getTotalElements(), PO_TO_SIMPLE_VO, pageInfo.getContent()));
    }

    @Override
    public JsonResult<ArticleVO> html(Long id) {
        return articleRepository.findById(id)
                .map(po -> JsonResult.of(PO_TO_HTML_VO.apply(po)))
                .orElseGet(() -> JsonResult.of(ArticleError.NOT_FOUND));
    }

    @Override
    public JsonResult<ArticleVO> content(long id) {
        return articleRepository.findById(id)
                .map(po -> JsonResult.of(PO_TO_CONTENT_VO.apply(po)))
                .orElseGet(() -> JsonResult.of(ArticleError.NOT_FOUND));
    }
}
