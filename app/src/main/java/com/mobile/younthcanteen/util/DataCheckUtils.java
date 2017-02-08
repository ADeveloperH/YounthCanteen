package com.mobile.younthcanteen.util;

import android.text.TextUtils;

/**
 * author：hj
 * time: 2017/2/8 0008 10:42
 */

public class DataCheckUtils {
    public static boolean isValidatePhone(String phnoeNumber) {
        return !TextUtils.isEmpty(phnoeNumber) && phnoeNumber.startsWith("1") && (phnoeNumber.length() == 11);
    }

}
