package com.mobile.younthcanteen.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.RegisterAgreementBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/4/13 0013 21:21
 */


public class RegisterAgreementActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("注册协议");
        setTitleBackVisible(true);
        setContentView(R.layout.activity_registeragreement_layout);
        ButterKnife.bind(this);

        getData();
    }

    private void getData() {
        Http.post(Http.GETREGISTERAGREEMENT, new RequestParams(), new MyTextAsyncResponseHandler(act, "请求中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    RegisterAgreementBean bean = JsonUtil.fromJson(content,
                            RegisterAgreementBean.class);
                    if (null != bean) {
                        ToastUtils.showShortToast(bean.getReturnMessage());
                        if (Http.SUCCESS.equals(bean.getReturnCode())) {
                            if (TextUtils.isEmpty(bean.getResults().getContent())) {
                                //为空显示H5
                                if (TextUtils.isEmpty(bean.getResults().getUrl())) {
                                    ToastUtils.showShortToast("服务器异常，请稍后重试");
                                    return;
                                }
                                webview.setVisibility(View.VISIBLE);
                                scrollView.setVisibility(View.GONE);
                                showWebView(bean.getResults().getUrl());
                            } else {
                                scrollView.setVisibility(View.VISIBLE);
                                webview.setVisibility(View.GONE);
                                tvName.setText(bean.getResults().getName());
                                tvContent.setText(bean.getResults().getContent());
                            }
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

    /**
     * 显示H5
     * @param url
     */
    private void showWebView(String url) {
        webview.setBackgroundColor(Color.argb(0, 0, 0, 0));
        WebSettings webviewSettings = webview.getSettings();
        if (Build.VERSION.SDK_INT >= 21) {
            //设置允许混合加载(http/https)
            webviewSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webviewSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webviewSettings.setDomStorageEnabled(true);
        webviewSettings.setDatabaseEnabled(true);
        // 设置响应js
        webviewSettings.setJavaScriptEnabled(true);
        //自适应屏幕
        webviewSettings.setUseWideViewPort(true);
        webviewSettings.setLoadWithOverviewMode(true);
        //支持缩放
        webviewSettings.setSupportZoom(true);
        webviewSettings.setBuiltInZoomControls(true);

        webview.setFocusable(true);
        webview.requestFocus();
        webview.removeJavascriptInterface("searchBoxJavaBredge_");
        webview.loadUrl(url);
    }
}
