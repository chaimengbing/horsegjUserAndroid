package com.project.mgjandroid.ui.activity.information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pingplusplus.android.PaymentActivity;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.ChargeType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.BalancePayModel;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.GetAlipayInfoModel;
import com.project.mgjandroid.model.PayWaysModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.ViewFindUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/11/17.
 */

public class PayActivity extends BaseActivity {

    public static final String FROM_INFORMATION_DETAIL_ACTIVITY = "from_information_detail_activity";

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
    private String informationOrderId;
    private PayWaysModel payWaysModel;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private String payChannel;
    private String mFrom;
    private long informationId;
    private int informationType;
    private int preTag = -1;
    private String constomer;

    public static void toPay(Context context, long informationId, String informationOrderId, int informationType, long agentId) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("informationId", informationId);
        intent.putExtra("informationOrderId", informationOrderId);
        intent.putExtra("informationType", informationType);
        intent.putExtra("agentId", agentId);
        context.startActivity(intent);
    }

    public static void toPayForResult(Activity context, String from, String informationOrderId, int requestCode) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("informationOrderId", informationOrderId);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_pay);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        loadingDialog = new MLoadingDialog();
        onlineBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        rlExtra.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("informationOrderId")) {
            informationOrderId = intent.getStringExtra("informationOrderId");
            informationId = intent.getLongExtra("informationId", 0);
            informationType = intent.getIntExtra("informationType", 0);
            mFrom = intent.getStringExtra("from");
            getPayWays(informationOrderId);

        }
//        if (intent.hasExtra("agentId")) {
//            int agentId = intent.getIntExtra("agentId", 0);
//            getTelNumId(agentId);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.online_pay_back:
                finish();
                break;
            case R.id.pay_extra:
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
                    payChannel = null;
                }
                break;
            case R.id.online_pay_confirm:
                if (payChannel == null) {
                    ToastUtils.displayMsg("请选择支付方式", mActivity);
                    return;
                }
                if ("extra".equals(payChannel)) {
                    payExtraMoney(informationOrderId);
                } else {
                    if ("wx".equals(payChannel)) {
                        if (!WXAPIFactory.createWXAPI(this, Constants.WE_CHAT_APP_ID).isWXAppInstalled()) {
                            ToastUtils.displayMsg("尚未安装微信", mActivity);
                            break;
                        }
                    }
                    if (ivExtra.isSelected()) {
                        getPayInfo(informationOrderId, payWaysModel.getValue().getUserBalance(), payChannel);
                    } else {
                        getPayInfo(informationOrderId, BigDecimal.ZERO, payChannel);
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
    private void payExtraMoney(String orderId) {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("informationOrderId", orderId);
        VolleyOperater<BalancePayModel> operater = new VolleyOperater<BalancePayModel>(this);
        operater.doRequest(Constants.URL_INFORMATION_BALANCE_PAY, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                    } else {
                        BalancePayModel balancePayModel = (BalancePayModel) obj;
                        if (balancePayModel.getCode() == 0) {
                            if (FROM_INFORMATION_DETAIL_ACTIVITY.equals(mFrom)) {
                                Intent intent = new Intent();
                                intent.putExtra(FROM_INFORMATION_DETAIL_ACTIVITY, true);
                                setResult(RESULT_OK, intent);
                            } else {
//                                InformationDetailActivity.toInformationDetail(mActivity, informationId, informationType);
                                if (MyPublishInformationActivity.getInstance() != null) {
                                    MyPublishInformationActivity.getInstance().finish();
                                }
                                MyPublishInformationActivity.toMyPublishInformationList(mActivity, informationType);
                            }
                            finish();
                            MyPublishInformationActivity.getInstance().refresh();
                        }
                    }
                }
                loadingDialog.dismiss();
            }
        }, BalancePayModel.class);
    }

    /**
     * alipay获取payinfo
     */
    private void getPayInfo(String orderId, BigDecimal cost, String channel) {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("informationOrderId", orderId);
        map.put("channel", channel);
        map.put("balanceCost", cost);
        VolleyOperater<GetAlipayInfoModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_INFORMATION_PINGXX_PAY, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
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
                    if (FROM_INFORMATION_DETAIL_ACTIVITY.equals(mFrom)) {
                        Intent intent = new Intent();
                        intent.putExtra(FROM_INFORMATION_DETAIL_ACTIVITY, true);
                        setResult(RESULT_OK, intent);
                    } else {
//                        InformationDetailActivity.toInformationDetail(mActivity, informationId, informationType);
                        if (MyPublishInformationActivity.getInstance() != null) {
                            MyPublishInformationActivity.getInstance().finish();
                        }
                        MyPublishInformationActivity.toMyPublishInformationList(mActivity, informationType);
                    }
                    finish();
                    if (MyPublishInformationActivity.getInstance() != null)
                        MyPublishInformationActivity.getInstance().refresh();
                } else if ("fail".equals(result)) {
                    ToastUtils.displayMsg("支付失败", mActivity);
                } else if ("cancel".equals(result)) {
                    ToastUtils.displayMsg("取消支付", mActivity);
                }
            }
        }
    }

    /**
     * 获取充值方式
     */
    private void getPayWays(String informationOrderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("informationOrderId", informationOrderId);
        VolleyOperater<PayWaysModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_FIND_INFORMATION_CHARGE_TYPES, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    payWaysModel = (PayWaysModel) obj;
                    String price = StringUtils.BigDecimal2Str(payWaysModel.getValue().getTotalPrice());
                    tvPayMoney.setText("¥" + price);
                    tvThirdMoney.setText("¥" + price);
                    BigDecimal userBalance = payWaysModel.getValue().getUserBalance();
                    tvAccountExtraMoney.setText("(账户余额：¥" + (userBalance == null ? 0 : StringUtils.BigDecimal2Str(userBalance)) + ")");
                    tvBalancePayMoney.setText(userBalance == null ? "" : (userBalance.compareTo(payWaysModel.getValue().getTotalPrice()) > 0 ?
                            ("¥" + price) : ("¥" + StringUtils.BigDecimal2Str(userBalance))));
                    creatPayContainer();
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
}
