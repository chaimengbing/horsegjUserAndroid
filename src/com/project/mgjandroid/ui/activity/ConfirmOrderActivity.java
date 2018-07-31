package com.project.mgjandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.DayBean;
import com.project.mgjandroid.bean.MerchantTakeAwayMenu;
import com.project.mgjandroid.bean.PromotionActivity;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.TimeBean;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.model.SuperMarketCartModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.ConfirmOrderListAdapter;
import com.project.mgjandroid.ui.adapter.SelectDayListAdapter;
import com.project.mgjandroid.ui.adapter.SelectTimeListAdapter;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {
    public static final int REQUEST_GET_ADDRESS = 10001;
    public static final int RESPONSE_GET_ADDRESS = 10002;
    public static final int REQUEST_SET_CAUTION = 10003;
    public static final int RESPONSE_SET_CAUTION = 10004;
    public static final int REQUEST_CHOOSE_RED_BAG = 10005;
    public static final int GOTO_PAY = 10006;
    @InjectView(R.id.confirm_order_back)
    private ImageView img_back;
    @InjectView(R.id.top_address_tips)
    private TextView tv_noAddressTips;
    @InjectView(R.id.top_address_panel)
    private RelativeLayout rl_addressPanel;
    @InjectView(R.id.top_address)
    private RelativeLayout rl_topAddress;
    @InjectView(R.id.address_name)
    private TextView tv_name;
    @InjectView(R.id.confirm_order_position)
    private ImageView iv_orderPosition;
    @InjectView(R.id.address_sex)
    private TextView tv_sex;
    @InjectView(R.id.address_mobile)
    private TextView tv_mobile;
    @InjectView(R.id.address_description)
    private TextView tv_address;
    @InjectView(R.id.confirm_order_list_view)
    private NoScrollListView noScrollListView;
    @InjectView(R.id.expand_textview)
    private TextView expandTextView;
    @InjectView(R.id.receive_time)
    private TextView tv_receiveTime;
    //    @InjectView(R.id.payment_online)
    private RelativeLayout rl_onlinePay;
    //    @InjectView(R.id.payment_outline)
    private RelativeLayout rl_outlinePay;
    //    @InjectView(R.id.online_checkbox)
    private CheckBox cb_online;
    //    @InjectView(R.id.outline_checkbox)
    private CheckBox cb_outline;
    @InjectView(R.id.shipment_tip)
    private TextView tv_shipmentTip;
    @InjectView(R.id.shipping_fee)
    private TextView tv_shippingFee;
    @InjectView(R.id.use_red_bag)
    private RelativeLayout redBagLayout;
    @InjectView(R.id.new_red_bag_layout)
    private RelativeLayout newRedBagLayout;
    @InjectView(R.id.tv_new_red_bag_show)
    private TextView newRedBagShow;
    @InjectView(R.id.pay_type_layout)
    private RelativeLayout payTypeLayout;
    @InjectView(R.id.tv_pay_type_show)
    private TextView payTypeShow;
    @InjectView(R.id.use_red_bag_checkbox)
    private CheckBox cb_redBag;
    @InjectView(R.id.use_red_bag_margin)
    private View redBagMargin;
    @InjectView(R.id.tv_red_bag)
    private TextView tvRedbag;
    @InjectView(R.id.confirm_order_bottom_money)
    private TextView tv_totalPrice;
    @InjectView(R.id.box_fee_label)
    private RelativeLayout rl_boxFee;
    @InjectView(R.id.shipping_fee_label)
    private RelativeLayout rl_shipFee;
    @InjectView(R.id.box_fee)
    private TextView tv_boxFee;
    @InjectView(R.id.confirm_order)
    private TextView tv_confirm;
    @InjectView(R.id.confirm_order_select_time)
    private RelativeLayout rl_selectTime;
    @InjectView(R.id.relative_cover)
    private RelativeLayout relativeCover;
    @InjectView(R.id.confirm_order_caution)
    private RelativeLayout rl_caution;
    @InjectView(R.id.tv_order_caution)
    private TextView tv_caution;
    @InjectView(R.id.platform_redbag_layout)
    private RelativeLayout platform_redbag_layout;
    @InjectView(R.id.platform_num_textview)
    private TextView platform_num_textview;
    @InjectView(R.id.confir_order_act_shipping_fee_label)
    private LinearLayout ll_shippingFeeLabel;
    @InjectView(R.id.confir_order_act_shipping_fee_change)
    private TextView tv_shippingFeeChangeRemind;
    @InjectView(R.id.confir_order_act_promotion_label)
    private LinearLayout ll_promotionLabel;
    @InjectView(R.id.confir_order_act_promotion_change)
    private TextView tv_promotionChangeRemind;
    @InjectView(R.id.promotion_layout)
    private LinearLayout promotionLayout;
    @InjectView(R.id.confirm_order_bottom_money_free)
    private TextView freeMoney;

    private boolean isExpand;
    private double longitude;
    private double latitude;
    private JSONObject previewJsonData;
    private UserAddress userAddress;
    private ConfirmOrderModel confirmOrderModel;
    private ArrayList<ConfirmOrderModel.ValueEntity.OrderItemsEntity> data;
    private ConfirmOrderListAdapter listAdapter;
    private LayoutInflater inflater;
    private ListView timeListView;
    private SelectDayListAdapter dayListAdapter;
    private SelectTimeListAdapter timeListAdapter;
    private ArrayList<DayBean> dayList;
    private PopupWindow popupWindow;
    private int dayPosition = 0;
    private TimeBean selectTime;
    private int selectType = -1;
    private String errorMsg;
    private String caution = "";
    private MLoadingDialog mLoadingDialog;
    private boolean needPostDiscountAmt = false;
    private RedBag redBag;
    private RedBag platformRedBag;
    private CallPhoneDialog dialog;

    private boolean isFromMarket = false;
    private boolean hasRedBag = false;
    private PopupWindow pickWindow;

    private boolean canPayOnline = false, canPayOutline = false;
    private TextView tv_outline;
    private TextView tv_online;
    private MerchantTakeAwayMenu merchantTakeAwayMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Context context = this;
        inflater = LayoutInflater.from(context);
        Injector.get(this).inject();
        initPayTypePicker();
        setListener();
        latitude = Double.parseDouble(PreferenceUtils.getLocation(this)[0]);
        longitude = Double.parseDouble(PreferenceUtils.getLocation(this)[1]);
        initBottomDialog();
        mLoadingDialog = new MLoadingDialog();
        initViews();
    }

    /**
     * 底部弹框
     */
    private void initBottomDialog() {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.layout_confirm_order_select_time, null);
        ListView dayListView = (ListView) view.findViewById(R.id.day_list_view);
        dayListAdapter = new SelectDayListAdapter(R.layout.item_select_day_list_view, this);
        dayList = new ArrayList<>();
        dayListAdapter.setData(dayList);
        dayListView.setAdapter(dayListAdapter);
        dayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeListView.setSelection(0);
                dayList.get(dayPosition).setIsChecked(false);
                dayPosition = position;
                DayBean dayBean = dayList.get(position);
                dayBean.setIsChecked(true);
                dayListAdapter.notifyDataSetChanged();
                timeListAdapter.setData(dayBean.getTimeList());
            }
        });
        timeListView = (ListView) view.findViewById(R.id.time_list_view);
        timeListAdapter = new SelectTimeListAdapter(R.layout.item_select_time_list_view, this);
        ArrayList<TimeBean> timeList = new ArrayList<>();
        timeListAdapter.setData(timeList);
        timeListAdapter.setData(timeList);
        timeListView.setAdapter(timeListAdapter);
        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (DayBean dayBean : dayList) {
                    ArrayList<TimeBean> timeList = dayBean.getTimeList();
                    for (TimeBean ti : timeList) {
                        ti.setIsChecked(false);
                    }
                }
                List<TimeBean> timeBeans = timeListAdapter.getData();
                selectTime = timeBeans.get(position);
                selectTime.setIsChecked(true);
                timeListAdapter.notifyDataSetChanged();
                String selectDay = null;
                for (DayBean dayBean : dayList) {
                    if (dayBean.isChecked()) {
                        selectDay = dayBean.getDay();
                    }
                }
                if (selectDay != null && !selectDay.contains("今天")) {
                    tv_receiveTime.setText(selectDay + " " + selectTime.getDay());
                } else {
                    tv_receiveTime.setText(selectTime.getDay());
                }
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    relativeCover.setVisibility(View.GONE);
                }
                getOrderPreview();
            }
        });
        TextView tv_cancel = (TextView) view.findViewById(R.id.select_time_cancel);
        tv_cancel.setOnClickListener(this);
        View popupOutside = view.findViewById(R.id.popup_outside);
        popupOutside.setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.MenuDialogAnimation);
        popupWindow.setOutsideTouchable(true);
    }

    private void initViews() {
        expandTextView.setOnClickListener(this);
        listAdapter = new ConfirmOrderListAdapter(R.layout.item_confirm_order_list_view, this);
        data = new ArrayList<>();
        listAdapter.setData(data);
        noScrollListView.setAdapter(listAdapter);
        if (data != null && data.size() > 3) {
            expandTextView.setVisibility(View.VISIBLE);
            isExpand = false;
        } else {
            isExpand = true;
            expandTextView.setVisibility(View.GONE);
        }
        listAdapter.setExpand(isExpand);
        listAdapter.notifyDataSetChanged();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("confirmOrderModel")) {
            confirmOrderModel = (ConfirmOrderModel) intent.getSerializableExtra("confirmOrderModel");
            setViewData();
        }
        if (intent != null && intent.hasExtra("onceMoreOrder")) {
            previewJsonData = new JSONObject((Map<String, Object>) intent.getSerializableExtra("onceMoreOrder"));
            Log.d("aaa", previewJsonData.toString());
        }
        if (intent != null && intent.hasExtra("isFromMarket")) {
            isFromMarket = intent.getBooleanExtra("isFromMarket", false);
            if (true) {
                tv_caution.setHint("偏好等其他需求");
            }
        }
        if (intent != null && intent.hasExtra("caution")) {
            caution = intent.getStringExtra("caution");
            tv_caution.setText(caution);
        }
    }

    private void setListener() {
        img_back.setOnClickListener(this);
        rl_topAddress.setOnClickListener(this);
        rl_onlinePay.setOnClickListener(this);
        cb_online.setOnClickListener(this);
        rl_outlinePay.setOnClickListener(this);
        cb_outline.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        rl_selectTime.setOnClickListener(this);
        rl_caution.setOnClickListener(this);
        platform_redbag_layout.setOnClickListener(this);
        cb_redBag.setOnClickListener(this);
        redBagLayout.setOnClickListener(this);
        newRedBagLayout.setOnClickListener(this);
        payTypeLayout.setOnClickListener(this);
    }

    /**
     * 订单提交
     */
    private void submitOrder() {
        SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getUserInfo();
        List<ConfirmOrderModel.ValueEntity.OrderItemsEntity> orderItems = confirmOrderModel.getValue().getOrderItems();
        try {
            if (userInfo != null) {
                previewJsonData.put("userId", userInfo.getId());
                previewJsonData.put("loginToken", userInfo.getToken());
            }
            if (!TextUtils.isEmpty(caution)) {
                previewJsonData.put("caution", caution);
            }
            previewJsonData.put("userAddressId", userAddress.getId());
            previewJsonData.put("orderPayType", selectType);//1在线支付，2货到付款
            if (selectTime == null) {
                selectTime = dayList.get(0).getTimeList().get(0);//默认第0条
            }
            previewJsonData.put("expectedArrivalTime", selectTime.getId());//送达时间
            previewJsonData.put("orderItems", orderItems);

            if ((redBag == null && platformRedBag == null) && previewJsonData.containsKey("redBags")) {
                previewJsonData.remove("redBags");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLoadingDialog.show(getFragmentManager(), "");
//        MLog.s("----json data--->" + previewJsonData.toString());
        Map<String, Object> map = new HashMap<>();
        map.put("data", previewJsonData.toString());
        Log.d("bbb", previewJsonData.toString());
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<>(ConfirmOrderActivity.this);
        String url = Constants.URL_SUBMIT_ORDER;
        if (isFromMarket) {
            url = Constants.URL_MERCHANT_SHOP_ORDER_SUBMIT;
        }
        operater.doRequest(url, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    SubmitOrderModel submitOrderModel = (SubmitOrderModel) obj;
                    if (submitOrderModel.getCode() == 0) {
                        //TODO 下单成功操作。
                        if (isFromMarket) {
                            List<SubmitOrderModel.ValueEntity.OrderItemsEntity> clearCartData = submitOrderModel.getValue().getOrderItems();
                            SuperMarketCartModel cartModel = SuperMarketCartModel.getInstance();
                            cartModel.initData();
                            SuperMarketCartModel.SuperMarketCartBean cartBean = cartModel.getSuperMarketCartBean();
                            for (SubmitOrderModel.ValueEntity.OrderItemsEntity entity : clearCartData) {
                                cartBean.deleteGoods(entity.getGoodsId());
                            }
                            MarketCartActivity.instance.paySuccess();
                        }
                        if (selectType == 2) {
//                            ToastUtils.displayMsg("下单成功，订单编号：" + submitOrderModel.getValue().getId(), mContext);
                            Intent intent = new Intent(ConfirmOrderActivity.this, OrderDetailActivity.class);
                            intent.putExtra(OrderDetailActivity.SUBMIT_ORDER_ENTITY, submitOrderModel.getValue());
//                            intent.putExtra("hasRedPackage",true);
                            startActivity(intent);
                            clearThisMerchantCart();
                            finish();
                        } else if (selectType == 1) {
                            Intent intent = new Intent(ConfirmOrderActivity.this, OnlinePayActivity.class);
                            intent.putExtra("orderId", submitOrderModel.getValue().getId());
                            intent.putExtra("agentId", submitOrderModel.getValue().getAgentId());
                            startActivityForResult(intent, GOTO_PAY);
                            clearThisMerchantCart();
                        }
                    }
                }
            }
        }, SubmitOrderModel.class);
    }

    /**
     * 订单预览刷新
     */
    private void getOrderPreview() {
        mLoadingDialog.show(getFragmentManager(), "");
        if (!previewJsonData.containsKey("loginToken")) {
            previewJsonData.put("loginToken", App.getUserInfo().getToken());
        }
        if (!previewJsonData.containsKey("userId")) {
            previewJsonData.put("userId", App.getUserInfo().getId());
        }
        if (userAddress != null) {
            previewJsonData.put("userAddressId", userAddress.getId());
        } else {
            previewJsonData.remove("userAddressId");
        }
        if (needPostDiscountAmt) {
            previewJsonData.put("discountAmt", confirmOrderModel.getValue().getOriginalTotalPrice().doubleValue() - confirmOrderModel.getValue().getTotalPrice().doubleValue());
            needPostDiscountAmt = false;
        } else if (previewJsonData.containsKey("discountAmt")) {
            previewJsonData.remove("discountAmt");
        }
        previewJsonData.put("orderPayType", selectType);
        if (selectTime == null) {
            selectTime = dayList.get(0).getTimeList().get(0);
        }
        ArrayList<Map<String, Object>> redBagRequestDTOs = new ArrayList<>();
        if (redBag != null) {
            HashMap<String, Object> m = new HashMap<>();
            m.put("id", redBag.getId());
            m.put("name", redBag.getName());
            m.put("amt", redBag.getAmt());
            m.put("promotionType", redBag.getPromotionType());
            redBagRequestDTOs.add(m);
        }

        if (platformRedBag != null) {
            HashMap<String, Object> m = new HashMap<>();
            m.put("id", platformRedBag.getId());
            m.put("name", platformRedBag.getName());
            m.put("amt", platformRedBag.getAmt());
            m.put("promotionType", platformRedBag.getPromotionType());
            redBagRequestDTOs.add(m);
        }


        previewJsonData.put("redBags", redBagRequestDTOs);
        previewJsonData.put("expectedArrivalTime", selectTime.getId());
        Map<String, Object> params = new HashMap<>();
        params.put("data", previewJsonData.toString());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        VolleyOperater<ConfirmOrderModel> operater = new VolleyOperater<>(ConfirmOrderActivity.this);
        String url = Constants.URL_GET_ORDER_PREVIEW;
        if (isFromMarket) {
            url = Constants.URL_MERCHANT_SHOP_ORDER_PREVIEW;
        }
        operater.doRequest(url, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        errorMsg = (String) obj;
                        ToastUtils.displayMsg(errorMsg, mActivity);
                    } else {
                        errorMsg = null;
                        confirmOrderModel = (ConfirmOrderModel) obj;
                        setViewData();
                    }
                }
            }
        }, ConfirmOrderModel.class);
    }

    private void clearThisMerchantCart() {
        PickGoodsModel.getInstance().setHasChange(true);
        int position = -1;
        for (int i = 0; i < PickGoodsModel.getInstance().getMerchantPickGoodsList().size(); i++) {
            if (PickGoodsModel.getInstance().getMerchantPickGoodsList().get(i).getMerchantId() == confirmOrderModel.getValue().getMerchantId()) {
                position = i;
                break;
            }
        }
        if (position != -1) {
            PickGoodsModel.getInstance().getMerchantPickGoodsList().remove(position);
        }
    }

    /**
     * 设置界面数据
     */
    private void setViewData() {
        ConfirmOrderModel.ValueEntity valueEntity = confirmOrderModel.getValue();
        userAddress = valueEntity.getAddressInfo();
        if (userAddress != null) {
            showAddress(userAddress);
            tv_noAddressTips.setVisibility(View.INVISIBLE);
            iv_orderPosition.setVisibility(View.INVISIBLE);
            latitude = userAddress.getLatitude();
            longitude = userAddress.getLongitude();
            rl_addressPanel.setVisibility(View.VISIBLE);
        } else {
            iv_orderPosition.setBackgroundResource(R.drawable.icon_order_user_address);
        }
        if (!TextUtils.isEmpty(valueEntity.getShippingFeeDiscountTip())) {
            ll_shippingFeeLabel.setVisibility(View.VISIBLE);
            tv_shippingFeeChangeRemind.setText(valueEntity.getShippingFeeDiscountTip());
        } else {
            ll_shippingFeeLabel.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(valueEntity.getPromoMutexInfo())) {
            ll_promotionLabel.setVisibility(View.VISIBLE);
            tv_promotionChangeRemind.setText(valueEntity.getPromoMutexInfo());
        } else {
            ll_promotionLabel.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(tv_receiveTime.getText())) {
            String time = "";
            Map<String, String> map = valueEntity.getDeliveryTimes().get(0).getTimes().get(0);
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                time = map.get(key);
            }

            tv_receiveTime.setText(time);
        }
        List<ConfirmOrderModel.ValueEntity.PaymentsEntity> payments = valueEntity.getPayments();
        canPayOnline = false;
        canPayOutline = false;
        tv_online.setVisibility(View.INVISIBLE);
        cb_online.setVisibility(View.VISIBLE);
        tv_outline.setVisibility(View.INVISIBLE);
        cb_outline.setVisibility(View.VISIBLE);
        for (ConfirmOrderModel.ValueEntity.PaymentsEntity payment : payments) {
            int paymentType = payment.getPaymentType();
            if (paymentType == 1) {
//                rl_onlinePay.setVisibility(View.VISIBLE);
                canPayOnline = true;
                if (!cb_outline.isChecked()) {
                    cb_online.setChecked(true);
                    selectType = 1;
                    payTypeShow.setHint("在线支付");
                }
            } else if (paymentType == 2) {
                canPayOutline = true;
                if (!cb_online.isChecked()) {
                    cb_outline.setChecked(true);
                    selectType = 2;
                    payTypeShow.setHint("货到付款");
                }
//                rl_outlinePay.setVisibility(View.VISIBLE);
            }
        }
        if (canPayOnline) {
            tv_online.setVisibility(View.INVISIBLE);
            cb_online.setVisibility(View.VISIBLE);
        } else {
            tv_online.setVisibility(View.VISIBLE);
            cb_online.setVisibility(View.INVISIBLE);
        }
        if (canPayOutline) {
            tv_outline.setVisibility(View.INVISIBLE);
            cb_outline.setVisibility(View.VISIBLE);
        } else {
            tv_outline.setVisibility(View.VISIBLE);
            cb_outline.setVisibility(View.INVISIBLE);
        }
        String shipmentTip = valueEntity.getShipmentTip();
        tv_shipmentTip.setText("(" + shipmentTip + ")");
        List<ConfirmOrderModel.ValueEntity.OrderItemsEntity> orderItems = valueEntity.getOrderItems();
        data.clear();
        data.addAll(orderItems);
        if (data != null && data.size() > 3) {
            expandTextView.setVisibility(View.VISIBLE);
            isExpand = false;
        } else {
            isExpand = true;
            expandTextView.setVisibility(View.GONE);
        }
        listAdapter.setExpand(isExpand);
        listAdapter.notifyDataSetChanged();

        tv_shippingFee.setText("¥" + StringUtils.BigDecimal2Str(valueEntity.getShippingFee()));
        tv_totalPrice.setText("" + StringUtils.BigDecimal2Str(valueEntity.getTotalPrice()));
        if (valueEntity.getDiscountAmt() != null && BigDecimal.ZERO.compareTo(valueEntity.getDiscountAmt()) != 0 || valueEntity.getDiscountGoodsDiscountAmt() != null && BigDecimal.ZERO.compareTo(valueEntity.getDiscountGoodsDiscountAmt()) != 0) {
            freeMoney.setText(" | 优惠¥" + valueEntity.getDiscountAmt().add(valueEntity.getDiscountGoodsDiscountAmt()));
            freeMoney.setVisibility(View.VISIBLE);
        } else {
            freeMoney.setVisibility(View.GONE);
        }

        double boxPrice = valueEntity.getBoxPrice().doubleValue();
        if (boxPrice == 0) {
            rl_boxFee.setVisibility(View.GONE);
        } else {
            rl_boxFee.setVisibility(View.VISIBLE);
            tv_boxFee.setText("¥" + boxPrice);
        }
        if (valueEntity.getShippingFee() != null && valueEntity.getShippingFee().compareTo(BigDecimal.ZERO) != 0) {
            rl_shipFee.setVisibility(View.VISIBLE);
        } else {
            rl_shipFee.setVisibility(View.GONE);
        }

        if (valueEntity.getPlatformRedBags() != null && valueEntity.getPlatformRedBags().size() > 0) {
            String money = StringUtils.BigDecimal2Str(valueEntity.getPlatformRedBags().get(0).getAmt());
            if (CheckUtils.isNoEmptyStr(money)) {
                platform_num_textview.setText("-￥" + String.valueOf(money));
            }
        }
        if (valueEntity.getPlatformRedBagCount() > 0) {
            platform_num_textview.setHint("有" + valueEntity.getPlatformRedBagCount() + "个红包可用");
            platform_num_textview.setHintTextColor(getResources().getColor(R.color.platform_color));
        } else {
            platform_num_textview.setHintTextColor(getResources().getColor(R.color.color_6));
            platform_num_textview.setHint("无可用红包");
        }


        if (userAddress != null) {
            showAddress(userAddress);
            latitude = userAddress.getLatitude();
            longitude = userAddress.getLongitude();
        }
        dayList.clear();
        List<ConfirmOrderModel.ValueEntity.DeliveryTimesEntity> deliveryTimes = valueEntity.getDeliveryTimes();
        for (ConfirmOrderModel.ValueEntity.DeliveryTimesEntity deliveryTime : deliveryTimes) {
            DayBean dayBean = new DayBean(deliveryTime.getDay());
            List<Map<String, String>> times = deliveryTime.getTimes();
            ArrayList<TimeBean> timeBeans = new ArrayList<>();
            for (Map<String, String> time : times) {
                Set<String> strings = time.keySet();
                for (String str : strings) {
                    timeBeans.add(new TimeBean(str, time.get(str)));
                }
            }
            dayBean.setTimeList(timeBeans);
            dayList.add(dayBean);
        }
        if (dayList.size() > 0) {
            DayBean dayBean = dayList.get(0);
            dayBean.setIsChecked(true);
            ArrayList<TimeBean> timeList = dayBean.getTimeList();
            if (timeList.size() > 0) {
                timeList.get(0).setIsChecked(true);
            }
        }
        dayListAdapter.notifyDataSetChanged();

        promotionLayout.removeAllViews();
        if (CheckUtils.isNoEmptyList(valueEntity.getPromoList())) {
            View view = new View(mActivity);
            view.setBackgroundColor(getResources().getColor(R.color.common_gray_line));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.x1);
            params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.x15);
            promotionLayout.addView(view, params);

            for (PromotionActivity promotion : valueEntity.getPromoList()) {
                addPromotion(promotionLayout, promotion);
            }
            promotionLayout.setVisibility(View.VISIBLE);
        } else {
            promotionLayout.setVisibility(View.GONE);
        }
