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

public class Http {

    public static final String SUCCESS = "0";//接口请求成功的标志
    public static final String LOGIN = "userLogin";//登录
    public static final String SENDCODE = "sendCode";//注册页面发送验证码
    public static final String REGISTER = "userReg";//注册
    public static final String FINDPWD = "userPasswordFind";//找回密码
    public static final String SENDCODEFIND = "sendCodeFind";//找回密码中发送短信验证码
    public static final String MODIFYNICKNAME = "userNickUp";//修改用户名
    public static final String MODIFYPWD = "userPasswordUp";//修改密码
    public static final String UPLOADIMG = "userImgUpload";//上传头像
    public static final String CHECKVERSION = "appversion";//检查版本更新
    public static final String GETADDRESSLIST = "userAddrGet";//获取常用地址列表
    public static final String GETOFFICELIST = "officeList";//获取写字楼列表
    public static final String ADDADDRESS = "userAddrAdd";//添加收货地址
    public static final String DELETEADDRESS = "userAddrDel";//删除收货地址
    public static final String GETHOMEDATA = "indexPage";//获取首页数据
    public static final String GETGOODSINFO = "proDetail";//获取详情数据
    public static final String GETUSERDETAILINFO = "userDetail";//获取用户详情数据
    public static final String ORDERADD = "orderAdd";//添加订单
    public static final String PAYORDER = "orderPay";//支付订单
    public static final String SETPAYPWD = "userPayPasswrodUp";//设置支付密码
    public static final String GETORDERLIST = "orderList";//获取订单列表
    public static final String COMMITORDERBYALIPAY = "signatures";//通过支付宝提交订单
    public static final String COMMITALIPAYRESULT = "alipayResultCheck";//提交支付结果
    public static final String GETCONSUMEDETAIL = "moneyList";//获取消费明细
    public static final String SIGNATURERECHARGE = "signaturesRecharge";//支付宝充值，加签要充值的金额
    public static final String GETMOREGOODS = "proMore";//获取更多列表
    public static final String GETSEARCHGOODS = "proSearch";//搜索列表
    public static final String COMMITSUGGEST = "suggestionAdd";//提交反馈建议
    public static final String DELETEORDER = "orderDelete";//删除订单


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
     * @param json
     * @param responseHandler
     */
    public static void postJson(String url, String json, Callback<ResponseBody> responseHandler) {
        if (TextUtils.isEmpty(url)) {
            Log.e("Http", "请求地址不能为空");
        } else {
            //带参数
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"),
                    json);
            ApiRequestFactory.INSTANCE.getiPostRequest().
                    getDataByPostJson(url, body).enqueue(responseHandler);
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
     * @param responseHandler
     */
    public static void uploadFileImage(final String url,
                                       final String filePath,
                                       String token,
                                       String imgtype,
                                       final Callback<ResponseBody> responseHandler) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(filePath)) {
            Log.e("Http", "请求地址和文件路径不能为空");
        } else {
            //带参数
            File file = new File(filePath);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", file.getName(),
                    photoRequestBody);
            ApiRequestFactory.INSTANCE.getiUploadFileRequest()
                    .uploadFileWithHeader(url, token,imgtype,photo).enqueue(responseHandler);
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
