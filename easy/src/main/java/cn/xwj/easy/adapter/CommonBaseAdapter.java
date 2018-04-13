package cn.xwj.easy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cn.xwj.easy.holder.HolderHelper;


/**
 * 常用的adapter(不适用于RecyclerView)
 * <p>
 * Created by xw on 2017/12/07.
 */

public abstract class CommonBaseAdapter<T> extends BaseAdapter {

    private int layoutId;
    protected Context mContext;
    protected List<T> mDataList;

    public CommonBaseAdapter(Context context, int layoutId, List<T> dataList) {
        this.layoutId = layoutId;
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList == null || position >= mDataList.size() ? null : mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderHelper.ViewHolder holder;
        if (convertView == null) {
            holder = HolderHelper.create().inflater(mContext, parent, layoutId).viewHolder();
            convertView = holder.getViewHelper().getContentView();
            convertView.setTag(holder);
        } else {
            holder = (HolderHelper.ViewHolder) convertView.getTag();
        }
        convert(holder, getItem(position), position);

        return convertView;
    }

    /**
     * 重新设置新的list
     *
     * @param list 新的list
     */
    public void setList(List<T> list) {
        this.mDataList = list;
        notifyDataSetChanged();
    }

    /**
     * item的数据绑定
     *
     * @param holder   holder
     * @param t        当前position下的数据
     * @param position 当前位置
     */
    public abstract void convert(HolderHelper.ViewHolder holder, T t, int position);
}
