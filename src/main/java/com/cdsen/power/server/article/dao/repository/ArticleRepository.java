package com.cdsen.power.server.article.dao.repository;

import com.cdsen.power.server.article.dao.po.ArticlePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuSen
 * create on 2019/9/26 16:52
 */
public interface ArticleRepository extends JpaRepository<ArticlePO, Long> {
}
