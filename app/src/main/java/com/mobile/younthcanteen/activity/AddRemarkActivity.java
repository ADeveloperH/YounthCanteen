package com.mobile.younthcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.younthcanteen.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：hj
 * time: 2017/4/4 0004 16:31
 */


public class AddRemarkActivity extends BaseActivity {
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_remark1)
    TextView tvRemark1;
    @BindView(R.id.tv_remark2)
    TextView tvRemark2;
    @BindView(R.id.tv_remark3)
    TextView tvRemark3;
    @BindView(R.id.tv_remark4)
    TextView tvRemark4;
    @BindView(R.id.tv_remark5)
    TextView tvRemark5;
    @BindView(R.id.tv_remark6)
    TextView tvRemark6;
    private final int GETREMARK_RESULTCODE = 14;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加备注");
        setTitleBackVisible(true);
        setSubTitle("完成");
        setContentView(R.layout.activity_add_remark_layout);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("remark")) {
            etRemark.setText(intent.getStringExtra("remark"));
            etRemark.setSelection(etRemark.getText().toString().trim().length());
        }

        setClickSubTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remark = etRemark.getText().toString().trim();
                if (!TextUtils.isEmpty(remark)) {
                    Intent intent = new Intent();
                    intent.putExtra("remark", remark);
                    setResult(GETREMARK_RESULTCODE, intent);
                }
                finish();
            }
        });
    }

    @OnClick({R.id.tv_remark1, R.id.tv_remark2, R.id.tv_remark3, R.id.tv_remark4, R.id.tv_remark5, R.id.tv_remark6})
    public void onClick(View view) {
        String remark = etRemark.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_remark1:
                if (!TextUtils.isEmpty(remark)) {
                    remark += (" " + tvRemark1.getText().toString().trim());
                } else {
                    remark += tvRemark1.getText().toString().trim();
                }
                break;
            case R.id.tv_remark2:
                if (!TextUtils.isEmpty(remark)) {
                    remark += (" " + tvRemark2.getText().toString().trim());
                } else {
                    remark += tvRemark2.getText().toString().trim();
                }
                break;
            case R.id.tv_remark3:
                if (!TextUtils.isEmpty(remark)) {
                    remark += (" " + tvRemark3.getText().toString().trim());
                } else {
                    remark += tvRemark3.getText().toString().trim();
                }
                break;
            case R.id.tv_remark4:
                if (!TextUtils.isEmpty(remark)) {
                    remark += (" " + tvRemark4.getText().toString().trim());
                } else {
                    remark += tvRemark4.getText().toString().trim();
                }
                break;
            case R.id.tv_remark5:
                if (!TextUtils.isEmpty(remark)) {
                    remark += (" " + tvRemark5.getText().toString().trim());
                } else {
                    remark += tvRemark5.getText().toString().trim();
                }
                break;
            case R.id.tv_remark6:
                if (!TextUtils.isEmpty(remark)) {
                    remark += (" " + tvRemark6.getText().toString().trim());
                } else {
                    remark += tvRemark6.getText().toString().trim();
                }
                break;
        }
        if (!TextUtils.isEmpty(remark)) {
            etRemark.setText(remark);
            etRemark.setSelection(etRemark.getText().toString().trim().length());
        }

    }
}
