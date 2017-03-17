package com.mobile.younthcanteen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.activity.GoodsDetailInfoActivity;
import com.mobile.younthcanteen.activity.MyAddressActivity;
import com.mobile.younthcanteen.activity.PackageGoodsInfoActivity;
import com.mobile.younthcanteen.activity.PayActivity;
import com.mobile.younthcanteen.adapter.ShoppingCartListAdapter;
import com.mobile.younthcanteen.bean.AddressListBean;
import com.mobile.younthcanteen.bean.CommitOrderResult;
import com.mobile.younthcanteen.bean.OrderBean;
import com.mobile.younthcanteen.bean.ShoppingCartItemBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.ui.ListViewForScroll;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.LoginUtils;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ShoppingCartUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.List;

import static com.mobile.younthcanteen.util.ShoppingCartUtil.getAllShoppingList;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class ShoppingCartFragment extends Fragment implements View.OnClickListener {
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
    private ListViewForScroll listViewForScroll;
    private ShoppingCartListAdapter cartListAdapter;
    private TextView tvModify;
    private TextView tvTotalPrice;
    private Button btnCommitOrder;
    private AddressListBean.ResultsEntity addressBean;//收货地址
    private EditText etRemark;

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
        listViewForScroll = (ListViewForScroll) view.findViewById(R.id.lv_address_list);
        tvModify = (TextView) view.findViewById(R.id.tv_modify);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        btnCommitOrder = (Button) view.findViewById(R.id.btn_commit_order);
        etRemark = (EditText) view.findViewById(R.id.et_remark);

        llNoAddress.setVisibility(View.VISIBLE);
        rlAddress.setVisibility(View.GONE);
    }

    private void setListener() {
        llNoAddress.setOnClickListener(this);
        rlAddress.setOnClickListener(this);
        tvModify.setOnClickListener(this);
        btnCommitOrder.setOnClickListener(this);
        listViewForScroll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ShoppingCartItemBean> cartList = ShoppingCartUtil.getAllShoppingList();
                ShoppingCartItemBean bean = cartList.get(position);
                if (bean != null) {
                    Intent intent = new Intent();
                    if ("0".equals(bean.getType())) {
                        intent.setClass(getActivity(), GoodsDetailInfoActivity.class);
                    } else {
                        intent.setClass(getActivity(), PackageGoodsInfoActivity.class);
                    }
                    intent.putExtra("imageUrl", bean.getImgUrl());
                    intent.putExtra("proid", bean.getProid());
                    startActivity(intent);
                }
            }
        });
    }

    private void initFragment() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_no_address://添加地址
            case R.id.rl_address://添加地址
                if (LoginUtils.isLogin()) {
                    Intent intent = new Intent(getActivity(), MyAddressActivity.class);
                    startActivityForResult(intent, GETADDRESS_REQUESTCODE);
                } else {
                    ToastUtils.showShortToast("请先登录");
                }
                break;
            case R.id.tv_modify://编辑完成
                String curTxt = tvModify.getText().toString().trim();
                if ("编辑".equals(curTxt)) {
                    tvModify.setText("完成");
                    if (cartListAdapter != null) {
                        cartListAdapter.setShowDelete(true);
                        cartListAdapter.notifyDataSetChanged();
                    }
                } else {
                    tvModify.setText("编辑");
                    if (cartListAdapter != null) {
                        cartListAdapter.setShowDelete(false);
                        cartListAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.btn_commit_order://提交订单
                commitOrder();
                break;
        }
    }

    /**
     * 提交订单
     */
   private void commitOrder() {
       if (!LoginUtils.isLogin()) {
           ToastUtils.showShortToast("请先登录");
           return;
       }
       if (addressBean == null) {
           ToastUtils.showShortToast("请选择收货地址");
           return;
       }
       String remarkStr = etRemark.getText().toString().trim();
       if (TextUtils.isEmpty(remarkStr)) {
           remarkStr = "";
       }
       OrderBean orderBean = new OrderBean();
       orderBean.setToken(SharedPreferencesUtil.getToken());
       orderBean.setAddrid(addressBean.getAddressid());
       orderBean.setRemark(remarkStr);
       orderBean.setPros(JsonUtil.toJson(ShoppingCartUtil.getAllShoppingList()));
       Http.postJson(Http.ORDERADD,orderBean.toJson(),new MyTextAsyncResponseHandler(getActivity(),"提交中..."){
           @Override
           public void onSuccess(String content) {
               super.onSuccess(content);
               CommitOrderResult result = JsonUtil.fromJson(content, CommitOrderResult.class);
               if (null != result) {
                   if (!Http.SUCCESS.equals(result.getReturnCode())) {
                       ToastUtils.showShortToast(result.getReturnMessage());
                       return;
                   }
                   Intent intent = new Intent();
                   intent.setClass(getActivity(), PayActivity.class);
                   intent.putExtra("orderno", result.getOrderno());
                   intent.putExtra("money", result.getMoney());
                   startActivity(intent);
               }
           }

           @Override
           public void onFailure(Throwable error) {
               super.onFailure(error);
           }
       });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == GETADDRESS_RESULTCODE) {
            Bundle bundle = data.getExtras();
            addressBean = (AddressListBean.ResultsEntity) bundle.getSerializable("resultBean");
            System.out.println("resultCode::" + addressBean);
            if (addressBean != null) {
                llNoAddress.setVisibility(View.GONE);
                rlAddress.setVisibility(View.VISIBLE);
                tvAddress.setText(addressBean.getAddr());
                tvOffice.setText(addressBean.getOffice());
                tvName.setText(addressBean.getConsignee());
                tvTel.setText(addressBean.getTel());
                tvSex.setText("1".equals(addressBean.getSex()) ? "女士" : "先生");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isRefreshUI) {
            refreshUI();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResumen
            if (!isNeedReLoad && !isRefreshUI) {
                refreshUI();
            }
        } else {
            //相当于Fragment的onPause
        }
    }

    private boolean isRefreshUI = false;

    /**
     * 处理数据逻辑
     */
    private void refreshUI() {
        isRefreshUI = true;
        shoppingCartList = getAllShoppingList();
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            //购物车中有物品
            tvModify.setVisibility(View.VISIBLE);
            tvNoShopping.setVisibility(View.GONE);
            svCartContent.setVisibility(View.VISIBLE);
            tvModify.setText("编辑");
            tvTotalPrice.setText("￥" + ShoppingCartUtil.getTotalPrice());
            if (cartListAdapter == null) {
                cartListAdapter =
                        new ShoppingCartListAdapter(getActivity(), shoppingCartList,
                                new ShoppingCartListAdapter.CartListChangeListener() {
                                    @Override
                                    public void cartListChanged() {
                                        //购物车的数据改变了
                                        if (ShoppingCartUtil.shoppingCartIsNull()) {
                                            //购物车为空
                                            tvModify.setVisibility(View.GONE);
                                            tvNoShopping.setVisibility(View.VISIBLE);
                                            svCartContent.setVisibility(View.GONE);
                                        } else {
                                            tvModify.setVisibility(View.VISIBLE);
                                            tvNoShopping.setVisibility(View.GONE);
                                            svCartContent.setVisibility(View.VISIBLE);
                                            tvTotalPrice.setText("￥" + ShoppingCartUtil.getTotalPrice());
                                        }
                                    }
                                });
                listViewForScroll.setAdapter(cartListAdapter);
            } else {
                cartListAdapter.setShowDelete(false);
                cartListAdapter.setShoppingCartList(shoppingCartList);
                cartListAdapter.notifyDataSetChanged();
            }
        } else {
            //购物车为空
            tvModify.setVisibility(View.GONE);
            tvNoShopping.setVisibility(View.VISIBLE);
            svCartContent.setVisibility(View.GONE);
        }
        isRefreshUI = false;
    }
}
