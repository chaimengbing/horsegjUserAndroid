package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCoupon;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingCouponModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingMerchantModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PayBillDetailActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView tvBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.img_phone)
    private ImageView imgPhone;
    @InjectView(R.id.tv_payment_money)
    private TextView tvPaymentMoney;
    @InjectView(R.id.tv_order_money)
    private TextView tvOrderMoney;
    @InjectView(R.id.tv_discount)
    private TextView tvDiscount;
    @InjectView(R.id.tv_voucher)
    private TextView tvVoucher;
    @InjectView(R.id.tv_red_bag)
    private TextView tvRedBag;
    @InjectView(R.id.tv_now_status)
    private TextView tvNowStatus;
    @InjectView(R.id.tv_merchant_name)
    private TextView tvMerchantName;
    @InjectView(R.id.tv_pay_time)
    private TextView tvPayTime;
    @InjectView(R.id.tv_pay_way)
    private TextView tvPayWay;
    @InjectView(R.id.tv_order_number)
    private TextView tvOrderNumber;
    @InjectView(R.id.tv_evaluate)
    private TextView tvEvaluate;
    @InjectView(R.id.merchant_avatar)
    private ImageView imgAcatar;

    private MLoadingDialog loadingDialog;
    private String orderId;
    private GroupPurchaseOrder order;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_pay_bill_detail);
        Injector.get(this).inject();
        initView();
        getOrderData();
    }

    private void initView(){
        tvBack.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
        imgPhone.setOnClickListener(this);
        orderId = getIntent().getStringExtra("orderId");
        loadingDialog = new MLoadingDialog();
    }

    private void getOrderData() {
        loadingDialog.show(getFragmentManager(), "");
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<GroupBuyingOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_NEW_ORDER_DETAIL, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    order = ((GroupBuyingOrderModel) obj).getValue().getGroupPurchaseOrder();
                    showDetail();
                }
            }
        }, GroupBuyingOrderModel.class);
    }

    private void showDetail(){
        tvMerchantName.setText(order.getGroupPurchaseMerchantName());
        ImageUtils.loadBitmap(mActivity, order.getGroupPurchaseCouponImages().split(";")[0], imgAcatar, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        tvPaymentMoney.setText(StringUtils.BigDecimal2Str(order.getTotalPrice()));
        tvOrderMoney.setText(StringUtils.BigDecimal2Str(order.getTotalOriginalPrice()));
        tvDiscount.setText(StringUtils.BigDecimal2Str(order.getDiscountAmt()));
        tvVoucher.setText(StringUtils.BigDecimal2Str(order.getCashDeductionPrice()));
        tvRedBag.setText(StringUtils.BigDecimal2Str(order.getRedBagDiscountTotalAmt()));
//        tvNowStatus.setText();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.common_back:
                back();
                break;
            case R.id.tv_evaluate:
                startActivity(new Intent(this,GroupBuyingAddEvaluationActivity.class));
                break;
            case R.id.img_phone:

                break;
        }
    }
}
