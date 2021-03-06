package com.cdsen.power.core.security.annotation;

import java.lang.annotation.*;

/**
 * @author HuSen
 * create on 2019/7/18 10:35
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityModule {

    String name();
}
