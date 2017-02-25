package com.mobile.younthcanteen.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.HomeListAdapter;
import com.mobile.younthcanteen.bean.HomeDataBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.ToastUtils;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class HomeFragment extends Fragment {
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
    private ListView lvHome;
    private Context mActivity;
    private ImageView ivBanner;
    private ViewPager viewPager;
    private LinearLayout llPointGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
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
//            setListener();
            mActivity = getActivity();
            getData();
            isNeedReLoad = false;
        }
    }

    private void initView(View view) {
        lvHome = (ListView) view.findViewById(R.id.lv_home);
        ivBanner = (ImageView) view.findViewById(R.id.iv_banner);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        llPointGroup = (LinearLayout) view.findViewById(R.id.ll_point_group);

    }

    private void getData() {
        RequestParams params = new RequestParams();
        Http.post(Http.GETHOMEDATA, params, new MyTextAsyncResponseHandler(mActivity, "正在加载中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                HomeDataBean homeDataBean = JsonUtil.fromJson(content, HomeDataBean.class);
                if (null != homeDataBean) {
                    if (Http.SUCCESS.equals(homeDataBean.getReturnCode())) {
                        //成功
                        HomeListAdapter adapter = new HomeListAdapter(mActivity, homeDataBean.getCenter());
                        lvHome.setAdapter(adapter);
                    } else {
                        ToastUtils.showShortToast(homeDataBean.getReturnMessage());
                    }

                } else {
                    ToastUtils.showShortToast("服务器数据异常,请稍后重试.");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常,请稍后重试.");
            }
        });
    }
}
