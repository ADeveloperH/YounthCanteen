package com.mobile.younthcanteen.activity;

import android.app.Activity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

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
        }
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
