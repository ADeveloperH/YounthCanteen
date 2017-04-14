package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.ConsumeDetailBean;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/4/4 0004 12:44
 */


public class ConsumeDetailAdapter extends BaseAdapter {
    private Context context;
    private List<ConsumeDetailBean.ResultsEntity> results;

    public ConsumeDetailAdapter(Context context, List<ConsumeDetailBean.ResultsEntity> results) {
        this.context = context;
        this.results = results;

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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_consume_detail_layout);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ConsumeDetailBean.ResultsEntity bean = results.get(position);
        viewHolder.tvType.setText(bean.getType());
        viewHolder.tvMoney.setText(bean.getMoney() + "元");
        double money = Double.parseDouble(bean.getMoney());
        if (money < 0) {
            viewHolder.tvMoney.setTextColor(Color.parseColor("#ff224c"));
        } else {
            viewHolder.tvMoney.setTextColor(Color.parseColor("#333333"));
        }
        viewHolder.tvBalance.setText("余额：" + bean.getBalance() + "元");
        viewHolder.tvTime.setText(bean.getTime());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_balance)
        TextView tvBalance;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setResults(List<ConsumeDetailBean.ResultsEntity> results) {
        this.results = results;
    }
}
