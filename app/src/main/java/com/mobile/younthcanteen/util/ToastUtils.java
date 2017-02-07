package com.mobile.younthcanteen.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 显示Toast的类。这里保证了在主线程显示
 *
 * @author huangjian
 */
public class ToastUtils {

    public static void showLongToast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    //一般的Toast不能在子线程中调用。如想在子线程中使用可以将注释打开
//					Looper.prepare();
                    Toast.makeText(UIUtils.getContext(), msg, Toast.LENGTH_LONG).show();
//					Looper.loop();
                }
            });
        }
    }

    public static void showShortToast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(UIUtils.getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void showCenterLongToast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(UIUtils.getContext(), msg, Toast.LENGTH_LONG);
                    // 这里给了一个1/4屏幕高度的y轴偏移量
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
    }
    public static void showCenterShortToast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(UIUtils.getContext(), msg, Toast.LENGTH_SHORT);
                    // 这里给了一个1/4屏幕高度的y轴偏移量
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
    }
}
