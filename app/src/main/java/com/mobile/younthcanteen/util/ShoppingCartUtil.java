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
     *
     * @param bean
     */
    public static void addToCart(ShoppingCartItemBean bean) {
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            int index = -1;
            for (int i = 0, length = shoppingCartList.size(); i < length; i++) {
                ShoppingCartItemBean itemInList = shoppingCartList.get(i);
                if (itemInList.getProid().equals(bean.getProid())) {
                    //购物车中已经有该商品了.
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                //购物车中已经有了
                shoppingCartList.set(index, bean);
            } else {
                shoppingCartList.add(bean);
            }
        } else {
            //购物车中还没有商品.直接添加进集合
            shoppingCartList.add(bean);
        }

    }

    /**
     * 从购物车集合中移除该商品
     *
     * @param proid 商品id
     */
    public static void removeShopping(String proid) {
        for (int i = 0, length = shoppingCartList.size(); i < length; i++) {
            ShoppingCartItemBean itemInList = shoppingCartList.get(i);
            if (itemInList.getProid().equals(proid)) {
                shoppingCartList.remove(i);
                break;
            }
        }
    }

    /**
     * 获取购物车中该商品的数量。用于回显数据
     *
     * @param proid
     */
    public static int getShopping(String proid) {
        for (int i = 0, length = shoppingCartList.size(); i < length; i++) {
            ShoppingCartItemBean itemInList = shoppingCartList.get(i);
            if (itemInList.getProid().equals(proid)) {
                return Integer.parseInt(itemInList.getCount());
            }
        }
        return 0;
    }

    /**
     * 获取所有的商品列表
     *
     * @return
     */
    public static List<ShoppingCartItemBean> getAllShoppingList() {
        return shoppingCartList;
    }


    /**
     * 获取总价格
     * @return
     */
    public static int getTotalPrice() {
        if (shoppingCartList == null || shoppingCartList.size() <= 0) {
            return 0;
        } else {
            int totalPrice = 0;
            for (int i = 0,length = shoppingCartList.size(); i < length; i++) {
                ShoppingCartItemBean bean = shoppingCartList.get(i);
                totalPrice += (Integer.parseInt(bean.getPrice()) * Integer.parseInt(bean.getCount()));
            }
            return totalPrice;
        }

    }

    /**
     * 购物车是否为空
     * @return
     */
    public static boolean shoppingCartIsNull() {
        return shoppingCartList == null || shoppingCartList.size() == 0;
    }
}
