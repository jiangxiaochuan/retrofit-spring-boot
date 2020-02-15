package com.github.sanjin.spring;

/**
 * @author: sanjin
 * @date: 2020/2/13 下午3:44
 */
public interface RetrofitSpringFactory {
    <T> T newProxy(Class<T> clazz);
}
