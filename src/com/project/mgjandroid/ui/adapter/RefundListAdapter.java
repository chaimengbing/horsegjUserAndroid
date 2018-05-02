package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupBuyingTest;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderCouponCode;
import com.project.mgjandroid.model.Entity;
import com.project.mgjandroid.utils.CommonUtils;

/**
 * Created by SunXueLiang on 2017-03-10.
 */

public class RefundListAdapter extends BaseListAdapter<GroupPurchaseOrderCouponCode> {


    public RefundListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, GroupPurchaseOrderCouponCode bean, int position, View convertView, ViewGroup parent) {

        if (bean.getStatus() == 0 && bean.getIsExpire() == 0) {
            holder.setText(R.id.tv_voucher, CommonUtils.getCouponCode(position + 1));
            holder.setText(R.id.tv_coupon_code, bean.getCouponCode());
        }
        ImageView rb = holder.getView(R.id.img_refund);
        if (bean.isSelected()) {
            rb.setImageResource(R.drawable.group_buying_right);
        } else {
            rb.setImageResource(R.drawable.group_buying_circle_gray);
        }
    }


}
