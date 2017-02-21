package com.mobile.younthcanteen.activity;

import android.content.Intent;
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
 * time: 2017/2/9 0009 14:45
 */

public class ModifyNickNameActivity extends BaseActivity {
    private EditText etNickName;
    private Button btnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("修改用户名");
        setTitleBackVisible(true);
        checkLogin(true);
        setContentView(R.layout.activity_modifynickname_layout);

        initView();
        initData();
        setListener();
    }

    private void initView() {
        etNickName = (EditText) findViewById(R.id.et_nickname);
        btnOk = (Button) findViewById(R.id.btn_ok);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null != intent && intent.hasExtra("nickName")) {
            String nickName = intent.getStringExtra("nickName");
            etNickName.setText(nickName);
            etNickName.setSelection(nickName.length());
        }
    }

    private void setListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickNameStr = etNickName.getText().toString();
                if (TextUtils.isEmpty(nickNameStr)) {
                    ToastUtils.showLongToast("请输入新用户名");
                } else {
                    modifyNickName(nickNameStr);
                }
            }
        });
    }


    /**
     * 修改用户名
     * @param nickNameStr
     */
    private void modifyNickName(final String nickNameStr) {
        RequestParams params = new RequestParams();
        params.put("nick", nickNameStr);
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.MODIFYNICKNAME,params,new MyTextAsyncResponseHandler(act,"提交中..."){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                if (null != bean) {
                    ToastUtils.showLongToast(bean.getReturnMessage());
                    if ("0".equals(bean.getReturnCode())) {
                        SharedPreferencesUtil.setNickName(nickNameStr);
                        finish();
                    }
                } else {
                    ToastUtils.showLongToast("服务器异常，请稍后重试");
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
