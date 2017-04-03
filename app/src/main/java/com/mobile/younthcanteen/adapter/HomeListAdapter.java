package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.GoodsDetailInfoActivity;
import com.mobile.younthcanteen.activity.PackageGoodsInfoActivity;
import com.mobile.younthcanteen.bean.HomeDataBean;
import com.mobile.younthcanteen.ui.GridViewForScroll;
import com.mobile.younthcanteen.util.ToastUtils;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/2/22 0022 22:59
 */

public class HomeListAdapter extends BaseAdapter {
    private Context context;
    private List<HomeDataBean.CenterEntity> centerList;

    public HomeListAdapter(Context mActivity, List<HomeDataBean.CenterEntity> center) {
        this.context = mActivity;
        this.centerList = center;
    }

    @Override
    public int getCount() {
        return centerList == null ? 0 : centerList.size();
    }

    @Override
    public Object getItem(int position) {
        return centerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_homelistview);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final HomeDataBean.CenterEntity bean = centerList.get(position);
        viewHolder.tvTypeName.setText(bean.getTypename());
        //1套餐 2炒菜 3面食 4饮料
        String typeId = bean.getTypeid();
        ListAdapter adapter = viewHolder.gvHome.getAdapter();
        if ("1".equals(typeId) || "2".equals(typeId)) {
            //一行只显示一个
            viewHolder.gvHome.setNumColumns(1);
            if (adapter != null && (adapter instanceof  HomeGridViewAdapter1)) {
                HomeGridViewAdapter1 adapter1 = (HomeGridViewAdapter1)adapter;
                adapter1.setProsList(bean.getPros());
                adapter1.notifyDataSetChanged();
            } else {
                adapter = new HomeGridViewAdapter1(viewHolder.gvHome, context, bean.getPros());
            }
            viewHolder.gvHome.setAdapter(adapter);
        } else {
            viewHolder.gvHome.setNumColumns(2);
            if (adapter != null && (adapter instanceof  HomeGridViewAdapter2)) {
                HomeGridViewAdapter2 adapter2 = (HomeGridViewAdapter2)adapter;
                adapter2.setProsList(bean.getPros());
                adapter2.notifyDataSetChanged();
            } else {
                adapter = new HomeGridViewAdapter2(viewHolder.gvHome, context, bean.getPros());
            }
            viewHolder.gvHome.setAdapter(adapter);
        }



        viewHolder.llTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("点击了:" + bean.getTypename());
            }
        });
        viewHolder.gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                if ("1".equals(bean.getTypeid())) {
                    //套餐类
                    intent.setClass(context, PackageGoodsInfoActivity.class);
                } else {
                    //非套餐类
                    intent.setClass(context, GoodsDetailInfoActivity.class);
                }
                intent.putExtra("proid", bean.getPros().get(position).getProid());
                intent.putExtra("imageUrl", bean.getPros().get(position).getUrl());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.gv_home)
        GridViewForScroll gvHome;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setCenterList(List<HomeDataBean.CenterEntity> centerList) {
        this.centerList = centerList;
    }
}
