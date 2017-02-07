package com.mobile.younthcanteen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.LoginActivity;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class OrderFragment extends Fragment implements View.OnClickListener {
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
    private Button btnLogin;

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
//            initData();
            setListener();
//            getData();
            isNeedReLoad = false;
        }
    }

    private void setListener() {
        btnLogin.setOnClickListener(this);
    }

    private void initView(View view) {
        btnLogin = (Button) view.findViewById(R.id.btn_login);
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
