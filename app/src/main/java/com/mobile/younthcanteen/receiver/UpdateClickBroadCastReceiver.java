package com.mobile.younthcanteen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mobile.younthcanteen.util.DownLoader;


/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class UpdateClickBroadCastReceiver extends BroadcastReceiver {
    //点击通知栏刷新执行发送广播操作的Action
    public final static String ACTION_CLICK = "zz.dela.cmcc.traffic.service.DownLoadService.click";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        DownLoader downLoader = DownLoader.getInstance(context);
        if (action.equals(ACTION_CLICK)) {
            if (downLoader.getDownLoadListener() != null) {
                System.out.println("downLoader.getDownLoadListener() != null");
                if (downLoader.isSuspend()) {
                    //当前是暂停状态。点击继续下载
                    downLoader.startDown();
                } else {
                    //当前不是暂停状态，点击暂停
                    downLoader.setSuspend();
                }
            }
        }
    }
}
