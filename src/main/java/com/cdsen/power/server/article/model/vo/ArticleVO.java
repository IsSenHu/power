package com.cdsen.power.server.article.model.vo;

import com.cdsen.power.core.cons.TimeCons;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author HuSen
 * create on 2019/9/27 15:01
 */
@Getter
@Setter
public class ArticleVO {

    private Long id;

    private String title;

    private String content;

    private String html;

    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private LocalDateTime time;

    private Long type;

    private String typeName;
}
