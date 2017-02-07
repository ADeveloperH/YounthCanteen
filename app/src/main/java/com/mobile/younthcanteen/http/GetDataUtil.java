package com.mobile.younthcanteen.http;

import android.content.Context;
import android.widget.Toast;

import com.mobile.younthcanteen.util.NetWorkUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据获取类
 */
public class GetDataUtil {

    private String result = null;//获取的数据
    private SimpleDateFormat sdf;//时间格式
    private String number = "";//当前登录账户
    private String date = "";//日期
    private Context context;//上下文


    private com.mobile.younthcanteen.http.OnGetDataListener onGetDataListener = null;

    public void setOnGetDataListener(com.mobile.younthcanteen.http.OnGetDataListener listener) {
        this.onGetDataListener = listener;
    }

    /**
     * 需要数据库存储的，一定要用这个构造函数
     *
     * @param context
     */
    public GetDataUtil(Context context) {
        super();
        this.context = context;
        number = SharedPreferencesUtil.getPhoneNumber(); //得到登录手机号
        sdf = new SimpleDateFormat("yyyyMMdd");
        date = sdf.format(new Date());
    }


    /**
     * 获取已订业务中可退订的列表
     *
     * @param phoneNumber
     * @param isShowRemind
     */
    public void getUnsubscribeBusiness(String phoneNumber, boolean isShowRemind, String moduleId) {
        String url = com.mobile.younthcanteen.http.Http.QUERY_BUSINESS;
        com.mobile.younthcanteen.http.RequestParams params = new com.mobile.younthcanteen.http.RequestParams();
        params.put("phoneNumber", phoneNumber);
        params.put("moduleId", moduleId);
        if (isShowRemind) {
            getData(context, "Get", url, params, "正在加载...");
        } else {
            getData(context, "Get", url, params, null);
        }
    }


    /**
     * 获取数据（本地数据、服务器数据）
     *
     * @param context
     * @param requestType   请求方式 （Http类中的四个方法:Get,Post,GetTemp,PostTemp）
     * @param url           接口地址
     * @param params        参数
     * @param dialogMessage 带进度条的弹出框提示语
     *                      true表示对网络获取数据后有操作，result表中有数据的话则更新数据，result表中没有数据则插入
     *                      false表示对网络获取数据后没有操作
     * @return
     */
    private void getData(Context context, String requestType, String url, com.mobile.younthcanteen.http.RequestParams params, String dialogMessage) {
        //网络请求
        getServiceData(context, requestType, url, params, dialogMessage);
    }

    /**
     * 网络请求，获取服务器数据
     *
     * @param context
     * @param requestType   请求方式 （Http类中的四个方法:Get,Post,GetTemp,PostTemp）
     * @param url           接口地址
     * @param params        参数
     * @param dialogMessage 带进度条的弹出框提示语
     * @return
     */
    private String getServiceData(Context context, String requestType, String url, com.mobile.younthcanteen.http.RequestParams params, String dialogMessage) {

        if (context != null && NetWorkUtil.isNetworkAvailable(context)) {

            if (null == params) {
                params = new com.mobile.younthcanteen.http.RequestParams();
            }

            if (requestType.equals("Get")) {
                com.mobile.younthcanteen.http.Http.get(url, params, getMyTextAsyncResponseHandler(context, url, dialogMessage));
            } else if (requestType.equals("Post")) {
                com.mobile.younthcanteen.http.Http.post(url, params, getMyTextAsyncResponseHandler(context, url, dialogMessage));
            } else if (requestType.equals("GetTemp")) {
                com.mobile.younthcanteen.http.Http.getTemp(url, params, getMyTextAsyncResponseHandler(context, url, dialogMessage));
            } else if (requestType.equals("PostTemp")) {
                com.mobile.younthcanteen.http.Http.postTemp(url, params, getMyTextAsyncResponseHandler(context, url, dialogMessage));
            }
        } else {
            Toast.makeText(context, "您当前网络不可用，请检测您的网络设置", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private com.mobile.younthcanteen.http.MyTextAsyncResponseHandler getMyTextAsyncResponseHandler(Context context, final String url, String dialogMessage) {

        return new com.mobile.younthcanteen.http.MyTextAsyncResponseHandler(context, dialogMessage) {

            @Override
            public void onStart() {
                super.onStart();

                if (onGetDataListener != null) {
                    onGetDataListener.onGetDataStart();
                }
            }

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (content != null && !"".equals(content)) {
                    result = content;
                    if (onGetDataListener != null) {
                        onGetDataListener.onGetDataSuccess(url, result);
                    }
                } else {
                    //返回的content的无内容就当失败处理
                    if (onGetDataListener != null) {
                        onGetDataListener.onGetDataFailure();
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (onGetDataListener != null) {
                    onGetDataListener.onGetDataFinish();
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                if (onGetDataListener != null) {
                    onGetDataListener.onGetDataFailure();
                }
            }
        };
    }


}
