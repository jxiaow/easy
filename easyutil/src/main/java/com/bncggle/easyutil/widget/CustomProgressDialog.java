package com.bncggle.easyutil.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bncggle.easyutil.R;


/**
 * Created by xiaowu on 2017/7/30.
 */

public class CustomProgressDialog extends Dialog {
    private Context mContext;
    private TextView mMsg;
    private View contentView;

    public CustomProgressDialog(Context context) {
        this(context, R.style.CustomDialog);

    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public static CustomProgressDialog createProgressDialog(Context context) {
        return new CustomProgressDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        contentView = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog, null);
        //WindowManager.LayoutParams attributes = getWindow().getAttributes();
        //attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        // attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        //  getWindow().setAttributes(attributes);
        initView();
        setContentView(contentView);
    }

    private void initView() {
        mMsg = (TextView) contentView.findViewById(R.id.tv_load_dialog);
    }

    public void setMsg(String msg) {
        mMsg.setText(msg);
    }

    public void show(String text) {
        super.show();
        setMsg(text);
    }
}
