package com.cdsen.power.server.article.model.ao;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author HuSen
 * create on 2019/9/27 15:00
 */
@Getter
@Setter
public class ArticleCreateAO {

    @NotBlank
    private String title;

    private String content;

    private String html;

    @NotNull
    private Long type;
}
