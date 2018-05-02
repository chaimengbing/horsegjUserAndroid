package com.project.mgjandroid.ui.activity.healthy;


import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationHealth;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by Administrator on 2016/12/2.
 */

public class HealthListAdapter extends BaseListAdapter<InformationHealth> {


    public HealthListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, InformationHealth bean, int position, View convertView, ViewGroup parent) {
        String imgs = bean.getHeadImg();
        RoundImageView cornerImageView = holder.getView(R.id.health_user_avatar);
        if (TextUtils.isEmpty(imgs)) {
            cornerImageView.setVisibility(View.GONE);
        } else {
            cornerImageView.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, bean.getHeadImg().split(";")[0], cornerImageView, R.drawable.info_no_header_icon, Constants.getEndThumbnail(86, 66));
        }
        TextView isTop = holder.getView(R.id.health_is_top);
        holder.setText(R.id.health_name, bean.getName());
        holder.setText(R.id.health_Title, bean.getProfessionalTitle());
        holder.setText(R.id.health_hospital, bean.getHospital());
        holder.setText(R.id.health_position_name, bean.getDepartments());
        holder.setText(R.id.health_be_good_at, "擅长：" + bean.getDoctorExpertise());
        if (bean.getIsTop() == 1) {
            isTop.setVisibility(View.VISIBLE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }
}

