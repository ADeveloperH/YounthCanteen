package com.mobile.younthcanteen.util;

/**
 * Created by Administrator on 2015/5/7.
 */

import java.security.MessageDigest;

public class MD5Utils {

    /**
     * MD5加密
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
//            byteArray = (byte) charArray;
            String temp=String.valueOf(charArray);
            byteArray=temp.getBytes();
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int mask=0xff;
            int temp=0;
            int n=0;
            for(int j=0;j<4;j++){
                n<<=8;
                temp=md5Bytes[j]&mask;
                n|=temp;
            }

            int val = n & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
