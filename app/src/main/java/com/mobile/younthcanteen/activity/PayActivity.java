package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.ui.pwdinput.InputPwdView;
import com.mobile.younthcanteen.ui.pwdinput.MyInputPwdUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/3/15 0015 21:09
 */


public class PayActivity extends BaseActivity {
    @BindView(R.id.iv_rb_yue)
    ImageView ivRbYue;
    @BindView(R.id.rl_yue)
    RelativeLayout rlYue;
    @BindView(R.id.iv_rb_weixin)
    ImageView ivRbWeixin;
    @BindView(R.id.rl_weixin)
    RelativeLayout rlWeixin;
    @BindView(R.id.iv_rb_zfb)
    ImageView ivRbZfb;
    @BindView(R.id.rl_zfb)
    RelativeLayout rlZfb;
    private String orderno;//订单号
    private MyInputPwdUtil myInputPwdUtil;
    private int curSelect = 0;//当前选择的支付方式

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("支付订单");
        checkLogin(true);
        setTitleBackVisible(true);
        setContentView(R.layout.activity_pay_layout);
        ButterKnife.bind(this);
        initData();
        initPwdInput();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("orderno")) {
            orderno = intent.getStringExtra("orderno");
        }

        setChecked(0);
    }

    private void initPwdInput() {
        myInputPwdUtil = new MyInputPwdUtil(this);
        myInputPwdUtil.getMyInputDialogBuilder().setAnimStyle(R.style.dialog_anim);
        myInputPwdUtil.setListener(new InputPwdView.InputPwdListener() {
            @Override
            public void hide() {
                myInputPwdUtil.hide();
            }

            @Override
            public void forgetPwd() {
            }

            @Override
            public void finishPwd(String pwd) {
                Toast.makeText(act, pwd, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 设置选择的支付方式
     *
     * @param i 0 余额支付  1 微信支付  2 支付宝支付
     */
    private void setChecked(int i) {
        curSelect = i;
        switch (i) {
            case 0:
                ivRbYue.setImageResource(R.drawable.rb_bg_checked);
                ivRbWeixin.setImageResource(R.drawable.rb_bg_normal);
                ivRbZfb.setImageResource(R.drawable.rb_bg_normal);
                break;
            case 1:
                ivRbWeixin.setImageResource(R.drawable.rb_bg_checked);
                ivRbYue.setImageResource(R.drawable.rb_bg_normal);
                ivRbZfb.setImageResource(R.drawable.rb_bg_normal);
                break;
            case 2:
                ivRbZfb.setImageResource(R.drawable.rb_bg_checked);
                ivRbYue.setImageResource(R.drawable.rb_bg_normal);
                ivRbWeixin.setImageResource(R.drawable.rb_bg_normal);
                break;

        }
    }

    @OnClick({R.id.rl_yue, R.id.rl_weixin, R.id.rl_zfb, R.id.btn_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_yue:
                setChecked(0);
                break;
            case R.id.rl_weixin:
                setChecked(1);
                break;
            case R.id.rl_zfb:
                setChecked(2);
                break;
            case R.id.btn_pay://立即支付
                if (curSelect == 0) {
                    //余额支付
                    if (myInputPwdUtil != null) {
                        myInputPwdUtil.show();
                    } else {
                        initPwdInput();
                        myInputPwdUtil.show();
                    }
                } else {
                    payOrder();
                }
                break;
        }
    }

    /**
     * 支付订单
     */
    private void payOrder() {
        RequestParams params = new RequestParams();
        params.put("orderno", orderno);
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("type", curSelect);
        Http.post(Http.PAYORDER, params, new MyTextAsyncResponseHandler(act, "提交中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
            }
        });
    }
}
