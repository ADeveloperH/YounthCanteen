package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;

/**
 * author：hj
 * time: 2017/2/7 0007 23:12
 */

public class RegisterActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

        initView();
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
}
