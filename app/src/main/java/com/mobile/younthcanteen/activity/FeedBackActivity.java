package com.mobile.younthcanteen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/4/11 0011 14:13
 * 反馈建议
 */


public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.et_subject)
    EditText etSubject;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin(true);
        setTitleBackVisible(true);
        setTitle("反馈建议");
        setContentView(R.layout.activity_feedback_layout);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_commit)
    public void onClick() {
        String subjectStr = etSubject.getText().toString().trim();
        String emailStr = etEmail.getText().toString().trim();
        String contentStr = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(subjectStr)) {
            ToastUtils.showShortToast("请输入您要反馈的主题");
            return;
        }

        if (TextUtils.isEmpty(contentStr)) {
            ToastUtils.showShortToast("请输入您要反馈的内容");
            return;
        }

        if (TextUtils.isEmpty(emailStr) || !checkEmailFormat(emailStr)) {
            ToastUtils.showShortToast("请输入正确的邮箱格式");
            return;
        }

        commitFeedBack(subjectStr, emailStr, contentStr);

    }

    /**
     * 提交反馈建议
     *
     * @param subjectStr
     * @param emailStr
     * @param contentStr
     */
    private void commitFeedBack(String subjectStr, String emailStr, String contentStr) {
        RequestParams params = new RequestParams();
        params.put("title", subjectStr);
        params.put("email", emailStr);
        params.put("contents", contentStr);
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.COMMITSUGGEST, params, new MyTextAsyncResponseHandler(act, "正在提交中...") {
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
                        ToastUtils.showShortToast("服务器异常，请稍后重试");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("数据异常，请稍后重试");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });
    }


    private boolean checkEmailFormat(String emailStr) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(emailStr);
        return matcher.matches();
    }
}
