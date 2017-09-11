package cn.xwj.easy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by xw on 2017/8/1.
 */

public class NoticeAngle extends RelativeLayout {
    private int msgCount;
    private int mRes;
    private TextView bar_num;
    private ImageView bar_image;

    public NoticeAngle(Context context) {
        this(context, null);
    }

    public NoticeAngle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoticeAngle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(cn.xwj.easy.R.layout.notice_angle, this, true);
        bar_num = (TextView) rl.findViewById(cn.xwj.easy.R.id.tv_notice_angle);
        bar_image = (ImageView) rl.findViewById(cn.xwj.easy.R.id.iv_icon);
        rl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void setMessageCount(int count) {
        msgCount = count;
        if (count == 0) {
            bar_num.setVisibility(View.GONE);
        } else {
            bar_num.setVisibility(View.VISIBLE);
            if (count < 100) {
                bar_num.setText(count + "");
            } else {
                bar_num.setText("99+");
            }
        }
        invalidate();
    }

    public void setImageBackGroud(int res) {
        mRes = res;
        bar_image.setImageResource(mRes);
    }

    public void addMsg() {
        setMessageCount(msgCount + 1);
    }
}
