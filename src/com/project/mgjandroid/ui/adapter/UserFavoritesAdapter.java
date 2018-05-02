package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.UserFavorites;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UserFavoritesAdapter extends BaseAdapter {
    private ArrayList<UserFavorites> list;
    private Context context;
    private CustomDialog mDialog;
    private MyClickListener clickListener;
    private LayoutInflater mInflater;
    private DecimalFormat df = new DecimalFormat("0.0");
    private boolean showEditStatus = false;

    public UserFavoritesAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public ArrayList<UserFavorites> getList() {
        return list;
    }

    public void setList(ArrayList<UserFavorites> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public void setShowEditStatus(boolean showEditStatus) {
        this.showEditStatus = showEditStatus;
        notifyDataSetChanged();
    }

    public void setClickListener(MyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.commercial_list_item_new, null);
            holder.promotionLine = convertView.findViewById(R.id.promotion_line);
            holder.imgEditStatus = (ImageView) convertView.findViewById(R.id.list_item_edit_status);
            holder.img = (CornerImageView) convertView.findViewById(R.id.restaurant_list_item_img);
            holder.imgStatus = (ImageView) convertView.findViewById(R.id.restaurant_list_item_img_status);
            holder.tvPickGoodsCount = (TextView) convertView.findViewById(R.id.pick_goods_count);
            holder.tvName = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_name);
            holder.tvSendPrice = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_sendprice);
            holder.scoreBar = (RatingBar) convertView.findViewById(R.id.restaurant_list_item_rat_score);
            holder.tvScore = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_score);
            holder.tvInSales = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_insales);
            holder.tvShippingFee = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_shipping_fee);
            holder.tvMianShippingFee = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_mian_shipping_fee);
            holder.tvTips = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_tips);
            holder.layoutImgs = (LinearLayout) convertView.findViewById(R.id.restaurant_list_item_layout_img);
            holder.layoutActive = (LinearLayout) convertView.findViewById(R.id.restaurant_list_item_layout_active);
            holder.layoutActiveHide = (LinearLayout) convertView.findViewById(R.id.restaurant_list_item_layout_active_hide);
            holder.tvActiveCount = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_active_count);
            holder.imgActive = (ImageView) convertView.findViewById(R.id.restaurant_list_item_iv_active);
            holder.tvTimeDistance = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_time_distance);
            holder.tvShipFeeNew = (TextView) convertView.findViewById(R.id.restaurant_list_item_ship_fee);
            holder.verticalLine = convertView.findViewById(R.id.restaurant_list_item_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
            if (list.get(position).getMerchantType() == 2) {
                showGroupPurchaseMerchantItem(convertView, list.get(position).getGroupPurchaseMerchant(), holder, position, list.get(position).getHasDel());
            } else if (list.get(position).getMerchantType() == 0) {
                showItem(convertView, list.get(position).getMerchant(), holder, position, list.get(position).getHasDel());
            }
        }

        if (showEditStatus) {
            holder.imgEditStatus.setVisibility(View.VISIBLE);
            if (list.get(position).isSelected()) {
                holder.imgEditStatus.setImageResource(R.drawable.market_cart_checked);
            } else {
                holder.imgEditStatus.setImageResource(R.drawable.market_cart_unselect);
            }
            final ViewHolder finalHolder = holder;
            holder.imgEditStatus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).isSelected()) {
                        list.get(position).setSelected(false);
                        finalHolder.imgEditStatus.setImageResource(R.drawable.market_cart_unselect);
                    } else {
                        list.get(position).setSelected(true);
                        finalHolder.imgEditStatus.setImageResource(R.drawable.market_cart_checked);
                    }
                    clickListener.doClick(position);
                }
            });
        } else {
            holder.imgEditStatus.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView img, imgActive, imgStatus, imgEditStatus;
        TextView tvName, tvSendPrice, tvScore, tvInSales, tvShippingFee, tvMianShippingFee, tvTips, tvActiveCount, tvTimeDistance, tvPickGoodsCount, tvShipFeeNew;
        RatingBar scoreBar;
        LinearLayout layoutImgs, layoutActive, layoutActiveHide;
        View promotionLine, verticalLine;
    }


    private void showGroupPurchaseMerchantItem(View view, final GroupPurchaseMerchant merchant, ViewHolder holder, int position, final int hasDel) {
        if (merchant != null) {
            holder.img.setImageResource(R.drawable.horsegj_default);
            if (merchant.getStatus() == 0) {
                holder.imgStatus.setVisibility(View.VISIBLE);
            } else {
                holder.imgStatus.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(merchant.getImgs()))
                ImageUtils.loadBitmap(context, merchant.getImgs().split(";")[0], holder.img, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_MEERCHANT_ICON);
            if (!TextUtils.isEmpty(merchant.getName()))
                holder.tvName.setText(merchant.getName());
            holder.tvSendPrice.setText("");

            BigDecimal avgPersonPrice;
            if (merchant.getEvaluateCount() >= 10) {
                avgPersonPrice = merchant.getEvaluateAvgPersonPrice();
            } else {
                avgPersonPrice = merchant.getAvgPersonPrice();
            }
            if (avgPersonPrice != null) {
                holder.tvInSales.setText("¥" + StringUtils.BigDecimal2Str(avgPersonPrice) + "/人");
            } else {
                holder.tvInSales.setText("");
            }

            if (merchant.getAverageScore() != null) {
                holder.scoreBar.setRating(merchant.getAverageScore().floatValue());
            } else {
                holder.scoreBar.setRating(0);
            }
            holder.tvScore.setText("");
            holder.tvTimeDistance.setText((CheckUtils.isNoEmptyStr(merchant.getMerchantRecommend()) ? merchant.getMerchantRecommend() : ""));
            holder.tvShipFeeNew.setText("");
            holder.tvShippingFee.setText("");
            holder.verticalLine.setVisibility(View.GONE);
            holder.tvTips.setVisibility(View.GONE);
            holder.tvTimeDistance.setVisibility(View.VISIBLE);

            holder.layoutImgs.removeAllViews();
            holder.layoutImgs.setVisibility(View.VISIBLE);
            TextView textView = new TextView(context);
            textView.setTextColor(ContextCompat.getColor(context, R.color.color_6));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            if (merchant.getDistance() != null) {
                if (merchant.getDistance() > 1000) {
                    Double d = merchant.getDistance() / 1000;
                    textView.setText(df.format(d) + "km");
                } else {
                    textView.setText(merchant.getDistance().intValue() + "m");
                }
            }
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.layoutImgs.addView(textView, layoutParams);

            holder.promotionLine.setVisibility(View.GONE);
            holder.tvActiveCount.setVisibility(View.GONE);
            holder.imgActive.setVisibility(View.GONE);
            holder.layoutActive.setVisibility(View.GONE);

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hasDel == 1) {
                        showMyDialog();
                        return;
                    }
                    Routers.open(context, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + merchant.getId());
                }
            });
        }
    }

    private void showItem(View view, final Merchant merchant, final ViewHolder holder, final int position, final int hasDel) {
        if (merchant != null) {
            holder.img.setImageResource(R.drawable.horsegj_default);
            if (merchant.getStatus() == 0) {
                holder.imgStatus.setVisibility(View.VISIBLE);
            } else {
                holder.imgStatus.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(merchant.getLogo()))
                ImageUtils.loadBitmap(context, merchant.getLogo(), holder.img, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_MEERCHANT_ICON);
            if (!TextUtils.isEmpty(merchant.getName()))
                holder.tvName.setText(merchant.getName());

            if (merchant.getDistance() != null) {
                if (merchant.getDistance() > 1000) {
                    Double d = merchant.getDistance() / 1000;
                    holder.tvSendPrice.setText(df.format(d) + "km");
                } else {
                    holder.tvSendPrice.setText(merchant.getDistance().intValue() + "m");
                }
            }
            holder.tvInSales.setText("月售" + merchant.getMonthSaled() + "单");
            if (merchant.getAverageScore() != null) {
                holder.scoreBar.setRating(merchant.getAverageScore().floatValue());
                holder.tvScore.setText(CommonUtils.BigDecimal2Str(merchant.getAverageScore()));
            } else {
                holder.scoreBar.setRating(0);
                holder.tvScore.setText("");
            }
            if (merchant.getMinPrice() != null)
                holder.tvTimeDistance.setText("起送价 ¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice()));
            else {
                holder.tvTimeDistance.setText("起送价 ¥0");
            }
            if (merchant.getShipFee().compareTo(BigDecimal.ZERO) == 0) {
                holder.tvShipFeeNew.setText("免配送费");
            } else {
                holder.tvShipFeeNew.setText("配送费" + " ¥" + StringUtils.BigDecimal2Str(merchant.getShipFee()));
            }
            holder.tvShippingFee.setText(merchant.getAvgDeliveryTime() + "分钟");
            holder.verticalLine.setVisibility(View.VISIBLE);
            holder.tvTips.setVisibility(View.GONE);
            holder.tvTimeDistance.setVisibility(View.VISIBLE);

            if (CheckUtils.isNoEmptyStr(merchant.getPayments())) {
                String[] payments = merchant.getPayments().split(",");
                holder.layoutImgs.removeAllViews();
                holder.layoutImgs.setVisibility(View.VISIBLE);
                for (int i = 0; i < payments.length; i++) {
                    if (Integer.parseInt(payments[i]) == 1) {
                        ImageView icon = new ImageView(context);
                        int resId = chooseIcon(3);
                        icon.setBackgroundResource(resId);
                        LayoutParams params = new LayoutParams(DipToPx.dip2px(context, 11f), DipToPx.dip2px(context, 11f));
                        params.leftMargin = DipToPx.dip2px(context, 2);
                        holder.layoutImgs.addView(icon, params);
                    }
                }
            } else {
                holder.layoutImgs.setVisibility(View.GONE);
            }

            holder.promotionLine.setVisibility(View.GONE);
            holder.tvActiveCount.setVisibility(View.GONE);
            holder.imgActive.setVisibility(View.GONE);
            holder.layoutActive.setVisibility(View.GONE);

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hasDel == 1) {
                        showMyDialog();
                        return;
                    }
                    Intent intent = new Intent(context, CommercialActivity.class);
                    intent.putExtra(CommercialActivity.MERCHANT_ID, merchant.getId().intValue());
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                }
            });
        }
    }

    private void showMyDialog() {
        if (mDialog == null) {
            mDialog = new CustomDialog(context, new CustomDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    mDialog.dismiss();
                }

                @Override
                public void onExit() {
                    mDialog.dismiss();
                }

            }, "我知道了", "", "", "该商家已下架");
            mDialog.show();
        } else {
            mDialog.show();
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

    public interface MyClickListener {
        void doClick(int position);
    }
}

