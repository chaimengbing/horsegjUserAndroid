package com.project.mgjandroid.ui.view.homeredbag;

import android.app.Activity;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.activity.MyRedBagActivity;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuandi on 2017/2/23.
 */
public class HomeRedBagAdapter extends BaseListAdapter<RedBag> {

    private SimpleDateFormat sdf = new SimpleDateFormat("有效期至 yyyy.MM.dd");

    public HomeRedBagAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, final RedBag bean, int position, View convertView, ViewGroup parent) {
        TextView red_bag_name = holder.getView(R.id.red_bag_name);
        TextView tv_effect_period = holder.getView(R.id.effect_period);
        TextView tv_discount_amt = holder.getView(R.id.discount_amt);

        if (!TextUtils.isEmpty(bean.getName())) {
            red_bag_name.setText(bean.getName());
        } else {
            red_bag_name.setText("");
        }

        if (bean.getExpirationTime() != null) {
            tv_effect_period.setText(sdf.format(new Date(bean.getExpirationTime())));
        } else {
            tv_effect_period.setText("");
        }

        if (bean.getCouponType() == 3) {
            String str = StringUtils.BigDecimal2Str(bean.getDiscountRate()) + " 折";
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new TextAppearanceSpan(null, 0, mActivity.getResources().getDimensionPixelSize(R.dimen.home_bottom_text_size), null, null), str.length() - 2, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            tv_discount_amt.setText(style);
        } else {
            String str = StringUtils.BigDecimal2Str(bean.getAmt()) + " 元";
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new TextAppearanceSpan(null, 0, mActivity.getResources().getDimensionPixelSize(R.dimen.home_bottom_text_size), null, null), str.length() - 2, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            tv_discount_amt.setText(style);
        }
    }
}
