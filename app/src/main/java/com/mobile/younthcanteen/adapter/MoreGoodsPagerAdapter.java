package com.mobile.younthcanteen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author：hj
 * time: 2016/12/14 0014 09:59
 */

public class MoreGoodsPagerAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    private ArrayList<Fragment> listFragmentsa;


    public MoreGoodsPagerAdapter(FragmentManager fm, ArrayList<Fragment> listFragmentsa, List<String> titles) {
        super(fm);
        this.listFragmentsa = listFragmentsa;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragmentsa.get(position);
    }

    @Override
    public int getCount() {
        return listFragmentsa == null ? 0 : listFragmentsa.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }


    public View getTabView(int position){
        View view = UIUtils.inflate(R.layout.tab_item);
        TextView tv= (TextView) view.findViewById(R.id.textView);
        tv.setText(titles.get(position));
//        ImageView img = (ImageView) view.findViewById(R.id.imageView);
//        img.setImageResource(imageResId[position]);
        return view;
    }
}
