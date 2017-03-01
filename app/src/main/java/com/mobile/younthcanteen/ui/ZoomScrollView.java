package com.mobile.younthcanteen.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.util.ToastUtils;

/**
 * author：hj
 * time: 2017/3/1 0001 23:16
 */

public class ZoomScrollView extends ScrollView {
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
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            ToastUtils.showLongToast("获取到了viewPager");
        } else {
            ToastUtils.showLongToast("没有获取到了viewPager");
        }
    }
}
