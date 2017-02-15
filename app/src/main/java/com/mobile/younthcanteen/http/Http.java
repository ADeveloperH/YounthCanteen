package com.mobile.younthcanteen.http;

import android.text.TextUtils;
import android.util.Log;

import com.mobile.younthcanteen.http.factory.ApiRequestFactory;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: sfce
 * Date: 13-10-22
 * Time: 上午9:30
 */
public class Http {

    public static final String LOGIN = "userLogin";//登录
    public static final String SENDCODE = "sendCode";//注册页面发送验证码
    public static final String REGISTER = "userReg";//注册
    public static final String FINDPWD = "userPasswordFind";//找回密码
    public static final String SENDCODEFIND = "sendCodeFind";//找回密码中发送短信验证码
    public static final String MODIFYNICKNAME = "userNickUp";//修改用户名
    public static final String MODIFYPWD = "userPasswordUp";//修改密码
    public static final String UPLOADIMG = "userImgUpload";//上传头像
    public static final String CHECKVERSION = "appversion";//检查版本更新

    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static final String BASE_URL;

    static {
        BASE_URL = "http://116.255.130.49:8732/";//主机域名
    }

    /**
     * 相对地址GET异步请求
     *
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(final String url, final RequestParams params, final Callback<ResponseBody> responseHandler) {
        if (TextUtils.isEmpty(url)) {
            Log.e("Http", "请求地址不能为空");
        } else {
            //带参数
            ApiRequestFactory.INSTANCE.getiGetRequest()
                    .getDataByGet(url, params).enqueue(responseHandler);
        }
    }

    /**
     * 相对地址GET异步请求(可设置超时时间)
     *
     * @param url
     * @param params
     * @param responseHandler
     * @param readTimeout     单位为秒
     * @param connTimeout     单位为秒
     */
    public static void get(final String url, final RequestParams params,
                           final Callback<ResponseBody> responseHandler,
                           final int readTimeout, final int connTimeout) {
        if (TextUtils.isEmpty(url)) {
            Log.e("Http", "请求地址不能为空");
        } else {
            //带参数
            ApiRequestFactory.INSTANCE.getiGetRequestTimeout(readTimeout, connTimeout)
                    .getDataByGet(url, params).enqueue(responseHandler);
        }
    }

    /**
     * 绝对地址GET异步请求
     *
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void getTemp(final String url, final RequestParams params, final Callback<ResponseBody> responseHandler) {
        if (TextUtils.isEmpty(url)) {
            Log.e("Http", "请求地址不能为空");
        } else {
            //带参数
            ApiRequestFactory.INSTANCE.getiGetRequest()
                    .getDataByGetTem(url, params).enqueue(responseHandler);
        }
    }

    /**
     * 绝对地址GET异步请求
     *
     * @param url
     * @param params
     * @param responseHandler
     * @param readTimeout     单位为秒
     * @param connTimeout     单位为秒
     */
    public static void getTemp(final String url, final RequestParams params,
                               final Callback<ResponseBody> responseHandler,
                               final int readTimeout, final int connTimeout) {
        if (TextUtils.isEmpty(url)) {
            Log.e("Http", "请求地址不能为空");
        } else {
            //带参数
            ApiRequestFactory.INSTANCE.getiGetRequestTimeout(readTimeout, connTimeout)
                    .getDataByGetTem(url, params).enqueue(responseHandler);
        }
    }

    /**
     * 相对地址POST异步请求
     *
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void post(final String url, final RequestParams params, final Callback<ResponseBody> responseHandler) {
        if (TextUtils.isEmpty(url)) {
            Log.e("Http", "请求地址不能为空");
        } else {
            //带参数
            ApiRequestFactory.INSTANCE.getiPostRequest()
                    .getDataByPost(url, params).enqueue(responseHandler);
        }
    }

    /**
     * 相对地址POST异步请求
     *
     * @param url
     * @param params
     * @param responseHandler
     * @param readTimeout     单位为秒
     * @param connTimeout     单位为秒
     */
    public static void post(final String url, final RequestParams params,
                            final Callback<ResponseBody> responseHandler,
                            final int readTimeout, final int connTimeout) {
        if (TextUtils.isEmpty(url)) {
            Log.e("Http", "请求地址不能为空");
        } else {
            //带参数
            ApiRequestFactory.INSTANCE.getiPostRequestTimeout(readTimeout, connTimeout)
                    .getDataByPost(url, params).enqueue(responseHandler);
        }
    }

