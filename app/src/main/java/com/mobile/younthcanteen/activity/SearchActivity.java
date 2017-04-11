package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.SearchGoodsLvAdapter;
import com.mobile.younthcanteen.bean.MoreGoodsResultBean;
import com.mobile.younthcanteen.bean.SearchBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mobile.younthcanteen.R.id.et_search;

/**
 * author：hj
 * time: 2017/4/10 0010 23:28
 */


public class SearchActivity extends Activity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(et_search)
    EditText etSearch;
    @BindView(R.id.iv_del)
    ImageView ivDel;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.lv)
    ListView listView;
    private String lastId = "0";//分页用id
    private int lastRequestSize = 0;//上次请求返回的数据大小
    private final int pageCount = 15;//每页的数量。后台写死的
    private Context context;
    List<SearchBean> searchBeanList = new ArrayList<SearchBean>();
    private SearchGoodsLvAdapter searchGoodsLvAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        ButterKnife.bind(this);

        context = this;
        setListener();

    }

    private void setListener() {
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
                if (searchGoodsLvAdapter != null) {
                    Intent intent = new Intent();
                    SearchBean bean = (SearchBean) searchGoodsLvAdapter.getItem(position);
                    if ("1".equals(bean.getTypeid())) {
                        //套餐类
                        intent.setClass(context, PackageGoodsInfoActivity.class);
                    } else {
                        //非套餐类
                        intent.setClass(context, GoodsDetailInfoActivity.class);
                    }
                    intent.putExtra("proid", bean.getProid());
                    intent.putExtra("imageUrl", bean.getUrl());
                    context.startActivity(intent);
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    getOrderData();
                    return true;
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            public String befroeSearchKey = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                befroeSearchKey = etSearch.getText().toString().trim();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchKey = etSearch.getText().toString().trim();
                //关键字改变了。
                if (!befroeSearchKey.equals(searchKey)) {
                    lastId = "0";
                }
                if (TextUtils.isEmpty(searchKey)) {
                    ivDel.setVisibility(View.GONE);
                } else {
                    ivDel.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_del, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.iv_del://删除
                etSearch.setText("");
                break;
            case R.id.tv_search://搜索
                getOrderData();
                break;
        }
    }

    /**
     * 获取订单数据
     */
    private void getOrderData() {
        final RequestParams params = new RequestParams();
        String keyStr = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keyStr)) {
            return;
        }
        params.put("id", lastId);
        params.put("key", keyStr);
        params.put("type", "0");
        Http.post(Http.GETSEARCHGOODS, params, new MyTextAsyncResponseHandler(context, null) {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    MoreGoodsResultBean bean = JsonUtil.fromJson(content, MoreGoodsResultBean.class);
                    if (null != bean) {
                        if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                            ToastUtils.showShortToast(bean.getReturnMessage());
                            return;
                        }

                        if ("0".equals(lastId)) {
                            //重新搜索的
                            if (searchBeanList.size() > 0 ) {
                                //代表上次搜索的有
                                searchBeanList.clear();
                                searchGoodsLvAdapter.notifyDataSetChanged();
                            }
                        }

                        if (bean != null && bean.getCenter().size() > 0) {
                            List<MoreGoodsResultBean.CenterEntity> centerEntityList = bean.getCenter();
                            List<SearchBean> requestList = new ArrayList<SearchBean>();
                            for (int i = 0; i < centerEntityList.size(); i++) {
                                MoreGoodsResultBean.CenterEntity centerEntity = centerEntityList.get(i);
                                if (centerEntity != null && centerEntity.getPros().size() > 0) {
                                    List<MoreGoodsResultBean.CenterEntity.ProsEntity> list =
                                            centerEntity.getPros();
                                    for (int j = 0; j < list.size(); j++) {
                                        MoreGoodsResultBean.CenterEntity.ProsEntity prosEntity = list.get(j);

                                        SearchBean searchBean = new SearchBean();
                                        searchBean.setIntro(prosEntity.getIntro());
                                        searchBean.setName(prosEntity.getName());
                                        searchBean.setPrice(prosEntity.getPrice());
                                        searchBean.setProid(prosEntity.getProid());
                                        searchBean.setUrl(prosEntity.getUrl());
                                        searchBean.setTypeid(centerEntity.getTypeid());
                                        searchBean.setTypename(centerEntity.getTypename());
                                        requestList.add(searchBean);
                                    }
                                }
                            }
                            if (requestList != null && requestList.size() > 0) {
                                //最后一个proid作为id请求更 多
                                lastRequestSize = requestList.size();
                                lastId = requestList.get(lastRequestSize - 1).getProid();
                                searchBeanList.addAll(requestList);
                                if (searchGoodsLvAdapter == null) {
                                    searchGoodsLvAdapter = new SearchGoodsLvAdapter(context, searchBeanList);
                                    listView.setAdapter(searchGoodsLvAdapter);
                                } else {
                                    searchGoodsLvAdapter.setSearchBeanList(searchBeanList);
                                    searchGoodsLvAdapter.notifyDataSetChanged();
                                }
                            } else {
                                lastRequestSize = 0;
                                if ("0".equals(lastId)) {
                                    //重新搜索的
                                    ToastUtils.showShortToast("暂未查询到相关信息");
                                }
                            }
                        } else {
                            if ("0".equals(lastId)) {
                                //重新搜索的
                                ToastUtils.showShortToast("暂未查询到相关信息");
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
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });
    }
}
