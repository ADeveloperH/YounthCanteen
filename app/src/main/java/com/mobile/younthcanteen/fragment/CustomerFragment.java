package com.mobile.younthcanteen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.LoginActivity;
import com.mobile.younthcanteen.activity.MyAccountActivity;
import com.mobile.younthcanteen.util.LoginUtils;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class CustomerFragment extends Fragment implements View.OnClickListener {
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
    private LinearLayout llUser;
    private ImageView ivUserIcon;
    private TextView tvNickName;
    private LinearLayout llYuE;
    private LinearLayout llJiFen;
    private TextView tvYuE;
    private TextView tvJiFen;
    private LinearLayout llPingJia;
    private LinearLayout llShouCang;
    private LinearLayout llAddress;
    private LinearLayout llYaoQing;
    private LinearLayout llFanKui;
    private LinearLayout llKeFu;
    private LinearLayout llUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_customer, null);
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
//            initData();
//            getData();
            isNeedReLoad = false;
        }
    }

    private void initView(View view) {
        llUser = (LinearLayout) view.findViewById(R.id.ll_userinfo);
        ivUserIcon = (ImageView) view.findViewById(R.id.iv_usericon);
        tvNickName = (TextView) view.findViewById(R.id.tv_nickname);

        llYuE = (LinearLayout) view.findViewById(R.id.ll_yue);
        llJiFen = (LinearLayout) view.findViewById(R.id.ll_jifen);
        tvYuE = (TextView) view.findViewById(R.id.tv_yue);
        tvJiFen = (TextView) view.findViewById(R.id.tv_jifen);

        llPingJia = (LinearLayout) view.findViewById(R.id.ll_pingjia);
        llShouCang = (LinearLayout) view.findViewById(R.id.ll_shoucang);
        llAddress = (LinearLayout) view.findViewById(R.id.ll_address);
        llYaoQing = (LinearLayout) view.findViewById(R.id.ll_yaoqing);
        llFanKui = (LinearLayout) view.findViewById(R.id.ll_fankui);
        llKeFu = (LinearLayout) view.findViewById(R.id.ll_kefu);
        llUpdate = (LinearLayout) view.findViewById(R.id.ll_update);
    }

    private void setListener() {
        llUser.setOnClickListener(this);
        tvNickName.setOnClickListener(this);
        ivUserIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_userinfo:
            case R.id.tv_nickname:
            case R.id.iv_usericon://进入用户信息页面
                if (!LoginUtils.isLogin()) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyAccountActivity.class));
                break;
        }
    }
}
