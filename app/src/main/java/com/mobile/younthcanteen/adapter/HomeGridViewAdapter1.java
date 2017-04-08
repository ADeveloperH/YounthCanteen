package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.HomeDataBean;
import com.mobile.younthcanteen.ui.GridViewForScroll;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.FileUtil;
import com.mobile.younthcanteen.util.UIUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * author：hj
 * time: 2017/2/22 0022 23:16
 * 套餐、炒菜的Adapter
 */

public class HomeGridViewAdapter1 extends BaseAdapter {
    private static GridViewForScroll gridview;
    private final Picasso picasso;
    private BitmapUtil bitmapUtil;
    private Context context;
    private List<HomeDataBean.CenterEntity.ProsEntity> prosList;

    public HomeGridViewAdapter1(GridViewForScroll gridview, Context context, List<HomeDataBean.CenterEntity.ProsEntity> prosList) {
        this.gridview = gridview;
        this.context = context;
        this.prosList = prosList;
        bitmapUtil = new BitmapUtil(context, FileUtil.getCachePath(context, "/bitmapcache"),
                new ColorDrawable(context.getResources().getColor(R.color.gray_bg)));


        File file = new File(FileUtil.getCachePath(context,"AAAA_YOUNTHCANTEEN"));
        if (!file.exists()) {
            file.mkdirs();
        }

        long maxSize = Runtime.getRuntime().maxMemory() / 8;//设置图片缓存大小为运行时缓存的八分之一
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(new Cache(file, maxSize))
                .build();

        picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))//注意此处替换为 OkHttp3Downloader
                .build();
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
            convertView = UIUtils.inflate(R.layout.item_homegridview1);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HomeDataBean.CenterEntity.ProsEntity bean = prosList.get(position);
        bitmapUtil.display(viewHolder.iv, bean.getUrl());
//        picasso.load(bean.getUrl()).into(viewHolder.iv);
//        Glide.with(context).load(bean.getUrl()).into(viewHolder.iv);
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvPrice.setText("优惠价：" + bean.getPrice() + "元 / 份");
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

    public void setProsList(List<HomeDataBean.CenterEntity.ProsEntity> prosList) {
        this.prosList = prosList;
    }
}
