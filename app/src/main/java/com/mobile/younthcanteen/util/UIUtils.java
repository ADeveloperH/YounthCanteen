package com.mobile.younthcanteen.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;

import com.mobile.younthcanteen.activity.CanteenApplication;

import java.lang.reflect.Field;

public class UIUtils {
	/**
	 * 获取上下文
	 *
	 * @return
	 */
	public static Context getContext() {
		return CanteenApplication.getContext();
	}

	/**
	 * 获取主线程id
	 *
	 * @return
	 */
	public static int getMainThreadId() {
		return CanteenApplication.getMainThreadId();
	}

	/**
	 * 获取主线程
	 *
	 * @return
	 */
	public static Thread getMainThread() {
		return CanteenApplication.getMainThread();
	}

	/**
	 * 获取Handler
	 *
	 * @return
	 */
	public static Handler getHandler() {
		return CanteenApplication.getHandler();
	}

	/**
	 * 判断当前的线程是否在主线程中 根据当前的线程的id和主线程的id进行比较
	 *
	 * @return
	 */
	public static boolean isRunInMainThread() {
		return getMainThreadId() == android.os.Process.myTid();
	}

	/**
	 * 让当前的任务在主线程中立即执行
	 *
	 * @param runnable
	 */
	public static void runOnUIThread(Runnable runnable) {
		if (isRunInMainThread()) {
			// 当前的线程在主线程
			runnable.run();
		} else {
			// 在主线程中执行任务。
			getHandler().post(runnable);
		}
	}

	/**
	 * 让当前的任务延迟在主线程中执行
	 *
	 * @param runnable
	 *            要执行的任务
	 * @param delayMillis
	 *            延迟的时间。单位为毫秒
	 */
	public static void postDelayRunnable(Runnable runnable, long delayMillis) {
		getHandler().postDelayed(runnable, delayMillis);
	}

	/**
	 * 移除在当前handler中维护的任务(传递进来的任务)
	 *
	 * @param runnable
	 */
	public static void removeCallback(Runnable runnable) {
		getHandler().removeCallbacks(runnable);
	}

	/**
	 * dp转化为px dip--->px 1dp = 1px 1dp = 2px
	 *
	 * @param dip
	 * @return
	 */
	public static int dip2px(int dip) {
		// dp和px的转换关系比例值
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5);
	}

	/**
	 * px转化为dp
	 *
	 * @param px
	 * @return
	 */
	public static int px2dip(int px) {
		// dp和px的转换关系比例值
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (px / density + 0.5);
	}

	// 根据布局文件填充View获取的返回值是一个View
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	// string
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	// drawable
	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}

	// stringArray
	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	// java代码区设置颜色选择器的方法
	public static ColorStateList getColorStateList(int mTabTextColorResId) {
		return getContext().getResources()
				.getColorStateList(mTabTextColorResId);
	}

	// 根据dimens中提供的id，将其对应的dp值转换成相应的像素值大小
	public static int getDimens(int id) {
		return getContext().getResources().getDimensionPixelSize(id);
	}

	// color
	public static int getColor(int id) {
		return getContext().getResources().getColor(id);
	}

	/**
	 * 获取状态栏/通知栏高度 最上边
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 获取导航栏的高度 actionbar
	 *
	 * @param activity
	 * @return
	 */
	public static int getNavigationBarHeight(Activity activity) {
		try {
			Resources resources = activity.getResources();
			int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
			// 获取NavigationBar的高度
			int height = resources.getDimensionPixelSize(resourceId);
			return height;
		} catch (Exception e) {
			e.printStackTrace();
			return UIUtils.dip2px(50);
		}
	}

	/**
	 * 获取屏幕宽度
	 * @param act
	 * @return
	 */
	public static int getWindowWidt(Activity act) {
		DisplayMetrics metric = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;     // 屏幕宽度（像素）  
		int height = metric.heightPixels;
		return width;
	}
}
