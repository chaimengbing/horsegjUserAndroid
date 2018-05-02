package com.project.mgjandroid.ui.activity.carhailing;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.carhailing.ProvinceBean;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;

public class TripProvinceListAdapter extends BaseListAdapter<ProvinceBean> {
    public TripProvinceListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, ProvinceBean bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.area_name);
        tvName.setText(bean.getProvinceName());
        TextView tvStatus = holder.getView(R.id.is_entry);
        tvStatus.setVisibility(View.GONE);
    }
}
