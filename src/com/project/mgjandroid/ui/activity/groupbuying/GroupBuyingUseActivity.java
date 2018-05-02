package com.project.mgjandroid.ui.activity.groupbuying;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderCouponCode;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrderCouponGoods;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingCodeIdListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuandi on 2017/3/8.
 */

public class GroupBuyingUseActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView comBack;
    @InjectView(R.id.common_title)
    private TextView comTitle;
    @InjectView(R.id.list_view)
    private ListView listView;

    private List<GroupPurchaseOrderCouponCode> codeList;
    private GroupBuyingUseAdapter adapter;
    private int refreshTime;
    private String orderId;
    private List<GroupPurchaseOrderCouponGoods> goodsList;
    private List<Long> ids;
    private Timer timer;
    private TimerTask timerTask;
    private VolleyOperater<GroupBuyingCodeIdListModel> operater;
    private HashMap<String, Object> map = new HashMap<>();
    private String merchantName;

    public static void toGroupBuyingUseActivity(Activity context, String codeList, String orderId, String goodsList, String merchantName, int refreshTime, int requestCode) {
        Intent intent = new Intent(context, GroupBuyingUseActivity.class);
        intent.putExtra("codeList", codeList);
        intent.putExtra("refreshTime", refreshTime);
        intent.putExtra("orderId", orderId);
        intent.putExtra("goodsList", goodsList);
        intent.putExtra("merchantName", merchantName);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_use);
        Injector.get(this).inject();
        merchantName = getIntent().getStringExtra("merchantName");
        String listStr = getIntent().getStringExtra("codeList");
        if (CheckUtils.isNoEmptyStr(listStr))
            codeList = JSONArray.parseArray(listStr, GroupPurchaseOrderCouponCode.class);
        refreshTime = getIntent().getIntExtra("refreshTime", 5);
        orderId = getIntent().getStringExtra("orderId");
        String goodsStr = getIntent().getStringExtra("goodsList");
        if (CheckUtils.isNoEmptyStr(goodsStr))
            goodsList = JSONArray.parseArray(goodsStr, GroupPurchaseOrderCouponGoods.class);
        initView();
        initData();
        if (adapter.getCount() > 0) {
            operater = new VolleyOperater<>(mActivity);
            map.put("orderId", orderId);
            timer = new Timer();
            timerTask = new MyTimerTask();
            timer.schedule(timerTask, 0, refreshTime * 1000);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (adapter.getCount() > 0) {
            timer.schedule(timerTask, 0, refreshTime * 1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            if (timerTask != null) {
                timerTask.cancel();
            }
            timerTask = new MyTimerTask();
            timer.cancel();
            timer = new Timer();
        }
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    private void initView() {
        comBack.setOnClickListener(this);
        if (CheckUtils.isNoEmptyList(codeList)) {
            if (codeList.get(0).getGroupPurchaseCouponType() == 1) {
                comTitle.setText("代金券");
            } else {
                comTitle.setText("团购券");
            }
        }
        adapter = new GroupBuyingUseAdapter(mActivity, goodsList, merchantName);
        listView.setAdapter(adapter);
    }

    private void initData() {
        if (CheckUtils.isNoEmptyList(codeList)) {
            List<GroupPurchaseOrderCouponCode> data = new ArrayList<>();
            for (GroupPurchaseOrderCouponCode couponCode : codeList) {
                if (couponCode.getStatus() == 0 && couponCode.getIsExpire() == 0) {
                    data.add(couponCode);
                }
            }
            adapter.setCodeList(data);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back) {
            back();
        }
    }

    private void getCouponCodeIds() {
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_ORDER_COUPON_CODE_ID_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    ids = ((GroupBuyingCodeIdListModel) obj).getValue();
                    if (CheckUtils.isNoEmptyList(ids)) {
                        if (adapter.getCount() == ids.size()) {
                            return;
                        } else {
                            ToastUtils.displayMsg("您刚使用了" + (adapter.getCount() - ids.size()) + "张券", mActivity);
                        }
                        List<GroupPurchaseOrderCouponCode> data = new ArrayList<>();
                        for (Long id : ids) {
                            for (GroupPurchaseOrderCouponCode couponCode : codeList) {
                                if (id.equals(couponCode.getId())) {
                                    data.add(couponCode);
                                    break;
                                }
                            }
                        }
                        adapter.setCodeList(data);
                    } else {
                        adapter.setCodeList(new ArrayList<GroupPurchaseOrderCouponCode>());
                        ToastUtils.displayMsg("您的券码都已经使用~", mActivity);
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            }
        }, GroupBuyingCodeIdListModel.class);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            MLog.i("--------------> timer task at:" + System.currentTimeMillis());
            getCouponCodeIds();
        }
    }
}
