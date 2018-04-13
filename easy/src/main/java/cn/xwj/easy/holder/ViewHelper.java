package cn.xwj.easy.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.util.SparseArrayCompat;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * ViewHolder 帮助类
 * <p>
 * Created by xw on 2017/10/17.
 */

public class ViewHelper {

    private View mContentView;
    private Context mContext;
    private SparseArrayCompat<View> mViews;

    public ViewHelper() {
        this.mViews = new SparseArrayCompat<>();
    }

    public ViewHelper(Context context, View view) {
        this();
        this.mContentView = view;
        this.mContext = context;
    }

    public View getContentView() {
        return mContentView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param resId
     */
    public void setText(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setText(mContext.getResources().getString(resId));
    }

    public void setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
    }

    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
    }

    public void setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);

    }

    public void setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);

    }

    public void setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);

    }

    public void setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);

    }

    public void setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));

    }

    @SuppressLint("NewApi")
    public void setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }

    }

    public void setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);

    }

    public void linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);

    }

    public void setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }

    }

    public void setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);

    }

    public void setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);

    }

    public void setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);

    }

    public void setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);

    }

    public void setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);

    }

    public void setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);

    }

    public void setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);

    }

    public void setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);

    }

    /**
     * 关于事件的
     */
    public void setOnClickListener(int viewId,
                                   View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);

    }

    public void setOnTouchListener(int viewId,
                                   View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);

    }

    public void setOnLongClickListener(int viewId,
                                       View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);

    }

}
