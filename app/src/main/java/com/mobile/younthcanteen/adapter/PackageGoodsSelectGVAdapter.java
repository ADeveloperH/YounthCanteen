package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/2/22 0022 23:16
 */

public class PackageGoodsSelectGVAdapter extends BaseAdapter {

    private List<String> dataList;
    private List<String> nameList;
    private Context context;
    private int clickPosition = -1;//用户点击的下标作为当前选中的
    private final String VEGETABLE = "素菜";
    private final String MEAT = "荤菜";

    public PackageGoodsSelectGVAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
        nameList = new ArrayList<String>();
        int count = (dataList == null ? 0 : dataList.size());
        for (int i = 0; i < count; i++) {
            //初始值都为
            if ("0".equals(dataList.get(i))) {
                //素菜
                nameList.add(VEGETABLE);
            } else {
                nameList.add(MEAT);
            }
        }
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
            convertView = UIUtils.inflate(R.layout.item_packagegoodsselect_layout);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        String name = nameList.get(position);
        if (VEGETABLE.equals(name) || MEAT.equals(name)) {
            //初始值。用户尚未添加菜
            if (position == clickPosition) {
                //用户当前点击的位置
                viewHolder.llRoot.setBackgroundResource(R.drawable.shape_yellow_border);
            } else {
                viewHolder.llRoot.setBackgroundResource(R.drawable.shape_gray_border);
            }
        } else {
            //用户已添加过菜
            viewHolder.llRoot.setBackgroundResource(R.drawable.login_btn_enable);
        }
        viewHolder.tvAdd.setTag(dataList.get(position));
        viewHolder.tvAdd.setText(nameList.get(position));
        return convertView;
    }

    /**
     * 清除已选择的状态
     */
    public void clearState() {
        clickPosition = -1;
        nameList.clear();
        int count = (dataList == null ? 0 : dataList.size());
        for (int i = 0; i < count; i++) {
            //初始值都为
            if ("0".equals(dataList.get(i))) {
                //素菜
                nameList.add(VEGETABLE);
            } else {
                nameList.add(MEAT);
            }
        }
    }

    /**
     * 添加菜名
     *
     * @param name     菜的名字
     * @param position 设置菜名字的位置
     */
    public void select(String name, int position) {
        if (!TextUtils.isEmpty(nameList.get(position))) {
            nameList.set(position, name);
        }
    }

    /**
     * 获取指定位置的标示 0素菜 1荤菜
     *
     * @param position
     * @return
     */
    public String getFlagByPosition(int position) {
        return dataList.get(position);
    }

    /**
     * 设置选择状态
     *
     * @param position
     */
    public void setClickPosition(int position) {
        clickPosition = position;
    }

    /**
     * 当前是否已经选择完成
     */
    public boolean isSelectFinish() {
        if (nameList == null || nameList.size() == 0) {
            return false;
        }
        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            if (VEGETABLE.equals(name) || MEAT.equals(name)) {
                return false;
            }

        }
        return true;
    }

    static class ViewHolder {
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.ll_root)
        LinearLayout llRoot;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
