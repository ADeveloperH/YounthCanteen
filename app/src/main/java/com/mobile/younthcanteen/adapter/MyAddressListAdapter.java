package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.AddAddressActivity;
import com.mobile.younthcanteen.bean.AddressListBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.listener.DeleteAddressListener;
import com.mobile.younthcanteen.util.DialogUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/2/22 0022 14:35
 */

public class MyAddressListAdapter extends BaseAdapter {
    private DeleteAddressListener deleteAddressListener;
    private Context context;
    private boolean isShowModify;
    List<AddressListBean.ResultsEntity> addressDataList;

    public MyAddressListAdapter(Context context, List<AddressListBean.ResultsEntity> addressDataList,
                                boolean isShowModify, DeleteAddressListener deleteAddressListener) {
        this.deleteAddressListener = deleteAddressListener;
        this.context = context;
        this.addressDataList = addressDataList;
        this.isShowModify = isShowModify;
    }

    @Override
    public int getCount() {
        return addressDataList == null ? 0 : addressDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_myaddress_list);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final AddressListBean.ResultsEntity resultsEntity = addressDataList.get(position);
        vh.tvAddress.setText(resultsEntity.getAddr());
        vh.tvOffice.setText(resultsEntity.getOffice());
        vh.tvName.setText(resultsEntity.getConsignee());
        vh.tvTel.setText(resultsEntity.getTel());
        vh.ivModify.setVisibility(isShowModify ? View.VISIBLE : View.GONE);
        vh.ivDelete.setVisibility(isShowModify ? View.VISIBLE : View.GONE);
        vh.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.getSimpleDialogNoTitle(context, "确认删除此送货地址？", "确定", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAddress(resultsEntity.getAddressid());
                    }
                }, null).show();
            }
        });
        vh.ivModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("title", "编辑收货地址");
                intent.putExtra("name", resultsEntity.getConsignee());
                intent.putExtra("sex", resultsEntity.getConsignee());
                intent.putExtra("phone", resultsEntity.getTel());
                intent.putExtra("office", resultsEntity.getOffice());
                intent.putExtra("officeId", resultsEntity.getOfficeid());
                intent.putExtra("address", resultsEntity.getAddr());
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    /**
     * 删除收货地址
     * @param addressid
     */
    private void deleteAddress(String addressid) {
        RequestParams params = new RequestParams();
        params.put("addrid", addressid);
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.DELETEADDRESS,params,new MyTextAsyncResponseHandler(context,"正在删除中..."){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (deleteAddressListener != null) {
                    deleteAddressListener.deleteSucess();
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });
    }

    static class ViewHolder {
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_office)
        TextView tvOffice;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_tel)
        TextView tvTel;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.iv_modify)
        ImageView ivModify;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setShowModify(boolean showModify) {
        isShowModify = showModify;
    }
}
