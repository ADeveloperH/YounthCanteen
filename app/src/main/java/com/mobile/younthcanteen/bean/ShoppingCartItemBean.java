package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/3/8 0008 22:10
 */

public class ShoppingCartItemBean {
    /**
     * 商品类别
     * 0：单个商品
     * 1：套餐
     */
    private String type;
    private String name;//商品名称
    private String price;//商品单价
    private String imgUrl;//商品图片地址
    private String proid;//商品的proid
    private String count;//商品的数量
    private List<MateriaBean> material;//套餐商品列表

    public class MateriaBean {
        private String name;//商品名称
        private String proid;//商品的proid

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProid() {
            return proid;
        }

        public void setProid(String proid) {
            this.proid = proid;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<MateriaBean> getMaterial() {
        return material;
    }

    public void setMaterial(List<MateriaBean> material) {
        this.material = material;
    }
}
