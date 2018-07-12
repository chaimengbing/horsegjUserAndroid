package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

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

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_pay_bill_detail);
        Injector.get(this).inject();
        initView();
    }

    private void initView(){
        tvBack.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
        imgPhone.setOnClickListener(this);
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
