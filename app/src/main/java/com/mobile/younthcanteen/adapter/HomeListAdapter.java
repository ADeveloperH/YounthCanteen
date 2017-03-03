package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.GoodsDetailInfoActivity;
import com.mobile.younthcanteen.bean.HomeDataBean;
import com.mobile.younthcanteen.ui.GridViewForScroll;
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
//        Log.d("hj", "ListView:getView:" + position);
        if (convertView == null) {
//            Log.d("hj", "ListView:convertView == null:" + position);
            convertView = UIUtils.inflate(R.layout.item_homelistview);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
//            Log.d("hj", "ListView:convertView != null:" + position);
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final HomeDataBean.CenterEntity bean = centerList.get(position);
        viewHolder.tvTypeName.setText(bean.getTypename());
        HomeGridViewAdapter adapter = (HomeGridViewAdapter) viewHolder.gvHome.getAdapter();
        if (adapter == null) {
//            Log.d("hj", "ListView:adapter == null:" + position);
            adapter = new HomeGridViewAdapter(viewHolder.gvHome,context, bean.getPros());
        } else {
//            Log.d("hj", "ListView:adapter != null:" + position);
            adapter.setProsList(bean.getPros());
            adapter.notifyDataSetChanged();
        }
        viewHolder.gvHome.setAdapter(adapter);
        viewHolder.gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("1".equals(bean.getTypeid())) {
                    //套餐类

                } else {
                    //非套餐类
                    Intent intent = new Intent(context, GoodsDetailInfoActivity.class);
                    intent.putExtra("proid", bean.getPros().get(position).getProid());
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;
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
