package com.bncggle.easyutil.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import cn.com.bnc.ginms.widget.HeaderAdapter;

/**
 * Created by xw on 2017/8/8.
 */

public abstract class PinnedHeaderExpandableAdapter<T, V> extends BaseExpandableListAdapter
        implements HeaderAdapter {

    private List<T> groupDataList;
    private Context mContext;
    private SparseIntArray groupStatusMap = new SparseIntArray();
    private int groupLayoutId;
    private int childLayoutId;

    public PinnedHeaderExpandableAdapter(Context context, List<T> groupDataList, int groupLayoutId, int childLayoutId) {
        this.mContext = context;
        this.groupDataList = groupDataList;
        this.groupLayoutId = groupLayoutId;
        this.childLayoutId = childLayoutId;
    }

    /**
     * 子类在更新数据时，一定要调用
     *
     * @param groupDataList
     */
    public void setGroupDataList(List<T> groupDataList) {
        this.groupDataList = groupDataList;
        notifyDataSetChanged();
    }

    @Override
    public T getGroup(int groupPosition) {
        return groupPosition == -1 || groupDataList == null ? null : groupDataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groupDataList == null ? 0 : groupDataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder groupHolder;
        if (convertView == null) {
            groupHolder = ViewHolder.createViewHolder(mContext, parent, groupLayoutId);
            convertView = groupHolder.getContentView();
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (ViewHolder) convertView.getTag();
        }
        onBindGroupViewHolder(groupHolder, isExpanded, groupDataList.get(groupPosition));
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return groupPosition == -1 || getChildDataList(groupPosition) == null ? 0 : getChildDataList(groupPosition).size();
    }


    @Override
    public V getChild(int groupPosition, int childPosition) {
        return getChildDataList(groupPosition) == null ? null : getChildDataList(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder childHolder = null;
        if (convertView == null) {
            childHolder = ViewHolder.createViewHolder(mContext, parent, childLayoutId);
            convertView = childHolder.getContentView();
            convertView.setTag(childHolder);
        } else {
            childHolder = (ViewHolder) convertView.getTag();
        }
        onBindChildViewHolder(childHolder, getChild(groupPosition, childPosition), isLastChild);
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
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !isGroupExpanded) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
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
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, header);
        boolean isExpand = getGroupClickStatus(groupPosition) != 0;
        onBindHeaderViewHolder(viewHolder, isExpand, groupPosition, childPosition, alpha);
    }

    public abstract void onBindHeaderViewHolder(ViewHolder headerHolder, boolean isExpand, int groupPosition, int childPosition, int alpha);

    public abstract List<V> getChildDataList(int groupPosition);

    public abstract void onBindGroupViewHolder(ViewHolder groupHolder, boolean isExpanded, T t);

    public abstract void onBindChildViewHolder(ViewHolder childHolder, V v, boolean isLastChild);




}
