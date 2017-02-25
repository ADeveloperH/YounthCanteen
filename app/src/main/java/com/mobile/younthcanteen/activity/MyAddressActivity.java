package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.MyAddressListAdapter;
import com.mobile.younthcanteen.bean.AddressListBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.listener.DeleteAddressListener;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.List;

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
    @BindView(R.id.tv_subtitle)
    TextView tvSubTitle;
    private List<AddressListBean.ResultsEntity> addressDataList;
    private boolean isShowModify = false;
    private MyAddressListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的收货地址");
        setSubTitle("管理");
        setTitleBackVisible(true);
        checkLogin(true);
        setContentView(R.layout.activity_myaddress_layout);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //恢复初始状态。请求数据
        showInitState();
        getAddList();
    }

    /**
     * 副标题显示初始状态
     */
    private void showInitState() {
        isShowModify = false;
        setSubTitle("管理");
        llAddAddress.setVisibility(View.VISIBLE);
    }

    /**
     * 获取地址列表
     */
    private void getAddList() {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.GETADDRESSLIST, params, new MyTextAsyncResponseHandler(act, "正在加载中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                AddressListBean bean = JsonUtil.fromJson(content, AddressListBean.class);
                if (bean == null) {
                    ToastUtils.showShortToast("服务器数据异常，请稍后重试");
                } else {
                    if (Http.SUCCESS.equals(bean.getReturnCode())) {
                        addressDataList = bean.getResults();
                        if (addressDataList != null && addressDataList.size() > 0) {
                            adapter = new MyAddressListAdapter(act,addressDataList, isShowModify, new DeleteAddressListener() {
                                @Override
                                public void deleteSucess() {
                                    ToastUtils.showShortToast("删除成功");
                                    getAddList();
                                }
                            });
                            lvAddress.setAdapter(adapter);
                        } else {
                            if (adapter != null) {
                                adapter.setAddressDataList(null);
                                adapter.notifyDataSetChanged();
                            }
                            ToastUtils.showShortToast("当前暂无常用收货地址");
                            showInitState();
                        }
                    } else {
                        ToastUtils.showShortToast(bean.getReturnMessage());
                    }
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });
    }

    @OnClick({R.id.tv_subtitle, R.id.ll_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_subtitle://管理
                if ("完成".equals(getSubTitle())) {
                    showInitState();
                } else {
                    setSubTitle("完成");
                    isShowModify = true;
                    llAddAddress.setVisibility(View.GONE);
                }

                if (addressDataList != null && addressDataList.size() > 0) {
                    if (adapter == null) {
                        adapter = new MyAddressListAdapter(act, addressDataList, isShowModify, new DeleteAddressListener() {
                            @Override
                            public void deleteSucess() {
                                getAddList();
                            }
                        });
                        lvAddress.setAdapter(adapter);
                    } else {
                        adapter.setShowModify(isShowModify);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.ll_add_address:
                startActivity(new Intent(act, AddAddressActivity.class));
                break;
        }
    }
}
