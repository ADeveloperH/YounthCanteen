package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.OrderResultBean;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.FileUtil;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/3/19 0019 09:45
 */


public class PaidOrderLvAdapter extends BaseAdapter {

    private BitmapUtil bitmapUtil;
    private List<OrderResultBean.ResultsEntity> results;
    private Context context;

    public PaidOrderLvAdapter(Context context, List<OrderResultBean.ResultsEntity> results) {
        this.context = context;
        this.results = results;
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
            convertView = UIUtils.inflate(R.layout.item_lv_paid_layout);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
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
        return convertView;
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
        @BindView(R.id.tv_state)
        TextView tvState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
