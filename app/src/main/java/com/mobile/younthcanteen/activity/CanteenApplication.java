package com.mobile.younthcanteen.activity;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

public class CanteenApplication extends Application {
    private static CanteenApplication mSingleton;
    private static Context context;
    private static int mainThreadId;// 主线程id
    private static Thread mainThread;// 主线程
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        String curProName = getCurProcessName(this);
        if (!TextUtils.isEmpty(curProName)) {
            mSingleton = this;
            context = getApplicationContext();
            mainThreadId = android.os.Process.myTid();
            mainThread = Thread.currentThread();// 当前的线程一定在主线程中
            handler = new Handler();
        }
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }


    public static CanteenApplication getInstance() {
        return mSingleton;
    }

    public static Context getContext() {
        return context;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static Handler getHandler() {
        return handler;
    }
}
