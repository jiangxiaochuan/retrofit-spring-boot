package com.github.sanjin.spring.proxy;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: sanjin
 * @date: 2020/2/13 下午4:03
 */
public class RetrofitSpringInvocationHandler implements InvocationHandler {
    private Object serviceRetrofit;

    public RetrofitSpringInvocationHandler(Object serviceRetrofit) {
        this.serviceRetrofit = serviceRetrofit;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(serviceRetrofit, args);
        return invoke;
    }
}
