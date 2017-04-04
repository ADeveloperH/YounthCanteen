package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.OrderResultBean;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/4/4 0004 13:58
 * 订单详情页面显示具体的订单列表
 */


public class OrderDetailLvAdapter extends BaseAdapter {
    private Context context;
    private List<OrderResultBean.ResultsEntity.ProsEntity> pros;

    public OrderDetailLvAdapter(Context context,
                                List<OrderResultBean.ResultsEntity.ProsEntity> pros) {
        this.context = context;
        this.pros = pros;
    }

    @Override
    public int getCount() {
        return pros == null ? 0 : pros.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_order_detail_lv_layout);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OrderResultBean.ResultsEntity.ProsEntity bean = pros.get(position);
        String name = bean.getTitle();
        if (!TextUtils.isEmpty(bean.getCombo())) {
            name += ("(" + bean.getCombo() + ")");
        }
        viewHolder.tvName.setText(name);
        viewHolder.tvNum.setText("X" + bean.getCounts());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
