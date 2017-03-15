package com.mobile.younthcanteen.bean;

/**
 * author：hj
 * time: 2017/3/15 0015 21:05
 */


public class CommitOrderResult {

    /**
     * orderno : 170315210448000000265863
     * returnCode : 0
     * returnMessage : 订单提交成功
     */

    private String orderno;
    private String returnCode;
    private String returnMessage;

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
