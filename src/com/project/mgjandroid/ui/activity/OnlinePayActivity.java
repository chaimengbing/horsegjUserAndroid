package com.project.mgjandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.android.PaymentActivity;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.ChargeType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.BalancePayModel;
import com.project.mgjandroid.model.CheckOrderPayModel;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.GetAlipayInfoModel;
import com.project.mgjandroid.model.PayWaysModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.carhailing.CarHailingOrderDetailActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingOrderForGoodsDetailsActivity;
import com.project.mgjandroid.ui.activity.legwork.LegworkOrderdetailsActivity;
import com.project.mgjandroid.ui.activity.pintuan.MyGroupPurchaseDetailActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.ViewFindUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlinePayActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.online_pay_back)
    private ImageView onlineBack;
    @InjectView(R.id.pay_money)
    private TextView tvPayMoney;
    @InjectView(R.id.balance_pay_money)
    private TextView tvBalancePayMoney;
    @InjectView(R.id.third_money)
    private TextView tvThirdMoney;
    @InjectView(R.id.account_extra_money)
    private TextView tvAccountExtraMoney;
    @InjectView(R.id.pay_extra_check)
    private ImageView ivExtra;
    @InjectView(R.id.online_pay_confirm)
    private TextView tvConfirm;
    @InjectView(R.id.pay_extra)
    private RelativeLayout rlExtra;
    @InjectView(R.id.third_pay_pannel)
    private LinearLayout thirdPanel;
    @InjectView(R.id.pay_container)
    private LinearLayout payLabelContainer;
    @InjectView(R.id.constomer_nbr)
    private TextView constomerNbr;
    private MLoadingDialog loadingDialog;
    private String orderId;
    private PayWaysModel payWaysModel;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private String payChannel;
    private int preTag = -1;
    private boolean isGroup = false;
    private String constomer;
    private boolean isCarHailing = false;
    private NoticeDialog dialog;
    private boolean isGroupPurchase = false;
    private boolean isFromThird = false;
    private boolean isThirdparty = false;
    private String thirdUrl;
    private boolean isLegwork = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pay);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        loadingDialog = new MLoadingDialog();
        onlineBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        rlExtra.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("orderId")) {
            orderId = intent.getStringExtra("orderId");
            getPayWays(orderId);
        } else if (intent != null && intent.hasExtra(YLBSdkConstants.EXTRA_YLBSDK_MSG)) {
            orderId = intent.getStringExtra(YLBSdkConstants.EXTRA_YLBSDK_MSG);
            isFromThird = intent.getBooleanExtra("isFromThird", false);
            getPayWays(orderId);
        }
        if (CheckUtils.isEmptyStr(orderId)) {
            ToastUtils.displayMsg("订单信息为空", mActivity);
            finish();
        }
        if (intent.hasExtra("isGroup")) {
            isGroup = true;
        }
        if (intent.hasExtra("isCarHailing")) {
            isCarHailing = true;
        }
        if (intent.hasExtra("isGroupPurchase")) {
            isGroupPurchase = true;
        }
        if (intent.hasExtra("isThirdparty")) {
            isThirdparty = intent.getBooleanExtra("isThirdparty", false);
            thirdUrl = intent.getStringExtra("thirdUrl");
        }
        if (intent.hasExtra("isLegwork")) {
            isLegwork = true;
        }
