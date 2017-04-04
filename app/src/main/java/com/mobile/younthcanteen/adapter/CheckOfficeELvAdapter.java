package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.OfficeAddressBean;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

/**
 * author：hj
 * time: 2017/4/4 0004 22:32
 */


public class CheckOfficeELvAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<OfficeAddressBean.ResultsEntity> officeDataList;

    public CheckOfficeELvAdapter(Context context, List<OfficeAddressBean.ResultsEntity> officeDataList) {
        this.context = context;
        this.officeDataList = officeDataList;
    }

    @Override
    public int getGroupCount() {
        return officeDataList == null ? 0 : officeDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return officeDataList.get(groupPosition).getOffice().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return officeDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return officeDataList.get(groupPosition).getOffice().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_checkoffice_eplv_group_layout);
            viewHolder = new GroupViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }

        if (isExpanded) {
            viewHolder.ivArrow.setImageResource(R.drawable.icon_arrow_up);
        } else {
            viewHolder.ivArrow.setImageResource(R.drawable.icon_arrow_down);
        }

        viewHolder.tv.setText(officeDataList.get(groupPosition).getBusiness());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_checkoffice_eplv_child_layout);
            viewHolder = new ChildViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText(officeDataList.get(groupPosition).getOffice().
                get(childPosition).getOfficename());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder{
        private ImageView ivArrow;
        private TextView tv;

        public GroupViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.tv);
            ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        }
    }

    class ChildViewHolder{
        private TextView tv;

        public ChildViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }
}
