package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingPreviewModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingVoucherListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.SelectRedBagActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscountBuyTicketActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView tvBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
//    @InjectView(R.id.tv_explain)
//    private TextView tvRight;
    @InjectView(R.id.et_evaluation)
    private EditText etEvalution;
    @InjectView(R.id.img_unselected)
    private ImageView imgSelected;
    @InjectView(R.id.cb_unselected)
    private CheckBox cbSelected;
    @InjectView(R.id.tv_selected)
    private TextView tvSelected;
    @InjectView(R.id.tv_discount)
    private TextView tvDiscount;
    @InjectView(R.id.tv_voucher)
    private TextView tvVoucher;
    @InjectView(R.id.tv_red_bag)
    private TextView tvRedBag;
    @InjectView(R.id.tv_confirm)
    private TextView tvConfirm;
    @InjectView(R.id.rl_platform_redbag_layout)
    private RelativeLayout rlRedBagLayout;
    @InjectView(R.id.rl_voucher)
    private RelativeLayout rlVoucher;
    @InjectView(R.id.tv_amoun_actually_paid)
    private TextView tvAmounActuallyPaid;
    @InjectView(R.id.rl_discount)
    private RelativeLayout rlDiscount;
    @InjectView(R.id.rllayout)
    private RelativeLayout rlLayout;


    private String titleName;
    private GroupPurchaseMerchant merchant;
    private MLoadingDialog loadingDialog;
    private int isDiscount = 0;
    private String voucherPrice = "0";
    private String totalPrice = null;
    private RedBag redBag;
    private GroupBuyingPreviewModel.ValueBean previewModelValue;
    private ArrayList<Map<String, Object>> list = new ArrayList<>();
    private boolean isCanSelect;
    private boolean isVoucherChecked;
    private double discount;
    private GroupBuyingVoucherListModel voucherList;
    private  int max=100000000;
    private  int min;
    private  boolean isRun;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_discount_buy_ticket);
        Injector.get(this).inject();
        initView();
        payForPreview();
    }

    private void initView() {
        merchant = (GroupPurchaseMerchant) getIntent().getSerializableExtra("merchant");
        tvTitle.setText(merchant.getName());
        tvBack.setOnClickListener(this);
//        tvRight.setOnClickListener(this);
        rlRedBagLayout.setOnClickListener(this);
        rlVoucher.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        imgSelected.setOnClickListener(this);
        cbSelected.setOnClickListener(this);
        if(CheckUtils.isEmptyStr(merchant.getDiscountRatio())){
            rlDiscount.setVisibility(View.GONE);
        }else {
            discount = Integer.parseInt(merchant.getDiscountRatio()) * 0.01 * 10;
        }
        tvDiscount.setText(discount + "折");
//        loadingDialog = new MLoadingDialog();
        etEvalution.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                        if (!App.isLogin()) {
                            Intent intent = new Intent(mActivity, SmsLoginActivity.class);
                            startActivity(intent);
                            return;
                        }
                        payForPreview();
                }
            }
        });
        etEvalution.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned dest, int i2, int dend) {
                String lastInputContent = dest.toString();
                //如果包含.
                if (lastInputContent.contains(".")) {
                    int index = lastInputContent.indexOf(".");
                    //如果光标在.的前面，那么判断.的位置是不是在8以后
                    if (dend < index) {
                        if (index >= 8) {
                            return "";
                        }
                    }
                    //如果光标在.后面大于3，说明小数位要超过2位了
                    if (dend - index >= 3) {
                        return "";
                    }

                } else {
                    //如果输入的字符不是.并且字符串的长度大于8，说明输入的整数部分已经达到了千万
                    if (!charSequence.toString().equals(".") && lastInputContent.length() >= 8) {
                        return "";
                    }
                }
                //如果字符串长度大于11直接返回了
                if (lastInputContent.length() >= 11) {
                    return "";
                }
                return null;
            }
        }});

        etEvalution.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                etEvalution.setHint("");
                tvConfirm.setEnabled(true);
                tvAmounActuallyPaid.setText(etEvalution.getText().toString().trim());
                isCanSelect= false;
                isVoucherChecked= false;
                tvSelected.setText("");
                imgSelected.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.group_buy_unselected_new));
                isDiscount = 0;
                voucherPrice = "0";
                if(previewModelValue!=null){
                    previewModelValue.setTotalPrice(new BigDecimal(0));
                }
                if(previewModelValue!=null){
                    voucherList= null;
                    list.clear();
                    if(previewModelValue.getEnableGroupPurchaseOrderCouponCodeCount()>0){
                        tvVoucher.setText("有" + previewModelValue.getEnableGroupPurchaseOrderCouponCodeCount() + "个抵用券可用");
                    }else {
                        tvVoucher.setText("无可用抵用券");
                    }
                    redBag=null;
                    tvRedBag.setText("");
                    if (CheckUtils.isNoEmptyStr(etEvalution.getText().toString().trim())&&previewModelValue.getPlatformRedBagCount() > 0) {
                        tvRedBag.setText("去使用");
                    }
                }


                if(editable.toString().trim().length()>0){
                    if("0".equals(editable.toString().trim())){
                        tvConfirm.setEnabled(false);
                    }else {
                        etEvalution.setHint("");
                        tvConfirm.setEnabled(true);
                    }
                }else {
                    etEvalution.setHint("询问服务员后输入");
                    tvConfirm.setEnabled(false);
                }
                String text = editable.toString();
                int len = editable.toString().length();
                if (len == 1 && text.equals("0")) {
                    editable.clear();
                }}
        });
    }



    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_back:
                back();
            case R.id.cb_unselected:
                if(cbSelected.isChecked()){
                    rlLayout.setVisibility(View.VISIBLE);
                }else {
                    rlLayout.setVisibility(View.GONE);
                }
                break;
