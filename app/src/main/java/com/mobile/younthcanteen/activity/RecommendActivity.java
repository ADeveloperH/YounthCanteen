package com.mobile.younthcanteen.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.RecommendBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/4/12 0012 00:16
 * 推荐有奖
 */


public class RecommendActivity extends BaseActivity {
    @BindView(R.id.tv_myinvitedcode)
    TextView tvMyinvitedcode;
    @BindView(R.id.btn_copy)
    Button btnCopy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin(true);
        setTitle("推荐有奖");
        setTitleBackVisible(true);
        setContentView(R.layout.activity_recommend_layout);
        ButterKnife.bind(this);

        getMyInvitedCode();
    }

    /**
     * 获取我的邀请码
     */
    private void getMyInvitedCode() {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.GETJCODE, params, new MyTextAsyncResponseHandler(act, "正在获取中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    RecommendBean bean = JsonUtil.fromJson(content, RecommendBean.class);
                    if (null != bean) {
                        if (Http.SUCCESS.equals(bean.getReturnCode())) {
                            tvMyinvitedcode.setText(bean.getTjcode());
                        } else {
                            ToastUtils.showShortToast(bean.getReturnMessage());
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

    @OnClick(R.id.btn_copy)
    public void onClick() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tvMyinvitedcode.getText().toString().trim());
        ToastUtils.showShortToast("复制成功");
    }
}
