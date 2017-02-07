package com.mobile.younthcanteen.http.requestinterface;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/8/23 0023.
 * POST请求方式
 */
public interface IPostRequest {

    /**
     * 不带任何参数的post请求
     * @param url 相对地址
     * @return
     */
    @POST("{url}")
    Call<ResponseBody>getDataByPost(@Path(value = "url",encoded = true) String url);

    /**
     * 不带任何参数的post请求
     * @param url 绝对地址
     * @return
     */
    @POST
    Call<ResponseBody>getDataByPostTem(@Url String url);


//    /**
//     * 这种添加参数的方式也是可以的
//     */
//    @POST("{url}")
//    Call<ResponseBody>getDataByPost(@Path("url")String url
//            ,@QueryMap Map<String, String> queryMap);

    /**
     * 带参数的post请求
     * @param url 相对地址
     * @param queryMap 请求参数Map
     * @return
     */
    @FormUrlEncoded //表单的方式传递键值对
    @POST("{url}")
    Call<ResponseBody>getDataByPost(@Path(value = "url",encoded = true) String url,
                                    @FieldMap Map<String, String> queryMap);

    /**
     * 带参数的post请求
     * @param url 绝对地址
     * @param queryMap 请求参数Map
     * @return
     */
    @FormUrlEncoded //表单的方式传递键值对
    @POST
    Call<ResponseBody>getDataByPostTem(@Url String url,
                                       @FieldMap Map<String, String> queryMap);


    /**
     * 带参数的post请求
     * @param url 相对地址
     * @param headerMap 请求头Map
     * @param queryMap 请求参数Map
     * @return
     */
    @FormUrlEncoded //表单的方式传递键值对
    @POST("{url}")
    Call<ResponseBody>getDataByPost(@Path(value = "url",encoded = true) String url,
                                    @HeaderMap Map<String, String> headerMap,
                                    @FieldMap Map<String, String> queryMap);

    /**
     * 带参数的post请求
     * @param url 绝对地址
     * @param headerMap 请求头Map
     * @param queryMap 请求参数Map
     * @return
     */
    @FormUrlEncoded //表单的方式传递键值对
    @POST
    Call<ResponseBody>getDataByPostTem(@Url String url,
                                       @HeaderMap Map<String, String> headerMap,
                                       @FieldMap Map<String, String> queryMap);

}
