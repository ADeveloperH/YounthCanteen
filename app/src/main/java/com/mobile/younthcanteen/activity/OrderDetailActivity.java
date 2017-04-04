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
import com.mobile.younthcanteen.bean.OrderResultBean;
import com.mobile.younthcanteen.ui.ListViewForScroll;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.FileUtil;

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
    @BindView(R.id.lv)
    ListViewForScroll lv;
    private OrderResultBean.ResultsEntity bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin(true);
        setTitle("订单详情");
        setTitleBackVisible(true);

        setContentView(R.layout.activity_orderdetail_layout);
        ButterKnife.bind(this);

        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        bean = (OrderResultBean.ResultsEntity) intent.getSerializableExtra("orderInfo");

        OrderResultBean.ResultsEntity.ProsEntity prosEntity;
        String title = "";
        int count = 0;
        for (int i = 0; i < bean.getPros().size(); i++) {
            prosEntity = bean.getPros().get(i);
            title += (prosEntity.getTitle() + ",");
            count += Integer.parseInt(prosEntity.getCounts());
        }

        BitmapUtil bitmapUtil = new BitmapUtil(act, FileUtil.getCachePath(act, "/bitmapcache"),
                new ColorDrawable(act.getResources().getColor(R.color.gray_bg)));
        bitmapUtil.display(iv, bean.getPros().get(0).getImgs());


        tvName.setText(title.substring(0, title.lastIndexOf(",")));
        tvMoney.setText("￥" + bean.getAllMoney() + "元");
        tvState.setText(bean.getStatus());
        tvCounts.setText("数量：" + count);
        tvOrderno.setText("订单号：" + bean.getOrderno());
        tvPayTime.setText("付款时间：" + bean.getPayTime());
        tvPhone.setText("收货人：" + bean.getPhone());
        tvDeliveryPhone.setText("配送人：" + bean.getDeliveryPhone());
        tvOrderMoney.setText(bean.getOrderMoney() + "元");

        //显示具体的订单
        OrderDetailLvAdapter adapter = new OrderDetailLvAdapter(act, bean.getPros());
        lv.setAdapter(adapter);

    }

    @OnClick({R.id.rl_product_info, R.id.tv_state})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_product_info:
                break;
            case R.id.tv_state:
                if (bean != null && TextUtils.isEmpty(bean.getPayTime())) {
                    //未付款
                    Intent intent = new Intent(act, PayActivity.class);
                    intent.putExtra("orderno", bean.getOrderno());
                    intent.putExtra("money", bean.getAllMoney());
                    startActivity(intent);
                }
                break;
        }
    }
}
