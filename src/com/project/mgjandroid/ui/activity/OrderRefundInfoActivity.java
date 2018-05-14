package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.OrderRefundInfoModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;

/**
 * 项目名称：mgjuser
 * 类描述：退款详情
 * 创建人：Mr_Lei
 * 创建时间：2018/5/11 11:52
 */
public class OrderRefundInfoActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.tv_order_id)
    private TextView tvOrderId;
    @InjectView(R.id.tv_order_number)
    private TextView tvOrderNumber;
    @InjectView(R.id.tv_refund_thirdparty_name)
    private TextView tvThirdpartyName;
    @InjectView(R.id.tv_refund_thirdparty_price)
    private TextView tvThirdpartyPrice;
    @InjectView(R.id.tv_refund_balance_price)
    private TextView tvBalancePrice;
    @InjectView(R.id.tv_refund_price)
    private TextView tvRefundPrice;
    @InjectView(R.id.tv_status_time)
    private TextView tvStatusTime;
    @InjectView(R.id.tv_status_time_2)
    private TextView tvStatusTime2;
    @InjectView(R.id.tv_status_time_3)
    private TextView tvStatusTime3;
    @InjectView(R.id.iv_status_2)
    private ImageView ivStatus2;
    @InjectView(R.id.iv_status_3)
    private ImageView ivStatus3;
    @InjectView(R.id.ll_order_number)
    private LinearLayout llOrderNumber;
    @InjectView(R.id.view_line2)
    private View viewLine2;
    @InjectView(R.id.view_line3)
    private View viewLine3;
    @InjectView(R.id.tv_status_name_2)
    private TextView tvStatusName2;
    @InjectView(R.id.ll_order_state1)
    private LinearLayout llState1;
    @InjectView(R.id.ll_order_state2)
    private LinearLayout llState2;
    @InjectView(R.id.ll_order_state3)
    private LinearLayout llState3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_info);
        Injector.get(this).inject();
        initView();
        initData();
    }

    private void initData() {
        String orderId = getIntent().getStringExtra("orderId");
        String groupPurchaseOrderCouponCodeId = getIntent().getStringExtra("groupPurchaseOrderCouponCodeId");
        VolleyOperater<OrderRefundInfoModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        if (!TextUtils.isEmpty(groupPurchaseOrderCouponCodeId)) {
            map.put("groupPurchaseOrderCouponCodeId", groupPurchaseOrderCouponCodeId);
        }
        operater.doRequest(Constants.URL_QUERY_REFUND_INFO, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    OrderRefundInfoModel model = (OrderRefundInfoModel) obj;
                    if (model.getCode() == 0) {
                        OrderRefundInfoModel.ValueBean value = model.getValue();
                        if (value != null) {
                            setView(value);
                        }
                    }
                }
            }
        }, OrderRefundInfoModel.class);
    }

    private void setView(OrderRefundInfoModel.ValueBean value) {
        llState1.setVisibility(View.VISIBLE);
        llState2.setVisibility(View.VISIBLE);
        llState3.setVisibility(View.VISIBLE);
        tvOrderId.setText(value.getOrderId());
        if (!TextUtils.isEmpty(value.getTransactionNo())) {
            llOrderNumber.setVisibility(View.VISIBLE);
            tvOrderNumber.setText(value.getTransactionNo());
        }
        if ("balance".equals(value.getChargeType())) {
            tvThirdpartyName.setText("退回至第三方");
        } else if ("wx".equals(value.getChargeType())) {
            tvThirdpartyName.setText("退回至第三方（微信支付）");
            tvThirdpartyPrice.setText("¥ " + value.getAmt());
            tvStatusName2.setText("第三方处理中");
        } else if ("wx_lite".equals(value.getChargeType())) {
            tvThirdpartyName.setText("退回至第三方（微信小程序支付）");
            tvThirdpartyPrice.setText("¥ " + value.getAmt());
            tvStatusName2.setText("第三方处理中");
        } else if ("alipay".equals(value.getChargeType())) {
            tvThirdpartyName.setText("退回至第三方（支付宝支付）");
            tvThirdpartyPrice.setText("¥ " + value.getAmt());
            tvStatusName2.setText("第三方处理中");
        }
        tvBalancePrice.setText("¥ " + value.getBalanceCost());
        tvRefundPrice.setText("¥" + value.getRefundTotalMoney());

        switch (value.getState()) {
            case 2:
                //退款成功
                if (!TextUtils.isEmpty(value.getApplySuccessTime()))
                    tvStatusTime.setText(value.getApplySuccessTime());
                if (!TextUtils.isEmpty(value.getProcessingTime()))
                    tvStatusTime2.setText(value.getProcessingTime());
                if (!TextUtils.isEmpty(value.getRefundSuccessTime()))
                    tvStatusTime3.setText(value.getRefundSuccessTime());
                ivStatus2.setImageDrawable(getResources().getDrawable(R.drawable.refund));
                ivStatus3.setImageDrawable(getResources().getDrawable(R.drawable.refund));
                viewLine2.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                viewLine3.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                break;
            case 4:
                //退款中
                if (!TextUtils.isEmpty(value.getApplySuccessTime()))
                    tvStatusTime.setText(value.getApplySuccessTime());
                if (!TextUtils.isEmpty(value.getProcessingTime()))
                    tvStatusTime2.setText(value.getProcessingTime());
                ivStatus2.setImageDrawable(getResources().getDrawable(R.drawable.refund));
                viewLine2.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                viewLine3.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                break;
            case 3:
                //退款失败
                break;
        }
    }

    private void initView() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
        }
    }

}
