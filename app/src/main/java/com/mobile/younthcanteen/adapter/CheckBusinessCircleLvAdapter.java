package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.OfficeAddressBean;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

/**
 * author：hj
 * time: 2017/4/6 0006 20:39
 */


public class CheckBusinessCircleLvAdapter extends BaseAdapter{
        private Context context;
        private List<OfficeAddressBean.ResultsEntity> officeDataList;
        public CheckBusinessCircleLvAdapter(Context context, List<OfficeAddressBean.ResultsEntity> officeDataList) {
        this.context = context;
        this.officeDataList = officeDataList;
    }

    @Override
    public int getCount() {
        return officeDataList == null ? 0 : officeDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return officeDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_checkbusiness_lv__layout);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText(officeDataList.get(position).getBusiness());
        return convertView;
    }

    class ViewHolder {
        private TextView tv;

        public ViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }
}
