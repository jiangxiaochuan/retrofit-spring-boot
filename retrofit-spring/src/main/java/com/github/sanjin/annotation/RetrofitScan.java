package com.github.sanjin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: sanjin
 * @date: 2020/2/13 下午7:36
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface RetrofitScan {
    String[] value();
}
