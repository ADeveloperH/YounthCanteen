package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.MoreGoodsResultBean;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.FileUtil;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/2/22 0022 23:16
 * 套餐、炒菜的Adapter
 */

public class MoreGoodsLvAdapter extends BaseAdapter {
    private BitmapUtil bitmapUtil;
    private Context context;
    private List<MoreGoodsResultBean.CenterEntity.ProsEntity> prosList;

    public MoreGoodsLvAdapter(Context context, List<MoreGoodsResultBean.CenterEntity.ProsEntity> prosList) {
        this.context = context;
        this.prosList = prosList;
        bitmapUtil = new BitmapUtil(context, FileUtil.getCachePath(context, "/bitmapcache"),
                new ColorDrawable(context.getResources().getColor(R.color.gray_bg)));
    }

    @Override
    public int getCount() {
        return prosList == null ? 0 : prosList.size();
    }

    @Override
    public Object getItem(int position) {
        return prosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_moregoods_gridview);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MoreGoodsResultBean.CenterEntity.ProsEntity bean = prosList.get(position);
        bitmapUtil.display(viewHolder.iv, bean.getUrl());
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvPrice.setText("优惠价：" + bean.getPrice() + "元/份");
        viewHolder.tvDesc.setText(bean.getIntro());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setProsList(List<MoreGoodsResultBean.CenterEntity.ProsEntity> prosList) {
        this.prosList = prosList;
    }
}
