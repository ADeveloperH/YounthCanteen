package com.mobile.younthcanteen.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * author：hj
 * time: 2017/3/1 0001 23:16
 */

public class ZoomScrollView extends ScrollView {

    private View zoomView;
    private int zoomViewWidth;
    private int zoomViewHeight;
    private ViewGroup.LayoutParams layoutParams;

    public ZoomScrollView(Context context) {
        super(context);
        init(context);
    }

    public ZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        if (zoomView != null) {
//            ToastUtils.showLongToast("获取到了viewPager");
//        } else {
//            ToastUtils.showLongToast("没有获取到了viewPager");
//        }
    }


    public void setZoomView(View zoomView) {
        this.zoomView = zoomView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (zoomView != null && zoomViewHeight == 0 && zoomViewWidth == 0) {
            //只获取一次初始值
            zoomViewWidth = zoomView.getMeasuredWidth();
            zoomViewHeight = zoomView.getMeasuredHeight();
            layoutParams = zoomView.getLayoutParams();
            Log.d("hj", "zoomViewWidth:" + zoomViewWidth);
            Log.d("hj", "zoomViewHeight:" + zoomViewHeight);
        }
    }

    float downY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (zoomView != null && zoomViewHeight != 0
                && zoomViewWidth != 0) {

            Log.d("hj", "ev.getAction():" + ev.getAction());
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float temY = ev.getY();
                    if (downY == 0) {
                        downY = temY;
                    }
                    float offsetY = temY - downY;
                    Log.d("hj", "offsetY:" + offsetY);
                    float scale = 1 + offsetY / 3 / zoomViewHeight;
                    Log.d("hj", "scale:" + scale);
                    if (scale < 1) {
                        scale = 1;
                    } else if (scale > 2) {
                        scale = 2;
                    }
//                    zoomView.setScaleX(scale);
//                    zoomView.setScaleY(scale);
                    int paramsWidth = (int) (zoomViewWidth * scale);
                    int paramsHeight = (int) (zoomViewHeight * scale);
                    Log.d("hj", "paramsWidth:" + paramsWidth);
                    Log.d("hj", "paramsHeight:" + paramsHeight);
                    //向下拉。放大
                    layoutParams.width = paramsWidth;
                    layoutParams.height = paramsHeight;
                    zoomView.setLayoutParams(layoutParams);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }
}
