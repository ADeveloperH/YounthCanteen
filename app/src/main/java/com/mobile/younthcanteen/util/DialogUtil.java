package com.mobile.younthcanteen.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mobile.younthcanteen.R;


/**
 * 弹出框帮助类
 * Created by tjj on 2015/5/6.
 */
public class DialogUtil {
    /**
     * 一个基本的代确定取消按钮的对话框
     *
     * @param context
     * @param title             标题
     * @param content           内容
     * @param okBtnTxt          确定按钮的文字.默认为:"确定"
     * @param cancleBtnTxt      取消按钮的文字.默认为："取消"
     * @param okBtnListener     确定按钮的点击事件监听
     * @param cancleBtnListener 取消按钮的点击事件监听
     * @return
     */
    public static Dialog getSimpleDialog(Context context, String title, String content,
                                         String okBtnTxt, String cancleBtnTxt, final OnClickListener okBtnListener,
                                         final OnClickListener cancleBtnListener, boolean canCelable) {
        final Dialog dialog = new Dialog(context, R.style.Theme_CustomDialog_buy);
        View contentView = UIUtils.inflate(R.layout.dialog_common);
        dialog.setContentView(contentView);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_content);
        TextView tvCancle = (TextView) contentView.findViewById(R.id.tv_cancel);
        TextView tvOk = (TextView) contentView.findViewById(R.id.tv_ok);

        //标题
        if (TextUtils.isEmpty(title)) {
            tvTitle.setText("");
        } else {
            tvTitle.setText(title);
        }
        //内容
        if (TextUtils.isEmpty(content)) {
            tvContent.setText("");
        } else {
            tvContent.setText(content);
        }
        //确定按钮
        if (TextUtils.isEmpty(okBtnTxt)) {
            //默认为确定
            tvOk.setText("确定");
        } else {
            tvOk.setText(okBtnTxt);
        }
        //取消按钮
        if (TextUtils.isEmpty(cancleBtnTxt)) {
            //默认为取消
            tvCancle.setText("取消");
        } else {
            tvCancle.setText(cancleBtnTxt);
        }

        //确定按钮点击事件
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (okBtnListener != null) {
                    okBtnListener.onClick(view);
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        // 取消按钮点击事件
        tvCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancleBtnListener != null) {
                    cancleBtnListener.onClick(view);
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        //设置按返回键能够退出
        dialog.setCancelable(canCelable);
        return dialog;
    }


    /**
     * 一个基本的只有一个按钮的对话框
     *
     * @param context
     * @param title       标题
     * @param content     内容
     * @param btnTxt      按钮的文字.默认为:"确定"
     * @param btnListener 按钮的点击事件监听
     * @param cancelable  点击返回键和对话框外部是否可用消失
     * @return
     */
    public static Dialog getSimpleDialog3(Context context, String title, String content,
                                          String btnTxt, final OnClickListener btnListener, boolean cancelable) {
        final Dialog dialog = new Dialog(context, R.style.Theme_CustomDialog_buy);
        View contentView = UIUtils.inflate(R.layout.dialog_common3);
        dialog.setContentView(contentView);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_content);
        TextView tvBtn = (TextView) contentView.findViewById(R.id.tv_btn);

        //标题
        if (TextUtils.isEmpty(title)) {
            tvTitle.setText("");
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        //内容
        if (TextUtils.isEmpty(content)) {
            tvContent.setText("");
        } else {
            tvContent.setText(content);
        }
        //按钮
        if (TextUtils.isEmpty(btnTxt)) {
            //默认为确定
            tvBtn.setText("确定");
        } else {
            tvBtn.setText(btnTxt);
        }
        //确定按钮点击事件
        tvBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnListener != null) {
                    btnListener.onClick(view);
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        //设置按返回键能够退出
        dialog.setCancelable(cancelable);
        return dialog;
    }


    /**
     * 一个基本的代确定取消按钮的对话框.没有标题
     *
     * @param context
     * @param content           内容
     * @param okBtnTxt          确定按钮的文字.默认为:"确定"
     * @param cancleBtnTxt      取消按钮的文字.默认为："取消"
     * @param okBtnListener     确定按钮的点击事件监听
     * @param cancleBtnListener 取消按钮的点击事件监听
     * @return
     */
    public static Dialog getSimpleDialogNoTitle(Context context, String content,
                                                String okBtnTxt, String cancleBtnTxt, final OnClickListener okBtnListener,
                                                final OnClickListener cancleBtnListener) {
        final Dialog dialog = new Dialog(context, R.style.Theme_CustomDialog_buy);
        View contentView = UIUtils.inflate(R.layout.dialog_common_notitle);
        dialog.setContentView(contentView);
        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_content);
        TextView tvCancle = (TextView) contentView.findViewById(R.id.tv_cancel);
        TextView tvOk = (TextView) contentView.findViewById(R.id.tv_ok);
        //内容
        if (TextUtils.isEmpty(content)) {
            tvContent.setText("");
        } else {
            tvContent.setText(content);
        }
        //确定按钮
        if (TextUtils.isEmpty(okBtnTxt)) {
            //默认为确定
            tvOk.setText("确定");
        } else {
            tvOk.setText(okBtnTxt);
        }
        //取消按钮
        if (TextUtils.isEmpty(cancleBtnTxt)) {
            //默认为取消
            tvCancle.setText("取消");
        } else {
            tvCancle.setText(cancleBtnTxt);
        }

        //确定按钮点击事件
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (okBtnListener != null) {
                    okBtnListener.onClick(view);
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        // 取消按钮点击事件
        tvCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancleBtnListener != null) {
                    cancleBtnListener.onClick(view);
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        //设置按返回键能够退出
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * 一个基本的代确定取消按钮的对话框.没有标题
     *
     * @param context
     * @param content           内容
     * @param okBtnTxt          确定按钮的文字.默认为:"确定"
     * @param cancleBtnTxt      取消按钮的文字.默认为："取消"，“取消按钮加粗”
     * @param okBtnListener     确定按钮的点击事件监听
     * @param cancleBtnListener 取消按钮的点击事件监听
     * @return
     */
    public static Dialog getSimpleDialogNoTitleCancleBold(Context context, String content,
                                                          String okBtnTxt, String cancleBtnTxt, final OnClickListener okBtnListener,
                                                          final OnClickListener cancleBtnListener) {
        final Dialog dialog = new Dialog(context, R.style.Theme_CustomDialog_buy);
        View contentView = UIUtils.inflate(R.layout.dialog_common_notitle_canclebold);
        dialog.setContentView(contentView);
        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_content);
        TextView tvCancle = (TextView) contentView.findViewById(R.id.tv_cancel);
        TextView tvOk = (TextView) contentView.findViewById(R.id.tv_ok);
        //内容
        if (TextUtils.isEmpty(content)) {
            tvContent.setText("");
        } else {
            tvContent.setText(content);
        }
        //确定按钮
        if (TextUtils.isEmpty(okBtnTxt)) {
            //默认为确定
            tvOk.setText("确定");
        } else {
            tvOk.setText(okBtnTxt);
        }
        //取消按钮
        if (TextUtils.isEmpty(cancleBtnTxt)) {
            //默认为取消
            tvCancle.setText("取消");
        } else {
            tvCancle.setText(cancleBtnTxt);
        }

        //确定按钮点击事件
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (okBtnListener != null) {
                    okBtnListener.onClick(view);
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        // 取消按钮点击事件
        tvCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancleBtnListener != null) {
                    cancleBtnListener.onClick(view);
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        //设置按返回键能够退出
        dialog.setCancelable(true);
        return dialog;
    }
}
