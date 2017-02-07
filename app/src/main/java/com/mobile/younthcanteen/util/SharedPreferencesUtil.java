package com.mobile.younthcanteen.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobile.younthcanteen.activity.CanteenApplication;

public class SharedPreferencesUtil {

    // 记录登录成功后的个人信息相关
    public static final String Share_Preferences_Name_Login = "4GManager_Login";
    //记录友盟的device_token和服务器端返回的token
    public static final String Share_Preferences_Name_Token = "4GManager_Token";
    //记录历史记录。如详单中的搜索历史
    public static final String Share_Preferences_Name_SearchHistory = "4GManger_Search_History";
    //首页和服务页面功能模块的缓存json串
    public static final String Share_Preferences_Name_ModularListHistory = "4GManger_ModularList_History";
    //记录项目中其他需要记录的字段
    public static final String Share_Preferences_Name_Private = "4GManger_Private";
    //记录项目中一键检测相关内容的缓存数据
    public static final String Share_Preferences_Name_OneKeyCheck_HistoryData = "4GManger_OneKeyCheck_History";

    public static final String Key_Is_First_Into_LOGIN = "Key_Is_First_Into_Login";//是否是第一次进入服务密码登录页面

    public static final String DOWNLODEMSG = "downlodemsg"; //已下载安装包的信息

    public static final String TIMEOFGETREALCHARGE = "timeofgetrealcharge"; //一键检测获取余额接口成功的时间

    public static final String KEY_CUR_FEE = "key_cur_fee"; //一键检测获取的余额数据

    public static final String TIMEOFGETBILLDATA = "timeofgetrealcharge"; //一键检测获取账单接口成功的时间
    public static final String KEY_BILL_DATA = "key_bill_data"; //一键检测获取账单接口数据

    public static final String TIMEOFMEALDATA = "timeofmealdata"; //一键检测获取套餐接口成功的时间
    public static final String KEY_MEAL_DATA = "key_meal_data"; //一键检测获取套餐接口数据

    public static final String TIMEOFPOINT = "timeofpoint"; //一键检测获取剩余积分接口成功的时间
    public static final String KEY_POINT = "key_point"; //一键检测获取剩余积分接口数据

    public static final String TIMEOFYIDING = "timeofyiding"; //一键检测获取已订业务接口成功的时间
    public static final String KEY_YIDING = "key_yiding"; //一键检测获取已订业务接口数据

    public static final String TIMEOFTUIDING = "timeoftuiding"; //一键检测获取可退订业务接口成功的时间
    public static final String KEY_TUIDING = "key_tuiding"; //一键检测获取可退订业务接口数据

    public static final String ISDIALOGSHOW = "isdialogshow"; //标志位：重新登录的对话框是否显示true表示已经显示 false表示未显示

    public static final String MODULARLISTHISTORY = "modularlisthistory";//首页和服务中功能模块的缓存
    public static final String ISREMAINERGETINHOMEPAGE = "isremainergetinhomepage";//首页中剩余流量是否获取到了
    public static final String ISSPENDGETINHOMEPAGE = "isspendgetinhomepage";//首页中剩余话费是否获取到了
    public static final String ISINTEGRALGETINHOMEPAGE = "isintegralgetinhomepage";//首页中剩余积分是否获取到了

    public static final String DEVICE_TOKEN = "device_token"; //友盟生成的唯一的device_token登录时需要将其传过去
    public static final String TOKEN = "token"; //登录成功后后台会传过来一个token，这里保存起来，掉其他接口时需要

    public static final String EncryptionPhoneNumber = "encryptionPhoneNumber"; //

    public static final String LOGIN_MODE = "login_mode"; //登录的模式：0表示用服务密码登录 1表示动态密码登录
    public static final String NOTIFICATION_ISREFRESHING = "notification_isrefreshing"; //通知栏是否正在刷新刷新
    public static final String ISREMAINERGET = "isremainerget";//通知栏剩余流量是否获取到了
    public static final String ISSPENDGET = "isspendget";//通知栏剩余话费是否获取到了

    public static final String ISFIRSTSHOWNOTIFICATION = "isfirstshownotification"; //是否是第一次显示更新的notification在通知栏上


    public static final String NOTICE_BG_COLOR_TYPE = "0"; //通知栏背景颜色类型

    public static final String SCREEN_WIDTH = "0";//屏幕宽度
    public static final String SCREEN_HEIGHT = "0"; //屏幕高度


