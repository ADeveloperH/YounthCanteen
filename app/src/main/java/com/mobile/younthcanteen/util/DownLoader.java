package com.mobile.younthcanteen.util;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.mobile.younthcanteen.receiver.UpdateClickBroadCastReceiver;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class DownLoader {
    private Context context;
    private String downUrl;
    private static DownLoader instance;
    private static Object lock = new Object();
    private String TEMP_DOWNLOADPATH;//下载文件的存放路径
    private long fileSize = 0;//文件总大小
    private long downFileSize = 0;//已经下载的文件的大小
    private static DownLoadListener downLoadListener;
    private MyRunnable downLoadRunnable;
    /**
     * 当前的下载状态
     * 10:初始状态
     * 11:下载中
     * 12:暂停
     * 13:下载完成
     * 14:下载出错
     * 15:停止下载
     *
     */
    private final int STATE_INIT = 10;
    private final int STATE_DOWING = 11;
    private final int STATE_SUSPEND = 12;
    private final int STATE_FINISH = 13;
    private final int STATE_ERROR = 14;
    private final int STATE_STOP = 15;

    private int curState = STATE_INIT;

    private DownLoader(Context context) {
        this.context = context;
        TEMP_DOWNLOADPATH = FileUtil.getCachePath(context,"/filedownloader");
    }

    public static DownLoader getInstance(Context context) {
        synchronized (lock) {
            if (instance == null) {
                instance = new DownLoader(context);
            }
        }
        return instance;
    }


    public void startDown(){
        if (curState != STATE_DOWING) {
            if (downLoadRunnable == null) {
                downLoadRunnable = new MyRunnable();
                handler.sendEmptyMessage(START);
                new Thread(downLoadRunnable).start();
            } else {
                //立即改变通知栏进度条
                Message msg = Message.obtain();
                msg.what = PROGRESS;
                msg.arg1 = progress;
                handler.sendMessage(msg);
                new Thread(downLoadRunnable).start();
            }
        }
    }

    public void stopDown() {
        if (downLoadRunnable != null) {
            downLoadRunnable = null;
            curState = STATE_STOP;
            downFileSize = 0;
            fileSize = 0;
        }
    }

    private int progress;//已下载百分比
    class MyRunnable implements Runnable {
        private URL url;
        private HttpURLConnection urlConn;
        private RandomAccessFile localFile;
        private InputStream inputStream;

        public MyRunnable() {
            progress = 0;
        }

        @Override
        public void run() {
            try {
                if (downFileSize == fileSize && downFileSize > 0) {
                    //下载完成了
                    curState = STATE_FINISH;
                    handler.sendEmptyMessage(FINISH);
                    return;
                }
                url = new URL(downUrl);
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setConnectTimeout(30000);
                urlConn.setReadTimeout(30000);
                if (downFileSize < 1) {
                    //第一次下载
                    init();
                } else {
                    if (new File(TEMP_DOWNLOADPATH + "/4GManager.apk").exists()) {
                        localFile = new RandomAccessFile(TEMP_DOWNLOADPATH + "/4GManager.apk", "rwd");
                        localFile.seek(downFileSize);
                        urlConn.setRequestProperty("Range", "bytes=" + downFileSize + "-");
                    } else {
                        fileSize = 0;
                        downFileSize = 0;
                        init();
                    }
                }
                inputStream = urlConn.getInputStream();
                curState = STATE_DOWING;
                byte[] buffer = new byte[1024];
                int length = -1;
                while ((length = inputStream.read(buffer)) != -1) {
                    switch (curState) {
                        case STATE_DOWING://正在下载
                            localFile.write(buffer, 0, length);
                            downFileSize += length;
                            int nowProgress = (int) ((100 * downFileSize) / fileSize);
                            if (nowProgress > progress) {
                                progress = nowProgress;
                                Message msg = Message.obtain();
                                msg.what = PROGRESS;
                                msg.arg1 = progress;
                                handler.sendMessage(msg);
                            }
                            break;
                        case STATE_SUSPEND://暂停下载
                            Message msg = Message.obtain();
                            msg.what = SUSPEND;
                            msg.arg1 = progress;
                            handler.sendMessage(msg);
                            return;
                        case STATE_STOP://终止下载
                        case STATE_ERROR://下载出错
                            downFileSize = 0;
                            fileSize = 0;
                            //删除已经下载的文件
                            deleteFile();
                            return;
                    }
                }
                //下载完了
                if (downFileSize == fileSize) {
                    curState = STATE_FINISH;
                    handler.sendEmptyMessage(FINISH);
                }
            }
            catch (Exception e) {
                //下载出现问题了.终止下载
                if (downLoadListener != null) {
                    //暂停下载
                    Message msg = Message.obtain();
                    msg.what = SUSPEND;
                    msg.arg1 = progress;
                    handler.sendMessage(msg);
                } else {
                    //终止下载。
                    Intent intent = new Intent();
                    intent.setAction(UpdateClickBroadCastReceiver.ACTION_CLICK);
                    context.sendBroadcast(intent);

                    downFileSize = 0;
                    fileSize = 0;
                    //删除已经下载的文件
                    deleteFile();
                    curState = STATE_ERROR;
                    Message msg = Message.obtain();
                    msg.what = FAILURE;
                    msg.arg1 = progress;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            } finally {
                try {
                    if (urlConn != null) {
                        urlConn.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (localFile != null) {
                        localFile.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        private void init() throws Exception{
            long urlfilesize = urlConn.getContentLength();
            if(urlfilesize > 0){
                isFolderExist();
                localFile = new RandomAccessFile(TEMP_DOWNLOADPATH + "/4GManager.apk","rwd");
                localFile.setLength(urlfilesize);
                fileSize = urlfilesize;
            }
        }
    }

    public void deleteFile() {
        File file = new File(TEMP_DOWNLOADPATH + "/4GManager.apk");
        if (file.exists()) {
            //文件存在就删除
            file.delete();
        }
    }


    /**
     * (判断文件夹是否存在，不存在则创建)
     * @return
     */
    private boolean isFolderExist(){
        boolean result = false;
        try{
            String filepath = TEMP_DOWNLOADPATH;
            File file = new File(filepath);
            if(!file.exists()){
                if(file.mkdirs()){
                    result = true;
                }
            }else{
                result = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //设置下载的监听
    public void setDownLoadListener(DownLoadListener downLoadListener) {
        this.downLoadListener = downLoadListener;
    }

    public DownLoadListener getDownLoadListener() {
        return downLoadListener;
    }

    //获取是否正在下载
    public boolean isDownloading() {
        return curState == STATE_DOWING;
    }

    //获取是否暂停下载
    public boolean isSuspend() {
        return curState == STATE_SUSPEND;
    }

    //暂停下载
    public void setSuspend() {
        curState = STATE_SUSPEND;
    }

    //获取当前下载的进度
    public int getProgress() {
        return progress;
    }

    //设置当前下载进度
    public void setProgress(int progress) {
        this.progress = progress;
    }

    //设置下载的链接
    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    //获取下载文件的完全路径
    public String getDownFilePath() {
        File file = new File(TEMP_DOWNLOADPATH + "/4GManager.apk");
        return file.getAbsolutePath();
    }

    //获取下载的文件是否存在
    public boolean isDownLoadFileExist() {
        return new File(TEMP_DOWNLOADPATH + "/4GManager.apk").exists();
    }


    //设置已经下载的大小
    public void setDownFileSize(long downFileSize,long totalLenFrmUrl) {
        this.downFileSize = downFileSize;
        this.fileSize = totalLenFrmUrl;
    }

    private final int START = 1;
    private final int PROGRESS = 2;
    private final int FAILURE = 3;
    private final int FINISH = 4;
    private final int SUSPEND = 5;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (downLoadListener == null) {
                NotificationManager notificationManager = (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(138);
                return;
            }
            if(msg.what == START){ //开始下载
                if (downLoadListener != null)
                    downLoadListener.onStart();
            }else if(msg.what == PROGRESS){ //改变进度
                if (downLoadListener != null) {
                    downLoadListener.onProgress(msg.arg1, downFileSize);
                }
            }else if(msg.what == FAILURE){ //下载出错
                if(downLoadListener != null)
                    downLoadListener.onFailure(msg.arg1, downFileSize,fileSize);
            }else if(msg.what == FINISH){ //下载完成
                if(downLoadListener != null)
                    downLoadListener.onFinish(downFileSize,fileSize);
            }else if(msg.what == SUSPEND){ //暂停下载
                if(downLoadListener != null)
                    downLoadListener.onSuspend(msg.arg1, downFileSize,fileSize);
            }
        }
    };
}
