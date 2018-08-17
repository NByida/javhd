package com.azul.yida.javhd.net;

import android.content.Context;

import com.azul.yida.javhd.common.JavHdApp;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private volatile Retrofit retrofit;
    private volatile Retrofit milaRetrofit;
    private  FilmServices filmServices;

    File cacheFile = new File(JavHdApp.getInstance().getCacheDir().getAbsolutePath(), "HttpCache");
    Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);//缓存文件为10MB

    private RetrofitInstance(){

    }

    private static class holder{
        static final RetrofitInstance retrofitInstance=new RetrofitInstance();
    }

    public static RetrofitInstance getInstance(){
        return holder.retrofitInstance;
    }

    private Retrofit getRetrofit(Context context){
        if (retrofit==null){
            synchronized (this) {
                if (retrofit==null){
                    retrofit=new Retrofit.Builder()
                            .client(getClient(context))
                            .baseUrl(FilmServices.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }


    public FilmServices getFilmServices(Context context){
        if(filmServices==null){
            filmServices=getRetrofit(context).create(FilmServices.class);
        }
        return filmServices;
    }


    public OkHttpClient getClient(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .cache(cache)
                .build();
        return client;
    }
}

