package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
    @BindView(R.id.lv)
    ListViewForScroll lv;
    @BindView(R.id.tv_addTime)
    TextView tvAddTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_consignee)
    TextView tvConsignee;
    @BindView(R.id.tv_payType)
    TextView tvPayType;
    @BindView(R.id.tv_deliveryTime)
    TextView tvDeliveryTime;
    @BindView(R.id.tv_backApplyTime)
    TextView tvBackApplyTime;
    @BindView(R.id.tv_backApplyResult)
    TextView tvBackApplyResult;
    @BindView(R.id.tv_paybackstatus)
    TextView tvPaybackstatus;
    @BindView(R.id.tv_shoptel)
    TextView tvShoptel;
    @BindView(R.id.tv_backAgreeTime)
    TextView tvBackAgreeTime;
    @BindView(R.id.tv_backOverTime)
    TextView tvBackOverTime;
    @BindView(R.id.tv_ordermoney_desc)
    TextView tvOrdermoneyDesc;
    @BindView(R.id.btn_comfirm)
    Button btnComfirm;
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
            //已付款。
            tvPayType.setVisibility(View.VISIBLE);
            tvDeliveryTime.setVisibility(View.VISIBLE);
            tvPayTime.setVisibility(View.VISIBLE);
            tvDeliveryPhone.setVisibility(View.VISIBLE);
            tvShoptel.setVisibility(View.VISIBLE);
            tvOrdermoneyDesc.setText("实付款：");
            showTextViewContent(tvPayType, "支付方式：", orderDetailBean.getPayType());
            showTextViewContent(tvDeliveryTime, "接单时间：", orderDetailBean.getOkTime());
            showTextViewContent(tvPayTime, "付款时间：", orderDetailBean.getPayTime());
            showTextViewContent(tvDeliveryPhone, "配送电话：", orderDetailBean.getDeliveryPhone());
            showTextViewContent(tvShoptel, "商家电话：", orderDetailBean.getShoptel());
            //可申请退款
            tvApplyForRefund.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(orderDetailBean.getPaybackstatus())) {
                //当前没有申请退款
                tvApplyForRefund.setText("申请退款");
                tvBackApplyTime.setVisibility(View.GONE);
                tvPaybackstatus.setVisibility(View.GONE);
                tvBackApplyResult.setVisibility(View.GONE);
                tvBackAgreeTime.setVisibility(View.GONE);
                tvBackOverTime.setVisibility(View.GONE);
            } else {
                //当前已经申请退款
                tvBackApplyTime.setVisibility(View.VISIBLE);
                tvPaybackstatus.setVisibility(View.VISIBLE);
                tvBackApplyResult.setVisibility(View.VISIBLE);
                tvBackAgreeTime.setVisibility(View.VISIBLE);
                tvBackOverTime.setVisibility(View.VISIBLE);
                tvApplyForRefund.setText("取消申请退款");
                if ("已退款".equals(orderDetailBean.getPaybackstatus())) {
                    tvApplyForRefund.setVisibility(View.GONE);
                }
                showTextViewContent(tvBackApplyTime, "申请时间：", orderDetailBean.getBackApplyTime());
                showTextViewContent(tvPaybackstatus, "退款状态：", orderDetailBean.getPaybackstatus());
                showTextViewContent(tvBackApplyResult, "申请原因：", orderDetailBean.getBackApplyResult());
                showTextViewContent(tvBackAgreeTime, "处理时间：", orderDetailBean.getBackAgreeTime());
                showTextViewContent(tvBackOverTime, "到账时间：", orderDetailBean.getBackOverTime());
            }
        } else {
            tvOrdermoneyDesc.setText("待付款");
            tvDeliveryPhone.setVisibility(View.GONE);
            tvShoptel.setVisibility(View.GONE);
            tvPayTime.setVisibility(View.GONE);
            tvApplyForRefund.setVisibility(View.GONE);
            tvBackApplyTime.setVisibility(View.GONE);
            tvPaybackstatus.setVisibility(View.GONE);
            tvBackApplyResult.setVisibility(View.GONE);
            tvPayType.setVisibility(View.GONE);
            tvDeliveryTime.setVisibility(View.GONE);
            tvBackAgreeTime.setVisibility(View.GONE);
            tvBackOverTime.setVisibility(View.GONE);
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
        tvOrderMoney.setText(orderDetailBean.getOrderMoney() + "元");
        tvState.setText(orderDetailBean.getStatus());
        tvCounts.setText("数量：" + count);
        showTextViewContent(tvOrderno, "订单号：", orderDetailBean.getOrderno());
        showTextViewContent(tvPhone, "联系电话：", orderDetailBean.getPhone());
        showTextViewContent(tvAddTime, "下单时间：", orderDetailBean.getAddTime());
        showTextViewContent(tvAddress, "收货地址：", orderDetailBean.getAddress());
        showTextViewContent(tvConsignee, "收货人：", orderDetailBean.getConsignee());

        if ("配送中".equals(orderDetailBean.getStatus())) {
            //配送中的可以确认收货
            btnComfirm.setVisibility(View.VISIBLE);
        } else {
            btnComfirm.setVisibility(View.GONE);
        }


        //显示具体的订单
        OrderDetailLvAdapter adapter = new OrderDetailLvAdapter(act, orderDetailBean.getPros());
        lv.setAdapter(adapter);

    }

    @OnClick({R.id.rl_product_info, R.id.tv_state,
            R.id.tv_apply_for_refund, R.id.btn_comfirm,
            R.id.tv_shoptel,R.id.tv_deliveryPhone})
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
            case R.id.btn_comfirm://确认收货
                confirmReceipt();
                break;
            case R.id.tv_shoptel://商家电话
                String shoptelStr = tvShoptel.getText().toString().trim();
                if (!TextUtils.isEmpty(shoptelStr)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + shoptelStr));
                    startActivity(intent);
                }
                break;
            case R.id.tv_deliveryPhone://配送电话
                String deliveryPhone = tvDeliveryPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(deliveryPhone)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + deliveryPhone));
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 确认收货
     */
    private void confirmReceipt() {
        RequestParams params = new RequestParams();
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("orderno", orderno);
        Http.post(Http.CONFIRMRECEIPT, params, new MyTextAsyncResponseHandler(act, "正在申请中...") {
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


    private void showTextViewContent(TextView textView, String str, String content) {
        if (TextUtils.isEmpty(content)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(str + content);
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
