package cn.xwj.easy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xwj.easy.R;
import cn.xwj.easy.util.DisplayUtils;


/**
 * 自定义角标view
 * <p>
 * Created by xw on 2017/8/1.
 */

public class NoticeAngle extends RelativeLayout {
    private int msgCount;
    private Context mContext;
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
        mContext = context;
        initView();
        initAttrs(attrs);
    }

    private void initView() {
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(mContext).inflate(cn.xwj.easy.R.layout.notice_angle, this, true);
        bar_num = (TextView) rl.findViewById(cn.xwj.easy.R.id.tv_notice_angle);
        bar_image = (ImageView) rl.findViewById(cn.xwj.easy.R.id.iv_icon);
        rl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    private void initAttrs(AttributeSet attributeSet) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attributeSet, R.styleable.NoticeAngle);
        float noticeTextSize = typedArray.getDimension(R.styleable.NoticeAngle_notice_text_size, -1);
        int color = typedArray.getColor(R.styleable.NoticeAngle_notice_text_color, -1);

        if (noticeTextSize != -1) {
            bar_num.setTextSize(DisplayUtils.px2dp(getContext(),noticeTextSize));
        }

        if (color != -1) {
            bar_num.setTextColor(color);
        }
        typedArray.recycle();
    }

    /**
     * 设置角标数量
     *
     * @param count 需要显示的角标数量
     */
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

    /**
     * 设置角标图片的背景色
     *
     * @param res 背景资源id
     */
    public void setImageBackGroud(int res) {
        mRes = res;
        bar_image.setImageResource(mRes);
    }
}
