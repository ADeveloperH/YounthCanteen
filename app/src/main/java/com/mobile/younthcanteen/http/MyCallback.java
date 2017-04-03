package com.mobile.younthcanteen.http;

import com.mobile.younthcanteen.http.encrypt.RSAEncrypt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public abstract class MyCallback implements Callback<ResponseBody> {
    public abstract void onStart();

    public abstract void onSuccess(String content);

    public abstract void onFailure(Throwable error);

    public abstract void onFinish();


    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        onFinish();
        if (response.raw().code() == 200) {
            //请求成功.解密数据
            try {
                String myurl = response.raw().request().url().toString();
//                decryptResult(response.body().string(), myurl);
                onSuccess(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
                onFailure(e);
            }
        } else {
            onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));
        }
    }

    /**
     * 对返回的结果进行解密
     *
     * @param responseStr 响应数据
     */
    private void decryptResult(String responseStr, String URL) {
        try {
            JSONObject jsonObject = new JSONObject(responseStr);
            int keyLength = jsonObject.length();
            if (keyLength == 1 && jsonObject.has("result")) {
                //返回的是加密的数据
                String result = jsonObject.optString("result");
                String decodeStr = RSAEncrypt.decrypt(result);
//                Log.w("OkHttp", "URL::" + URL + "---------decodeStr::" + decodeStr);
                onSuccess(decodeStr);
            } else {
//                Log.w("OkHttp", "URL::" + URL + "---------response body donnot contain result");
                onSuccess(responseStr);
            }
        } catch (JSONException e) {
//            Log.w("OkHttp", "URL::" + URL + "---------JSONException::" + e.getMessage());
            onFailure(e);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        onFinish();
        onFailure(t);
    }
}
