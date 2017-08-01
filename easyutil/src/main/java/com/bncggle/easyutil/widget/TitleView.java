package com.bncggle.easyutil.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bncggle.easyutil.R;
import com.bncggle.easyutil.util.DisplayUtil;


/**
 * Created by xw on 2017/7/7.
 */

public class TitleView extends RelativeLayout {

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

    public void setActionBar(AppCompatActivity activity, boolean isShowBack) {
        activity.setSupportActionBar(mToolbar);
        ActionBar supportActionBar = activity.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(isShowBack);
            supportActionBar.setHomeButtonEnabled(isShowBack);
        }
    }

    public void setActionBar(AppCompatActivity activity) {
        setActionBar(activity, true);
    }

    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener menuItemClickListener) {
        mToolbar.setOnMenuItemClickListener(menuItemClickListener);
    }

    public void setBackMenuItemClickListener(OnClickListener clickListener) {
        mToolbar.setNavigationOnClickListener(clickListener);
    }

    private void initAttrs() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.TitleView);
        String titleValue = typedArray.getString(R.styleable.TitleView_title);
        int titleTextColor = typedArray.getColor(R.styleable.TitleView_title_text_color, Color.WHITE);
        int resId = typedArray.getResourceId(R.styleable.TitleView_backIcon, android.R.id.home);
        float textSize = typedArray.getDimension(R.styleable.TitleView_titleTextSize, 15);
        int backgroundId = typedArray.getResourceId(R.styleable.TitleView_title_background, android.R.color.white);
        mTitle.setText(titleValue);
        mTitle.setTextColor(titleTextColor);
        mTitle.setTextSize(DisplayUtil.px2sp(textSize));
        mToolbar.setNavigationIcon(resId);
        mToolbar.setBackgroundResource(backgroundId);
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.title_layout, this, true);
        mToolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        mTitle = (TextView) view.findViewById(R.id.title);
        mToolbar.setTitle("");
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
