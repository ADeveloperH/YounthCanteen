package com.mobile.younthcanteen.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.service.DownLoadService;


/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class UpdateAppUtil {
    public static boolean isUpdating = false;//是否正在下载
    /**
     * 有其他的类型弹框待弹出
     * 0：默认。没有弹框需要弹出
     * 1：下线通知弹框需要弹出
     * 2：安抚页面弹框需要弹出
     * 3: 活动页面弹框需要弹出
     */
    public static int hasOtherDialog = 0;
    public static String dialogMsg = "";//当有弹框需要弹出时，弹框的内容
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
//                try {
//                    if(content.startsWith("\ufeff")){
//                        content = content.substring(1);
//                    }
//                    JSONObject jsonObject = new JSONObject(content);
//                    String versionOnServer = jsonObject.getString("version");//服务器端版本号
//                    String versionContent = jsonObject.getString("function_suggestion");//版本描述
//                    //用来判断如果有新版本是大版本更新还是小版本更新。0代表小版本更新，1代表大版本更新
//                    String versionStatus = null;
//                    if(jsonObject.has("status")){
//                        versionStatus = jsonObject.getString("status");
//                    }
//                    long download_size = ConstantUtil.apk_size;
//                    if(jsonObject.has("size")){
//                        download_size  = jsonObject.getLong("size");
//                    }
//                    String download_link = Http.HOSTD;
//                    if (content.contains("download_link")){
//                        download_link = jsonObject.getString("download_link");
//                    }
//                    //加上时间戳目的为后台
//                    String tmeMillisStr = "?t="+System.currentTimeMillis();
//                    download_link = download_link + tmeMillisStr;
//
//                    //当前版本名称
//                    String versionInstalled = AppUtil.getAppVersionName(context);
//                    if(versionOnServer.compareTo(versionInstalled) > 0){
//                        //有新版本更新
//                        if(TextUtils.isEmpty(versionStatus)){
//                            //设置默认值，如果服务器端没有status默认值为1
//                            versionStatus = "1";
//                        }
//                        showCheckDialog(context,versionOnServer,versionContent,
//                                download_size,download_link);
//                        if (checkVersionResultListener != null) {
//                            checkVersionResultListener.hasNewVersion();
//                        }
//                    }else{
//                        //当前已是最新版本
//                        String versionName = AppUtil.getAppVersionName(context);
//                        if (checkVersionResultListener != null) {
//                            checkVersionResultListener.aleryNewVersion(versionName);
//                        }
//                    }
//                } catch (Exception e) {
//                    if (checkVersionResultListener != null) {
//                        checkVersionResultListener.parseDataFailure(e);
//                    }
//                }
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
        appSize.setText(FileUtil.bytes2kb(download_size));
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

