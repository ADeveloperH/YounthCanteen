package com.mobile.younthcanteen.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.ShoppingCartItemBean;
import com.mobile.younthcanteen.util.BitmapUtil;
import com.mobile.younthcanteen.util.DialogUtil;
import com.mobile.younthcanteen.util.FileUtil;
import com.mobile.younthcanteen.util.ShoppingCartUtil;
import com.mobile.younthcanteen.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：hj
 * time: 2017/3/10 0010 21:16
 */


public class ShoppingCartListAdapter extends BaseAdapter {
    private CartListChangeListener cartListChangeListener;
    private Context context;
    private List<ShoppingCartItemBean> shoppingCartList;
    private BitmapUtil bitmapUtil;
    private boolean isShowDelete = false;

    public ShoppingCartListAdapter(Context context, List<ShoppingCartItemBean> shoppingCartList,
                                   CartListChangeListener cartListChangeListener) {
        this.shoppingCartList = shoppingCartList;
        this.context = context;
        this.cartListChangeListener = cartListChangeListener;
        bitmapUtil = new BitmapUtil(context, FileUtil.getCachePath(context, "/bitmapcache"),
                new ColorDrawable(context.getResources().getColor(R.color.gray_bg)));
    }

    @Override
    public int getCount() {
        return shoppingCartList == null ? 0 : shoppingCartList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = UIUtils.inflate(R.layout.item_cartlist_layout);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ShoppingCartItemBean bean = shoppingCartList.get(position);
        bitmapUtil.display(viewHolder.iv, bean.getImgUrl());
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvPrice.setText("￥" + bean.getPrice() + "元");
        viewHolder.tvCartNum.setText(bean.getCount());
        if (isShowDelete) {
            //显示删除
            viewHolder.llAddSubtract.setVisibility(View.GONE);
            viewHolder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.llAddSubtract.setVisibility(View.VISIBLE);
            viewHolder.ivDelete.setVisibility(View.GONE);
        }

        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.getSimpleDialog(context, "提示", "是否删除该商品？", "确认", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //移除该商品
                        ShoppingCartUtil.removeShopping(bean.getProid());
                        if (cartListChangeListener != null) {
                            cartListChangeListener.cartListChanged();
                        }
                        notifyDataSetChanged();
                    }
                },null,true).show();
            }
        });

        viewHolder.ivCartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int goodsCount = Integer.parseInt(bean.getCount());
                goodsCount++;
                viewHolder.tvCartNum.setText(goodsCount + "");
                if ("0".equals(bean.getType())) {
                    bean.setCount(goodsCount + "");
                    ShoppingCartUtil.addGoodsToCart(bean);
                } else {
                    ShoppingCartUtil.addPackageToCart(bean);
                }
                if (cartListChangeListener != null) {
                    cartListChangeListener.cartListChanged();
                }
            }
        });

        viewHolder.ivCartSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int goodsCount = Integer.parseInt(bean.getCount());
                goodsCount--;
                if (goodsCount > 0) {
                    viewHolder.tvCartNum.setText(goodsCount + "");
                    if ("0".equals(bean.getType())) {
                        bean.setCount(goodsCount + "");
                        ShoppingCartUtil.addGoodsToCart(bean);
                    } else {
                        //套餐
                        ShoppingCartUtil.reducePackageToCart(bean);
                    }
                    if (cartListChangeListener != null) {
                        cartListChangeListener.cartListChanged();
                    }
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_cart_subtract)
        ImageView ivCartSubtract;
        @BindView(R.id.tv_cart_num)
        TextView tvCartNum;
        @BindView(R.id.iv_cart_add)
        ImageView ivCartAdd;
        @BindView(R.id.ll_add_subtract)
        LinearLayout llAddSubtract;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public void setShoppingCartList(List<ShoppingCartItemBean> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    public void setShowDelete(boolean showDelete) {
        isShowDelete = showDelete;
    }

    public interface CartListChangeListener{
        void cartListChanged();
    }
}
