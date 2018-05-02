package com.project.mgjandroid.ui.activity.pintuan;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MyGroupModel;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.ui.view.TimeTextView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User_Cjh on 2016/8/16.
 */
public class MyGroupPurchaseAdapter extends BaseListAdapter<MyGroupModel.ValueEntity> {
    public MyGroupPurchaseAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    private View.OnClickListener listener;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, MyGroupModel.ValueEntity bean, int position, View convertView, ViewGroup parent) {

        RoundImageView headerImage = holder.getView(R.id.my_group_header);
        LinearLayout toUser = holder.getView(R.id.my_group_to_user);
        toUser.setTag(position);
        toUser.setOnClickListener(listener);
        CornerImageView goodsImage = holder.getView(R.id.my_group_goods_image);
        FrameLayout toGroup = holder.getView(R.id.my_group_to_group);
        toGroup.setTag(position);
        toGroup.setOnClickListener(listener);
        if (CheckUtils.isNoEmptyStr(bean.getGroupBuy().getGroupBuyUser().getHeaderImg())) {
            ImageUtils.loadBitmap(mActivity, bean.getGroupBuy().getGroupBuyUser().getHeaderImg(), headerImage, R.drawable.default_group_user_avatar, Constants.getEndThumbnail(42, 42));
        }
        if (CheckUtils.isNoEmptyStr(bean.getGroupBuy().getImgs())) {
            ImageUtils.loadBitmap(mActivity, bean.getGroupBuy().getImgs().split(";")[0], goodsImage, R.drawable.horsegj_default, Constants.getEndThumbnail(75, 75));
        }

        TextView groupStatus = holder.getView(R.id.my_group_status);
        TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
        TextView tvInvite = holder.getView(R.id.to_invite_friend);
        TextView toEvaluate = holder.getView(R.id.to_evaluate_group);
        Integer status = bean.getStatus();
        if (status != null) {
            switch (status) {
                case -1:
                    groupStatus.setText("取消订单");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    break;
                case 0:
                    groupStatus.setText("订单创建");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    break;
                case 1:
                    groupStatus.setText("等待付款");
                    tvPay.setVisibility(View.VISIBLE);
                    tvInvite.setVisibility(View.GONE);
                    tvPay.setTimes(getTimeBetween1(new Date(), bean.getPaymentExpireTime()));
                    break;
                case 2:
                    groupStatus.setText("已支付,未成团");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    groupStatus.setText("待发货");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    break;
                case 4:
                    if (bean.getHasComments() == 0) {
                        groupStatus.setText("交易成功");
                    } else {
                        groupStatus.setText("已完成");
                    }
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    break;
                case 5:
                    groupStatus.setText("未成团,退款成功");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    break;
            }
        } else {
            groupStatus.setVisibility(View.GONE);
        }
        holder.setText(R.id.my_group_username, bean.getGroupBuy().getGroupBuyUser().getName());
        holder.setText(R.id.my_group_goods_name, bean.getGroupBuy().getGoodsName());
        holder.setText(R.id.my_group_goods_price, bean.getPrice() + "");
        holder.setText(R.id.my_group_goods_total_price, bean.getTotalPrice() + "");
        holder.setText(R.id.my_group_goods_total_count, "共" + bean.getQuantity() + "件商品  合计：");

        tvPay.setTag(position);
        tvPay.setOnClickListener(listener);
        tvInvite.setTag(position);
        tvInvite.setOnClickListener(listener);
        toEvaluate.setTag(position);
        toEvaluate.setOnClickListener(listener);

        if (bean.getStatus() == 4 && bean.getHasComments() == 0) {
            toEvaluate.setVisibility(View.VISIBLE);
        } else {
            toEvaluate.setVisibility(View.GONE);
        }
    }

    private long getTimeBetween1(Date serverTime, String laterTime) {
        if (CheckUtils.isEmptyStr(laterTime)) {
            return 0;
        }
        try {
            long time1 = serverTime.getTime();
            Date date2 = sdf.parse(laterTime);
            long time2 = date2.getTime();
            return time2 - time1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
