package com.castingmob.network;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description : This is Network Manager Class
               which has the type of Network
               Framework
 Date        : 14/01/16
 ==============================================
 */


import com.castingmob.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RssFeedManager {

    private final static RssFeedManager networkManager =new RssFeedManager();

    private RssFeedManager(){}

    public static RssFeedManager getInstance() {
        return networkManager;
    }

    private final static Retrofit.Builder retrofitBuilder  = new Retrofit.Builder()
            .baseUrl(BuildConfig.ENV)
            .addConverterFactory(GsonConverterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = retrofitBuilder.client(getOkHttpClient().build()).build();
        return retrofit.create(serviceClass);
    }

    public static final OkHttpClient.Builder getOkHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        return httpClient;
    }
}
