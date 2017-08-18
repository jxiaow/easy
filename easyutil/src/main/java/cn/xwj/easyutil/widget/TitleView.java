package cn.xwj.easyutil.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xwj.easyutil.util.DisplayUtil;


/**
 * Created by xw on 2017/7/7.
 */

public class TitleView extends RelativeLayout {

    private Toolbar mToolbar;
    private TextView mTitle;
    private Context mContext;
    private AttributeSet mAttrs;
    private AppCompatActivity mActivity;

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

    @Override
    public void setBackgroundResource(@DrawableRes int resid) {
        mToolbar.setBackgroundResource(resid);
    }


    public void setActionBar(AppCompatActivity activity, boolean isShowBack) {
        mActivity = activity;
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

    public void setBackMenuToFinish() {
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }


    private void initAttrs() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, cn.xwj.easyutil.R.styleable.TitleView);
        String titleValue = typedArray.getString(cn.xwj.easyutil.R.styleable.TitleView_title);
        int titleTextColor = typedArray.getColor(cn.xwj.easyutil.R.styleable.TitleView_title_text_color, Color.WHITE);
        int resId = typedArray.getResourceId(cn.xwj.easyutil.R.styleable.TitleView_backIcon, -1);
        float textSize = typedArray.getDimension(cn.xwj.easyutil.R.styleable.TitleView_titleTextSize, 15);
        int backgroundId = typedArray.getResourceId(cn.xwj.easyutil.R.styleable.TitleView_title_background, -1);
        mTitle.setText(titleValue);
        mTitle.setTextColor(titleTextColor);
        mTitle.setTextSize(DisplayUtil.px2sp(textSize));
        if (resId != -1) {
            mToolbar.setNavigationIcon(resId);
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

    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(cn.xwj.easyutil.R.layout.title_layout, this, true);
        mToolbar = (Toolbar) view.findViewById(cn.xwj.easyutil.R.id.tool_bar);
        mTitle = (TextView) view.findViewById(cn.xwj.easyutil.R.id.title_text);
        mToolbar.setTitle("");
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
