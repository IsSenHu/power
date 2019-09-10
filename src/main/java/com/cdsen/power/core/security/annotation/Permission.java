package com.cdsen.power.core.security.annotation;

import java.lang.annotation.*;

/**
 * 登录就能用的功能不用加注解
 *
 * @author HuSen
 * create on 2019/9/10 11:30
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Permission {

    String value();
}
