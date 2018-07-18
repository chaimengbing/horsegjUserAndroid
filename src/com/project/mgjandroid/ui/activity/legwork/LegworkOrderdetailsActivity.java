package com.project.mgjandroid.ui.activity.legwork;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.LegworkOrderDetailsModel;
import com.project.mgjandroid.model.LegworkServiceChargeModel;
import com.project.mgjandroid.model.LegworkStatusModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.adapter.LegworkStatusAdapter;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.ui.view.TimeView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SunXueLiang on 2018-03-12.
 */

public class LegworkOrderdetailsActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.common_text)
    private TextView tvText;
    @InjectView(R.id.tv_goods_information)
    private TextView tvGoodsInformation;
    @InjectView(R.id.legwork_iv_buy)
    private ImageView ivLegworkIcon;
    @InjectView(R.id.legwork_tv_buy_personal_information)
    private TextView TvBuyPersonalInformation;
    @InjectView(R.id.legwork_tv_buy_address)
    private TextView tvBuy;
    @InjectView(R.id.legwork_tv_deliver_address)
    private TextView tvGive;
    @InjectView(R.id.legwork_tv_deliver_personal_information)
    private TextView TvDeliverPersonalInformation;
    @InjectView(R.id.tv_remarks)
    private TextView tvRemarks;
    @InjectView(R.id.tv_good_price)
    private TextView tvGoodPrice;
    @InjectView(R.id.tv_service_charge)
    private TextView tvServiceCharge;
    @InjectView(R.id.img_service_charge)
    private TextView imgServiceCharge;
    @InjectView(R.id.tv_order_number)
    private TextView tvOrderNumber;
    @InjectView(R.id.tv_order_time)
    private TextView tvOrderTime;
    @InjectView(R.id.rider_user_avatar)
    private RoundImageView imgRider;
    @InjectView(R.id.tv_rider_name)
    private TextView tvRiderName;
    @InjectView(R.id.img_phone)
    private ImageView imgPhone;
    @InjectView(R.id.tv_time)
    private TimeView tvPrePaymentTime;
    @InjectView(R.id.tv_cancel_order)
    private TextView tvCancelOrder;
    @InjectView(R.id.tv_payment)
    private TextView tvPayment;
    @InjectView(R.id.tv_to_evaluate)
    private TextView tvToEvaluate;
    @InjectView(R.id.layout_complete)
    private LinearLayout layoutComplete;
    @InjectView(R.id.layout_no_payment)
    private LinearLayout layoutNoPayment;
    @InjectView(R.id.layout_pick_up)
    private LinearLayout layoutNoPickUp;
    @InjectView(R.id.layout_goods_information)
    private LinearLayout layoutGoodsInformation;
    @InjectView(R.id.tv_subtitle)
    private TextView tvSubtitle;
    @InjectView(R.id.layout_good_price)
    private RelativeLayout layoutGoodPrice;
    @InjectView(R.id.layout_remarks)
    private RelativeLayout layoutRemarks;
    @InjectView(R.id.redbags_layout)
    private RelativeLayout redbagsLayout;
    @InjectView(R.id.layout_public)
    private LinearLayout layoutPublic;
    @InjectView(R.id.tv_content)
    private TextView tvContent;
    @InjectView(R.id.tv_legwork_status_1)
    private TextView tvStatus1;
    @InjectView(R.id.tv_legwork_status_2)
    private TextView tvStatus2;
    @InjectView(R.id.tv_legwork_status_3)
    private TextView tvStatus3;
    @InjectView(R.id.tv_prompt)
    private TextView tvprompt;
    @InjectView(R.id.tv_pick_up)
    private TextView tvPickUp;
    @InjectView(R.id.tv_refund_desc)
    private TextView tvRefundDesc;
    @InjectView(R.id.redbags_money_textview)
    private TextView redbagsMoneyTextView;
    @InjectView(R.id.pay_money_textview)
    private TextView payMoneyTextView;

    private String orderId;
    private LegworkOrderDetailsModel.ValueBean valueBean;
    public static final int REFRESH = 2000;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LegworkOrderDetailsModel model;
    private CallPhoneDialog dialog;
    private String regionalHeadPhone;
    private long agentId = 0;
    private PopupWindow popupWindow;
    private LegworkServiceChargeModel.ValueBean value;
    private Dialog mStatusDialog;
    ArrayList<LegworkStatusModel> legworkStatusModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_legwork_orderdetails);
        Injector.get(this).inject();
        agentId = PreferenceUtils.getLongPreference("issueAgentId", 0, mActivity);
        initView();
        getTelNumXY(agentId, 18);
    }

    private void initView() {
        orderId = getIntent().getStringExtra("orderId");
        ivBack.setOnClickListener(this);
        imgServiceCharge.setOnClickListener(this);
        tvCancelOrder.setOnClickListener(this);
        tvPayment.setOnClickListener(this);
        tvToEvaluate.setOnClickListener(this);
        tvText.setOnClickListener(this);
        tvContent.setOnClickListener(this);
        tvStatus1.setOnClickListener(this);
        tvStatus2.setOnClickListener(this);
        tvStatus3.setOnClickListener(this);
        imgPhone.setOnClickListener(this);
        tvRefundDesc.setOnClickListener(this);
        tvTitle.setText("订单详情");
        tvText.setText("客服");
        tvPrePaymentTime.setOnTimerStopListener(new TimeView.OnTimerStopListener() {
            @Override
            public void onStopListener() {
                getData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        VolleyOperater<LegworkOrderDetailsModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        operater.doRequest(Constants.URL_FIND_LEG_WORK_ORDER_BY_ORDER_ID, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    model = (LegworkOrderDetailsModel) obj;
                    valueBean = model.getValue();
                    showDetail(valueBean);
//                    getServiceCharge();
                    initStatusDialog(valueBean);
                }
            }
        }, LegworkOrderDetailsModel.class);
    }

    private void showDetail(LegworkOrderDetailsModel.ValueBean valueBean) {
        if (valueBean != null) {
            if (valueBean.getChildType() == 1) {
                tvSubtitle.setVisibility(View.GONE);
                layoutGoodsInformation.setVisibility(View.GONE);
                layoutGoodPrice.setVisibility(View.GONE);
                layoutRemarks.setVisibility(View.VISIBLE);
                switch (valueBean.getStatus()) {
                    case -1:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.GONE);
                        layoutPublic.setVisibility(View.VISIBLE);
                        if (valueBean.getPaymentState() == 1 && DateUtils.compareTimeBefore(valueBean.getCreateTime())) {
                            //已经支付
                            if (valueBean.getServePrice().equals("0.0") || valueBean.getServePrice().equals("0.00") || valueBean.getServePrice().equals("0")) {
                                tvRefundDesc.setVisibility(View.GONE);
                            } else {
                                tvRefundDesc.setVisibility(View.VISIBLE);
                            }
                        }
                        tvContent.setText("已取消");
                        tvprompt.setText("感谢使用马管家跑腿");
                        break;
                    case 1:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.VISIBLE);
                        layoutNoPickUp.setVisibility(View.GONE);
                        layoutPublic.setVisibility(View.GONE);
                        tvPayment.setText("立即支付¥" + valueBean.getServePrice());
                        long surplusTime = getTimeBetween(model.getServertime(), valueBean.getPaymentExpireTime());
                        if (surplusTime > 0) {
                            tvPrePaymentTime.setTimes(surplusTime);
                        }
                        break;
                    case 2:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.GONE);
                        layoutPublic.setVisibility(View.VISIBLE);
                        tvContent.setText("待确认");
                        tvprompt.setText("等待骑手接单");
                        break;
                    case 4:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.VISIBLE);
                        layoutPublic.setVisibility(View.GONE);
                        tvStatus1.setText("取货中");
                        tvPickUp.setText("等待骑手取货");
                        break;
                    case 5:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.VISIBLE);
                        layoutPublic.setVisibility(View.GONE);
                        tvPickUp.setText("骑手正在配送中");
                        tvStatus1.setText("配送中");
                        break;
                    case 7:
                        layoutComplete.setVisibility(View.VISIBLE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.GONE);
                        layoutPublic.setVisibility(View.GONE);
                        if (valueBean.getHasComments() == 1) {
                            tvToEvaluate.setText("已评价");
                            tvToEvaluate.setClickable(false);
                        } else if (valueBean.getHasComments() == 0) {
                            tvToEvaluate.setText("去评价");
                            tvToEvaluate.setClickable(true);
                        }
                        break;
                }
                ivLegworkIcon.setImageDrawable(getResources().getDrawable(R.drawable.icon_take));
                tvBuy.setText(valueBean.getShipperAddress());
                TvBuyPersonalInformation.setVisibility(View.VISIBLE);
                TvBuyPersonalInformation.setText(valueBean.getShipperName() + " " + valueBean.getShipperGender() + " " + valueBean.getShipperMobile());
            } else if (valueBean.getChildType() == 0) {
                tvSubtitle.setVisibility(View.VISIBLE);
                layoutGoodsInformation.setVisibility(View.VISIBLE);
                layoutGoodPrice.setVisibility(View.VISIBLE);
                layoutRemarks.setVisibility(View.GONE);
                switch (valueBean.getStatus()) {
                    case -1:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.GONE);
                        layoutPublic.setVisibility(View.VISIBLE);
                        tvContent.setText("已取消");
                        tvprompt.setText("期待下次为您服务");
                        if (valueBean.getPaymentState() == 1 && DateUtils.compareTimeBefore(valueBean.getCreateTime())) {
                            //已经支付
                            if (valueBean.getServePrice().equals("0.0") || valueBean.getServePrice().equals("0.00") || valueBean.getServePrice().equals("0")) {
                                tvRefundDesc.setVisibility(View.GONE);
                            } else {
                                tvRefundDesc.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    case 1:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.VISIBLE);
                        layoutNoPickUp.setVisibility(View.GONE);
                        layoutPublic.setVisibility(View.GONE);
                        tvPayment.setText("立即支付¥" + valueBean.getServePrice());
                        long surplusTime = getTimeBetween(model.getServertime(), valueBean.getPaymentExpireTime());
                        if (surplusTime > 0) {
                            tvPrePaymentTime.setTimes(surplusTime);
                        }
                        break;
                    case 2:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.GONE);
                        layoutPublic.setVisibility(View.VISIBLE);
                        tvContent.setText("待确认");
                        tvprompt.setText("等待骑手接单");
                        break;
                    case 4:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.VISIBLE);
                        layoutPublic.setVisibility(View.GONE);
                        tvStatus1.setText("取货中");
                        tvPickUp.setText("等待骑手取货");
                        break;
                    case 5:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.VISIBLE);
                        layoutPublic.setVisibility(View.GONE);
                        tvPickUp.setText("骑手正在配送中");
                        tvStatus1.setText("配送中");
                        break;
                    case 7:
                        layoutComplete.setVisibility(View.VISIBLE);
                        layoutNoPayment.setVisibility(View.GONE);
                        layoutNoPickUp.setVisibility(View.GONE);
                        layoutPublic.setVisibility(View.GONE);
                        if (valueBean.getHasComments() == 1) {
                            tvToEvaluate.setText("已评价");
                            tvToEvaluate.setEnabled(false);
                        } else if (valueBean.getHasComments() == 0) {
                            tvToEvaluate.setText("去评价");
                            tvToEvaluate.setEnabled(true);
                        }
                        break;
                }
                if (CheckUtils.isNoEmptyStr(valueBean.getRedBagDiscountTotalAmt()) && !"0".equals(valueBean.getRedBagDiscountTotalAmt()) && !"0.0".endsWith(valueBean.getRedBagDiscountTotalAmt())){
                    redbagsLayout.setVisibility(View.VISIBLE);
                    redbagsMoneyTextView.setText("(红包抵扣" + valueBean.getRedBagDiscountTotalAmt() + "元)");
                    payMoneyTextView.setText("￥" + valueBean.getTotalPrice());
                }else {
                    redbagsLayout.setVisibility(View.GONE);
                }
                tvGoodsInformation.setText(valueBean.getDescription());
                ivLegworkIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_legwork_buy));
                TvBuyPersonalInformation.setVisibility(View.GONE);
                if (valueBean.getShipperType() == 1) {
                    tvBuy.setText("就近购买");
                } else if (valueBean.getShipperType() == 2) {
                    String shipperHouseNumber = valueBean.getShipperHouseNumber();
                    if (TextUtils.isEmpty(shipperHouseNumber)) {
                        tvBuy.setText(valueBean.getShipperAddress());
                    } else {
                        tvBuy.setText(valueBean.getShipperAddress() + "\n" + valueBean.getShipperHouseNumber());
                    }
                }
                if (CheckUtils.isNoEmptyStr(valueBean.getGoodsEstimatePrice())) {
                    tvGoodPrice.setText("¥" + valueBean.getGoodsEstimatePrice());
                } else {
                    layoutGoodPrice.setVisibility(View.GONE);
                }
            }
            if (valueBean.getStatus() == 4 || valueBean.getStatus() == 5) {
                tvRiderName.setText(valueBean.getDeliveryTask().getDeliveryman().getName());
                if (CheckUtils.isNoEmptyStr(valueBean.getDeliveryTask().getDeliveryman().getHeaderImg())) {
                    ImageUtils.loadBitmap(mActivity, valueBean.getDeliveryTask().getDeliveryman().getHeaderImg(), imgRider, R.drawable.horsegj_default, Constants.getEndThumbnail(40, 40));
                } else {
                    imgRider.setImageDrawable(getResources().getDrawable(R.drawable.default_group_user_avatar));
                }
            }
            tvGive.setText(valueBean.getUserAddress());
            TvDeliverPersonalInformation.setText(valueBean.getUserName() + " " + valueBean.getUserGender() + " " + valueBean.getUserMobile());
            if (CheckUtils.isNoEmptyStr(valueBean.getRemark())) {
                tvRemarks.setText(valueBean.getRemark());
            } else {
                tvRemarks.setText("无");
            }
            tvServiceCharge.setText("¥" + valueBean.getServePrice());
            tvOrderNumber.setText(valueBean.getId());
            tvOrderTime.setText(valueBean.getCreateTime());
        }
    }

    private void Cancel() {
        VolleyOperater<LegworkOrderDetailsModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("status", -1);
        map.put("cancelSource", "user");
        map.put("cancelReason", "用户自主取消");
        operater.doRequest(Constants.URL_MEGER_LEG_WORK_ORDER, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    getData();
                }
            }
        }, LegworkOrderDetailsModel.class);
    }


    private void getTelNumXY(long agentId, int agentType) {
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
                        regionalHeadPhone = model.getValue().get(i).getPhone();
                    }
                }
            }
        }, CustomerAndComplainPhoneDTOModel.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REFRESH) {
//            getData();
//        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.img_service_charge:
                if (valueBean != null) {
                    mPopupWindow(valueBean);
                }
                break;
            case R.id.tv_cancel_order:
                Cancel();
                break;
            case R.id.common_text:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        //拨打电话
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.DIAL");
                        intent.setData(Uri.parse("tel:" + regionalHeadPhone));
                        mActivity.startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", regionalHeadPhone);
                dialog.show();
                break;
            case R.id.tv_payment:
                Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                intent.putExtra("orderId", valueBean.getId());
                intent.putExtra("agentId", valueBean.getAgentId());
                intent.putExtra("isLegwork", true);
                startActivityForResult(intent, REFRESH);
                break;
            case R.id.tv_to_evaluate:
                Intent intent1 = new Intent(mActivity, LegworkEvaluateActivity.class);
                intent1.putExtra("orderId", valueBean.getId());
                intent1.putExtra("agentId", "" + valueBean.getAgentId());
                startActivityForResult(intent1, REFRESH);
                break;
            case R.id.view_bg:
                dismissWindow();
                break;
            case R.id.layout_ok:
                dismissWindow();
                break;
            case R.id.tv_content:
            case R.id.tv_legwork_status_1:
            case R.id.tv_legwork_status_2:
            case R.id.tv_legwork_status_3:
                if (mStatusDialog != null) {
                    mStatusDialog.show();
                }
                break;
            case R.id.img_phone:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        //拨打电话
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.DIAL");
                        intent.setData(Uri.parse("tel:" + valueBean.getDeliveryTask().getDeliveryman().getMobile()));
                        mActivity.startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", valueBean.getDeliveryTask().getDeliveryman().getMobile());
                dialog.show();
                break;
            case R.id.tv_refund_desc:
                //退款详情
                Intent intent2 = new Intent(mActivity, OrderRefundInfoActivity.class);
                intent2.putExtra("orderId", valueBean.getId());
                startActivity(intent2);
                break;
        }
    }

    /**
     * 初始化订单状态Dialog
     */
    private void initStatusDialog(LegworkOrderDetailsModel.ValueBean valueBean) {
        LinearLayout view = (LinearLayout) View.inflate(this, R.layout.dialog_legwork_status, null);
        TextView tvBack = (TextView) view.findViewById(R.id.tv_legwork_status_back);
        RecyclerView rvStatus = (RecyclerView) view.findViewById(R.id.rv_legwork_status);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStatusDialog != null) {
                    mStatusDialog.dismiss();
                }
            }
        });

        legworkStatusModels.clear();

        if (!TextUtils.isEmpty(valueBean.getCreateTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getCreateTime());
            legworkStatusModel.setName("订单已提交");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getPaymentFinishTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getPaymentFinishTime());
            legworkStatusModel.setName("支付成功");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getAcceptedFinishTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getAcceptedFinishTime());
            legworkStatusModel.setName("骑手已接单");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getHasTakeFinishTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getHasTakeFinishTime());
            legworkStatusModel.setName("骑手已取货");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getOrderDoneTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getOrderDoneTime());
            legworkStatusModel.setName("已完成");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getOrderCancelTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getOrderCancelTime());
            legworkStatusModel.setName("已取消");
            legworkStatusModels.add(legworkStatusModel);
        }

        if (legworkStatusModels != null && legworkStatusModels.size() > 0) {
            rvStatus.setLayoutManager(new LinearLayoutManager(this));
            rvStatus.setAdapter(new LegworkStatusAdapter(legworkStatusModels, this));
        }
        mStatusDialog = null;
        mStatusDialog = new Dialog(this, R.style.MyDialogStyle);
        Window dialogWindow = mStatusDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        mStatusDialog.setContentView(view);
        dialogWindow.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
        mStatusDialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        int height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        lp.height = height / 5 * 3;
        lp.width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        dialogWindow.setAttributes(lp);
    }

    private void mPopupWindow(LegworkOrderDetailsModel.ValueBean valueBean) {
        View view = LayoutInflater.from(this).inflate(R.layout.service_charge_item, null);
        View viewBg = view.findViewById(R.id.view_bg);
        RelativeLayout lyoutOk = (RelativeLayout) view.findViewById(R.id.layout_ok);
        TextView tvBaseCharge = (TextView) view.findViewById(R.id.tv_baseCharge);
        TextView tvAddCharge = (TextView) view.findViewById(R.id.tv_addCharge);
        TextView tvStart = (TextView) view.findViewById(R.id.tv_start);
        TextView tvAdd = (TextView) view.findViewById(R.id.tv_add);
        viewBg.setOnClickListener(this);
        lyoutOk.setOnClickListener(this);
        tvBaseCharge.setText("¥" + valueBean.getBaseCharge());
        tvAddCharge.setText("¥" + valueBean.getAddCharge());
        tvStart.setText("起始价（" + valueBean.getDefDistance() + "公里）");
        tvAdd.setText("超出" + valueBean.getDefDistance() + "公里（每公里）加价");
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        if (!popupWindow.isShowing()) {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 0.5f;
            mActivity.getWindow().setAttributes(lp);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            popupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

}
