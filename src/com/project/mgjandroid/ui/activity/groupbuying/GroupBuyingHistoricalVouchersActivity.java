package com.project.mgjandroid.ui.activity.groupbuying;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingVoucherListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupBuyingHistoricalVouchersActivity extends BaseActivity{

    @InjectView(R.id.common_back)
    private ImageView tvBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.list_view)
    private ListView listView;
    @InjectView(R.id.no_data)
    private RelativeLayout noData;

    private MyVoucherAdapter myVoucherAdapter;
    private long merchantId;
    private int isExpire = 1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_history_voucher);
        Injector.get(this).inject();
        initView();
        getData();
    }

    private void initView() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        tvTitle.setText("历史代金券");
        merchantId = getIntent().getLongExtra("merchantId",-1l);
        myVoucherAdapter = new MyVoucherAdapter(R.layout.my_voucher_item, mActivity);
        listView.setAdapter(myVoucherAdapter);
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
                    GroupBuyingVoucherListModel voucherList = (GroupBuyingVoucherListModel) obj;
                    List<GroupBuyingVoucherListModel.ValueBean> listValue = voucherList.getValue();
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

}
