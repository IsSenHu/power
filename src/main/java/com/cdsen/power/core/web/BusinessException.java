package com.cdsen.power.core.web;

import com.cdsen.power.core.Error;
import lombok.Getter;

/**
 * @author HuSen
 * create on 2019/9/10 16:38
 */
@Getter
public class BusinessException extends RuntimeException {
    private Error error;

    public BusinessException(Error error) {
        super(error.getError(), null, false, false);
        this.error = error;
    }
}
