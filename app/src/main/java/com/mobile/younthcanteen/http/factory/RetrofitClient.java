package com.mobile.younthcanteen.http.factory;

import com.mobile.younthcanteen.http.Http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public enum RetrofitClient{
    INSTANCE;
    private final Retrofit retrofit;
    RetrofitClient() {
        retrofit = new Retrofit.Builder()
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //设置OKHttpClient
                .client(OkHttpFactory.INSTANCE.getOkHttpClient())
                //设置BaseUrl
                .baseUrl(Http.BASE_URL)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Retrofit getRetrofitTimeout(int readTimeout,int connTimeout) {
        return new Retrofit.Builder()
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //设置OKHttpClient
                .client(OkHttpFactory.INSTANCE.getOkHttpClient(readTimeout,connTimeout))
                //设置BaseUrl
                .baseUrl(Http.BASE_URL)
                .build();
    }
}
