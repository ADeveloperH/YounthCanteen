package com.mobile.younthcanteen.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.BalanceActivity;
import com.mobile.younthcanteen.activity.FeedBackActivity;
import com.mobile.younthcanteen.activity.LoginActivity;
import com.mobile.younthcanteen.activity.MyAccountActivity;
import com.mobile.younthcanteen.activity.MyAddressActivity;
import com.mobile.younthcanteen.bean.UserDetailInfoBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.ui.CircleDrawable;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.DownLoader;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.LoginUtils;
import com.mobile.younthcanteen.util.NetWorkUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;
import com.mobile.younthcanteen.util.UIUtils;
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
    private LinearLayout llAddress;
    private LinearLayout llYaoQing;
    private LinearLayout llFanKui;
    private LinearLayout llUpdate;
    private ImageView ivRightArrow;
    private Activity mActivity;
    private TextView tvServicePhone;
    private BitmapUtil bitmapUtil;

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
        isRefreshUI = true;
        mActivity = getActivity();
        if (LoginUtils.isLogin()) {
            String nickName = SharedPreferencesUtil.getNickName();
            tvNickName.setText(nickName);
            ivRightArrow.setVisibility(View.VISIBLE);
            getUserDetailInfo();
        } else {
            tvNickName.setText("登录/注册");
            ivRightArrow.setVisibility(View.GONE);
        }
        isRefreshUI = false;
    }

    /**
     * 获取用户的详细信息
     */
    private void getUserDetailInfo() {
        RequestParams params = new RequestParams();
        params.put("userid", SharedPreferencesUtil.getUserId());
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.GETUSERDETAILINFO, params, new MyTextAsyncResponseHandler(getActivity(), null) {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    UserDetailInfoBean bean = JsonUtil.fromJson(content, UserDetailInfoBean.class);
                    if (null != bean) {
                        if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                            ToastUtils.showShortToast(bean.getReturnMessage());
                            return;
                        }
                        UserDetailInfoBean.ResultsEntity result = bean.getResults();
                        SharedPreferencesUtil.setNickName(result.getNick());
                        SharedPreferencesUtil.setToken(result.getToken());
                        SharedPreferencesUtil.setUserId(result.getUserid());
                        SharedPreferencesUtil.setPoint(result.getPoint());
                        SharedPreferencesUtil.setMoney(result.getMoney());
                        SharedPreferencesUtil.setIsSetPayPwd(result.isIspaypassset());
                        tvNickName.setText(result.getNick());
                        bitmapUtil.display(ivUserIcon, result.getImgs(), new BitmapUtil.BitmapLoadCallBack() {
                            @Override
                            public void onLoadCompleted(Bitmap bitmap) {
                                ivUserIcon.setImageDrawable(new CircleDrawable(bitmap));
                            }
                        });

                    } else {
                        ToastUtils.showShortToast("服务器数据异常，请稍后重试");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("数据异常，请稍后重试");
                }

            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");

            }
        });

    }

    private void initView(View view) {
        llUser = (LinearLayout) view.findViewById(R.id.ll_userinfo);
        ivUserIcon = (ImageView) view.findViewById(R.id.iv_usericon);
        ivRightArrow = (ImageView) view.findViewById(R.id.iv_right_arrow);
        tvNickName = (TextView) view.findViewById(R.id.tv_nickname);
        tvServicePhone = (TextView) view.findViewById(R.id.tv_service_phone);

        llYuE = (LinearLayout) view.findViewById(R.id.ll_yue);
        llJiFen = (LinearLayout) view.findViewById(R.id.ll_jifen);

        llAddress = (LinearLayout) view.findViewById(R.id.ll_address);
        llYaoQing = (LinearLayout) view.findViewById(R.id.ll_yaoqing);
        llFanKui = (LinearLayout) view.findViewById(R.id.ll_fankui);
        llUpdate = (LinearLayout) view.findViewById(R.id.ll_update);

        Drawable drawable = UIUtils.getDrawable(R.drawable.user_icon_default);
        bitmapUtil = new BitmapUtil(getActivity(),drawable,drawable);
    }

    private void setListener() {
        llUser.setOnClickListener(this);
        tvNickName.setOnClickListener(this);
        ivUserIcon.setOnClickListener(this);
        llUpdate.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        tvServicePhone.setOnClickListener(this);
        llYuE.setOnClickListener(this);
        llJiFen.setOnClickListener(this);
        llFanKui.setOnClickListener(this);
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
            case R.id.tv_service_phone://拨打电话
                String phone = tvServicePhone.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
            case R.id.ll_yue://余额页面
                startActivity(new Intent(getActivity(), BalanceActivity.class));
                break;
            case R.id.ll_fankui://反馈建议页面
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.ll_jifen://积分页面
                ToastUtils.showShortToast("本功能暂未开发");
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
