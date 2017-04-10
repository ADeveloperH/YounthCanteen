package com.mobile.younthcanteen.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.OrderResultByAlipayBean;
import com.mobile.younthcanteen.bean.PayResult;
import com.mobile.younthcanteen.bean.SimpleResultBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/4/4 0004 17:16
 * 账户充值
 */


public class AccountRechargeActivity extends BaseActivity {
    @BindView(R.id.iv_rb_zfb)
    ImageView ivRbZfb;
    @BindView(R.id.rl_zfb)
    RelativeLayout rlZfb;
    @BindView(R.id.iv_rb_weixin)
    ImageView ivRbWeixin;
    @BindView(R.id.rl_weixin)
    RelativeLayout rlWeixin;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private int curSelect = 1;//当前选择的支付方式

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("账户充值");
        checkLogin(true);
        setTitleBackVisible(true);
        setSubTitle("限额300元");
        setContentView(R.layout.activity_account_recharge_layout);
        ButterKnife.bind(this);

        setChecked(1);
    }


    /**
     * 设置选择的支付方式
     *
     * @param i 1 支付宝支付  2 微信支付
     */
    private void setChecked(int i) {
        curSelect = i;
        switch (i) {
            case 0:
                ivRbWeixin.setImageResource(R.drawable.rb_bg_normal);
                ivRbZfb.setImageResource(R.drawable.rb_bg_normal);
                break;
            case 1:
                ivRbZfb.setImageResource(R.drawable.rb_bg_checked);
                ivRbWeixin.setImageResource(R.drawable.rb_bg_normal);
                break;
            case 2:
                ivRbWeixin.setImageResource(R.drawable.rb_bg_checked);
                ivRbZfb.setImageResource(R.drawable.rb_bg_normal);
                break;
        }
    }


    @OnClick({R.id.rl_zfb, R.id.rl_weixin, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_zfb:
                setChecked(1);
                break;
            case R.id.rl_weixin:
                setChecked(2);
                break;
            case R.id.btn_ok:
                String money = etMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    ToastUtils.showShortToast("请输入金额");
                } else {
                    if (curSelect == 1) {
                        payOrderByAlipay(money);
                    } else {
                        ToastUtils.showShortToast("暂不支持微信支付");
                    }
                }
                break;
        }
    }

    /**
     * 通过支付宝提交订单
     * @param money
     */
    private void payOrderByAlipay(String money) {
        RequestParams params = new RequestParams();
        params.put("money", money);
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.SIGNATURERECHARGE, params, new MyTextAsyncResponseHandler(act, "提交中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    OrderResultByAlipayBean bean = JsonUtil.fromJson(content,
                            OrderResultByAlipayBean.class);
                    if (null != bean) {
                        if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                            ToastUtils.showShortToast(bean.getReturnMessage());
                            return;
                        }
                        awakeAlipay(bean.getSignOrder());
                    } else {
                        ToastUtils.showShortToast("服务器异常，请稍后重试");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("数据异常，请稍后重试");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
            }
        });
    }

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    PayResult payResult = new PayResult(result);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(act, "支付成功", Toast.LENGTH_SHORT).show();
                        Log.d("okhttp", "reslutl:::::" + payResult.toString());
                        validataPayResult(resultStatus, payResult.getMemo(), payResult.getResult());
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(act, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 验证支付宝支付的结果
     */
    private void validataPayResult(String resultStatus, String memo, String result) {
        RequestParams params = new RequestParams();
        params.put("memo", memo);
        params.put("resultStatus", resultStatus);
        params.put("result", result);
        Http.post(Http.COMMITALIPAYRESULT, params, new MyTextAsyncResponseHandler(act, null) {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                    if (null != bean) {
                        ToastUtils.showLongToast(bean.getReturnMessage());
                        if (Http.SUCCESS.equals(bean.getReturnCode())) {
                            finish();
                        }
                    } else {
                        ToastUtils.showLongToast("服务器异常，请稍后重试");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("数据异常，请稍后重试");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showLongToast("服务器异常，请稍后重试");
            }
        });
    }

    /**
     * 使用加签后的账单调起支付宝
     *
     * @param orderInfo
     */
    private void awakeAlipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(act);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
