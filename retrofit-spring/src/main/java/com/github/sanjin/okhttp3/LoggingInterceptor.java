package com.github.sanjin.okhttp3;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        try {
            Response response = chain.proceed(request);
            if (!log.isInfoEnabled()) {
                return response;
            }
            long spendTime = System.currentTimeMillis() - startTime;
            ResponseBody responseBody = response.body();
            MediaType mediaType = responseBody.contentType();
            String url = request.url().toString();
            String requestBodyStr = requestBodyToString(request);
            String requestHeadersStr = headersToString(request);
            Response newResponse = response;
            String responseBodyStringInfo = "mediaType not json ,can not print,mediaType=" + mediaType.toString();
            if (mediaType != null && mediaType.subtype() != null && mediaType.subtype().equalsIgnoreCase("json")) {
                String responseBodyString = response.body().string();
                responseBodyStringInfo = responseBodyString;
                if (responseBodyStringInfo.length() > 2048) {
                    responseBodyStringInfo = responseBodyStringInfo.substring(0, 2048) + "..more not print..";
                }
                Charset charset = Charset.forName("utf-8");
                if (mediaType != null) {
                    charset = mediaType.charset(Charset.forName("utf-8"));
                }
                newResponse = response.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes(charset))).build();
            }
            log.info("rest-api:execute {}ms,curl '{}' {} {},response={}", spendTime, url, requestHeadersStr, requestBodyStr, responseBodyStringInfo);
            return newResponse;
        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                long spendTime = System.currentTimeMillis() - startTime;
                String url = request.url().toString();
                String requestBodyStr = requestBodyToString(request);
                String requestHeadersStr = headersToString(request);
                log.warn("rest-api:execute {}ms,curl '{} {} {},exception={}-{}", spendTime, url, requestHeadersStr, requestBodyStr, e.getClass().getSimpleName(), e.getMessage());
            }
            throw e;
        }
    }

    private String headersToString(Request request) {
        Headers headers = request.headers();
        StringBuilder builder = new StringBuilder();
        if (request.body() != null && request.body().contentType() != null) {
            String contentType = request.body().contentType().toString();
            builder.append(" -H 'Content-Type:");
            builder.append(contentType);
            builder.append("'");
        }
        for (int i = 0; i < headers.size(); i++) {
            builder.append(" -H '");
            builder.append(headers.name(i));
            builder.append(":");
            builder.append(headers.value(i));
            builder.append("'");
        }
        return builder.toString();
    }

    private static String requestBodyToString(Request request) throws IOException {
        if (request.method() != null && request.method().equalsIgnoreCase("GET")) {
            return "";
        }
        String bodyPrefix = " -d '";
        if (request.body() == null) {
            return bodyPrefix + "'";
        }
        final Request copy = request.newBuilder().build();
        final Buffer buffer = new Buffer();
        copy.body().writeTo(buffer);
        return bodyPrefix + buffer.readUtf8() + "'";
    }
}
