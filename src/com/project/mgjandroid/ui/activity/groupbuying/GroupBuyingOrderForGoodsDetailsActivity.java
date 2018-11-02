package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupBuyingTest;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCoupon;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCouponGoods;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCouponGoodsType;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderCouponCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingCouponModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingMerchantModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.adapter.RefundListAdapter;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.ui.view.RefundDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.project.mgjandroid.R.id.group_buying_feedback;
import static com.project.mgjandroid.R.id.group_buying_refund;
import static com.project.mgjandroid.R.id.img_phone;
import static com.project.mgjandroid.R.id.layout_address;
import static com.project.mgjandroid.R.id.layout_group_buying_details;
import static com.project.mgjandroid.R.id.refund_outside;
import static com.project.mgjandroid.R.id.tv_immediate_use;
import static com.project.mgjandroid.R.id.tv_more;
import static com.project.mgjandroid.R.id.tv_refund;
import static com.project.mgjandroid.R.id.tv_refund_amount;

/**
 * Created by SunXueLiang on 2017-03-07.
 */


public class GroupBuyingOrderForGoodsDetailsActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static final int REFRESH = 2000;

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.scroll_view)
    private ScrollView scrollView;
    @InjectView(R.id.food_user_avatar)
    private CornerImageView userAvatar;
    @InjectView(R.id.tv_food_name)
    private TextView tvFoodName;
