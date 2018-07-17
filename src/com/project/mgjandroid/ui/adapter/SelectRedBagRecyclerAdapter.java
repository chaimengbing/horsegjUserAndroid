package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.MyRedBagActivity;
import com.project.mgjandroid.ui.activity.SelectRedBagActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/11/14.
 */

public class SelectRedBagRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private List<RedBag> redBagList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    private boolean isExpand = false;
    private int platformNum = 0;
    private int platformNumDis = 0;

    public SelectRedBagRecyclerAdapter(Context context) {
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
            return new RedBagNumViewHolder(inflater.inflate(R.layout.item_type_platform, parent, false));
        } else {
            return new PlatFormViewHolder(inflater.inflate(R.layout.item_platform, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 2) {
            RedBagNumViewHolder redBagNumViewHolder = (RedBagNumViewHolder) holder;
            if (position == 0) {
                redBagNumViewHolder.redNumTextView.setText("可使用红包(" + platformNum + ")个");
            } else {
                redBagNumViewHolder.redNumTextView.setText("不可使用红包(" + platformNumDis + ")个");
            }
        } else {
            final PlatFormViewHolder platFormViewHolder = (PlatFormViewHolder) holder;
            final RedBag redBag = redBagList.get(position);
            if (redBag.getRestrictAmt() != null && redBag.getRestrictAmt().compareTo(BigDecimal.ZERO) > 0) {
                platFormViewHolder.restrictAmt.setText("满" + StringUtils.BigDecimal2Str(redBag.getRestrictAmt()) + "元可用");
            } else {
                platFormViewHolder.restrictAmt.setText("无门槛红包");
            }

            platFormViewHolder.nameTextView.setText(redBag.getName());

            if (redBag.getExpirationTime() != null) {
                platFormViewHolder.expirationTextView.setText("有效期至 " + redBag.getExpirationTime());
            } else {
                platFormViewHolder.expirationTextView.setText("");
            }

            if (redBag.getRestrictTime() != null) {
                platFormViewHolder.restrictTime.setText(redBag.getRestrictTime() + "可用");
            } else {
                platFormViewHolder.restrictTime.setText("");
            }

            platFormViewHolder.mobile.setText("限收货人手机号" + redBag.getMobile());

            String str = "¥" + StringUtils.BigDecimal2Str(redBag.getAmt());
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new TextAppearanceSpan(null, 0, mContext.getResources().getDimensionPixelSize(R.dimen.title_bar_text_size), null, null), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            if (redBag.getIsDisable() == 0) {
                //红包不可用
                platFormViewHolder.moneyNum.setTextColor(mContext.getResources().getColor(R.color.color_6));
                platFormViewHolder.rootView.setBackgroundResource(0);
                platFormViewHolder.oneLayout.setBackgroundResource(R.drawable.redbag_disable_bg_1);
                platFormViewHolder.twoLayout.setBackgroundResource(R.drawable.redbag_disable_bg_2);
                final Drawable drawablePackup = mContext.getResources().getDrawable(R.drawable.icon_packup);
                final Drawable drawableExpand = mContext.getResources().getDrawable(R.drawable.icon_expand);
                drawablePackup.setBounds(0, 0, drawablePackup.getMinimumWidth(), drawablePackup.getMinimumHeight());
                drawableExpand.setBounds(0, 0, drawableExpand.getMinimumWidth(), drawableExpand.getMinimumHeight());
                platFormViewHolder.businessType.setCompoundDrawables(null, null, drawablePackup, null);
                platFormViewHolder.businessType.setTextColor(mContext.getResources().getColor(R.color.mine_number_color_red));
                platFormViewHolder.businessType.setText("不可用原因");
                platFormViewHolder.businessType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (redBag.getDisableList() != null && redBag.getDisableList().size() > 0) {
                            if (isExpand) {
                                isExpand = false;
                                platFormViewHolder.disTextview.setVisibility(View.GONE);
                                platFormViewHolder.businessType.setCompoundDrawables(null, null, drawablePackup, null);
                            } else {
                                platFormViewHolder.disTextview.setVisibility(View.VISIBLE);
                                platFormViewHolder.businessType.setCompoundDrawables(null, null, drawableExpand, null);
                                isExpand = true;
                                String result = "";
                                for (String disString : redBag.getDisableList()) {
                                    result += "" + disString + "\n";
                                }
                                platFormViewHolder.disTextview.setText(result.trim());
                            }
                        }
                    }
                });
            } else {
                if (redBag.isSelected()) {
                    platFormViewHolder.rootView.setBackgroundResource(R.drawable.reabag_selected_bg);
                } else {
                    platFormViewHolder.rootView.setBackgroundResource(R.drawable.normal_redbag_bg);
                }
                platFormViewHolder.oneLayout.setBackgroundResource(0);
                platFormViewHolder.twoLayout.setBackgroundResource(0);
                platFormViewHolder.moneyNum.setTextColor(mContext.getResources().getColor(R.color.mine_number_color_red));
                platFormViewHolder.businessType.setCompoundDrawables(null, null, null, null);
                platFormViewHolder.businessType.setTextColor(mContext.getResources().getColor(R.color.color_3));
                platFormViewHolder.businessType.setText("限品类：" + redBag.getBusinessTypeName());
            }
            platFormViewHolder.moneyNum.setText(style);

            platFormViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (redBag.getIsDisable() == 1) {
                        ((SelectRedBagActivity) mContext).setResult(SelectRedBagActivity.RED_BAG_MONEY, new Intent().putExtra(SelectRedBagActivity.RED_MONEY_BAG, redBag));
                        ((SelectRedBagActivity) mContext).finish();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return redBagList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == platformNum + 1) {
            return 2;
        }
        return 1;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.go_redbag_textview) {
            Intent intentRedBag = new Intent(mContext, MyRedBagActivity.class);
            intentRedBag.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intentRedBag);
        }
    }


    public void setPlatformNum(int platformNum) {
        this.platformNum = platformNum;
    }

    public void setPlatformNumDis(int platformNumDis) {
        this.platformNumDis = platformNumDis;
    }


    private class PlatFormViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rootView;
        TextView nameTextView;
        TextView expirationTextView;
        TextView restrictTime;
        TextView mobile;
        TextView moneyNum;
        TextView restrictAmt;
        TextView businessType;
        TextView disTextview;
        FrameLayout oneLayout;
        FrameLayout twoLayout;

        public PlatFormViewHolder(View view) {
            super(view);
            rootView = (RelativeLayout) view.findViewById(R.id.item_content_view);
            nameTextView = (TextView) view.findViewById(R.id.paltform_name_textview);
            expirationTextView = (TextView) view.findViewById(R.id.expiration_time_textview);
            restrictTime = (TextView) view.findViewById(R.id.restrict_time_textview);
            mobile = (TextView) view.findViewById(R.id.mobile_textview);
            moneyNum = (TextView) view.findViewById(R.id.redbag_money_textview);
            restrictAmt = (TextView) view.findViewById(R.id.restrict_amt_textview);
            businessType = (TextView) view.findViewById(R.id.business_type_textview);
            disTextview = (TextView) view.findViewById(R.id.dis_textview);
            oneLayout = (FrameLayout) view.findViewById(R.id.dis_one_layout);
            twoLayout = (FrameLayout) view.findViewById(R.id.dis_two_layout);

        }
    }

    private class RedBagNumViewHolder extends RecyclerView.ViewHolder {

        TextView redNumTextView;

        public RedBagNumViewHolder(View view) {
            super(view);
            redNumTextView = (TextView) view.findViewById(R.id.redbag_num_textview);
        }
    }

}
