package com.mobile.younthcanteen.http;

import android.content.Context;
import android.text.TextUtils;

public class GetHttpsDataUtil {

	private Context context;//上下文

    private IHttpsResponseListener listener = null;
	private static final int TIMEOUT_READ_DEFAULT = 30;
	private static final int TIMEOUT_CONNECTION_DEFAULT = 30;

    public void setOnGetDataListener(IHttpsResponseListener listener) {
        this.listener = listener;
    }

    /**
     * @param context
     */
	public GetHttpsDataUtil(Context context) {
		super();
		this.context = context;
	}


	/**
	 *
	 * 查询客户端详单的接口
	 * @param phoneNumber 手机号
	 * @param recordType 查询类型
	 *  01套餐及固定费用详单 	02通话详单	 03短/彩信详单	 04上网详单
	 *  05增值业务扣费记录 		06代收费业务扣费记录 		07其他费用扣费记录
	 * @param billMonth 帐单月份，数据格式：YYYYMM，不能大于当前月份
	 * @param searchValue  搜索字符串 非必填项
	 * @param start 第几页
	 * @param length 每页显示条数
	 * @param isShowRemind 是否显示正在加载中
	 * @param dialogMsg 正在加载的文字信息
	 */
	public void queryDetailRecord(String phoneNumber,String recordType,
								  String billMonth,String searchValue,int start,int length,
								  boolean isShowRemind,String dialogMsg,String moduleId){
		RequestParams requestParamsMap = new RequestParams();
		requestParamsMap.put("phoneNumber", phoneNumber);
		requestParamsMap.put("recordType", recordType);
		requestParamsMap.put("billMonth", billMonth);
		requestParamsMap.put("moduleId", moduleId);
		if(!TextUtils.isEmpty(searchValue)){
			requestParamsMap.put("searchValue", searchValue);
		}
		requestParamsMap.put("start", start + "");
		requestParamsMap.put("length", length + "");
		if (!isShowRemind) {
			dialogMsg = null;
		}
		getData(HttpsUtils.QUERY_DETAIL_RECORD, requestParamsMap,dialogMsg);
	}

	private void getData(final String url, RequestParams requestParamsMap,
						 String dialogMsg) {

		Http.postTemp(getAbsoultUrl(url),requestParamsMap,
				new MyTextAsyncResponseHandler(context,dialogMsg){
			@Override
			public void onStart() {
				super.onStart();
				if (listener != null) {
					listener.onGetDataStart();
				}
			}

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				if (listener != null) {
					listener.onGetDataSuccess(url,content);
				}
			}


			@Override
			public void onFailure(Throwable error) {
				super.onFailure(error);
				if (listener != null) {
					listener.onGetDataFailure(error);
				}
			}
		},TIMEOUT_READ_DEFAULT,TIMEOUT_CONNECTION_DEFAULT);
	}


	private String getAbsoultUrl(String url) {
		return HttpsUtils.HOST + url;
	}


	/**
	 * 监听https访问的接口
	 * @author huangjian
	 *
	 */
	public interface IHttpsResponseListener {
		/**
		 * 开始访问数据
		 */
		void onGetDataStart();
		/**
		 * 成功访问网络后
		 * @param url	访问的url地址
		 * @param result 成功访问后返回的字符串
		 */
		void onGetDataSuccess(String url,String result);

		/**
		 * 访问网络失败后
		 * @param error
		 */
		void onGetDataFailure(Throwable error);
	}

}
