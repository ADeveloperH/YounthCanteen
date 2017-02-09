package com.mobile.younthcanteen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.util.DataCheckUtils;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;

/**
 * author：hj
 * time: 2017/2/9 0009 11:08
 */

public class MyAccountActivity extends BaseActivity {
    private LinearLayout llUser;
    private LinearLayout llNickName;
    private LinearLayout llPwd;
    private LinearLayout llPhoneNumber;
    private ImageView ivUserIcon;
    private TextView tvNickName;
    private TextView tvPhoneNumber;
    private Button btnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的账号");
        setTitleBackVisible(View.VISIBLE);
        setContentView(R.layout.activity_myaccount_layout);

        initView();
        initData();
    }

    private void initView() {
        llUser = (LinearLayout) findViewById(R.id.ll_userinfo);
        llNickName = (LinearLayout) findViewById(R.id.ll_nick_name);
        llPwd = (LinearLayout) findViewById(R.id.ll_pwd);
        llPhoneNumber = (LinearLayout) findViewById(R.id.ll_phonenumber);
        ivUserIcon = (ImageView) findViewById(R.id.iv_usericon);
        tvNickName = (TextView) findViewById(R.id.tv_nickname);
        tvPhoneNumber = (TextView) findViewById(R.id.tv_phonenumber);
        btnLogout = (Button) findViewById(R.id.btn_logout);
    }

    private void initData() {
        tvNickName.setText(SharedPreferencesUtil.getNickName());
        String phoneNumber = SharedPreferencesUtil.getAccount().replace(" ","");
        if (DataCheckUtils.isValidatePhone(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        }
        tvPhoneNumber.setText(phoneNumber);
    }
}
