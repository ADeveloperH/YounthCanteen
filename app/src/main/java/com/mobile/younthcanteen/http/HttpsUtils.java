package com.mobile.younthcanteen.http;


public class HttpsUtils {

//    public final static String HOST = "https://221.176.66.244/gfms";
    public final static String HOST = "http://wx.10086.cn/gfms";//改为HTTP形式

    public final static String QUERY_DETAIL_RECORD = "/front/hn/busi2!getDetailedRecord";//详单查询

    public static String getAbsoluteUrl(String relativeUrl) {
        return HOST + relativeUrl;
    }
}
