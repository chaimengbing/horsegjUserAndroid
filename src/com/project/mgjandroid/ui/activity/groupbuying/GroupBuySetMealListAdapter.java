package com.project.mgjandroid.ui.activity.groupbuying;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCouponGoods;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCouponGoodsType;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.StringUtils;

public class GroupBuySetMealListAdapter extends BaseListAdapter<GroupPurchaseCouponGoodsType> {

    public GroupBuySetMealListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, GroupPurchaseCouponGoodsType bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.name);
        TextView tvCount = holder.getView(R.id.count);
        TextView tvPrice = holder.getView(R.id.price);
        if(CheckUtils.isNoEmptyList(bean.getGroupPurchaseCouponGoodsList())){
            tvName.setText("·"+bean.getGroupPurchaseCouponGoodsList().get(position).getName());
            tvCount.setText("("+bean.getGroupPurchaseCouponGoodsList().get(position).getQuantity() + "份)");
            tvPrice.setText("¥" + StringUtils.BigDecimal2Str(bean.getGroupPurchaseCouponGoodsList().get(position).getOriginPrice()));
        }
    }


}
