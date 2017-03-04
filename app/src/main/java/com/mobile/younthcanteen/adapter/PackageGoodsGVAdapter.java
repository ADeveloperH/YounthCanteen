package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.PackageGoodsInfoBean;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/2/22 0022 23:16
 */

public class PackageGoodsGVAdapter extends BaseAdapter {

    private List<PackageGoodsInfoBean.ResultsEntity.CombosEntity.MaterialEntity> dataList;
    private Context context;

    public PackageGoodsGVAdapter(Context context, List<PackageGoodsInfoBean.ResultsEntity.CombosEntity.MaterialEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_packagegoodsinfo_layout);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PackageGoodsInfoBean.ResultsEntity.CombosEntity.MaterialEntity bean =
                dataList.get(position);
        viewHolder.tvName.setText(bean.getName());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 返回固定位置
     * @param position
     * @return
     */
    public String getName(int position) {
        return dataList.get(position).getName();
    }
}