//        if (intent.hasExtra("agentId")) {
//            int agentId = intent.getIntExtra("agentId", 0);
//            getTelNumId(agentId);
//        }
    }

    @Override
    public void onClick(View v) {
        if (CheckUtils.isEmptyStr(orderId)) {
            return;
        }
        switch (v.getId()) {
            case R.id.online_pay_back:
                if (isFromThird) {
                    try {
                        org.json.JSONObject object = new org.json.JSONObject();
                        object.put("code", 500);
                        object.put("value", orderId);
                        object.put("msg", "取消支付");
                        Intent i = new Intent();
                        i.putExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT, object.toString());
                        setResult(RESULT_OK, i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        finish();
                    }
                }
                finish();
                break;
            case R.id.pay_extra:
                //余额支付
//                if (!idEnough) {
//                    ToastUtils.displayMsg("您的余额不足", mActivity);
//                    return;
//                }
                ivExtra.setSelected(!ivExtra.isSelected());
                if (ivExtra.isSelected()) {
                    BigDecimal userBalance = payWaysModel.getValue().getUserBalance();
                    BigDecimal totalPrice = payWaysModel.getValue().getTotalPrice();
                    if (userBalance != null) {
                        if (userBalance.compareTo(totalPrice) >= 0) {
                            thirdPanel.setVisibility(View.GONE);
                            clearThirdCheck();
                            payChannel = "extra";
                            preTag = -1;
                        } else {
                            tvThirdMoney.setText("¥" + StringUtils.BigDecimal2Str(totalPrice.subtract(userBalance)));
                        }
                    }
                } else {
                    tvThirdMoney.setText("¥" + StringUtils.BigDecimal2Str(payWaysModel.getValue().getTotalPrice()));
                    if (thirdPanel.getVisibility() == View.GONE) {
                        thirdPanel.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.online_pay_confirm:
                //提交订单
                if (payChannel == null) {
                    ToastUtils.displayMsg("请选择支付方式", mActivity);
                    return;
                }
                if ("extra".equals(payChannel)) {
                    payExtraMoney(orderId);
                } else {
                    if ("wx".equals(payChannel)) {
                        if (!WXAPIFactory.createWXAPI(this, Constants.WE_CHAT_APP_ID).isWXAppInstalled()) {
                            ToastUtils.displayMsg("尚未安装微信", OnlinePayActivity.this);
                            break;
                        }
                    }
                    if (ivExtra.isSelected()) {
                        getPayInfo(orderId, payWaysModel.getValue().getUserBalance(), payChannel);
                    } else {
                        getPayInfo(orderId, BigDecimal.ZERO, payChannel);
                    }
                }
                break;
            case R.id.pay_alipay:
                int tag = (int) v.getTag();
                changeLabel(tag);
                if (preTag != -1) {
                    changeLabel(preTag);
                }
                List<PayWaysModel.ValueEntity.ChargeTypesEntity> chargeTypes = payWaysModel.getValue().getChargeTypes();
                payChannel = chargeTypes.get(tag).getChannel();
                preTag = tag;
                break;
        }
    }

    private void clearThirdCheck() {
        for (int i = 0; i < payLabelContainer.getChildCount(); i++) {
            LinearLayout childAt = (LinearLayout) payLabelContainer.getChildAt(i);
            RelativeLayout childAt1 = (RelativeLayout) childAt.getChildAt(0);
            childAt1.getChildAt(2).setSelected(false);
        }
    }

    private void changeLabel(int tag) {
        LinearLayout childAt = (LinearLayout) payLabelContainer.getChildAt(tag);
        RelativeLayout childAt1 = (RelativeLayout) childAt.getChildAt(0);
        childAt1.getChildAt(2).setSelected(!childAt1.getChildAt(2).isSelected());
    }

    /**
     * 余额支付
     */
    private void payExtraMoney(final String orderId) {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<BalancePayModel> operater = new VolleyOperater<BalancePayModel>(this);
        operater.doRequest(Constants.URL_EXTRA, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        if (isFromThird) {
                            try {
                                org.json.JSONObject object = new org.json.JSONObject();
                                object.put("code", 0);
                                object.put("value", orderId);
                                Intent i = new Intent();
                                i.putExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT, object.toString());
                                setResult(RESULT_OK, i);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finish();
                            }
                        } else {
                            ToastUtils.displayMsg(obj.toString(), mActivity);
                            gotoDetail();
                            setResult(RESULT_OK);
                            finish();
                        }
                    } else if (obj instanceof JSONObject) {
                        if (isFromThird) {
                            try {
                                org.json.JSONObject object = new org.json.JSONObject();
                                object.put("code", 0);
                                object.put("value", orderId);
                                Intent i = new Intent();
                                i.putExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT, object.toString());
                                setResult(RESULT_OK, i);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finish();
                            }
                        } else {
                            JSONObject object = (JSONObject) obj;
                            String id = (String) object.get("id");
                            if (isGroup) {
                                Intent intent = new Intent(OnlinePayActivity.this, MyGroupPurchaseDetailActivity.class);
                                intent.putExtra(OrderDetailActivity.ORDER_ID, id);
                                startActivity(intent);
                                setResult(RESULT_OK);
                                finish();
                            } else if (isCarHailing) {
                                if (!getIntent().hasExtra("isFromDetail")) {
                                    Intent intent = new Intent(OnlinePayActivity.this, CarHailingOrderDetailActivity.class);
                                    intent.putExtra(OrderDetailActivity.ORDER_ID, id);
                                    intent.putExtra("paySuccess", "onLinePay");
                                    startActivity(intent);
                                }
                                setResult(RESULT_OK);
                                finish();
                            } else if (isGroupPurchase) {
                                if (!getIntent().hasExtra("isFromDetail")) {
                                    Intent intent = new Intent(OnlinePayActivity.this, GroupBuyingOrderForGoodsDetailsActivity.class);
                                    intent.putExtra("orderId", id);
                                    intent.putExtra("paySuccess", "onLinePay");
                                    startActivity(intent);
                                }
                                setResult(RESULT_OK);
                                finish();
                            } else if (isThirdparty) {
                                Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, thirdUrl);
                                startActivity(intent);
                                setResult(RESULT_OK);
                                finish();
                            } else if (isLegwork) {
                                if (!getIntent().hasExtra("isFromDetail")) {
                                    Intent intent = new Intent(OnlinePayActivity.this, LegworkOrderdetailsActivity.class);
                                    intent.putExtra("orderId", id);
                                    startActivity(intent);
                                }
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    } else {
                        if (isFromThird) {
                            try {
                                org.json.JSONObject object = new org.json.JSONObject();
                                object.put("code", 0);
                                object.put("value", orderId);
                                Intent i = new Intent();
                                i.putExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT, object.toString());
                                setResult(RESULT_OK, i);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finish();
                            }
                        } else {
                            BalancePayModel balancePayModel = (BalancePayModel) obj;
                            Intent intent = new Intent(OnlinePayActivity.this, OrderDetailActivity.class);
                            intent.putExtra(OrderDetailActivity.ORDER_ID, balancePayModel.getValue().getId());
                            intent.putExtra("hasRedPackage", true);
                            startActivity(intent);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }
            }
        }, BalancePayModel.class);
    }

    /**
     * alipay获取payinfo
     */
    private void getPayInfo(String orderId, BigDecimal cost, String channel) {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("channel", channel);
        map.put("balanceCost", cost);
        VolleyOperater<GetAlipayInfoModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_PING_PAY, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    GetAlipayInfoModel getAlipayInfoModel = (GetAlipayInfoModel) obj;
                    String data = getAlipayInfoModel.getValue();
                    if (null == data) {
                        ToastUtils.displayMsg("请求出错" + "请检查URL" + "URL无法获取charge", mActivity);
                        return;
                    }

                    Intent intent = new Intent(mActivity, PaymentActivity.class);
                    intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
                loadingDialog.dismiss();
            }
        }, GetAlipayInfoModel.class);
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                if ("success".equals(result)) {
                    checkPayState();
                    /*if (isGroup) {
                        Intent intent = new Intent(OnlinePayActivity.this, MyGroupPurchaseDetailActivity.class);
                        intent.putExtra(OrderDetailActivity.ORDER_ID, orderId);
                        startActivity(intent);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Intent intent = new Intent(OnlinePayActivity.this, OrderDetailActivity.class);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("hasRedPackage", true);
                        startActivity(intent);
                        finish();
                    }*/
                } else if ("fail".equals(result)) {
                    if (isGroup) {
                        ToastUtils.displayMsg("支付失败", mActivity);
                        Intent intent = new Intent(OnlinePayActivity.this, MyGroupPurchaseDetailActivity.class);
                        intent.putExtra(OrderDetailActivity.ORDER_ID, orderId);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtils.displayMsg("支付失败", mActivity);
                    }
                } else if ("cancel".equals(result)) {
                    ToastUtils.displayMsg("取消支付", mActivity);
                }
            }
        }
    }

    private void checkPayState() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<CheckOrderPayModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_CHECK_ORDER_PAY, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        if ("100004".equals(obj.toString())) {
                            dialog = new NoticeDialog(mActivity, new NoticeDialog.onBtnClickListener() {
                                @Override
                                public void onSure() {
                                    dialog.dismiss();
                                    if (isFromThird) {
                                        try {
                                            org.json.JSONObject object = new org.json.JSONObject();
                                            object.put("code", 500);
                                            object.put("value", orderId);
                                            object.put("msg", "当前行程已满员，如发生扣款请到账户余额查看");
                                            Intent i = new Intent();
                                            i.putExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT, object.toString());
                                            setResult(RESULT_OK, i);
                                            finish();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            finish();
                                        }
                                    } else {
                                        gotoDetail();
                                        OnlinePayActivity.this.finish();
                                    }
                                }
                            }, "", "当前行程已满员，如发生扣款请到账户余额查看");
                            dialog.show();
                            return;
                        } else {
                            ToastUtils.displayMsg(obj.toString(), mActivity);
                        }
                    } else {
                        CheckOrderPayModel model = (CheckOrderPayModel) obj;
                        if (model.getValue() != null && model.getValue().getPayState() == 1) {

                        } else {
                            ToastUtils.displayMsg("系统错误，如支付已完成，但订单页面仍显示“待支付”或“去支付”状态，请稍后刷新页面或联系客服", mActivity);
                        }
                    }
                    gotoDetail();
                }
            }
        }, CheckOrderPayModel.class);
    }

    private void gotoDetail() {
        if (isFromThird) {
            try {
                org.json.JSONObject object = new org.json.JSONObject();
                object.put("code", 0);
                object.put("value", orderId);
                Intent i = new Intent();
                i.putExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT, object.toString());
                setResult(RESULT_OK, i);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
                finish();
            }
        } else if (isGroup) {
            Intent intent = new Intent(OnlinePayActivity.this, MyGroupPurchaseDetailActivity.class);
            intent.putExtra(OrderDetailActivity.ORDER_ID, orderId);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        } else if (isCarHailing) {
            Intent intent = new Intent(OnlinePayActivity.this, CarHailingOrderDetailActivity.class);
            intent.putExtra(OrderDetailActivity.ORDER_ID, orderId);
            intent.putExtra("paySuccess", "onLinePay");
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        } else if (isGroupPurchase) {
            Intent intent = new Intent(OnlinePayActivity.this, GroupBuyingOrderForGoodsDetailsActivity.class);
            intent.putExtra("orderId", orderId);
            intent.putExtra("paySuccess", "onLinePay");
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        } else if (isThirdparty) {
            Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
            intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, thirdUrl);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        } else if (isLegwork) {
            Intent intent = new Intent(OnlinePayActivity.this, LegworkOrderdetailsActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        } else {
            Intent intent = new Intent(OnlinePayActivity.this, OrderDetailActivity.class);
            intent.putExtra("orderId", orderId);
            intent.putExtra("hasRedPackage", true);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        }
    }

    /**
     * 获取充值方式
     */
    private void getPayWays(String orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<PayWaysModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_PAY_WAYS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        if (isFromThird) {
                            try {
                                org.json.JSONObject object = new org.json.JSONObject();
                                object.put("code", 500);
                                object.put("value", obj.toString());
                                object.put("msg", "无法获取支付方式");
                                Intent i = new Intent();
                                i.putExtra(YLBSdkConstants.EXTRA_YLBSDK_RESULT, object.toString());
                                setResult(RESULT_OK, i);
                                OnlinePayActivity.this.finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.displayMsg(obj.toString(), mActivity);
                            OnlinePayActivity.this.finish();
                        }
                        return;
                    }
                    payWaysModel = (PayWaysModel) obj;
                    String price = StringUtils.BigDecimal2Str(payWaysModel.getValue().getTotalPrice());
                    tvPayMoney.setText("¥" + price);
                    tvThirdMoney.setText("¥" + price);
                    BigDecimal userBalance = payWaysModel.getValue().getUserBalance();
                    tvAccountExtraMoney.setText("(账户余额：¥" + (userBalance == null ? 0 : StringUtils.BigDecimal2Str(userBalance)) + ")");
                    tvBalancePayMoney.setText(userBalance == null ? "" : (userBalance.compareTo(payWaysModel.getValue().getTotalPrice()) > 0 ?
                            ("¥" + price) : ("¥" + StringUtils.BigDecimal2Str(userBalance))));
                    creatPayContainer();

//                    try {
//                        double balance = 0;
//                        if (userBalance != null) {
//                            balance = Double.parseDouble(StringUtils.BigDecimal2Str(userBalance));
//                        }
//                        double money = Double.parseDouble(StringUtils.BigDecimal2Str(payWaysModel.getValue().getTotalPrice()));
//                        if (money <= balance) {
//                            idEnough = true;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }, PayWaysModel.class);
    }

    private void creatPayContainer() {
        PayWaysModel.ValueEntity payWaysModelValue = payWaysModel.getValue();
        List<PayWaysModel.ValueEntity.ChargeTypesEntity> chargeTypes = payWaysModelValue.getChargeTypes();
        for (int i = 0; i < chargeTypes.size(); i++) {
            PayWaysModel.ValueEntity.ChargeTypesEntity chargeTypesEntity = chargeTypes.get(i);
            LinearLayout item = (LinearLayout) mInflater.inflate(R.layout.layout_pay_item, null);
            RelativeLayout rlPayLabel = ViewFindUtils.find(item, R.id.pay_alipay);
            rlPayLabel.setTag(i);
            rlPayLabel.setOnClickListener(this);
            ImageView payIcon = ViewFindUtils.find(item, R.id.pay_icon);
            TextView tvName = ViewFindUtils.find(item, R.id.pay_name);
            payIcon.setImageResource(ChargeType.findChargeTypeByValue(chargeTypesEntity.getChannel()).getIcon());
            tvName.setText(chargeTypesEntity.getName());
            payLabelContainer.addView(item);
        }
    }

    private void getTelNumId(int agentId) {
        final Map<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        VolleyOperater<CustomerAndComplainPhoneDTOModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_TELNUM_ID, map, new VolleyOperater.ResponseListener() {
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
                        constomerNbr.setText("订单如需退款，请联系客服：" + constomer);
                    }
                }

            }
        }, CustomerAndComplainPhoneDTOModel.class);
    }

    @Override
    public void onBackPressed() {
        if (isFromThird) {
            setResult(RESULT_CANCELED);
        }
        super.onBackPressed();
    }
}
