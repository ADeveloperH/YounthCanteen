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

    public static final String TOKEN = "token"; //登录成功后后台会传过来一个token，这里保存起来，掉其他接口时需要

    public static final String Key_Phone_Number = "Key_Phone_Number";//手机号

    private static Context mcontext;
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

    /**
     * 保存token
     * @return
     */
    public static boolean setToken(String token) {
        if (getPreferencesToken() != null) {
            return getPreferencesToken().edit().putString(TOKEN, token).commit();
        }
        return false;
    }

    /**
     * 获取当前token
     *
     * @return
     */
    public static String getToken() {
        if (getPreferencesToken() != null) {
            return getPreferencesToken().getString(TOKEN, "");
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
