package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/4/5 0005 12:55
 */


public class MoreGoodsResultBean {

    /**
     * center : [{"pros":[{"intro":"简介","name":"一荤一素米饭套餐","price":10,"proid":1,"url":"http://116.255.130.49:10001/pros/001.jpg"},{"intro":"简介","name":"一荤二素米饭套餐","price":12,"proid":2,"url":"http://116.255.130.49:10001/pros/002.jpg"},{"intro":"简介","name":"一荤三素米饭套餐","price":14,"proid":3,"url":"http://116.255.130.49:10001/pros/003.jpg"},{"intro":"简介","name":"二荤二素米饭套餐","price":20,"proid":4,"url":"http://116.255.130.49:10001/pros/004.jpg"}],"typeid":1,"typename":"套餐","url":""}]
     * returnCode : 0
     * returnMessage : 获取成功
     */

    private String returnCode;
    private String returnMessage;
    /**
     * pros : [{"intro":"简介","name":"一荤一素米饭套餐","price":10,"proid":1,"url":"http://116.255.130.49:10001/pros/001.jpg"},{"intro":"简介","name":"一荤二素米饭套餐","price":12,"proid":2,"url":"http://116.255.130.49:10001/pros/002.jpg"},{"intro":"简介","name":"一荤三素米饭套餐","price":14,"proid":3,"url":"http://116.255.130.49:10001/pros/003.jpg"},{"intro":"简介","name":"二荤二素米饭套餐","price":20,"proid":4,"url":"http://116.255.130.49:10001/pros/004.jpg"}]
     * typeid : 1
     * typename : 套餐
     * url :
     */

    private List<CenterEntity> center;

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

    public static class CenterEntity {
        private String typeid;
        private String typename;
        private String url;
        /**
         * intro : 简介
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
            private String intro;
            private String name;
            private String price;
            private String proid;
            private String url;

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
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
