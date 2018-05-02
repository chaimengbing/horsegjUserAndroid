package com.project.mgjandroid.ui.activity.laws;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationFengShui;
import com.project.mgjandroid.bean.information.InformationLaw;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/12/7.
 */

public class LawListAdapter extends BaseListAdapter<InformationLaw> {
    public LawListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, InformationLaw bean, int position, View convertView, ViewGroup parent) {
        String imgs = bean.getHeadImg();
        RoundImageView cornerImageView = holder.getView(R.id.law_user_avatar);
        if (TextUtils.isEmpty(imgs)) {
            cornerImageView.setVisibility(View.GONE);
        } else {
            cornerImageView.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, bean.getHeadImg(), cornerImageView, R.drawable.info_no_header_icon, Constants.getEndThumbnail(86, 66));
        }
        TextView isTop = holder.getView(R.id.law_is_top);
        holder.setText(R.id.law_name, bean.getName());
        if (bean.getPracticeTimeNum() == 0) {
            holder.setText(R.id.law_practiceTime, 1 + "年");
        } else {
            holder.setText(R.id.law_practiceTime, bean.getPracticeTimeNum() + "年");
        }
        holder.setText(R.id.law_position_name, bean.getCategoryName());
        holder.setText(R.id.tv_law_province, bean.getWhereProvinceName());
        holder.setText(R.id.tv_law_company, bean.getWhereCompany());
        holder.setText(R.id.law_be_good_at, "擅长：" + bean.getGoodField());
        if (bean.getIsTop() == 1) {
            isTop.setVisibility(View.VISIBLE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }
}