//        if (CheckUtils.isNoEmptyList(valueEntity.getRedBags())) {
//            for (RedBag promotion : valueEntity.getRedBags()) {
//                addPromotion(promotionLayout, promotion);
//            }
//            promotionLayout.setVisibility(View.VISIBLE);
//        } else {
//        if (CheckUtils.isEmptyList(valueEntity.getPromoList())) {
//            promotionLayout.setVisibility(View.GONE);
//        }
//        }

        if (valueEntity.getRedBagUsableCount() > 0) {
            hasRedBag = true;
            newRedBagLayout.setVisibility(View.VISIBLE);
            newRedBagShow.setHint("有" + valueEntity.getRedBagUsableCount() + "个代金券可用");
            if (redBag != null && redBag.getAmt() != null) {
                newRedBagShow.setText("-￥" + StringUtils.BigDecimal2Str(redBag.getAmt()));
            } else {
                newRedBagShow.setText("");
            }
        } else {
            redBag = null;
            hasRedBag = false;
            newRedBagLayout.setVisibility(View.GONE);
            newRedBagShow.setHint("无可用代金券");
            newRedBagShow.setText("");
        }

//        if (valueEntity.getRedBagUsableCount() > 0) {
//            redBagLayout.setVisibility(View.VISIBLE);
//            redBagMargin.setVisibility(View.VISIBLE);
//            if (redBag != null && redBag.getAmt() != null) {
//                cb_redBag.setChecked(true);
//                tvRedbag.setVisibility(View.VISIBLE);
//                tvRedbag.setText("红包金额为" + StringUtils.BigDecimal2Str(redBag.getAmt()) + "元");
//            } else {
//                cb_redBag.setChecked(false);
//                tvRedbag.setVisibility(View.GONE);
//            }
//        } else {
//            redBag = null;
//            redBagLayout.setVisibility(View.GONE);
//            redBagMargin.setVisibility(View.GONE);
//            tvRedbag.setVisibility(View.GONE);
//            cb_redBag.setChecked(false);
//        }
    }

    private void addPromotion(LinearLayout layout, PromotionActivity promotion) {
        LinearLayout childLayout = new LinearLayout(mActivity);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        if (CheckUtils.isNoEmptyStr(promotion.getPromoImg())) {
            ImageView image = new ImageView(mActivity);
            ImageUtils.loadBitmap(mActivity, promotion.getPromoImg(), image, R.drawable.jian, "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DipToPx.dip2px(mActivity, 12), DipToPx.dip2px(mActivity, 12));
            childLayout.addView(image, params);
        }
        if (CheckUtils.isNoEmptyStr(promotion.getRule())) {
            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = DipToPx.dip2px(mActivity, 5);
            tv.setText(promotion.getRule());
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextColor(mActivity.getResources().getColor(R.color.gray_text_3));
            tv.setTextSize(12);
            childLayout.addView(tv, params);
        }
        if (promotion.getDiscountAmt() != null) {
            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.x15);
            params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x15);
            if (promotion.getType() == 0) {
                tv.setText("- ¥" + promotion.getDiscountAmt());
            } else {
                tv.setText("");
            }
            tv.setTextColor(0xffff5959);
            tv.setTextSize(12);
            tv.setGravity(Gravity.RIGHT);
            childLayout.addView(tv, params);
        }
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsChild.topMargin = DipToPx.dip2px(mActivity, 14);
        layout.addView(childLayout, paramsChild);
    }

    private void addPromotion(LinearLayout layout, RedBag promotion) {
        LinearLayout childLayout = new LinearLayout(mActivity);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        if (CheckUtils.isNoEmptyStr(promotion.getRedBagImg())) {
            ImageView image = new ImageView(mActivity);
            ImageUtils.loadBitmap(mActivity, promotion.getRedBagImg(), image, R.drawable.jian, "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DipToPx.dip2px(mActivity, 12), DipToPx.dip2px(mActivity, 12));
            childLayout.addView(image, params);
        }
        if (CheckUtils.isNoEmptyStr(promotion.getTitle())) {
            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = DipToPx.dip2px(mActivity, 5);
            tv.setText(promotion.getTitle());
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextColor(mActivity.getResources().getColor(R.color.gray_text_3));
            tv.setTextSize(12);
            childLayout.addView(tv, params);
        }
        if (promotion.getAmt() != null) {
            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.x15);
            params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x15);
            tv.setText("- ¥" + promotion.getAmt());
            tv.setTextColor(0xffff5959);
            tv.setTextSize(12);
            tv.setGravity(Gravity.RIGHT);
            childLayout.addView(tv, params);
        }
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsChild.topMargin = DipToPx.dip2px(mActivity, 14);
        layout.addView(childLayout, paramsChild);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_order_back:
                finish();
                break;
            case R.id.expand_textview:
                Drawable drawable = null;
                if (isExpand) {
                    expandTextView.setText("点击展开");
                    drawable = ContextCompat.getDrawable(getApplication(), R.drawable.icon_expand);
                    isExpand = false;
                } else {
                    isExpand = true;
                    expandTextView.setText("点击收起");
                    drawable = ContextCompat.getDrawable(getApplication(), R.drawable.icon_packup);
                }
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                expandTextView.setCompoundDrawables(null, null, drawable, null);
                listAdapter.setExpand(isExpand);
                listAdapter.notifyDataSetChanged();
                break;
            case R.id.top_address:
                if (!App.isLogin()) {
                    startActivity(new Intent(mActivity, SmsLoginActivity.class));
                    break;
                }
                Intent intent = new Intent(this, AddressManageActivity.class);
                intent.putExtra("MERCHANT_ID", confirmOrderModel.getValue().getMerchantId());
                if (userAddress != null) {
                    intent.putExtra("USER_ADDRESS_ID", userAddress.getId());
                }
                startActivityForResult(intent, REQUEST_GET_ADDRESS);
                break;
            case R.id.online_checkbox:
            case R.id.payment_online:
                if (!canPayOnline) {
                    break;
                }
                cb_outline.setChecked(false);
                cb_online.setChecked(true);
                selectType = 1;
                getOrderPreview();
                pickWindow.dismiss();
                break;
            case R.id.outline_checkbox:
            case R.id.payment_outline:
                if (!canPayOutline) {
                    break;
                }
                cb_online.setChecked(false);
                cb_outline.setChecked(true);
                selectType = 2;
                getOrderPreview();
                ToastUtils.displayMsg("货到付款无法享受优惠活动", mActivity);
                pickWindow.dismiss();
                break;
            case R.id.confirm_order:
                if (!TextUtils.isEmpty(errorMsg)) {
                    ToastUtils.displayMsg(errorMsg, mActivity);
                    return;
                }
                if (userAddress == null) {
                    ToastUtils.displayMsg("请选择配送地址", mActivity);
                    break;
                }
                if (selectType == -1) {
                    ToastUtils.displayMsg("请选择支付方式", mActivity);
                    break;
                }
                if (TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                    showDialog();
                    return;
                }
                submitOrder();
                break;
            case R.id.confirm_order_select_time:
                if (!TextUtils.isEmpty(errorMsg)) {
                    ToastUtils.displayMsg(errorMsg, mActivity);
                    return;
                }
                if (popupWindow != null && !popupWindow.isShowing()) {
                    for (DayBean dayBean : dayList) {
                        if (dayBean.isChecked()) {
                            timeListAdapter.setData(dayBean.getTimeList());
                        }
                    }
                    popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                    relativeCover.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.confirm_order_caution:
                Intent intentCaution = new Intent(ConfirmOrderActivity.this, AddCautionActivity.class);
                intentCaution.putExtra("caution", caution);
                ConfirmOrderActivity.this.startActivityForResult(intentCaution, REQUEST_SET_CAUTION);
                break;
            case R.id.platform_redbag_layout:
                if (confirmOrderModel.getValue().getRedBagSharedRelation() == 0) {
                    if (redBag != null) {
                        toast("不可与商家代金券优惠同时使用");
                        return;
                    }
                }
                ConfirmOrderModel.ValueEntity valueEntity = confirmOrderModel.getValue();
                Intent intentSelect = new Intent(ConfirmOrderActivity.this, SelectRedBagActivity.class);
                intentSelect.putExtra(SelectRedBagActivity.ITEMS_PRICE, valueEntity.getItemsPrice().doubleValue());
                intentSelect.putExtra(SelectRedBagActivity.PROMOINFO_JSON, JSON.toJSONString(valueEntity.getPromoList()));
                intentSelect.putExtra(SelectRedBagActivity.MERCHANT_ID, valueEntity.getMerchantId());
                intentSelect.putExtra(SelectRedBagActivity.ADDRESS, userAddress);
                intentSelect.putExtra(SelectRedBagActivity.BUSINESS_TYPE, isFromMarket ? 3 : 1);
                if (platformRedBag != null) {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, platformRedBag.getId());
                } else {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, -1l);
                }
                ConfirmOrderActivity.this.startActivityForResult(intentSelect, REQUEST_SET_CAUTION);
                break;
            case R.id.select_time_cancel:
            case R.id.popup_outside:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    relativeCover.setVisibility(View.GONE);
                }
                break;
            case R.id.use_red_bag_checkbox:
                if (redBag == null && cb_redBag.isChecked()) {
                    cb_redBag.setChecked(false);
                }
                if (userAddress == null) return;
                if (redBag != null) {
                    redBag = null;
                    cb_redBag.setChecked(false);
                    getOrderPreview();
                    break;
                }
            case R.id.use_red_bag:
            case R.id.new_red_bag_layout:
                if (!hasRedBag) {
                    break;
                }
                if (confirmOrderModel.getValue().getRedBagSharedRelation() == 0) {
                    if (platformRedBag != null) {
                        toast("不可与平台红包优惠同时使用");
                        return;
                    }
                }
                Intent intentRedBag = new Intent(ConfirmOrderActivity.this, MyRedBagActivity.class);
                if (userAddress != null) {
                    intentRedBag.putExtra("longitude", userAddress.getLongitude());
                    intentRedBag.putExtra("latitude", userAddress.getLatitude());
                }
                intentRedBag.putExtra("itemsPrice", confirmOrderModel.getValue().getItemsPrice().doubleValue());
                intentRedBag.putExtra("merchantId", confirmOrderModel.getValue().getMerchantId());
                intentRedBag.putExtra("discountGoodsDiscountAmt", "" + confirmOrderModel.getValue().getDiscountGoodsDiscountAmt());
                intentRedBag.putExtra("isFromConfirmOrder", true);
                if (redBag != null) {
                    intentRedBag.putExtra("redBagId", redBag.getId());
                } else {
                    intentRedBag.putExtra("redBagId", -1l);
                }
                try {
                    intentRedBag.putExtra("PromoInfoJson", JSONArray.toJSONString(confirmOrderModel.getValue().getPromoList()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ConfirmOrderActivity.this.startActivityForResult(intentRedBag, REQUEST_CHOOSE_RED_BAG);
                break;
            case R.id.pay_type_layout:
                showPayTypeWindow();
                break;
        }
    }

    public void showDialog() {
        if (dialog == null) {
            dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    Intent intent = new Intent(mActivity, BindMobileActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }

                @Override
                public void onExit() {
                    dialog.dismiss();
                }
            }, "", "请绑定您的手机号，以完成下单！", "立刻去绑定", "残忍拒绝", "#ff9a00", "#333333");
        }
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (resultCode) {
                case RESPONSE_GET_ADDRESS:
                    if (data.getSerializableExtra("address") == null) {
                        userAddress = null;
                        rl_addressPanel.setVisibility(View.INVISIBLE);
                        tv_noAddressTips.setVisibility(View.VISIBLE);
                        needPostDiscountAmt = false;
                        getOrderPreview();
                        break;
                    }
                    if (userAddress == null || userAddress.getId() != ((UserAddress) data.getSerializableExtra("address")).getId()) {
                        needPostDiscountAmt = true;
                    }
                    userAddress = (UserAddress) data.getSerializableExtra("address");
                    tv_noAddressTips.setVisibility(View.INVISIBLE);
                    showAddress(userAddress);
                    latitude = userAddress.getLatitude();
                    longitude = userAddress.getLongitude();
                    rl_addressPanel.setVisibility(View.VISIBLE);
                    getOrderPreview();
                    break;
                case RESPONSE_SET_CAUTION:
                    caution = data.getStringExtra("caution");
                    if (caution != null) tv_caution.setText(caution);
                    break;
                case SelectRedBagActivity.RED_BAG_MONEY:
                    platformRedBag = (RedBag) data.getSerializableExtra(SelectRedBagActivity.RED_MONEY_BAG);
                    getOrderPreview();
                    break;
            }
        }
        if (requestCode == REQUEST_CHOOSE_RED_BAG) {
            if (data != null && resultCode == MyRedBagActivity.RESPONSE_CHOOSE_RED_BAG) {
                RedBag bag = (RedBag) data.getSerializableExtra("red_bag");
                if (bag == null) {
                    redBag = null;
                    getOrderPreview();
                } else if (null == redBag || (bag != null && !redBag.equals(bag))) {
                    redBag = bag;
                    cb_redBag.setChecked(true);
                    getOrderPreview();
                }
            }
        }
        if (requestCode == GOTO_PAY && resultCode == RESULT_OK) {
            //支付成功
            finish();
        }
    }

    private void showAddress(UserAddress userAddress) {
        tv_name.setText(userAddress.getName());
        tv_sex.setText(userAddress.getGender());
        tv_mobile.setText(userAddress.getMobile());
        tv_address.setText(CheckUtils.isNoEmptyStr(userAddress.getDetailedAddress()) ? userAddress.getDetailedAddress() : userAddress.getAddress());
    }

    private void initPayTypePicker() {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.view_choose_pay_type, null);
        rl_onlinePay = (RelativeLayout) contentView.findViewById(R.id.payment_online);
        rl_outlinePay = (RelativeLayout) contentView.findViewById(R.id.payment_outline);
        cb_online = (CheckBox) contentView.findViewById(R.id.online_checkbox);
        cb_outline = (CheckBox) contentView.findViewById(R.id.outline_checkbox);
        tv_outline = (TextView) contentView.findViewById(R.id.outline_unsupport);
        tv_online = (TextView) contentView.findViewById(R.id.online_unsupport);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickWindow.dismiss();
            }
        });

        pickWindow = new PopupWindow(contentView, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT, true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        pickWindow.setBackgroundDrawable(cd);
        pickWindow.setOutsideTouchable(true);
        pickWindow.setFocusable(true);

        pickWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
    }

    private void showPayTypeWindow() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mActivity.getWindow().setAttributes(lp);
        pickWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

}
