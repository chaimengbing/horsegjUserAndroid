package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Menu;
import com.project.mgjandroid.ui.view.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/10/11.
 */
public class SuperMarketExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private Context context;

    private final LayoutInflater inflater;

    private List<Menu> list;

    public SuperMarketExpandableListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    public void refreshData(List<Menu> list) {
        if (list == null) list = new ArrayList<>();
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getGoodsCategoryList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.expandable_group_item, null);
            viewHolder.layout = (FrameLayout) convertView.findViewById(R.id.group_layout);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.leftLine = convertView.findViewById(R.id.left_line);
            viewHolder.rightLine = convertView.findViewById(R.id.right_line);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        Menu group = list.get(groupPosition);
        if (group.isSelected()) {
            viewHolder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.title_bar_bg));
            viewHolder.textView.getPaint().setFakeBoldText(true);
            viewHolder.leftLine.setVisibility(View.VISIBLE);
            viewHolder.rightLine.setVisibility(View.INVISIBLE);
        } else if (isExpanded) {
            viewHolder.layout.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.title_bar_bg));
            viewHolder.textView.getPaint().setFakeBoldText(true);
            viewHolder.leftLine.setVisibility(View.INVISIBLE);
            viewHolder.rightLine.setVisibility(View.VISIBLE);
        } else {
            viewHolder.layout.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.color_6));
            viewHolder.textView.getPaint().setFakeBoldText(false);
            viewHolder.leftLine.setVisibility(View.INVISIBLE);
            viewHolder.rightLine.setVisibility(View.VISIBLE);
        }
        viewHolder.textView.setText(group.getName());
        return convertView;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.expandable_child_item, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.leftLine = convertView.findViewById(R.id.left_line);
            viewHolder.rightLine = convertView.findViewById(R.id.right_line);
            viewHolder.dashLine = convertView.findViewById(R.id.bottom_line);
            viewHolder.bottomLine = convertView.findViewById(R.id.bottom_line_2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        Menu child = list.get(groupPosition).getGoodsCategoryList().get(childPosition);
        if (child.isSelected()) {
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.title_bar_bg));
            viewHolder.leftLine.setVisibility(View.VISIBLE);
            viewHolder.rightLine.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.color_6));
            viewHolder.leftLine.setVisibility(View.INVISIBLE);
            viewHolder.rightLine.setVisibility(View.VISIBLE);
        }
        if (childPosition == getRealChildrenCount(groupPosition) - 1) {
            viewHolder.bottomLine.setVisibility(View.VISIBLE);
            viewHolder.dashLine.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.bottomLine.setVisibility(View.INVISIBLE);
            viewHolder.dashLine.setVisibility(View.VISIBLE);
        }
        viewHolder.textView.setText(child.getName());
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return list.get(groupPosition).getGoodsCategoryList() != null ? list.get(groupPosition).getGoodsCategoryList().size() : 0;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupViewHolder {
        FrameLayout layout;
        TextView textView;
        View leftLine, rightLine;
    }

    private class ChildViewHolder {
        TextView textView;
        View leftLine, rightLine, bottomLine, dashLine;
    }
}
