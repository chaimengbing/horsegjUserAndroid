package com.project.mgjandroid.ui.activity.illegalquery;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.Entity;
import com.project.mgjandroid.model.IllegalDetailsModel;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;

/**
 * Created by SunXueLiang on 2017-05-08.
 */

public class VehicleDetailsListAdapter extends BaseListAdapter<IllegalDetailsModel.ValueBean.ResultBean.ListBean> {
    public VehicleDetailsListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, IllegalDetailsModel.ValueBean.ResultBean.ListBean bean, int position, View convertView, ViewGroup parent) {
        TextView tvEditPlace = (TextView) holder.getView(R.id.tv_edit_place);
        TextView tvTime = (TextView) holder.getView(R.id.tv_edit_time);
        TextView tvEditCode = (TextView) holder.getView(R.id.tv_edit_code);
        TextView tvEditReason = (TextView) holder.getView(R.id.tv_edit_reason);
        TextView tvFine = (TextView) holder.getView(R.id.tv_fine);
        TextView tvPoints = (TextView) holder.getView(R.id.tv_points);
        tvTime.setText(bean.getTime());
        tvEditPlace.setText("【" + bean.getProvince() + "】" + bean.getAddress());
        tvEditCode.setText(bean.getLegalnum());
        tvEditReason.setText(bean.getContent());
        tvFine.setText(bean.getPrice() + "");
        tvPoints.setText(bean.getScore() + "");
    }


}
