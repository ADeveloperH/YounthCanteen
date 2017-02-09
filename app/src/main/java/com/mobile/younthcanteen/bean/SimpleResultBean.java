package com.mobile.younthcanteen.bean;

/**
 * author：hj
 * time: 2017/2/9 0009 21:11
 */

public class SimpleResultBean {

    /**
     * returnCode : -1
     * returnMessage : 必须声明标量变量 "@userid"。
     */

    private String returnCode;
    private String returnMessage;

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
