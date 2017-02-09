package com.mobile.younthcanteen.util;

import android.text.TextUtils;

/**
 * author：hj
 * time: 2017/2/8 0008 17:04
 */

public class LoginUtils {
    /**
     * 判断用户是否登录
     * @return
     */
    public static boolean isLogin() {
        String phoneNumber = SharedPreferencesUtil.getAccount();
        String token = SharedPreferencesUtil.getToken();
        String userId = SharedPreferencesUtil.getUserId();
        String nickName = SharedPreferencesUtil.getNickName();
        return !TextUtils.isEmpty(phoneNumber) &&
                !TextUtils.isEmpty(token) &&
                !TextUtils.isEmpty(userId)&&
                !TextUtils.isEmpty(nickName);
    }
}
