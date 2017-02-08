package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.util.DataCheckUtils;

/**
 * author：hj
 * time: 2017/2/7 0007 23:12
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    private TextView tvStep1;
    private TextView tvStep2;
    private TextView tvStep3;
    private LinearLayout llStep1;
    private LinearLayout llStep2;
    private LinearLayout llStep3;
    private EditText etPhone;
    private EditText etCode;
    private EditText etPwd;
    private EditText etRePwd;
    private Button btnSendCode;
    private Button btnCommitCode;
    private Button btnGetCode;
    private Button btnRegister;
    private TextWatcher watcherPhone = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String phoneNumStr = etPhone.getText().toString().trim();
            if (DataCheckUtils.isValidatePhone(phoneNumStr)) {
                //手机号合法
                btnSendCode.setEnabled(true);
                btnSendCode.setBackgroundResource(R.drawable.login_btn_enable);
                btnSendCode.setTextColor(Color.parseColor("#000000"));
            } else {
                btnSendCode.setEnabled(false);
                btnSendCode.setBackgroundResource(R.drawable.login_btn_unable);
                btnSendCode.setTextColor(Color.parseColor("#ffffff"));
            }
        }
    };
    private TextWatcher watcherCode = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String codeStr = etCode.getText().toString().trim();
            if (TextUtils.isEmpty(codeStr)) {
                btnCommitCode.setEnabled(false);
                btnCommitCode.setBackgroundResource(R.drawable.login_btn_unable);
                btnCommitCode.setTextColor(Color.parseColor("#ffffff"));
            } else {
                btnCommitCode.setEnabled(true);
                btnCommitCode.setBackgroundResource(R.drawable.login_btn_enable);
                btnCommitCode.setTextColor(Color.parseColor("#000000"));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

        initView();
        setListener();
    }

    private void initView() {
        tvStep1 = (TextView) findViewById(R.id.tv_step1);
        tvStep2 = (TextView) findViewById(R.id.tv_step2);
        tvStep3 = (TextView) findViewById(R.id.tv_step3);

        llStep1 = (LinearLayout) findViewById(R.id.ll_step1);
        llStep2 = (LinearLayout) findViewById(R.id.ll_step2);
        llStep3 = (LinearLayout) findViewById(R.id.ll_step3);

        etPhone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etRePwd = (EditText) findViewById(R.id.et_repwd);

        btnSendCode = (Button) findViewById(R.id.btn_sendcode);
        btnCommitCode = (Button) findViewById(R.id.btn_commitcode);
        btnGetCode = (Button) findViewById(R.id.btn_getcode);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    private void setListener() {
        etPhone.addTextChangedListener(watcherPhone);
        etCode.addTextChangedListener(watcherCode);
        btnSendCode.setOnClickListener(this);
        btnCommitCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendcode:
                //到第二步
                handleStepState(2);
                break;
            case R.id.btn_commitcode:
                handleStepState(3);
                break;
        }

    }


    private void handleStepState(int step) {
        switch (step) {
            case 2:
                llStep1.setVisibility(View.GONE);
                llStep3.setVisibility(View.GONE);
                llStep2.setVisibility(View.VISIBLE);
                tvStep1.setTextColor(getResources().getColor(R.color.grayblack));
                tvStep2.setTextColor(getResources().getColor(R.color.textblack));
                tvStep3.setTextColor(getResources().getColor(R.color.grayblack));
                break;
            case 3:
                llStep1.setVisibility(View.GONE);
                llStep2.setVisibility(View.GONE);
                llStep3.setVisibility(View.VISIBLE);
                tvStep1.setTextColor(getResources().getColor(R.color.grayblack));
                tvStep2.setTextColor(getResources().getColor(R.color.grayblack));
                tvStep3.setTextColor(getResources().getColor(R.color.textblack));
                break;
        }
    }
}
