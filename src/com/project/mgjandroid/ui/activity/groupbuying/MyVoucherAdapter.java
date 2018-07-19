package com.project.mgjandroid.ui.activity.groupbuying;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.groupbuying.GroupBuyingVoucherListModel;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.utils.CheckUtils;

import java.util.List;

public class MyVoucherAdapter extends BaseListAdapter<GroupBuyingVoucherListModel.ValueBean> {

    public MyVoucherAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, GroupBuyingVoucherListModel.ValueBean bean, int position, View convertView, ViewGroup parent) {
       TextView amt = holder.getView(R.id.discount_amt);
       TextView name = holder.getView(R.id.merchant_name);
       TextView tv1 = holder.getView(R.id.tv_1);
       ImageView imgRedBag  = holder.getView(R.id.iv_redbag);
       RelativeLayout bgRight = holder.getView(R.id.layout_amt);
       TextView tvExpirationTime = holder.getView(R.id.expiration_time_textview);
       TextView tvRestrictAmt = holder.getView(R.id.restrict_amt_textview);
       TextView tvVouchersNum = holder.getView(R.id.vouchers_num_textview);
       amt.setText(""+bean.getOriginPrice());
        name.setText("代金券");
        tv1.setText("¥");
       if(CheckUtils.isEmptyStr(bean.getEndTime())){
           tvExpirationTime.setText("有效期："+bean.getExpireTime());
       }else {
           tvExpirationTime.setText("有效期："+bean.getEndTime());
       }
       if(bean.getIsCumulate()==0){
           tvRestrictAmt.setText("不可叠加使用");
        }else {
           tvRestrictAmt.setText("可叠加使用");
       }
       tvVouchersNum.setText("券码："+bean.getCouponCode());
       if(bean.getIsExpire()==0){
           amt.setTextColor(mActivity.getResources().getColor(R.color.price_red));
           tv1.setVisibility(View.VISIBLE);
           imgRedBag.setVisibility(View.GONE);
           if(bean.isChecked()){
               bgRight.setBackgroundResource(R.drawable.vouchers_right_sel_bg);
           }else {
               bgRight.setBackgroundResource(R.drawable.vouchers_right_bg);
           }
       }else {
           amt.setTextColor(mActivity.getResources().getColor(R.color.color_c));
           tv1.setVisibility(View.GONE);
           imgRedBag.setVisibility(View.VISIBLE);
       }


    }
}
