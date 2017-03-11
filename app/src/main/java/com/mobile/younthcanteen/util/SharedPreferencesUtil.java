package com.mobile.younthcanteen.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mobile.younthcanteen.activity.CanteenApplication;
import com.mobile.younthcanteen.bean.DownLoadBean;

public class SharedPreferencesUtil {
    //记录项目中其他需要记录的字段
    public static final String Share_Preferences_Name_Private = "4GManger_Private";

    public static final String POINT = "point"; //剩余积分

    public static final String MONEY = "money"; //剩余余额

    public static final String TOKEN = "token"; //登录成功后后台会传过来一个token，这里保存起来，掉其他接口时需要

    public static final String KEY_ACCOUNT = "KEY_ACCOUNT";//手机号

    public static final String KEY_USERID = "KEY_USERID";//userid
    public static final String KEY_NICKNAME = "KEY_NICKNAME";//nickname
    public static final String KEY_DOWNLODEMSG = "downlodemsg"; //已下载安装包的信息

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


//===========================================getPreferencesLogin	START====================================//


    /**
     * 保存当前登陆账号
     * @return
     */
    public static boolean setAccount(String phoneNumber) {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().edit().putString(KEY_ACCOUNT, phoneNumber).commit();
        }
        return false;
    }


    /**
     * 获取当前登陆账号
     *
     * @return
     */
    public static String getAccount() {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().getString(KEY_ACCOUNT, "");
        }
        return "";
    }

    /**
     * 保存token
     * @return
     */
    public static boolean setToken(String token) {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().edit().putString(TOKEN, token).commit();
        }
        return false;
    }

    /**
     * 获取当前token
     *
     * @return
     */
    public static String getToken() {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().getString(TOKEN, "");
        }
        return "";
    }




    /**
     * 保存money
     * @return
     */
    public static boolean setMoney(String money) {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().edit().putString(MONEY, money).commit();
        }
        return false;
    }

    /**
     * 获取当前money
     *
     * @return
     */
    public static String getMoney() {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().getString(MONEY, "");
        }
        return "";
    }


    /**
     * 保存point
     * @return
     */
    public static boolean setPoint(String point) {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().edit().putString(POINT, point).commit();
        }
        return false;
    }

    /**
     * 获取当前point
     *
     * @return
     */
    public static String getPoint() {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().getString(POINT, "");
        }
        return "";
    }




    /**
     * 保存userid
     * @return
     */
    public static boolean setUserId(String userid) {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().edit().putString(KEY_USERID, userid).commit();
        }
        return false;
    }

    /**
     * 获取当前userid
     *
     * @return
     */
    public static String getUserId() {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().getString(KEY_USERID, "");
        }
        return "";
    }

    /**
     * 保存nickName
     * @return
     */
    public static boolean setNickName(String nickName) {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().edit().putString(KEY_NICKNAME, nickName).commit();
        }
        return false;
    }

    /**
     * 获取当前nickName
     *
     * @return
     */
    public static String getNickName() {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().getString(KEY_NICKNAME, "");
        }
        return "";
    }


    /**
     * 保存已下载安装包的信息
     *
     * @return
     */
    public static boolean setDownMsg(DownLoadBean downLoadBean) {
        if (getPreferencesPrivate() != null) {
            String beanStr = "";
            if (downLoadBean != null) {
                beanStr = new Gson().toJson(downLoadBean);
            }
            return getPreferencesPrivate().edit().putString(KEY_DOWNLODEMSG, beanStr).commit();
        }
        return false;
    }

    /**
     * 获取已下载安装包的信息
     *
     * @return
     */
    public static DownLoadBean getDownMsg() {
        if (getPreferencesPrivate() != null) {
            String downMsgStr = getPreferencesPrivate().getString(KEY_DOWNLODEMSG, "");
            if (TextUtils.isEmpty(downMsgStr)) {
                return null;
            } else {
                return new Gson().fromJson(downMsgStr, DownLoadBean.class);
            }
        }
        return null;
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
