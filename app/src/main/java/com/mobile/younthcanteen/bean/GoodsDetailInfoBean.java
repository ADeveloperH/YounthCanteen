package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/3/1 0001 16:54
 */

public class GoodsDetailInfoBean {

    /**
     * choices : ["不辣","不辣","不辣","不辣"]
     * combos : []
     * count : 0
     * describe :     “消费不满意, 我们就免单”计划的适用范围：
     提供产品或服务与承诺的产品或服务内容严重不符经合法有效的证据证明存在严重质量问题，或有违反《中华人民共和国食品安全法》、《中华人民共和国食品安全法实施条例》、《餐饮服务食品安全监督管理办法》、《中华人民共和国消费者权益保护法》的情况
     * intro : 简介：鱼香肉丝，“消费不满意, 我们就免单”
     * name : 鱼香肉丝
     * price : 20
     * proid : 5
     * url : ["http://116.255.130.49:10001/pros/2017413162954454120.jpeg"]
     */

    private ResultsEntity results;
    /**
     * results : {"choices":["不辣","不辣","不辣","不辣"],"combos":[],"count":0,"describe":"    \u201c消费不满意, 我们就免单\u201d计划的适用范围：\n    提供产品或服务与承诺的产品或服务内容严重不符经合法有效的证据证明存在严重质量问题，或有违反《中华人民共和国食品安全法》、《中华人民共和国食品安全法实施条例》、《餐饮服务食品安全监督管理办法》、《中华人民共和国消费者权益保护法》的情况","intro":"简介：鱼香肉丝，\u201c消费不满意, 我们就免单\u201d","name":"鱼香肉丝","price":20,"proid":5,"url":["http://116.255.130.49:10001/pros/2017413162954454120.jpeg"]}
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
        private String count;
        private String describe;
        private String intro;
        private String name;
        private String price;
        private String proid;
        private List<String> choices;
        private List<?> combos;
        private List<String> url;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

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

        public List<String> getChoices() {
            return choices;
        }

        public void setChoices(List<String> choices) {
            this.choices = choices;
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
