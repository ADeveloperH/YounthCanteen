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


    /***
     *  添加商品到购物车
     * @param bean
     */
    public static void addGoodsToCart(ShoppingCartItemBean bean) {
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            int index = -1;
            if ("0".equals(bean.getType())) {
                //单个商品。判断Proid可以确定是否已存在购物车
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

            }
        } else {
            //购物车中还没有商品.直接添加进集合
            shoppingCartList.add(bean);
        }
    }


    /**
     * 添加套餐到购物车
     * @param bean
     */
    public static void addPackageToCart(ShoppingCartItemBean bean) {
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            int index = -1;
            if ("1".equals(bean.getType())) {
                //套餐。还需判断materia的所有Proid
                for (int i = 0, length = shoppingCartList.size(); i < length; i++) {
                    ShoppingCartItemBean itemInList = shoppingCartList.get(i);
                    if (itemInList.getProid().equals(bean.getProid())) {
                        //购物车中已经有该套餐了.需要判断添加的materia是否完全一致
                        List<ShoppingCartItemBean.MateriaBean> materiaListInItem =
                                itemInList.getMaterial();
                        List<ShoppingCartItemBean.MateriaBean> materiaListInBean =
                                bean.getMaterial();
                        if (isMateriaListSame(materiaListInItem, materiaListInBean)) {
                            index = i;
                        }
                    }
                }

                if (index != -1) {
                    //购物车中已经添加了该套餐。将其数量加一。
                    int count = Integer.parseInt(shoppingCartList.get(index).getCount());
                    shoppingCartList.get(index).setCount((count + 1) + "");
                } else {
                    shoppingCartList.add(bean);
                }
            }
        } else {
            //购物车中还没有商品.直接添加进集合
            shoppingCartList.add(bean);
        }
    }

    /**
     * 将套餐bean的数量减1.
     * @param bean
     */
    public static void reducePackageToCart(ShoppingCartItemBean bean) {
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            int index = -1;
            if ("1".equals(bean.getType())) {
                //套餐。还需判断materia的所有Proid
                for (int i = 0, length = shoppingCartList.size(); i < length; i++) {
                    ShoppingCartItemBean itemInList = shoppingCartList.get(i);
                    if (itemInList.getProid().equals(bean.getProid())) {
                        //购物车中已经有该套餐了.需要判断添加的materia是否完全一致
                        List<ShoppingCartItemBean.MateriaBean> materiaListInItem =
                                itemInList.getMaterial();
                        List<ShoppingCartItemBean.MateriaBean> materiaListInBean =
                                bean.getMaterial();
                        if (isMateriaListSame(materiaListInItem, materiaListInBean)) {
                            index = i;
                        }
                    }
                }
                if (index != -1) {
                    //购物车中已经有该套餐。将其数量减一。
                    int count = Integer.parseInt(shoppingCartList.get(index).getCount());
                    shoppingCartList.get(index).setCount((count - 1) + "");
                }
            }
        }
    }

    /**
     * 判断两个集合的materiaList是否相同。
     *
     * @param materiaList1
     * @param materiaList2
     * @return
     */
    private static boolean isMateriaListSame(List<ShoppingCartItemBean.MateriaBean> materiaList1,
                                             List<ShoppingCartItemBean.MateriaBean> materiaList2) {
        if (materiaList1 == null || materiaList1.size() == 0 ||
                materiaList2 == null || materiaList2.size() == 0) {
            return false;
        }
        if (materiaList2.size() != materiaList1.size()) {
            return false;
        }

        List<String> proidList1 = new ArrayList<String>();
        for (int i = 0; i < materiaList1.size(); i++) {
            proidList1.add(materiaList1.get(i).getProid());
        }

        List<String> proidList2 = new ArrayList<String>();
        for (int i = 0; i < materiaList2.size(); i++) {
            proidList2.add(materiaList2.get(i).getProid());
        }

        boolean isSame = true;
        for (int i = 0; i < proidList1.size(); i++) {
            if (!proidList2.contains(proidList1.get(i))) {
                isSame = false;
                break;
            }
        }
        return isSame;
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
     * 获取购物车中该商品对象。用于回显数据
     *
     * @param proid
     */
    public static ShoppingCartItemBean getShopping(String proid) {
        for (int i = 0, length = shoppingCartList.size(); i < length; i++) {
            ShoppingCartItemBean itemInList = shoppingCartList.get(i);
            if (itemInList.getProid().equals(proid)) {
                return itemInList;
            }
        }
        return null;
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
     *
     * @return
     */
    public static int getTotalPrice() {
        if (shoppingCartList == null || shoppingCartList.size() <= 0) {
            return 0;
        } else {
            int totalPrice = 0;
            for (int i = 0, length = shoppingCartList.size(); i < length; i++) {
                ShoppingCartItemBean bean = shoppingCartList.get(i);
                totalPrice += (Integer.parseInt(bean.getPrice()) * Integer.parseInt(bean.getCount()));
            }
            return totalPrice;
        }

    }

    /**
     * 购物车是否为空
     *
     * @return
     */
    public static boolean shoppingCartIsNull() {
        return shoppingCartList == null || shoppingCartList.size() == 0;
    }

    /**
     * 获取购物车商品数量
     *
     * @return
     */
    public static int getCartCount() {
        if (shoppingCartList == null || shoppingCartList.size() <= 0) {
            return 0;
        } else {
            int totalCount = 0;
            for (int i = 0, length = shoppingCartList.size(); i < length; i++) {
                ShoppingCartItemBean bean = shoppingCartList.get(i);
                totalCount += (Integer.parseInt(bean.getCount()));
            }
            return totalCount;
        }
    }


    /**
     * 清空购物车
     */
    public static void clearCart() {
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            shoppingCartList.clear();
        }

    }
}
