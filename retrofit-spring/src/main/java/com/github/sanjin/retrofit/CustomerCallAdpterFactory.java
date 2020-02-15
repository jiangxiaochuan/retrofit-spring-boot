package com.github.sanjin.retrofit;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: sanjin
 * @date: 2020/2/13 下午5:17
 */
public class CustomerCallAdpterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (returnType instanceof ParameterizedType) {
            Class clazz = getRawType(returnType);
            if (clazz == Call.class) {
                return null;
            }
        }
        return new Response2JsonAdpter(returnType);
    }

    /**
     * execute http request
     */
    public class Response2JsonAdpter<R> implements CallAdapter<R, R> {
        private Type type;

        public Response2JsonAdpter(Type type) {
            this.type = type;
        }

        @Override
        public Type responseType() {
            return type;
        }

        @Override
        public R adapt(Call<R> call) {
            try {
                Response<R> response = call.execute();
                if (response.code() != 200) {
                    throw new IllegalStateException(String.format(
                            "response.code!=200,response={},msg={}",
                            response.toString(),response.message()
                    ));
                }
                return response.body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
