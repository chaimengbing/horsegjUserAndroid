package com.project.mgjandroid.ui.activity.employment;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.PositionInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;

import java.text.SimpleDateFormat;

public class PositionListAdapter extends BaseListAdapter<PositionInformation> {

    private SimpleDateFormat format = new SimpleDateFormat("发布时间：yyyy-MM-dd HH:mm");
    private int type;

    public PositionListAdapter(int layoutId, Activity mActivity, int type) {
        super(layoutId, mActivity);
        this.type = type;
    }

    @Override
    protected void getRealView(ViewHolder holder, final PositionInformation bean, final int position, View convertView, ViewGroup parent) {
        TextView isTop = holder.getView(R.id.new_recruit_is_top);
        RoundImageView avatar = holder.getView(R.id.new_recruit_user_avatar);
        TextView salary = holder.getView(R.id.new_recruit_salary);
        TextView intro = holder.getView(R.id.new_recruit_user_introduce);
        TextView comDate = holder.getView(R.id.new_recruit_company_date);
        TextView updateTime = holder.getView(R.id.new_recruit_update_time);
        LinearLayout welfareLayout = holder.getView(R.id.new_recruit_welfare_layout);
        View comLine = holder.getView(R.id.new_recruit_company_line);
        LinearLayout comIntro = holder.getView(R.id.new_recruit_company_intro);
        View divider = holder.getView(R.id.recruit_divider);
        holder.setText(R.id.new_recruit_title, bean.getTitle());

        if (type == InformationType.Position.getValue()) {//求职
            avatar.setVisibility(View.VISIBLE);
            intro.setVisibility(View.VISIBLE);
            updateTime.setVisibility(View.VISIBLE);
            salary.setVisibility(View.GONE);
            comDate.setVisibility(View.GONE);
            welfareLayout.setVisibility(View.GONE);
            comLine.setVisibility(View.GONE);
            comIntro.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);

            holder.setText(R.id.new_recruit_position_name, bean.getExpectPosition());
            if (bean.getSex() == 0) {//男
                ImageUtils.loadBitmap(mActivity, bean.getHeadImg(), avatar, R.drawable.sir_default_icon, Constants.getEndThumbnail(80, 80));
                intro.setText("男/" + bean.getAge() + "/" + bean.getHighestEducation() + "/" + bean.getWorkExperience());
            } else {
                ImageUtils.loadBitmap(mActivity, bean.getHeadImg(), avatar, R.drawable.miss_default_icon, Constants.getEndThumbnail(80, 80));
                intro.setText("女/" + bean.getAge() + "/" + bean.getHighestEducation() + "/" + bean.getWorkExperience());
            }
//            updateTime.setText(CommonUtils.formatTime(bean.getModifyTime().getTime(), "更新时间: yyyy-MM-dd"));
            updateTime.setText("更新时间：" + DateUtils.format(bean.getModifyTime(), bean.getServiceTime()));
        } else {//招聘
            avatar.setVisibility(View.GONE);
            intro.setVisibility(View.GONE);
            updateTime.setVisibility(View.GONE);
            salary.setVisibility(View.VISIBLE);
            comDate.setVisibility(View.VISIBLE);
            welfareLayout.setVisibility(View.VISIBLE);
            comLine.setVisibility(View.VISIBLE);
            comIntro.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        }
        if (bean.getIsTop() == 1) {
            isTop.setVisibility(View.VISIBLE);
            comDate.setVisibility(View.GONE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }
}