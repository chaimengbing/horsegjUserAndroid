package com.project.mgjandroid.ui.activity.groupbuying;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCoupon;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GroupBuyMealListAdapter extends BaseListAdapter<GroupPurchaseCoupon>{

    private boolean isExpand = false;

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }

    public GroupBuyMealListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    public int getCount() {
        if (mDatas != null && mDatas.size() < 2) {
            return mDatas.size();
        } else {
            return isExpand ? mDatas.size() : 2;
        }
    }

    @Override
    protected void getRealView(ViewHolder holder, final GroupPurchaseCoupon bean, int position, View convertView, ViewGroup parent) {
        CornerImageView icon = holder.getView(R.id.img);
        TextView tvOriginPrice =holder.getView(R.id.tv_origin_price);
        TextView tvOption =holder.getView(R.id.tv_option);
        TextView tvPayBill =holder.getView(R.id.tv_pay_bill1);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvSold =holder.getView(R.id.tv_sold);
        TextView tvPrice =holder.getView(R.id.tv_price);
        TextView tvVip =holder.getView(R.id.tv_vip);
        if (CheckUtils.isNoEmptyStr(bean.getImages())) {
            ImageUtils.loadBitmap(mActivity, bean.getImages().split(";")[0], icon, R.drawable.horsegj_default, Constants.getEndThumbnail(130, 110));
        }
        String groupPurchaseName = bean.getGroupPurchaseName();
        tvName.setText(bean.getGroupPurchaseName());
        tvPrice.setText("¥" + StringUtils.BigDecimal2Str(bean.getPrice()));
        if (bean.getSumGroupPurchaseCouponGoodsOriginPrice() != null && bean.getSumGroupPurchaseCouponGoodsOriginPrice().compareTo(BigDecimal.ZERO) > 0) {
            tvOriginPrice.setText("门市价¥" + StringUtils.BigDecimal2Str(bean.getSumGroupPurchaseCouponGoodsOriginPrice()));
        }
        if(bean.getIsPurchaseRestriction()==3){
            tvVip.setVisibility(View.VISIBLE);
            tvOption.setText(bean.getIsBespeak() == 0 ? "免预约" : "需预约 ");
        }else {
            tvVip.setVisibility(View.GONE);
            tvOption.setText((bean.getIsBespeak() == 0 ? "免预约 | " : "需预约 | ") + "不可叠加");
        }
        tvSold.setText("已售"+bean.getBuyCount());
        tvPayBill.setBackgroundResource(R.drawable.buy_bg);
        tvPayBill.setTextColor(mActivity.getResources().getColor(R.color.title_bar_bg));
        tvPayBill.setEnabled(true);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        if(CheckUtils.isNoEmptyStr(bean.getSellOutDates())){
            List<String> stringList = com.alibaba.fastjson.JSONArray.parseArray(bean.getSellOutDates(), String.class);
            for(String str : stringList){
                if(format.equals(str)){
                    tvPayBill.setBackgroundResource(R.drawable.buy_gary_bg);
                    tvPayBill.setTextColor(mActivity.getResources().getColor(R.color.white));
                    tvPayBill.setEnabled(false);
                    tvSold.setText("已售罄");
                    break;
                }
            }
        }

        tvPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!App.isLogin()) {
                    Intent intent = new Intent(mActivity, SmsLoginActivity.class);
                    mActivity.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(mActivity, BuyTicketActivity.class);
                intent2.putExtra("ticketName",bean.getGroupPurchaseName());
                intent2.putExtra("ticketPrice",bean.getPrice().doubleValue());
                intent2.putExtra("type",bean.getType());
                intent2.putExtra("bespeak",bean.getIsBespeak());
                intent2.putExtra("agentId",bean.getAgentId());
                intent2.putExtra("bespeakDayCount",bean.getBespeakDayCount());
                intent2.putExtra("groupPurchaseCoupon",bean);
                mActivity.startActivity(intent2);
            }
        });
    }
}
