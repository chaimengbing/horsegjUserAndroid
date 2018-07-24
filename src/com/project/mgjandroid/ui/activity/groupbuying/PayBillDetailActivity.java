package com.project.mgjandroid.ui.activity.groupbuying;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import com.project.mgjandroid.ui.activity.OrderDetailActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
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
    @InjectView(R.id.merchant_name)
    private TextView titleMerchantName;

    private MLoadingDialog loadingDialog;
    private String orderId;
    private GroupPurchaseOrder order;
    public static final int REFRESH = 2000;
    private Dialog callNumDialog;
    private CallPhoneDialog dialog;

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
        callNumDialog = new Dialog(this, R.style.fullDialog);
    }

    private void getOrderData() {
        loadingDialog.show(getFragmentManager(), "");
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<GroupBuyingOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_NEW_ORDER_DETAIL, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    order = ((GroupBuyingOrderModel) obj).getValue().getGroupPurchaseOrder();
                    showDetail();
                }
            }
        }, GroupBuyingOrderModel.class);
    }

    private void showDetail(){
        tvMerchantName.setText(order.getGroupPurchaseMerchantName());
        if(CheckUtils.isNoEmptyStr(order.getGroupPurchaseMerchantImg())){
            ImageUtils.loadBitmap(mActivity, order.getGroupPurchaseMerchantImg().split(";")[0], imgAcatar, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        }
        tvPaymentMoney.setText("¥"+StringUtils.BigDecimal2Str(order.getTotalPrice()));
        tvOrderMoney.setText("¥"+StringUtils.BigDecimal2Str(order.getOriginalPrice()));
        if(CheckUtils.isNoEmptyStr(StringUtils.BigDecimal2Str(order.getDiscountAmt()))){
            tvDiscount.setText("-¥"+StringUtils.BigDecimal2Str(order.getDiscountAmt()));
        }else {
            tvDiscount.setText("无");
        }
        if(CheckUtils.isNoEmptyStr(StringUtils.BigDecimal2Str(order.getCashDeductionPrice()))){
            tvVoucher.setText("-¥"+StringUtils.BigDecimal2Str(order.getCashDeductionPrice()));
        }else {
            tvVoucher.setText("无");
        }
        if(CheckUtils.isNoEmptyStr(StringUtils.BigDecimal2Str(order.getRedBagDiscountTotalAmt()))){
            tvRedBag.setText("-¥"+StringUtils.BigDecimal2Str(order.getRedBagDiscountTotalAmt()));
        }else {
            tvRedBag.setText("无");
        }

//        tvNowStatus.setText();
        titleMerchantName.setText(order.getGroupPurchaseMerchantName());
        tvPayTime.setText(order.getPaymentExpireTime());
        if(order.getPaymentType()==1){
            tvPayWay.setText("在线支付");
        }else {
            tvPayWay.setText("货到付款");
        }
        tvOrderNumber.setText(order.getId());
        if (order.getHasComments() == 0){
            tvEvaluate.setEnabled(true);
            tvEvaluate.setText("评价");
        }else {
            tvEvaluate.setEnabled(false);
            tvEvaluate.setText("已评价");
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.common_back:
                back();
                break;
            case R.id.tv_evaluate:
                Intent carEvaluate = new Intent(mActivity, GroupBuyingAddEvaluationActivity.class);
                carEvaluate.putExtra("groupPurchaseOrder", order);
                startActivityForResult(carEvaluate, REFRESH);
                break;
            case R.id.img_phone:
                showCallDialog(order.getGroupPurchaseMerchantContacts());
                break;
        }
    }

    private void showCallDialog(final String mobild) {

        if (callNumDialog != null) {
            callNumDialog.dismiss();
        }
        dialog = new CallPhoneDialog(PayBillDetailActivity.this, new CallPhoneDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                //拨打电话
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                //submitOrderEntity.getMerchant().getContacts() 商家电话
                intent.setData(Uri.parse("tel:" + mobild));
                PayBillDetailActivity.this.startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void onExit() {
                dialog.dismiss();
            }
        }, "", mobild);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFRESH) {
            getOrderData();
        }
    }
}
