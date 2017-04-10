package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.UserDetailInfoBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/4/4 0004 12:08
 * 余额页面
 */


public class BalanceActivity extends BaseActivity {
    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.ll_recharge)
    LinearLayout llRecharge;
    @BindView(R.id.ll_consume_detail)
    LinearLayout llConsumeDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBackVisible(true);
        checkLogin(true);
        setTitle("余额");
        setContentView(R.layout.activity_balance_layout);
        ButterKnife.bind(this);

//        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetailInfo();
    }

//    private void initData() {
//        String money = SharedPreferencesUtil.getMoney();
//        if (TextUtils.isEmpty(money)) {
//            //余额请求失败
//            getUserDetailInfo();
//        } else {
//            tvYue.setText(money);
//        }
//    }

    /**
     * 获取用户的详细信息
     */
    private void getUserDetailInfo() {
        RequestParams params = new RequestParams();
        params.put("userid", SharedPreferencesUtil.getUserId());
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.GETUSERDETAILINFO, params, new MyTextAsyncResponseHandler(act, "获取中...") {
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
                        tvYue.setText(result.getMoney());
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


    @OnClick({R.id.ll_recharge, R.id.ll_consume_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_recharge://充值
                startActivity(new Intent(act,AccountRechargeActivity.class));
                break;
            case R.id.ll_consume_detail://消费明细
                startActivity(new Intent(act,ConsumeDetailActivity.class));
                break;
        }
    }
}
