package com.mobile.younthcanteen.util;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public interface DownLoadListener {
	void onStart();//开始下载
	void onProgress(int progress, long downFileLength);//下载中
	void onFinish(long downFileLength, long totalFileLen);//下载完成
	void onFailure(int curProgress, long downFileLength, long totalFileLen);//下载失败
	void onSuspend(int curProgress, long downFileLength, long totalFileLen);//暂停下载

}
