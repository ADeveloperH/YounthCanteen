package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.util.List;

public class GoodsInfoPagerAdapter extends PagerAdapter {

	private List<String> viewPagerDataList;
	private Context context;
	private List<ImageView> imageList;
	public GoodsInfoPagerAdapter(Context context, List<String> viewPagerDataList, List<ImageView> imageList) {
		this.context = context;
		this.viewPagerDataList = viewPagerDataList;
		this.imageList = imageList;
	}

	@Override
	public int getCount() {
		return imageList == null ? 0 :imageList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
//		container.removeView((View)object);
		if(position < imageList.size()){
			((ViewPager)container).removeView(imageList.get(position));
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 第一件事：根据position 获得一个对应的view ，并将view，添加至 container
		View view = imageList.get(position);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		container.addView(view, params);
		// 第二件事：返回一个和view 有关系 的对象。
		return view;
	}

}
