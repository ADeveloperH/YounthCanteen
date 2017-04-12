package com.mobile.younthcanteen.bean;

/**
 * author：hj
 * time: 2017/4/12 0012 09:20
 */


public class RecommendBean {

    /**
     * returnCode : 0
     * returnMessage : 获取成功！
     * tjcode : 好东西要和最亲密的人分享，欢迎访问www.baidu.com下载青春食堂APP，注册时请填写好友推荐码：I5RSC5
     */

    private String returnCode;
    private String returnMessage;
    private String tjcode;

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

    public String getTjcode() {
        return tjcode;
    }

    public void setTjcode(String tjcode) {
        this.tjcode = tjcode;
    }
}
