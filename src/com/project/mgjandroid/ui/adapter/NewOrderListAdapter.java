package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupBuyOrder;
import com.project.mgjandroid.bean.UserOrderType;
import com.project.mgjandroid.bean.carhailing.CarHailingOrderStatus;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderStatus;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.constants.OrderFlowStatus;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.TimeTextView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
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
                case 4:
                    showCarItem(bean, holder, position);
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

    private void showLegWorkItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
        if (null != valueEntity) {
            LinearLayout view = holder.getView(R.id.layout_old);
            LinearLayout view2 = holder.getView(R.id.layout_new);
            TextView tvCategory = holder.getView(R.id.legwork_tv_category);
            TextView tvState = holder.getView(R.id.order_list_item_tv_state_legwoerk);
            ImageView ivBuy = holder.getView(R.id.legwork_iv_buy);
            TextView tvBuyAddress = holder.getView(R.id.legwork_tv_buy_address);
            //TextView tvBuyInformation = holder.getView(R.id.legwork_tv_buy_personal_information);
            TextView tvDeliverAddress = holder.getView(R.id.legwork_tv_deliver_address);
            TextView tvDeliverInformation = holder.getView(R.id.legwork_tv_deliver_personal_information);
            TextView tvLegworkTime = holder.getView(R.id.tv_legwork_time);
            TimeTextView tvLegworkPay = holder.getView(R.id.order_state_go_pay1);
            TextView tvOrderStateEvalute = holder.getView(R.id.order_state_evaluate1);
            TextView tvOrderStateRefund = holder.getView(R.id.order_state_refund);//退款详情
            view.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
            if (valueEntity.getLegWorkOrder().getChildType() == 1) {
                tvCategory.setText("取送件");
                ivBuy.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.icon_take));
            } else {
                tvCategory.setText("跑腿");
                ivBuy.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_legwork_buy));
            }
            switch (valueEntity.getLegWorkOrder().getStatus()) {
                case -1:
                    tvState.setText("已取消");
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
                    tvState.setText("待付款");
                    tvLegworkPay.setVisibility(View.VISIBLE);
                    tvOrderStateEvalute.setVisibility(View.GONE);
                    tvOrderStateRefund.setVisibility(View.GONE);
                    tvLegworkPay.setTimes(getTimeBetween(valueEntity.getServerTime(), valueEntity.getLegWorkOrder().getPaymentExpireTime()));
                    break;
                case 2:
                    tvState.setText("待确认");
                    tvLegworkPay.setVisibility(View.GONE);
                    tvOrderStateEvalute.setVisibility(View.GONE);
                    tvOrderStateRefund.setVisibility(View.GONE);
                    break;
                case 4:
                    tvState.setText("待取货");
                    tvLegworkPay.setVisibility(View.GONE);
                    tvOrderStateEvalute.setVisibility(View.GONE);
                    tvOrderStateRefund.setVisibility(View.GONE);
                    break;
                case 5:
                    tvState.setText("配送中");
                    tvLegworkPay.setVisibility(View.GONE);
                    tvOrderStateEvalute.setVisibility(View.GONE);
                    tvOrderStateRefund.setVisibility(View.GONE);
                    break;
                case 7:
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
            if (valueEntity.getLegWorkOrder().getShipperType() == 1) {
                tvBuyAddress.setText("就近购买");
                //tvBuyInformation.setVisibility(View.GONE);
            } else if (valueEntity.getLegWorkOrder().getShipperType() == 2) {
                String shipperHouseNumber = valueEntity.getLegWorkOrder().getShipperHouseNumber();
                String userHouseNumber = valueEntity.getLegWorkOrder().getUserHouseNumber();
                if (TextUtils.isEmpty(shipperHouseNumber)) {
                    tvBuyAddress.setText(valueEntity.getLegWorkOrder().getShipperAddress() + (TextUtils.isEmpty(userHouseNumber) ? "" : userHouseNumber));
                } else {
                    tvBuyAddress.setText(valueEntity.getLegWorkOrder().getShipperAddress() + (TextUtils.isEmpty(userHouseNumber) ? "" : userHouseNumber) + "\n" + valueEntity.getLegWorkOrder().getShipperHouseNumber());
                }
                // tvBuyInformation.setVisibility(View.GONE);
            } else if (valueEntity.getLegWorkOrder().getShipperType() == 0) {
                tvBuyAddress.setText(valueEntity.getLegWorkOrder().getShipperAddress());
                //tvBuyInformation.setVisibility(View.VISIBLE);
                //tvBuyInformation.setText(valueEntity.getLegWorkOrder().getShipperName() + " " + valueEntity.getLegWorkOrder().getShipperGender() + " " + valueEntity.getLegWorkOrder().getShipperMobile());
            }
            tvLegworkPay.setTag(position);
            tvLegworkPay.setOnClickListener(listener);
            tvOrderStateRefund.setTag(position);
            tvOrderStateRefund.setOnClickListener(listener);
            tvOrderStateEvalute.setTag(position);
            tvOrderStateEvalute.setOnClickListener(listener);
            String userHouseNumber = valueEntity.getLegWorkOrder().getUserHouseNumber();
            tvDeliverAddress.setText(valueEntity.getLegWorkOrder().getUserAddress() + (TextUtils.isEmpty(userHouseNumber) ? "" : userHouseNumber));
            tvDeliverInformation.setText(valueEntity.getLegWorkOrder().getUserName() + " " + valueEntity.getLegWorkOrder().getUserGender() + " " + valueEntity.getLegWorkOrder().getUserMobile());
            tvLegworkTime.setText(com.project.mgjandroid.utils.DateUtils.getFormatTime1(valueEntity.getLegWorkOrder().getCreateTime(), "MM-dd HH:mm:ss"));
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
        if (null != valueEntity) {
            LinearLayout view = holder.getView(R.id.layout_old);
            LinearLayout view2 = holder.getView(R.id.layout_new);
            CornerImageView logo = holder.getView(R.id.order_list_item_img);
            LinearLayout imgFather = holder.getView(R.id.order_list_item_img_father);
            TextView tvStatus = holder.getView(R.id.order_list_item_tv_state);
            TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
            TextView tvQuantity = holder.getView(R.id.order_list_item_tv_quantity);
            TextView tvTitle = holder.getView(R.id.order_list_item_tv_name);
            TextView tvTotalPrice = holder.getView(R.id.order_list_item_tv_money);
            TextView tvEvaluate = holder.getView(R.id.order_state_evaluate);
            TextView tvIntro = holder.getView(R.id.order_list_item_tv_intro);
            TextView tvType = holder.getView(R.id.order_list_item_tv_type);
            TextView tvMoreOrder = holder.getView(R.id.order_state_more_one);
            TextView tvInvite = holder.getView(R.id.to_invite_friend);
            TextView tvCode = holder.getView(R.id.order_state_to_see_code);
            TextView tvRefund = holder.getView(R.id.order_state_refund_to_balance);
            tvCode.setVisibility(View.GONE);
            tvInvite.setVisibility(View.GONE);
            tvMoreOrder.setVisibility(View.GONE);
            tvEvaluate.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            tvQuantity.setText("");

            tvStatus.setText(valueEntity.getThirdpartyOrder().getOrderFlowStatusStr());
            tvType.setText(valueEntity.getThirdpartyOrder().getTypeStr());
            tvTitle.setText(valueEntity.getThirdpartyOrder().getTitle());
            tvIntro.setText(valueEntity.getThirdpartyOrder().getDescription());
            tvTotalPrice.setText(StringUtils.BigDecimal2Str(valueEntity.getTotalPrice()));
            if (CheckUtils.isNoEmptyStr(valueEntity.getThirdpartyOrder().getPicUrl())) {
                imgFather.setVisibility(View.VISIBLE);
                ImageUtils.loadBitmap(mActivity, valueEntity.getThirdpartyOrder().getPicUrl(), logo, R.drawable.horsegj_default, "");
            } else {
                imgFather.setVisibility(View.GONE);
                logo.setImageResource(R.drawable.horsegj_default);
            }

            tvPay.setTag(position);
            tvPay.setOnClickListener(listener);
            tvEvaluate.setTag(position);
            tvEvaluate.setOnClickListener(listener);
            tvRefund.setTag(position);
            tvRefund.setOnClickListener(listener);

            if (valueEntity.getThirdpartyOrder().getStatus() == -1 && valueEntity.getThirdpartyOrder().getPaymentState() == 1) {
                tvRefund.setVisibility(View.VISIBLE);
            } else {
                tvRefund.setVisibility(View.GONE);
            }

            if (valueEntity.getOrderFlowStatus() == 1) {
                //"paymentExpireTime": 1515575449643,
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
    }

    private void showGroupPurchaseItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
        if (null != valueEntity) {
            LinearLayout view = holder.getView(R.id.layout_old);
            LinearLayout view2 = holder.getView(R.id.layout_new);
            CornerImageView logo = holder.getView(R.id.order_list_item_img);
            LinearLayout imgFather = holder.getView(R.id.order_list_item_img_father);
            TextView tvStatus = holder.getView(R.id.order_list_item_tv_state);
            TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
            TextView tvQuantity = holder.getView(R.id.order_list_item_tv_quantity);
            TextView tvTitle = holder.getView(R.id.order_list_item_tv_name);
            TextView tvTotalPrice = holder.getView(R.id.order_list_item_tv_money);
            TextView tvEvaluate = holder.getView(R.id.order_state_evaluate);
            TextView tvIntro = holder.getView(R.id.order_list_item_tv_intro);
            TextView tvType = holder.getView(R.id.order_list_item_tv_type);
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
            view.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            tvType.setText(UserOrderType.getOrderTypeByValue(valueEntity.getType()).getName());
            GroupPurchaseOrder order = valueEntity.getGroupPurchaseOrder();
            if (order.getStatus() == GroupPurchaseOrderStatus.Done.getValue()) {
                tvStatus.setText("已完成");
                if (order.getUsableQuantity() > 0) {
                    tvStatus.setText("待消费");
                    tvCode.setVisibility(View.VISIBLE);
                } else if (order.getUsableQuantity() == 0 && order.getUseQuantity() > 0 && order.getHasComments() == 0) {
                    tvStatus.setText("待评价");
                    tvEvaluate.setVisibility(View.VISIBLE);
                }
            } else if (order.getStatus() == GroupPurchaseOrderStatus.Cancel.getValue()) {
                tvStatus.setText("已取消");
            } else if (order.getStatus() == GroupPurchaseOrderStatus.Refund.getValue()) {
                //tvRefund.setVisibility(View.VISIBLE);
                tvStatus.setText("已退款");
            } else {
                tvStatus.setText(GroupPurchaseOrderStatus.getGroupPurchaseCouponTypeByValue(order.getStatus()).getMemo());
            }
            imgFather.setVisibility(View.VISIBLE);
            if (CheckUtils.isNoEmptyStr(order.getGroupPurchaseCouponImages())) {
                ImageUtils.loadBitmap(mActivity, order.getGroupPurchaseCouponImages().split(";")[0], logo, R.drawable.horsegj_default, Constants.getEndThumbnail(43, 43));
            } else {
                logo.setImageResource(R.drawable.horsegj_default);
            }
            tvTitle.setText(order.getGroupPurchaseMerchantName() + (order.getGroupPurchaseCouponType() == 1 ? "代金券" : "团购券"));
            tvIntro.setText("有效期至：" + order.getGroupPurchaseCouponEndTime());
            tvTotalPrice.setText(StringUtils.BigDecimal2Str(order.getTotalPrice()));
            tvQuantity.setText(" (" + order.getQuantity() + "张)");

            tvCode.setTag(position);
            tvCode.setOnClickListener(listener);
            tvPay.setTag(position);
            tvPay.setOnClickListener(listener);
            tvEvaluate.setTag(position);
            tvEvaluate.setOnClickListener(listener);
            imgFather.setTag(position);
            imgFather.setOnClickListener(listener);

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
                //tvRefund.setVisibility(View.VISIBLE);
                tvStatus.setText("");
            } else if (order.getQueryType() == 3) { // 待评价
                tvPay.setVisibility(View.GONE);
                tvCode.setVisibility(View.GONE);
                tvEvaluate.setVisibility(View.VISIBLE);
                //tvRefund.setVisibility(View.GONE);
                tvStatus.setText("待评价");
            } else if (order.getQueryType() == 2) { // 待销费
                tvPay.setVisibility(View.GONE);
                tvCode.setVisibility(View.VISIBLE);
                tvEvaluate.setVisibility(View.GONE);
                //tvRefund.setVisibility(View.GONE);
                tvStatus.setText("待消费");
            } else if (order.getQueryType() == 1) { // 待付款
                tvPay.setVisibility(View.VISIBLE);
                tvCode.setVisibility(View.GONE);
                tvEvaluate.setVisibility(View.GONE);
                //tvRefund.setVisibility(View.GONE);
                tvStatus.setText("等待付款");
                tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), order.getPaymentExpireTime()));
            }
        }
    }

    private void showCarItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
        if (null != valueEntity) {
            LinearLayout view = holder.getView(R.id.layout_old);
            LinearLayout view2 = holder.getView(R.id.layout_new);
            CornerImageView logo = holder.getView(R.id.order_list_item_img);
            LinearLayout imgFather = holder.getView(R.id.order_list_item_img_father);
            TextView tvStatus = holder.getView(R.id.order_list_item_tv_state);
            TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
            TextView tvQuantity = holder.getView(R.id.order_list_item_tv_quantity);
            TextView tvTitle = holder.getView(R.id.order_list_item_tv_name);
            TextView tvTotalPrice = holder.getView(R.id.order_list_item_tv_money);
            TextView tvEvaluate = holder.getView(R.id.order_state_evaluate);
            TextView tvIntro = holder.getView(R.id.order_list_item_tv_intro);
            TextView tvType = holder.getView(R.id.order_list_item_tv_type);
            TextView tvMoreOrder = holder.getView(R.id.order_state_more_one);
            TextView tvInvite = holder.getView(R.id.to_invite_friend);
            TextView tvCode = holder.getView(R.id.order_state_to_see_code);
            TextView tvRefund = holder.getView(R.id.order_state_refund_to_balance);
            tvCode.setVisibility(View.GONE);
            tvRefund.setVisibility(View.GONE);
            tvInvite.setVisibility(View.GONE);
            tvMoreOrder.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            tvQuantity.setText("");

            tvStatus.setText(CarHailingOrderStatus.getOrderTypeByValue(valueEntity.getChauffeurOrder().getStatus()).getName());
            tvType.setText(UserOrderType.getOrderTypeByValue(valueEntity.getType()).getName());
            tvTitle.setText(valueEntity.getChauffeurOrder().getChauffeurOrderTrip().getStartAddress() + "-"
                    + valueEntity.getChauffeurOrder().getChauffeurOrderTrip().getEndAddress());
            tvIntro.setText("发车时间：" + valueEntity.getChauffeurOrder().getLeaveTime());
            tvTotalPrice.setText(StringUtils.BigDecimal2Str(valueEntity.getChauffeurOrder().getTotalPrice()));
            imgFather.setVisibility(View.VISIBLE);
            if (CheckUtils.isNoEmptyStr(valueEntity.getChauffeurOrder().getChauffeur().getHeaderImg())) {
                ImageUtils.loadBitmap(mActivity, valueEntity.getChauffeurOrder().getChauffeur().getHeaderImg(), logo, R.drawable.horsegj_default, Constants.getEndThumbnail(43, 43));
            } else {
                logo.setImageResource(R.drawable.horsegj_default);
            }

            tvPay.setTag(position);
            tvPay.setOnClickListener(listener);
            tvEvaluate.setTag(position);
            tvEvaluate.setOnClickListener(listener);
            imgFather.setTag(position);
            imgFather.setOnClickListener(listener);

            if (valueEntity.getChauffeurOrder().getStatus() == CarHailingOrderStatus.WaitPay.getValue()) {
                String paymentExpireTime = valueEntity.getChauffeurOrder().getPaymentExpireTime();
                if (paymentExpireTime != null) {
                    tvPay.setVisibility(View.VISIBLE);
                    tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
                } else {
                    tvPay.setVisibility(View.GONE);
                }
            } else {
                tvPay.setVisibility(View.GONE);
            }
            if (valueEntity.getChauffeurOrder().getStatus() == CarHailingOrderStatus.Done.getValue() && valueEntity.getChauffeurOrder().getHasComments() == 0) {
                tvEvaluate.setVisibility(View.VISIBLE);
            } else {
                tvEvaluate.setVisibility(View.GONE);
            }
        }
    }

    private void showGroupItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder groupHolder, int position) {
        if (valueEntity != null) {
            LinearLayout view = groupHolder.getView(R.id.layout_old);
            LinearLayout view2 = groupHolder.getView(R.id.layout_new);
            CornerImageView logo = groupHolder.getView(R.id.order_list_item_img);
            TextView groupStatus = groupHolder.getView(R.id.order_list_item_tv_state);
            TimeTextView tvPay = groupHolder.getView(R.id.order_state_go_pay);
            TextView tvQuantity = groupHolder.getView(R.id.order_list_item_tv_quantity);
            TextView tvInvite = groupHolder.getView(R.id.to_invite_friend);
            TextView tvTitle = groupHolder.getView(R.id.order_list_item_tv_name);
            TextView tvTotalPrice = groupHolder.getView(R.id.order_list_item_tv_money);
            TextView tvEvaluate = groupHolder.getView(R.id.order_state_evaluate);
            TextView tvIntro = groupHolder.getView(R.id.order_list_item_tv_intro);
            TextView tvType = groupHolder.getView(R.id.order_list_item_tv_type);
            TextView tvMoreOrder = groupHolder.getView(R.id.order_state_more_one);
            LinearLayout imgFather = groupHolder.getView(R.id.order_list_item_img_father);
            TextView tvCode = groupHolder.getView(R.id.order_state_to_see_code);
            TextView tvRefund = groupHolder.getView(R.id.order_state_refund_to_balance);
            tvCode.setVisibility(View.GONE);
            tvRefund.setVisibility(View.GONE);
            tvMoreOrder.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            tvQuantity.setText("");
            tvPay.setTag(position);
            tvEvaluate.setTag(position);
            tvInvite.setTag(position);
            imgFather.setTag(position);
            tvPay.setOnClickListener(listener);
            tvEvaluate.setOnClickListener(listener);
            tvInvite.setOnClickListener(listener);
            imgFather.setOnClickListener(listener);
            tvType.setText(UserOrderType.getOrderTypeByValue(valueEntity.getType()).getName());
            GroupBuyOrder groupBuyOrder = valueEntity.getGroupbuyOrder();
            imgFather.setVisibility(View.VISIBLE);
            if (CheckUtils.isNoEmptyStr(groupBuyOrder.getGroupBuy().getImgs())) {
                ImageUtils.loadBitmap(mActivity, groupBuyOrder.getGroupBuy().getImgs().split(";")[0], logo, R.drawable.horsegj_default, Constants.getEndThumbnail(43, 43));
            } else {
                logo.setImageResource(R.drawable.horsegj_default);
            }
            Integer status = groupBuyOrder.getStatus();
            if (status != null) {
                switch (status) {
                    case -1:
                        groupStatus.setText("已取消");
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
                        tvPay.setTimes(getTimeBetween1(new Date(), groupBuyOrder.getPaymentExpireTime()));
                        break;
                    case 2:
                        groupStatus.setText("已支付，未成团");
                        tvPay.setVisibility(View.GONE);
                        tvInvite.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        groupStatus.setText("待发货");
                        tvPay.setVisibility(View.GONE);
                        tvInvite.setVisibility(View.GONE);
                        break;
                    case 4:
                        if (groupBuyOrder.getHasComments() == 0) {
                            groupStatus.setText("交易成功");
                        } else {
                            groupStatus.setText("已完成");
                        }
                        tvPay.setVisibility(View.GONE);
                        tvInvite.setVisibility(View.GONE);
                        break;
                    case 5:
                        groupStatus.setText("未成团，退款成功");
                        tvPay.setVisibility(View.GONE);
                        tvInvite.setVisibility(View.GONE);
                        break;
                }
            } else {
                groupStatus.setVisibility(View.GONE);
            }
            tvTitle.setText(groupBuyOrder.getGroupBuy().getGoodsName());
            tvTotalPrice.setText(StringUtils.BigDecimal2Str(groupBuyOrder.getTotalPrice()));
            tvIntro.setText("");
            if (groupBuyOrder.getStatus() == 4 && groupBuyOrder.getHasComments() == 0) {
                tvEvaluate.setVisibility(View.VISIBLE);
            } else {
                tvEvaluate.setVisibility(View.GONE);
            }
        }
    }

    private void showItem(NewOrderFragmentModel.ValueEntity valueEntity, ViewHolder holder, int position) {
        if (valueEntity != null) {
            LinearLayout view = holder.getView(R.id.layout_old);
            LinearLayout view2 = holder.getView(R.id.layout_new);
            CornerImageView logo = holder.getView(R.id.order_list_item_img);
            TextView tvStatus = holder.getView(R.id.order_list_item_tv_state);
            TimeTextView tvPay = holder.getView(R.id.order_state_go_pay);
            TextView tvQuantity = holder.getView(R.id.order_list_item_tv_quantity);
            TextView tvMoreOrder = holder.getView(R.id.order_state_more_one);
            TextView tvTitle = holder.getView(R.id.order_list_item_tv_name);
            TextView tvTotalPrice = holder.getView(R.id.order_list_item_tv_money);
            TextView tvEvaluate = holder.getView(R.id.order_state_evaluate);
            TextView tvIntro = holder.getView(R.id.order_list_item_tv_intro);
            TextView tvType = holder.getView(R.id.order_list_item_tv_type);
            TextView tvInvite = holder.getView(R.id.to_invite_friend);
            LinearLayout imgFather = holder.getView(R.id.order_list_item_img_father);
            TextView tvCode = holder.getView(R.id.order_state_to_see_code);
            TextView tvRefund = holder.getView(R.id.order_state_refund_to_balance);
            tvCode.setVisibility(View.GONE);
            tvRefund.setVisibility(View.GONE);
            tvInvite.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            tvQuantity.setText("");
            tvStatus.setText(OrderFlowStatus.getOrderStatusByValue(valueEntity.getOrderFlowStatus()).getMemo());
            tvType.setText(UserOrderType.getOrderTypeByValue(valueEntity.getType()).getName());
            if (valueEntity.getMerchantId() > 0) {
                imgFather.setVisibility(View.VISIBLE);
                tvTitle.setText(valueEntity.getMerchant().getName());
                ImageUtils.loadBitmap(mActivity, valueEntity.getMerchant().getLogo(), logo, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
            } else {
                imgFather.setVisibility(View.GONE);
            }
            tvTotalPrice.setText(StringUtils.BigDecimal2Str(valueEntity.getTotalPrice()));
            tvMoreOrder.setTag(position);
            tvPay.setTag(position);
            tvEvaluate.setTag(position);
            imgFather.setTag(position);
            tvRefund.setTag(position);
            tvMoreOrder.setOnClickListener(listener);
            tvPay.setOnClickListener(listener);
            tvEvaluate.setOnClickListener(listener);
            imgFather.setOnClickListener(listener);
            tvRefund.setOnClickListener(listener);
            switch (valueEntity.getOrderFlowStatus()) {
                case -1://取消
                    changeButtonShowState(holder, true, false, false, false, false);
                    if (valueEntity.getPaymentState() == 1) {
                        //已支付
                        tvRefund.setVisibility(View.VISIBLE);
                    }
                    break;
                case 0://已创建
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 1://等待付款
                    changeButtonShowState(holder, false, false, false, false, true);
                    break;
                case 2://等待商家确认
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 3://商家已接单
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 4://配送员取货中
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 5://配送员已取货
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 6://等待送达
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 7://完成
                    if (valueEntity.getHasComments() == 0)
                        changeButtonShowState(holder, true, true, true, false, false);
                    else
                        changeButtonShowState(holder, true, true, false, false, false);
                    break;
            }
            String paymentExpireTime = valueEntity.getPaymentExpireTime();
            if (paymentExpireTime != null) {
                tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
            } else {
                tvPay.setVisibility(View.GONE);
            }
            if (CheckUtils.isNoEmptyList(valueEntity.getOrderItems())) {
                List<String> name = new ArrayList<>();
                int totalCount = 0;
                for (NewOrderFragmentModel.ValueEntity.OrderItemsEntity entity : valueEntity.getOrderItems()) {
                    name.add(entity.getName());
                    totalCount += entity.getQuantity();
                }
                tvIntro.setText(CommonUtils.getOrderIntro(name, totalCount));
            }
            if (valueEntity.getMerchant().getType() == 1) {
                tvMoreOrder.setVisibility(View.GONE);
            } else {
                tvMoreOrder.setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeButtonShowState(ViewHolder holder, boolean tvMoreOrderShow,
                                       boolean tvTakePhotoShow, boolean tvEvaluateShow, boolean tvConfirmShow, boolean tvPayShow) {
        if (tvMoreOrderShow) {
            holder.getView(R.id.order_state_more_one).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.order_state_more_one).setVisibility(View.GONE);
        }
        if (tvEvaluateShow) {
            holder.getView(R.id.order_state_evaluate).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.order_state_evaluate).setVisibility(View.GONE);
        }
        if (tvPayShow) {
            holder.getView(R.id.order_state_go_pay).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.order_state_go_pay).setVisibility(View.GONE);
        }
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
}
