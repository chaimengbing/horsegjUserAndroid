package com.project.mgjandroid.ui.activity.employment;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.RecruitInformation;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;

/**
 * Created by User_Cjh on 2016/11/18.
 */
public class MyPublishRecruitListAdapter extends BaseListAdapter<RecruitInformation> {

    private View.OnClickListener listener;

    public MyPublishRecruitListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, RecruitInformation bean, int position, View convertView, ViewGroup parent) {
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
        View payLine = holder.getView(R.id.my_publish_pay_line);
        LinearLayout payLayout = holder.getView(R.id.my_publish_pay_layout);
        TextView rePublic = holder.getView(R.id.my_publish_republic);
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
        holder.setText(R.id.new_recruit_title, bean.getTitle());
//        holder.setText(R.id.new_recruit_company_name,bean.getCompanyName());
//        holder.setText(R.id.new_recruit_company_type,bean.getCompanyType());
        salary.setText(bean.getSalary());
        comDate.setText(CommonUtils.formatTime(bean.getModifyTime().getTime(), "yyyy.MM.dd"));
        welfareLayout.setVisibility(View.VISIBLE);
        welfareLayout.removeAllViews();
        if (CheckUtils.isNoEmptyStr(bean.getWelfare())) {
            String[] strs = bean.getWelfare().split(",");
            for (int i = 0; i < strs.length; i++) {
                if (i <= 3) {
                    TextView tv = new TextView(mActivity);
                    tv.setText(strs[i]);
                    tv.setBackgroundResource(R.drawable.shap_org_range_bg_recruit_mark);
                    tv.setTextColor(mActivity.getResources().getColor(R.color.title_bar_bg));
                    tv.setTextSize(13);
                    LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    tvlp.setMargins(0, 0, mActivity.getResources().getDimensionPixelSize(R.dimen.x2), 0);
                    tv.setLayoutParams(tvlp);
                    welfareLayout.addView(tv);
                } else if (i == 4) {
                    TextView tv = new TextView(mActivity);
                    tv.setText("…");
                    tv.setTextColor(mActivity.getResources().getColor(R.color.title_bar_bg));
                    welfareLayout.addView(tv);
                }
            }
        } else {
            TextView tv = new TextView(mActivity);
            tv.setText("");
            tv.setBackgroundResource(R.drawable.shap_org_range_bg_recruit_mark);
            tv.setTextColor(mActivity.getResources().getColor(R.color.title_bar_bg));
            tv.setTextSize(13);
            welfareLayout.addView(tv);
            welfareLayout.setVisibility(View.INVISIBLE);
        }

        //求职
        avatar.setVisibility(View.GONE);
        intro.setVisibility(View.GONE);
        updateTime.setVisibility(View.GONE);
        salary.setVisibility(View.VISIBLE);
        comDate.setVisibility(View.VISIBLE);
        if (bean.getIsTop() == 1 && bean.getIsExpire() != 1) {
            isTop.setVisibility(View.VISIBLE);
            comDate.setVisibility(View.GONE);
        } else {
            isTop.setVisibility(View.GONE);
        }
    }
}
