package com.mobile.younthcanteen.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobile.younthcanteen.activity.CanteenApplication;
import com.mobile.younthcanteen.bean.DownLoadBean;
import com.mobile.younthcanteen.bean.GoodsTypeBean;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesUtil {
    //记录项目中其他需要记录的字段
    public static final String Share_Preferences_Name_Private = "4GManger_Private";

    public static final String POINT = "point"; //剩余积分

    public static final String MONEY = "money"; //剩余余额

    public static final String TOKEN = "token"; //登录成功后后台会传过来一个token，这里保存起来，掉其他接口时需要

    public static final String ISPAYPWDSET = "ispaypwdset"; //是否设置过支付密码

    public static final String KEY_ACCOUNT = "KEY_ACCOUNT";//手机号

    public static final String KEY_USERID = "KEY_USERID";//userid
    public static final String KEY_NICKNAME = "KEY_NICKNAME";//nickname
    public static final String KEY_USERICON = "KEY_USERICON";//usericonurl
    public static final String KEY_DOWNLODEMSG = "downlodemsg"; //已下载安装包的信息

    public static final String KEY_HOME_TYPE_LIST = "hometypelist"; //商品类别信息

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
     * 保存当前账号是否设置了支付密码
     * @return
     */
    public static boolean setIsSetPayPwd(boolean isSetPwd) {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().edit().putBoolean(ISPAYPWDSET, isSetPwd).commit();
        }
        return false;
    }


    /**
     * 获取当前账号是否设置了支付密码
     *
     * @return
     */
    public static boolean getIsSetPayPwd() {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().getBoolean(ISPAYPWDSET, false);
        }
        return false;
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
     * 保存nickName
     * @return
     */
    public static boolean setUserIconUrl(String userIconUrl) {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().edit().putString(KEY_USERICON, userIconUrl).commit();
        }
        return false;
    }

    /**
     * 获取当前nickName
     *
     * @return
     */
    public static String getUserIconUrl() {
        if (getPreferencesPrivate() != null) {
            return getPreferencesPrivate().getString(KEY_USERICON, "");
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


    /**
     * 保存商品类别列表的相关数据
     *
     * @param infos
     * @return
     */
    public static boolean saveGoodsType2SP(List<GoodsTypeBean> infos) {
        if (getPreferencesPrivate() != null) {
            Gson gson = new Gson();
            return getPreferencesPrivate().edit().putString(KEY_HOME_TYPE_LIST,
                    gson.toJson(infos)).commit();
        }
        return false;
    }

    /**
     * 获取商品类别的相关信息
     *
     * @return
     */
    public static List<GoodsTypeBean> getGoodsTypeFromSP() {
        Gson gson = new Gson();
        List<GoodsTypeBean> infos = null;
        if (getPreferencesPrivate() != null) {
            String saveStr = getPreferencesPrivate().getString(KEY_HOME_TYPE_LIST, "");
            //读出以前保存的集合
            if (TextUtils.isEmpty(saveStr)) {
                //如果以前没有相关的信息
                infos = new ArrayList<GoodsTypeBean>();
            } else {
                //如果以前sp中存储的有相关信息
                infos = gson.fromJson(saveStr, new TypeToken<List<GoodsTypeBean>>() {
                }.getType());
            }
        }
        return infos;
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
