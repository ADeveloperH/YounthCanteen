package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.CheckBusinessCircleLvAdapter;
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
 *
 */


public class CheckBusinessCircleActivity extends BaseActivity {
    @BindView(R.id.lv)
    ListView listView;
    private final int CHECKOFFICE_RESULT_CODE = 11;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择商圈");
        setTitleBackVisible(true);
        setContentView(R.layout.activity_checkbusinesscircle_layout);
        ButterKnife.bind(this);

        getAddList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBusinessCircleLvAdapter adapter = (CheckBusinessCircleLvAdapter) listView.getAdapter();
                OfficeAddressBean.ResultsEntity bean = (OfficeAddressBean.ResultsEntity) adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("businessname", bean.getBusiness());
                setResult(CHECKOFFICE_RESULT_CODE,intent);
                finish();
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
                        CheckBusinessCircleLvAdapter adapter = new CheckBusinessCircleLvAdapter(act,officeDataList);
                        listView.setAdapter(adapter);
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
