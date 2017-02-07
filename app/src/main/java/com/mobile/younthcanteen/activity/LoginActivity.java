package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.LoginResultBean;
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
 * time: 2017/2/7 0007 18:24
 */

public class LoginActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    private TextView tvRegister;
    private TextView tvForgetPwd;
    private EditText etAccount;
    private EditText etPassword;
    private ImageView ivDeleteAccount;
    private ImageView ivIsShowPwd;
    private Button btnLogin;
    private ImageView ivDeletePwd;
    private boolean curShowPwdState = false;//是否显示密码。默认不显示
    private Activity act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        act = this;
        initView();
        setListener();
    }

    private void initView() {
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvForgetPwd = (TextView) findViewById(R.id.tv_forgetpwd);

        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);

        ivDeleteAccount = (ImageView) findViewById(R.id.iv_deleteaccount);
        ivDeletePwd = (ImageView) findViewById(R.id.iv_deletepwd);
        ivIsShowPwd = (ImageView) findViewById(R.id.iv_isshowpwd);

        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    private void setListener() {
        etAccount.addTextChangedListener(watcherIsCanLogin);
        etPassword.addTextChangedListener(watcherIsCanLogin);
        etAccount.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);

        ivDeleteAccount.setOnClickListener(this);
        ivDeletePwd.setOnClickListener(this);
        ivIsShowPwd.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private TextWatcher watcherIsCanLogin = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String inputAccountStr = etAccount.getText().toString().trim();
            String inputPwdStr = etPassword.getText().toString().trim();
            if (!TextUtils.isEmpty(inputAccountStr) && etAccount.hasFocus()) {
                ivDeleteAccount.setVisibility(View.VISIBLE);
            } else {
                ivDeleteAccount.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(inputPwdStr) && etPassword.hasFocus()) {
                ivDeletePwd.setVisibility(View.VISIBLE);
            } else {
                ivDeletePwd.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(inputAccountStr) || TextUtils.isEmpty(inputPwdStr)) {
                btnLogin.setEnabled(false);
                btnLogin.setBackgroundResource(R.drawable.login_btn_unable);
                btnLogin.setTextColor(Color.parseColor("#ffffff"));
            } else {
                btnLogin.setEnabled(true);
                btnLogin.setBackgroundResource(R.drawable.login_btn_enable);
                btnLogin.setTextColor(Color.parseColor("#000000"));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_deleteaccount://账号右侧删除
                etAccount.setText("");
                break;
            case R.id.iv_deletepwd://账号右侧删除
                etPassword.setText("");
                break;
            case R.id.iv_isshowpwd://是否显示密码
                String inputPwdStr = etPassword.getText().toString();
                if (curShowPwdState) {
                    //当前显示
                    ivIsShowPwd.setImageResource(R.drawable.passport_ic_show_password);
                    curShowPwdState = false;
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    ivIsShowPwd.setImageResource(R.drawable.passport_ic_hide_password);
                    curShowPwdState = true;
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                etPassword.setSelection(inputPwdStr.length());
                break;
            case R.id.btn_login://登录
                login();
                break;
        }
    }

    private void login() {
        final String inputAccountStr = etAccount.getText().toString().trim();
        String inputPwdStr = etPassword.getText().toString().trim();
        RequestParams params = new RequestParams();
        params.put("account", inputAccountStr);
        params.put("password", inputPwdStr);
        Http.post(Http.LOGIN, params, new MyTextAsyncResponseHandler(act, "正在登录中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (!TextUtils.isEmpty(content)) {
                    LoginResultBean loginResultBean = JsonUtil.fromJson(content, LoginResultBean.class);
                    ToastUtils.showLongToast(loginResultBean.getReturnMessage());
                    if ("0".equals(loginResultBean.getReturnCode())) {
                        SharedPreferencesUtil.setToken(loginResultBean.getResults().getToken());
                        SharedPreferencesUtil.setAccount(inputAccountStr);
                        SharedPreferencesUtil.setUserId(loginResultBean.getResults().getUserid());
                        startActivity(new Intent(act,MainActivity.class));
                        finish();
                    }
                } else {
                    ToastUtils.showLongToast("服务器响应失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
                ToastUtils.showLongToast("登录失败,请重试");
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_account:
                String inputAccountStr = etAccount.getText().toString().trim();
                if (hasFocus) {
                    //输入框有焦点
                    if (TextUtils.isEmpty(inputAccountStr)) {
                        ivDeleteAccount.setVisibility(View.GONE);
                    } else {
                        ivDeleteAccount.setVisibility(View.VISIBLE);
                    }
                } else {
                    ivDeleteAccount.setVisibility(View.GONE);
                }
                break;
            case R.id.et_password:
                String inputPwdStr = etPassword.getText().toString().trim();
                if (hasFocus) {
                    //输入框有焦点
                    if (TextUtils.isEmpty(inputPwdStr)) {
                        ivDeletePwd.setVisibility(View.GONE);
                    } else {
                        ivDeletePwd.setVisibility(View.VISIBLE);
                    }
                } else {
                    ivDeletePwd.setVisibility(View.GONE);
                }
                break;
        }
    }
}
