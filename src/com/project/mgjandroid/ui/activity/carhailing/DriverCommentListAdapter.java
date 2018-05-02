package com.project.mgjandroid.ui.activity.carhailing;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.carhailing.DriverComment;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by User_Cjh on 2016/12/19.
 */
public class DriverCommentListAdapter extends BaseListAdapter<DriverComment> {
    public DriverCommentListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, DriverComment bean, int position, View convertView, ViewGroup parent) {
        RoundImageView image = holder.getView(R.id.driver_comment_user_header);
        if (CheckUtils.isNoEmptyStr(bean.getUserHeaderImg()) && bean.getAnonymous() == 1) {
            ImageUtils.loadBitmap(mActivity, bean.getUserHeaderImg(), image, R.drawable.horsegj_default, Constants.getEndThumbnail(35, 35));
        } else {
            image.setImageResource(R.drawable.user_avatar);
        }
        if (bean.getAnonymous() == 1) {
            holder.setText(R.id.driver_comment_user_name, bean.getUserName());
        } else {
            holder.setText(R.id.driver_comment_user_name, "匿名用户");
        }
        RatingBar rbScore = holder.getView(R.id.driver_comment_user_score);
        rbScore.setRating(bean.getCompositeScore().floatValue());
        holder.setText(R.id.driver_comment_user_tv_score, bean.getCompositeScore() + "");
        holder.setText(R.id.driver_comment_date, bean.getCreateTime().split(" ")[0]);
        holder.setText(R.id.driver_comment_user_comment, bean.getChauffeurOrderScoreComments());
    }
}
