package com.project.mgjandroid.ui.activity.pintuan;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.ServiceTypeModel;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;

/**
 * Created by User_Cjh on 2016/8/18.
 */
public class GroupServiceAdapter extends BaseListAdapter<ServiceTypeModel.ValueEntity> {
    public GroupServiceAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, ServiceTypeModel.ValueEntity bean, int position, View convertView, ViewGroup parent) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(position, convertView, parent, layoutId);
        TextView tvName = holder.getView(R.id.create_group_service_name);
        ImageView ivIcon = holder.getView(R.id.create_group_service_status);
        tvName.setText(mDatas.get(position).getName());
        if (mDatas.get(position).isSelected()) {
            ivIcon.setImageResource(R.drawable.service_status_checked);
        } else {
            ivIcon.setImageResource(R.drawable.service_status_unchecked);
        }
        return holder.getConvertView();
    }
}
