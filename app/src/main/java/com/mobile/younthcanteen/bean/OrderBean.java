package com.mobile.younthcanteen.bean;

import android.text.TextUtils;

/**
 * author：hj
 * time: 2017/3/14 0014 22:39
 */


public class OrderBean {
    private String token;
    private String addrid;
    private String pros;
    private String remark;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddrid() {
        return addrid;
    }

    public void setAddrid(String addrid) {
        this.addrid = addrid;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        if (!TextUtils.isEmpty(token)) {
            stringBuilder.append(" \"token\": ").append(token).append(",");
        }
        if (!TextUtils.isEmpty(addrid)) {
            stringBuilder.append(" \"addrid\": ").append(addrid).append(",");
        }
        if (!TextUtils.isEmpty(pros)) {
            stringBuilder.append(" \"pros\": ").append(pros).append(",");
        }
        if (!TextUtils.isEmpty(remark)) {
            stringBuilder.append(" \"remark\": ").append(remark).append(",");
        }
        if (stringBuilder.lastIndexOf(",") != -1) {
            //删除多加的一个,
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();

    }
}
