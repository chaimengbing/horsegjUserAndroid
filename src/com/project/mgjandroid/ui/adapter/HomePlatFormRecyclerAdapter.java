package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.project.mgjandroid.ui.activity.MyRedBagActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2016/11/14.
 */

public class HomePlatFormRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private List<RedBag> redBagList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    public HomePlatFormRecyclerAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<RedBag> redBagList) {
        if (redBagList == null) {
            this.redBagList = new ArrayList<>();
        } else {
            this.redBagList = redBagList;
        }
        notifyDataSetChanged();
    }

    public List<RedBag> getList() {
        return redBagList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            return new FoodViewHolder(inflater.inflate(R.layout.item_home_food, parent, false));
        } else {
            return new PlatFormViewHolder(inflater.inflate(R.layout.item_home_platfoom, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FoodViewHolder foodViewHolder = null;
        PlatFormViewHolder platFormViewHolder = null;
        RedBag redBag = redBagList.get(position);
        if (getItemViewType(position) == 2) {
            //外卖
            foodViewHolder = (FoodViewHolder) holder;
            if (redBag.getRestrictAmt() != null && redBag.getRestrictAmt().compareTo(BigDecimal.ZERO) > 0) {
                foodViewHolder.restrict_amt_textview.setText("满" + StringUtils.BigDecimal2Str(redBag.getRestrictAmt()) + "元可用");
            } else {
                foodViewHolder.restrict_amt_textview.setText("无门槛红包");
            }
            if (CheckUtils.isNoEmptyStr(redBag.getMerchantLogo())) {
                ImageUtils.loadBitmap(mContext, redBag.getMerchantLogo(), foodViewHolder.iv_merchant_icon, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
            } else {
                foodViewHolder.iv_merchant_icon.setImageResource(R.drawable.horsegj_default);
            }
            foodViewHolder.iv_merchant_icon.setmBorderThickness(1);

            if (!TextUtils.isEmpty(redBag.getMerchantName())) {
                foodViewHolder.name_textview.setText(redBag.getMerchantName());
            } else {
                foodViewHolder.name_textview.setText(redBag.getName());
            }

            if (redBag.getExpirationTime() != null) {
                foodViewHolder.expiration_time_textview.setText("有效期至 " + redBag.getExpirationTime());
            } else {
                foodViewHolder.expiration_time_textview.setText("");
            }

            StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
            if (redBag.getCouponType() == 3) {
                String str = StringUtils.BigDecimal2Str(redBag.getDiscountRate()) + "折";
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                style.setSpan(new TextAppearanceSpan(null, 0, mContext.getResources().getDimensionPixelSize(R.dimen.title_bar_text_size), null, null), str.length() - 1, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                style.setSpan(styleSpan, 1, style.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                foodViewHolder.redbag_money_textview.setText(style);
            } else {
                String str = "¥" + StringUtils.BigDecimal2Str(redBag.getAmt());
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                style.setSpan(new TextAppearanceSpan(null, 0, mContext.getResources().getDimensionPixelSize(R.dimen.title_bar_text_size), null, null), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                style.setSpan(styleSpan, 1, style.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                foodViewHolder.redbag_money_textview.setText(style);
            }

            foodViewHolder.go_redbag_textview.setText("去查看");
            foodViewHolder.type_textview.setText("外卖");
            foodViewHolder.go_redbag_textview.setOnClickListener(this);

        } else {
            platFormViewHolder = (PlatFormViewHolder) holder;
            if (redBag.getRestrictAmt() != null && redBag.getRestrictAmt().compareTo(BigDecimal.ZERO) > 0) {
                platFormViewHolder.restrict_amt_textview.setText("满" + StringUtils.BigDecimal2Str(redBag.getRestrictAmt()) + "元可用");
            } else {
                platFormViewHolder.restrict_amt_textview.setText("无门槛红包");
            }

            if (!TextUtils.isEmpty(redBag.getName())) {
                platFormViewHolder.name_textview.setText(redBag.getName());
            } else {
                platFormViewHolder.name_textview.setText(redBag.getMerchantName());
            }

            if (redBag.getExpirationTime() != null) {
                platFormViewHolder.expiration_time_textview.setText("有效期至 " + redBag.getExpirationTime());
            } else {
                platFormViewHolder.expiration_time_textview.setText("");
            }

            if (redBag.getRestrictTime() != null) {
                platFormViewHolder.restrict_time_textview.setText(redBag.getRestrictTime());
            } else {
                platFormViewHolder.restrict_time_textview.setText("");
            }
            StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
            String str = "¥" + StringUtils.BigDecimal2Str(redBag.getAmt());
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new TextAppearanceSpan(null, 0, mContext.getResources().getDimensionPixelSize(R.dimen.title_bar_text_size), null, null), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            style.setSpan(styleSpan, 1, style.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


            platFormViewHolder.redbag_money_textview.setText(style);
            platFormViewHolder.go_redbag_textview.setText("去查看");
            platFormViewHolder.type_textview.setText("限品类：" + redBag.getBusinessTypeName());
            platFormViewHolder.go_redbag_textview.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return redBagList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position >= redBagList.size()) {
//            return 2;
//        }
        return 1;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.go_redbag_textview) {
            ((HomeActivity) mContext).hiddenReceiverRedBagDialog();
            Intent intentRedBag = new Intent(mContext, MyRedBagActivity.class);
            intentRedBag.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intentRedBag);
        }
    }

    private class FoodViewHolder extends RecyclerView.ViewHolder {

        private CornerImageView iv_merchant_icon;
        private TextView name_textview;
        private TextView expiration_time_textview;
        private TextView type_textview;
        private TextView redbag_money_textview;
        private TextView restrict_amt_textview;
        private TextView go_redbag_textview;


        public FoodViewHolder(View view) {
            super(view);
            iv_merchant_icon = (CornerImageView) view.findViewById(R.id.merchant_icon);
            name_textview = (TextView) view.findViewById(R.id.name_textview);
            expiration_time_textview = (TextView) view.findViewById(R.id.expiration_time_textview);
            type_textview = (TextView) view.findViewById(R.id.type_textview);
            redbag_money_textview = (TextView) view.findViewById(R.id.redbag_money_textview);
            restrict_amt_textview = (TextView) view.findViewById(R.id.restrict_amt_textview);
            go_redbag_textview = (TextView) view.findViewById(R.id.go_redbag_textview);

        }
    }

    private class PlatFormViewHolder extends RecyclerView.ViewHolder {

        private TextView restrict_time_textview;
        private TextView name_textview;
        private TextView expiration_time_textview;
        private TextView type_textview;
        private TextView redbag_money_textview;
        private TextView restrict_amt_textview;
        private TextView go_redbag_textview;

        public PlatFormViewHolder(View view) {
            super(view);
            name_textview = (TextView) view.findViewById(R.id.name_textview);
            expiration_time_textview = (TextView) view.findViewById(R.id.expiration_time_textview);
            restrict_time_textview = (TextView) view.findViewById(R.id.restrict_time_textview);
            type_textview = (TextView) view.findViewById(R.id.type_textview);
            redbag_money_textview = (TextView) view.findViewById(R.id.redbag_money_textview);
            restrict_amt_textview = (TextView) view.findViewById(R.id.restrict_amt_textview);
            go_redbag_textview = (TextView) view.findViewById(R.id.go_redbag_textview);
//            itemHomeLayout = (RelativeLayout) view.findViewById(R.id.item_home_bg_layout);
//            itemHomeLayout1 = (RelativeLayout) view.findViewById(R.id.item_home_bg_layout1);
//            itemHomeLayout2 = (RelativeLayout) view.findViewById(R.id.layout_amt);

//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemHomeLayout1.getLayoutParams();
//            params.height = itemHomeLayout.getHeight();
//            itemHomeLayout1.setLayoutParams(params);
//            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) itemHomeLayout2.getLayoutParams();
//            params1.height = itemHomeLayout.getHeight();
//            itemHomeLayout2.setLayoutParams(params1);
        }
    }

}
