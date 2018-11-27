package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCoupon;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2017/3/6.
 */

public class GroupBuyingMerchantAdapter extends BaseAdapter {

    private List<GroupPurchaseMerchant> list;
    private Context context;
    private LayoutInflater mInflater;
    private DecimalFormat df = new DecimalFormat("0.00");
    private boolean fromMainPage = false;

    public GroupBuyingMerchantAdapter(Context context, boolean fromMainPage) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        list = new ArrayList<>();
        this.fromMainPage = fromMainPage;
    }

    public List<GroupPurchaseMerchant> getList() {
        return list;
    }

    public void setList(List<GroupPurchaseMerchant> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GroupPurchaseMerchant getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.group_buying_merchant_item, null);
            holder.rootView = convertView.findViewById(R.id.view_root);
            holder.img = (CornerImageView) convertView.findViewById(R.id.marchant_img);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_merchant_name);
            holder.scoreBar = (RatingBar) convertView.findViewById(R.id.rb_score);
            holder.tvAveragePrice = (TextView) convertView.findViewById(R.id.tv_average_price);
            holder.tvRecommendedDishes = (TextView) convertView.findViewById(R.id.tv_recommended_dishes);
            holder.layoutActive = (LinearLayout) convertView.findViewById(R.id.layout_activity);
            holder.icTakeAway = (ImageView) convertView.findViewById(R.id.icon_take_away);
            holder.tvDistance = (TextView) convertView.findViewById(R.id.tv_distance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        showItem(convertView, position, holder);

        return convertView;
    }

    private void showItem(View convertView, int position, ViewHolder holder) {
        final GroupPurchaseMerchant merchant = list.get(position);
        if (merchant.getDistance() != null) {
            if (merchant.getDistance() > 1000) {
                Double d = merchant.getDistance() / 1000;
                holder.tvDistance.setText(df.format(d) + "km");
            } else {
                holder.tvDistance.setText(merchant.getDistance().intValue() + "m");
            }
        }

        if (!TextUtils.isEmpty(merchant.getHighLightName())) {
            String color = merchant.getHighLightName();
            color = color.replaceAll("<em>", "<font color='#ff9900'>");
            color = color.replace("</em>", "</font>");
            holder.tvName.setText(Html.fromHtml(color));
        } else {
            if (!TextUtils.isEmpty(merchant.getName()))
                holder.tvName.setText(merchant.getName());
        }

        if (fromMainPage) {
            holder.scoreBar.setVisibility(View.GONE);
            holder.layoutActive.setVisibility(View.GONE);
            holder.icTakeAway.setVisibility(View.GONE);
            if (CheckUtils.isNoEmptyList(merchant.getGroupPurchaseCouponList())) {
                final GroupPurchaseCoupon coupon = merchant.getGroupPurchaseCouponList().get(0);
                coupon.setGroupPurchaseMerchant(merchant);
                if (!TextUtils.isEmpty(coupon.getImages())) {
                    ImageUtils.loadBitmap(context, coupon.getImages().split(";")[0], holder.img, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_MEERCHANT_ICON);
                } else {
                    holder.img.setImageResource(R.drawable.horsegj_default);
                }
                if (coupon.getType() == 2) {
                    holder.tvAveragePrice.setText(coupon.getGroupPurchaseName() +
                            (CheckUtils.isNoEmptyStr(merchant.getMerchantTag()) ? (" | " + merchant.getMerchantTag()) : ""));
                } else {
                    holder.tvAveragePrice.setText((CheckUtils.isNoEmptyStr(merchant.getMerchantTag()) ? merchant.getMerchantTag() : ""));
                }
                String price = "¥" + StringUtils.BigDecimal2Str(coupon.getPrice());
                String str = price;
                if (coupon.getType() == 2) {
                    if (coupon.getSumGroupPurchaseCouponGoodsOriginPrice() != null && coupon.getSumGroupPurchaseCouponGoodsOriginPrice().compareTo(BigDecimal.ZERO) > 0)
                        str += "  门市价¥" + StringUtils.BigDecimal2Str(coupon.getSumGroupPurchaseCouponGoodsOriginPrice());
                } else {
                    str += "  代¥" + StringUtils.BigDecimal2Str(coupon.getOriginPrice());
                }
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                style.setSpan(new ForegroundColorSpan(Color.parseColor("#f95046")), 0, price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_9)), price.length(), str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                style.setSpan(new TextAppearanceSpan(null, 0, context.getResources().getDimensionPixelSize(R.dimen.button_text_size), null, null), 0, price.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                style.setSpan(new TextAppearanceSpan(null, 0, context.getResources().getDimensionPixelSize(R.dimen.sp12), null, null), price.length(), str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.tvRecommendedDishes.setText(style);
                holder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Routers.open(context, ActivitySchemeManager.SCHEME + "groupPurchaseCoupon/" + coupon.getId());
                    }
                });
            }
        } else {
            if (merchant.getHasTakeaway() == 1) {
                holder.icTakeAway.setVisibility(View.VISIBLE);
            } else {
                holder.icTakeAway.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(merchant.getImgs())) {
                ImageUtils.loadBitmap(context, merchant.getImgs().split(";")[0], holder.img, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_MEERCHANT_ICON);
            } else {
                holder.img.setImageResource(R.drawable.horsegj_default);
            }
            if (merchant.getAverageScore() != null) {
                holder.scoreBar.setRating(merchant.getAverageScore().floatValue());
            } else {
                holder.scoreBar.setRating(5);
            }
            BigDecimal avgPersonPrice;
            if (merchant.getEvaluateCount() >= 10) {
                avgPersonPrice = merchant.getEvaluateAvgPersonPrice();
            } else {
                avgPersonPrice = merchant.getAvgPersonPrice();
            }
            if (avgPersonPrice != null) {
                holder.tvAveragePrice.setText("¥" + StringUtils.BigDecimal2Str(avgPersonPrice) + "/人");
            } else {
                holder.tvAveragePrice.setText("");
            }
            if (CheckUtils.isNoEmptyStr(merchant.getHighLightMerchantTag())) {
                String color = merchant.getHighLightMerchantTag();
                color = color.replaceAll("<em>", "<font color='#ff9900'>");
                color = color.replace("</em>", "</font>");
                holder.tvRecommendedDishes.setText(Html.fromHtml(color));
            } else {
                holder.tvRecommendedDishes.setText(merchant.getMerchantTag());
            }
            holder.layoutActive.removeAllViews();
            if (CheckUtils.isNoEmptyStr(merchant.getDiscountRatio())) {
                double discount = Integer.parseInt(merchant.getDiscountRatio()) * 0.01 * 10;
                addActive(holder.layoutActive, R.drawable.ic_buy, "在线支付，" + discount + "折");
            }

            if (CheckUtils.isNoEmptyList(merchant.getGroupPurchaseCouponList())) {
//                holder.promotionLine.setVisibility(View.VISIBLE);
//                holder.layoutActive.setVisibility(View.VISIBLE);
                String quan = "";
                String tuan = "";
                for (GroupPurchaseCoupon coupon : merchant.getGroupPurchaseCouponList()) {
                    if (coupon.getType() == 1) {
                        quan += StringUtils.BigDecimal2Str(coupon.getPrice()) + "代" + StringUtils.BigDecimal2Str(coupon.getOriginPrice()) + "元  ";
                    } else {
                        tuan += StringUtils.BigDecimal2Str(coupon.getPrice()) + "元" + coupon.getGroupPurchaseName() + "  ";
                    }
                }

                if (CheckUtils.isNoEmptyStr(tuan)) {
                    addActive(holder.layoutActive, R.drawable.group_buying_tuan, tuan.substring(0, tuan.length() - 2));
                }
                if (CheckUtils.isNoEmptyStr(quan)) {
                    addActive(holder.layoutActive, R.drawable.group_buying_quan, quan.substring(0, quan.length() - 2));
                }
            }
            if (CheckUtils.isNoEmptyStr(merchant.getDiscountRatio()) || CheckUtils.isNoEmptyList(merchant.getGroupPurchaseCouponList())) {
                holder.layoutActive.setVisibility(View.VISIBLE);
            } else {
                holder.layoutActive.setVisibility(View.GONE);
            }
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Routers.open(context, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + merchant.getId());
                }
            });
        }
    }

    private void addActive(LinearLayout layout, int resId, String string) {
        LinearLayout childLayout = new LinearLayout(context);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView image = new ImageView(context);
        image.setImageResource(resId);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        childLayout.addView(image, params);

        TextView tv = new TextView(context);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvParams.leftMargin = DipToPx.dip2px(context, 4);
        tv.setSingleLine();
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setTextColor(context.getResources().getColor(R.color.color_9));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv.setText(string);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        childLayout.addView(tv, tvParams);

        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsChild.topMargin = DipToPx.dip2px(context, 4);
        layout.addView(childLayout, paramsChild);
    }

    static class ViewHolder {
        CornerImageView img;
        ImageView icTakeAway;
        TextView tvName, tvAveragePrice, tvRecommendedDishes, tvDistance;
        RatingBar scoreBar;
        LinearLayout layoutActive;
        View promotionLine, rootView;
    }
}
