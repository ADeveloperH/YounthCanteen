package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mobile.younthcanteen.AppManager;
import com.mobile.younthcanteen.R;

/**
 * author：hj
 * time: 2017/2/7 0007 13:05
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);
        AppManager.getAppManager().addActivity(this);

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
