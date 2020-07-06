package com.coderpig.drysister;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2020/7/5 10:18.
 **/
public class HttpManager {
    public OkHttpClient getOkHttpClient(){
        //log用拦截器
        HttpLoggingInterceptor logging =new HttpLoggingInterceptor();
        //开发模式记录整个boby，否则只记录基础信息如返回200，http协议版本等
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();
    }
    public Retrofit getRetrofit(OkHttpClient okHttpClient){
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("http://gank.io/api/data/福利/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient);
        return builder.build();
    }

}
