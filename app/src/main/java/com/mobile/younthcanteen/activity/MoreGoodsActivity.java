package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.MoreGoodsPagerAdapter;
import com.mobile.younthcanteen.bean.GoodsTypeBean;
import com.mobile.younthcanteen.fragment.MoreGoodsFragment;
import com.mobile.younthcanteen.ui.TabPageIndicator;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/4/4 0004 23:25
 */


public class MoreGoodsActivity extends BaseActivity {
    @BindView(R.id.tab_page_indicator)
    TabPageIndicator tabPageIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private List<String> titleList = new ArrayList<String>();
    private ArrayList<Fragment> listFragmentsa = new ArrayList<Fragment>();
    private int tabIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBackVisible(true);
        setTitle("更多套餐");
        setContentView(R.layout.activity_more_goods_layout);
        ButterKnife.bind(this);

        initFragment();
    }

    private void initFragment() {
        List<GoodsTypeBean> goodsTypeBeanList = SharedPreferencesUtil.getGoodsTypeFromSP();
        GoodsTypeBean bean;
        if (goodsTypeBeanList != null && goodsTypeBeanList.size() >0) {
            for (int i = 0,length= goodsTypeBeanList.size(); i < length; i++) {
                bean = goodsTypeBeanList.get(i);
                titleList.add(bean.getTypename());
                MoreGoodsFragment fragment = new MoreGoodsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("typeid", bean.getTypeid());
                fragment.setArguments(bundle);
                listFragmentsa.add(fragment);
            }

            MoreGoodsPagerAdapter mAdatpter = new MoreGoodsPagerAdapter(getSupportFragmentManager(),
                    listFragmentsa, titleList);
            viewpager.setAdapter(mAdatpter);
            tabPageIndicator.setViewPager(viewpager);
            tabPageIndicator.setVisibility(View.VISIBLE);
            tabPageIndicator.setIndicatorHeight(UIUtils.dip2px(2));
            tabPageIndicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_NOSAME);
            tabPageIndicator.requestLayout();
//        tabPageIndicator.setBackgroundResource(R.drawable.viewpager_tab_indicator);
            viewpager.setVisibility(View.VISIBLE);

            Intent intent = getIntent();
            if (intent != null) {
                tabIndex = intent.getIntExtra("tabIndex", 0);
                viewpager.setCurrentItem(tabIndex,false);
            }
        }
    }


}
