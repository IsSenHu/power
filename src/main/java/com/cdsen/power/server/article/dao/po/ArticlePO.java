package com.cdsen.power.server.article.dao.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/25 14:45
 */
@Getter
@Setter
@Entity
@Table(name = "tb_article")
public class ArticlePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Lob
    @Column(name = "html")
    @Basic(fetch = FetchType.LAZY)
    private String html;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "type", nullable = false)
    private Long type;
}