//            case R.id.tv_explain:
//                startActivity(new Intent(mActivity, GroupBuyingPrivilegeActivity.class));
//                break;
            case R.id.rl_platform_redbag_layout:
                Intent intentSelect = new Intent(this, SelectRedBagActivity.class);
                if("0".equals(StringUtils.BigDecimal2Str(previewModelValue.getTotalPrice()))){
                    if(CheckUtils.isNoEmptyStr(etEvalution.getText().toString().trim())){
                        intentSelect.putExtra(SelectRedBagActivity.ITEMS_PRICE, Double.parseDouble(etEvalution.getText().toString().trim()));
                    }
                }else {
                    intentSelect.putExtra(SelectRedBagActivity.ITEMS_PRICE, previewModelValue.getTotalPrice().doubleValue());
                }
                intentSelect.putExtra(SelectRedBagActivity.BUSINESS_TYPE, 6);
                intentSelect.putExtra("isAgentId", previewModelValue.getAgentId());
                if (redBag != null) {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, redBag.getId());
                } else {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, -1l);
                }
                startActivityForResult(intentSelect, 1111);
                break;
            case R.id.rl_voucher:
                Intent intent = new Intent(this, GroupBuyingMyVoucher.class);
                intent.putExtra("merchantId", merchant.getId());
                intent.putExtra("totalPrice", etEvalution.getText().toString().trim());
                intent.putExtra("voucherList", voucherList);
                intent.putExtra("merchant", merchant);
                intent.putExtra("isCanSelect", isCanSelect);
                startActivityForResult(intent,2018);
                break;
            case R.id.tv_confirm:
                submitOrder();
                break;
            case R.id.img_unselected:
                if(CheckUtils.isNoEmptyStr(etEvalution.getText().toString().trim())){
                    if(isVoucherChecked){
                        if(merchant.getIsSharingRelationship()==2){
                            isDiscount = 0;
                            toast("代金券和优惠折扣不可同时选择");
                            return;
                        }else {
                            if(isCanSelect){
                                isDiscount = 0;
                                isCanSelect = false;
                            }else {
                                isDiscount = 1;
                                isCanSelect = true;
                            }
//                            if(isCanSelect){
//                                isDiscount = 1;
//                                isCanSelect = true;
//                                tvSelected.setText("-¥"+StringUtils.BigDecimal2Str(previewModelValue.getDiscountAmt()));
//                                imgSelected.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.group_buy_selected));
//                            }else {
//                                isDiscount = 0;
//                                isCanSelect = false;
//                                tvSelected.setText("");
//                                imgSelected.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.group_buy_unselected));
//                            }
                        }
                    }else {
                        if(isCanSelect){
                            isDiscount = 0;
                            isCanSelect = false;
                        }else {
                            isDiscount = 1;
                            isCanSelect = true;
                        }
                    }
                }
                payForPreview();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SelectRedBagActivity.RED_BAG_MONEY) {
            redBag = (RedBag) data.getSerializableExtra(SelectRedBagActivity.RED_MONEY_BAG);
            payForPreview();
        }
        if(resultCode == 2018){
            list.clear();
            voucherList = (GroupBuyingVoucherListModel)data.getSerializableExtra("selectVoucher");
            for(GroupBuyingVoucherListModel.ValueBean bean: voucherList.getValue()){
                if(bean.isChecked()){
                    Map<String,Object> voucherMap = new HashMap<>();
                    voucherMap.put("id", bean.getId());
                    voucherMap.put("couponCode", bean.getCouponCode());
                    list.add(voucherMap);
                }
            }
            if(list.size()>0){
                isVoucherChecked = true;
            }else {
                isVoucherChecked = false;
                voucherPrice ="0";
            }

            payForPreview();
        }

    }

    private void showPreviewOrder(GroupBuyingPreviewModel.ValueBean previewModelValue) {
        if(list.size()>0){
            voucherPrice = previewModelValue.getCashDeductionPrice();
            tvVoucher.setText("-¥" + previewModelValue.getCashDeductionPrice());
        }else {
            voucherPrice= "0";
            tvVoucher.setText("无可用抵用券");
            if (previewModelValue.getEnableGroupPurchaseOrderCouponCodeCount() > 0) {
                if(previewModelValue.getEnableGroupPurchaseOrderCouponCodeCount()>0){
                    tvVoucher.setText("有" + previewModelValue.getEnableGroupPurchaseOrderCouponCodeCount() + "个抵用券可用");
                }else {
                    tvVoucher.setText("无可用抵用券");
                }

            }
        }
        if (redBag != null) {
            tvRedBag.setText("-¥" + StringUtils.BigDecimal2Str(redBag.getAmt()));
        } else {
            tvRedBag.setText("");
            if (CheckUtils.isNoEmptyStr(etEvalution.getText().toString().trim())&&previewModelValue.getPlatformRedBagCount() > 0) {
                tvRedBag.setText("去使用");
            }
        }
        if (!CheckUtils.isEmptyStr(StringUtils.BigDecimal2Str(previewModelValue.getTotalPrice()))) {
            tvAmounActuallyPaid.setText("¥" + StringUtils.BigDecimal2Str(previewModelValue.getTotalPrice()));
            tvConfirm.setEnabled(true);
        }
        if(CheckUtils.isEmptyStr(etEvalution.getText().toString().trim())){
            tvAmounActuallyPaid.setText("");
            tvConfirm.setEnabled(false);
        }
    }

    public void submitOrder() {
//        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> params = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> redBagList = new ArrayList<>();
        if (redBag != null) {
            Map<String, Object> redmap = new HashMap<>();
            redmap.put("id", redBag.getId());
            redmap.put("name", redBag.getName());
            redmap.put("amt", redBag.getAmt());
            redmap.put("promotionType", redBag.getPromotionType());
            redBagList.add(redmap);
        }
        data.put("loginToken", App.getUserInfo().getToken());
        data.put("merchantId", merchant.getId());
        if (CheckUtils.isNoEmptyStr(etEvalution.getText().toString().trim())) {
            data.put("originalPrice", etEvalution.getText().toString().trim());
        }
        data.put("quantity", 1);
//        data.put("totalPrice", 1);
        data.put("userId", App.getUserInfo().getId());
//        团购订单订单类型 1, "代金券",2, "团购券",3, "优惠买单"
        data.put("groupPurchaseOrderType", 3);
        if (redBagList.size() > 0) {
            data.put("redBags", redBagList);
        }
        data.put("hasDiscount", isDiscount);
        if(CheckUtils.isNoEmptyStr(merchant.getDiscountRatio())){
            data.put("discountRatio", Integer.parseInt(merchant.getDiscountRatio()));
        }else {
            data.put("discountRatio", 0);
        }

        data.put("cashDeductionPrice", voucherPrice);
        if(list.size()>0){
            data.put("groupPurchaseOrderCouponCodeList", list);
        }
        if("0".equals(StringUtils.BigDecimal2Str(previewModelValue.getTotalPrice()))){
            data.put("totalPrice", etEvalution.getText().toString().trim());
        }else {
            data.put("totalPrice", previewModelValue.getTotalPrice());
        }

//        params.put("groupPurchaseOrderCouponCodeList", JSON.toJSONString(data));
        params.put("data", JSON.toJSONString(data));

        VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GROUP_PURCHASE_ORDER_SUBMIT, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
//                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    SubmitOrderModel submitOrderModel = (SubmitOrderModel) obj;
                    Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                    intent.putExtra("orderId", submitOrderModel.getValue().getId());
                    intent.putExtra("agentId", submitOrderModel.getValue().getAgentId());
                    intent.putExtra("merchantId", submitOrderModel.getValue().getMerchantId());
                    intent.putExtra("isGroupPurchaseBuy", true);
                    startActivity(intent);
                    finish();
                }
            }
        }, SubmitOrderModel.class);
    }

    private void payForPreview() {
//        if(!loadingDialog.isVisible()){
//            loadingDialog.show(getFragmentManager(), "");
//        }
        Map<String, Object> params = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> redBagList = new ArrayList<>();
        if (redBag != null) {
            Map<String, Object> redmap = new HashMap<>();
            redmap.put("id", redBag.getId());
            redmap.put("name", redBag.getName());
            redmap.put("amt", redBag.getAmt());
            redmap.put("promotionType", redBag.getPromotionType());
            redBagList.add(redmap);
        }
        if(list.size()>0){
            data.put("groupPurchaseOrderCouponCodeList", list);
        }
        data.put("loginToken", App.getUserInfo().getToken());
        data.put("merchantId", merchant.getId());
        if (CheckUtils.isNoEmptyStr(etEvalution.getText().toString().trim())) {
            data.put("originalPrice", etEvalution.getText().toString().trim());
        }
        data.put("quantity", 1);
//        data.put("totalPrice", 1);
        data.put("userId", App.getUserInfo().getId());
//        团购订单订单类型 1, "代金券",2, "团购券",3, "优惠买单"
        data.put("groupPurchaseOrderType", 3);
        if (redBagList.size() > 0) {
            data.put("redBags", redBagList);
        }
        data.put("hasDiscount", isDiscount);
        if(CheckUtils.isNoEmptyStr(merchant.getDiscountRatio())){
            data.put("discountRatio", Integer.parseInt(merchant.getDiscountRatio()));
        }else {
            data.put("discountRatio", 0);
        }
        data.put("cashDeductionPrice", voucherPrice);
//        params.put("groupPurchaseOrderCouponCodeList", JSON.toJSONString(data));
        params.put("data", JSON.toJSONString(data));
        VolleyOperater<GroupBuyingPreviewModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GROUP_PURCHASE_ORDER_PREVIEW, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
//                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        if(isCanSelect){
                            isDiscount = 0;
                            isCanSelect = false;
                        }
                        if(redBag!=null&&"支付金额不能小于红包金额".equals(obj.toString().trim())){
                            isDiscount = 1;
                            isCanSelect = true;
                            redBag=null;
                            tvRedBag.setText("");
                            if (CheckUtils.isNoEmptyStr(etEvalution.getText().toString().trim())&&previewModelValue.getPlatformRedBagCount() > 0) {
                                tvRedBag.setText("去使用");
                            }
                            payForPreview();
                        }else {
                            ToastUtils.displayMsg(obj.toString(), mActivity);
                        }
//                        if(isVoucherChecked&&!isCanSelect){
//                            list.clear();
//                            voucherPrice= "0";
//                            isVoucherChecked = false;
//                            if(voucherList!=null){
//                                for(int i=0;i<voucherList.getValue().size();i++){
//                                    voucherList.getValue().get(i).setIsChecked(false);
//                                }
//                            }
//
//                        }
                        return;
                    }
                    GroupBuyingPreviewModel previewModel = (GroupBuyingPreviewModel) obj;
                    previewModelValue = previewModel.getValue();
                    showPreviewOrder(previewModelValue);
                    if(isCanSelect){
                        tvSelected.setText("-¥"+StringUtils.BigDecimal2Str(previewModelValue.getDiscountAmt()));
                        imgSelected.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.group_buy_selected_new));
//                        isCanSelect = false;
                    }else {
                        tvSelected.setText("");
                        imgSelected.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.group_buy_unselected_new));
//                        isCanSelect = true;
                    }
                }
            }
        }, GroupBuyingPreviewModel.class);
    }

}
