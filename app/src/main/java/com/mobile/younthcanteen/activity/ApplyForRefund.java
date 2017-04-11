package com.mobile.younthcanteen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.SimpleResultBean;
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
 * time: 2017/4/11 0011 23:13
 * 申请退款
 */


public class ApplyForRefund extends BaseActivity {
    @BindView(R.id.et_reson)
    EditText etReson;
    @BindView(R.id.btn_apply)
    Button btnApply;
    private String orderno;//订单号

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin(true);
        setTitleBackVisible(true);
        setTitle("申请退款");
        setContentView(R.layout.activity_applyforrefund_layout);
        ButterKnife.bind(this);

        orderno = getIntent().getStringExtra("orderno");
        if (TextUtils.isEmpty(orderno)) {
            finish();
        }
    }

    @OnClick(R.id.btn_apply)
    public void onClick() {
        String resonStr = etReson.getText().toString().trim();
        if (TextUtils.isEmpty(resonStr)) {
            ToastUtils.showShortToast("请填写申请退款的原因");
        } else {
            applyForRefund(resonStr);
        }
    }

    /**
     * 申请退款
     * @param resonStr
     */
    private void applyForRefund(String resonStr) {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("orderno", orderno);
        params.put("remark", resonStr);
        Http.post(Http.APPLYPAYBACK, params, new MyTextAsyncResponseHandler(act, "正在申请中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                    if (null != bean) {
                        ToastUtils.showShortToast(bean.getReturnMessage());
                        if (Http.SUCCESS.equals(bean.getReturnCode())) {
                            finish();
                        }
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
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });
    }
}
