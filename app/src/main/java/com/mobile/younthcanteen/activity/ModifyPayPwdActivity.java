package com.mobile.younthcanteen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
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

/**
 * author：hj
 * time: 2017/2/9 0009 21:28
 */

public class ModifyPayPwdActivity extends BaseActivity {
    private EditText etOldPwd;
    private EditText etNewPwd;
    private EditText etReNewPwd;
    private Button btnOk;
    private boolean isSetPayPwd;//当前是否设置了支付密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("修改支付密码");
        setTitleBackVisible(true);
        checkLogin(true);
        setContentView(R.layout.activity_modifypaypwd_layout);

        isSetPayPwd = SharedPreferencesUtil.getIsSetPayPwd();
        initView();
        setListener();
    }

    private void initView() {
        etOldPwd = (EditText) findViewById(R.id.et_old_pwd);
        etNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        etReNewPwd = (EditText) findViewById(R.id.et_re_new_pwd);

        btnOk = (Button) findViewById(R.id.btn_ok);

        if (!isSetPayPwd) {
            //未设置过密码
            etOldPwd.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPwdStr = etOldPwd.getText().toString();
                String newPwdStr = etNewPwd.getText().toString();
                String newRePwdStr = etReNewPwd.getText().toString();

                if (isSetPayPwd) {
                    if (TextUtils.isEmpty(oldPwdStr) || oldPwdStr.length() != 6) {
                        ToastUtils.showLongToast("当前密码格式错误");
                    } else if (TextUtils.isEmpty(newPwdStr) || newPwdStr.length() != 6) {
                        ToastUtils.showLongToast("新密码格式错误");
                    } else if (TextUtils.isEmpty(newRePwdStr) || newRePwdStr.length() != 6) {
                        ToastUtils.showLongToast("确认密码格式错误");
                    } else if (!newPwdStr.equals(newRePwdStr)) {
                        ToastUtils.showLongToast("两次输入的密码不一致");
                    } else {
                        modifyPayPwd(oldPwdStr, newPwdStr);
                    }
                } else {
                    if (TextUtils.isEmpty(newPwdStr) || newPwdStr.length() != 6) {
                        ToastUtils.showLongToast("新密码格式错误");
                    } else if (TextUtils.isEmpty(newRePwdStr) || newRePwdStr.length() != 6) {
                        ToastUtils.showLongToast("确认密码格式错误");
                    } else if (!newPwdStr.equals(newRePwdStr)) {
                        ToastUtils.showLongToast("两次输入的密码不一致");
                    } else {
                        modifyPayPwd(null, newPwdStr);
                    }
                }

            }
        });
    }

    /**
     * 修改密码
     *
     * @param oldPwdStr
     * @param newPwdStr
     */
    private void modifyPayPwd(String oldPwdStr, String newPwdStr) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(oldPwdStr)) {
            params.put("oldPass", oldPwdStr);
        }
        params.put("newPass", newPwdStr);
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.SETPAYPWD, params, new MyTextAsyncResponseHandler(act, "提交中...") {
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
}
