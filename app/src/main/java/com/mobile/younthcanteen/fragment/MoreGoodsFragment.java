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
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.GoodsDetailInfoActivity;
import com.mobile.younthcanteen.activity.PackageGoodsInfoActivity;
import com.mobile.younthcanteen.adapter.MoreGoodsLvAdapter;
import com.mobile.younthcanteen.bean.MoreGoodsResultBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mobile.younthcanteen.R.id.lv;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class MoreGoodsFragment extends Fragment{
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
    private Context context;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String typeId;
    private String lastId = "0";//分页用id
    private int lastRequestSize = 0;//上次请求返回的数据大小
    private final int pageCount = 15;//每页的数量。后台写死的
    List<MoreGoodsResultBean.CenterEntity.ProsEntity> prosList;
    private MoreGoodsLvAdapter moreGoodsLvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_moregoods_layout, null);
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
            refreshUI();
            isNeedReLoad = false;
        }
    }

    private boolean isRefreshUI = false;

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!isRefreshUI) {
//            refreshUI();
//        }
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            //相当于Fragment的onResume
//            if (!isNeedReLoad && !isRefreshUI) {
//                refreshUI();
//            }
//        } else {
//            //相当于Fragment的onPause
//        }
//
//    }

    private void refreshUI() {
        isRefreshUI = true;
        getOrderData();
        isRefreshUI = false;
    }

    /**
     * 获取订单数据
     */
    private void getOrderData() {
        RequestParams params = new RequestParams();
        params.put("type", typeId);
        params.put("id", lastId);
        Http.post(Http.GETMOREGOODS, params, new MyTextAsyncResponseHandler(context, null) {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                refreshComplete();
                try {
                    MoreGoodsResultBean bean = JsonUtil.fromJson(content, MoreGoodsResultBean.class);
                    if (null != bean) {
                        if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                            ToastUtils.showShortToast(bean.getReturnMessage());
                            return;
                        }
                        if (prosList == null) {
                            prosList = new ArrayList<MoreGoodsResultBean.CenterEntity.ProsEntity>();
                        }
                        List<MoreGoodsResultBean.CenterEntity.ProsEntity> requestList
                                = bean.getCenter().get(0).getPros();
                        if (requestList != null && requestList.size() > 0) {
                            //最后一个proid作为id请求更多
                            lastRequestSize = requestList.size();
                            lastId = requestList.get(lastRequestSize - 1).getProid();
                            prosList.addAll(requestList);
                            if (moreGoodsLvAdapter == null) {
                                moreGoodsLvAdapter = new MoreGoodsLvAdapter(getActivity(), prosList);
                                listView.setAdapter(moreGoodsLvAdapter);
                            } else {
                                moreGoodsLvAdapter.setProsList(prosList);
                                moreGoodsLvAdapter.notifyDataSetChanged();
                            }
                        } else {
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(lv);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.myyellow));

        typeId = getArguments().getString("typeid","1");

    }

    private void setListener() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                lastId = "0";
                lastRequestSize = 0;
                if (prosList != null) {
                    prosList.clear();
                }
                getOrderData();
            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //空闲状态
                    int lastVisiblePosition = listView.getLastVisiblePosition();
                    if (lastVisiblePosition == listView.getCount() - 1) {
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



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                if ("1".equals(typeId)) {
                    //套餐类
                    intent.setClass(context, PackageGoodsInfoActivity.class);
                } else {
                    //非套餐类
                    intent.setClass(context, GoodsDetailInfoActivity.class);
                }
                intent.putExtra("proid", prosList.get(position).getProid());
                intent.putExtra("imageUrl", prosList.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }
}
