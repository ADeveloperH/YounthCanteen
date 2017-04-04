package com.mobile.younthcanteen.bean;

import java.util.List;

/**
 * author：hj
 * time: 2017/4/4 0004 12:41
 */


public class ConsumeDetailBean {

    /**
     * id : 2
     * results : [{"balance":988,"money":-12,"remark":"170323213329000000106680","time":"2017-3-23 21:33:47","type":"消费"},{"balance":1000,"money":1000,"remark":"系统充值","time":"2017-3-21 9:06:24","type":"充值"}]
     * returnCode : 0
     * returnMessage : 获取成功
     */

    private String id;
    private String returnCode;
    private String returnMessage;
    /**
     * balance : 988
     * money : -12
     * remark : 170323213329000000106680
     * time : 2017-3-23 21:33:47
     * type : 消费
     */

    private List<ResultsEntity> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        private String balance;
        private String money;
        private String remark;
        private String time;
        private String type;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
