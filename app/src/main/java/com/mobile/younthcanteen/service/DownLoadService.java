package com.mobile.younthcanteen.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.DownLoadBean;
import com.mobile.younthcanteen.receiver.UpdateClickBroadCastReceiver;
import com.mobile.younthcanteen.util.DownLoadListener;
import com.mobile.younthcanteen.util.DownLoader;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.List;


public class DownLoadService extends IntentService {
    private String newVersion;//最新的版本号
    private String newVersionContent;//最新版本的内容
    private long newFileLength;//最新版本的大小
    private NotificationManager notificationManager;
    private Notification notification;
    private RemoteViews rViews;
    private DownLoader downLoader;
    final long TIMESTAMP_FIXED = 1234567890l;

    public DownLoadService() {
        super("com.mobile.younthcanteen.service.DownLoadService");
    }

    protected void onHandleIntent(Intent intent) {
        downLoader = DownLoader.getInstance(this);
        initNotification();
        downLoadApk(intent);
    }

    private void initNotification() {
        // 设置Notification
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder noticomBuilder = new NotificationCompat.Builder(this);

//        noticomBuilder.setTicker("版本更新下载") //通知首次出现在通知栏，带上升动画效果的
////                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//                .setPriority(Notification.PRIORITY_MIN) //设置该通知优先级
//                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                .setSmallIcon(R.drawable.status_icon);//设置通知小ICON
//        notification = noticomBuilder.build();
        notification = new Notification(R.drawable.ic_launcher, "版本更新下载", TIMESTAMP_FIXED);
        // 加载Notification的布局文件
        rViews = new RemoteViews(getPackageName(), R.layout.downloadfile_layout);
        // 设置下载进度条
        rViews.setProgressBar(R.id.downloadFile_pb, 100, 0, false);
        rViews.setTextViewText(R.id.progress_percent_text,"正在下载0%");
        Intent intentClick = new Intent(this, UpdateClickBroadCastReceiver.class);
        intentClick.setAction(UpdateClickBroadCastReceiver.ACTION_CLICK);
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(this, 0,
                intentClick, PendingIntent.FLAG_UPDATE_CURRENT);

        rViews.setOnClickPendingIntent(R.id.rl_download, pendingIntentClick);
        notification.contentView = rViews;
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(138, notification);
    }

    private void downLoadApk(Intent intent) {
        Bundle bundle = intent.getExtras();
        newVersion = bundle.getString("versionOnServer");
        newVersionContent = bundle.getString("versionContent");
        newFileLength = bundle.getLong("downloadFileLength");//服务器端返回的文件的总大小
        // 获得下载文件的url
        final String downloadUrl = bundle.getString("url");
        //设置下载链接
        downLoader.setDownUrl(downloadUrl);
        //获取已经下载的信息
        DownLoadBean downLoadBean = SharedPreferencesUtil.getDownMsg();
        if (downLoadBean != null) {
            System.out.println("已经下载的信息：" + downLoadBean.toString());
            long downLoadSize = downLoadBean.getDownSize();
            if (downLoadSize > 0 && downLoadBean.getTotalSize() > 0
                    && downLoader.isDownLoadFileExist()
                    && newFileLength == downLoadBean.getTotalSize()
                    && newVersion.equals(downLoadBean.getDownVersion())) {
                System.out.println("已经下载的大小大于0 & 下载的APK文件存在 & 下载的文件是最新的版本");
                //已经下载的大小大于0 & 下载的APK文件存在 & 下载的文件是最新的版本
                downLoader.setDownFileSize(downLoadSize, downLoadBean.getTotalSizeFrmContentLen());
            } else {
                SharedPreferencesUtil.setDownMsg(null);
                downLoader.setProgress(0);
                downLoader.setDownFileSize(0, downLoadBean.getTotalSizeFrmContentLen());
            }
        }else {
            SharedPreferencesUtil.setDownMsg(null);
            downLoader.setProgress(0);
            downLoader.setDownFileSize(0, newFileLength);
        }

        downLoader.setDownLoadListener(new DownLoadListener() {
            @Override
            public void onStart() {
                ToastUtils.showLongToast("开始下载了");
                rViews.setProgressBar(R.id.downloadFile_pb, 100, 0, false);
                rViews.setTextViewText(R.id.progress_percent_text,"正在下载"+0+"%");
                notificationManager.notify(138, notification);
            }

            @Override
            public void onProgress(int progress,long downFileLength) {
                rViews.setProgressBar(R.id.downloadFile_pb, 100, progress, false);
                rViews.setTextViewText(R.id.progress_percent_text,"正在下载"+progress+"%");
                notificationManager.notify(138, notification);
            }

            @Override
            public void onFinish(long downFileLength,long totalFileLen) {
                SharedPreferencesUtil.setDownMsg(
                        new DownLoadBean(newVersion,downFileLength,totalFileLen,totalFileLen));
                ToastUtils.showLongToast("下载完成了");
                // 移除通知栏
                notificationManager.cancel(138);
                //打开安装的界面
                installApk();
            }

            @Override
            public void onFailure(int curProgress,long downFileLength,long totalFileLen) {
                ToastUtils.showLongToast("下载失败了");
                SharedPreferencesUtil.setDownMsg(null);
                // 移除通知栏
                notificationManager.cancel(138);
            }

            @Override
            public void onSuspend(int curProgress,long downFileLength,long totalFileLen) {
                SharedPreferencesUtil.setDownMsg(
                        new DownLoadBean(newVersion,downFileLength,totalFileLen,totalFileLen));
                rViews.setProgressBar(R.id.downloadFile_pb, 100, curProgress, false);
                rViews.setTextViewText(R.id.progress_percent_text,"下载已暂停，已下载"+ curProgress +"%。点击继续");
                notificationManager.notify(138, notification);
            }
        });

        //开始下载
        downLoader.startDown();
    }


    /**
     * 判断该服务是否运行
     * @param mContext
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }


    /**
     * 安装APK界面
     *
     */
    private void installApk() {
        // 通过Intent安装APK文件
        try {
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setDataAndType(Uri.parse("file://" + downLoader.getDownFilePath()),
                    "application/vnd.android.package-archive");
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(installIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
