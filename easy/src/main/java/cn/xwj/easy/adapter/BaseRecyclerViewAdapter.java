package cn.xwj.easy.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import cn.xwj.easy.holder.HolderHelper;


/**
 * Author: xw
 * Date: 2018-04-12 13:07:04
 * Description: BaseRecyclerViewHelper: RecyclerView的adapter.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<HolderHelper.RecyclerViewHolder> {
    protected List<T> mList;
    private int layoutId;
    protected Context mContext;

    public BaseRecyclerViewAdapter(List<T> list, @LayoutRes int layoutId) {
        this.mList = list;
        this.layoutId = layoutId;
    }


    @Override
    public HolderHelper.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        return HolderHelper.create().inflater(mContext, parent, layoutId).recyclerView();
    }

    @Override
    public void onBindViewHolder(HolderHelper.RecyclerViewHolder holder, int position) {
        convert(holder, getItem(position), position);
    }

    @Nullable
    public T getItem(int position) {
        return mList == null || position >= mList.size() ? null : mList.get(position);
    }

    protected abstract void convert(HolderHelper.RecyclerViewHolder holder, T t, int position);


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setList(List<T> tList) {
        this.mList = tList;
        notifyDataSetChanged();
    }
}
