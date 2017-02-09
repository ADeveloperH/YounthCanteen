package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobile.younthcanteen.AppManager;
import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.SimpleResultBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * author：hj
 * time: 2017/2/9 0009 21:28
 */

public class ModifyPwdActivity extends BaseActivity {
    private EditText etOldPwd;
    private EditText etNewPwd;
    private EditText etReNewPwd;
    private Button btnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("修改密码");
        setTitleBackVisible(true);
        checkLogin(true);
        setContentView(R.layout.activity_modifypwd_layout);

        initView();
        setListener();
    }

    private void initView() {
        etOldPwd = (EditText) findViewById(R.id.et_old_pwd);
        etNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        etReNewPwd = (EditText) findViewById(R.id.et_re_new_pwd);

        btnOk = (Button) findViewById(R.id.btn_ok);
    }

    private void setListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPwdStr = etOldPwd.getText().toString();
                String newPwdStr = etNewPwd.getText().toString();
                String newRePwdStr = etReNewPwd.getText().toString();

                if (TextUtils.isEmpty(oldPwdStr)) {
                    ToastUtils.showLongToast("当前密码不能为空");
                } else if (TextUtils.isEmpty(newPwdStr)) {
                    ToastUtils.showLongToast("新密码不能为空");
                } else if (TextUtils.isEmpty(newRePwdStr)) {
                    ToastUtils.showLongToast("确认密码不能为空");
                } else if (!newPwdStr.equals(newRePwdStr)) {
                    ToastUtils.showLongToast("两次输入的密码不一致");
                } else if (newPwdStr.length() < 6) {
                    ToastUtils.showLongToast("新密码的长度应为6到15个字符");
                } else {
                    modifyPwd(oldPwdStr, newPwdStr);
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
    private void modifyPwd(String oldPwdStr, String newPwdStr) {
        RequestParams params = new RequestParams();
        params.put("oldPass", oldPwdStr);
        params.put("newPass", newPwdStr);
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.MODIFYPWD, params, new MyTextAsyncResponseHandler(act, "提交中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                if (null != bean) {
                    ToastUtils.showLongToast(bean.getReturnMessage());
                    if ("0".equals(bean.getReturnCode())) {
                        SharedPreferencesUtil.clear();
                        AppManager.getAppManager().finishAllActivity();
                        startActivity(new Intent(act, LoginActivity.class));
                    }
                } else {
                    ToastUtils.showLongToast("服务器异常，请稍后重试");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
                ToastUtils.showLongToast("服务器异常，请稍后重试");

            }
        });
    }
}
