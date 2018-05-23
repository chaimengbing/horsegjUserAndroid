package com.project.mgjandroid.ui.activity.employment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationOrder;
import com.project.mgjandroid.bean.information.InformationOrderStatus;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.OrderType;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.activity.information.PayActivity;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.TimeTextView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User_Cjh on 2016/11/18.
 */
public class MyPublishOrderListAdapter extends BaseListAdapter<InformationOrder> {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MyPublishOrderListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, final InformationOrder bean, int position, View convertView, ViewGroup parent) {
        holder.setText(R.id.my_publish_order_tv_type, InformationType.getInformationTypeByValue(bean.getInformationType()).getName());
        holder.setText(R.id.my_publish_order_tv_name, bean.getTitle());
        holder.setText(R.id.my_publish_order_tv_state, InformationOrderStatus.getInformationOrderStatusByValue(bean.getStatus()).getMemo());
        holder.setText(R.id.order_pay_type, OrderType.getOrderTypeByValue(bean.getOrderType()).getName());
        holder.setText(R.id.order_still_time, bean.getDays() + "(天)");
        holder.setText(R.id.my_publish_order_tv_money, bean.getTotalPrice().toString());
        holder.setText(R.id.information_order_num, "订单编号：" + bean.getId());
        holder.setText(R.id.information_order_create_time, CommonUtils.formatTime(bean.getCreateTime().getTime(), "下单时间：yyyy-MM-dd HH:mm:ss"));
        final TimeTextView toPay = holder.getView(R.id.order_state_go_pay);
        TextView tvRefund = holder.getView(R.id.order_state_refund);
        if (bean.getStatus() == InformationOrderStatus.WaitPay.getValue()) {
            toPay.setVisibility(View.VISIBLE);
            toPay.setText("去支付");
        } else {
            toPay.setVisibility(View.GONE);
        }
        if (bean.getStatus() == InformationOrderStatus.Refund.getValue() && bean.getPaymentState() == 1 && DateUtils.compareTimeBefore(bean.getCreateTime())) {
            //已支付 取消
            tvRefund.setVisibility(View.VISIBLE);
        } else {
            tvRefund.setVisibility(View.GONE);
        }
        tvRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, OrderRefundInfoActivity.class);
                intent.putExtra("orderId", bean.getId());
                mActivity.startActivity(intent);
            }
        });

        toPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayActivity.toPay(mActivity, bean.getInformationId(), bean.getId(), bean.getInformationType(), bean.getAgentId());
            }
        });
    }

    private long getTimeBetween(String serverTime, Date laterTime) {
        if (CheckUtils.isEmptyStr(serverTime)) {
            return 0;
        }
        try {
            Date date1 = sdf.parse(serverTime);
            long time1 = date1.getTime();
            long time2 = laterTime.getTime();
            return time2 - time1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
