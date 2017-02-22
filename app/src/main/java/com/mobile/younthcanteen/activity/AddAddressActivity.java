package com.mobile.younthcanteen.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.mobile.younthcanteen.util.UIUtils;

import java.util.ArrayList;
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
        getAddList();
    }

    private void initData() {
        String title = intent.getStringExtra("title");
        String name = intent.getStringExtra("name");
        String sex = intent.getStringExtra("sex");
        String phone = intent.getStringExtra("phone");
        String office = intent.getStringExtra("office");
        String officeId = intent.getStringExtra("officeId");
        String address = intent.getStringExtra("address");

        setTitle(title);
        etName.setText(name);
        etName.setSelection(name.length());
        etPhone.setText(phone);
        tvAddress.setText(office);
        choiceOfficeId = officeId;
        etDetailadd.setText(address);
    }

    /**
     * 获取写字楼列表数据
     */
    private void getAddList() {
        RequestParams params = new RequestParams();
        params.put("zoneid", "0");
        Http.post(Http.GETOFFICELIST, params, new MyTextAsyncResponseHandler(act, "获取地址中...") {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                OfficeAddressBean bean = JsonUtil.fromJson(content, OfficeAddressBean.class);
                if (null != bean) {
                    if (!"0".equals(bean.getReturnCode())) {
                        ToastUtils.showShortToast(bean.getReturnMessage());
                        return;
                    }
                    officeDataList = bean.getResults();
                    if (officeDataList == null || officeDataList.size() == 0) {
                        ToastUtils.showShortToast("当前无可配送地址");
                    }
                } else {
                    ToastUtils.showShortToast("服务器异常，请稍后重试");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });

    }


    @OnClick({R.id.ll_address, R.id.btn_add ,R.id.titlebar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address://选择地址
                if (officeDataList == null || officeDataList.size() == 0) {
                    getAddList();
                } else {
                    showCheckAddDialog();
                }
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
        params.put("addressid", "0");
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
                SimpleResultBean bean = JsonUtil.fromJson(content, SimpleResultBean.class);
                if (null != bean) {
                    ToastUtils.showLongToast(bean.getReturnMessage());
                    if ("0".equals(bean.getReturnCode())) {
                        finish();
                    }
                } else {
                    ToastUtils.showShortToast("服务器异常，请稍后重试");
                }
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                ToastUtils.showShortToast("服务器异常，请稍后重试");
            }
        });
    }

    /**
     * 显示选择地址的对话框
     */
    private void showCheckAddDialog() {
        final List<String> dataList = new ArrayList<>();
        for (int i = 0; i < officeDataList.size(); i++) {
            dataList.add(officeDataList.get(i).getOfficename());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        final AlertDialog alertDialog = builder.create();
        View view = UIUtils.inflate(R.layout.dialog_choiceadd);
        alertDialog.setView(view);
        alertDialog.setTitle("选择收货地址");
        alertDialog.show();

        ListView lv = (ListView) view.findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(act,
                android.R.layout.simple_expandable_list_item_1,
                dataList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String choiceAddress = dataList.get(position);
                if (!TextUtils.isEmpty(choiceAddress)) {
                    choiceOfficeId = officeDataList.get(position).getOfficeid();
                    tvAddress.setText(choiceAddress);
                    alertDialog.dismiss();
                }
            }
        });
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
