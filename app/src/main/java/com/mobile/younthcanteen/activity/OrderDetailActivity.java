package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.OrderDetailLvAdapter;
import com.mobile.younthcanteen.bean.OrderDetailInfoBean;
import com.mobile.younthcanteen.bean.SimpleResultBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.ui.ListViewForScroll;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.FileUtil;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * 订单详情
 * time: 2017/4/4 0004 13:03
 */


public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rl_product_info)
    RelativeLayout rlProductInfo;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_orderno)
    TextView tvOrderno;
    @BindView(R.id.tv_payTime)
    TextView tvPayTime;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_deliveryPhone)
    TextView tvDeliveryPhone;
    @BindView(R.id.tv_counts)
    TextView tvCounts;
    @BindView(R.id.tv_orderMoney)
    TextView tvOrderMoney;
    @BindView(R.id.tv_apply_for_refund)
    TextView tvApplyForRefund;
    @BindView(R.id.tv_payback_status)
    TextView tvPayBackStatus;
    @BindView(R.id.lv)
    ListViewForScroll lv;
    private OrderDetailInfoBean.OrderGetResultEntity.ResultsEntity orderDetailBean;
    private String orderno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin(true);
        setTitle("订单详情");
        setTitleBackVisible(true);

        setContentView(R.layout.activity_orderdetail_layout);
        ButterKnife.bind(this);

        orderno = getIntent().getStringExtra("orderno");
        if (TextUtils.isEmpty(orderno)) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetailInfo();
    }

    /**
     * 获取订单的详情信息
     */
    private void getDetailInfo() {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("orderno", orderno);
        Http.post(Http.GETORDERINFO, params, new MyTextAsyncResponseHandler(act, "正在获取中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    OrderDetailInfoBean bean = JsonUtil.fromJson(content, OrderDetailInfoBean.class);
                    if (null != bean) {
                        if (Http.SUCCESS.equals(bean.getOrderGetResult().getReturnCode())) {
                            orderDetailBean = bean.getOrderGetResult().getResults();
                            showData();
                        } else {
                            ToastUtils.showShortToast(bean.getOrderGetResult().getReturnMessage());
                        }
                    } else {
                        ToastUtils.showShortToast("服务器异常，请稍后重试");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("数据异常，请稍后重试");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });
    }

    /**
     * 显示数据
     */
    private void showData() {
        Intent intent = getIntent();
        boolean isAlreadyPaid = intent.getBooleanExtra("isAlreadyPaid", false);//是否已经付款
        if (isAlreadyPaid) {
            //已付款。可申请退款
            tvApplyForRefund.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(orderDetailBean.getPaybackstatus())) {
                //当前没有申请退款
                tvApplyForRefund.setText("申请退款");
                tvPayBackStatus.setText("");
            } else {
                //当前已经申请退款
                tvApplyForRefund.setText("取消申请退款");
                tvPayBackStatus.setText("(" + orderDetailBean.getPaybackstatus() + ")");
            }
        } else {
            tvApplyForRefund.setVisibility(View.GONE);
            tvPayBackStatus.setText("");
        }
        OrderDetailInfoBean.OrderGetResultEntity.ResultsEntity.ProsEntity prosEntity;
        String title = "";
        int count = 0;
        for (int i = 0; i < orderDetailBean.getPros().size(); i++) {
            prosEntity = orderDetailBean.getPros().get(i);
            title += (prosEntity.getTitle() + ",");
            count += Integer.parseInt(prosEntity.getCounts());
        }

        BitmapUtil bitmapUtil = new BitmapUtil(act, FileUtil.getCachePath(act, "/bitmapcache"),
                new ColorDrawable(act.getResources().getColor(R.color.gray_bg)));
        bitmapUtil.display(iv, orderDetailBean.getPros().get(0).getImgs());


        tvName.setText(title.substring(0, title.lastIndexOf(",")));
        tvMoney.setText("￥" + orderDetailBean.getAllMoney() + "元");
        tvState.setText(orderDetailBean.getStatus());
        tvCounts.setText("数量：" + count);
        tvOrderno.setText("订单号：" + orderDetailBean.getOrderno());
        tvPayTime.setText("付款时间：" + orderDetailBean.getPayTime());
        tvPhone.setText("收货人：" + orderDetailBean.getPhone());
        tvDeliveryPhone.setText("配送人：" + orderDetailBean.getDeliveryPhone());
        tvOrderMoney.setText(orderDetailBean.getOrderMoney() + "元");

        //显示具体的订单
        OrderDetailLvAdapter adapter = new OrderDetailLvAdapter(act, orderDetailBean.getPros());
        lv.setAdapter(adapter);

    }

    @OnClick({R.id.rl_product_info, R.id.tv_state, R.id.tv_apply_for_refund})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_product_info:
                break;
            case R.id.tv_state:
                if (orderDetailBean != null && TextUtils.isEmpty(orderDetailBean.getPayTime())) {
                    //未付款
                    Intent intent = new Intent(act, PayActivity.class);
                    intent.putExtra("orderno", orderDetailBean.getOrderno());
                    intent.putExtra("money", orderDetailBean.getAllMoney());
                    startActivity(intent);
                }
                break;
            case R.id.tv_apply_for_refund://申请退款/取消申请
                String str = tvApplyForRefund.getText().toString().trim();
                if ("申请退款".equals(str)) {
                    //申请退款
                    Intent intent = new Intent(act, ApplyForRefund.class);
                    intent.putExtra("orderno", orderno);
                    startActivity(intent);
                } else {
                    //取消申请退款
                    cancelApplyForRefund();
                }
                break;
        }
    }

    /**
     * 取消申请退款
     */
    private void cancelApplyForRefund() {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("orderno", orderno);
        Http.post(Http.CANCELAPPLYPAYBACK, params, new MyTextAsyncResponseHandler(act, "正在申请中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                    if (null != bean) {
                        ToastUtils.showShortToast(bean.getReturnMessage());
                        if (Http.SUCCESS.equals(bean.getReturnCode())) {
                            getDetailInfo();
                        }
                    } else {
                        ToastUtils.showShortToast("服务器异常，请稍后重试");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("数据异常，请稍后重试");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });
    }
}
