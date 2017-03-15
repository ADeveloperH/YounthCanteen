package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.younthcanteen.AppManager;
import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.ui.SelectPicPopupWindow;
import com.mobile.younthcanteen.util.DataCheckUtils;
import com.mobile.younthcanteen.util.DialogUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.StringUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * author：hj
 * time: 2017/2/9 0009 11:08
 */

public class MyAccountActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llUser;
    private LinearLayout llNickName;
    private LinearLayout llPwd;
    private LinearLayout llPhoneNumber;
    private ImageView ivUserIcon;
    private TextView tvNickName;
    private TextView tvPhoneNumber;
    private Button btnLogout;
    private String nickNameStr;
    private SelectPicPopupWindow menuWindow;
    private LinearLayout llPayPwd;

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
        initData();
    }

    private void initView() {
        llUser = (LinearLayout) findViewById(R.id.ll_user_icon);
        llNickName = (LinearLayout) findViewById(R.id.ll_nick_name);
        llPwd = (LinearLayout) findViewById(R.id.ll_pwd);
        llPayPwd = (LinearLayout) findViewById(R.id.ll_pay_pwd);
        llPhoneNumber = (LinearLayout) findViewById(R.id.ll_phonenumber);
        ivUserIcon = (ImageView) findViewById(R.id.iv_usericon);
        tvNickName = (TextView) findViewById(R.id.tv_nickname);
        tvPhoneNumber = (TextView) findViewById(R.id.tv_phonenumber);
        btnLogout = (Button) findViewById(R.id.btn_logout);
    }

    private void initData() {
        nickNameStr = SharedPreferencesUtil.getNickName();
        tvNickName.setText(nickNameStr);
        String phoneNumber = SharedPreferencesUtil.getAccount().replace(" ", "");
        if (DataCheckUtils.isValidatePhone(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        tvPhoneNumber.setText(phoneNumber);
    }

    private void setListener() {
        llNickName.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        llPwd.setOnClickListener(this);
        llUser.setOnClickListener(this);
        llPayPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_nick_name://修改用户名
                Intent nickNameIntent = new Intent(this, ModifyNickNameActivity.class);
                nickNameIntent.putExtra("nickName", nickNameStr);
                startActivity(nickNameIntent);
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
            File file = new File(filePath);
            RequestParams params = new RequestParams();
            params.put("token", SharedPreferencesUtil.getToken());
            System.out.println("token::" + SharedPreferencesUtil.getToken()) ;

            Http.uploadFile(Http.UPLOADIMG, filePath, params, new MyTextAsyncResponseHandler(act, "正在上传中...") {
                @Override
                public void onSuccess(String content) {
                    super.onSuccess(content);
                }

                @Override
                public void onFailure(Throwable error) {
                    super.onFailure(error);
                }
            });

        } catch (Exception e) {
            ToastUtils.showShortToast("上传失败" + e.getMessage());
        }
    }
}
