package com.project.mgjandroid.ui.activity.groupbuying;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupBuyingRefundActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.refund_list_view)
    private NoScrollListView listView;
    @InjectView(R.id.expand_textview)
    private TextView tvExpand;
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
    private String[] str = new String[]{"商家拒绝接待","商家倒闭/装修/搬迁","套餐内容/有效期与网页不符"," 预约有问题","朋友/网上评价不好","去过，不太满意","计划有变，没时间消费","后悔了，不想要了"};
    private RefundReasonListAdapter refundReasonListAdapter;
    private List list  = new ArrayList<Integer>();
    private List mlist = new ArrayList<GroupPurchaseOrderCouponCode>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_refund);
        Injector.get(this).inject();
        initView();
    }
    private void initView(){
        tvExpand.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        tvRefundOrder.setOnClickListener(this);
        order = (GroupPurchaseOrder)getIntent().getSerializableExtra("order");
        refundReasonListAdapter = new RefundReasonListAdapter(mActivity,str);
        mlistView.setAdapter(refundReasonListAdapter);
        couponCodeList = order.getGroupPurchaseOrderCouponCodeList();
        adapter = new NewRefundListAdapter(R.layout.new_refund_item,this);
        for(int i=0;i<couponCodeList.size();i++){
            if(couponCodeList.get(i).getStatus() == 0 && couponCodeList.get(i).getIsExpire() == 0){
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

    private void getRefund() {
        boolean isCheck= false;
        HashMap<String, Object> map = new HashMap<>();
        if(buffer.length()==0){
            toast("请选择团购券");
            return;
        }else {
            map.put("groupPurchaseOrderCouponCodeIds", buffer.toString());
        }
        StringBuffer stringBuffer = new StringBuffer();
        for(int i= 0; i<str.length;i++){
           if(mlistView!=null){
               if(mlistView.getChildAt(i)!=null){
                   CheckBox tvWord = (CheckBox) mlistView.getChildAt(i).findViewById(R.id.tv_textview);
                   if(tvWord.isChecked()){
                       isCheck= true;
                       stringBuffer.append(str[i]).append(",");
                   }
               }
           }
       }
       if(isCheck){
            if(CheckUtils.isNoEmptyStr(etEvaluation.getText().toString().trim())){
                map.put("cancelReason", stringBuffer.toString().substring(0,stringBuffer.toString().length()-1)+","+etEvaluation.getText().toString());
            }else {
                map.put("cancelReason", stringBuffer.substring(0, stringBuffer.lastIndexOf(",")));
            }
       }else {
            toast("请选择退款原因");
            return;
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
        switch (v.getId()){
            case R.id.expand_textview:
                Drawable drawable = null;
                if (isExpand) {
                    tvExpand.setText("其他团购券");
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
                getRefund();
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
            BigDecimal price = adapter.getData().get(i).getPrice();
            bigDecimal = bigDecimal.add(price);
            Long id = adapter.getData().get(i).getId();
            buffer.append(id + ",");
            tvRefundPrice.setText("¥" + bigDecimal);
        }
        adapter.notifyDataSetChanged();

    }

}
