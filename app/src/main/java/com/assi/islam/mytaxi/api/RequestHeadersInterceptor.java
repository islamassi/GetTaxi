package com.assi.islam.mytaxi.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by islam assi
 * <p>
 * An Interceptor that adds headers for all the requests. Mainly for handling authorization header
 */

public class RequestHeadersInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = requestBuilder = request.newBuilder()
                .addHeader("Content-Type", "application/json");
        request = requestBuilder.build();
        return chain.proceed(request);
    }
}
