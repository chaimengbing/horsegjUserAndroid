package com.project.mgjandroid.ui.activity.groupbuying;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderCouponCode;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class NewRefundListAdapter extends BaseListAdapter<GroupPurchaseOrderCouponCode> {

    private boolean isExpand = false;

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }

    public NewRefundListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    public int getCount() {
        if (mDatas != null && mDatas.size() < 3) {
            return mDatas.size();
        } else {
            return isExpand ? mDatas.size() : 3;
        }
    }

    @Override
    protected void getRealView(ViewHolder holder, GroupPurchaseOrderCouponCode bean, int position, View convertView, ViewGroup parent) {
        if (bean.getStatus() == 0 && bean.getIsExpire() == 0) {
            holder.setText(R.id.tv_coupon_code, "团购券："+ bean.getCouponCode());
        }
        ImageView rb = holder.getView(R.id.img_unselected);
        if (bean.isSelected()) {
            rb.setImageResource(R.drawable.refund_selected);
        } else {
            rb.setImageResource(R.drawable.refund_unselected);
        }
    }
}
