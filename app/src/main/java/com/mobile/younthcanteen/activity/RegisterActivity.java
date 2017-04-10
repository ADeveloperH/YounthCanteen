package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.mobile.younthcanteen.AppManager;
import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.RegisterResultBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.DataCheckUtils;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author：hj
 * time: 2017/2/7 0007 23:12
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText etPhone;
    private EditText etPassword;
    private EditText etRePassword;
    private EditText etCode;
    private Button btnGetCode;
    private Button btnRegister;
    private CheckBox checkBox;
    private Activity act;
    private boolean isTiming = false;//是否正在倒计时
    private TextWatcher watcherCanReg = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String phoneNumStr = etPhone.getText().toString().trim();
            String pwdStr = etPassword.getText().toString().trim();
            String codeStr = etCode.getText().toString().trim();
            String rePwdStr = etRePassword.getText().toString().trim();
            if (!TextUtils.isEmpty(pwdStr) && !TextUtils.isEmpty(rePwdStr)
                    && !TextUtils.isEmpty(codeStr)
                    && DataCheckUtils.isValidatePhone(phoneNumStr)
                    && checkBox.isChecked()) {
                //输入数据合法
                btnRegister.setEnabled(true);
                btnRegister.setBackgroundResource(R.drawable.login_btn_enable);
                btnRegister.setTextColor(Color.parseColor("#000000"));
            } else {
                btnRegister.setEnabled(false);
                btnRegister.setBackgroundResource(R.drawable.login_btn_unable);
                btnRegister.setTextColor(Color.parseColor("#ffffff"));
            }

            if (DataCheckUtils.isValidatePhone(phoneNumStr) && !isTiming) {
                //输入手机号合法
                btnGetCode.setEnabled(true);
                btnGetCode.setBackgroundResource(R.drawable.login_btn_enable);
                btnGetCode.setTextColor(Color.parseColor("#000000"));
            } else {
                btnGetCode.setEnabled(false);
                btnGetCode.setBackgroundResource(R.drawable.login_btn_unable);
                btnGetCode.setTextColor(Color.parseColor("#ffffff"));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("注册");
        setTitleBackVisible(true);
        setContentView(R.layout.activity_register_layout);

        act = this;
        AppManager.getAppManager().addActivity(this);
        initView();
        setListener();
    }

    private void initView() {
        etPhone = (EditText) findViewById(R.id.et_phone);
        etPassword = (EditText) findViewById(R.id.et_password);
        etRePassword = (EditText) findViewById(R.id.et_repassword);
        etCode = (EditText) findViewById(R.id.et_code);
        btnGetCode = (Button) findViewById(R.id.btn_getcode);
        btnRegister = (Button) findViewById(R.id.btn_register);

        checkBox = (CheckBox) findViewById(R.id.checkbox);
    }

    private void setListener() {
        etPhone.addTextChangedListener(watcherCanReg);
        etPassword.addTextChangedListener(watcherCanReg);
        etCode.addTextChangedListener(watcherCanReg);
        etRePassword.addTextChangedListener(watcherCanReg);

        btnGetCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String phoneNumStr = etPhone.getText().toString().trim();
                String pwdStr = etPassword.getText().toString().trim();
                String codeStr = etCode.getText().toString().trim();
                String rePwdStr = etRePassword.getText().toString().trim();
                if (!TextUtils.isEmpty(pwdStr) && !TextUtils.isEmpty(rePwdStr)
                        && !TextUtils.isEmpty(codeStr)
                        && DataCheckUtils.isValidatePhone(phoneNumStr)
                        && isChecked) {
                    //输入数据合法
                    btnRegister.setEnabled(true);
                    btnRegister.setBackgroundResource(R.drawable.login_btn_enable);
                    btnRegister.setTextColor(Color.parseColor("#000000"));
                } else {
                    btnRegister.setEnabled(false);
                    btnRegister.setBackgroundResource(R.drawable.login_btn_unable);
                    btnRegister.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getcode://获取验证码
                getCode();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        final String phoneNumStr = etPhone.getText().toString().trim();
        String codeStr = etCode.getText().toString().trim();
        String pwdStr = etPassword.getText().toString().trim();
        final String rePwdStr = etRePassword.getText().toString().trim();
        if (!pwdStr.equals(rePwdStr)) {
            ToastUtils.showLongToast("两次输入的密码不一致。");
            return;
        } else if (pwdStr.length() < 6) {
            ToastUtils.showLongToast("请设置6位以上的密码。");
        }
        RequestParams params = new RequestParams();
        params.put("phone", phoneNumStr);
        params.put("password", pwdStr);
        params.put("code", codeStr);
        Http.post(Http.REGISTER, params, new MyTextAsyncResponseHandler(act, "注册中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.showLongToast("服务器响应失败");
                        return;
                    }
                    RegisterResultBean registerResultBean = JsonUtil.fromJson(content,
                            RegisterResultBean.class);
                    ToastUtils.showLongToast(registerResultBean.getReturnMessage());
                    if (Http.SUCCESS.equals(registerResultBean.getReturnCode())) {
                        SharedPreferencesUtil.setToken(registerResultBean.getResults().getToken());
                        SharedPreferencesUtil.setAccount(phoneNumStr);
                        SharedPreferencesUtil.setUserId(registerResultBean.getResults().getUserid());
                        SharedPreferencesUtil.setNickName(registerResultBean.getResults().getNick());
                        startActivity(new Intent(act,MainActivity.class));
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("数据异常，请稍后重试");
                }

            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showLongToast("注册失败，请重试");
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                btnGetCode.setText("(" + msg.arg1 + ")" + "秒后重新获取");
                btnGetCode.setTextColor(Color.parseColor("#ffffff"));
                if (msg.arg1 <= 0) {
                    isTiming = false;
                    btnGetCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    btnGetCode.setEnabled(true);
                    btnGetCode.setBackgroundResource(R.drawable.login_btn_enable);
                    btnGetCode.setText("点击重新获取");
                    btnGetCode.setTextColor(Color.parseColor("#000000"));
                    if (timer != null)
                        timer.cancel();
                }
            }
        }
    };
    Timer timer = null;

    private void startTimer() {
        timer = new Timer();
        btnGetCode.setBackgroundResource(R.drawable.login_btn_unable);
        btnGetCode.setEnabled(false);
        TimerTask timerTask = new TimerTask() {
            int i = 60;

            @Override
            public void run() {
                isTiming = true;
                i--;
                Message message = Message.obtain();
                message.arg1 = i;
                message.what = 0;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 0, 1 * 1000);
    }

    /**
     * 获取短信验证码
     */
    private void getCode() {
        String phoneNumStr = etPhone.getText().toString().trim();
        RequestParams params = new RequestParams();
        params.put("phone", phoneNumStr);
        Http.post(Http.SENDCODE, params, new MyTextAsyncResponseHandler(act, "正在发送中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showLongToast("服务器响应失败");
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    String returnCode = jsonObject.optString("returnCode");
                    String returnMessage = jsonObject.optString("returnMessage");
                    if (Http.SUCCESS.equals(returnCode)) {
                        startTimer();
                    }
                    ToastUtils.showLongToast(returnMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showLongToast("发送失败，请重试");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showLongToast("发送失败，请重试");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
