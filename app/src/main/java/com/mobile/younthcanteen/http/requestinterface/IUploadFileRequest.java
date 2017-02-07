package com.mobile.younthcanteen.http.requestinterface;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public interface IUploadFileRequest {
    /**
     * 上传单个文件的POST请求
     * @param url  相对地址
     * @param photo 上传的文件
     * @param queryMap 参数map
     * @return
     */
    @Multipart
    @POST("{url}")
    Call<ResponseBody>uploadFile(@Path(value = "url",encoded = true) String url,
                                 @Part MultipartBody.Part photo,
                                 @QueryMap Map<String, String> queryMap);
    /**
     *
     * 带参数的另一种写法。直接写死参数
     *  @Multipart
        @POST("register")
        Call<ResponseBody>uploadFile(@Part MultipartBody.Part photo,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password);
     *
     */




    /**
     * 上传单个文件的POST请求
     * @param url  绝对地址
     * @param photo 上传的文件
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody>uploadFileTemp(@Url String url,
                                     @Part MultipartBody.Part photo,
                                     @QueryMap Map<String, String> queryMap);



    /**
     * 上传单个文件的POST请求.不带参数
     * @param url  相对地址
     * @param photo 上传的文件
     * @return
     */
    @Multipart
    @POST("{url}")
    Call<ResponseBody>uploadFile(@Path(value = "url",encoded = true) String url,
                                 @Part MultipartBody.Part photo);

    /**
     * 上传单个文件的POST请求。不带参数
     * @param url  绝对地址
     * @param photo 上传的文件
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody>uploadFileTemp(@Url String url,
                                     @Part MultipartBody.Part photo);
}
