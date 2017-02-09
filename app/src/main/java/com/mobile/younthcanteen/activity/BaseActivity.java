package com.mobile.younthcanteen.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.AppManager;
import com.mobile.younthcanteen.R;


/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class BaseActivity extends AppCompatActivity {
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base_layout);
//        // 经测试在代码里直接声明透明状态栏更有效
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(getResources().getColor(R.color.transparent));
//        }
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void setTitle(CharSequence title) {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        if (null != tvTitle) {
            tvTitle.setText(title);
        }
    }

    protected void setToolbarBg(int color) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setBackgroundResource(color);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void setToolbarBg(Drawable drawable) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setBackground(drawable);
        }
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) return;
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }

    /**
     * 设置是否显示toolbar
     *
     * @param isShow
     */
    public void setToolBarVisible(boolean isShow) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setTitleBackVisible(int visibility) {
        ImageButton back_Btn = (ImageButton) findViewById(R.id.titlebar_back);
        back_Btn.setVisibility(visibility);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
