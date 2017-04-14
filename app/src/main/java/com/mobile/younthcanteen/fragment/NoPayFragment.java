package com.mobile.younthcanteen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.MainActivity;
import com.mobile.younthcanteen.adapter.NoPayOrderLvAdapter;
import com.mobile.younthcanteen.bean.OrderResultBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 * 未付款
 */

public class NoPayFragment extends Fragment implements View.OnClickListener {
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
    private Button btnOrder;
    private Context context;
    private LinearLayout llNoOrder;
    private ListView lvNoPayOrder;
    private NoPayOrderLvAdapter noPayOrderLvAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String lastId = "0";//分页用id
    private int lastRequestSize = 0;//上次请求返回的数据大小
    private final int pageCount = 15;//每页的数量。后台写死的
    private List<OrderResultBean.ResultsEntity> results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_nopay_layout, null);
        }
        //缓存的rootView需要判断是否已经被加过parent，
        //如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isNeedReLoad) {
            initView(getView());
            setListener();
            isNeedReLoad = false;
        }
    }

    private boolean isRefreshUI = false;

    @Override
    public void onResume() {
        super.onResume();
        if (!isRefreshUI) {
            refreshUI();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            if (!isNeedReLoad && !isRefreshUI) {
                refreshUI();
            }
        } else {
            //相当于Fragment的onPause
        }

    }

    private void refreshUI() {
        isRefreshUI = true;
        //刷新
        lastId = "0";
        lastRequestSize = 0;
        if (results != null) {
            results.clear();
        }
        getOrderData();
        isRefreshUI = false;
    }

    /**
     * 获取订单数据
     */
    private void getOrderData() {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("status", "1");
        params.put("id", lastId);
        Http.post(Http.GETORDERLIST, params, new MyTextAsyncResponseHandler(context, null) {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                refreshComplete();
                try {
                    OrderResultBean bean = JsonUtil.fromJson(content, OrderResultBean.class);
                    if (null != bean) {
                        if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                            ToastUtils.showShortToast(bean.getReturnMessage());
                            return;
                        }

                        if (results == null) {
                            results = new ArrayList<OrderResultBean.ResultsEntity>();
                        }
                        List<OrderResultBean.ResultsEntity>  requestList = bean.getResults();
                        if (requestList != null && requestList.size() > 0) {
                            //最后一个proid作为id请求更多
                            lastRequestSize = requestList.size();
                            lastId = bean.getId();
                            results.addAll(requestList);
                            llNoOrder.setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            if (noPayOrderLvAdapter == null) {
                                noPayOrderLvAdapter = new NoPayOrderLvAdapter(context, results, new NoPayOrderLvAdapter.DeleteOrderListener() {
                                    @Override
                                    public void deleteOrderSuc() {
                                        lastId = "0";
                                        lastRequestSize = 0;
                                        if (results != null) {
                                            results.clear();
                                        }
                                        getOrderData();
                                    }
                                });
                                lvNoPayOrder.setAdapter(noPayOrderLvAdapter);
                            } else {
                                noPayOrderLvAdapter.setResults(results);
                                noPayOrderLvAdapter.notifyDataSetChanged();
                            }
                        } else {
                            if ("0".equals(lastId)) {
                                //第一次请求没有数据
                                llNoOrder.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setVisibility(View.GONE);
                            }
                            lastRequestSize = 0;
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
                refreshComplete();
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initView(View view) {
        btnOrder = (Button) view.findViewById(R.id.btn_order);
        llNoOrder = (LinearLayout) view.findViewById(R.id.ll_no_order);
        lvNoPayOrder = (ListView) view.findViewById(R.id.lv_paid_order);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipelayout);

    }

    private void setListener() {
        btnOrder.setOnClickListener(this);
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
                getOrderData();
            }
        });


        lvNoPayOrder.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //空闲状态
                    int lastVisiblePosition = lvNoPayOrder.getLastVisiblePosition();
                    if (lastVisiblePosition == lvNoPayOrder.getCount() - 1) {
                        //最后一个条目,加载更多
                        if (lastRequestSize == pageCount) {
                            //还有可能有更多
                            getOrderData();
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

//        lvNoPayOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (noPayOrderLvAdapter != null ) {
//                    List<OrderResultBean.ResultsEntity> listData = noPayOrderLvAdapter.getResults();
//                    if (listData != null && listData.size() > 0) {
//                        OrderResultBean.ResultsEntity bean = listData.get(position);
//                        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("orderInfo",bean);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_order://立即下单
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("tabIndex", 0);
                startActivity(intent);

                break;
        }
    }
}
