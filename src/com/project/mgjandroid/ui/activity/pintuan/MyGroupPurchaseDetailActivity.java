package com.project.mgjandroid.ui.activity.pintuan;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.constants.AgentRequestType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.GroupOrderDetailModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.OrderDetailActivity;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/8/15.
 */
public class MyGroupPurchaseDetailActivity extends BaseActivity {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.com_share)
    private ImageView ivPhone;
    @InjectView(R.id.group_is_waiting_layout)
    private RelativeLayout rlIsWaiting;
    @InjectView(R.id.group_no_change_layout)
    private RelativeLayout rlNoWaiting;
    @InjectView(R.id.my_pintuan_image_1)
    private CornerImageView ivImage1;
    @InjectView(R.id.my_pintuan_image_2)
    private CornerImageView ivImage2;
    @InjectView(R.id.my_pintuan_goods_title_1)
    private TextView tvTitle1;
    @InjectView(R.id.my_pintuan_goods_title_2)
    private TextView tvTitle2;
    @InjectView(R.id.my_pintuan_goods_price)
    private TextView tvPrice1;
    @InjectView(R.id.my_pintuan_goods_price_2)
    private TextView tvPrice2;
    @InjectView(R.id.my_pintuan_total_num)
    private TextView tvTotalCount1;
    @InjectView(R.id.my_pintuan_total_num_2)
    private TextView tvTotalCount2;
    @InjectView(R.id.my_pintuan_total_price)
    private TextView tvTotalPrice1;
    @InjectView(R.id.my_pintuan_total_price_2)
    private TextView tvTotalPrice2;
    @InjectView(R.id.my_group_status)
    private ImageView ivStatusShow;
    @InjectView(R.id.my_group_invite_friend)
    private TextView tvInvite;
    @InjectView(R.id.my_pintuan_user_header_img)
    private RoundImageView ivUserHeader;
    @InjectView(R.id.my_pintuan_user_name)
    private TextView tvUsername;
    @InjectView(R.id.my_pintuan_group_status)
    private TextView tvGroupStatus;
    @InjectView(R.id.address_name)
    private TextView tv_name;
    @InjectView(R.id.address_sex)
    private TextView tv_sex;
    @InjectView(R.id.address_mobile)
    private TextView tv_mobile;
    @InjectView(R.id.address_description)
    private TextView tv_address;
    @InjectView(R.id.group_order_to_group_detail)
    private TextView tvToDetail;
    @InjectView(R.id.group_order_to_group_layout)
    private LinearLayout llToDetail;
    @InjectView(R.id.my_pintuan_order_id)
    private TextView tvOrderId;
    @InjectView(R.id.my_pintuan_order_create_time)
    private TextView tvOrderCreatTime;
    @InjectView(R.id.my_pintuan_group_create_time)
    private TextView tvGroupCreatTime;
    @InjectView(R.id.my_pintuan_order_pay_time)
    private TextView tvPayTime;
    @InjectView(R.id.my_pintuan_maybe_favor)
    private TextView tvFavor;
    @InjectView(R.id.pintuan_favor_layout)
    private LinearLayout llFavor;
    @InjectView(R.id.ll_order_status)
    private LinearLayout llStatus;
    @InjectView(R.id.tv_group_state)
    private TextView tvGroupState;
    @InjectView(R.id.tv_group_refund)
    private TextView tvGroupRefund;
    @InjectView(R.id.pay_cancel_layout)
    private LinearLayout llPayCancel;
    @InjectView(R.id.cancel_order)
    private TextView tvCancel;
    @InjectView(R.id.to_pay_order)
    private TextView tvPay;
    @InjectView(R.id.pintuan_redbags_price)
    private TextView pintuanRedbagsPrice;
    @InjectView(R.id.my_group_detail_layout)
    private ScrollView svDetail;

    private String orderId;
    private GroupOrderDetailModel model;
    private ShareUtil shareUtil;
    private MLoadingDialog mMLoadingDialog;
    private CallPhoneDialog stickyDialog;
    private CallPhoneDialog dialog;
    private String constomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group_purchase_detail);
        Injector.get(this).inject();
        initView();
        setLinstener();
    }

    private void setLinstener() {
        ivBack.setOnClickListener(this);
        tvInvite.setOnClickListener(this);
        tvToDetail.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        llStatus.setOnClickListener(this);
        tvGroupRefund.setOnClickListener(this);
    }

    private void initView() {
        tvTitle.setText("订单详情");
        if (getIntent().hasExtra(OrderDetailActivity.ORDER_ID)) {
            orderId = getIntent().getStringExtra(OrderDetailActivity.ORDER_ID);
            mMLoadingDialog = new MLoadingDialog();
            getData();
        } else {
            finish();
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put(OrderDetailActivity.ORDER_ID, orderId);
        VolleyOperater<GroupOrderDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_NEW_ORDER_DETAIL, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    model = (GroupOrderDetailModel) obj;
                    getTelNumXY(model.getValue().getAgentId(), AgentRequestType.Groupbuy.getValue());
                    setView(model);
                } else {

                }
            }
        }, GroupOrderDetailModel.class);
    }

    private void setView(GroupOrderDetailModel model) {
        svDetail.setVisibility(View.VISIBLE);
        ////1 "审核中",2 "审核失败",3 "组团中",4 "组团成功",5 "组团失败";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fade_fade_in);
            animator.setTarget(svDetail);
            animator.start();
        }
        GroupOrderDetailModel.ValueEntity order = model.getValue();
        int status = order.getGroupbuyOrder().getGroupBuy().getStatus();
        switch (status) {
            case 3:
                rlNoWaiting.setVisibility(View.GONE);
                rlIsWaiting.setVisibility(View.VISIBLE);
                break;
            case 4:
                rlNoWaiting.setVisibility(View.VISIBLE);
                rlIsWaiting.setVisibility(View.GONE);
                ivStatusShow.setImageResource(R.drawable.my_group_success);
                tvGroupCreatTime.setVisibility(View.VISIBLE);
                tvGroupCreatTime.setText("成团时间:" + order.getGroupbuyOrder().getGroupBuy().getEndTime());
                break;
            case 5:
            case -1:
                rlNoWaiting.setVisibility(View.VISIBLE);
                rlIsWaiting.setVisibility(View.GONE);
                ivStatusShow.setImageResource(R.drawable.my_group_fail);
                break;
        }
        if (CheckUtils.isNoEmptyStr(order.getGroupbuyOrder().getGroupBuy().getImgs())) {
            ImageUtils.loadBitmap(mActivity, order.getGroupbuyOrder().getGroupBuy().getImgs().split(";")[0], ivImage1, R.drawable.horsegj_default, Constants.getEndThumbnail(75, 75));
            ImageUtils.loadBitmap(mActivity, order.getGroupbuyOrder().getGroupBuy().getImgs().split(";")[0], ivImage2, R.drawable.horsegj_default, Constants.getEndThumbnail(75, 75));
        }
        List<RedBag> redBags = JSON.parseArray(order.getRedBagJson(), RedBag.class);
        if (redBags != null && redBags.size() > 0){
            pintuanRedbagsPrice.setText("红包抵扣" + redBags.get(0).getAmt() + "元");
            pintuanRedbagsPrice.setVisibility(View.VISIBLE);
        }else {
            pintuanRedbagsPrice.setVisibility(View.INVISIBLE);
        }
        tvTitle1.setText(order.getGroupbuyOrder().getGroupBuy().getGoodsName());
        tvTitle2.setText(order.getGroupbuyOrder().getGroupBuy().getGoodsName());
        tvPrice1.setText(order.getGroupbuyOrder().getPrice() + "");
        tvPrice2.setText(order.getGroupbuyOrder().getPrice() + "");
        tvTotalCount1.setText("共" + order.getGroupbuyOrder().getQuantity() + "件商品  合计：");
        tvTotalCount2.setText("共" + order.getGroupbuyOrder().getQuantity() + "件商品  合计：");
        tvTotalPrice1.setText(order.getGroupbuyOrder().getTotalPrice() + "");
        tvTotalPrice2.setText(order.getGroupbuyOrder().getTotalPrice() + "");

        if (CheckUtils.isNoEmptyStr(order.getGroupbuyOrder().getGroupBuy().getGroupBuyUser().getHeaderImg())) {
            ImageUtils.loadBitmap(mActivity, order.getGroupbuyOrder().getGroupBuy().getGroupBuyUser().getHeaderImg(), ivUserHeader,
                    R.drawable.default_group_user_avatar, Constants.getEndThumbnail(42, 42));
        }
        tvUsername.setText(order.getGroupbuyOrder().getGroupBuy().getGroupBuyUser().getName());
        Integer groupStatus = order.getGroupbuyOrder().getStatus();
        if (groupStatus != null) {
            switch (groupStatus) {
                case -1:
                    tvGroupStatus.setText("取消订单");
                    rlNoWaiting.setVisibility(View.GONE);
                    rlIsWaiting.setVisibility(View.GONE);
                    llStatus.setVisibility(View.VISIBLE);
                    if (order.getGroupbuyOrder().getPaymentState() == 1 && DateUtils.compareTimeBefore(order.getGroupbuyOrder().getCreateTime())) {
                        //已付款
                        if(StringUtils.BigDecimal2Str(order.getGroupbuyOrder().getTotalPrice()).equals("0")){
                            tvGroupRefund.setVisibility(View.GONE);
                        }else {
                            tvGroupRefund.setVisibility(View.VISIBLE);
                        }
                        tvGroupState.setText("申请退款成功");
                    } else {
                        tvGroupState.setText("订单已取消");
                    }
                    llToDetail.setVisibility(View.GONE);
                    llPayCancel.setVisibility(View.GONE);
                    break;
                case 0:
                    tvGroupStatus.setText("订单创建");
//                    groupHolder.tvPay.setVisibility(View.GONE);
                    break;
                case 1:
                    tvGroupStatus.setText("等待付款");
//                    groupHolder.tvPay.setVisibility(View.VISIBLE);
//                    groupHolder.tvPay.setTimes(getTimeBetween1(new Date(), groupBuyOrder.getPaymentExpireTime()));
                    llStatus.setVisibility(View.VISIBLE);
                    tvGroupState.setText("等待买家付款");
                    llToDetail.setVisibility(View.GONE);
                    rlNoWaiting.setVisibility(View.GONE);
                    rlIsWaiting.setVisibility(View.GONE);
                    llPayCancel.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    tvGroupStatus.setText("已支付，未成团");
//                    groupHolder.tvPay.setVisibility(View.GONE);
                    break;
                case 3:
                    tvGroupStatus.setText("待发货");
//                    groupHolder.tvPay.setVisibility(View.GONE);
                    break;
                case 4:
                    if (order.getGroupbuyOrder().getHasComments() == 0) {
                        tvGroupStatus.setText("交易成功");
                    } else {
                        tvGroupStatus.setText("已完成");
                    }
//                    groupHolder.tvPay.setVisibility(View.GONE);
                    if (CheckUtils.isNoEmptyStr(order.getGroupbuyOrder().getOrderDoneTime())) {
                        tvPayTime.setVisibility(View.VISIBLE);
                        tvPayTime.setText("成交时间:" + order.getGroupbuyOrder().getOrderDoneTime());
                    }
                    break;
                case 5:
                    tvGroupStatus.setText("未成团，退款成功");
//                    groupHolder.tvPay.setVisibility(View.GONE);
                    break;
            }
        } else {
            tvGroupStatus.setVisibility(View.GONE);
        }
        tv_name.setText(order.getGroupbuyOrder().getUserName());
        tv_sex.setText(order.getGroupbuyOrder().getUserGender());
        tv_mobile.setText(order.getGroupbuyOrder().getUserMobile());
        tv_address.setText(order.getGroupbuyOrder().getUserAddress() + " " + order.getGroupbuyOrder().getUserHouseNumber());

        tvOrderId.setText("订单编号:" + order.getId());
        tvOrderCreatTime.setText("下单时间:" + order.getGroupbuyOrder().getCreateTime());

        if (CheckUtils.isNoEmptyList(order.getGroupbuyOrder().getGroupBuy().getOtherGroupBuyList()) &&
                order.getGroupbuyOrder().getGroupBuy().getOtherGroupBuyList().size() == 2) {
            for (int i = 0; i < order.getGroupbuyOrder().getGroupBuy().getOtherGroupBuyList().size(); i++) {
                View view = llFavor.getChildAt(i);
                GroupOrderDetailModel.ValueEntity.GroupbuyOrderBean.GroupBuyBean.OtherGroupBuyListBean group = order.getGroupbuyOrder().getGroupBuy().getOtherGroupBuyList().get(i);
                CornerImageView image = (CornerImageView) view.findViewById(R.id.pintuan_favor_image);
                TextView title = (TextView) view.findViewById(R.id.pintuan_favor_title);
                TextView price = (TextView) view.findViewById(R.id.pintuan_favor_price);
                ProgressBar bar = (ProgressBar) view.findViewById(R.id.pintuan_favor_progress);
                if (CheckUtils.isNoEmptyStr(group.getImgs())) {
                    ImageUtils.loadBitmap(mActivity, group.getImgs().split(";")[0], image, R.drawable.group_zhanwei, "");
                }
                title.setText(group.getGoodsName());
                price.setText(group.getPrice() + "");
                int progress = group.getTotalNum() * 100 / group.getMinNum();
                if (progress >= 100) {
                    progress = 100;
                }
                bar.setProgress(progress);
                final String id = group.getId();
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mActivity, GroupPurchaseDetailActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
            }
        } else {
            tvFavor.setVisibility(View.GONE);
            llFavor.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.my_group_invite_friend://邀请好友
                if (shareUtil == null && model != null) {
                    shareUtil = new ShareUtil(mActivity, model.getValue().getGroupbuyOrder().getGroupBuy().getGoodsName(),
                            model.getValue().getGroupbuyOrder().getGroupBuy().getDescription(),
                            model.getValue().getGroupbuyOrder().getGroupBuy().getShareUrl(),
                            model.getValue().getGroupbuyOrder().getGroupBuy().getImgs());
                }
                if (shareUtil != null) shareUtil.showPopupWindow();
                break;
            case R.id.com_share://电话
                if (model != null && CheckUtils.isNoEmptyStr(model.getValue().getGroupbuyOrder().getAgentInfoMap().getPhone())) {
                    showStickyDialog(constomer);
                }
                break;
            case R.id.group_order_to_group_detail:
                Intent groupIntent = new Intent(mActivity, GroupPurchaseDetailActivity.class);
                groupIntent.putExtra("id", model.getValue().getGroupbuyOrder().getGroupBuyId());
                startActivity(groupIntent);
                break;

            case R.id.cancel_order:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        cancelOrder();
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", "确定要取消订单？", "确定", "还是不了");
                dialog.show();
                break;
            case R.id.to_pay_order:
                Intent payIntent = new Intent(mActivity, OnlinePayActivity.class);
                payIntent.putExtra("orderId", orderId);
                payIntent.putExtra("isGroup", true);
                startActivityForResult(payIntent, 100);
                break;
            case R.id.ll_order_status:
                if (model != null && model.getValue() != null) {
                    if (model.getValue().getGroupbuyOrder().getStatus() == -1 && model.getValue().getPaymentState() == 1) {
                        Intent intent2 = new Intent(mActivity, OrderRefundInfoActivity.class);
                        intent2.putExtra("orderId", orderId);
                        startActivity(intent2);
                    }
                }
                break;
            case R.id.tv_group_refund:
                //退款
                if (model.getValue().getGroupbuyOrder().getStatus() == -1 && model.getValue().getGroupbuyOrder().getPaymentState() == 1) {
                    Intent intent2 = new Intent(mActivity, OrderRefundInfoActivity.class);
                    intent2.putExtra("orderId", model.getValue().getGroupbuyOrder().getId());
                    startActivity(intent2);
                }
                break;
        }
    }

    private void cancelOrder() {
        mMLoadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put(OrderDetailActivity.ORDER_ID, orderId);
        VolleyOperater<GroupOrderDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CANCEL_ORDER_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    getData();
                } else {

                }
            }
        }, GroupOrderDetailModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                finish();
                break;
        }
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }

    private void showStickyDialog(final String phone) {
        if (stickyDialog != null) {
            stickyDialog.cancel();
            stickyDialog = null;
        }
        stickyDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(mActivity.getString(R.string.withdraw_phone), phone)));
                mActivity.startActivity(intent);
                stickyDialog.dismiss();
            }

            @Override
            public void onExit() {
                stickyDialog.dismiss();
            }
        }, "", phone);
        stickyDialog.show();
    }

    private void getTelNumXY(int agentId, int agentType) {
        final Map<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("type", agentType);
        VolleyOperater<CustomerAndComplainPhoneDTOModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_TELNUM_ID_NEW, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (obj instanceof String) {
                    ToastUtils.displayMsg(obj.toString(), mActivity);
                    return;
                }
                CustomerAndComplainPhoneDTOModel model = (CustomerAndComplainPhoneDTOModel) obj;
                for (int i = 0; i < model.getValue().size(); i++) {
                    if (model.getValue() != null && 1 == model.getValue().get(i).getType()) {
                        constomer = model.getValue().get(i).getPhone();
                    }
                }
            }
        }, CustomerAndComplainPhoneDTOModel.class);
    }

}
