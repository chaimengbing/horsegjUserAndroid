package com.project.mgjandroid.ui.activity.healthy;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationHealth;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by pb on 2016-12-15.
 */

public class MyPublishHealthyListAdapter extends BaseListAdapter<InformationHealth> {

    private View.OnClickListener listener;

    public MyPublishHealthyListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, InformationHealth bean, int position, View convertView, ViewGroup parent) {
        TextView isTop = holder.getView(R.id.health_is_top);
        ImageView status = holder.getView(R.id.my_healthy_status);
        View bottomLine = holder.getView(R.id.my_publish_bottom_line);
        LinearLayout bottomLayout = holder.getView(R.id.my_publish_bottom_layout);
        TextView rePublic = holder.getView(R.id.my_publish_republic);
        View payLine = holder.getView(R.id.my_publish_pay_line);
        LinearLayout payLayout = holder.getView(R.id.my_publish_pay_layout);
        TextView toPay = holder.getView(R.id.my_publish_toPay);
        toPay.setTag(position);
        toPay.setOnClickListener(listener);
        rePublic.setTag(position);
        rePublic.setOnClickListener(listener);
        status.setVisibility(View.VISIBLE);
        ImageView operate = holder.getView(R.id.iv_more_setting);
        operate.setTag(position);
        operate.setOnClickListener(listener);
        operate.setImageResource(R.drawable.my_publish_delete);
        if (bean.getIsExpire() == 1) {
            status.setImageResource(R.drawable.info_expiration);
            bottomLine.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
            payLine.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
            if (bean.getHasAgainSend() == 0) {
                bottomLine.setVisibility(View.VISIBLE);
                bottomLayout.setVisibility(View.VISIBLE);
            } else {
                bottomLine.setVisibility(View.GONE);
                bottomLayout.setVisibility(View.GONE);
            }
        } else {
            bottomLine.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
            payLine.setVisibility(View.GONE);
            payLayout.setVisibility(View.GONE);
            if (bean.getStatus() == 1) {
                status.setImageResource(R.drawable.info_checking);
            } else if (bean.getStatus() == 2) {
                operate.setImageResource(R.drawable.my_publish_moe_set);
                status.setImageResource(R.drawable.info_check_success);
            } else if (bean.getStatus() == 3) {
                status.setImageResource(R.drawable.info_check_fail);
            } else if (bean.getStatus() == 0) {
                status.setImageResource(R.drawable.wait_to_pay);
                payLine.setVisibility(View.VISIBLE);
                payLayout.setVisibility(View.VISIBLE);
            } else if (bean.getStatus() == -1) {
                status.setImageResource(R.drawable.failure);
            } else {
                status.setVisibility(View.GONE);
            }
        }

        String imgs = bean.getHeadImg();
        RoundImageView avatar = holder.getView(R.id.new_healthy_user_avatar);
        if (TextUtils.isEmpty(imgs)) {
            avatar.setVisibility(View.GONE);
        } else {
            avatar.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, bean.getHeadImg().split(";")[0], avatar, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        }
        holder.setText(R.id.health_name, bean.getName());
        holder.setText(R.id.health_title, bean.getProfessionalTitle());
        holder.setText(R.id.health_hospital, bean.getHospital());
        holder.setText(R.id.health_be_good_at, "擅长：" + bean.getDoctorExpertise());

        if (bean.getIsTop() == 1 && bean.getIsExpire() != 1) {
            isTop.setVisibility(View.VISIBLE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }
}
