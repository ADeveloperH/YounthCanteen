package com.mobile.younthcanteen.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.GoodsInfoPagerAdapter;
import com.mobile.younthcanteen.bean.GoodsDetailInfoBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.ui.ZoomScrollView;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.FileUtil;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.ToastUtils;
import com.mobile.younthcanteen.util.UIUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/3/1 0001 17:07
 */

public class GoodsDetailInfoActivity extends Activity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ll_add_to_cart)
    LinearLayout llAddToCart;
    @BindView(R.id.tv_goods_info)
    TextView tvGoodsInfo;
    @BindView(R.id.ll_goods_info)
    LinearLayout llGoodsInfo;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.ll_point_group)
    LinearLayout llPointGroup;
    @BindView(R.id.sv_root)
    ZoomScrollView svRoot;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    @BindView(R.id.ll_out)
    LinearLayout llOut;
    @BindView(R.id.iv_cart_add)
    ImageView ivCartAdd;
    @BindView(R.id.tv_cart_num)
    TextView tvCartNum;
    @BindView(R.id.iv_cart_subtract)
    ImageView ivCartSubtract;
    @BindView(R.id.ll_add_subtract)
    LinearLayout llAddSubtract;
    @BindView(R.id.tv_clearing)
    TextView tvClearing;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.tv_red_num)
    TextView tvRedNum;
    @BindView(R.id.tv_result_price)
    TextView tvResultPrice;
    private Context context;
    private List<String> viewPagerDataList;
    private ArrayList<ImageView> imageList;
    private final static int VIEWPAGEDATAGETSUCCESS = 0;
    private final static int SHOWVIEWPAGER = 2;
    private final static int CHANGVIEWPAGERITEM = 3;
    private MyHandler mHandler = new MyHandler(this);

    private int goodsCount = 0;//已添加的商品数量
    private double unitPrice = 0;//商品单价

    @OnClick({R.id.iv_back, R.id.ll_add_to_cart, R.id.iv_cart_add,
            R.id.iv_cart_subtract, R.id.tv_clearing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.ll_add_to_cart://添加到购物车
                llAddToCart.setVisibility(View.GONE);
                llAddSubtract.setVisibility(View.VISIBLE);
                goodsCount++;
                tvCartNum.setText(goodsCount + "");
                tvRedNum.setVisibility(View.VISIBLE);
                tvRedNum.setText(goodsCount + "");
                ivCart.setImageResource(R.drawable.cart_enable);
                tvResultPrice.setText("￥" + goodsCount * unitPrice + "");
                tvClearing.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_cart_add://添加购物车加号
                goodsCount++;
                tvCartNum.setText(goodsCount + "");
                tvRedNum.setText(goodsCount + "");
                tvResultPrice.setText("￥" + goodsCount * unitPrice + "");
                break;
            case R.id.iv_cart_subtract://添加购物车减号
                goodsCount--;
                if (goodsCount == 0) {
                    //
                    llAddToCart.setVisibility(View.VISIBLE);
                    llAddSubtract.setVisibility(View.GONE);
                    tvCartNum.setText(goodsCount + "");
                    tvRedNum.setVisibility(View.GONE);
                    tvRedNum.setText(goodsCount + "");
                    ivCart.setImageResource(R.drawable.cart_unable);
                    tvResultPrice.setText("￥" + "0");
                    tvClearing.setVisibility(View.GONE);
                } else {
                    tvCartNum.setText(goodsCount + "");
                    tvRedNum.setText(goodsCount + "");
                    tvResultPrice.setText("￥" + goodsCount * unitPrice + "");
                }
                break;
            case R.id.tv_clearing://去结算
                ToastUtils.showShortToast("该功能正在开发中...");
                break;
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<GoodsDetailInfoActivity> activityWeakReference;

        public MyHandler(GoodsDetailInfoActivity goodsDetailInfoActivity) {
            activityWeakReference = new WeakReference<GoodsDetailInfoActivity>(
                    goodsDetailInfoActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final GoodsDetailInfoActivity activity = activityWeakReference.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case VIEWPAGEDATAGETSUCCESS:// 轮播图请求成功
                    break;
                case SHOWVIEWPAGER:// 展示轮播图
                    activity.showViewPager();
                    break;
                case CHANGVIEWPAGERITEM:// 轮播图自动切换
                    // viewPager 切换至下一页
                    if (activity.viewpager != null) {
                        activity.viewpager
                                .setCurrentItem(activity.viewpager
                                        .getCurrentItem() + 1);
                        // 发送延时消息，发送后， 3000 毫秒后，执行handleMessage方法
                        sendEmptyMessageDelayed(CHANGVIEWPAGERITEM, 6000);
                    }
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetailinfo_layout);
        ButterKnife.bind(this);
        context = this;
        init();
        getData();

    }

    private void init() {
        svRoot.setZoomView(rlContainer);
//        viewpager.getLayoutParams();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra("proid")) {
            finish();
            return;
        }
        String proid = intent.getStringExtra("proid");
        RequestParams params = new RequestParams();
        params.put("proid", proid);
        params.put("zoneid", "0");
        Http.post(Http.GETGOODSINFO, params, new MyTextAsyncResponseHandler(context, "正在获取中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                GoodsDetailInfoBean bean = JsonUtil.fromJson(content, GoodsDetailInfoBean.class);
                if (bean == null) {
                    ToastUtils.showShortToast("服务器数据异常，请稍后重试");
                } else {
                    if (Http.SUCCESS.equals(bean.getReturnCode())) {
                        showDetailInfo(bean);
                    } else {
                        ToastUtils.showShortToast(bean.getReturnMessage());
                    }
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
     * 显示商品详情
     *
     * @param bean
     */
    private void showDetailInfo(GoodsDetailInfoBean bean) {
        tvName.setText(bean.getResults().getName());
        unitPrice = Double.parseDouble(bean.getResults().getPrice());
        tvPrice.setText("￥" + unitPrice + "元");
        String goodsDes = bean.getResults().getDescribe();
        if (!TextUtils.isEmpty(goodsDes)) {
            llGoodsInfo.setVisibility(View.VISIBLE);
            tvGoodsInfo.setText(goodsDes);
        } else {
            llGoodsInfo.setVisibility(View.GONE);
        }
        viewPagerDataList = bean.getResults().getUrl();
        initViewPagerData();

    }


    /**
     * 处理轮播图数据
     */
    private void initViewPagerData() {
        viewpager.setOnPageChangeListener(this);
        if (viewPagerDataList == null || viewPagerDataList.size() == 0) {
            //没有轮播图.显示默认图
            viewpager.setVisibility(View.GONE);
            llPointGroup.setVisibility(View.GONE);
            ivBanner.setVisibility(View.VISIBLE);
            return;
        }
        imageList = new ArrayList<ImageView>();
        BitmapUtil bitmapUtil = new BitmapUtil(context,
                FileUtil.getCachePath(context, "/bitmapcache"));
        bitmapUtil.setLoadFailedDrawable(UIUtils.getDrawable(
                R.color.gray_bg));
        bitmapUtil.setLoadingDrawable(UIUtils.getDrawable(
                R.color.gray_bg));

        if (viewPagerDataList.size() >= 2) {
            int location = viewPagerDataList.size() - 1;
            final ImageView imageFirst = new ImageView(context);
            imageFirst.setTag(location);// 实际的位置
            imageFirst.setImageResource(R.color.gray_bg);
            imageFirst.setScaleType(ImageView.ScaleType.FIT_XY);
            imageList.add(imageFirst);
            for (int i = 0; i < viewPagerDataList.size(); i++) {
                final ImageView image = new ImageView(context);
                image.setTag(i);// 实际的位置
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                image.setImageResource(R.color.gray_bg);
                if (i == location) {
                    bitmapUtil.display(image, viewPagerDataList.get(i)
                            , new BitmapUtil.BitmapLoadCallBack() {
                                @Override
                                public void onLoadCompleted(Bitmap bitmap) {
                                    image.setImageBitmap(bitmap);
                                    imageFirst.setImageBitmap(bitmap);
                                }
                            });
                } else {
                    if (i != 0) {
                        bitmapUtil.display(image, viewPagerDataList.get(i));
                    }
                }
                imageList.add(image);
            }
            final ImageView imageLast = new ImageView(context);
            imageLast.setTag(0);// 实际的位置
            imageLast.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLast.setImageResource(R.color.gray_bg);
            bitmapUtil.display(imageLast, viewPagerDataList.get(0)
                    , new BitmapUtil.BitmapLoadCallBack() {
                        @Override
                        public void onLoadCompleted(Bitmap bitmap) {
                            imageLast.setImageBitmap(bitmap);
                            if (imageList.size() > 1) {
                                imageList.get(1).setImageBitmap(bitmap);
                            }
                        }
                    });
            imageList.add(imageLast);
        } else {
            for (int i = 0; i < viewPagerDataList.size(); i++) {
                ImageView image = new ImageView(context);
                image.setTag(i);// 实际的位置
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                bitmapUtil.display(image, viewPagerDataList.get(i));
                imageList.add(image);
            }
        }
        mHandler.removeMessages(CHANGVIEWPAGERITEM);
        mHandler.sendEmptyMessage(SHOWVIEWPAGER);
    }


    /**
     * 显示轮播图
     */
    private void showViewPager() {
        ivBanner.setVisibility(View.GONE);
        viewpager.setVisibility(View.VISIBLE);
        llPointGroup.setVisibility(View.VISIBLE);
        llPointGroup.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                -2, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 20;
        if (viewPagerDataList.size() >= 2) {
            for (int i = 0; i < viewPagerDataList.size(); i++) {
                ImageView point = new ImageView(context);
                point.setBackgroundResource(R.drawable.point_bg);
                llPointGroup.addView(point, layoutParams);
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }
            }
        }
        mHandler.removeMessages(CHANGVIEWPAGERITEM);
        viewPagerAdapter = new GoodsInfoPagerAdapter(
                context, viewPagerDataList, imageList);
        viewpager.setAdapter(viewPagerAdapter);
        if (viewPagerDataList.size() >= 2) {
            // 自动循环
            lastPosition = 1;
            viewpager.setCurrentItem(1, true);// 显示第一个图片。
            mHandler.sendEmptyMessageDelayed(CHANGVIEWPAGERITEM, 6000);
        } else {
            lastPosition = 0;
            viewpager.setCurrentItem(0, true);// 显示第一个图片。
        }
    }

    // 上一个选中的页面的下标
    private int lastPosition = 0;
    private GoodsInfoPagerAdapter viewPagerAdapter;

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            int curPosition = viewpager.getCurrentItem();
            if (curPosition == imageList.size() - 1) {
                viewpager.setCurrentItem(1, false);// 为了无限循环
            } else if (curPosition == 0) {
                viewpager.setCurrentItem(imageList.size() - 2, false);
            }
            viewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPageSelected(int position) {
        // 切换指示点的选中状态
        View lastChild = llPointGroup
                .getChildAt(getRealIndexFromDataList(lastPosition));
        View curChild = llPointGroup
                .getChildAt(getRealIndexFromDataList(position));
        if (lastChild != null) {
            // 让上一个选中的点，显示为普通色
            lastChild.setEnabled(false);
        }
        if (curChild != null) {
            // 让当前选中的点显示为白色
            curChild.setEnabled(true);
        }
        // 为lastPosition 设值
        lastPosition = position;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * 根据当前index获取实际在数据集合中的位置下标
     *
     * @param index 在图片集合imageList中的位置
     * @return
     */
    private int getRealIndexFromDataList(int index) {
        int realPosition = index - 1;
        int count = viewPagerDataList.size();
        if (realPosition < 0) {
            realPosition += count;
        } else if (realPosition >= count) {
            realPosition -= count;
        }
        return realPosition;
    }
}
