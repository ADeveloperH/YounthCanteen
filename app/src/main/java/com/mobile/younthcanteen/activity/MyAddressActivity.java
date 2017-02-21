package com.mobile.younthcanteen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.util.ToastUtils;

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

    }

    @OnClick(R.id.ll_add_address)
    public void onClick() {
        ToastUtils.showLongToast("ll_add_address");
    }
}
