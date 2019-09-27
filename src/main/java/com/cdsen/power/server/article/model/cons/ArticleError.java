package com.cdsen.power.server.article.model.cons;

import com.cdsen.power.core.Error;
import lombok.Getter;

/**
 * @author HuSen
 * create on 2019/9/27 15:10
 */
@Getter
public enum ArticleError implements Error {
    EXISTED(70001, "该文章标题已存在"),
    NOT_FOUND(70002, "该文章不存在");

    private Integer code;
    private String error;

    ArticleError(Integer code, String error) {
        this.code = code;
        this.error = error;
    }
}
