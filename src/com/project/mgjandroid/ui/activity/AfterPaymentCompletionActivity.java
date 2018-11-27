package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderCouponCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingMerchantListModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingMerchantModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingAddEvaluationActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingMerchantAdapter;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingOrderForGoodsDetailsActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingUseActivity;
import com.project.mgjandroid.ui.activity.groupbuying.NewRefundListAdapter;
import com.project.mgjandroid.ui.activity.groupbuying.PayBillDetailActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AfterPaymentCompletionActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    @InjectView(R.id.tv_right)
    private TextView tvRight;
    @InjectView(R.id.tv_hint)
    private TextView tvHint;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.tv_payment_amount)
    private TextView tvPaymentAmount;
    @InjectView(R.id.tv_order_number)
    private TextView tvOrderNumber;
    @InjectView(R.id.tv_button)
    private TextView tvButton;
    @InjectView(R.id.about_back)
    private ImageView tvBack;
    @InjectView(R.id.merchant_list)
    private NoScrollListView merchantListView;
    @InjectView(R.id.layout)
    private LinearLayout moreLayout;
    private GroupPurchaseMerchant merchant;
    private MLoadingDialog loadingDialog;
    private int merchantId;
    public static final int REFRESH = 2000;
    private String mResult;
    private String orderId;
    private GroupPurchaseOrder order;
    private boolean isGroupPurchaseBuy;
    private List<GroupPurchaseOrderCouponCode> couponCodeList;
    private List mlist = new ArrayList<GroupPurchaseOrderCouponCode>();


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_after_payment_completion);
        Injector.get(this).inject();
        loadingDialog = new MLoadingDialog();
        initData();
    }

    private void initData() {
        merchantId = getIntent().getIntExtra("merchantId", -1);
        mResult = getIntent().getStringExtra("mResult");
        orderId = getIntent().getStringExtra("orderId");
        isGroupPurchaseBuy = getIntent().getBooleanExtra("isGroupPurchaseBuy", false);
        if (merchantId == -1) {
            finish();
            return;
        }
        getOrderData();
        getMerchant();
        getMoreMerchant();
    }

    private void showInformation() {
        if (CheckUtils.isNoEmptyStr(mResult)) {
            if ("success".equals(mResult)) {
                tvHint.setText("支付成功");
                Drawable drawable = getResources().getDrawable(R.drawable.ic_payment_success);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvHint.setCompoundDrawables(drawable, null, null, null);
                if (isGroupPurchaseBuy) {
                    if(order.getHasComments()>0){
                        tvButton.setText("订单详情");
                    }else {
                        tvButton.setText("评价");
                    }
                } else {
                    if (order.getGroupPurchaseOrderCoupon().getType() == 2 && order.getStatus() == 4) {
                        tvButton.setText("订单详情");
                    } else if (order.getStatus() == 2) {
                        if(CheckUtils.isNoEmptyList(order.getGroupPurchaseOrderCouponCodeList())){
                            couponCodeList = order.getGroupPurchaseOrderCouponCodeList();
                            for (int i = 0; i < couponCodeList.size(); i++) {
                                if (couponCodeList.get(i).getStatus() == 0 && couponCodeList.get(i).getIsExpire() == 0) {
                                    mlist.add(couponCodeList.get(i));
                                }
                            }
                        }
                        if(mlist.size()>0){
                            tvButton.setText("立即使用");
                        }else {
                            tvButton.setVisibility(View.GONE);
                        }
                    }
                }
            } else if ("fail".equals(mResult)) {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_payment_unsuccess);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvHint.setCompoundDrawables(drawable, null, null, null);
                tvHint.setText("支付失败");
                tvButton.setText("重新支付");
            }
        }

        if (order != null) {
            tvName.setText(order.getGroupPurchaseMerchantName());
            tvPaymentAmount.setText("实付金额：" + StringUtils.BigDecimal2Str(order.getTotalPrice()) + "元");
            tvOrderNumber.setText("订单编号：" + order.getId());
        }
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPlace();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order.getOrderType()== 3) {
                    Intent intentDetail = new Intent(mActivity, PayBillDetailActivity.class);
                    intentDetail.putExtra("orderId", orderId);
                    intentDetail.putExtra("isCanIn", true);
                    startActivityForResult(intentDetail, REFRESH);
                } else {
                    Intent intentDetail = new Intent(mActivity, GroupBuyingOrderForGoodsDetailsActivity.class);
                    intentDetail.putExtra("orderId", order.getId());
                    startActivityForResult(intentDetail, REFRESH);
                }
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderData();
    }

    private void goToPlace() {
        if ("success".equals(mResult)) {
            if (isGroupPurchaseBuy) {
                if(order.getHasComments()>0){
                    Intent intentDetail = new Intent(mActivity, PayBillDetailActivity.class);
                    intentDetail.putExtra("orderId", orderId);
                    intentDetail.putExtra("isCanIn", true);
                    startActivityForResult(intentDetail, REFRESH);
                }else {
                    Intent carEvaluate = new Intent(mActivity, GroupBuyingAddEvaluationActivity.class);
                    carEvaluate.putExtra("groupPurchaseOrder", order);
                    startActivityForResult(carEvaluate, REFRESH);
                }
            } else {
                if (order.getGroupPurchaseOrderCoupon().getType() == 2 && order.getStatus() == 4) {
                    Intent intentDetail = new Intent(mActivity, GroupBuyingOrderForGoodsDetailsActivity.class);
                    intentDetail.putExtra("orderId", order.getId());
                    startActivityForResult(intentDetail, REFRESH);
                } else if (order.getStatus() == 2) {
                    GroupBuyingUseActivity.toGroupBuyingUseActivity(mActivity, JSONArray.toJSONString(order.getGroupPurchaseOrderCouponCodeList()), orderId, JSONArray.toJSONString(order.getGroupPurchaseOrderCouponGoodsList()), order.getGroupPurchaseMerchantName(), order.getRefreshTime(), REFRESH);
                }
            }
        } else if ("fail".equals(mResult)) {
            if (isGroupPurchaseBuy) {
                Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("agentId", order.getAgentId());
                intent.putExtra("isGroupPurchaseBuy", true);
                startActivityForResult(intent, REFRESH);
            } else {
                Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("agentId", order.getAgentId());
                intent.putExtra("isGroupPurchase", true);
                startActivityForResult(intent, REFRESH);
            }
        }
    }

    private void getOrderData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<GroupBuyingOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_NEW_ORDER_DETAIL, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    order = null;
                    order = ((GroupBuyingOrderModel) obj).getValue().getGroupPurchaseOrder();
                    showInformation();
                }
            }
        }, GroupBuyingOrderModel.class);
    }

    private void getMerchant() {
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<GroupBuyingMerchantModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("groupPurchaseMerchantId", merchantId);
        if (App.getUserInfo() != null) map.put("userId", App.getUserInfo().getId());
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_MERCHANT_INFO, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    merchant = ((GroupBuyingMerchantModel) obj).getValue();
                }
            }
        }, GroupBuyingMerchantModel.class);
    }

    private void getMoreMerchant() {
        Map<String, Object> map = new HashMap<>();
        map.put("start", 0);
        map.put("size", 20);
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("merchantId", merchantId);
//        排序方式1:智能排序;2:离我最近;3:好评优先
//        map.put("sortType", 1);
//        map.put("groupPurchaseCategoryId", 1);
//        map.put("childGroupPurchaseCategoryId", 2);
//        /** 商户服务多个逗号分隔：1,2,3,4,5,6,7 **/
//        map.put("groupPurchaseMerchantServices", "1,2");
        VolleyOperater<GroupBuyingMerchantListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_NEAR_GROUP_PURCHASE_MERCHANT, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    GroupBuyingMerchantListModel model = (GroupBuyingMerchantListModel) obj;
                    ArrayList<GroupPurchaseMerchant> mlist = model.getValue();
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        showMoreMerchant(mlist);
                    }
                }
            }
        }, GroupBuyingMerchantListModel.class);
    }

    private void showMoreMerchant(ArrayList<GroupPurchaseMerchant> mlist) {
        if(CheckUtils.isNoEmptyList(mlist)){
            moreLayout.setVisibility(View.VISIBLE);
        }else {
            moreLayout.setVisibility(View.GONE);
        }
        GroupBuyingMerchantAdapter adapter = new GroupBuyingMerchantAdapter(mActivity, false);
        merchantListView.setAdapter(adapter);
        adapter.setList(mlist);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        getOrderData();
    }

    @Override
    public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
