package com.github.sanjin.spring;

import com.github.sanjin.annotation.RetrofitConfig;
import com.github.sanjin.convert.GsonConverterFactory;
import com.github.sanjin.okhttp3.LoggingInterceptor;
import com.github.sanjin.retrofit.CustomerCallAdpterFactory;
import com.github.sanjin.spring.proxy.RetrofitSpringInvocationHandler;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: sanjin
 * @date: 2020/2/13 下午3:46
 */
public class DefaultRetrofitSpringFactory implements RetrofitSpringFactory {

    private static Gson gson = new Gson();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T newProxy(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(RetrofitConfig.class)) {
            throw new IllegalArgumentException(
                    String.format("class={} must present {} annotation",
                            clazz.getName(), RetrofitConfig.class.getName()));
        }
        RetrofitConfig retrofitConfig = clazz.getAnnotation(RetrofitConfig.class);
        Retrofit retrofit = newRetrofit(retrofitConfig.value());
        T serviceRetrofit = retrofit.create(clazz);

        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new RetrofitSpringInvocationHandler(serviceRetrofit)
        );
    }

    private Retrofit newRetrofit(String domain) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // curl log
        builder.addInterceptor(new LoggingInterceptor());
        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new CustomerCallAdpterFactory())
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}
