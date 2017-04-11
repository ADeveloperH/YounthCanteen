package com.mobile.younthcanteen.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

/**
 * author：hj
 * time: 2017/2/16 0016 16:24
 */

public class BitmapUtil {
    private BitmapDisplayConfig bitmapDisplsyConfig;
    private BitmapUtils bitmapUtils;

    public BitmapUtil(Context context) {
        bitmapUtils = new BitmapUtils(context);
    }

    public BitmapUtil(Context context, Drawable loadingDrawable, Drawable loadFailedDrawable) {
        bitmapUtils = new BitmapUtils(context);
        bitmapDisplsyConfig = new BitmapDisplayConfig();
        bitmapDisplsyConfig.setLoadingDrawable(loadingDrawable);
        bitmapDisplsyConfig.setLoadFailedDrawable(loadFailedDrawable);
    }
    public BitmapUtil(Context context, String diskCachePath) {
        bitmapUtils = new BitmapUtils(context, diskCachePath);
    }

    public BitmapUtil(Context context, String diskCachePath, Drawable loadFailedDrawable) {
        bitmapUtils = new BitmapUtils(context, diskCachePath);
        bitmapDisplsyConfig = new BitmapDisplayConfig();
        bitmapDisplsyConfig.setLoadFailedDrawable(loadFailedDrawable);
    }

    public BitmapUtil(Context context, String diskCachePath, Drawable loadingDrawable, Drawable loadFailedDrawable) {
        bitmapUtils = new BitmapUtils(context, diskCachePath);
        bitmapDisplsyConfig = new BitmapDisplayConfig();
        bitmapDisplsyConfig.setLoadingDrawable(loadingDrawable);
        bitmapDisplsyConfig.setLoadFailedDrawable(loadFailedDrawable);
    }

    public void setLoadingDrawable(Drawable loadingDrawable) {
        if (null == loadingDrawable) {
            throw new IllegalArgumentException("loadingDrawable may not be null");
        } else if (bitmapDisplsyConfig == null) {
            bitmapDisplsyConfig = new BitmapDisplayConfig();
            bitmapDisplsyConfig.setLoadingDrawable(loadingDrawable);
        } else {
            bitmapDisplsyConfig.setLoadingDrawable(loadingDrawable);
        }
    }

    public void setLoadFailedDrawable(Drawable loadFailedDrawable) {
        if (null == loadFailedDrawable) {
            throw new IllegalArgumentException("loadFailedDrawable may not be null");
        } else if (bitmapDisplsyConfig == null) {
            bitmapDisplsyConfig = new BitmapDisplayConfig();
            bitmapDisplsyConfig.setLoadFailedDrawable(loadFailedDrawable);
        } else {
            bitmapDisplsyConfig.setLoadFailedDrawable(loadFailedDrawable);
        }
    }

    public <T extends View> void display(T container, String uri) {
        if (null != bitmapDisplsyConfig) {
            bitmapUtils.display(container, uri, bitmapDisplsyConfig);
        } else {
            bitmapUtils.display(container, uri);
        }
    }

    public <T extends View> void displayNoCache(T container, String uri) {
        bitmapUtils.clearCache();
        if (null != bitmapDisplsyConfig) {
            bitmapUtils.display(container, uri, bitmapDisplsyConfig);
        } else {
            bitmapUtils.display(container, uri);
        }
    }

    public <T extends View> void display(T container, String uri, final BitmapLoadCallBack callBack) {
        if (null == callBack) {
            throw new IllegalArgumentException("callBack may not be null");
        }
        if (null != bitmapDisplsyConfig) {
            bitmapUtils.display(container, uri, bitmapDisplsyConfig, new com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack<T>() {
                @Override
                public void onLoadCompleted(T t, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                    if (callBack != null) {
                        callBack.onLoadCompleted(bitmap);
                    }
                }

                @Override
                public void onLoadFailed(T t, String s, Drawable drawable) {
                }
            });
        } else {
            bitmapUtils.display(container, uri, new com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack<T>() {
                @Override
                public void onLoadCompleted(T t, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                    if (callBack != null) {
                        callBack.onLoadCompleted(bitmap);
                    }
                }

                @Override
                public void onLoadFailed(T t, String s, Drawable drawable) {
                }
            });
        }
    }

    public <T extends View> void displayNoCache(T container, String uri, final BitmapLoadCallBack callBack) {
        if (null == callBack) {
            throw new IllegalArgumentException("callBack may not be null");
        }

        bitmapUtils.clearCache();
        if (null != bitmapDisplsyConfig) {
            bitmapUtils.display(container, uri, bitmapDisplsyConfig, new com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack<T>() {
                @Override
                public void onLoadCompleted(T t, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                    if (callBack != null) {
                        callBack.onLoadCompleted(bitmap);
                    }
                }

                @Override
                public void onLoadFailed(T t, String s, Drawable drawable) {
                }
            });
        } else {
            bitmapUtils.display(container, uri, new com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack<T>() {
                @Override
                public void onLoadCompleted(T t, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                    if (callBack != null) {
                        callBack.onLoadCompleted(bitmap);
                    }
                }

                @Override
                public void onLoadFailed(T t, String s, Drawable drawable) {
                }
            });
        }
    }


    public interface BitmapLoadCallBack {
        void onLoadCompleted(Bitmap bitmap);
    }
}
