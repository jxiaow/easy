package cn.xwj.easy.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import cn.xwj.easy.holder.HolderHelper;
import cn.xwj.easy.widget.PinnedHeaderExpandableListView;


/**
 * 折叠式ListView的适配类
 * <p>
 * Created by xw on 2017/8/8.
 */

public abstract class PinnedHeaderExpandableAdapter<T, V> extends BaseExpandableListAdapter
        implements PinnedHeaderExpandableListView.HeaderAdapter {

    protected Context mContext;
    private SparseIntArray groupStatusMap = new SparseIntArray();
    private int groupLayoutId;
    private int childLayoutId;

    public PinnedHeaderExpandableAdapter(Context context, int groupLayoutId, int childLayoutId) {
        this.mContext = context;
        this.groupLayoutId = groupLayoutId;
        this.childLayoutId = childLayoutId;
    }


    @Override
    public T getGroup(int groupPosition) {
        return groupPosition == -1 || getGroupData() == null ? null : getGroupData().get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return getGroupData() == null ? 0 : getGroupData().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HolderHelper.ViewHolder groupHolder;
        if (convertView == null) {
            groupHolder = HolderHelper.create().inflater(mContext, parent, groupLayoutId).viewHolder();
            convertView = groupHolder.getViewHelper().getContentView();
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (HolderHelper.ViewHolder) convertView.getTag();
        }
        onBindGroupViewHolder(groupHolder, isExpanded, getGroupData().get(groupPosition), groupPosition);
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return groupPosition == -1 || getChildData(groupPosition) == null ? 0 : getChildData(groupPosition).size();
    }


    @Override
    public V getChild(int groupPosition, int childPosition) {
        return getChildData(groupPosition) == null ? null : getChildData(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        HolderHelper.ViewHolder childHolder;
        if (convertView == null) {
            childHolder = HolderHelper.create().inflater(mContext, parent, childLayoutId).viewHolder();
            convertView = childHolder.getViewHelper().getContentView();
            convertView.setTag(childHolder);
        } else {
            childHolder = (HolderHelper.ViewHolder) convertView.getTag();
        }
        onBindChildViewHolder(childHolder, getChild(groupPosition, childPosition), isLastChild, groupPosition, childPosition);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition, boolean isGroupExpanded) {
        final int childCount = getChildrenCount(groupPosition);

        if (childCount == 0) {
            return PINNED_HEADER_GONE;
        }

        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        }

        if (childPosition == -1
                && !isGroupExpanded) {
            return PINNED_HEADER_GONE;
        }

        return PINNED_HEADER_VISIBLE;
    }

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }


    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
        HolderHelper.ViewHolder viewHolder = HolderHelper.create()
                .setContentView(mContext, header).viewHolder();
        boolean isExpand = getGroupClickStatus(groupPosition) != 0;
        onBindHeaderViewHolder(viewHolder, isExpand, groupPosition, childPosition, alpha);
    }

    /**
     * 绑定头部信息
     *
     * @param headerHolder  头部ViewHolder
     * @param isExpand      是否展开
     * @param groupPosition 组的位置
     * @param childPosition 子项的位置
     * @param alpha         透明度
     */
    public abstract void onBindHeaderViewHolder(HolderHelper.ViewHolder headerHolder, boolean isExpand,
                                                int groupPosition, int childPosition, int alpha);

    /**
     * 根据组的位置获取子项的数据
     *
     * @param groupPosition 组的位置
     * @return 返回子项的所有数据
     */
    public abstract List<V> getChildData(int groupPosition);

    /**
     * 绑定组的相关信息
     *
     * @param groupHolder   组的ViewHolder
     * @param isExpanded    是否展开
     * @param t             需要绑定数据的实例
     * @param groupPosition 组的位置
     */
    public abstract void onBindGroupViewHolder(HolderHelper.ViewHolder groupHolder, boolean isExpanded, T t, int groupPosition);

    /**
     * 绑定子项的相关数据
     *
     * @param childHolder
     * @param v
     * @param isLastChild
     * @param groupPosition
     * @param childPosition
     */
    public abstract void onBindChildViewHolder(HolderHelper.ViewHolder childHolder, V v, boolean isLastChild,
                                               int groupPosition, int childPosition);

    /**
     * 设置组的信息
     *
     * @return list集合
     */
    protected abstract List<T> getGroupData();
}
