package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/2/21 0021 15:17
 */

public class MyAddressActivity extends BaseActivity {
    @BindView(R.id.lv_address)
    ListView lvAddress;
    @BindView(R.id.ll_add_address)
    LinearLayout llAddAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的收货地址");
        setTitleBackVisible(true);
        checkLogin(true);
        setContentView(R.layout.activity_myaddress_layout);
        ButterKnife.bind(this);

        getAddList();
    }

    /**
     * 获取地址列表
     */
    private void getAddList() {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.GETADDRESSLIST,params,new MyTextAsyncResponseHandler(act,"正在加载中..."){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
            }
        });
    }


    @OnClick(R.id.ll_add_address)
    public void onClick() {
        startActivity(new Intent(act,AddAddressActivity.class));
    }
}
