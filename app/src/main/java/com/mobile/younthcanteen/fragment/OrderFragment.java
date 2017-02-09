package com.mobile.younthcanteen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.LoginActivity;
import com.mobile.younthcanteen.adapter.OrderFragmentPagerAdapter;
import com.mobile.younthcanteen.ui.TabPageIndicator;
import com.mobile.younthcanteen.util.LoginUtils;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class OrderFragment extends Fragment implements View.OnClickListener {
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
    private Button btnLogin;
    private OrderFragmentPagerAdapter mAdatpter;
    private ArrayList<Fragment> listFragmentsa = new ArrayList<Fragment>();
    private static List<String> titleList = new ArrayList<String>();
    private TabPageIndicator tabPageIndicator;
    private ViewPager viewpager;
    private LinearLayout llAlLogin;
    private LinearLayout llUnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_order, null);
        }
        //缓存的rootView需要判断是否已经被加过parent，
        //如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isNeedReLoad) {
            initView(getView());
            setListener();
//            getData();
            isNeedReLoad = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginUtils.isLogin()) {
            llAlLogin.setVisibility(View.VISIBLE);
            llUnLogin.setVisibility(View.GONE);
            initFragment();
        } else {
            llAlLogin.setVisibility(View.GONE);
            llUnLogin.setVisibility(View.VISIBLE);
        }
    }

    private void setListener() {
        btnLogin.setOnClickListener(this);
    }

    private void initView(View view) {
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        tabPageIndicator = (TabPageIndicator) view.findViewById(R.id.tab_page_indicator);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        llAlLogin = (LinearLayout) view.findViewById(R.id.ll_allogin);
        llUnLogin = (LinearLayout) view.findViewById(R.id.ll_unlogin);
    }

    private void initFragment() {
        titleList.add("全部订单");
        titleList.add("待评价");
        listFragmentsa.add(new AllOrderFragment());
        listFragmentsa.add(new ToBeEvaluateFragment());
        // 此处，如果不是继承的FragmentActivity,而是继承的Fragment，则参数应该传入getChildFragmentManager()
        mAdatpter = new OrderFragmentPagerAdapter(getChildFragmentManager(),
                listFragmentsa, titleList);
        viewpager.setAdapter(mAdatpter);
        tabPageIndicator.setViewPager(viewpager);
        tabPageIndicator.setVisibility(View.VISIBLE);
        tabPageIndicator.setIndicatorHeight(UIUtils.dip2px(2));
        tabPageIndicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_NOSAME);
        tabPageIndicator.requestLayout();
//        tabPageIndicator.setBackgroundResource(R.drawable.viewpager_tab_indicator);
        viewpager.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://跳转到登录注册页面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

}
