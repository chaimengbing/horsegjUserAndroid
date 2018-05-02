package com.project.mgjandroid.ui.activity.carhailing;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.carhailing.CityListBean;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;

public class TripCityListAdapter extends BaseListAdapter<CityListBean> {
    public TripCityListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, CityListBean bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.area_name);
        tvName.setText(bean.getCityName());
        TextView tvStatus = holder.getView(R.id.is_entry);
        tvStatus.setVisibility(View.GONE);
    }
}
