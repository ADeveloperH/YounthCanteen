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

    public static final String DEFAULT_SHARE_TITLE = "10086";

    public static final String DEFAULT_SHARE_CONTENT = "10086,随时随地，掌控你的流量！";

    private static final String TAG = "Http";
    public final static String GET_FINDCONFIG_LIST = "front/hn/busi4!findConfigList";//发现页面配置的链接

    public final static String ONEKEY_LOGIN = "front/hn/login!checkLoginToken";//一键登录接口

    public final static String GET_PACKAGE_LIST = "front/hn/busi4!getTypeOfPackage";//套餐办理列表接口

    public final static String ONLINESERVICE_DEGREE = "front/hn/busi4!webSatisfiedService";//在线客服满意度上传接口

    public final static String GET_CAPTCHA = "front/hn/busi2!getCaptcha";//服务密码登录获取图片验证码的接口

    public final static String CANCEL_SUBSCRIBE_BOOK = "front/hn/busi4!deleteSmishingSubscribe";//取消订阅防诈骗秘籍接口

    public final static String SUBSCRIBE_BOOK = "front/hn/busi4!setSmishingSubscribe";//订阅防诈骗秘籍接口

    public final static String CERTIFIED = "front/hn/busi4!getCertificate";//调用能力平台认证接口
    public final static String QUERY_SUBSCRIBE_LIST = "front/hn/busi4!queryAllSmishingMessages";//查询防诈骗秘籍列表接口

    public final static String GET_CAPTCHA_NEW = "front/vcode/securitycodeimage!getcode";//获取图片验证码的接口


    public final static String QUERY_IS_SUBSCRIBE = "front/hn/busi4!querySmishingSubscribe";//查询是否订阅防诈骗秘籍的接口

    public final static String LOG_OUT = "front/hn/busi4!deleteLoginUserInfo";//退出登录的接口

    public final static String REQUEST_CARE = "front/hn/busi!saveFamilyUserRequestCare";//求关照接口

    public final static String QUERY_FRIEND_REQUEST_OPERATION = "front/hn/busi!updateFamilyUserRequestCare4YesOrNo"; //操作新朋友同意/拒绝接口
    public final static String QUERY_FRIEND_REQUEST_LIST = "front/hn/busi!queryFamilyUsersRequestCareListToMe"; //查询新朋友列表接口

    public final static String QUERY_HOT_EXCHANGE_LIST = "front/hn/busi!queryHotPointExchange";//热门兑换积分的接口

    public final static String QUERY_USER_FLAG = "front/hn/busi4!queryUserPayType";//更新用户标示的接口

    public final static String UPDATE_USER_FLAG = "front/hn/busi4!updateUserPayType";//查询用户标示的接口

    public final static String NOTIFY_TA = "front/hn/busi4!smsFamilyNotice";//亲密我的添加记录提醒TA接口

    public final static String GET_RELATIVE_BE_ADDED_RECORDS = "front/hn/busi4!qureyFamilyUsersByMemberPhone";//亲密获取我的被添加记录列表

    public final static String GET_HUMAN_IDENTIFICATE_LIST = "front/hn/sms!querySmsDefraudManualRecords";//人工鉴定结果列表

    public final static String HUMAN_IDENTIFICATE_SMS = "front/hn/sms!smsDefraudManual";//人工鉴定接口

    public final static String IDENTIFICATE_SMS = "front/hn/sms!smsDefraudCheck";//短信鉴定接口

    public final static String GET_Customer_Info = "front/hn/busi4!queryMyInfo";//获取我的数据的接口

    public final static String GET_ALL_MESSAGE = "front/hn/busi4!queryManagerTalkList";//获取管家说数据接口

    public final static String GET_ALL_FUNCTION_LIST = "front/hn/busi4!queryAllFunctionList";//首页轮播图和功能模块的接口

    public final static String GET_SEX = "front/hn/busi4!getSexByPhoneNumber";//获取用户性别

    public final static String GET_MYTRAFFIC_INFO = "front/hn/busi!getPackageInfo";//套餐查询服务

    public final static String GET_MAIN_MYTRAFFIC_INFO = "front/hn/busi!getMainPlan";//主套餐查询服务

    public final static String GET_ADVERT = "front/hn/busi3!getAdvert";//获取广告图

    public final static String UPDATE_USER_CLAIN = "front/hn/sms!updateUserClaim";//诈骗轮播图状态修改

    //    public final static String CHECK_VERSION = "app/apk/version.txt "; //检查版本更新
