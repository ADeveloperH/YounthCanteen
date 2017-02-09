package com.mobile.younthcanteen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mobile.younthcanteen.R;

/**
 * author：hj
 * time: 2017/2/9 0009 11:08
 */

public class MyAccountActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的账号");
        setTitleBackVisible(View.VISIBLE);
        setContentView(R.layout.activity_myaccount_layout);
    }
}