    /**
     * 绝对地址POST异步请求
     *
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void postTemp(final String url, final RequestParams params, final Callback<ResponseBody> responseHandler) {
        if (TextUtils.isEmpty(url)) {
            Log.e("Http", "请求地址不能为空");
        } else {
            //带参数
            ApiRequestFactory.INSTANCE.getiPostRequest()
                    .getDataByPostTem(url, params).enqueue(responseHandler);
        }
    }

    /**
     * 绝对地址POST异步请求
     *
     * @param url
     * @param params
     * @param responseHandler
     * @param readTimeout     单位为秒
     * @param connTimeout     单位为秒
     */
    public static void postTemp(final String url, final RequestParams params,
                                final Callback<ResponseBody> responseHandler,
                                final int readTimeout, final int connTimeout) {
        if (TextUtils.isEmpty(url)) {
            Log.e("Http", "请求地址不能为空");
        } else {
            //带参数
            ApiRequestFactory.INSTANCE.getiPostRequestTimeout(readTimeout, connTimeout)
                    .getDataByPostTem(url, params).enqueue(responseHandler);
        }
    }


    /**
     * POST上传单个文件
     *
     * @param url             相对地址
     * @param filePath        文件绝对路径
     * @param params          参数Map
     * @param responseHandler
     */
    public static void uploadFile(final String url, final String filePath, final RequestParams params, final Callback<ResponseBody> responseHandler) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(filePath)) {
            Log.e("Http", "请求地址和文件路径不能为空");
        } else {
            //带参数
            File file = new File(filePath);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", file.getName(),
                    photoRequestBody);
            ApiRequestFactory.INSTANCE.getiUploadFileRequest()
                    .uploadFile(url, photo, params).enqueue(responseHandler);
        }
    }

    /**
     * POST上传单个文件
     *
     * @param url             相对地址
     * @param filePath        文件绝对路径
     * @param params          参数Map
     * @param responseHandler
     * @param readTimeout     单位为秒
     * @param connTimeout     单位为秒
     */
    public static void uploadFile(final String url, final String filePath, final RequestParams params,
                                  final Callback<ResponseBody> responseHandler,
                                  final int readTimeout, final int connTimeout) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(filePath)) {
            Log.e("Http", "请求地址和文件路径不能为空");
        } else {
            //带参数
            File file = new File(filePath);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", file.getName(),
                    photoRequestBody);
            ApiRequestFactory.INSTANCE.getiUploadFileRequestTimeout(readTimeout, connTimeout)
                    .uploadFile(url, photo, params).enqueue(responseHandler);
        }
    }

    /**
     * POST上传单个文件
     *
     * @param url             绝对地址
     * @param filePath        文件绝对路径
     * @param params          参数Map
     * @param responseHandler
     */
    public static void uploadFileTemp(final String url, final String filePath, final RequestParams params, final Callback<ResponseBody> responseHandler) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(filePath)) {
            Log.e("Http", "请求地址和文件路径不能为空");
        } else {
            //带参数
            File file = new File(filePath);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", file.getName(),
                    photoRequestBody);
            ApiRequestFactory.INSTANCE.getiUploadFileRequest()
                    .uploadFileTemp(url, photo, params).enqueue(responseHandler);
        }
    }

    /**
     * POST上传单个文件
     *
     * @param url             绝对地址
     * @param filePath        文件绝对路径
     * @param params          参数Map
     * @param responseHandler
     * @param readTimeout     单位为秒
     * @param connTimeout     单位为秒
     */
    public static void uploadFileTemp(final String url, final String filePath, final RequestParams params,
                                      final Callback<ResponseBody> responseHandler,
                                      final int readTimeout, final int connTimeout) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(filePath)) {
            Log.e("Http", "请求地址和文件路径不能为空");
        } else {
            //带参数
            File file = new File(filePath);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", file.getName(),
                    photoRequestBody);
            ApiRequestFactory.INSTANCE.getiUploadFileRequestTimeout(readTimeout, connTimeout)
                    .uploadFileTemp(url, photo, params).enqueue(responseHandler);
        }
    }

}
