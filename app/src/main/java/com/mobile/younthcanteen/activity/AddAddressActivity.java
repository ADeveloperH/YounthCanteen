package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mobile.younthcanteen.R;
import com.mobile.younthcanteen.bean.OfficeAddressBean;
import com.mobile.younthcanteen.bean.SimpleResultBean;
import com.mobile.younthcanteen.http.Http;
import com.mobile.younthcanteen.http.MyTextAsyncResponseHandler;
import com.mobile.younthcanteen.http.RequestParams;
import com.mobile.younthcanteen.util.DataCheckUtils;
import com.mobile.younthcanteen.util.DialogUtil;
import com.mobile.younthcanteen.util.JsonUtil;
import com.mobile.younthcanteen.util.SharedPreferencesUtil;
import com.mobile.younthcanteen.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/2/21 0021 15:49
 */

public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_detailadd)
    EditText etDetailadd;
    @BindView(R.id.titlebar_back)
    ImageButton backBtn;
    private List<OfficeAddressBean.ResultsEntity> officeDataList;
    private String choiceOfficeId;
    private Intent intent;
    private String addressid = "0";//默认新增
    private final int CHECKOFFICE_REQUEST_CODE = 10;
    private final int CHECKOFFICE_RESULT_CODE = 11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("新增收货地址");
        setTitleBackVisible(true);
        checkLogin(true);
        setContentView(R.layout.activity_addaddress_layout);
        ButterKnife.bind(this);
        intent = getIntent();
        if (intent != null && intent.hasExtra("title")) {
            setTitle(intent.getStringExtra("title"));
            initData();
        }
    }

    private void initData() {
        String title = intent.getStringExtra("title");
        String name = intent.getStringExtra("name");
        String sex = intent.getStringExtra("sex");
        String phone = intent.getStringExtra("phone");
        String office = intent.getStringExtra("office");
        choiceOfficeId = intent.getStringExtra("officeId");
        String address = intent.getStringExtra("address");
        addressid = intent.getStringExtra("addressid");

        setTitle(title);
        etName.setText(name);
        etName.setSelection(name.length());
        etPhone.setText(phone);
        tvAddress.setText(office);
        etDetailadd.setText(address);
        if ("1".equals(sex)) {
            //女士
            rbFemale.setChecked(true);
        } else {
            rbMale.setChecked(true);
        }
        btnAdd.setText("提交修改");
    }




    @OnClick({R.id.ll_address, R.id.btn_add ,R.id.titlebar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address://选择地址
                startActivityForResult(new Intent(act,CheckOfficeActivity.class),CHECKOFFICE_REQUEST_CODE);
                break;
            case R.id.btn_add://添加
                commit();
                break;
            case R.id.titlebar_back://返回按键
                clickBack();
                break;
        }
    }

    /**
     * 提交添加的收货地址
     */
    private void commit() {
        String nameStr = etName.getText().toString().trim();
        String phoneStr = etPhone.getText().toString().trim();
        String detailAddStr = etDetailadd.getText().toString().trim();
        int checkId = rgSex.getCheckedRadioButtonId();
        String sex = "0";
        if (checkId == R.id.rb_female) {
            sex = "1";
        }
        if (TextUtils.isEmpty(nameStr)) {
            ToastUtils.showShortToast("请输入收货人姓名");
            return;
        }

        if (!DataCheckUtils.isValidatePhone(phoneStr)) {
            ToastUtils.showShortToast("请求输入正确的手机号码");
            return;
        }

        RequestParams params = new RequestParams();
        params.put("addrid", addressid);
        params.put("consignee", nameStr);
        params.put("tel", phoneStr);
        params.put("officeid", choiceOfficeId);
        params.put("token", SharedPreferencesUtil.getToken());
        params.put("sex", sex);
        params.put("addr", detailAddStr);
        Http.post(Http.ADDADDRESS, params, new MyTextAsyncResponseHandler(act, "正在提交中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                    if (null != bean) {
                        ToastUtils.showLongToast(bean.getReturnMessage());
                        if (Http.SUCCESS.equals(bean.getReturnCode())) {
                            finish();
                        }
                    } else {
                        ToastUtils.showShortToast("服务器异常，请稍后重试");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CHECKOFFICE_RESULT_CODE) {
            if (data != null) {
                choiceOfficeId = data.getStringExtra("officeid");
                tvAddress.setText(data.getStringExtra("officename"));
            }
        }
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }

    /**
     * 使用了返回按键
     */
    private void clickBack() {
        String nameStr = etName.getText().toString().trim();
        String phoneStr = etPhone.getText().toString().trim();
        String detailAddStr = etDetailadd.getText().toString().trim();
        if (!TextUtils.isEmpty(nameStr) ||
                !TextUtils.isEmpty(phoneStr) ||
                !TextUtils.isEmpty(detailAddStr)) {
            DialogUtil.getSimpleDialogNoTitle(act, "确定要放弃此编辑", "取消", "确定", null
                    , new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).show();
        } else {
            finish();
        }
    }
}
