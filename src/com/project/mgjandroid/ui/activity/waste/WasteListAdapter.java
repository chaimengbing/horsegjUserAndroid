package com.project.mgjandroid.ui.activity.waste;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationWasteRecovery;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by Administrator on 2016/12/12.
 */

public class WasteListAdapter extends BaseListAdapter<InformationWasteRecovery> {
    public WasteListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, InformationWasteRecovery bean, int position, View convertView, ViewGroup parent) {
        String imgs = bean.getImgs();
        CornerImageView cornerImageView = holder.getView(R.id.waste_user_avatar);
        if (TextUtils.isEmpty(imgs)) {
            cornerImageView.setVisibility(View.GONE);
        } else {
            cornerImageView.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, bean.getImgs().split(";")[0], cornerImageView, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        }
        TextView isTop = holder.getView(R.id.waste_is_top);
        RatingBar adminScore = holder.getView(R.id.group_detail_admin_score);
        TextView score = holder.getView(R.id.tv_score);
        TextView business = holder.getView(R.id.tv_business);
        holder.setText(R.id.waste_title, bean.getTitle());
        holder.setText(R.id.waste_type, bean.getGoodField());
        adminScore.setRating(bean.getScore().floatValue());
        if (bean.getScore().floatValue() == 0) {
            adminScore.setVisibility(View.GONE);
            score.setTextColor(Color.parseColor("#999999"));
            score.setText("暂无评分");
        } else {
            adminScore.setVisibility(View.VISIBLE);
            score.setTextColor(Color.parseColor("#ff6634"));
            score.setText(bean.getScore().floatValue() + "分");
        }
        if (bean.getType() == 1) {
            business.setText("个人");
        } else {
            business.setText("商家");
        }
        if (bean.getIsTop() == 1) {
            isTop.setVisibility(View.VISIBLE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }
}
