package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.ChangeOrReturnGoods;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChangeOrReturnModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/9/21.
 */
public class ApplyAdultsActivity extends BaseActivity {
    @InjectView(R.id.apply_adults_back)
    private ImageView back;
    @InjectView(R.id.apply_adults_commit)
    private TextView commit;
    @InjectView(R.id.apply_adults_merchant_logo)
    private RoundImageView merchantLogo;
    @InjectView(R.id.apply_adults_merchant_name)
    private TextView merchantName;
    @InjectView(R.id.apply_adults_order_num)
    private TextView orderNum;
    @InjectView(R.id.apply_adults_order_time)
    private TextView orderTime;
    @InjectView(R.id.apply_adults_goods_layout)
    private LinearLayout goodsLayout;
    @InjectView(R.id.apply_adults_address_detail)
    private TextView address;
    @InjectView(R.id.apply_adults_user_name)
    private TextView username;
    @InjectView(R.id.apply_adults_user_phone)
    private TextView userPhone;

    private SubmitOrderModel.ValueEntity submitOrderEntity;
    private ArrayList<ChangeOrReturnGoods> commitData = new ArrayList<>();
    private ChangeOrReturnModel model;

    private boolean canDeal = true;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_apply_adults);
        Injector.get(this).inject();
        initView();
        setListener();
    }

    private void setListener() {
        back.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    private void initView() {
        if (getIntent().hasExtra("submitOrderEntity")) {
            submitOrderEntity = (SubmitOrderModel.ValueEntity) getIntent().getSerializableExtra("submitOrderEntity");
        }
        if (submitOrderEntity == null) {
            onBackPressed();
            return;
        }
        setData();
        findStatus();
    }

    private void findStatus() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", submitOrderEntity.getId());
        VolleyOperater<ChangeOrReturnModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_RETURN_OR_CHANGE_STATUS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast("获取数据失败");
                        return;
                    }
                    model = (ChangeOrReturnModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue())) {
                        for (int i = 0; i < model.getValue().size(); i++) {
                            ChangeOrReturnModel.ValueEntity orderItem = model.getValue().get(i);
                            orderItem.setPosition(i);
                            View view = LayoutInflater.from(ApplyAdultsActivity.this).inflate(R.layout.item_back_goods, null);
                            TextView tvName = (TextView) view.findViewById(R.id.back_goods_name);
                            TextView tvCount = (TextView) view.findViewById(R.id.back_goods_count);
                            TextView tvApply = (TextView) view.findViewById(R.id.back_goods_apply);
                            if (!orderItem.isCanChange() && !orderItem.isCanReturn() && orderItem.getApplyStatus() == null) {
                                tvApply.setVisibility(View.INVISIBLE);
                            } else {
                                tvApply.setVisibility(View.VISIBLE);
                                //APPLY(0, "已申请"), ACCEPT(1, "处理中"), REJECT(-1, "驳回"), PROCESSING(2, "退换中"), DONE(3, "已退换"), FINISH(4, "已完成")
                                Integer status = orderItem.getApplyStatus();
                                if (status != null) {
                                    canDeal = false;
                                    switch (status) {
                                        case 0:
                                            tvApply.setText("已申请");
                                            break;
                                        case 1:
                                            tvApply.setText("处理中");
                                            break;
                                        case -1:
                                            tvApply.setText("驳回");
                                            break;
                                        case 2:
                                            tvApply.setText("退换中");
                                            break;
                                        case 3:
                                            tvApply.setText("已退换");
                                            break;
                                        case 4:
                                            tvApply.setText("已完成");
                                            break;
                                    }
                                    tvApply.setBackgroundResource(R.drawable.shap_org_range_bg);
                                    tvApply.setTextColor(mResource.getColor(R.color.org_yellow));
                                    commit.setVisibility(View.GONE);
                                } else {
                                    tvApply.setTag(orderItem);
                                    tvApply.setOnClickListener(ApplyAdultsActivity.this);
                                }
                            }
                            String spec = "";
                            if (!TextUtils.isEmpty(orderItem.getOrderItem().getSpec())) {
                                spec = " (" + orderItem.getOrderItem().getSpec() + ")";
                            }
                            tvName.setText(orderItem.getOrderItem().getName() + spec);
                            tvCount.setText("×" + orderItem.getOrderItem().getQuantity());
                            goodsLayout.addView(view);
                        }
                        if (!canDeal) {
                            for (int i = 0; i < model.getValue().size(); i++) {
                                ChangeOrReturnModel.ValueEntity orderItem = model.getValue().get(i);
                                Integer status = orderItem.getApplyStatus();
                                if (status == null) {
                                    TextView apply = (TextView) goodsLayout.getChildAt(i).findViewById(R.id.back_goods_apply);
                                    apply.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }
                }
            }
        }, ChangeOrReturnModel.class);
    }

    private void setData() {
        orderNum.setText("订单编号：" + submitOrderEntity.getId());
        orderTime.setText("下单时间：" + submitOrderEntity.getCreateTime());
        ImageUtils.loadBitmap(this, submitOrderEntity.getMerchant().getLogo(), merchantLogo, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        merchantName.setText(submitOrderEntity.getMerchant().getName());
        address.setText(submitOrderEntity.getUserAddress() + " " + submitOrderEntity.getUserHouseNumber());
        username.setText(submitOrderEntity.getUserName() + " " + submitOrderEntity.getUserGender());
        userPhone.setText(submitOrderEntity.getUserMobile());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 100:
                ChangeOrReturnGoods goods = (ChangeOrReturnGoods) data.getSerializableExtra("goods");
                if (CheckUtils.isEmptyList(commitData)) {
                    commitData.add(goods);
                } else {
                    for (ChangeOrReturnGoods good : commitData) {
                        if (good.getOrderItemId() == goods.getOrderItemId()) {
                            commitData.remove(good);
                            break;
                        }
                    }
                    commitData.add(goods);
                }
                TextView apply = (TextView) goodsLayout.getChildAt(goods.getPosition()).findViewById(R.id.back_goods_apply);
                apply.setText("修改申请");
                apply.setBackgroundResource(R.drawable.shap_org_range_bg);
                apply.setTextColor(mResource.getColor(R.color.org_yellow));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.apply_adults_back:
                onBackPressed();
                break;
            case R.id.back_goods_apply:
                if (!canDeal) {
                    break;
                }
                ChangeOrReturnModel.ValueEntity orderItem = (ChangeOrReturnModel.ValueEntity) v.getTag();
                Intent intent = new Intent(mActivity, ApplyChangeGoodsActivity.class);
                intent.putExtra("orderItem", orderItem);
                long orderItemId = orderItem.getOrderItem().getId();
                if (CheckUtils.isNoEmptyList(commitData)) {
                    for (ChangeOrReturnGoods goods : commitData) {
                        if (goods.getOrderItemId() == orderItemId) {
                            intent.putExtra("updateGoods", goods);
                            break;
                        }
                    }
                }
                startActivityForResult(intent, 1);
                break;
            case R.id.apply_adults_commit:
                if (CheckUtils.isEmptyList(commitData)) {
                    ToastUtils.displayMsg("您还未选择退换的商品", mActivity);
                    break;
                }
                commitInfo();
                break;
        }
    }

    private void commitInfo() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < commitData.size(); i++) {
            ChangeOrReturnGoods goods = commitData.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", goods.getOrderId());
            map.put("orderItemId", goods.getOrderItemId());
            map.put("imgs", goods.getImgs());
            map.put("amt", goods.getAmt());
            map.put("reason", goods.getReason());
            map.put("type", goods.getType());
            list.add(map);
        }
        JSON.toJSONString(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("applyListJsonString", JSON.toJSONString(list));
        VolleyOperater<ChangeOrReturnModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_APPLY_RETURN_OR_CHANGE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    toast("提交成功，请等待审核");
                    finish();
                }
            }
        }, ChangeOrReturnModel.class);
    }
}
