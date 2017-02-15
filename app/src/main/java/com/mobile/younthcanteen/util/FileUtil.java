package com.mobile.younthcanteen.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;

public class FileUtil {
	/**
	 * 复制单个文件
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}


	/**
	 * BitmapUtils的缓存目录
	 * @param context
	 * @param cacheName
	 * @return
	 */
	public static String getCachePath(Context context, String cacheName) {
		File mCacheDir;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)){
			mCacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
					cacheName);
		}else{
			mCacheDir = context.getCacheDir();// 如何获取系统内置的缓存存储路径
		}
		if (!mCacheDir.exists())
			mCacheDir.mkdirs();
		return mCacheDir.getAbsolutePath();
	}

	/**
	 * 删除文件
	 * @param dir
	 */
	public static boolean deleteDir(File dir) {
		if(dir == null){
			return true;
		}
		if(!dir.exists()){
			return true;
		}
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
	 *
	 * @param bytes
	 * @return
	 */
	public static String bytes2kb(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		if (returnValue > 1)
			return (returnValue + "MB");
		BigDecimal kilobyte = new BigDecimal(1024);
		returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		return (returnValue + "KB");
	}
}
