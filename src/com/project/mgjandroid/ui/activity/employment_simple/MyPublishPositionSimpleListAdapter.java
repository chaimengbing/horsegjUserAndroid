package com.project.mgjandroid.ui.activity.employment_simple;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.NewPositionInformation;
import com.project.mgjandroid.bean.information.PositionInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;

public class MyPublishPositionSimpleListAdapter extends BaseListAdapter<NewPositionInformation> {
    private View.OnClickListener listener;

    public MyPublishPositionSimpleListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, NewPositionInformation bean, int position, View convertView, ViewGroup parent) {
        TextView isTop = holder.getView(R.id.new_recruit_is_top);
        TextView category = holder.getView(R.id.tv_category);
        TextView positionName = holder.getView(R.id.new_recruit_position_name);
        TextView time = holder.getView(R.id.tv_publish_time);
        TextView description = holder.getView(R.id.tv_job_description);
        ImageView status = holder.getView(R.id.my_publish_recruit_status);
        View bottomLine = holder.getView(R.id.my_publish_bottom_line);
        LinearLayout bottomLayout = holder.getView(R.id.my_publish_bottom_layout);
        TextView rePublic = holder.getView(R.id.my_publish_republic);
        View payLine = holder.getView(R.id.my_publish_pay_line);
        LinearLayout payLayout = holder.getView(R.id.my_publish_pay_layout);
        TextView toPay = holder.getView(R.id.my_publish_toPay);
        rePublic.setTag(position);
        rePublic.setOnClickListener(listener);
        toPay.setTag(position);
        toPay.setOnClickListener(listener);
        status.setVisibility(View.VISIBLE);
        ImageView operate = holder.getView(R.id.iv_more_setting);
        operate.setTag(position);
        operate.setOnClickListener(listener);
        operate.setImageResource(R.drawable.my_publish_delete);
        if (bean.getIsExpire() == 1) {
            status.setImageResource(R.drawable.info_expiration);
            bottomLine.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
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
            Log.d("zxc", bean.getStatus() + "----------");
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
        category.setText(bean.getTitle());
        positionName.setText(bean.getCategoryName());
        description.setText(bean.getDescription());
        time.setText(CommonUtils.formatTime(bean.getModifyTime().getTime(), "更新时间: yyyy-MM-dd HH:mm"));

        if (bean.getIsTop() == 1 && bean.getIsExpire() != 1) {
            isTop.setVisibility(View.VISIBLE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

}
