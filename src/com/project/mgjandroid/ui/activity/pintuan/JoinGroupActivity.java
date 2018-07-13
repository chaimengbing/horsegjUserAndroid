package com.project.mgjandroid.ui.activity.pintuan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.mzule.activityrouter.annotation.Router;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.GroupInfo;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmGroupOrModel;
import com.project.mgjandroid.model.ConfirmGroupOrderModel;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.JoinGroupModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.AddressManageActivity;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.BindMobileActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.SelectRedBagActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/8/12.
 */
@Router("joinGroup")
public class JoinGroupActivity extends BaseActivity {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.top_address_panel)
    private RelativeLayout rl_addressPanel;
    @InjectView(R.id.top_address_tips)
    private TextView tv_noAddressTips;
    @InjectView(R.id.top_address)
    private RelativeLayout rlAddress;
    @InjectView(R.id.address_name)
    private TextView tv_name;
    @InjectView(R.id.address_sex)
    private TextView tv_sex;
    @InjectView(R.id.address_mobile)
    private TextView tv_mobile;
    @InjectView(R.id.address_description)
    private TextView tv_address;
    @InjectView(R.id.join_group_image)
    private CornerImageView groupImage;
    @InjectView(R.id.join_group_name)
    private TextView groupName;
    @InjectView(R.id.join_group_price)
    private TextView groupPrice;
    @InjectView(R.id.join_group_minus)
    private ImageView minusCount;
    @InjectView(R.id.join_group_add)
    private ImageView addCount;
    @InjectView(R.id.join_group_count)
    private TextView tvCount;
    @InjectView(R.id.join_group_tips)
    private EditText etTips;
    @InjectView(R.id.join_group_total_price)
    private TextView tvTotalPrice;
    @InjectView(R.id.join_group_use_balance_status)
    private ImageView ivUseBalance;
    @InjectView(R.id.join_group_use_balance_layout)
    private LinearLayout llUseBalance;
    @InjectView(R.id.join_group_balance)
    private TextView tvBalance;
    @InjectView(R.id.join_group_submit_order)
    private TextView tvSubmitOrder;
    @InjectView(R.id.platform_redbag_layout)
    private RelativeLayout platform_redbag_layout;
    @InjectView(R.id.platform_num_textview)
    private TextView platform_num_textview;
    @InjectView(R.id.divier)
    private View divier;

    private GroupInfo group;
    private final int REQUEST_GET_ADDRESS = 101;
    private final int RESPONSE_GET_ADDRESS = 10002;
    private UserAddress userAddress;
    private Double latitude;
    private Double longitude;
    private int currentCount = 1;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private boolean useBalance = false;
    private MLoadingDialog mLoadingDialog;
    private String errorMsg;
    private JSONObject data;
    private String orderId;
    private CallPhoneDialog dialog;

    private RedBag redBag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        Injector.get(this).inject();

        initView();
        setListener();
        getOrderPreview();
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        rlAddress.setOnClickListener(this);
        minusCount.setOnClickListener(this);
        addCount.setOnClickListener(this);
        llUseBalance.setOnClickListener(this);
        tvSubmitOrder.setOnClickListener(this);
        platform_redbag_layout.setOnClickListener(this);
    }

    private void initView() {
        tvTitle.setText("提交订单");
        if (getIntent().hasExtra("group") && getIntent().hasExtra("address")) {
            group = (GroupInfo) getIntent().getSerializableExtra("group");
            userAddress = (UserAddress) getIntent().getSerializableExtra("address");
            tv_noAddressTips.setVisibility(View.INVISIBLE);
            rl_addressPanel.setVisibility(View.VISIBLE);
            setData(group);
            showAddress(userAddress);
        } else {
            ToastUtils.displayMsg("数据错误，请重新尝试", mActivity);
            finish();
        }
        mLoadingDialog = new MLoadingDialog();
    }

    private void setData(GroupInfo group) {
        if (CheckUtils.isNoEmptyStr(group.getImgs())) {
            ImageUtils.loadBitmap(mActivity, group.getImgs().split(";")[0], groupImage, R.drawable.horsegj_default, Constants.getEndThumbnail(94, 94));
        }
        groupName.setText(group.getGoodsName());
        groupPrice.setText("¥" + group.getPrice());
        price = group.getPrice();
        totalPrice = price.multiply(new BigDecimal(Integer.toString(currentCount)));
        tvTotalPrice.setText("总计 ¥" + totalPrice);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.top_address:
                Intent intent = new Intent(this, AddressManageActivity.class);
                if (userAddress != null) {
                    intent.putExtra("USER_ADDRESS_ID", userAddress.getId());
                }
                intent.putExtra("group", "group");
                intent.putExtra("agentId", group.getAgentId());
                startActivityForResult(intent, REQUEST_GET_ADDRESS);
                break;
            case R.id.join_group_add:
                minusCount.setImageResource(R.drawable.min_group_goods);
                currentCount++;
                tvCount.setText(currentCount + "");
                totalPrice = price.multiply(new BigDecimal(Integer.toString(currentCount)));
                tvTotalPrice.setText("总计 ¥" + totalPrice);
                break;
            case R.id.join_group_minus:
                if (currentCount == 1) {
                    break;
                }
                currentCount--;
                if (currentCount == 1) {
                    minusCount.setImageResource(R.drawable.min_group_goods_gray);
                }
                tvCount.setText(currentCount + "");
                totalPrice = price.multiply(new BigDecimal(Integer.toString(currentCount)));
                tvTotalPrice.setText("总计 ¥" + totalPrice);
                break;
            case R.id.join_group_use_balance_layout:
                if (useBalance) {
                    useBalance = false;
                    ivUseBalance.setImageResource(R.drawable.service_status_unchecked);
                } else {
                    useBalance = true;
                    ivUseBalance.setImageResource(R.drawable.service_status_checked);
                }
                break;
            case R.id.join_group_submit_order:
                if (userAddress == null) {
                    ToastUtils.displayMsg("请选择配送地址", mActivity);
                    break;
                }
                if (TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                    showDialog();
                    return;
                }
                submitOrder();
                break;
            case R.id.platform_redbag_layout:
                Intent intentSelect = new Intent(this, SelectRedBagActivity.class);
                intentSelect.putExtra(SelectRedBagActivity.ITEMS_PRICE, totalPrice);
                intentSelect.putExtra(SelectRedBagActivity.ADDRESS, userAddress);
                intentSelect.putExtra(SelectRedBagActivity.BUSINESS_TYPE, 2);
                intentSelect.putExtra(SelectRedBagActivity.QUANTITY, currentCount);
                if (redBag != null) {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, redBag.getId());
                } else {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, -1);
                }
                startActivityForResult(intentSelect, 1111);
                break;
        }
    }

    public void showDialog() {
        if (dialog == null) {
            dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    Intent intent = new Intent(mActivity, BindMobileActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }

                @Override
                public void onExit() {
                    dialog.dismiss();
                }
            }, "", "请绑定您的手机号，以完成下单！", "立刻去绑定", "残忍拒绝", "#ff9a00", "#333333");
        }
        dialog.show();
    }

    private void submitOrder() {
        mLoadingDialog.show(getFragmentManager(), "");
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        if (App.isLogin()) {
            map.put("loginToken", App.getUserInfo().getToken());
            map.put("userId", App.getUserInfo().getId());
        }
        if (userAddress != null) {
            map.put("userAddressId", userAddress.getId());
        } else {
            map.remove("userAddressId");
        }
        map.put("groupBuyId", group.getId());
        map.put("agentId", group.getAgentId());
        if (CheckUtils.isNoEmptyStr(etTips.getText().toString().trim())) {
            map.put("caution", etTips.getText().toString().trim());
        }
        map.put("originPrice", group.getOriginalPrice());
        map.put("price", group.getPrice());
        map.put("totalPrice", totalPrice);
        map.put("quantity", currentCount);
        ArrayList<Map<String, Object>> redBagList = new ArrayList<>();
        if (redBag != null) {
            Map<String, Object> redmap = new HashMap<>();
            redmap.put("id", redBag.getId());
            redmap.put("name", redBag.getName());
            redmap.put("amt", redBag.getAmt());
            redmap.put("promotionType", redBag.getPromotionType());
            redBagList.add(redmap);
        }
        map.put("redBags", redBagList);
        data = new JSONObject(map);
        params.put("data", data.toString());

        VolleyOperater<JoinGroupModel> operater = new VolleyOperater<>(JoinGroupActivity.this);
        operater.doRequest(Constants.URL_GROUP_ORDER_SUBMIT, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        errorMsg = (String) obj;
                        ToastUtils.displayMsg(errorMsg, mActivity);
                    } else {
                        errorMsg = null;
                        JoinGroupModel model = (JoinGroupModel) obj;
                        if (model.getValue().getType() == 2) {
                            orderId = model.getValue().getGroupbuyOrder().getId();
                            Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("isGroup", true);
                            startActivity(intent);
                            JoinGroupActivity.this.finish();
                        }
                    }
                }
            }
        }, JoinGroupModel.class);
    }


    /**
     * 订单预览刷新
     */
    private void getOrderPreview() {
        divier.setVisibility(View.GONE);
        mLoadingDialog.show(getFragmentManager(), "");
        ArrayList<Map<String, Object>> redBagList = new ArrayList<>();
        if (redBag != null) {
            Map<String, Object> redmap = new HashMap<>();
            redmap.put("id", redBag.getId());
            redmap.put("name", redBag.getName());
            redmap.put("amt", redBag.getAmt());
            redmap.put("promotionType", redBag.getPromotionType());
            redBagList.add(redmap);
        }
        Map<String, Object> params = new HashMap<>();
        if (userAddress != null) {
            params.put("userAddressId", userAddress.getId());
        } else {
            params.remove("userAddressId");
        }

        params.put("itemPrice", group.getPrice());
        params.put("totalPrice", totalPrice);
        params.put("quantity", currentCount);
        params.put("agentId", group.getAgentId());
        params.put("businessType", 2);
        params.put("redBags", JSONArray.toJSON(redBagList).toString());
        VolleyOperater<ConfirmGroupOrderModel> operater = new VolleyOperater<>(this);
        String url = Constants.URL_GET_REDBAG_SETTING;
        operater.doRequest(url, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        errorMsg = (String) obj;
                        ToastUtils.displayMsg(errorMsg, mActivity);
                    } else {
                        errorMsg = null;
                    }
                    ConfirmGroupOrModel confirmGroupOrModel = ((ConfirmGroupOrderModel) obj).getValue();
                    setViewData(confirmGroupOrModel);
                }
            }
        }, ConfirmGroupOrderModel.class);
    }

    private void setViewData(ConfirmGroupOrModel confirmGroupOrModel) {
        if (confirmGroupOrModel != null) {
//            if (confirmGroupOrModel.getPlatformRedBagList() != null && confirmGroupOrModel.getPlatformRedBagList().size() > 0) {
//                String money = StringUtils.BigDecimal2Str(confirmGroupOrModel.getPlatformRedBagList().get(0).getAmt());
////                if (CheckUtils.isNoEmptyStr(money)) {
////                    platform_num_textview.setText("-￥" + String.valueOf(money));
////                }
////            } else {
////                platform_num_textview.setText("");
////            }
            if (confirmGroupOrModel.getRedBagsTotalAmt() != null && confirmGroupOrModel.getRedBagsTotalAmt().compareTo(BigDecimal.ZERO) == 1) {
                platform_num_textview.setText("-￥" + StringUtils.BigDecimal2Str(confirmGroupOrModel.getRedBagsTotalAmt()));
            } else {
                platform_num_textview.setText("");
            }
            if (confirmGroupOrModel.getPlatformRedBagCount() > 0) {
                platform_redbag_layout.setVisibility(View.VISIBLE);
                divier.setVisibility(View.VISIBLE);
                platform_num_textview.setHint("有" + confirmGroupOrModel.getPlatformRedBagCount() + "个红包可用");
            } else {
                platform_redbag_layout.setVisibility(View.GONE);
                divier.setVisibility(View.GONE);
            }
            totalPrice = confirmGroupOrModel.getTotalPrice();
            tvTotalPrice.setText("总计 ¥" + totalPrice);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GET_ADDRESS:
                if (data != null) {
                    userAddress = (UserAddress) data.getSerializableExtra("address");
                    if (userAddress != null) {
                        tv_noAddressTips.setVisibility(View.INVISIBLE);
                        latitude = userAddress.getLatitude();
                        longitude = userAddress.getLongitude();
                        rl_addressPanel.setVisibility(View.VISIBLE);
                        showAddress(userAddress);
                    } else {
                        showAddress(userAddress);
                    }
                }
                getOrderPreview();
                break;
        }
        switch (resultCode) {
            case SelectRedBagActivity.RED_BAG_MONEY:
                redBag = (RedBag) data.getSerializableExtra(SelectRedBagActivity.RED_MONEY_BAG);
                getOrderPreview();
                break;
            default:
                break;
        }
    }

    private void showAddress(UserAddress userAddress) {
        if (userAddress == null) {
            tv_noAddressTips.setVisibility(View.VISIBLE);
            rl_addressPanel.setVisibility(View.GONE);
            return;
        }
        tv_name.setText(userAddress.getName());
        tv_sex.setText(userAddress.getGender());
        tv_mobile.setText(userAddress.getMobile());
        tv_address.setText(userAddress.getAddress());
    }
}