    //引导页面、登陆页面
    public static final String Key_Is_First_Into_App = "Key_Is_First_Into_App";//是否是第一次进入app
    public static final String Key_Is_Login = "Key_Is_Login";//是否登录
    public static final String Key_Is_First_Login = "Key_Is_First_Login";//是否是第一次登陆
    public static final String Key_Phone_Number = "Key_Phone_Number";//手机号
    public static final String Key_Sex = "Key_Sex";//手机号
    public static final String Key_Province_Code = "Key_Province_Code";//省编码
    public static final String Key_Is_Remember_Password = "Key_Is_Remember_Password";//是否记住密码
    public static final String Key_Password = "Key_Password";//登陆密码

    public static final String Main_Meal = "Main_Meal";//主套餐

    public static final String Key_Is_Open_Guide = "Key_Is_Open_Guide";//是否打开引导页
    public static final String KEY_LOGIN_INFOS = "key_login_infos";//记住手机号和密码的信息集合


    public static final String EVERY_MEAL_INFOS = "every_Meal_Infos"; //套餐信息

    //首页
    public static final String Key_Flow_Total = "Key_Flow_Total";//总流量
    public static final String Key_Flow_Remain = "Key_Flow_Remain";//剩余流量
    public static final String Key_Flow_Rest_Rate = "Key_Flow_Rest_Rate";//流量剩余的百分比
    public static final String Key_Fee_Current_Month = "Key_Fee_Current_Month";//本月话费
    public static final String Key_Fee_Remain = "Key_Fee_Remain";//话费剩余

    public static final String Key_Voice_total = "Key_Voice_total";//语音总量

    public static final String Key_Voice_Remain = "Key_Voice_Remain";//语音剩余
    public static final String Key_Point = "Key_Point";//积分START_KEY_ISFIRSTInToMAINACTIVITY
    public static final String Key_Is_First_Into_NewMyActivity = "Key_Is_First_Into_NewMyActivity";//是否第一次进入NewMyActivity
    public static final String Key_Is_Real_Time_Get_Fee_Province = "Key_Is_Real_Time_Get_Fee_Province"; //是否是实时获取话费的省份

    //设置
    public static final String Key_Is_Open_Over_Meal = "Key_Is_Open_Over_Meal";//是否超套餐断网
    public static final String Key_Is_Open_Flow_Over_Remind = "Key_Is_Open_Flow_Over_Remind";//是否允许通知
    public static final String Key_Flow_Over_Remind_Rate = "Key_Flow_Over_Remind_Rate";//流量超额提醒百分比
    public static final String Key_Is_Open_Lock_Screen = "Key_Is_Open_Lock_Screen";//是否锁屏
    public static final String Key_Is_Open_Allow_Notification = "Key_Is_Open_Allow_Notification";//是否打开允许通知
    public static final String Key_Is_Open_Pull_Down = "Key_Is_Open_Pull_Down";//是否允许显示下拉通知栏
    public static final String Key_Is_Open_Wifi_Download = "Key_Is_Open_Wifi_Download";//是否允许WiFi下自动下载
    public static final String Key_Is_Open_Harass = "Key_Is_Open_Harass";//是否打开骚扰拦截

    //流量提醒页面
    public static final String Key_Is_Open_Out_Of_Meal_Flow_Remind = "Key_Is_Open_Out_Of_Meal_Flow_Remind";//是否打开流量超套餐提醒
    public static final String Key_Is_Open_Set_Flow_Remind = "Key_Is_Open_Set_Flow_Remind";//是否打开流量超出设置限额提醒
    public static final String Key_Is_Open_Day_Flow_Limit = "Key_Is_Open_Day_Flow_Limit";//是否打开每日流量超额提醒
    public static final String Key_Day_Flow_Limit = "Key_Day_Flow_Limit";//每日流量限额

    public static final String Key_Lin_Flow_Limit = "Key_Lin_Flow_Limit";//剩余流量超额限制

    public static final String Key_Is_Open_Flow_Alarm = "Key_Is_Open_Flow_Alarm";//是否打开流量闹铃

    //其他
    public static final String Key_Is_Multi_Term = "Key_Is_Multi_Term";//当前用户是否开通流量共享（只要4g套餐时才有权限）
    public static final String Key_Time_Line_State = "Key_Time_Line_State";//记录管家说状态（是否有最新）
    public static final String Key_Net_Work_State = "Key_Net_Work_State";//记录上一次网络状态

    //通知栏
    public static final String Key_Is_Open_Notify_Status = "Key_Is_Open_Notify_Status";//通知栏是否自启动
    public static final String LastVersion = "last_version";
    public static final String Can_Read_Caontact = "Can_Read_Caontact";