//    public final static String APK = "app/apk/DelaTraffic.apk"; //更新时，下载的APK包
//    public final static String LOGIN = "front/hn/login!login";//接收验证码短信服务
    public final static String REGISTER = "front/hn/login!register";//注册（获取验证码）
    public final static String CHECK_RANDOM_NUMBER = "front/hn/login!checkRandomNumber";//随机码校验

    //新开的接口：用来检测密码是否正确的（在亲密添加中使用。调用这个接口不会更改后台数据库中的token）
    public final static String CHECK_USER_PWD = "front/hn/login!checkUserPassword";

    public final static String GETLOCALPHONENUM = "front/hn/common!getPhoneServer";//本机号码捕获服务
    public final static String GET_FLOW = "front/hn/busi!jyList";//获得加油列表
    public final static String BUY_FLOW = "front/hn/busi!dgjy";//购买加油包

    //购买加油包新接口
    public final static String BUY_FLOW2 = "front/hn/busi!prodOrder";


    public final static String FEED_BACK_REPLY = "front/hn/busi!getFeedbackAndReply";//反馈建议含回复

    public final static String FEED_BACK = "front/hn/busi!addFeedback";//反馈建议
    public final static String REAL_CHARGE = "front/hn/busi!getRealFee";//获取实时话费
    public final static String INTEGRAL = "front/hn/busi!getPoints";//获取积分
    public final static String GET_BILL = "front/hn/busi!getBillSum";//获取账单
    public final static String GET_PAY_RECORD = "front/hn/busi!getPayRecord";//获取缴费记录
    public final static String GET_KNOW = "front/hn/busi!queryKnowledgeListNew";//获取4G知识
    public final static String INTEGRAL_TRAFFIC = "http://jfwap.10086.cn/product/view?productType=0";//积分换流量
    public final static String TRAFFIC_MORE = "http://www.sojump.com/jq/4567442.aspx";

    public final static String SHARE_BACK_TWO = "front/hn/busi2!getShareAppConent";//分享2

    //积分兑换话费
    public final static String GET_EXCHANGE_MONEY = "front/hn/busi!queryPointToFee";
    //积分兑换流量
    public final static String GET_EXCHANGE_TRAFFIC = "front/hn/busi!queryPointToFlows";

    //积分兑换通话
    public final static String GET_EXCHANGE_TONGHUA = "front/hn/busi!queryPointToCall";

    //流量加油包
    public final static String GET_JiaYouBao = "front/hn/busi!buyJybFlows";
    //流量可选包
    public final static String GET_KeXuanBao = "front/hn/busi!buyKxbFlows";

    //流量跨月包
    public final static String GET_KuaYueBao = "front/hn/busi2!findFlowPackages";
    //获取流量包信息
    public final static String GET_FLOWPkg = "front/hn/busi!getFlowPkgDetail";
    //发送异常信息
//    public final static String SEND_CRASH_INFO = "front/hn/common!sendMail";


    public final static String GET_HuoDong = "front/hn/busi!getActivityListToApp";//获取优惠活动列表


    public final static String SEND_CRASH_INFO = "front/hn/busi2!saveException";//接收崩溃异常日志的接口
    //4G自选套餐
    public final static String GET_4G_CHOOSE_MEAL_BY_SELF_DATA = "front/hn/busi!getFourOptional";
    //4G上网套餐
    public final static String GET_4G_INTERNER_MEAL = "front/hn/busi!getFourGSurfNet";
    //4G飞享套餐
    public final static String FourG_4G_FENXIANG = "front/hn/busi!getFourGFly";
    //4G商旅套餐
    public final static String FourG_4G_SHANGLV = "front/hn/busi!getFourGBusi";
    //4G套餐办理接口
    public final static String FourG_MEAL_BANLI = "front/hn/busi!changeFourGPlan";
    //查询已定业务
    public final static String Business_Query = "front/hn/busi!getAllBusi";
    //流量共享 获取是否开通流量共享，共享套餐ID
    public final static String Flow_Share_Status = "front/hn/busi2!shareFlows";
    //流量共享  4G套餐多终端共享使用明细查询
    public final static String Flow_Share_Detail = "front/hn/busi2!shareDetail";
    //流量共享  4G套餐多终端共享成员查询
    public final static String Flow_Share_Select_Member = "front/hn/busi2!selectMember";
    //流量共享  4G套餐多终端共享成员管理
    public final static String Flow_Share_Manage_Member = "front/hn/busi2!manageMember";
    //退订增值业务（15/11/11再次使用的）
    public final static String CANCE_BUSI = "front/hn/busi2!cancelBusi";

    //我的消息接口
    public final static String MY_NEWS = "front/hn/busi2!getPushMessage";

    //山东推广小图1
    public final static String SHANGDONG_TUIGUANG_SMALL_ICON1 = "img/specialOffer/531/youhui_1.jpg";
    //山东推广小图2
    public final static String SHANGDONG_TUIGUANG_SMALL_ICON2 = "img/specialOffer/531/youhui_2.jpg";
    //山东推广小图3
    public final static String SHANGDONG_TUIGUANG_SMALL_ICON3 = "img/specialOffer/531/youhui_3.jpg";
    //山东推广大图1
    public final static String SHANGDONG_TUIGUANG_BIG_ICON1 = "img/specialOffer/531/shandongtuiguang1.jpg";
    //山东推广大图2
    public final static String SHANGDONG_TUIGUANG_BIG_ICON2 = "img/specialOffer/531/shandongtuiguang2.jpg";
    //山东推广大图3
    public final static String SHANGDONG_TUIGUANG_BIG_ICON3 = "img/specialOffer/531/shandongtuiguang3.jpg";
    //青海推广小图
    public final static String QINGHAI_TUIGUANG_SMALL_ICON4 = "img/specialOffer/971/activity1.jpg";

    public final static String GET_RECOMMEND_FLOW = "front/hn/busi!recommendFlow";//推荐流量包和上网套餐
    //爱流量中获取加密的账号
    public final static String GET_ENCRYPTION_NUMBER = "AESEncoding";

    public final static String QUREY_FAMILYUSERS_BYMASTERPHONE = "front/hn/busi!qureyFamilyUsersByMasterPhone";//亲密号码查询
    public final static String ADD_FAMILYUSERS = "front/hn/busi!saveFamilyUsers";//亲密号码添加
    public final static String UPDATE_FAMILYUSERS = "front/hn/busi!updateFamilyUsers";//亲密号码更新
    public final static String DELETE_FAMILYUSERS_BYMASTERANDMEMBERPHONE = "front/hn/busi!deleteFamilyUsersByMasterAndMemberPhone";//亲密号码删除
    public final static String UPLOAD_RELATIVE_POHOTO = "front/hn/busi!uploadPic";//亲密头像上传
    public final static String QUERY_RELATIVTPEOPLEADDRECORD = "front/hn/busi!smsFamilyTempState";//亲密添加历史记录的接口
    public final static String ADD_RELATIVEPEOPLE = "front/hn/busi!smsFamily";//亲密添加新接口(通过短信平台发送短信验证，不是通过服务密码添加)


    public final static String UPLOAD_USER_POHOTO = "front/hn/busi!uploadPic";//用户头像上传
    public final static String UPDATE_USER_INFO = "front/hn/busi!updateHeadPic";//用户信息更新

    // 4G 内部版本
