package com.project.mgjandroid.ui.adapter;

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
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuandi on 2016/5/27.
 */
public class RedBagAdapter extends BaseListAdapter<RedBag> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private boolean isFromOrderPre;
    private boolean canUse;

    public RedBagAdapter(int layoutId, Activity mActivity, boolean isFromOrderPre, boolean canUse) {
        super(layoutId, mActivity);
        this.isFromOrderPre = isFromOrderPre;
        this.canUse = canUse;
    }

    @Override
    protected void getRealView(ViewHolder holder, final RedBag bean, int position, View convertView, ViewGroup parent) {
        LinearLayout rootView = holder.getView(R.id.item_content_view);
        CornerImageView iv_merchant_icon = holder.getView(R.id.merchant_icon);
        TextView tv_merchant_name = holder.getView(R.id.merchant_name);
        TextView tv_effect_period = holder.getView(R.id.effect_period);
        TextView tv_discount_amt = holder.getView(R.id.discount_amt);
        TextView tv_restrict = holder.getView(R.id.tv_red_bag_restrict);
        TextView tv_type = holder.getView(R.id.tv_type);
        ImageView iv_redbag = holder.getView(R.id.iv_redbag);

        if (!TextUtils.isEmpty(bean.getMerchantName())) {
            tv_merchant_name.setText(bean.getMerchantName());
        } else {
            tv_merchant_name.setText(bean.getName());
        }

        if (bean.getExpirationTime() != null) {
            tv_effect_period.setText("有效期至 " + bean.getExpirationTime());
        } else {
            tv_effect_period.setText("");
        }

        if (bean.getCouponType() == 3) {
            String str = StringUtils.BigDecimal2Str(bean.getDiscountRate()) + "折";
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new TextAppearanceSpan(null, 0, mActivity.getResources().getDimensionPixelSize(R.dimen.title_bar_text_size), null, null), str.length() - 1, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            tv_discount_amt.setText(style);
        } else {
            String str = "¥" + StringUtils.BigDecimal2Str(bean.getAmt());
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new TextAppearanceSpan(null, 0, mActivity.getResources().getDimensionPixelSize(R.dimen.title_bar_text_size), null, null), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            tv_discount_amt.setText(style);
        }

        if (canUse) {
            if (bean.isSelected()) {
                rootView.setBackgroundResource(R.drawable.reabag_selected_bg);
//                tv_type.setTextColor(mActivity.getResources().getColor(R.color.white));
//                tv_discount_amt.setTextColor(mActivity.getResources().getColor(R.color.white));
//                tv_effect_period.setTextColor(mActivity.getResources().getColor(R.color.white));
//                tv_merchant_name.setTextColor(mActivity.getResources().getColor(R.color.white));
//                tv_restrict.setTextColor(mActivity.getResources().getColor(R.color.white));
            } else {
                rootView.setBackgroundResource(R.drawable.normal_redbag_bg);
                tv_type.setTextColor(mActivity.getResources().getColor(R.color.color_3));
                tv_discount_amt.setTextColor(mActivity.getResources().getColor(R.color.platform_color));
                tv_effect_period.setTextColor(mActivity.getResources().getColor(R.color.color_3));
                tv_merchant_name.setTextColor(mActivity.getResources().getColor(R.color.color_3));
                tv_restrict.setTextColor(mActivity.getResources().getColor(R.color.color_9));
            }
            iv_redbag.setVisibility(View.INVISIBLE);
        } else if (bean.getStatus() == 1) {
            rootView.setBackgroundResource(R.drawable.invalid_redbag_bg);
            tv_type.setTextColor(mActivity.getResources().getColor(R.color.color_c));
            iv_redbag.setVisibility(View.VISIBLE);
            iv_redbag.setImageResource(R.drawable.redbag_used);
            tv_discount_amt.setTextColor(mActivity.getResources().getColor(R.color.color_c));
        } else {
            rootView.setBackgroundResource(R.drawable.invalid_redbag_bg);
            tv_type.setTextColor(mActivity.getResources().getColor(R.color.color_c));
            iv_redbag.setVisibility(View.VISIBLE);
            iv_redbag.setImageResource(R.drawable.redbag_invalid);
            tv_discount_amt.setTextColor(mActivity.getResources().getColor(R.color.color_c));
        }

        if (bean.getType() == 3) {
            tv_type.setText("约车");
            tv_merchant_name.setText(bean.getName());
            String type = "";
            if (bean.getCouponType() == 3) {
                type = "折扣红包";
            } else {
                type = "抵价红包";
            }
            if (canUse && isFromOrderPre) {
                tv_restrict.setText(type);
            } else {
                String str = type + "(限区域使用)";
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                style.setSpan(new TextAppearanceSpan(null, 0, mActivity.getResources().getDimensionPixelSize(R.dimen.sp12), null, null), 4, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                tv_restrict.setText(style);
            }
            iv_merchant_icon.setImageResource(R.drawable.redbag_carhailing);
            iv_merchant_icon.setmBorderThickness(0);
        } else if (bean.getType() == 2) {
            tv_type.setText("外卖");
            if (bean.getRestrictAmt() != null && bean.getRestrictAmt().compareTo(BigDecimal.ZERO) > 0) {
                tv_restrict.setText("满" + StringUtils.BigDecimal2Str(bean.getRestrictAmt()) + "元可用");
            } else {
                tv_restrict.setText("下单即可使用");
            }
            if (CheckUtils.isNoEmptyStr(bean.getMerchantLogo())) {
                ImageUtils.loadBitmap(mActivity, bean.getMerchantLogo(), iv_merchant_icon, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
            } else {
                iv_merchant_icon.setImageResource(R.drawable.horsegj_default);
            }
            iv_merchant_icon.setmBorderThickness(1);
        } else if (bean.getType() == 1) {
            tv_type.setText("通用");
            if (bean.getRestrictAmt() != null && bean.getRestrictAmt().compareTo(BigDecimal.ZERO) > 0) {
                tv_restrict.setText("满" + StringUtils.BigDecimal2Str(bean.getRestrictAmt()) + "元可用");
            } else {
                tv_restrict.setText("下单即可使用");
            }
            if (CheckUtils.isNoEmptyStr(bean.getMerchantLogo())) {
                ImageUtils.loadBitmap(mActivity, bean.getMerchantLogo(), iv_merchant_icon, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
            } else {
                iv_merchant_icon.setImageResource(R.drawable.horsegj_default);
            }
            iv_merchant_icon.setmBorderThickness(1);
        } else {
            tv_type.setText("");
            if (bean.getRestrictAmt() != null && bean.getRestrictAmt().compareTo(BigDecimal.ZERO) > 0) {
                tv_restrict.setText("满" + StringUtils.BigDecimal2Str(bean.getRestrictAmt()) + "元可用");
            } else {
                tv_restrict.setText("下单即可使用");
            }
            if (CheckUtils.isNoEmptyStr(bean.getMerchantLogo())) {
                ImageUtils.loadBitmap(mActivity, bean.getMerchantLogo(), iv_merchant_icon, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
            } else {
                iv_merchant_icon.setImageResource(R.drawable.horsegj_default);
            }
            iv_merchant_icon.setmBorderThickness(1);
        }

        if (canUse && !isFromOrderPre) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == 2) {
                        Intent intent = new Intent(mActivity, CommercialActivity.class);
                        intent.putExtra(CommercialActivity.MERCHANT_ID, bean.getMerchantId().intValue());
                        mActivity.startActivity(intent);
                    } else if (bean.getType() == 3) {
                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + "callCar");
                    }
                }
            });
        } else if (isFromOrderPre && canUse) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("red_bag", bean);
                    mActivity.setResult(MyRedBagActivity.RESPONSE_CHOOSE_RED_BAG, intent);
                    mActivity.finish();
                }
            });
        }
    }
}
