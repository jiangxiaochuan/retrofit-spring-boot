package com.github.sanjin.spring;

import lombok.*;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author: sanjin
 * @date: 2020/2/13 下午3:18
 */
@Getter
@Setter
@ToString
public class RetrofitSpringFactoryBean<T> implements FactoryBean<T> {
    private RetrofitSpringFactory factory;
    private Class<T> clazz;

    public RetrofitSpringFactoryBean() {
    }

    public RetrofitSpringFactoryBean(Class<T> clazz, RetrofitSpringFactory factory) {
        this.factory = factory;
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() throws Exception {
        return factory.newProxy(clazz);
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
