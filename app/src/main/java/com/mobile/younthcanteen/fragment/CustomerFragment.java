package com.mobile.younthcanteen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.younthcanteen.R;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class CustomerFragment extends Fragment {
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
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
//            initView(getView());
//            initData();
//            setListener();
//            getData();
            isNeedReLoad = false;
        }
    }
}
