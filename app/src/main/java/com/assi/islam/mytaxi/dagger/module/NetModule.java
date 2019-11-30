package com.assi.islam.mytaxi.dagger.module;

import com.assi.islam.mytaxi.api.webService.GoogleApiWebservice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.assi.islam.mytaxi.api.RequestHeadersInterceptor;
import com.assi.islam.mytaxi.api.webService.Webservice;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.assi.islam.mytaxi.constants.Constants.BASE_URL;
import static com.assi.islam.mytaxi.constants.Constants.DEFAULT_TIMEOUT;
import static com.assi.islam.mytaxi.constants.Constants.GOOGLE_API_BASE_URL;

/**
 * Created by islam assi
 *
 * provide net related objects
 */

@Module
public class NetModule {

    @Provides
    @Singleton
    Webservice provideWebService(Retrofit.Builder retrofitBuilder){

        return retrofitBuilder
                .build()
                .create(Webservice.class);

    }

    @Provides
    @Singleton
    GoogleApiWebservice provideGoogleApiWebService(Retrofit.Builder retrofitBuilder){

        return retrofitBuilder
                .baseUrl(GOOGLE_API_BASE_URL)
                .build()
                .create(GoogleApiWebservice.class);

    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(OkHttpClient okHttpClient){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder defaultRetrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson));

        return defaultRetrofitBuilder
                .client(okHttpClient);
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttp(){

        OkHttpClient.Builder defaultHttpClient =
                new OkHttpClient.Builder()
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        defaultHttpClient.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));

        defaultHttpClient.addInterceptor(new RequestHeadersInterceptor());

        return defaultHttpClient.build();
    }

}
