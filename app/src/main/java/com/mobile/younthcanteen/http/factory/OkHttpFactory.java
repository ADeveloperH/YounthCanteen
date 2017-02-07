package com.mobile.younthcanteen.http.factory;

import com.mobile.younthcanteen.activity.CanteenApplication;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public enum  OkHttpFactory {
    INSTANCE;
    private final OkHttpClient okHttpClient;
    private static final int TIMEOUT_READ_DEFAULT = 10;
    private static final int TIMEOUT_CONNECTION_DEFAULT = 10;

    OkHttpFactory() {
        //打印请求Log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存目录
        Cache cache = new Cache(CanteenApplication.getContext().getCacheDir(), 10 * 1024 * 1024);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //打印请求log
                .addInterceptor(interceptor)
                //必须是设置Cache目录
                .cache(cache)
//                //走缓存，两个都要设置
//                .addInterceptor(new OnOffLineCachedInterceptor())
//                .addNetworkInterceptor(new OnOffLineCachedInterceptor())
                //失败重连
                .retryOnConnectionFailure(true)
                //设置拦截器。添加头和公共参数
//                .addInterceptor(new MyInterceptor())
                //https请求
                .sslSocketFactory(getSSLSocketFactory(),new TrustAllManager())
                .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                //time out
                .readTimeout(TIMEOUT_READ_DEFAULT, TimeUnit.SECONDS)//主机传递数据超时时间
                .connectTimeout(TIMEOUT_CONNECTION_DEFAULT, TimeUnit.SECONDS);//主机建立连接超时时间
        okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public OkHttpClient getOkHttpClient(int readTimeout,int connTimeout) {
        OkHttpClient okHttpClientTimeout = okHttpClient.newBuilder()
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectTimeout(connTimeout, TimeUnit.SECONDS)
                .build();
        return okHttpClientTimeout;
    }

    private SSLSocketFactory getSSLSocketFactory() {
        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{new TrustAllManager()}, new java.security.SecureRandom());
            return context.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

    }

}