//  public final static String HOSTV = "http://211.138.16.140:80/gfms/app/apk/version.txt";//检查
//  public final static String HOSTD = "http://211.138.16.140:80/gfms/app/apk/4GTraffic.apk";//下载
//  public final static String HOST_HTML = "http://211.138.16.140:80/gfms/app/apk/html/4GDownload.html";//html
//    public final static String HOSTV = "http://wx.10086.cn/gfms/app/apk/version.txt";//检查更新
    public final static String HOSTV = "http://wx.10086.cn/gfms/front/hn/busi3!checkVersionUpdate";//检查更新
    public final static String HOSTD = "http://wx.10086.cn/gfms/app/apk/4GTraffic.apk";//下载apk
    public final static String HOST_HTML = "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html";//html
    public final static String USER_FLAG = "4G";//用户注册标示


    //我的邀请码功能中：获取邀请码的接口
    public final static String GET_INVITE_CODE = "front/hn/busi2!getPersonalInviteCode";//获取邀请码的接口
    public final static String GET_INVITE_RANKING = "front/hn/busi2!getStatisticsInviteCodes";//邀请排行数据集合的接口
    public final static String GET_MY_INVITE = "front/hn/busi2!getInviteCodes";//我的邀请数据集合的接口
    public final static String SAVE_INVITE_CODE = "front/hn/busi2!saveInviteCode";//我的邀请中保存邀请码的接口

    //获取短信指令的接口
    public final static String GET_SMS_COMMANDS = "front/hn/busi2!getSmsCommands";//获取短信指令的接口


    public final static String IDENTITY_AUTHENTICATION = "front/hn/busi2!authentication";//身份认证的接口
    public final static String QUERY_DETAIL_RECORD = "front/hn/busi2!getDetailedRecord";//客户端详单查询接口


    public final static String GET_CUSTOMER_INFO = "front/hn/busi2!getCustInfo";//获取用户信息的接口
    public final static String UPDATE_CUSTOMER_INFO = "front/hn/busi2!updateCustInfo";//更新用户信息的接口

    public final static String RECHARGE_MEAL = "flowChargeServlet";//流量充值的接口，实际上是一个网页的链接

    public final static String QUERY_BUSINESS = "front/hn/busi2!uniQuery";//查询可以退订的增值业务列表


    public final static String QUERY_139ISEXIST = "front/hn/busi3!queryUserMail";//查询用户的139账单是否存在
    public final static String GET_DATD_FROM_139 = "front/hn/busi3!excuteMailUrl";//从服务器获取139邮箱的账单
    public final static String QUERY_USER_NOTICE = "front/hn/busi4!queryUserNotice";//查询用户的亲密提醒
    public final static String UPDATE_USER_NOTICE = "front/hn/busi4!updateUserNotice";//更新用户的亲密提醒
    public final static String GET_FLOW_TYPE = "front/hn/busi4!getFlowTypeOfPackage";//获取流量配置数据


    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static final String BASE_URL;

    static {
        BASE_URL = "http://wx.10086.cn/gfms/";//主机域名
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
