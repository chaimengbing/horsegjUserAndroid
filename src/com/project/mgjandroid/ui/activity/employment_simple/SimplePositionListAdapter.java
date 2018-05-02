package com.project.mgjandroid.ui.activity.employment_simple;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.NewPositionInformation;
import com.project.mgjandroid.bean.information.PositionInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;

import java.text.SimpleDateFormat;

public class SimplePositionListAdapter extends BaseListAdapter<NewPositionInformation> {

    private SimpleDateFormat format = new SimpleDateFormat("发布时间：yyyy-MM-dd HH:mm");
    private int type;

    public SimplePositionListAdapter(int layoutId, Activity mActivity, int type) {
        super(layoutId, mActivity);
        this.type = type;
    }

    @Override
    protected void getRealView(ViewHolder holder, NewPositionInformation bean, int position, View convertView, ViewGroup parent) {
        TextView isTop = holder.getView(R.id.new_recruit_is_top);
        TextView tvCategory = holder.getView(R.id.tv_category);
        TextView name = holder.getView(R.id.new_recruit_position_name);
        TextView publishTime = holder.getView(R.id.tv_publish_time);
        TextView jobDescription = holder.getView(R.id.tv_job_description);

        tvCategory.setText(bean.getTitle());
        name.setText(bean.getCategoryName());
        publishTime.setText(CommonUtils.formatTime(bean.getModifyTime().getTime(), "更新时间: yyyy-MM-dd HH:mm"));
        jobDescription.setText(bean.getDescription());

        if (bean.getIsTop() == 1) {
            isTop.setVisibility(View.VISIBLE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }
}