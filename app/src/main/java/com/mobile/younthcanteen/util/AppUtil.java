package com.mobile.younthcanteen.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import java.util.List;

public class AppUtil {
	/**
	 * 获取版本号
	 *
	 * @return 当前应用的版本号名称如：1.0
	 */
	public static String getAppVersionName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "无法获取当前版本";
		}
	}

	/**
	 *
	 * 获取Android手机型号。如MI2S
	 *
	 * @return
	 */
	public static String getAndroidModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取Android手机SDK版本号 如：22
	 *
	 * @return
	 */
	public static int getSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 *
	 * 获取Android系统版本号如：5.1.1
	 *
	 * @return
	 */
	public static String getAndroidVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取应用的版本号 例如：2
	 *
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	public static int getVersionCode(Context ctx) {
		try {
			PackageManager packageManager = ctx.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					ctx.getPackageName(), 0);
			int version = packInfo.versionCode;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}


	/**
	 *
	 * 根据包名来判断应用是否是系统应用(这里必须要传正确的包名)
	 * @param ctx
	 * @param packageName
	 * @return
	 */
	public static boolean isSystemApplication(Context ctx,String packageName){
		PackageManager packageManager = ctx.getPackageManager();
		List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);
		for (ApplicationInfo applicationInfo : installedApplications) {
			if(applicationInfo.packageName.equals(packageName)){
				return (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
			}
		}
		return false;
	}


	/**
	 * 获取应用的包名
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context){
		return context.getPackageName();
	}
}
