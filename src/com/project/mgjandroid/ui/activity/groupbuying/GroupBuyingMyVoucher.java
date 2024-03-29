package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingVoucherListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupBuyingMyVoucher extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView tvBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_affirm)
    private TextView tvAffirm;
    @InjectView(R.id.list_view)
    private NoScrollListView listView;
    @InjectView(R.id.tv_history)
    private TextView tvHistory;
    @InjectView(R.id.no_data)
    private RelativeLayout noData;
    @InjectView(R.id.tv_transaction)
    private TextView tvTransaction;

    private long merchantId;
    private int isExpire = 0;
    private MyVoucherAdapter myVoucherAdapter;
    private List<GroupBuyingVoucherListModel.ValueBean> listValue;
    private GroupBuyingVoucherListModel voucherList;
    private List<GroupBuyingVoucherListModel.ValueBean> isCheckedList = new ArrayList<>();
    private String totalPrice;
    private GroupBuyingVoucherListModel voucherList1;
    private GroupPurchaseMerchant merchant;
    private boolean isCanSelect;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_my_voucher);
        Injector.get(this).inject();
        initView();
        getData();
    }
    private void initView(){
        tvBack.setOnClickListener(this);
        tvAffirm.setOnClickListener(this);
        tvHistory.setOnClickListener(this);
        tvTransaction.setOnClickListener(this);
        tvTitle.setText("我的代金券");
        merchantId = getIntent().getLongExtra("merchantId",-1l);
        totalPrice = getIntent().getStringExtra("totalPrice");
        isCanSelect = getIntent().getBooleanExtra("isCanSelect", false);
        merchant = (GroupPurchaseMerchant) getIntent().getSerializableExtra("merchant");
        voucherList1 = (GroupBuyingVoucherListModel)getIntent().getSerializableExtra("voucherList");
        myVoucherAdapter = new MyVoucherAdapter(R.layout.my_voucher_item, mActivity,merchant);
        listView.setAdapter(myVoucherAdapter);
        if(voucherList1!=null){
            for(GroupBuyingVoucherListModel.ValueBean bean: voucherList1.getValue()){
                if(bean.isChecked()){
                    isCheckedList.add(bean);
                }
            }
        }
        if(isCanSelect){
            if(merchant.getIsSharingRelationship()==2){
                listView.setOnItemClickListener(null);
            }else {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        List<GroupBuyingVoucherListModel.ValueBean> data = myVoucherAdapter.getData();
                        GroupBuyingVoucherListModel.ValueBean selectBean = null;
                        for (GroupBuyingVoucherListModel.ValueBean bean : data){
                            if(bean.isChecked()) {
                                selectBean = bean;
                                break;
                            }
                        }
                        GroupBuyingVoucherListModel.ValueBean bean = data.get(i);
                        if (selectBean != null && bean.getId() != selectBean.getId()){
                            if (selectBean.getIsCumulate() == 1){
                                if (bean.getIsCumulate() == 0){
                                    toast("不可叠加");
                                    return;
                                }
                            }else if (selectBean.getIsCumulate() == 0){
                                toast("不可叠加");
                                return;
                            }
                        }

                        if(data.get(i).isChecked()){
                            data.get(i).setIsChecked(false);
                        }else {
                            if(CheckUtils.isEmptyStr(totalPrice)){
                                toast("请填写消费总额");
                                return;
                            }
                            data.get(i).setIsChecked(true);
                        }

                        myVoucherAdapter.notifyDataSetChanged();
                    }
                });
            }
        }else {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    List<GroupBuyingVoucherListModel.ValueBean> data = myVoucherAdapter.getData();
                    GroupBuyingVoucherListModel.ValueBean selectBean = null;
                    for (GroupBuyingVoucherListModel.ValueBean bean : data){
                        if(bean.isChecked()) {
                            selectBean = bean;
                            break;
                        }
                    }
                    GroupBuyingVoucherListModel.ValueBean bean = data.get(i);
                    if (selectBean != null && bean.getId() != selectBean.getId()){
                        if (selectBean.getIsCumulate() == 1){
                            if (bean.getIsCumulate() == 0){
                                toast("不可叠加");
                                return;
                            }
                        }else if (selectBean.getIsCumulate() == 0){
                            toast("不可叠加");
                            return;
                        }
                    }

                    if(data.get(i).isChecked()){
                        data.get(i).setIsChecked(false);
                    }else {
                        if(CheckUtils.isEmptyStr(totalPrice)){
                            toast("请填写消费总额");
                            return;
                        }
                        data.get(i).setIsChecked(true);
                    }

                    myVoucherAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("isExpire", isExpire);
        data.put("merchantId", merchantId);
        data.put("start", 0);
        data.put("size", 20);
        VolleyOperater<GroupBuyingVoucherListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_ORDER_COUPON_CODE_LIST, data, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
//                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    voucherList = (GroupBuyingVoucherListModel) obj;
                    listValue = voucherList.getValue();
                    if(CheckUtils.isNoEmptyList(isCheckedList)){
                        for(GroupBuyingVoucherListModel.ValueBean  data :listValue){
                            for (GroupBuyingVoucherListModel.ValueBean valueBean1:isCheckedList){
                                if (valueBean1.getId()==data.getId()){
                                    data.setIsChecked(true);
                                }
                            }
                        }
                    }
                    if(listValue.size()==0){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                        myVoucherAdapter.setData(listValue);
                    }
                }
            }
        }, GroupBuyingVoucherListModel.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.common_back:
                back();
                break;
            case R.id.tv_affirm:
                setResult(2018,new Intent().putExtra("selectVoucher",voucherList));
                finish();
                break;
            case R.id.tv_transaction:
            case R.id.tv_history:
                Intent intent = new Intent(mActivity, GroupBuyingHistoricalVouchersActivity.class);
                intent.putExtra("merchantId", merchantId);
                intent.putExtra("merchant", merchant);
                startActivity(intent);
                break;
        }
    }
}
