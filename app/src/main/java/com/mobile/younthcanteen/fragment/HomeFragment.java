package com.mobile.younthcanteen.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.HomeFragmentPagerAdapter;
import com.mobile.younthcanteen.adapter.HomeListAdapter;
import com.mobile.younthcanteen.bean.HomeDataBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.ui.HomeRefreshListView;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.FileUtil;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.ToastUtils;
import com.mobile.younthcanteen.util.UIUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * author：hj
 * time: 2017/2/7 0007 15:15
 */

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private View rootView;//缓存Fragment的View
    private boolean isNeedReLoad = true;//是否需要重新加载该Fragment数据
    private HomeRefreshListView lvHome;
    private Context mActivity;
    private ImageView ivBanner;
    private ViewPager viewPager;
    private LinearLayout llPointGroup;
    private final static int VIEWPAGEDATAGETSUCCESS = 0;
    private final static int VIEWPAGEDATAGETFAILURE = 1;
    private final static int SHOWVIEWPAGER = 2;
    private final static int CHANGVIEWPAGERITEM = 3;
    private List<ImageView> imageList;// 轮播图图片的集合
    private List<HomeDataBean.TopEntity> viewPagerDataList;// 轮播图的数据集合
    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<HomeFragment> homeFragmentWeakReference;

        public MyHandler(HomeFragment homeFragment) {
            homeFragmentWeakReference = new WeakReference<HomeFragment>(
                    homeFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final HomeFragment homeFragment = homeFragmentWeakReference.get();
            if (homeFragment == null) {
                return;
            }
            switch (msg.what) {
                case VIEWPAGEDATAGETSUCCESS:// 轮播图请求成功
                    break;
                case SHOWVIEWPAGER:// 展示轮播图
                    homeFragment.showViewPager();
                    break;
//                case VIEWPAGEDATAGETFAILURE:// 轮播图请求失败
//                    if (!homeFragment.isGetViewPagerSus) {
//                        homeFragment.viewPagerShowDefault();
//                    }
//                    break;
                case CHANGVIEWPAGERITEM:// 轮播图自动切换
                    // viewPager 切换至下一页
                    if (homeFragment.viewPager != null) {
                        homeFragment.viewPager
                                .setCurrentItem(homeFragment.viewPager
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
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
            mActivity = getActivity();
            initView(getView());
//            initData();
            setListener();
            getData();
            isNeedReLoad = false;
        }
    }

    private void initView(View view) {
        lvHome = (HomeRefreshListView) view.findViewById(R.id.lv_home);
        addLvHeaderView();
        ivBanner = (ImageView) view.findViewById(R.id.iv_banner);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        llPointGroup = (LinearLayout) view.findViewById(R.id.ll_point_group);
    }

    /**
     * 为listview 添加头
     */
    private void addLvHeaderView() {
        LayoutInflater lif = (LayoutInflater) mActivity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = lif.inflate(R.layout.fragment_home_lv_header,
                lvHome, false);
        lvHome.addHeaderView(headerView,null,true);
        //不显示header的分割线
        lvHome.setHeaderDividersEnabled(false);
    }

    private void setListener() {
        viewPager.setOnPageChangeListener(this);

    }

    private void getData() {
        RequestParams params = new RequestParams();
        Http.post(Http.GETHOMEDATA, params, new MyTextAsyncResponseHandler(mActivity, "正在加载中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                HomeDataBean homeDataBean = JsonUtil.fromJson(content, HomeDataBean.class);
                if (null != homeDataBean) {
                    if (Http.SUCCESS.equals(homeDataBean.getReturnCode())) {
                        //成功
                        showListView(homeDataBean.getCenter());
                        viewPagerDataList = homeDataBean.getTop();
                        initViewPagerData();

                    } else {
                        ToastUtils.showShortToast(homeDataBean.getReturnMessage());
                    }

                } else {
                    ToastUtils.showShortToast("服务器数据异常,请稍后重试.");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常,请稍后重试.");
            }
        });
    }

    /**
     * 处理轮播图数据
     */
    private void initViewPagerData() {
        if (viewPagerDataList == null || viewPagerDataList.size() == 0) {
            //没有轮播图.显示默认图
            viewPager.setVisibility(View.GONE);
            llPointGroup.setVisibility(View.GONE);
            ivBanner.setVisibility(View.VISIBLE);
            return;
        }
        if (imageList == null) {
            imageList = new ArrayList<ImageView>();
        } else {
            imageList.clear();
        }
        BitmapUtil bitmapUtil = new BitmapUtil(mActivity,
                FileUtil.getCachePath(mActivity, "/bitmapcache"));
        if (isAdded()) {
            bitmapUtil.setLoadFailedDrawable(UIUtils.getDrawable(
                    R.color.gray_bg));
            bitmapUtil.setLoadingDrawable(UIUtils.getDrawable(
                    R.color.gray_bg));
        }

        if (viewPagerDataList.size() >= 2) {
            int location = viewPagerDataList.size() - 1;
            final ImageView imageFirst = new ImageView(mActivity);
            imageFirst.setTag(location);// 实际的位置
            imageFirst.setImageResource(R.color.gray_bg);
            imageFirst.setScaleType(ImageView.ScaleType.FIT_XY);
            imageList.add(imageFirst);
            for (int i = 0; i < viewPagerDataList.size(); i++) {
                final ImageView image = new ImageView(mActivity);
                image.setTag(i);// 实际的位置
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                image.setImageResource(R.color.gray_bg);
                if (i == location) {
                    bitmapUtil.display(image, viewPagerDataList.get(i)
                            .getImg(), new BitmapUtil.BitmapLoadCallBack() {
                        @Override
                        public void onLoadCompleted(Bitmap bitmap) {
                            image.setImageBitmap(bitmap);
                            imageFirst.setImageBitmap(bitmap);
                        }
                    });
                } else {
                    if (i != 0) {
                        bitmapUtil.display(image, viewPagerDataList.get(i).getImg());
                    }
                }
                imageList.add(image);
            }
            final ImageView imageLast = new ImageView(mActivity);
            imageLast.setTag(0);// 实际的位置
            imageLast.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLast.setImageResource(R.color.gray_bg);
            bitmapUtil.display(imageLast, viewPagerDataList.get(0)
                    .getImg(), new BitmapUtil.BitmapLoadCallBack() {
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
                ImageView image = new ImageView(mActivity);
                image.setTag(i);// 实际的位置
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                bitmapUtil.display(image, viewPagerDataList.get(i).getImg());
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
        viewPager.setVisibility(View.VISIBLE);
        llPointGroup.setVisibility(View.VISIBLE);
        llPointGroup.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                -2, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 20;
        if (viewPagerDataList.size() >= 2) {
            for (int i = 0; i < viewPagerDataList.size(); i++) {
                ImageView point = new ImageView(mActivity);
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
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new HomeFragmentPagerAdapter(
                    mActivity, viewPagerDataList, imageList);
            viewPager.setAdapter(viewPagerAdapter);
        } else {
            viewPagerAdapter.setDataList(viewPagerDataList, imageList);
            viewPagerAdapter.notifyDataSetChanged();
        }
        if (viewPagerDataList.size() >= 2) {
            // 自动循环
            lastPosition = 1;
            viewPager.setCurrentItem(1, true);// 显示第一个图片。
            mHandler.sendEmptyMessageDelayed(CHANGVIEWPAGERITEM, 6000);
        } else {
            lastPosition = 0;
            viewPager.setCurrentItem(0, true);// 显示第一个图片。
        }
    }



    /**
     * 显示套餐内容数据
     *
     * @param homeDataBean
     */
    private void showListView(List<HomeDataBean.CenterEntity> homeDataBean) {
        HomeListAdapter adapter = new HomeListAdapter(mActivity, homeDataBean);
        lvHome.setAdapter(adapter);
    }

    // 上一个选中的页面的下标
    private int lastPosition = 0;
    private HomeFragmentPagerAdapter viewPagerAdapter;
    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            int curPosition = viewPager.getCurrentItem();
            if (curPosition == imageList.size() - 1) {
                viewPager.setCurrentItem(1, false);// 为了无限循环
            } else if (curPosition == 0) {
                viewPager.setCurrentItem(imageList.size() - 2, false);
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
