package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.CheckOfficeELvAdapter;
import com.mobile.younthcanteen.bean.OfficeAddressBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/4/4 0004 22:21
 */


public class CheckOfficeActivity extends BaseActivity {
    @BindView(R.id.expand_listView)
    ExpandableListView expandListView;
    private final int CHECKOFFICE_RESULT_CODE = 11;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择地址");
        setTitleBackVisible(true);
        setContentView(R.layout.activity_checkoffice_layout);
        ButterKnife.bind(this);

        getAddList();

        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                CheckOfficeELvAdapter adapter = (CheckOfficeELvAdapter) expandListView.getExpandableListAdapter();
                OfficeAddressBean.ResultsEntity.OfficeEntity bean = (OfficeAddressBean.ResultsEntity.OfficeEntity) adapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent();
                intent.putExtra("officeid", bean.getOfficeid());
                intent.putExtra("officename", bean.getOfficename());
                setResult(CHECKOFFICE_RESULT_CODE,intent);
                finish();
                return false;
            }
        });
    }


    /**
     * 获取写字楼列表数据
     */
    private void getAddList() {
        RequestParams params = new RequestParams();
        params.put("zoneid", "0");
        Http.post(Http.GETOFFICELIST, params, new MyTextAsyncResponseHandler(act, "获取中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                OfficeAddressBean bean = JsonUtil.fromJson(content, OfficeAddressBean.class);
                if (null != bean) {
                    if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                        ToastUtils.showShortToast(bean.getReturnMessage());
                        return;
                    }
                    List<OfficeAddressBean.ResultsEntity> officeDataList = bean.getResults();
                    if (officeDataList == null || officeDataList.size() == 0) {
                        ToastUtils.showShortToast("当前无可配送地址");
                    } else {
                        CheckOfficeELvAdapter adapter = new CheckOfficeELvAdapter(act,officeDataList);
                        expandListView.setAdapter(adapter);
                        for (int i = 0; i < adapter.getGroupCount(); i++) {
                            // 展开所有分组
                            expandListView.expandGroup(i);
                        }
                    }
                } else {
                    ToastUtils.showShortToast("服务器异常，请稍后重试");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });

    }
}
