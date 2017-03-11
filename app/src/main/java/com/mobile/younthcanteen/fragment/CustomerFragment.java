package com.mobile.younthcanteen.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.LoginActivity;
import com.mobile.younthcanteen.activity.MyAccountActivity;
import com.mobile.younthcanteen.activity.MyAddressActivity;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.DownLoader;
import com.mobile.younthcanteen.util.LoginUtils;
import com.mobile.younthcanteen.util.NetWorkUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;
import com.mobile.younthcanteen.util.UpdateAppUtil;

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
    private ImageView ivRightArrow;
    private Activity mActivity;

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
//            getData()
            isNeedReLoad = false;
        }
    }

    private boolean isRefreshUI = false;//是否在刷新UI

    @Override
    public void onResume() {
        super.onResume();
        if (!isRefreshUI) {
            refreshUI();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            if (!isNeedReLoad && !isRefreshUI) {
                //当前view已进行初始化且未刷新UI
                refreshUI();
            }
        } else {
            //相当于Fragment的onPause
        }
    }

    private void refreshUI() {
        Log.d("hj", "Customer::refreshUI");
        isRefreshUI = true;
        mActivity = getActivity();
        if (LoginUtils.isLogin()) {
            String nickName = SharedPreferencesUtil.getNickName();
            tvNickName.setText(nickName);
            ivRightArrow.setVisibility(View.VISIBLE);
        } else {
            tvNickName.setText("登录/注册");
            ivRightArrow.setVisibility(View.GONE);
            tvYuE.setText("----");
            tvJiFen.setText("----");
        }
        isRefreshUI = false;
    }

    private void initView(View view) {
        llUser = (LinearLayout) view.findViewById(R.id.ll_userinfo);
        ivUserIcon = (ImageView) view.findViewById(R.id.iv_usericon);
        ivRightArrow = (ImageView) view.findViewById(R.id.iv_right_arrow);
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
        llUpdate.setOnClickListener(this);
        llAddress.setOnClickListener(this);
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
            case R.id.ll_update://检查更新
                if (!NetWorkUtil.hasAvailableNetWork(mActivity)) {
                    Toast.makeText(mActivity, "当前无可用网络", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * 判断当前是否在下载，如果正在下载就直接显示下载的对话框
                 */
                if (DownLoader.getInstance(mActivity).isDownloading()) {
                    //当前正在下载
                    ToastUtils.showLongToast("当前正在下载中");
                } else {
                    checkNewVersion();
                }
                break;
            case R.id.ll_address://常用地址
                startActivity(new Intent(mActivity, MyAddressActivity.class));
                break;
        }
    }

    /**
     * 检测是否有新的版本更新
     */
    private void checkNewVersion() {
        RequestParams params = new RequestParams();
//        params.put("moduleId", "CustomerFragment");
        UpdateAppUtil.checkNewVersion(mActivity, "正在检查更新...", params,
                new UpdateAppUtil.CheckVersionResultListener() {
                    @Override
                    public void getDataFailure(Throwable error) {
                        UpdateAppUtil.isUpdating = false;
                    }

                    @Override
                    public void aleryNewVersion(String curVersionName) {
                        //当前已是最新版本
                        Toast.makeText(mActivity, "当前已是最新版本", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void hasNewVersion() {
                    }

                    @Override
                    public void parseDataFailure(Exception e) {
                        ToastUtils.showLongToast("数据异常，请稍后重试");
                    }

                    @Override
                    public void startRequest() {
                    }
                });
    }
}
