package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.HomeDataBean;
import com.mobile.younthcanteen.ui.GridViewForScroll;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.FileUtil;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/2/22 0022 23:16
 */

public class HomeGridViewAdapter extends BaseAdapter {
    private static GridViewForScroll gridview;
    private BitmapUtil bitmapUtil;
    private Context context;
    private List<HomeDataBean.CenterEntity.ProsEntity> prosList;

    public HomeGridViewAdapter(GridViewForScroll gridview, Context context, List<HomeDataBean.CenterEntity.ProsEntity> prosList) {
        this.gridview = gridview;
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
            convertView = UIUtils.inflate(R.layout.item_homegridview);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
//            viewHolder.update();
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HomeDataBean.CenterEntity.ProsEntity bean = prosList.get(position);
        bitmapUtil.display(viewHolder.iv, bean.getUrl());
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvPrice.setText("价格：" + bean.getPrice() + "元");
//        viewHolder.tvPrice.setTag(position);
//        viewHolder.tvName.setTag(convertView);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void update() {
            // 精确计算GridView的item高度
            tvPrice.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        public void onGlobalLayout() {
                            int position = (Integer) tvPrice.getTag();
                            // 这里是保证同一行的item高度是相同的！！也就是同一行是齐整的 height相等
                            if (position > 0 && position % 2 == 1) {
                                View v = (View) tvName.getTag();
                                int height = v.getHeight();
                                View view = gridview.getChildAt(position - 1);
                                int lastheight = view.getHeight();

                                // 得到同一行的最后一个item和前一个item想比较，把谁的height大，就把两者中
                                // height小的item的高度设定为height较大的item的高度一致，也就是保证同一行高度相等即可
                                if (height > lastheight) {
                                    view.setLayoutParams(new GridView.LayoutParams(
                                            GridView.LayoutParams.FILL_PARENT,
                                            height));
                                } else if (height < lastheight) {
                                    v.setLayoutParams(new GridView.LayoutParams(
                                            GridView.LayoutParams.FILL_PARENT,
                                            lastheight));
                                }
                            }
                        }
                    });
        }
    }

    public void setProsList(List<HomeDataBean.CenterEntity.ProsEntity> prosList) {
        this.prosList = prosList;
    }
}
