package cn.xwj.easy.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.xwj.easy.R;

/**
 * 加载等待时的对话框
 * <p>
 * Created by xw on 2017/11/21.
 */

public class LoadingDialog extends Dialog {
    private Context mContext;
    private TextView mMsg;
    private View contentView;

    public LoadingDialog(Context context) {
        this(context, R.style.CustomDialog);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        contentView = LayoutInflater.from(mContext).inflate(cn.xwj.easy.R.layout.custom_dialog, null);
        initView();
        setContentView(contentView);
    }

    private void initView() {
        mMsg = (TextView) contentView.findViewById(cn.xwj.easy.R.id.tv_load_dialog);
    }

    private void setMsg(String msg) {
        mMsg.setVisibility(View.VISIBLE);
        mMsg.setText(msg);
    }

    /**
     * 显示对话框
     *
     * @param text 显示的文本
     */
    public void show(String text) {
        super.show();
        setMsg(text);
    }

    /**
     * 显示对话框
     *
     * @param msgId 显示的资源id
     */
    public void show(int msgId) {
        show(mContext.getString(msgId));
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mContext = null;
        contentView = null;
        mMsg = null;
    }

    /**
     * 设置对话框不可按返回键取消和触摸取消
     *
     * @param cancelable true 可以取消，false不可取消
     */
    public void cancelable(boolean cancelable) {
        this.setCancelable(cancelable);
        this.setCanceledOnTouchOutside(cancelable);
    }
}
