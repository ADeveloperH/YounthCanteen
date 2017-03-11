package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobile.younthcanteen.AppManager;
import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.FragmentAdapter;
import com.mobile.younthcanteen.fragment.CustomerFragment;
import com.mobile.younthcanteen.fragment.HomeFragment;
import com.mobile.younthcanteen.fragment.OrderFragment;
import com.mobile.younthcanteen.fragment.ShoppingCartFragment;
import com.mobile.younthcanteen.ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private NoScrollViewPager mContentPage;
    private Activity act;
    private FragmentAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private HomeFragment mHomeFragment;
    private OrderFragment mOrderFragment;
    private CustomerFragment mCustomerFragment;
    private ShoppingCartFragment mShoppingCartFragment;

    private LinearLayout llTabContainer;
    private View[] tabImageViews;
    private View[] tabTextViews;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int tabIndex = intent.getIntExtra("tabIndex", -1);
            if (tabIndex != -1) {
                setSelect(tabIndex);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = this;
        AppManager.getAppManager().addActivity(this);
        initView();
        initViewPager();
    }


    private void initView() {
        tabImageViews = new View[]{findViewById(R.id.home_imageview), findViewById(R.id.cart_imageview),
                findViewById(R.id.order_imageview), findViewById(R.id.custome_imageview)};
        tabTextViews = new View[]{findViewById(R.id.home_textview), findViewById(R.id.cart_textview),
                findViewById(R.id.order_textview), findViewById(R.id.custome_textview)};

        llTabContainer = (LinearLayout) findViewById(R.id.ll_tab_container);
        mContentPage = (NoScrollViewPager) findViewById(R.id.content);

        mContentPage.setNoScroll(true);
    }

    private void initViewPager() {
        mHomeFragment = new HomeFragment();
        mShoppingCartFragment = new ShoppingCartFragment();
        mOrderFragment = new OrderFragment();
        mCustomerFragment = new CustomerFragment();
        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mShoppingCartFragment);
        mFragmentList.add(mOrderFragment);
        mFragmentList.add(mCustomerFragment);

        mFragmentAdapter = new FragmentAdapter(
                this.getSupportFragmentManager(), mFragmentList);
        mContentPage.setAdapter(mFragmentAdapter);
        mContentPage.setOffscreenPageLimit(2);
        //默认选中第一个
        setSelect(0);
        setListener();
    }

    private void setListener() {
        for (int i = 0; i < llTabContainer.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llTabContainer.getChildAt(i);
            final int index = i;
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelect(index);
                }
            });
        }
    }

    /**
     * 设置tab选中状态
     * @param index
     */
    private void setSelect(int index) {
        mContentPage.setCurrentItem(index, false);
        for (int i = 0; i < tabImageViews.length; i++) {
            if (i == index) {
                tabImageViews[i].setSelected(true);
                tabTextViews[i].setSelected(true);
            } else {
                tabImageViews[i].setSelected(false);
                tabTextViews[i].setSelected(false);
            }
        }
    }


    @Override
    public void onBackPressed() {
        exit();
    }

    private boolean exiting = false;

    // 按两次返回键退出程序
    private void exit() {
        if (exiting) {
            moveTaskToBack(true);
        } else {
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            exiting = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exiting = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
