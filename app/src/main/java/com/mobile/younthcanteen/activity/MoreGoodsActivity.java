package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.adapter.MoreGoodsPagerAdapter;
import com.mobile.younthcanteen.bean.GoodsTypeBean;
import com.mobile.younthcanteen.fragment.MoreGoodsFragment;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/4/4 0004 23:25
 */


public class MoreGoodsActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    android.support.design.widget.TabLayout tablayout;
    private List<String> titleList = new ArrayList<String>();
    private ArrayList<Fragment> listFragmentsa = new ArrayList<Fragment>();
    private int tabIndex;
    private TabLayout tabLayout;

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
        if (goodsTypeBeanList != null && goodsTypeBeanList.size() > 0) {
            for (int i = 0, length = goodsTypeBeanList.size(); i < length; i++) {
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
            viewpager.setVisibility(View.VISIBLE);
            tabLayout = (TabLayout) findViewById(R.id.tablayout);
            tabLayout.setupWithViewPager(viewpager);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            Intent intent = getIntent();
            if (intent != null) {
                tabIndex = intent.getIntExtra("tabIndex", 0);
                viewpager.setCurrentItem(tabIndex, false);
            }
        }
    }


}
