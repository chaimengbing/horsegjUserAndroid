package com.project.mgjandroid.ui.activity.groupbuying;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderCouponCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.adapter.RefundListAdapter;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.ui.view.RefundDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupBuyingRefundActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.refund_list_view)
    private NoScrollListView listView;
    @InjectView(R.id.expand_textview)
    private TextView tvExpand;
    @InjectView(R.id.common_back)
    private ImageView imgBack;
    @InjectView(R.id.tv_refund_price)
    private TextView tvRefundPrice;
    @InjectView(R.id.tv_refund_order)
    private TextView tvRefundOrder;
    @InjectView(R.id.list_view)
    private NoScrollListView mlistView;
    @InjectView(R.id.et_evaluation)
    private EditText etEvaluation;

    private GroupPurchaseOrder order;
    private NewRefundListAdapter adapter;
    private boolean isExpand;
    private int ableCount;
    private List<GroupPurchaseOrderCouponCode> couponCodeList;
    private BigDecimal bigDecimal = new BigDecimal(0);
    private StringBuffer buffer = new StringBuffer();
    private String[] str = new String[]{"商家拒绝接待", "商家倒闭/装修/搬迁", "套餐内容/有效期与网页不符", " 预约有问题", "朋友/网上评价不好", "去过，不太满意", "计划有变，没时间消费", "后悔了，不想要了"};
    private RefundReasonListAdapter refundReasonListAdapter;
    private List list = new ArrayList<Integer>();
    private List mlist = new ArrayList<GroupPurchaseOrderCouponCode>();
    private RefundDialog dialog;
    private int count;
    private BigDecimal price;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_refund);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        tvExpand.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        tvRefundOrder.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        order = (GroupPurchaseOrder) getIntent().getSerializableExtra("order");
        if (order != null && order.getGroupPurchaseCouponType() == 1) {
            tvExpand.setText("其他代金券");
        } else {
            tvExpand.setText("其他团购券");
        }
        refundReasonListAdapter = new RefundReasonListAdapter(mActivity, str);
        mlistView.setAdapter(refundReasonListAdapter);
        couponCodeList = order.getGroupPurchaseOrderCouponCodeList();
        adapter = new NewRefundListAdapter(R.layout.new_refund_item, this);
        for (int i = 0; i < couponCodeList.size(); i++) {
            if (couponCodeList.get(i).getStatus() == 0 && couponCodeList.get(i).getIsExpire() == 0) {
                mlist.add(couponCodeList.get(i));
            }
        }
        adapter.addData(mlist);
        listView.setAdapter(adapter);
        if (mlist != null && mlist.size() > 3) {
            tvExpand.setVisibility(View.VISIBLE);
            isExpand = false;
        } else {
            isExpand = true;
            tvExpand.setVisibility(View.GONE);
        }
        adapter.setExpand(isExpand);
        adapter.notifyDataSetChanged();
    }

    private boolean checkSubmit() {
        if (buffer.length() == 0) {
            if (order != null && order.getGroupPurchaseCouponType() == 1) {
                toast("请选择代金券");
            } else {
                toast("请选择团购券");
            }

            return false;
        }
        boolean isCheck = false;
        for (int i = 0; i < str.length; i++) {
            if (mlistView != null) {
                if (mlistView.getChildAt(i) != null) {
                    CheckBox tvWord = (CheckBox) mlistView.getChildAt(i).findViewById(R.id.tv_textview);
                    if (tvWord.isChecked()) {
                        isCheck = true;
                    }
                }
            }
        }
        if (!isCheck) {
            toast("请选择退款原因");
            return false;
        }
        return true;
    }

    private void getRefund() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("groupPurchaseOrderCouponCodeIds", buffer.toString());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            if (mlistView != null) {
                if (mlistView.getChildAt(i) != null) {
                    CheckBox tvWord = (CheckBox) mlistView.getChildAt(i).findViewById(R.id.tv_textview);
                    if (tvWord.isChecked()) {
                        stringBuffer.append(str[i]).append(",");
                    }
                }
            }
        }
        if (CheckUtils.isNoEmptyStr(etEvaluation.getText().toString().trim())) {
            map.put("cancelReason", stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1) + "," + etEvaluation.getText().toString());
        } else {
            map.put("cancelReason", stringBuffer.substring(0, stringBuffer.lastIndexOf(",")));
        }
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_BATCH_REFUND_GROUP_PURCHASE_ORDER_COUPON_CODE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    toast("已退款");
                    finish();
                }
            }
        }, Object.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.expand_textview:
                Drawable drawable = null;
                if (isExpand) {
                    if (order.getGroupPurchaseCouponType() == 1) {
                        tvExpand.setText("其他代金券");
                    } else {
                        tvExpand.setText("其他团购券");
                    }
                    drawable = ContextCompat.getDrawable(getApplication(), R.drawable.icon_expand_yellow);
                    isExpand = false;
                } else {
                    isExpand = true;
                    tvExpand.setText("收起");
                    drawable = ContextCompat.getDrawable(getApplication(), R.drawable.icon_packup_yellow);
                }
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvExpand.setCompoundDrawables(null, null, drawable, null);
                adapter.setExpand(isExpand);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_refund_order:
                if (checkSubmit()) {
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

                        }, "是否确认退款" + ableCount + "张价值" + StringUtils.BigDecimal2Str(price) + "元的代金券？", "申请退款成功后，使用余额支付部分将退还至余额，使用第三方支付部分,将原路退回。", "确定", "取消");
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

                        }, "是否确认退款" + ableCount + "张价值" + StringUtils.BigDecimal2Str(price) + "元的团购券？", "申请退款成功后，使用余额支付部分将退还至余额，使用第三方支付部分,将原路退回。", "确定", "取消");
                        dialog.show();
                    }
                }
                break;
            case R.id.common_back:
                back();
                break;
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
            tvRefundPrice.setText("¥" + bigDecimal);
        } else {
            ableCount++;
            adapter.getData().get(i).setSelected(true);
            price = adapter.getData().get(i).getPrice();
            bigDecimal = bigDecimal.add(price);
            Long id = adapter.getData().get(i).getId();
            buffer.append(id + ",");
            tvRefundPrice.setText("¥" + bigDecimal);
        }
        adapter.notifyDataSetChanged();

    }


}
