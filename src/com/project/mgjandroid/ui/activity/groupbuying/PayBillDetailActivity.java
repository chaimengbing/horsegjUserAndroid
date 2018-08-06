package com.project.mgjandroid.ui.activity.groupbuying;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.project.mgjandroid.utils.ShareUtil;
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
    @InjectView(R.id.img_send_redbag)
    private ImageView sendRedBag;

    private MLoadingDialog loadingDialog;
    private String orderId;
    private GroupPurchaseOrder order;
    public static final int REFRESH = 2000;
    private Dialog callNumDialog;
    private CallPhoneDialog dialog;
    private GroupBuyingOrderModel.ValueBean.ShareRedBagInfo shareRedBagInfo;
    private PopupWindow popupWindow;
    private ShareUtil shareUtil;
    private boolean isCanIn;
    private long currentMS;

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
        sendRedBag.setOnClickListener(this);
        orderId = getIntent().getStringExtra("orderId");
        isCanIn = getIntent().getBooleanExtra("isCanIn", false);
        loadingDialog = new MLoadingDialog();
        callNumDialog = new Dialog(this, R.style.fullDialog);
        sendRedBag.setOnTouchListener(new View.OnTouchListener() {
            int maxwidth;
            int maxheight;
            int preX;// 上一次操作的x的坐标
            int preY;// 上一次操作的Y坐标

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取当前坐标
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                        //判断是否继续传递信号
                        if (moveTime > 200 && (preX > 20 || preY > 20)) {
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        if (maxwidth == 0) {
                            RelativeLayout ivparent = (RelativeLayout) sendRedBag
                                    .getParent();
                            maxwidth = ivparent.getWidth();
                            maxheight = ivparent.getHeight();

                        }
                        preX = rawX;
                        preY = rawY;
                        currentMS = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取当前x,y轴移动的距离
                        int dx = rawX - preX;
                        int dy = rawY - preY;
                        // 获取当前图片的四个值
                        int left = sendRedBag.getLeft() + dx;
                        int top = sendRedBag.getTop() + dy;
                        int right = sendRedBag.getRight() + dx;
                        int bottm = sendRedBag.getBottom() + dy;
                        if (left < 0) {
                            right = right - left;
                            left = 0;

                        }
                        //限制right
                        if (right > maxwidth) {
                            left = left - (right - maxwidth);
                            right = maxwidth;
                        }
                        if (top < 0) {
                            bottm = bottm - top;
                            top = 0;
                        }
                        if (bottm > maxheight) {
                            top = top - (bottm - maxheight);
                            bottm = maxheight;
                        }
                        sendRedBag.layout(left, top, right, bottm);
                        // 从新给初始值赋值
                        preX = rawX;
                        preY = rawY;

                        break;

                }

                return false;
            }
        });
    }

    private void showRedBag(){
        sendRedBag.setVisibility(View.GONE);
        View view = LayoutInflater.from(this).inflate(R.layout.send_redbag, null);
        TextView tvSure = (TextView) view.findViewById(R.id.sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.cancel);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                sendRedBag.setVisibility(View.VISIBLE);
                if (shareUtil == null && shareRedBagInfo != null) {
                    shareUtil = new ShareUtil(mActivity, shareRedBagInfo.getTitle(),
                            shareRedBagInfo.getMemo(),
                            shareRedBagInfo.getUrl(), shareRedBagInfo.getImg());
                }
                if (shareUtil != null) shareUtil.showRedBagPopupWindow();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRedBag.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(!popupWindow.isShowing()){
                    sendRedBag.setVisibility(View.VISIBLE);
                }
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
            popupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER_VERTICAL, 0, 0);
        }
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
                    shareRedBagInfo = ((GroupBuyingOrderModel) obj).getValue().getShareRedBagInfo();
                    if(shareRedBagInfo == null){
                        sendRedBag.setVisibility(View.GONE);
                    }else {
                        sendRedBag.setVisibility(View.VISIBLE);
                    }
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
        if(order.getStatus()== -1){
            tvNowStatus.setText("超时已取消");
        }else if(order.getStatus()== 1){
            tvNowStatus.setText("未付款");
        }else if(order.getStatus()==2){
            tvNowStatus.setText("支付完成");
            if(shareRedBagInfo!=null){
                if(isCanIn){
                    isCanIn = false;
                    showRedBag();
                }
            }
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
            case R.id.img_send_redbag:
                sendRedBag.setVisibility(View.VISIBLE);
                if (shareUtil == null && shareRedBagInfo != null) {
                    shareUtil = new ShareUtil(mActivity, shareRedBagInfo.getTitle(),
                            shareRedBagInfo.getMemo(),
                            shareRedBagInfo.getUrl(), shareRedBagInfo.getImg());
                }
                if (shareUtil != null) shareUtil.showRedBagPopupWindow();
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
