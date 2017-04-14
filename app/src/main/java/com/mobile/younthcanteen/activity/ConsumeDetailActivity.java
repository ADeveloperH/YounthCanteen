package com.mobile.younthcanteen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
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

import java.util.ArrayList;
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
    @BindView(R.id.swipelayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private String lastId = "0";//分页用id
    private int lastRequestSize = 0;//上次请求返回的数据大小
    private final int pageCount = 15;//每页的数量。后台写死的
    private List<ConsumeDetailBean.ResultsEntity> results;
    private ConsumeDetailAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("余额明细");
        setTitleBackVisible(true);
        checkLogin(true);
        setContentView(R.layout.activity_consume_detail_layout);
        ButterKnife.bind(this);

        setListener();
        getDetailData();
    }

    private void setListener() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.myyellow));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                lastId = "0";
                lastRequestSize = 0;
                if (results != null) {
                    results.clear();
                }
                getDetailData();
            }
        });


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //空闲状态
                    int lastVisiblePosition = lv.getLastVisiblePosition();
                    if (lastVisiblePosition == lv.getCount() - 1) {
                        //最后一个条目,加载更多
                        if (lastRequestSize == pageCount) {
                            //还有可能有更多
                            getDetailData();
                        } else {
                            ToastUtils.showShortToast("没有更多了");
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    /**
     * 获取消费明细的数据
     */
    private void getDetailData() {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("id", lastId);
        Http.post(Http.GETCONSUMEDETAIL, params, new MyTextAsyncResponseHandler(act, "获取中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                refreshComplete();
                try {
                    ConsumeDetailBean bean = JsonUtil.fromJson(content, ConsumeDetailBean.class);
                    if (null != bean) {
                        if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                            ToastUtils.showShortToast(bean.getReturnMessage());
                            return;
                        }

                        if (results == null) {
                            results = new ArrayList<ConsumeDetailBean.ResultsEntity>();
                        }
                        List<ConsumeDetailBean.ResultsEntity> requestList = bean.getResults();
                        if (requestList != null && requestList.size() > 0) {
                            //最后一个proid作为id请求更多
                            lastRequestSize = requestList.size();
                            lastId = bean.getId();
                            results.addAll(requestList);
                            if (adapter == null) {
                                adapter = new ConsumeDetailAdapter(act, results);
                                lv.setAdapter(adapter);
                            } else {
                                adapter.setResults(results);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            if ("0".equals(lastId)) {
                                //第一次请求没有数据
                                ToastUtils.showShortToast("暂无消费记录");
                            }
                            lastRequestSize = 0;
                        }

                    } else {
                        ToastUtils.showShortToast("服务器数据异常，请稍后重试");
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
                refreshComplete();
            }
        });
    }

    /**
     * 刷新完成
     */
    private void refreshComplete() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            ToastUtils.showShortToast("刷新完成");
        }
    }

}
