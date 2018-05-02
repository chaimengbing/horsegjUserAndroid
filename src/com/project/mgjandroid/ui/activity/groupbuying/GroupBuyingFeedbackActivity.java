package com.project.mgjandroid.ui.activity.groupbuying;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingComplainListModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingComplainModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

import static com.project.mgjandroid.R.id.common_back;
import static com.project.mgjandroid.R.id.tv_feed_back_submit;

/**
 * Created by SunXueLiang on 2017-03-08.
 */

public class GroupBuyingFeedbackActivity extends BaseActivity {

    @InjectView(common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.et_feed_back_content)
    private EditText etFeedBackContent;
    @InjectView(tv_feed_back_submit)
    private TextView tvFeedBackSubmit;
    private GroupPurchaseOrder purchaseOrder;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_feed_back);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        purchaseOrder = (GroupPurchaseOrder) getIntent().getSerializableExtra("groupPurchaseOrder");
        if (purchaseOrder == null) {
            finish();
            return;
        }
        ivBack.setOnClickListener(this);
        tvTitle.setText("反馈");
        tvFeedBackSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case common_back:
                onBackPressed();
                return;
            case tv_feed_back_submit:
                if (checkCanSubmit()) {
                    submit();
                }
                return;
        }
        super.onClick(v);
    }

    private boolean checkCanSubmit() {
        if (TextUtils.isEmpty(etFeedBackContent.getText().toString().trim())) {
            ToastUtils.displayMsg("请描述一下您遇到的问题", mActivity);
            return false;
        }

        return true;
    }

    private void submit() {
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", purchaseOrder.getAgentId());
        map.put("merchantId", purchaseOrder.getMerchantId());
        map.put("groupPurchaseCouponType", purchaseOrder.getGroupPurchaseCouponType());
        map.put("content", etFeedBackContent.getText().toString().trim());
        VolleyOperater<GroupBuyingComplainModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_GROUP_PURCHASE_COMPLAIN, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    GroupBuyingComplainModel model = (GroupBuyingComplainModel) obj;
                    if (model.getCode() == 0) {
                        toast("已提交");
                        finish();
                    }
                }

            }
        }, GroupBuyingComplainModel.class);
    }
}
