package com.github.sanjin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: sanjin
 * @date: 2020/2/13 下午2:33
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface RetrofitConfig {
    /**
     * domain,if you want request API http://www.xxx.com/article/get,
     * the domain is "http://www.xxx.com" or "http://www.xxx.com/"
     */
    String value();

    /**
     * domain describe
     */
    String desc() default "";
}
