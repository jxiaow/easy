package cn.xwj.easy.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: xw
 * Date: 2018-04-12 11:37:35
 * Description: HolderHelper: .
 */
public final class HolderHelper {

    private View mContentView;
    private Context mContext;

    private HolderHelper() {
    }

    public RecyclerViewHolder recyclerView() {
        checkNull(mContentView, "please call inflater() or setContentView()" +
                " before call recyclerView");
        return new RecyclerViewHolder(mContext, mContentView);
    }

    public ViewHolder viewHolder() {
        checkNull(mContentView, "please call inflater() or setContentView()" +
                " before call viewHolder");
        return new ViewHolder(mContext, mContentView);
    }

    private void checkNull(View view, String msg) {
        if (view == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static HolderHelper create() {
        return new HolderHelper();
    }

    public HolderHelper inflater(Context context, ViewGroup parent, int layoutRes) {
        mContext = context;
        mContentView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        return this;
    }

    public HolderHelper setContentView(Context context, View view) {
        mContext = context;
        mContentView = view;
        return this;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ViewHelper mViewHelper;

        private RecyclerViewHolder(Context context, View itemView) {
            super(itemView);
            mViewHelper = new ViewHelper(context, itemView);
        }

        public ViewHelper getViewHelper() {
            return mViewHelper;
        }

    }

    public static class ViewHolder {
        private ViewHelper mViewHelper;

        private ViewHolder(Context context, View contentView) {
            mViewHelper = new ViewHelper(context, contentView);
        }

        public ViewHelper getViewHelper() {
            return mViewHelper;
        }

    }
}
