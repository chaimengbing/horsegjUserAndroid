package com.project.mgjandroid.ui.activity.employment;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.PositionInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by User_Cjh on 2016/11/18.
 */
public class MyPublishPositionListAdapter extends BaseListAdapter<PositionInformation> {
    private View.OnClickListener listener;

    public MyPublishPositionListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, PositionInformation bean, int position, View convertView, ViewGroup parent) {
        TextView isTop = holder.getView(R.id.new_recruit_is_top);
        RoundImageView avatar = holder.getView(R.id.new_recruit_user_avatar);
        TextView salary = holder.getView(R.id.new_recruit_salary);
        TextView intro = holder.getView(R.id.new_recruit_user_introduce);
        TextView comDate = holder.getView(R.id.new_recruit_company_date);
        TextView updateTime = holder.getView(R.id.new_recruit_update_time);
        LinearLayout welfareLayout = holder.getView(R.id.new_recruit_welfare_layout);
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
        holder.setText(R.id.new_recruit_title, bean.getTitle());
        if (bean.getSex() == 0) {//男
            ImageUtils.loadBitmap(mActivity, bean.getHeadImg(), avatar, R.drawable.sir_default_icon, Constants.getEndThumbnail(80, 80));
            intro.setText("男/" + bean.getAge() + "/" + bean.getHighestEducation() + "/" + bean.getWorkExperience());
        } else {
            ImageUtils.loadBitmap(mActivity, bean.getHeadImg(), avatar, R.drawable.miss_default_icon, Constants.getEndThumbnail(80, 80));
            intro.setText("女/" + bean.getAge() + "/" + bean.getHighestEducation() + "/" + bean.getWorkExperience());
        }
        updateTime.setText(CommonUtils.formatTime(bean.getModifyTime().getTime(), "更新时间: yyyy-MM-dd"));
        //求职
        avatar.setVisibility(View.VISIBLE);
        intro.setVisibility(View.VISIBLE);
        updateTime.setVisibility(View.VISIBLE);
        salary.setVisibility(View.GONE);
        comDate.setVisibility(View.GONE);
        welfareLayout.setVisibility(View.GONE);
        if (bean.getIsTop() == 1 && bean.getIsExpire() != 1) {
            isTop.setVisibility(View.VISIBLE);
            comDate.setVisibility(View.GONE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }
}
