package com.mobile.younthcanteen.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.APPUpdateBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.service.DownLoadService;

import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class UpdateAppUtil {
    public static boolean isUpdating = false;//是否正在下载
    /**
     * 检测是否有新的版本更新
     */
    public static void checkNewVersion(final Context context, String loadMsg
            , RequestParams params, final CheckVersionResultListener checkVersionResultListener) {

        Http.post(Http.CHECKVERSION,params,new MyTextAsyncResponseHandler(context,loadMsg){
            @Override
            public void onStart() {
                super.onStart();
                if (checkVersionResultListener != null) {
                    checkVersionResultListener.startRequest();
                }
            }

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                final APPUpdateBean updateBean = JsonUtil.fromJson(content, APPUpdateBean.class);
                String versionInstalled = AppUtil.getAppVersionName(context);
                final String versionOnServer = updateBean.getResults().getVersion();
                if (null != updateBean) {
                    //当前版本名称
                    if(versionOnServer.compareTo(versionInstalled) > 0){
                            ThreadManager.getThreadPool().execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        URL url = new URL(updateBean.getResults().getUrl());
                                        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                                        //必须子线程
                                        final long apkSize = urlConn.getContentLength();
                                        UIUtils.runOnUIThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showCheckDialog(context,versionOnServer,updateBean.getResults().getDescribe(),
                                                        apkSize,updateBean.getResults().getUrl());
                                                if (checkVersionResultListener != null) {
                                                    checkVersionResultListener.hasNewVersion();
                                                }
                                            }
                                        });
                                    } catch (Exception e) {
                                        if (checkVersionResultListener != null) {
                                            checkVersionResultListener.parseDataFailure(e);
                                        }
                                    }
                                }
                            });
                    }else{
                        //当前已是最新版本
                        if (checkVersionResultListener != null) {
                            checkVersionResultListener.aleryNewVersion(versionInstalled);
                        }
                    }
                } else {
                    if (checkVersionResultListener != null) {
                        checkVersionResultListener.parseDataFailure(new Exception("APPUpdateBean is null"));
                    }
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                if (checkVersionResultListener != null) {
                    checkVersionResultListener.getDataFailure(error);
                }
            }
        });
    }
    /**
     *
     *  显示对话框提示是否更新新版本
     * @param context
     * @param newVersion 新的版本号
     * @param versionContent 新版本更新的内容
     * @param download_size
     * @param download_link
     */
    private static void showCheckDialog(final Context context, final String newVersion,
                                        final String versionContent, final long download_size,
                                        final String download_link) {
        final Dialog dialog = new Dialog(context, R.style.Theme_CustomDialog_buy);
        dialog.setContentView(R.layout.dialog_checkingver);

        TextView newVersionTv = (TextView) dialog.findViewById(R.id.dialog_new_version_tv);
        TextView contentTv = (TextView) dialog.findViewById(R.id.dialog_content_version_tv);
        TextView appSize = (TextView) dialog.findViewById(R.id.new_app_size_tv);
        appSize.setText(StringUtil.bytes2kb(download_size));
        newVersionTv.setText(newVersion);
        contentTv.setText(versionContent==null?"":versionContent);

        TextView okBtn = (TextView) dialog.findViewById(R.id.ok_tv);
        TextView cancleBtn = (TextView) dialog.findViewById(R.id.cancle_tv);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAppUtil.isUpdating = false;
                //跳转到下载apk的界面
                Intent downloadIntent = new Intent(context, DownLoadService.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", download_link);
                bundle.putString("versionOnServer", newVersion);//最新的版本
                bundle.putString("versionContent", versionContent);//更新的内容
                bundle.putLong("downloadFileLength", download_size);//APK文件的大小
                downloadIntent.putExtras(bundle);
                context.startService(downloadIntent);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


    public interface CheckVersionResultListener {
        //获取数据失败
        void getDataFailure(Throwable error);

        void aleryNewVersion(String curVersionName);//当前已是最新版本

        void hasNewVersion();//当前有新的版本

        void parseDataFailure(Exception e);//解析数据异常

        void startRequest();//开始请求更新的接口

    }
}

