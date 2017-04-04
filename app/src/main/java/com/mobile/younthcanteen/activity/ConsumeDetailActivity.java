package com.mobile.younthcanteen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.ConsumeDetailAdapter;
import com.mobile.younthcanteen.bean.ConsumeDetailBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/4/4 0004 12:32
 * 余额明细
 */


public class ConsumeDetailActivity extends BaseActivity {
    @BindView(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("余额明细");
        setTitleBackVisible(true);
        checkLogin(true);
        setContentView(R.layout.activity_consume_detail_layout);
        ButterKnife.bind(this);

        getDetailData();
    }

    /**
     * 获取消费明细的数据
     */
    private void getDetailData() {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.GETCONSUMEDETAIL, params, new MyTextAsyncResponseHandler(act, "获取中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                ConsumeDetailBean bean = JsonUtil.fromJson(content, ConsumeDetailBean.class);
                if (null != bean) {
                    if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                        ToastUtils.showShortToast(bean.getReturnMessage());
                        return;
                    }

                    List<ConsumeDetailBean.ResultsEntity> results = bean.getResults();
                    if (results == null || results.size() <= 0) {
                        ToastUtils.showShortToast("暂无消费记录");
                    } else {
                        showDetail(results);
                    }

                } else {
                    ToastUtils.showShortToast("服务器数据异常，请稍后重试");
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
     * 显示消费记录
     * @param results
     */
    private void showDetail(List<ConsumeDetailBean.ResultsEntity> results) {
        ConsumeDetailAdapter adapter = new ConsumeDetailAdapter(act,results);
        lv.setAdapter(adapter);
    }


}
