package com.project.mgjandroid.ui.activity.illegalquery;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.AutoLogos;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

/**
 * Created by SunXueLiang on 2017-05-09.
 */

public class SimpleExpAdapter extends MyExpandAdapter {

    public static final String BRAND = "brand";
    public static final String IMGURL = "imgurl";

    private Map<String, List<AutoLogos>> mDataEntities;   //列表数据存放的集合
    private Activity mActivity;
    private LayoutInflater mLayoutInflater;  //布局渲染成一个view
    private List<String> strings;

    public SimpleExpAdapter(Map<String, List<AutoLogos>> dataEntities, Activity activity) {
        mDataEntities = dataEntities;
        mActivity = activity;
        initData();
        mLayoutInflater = mActivity.getLayoutInflater();
    }

    private void initData() {
        strings = new ArrayList<>();
        for (Map.Entry<String, List<AutoLogos>> entry : mDataEntities.entrySet()) {
            strings.add(entry.getKey());
        }
        Collections.sort(strings);
    }

    @Override
    public int getGroupCount() {
        return strings.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String s = strings.get(groupPosition);
        List<AutoLogos> autoLogoses = mDataEntities.get(s);
        return autoLogoses.size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.exlv_item_group, parent, false);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.tv_exlv_group);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTextView.setText(strings.get(groupPosition));
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        String s = strings.get(groupPosition);
        final List<AutoLogos> autoLogoses = mDataEntities.get(s);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.exlv_item_child, parent, false);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.tv_exlv_child);
            viewHolder.imgLogos = (ImageView) convertView.findViewById(R.id.img_logos);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageUtils.setImageViewBitmap(autoLogoses.get(childPosition).getImgUrl(), viewHolder.imgLogos, 0);
        viewHolder.mTextView.setText(autoLogoses.get(childPosition).getBrand());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(IMGURL, autoLogoses.get(childPosition).getImgUrl());
                intent.putExtra(BRAND, autoLogoses.get(childPosition).getBrand());
                mActivity.setResult(RESULT_OK, intent);
                mActivity.finish();
            }
        });
        return convertView;
    }

    class ViewHolder {//item放置的控件
        TextView mTextView;
        ImageView imgLogos;
    }
}
