package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.OrderDetailActivity;
import com.mobile.younthcanteen.activity.PayActivity;
import com.mobile.younthcanteen.bean.OrderResultBean;
import com.mobile.younthcanteen.bean.SimpleResultBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.ui.SwipeMenuLayout;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.FileUtil;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/3/19 0019 09:45
 */


public class NoPayOrderLvAdapter extends BaseAdapter {

    private DeleteOrderListener deleteOrderListener;
    private BitmapUtil bitmapUtil;
    private List<OrderResultBean.ResultsEntity> results;
    private Context context;

    public NoPayOrderLvAdapter(Context context, List<OrderResultBean.ResultsEntity> results,
                               DeleteOrderListener deleteOrderListener) {
        this.context = context;
        this.results = results;
        this.deleteOrderListener = deleteOrderListener;
        bitmapUtil = new BitmapUtil(context, FileUtil.getCachePath(context, "/bitmapcache"),
                new ColorDrawable(context.getResources().getColor(R.color.gray_bg)));
    }

    @Override
    public int getCount() {
        return results == null ? 0 : results.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_lv_nopay_layout);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        //隐藏删除按钮
        final SwipeMenuLayout swipeMenuLayout = ((SwipeMenuLayout) convertView);
        swipeMenuLayout.smoothClose();

        final OrderResultBean.ResultsEntity resultsEntity = results.get(position);
        OrderResultBean.ResultsEntity.ProsEntity bean;
        String title = "";
        int count = 0;
        for (int i = 0; i < resultsEntity.getPros().size(); i++) {
            bean = resultsEntity.getPros().get(i);
            title += (bean.getTitle() + ",");
            count += Integer.parseInt(bean.getCounts());
        }
        vh.tvTitle.setText(title.substring(0, title.lastIndexOf(",")));
        vh.tvCount.setText("数量：" + count);
        vh.tvPrice.setText("总价：￥" + resultsEntity.getAllMoney());
        vh.tvState.setText(resultsEntity.getStatus());
        bitmapUtil.display(vh.iv, resultsEntity.getPros().get(0).getImgs());
        vh.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra("orderno", resultsEntity.getOrderno());
                intent.putExtra("money", resultsEntity.getAllMoney());
                context.startActivity(intent);
            }
        });
        vh.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderInfo", resultsEntity);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        vh.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrder(resultsEntity.getOrderno());
            }
        });
        return convertView;
    }

    /**
     * 删除订单
     * @param orderno
     */
    private void deleteOrder(String orderno) {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("orderno", orderno);
        Http.post(Http.DELETEORDER, params, new MyTextAsyncResponseHandler(context, null) {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                    if (null != bean) {
                        ToastUtils.showShortToast(bean.getReturnMessage());
                        if (Http.SUCCESS.equals(bean.getReturnCode())) {
                            //删除成功。重新获取数据
                            if (deleteOrderListener != null) {
                                deleteOrderListener.deleteOrderSuc();
                            }
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

    public void setResults(List<OrderResultBean.ResultsEntity> results) {
        this.results = results;
    }

    public List<OrderResultBean.ResultsEntity> getResults() {
        return results;
    }

    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_pay)
        TextView tvPay;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.ll_content)
        LinearLayout llContent;
        @BindView(R.id.swiplayout)
        SwipeMenuLayout swipLayout;
        @BindView(R.id.btn_delete)
        TextView btnDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public interface DeleteOrderListener{
        //账单删除成功
        void deleteOrderSuc();
    }
}
