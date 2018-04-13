package cn.xwj.easy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.xwj.easy.R;
import cn.xwj.easy.util.DisplayUtils;


/**
 * 自定义的TitleView
 * <p>
 * Created by xw on 2017/7/7.
 */

public class TitleView extends LinearLayout {

    private Toolbar mToolbar;
    private TextView mTitle;
    private Context mContext;
    private AttributeSet mAttrs;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        this.mAttrs = attrs;
        initView();
        initAttrs();
    }

    /**
     * 设置背景
     *
     * @param resId 背景资源id
     */
    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        mToolbar.setBackgroundResource(resId);
    }

    /**
     * 设置它的menu菜单点击事件
     *
     * @param menuItemClickListener menuItemClickListener
     */
    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener menuItemClickListener) {
        mToolbar.setOnMenuItemClickListener(menuItemClickListener);
    }

    /**
     * 左侧按钮的点击事件
     *
     * @param clickListener clickListener
     */
    public void setLeftIconOnclickListener(OnClickListener clickListener) {
        mToolbar.setNavigationOnClickListener(clickListener);
    }

    public void setMenuId(int menuId) {
        mToolbar.inflateMenu(menuId);
    }

    public void setNavigationIcon(int iconId) {
        mToolbar.setNavigationIcon(iconId);
    }


    private void initAttrs() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.TitleView);
        String titleValue = typedArray.getString(R.styleable.TitleView_title_text);
        int titleTextColor = typedArray.getColor(R.styleable.TitleView_title_text_color, Color.WHITE);
        int leftIcon = typedArray.getResourceId(R.styleable.TitleView_left_icon, -1);
        float textSize = typedArray.getDimension(R.styleable.TitleView_title_text_size,
                DisplayUtils.sp2px(getContext(), 15));
        int backgroundId = typedArray.getResourceId(R.styleable.TitleView_title_background, -1);
        int menu_layout_id = typedArray.getResourceId(R.styleable.TitleView_title_menu, -1);
        mTitle.setText(titleValue);
        mTitle.setTextColor(titleTextColor);
        mTitle.setTextSize(DisplayUtils.px2sp(getContext(), textSize));
        if (leftIcon != -1) {
            mToolbar.setNavigationIcon(leftIcon);
        } else {
            mToolbar.setNavigationIcon(null);
        }
        if (backgroundId == -1) {
            if (Build.VERSION.SDK_INT > 16) {
                mToolbar.setBackground(this.getBackground());
            } else {
                mToolbar.setBackgroundDrawable(this.getBackground());
            }
        } else {
            mToolbar.setBackgroundResource(backgroundId);
        }

        if (menu_layout_id != -1) {
            setMenuId(menu_layout_id);
        }

        typedArray.recycle();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.title_layout, this, true);
        mToolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        mTitle = (TextView) view.findViewById(R.id.title_text);
        mToolbar.setTitle("");
    }

    /**
     * 设置标题文字
     *
     * @param title 标题文字
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    /**
     * 返回Toolbar
     *
     * @return toolbar
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }

}
