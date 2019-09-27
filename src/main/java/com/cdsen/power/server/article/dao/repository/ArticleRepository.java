package com.cdsen.power.server.article.dao.repository;

import com.cdsen.power.server.article.dao.po.ArticlePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author HuSen
 * create on 2019/9/26 16:52
 */
public interface ArticleRepository extends JpaRepository<ArticlePO, Long>, JpaSpecificationExecutor<ArticlePO> {

    boolean existsByTitleAndUserId(String title, Long userId);

    @Query("SELECT tb.html FROM ArticlePO tb WHERE tb.id = :id")
    Optional<ArticlePO> findHtmlById(@Param("id") Long id);
}
