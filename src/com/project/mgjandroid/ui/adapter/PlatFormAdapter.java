package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuandi on 2016/5/27.
 */
public class PlatFormAdapter extends BaseListAdapter<RedBag> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private boolean isFromOrderPre;
    private boolean canUse;

    public PlatFormAdapter(int layoutId, Activity mActivity, boolean isFromOrderPre, boolean canUse) {
        super(layoutId, mActivity);
        this.isFromOrderPre = isFromOrderPre;
        this.canUse = canUse;
    }

    @Override
    protected void getRealView(ViewHolder holder, final RedBag bean, int position, View convertView, ViewGroup parent) {
        LinearLayout rootView = holder.getView(R.id.item_content_view);
        TextView nameTextView = holder.getView(R.id.paltform_name_textview);
        TextView expirationTextView = holder.getView(R.id.expiration_time_textview);
        TextView restrictTime = holder.getView(R.id.restrict_time_textview);
        TextView mobile = holder.getView(R.id.mobile_textview);
        TextView moneyNum = holder.getView(R.id.redbag_money_textview);
        TextView restrictAmt = holder.getView(R.id.restrict_amt_textview);
        TextView businessType = holder.getView(R.id.business_type_textview);
        ImageView iv_redbag = holder.getView(R.id.iv_redbag);

        if (canUse) {
            rootView.setBackgroundResource(R.drawable.normal_redbag_bg);
            iv_redbag.setVisibility(View.INVISIBLE);
            expirationTextView.setTextColor(mActivity.getResources().getColor(R.color.color_3));
            businessType.setTextColor(mActivity.getResources().getColor(R.color.color_3));
        } else if (bean.getStatus() == 1) {
            rootView.setBackgroundResource(R.drawable.invalid_redbag_bg);
            moneyNum.setTextColor(mActivity.getResources().getColor(R.color.color_c));
            iv_redbag.setVisibility(View.VISIBLE);
            iv_redbag.setImageResource(R.drawable.redbag_used);
            restrictAmt.setTextColor(mActivity.getResources().getColor(R.color.color_c));
            expirationTextView.setTextColor(mActivity.getResources().getColor(R.color.color_c));
            businessType.setTextColor(mActivity.getResources().getColor(R.color.color_c));

        } else {
            rootView.setBackgroundResource(R.drawable.invalid_redbag_bg);
            moneyNum.setTextColor(mActivity.getResources().getColor(R.color.color_c));
            iv_redbag.setVisibility(View.VISIBLE);
            iv_redbag.setImageResource(R.drawable.redbag_invalid);
            restrictAmt.setTextColor(mActivity.getResources().getColor(R.color.color_c));
            expirationTextView.setTextColor(mActivity.getResources().getColor(R.color.color_c));
            businessType.setTextColor(mActivity.getResources().getColor(R.color.color_c));
        }

        nameTextView.setText(CheckUtils.isEmptyStr(bean.getName()) ? "红包" : bean.getName());
        expirationTextView.setText("有效期至：" + bean.getExpirationTime());
        restrictTime.setText(CheckUtils.isEmptyStr(bean.getRestrictTime()) ? "" : bean.getRestrictTime());
        mobile.setText("限收货人手机号" + bean.getMobile());
        if (bean.getAmt() != null) {
            String str = "¥" + StringUtils.BigDecimal2Str(bean.getAmt());
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new TextAppearanceSpan(null, 0, mActivity.getResources().getDimensionPixelSize(R.dimen.title_bar_text_size), null, null), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            moneyNum.setText(style);
        }

        if (bean.getRestrictAmt() != null && bean.getRestrictAmt().doubleValue() > 0) {
            restrictAmt.setText("满" + StringUtils.BigDecimal2Str(bean.getRestrictAmt()) + "可用");
        } else {
            restrictAmt.setText("无门槛红包");
        }

        businessType.setText("限品类：" + bean.getBusinessTypeName());
    }
}
