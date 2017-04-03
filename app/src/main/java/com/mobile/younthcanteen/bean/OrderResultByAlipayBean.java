package com.mobile.younthcanteen.bean;

/**
 * author：hj
 * time: 2017/3/27 0027 21:34
 */


public class OrderResultByAlipayBean {

    /**
     * returnCode : 0
     * returnMessage : success
     * signOrder : app_id=2015031600036475&biz_content=%7b%22body%22%3a%22%e7%83%a7%e9%9d%92%e8%8f%9c%3b%22%2c%22subject%22%3a%22%e9%9d%92%e6%98%a5%e9%a3%9f%e5%a0%82%e8%ae%a2%e5%8d%95%22%2c%22out_trade_no%22%3a%22170327213206000000375058%22%2c%22timeout_express%22%3a%2230m%22%2c%22total_amount%22%3a0.01%2c%22product_code%22%3a%22QUICK_MSECURITY_PAY%22%7d&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2f116.255.130.49%3a8732%2freceiveAliPay&sign_type=RSA2&timestamp=2017-03-27+21%3a32%3a06&version=1.0&sign=XE9RX5mteoKZDItFqF%2btzZpGHyr5qQvZoDlTHU0zmMWV1YpxbQTmWuQpSGcVmz4Aa1bgnYbPxUi8hGWnz%2bkPjNfiWoLVc%2f5kLDo%2fbtyBcqjM9bmENd%2bUNKsVzuLAxGLWFnF%2f5mYtzQIhGZQXvqx0kJwJJ6dhfxkuh6YFOgM2klo9sic9fYD7IJBXfTZe0E49sfDIYl4jKexDWufn9ziX%2ft95rLf%2f1BRSlDp9gC5Zn6LVYTH9CdTtsaQA%2bSKvjPYjHUTq5d8WTNaSn2fGYukJjFZvhDv3UcqOqgByzP8iLTaIr5MesGVyosISYLaEOGhgJlIclIENViWCrjB1m%2bvAAA%3d%3d
     */

    private String returnCode;
    private String returnMessage;
    private String signOrder;

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

    public String getSignOrder() {
        return signOrder;
    }

    public void setSignOrder(String signOrder) {
        this.signOrder = signOrder;
    }
}
