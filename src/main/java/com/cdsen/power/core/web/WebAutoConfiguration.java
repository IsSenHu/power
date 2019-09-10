package com.cdsen.power.core.web;

import com.cdsen.power.core.JsonResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author HuSen
 * create on 2019/9/10 16:18
 */
@Configuration
public class WebAutoConfiguration {

    @RestControllerAdvice
    public final static class AdviceConfiguration {

        @ExceptionHandler(Exception.class)
        public JsonResult<Object> exceptionHandler(Exception e) {
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                return JsonResult.of(businessException.getError());
            } else {
                return JsonResult.of(999999999, "未知异常");
            }
        }
    }
}
