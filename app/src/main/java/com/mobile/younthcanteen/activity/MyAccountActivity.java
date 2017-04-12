package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.AppManager;
import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.SimpleResultBean;
import com.mobile.younthcanteen.bean.UserDetailInfoBean;
import com.mobile.younthcanteen.fragment.CustomerFragment;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.ui.CircleImageView;
import com.mobile.younthcanteen.ui.SelectPicPopupWindow;
import com.mobile.younthcanteen.util.DataCheckUtils;
import com.mobile.younthcanteen.util.DialogUtil;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.StringUtil;
import com.mobile.younthcanteen.util.ThreadManager;
import com.mobile.younthcanteen.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.phoneNumber;

/**
 * author：hj
 * time: 2017/2/9 0009 11:08
 */

public class MyAccountActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llUser;
    private LinearLayout llNickName;
    private LinearLayout llPwd;
    private LinearLayout llPhoneNumber;
    private CircleImageView ivUserIcon;
    private TextView tvNickName;
    private TextView tvPhoneNumber;
    private Button btnLogout;
    private String nickNameStr;
    private SelectPicPopupWindow menuWindow;
    private LinearLayout llPayPwd;
    private boolean isFirstIn = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的账号");
        setTitleBackVisible(true);
        checkLogin(true);
        setContentView(R.layout.activity_myaccount_layout);

        initView();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetailInfo();
    }

    private void initView() {
        llUser = (LinearLayout) findViewById(R.id.ll_user_icon);
        llNickName = (LinearLayout) findViewById(R.id.ll_nick_name);
        llPwd = (LinearLayout) findViewById(R.id.ll_pwd);
        llPayPwd = (LinearLayout) findViewById(R.id.ll_pay_pwd);
        llPhoneNumber = (LinearLayout) findViewById(R.id.ll_phonenumber);
        ivUserIcon = (CircleImageView) findViewById(R.id.iv_usericon);
        tvNickName = (TextView) findViewById(R.id.tv_nickname);
        tvPhoneNumber = (TextView) findViewById(R.id.tv_phonenumber);
        btnLogout = (Button) findViewById(R.id.btn_logout);

    }

    private void setListener() {
        llNickName.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        llPwd.setOnClickListener(this);
        llUser.setOnClickListener(this);
        llPayPwd.setOnClickListener(this);
    }

    /**
     * 获取用户的详细信息
     */
    private void getUserDetailInfo() {
        RequestParams params = new RequestParams();
        params.put("userid", SharedPreferencesUtil.getUserId());
        params.put("token", SharedPreferencesUtil.getToken());
        Http.post(Http.GETUSERDETAILINFO, params, new MyTextAsyncResponseHandler(act, "加载中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    UserDetailInfoBean bean = JsonUtil.fromJson(content, UserDetailInfoBean.class);
                    if (null != bean) {
                        if (!Http.SUCCESS.equals(bean.getReturnCode())) {
                            ToastUtils.showShortToast(bean.getReturnMessage());
                            return;
                        }
                        UserDetailInfoBean.ResultsEntity result = bean.getResults();
                        SharedPreferencesUtil.setNickName(result.getNick());
                        SharedPreferencesUtil.setToken(result.getToken());
                        SharedPreferencesUtil.setUserId(result.getUserid());
                        SharedPreferencesUtil.setPoint(result.getPoint());
                        SharedPreferencesUtil.setMoney(result.getMoney());
                        SharedPreferencesUtil.setIsSetPayPwd(result.isIspaypassset());
                        SharedPreferencesUtil.setUserIconUrl(result.getImgs());
                        nickNameStr = result.getNick();
                        tvNickName.setText(nickNameStr);
                        String phoneNumber = SharedPreferencesUtil.getAccount().replace(" ", "");
                        if (DataCheckUtils.isValidatePhone(phoneNumber)) {
                            phoneNumber = phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                        }
                        tvPhoneNumber.setText(phoneNumber);
                        getUserIconFromServer(result.getImgs());
                    } else {
                        ToastUtils.showShortToast("服务器数据异常，请稍后重试");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("数据异常，请稍后重试");
                }

            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_nick_name://修改用户名
                if (!TextUtils.isEmpty(nickNameStr)) {
                    Intent nickNameIntent = new Intent(this, ModifyNickNameActivity.class);
                    nickNameIntent.putExtra("nickName", nickNameStr);
                    startActivity(nickNameIntent);
                } else {
                    getUserDetailInfo();
                }
                break;
            case R.id.btn_logout://退出登录
                DialogUtil.getSimpleDialog(act, "提示", "确定退出?", "取消", "确认", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesUtil.clear();
                        AppManager.getAppManager().finishAllActivity();
                        startActivity(new Intent(act, MainActivity.class));
                    }
                }, false).show();

                break;
            case R.id.ll_pwd://修改密码
                startActivity(new Intent(act, ModifyPwdActivity.class));
                break;
            case R.id.ll_pay_pwd://修改支付密码
                startActivity(new Intent(act, ModifyPayPwdActivity.class));
                break;
            case R.id.ll_user_icon://修改头像
                menuWindow = new SelectPicPopupWindow(act, this);//实例化SelectPicPopupWindow
                menuWindow.showAtLocation(findViewById(R.id.ll_rootview),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);//显示窗口
                break;
            case R.id.btn_take_photo://拍照
                takePhotoGet();
                menuWindow.dismiss();
                break;
            case R.id.btn_pick_photo://选择相册
                fromPhotoGet();
                menuWindow.dismiss();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(tempFile), 150);
                break;
            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom(data.getData(), 150);
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null)
                    setPicToView(data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    // 创建一个以当前时间为名称的文件
    File tempFile = new File(Environment.getExternalStorageDirectory(), "cache_user_icon.jpg");

    /**
     * 拍照获取图片
     */
    private void takePhotoGet() {
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
    }

    /***
     * 从相册中取图片
     */
    private void fromPhotoGet() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    // 将进行剪裁后的图片(无论是拍照获取的还是从相册中获取的)保存到临时文件中。在将临时文件上传到服务器
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            try {
                Bitmap photo = bundle.getParcelable("data");
                FileOutputStream out = new FileOutputStream(tempFile);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
            }
        }
        submitIcon();
    }

    private void submitIcon() {
        try {
            String filePath = StringUtil.getThumbUploadPath(tempFile.getAbsolutePath(), 200);//得到压缩后的图片地址
            Http.uploadFileImage(Http.UPLOADIMG, filePath, SharedPreferencesUtil.getToken(), "jpg"
                    , new MyTextAsyncResponseHandler(act, "正在上传中...") {
                        @Override
                        public void onSuccess(String content) {
                            super.onSuccess(content);
                            try {
                                SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                                if (null != bean) {
                                    ToastUtils.showShortToast(bean.getReturnMessage());
                                    if (Http.SUCCESS.equals(bean.getReturnCode())) {
                                        CustomerFragment.isNeedLoadUserInfo = true;
                                        getUserDetailInfo();
                                    }
                                } else {
                                    ToastUtils.showLongToast("服务器异常，请稍后重试");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                ToastUtils.showShortToast("数据异常，请稍后重试");
                            }
                        }

                        @Override
                        public void onFailure(Throwable error) {
                            super.onFailure(error);
                            ToastUtils.showLongToast("服务器异常，请稍后重试");
                        }
                    });

        } catch (Exception e) {
            ToastUtils.showShortToast("上传失败" + e.getMessage());
        }
    }



    /**
     * 将服务器端的图片显示并缓存到本地
     *
     * @param userIconUrl
     */
    private void getUserIconFromServer(final String userIconUrl) {
        ThreadManager.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(userIconUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(30000); // 设置联接超时时间
                    conn.setReadTimeout(30000); // 设置读取内容的超时时间
                    conn.setRequestMethod("GET");// 设置为get 请求

                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) { // 说明，连网成功
                        InputStream inputStream = conn.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        File cacheDir = new File(act.getFilesDir(), "userIconCache");
                        if (!cacheDir.exists()) {
                            cacheDir.mkdirs();
                        }
                        // 把图片存入文件
                        FileOutputStream outputStream = new FileOutputStream(cacheDir.getAbsolutePath() + "/" + phoneNumber);
                        bitmap.compress(CompressFormat.JPEG, 100, outputStream);
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivUserIcon.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (Exception e) {
                }

            }
        });
    }
}
