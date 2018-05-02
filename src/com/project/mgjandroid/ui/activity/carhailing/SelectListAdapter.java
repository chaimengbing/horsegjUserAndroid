package com.project.mgjandroid.ui.activity.carhailing;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.carhailing.CarHailingTripPlace;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;

/**
 * Created by User_Cjh on 2016/12/13.
 */
public class SelectListAdapter extends BaseListAdapter<CarHailingTripPlace> {
    public SelectListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, CarHailingTripPlace bean, int position, View convertView, ViewGroup parent) {
        holder.setText(R.id.popup_item_car_hailing, bean.getAddress());
    }
}