//    @InjectView(R.id.tv_food_voucher)
//    private TextView tvFoodVoucher;
    @InjectView(R.id.tv_money)
    private TextView tvMoney;
    @InjectView(R.id.tv_voucher)
    private TextView tvVoucher;
    @InjectView(R.id.tv_available_time)
    private TextView tvAvailableTime;
    @InjectView(R.id.tv_immediate_use)
    private TextView tvImmediateUse;
    @InjectView(R.id.tv_shop_name)
    private TextView tvShopName;
    @InjectView(R.id.tv_shop_address)
    private TextView tvShopAddress;
    @InjectView(R.id.tv_length)
    private TextView tvLength;
    @InjectView(img_phone)
    private ImageView imgPhone;
    @InjectView(R.id.tv_menu)
    private TextView tvMenu;
    @InjectView(R.id.tv_order_number)
    private TextView tvOrderNumber;
    @InjectView(R.id.tv_time_of_payment)
    private TextView tvTimeOfPayment;
    @InjectView(R.id.tv_count)
    private TextView tvCount;
    @InjectView(R.id.tv_total)
    private TextView tvTotalPrice;
    @InjectView(R.id.layout_group_purchase_food)
    private LinearLayout layoutGroupPurchaseFood;
    @InjectView(R.id.layout_recommend)
    private LinearLayout layoutRecommend;
    @InjectView(R.id.layout_payment_time)
    private LinearLayout layoutPaymentTime;
    @InjectView(R.id.coupon_code_layout)
    private LinearLayout couponCodeLayout;
    @InjectView(R.id.layout_group_buying_details)
    private RelativeLayout layoutDetails;
    @InjectView(R.id.layout_address)
    private LinearLayout layoutAddress;
    @InjectView(R.id.option_layout)
    private LinearLayout optionLayout;
    @InjectView(R.id.text_1)
    private TextView tv_1;
    @InjectView(R.id.text_2)
    private TextView tv_2;
    @InjectView(R.id.text_3)
    private TextView tv_3;
    @InjectView(R.id.text_4)
    private TextView tv_4;
    @InjectView(R.id.space_view)
    private View spaceView;
    @InjectView(R.id.tv_date)
    private TextView tvDate;
    @InjectView(R.id.ll_date)
    private LinearLayout llDate;
    @InjectView(R.id.ll_red_bag)
    private LinearLayout llRedBag;
    @InjectView(R.id.tv_red_bag)
    private TextView tvRedBag;
    @InjectView(R.id.ll_pay_price)
    private LinearLayout llPayPrice;
    @InjectView(R.id.tv_pay_price)
    private TextView tvPayPrice;
    @InjectView(R.id.goods_layout)
    private LinearLayout goodsLayout;
    @InjectView(R.id.layout_coupon_code)
    private LinearLayout layoutCouponCode;
    @InjectView(R.id.business_avatar)
    private CornerImageView businessAvatar;
    @InjectView(R.id.group_buying_feedback)
    private TextView tvFeedBack;
    @InjectView(R.id.group_buying_refund)
    private TextView tvRefund;

    private StringBuffer buffer = new StringBuffer();

    private PopupWindow morePopupWindow;
    private RefundDialog dialog;
    private List<GroupBuyingTest> list = new ArrayList<GroupBuyingTest>();
    private RefundListAdapter adapter;
    private String orderId;
    private String paySuccess;
    private GroupPurchaseCoupon coupon;
    private String couponCode;
    private GroupPurchaseOrderCouponCode groupPurchaseCouponCode;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private GroupPurchaseMerchant merchant;
    private GroupPurchaseOrder order;
    private GroupPurchaseCoupon purchaseCoupon;
    private TextView tvRefundAmount;
    private PopupWindow mPhoneWindow;
    private BigDecimal bigDecimal = new BigDecimal(0);
    private BigDecimal add;
    private int count = 0;
    private boolean flag;
    private MLoadingDialog loadingDialog;
    private int ableCount;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_order_for_goods_details);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        orderId = getIntent().getStringExtra("orderId");
        ivBack.setOnClickListener(this);
        tvImmediateUse.setOnClickListener(this);
        imgPhone.setOnClickListener(this);
        layoutAddress.setOnClickListener(this);
        layoutDetails.setOnClickListener(this);
        tvFeedBack.setOnClickListener(this);
        tvRefund.setOnClickListener(this);
        loadingDialog = new MLoadingDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderData();
    }

    private void getOrderData() {
        bigDecimal = new BigDecimal(0);
        buffer = new StringBuffer();
        count = 0;
        loadingDialog.show(getFragmentManager(), "");
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<GroupBuyingOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_NEW_ORDER_DETAIL, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    order = null;
                    order = ((GroupBuyingOrderModel) obj).getValue().getGroupPurchaseOrder();
                    getMerchant();
                }
            }
        }, GroupBuyingOrderModel.class);
    }

    private void getMerchant() {
        VolleyOperater<GroupBuyingMerchantModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("groupPurchaseMerchantId", order.getMerchantId());
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_MERCHANT_INFO, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    merchant = ((GroupBuyingMerchantModel) obj).getValue();
                    if (merchant != null) {
                        getGroupPurchaseCoupon();
                    }
                }
            }
        }, GroupBuyingMerchantModel.class);
    }

    private void getGroupPurchaseCoupon() {
        VolleyOperater<GroupBuyingCouponModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("groupPurchaseCouponId", order.getGroupPurchaseCouponId());
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_COUPON_INFO, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    purchaseCoupon = ((GroupBuyingCouponModel) obj).getValue();
                    if (purchaseCoupon != null) {
                        showData();
                    }
                }
            }
        }, GroupBuyingCouponModel.class);
    }

    private void showData() {
        if (order.getGroupPurchaseCouponType() == 1) {
            tvVoucher.setText("代金券");
            tvFoodName.setText(merchant.getName() + "代金券");
//            tvFoodVoucher.setText(StringUtils.BigDecimal2Str(order.getOriginalPrice()) + "元代金券" + order.getQuantity() + "张");
        } else {
            tvVoucher.setText("团购券");
            tvFoodName.setText(purchaseCoupon.getGroupPurchaseName());
//            tvFoodVoucher.setText(StringUtils.BigDecimal2Str(order.getOriginalPrice()) + "元团购券" + order.getQuantity() + "张");
        }

        tvMoney.setText("¥" + StringUtils.BigDecimal2Str(order.getTotalPrice()));

        if (CheckUtils.isNoEmptyStr(order.getGroupPurchaseCouponImages())) {
            ImageUtils.loadBitmap(mActivity, order.getGroupPurchaseCouponImages().split(";")[0], userAvatar, R.drawable.banner_default, Constants.getEndThumbnail(88, 66));
        }
        scrollView.setVisibility(View.VISIBLE);
        showOption();
        if(order.getGroupPurchaseOrderCoupon().getIsBespeak()==0){
            llDate.setVisibility(View.GONE);
            tvAvailableTime.setText("·有效期至" + order.getGroupPurchaseCouponEndTime());
        }else {
            if(order.getGroupPurchaseOrderCoupon().getIsAutomaticallyCancelAfterVerification()==1){
                llDate.setVisibility(View.GONE);
                tvAvailableTime.setText("·有效期至" +order.getGroupPurchaseOrderCoupon().getTargetTime()+" "+order.getGroupPurchaseOrderCoupon().getCancelAfterVerificationTime());
            }else {
                llDate.setVisibility(View.VISIBLE);
                tvDate.setText(order.getGroupPurchaseOrderCoupon().getTargetTime());
                tvAvailableTime.setText("·有效期至" + order.getGroupPurchaseCouponEndTime());
            }
        }

        showCouponCode();
        if (merchant != null) {
            tvShopName.setText(merchant.getName());
            tvShopAddress.setText(merchant.getAddress());
            ImageUtils.loadBitmap(mActivity, merchant.getImgs().split(";")[0], businessAvatar, R.drawable.banner_default, Constants.getEndThumbnail(88, 66));
            if (merchant.getDistance() != null) {
                if (merchant.getDistance() > 1000) {
                    Double d = merchant.getDistance() / 1000;
                    tvLength.setText(new DecimalFormat("0.00").format(d) + "km");
                } else {
                    tvLength.setText(merchant.getDistance().intValue() + "m");
                }
            } else {
                tvLength.setText("");
            }
            if (order.getGroupPurchaseCouponType() == 1 && CheckUtils.isNoEmptyStr(merchant.getMerchantRecommend())) {
                layoutRecommend.setVisibility(View.VISIBLE);
                tvMenu.setText(merchant.getMerchantRecommend());
            }
            if (order.getGroupPurchaseCouponType() == 2 && CheckUtils.isNoEmptyList(purchaseCoupon.getGroupPurchaseCouponGoodsTypeList())) {
                showGoodsList();
            }
        }
        tvOrderNumber.setText(order.getId());
        if(CheckUtils.isNoEmptyStr(format.format(order.getPayDoneTime()))){
            layoutPaymentTime.setVisibility(View.VISIBLE);
            tvTimeOfPayment.setText(format.format(order.getPayDoneTime()));
        }else {
            layoutPaymentTime.setVisibility(View.GONE);
        }
        tvCount.setText(order.getQuantity() + "");
        if(CheckUtils.isNoEmptyStr(StringUtils.BigDecimal2Str(order.getOriginalTotalPrice()))&&!"0".equals(StringUtils.BigDecimal2Str(order.getOriginalTotalPrice()))){
            tvTotalPrice.setText("¥" + StringUtils.BigDecimal2Str(order.getOriginalTotalPrice()));
        }else {
            tvTotalPrice.setText("¥" + StringUtils.BigDecimal2Str(order.getTotalPrice()));
        }
        if(CheckUtils.isNoEmptyStr(StringUtils.BigDecimal2Str(order.getRedBagDiscountTotalAmt()))&&!"0".equals(StringUtils.BigDecimal2Str(order.getRedBagDiscountTotalAmt()))){
            llRedBag.setVisibility(View.VISIBLE);
            llPayPrice.setVisibility(View.VISIBLE);
            tvPayPrice.setText("¥"+StringUtils.BigDecimal2Str(order.getTotalPrice()));
            tvRedBag.setText("¥"+StringUtils.BigDecimal2Str(order.getRedBagDiscountTotalAmt()));
        }else {
            llRedBag.setVisibility(View.GONE);
            llPayPrice.setVisibility(View.GONE);
        }
    }

    /**
     * 显示团购套餐
     */
    private void showGoodsList() {
        layoutGroupPurchaseFood.setVisibility(View.VISIBLE);
        goodsLayout.removeAllViews();
        List<GroupPurchaseCouponGoodsType> list = purchaseCoupon.getGroupPurchaseCouponGoodsTypeList();
        for (GroupPurchaseCouponGoodsType goodsType : list) {
            if (list.size() > 1) {
                TextView textView = new TextView(mActivity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.x25));
                textView.setTextColor(ContextCompat.getColor(mActivity, R.color.color_3));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textView.setText(goodsType.getTypeName());
                goodsLayout.addView(textView, params);
            }
//            else {
//                View v = new View(mActivity);
//                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
//                v.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_e5));
//                goodsLayout.addView(v, p);
//            }
            List<GroupPurchaseCouponGoods> goodsList = goodsType.getGroupPurchaseCouponGoodsList();
            for (int i = 0, size = goodsList.size(); i < size; i++) {
                RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.group_buying_goods_item, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tvName = (TextView) layout.findViewById(R.id.name);
                TextView tvCount = (TextView) layout.findViewById(R.id.count);
                TextView tvPrice = (TextView) layout.findViewById(R.id.price);
                tvName.setText("·"+goodsList.get(i).getName());
                tvCount.setText("("+goodsList.get(i).getQuantity() + "份)");
                tvPrice.setText("¥" + StringUtils.BigDecimal2Str(goodsList.get(i).getOriginPrice()));
                goodsLayout.addView(layout, layoutParams);
//                if (i != size - 1) {
//                    View v = new View(mActivity);
//                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
//                    v.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_e5));
//                    goodsLayout.addView(v, p);
//                }
            }
        }
    }

    private void showCouponCode() {
        layoutCouponCode.setVisibility(View.VISIBLE);
        couponCodeLayout.setVisibility(View.VISIBLE);
        couponCodeLayout.removeAllViews();
        tvImmediateUse.setVisibility(View.GONE);
        for (int i = 0, size = order.getGroupPurchaseOrderCouponCodeList().size(); i < size; i++) {
            LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.group_buying_coupon_code_item, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView tvName = (TextView) layout.findViewById(R.id.tv_quanma);
            TextView tvSpending = (TextView) layout.findViewById(R.id.tv_spending);
            tvName.setText("·" + order.getGroupPurchaseOrderCouponCodeList().get(i).getCouponCode());
            if (order.getGroupPurchaseOrderCouponCodeList().get(i).getStatus() == 0 && order.getQuantity() > 0) {
                tvSpending.setText("未消费");
                tvImmediateUse.setVisibility(View.VISIBLE);
            } else if (order.getGroupPurchaseOrderCouponCodeList().get(i).getStatus() == 1) {
                tvSpending.setText("已使用");
            } else {
                if (DateUtils.compareTimeBefore(order.getCreateTime())) {
                    tvSpending.setText("退款详情 >");
                } else {
                    tvSpending.setText("已退款 >");
                }
            }
            final int finalI = i;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GroupPurchaseOrderCouponCode groupPurchaseOrderCouponCode = order.getGroupPurchaseOrderCouponCodeList().get(finalI);
                    if (groupPurchaseOrderCouponCode.getStatus() == 2 && DateUtils.compareTimeBefore(order.getCreateTime())) {
                        // 已退款
                        Intent intent = new Intent(mActivity, OrderRefundInfoActivity.class);
                        intent.putExtra("orderId", order.getId());
                        intent.putExtra("groupPurchaseOrderCouponCodeId", "" + groupPurchaseOrderCouponCode.getId());
                        startActivity(intent);
                    }
                }
            });
            couponCodeLayout.addView(layout, layoutParams);
            if (i != size - 1) {
                View v = new View(mActivity);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                v.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_e5));
                couponCodeLayout.addView(v, p);
            }
        }
    }


    private boolean checkCancel() {
        List<GroupPurchaseOrderCouponCode> groupPurchaseOrderCouponCodeList = order.getGroupPurchaseOrderCouponCodeList();
        for (int i = 0, size = groupPurchaseOrderCouponCodeList.size(); i < size; i++) {
            if (groupPurchaseOrderCouponCodeList.get(i).getIsExpire() == 0 && groupPurchaseOrderCouponCodeList.get(i).getStatus() == 0) {
                return true;
            }
        }
        return false;
    }

    private void getRefund() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("groupPurchaseOrderCouponCodeIds", buffer.toString());
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_BATCH_REFUND_GROUP_PURCHASE_ORDER_COUPON_CODE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    toast("已退款");
                    getOrderData();
                }
            }
        }, Object.class);
    }

    //退款 反馈
    private void showPopupWindow(View v, boolean canRefund) {
        View view = LayoutInflater.from(this).inflate(R.layout.group_buying_more, null);
        TextView tvFeedBack = (TextView) view.findViewById(group_buying_feedback);
        TextView tvRefund = (TextView) view.findViewById(group_buying_refund);
        LinearLayout lyoutMore = (LinearLayout) view.findViewById(R.id.more);
        if (canRefund) {
            tvRefund.setVisibility(View.VISIBLE);
            lyoutMore.setBackgroundResource(R.drawable.more_operate_bg);
        } else {
            tvRefund.setVisibility(View.GONE);
            lyoutMore.setBackgroundResource(R.drawable.more_operate_bg_1);
        }
        tvFeedBack.setOnClickListener(this);
        tvRefund.setOnClickListener(this);
        morePopupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        morePopupWindow.setBackgroundDrawable(cd);
        morePopupWindow.setOutsideTouchable(true);
        morePopupWindow.setFocusable(true);
        if (!morePopupWindow.isShowing()) {
            morePopupWindow.showAsDropDown(v, 0,
                    -this.getResources().getDimensionPixelSize(R.dimen.x5));
        }
    }

    private void hideMoreWindow() {
        if (morePopupWindow != null && morePopupWindow.isShowing()) {
            morePopupWindow.dismiss();
        }
    }

    /**
     * 显示服务备注
     */
    private void showOption() {
        tv_1.setText("随时退款");
        if(order!=null&&order.getGroupPurchaseCouponType() == 2){
            if(purchaseCoupon.getIsBespeak()==0){
                tv_2.setText("过期自动退");
            }else {
                if(purchaseCoupon.getIsAutomaticallyCancelAfterVerification()==1){
                    tv_2.setText("到期自动使用");
                }else {
                    tv_2.setText("过期自动退");
                }
            }
        }else {
            tv_2.setText("过期自动退");
        }
        if (purchaseCoupon.getIsBespeak() == 0) {
            tv_3.setText("免预约");
        } else {
            tv_3.setText("需预约");
        }
        if (purchaseCoupon.getIsCumulate() == 0) {
            tv_4.setText("不可叠加");
        } else {
            tv_4.setText("可叠加");
        }
        if (purchaseCoupon.getType() == 2) {
            tv_4.setText("不可叠加");
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case refund_outside:
                dismissRefundWindow();
                break;
            case group_buying_feedback:
                hideMoreWindow();
                Intent intent1 = new Intent(mActivity, GroupBuyingFeedbackActivity.class);
                intent1.putExtra("groupPurchaseOrder", order);
                startActivity(intent1);
                break;
            case group_buying_refund:
//                hideMoreWindow();
//                refundPopupWindow();
                Intent intent2 = new Intent(mActivity, GroupBuyingRefundActivity.class);
                intent2.putExtra("order",order);
                startActivity(intent2);
                break;
            case layout_address:
                if (merchant != null)
                    GroupBuyingMapActivity.toGroupBuyingMapActivity(mActivity, merchant.getName(), merchant.getAddress(), merchant.getLatitude(), merchant.getLongitude());
                break;
            case img_phone:
                showPhoneWindow();
                break;
            case layout_group_buying_details:
                Intent intent = new Intent(mActivity, GroupBuyingQuanOrTuanDetailActivity.class);
                intent.putExtra("couponId", order.getGroupPurchaseCouponId());
                startActivity(intent);
                break;
            case tv_immediate_use:
                GroupBuyingUseActivity.toGroupBuyingUseActivity(mActivity, JSONArray.toJSONString(order.getGroupPurchaseOrderCouponCodeList()), order.getId(), JSONArray.toJSONString(order.getGroupPurchaseOrderCouponGoodsList()), order.getGroupPurchaseMerchantName(), order.getRefreshTime(), REFRESH);
                break;
            case R.id.cancel:
                dismissPhoneWindow();
                break;
        }
        super.onClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REFRESH) {
            getOrderData();
        }
    }

    private void refundPopupWindow() {
        count = 0;
        bigDecimal = new BigDecimal(0);
        buffer = new StringBuffer();
        View view = LayoutInflater.from(this).inflate(R.layout.group_buying_refund, null);
        View refundOutside = view.findViewById(R.id.refund_outside);
        tvRefundAmount = (TextView) view.findViewById(tv_refund_amount);
        TextView tvRefund = (TextView) view.findViewById(tv_refund);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        refundOutside.setOnClickListener(this);
        List<GroupPurchaseOrderCouponCode> couponCodeList = order.getGroupPurchaseOrderCouponCodeList();

        for (Iterator<GroupPurchaseOrderCouponCode> iterator = couponCodeList.iterator(); iterator.hasNext(); ) {
            GroupPurchaseOrderCouponCode code = iterator.next();
            if (code.getStatus() != 0) {
                iterator.remove();
            }
        }
        adapter = new RefundListAdapter(R.layout.popup_item_refund, mActivity);
        int counts = couponCodeList.size();
        for (int i = 0; i < counts; i++) {
            couponCodeList.get(i).setSelected(false);
        }
        listView.setAdapter(adapter);
        adapter.setData(couponCodeList);
        listView.setOnItemClickListener(this);
        morePopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        morePopupWindow.setBackgroundDrawable(cd);
        morePopupWindow.setOutsideTouchable(false);
        morePopupWindow.setFocusable(true);
        morePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        if (!morePopupWindow.isShowing()) {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 0.5f;
            mActivity.getWindow().setAttributes(lp);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            morePopupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }


        tvRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ableCount <= 0) {
                    toast("请选择");
                    return;
                }
                for (int i = 0; i < adapter.getData().size(); i++) {
                    if (adapter.getData().get(i).isSelected() == true) {
                        GroupBuyingOrderForGoodsDetailsActivity.this.count++;
                    }
                }
                morePopupWindow.dismiss();
                if (order.getGroupPurchaseCouponType() == 1) {
                    dialog = new RefundDialog(mActivity, new RefundDialog.onBtnClickListener() {
                        @Override
                        public void onSure() {
                            getRefund();
                            dialog.dismiss();
                        }

                        @Override
                        public void onExit() {
                            dialog.dismiss();
                        }

                    }, "是否确认退款" + GroupBuyingOrderForGoodsDetailsActivity.this.count + "张价值" + StringUtils.BigDecimal2Str(purchaseCoupon.getPrice()) + "元的代金券？", "申请退款成功后，使用余额支付部分将退还至余额，使用第三方支付部分将原路退回。", "确定", "取消");
                    dialog.show();
                } else {
                    dialog = new RefundDialog(mActivity, new RefundDialog.onBtnClickListener() {
                        @Override
                        public void onSure() {
                            getRefund();
                            dialog.dismiss();
                        }

                        @Override
                        public void onExit() {
                            dialog.dismiss();
                        }

                    }, "是否确认退款" + GroupBuyingOrderForGoodsDetailsActivity.this.count + "张价值" + StringUtils.BigDecimal2Str(purchaseCoupon.getPrice()) + "元的团购券？", "申请退款成功后，使用余额支付部分将退还至余额，使用第三方支付部分将原路退回。", "确定", "取消");
                    dialog.show();
                }
            }
        });

    }

    private void dismissRefundWindow() {
        if (morePopupWindow != null && morePopupWindow.isShowing()) {
            morePopupWindow.dismiss();
        }
    }

    private void initPhoneWindow() {
        if (CheckUtils.isEmptyStr(merchant.getContacts())) return;
        String[] contacts = merchant.getContacts().split(";");
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_select_map_app, null);
        LinearLayout baidu = (LinearLayout) linearLayout.findViewById(R.id.baidu_map);
        LinearLayout gaode = (LinearLayout) linearLayout.findViewById(R.id.gaode_map);
        LinearLayout tencent = (LinearLayout) linearLayout.findViewById(R.id.tencent_map);
        RelativeLayout root = (RelativeLayout) linearLayout.findViewById(R.id.root_view_popup);
        root.setOnClickListener(this);
        TextView title = (TextView) linearLayout.findViewById(R.id.title);
        TextView textView1 = (TextView) linearLayout.findViewById(R.id.text_1);
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.text_2);
        TextView textView3 = (TextView) linearLayout.findViewById(R.id.text_3);
        title.setText("商家服务热线");
        baidu.setVisibility(View.GONE);
        gaode.setVisibility(View.GONE);
        tencent.setVisibility(View.GONE);
        if (contacts.length > 0) {
            baidu.setVisibility(View.VISIBLE);
            baidu.setTag(contacts[0]);
            textView1.setText(contacts[0]);
        }
        if (contacts.length > 1) {
            gaode.setVisibility(View.VISIBLE);
            gaode.setTag(contacts[1]);
            textView2.setText(contacts[1]);
        }
        if (contacts.length > 2) {
            tencent.setVisibility(View.VISIBLE);
            tencent.setTag(contacts[2]);
            textView3.setText(contacts[2]);
        }
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.cancel);
        baidu.setOnClickListener(this);
        gaode.setOnClickListener(this);
        tencent.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        mPhoneWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPhoneWindow.setOutsideTouchable(false);
    }

    private void showPhoneWindow() {
        if (mPhoneWindow == null) {
            initPhoneWindow();
            mPhoneWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (!mPhoneWindow.isShowing()) {
            mPhoneWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissPhoneWindow() {
        if (mPhoneWindow != null && mPhoneWindow.isShowing()) {
            mPhoneWindow.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapter.getData().get(i).isSelected()) {
            ableCount--;
            adapter.getData().get(i).setSelected(false);
            bigDecimal = bigDecimal.subtract(adapter.getData().get(i).getPrice());
            Long id = adapter.getData().get(i).getId();
            String all = buffer.toString().replaceAll(id + ",", "");
            buffer = new StringBuffer(all);
            tvRefundAmount.setText("退款金额：¥" + bigDecimal);
        } else {
            ableCount++;
            adapter.getData().get(i).setSelected(true);
            BigDecimal price = adapter.getData().get(i).getPrice();
            bigDecimal = bigDecimal.add(price);
            Long id = adapter.getData().get(i).getId();
            buffer.append(id + ",");
            tvRefundAmount.setText("退款金额：¥" + bigDecimal);
        }
        adapter.notifyDataSetChanged();

    }

}
