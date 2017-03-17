package com.mobile.younthcanteen.bean;

/**
 * author：hj
 * time: 2017/3/15 0015 21:05
 */


public class CommitOrderResult {

    /**
     * money : 116
     * orderno : 170316231706000000020595
     * returnCode : 0
     * returnMessage : 订单提交成功
     */

    private String money;
    private String orderno;
    private String returnCode;
    private String returnMessage;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
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
}
