package com.project.mgjandroid.ui.activity.education;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.EducationInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by rjp on 2016/7/22.
 * Email:rjpgoodjob@gmail.com
 */
public class EducationListAdapter extends BaseListAdapter<EducationInformation> {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public EducationListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, EducationInformation bean, int position, View convertView, ViewGroup parent) {
        String imgs = bean.getImgs();
        CornerImageView cornerImageView = holder.getView(R.id.second_market_image_view);
        if (TextUtils.isEmpty(imgs)) {
            cornerImageView.setVisibility(View.GONE);
        } else {
            cornerImageView.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, bean.getImgs().split(";")[0], cornerImageView, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        }

        TextView tvName = holder.getView(R.id.second_market_name);
        TextView tvAddress = holder.getView(R.id.education_address);

        int type = bean.getType();
        if (type == 1) {//教育
            tvName.setText(bean.getOrganizationName());
            tvAddress.setText(bean.getAddress());
        } else if (type == 2) {//家教
            tvName.setText(bean.getTitle());
            tvAddress.setText(bean.getEducationTeacherTypeName() + "-" + bean.getEducationTutorshipStageName());
        }

        TextView tvTag = holder.getView(R.id.second_market_tag);
        tvTag.setText(bean.getCategoryName());

        RatingBar rbEvaluate = holder.getView(R.id.education_rating_bar);
        rbEvaluate.setRating(bean.getScore().floatValue());
        TextView tvScore = holder.getView(R.id.education_score);
        if (bean.getScore().floatValue() == 0) {
            rbEvaluate.setVisibility(View.GONE);
            tvScore.setTextColor(Color.parseColor("#999999"));
            tvScore.setText("暂无评分");
        } else {
            rbEvaluate.setVisibility(View.VISIBLE);
            tvScore.setTextColor(Color.parseColor("#ff6634"));
            tvScore.setText(bean.getScore().floatValue() + "分");
        }

        TextView tvStatus = holder.getView(R.id.second_hand_status);
        if (bean.getIsTop() == 0) {
            tvStatus.setVisibility(View.GONE);
        } else if (bean.getIsTop() == 1) {
            tvStatus.setVisibility(View.VISIBLE);
        }
    }
}
