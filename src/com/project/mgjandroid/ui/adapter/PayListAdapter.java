package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationFreeStandard;

/**
 * Created by User_Cjh on 2016/11/15.
 */
public class PayListAdapter extends BaseListAdapter<InformationFreeStandard> {
    public PayListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, InformationFreeStandard bean, int position, View convertView, ViewGroup parent) {
        holder.setText(R.id.popup_item_pay_count, bean.getTitle());
    }
}
