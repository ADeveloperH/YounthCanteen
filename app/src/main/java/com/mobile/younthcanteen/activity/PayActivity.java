package com.mobile.younthcanteen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.mobile.younthcanteen.ui.pwdinput.InputPwdView;
import com.mobile.younthcanteen.ui.pwdinput.MyInputPwdUtil;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.Map;

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
    @BindView(R.id.btn_pay)
    Button btnPay;
    private String orderno;//订单号
    private MyInputPwdUtil myInputPwdUtil;
    private int curSelect = 0;//当前选择的支付方式
    private double needMoney;//需要的钱数

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
            needMoney = Double.parseDouble(intent.getStringExtra("money"));
            btnPay.setText("确认支付￥" + needMoney);
        }

        setChecked(0);
    }

    private void initPwdInput() {
        myInputPwdUtil = new MyInputPwdUtil(this);
        myInputPwdUtil.getMyInputDialogBuilder().setAnimStyle(R.style.dialog_anim);
        myInputPwdUtil.setListener(new InputPwdView.InputPwdListener() {
            @Override
            public void hide() {
                hiddenInputPwd();
            }

            @Override
            public void forgetPwd() {
            }

            @Override
            public void finishPwd(String pwd) {
                payOrder(pwd);
            }
        });
    }

    /**
     * 设置选择的支付方式
     *
     * @param i 0 余额支付  1 支付宝支付  2 微信支付
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
                ivRbZfb.setImageResource(R.drawable.rb_bg_checked);
                ivRbYue.setImageResource(R.drawable.rb_bg_normal);
                ivRbWeixin.setImageResource(R.drawable.rb_bg_normal);
                break;
            case 2:
                ivRbWeixin.setImageResource(R.drawable.rb_bg_checked);
                ivRbYue.setImageResource(R.drawable.rb_bg_normal);
                ivRbZfb.setImageResource(R.drawable.rb_bg_normal);
                break;
        }
    }

    @OnClick({R.id.rl_yue, R.id.rl_weixin, R.id.rl_zfb, R.id.btn_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_yue:
                setChecked(0);
                break;
            case R.id.rl_zfb:
                setChecked(1);
                break;
            case R.id.rl_weixin:
                setChecked(2);
                break;

            case R.id.btn_pay://立即支付
                if (curSelect == 0) {
                    //余额支付
//                    double remainMoney = Double.parseDouble(SharedPreferencesUtil.getMoney());
//                    if (remainMoney < needMoney) {
//                        //剩余余额不足
//                        ToastUtils.showShortToast("您当前余额不足，选择其它支付方式");
//                    } else {
//
//                    }
                    if (SharedPreferencesUtil.getIsSetPayPwd()) {
                        //如果设置密码需要传入密码
                        if (myInputPwdUtil != null) {
                            myInputPwdUtil.show();
                        } else {
                            initPwdInput();
                            myInputPwdUtil.show();
                        }
                    } else {
                        payOrder(null);
                    }
                } else if (curSelect == 1) {
                    payOrderByAlipay();
                } else {
                    ToastUtils.showShortToast("暂不支持微信支付");
                }
                break;
        }
    }


    /**
     * 通过支付宝提交订单
     */
    private void payOrderByAlipay() {
        RequestParams params = new RequestParams();
        params.put("orderno", orderno);
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.COMMITORDERBYALIPAY, params, new MyTextAsyncResponseHandler(act, "提交中...") {
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
                        validataPayResult(resultStatus,payResult.getMemo(),payResult.getResult());
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(act, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };


    /**
     * 验证支付宝支付的结果
     */
    private void validataPayResult(String resultStatus,String memo,String result) {
        RequestParams params = new RequestParams();
        params.put("memo", memo);
        params.put("resultStatus", resultStatus);
        params.put("result", result);
        Http.post(Http.COMMITALIPAYRESULT,params,new MyTextAsyncResponseHandler(act,null){
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

    /**
     * 支付订单
     *
     * @param pwd 用余额支付时输入的密码
     */
    private void payOrder(String pwd) {
        hiddenInputPwd();
        RequestParams params = new RequestParams();
        params.put("orderno", orderno);
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("type", curSelect + "");
        if (!TextUtils.isEmpty(pwd)) {
            params.put("paypass", pwd);

        }
        Http.post(Http.PAYORDER, params, new MyTextAsyncResponseHandler(act, "提交中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                    if (null != bean) {
                        ToastUtils.showLongToast(bean.getReturnMessage());
                        if (Http.SUCCESS.equals(bean.getReturnCode())) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("tabIndex", 2);
                            startActivity(intent);
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
            }
        });
    }

    /**
     * 隐藏密码输入框
     */
    private void hiddenInputPwd() {
        if (myInputPwdUtil != null) {
            myInputPwdUtil.hide();
        }
    }
}