    public static final String Key_Is_Open_Sarao = "Key_Is_Open_Saorao";//通知栏是否自启动

    public static final String Key_Is_Open_Duanxin_Status = "Key_Is_Open_Duanxin_Status";//智能是否自启动

//    public static final String Key_Is_ZhaPian_Status = "Key_Is_ZhaPian_Status";//诈骗短信引导图是否查看

    //流量排行
    public static final String Key_Is_Only_Wifi_NetWorking = "key_id_only_wifi_net";//是否设置仅wifi联网
    public static final String Key_Ban_On_NetWorking = "key_ban_on_networking";//是否设置禁止联网

    //我的邀请码
    public static final String Key_My_Id = "key_my_id";//我的邀请码中的我的id
    public static final String Key_My_Invite_Code = "key_my_invite_code";//我的邀请码中的我的邀请码

    public static final String Key_USER_ICON_URL = "key_user_icon_url";//我头像的地址链接
    //搜索详单中的搜索历史手机号码
    public static final String KEY_SEARCH_HISTORY_PHONENUMS = "key_search_history_phonenums";
    //弹出软键盘的高度
    public static final String KRYBOARD_HEIGHT = "keyboard_height";

    //新手引导图的图片版本。如果图片版本改变会显示新手引导
    public static final String USEHELPVIEWVERSION = "usehelpviewversion";

    //启动图后引导图的图片版本。如果图片版本改变会显示引导图
    public static final String GUIDEVERSION = "guideversion";

    private static Context mcontext;
    private static final String PROVICE_NAME = "province_name";

    static {
        mcontext = CanteenApplication.getInstance();
    }

    /**
     * 该配置文件记录项目中其他需要记录的字段
     */
    public static SharedPreferences getPreferencesPrivate() {
        if (mcontext != null) {
            return mcontext.getSharedPreferences(Share_Preferences_Name_Private, Context.MODE_MULTI_PROCESS);
        }
        return null;
    }

    /**
     * 该配置文件记录友盟的device_token和服务器端返回的token
     */
    public static SharedPreferences getPreferencesToken() {
        if (mcontext != null) {
            return mcontext.getSharedPreferences(Share_Preferences_Name_Token, Context.MODE_MULTI_PROCESS);
        }
        return null;
    }

    /**
     * 该配置文件记录登录成功后的个人信息相关
     */
    public static SharedPreferences getPreferencesLogin() {
        if (mcontext != null) {
            return mcontext.getSharedPreferences(Share_Preferences_Name_Login, Context.MODE_MULTI_PROCESS);
        }
        return null;
    }

    /**
     * 该配置文件记录首页和服务页的功能模块的缓存
     */
    public static SharedPreferences getPreferencesModularListHistory() {
        if (mcontext != null) {
            return mcontext.getSharedPreferences(Share_Preferences_Name_ModularListHistory, Context.MODE_MULTI_PROCESS);
        }
        return null;
    }

    /**
     * 该配置文件记录搜索的历史记录。如详单中的搜索历史
     */
    public static SharedPreferences getPreferencesSearchHistory() {
        if (mcontext != null) {
            return mcontext.getSharedPreferences(Share_Preferences_Name_SearchHistory, Context.MODE_MULTI_PROCESS);
        }
        return null;
    }

    /**
     * 该配置文件记录一键检测相关的缓存
     */
    public static SharedPreferences getPreferencesOneKeyCheckHistory() {
        if (mcontext != null) {
            return mcontext.getSharedPreferences(Share_Preferences_Name_OneKeyCheck_HistoryData, Context.MODE_MULTI_PROCESS);
        }
        return null;
    }


//===========================================getPreferencesLogin	START====================================//


    /**
     * 保存当前登陆账号
     * @return
     */
    public static boolean setPhoneNumber(String phoneNumber) {
        if (getPreferencesLogin() != null) {
            return getPreferencesLogin().edit().putString(Key_Phone_Number, phoneNumber).commit();
        }
        return false;
    }


    /**
     * 获取当前登陆账号
     *
     * @return
     */
    public static String getPhoneNumber() {
        if (getPreferencesLogin() != null) {
            return getPreferencesLogin().getString(Key_Phone_Number, "");
        }
        return "";
    }
//==============================getPreferencesLogin	END=========================================//

    /**
     * 清除保存的数据(不包括第一次登陆)
     */
    public static boolean clear() {
        return getPreferencesPrivate().edit()
                .clear().commit();
    }
}
