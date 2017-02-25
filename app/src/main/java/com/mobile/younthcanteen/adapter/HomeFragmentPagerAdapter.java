package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.mobile.younthcanteen.bean.HomeDataBean;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.List;

public class HomeFragmentPagerAdapter extends PagerAdapter {

	private List<HomeDataBean.TopEntity> viewPagerDataList;
	private Context context;
	private List<ImageView> imageList;
	public HomeFragmentPagerAdapter(Context context, List<HomeDataBean.TopEntity> viewPagerDataList, List<ImageView> imageList) {
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
		view.setOnClickListener(new MyOnClickListener((Integer) view.getTag()));
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		container.addView(view, params);
		// 第二件事：返回一个和view 有关系 的对象。
		return view;
	}

	private class MyOnClickListener implements OnClickListener{
		private int curPosition = 0;
		public MyOnClickListener(int position) {
			curPosition = position;
		}

		@Override
		public void onClick(View v) {
			HomeDataBean.TopEntity homePageViewPagerItemBean = viewPagerDataList.get(curPosition);
			if(homePageViewPagerItemBean != null){
				ToastUtils.showShortToast("你点击了第" + (curPosition + 1) + "张图片");
			}
		}

	}

	public void setDataList(List<HomeDataBean.TopEntity> viewPagerDataList,
							List<ImageView> imageList){
		this.viewPagerDataList = viewPagerDataList;
		this.imageList = imageList;
	}
}
