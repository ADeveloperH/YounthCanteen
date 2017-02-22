package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/2/22 0022 22:51
 */

public class HomeDataBean {

    /**
     * center : [{"pros":[{"name":"一荤一素米饭套餐","price":10,"proid":1,"url":"http://116.255.130.49:10001/pros/001.jpg"},{"name":"一荤二素米饭套餐","price":12,"proid":2,"url":"http://116.255.130.49:10001/pros/002.jpg"},{"name":"一荤三素米饭套餐","price":14,"proid":3,"url":"http://116.255.130.49:10001/pros/003.jpg"},{"name":"二荤二素米饭套餐","price":20,"proid":4,"url":"http://116.255.130.49:10001/pros/004.jpg"}],"typeid":1,"typename":"套餐","url":""},{"pros":[{"name":"鱼香肉丝","price":20,"proid":5,"url":"http://116.255.130.49:10001/pros/006.jpg"},{"name":"烧青菜","price":12,"proid":6,"url":"http://116.255.130.49:10001/pros/007.jpg"},{"name":"烧茄子","price":16,"proid":7,"url":"http://116.255.130.49:10001/pros/008.jpg"},{"name":"宫保鸡丁","price":24,"proid":8,"url":"http://116.255.130.49:10001/pros/009.jpg"}],"typeid":2,"typename":"炒菜","url":""},{"pros":[{"name":"鸡蛋面","price":8,"proid":9,"url":"http://116.255.130.49:10001/pros/鸡蛋面.jpg"},{"name":"肉丝面","price":10,"proid":10,"url":"http://116.255.130.49:10001/pros/肉丝面.jpg"},{"name":"清汤面","price":7,"proid":11,"url":"http://116.255.130.49:10001/pros/清汤面.jpg"},{"name":"馒头","price":1,"proid":12,"url":"http://116.255.130.49:10001/pros/馒头.jpg"}],"typeid":3,"typename":"面食","url":""},{"pros":[{"name":"花花牛","price":3,"proid":13,"url":"http://116.255.130.49:10001/pros/花花牛.jpg"},{"name":"农夫山泉","price":2,"proid":14,"url":"http://116.255.130.49:10001/pros/农夫山泉.jpg"},{"name":"雪花啤酒","price":6,"proid":15,"url":"http://116.255.130.49:10001/pros/雪花啤酒.jpg"},{"name":"江小白","price":30,"proid":16,"url":"http://116.255.130.49:10001/pros/江小白.jpg"}],"typeid":4,"typename":"饮料","url":""}]
     * returnCode : 1
     * returnMessage : 获取成功
     * top : []
     */

    private String returnCode;
    private String returnMessage;
    /**
     * pros : [{"name":"一荤一素米饭套餐","price":10,"proid":1,"url":"http://116.255.130.49:10001/pros/001.jpg"},{"name":"一荤二素米饭套餐","price":12,"proid":2,"url":"http://116.255.130.49:10001/pros/002.jpg"},{"name":"一荤三素米饭套餐","price":14,"proid":3,"url":"http://116.255.130.49:10001/pros/003.jpg"},{"name":"二荤二素米饭套餐","price":20,"proid":4,"url":"http://116.255.130.49:10001/pros/004.jpg"}]
     * typeid : 1
     * typename : 套餐
     * url :
     */

    private List<CenterEntity> center;
    private List<?> top;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public List<CenterEntity> getCenter() {
        return center;
    }

    public void setCenter(List<CenterEntity> center) {
        this.center = center;
    }

    public List<?> getTop() {
        return top;
    }

    public void setTop(List<?> top) {
        this.top = top;
    }

    public static class CenterEntity {
        private String typeid;
        private String typename;
        private String url;
        /**
         * name : 一荤一素米饭套餐
         * price : 10
         * proid : 1
         * url : http://116.255.130.49:10001/pros/001.jpg
         */

        private List<ProsEntity> pros;

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<ProsEntity> getPros() {
            return pros;
        }

        public void setPros(List<ProsEntity> pros) {
            this.pros = pros;
        }

        public static class ProsEntity {
            private String name;
            private String price;
            private String proid;
            private String url;

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

            public String getProid() {
                return proid;
            }

            public void setProid(String proid) {
                this.proid = proid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
