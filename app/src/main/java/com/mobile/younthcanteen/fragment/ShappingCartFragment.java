package com.mobile.younthcanteen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.MyAddressActivity;
import com.mobile.younthcanteen.bean.AddressListBean;
import com.mobile.younthcanteen.bean.ShoppingCartItemBean;
import com.mobile.younthcanteen.util.ShoppingCartUtil;

import java.util.List;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class ShappingCartFragment extends Fragment implements View.OnClickListener {
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
    private LinearLayout llNoAddress;
    private RelativeLayout rlAddress;
    private TextView tvOffice;
    private TextView tvAddress;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvTel;
    private TextView tvNoShopping;
    private ScrollView svCartContent;
    private final int GETADDRESS_REQUESTCODE = 11;
    private final int GETADDRESS_RESULTCODE = 12;
    private List<ShoppingCartItemBean> shoppingCartList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_cart, null);
        }
        //缓存的rootView需要判断是否已经被加过parent，
        //如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isNeedReLoad) {
            initView(getView());
            setListener();
//            getData();
            isNeedReLoad = false;
        }
    }


    private void initView(View view) {
        llNoAddress = (LinearLayout) view.findViewById(R.id.ll_no_address);
        rlAddress = (RelativeLayout) view.findViewById(R.id.rl_address);
        tvOffice = (TextView) view.findViewById(R.id.tv_office);
        tvAddress = (TextView) view.findViewById(R.id.tv_address);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSex = (TextView) view.findViewById(R.id.tv_sex);
        tvTel = (TextView) view.findViewById(R.id.tv_tel);
        tvNoShopping = (TextView) view.findViewById(R.id.tv_no_shopping);
        svCartContent = (ScrollView) view.findViewById(R.id.sv_cart_content);

        llNoAddress.setVisibility(View.VISIBLE);
        rlAddress.setVisibility(View.GONE);
    }

    private void setListener() {
        llNoAddress.setOnClickListener(this);
        rlAddress.setOnClickListener(this);
    }

    private void initFragment() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_no_address://添加地址
            case R.id.rl_address://添加地址
                Intent intent = new Intent(getActivity(), MyAddressActivity.class);
                startActivityForResult(intent, GETADDRESS_REQUESTCODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == GETADDRESS_RESULTCODE) {
            Bundle bundle = data.getExtras();
            AddressListBean.ResultsEntity resultsEntity = (AddressListBean.ResultsEntity) bundle.getSerializable("resultBean");
            System.out.println("resultCode::" + resultsEntity);
            if (resultsEntity != null) {
                llNoAddress.setVisibility(View.GONE);
                rlAddress.setVisibility(View.VISIBLE);
                tvAddress.setText(resultsEntity.getAddr());
                tvOffice.setText(resultsEntity.getOffice());
                tvName.setText(resultsEntity.getConsignee());
                tvTel.setText(resultsEntity.getTel());
                tvSex.setText("1".equals(resultsEntity.getSex()) ? "女士" : "先生");
            }
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            shoppingCartList = ShoppingCartUtil.getAllShoppingList();
            if (shoppingCartList != null && shoppingCartList.size() > 0) {
                //购物车中有物品
                tvNoShopping.setVisibility(View.GONE);
                svCartContent.setVisibility(View.VISIBLE);
            } else {
                tvNoShopping.setVisibility(View.VISIBLE);
                svCartContent.setVisibility(View.GONE);
            }
        } else {
            //相当于Fragment的onPause
        }
    }
}
