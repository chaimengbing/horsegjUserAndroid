package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.PromotionActivity;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by ning on 2016/4/29.
 */
public class SearchListAdapter extends BaseListAdapter<Merchant> {

    private DecimalFormat df = new DecimalFormat("0.00");
    private View.OnClickListener listener;

    public SearchListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, Merchant bean, int position, View convertView, ViewGroup parent) {
//        holder.setText(R.id.search_name,bean.getName());
//        holder.setText(R.id.search_time,bean.getAvgDeliveryTime() + "分钟送达");
//        if(bean.getDistance()!=null){
//            String distance = "";
//            if (bean.getDistance() > 1000) {
//                Double d = bean.getDistance() / 1000;
//                distance = df.format(d) + "km";
//            } else {
//                distance = bean.getDistance().intValue() + "m";
//            }
//            holder.setText(R.id.search_distance, distance);
//        }

        ImageView visibleLiveImageView = holder.getView(R.id.visible_live_imageview);
        if (bean.getHasVisualRestaurant() == 1) {
            visibleLiveImageView.setVisibility(View.VISIBLE);
        } else {
            visibleLiveImageView.setVisibility(View.INVISIBLE);
        }

        if (bean != null) {
            ImageView imgStatus = holder.getView(R.id.search_list_item_img_status);
            CornerImageView img = holder.getView(R.id.search_list_item_img);
            ImageView brantImageView = holder.getView(R.id.brant_imageview);
            if (bean.getStatus() == 0) {
                imgStatus.setVisibility(View.VISIBLE);
            } else {
                imgStatus.setVisibility(View.GONE);
            }
            if (bean.getIsBrandMerchant() == 1){
                brantImageView.setVisibility(View.VISIBLE);
            }else {
                brantImageView.setVisibility(View.GONE);
            }
            if (CheckUtils.isNoEmptyStr(bean.getLogo())) {
                ImageUtils.loadBitmap(mActivity, bean.getLogo(), img, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
            } else {
                img.setImageResource(R.drawable.horsegj_default);
            }
            TextView merchantName = holder.getView(R.id.restaurant_list_item_tv_name);
            if (!TextUtils.isEmpty(bean.getHighLights())) {
                String color = bean.getHighLights();
                color = color.replaceAll("<em>", "<font color='#ff9900'>");
                color = color.replace("</em>", "</font>");
                merchantName.setText(Html.fromHtml(color));
            } else {
                if (!TextUtils.isEmpty(bean.getName()))
                    merchantName.setText(bean.getName());
            }
            if (bean.getDistance() != null) {
                if (bean.getDistance() > 1000) {
                    Double d = bean.getDistance() / 1000;
                    holder.setText(R.id.search_list_item_tv_distance, df.format(d) + "km");
                } else {
                    holder.setText(R.id.search_list_item_tv_distance, bean.getDistance().intValue() + "m");
                }
            }
            holder.setText(R.id.search_list_item_tv_insales, "月售" + bean.getMonthSaled() + "单");
            RatingBar ratingBar = holder.getView(R.id.search_list_item_rat_score);
            if (bean.getAverageScore() != null) {
                ratingBar.setRating(bean.getAverageScore().floatValue());
                holder.setText(R.id.search_list_item_tv_score, CommonUtils.BigDecimal2Str(bean.getAverageScore()));
            } else {
                ratingBar.setRating(0);
                holder.setText(R.id.search_list_item_tv_score, "");
            }
            if (bean.getMinPrice() != null)
                holder.setText(R.id.search_list_item_tv_shipping_fee, "起送价 ¥" + StringUtils.BigDecimal2Str(bean.getMinPrice()));
            else {
                holder.setText(R.id.search_list_item_tv_shipping_fee, "起送价 ¥0");
            }
            if (bean.getShipFee().compareTo(BigDecimal.ZERO) == 0) {
                holder.setText(R.id.restaurant_list_item_ship_fee, "免配送费");
            } else {
                holder.setText(R.id.restaurant_list_item_ship_fee, "配送费" + " ¥" + StringUtils.BigDecimal2Str(bean.getShipFee()));
            }
            holder.setText(R.id.search_list_item_tv_shipping_time, bean.getAvgDeliveryTime() + "分钟");
//            LinearLayout layoutImgs = holder.getView(R.id.restaurant_list_item_layout_img);
//            if (CheckUtils.isNoEmptyStr(bean.getPayments())) {
//                String[] payments = bean.getPayments().split(",");
//                layoutImgs.removeAllViews();
//                layoutImgs.setVisibility(View.VISIBLE);
//                for (int i = 0; i < payments.length; i++) {
//                    if (Integer.parseInt(payments[i]) == 1) {
//                        ImageView icon = new ImageView(mActivity);
//                        int resId = chooseIcon(3);
//                        icon.setBackgroundResource(resId);
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DipToPx.dip2px(mActivity, 11f), DipToPx.dip2px(mActivity, 11f));
//                        params.leftMargin = DipToPx.dip2px(mActivity, 2);
//                        layoutImgs.addView(icon, params);
//                    }
//                }
//            } else {
//                layoutImgs.setVisibility(View.GONE);
//            }
            TextView tvActiveCount = holder.getView(R.id.restaurant_list_item_tv_active_count);
            final LinearLayout layoutActiveHide = holder.getView(R.id.restaurant_list_item_layout_active_hide);
            final ImageView imgActive = holder.getView(R.id.restaurant_list_item_iv_active);
            tvActiveCount.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (layoutActiveHide.getVisibility() == View.VISIBLE) {
                        layoutActiveHide.setVisibility(View.GONE);
                        AnimatorUtils.rotating(imgActive, 180, 360);
                    } else {
                        layoutActiveHide.setVisibility(View.VISIBLE);
                        AnimatorUtils.rotating(imgActive, 0, 180);
                    }
                }
            });
            imgActive.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (layoutActiveHide.getVisibility() == View.VISIBLE) {
                        layoutActiveHide.setVisibility(View.GONE);
                        AnimatorUtils.rotating(imgActive, 180, 360);
                    } else {
                        layoutActiveHide.setVisibility(View.VISIBLE);
                        AnimatorUtils.rotating(imgActive, 0, 180);
                    }
                }
            });
            View promotionLine = holder.getView(R.id.promotion_line);
            LinearLayout layoutActive = holder.getView(R.id.restaurant_list_item_layout_active);
            if (CheckUtils.isNoEmptyList(bean.getPromotionActivityList())) {
                promotionLine.setVisibility(View.VISIBLE);
                if (bean.getPromotionActivityList().size() > 2) {
                    tvActiveCount.setVisibility(View.VISIBLE);
                    tvActiveCount.setText(bean.getPromotionActivityList().size() + "个活动");
                    imgActive.setVisibility(View.VISIBLE);
                } else {
                    tvActiveCount.setVisibility(View.GONE);
                    imgActive.setVisibility(View.GONE);
                }
                layoutActive.setVisibility(View.VISIBLE);
                layoutActive.removeAllViews();
                layoutActiveHide.removeAllViews();
                for (int i = 0; i < bean.getPromotionActivityList().size(); i++) {
                    PromotionActivity promotion = bean.getPromotionActivityList().get(i);
                    if (i > 1) {
                        addPromotion(layoutActiveHide, promotion);
                    } else {
                        addPromotion(layoutActive, promotion);
                    }
                }
            } else {
                promotionLine.setVisibility(View.GONE);
                tvActiveCount.setVisibility(View.GONE);
                imgActive.setVisibility(View.GONE);
                layoutActive.setVisibility(View.GONE);
            }
            RelativeLayout view = holder.getView(R.id.list_item_rl);
            view.setTag(bean);
            view.setOnClickListener(listener);
            LinearLayout goodsLayout = holder.getView(R.id.search_list_goods_layout);
            goodsLayout.removeAllViews();
            if (CheckUtils.isNoEmptyList(bean.getGoodsList()) && bean.getGoodsList().size() <= 8) {
                for (Goods good : bean.getGoodsList()) {
                    good.setType(bean.getType());
                }
                if (bean.getGoodsList().size() <= 2) {
                    for (int i = 0; i < bean.getGoodsList().size(); i++) {
                        Goods goods = bean.getGoodsList().get(i);
                        View viewS = mInflater.inflate(R.layout.item_search_goods, null);
                        RelativeLayout goodsView = (RelativeLayout) viewS.findViewById(R.id.search_goods_layout);
                        TextView name = (TextView) goodsView.findViewById(R.id.search_goods_name);
                        LinearLayout priceLayout = (LinearLayout) goodsView.findViewById(R.id.restaurant_list_item_shipping_fee_layout);
                        TextView price = (TextView) goodsView.findViewById(R.id.search_goods_price);
                        TextView sales = (TextView) goodsView.findViewById(R.id.search_goods_sale);
                        View endLine = viewS.findViewById(R.id.end_line);
                        name.setText(goods.getName());
                        if (CheckUtils.isNoEmptyList(goods.getGoodsSpecList())) {
                            if (goods.getGoodsSpecList().size() > 1) {
                                BigDecimal minPrice = goods.getGoodsSpecList().get(0).getPrice();
                                for (GoodsSpec spec : goods.getGoodsSpecList()) {
                                    if (minPrice.compareTo(spec.getPrice()) > 0) {
                                        minPrice = spec.getPrice();
                                    }
                                }
                                price.setText(minPrice + "起");
                            } else {
                                price.setText(goods.getGoodsSpecList().get(0).getPrice() + "");
                            }
                        } else {
                            priceLayout.setVisibility(View.INVISIBLE);
                        }
                        sales.setText("月售" + goods.getMonthSaled() + "单");
                        if (i == bean.getGoodsList().size() - 1) {
                            endLine.setVisibility(View.VISIBLE);
                        } else {
                            endLine.setVisibility(View.GONE);
                        }
                        goodsLayout.addView(viewS);
                        goodsView.setTag(goods);
                        goodsView.setOnClickListener(listener);
                    }
                } else {
                    if (bean.isShowGoods()) {
                        for (int i = 0; i < bean.getGoodsList().size(); i++) {
                            Goods goods = bean.getGoodsList().get(i);
                            View viewS = mInflater.inflate(R.layout.item_search_goods, null);
                            RelativeLayout goodsView = (RelativeLayout) viewS.findViewById(R.id.search_goods_layout);
                            TextView name = (TextView) goodsView.findViewById(R.id.search_goods_name);
                            TextView price = (TextView) goodsView.findViewById(R.id.search_goods_price);
                            LinearLayout priceLayout = (LinearLayout) goodsView.findViewById(R.id.restaurant_list_item_shipping_fee_layout);
                            TextView sales = (TextView) goodsView.findViewById(R.id.search_goods_sale);
                            View endLine = viewS.findViewById(R.id.end_line);
                            name.setText(goods.getName());
                            if (CheckUtils.isNoEmptyList(goods.getGoodsSpecList())) {
                                if (goods.getGoodsSpecList().size() > 1) {
                                    BigDecimal minPrice = goods.getGoodsSpecList().get(0).getPrice();
                                    for (GoodsSpec spec : goods.getGoodsSpecList()) {
                                        if (minPrice.compareTo(spec.getPrice()) > 0) {
                                            minPrice = spec.getPrice();
                                        }
                                    }
                                    price.setText(minPrice + "起");
                                } else {
                                    price.setText(goods.getGoodsSpecList().get(0).getPrice() + "");
                                }
                            } else {
                                priceLayout.setVisibility(View.INVISIBLE);
                            }
                            sales.setText("月售" + goods.getMonthSaled() + "单");
                            goodsLayout.addView(viewS);
                            goodsView.setTag(goods);
                            goodsView.setOnClickListener(listener);
                            if (i == bean.getGoodsList().size() - 1) {
                                endLine.setVisibility(View.VISIBLE);
                            } else {
                                endLine.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        for (int i = 0; i < 2; i++) {
                            Goods goods = bean.getGoodsList().get(i);
                            View viewS = mInflater.inflate(R.layout.item_search_goods, null);
                            RelativeLayout goodsView = (RelativeLayout) viewS.findViewById(R.id.search_goods_layout);
                            TextView name = (TextView) goodsView.findViewById(R.id.search_goods_name);
                            TextView price = (TextView) goodsView.findViewById(R.id.search_goods_price);
                            LinearLayout priceLayout = (LinearLayout) goodsView.findViewById(R.id.restaurant_list_item_shipping_fee_layout);
                            TextView sales = (TextView) goodsView.findViewById(R.id.search_goods_sale);
                            name.setText(goods.getName());
                            if (CheckUtils.isNoEmptyList(goods.getGoodsSpecList())) {
                                if (goods.getGoodsSpecList().size() > 1) {
                                    BigDecimal minPrice = goods.getGoodsSpecList().get(0).getPrice();
                                    for (GoodsSpec spec : goods.getGoodsSpecList()) {
                                        if (minPrice.compareTo(spec.getPrice()) > 0) {
                                            minPrice = spec.getPrice();
                                        }
                                    }
                                    price.setText(minPrice + "起");
                                } else {
                                    price.setText(goods.getGoodsSpecList().get(0).getPrice() + "");
                                }
                            } else {
                                priceLayout.setVisibility(View.INVISIBLE);
                            }
                            sales.setText("月售" + goods.getMonthSaled() + "单");
                            goodsLayout.addView(viewS);
                            goodsView.setTag(goods);
                            goodsView.setOnClickListener(listener);
                        }
                        View hideGoods = mInflater.inflate(R.layout.item_search_hide, null);
                        TextView textView = (TextView) hideGoods.findViewById(R.id.hide_text);
                        textView.setText("查看其他" + (bean.getGoodsList().size() - 2) + "个相关商品");
                        goodsLayout.addView(hideGoods);
                        hideGoods.setTag(bean);
                        hideGoods.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Merchant bean = (Merchant) v.getTag();
                                bean.setIsShowGoods(true);
                                notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        }
    }

    private int chooseIcon(int id) {
        switch (id) {
            case 1:
                return R.drawable.piao;
            case 2:
                return R.drawable.bao;
            case 3:
                return R.drawable.fu;
            default:
                return R.drawable.pei;
        }
    }

    private void addPromotion(LinearLayout layout, PromotionActivity promotion) {
        LinearLayout childLayout = new LinearLayout(mActivity);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        if (CheckUtils.isNoEmptyStr(promotion.getPromoImg())) {
            ImageView image = new ImageView(mActivity);
            ImageUtils.loadBitmap(mActivity, promotion.getPromoImg(), image, R.drawable.jian, "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DipToPx.dip2px(mActivity, 12), DipToPx.dip2px(mActivity, 12));
            childLayout.addView(image, params);
        }
        if (CheckUtils.isNoEmptyStr(promotion.getPromoName())) {
            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = DipToPx.dip2px(mActivity, 4);
            tv.setText(promotion.getPromoName());
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextColor(mActivity.getResources().getColor(R.color.color_9));
            tv.setTextSize(11);
            childLayout.addView(tv, params);
        }
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsChild.topMargin = DipToPx.dip2px(mActivity, 6);
        layout.addView(childLayout, paramsChild);
    }
}
