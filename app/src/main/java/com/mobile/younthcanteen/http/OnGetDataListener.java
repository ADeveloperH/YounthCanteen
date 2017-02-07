package com.mobile.younthcanteen.http;

/**
 * 获取数据监听
 * Created by tjj on 2015/4/15.
 */
public interface OnGetDataListener {

    public void onGetDataStart();
    public void onGetDataSuccess(String url,String content);
    public void onGetDataFailure();
    public void onGetDataFinish();

}
