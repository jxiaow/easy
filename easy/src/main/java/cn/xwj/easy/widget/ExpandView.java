package cn.xwj.easy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xwj.easy.R;
import cn.xwj.easy.util.DisplayUtils;

/**
 * 自定义可伸缩的View
 * Created by xwj on 2017/9/21.
 */
/**
 * 自定义可伸缩的View
 * Created by xwj on 2017/9/21.
 */

public class ExpandView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private TextView mTitle;
    private ImageView mIndicator;
    private RelativeLayout mRlTitleItem;
    private ViewStub mExpandContainer;
    private boolean isExpand = false;

    private AttributeSet mAttributeSet;
    private int expandIndicatorResId;
    private int collapseIndicatorResId;
    private View mExpandView;

    public ExpandView(Context context) {
        this(context, null);
    }

    public ExpandView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(LinearLayout.VERTICAL);
        this.mContext = context;
        this.mAttributeSet = attrs;
        init();
    }

    private void init() {
        initView();
        setAttrs();
        setListener();
    }

    private void setAttrs() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.ExpandView);
        String titleName = typedArray.getString(R.styleable.ExpandView_title_text);
        float titleSize = typedArray.getDimension(R.styleable.ExpandView_title_size, DisplayUtils.dp2px(getContext(), 15));
        int collapseResId = typedArray.getResourceId(R.styleable.ExpandView_indicator_collapse, R.drawable.ic_collapse);
        int expandResId = typedArray.getResourceId(R.styleable.ExpandView_indicator_expand, R.drawable.ic_expand);
        int layoutId = typedArray.getResourceId(R.styleable.ExpandView_expand_layout, -1);
        int titleTextColor = typedArray.getColor(R.styleable.ExpandView_title_text_color, -1);
        int itemBackground = typedArray.getColor(R.styleable.ExpandView_item_background, -1);

        setTitle(titleName);
        setTitleTextSize(DisplayUtils.px2dp(getContext(), titleSize));
        setTitleTextColor(titleTextColor);
        setItemBackground(itemBackground);
        this.expandIndicatorResId = expandResId;
        this.collapseIndicatorResId = collapseResId;
        if (layoutId != -1) {
            mExpandContainer.setLayoutResource(layoutId);
            mExpandContainer.inflate();
            mExpandView = this.findViewById(mExpandContainer.getInflatedId());
            mExpandView.setVisibility(View.GONE);
        }
        typedArray.recycle();
    }

    public void setItemBackground(int itemBackground) {
        if (itemBackground != -1) {
            mRlTitleItem.setBackgroundColor(itemBackground);
        }
    }

    private void setListener() {
        mRlTitleItem.setOnClickListener(this);
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.expand_layout, this, true);
        mTitle = view.findViewById(R.id.tv_title);
        mIndicator = view.findViewById(R.id.iv_indicator);
        mExpandContainer = view.findViewById(R.id.stub_import);
        mRlTitleItem = view.findViewById(R.id.rl_title_item);
    }


    /**
     * 设置title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.mTitle.setText(title);
    }

    /**
     * 设置伸缩的图标
     *
     * @param resId 图标的资源id
     */
    public void setIndicator(int resId) {
        this.mIndicator.setImageResource(resId);
    }

    /**
     * 设置伸缩的图标
     *
     * @param bitmap 图标
     */
    public void setIndicator(Bitmap bitmap) {
        this.mIndicator.setImageBitmap(bitmap);
    }

    private void setExpandContainerVisibility(boolean visible) {
        this.mExpandView.setVisibility(visible ? VISIBLE : GONE);
    }


    /**
     * item的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        isExpand = !isExpand;
        setIndicator(isExpand ? expandIndicatorResId : collapseIndicatorResId);
        setExpandContainerVisibility(isExpand);
    }

    /**
     * 设置title的字体大小
     *
     * @param titleTextSize 字体大小
     */
    public void setTitleTextSize(float titleTextSize) {
        this.mTitle.setTextSize(titleTextSize);
    }

    /**
     * 获取当前伸缩的内容视图
     *
     * @return 伸缩的内容视图
     */
    public View getExpandView() {
        return mExpandView;
    }

    /**
     * 返回当前的伸缩状态
     *
     * @return true 展开， false 收缩
     */
    public boolean isExpand() {
        return isExpand;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.mTitle.setTextColor(titleTextColor);
    }
}