package com.mobile.younthcanteen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class MoreGoodsFragment extends Fragment{
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
    private Context context;
    private ListView listView;
    private String typeId;
    private MoreGoodsResultBean bean;

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
        getOrderData();
        isRefreshUI = false;
    }

    /**
     * 获取订单数据
     */
    private void getOrderData() {

        RequestParams params = new RequestParams();
        params.put("type", typeId);
        Http.post(Http.GETMOREGOODS, params, new MyTextAsyncResponseHandler(context, null) {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    bean = JsonUtil.fromJson(content, MoreGoodsResultBean.class);
                    if (null != bean) {
                        if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                            ToastUtils.showShortToast(bean.getReturnMessage());
                            return;
                        }
                        MoreGoodsLvAdapter adapter = new MoreGoodsLvAdapter(getActivity(),
                                bean.getCenter().get(0).getPros());
                        listView.setAdapter(adapter);
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



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.gv);

        typeId = getArguments().getString("typeid","1");

    }

    private void setListener() {

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
                intent.putExtra("proid", bean.getCenter().get(0).getPros().get(position).getProid());
                intent.putExtra("imageUrl", bean.getCenter().get(0).getPros().get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }
}
