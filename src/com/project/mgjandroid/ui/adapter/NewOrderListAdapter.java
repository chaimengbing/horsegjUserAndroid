package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupBuyOrder;
import com.project.mgjandroid.bean.ThirdPartyOrderBean;
import com.project.mgjandroid.bean.UserOrderType;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderStatus;
import com.project.mgjandroid.constants.OrderFlowStatus;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.ui.view.TimeTextView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User_Cjh on 2016/12/14.
 */
public class NewOrderListAdapter extends BaseListAdapter<NewOrderFragmentModel.ValueEntity> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private View.OnClickListener listener;

    public NewOrderListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, NewOrderFragmentModel.ValueEntity bean, int position, View convertView, ViewGroup parent) {
        if (bean == null) {
            return;
        }

        if (bean.getThirdpartyOrder() != null) {
            //第三方订单
            showThirdItem(bean, holder, position);
        } else {
            switch (bean.getType()) {
                case 1:
                case 3:
                    // 外卖/商超
                    showItem(bean, holder, position);
                    break;
                case 2:
                    //拼团
                    showGroupItem(bean, holder, position);
                    break;
                case 6:
                    //团购
                    showGroupPurchaseItem(bean, holder, position);
                    break;
                case 9:
                    //跑腿代购
                    showLegWorkItem(bean, holder, position);
                    break;
            }
        }
    }

    /**
     * 第三方模块统一Item
     *
     * @param valueEntity
     * @param holder
     * @param position
     */
    private void showThirdItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
        ThirdPartyOrderBean thirdpartyOrder = valueEntity.getThirdpartyOrder();
        holder.setText(R.id.tv_item_type, valueEntity.getTypeName());
        holder.setText(R.id.order_list_item_tv_state, valueEntity.getThirdpartyOrder().getOrderFlowStatusStr());
        holder.setText(R.id.tv_item_time, com.project.mgjandroid.utils.DateUtils.getFormatTime1(valueEntity.getCreateTime(), "MM-dd HH:mm"));
        holder.setVisibility(R.id.tv_item_right1, false);
        holder.setText(R.id.tv_item_price, StringUtils.BigDecimal2Str(valueEntity.getTotalPrice()));
        if (thirdpartyOrder.getStatus() == -1 || thirdpartyOrder.getStatus() == 7) {
            holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
        } else {
            holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
        }
        LinearLayout llItemInfo = holder.getView(R.id.ll_item_order_info); //商品信息
        if (valueEntity.getType() == 7) {
            //顺风车
            holder.setImageResource(R.id.iv_item_type, R.drawable.item_icon_7);
            holder.setVisibility(R.id.ll_item_desc2, true);
            holder.setVisibility(R.id.ll_item_desc3, true);
            holder.setVisibility(R.id.ll_item_info, false);
            holder.setText(R.id.tv_item_name1, "起始位置：");
            holder.setText(R.id.tv_item_name2, "终点位置：");
            holder.setText(R.id.tv_item_content1, thirdpartyOrder.getStartAddress());
            holder.setText(R.id.tv_item_content2, thirdpartyOrder.getEndAddress());
            holder.setText(R.id.tv_item_content3, thirdpartyOrder.getDescription());
        } else if (valueEntity.getType() == 8) {
            //可视认养
            holder.setImageResource(R.id.iv_item_type, R.drawable.item_icon_8);
            holder.setVisibility(R.id.ll_item_desc2, false);
            holder.setVisibility(R.id.ll_item_desc3, false);
            holder.setVisibility(R.id.ll_item_info, true);
            ArrayList<ThirdPartyOrderBean.OrderModer> orderList = thirdpartyOrder.getOrderList();
            List<ThirdPartyOrderBean.OrderModer.OrderItemsBean> items = new ArrayList<>();
            if (orderList != null) {
                for (ThirdPartyOrderBean.OrderModer orderModer : orderList) {
                    List<ThirdPartyOrderBean.OrderModer.OrderItemsBean> orderItems = orderModer.getOrderItems();
                    if (orderItems != null) {
                        items.addAll(orderItems);
                    }
                }
            }
            holder.setText(R.id.tv_item_name1, "商品信息：");

            llItemInfo.removeAllViews();
            int count = 0;
            for (int i = 0; i < items.size(); i++) {
                if (i < 2) {
                    View inflate = View.inflate(mActivity, R.layout.item_order_list_info, null);
                    TextView tvDesc = (TextView) inflate.findViewById(R.id.tv_order_info_desc);
                    TextView tvNum = (TextView) inflate.findViewById(R.id.tv_order_info_num);
                    TextView tvPrice = (TextView) inflate.findViewById(R.id.tv_order_info_price);
                    tvDesc.setText(items.get(i).getGoodsName());
                    tvNum.setText("x " + items.get(i).getTotalQuantity());
                    tvPrice.setText("¥ " + items.get(i).getPrice());
                    llItemInfo.addView(inflate);
                }
                count = count + items.get(i).getTotalQuantity();
            }
            holder.setText(R.id.tv_item_content1, "共" + count + "件商品");
        } else if (valueEntity.getType() == 10) {
            //快递
            holder.setImageResource(R.id.iv_item_type, R.drawable.item_icon_10);
            holder.setVisibility(R.id.ll_item_desc2, true);
            holder.setVisibility(R.id.ll_item_desc3, false);
            holder.setVisibility(R.id.ll_item_info, false);
            holder.setText(R.id.tv_item_name1, "寄件地址：");
            holder.setText(R.id.tv_item_name2, "收件地址：");
            holder.setText(R.id.tv_item_content1, thirdpartyOrder.getTitle());
            holder.setText(R.id.tv_item_content2, thirdpartyOrder.getDescription());
//        } else if (valueEntity.getType() == 11) {
//            //洗衣
//            holder.setImageResource(R.id.iv_item_type, R.drawable.item_icon_11);
//            holder.setVisibility(R.id.ll_item_desc2, false);
//            holder.setVisibility(R.id.ll_item_desc3, false);
//            holder.setVisibility(R.id.ll_item_info, true);
        } else {
            holder.setVisibility(R.id.ll_item_desc2, false);
            holder.setVisibility(R.id.ll_item_desc3, false);
            holder.setVisibility(R.id.ll_item_info, false);
            holder.setText(R.id.tv_item_name1, "请升级马管家最新版本");
            holder.setText(R.id.tv_item_content1, "");
        }

        TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
        TextView tvEvaluate = holder.getView(R.id.order_state_evaluate);
        TextView tvMoreOrder = holder.getView(R.id.order_state_more_one);
        TextView tvInvite = holder.getView(R.id.to_invite_friend);
        TextView tvCode = holder.getView(R.id.order_state_to_see_code);
        TextView tvRefund = holder.getView(R.id.order_state_refund_to_balance);
        tvCode.setVisibility(View.GONE);
        tvInvite.setVisibility(View.GONE);
        tvMoreOrder.setVisibility(View.GONE);
        tvEvaluate.setVisibility(View.GONE);
        tvPay.setTag(position);
        tvRefund.setTag(position);
        tvPay.setOnClickListener(listener);
        tvRefund.setOnClickListener(listener);

        //1525968000  2018/5/11
        if (valueEntity.getThirdpartyOrder().getStatus() == -1 && valueEntity.getThirdpartyOrder().getPaymentState() == 1 && DateUtils.compareTimeBefore(valueEntity.getCreateTime())) {
            tvRefund.setVisibility(View.VISIBLE);
        } else {
            tvRefund.setVisibility(View.GONE);
        }

        if (valueEntity.getOrderFlowStatus() == 1) {
            // "paymentExpireTime": 1515575449643,
            String paymentExpireTime = valueEntity.getThirdpartyOrder().getPaymentExpireTime();
            if (paymentExpireTime != null) {
                try {
                    if (paymentExpireTime.length() == 13) {
                        if (TextUtils.isEmpty(valueEntity.getThirdpartyOrder().getServerTime())) {
                            Date date1 = sdf.parse(valueEntity.getServerTime());
                            tvPay.setTimes(Long.parseLong(paymentExpireTime) - date1.getTime());
                        } else {
                            //13位时间戳
                            tvPay.setTimes(Long.parseLong(paymentExpireTime) - Long.parseLong(valueEntity.getThirdpartyOrder().getServerTime()));
                        }
                    } else {
                        tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
                    }
                    tvPay.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    tvPay.setVisibility(View.GONE);
                }
            } else {
                tvPay.setVisibility(View.GONE);
            }
        } else {
            tvPay.setVisibility(View.GONE);
        }
    }

    private void showLegWorkItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
        holder.setImageResource(R.id.iv_item_type, R.drawable.item_icon_9);
        holder.setVisibility(R.id.ll_item_desc2, true);
        holder.setVisibility(R.id.ll_item_desc3, false);
        holder.setVisibility(R.id.ll_item_info, false);
        holder.setVisibility(R.id.tv_item_right1, false);
        holder.setText(R.id.tv_item_type, valueEntity.getTypeName());
        holder.setText(R.id.tv_item_price, StringUtils.BigDecimal2Str(valueEntity.getTotalPrice()));
        holder.setText(R.id.tv_item_time, com.project.mgjandroid.utils.DateUtils.getFormatTime1(valueEntity.getLegWorkOrder().getCreateTime(), "MM-dd HH:mm"));

        TextView tvState = holder.getView(R.id.order_list_item_tv_state);
        TimeTextView tvLegworkPay = holder.getView(R.id.order_state_go_pay);
        TextView tvOrderStateEvalute = holder.getView(R.id.order_state_evaluate);
        TextView tvOrderStateRefund = holder.getView(R.id.order_state_refund_to_balance);
        holder.setVisibility(R.id.order_state_to_see_code, false);
        holder.setVisibility(R.id.to_invite_friend, false);
        holder.setVisibility(R.id.order_state_more_one, false);

        tvLegworkPay.setTag(position);
        tvLegworkPay.setOnClickListener(listener);
        tvOrderStateRefund.setTag(position);
        tvOrderStateRefund.setOnClickListener(listener);
        tvOrderStateEvalute.setTag(position);
        tvOrderStateEvalute.setOnClickListener(listener);

        if (valueEntity.getLegWorkOrder().getChildType() == 1) {
            //取送件 取货地址  送货地址
            holder.setText(R.id.tv_item_name1, "取货地址：");
            holder.setText(R.id.tv_item_name2, "送货地址：");
        } else {
            //跑腿 购买地址  送货地址
            holder.setText(R.id.tv_item_name1, "购买地址：");
            holder.setText(R.id.tv_item_name2, "送货地址：");
        }
        if (valueEntity.getLegWorkOrder().getShipperType() == 1) {
            holder.setText(R.id.tv_item_content1, "就近购买");
        } else if (valueEntity.getLegWorkOrder().getShipperType() == 2) {
            String shipperHouseNumber = valueEntity.getLegWorkOrder().getShipperHouseNumber();
            String userHouseNumber = valueEntity.getLegWorkOrder().getUserHouseNumber();
            if (TextUtils.isEmpty(shipperHouseNumber)) {
                holder.setText(R.id.tv_item_content1, valueEntity.getLegWorkOrder().getShipperAddress() + (TextUtils.isEmpty(userHouseNumber) ? "" : userHouseNumber));
            } else {
                holder.setText(R.id.tv_item_content1, valueEntity.getLegWorkOrder().getShipperAddress() + (TextUtils.isEmpty(userHouseNumber) ? "" : userHouseNumber) + "\n" + valueEntity.getLegWorkOrder().getShipperHouseNumber());
            }
        } else if (valueEntity.getLegWorkOrder().getShipperType() == 0) {
            holder.setText(R.id.tv_item_content1, valueEntity.getLegWorkOrder().getShipperAddress());
        }

        String userHouseNumber = valueEntity.getLegWorkOrder().getUserHouseNumber();
        holder.setText(R.id.tv_item_content2, valueEntity.getLegWorkOrder().getUserAddress() + (TextUtils.isEmpty(userHouseNumber) ? "" : userHouseNumber));

        switch (valueEntity.getLegWorkOrder().getStatus()) {
            case -1:
                tvState.setText("已取消");
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
                if (valueEntity.getLegWorkOrder().getPaymentState() == 1) {
                    //已经支付
                    tvOrderStateRefund.setVisibility(View.VISIBLE);
                } else {
                    tvOrderStateRefund.setVisibility(View.GONE);
                }
                tvLegworkPay.setVisibility(View.GONE);
                tvOrderStateEvalute.setVisibility(View.GONE);
                break;
            case 1:
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                tvState.setText("待付款");
                tvLegworkPay.setVisibility(View.VISIBLE);
                tvOrderStateEvalute.setVisibility(View.GONE);
                tvOrderStateRefund.setVisibility(View.GONE);
                tvLegworkPay.setTimes(getTimeBetween(valueEntity.getServerTime(), valueEntity.getLegWorkOrder().getPaymentExpireTime()));
                break;
            case 2:
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                tvState.setText("待确认");
                tvLegworkPay.setVisibility(View.GONE);
                tvOrderStateEvalute.setVisibility(View.GONE);
                tvOrderStateRefund.setVisibility(View.GONE);
                break;
            case 4:
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                tvState.setText("待取货");
                tvLegworkPay.setVisibility(View.GONE);
                tvOrderStateEvalute.setVisibility(View.GONE);
                tvOrderStateRefund.setVisibility(View.GONE);
                break;
            case 5:
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                tvState.setText("配送中");
                tvLegworkPay.setVisibility(View.GONE);
                tvOrderStateEvalute.setVisibility(View.GONE);
                tvOrderStateRefund.setVisibility(View.GONE);
                break;
            case 7:
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
                tvState.setText("已完成");
                tvLegworkPay.setVisibility(View.GONE);
                tvOrderStateRefund.setVisibility(View.GONE);
                tvOrderStateEvalute.setVisibility(View.VISIBLE);
                if (valueEntity.getLegWorkOrder().getHasComments() == 1) {
                    tvOrderStateEvalute.setEnabled(false);
                    tvOrderStateEvalute.setText("已评价");
                } else {
                    tvOrderStateEvalute.setEnabled(true);
                    tvOrderStateEvalute.setText("去评价");
                }
                break;
        }
    }

    private void showGroupPurchaseItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
        GroupPurchaseOrder order = valueEntity.getGroupPurchaseOrder();
        holder.setImageResource(R.id.iv_item_type, R.drawable.item_icon_6);
        holder.setVisibility(R.id.ll_item_desc2, false);
        holder.setVisibility(R.id.ll_item_desc3, false);
        holder.setVisibility(R.id.ll_item_info, true);
        holder.setVisibility(R.id.tv_item_right1, true);
        holder.setText(R.id.tv_item_type, "团购");
        holder.setText(R.id.tv_item_price, StringUtils.BigDecimal2Str(order.getTotalPrice()));
        holder.setText(R.id.tv_item_time, com.project.mgjandroid.utils.DateUtils.getFormatTime1(order.getCreateTime(), "MM-dd HH:mm"));

        LinearLayout llItemInfo = holder.getView(R.id.ll_item_order_info); //商品信息
        TextView tvStatus = holder.getView(R.id.order_list_item_tv_state);


        TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
        TextView tvEvaluate = holder.getView(R.id.order_state_evaluate);
        TextView tvMoreOrder = holder.getView(R.id.order_state_more_one);
        TextView tvInvite = holder.getView(R.id.to_invite_friend);
        TextView tvCode = holder.getView(R.id.order_state_to_see_code);
        TextView tvRefund = holder.getView(R.id.order_state_refund_to_balance);
        tvCode.setVisibility(View.GONE);
        tvRefund.setVisibility(View.GONE);
        tvInvite.setVisibility(View.GONE);
        tvMoreOrder.setVisibility(View.GONE);
        tvPay.setVisibility(View.GONE);
        tvEvaluate.setVisibility(View.GONE);
        tvCode.setTag(position);
        tvPay.setTag(position);
        tvEvaluate.setTag(position);
        tvCode.setOnClickListener(listener);
        tvPay.setOnClickListener(listener);
        tvEvaluate.setOnClickListener(listener);

        if (order.getStatus() == GroupPurchaseOrderStatus.Done.getValue()) {
            if (order.getUsableQuantity() > 0) {
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                tvStatus.setText("待消费");
                tvCode.setVisibility(View.VISIBLE);
            } else if (order.getUsableQuantity() == 0 && order.getUseQuantity() > 0 && order.getHasComments() == 0) {
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                tvStatus.setText("待评价");
                tvEvaluate.setVisibility(View.VISIBLE);
            } else {
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
                tvStatus.setText("已完成");
            }
        } else if (order.getStatus() == GroupPurchaseOrderStatus.Cancel.getValue()) {
            holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
            tvStatus.setText("已取消");
        } else if (order.getStatus() == GroupPurchaseOrderStatus.Refund.getValue()) {
            holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
            tvStatus.setText("已退款");
        } else {
            holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
            tvStatus.setText(GroupPurchaseOrderStatus.getGroupPurchaseCouponTypeByValue(order.getStatus()).getMemo());
        }

        holder.setText(R.id.tv_item_name1, "团购内容：");
        holder.setText(R.id.tv_item_content1, "共" + order.getQuantity() + "件商品");
        holder.setText(R.id.tv_item_right1, "有效期至：" + order.getGroupPurchaseCouponEndTime());

        llItemInfo.removeAllViews();
        View inflate = View.inflate(mActivity, R.layout.item_order_list_info, null);
        TextView tvDesc = (TextView) inflate.findViewById(R.id.tv_order_info_desc);
        TextView tvNum = (TextView) inflate.findViewById(R.id.tv_order_info_num);
        TextView tvPrice = (TextView) inflate.findViewById(R.id.tv_order_info_price);
        tvDesc.setText(order.getGroupPurchaseMerchantName() + (order.getGroupPurchaseCouponType() == 1 ? "代金券" : "团购券"));
        tvNum.setText("x " + order.getQuantity());
        tvPrice.setText("¥ " + StringUtils.BigDecimal2Str(order.getPrice()));
        llItemInfo.addView(inflate);

        if (order.getStatus() == GroupPurchaseOrderStatus.WaitPay.getValue()) {
            String paymentExpireTime = order.getPaymentExpireTime();
            if (paymentExpireTime != null) {
                tvPay.setVisibility(View.VISIBLE);
                tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
            }
        }

        if (order.getQueryType() == 4) { // 退款
            tvPay.setVisibility(View.GONE);
            tvCode.setVisibility(View.GONE);
            tvEvaluate.setVisibility(View.GONE);
            tvStatus.setText("");
        } else if (order.getQueryType() == 3) { // 待评价
            tvPay.setVisibility(View.GONE);
            tvCode.setVisibility(View.GONE);
            tvEvaluate.setVisibility(View.VISIBLE);
            tvStatus.setText("待评价");
        } else if (order.getQueryType() == 2) { // 待销费
            tvPay.setVisibility(View.GONE);
            tvCode.setVisibility(View.VISIBLE);
            tvEvaluate.setVisibility(View.GONE);
            tvStatus.setText("待消费");
        } else if (order.getQueryType() == 1) { // 待付款
            tvPay.setVisibility(View.VISIBLE);
            tvCode.setVisibility(View.GONE);
            tvEvaluate.setVisibility(View.GONE);
            tvStatus.setText("等待付款");
            tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), order.getPaymentExpireTime()));
        }
    }

    private void showGroupItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
        GroupBuyOrder groupBuyOrder = valueEntity.getGroupbuyOrder();
        holder.setImageResource(R.id.iv_item_type, R.drawable.item_icon_2);
        holder.setVisibility(R.id.ll_item_desc2, false);
        holder.setVisibility(R.id.ll_item_desc3, false);
        holder.setVisibility(R.id.ll_item_info, true);
        holder.setVisibility(R.id.tv_item_right1, false);
        holder.setText(R.id.tv_item_type, valueEntity.getTypeName());
        holder.setText(R.id.tv_item_price, StringUtils.BigDecimal2Str(groupBuyOrder.getTotalPrice()));
        holder.setText(R.id.tv_item_time, DateUtils.getFormatTime1(valueEntity.getCreateTime(), "MM-dd HH:mm"));

        LinearLayout llItemInfo = holder.getView(R.id.ll_item_order_info); //商品信息
        TextView groupStatus = holder.getView(R.id.order_list_item_tv_state);
        TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
        TextView tvInvite = holder.getView(R.id.to_invite_friend);
        TextView tvEvaluate = holder.getView(R.id.order_state_evaluate);
        TextView tvMoreOrder = holder.getView(R.id.order_state_more_one);
        TextView tvCode = holder.getView(R.id.order_state_to_see_code);
        TextView tvRefund = holder.getView(R.id.order_state_refund_to_balance);

        tvCode.setVisibility(View.GONE);
        tvRefund.setVisibility(View.GONE);
        tvMoreOrder.setVisibility(View.GONE);
        tvPay.setTag(position);
        tvEvaluate.setTag(position);
        tvInvite.setTag(position);
        tvRefund.setTag(position);
        tvRefund.setOnClickListener(listener);
        tvPay.setOnClickListener(listener);
        tvEvaluate.setOnClickListener(listener);
        tvInvite.setOnClickListener(listener);

        if (groupBuyOrder.getStatus() != null) {
            switch (groupBuyOrder.getStatus()) {
                case -1:
                    groupStatus.setText("已取消");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
                    if (groupBuyOrder.getPaymentState() == 1 && DateUtils.compareTimeBefore(valueEntity.getCreateTime())) {
                        //已支付
                        tvRefund.setVisibility(View.VISIBLE);
                    }
                    break;
                case 0:
                    holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                    groupStatus.setText("订单创建");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                    groupStatus.setText("等待付款");
                    tvPay.setVisibility(View.VISIBLE);
                    tvInvite.setVisibility(View.GONE);
                    tvPay.setTimes(getTimeBetween1(new Date(), groupBuyOrder.getPaymentExpireTime()));
                    break;
                case 2:
                    holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                    groupStatus.setText("已支付，未成团");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                    groupStatus.setText("待发货");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    break;
                case 4:
                    holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
                    if (groupBuyOrder.getHasComments() == 0) {
                        groupStatus.setText("交易成功");
                    } else {
                        groupStatus.setText("已完成");
                    }
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    break;
                case 5:
                    holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
                    groupStatus.setText("未成团，退款成功");
                    tvPay.setVisibility(View.GONE);
                    tvInvite.setVisibility(View.GONE);
                    break;
            }
        } else {
            groupStatus.setVisibility(View.GONE);
        }

        holder.setText(R.id.tv_item_name1, "商品信息：");
        holder.setText(R.id.tv_item_content1, "共" + groupBuyOrder.getQuantity() + "件商品");

        llItemInfo.removeAllViews();
        View inflate = View.inflate(mActivity, R.layout.item_order_list_info, null);
        TextView tvDesc = (TextView) inflate.findViewById(R.id.tv_order_info_desc);
        TextView tvNum = (TextView) inflate.findViewById(R.id.tv_order_info_num);
        TextView tvPrice = (TextView) inflate.findViewById(R.id.tv_order_info_price);
        tvDesc.setText(groupBuyOrder.getGroupBuy().getGoodsName());
        tvNum.setText("x " + groupBuyOrder.getQuantity());
        tvPrice.setText("¥ " + StringUtils.BigDecimal2Str(groupBuyOrder.getPrice()));
        llItemInfo.addView(inflate);

        if (groupBuyOrder.getStatus() == 4 && groupBuyOrder.getHasComments() == 0) {
            tvEvaluate.setVisibility(View.VISIBLE);
        } else {
            tvEvaluate.setVisibility(View.GONE);
        }
    }

    private void showItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
        if (valueEntity.getType() == 1) {
            holder.setImageResource(R.id.iv_item_type, R.drawable.item_icon_1);
        } else {
            holder.setImageResource(R.id.iv_item_type, R.drawable.item_icon_3);
        }
        holder.setVisibility(R.id.ll_item_desc2, true);
        holder.setVisibility(R.id.ll_item_desc3, false);
        holder.setVisibility(R.id.ll_item_info, true);
        holder.setVisibility(R.id.tv_item_right1, false);
        holder.setText(R.id.tv_item_type, UserOrderType.getOrderTypeByValue(valueEntity.getType()).getName());
        holder.setText(R.id.order_list_item_tv_state, OrderFlowStatus.getOrderStatusByValue(valueEntity.getOrderFlowStatus()).getMemo());
        holder.setText(R.id.tv_item_price, StringUtils.BigDecimal2Str(valueEntity.getTotalPrice()));
        holder.setText(R.id.tv_item_time, com.project.mgjandroid.utils.DateUtils.getFormatTime1(valueEntity.getCreateTime(), "MM-dd HH:mm"));

        LinearLayout llItemInfo = holder.getView(R.id.ll_item_order_info); //商品信息
        TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
        TextView tvMoreOrder = holder.getView(R.id.order_state_more_one);
        TextView tvEvaluate = holder.getView(R.id.order_state_evaluate);
        TextView tvInvite = holder.getView(R.id.to_invite_friend);
        TextView tvCode = holder.getView(R.id.order_state_to_see_code);
        TextView tvRefund = holder.getView(R.id.order_state_refund_to_balance);
        tvCode.setVisibility(View.GONE);
        tvRefund.setVisibility(View.GONE);
        tvInvite.setVisibility(View.GONE);

        tvMoreOrder.setTag(position);
        tvPay.setTag(position);
        tvEvaluate.setTag(position);
        tvRefund.setTag(position);
        tvMoreOrder.setOnClickListener(listener);
        tvPay.setOnClickListener(listener);
        tvEvaluate.setOnClickListener(listener);
        tvRefund.setOnClickListener(listener);

        switch (valueEntity.getOrderFlowStatus()) {
            case -1://取消
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
                changeButtonShowState(holder, true, false, false, false, false);
                if (valueEntity.getPaymentState() == 1 && DateUtils.compareTimeBefore(valueEntity.getCreateTime())) {
                    //已支付
                    tvRefund.setVisibility(View.VISIBLE);
                }
                break;
            case 0://已创建
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
                changeButtonShowState(holder, true, false, false, true, false);
                break;
            case 1://等待付款
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                changeButtonShowState(holder, false, false, false, false, true);
                break;
            case 2://等待商家确认
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                changeButtonShowState(holder, true, false, false, true, false);
                break;
            case 3://商家已接单
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                changeButtonShowState(holder, true, false, false, true, false);
                break;
            case 4://配送员取货中
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                changeButtonShowState(holder, true, false, false, true, false);
                break;
            case 5://配送员已取货
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                changeButtonShowState(holder, true, false, false, true, false);
                break;
            case 6://等待送达
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.title_bar_bg);
                changeButtonShowState(holder, true, false, false, true, false);
                break;
            case 7://完成
                holder.setTextColor(R.id.order_list_item_tv_state, R.color.color_3);
                if (valueEntity.getHasComments() == 0)
                    changeButtonShowState(holder, true, true, true, false, false);
                else
                    changeButtonShowState(holder, true, true, false, false, false);
                break;
        }

        if (valueEntity.getMerchantId() > 0) {
            holder.setText(R.id.tv_item_name1, "商家信息：");
            holder.setText(R.id.tv_item_content1, valueEntity.getMerchant().getName());
        } else {
            holder.setVisibility(R.id.ll_item_desc1, false);
        }

        List<NewOrderFragmentModel.ValueEntity.OrderItemsEntity> orderItems = valueEntity.getOrderItems();
        if (CheckUtils.isNoEmptyList(orderItems)) {
            holder.setText(R.id.tv_item_name2, "商品信息：");
            int count = 0;
            llItemInfo.removeAllViews();
            for (int i = 0; i < orderItems.size(); i++) {
                if (i < 2) {
                    //最多展示两件
                    View inflate = getLayoutInflater(mActivity).inflate(R.layout.item_order_list_info, null);
                    TextView tvDesc = (TextView) inflate.findViewById(R.id.tv_order_info_desc);
                    TextView tvNum = (TextView) inflate.findViewById(R.id.tv_order_info_num);
                    TextView tvPrice = (TextView) inflate.findViewById(R.id.tv_order_info_price);
                    tvDesc.setText(orderItems.get(i).getName());
                    tvNum.setText("x " + orderItems.get(i).getQuantity());
                    tvPrice.setText("¥ " + StringUtils.BigDecimal2Str(orderItems.get(i).getPrice()));
                    llItemInfo.addView(inflate);
                }
                count = count + orderItems.get(i).getQuantity();
            }
            holder.setText(R.id.tv_item_content2, "共" + count + "件商品");
        } else {
            holder.setVisibility(R.id.ll_item_desc2, false);
            holder.setVisibility(R.id.ll_item_info, false);
        }
        String paymentExpireTime = valueEntity.getPaymentExpireTime();
        if (paymentExpireTime != null) {
            tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
        } else {
            tvPay.setVisibility(View.GONE);
        }
        //再来一单
        if (valueEntity.getMerchant().getType() == 1) {
            tvMoreOrder.setVisibility(View.GONE);
        } else {
            tvMoreOrder.setVisibility(View.VISIBLE);
        }
    }

    private void changeButtonShowState(ViewHolder holder, boolean tvMoreOrderShow,
                                       boolean tvTakePhotoShow, boolean tvEvaluateShow, boolean tvConfirmShow, boolean tvPayShow) {
        holder.setVisibility(R.id.order_state_more_one, tvMoreOrderShow);

        holder.setVisibility(R.id.order_state_evaluate, tvEvaluateShow);

        holder.setVisibility(R.id.order_state_go_pay, tvPayShow);
    }

    private long getTimeBetween(String serverTime, String laterTime) {
        if (CheckUtils.isEmptyStr(serverTime) || CheckUtils.isEmptyStr(laterTime)) {
            return 0;
        }
        try {
            Date date1 = sdf.parse(serverTime);
            long time1 = date1.getTime();
            Date date2 = sdf.parse(laterTime);
            long time2 = date2.getTime();
            return time2 - time1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
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

    //    private void showCarItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
//        if (null != valueEntity) {
//            LinearLayout view = holder.getView(R.id.layout_old);
//            LinearLayout view2 = holder.getView(R.id.layout_new);
//            CornerImageView logo = holder.getView(R.id.order_list_item_img);
//            //LinearLayout imgFather = holder.getView(R.id.order_list_item_img_father);
//            TextView tvStatus = holder.getView(R.id.order_list_item_tv_state);
//            TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
//            TextView tvQuantity = holder.getView(R.id.order_list_item_tv_quantity);
//            TextView tvTitle = holder.getView(R.id.order_list_item_tv_name);
//            TextView tvTotalPrice = holder.getView(R.id.order_list_item_tv_money);
//            TextView tvEvaluate = holder.getView(R.id.order_state_evaluate);
//            TextView tvIntro = holder.getView(R.id.order_list_item_tv_intro);
//            TextView tvType = holder.getView(R.id.order_list_item_tv_type);
//            TextView tvMoreOrder = holder.getView(R.id.order_state_more_one);
//            TextView tvInvite = holder.getView(R.id.to_invite_friend);
//            TextView tvCode = holder.getView(R.id.order_state_to_see_code);
//            TextView tvRefund = holder.getView(R.id.order_state_refund_to_balance);
//            tvCode.setVisibility(View.GONE);
//            tvRefund.setVisibility(View.GONE);
//            tvInvite.setVisibility(View.GONE);
//            tvMoreOrder.setVisibility(View.GONE);
//            view.setVisibility(View.VISIBLE);
//            view2.setVisibility(View.GONE);
//            tvQuantity.setText("");
//
//            tvStatus.setText(CarHailingOrderStatus.getOrderTypeByValue(valueEntity.getChauffeurOrder().getStatus()).getName());
//            tvType.setText(UserOrderType.getOrderTypeByValue(valueEntity.getType()).getName());
//            tvTitle.setText(valueEntity.getChauffeurOrder().getChauffeurOrderTrip().getStartAddress() + "-"
//                    + valueEntity.getChauffeurOrder().getChauffeurOrderTrip().getEndAddress());
//            tvIntro.setText("发车时间：" + valueEntity.getChauffeurOrder().getLeaveTime());
//            tvTotalPrice.setText(StringUtils.BigDecimal2Str(valueEntity.getChauffeurOrder().getTotalPrice()));
//            imgFather.setVisibility(View.VISIBLE);
//            if (CheckUtils.isNoEmptyStr(valueEntity.getChauffeurOrder().getChauffeur().getHeaderImg())) {
//                ImageUtils.loadBitmap(mActivity, valueEntity.getChauffeurOrder().getChauffeur().getHeaderImg(), logo, R.drawable.horsegj_default, Constants.getEndThumbnail(43, 43));
//            } else {
//                logo.setImageResource(R.drawable.horsegj_default);
//            }
//
//            tvPay.setTag(position);
//            tvPay.setOnClickListener(listener);
//            tvEvaluate.setTag(position);
//            tvEvaluate.setOnClickListener(listener);
//            imgFather.setTag(position);
//            imgFather.setOnClickListener(listener);
//
//            if (valueEntity.getChauffeurOrder().getStatus() == CarHailingOrderStatus.WaitPay.getValue()) {
//                String paymentExpireTime = valueEntity.getChauffeurOrder().getPaymentExpireTime();
//                if (paymentExpireTime != null) {
//                    tvPay.setVisibility(View.VISIBLE);
//                    tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
//                } else {
//                    tvPay.setVisibility(View.GONE);
//                }
//            } else {
//                tvPay.setVisibility(View.GONE);
//            }
//            if (valueEntity.getChauffeurOrder().getStatus() == CarHailingOrderStatus.Done.getValue() && valueEntity.getChauffeurOrder().getHasComments() == 0) {
//                tvEvaluate.setVisibility(View.VISIBLE);
//            } else {
//                tvEvaluate.setVisibility(View.GONE);
//            }
//        }
//    }
}
