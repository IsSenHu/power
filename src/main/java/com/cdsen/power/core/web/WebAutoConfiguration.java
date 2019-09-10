package com.cdsen.power.core.web;

import com.cdsen.power.core.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author HuSen
 * create on 2019/9/10 16:18
 */
@Configuration
public class WebAutoConfiguration {

    @Slf4j
    @RestControllerAdvice
    public final static class AdviceConfiguration {

        @ExceptionHandler(Exception.class)
        public JsonResult<Object> exceptionHandler(Exception e) {
            log.error("请求发生异常:", e);
            return JsonResult.of(999999999, "未知异常");
        }
    }
}
