package com.mobile.younthcanteen.http.requestinterface;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/8/23 0023.
 * GET请求
 */
public interface IGetRequest {


    /**
     * 带请求头和请求体
     * @param url 相对地址
     * @param headerMap 请求头Map
     * @param queryMap 请求体Map
     * @return
     */
    @GET("{url}")
    Call<ResponseBody>getDataByGet(@Path(value = "url",encoded = true) String url,
                                   @HeaderMap Map<String, String> headerMap,
                                   @QueryMap Map<String, String> queryMap);

    /**
     * 只带请求体
     * @param url 请求头Map
     * @param queryMap 请求体Map
     * @return
     */
    @GET("{url}")
    Call<ResponseBody>getDataByGet(@Path(value = "url",encoded = true) String url,
                                   @QueryMap Map<String, String> queryMap);

    /**
     * 不含任何参数
     * @param url 相对地址
     * @return
     */
    @GET("{url}")
    Call<ResponseBody>getDataByGet(@Path(value = "url",encoded = true) String url);


    /**
     * 不含任何参数
     * @param abUrl 绝对地址
     * @return
     */
    @GET
    Call<ResponseBody>getDataByGetTem(@Url String abUrl);

    /**
     * 只含请求体
     * @param abUrl 绝对地址
     * @param queryMap 请求体Map
     * @return
     */
    @GET
    Call<ResponseBody>getDataByGetTem(@Url String abUrl,
                                      @QueryMap Map<String, String> queryMap);

    /**
     * 含有请求头和请求体
     * @param abUrl 绝对地址
     * @param headerMap 请求头的Map
     * @param queryMap 请求体Map
     * @return
     */
    @GET
    Call<ResponseBody>getDataByGetTem(@Url String abUrl,
                                      @HeaderMap Map<String, String> headerMap,
                                      @QueryMap Map<String, String> queryMap);

}
