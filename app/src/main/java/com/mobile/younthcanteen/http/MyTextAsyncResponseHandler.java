package com.mobile.younthcanteen.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class MyTextAsyncResponseHandler extends MyCallback {
    private static final String TAG = "MyTextAsyncResponseHandler";

    protected Context context;
    private ProgressDialog loading;
    private String loadingMessage;
    private boolean cancel;

    public MyTextAsyncResponseHandler(Context context, String loadingMessage) {
        if (null == context) {
            throw new IllegalArgumentException("context can't be null");
        }
        this.context = context;
        this.loadingMessage = loadingMessage;
        onStart();
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        super.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        super.onFailure(call, t);
    }

    @Override
    public void onStart() {
        if (!TextUtils.isEmpty(loadingMessage)) {
            try {
                if(null!=context){
                    loading = ProgressDialog.show(context, "", loadingMessage, true, false , new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            try {
                                if(null!=context){
                                    loading.dismiss();
                                    cancel = true;
                                }
                            } catch (Exception e) {
                            }
                        }
                    });

                    loading.setOnKeyListener(new DialogInterface.OnKeyListener() {

                        @Override
                        public boolean onKey(DialogInterface dialog,
                                             int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                loading.dismiss();
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cancel = false;
        }
    }

    @Override
    public void onSuccess(String content) {
        /**
         * 这里判断，如果此时的token已经失效了就提醒账户重新登录
         */
//        if(!TextUtils.isEmpty(content)){
//            try {
//                JSONObject jsonObject = new JSONObject(content);
//                if(jsonObject.has("Returncode")){
//                    String returnCode = jsonObject.getString("Returncode");
//                    String returnMessage = jsonObject
//                            .getString("Returnmessage");
//                    if ("444".equals(returnCode)) {
//                        ResponseHandlerUtil.handleRelogin(context,returnMessage);
////						ResponseHandlerUtil.handleExceptionState(context,returnMessage);
//                        return;
//                    } else if ("222".equals(returnCode)) {
//                        ResponseHandlerUtil.handleExceptionState(context,returnMessage);
//                        return;
//                    }else if ("944".equals(returnCode)) {
//                        //非法请求
//                        ToastUtils.showLongToast(returnMessage);
//                        return;
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return;
//            }
//        }
    }

    @Override
    public void onFailure(Throwable error) {
        // TODO完善提示
        if (!TextUtils.isEmpty(loadingMessage)) {
            if (error instanceof ConnectTimeoutException) {
                Toast.makeText(context, "网络超时，请重试", Toast.LENGTH_LONG).show();
            } else if (error instanceof SocketTimeoutException) {
                Toast.makeText(context, "网络超时，请重试", Toast.LENGTH_LONG).show();
            } else if (error instanceof HttpResponseException) {
                Toast.makeText(context, "服务器错误：code=" + ((HttpResponseException) error).getStatusCode(), Toast.LENGTH_LONG).show();
            } else if (error instanceof HttpHostConnectException) {
                Toast.makeText(context, "无网络连接，请连接WIFI或打开数据连接", Toast.LENGTH_LONG).show();
            } else if (error instanceof NoHttpResponseException) {
                Toast.makeText(context, "服务器未响应，请重试", Toast.LENGTH_LONG).show();
            } else if (error instanceof SocketException) {
                Toast.makeText(context, "连接错误，请重试", Toast.LENGTH_LONG).show();
            } else if (error instanceof UnknownHostException) {
                Toast.makeText(context, "未知主机，请重试", Toast.LENGTH_LONG).show();
            } else if (error instanceof IOException) {
                //TODO IO异常  上线去需要修改
//              Toast.makeText(context, "IO异常", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, error.getClass().getName(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onFinish() {
        try {
            if (!TextUtils.isEmpty(loadingMessage)) {
                if(context instanceof Activity){
                    Activity activity = (Activity) context;
                    if(!activity.isFinishing() && loading != null && loading.isShowing()){
                        loading.dismiss();
                    }
                }else{
                    if(loading != null && loading.isShowing()){
                        loading.dismiss();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
