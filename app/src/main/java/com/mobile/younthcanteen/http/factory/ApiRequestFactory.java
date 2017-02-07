package com.mobile.younthcanteen.http.factory;

import com.mobile.younthcanteen.http.requestinterface.IGetRequest;
import com.mobile.younthcanteen.http.requestinterface.IPostRequest;
import com.mobile.younthcanteen.http.requestinterface.IUploadFileRequest;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public enum ApiRequestFactory {

    INSTANCE;

    private final IGetRequest iGetRequest;
    private final IPostRequest iPostRequest;
    private final IUploadFileRequest iUploadFileRequest;

    private final String TAG = "ApiRequestFactory";
    ApiRequestFactory() {
        iGetRequest = RetrofitClient.INSTANCE.getRetrofit().create(IGetRequest.class);
        iPostRequest = RetrofitClient.INSTANCE.getRetrofit().create(IPostRequest.class);
        iUploadFileRequest = RetrofitClient.INSTANCE.getRetrofit().create(IUploadFileRequest.class);
    }

    public IGetRequest getiGetRequest() {
        return iGetRequest;
    }

    public IPostRequest getiPostRequest() {
        return iPostRequest;
    }

    public IUploadFileRequest getiUploadFileRequest() {
        return iUploadFileRequest;
    }

    public IGetRequest getiGetRequestTimeout(int readTimeout,int connTimeout) {
        return RetrofitClient.INSTANCE.getRetrofitTimeout(readTimeout,connTimeout)
                .create(IGetRequest.class);
    }

    public IPostRequest getiPostRequestTimeout(int readTimeout,int connTimeout) {
        return RetrofitClient.INSTANCE.getRetrofitTimeout(readTimeout,connTimeout)
                .create(IPostRequest.class);
    }

    public IUploadFileRequest getiUploadFileRequestTimeout(int readTimeout,int connTimeout) {
        return RetrofitClient.INSTANCE.getRetrofitTimeout(readTimeout,connTimeout)
                .create(IUploadFileRequest.class);
    }


}
