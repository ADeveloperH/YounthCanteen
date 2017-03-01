package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/3/1 0001 16:54
 */

public class GoodsDetailInfoBean {

    /**
     * combos : []
     * describe :
     * name : 农夫山泉
     * price : 2
     * proid : 14
     * url : ["http://116.255.130.49:10001/pros/nongfushanquan.jpg"]
     */

    private ResultsEntity results;
    /**
     * results : {"combos":[],"describe":"","name":"农夫山泉","price":2,"proid":14,"url":["http://116.255.130.49:10001/pros/nongfushanquan.jpg"]}
     * returnCode : 0
     * returnMessage : 成功！
     */

    private String returnCode;
    private String returnMessage;

    public ResultsEntity getResults() {
        return results;
    }

    public void setResults(ResultsEntity results) {
        this.results = results;
    }

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

    public static class ResultsEntity {
        private String describe;
        private String name;
        private String price;
        private String proid;
        private List<?> combos;
        private List<String> url;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
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

        public List<?> getCombos() {
            return combos;
        }

        public void setCombos(List<?> combos) {
            this.combos = combos;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }
    }
}
