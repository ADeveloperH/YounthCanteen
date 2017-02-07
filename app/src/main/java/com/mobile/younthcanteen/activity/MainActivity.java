package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.FragmentAdapter;
import com.mobile.younthcanteen.fragment.OrderFragment;
import com.mobile.younthcanteen.fragment.CustomerFragment;
import com.mobile.younthcanteen.fragment.HomeFragment;
import com.mobile.younthcanteen.ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private NoScrollViewPager mContentPage;
    private TextView mTabHome, mTabFind, mTabCustome;
    private ImageView mImageHome, mImageFind, mImageCustome;
    private LinearLayout mHomeLinear, mFindLinear, mCustomLinear;
    private Activity act;
    private FragmentAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private HomeFragment mHomeFragment;
    private OrderFragment mOrderFragment;
    private CustomerFragment mCustomerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = this;
        initView();
        initViewPager();
    }


    private void initView() {
        mContentPage = (NoScrollViewPager) findViewById(R.id.content);
        mTabHome = (TextView) findViewById(R.id.home_textview);
        mTabFind = (TextView) findViewById(R.id.find_textview);
        mTabCustome = (TextView) findViewById(R.id.custom_textview);
        mImageCustome = (ImageView) findViewById(R.id.custome_imageview);
        mImageFind = (ImageView) findViewById(R.id.find_imageview);
        mImageHome = (ImageView) findViewById(R.id.home_imageview);
        mHomeLinear = (LinearLayout) findViewById(R.id.home_linear);
        mFindLinear = (LinearLayout) findViewById(R.id.find_linear);
        mCustomLinear = (LinearLayout) findViewById(R.id.custome_linear);
        mCustomLinear.setOnClickListener(this);
        mFindLinear.setOnClickListener(this);
        mHomeLinear.setOnClickListener(this);
        mContentPage.setNoScroll(true);
    }

    private void initViewPager() {
        mHomeFragment = new HomeFragment();
        mOrderFragment = new OrderFragment();
        mCustomerFragment = new CustomerFragment();
        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mOrderFragment);
        mFragmentList.add(mCustomerFragment);

        mFragmentAdapter = new FragmentAdapter(
                this.getSupportFragmentManager(), mFragmentList);
        mContentPage.setAdapter(mFragmentAdapter);
        mContentPage.setOffscreenPageLimit(2);
        mContentPage.setCurrentItem(0, false);
        setTabSelection(0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_linear:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.find_linear:
                // 当点击了动态tab时，选中第3个tab
                setTabSelection(1);
                break;
            case R.id.custome_linear:
                // 当点击了设置tab时，选中第4个tab
                setTabSelection(2);
                break;
            default:
                break;
        }
    }


    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        switch (index) {
            case 0:
                mContentPage.setCurrentItem(0, false);
                mTabHome.setTextColor(Color.parseColor("#67c0f4"));
                mImageHome.setImageDrawable(act.getResources().getDrawable(R.drawable.tab_home_icon_select));
                break;
            case 1:
                mTabFind.setTextColor(Color.parseColor("#67c0f4"));
                mImageFind.setImageDrawable(act.getResources().getDrawable(R.drawable.tab_find_icon_select));
                mContentPage.setCurrentItem(1, false);
                break;
            case 2:
                mContentPage.setCurrentItem(2, false);
                mTabCustome.setTextColor(Color.parseColor("#67c0f4"));
                mImageCustome.setImageDrawable(act.getResources().getDrawable(R.drawable.tab_customer_icon_select));
                break;
            default:
                break;
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        mTabHome.setTextColor(Color.parseColor("#ababab"));
        mImageHome.setImageDrawable(act.getResources().getDrawable(R.drawable.tab_home_icon_default));
        mTabFind.setTextColor(Color.parseColor("#ababab"));
        mImageFind.setImageDrawable(act.getResources().getDrawable(R.drawable.tab_find_icon_default));
        mTabCustome.setTextColor(Color.parseColor("#ababab"));
        mImageCustome.setImageDrawable(act.getResources().getDrawable(R.drawable.tab_customer_icon_default));
    }
}
