package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.mobile.younthcanteen.R;

/**
 * author：hj
 * time: 2017/2/7 0007 23:12
 */

public class RegisterActivity extends Activity {
    private EditText etPhone;
    private EditText etPassword;
    private EditText etRePassword;
    private EditText etCode;
    private Button btnGetCode;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

        initView();
    }

    private void initView() {
        etPhone = (EditText) findViewById(R.id.et_phone);
        etPassword  = (EditText) findViewById(R.id.et_password);
        etRePassword  = (EditText) findViewById(R.id.et_repassword);
        etCode  = (EditText) findViewById(R.id.et_code);
        btnGetCode = (Button) findViewById(R.id.btn_getcode);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }
}
