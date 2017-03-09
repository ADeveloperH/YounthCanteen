package com.mobile.younthcanteen.util;

import com.mobile.younthcanteen.bean.ShoppingCartItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author：hj
 * time: 2017/3/8 0008 22:05
 */

public class ShoppingCartUtil {

    private static List<ShoppingCartItemBean> shoppingCartList = new ArrayList<ShoppingCartItemBean>();


    /**
     * 添加到购物车
     * @param bean
     */
    public static void addToCart(ShoppingCartItemBean bean) {
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            for (int i = 0; i < shoppingCartList.size(); i++) {
                ShoppingCartItemBean itemInList = shoppingCartList.get(i);
                if (itemInList.getProid().equals(bean.getProid())) {
                    //购物车中已经有该商品了.
                    shoppingCartList.set(i, bean);
                    break;
                } else {
                    //购物车中还没有该商品.直接添加进集合
                    shoppingCartList.add(bean);
                }
            }
        } else {
            //购物车中还没有商品.直接添加进集合
            shoppingCartList.add(bean);
        }

    }

    /**
     * 从购物车集合中移除该商品
     * @param proid 商品id
     */
    public static void removeShopping(String proid) {
        for (int i = 0; i < shoppingCartList.size(); i++) {
            ShoppingCartItemBean itemInList = shoppingCartList.get(i);
            if (itemInList.getProid().equals(proid)) {
                shoppingCartList.remove(i);
                break;
            }
        }
    }

    /**
     * 获取购物车中该商品的数量。用于回显数据
     * @param proid
     */
    public static int getShopping(String proid) {
        for (int i = 0; i < shoppingCartList.size(); i++) {
            ShoppingCartItemBean itemInList = shoppingCartList.get(i);
            if (itemInList.getProid().equals(proid)) {
                return Integer.parseInt(itemInList.getCount());
            }
        }
        return 0;
    }

    /**
     * 获取所有的商品列表
     * @return
     */
    public static List<ShoppingCartItemBean> getAllShoppingList() {
        return shoppingCartList;
    }


}
