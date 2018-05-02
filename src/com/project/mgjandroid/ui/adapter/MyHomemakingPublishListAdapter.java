package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HomemakingInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.ImageUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by rjp on 2016/7/14.
 * Email:rjpgoodjob@gmail.com
 */
public class MyHomemakingPublishListAdapter extends BaseListAdapter<HomemakingInformation> {

    private View.OnClickListener listener;


    public MyHomemakingPublishListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, HomemakingInformation bean, int position, View convertView, ViewGroup parent) {
//
        ImageView status = holder.getView(R.id.my_publish_education_status);
        View bottomLine = holder.getView(R.id.my_publish_bottom_line);
        LinearLayout bottomLayout = holder.getView(R.id.my_publish_bottom_layout);
        View payLine = holder.getView(R.id.my_publish_pay_line);
        LinearLayout payLayout = holder.getView(R.id.my_publish_pay_layout);

        String imgs = bean.getImgs();
        CornerImageView avatar = holder.getView(R.id.new_education_user_avatar);
        if (TextUtils.isEmpty(imgs)) {
            avatar.setVisibility(View.GONE);
        } else {
            avatar.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, bean.getImgs().split(";")[0], avatar, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        }
        TextView rePublic = holder.getView(R.id.my_publish_republic);
        TextView toPay = holder.getView(R.id.my_publish_toPay);
        rePublic.setTag(position);
        rePublic.setOnClickListener(listener);
        toPay.setTag(position);
        toPay.setOnClickListener(listener);
        TextView tvNoScore = holder.getView(R.id.tv_no_score);
        TextView tvName = holder.getView(R.id.new_education_title);
        if (bean.getType() == 1) {
            tvName.setText(bean.getTitle());
        } else if (bean.getType() == 2) {
            tvName.setText(bean.getTitle());
        } else {
            tvName.setText("");
        }
        RatingBar ratingBar = holder.getView(R.id.group_detail_admin_score);
        ratingBar.setRating(bean.getScore().floatValue());
        BigDecimal score = bean.getScore();
        if (score.compareTo(BigDecimal.ZERO) == 0) {
            ratingBar.setVisibility(View.GONE);
            tvNoScore.setTextColor(Color.parseColor("#999999"));
            tvNoScore.setText("暂无评分");
        } else {
            ratingBar.setVisibility(View.VISIBLE);
            tvNoScore.setTextColor(Color.parseColor("#ff6634"));
            tvNoScore.setText(bean.getScore().floatValue() + "分");
        }

        TextView tvTime = holder.getView(R.id.education_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        tvTime.setText("发布时间：" + format.format(bean.getCreateTime()));

        TextView tvStatus = holder.getView(R.id.education_is_top);
        if (bean.getIsTop() == 1 && bean.getIsExpire() != 1) {
            tvStatus.setVisibility(View.VISIBLE);
        } else {
            tvStatus.setVisibility(View.GONE);
        }
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
            Log.d("asdf", bean.getStatus() + "----------------");
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


    }

}
