package com.project.mgjandroid.ui.activity.legwork;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.BaiduGeocoderModel;
import com.project.mgjandroid.model.ConfirmGroupOrModel;
import com.project.mgjandroid.model.ConfirmGroupOrderModel;
import com.project.mgjandroid.model.LegworkEntityModel;
import com.project.mgjandroid.model.LegworkOrderModel;
import com.project.mgjandroid.model.LegworkServiceChargeModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.AddressManageActivity;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.BindMobileActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.SelectRedBagActivity;
import com.project.mgjandroid.ui.activity.SetAddressActivity;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建代购订单
 */

public class LegworkWriteOrderActivity extends BaseActivity {

    @InjectView(R.id.et_legwork_desc)
    private EditText etLegworkDesc;
    @InjectView(R.id.rg_legwork_service_time)
    private RadioGroup rgLegworkServiceTime;
    @InjectView(R.id.rg_legwork_address)
    private RadioGroup rgLegworkAddress;
    @InjectView(R.id.ll_select_time)
    private LinearLayout llSelectTime;
    @InjectView(R.id.tv_select_time)
    private TextView tvSelectTime;
    @InjectView(R.id.fl_legwork)
    private FlowLayout flLegwork;
    @InjectView(R.id.common_back)
    private ImageView commonBack;
    @InjectView(R.id.rl_select_buy)
    private RelativeLayout rlSelectBuy;
    @InjectView(R.id.rl_select_deliver)
    private RelativeLayout rlSelectDeliver;
    @InjectView(R.id.tv_deliver_address)
    private TextView tvDeliverAddress;
    @InjectView(R.id.tv_deliver_name)
    private TextView tvDeliverName;
    @InjectView(R.id.tv_buy_address)
    private TextView tvBuyAddress;
    @InjectView(R.id.tv_service_price)
    private TextView tvServicePrice;
    @InjectView(R.id.tv_legwork_price)
    private TextView tvLegworkPrice;
    @InjectView(R.id.tv_legwork_go_pay)
    private TextView tvLegworkGoPay;
    @InjectView(R.id.et_estimate_price)
    private EditText etEstimatePrice;
    @InjectView(R.id.cb_legwork_protocol)
    private CheckBox cbLegworkProtocol;
    @InjectView(R.id.tv_legwork_protocol)
    private TextView tvLegworkProtocol;
    @InjectView(R.id.tv_service_charge)
    private TextView tvServiceCharge;
    @InjectView(R.id.rb_legwork_immediately)
    private RadioButton rbLegworkImmediately;
    @InjectView(R.id.et_detailed)
    private EditText etDetailed;
    @InjectView(R.id.view_line)
    private View viewLine;
    @InjectView(R.id.ll_address_detailed)
    private LinearLayout llDetailed;

    //平台红包
    @InjectView(R.id.platform_redbag_layout)
    private RelativeLayout platform_redbag_layout;
    @InjectView(R.id.platform_num_textview)
    private TextView platform_num_textview;
    private RedBag redBag;
    public static final int SELECT_TAKE_ADDRESS = 301;
    public static final int SELECT_DELIVER_ADDRESS = 302;
    public static final int GO_PAY = 221;
    private long agentId = 0;
    private String desc;
    private String parentId;
    private ListView dayListView;
    private ListView timeListView;
    private DayListAdapter dayListAdapter;
    private TimeListAdapter timeListAdapter;
    private boolean isSpecifyAddress = false;//是否指定地址
    private boolean isSpecifyTime = false;//是否指定时间
    private UserAddress userAddress;
    private String longitude;
    private String latitude;
    private double price;
    private boolean isComputingSuccess;
    private String protocolUrl;
    private boolean business = true;
    private Dialog mTimeDialog;
    private Map<String, String> stringStringMap;
    private LegworkServiceChargeModel.ValueBean serviceChargeModel;
    private Dialog mServiceaDialog;
    private CallPhoneDialog dialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_legwork_write_order);
        Injector.get(this).inject();
        agentId = PreferenceUtils.getLongPreference("issueAgentId", 0, mActivity);
        initData();
        inListener();

        //获取服务费
        computingServicePrice();
    }

    private void inListener() {
        tvSelectTime.setOnClickListener(this);
        commonBack.setOnClickListener(this);
        rlSelectBuy.setOnClickListener(this);
        rlSelectDeliver.setOnClickListener(this);
        tvLegworkGoPay.setOnClickListener(this);
        tvLegworkProtocol.setOnClickListener(this);
        tvServiceCharge.setOnClickListener(this);
        platform_redbag_layout.setOnClickListener(this);
        tvSelectTime.setOnClickListener(this);
        rgLegworkAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_legwork_nearby:
                        isSpecifyAddress = false;
                        rlSelectBuy.setVisibility(View.GONE);
                        llDetailed.setVisibility(View.GONE);
                        viewLine.setVisibility(View.GONE);
                        longitude = "";
                        latitude = "";
                        // 计算服务费
                        computingServicePrice();
                        break;
                    case R.id.rb_legwork_address:
                        isSpecifyAddress = true;
                        rlSelectBuy.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(tvBuyAddress.getText().toString())) {
                            llDetailed.setVisibility(View.VISIBLE);
                            viewLine.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });
        rgLegworkServiceTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_legwork_immediately:
                        isSpecifyTime = false;
                        stringStringMap = null;
                        llSelectTime.setVisibility(View.GONE);
                        // 计算服务费
                        computingServicePrice();
                        break;
                    case R.id.rb_legwork_time:
                        if (mTimeDialog != null && !mTimeDialog.isShowing()) {
                            mTimeDialog.show();
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_select_time:
                if (mTimeDialog != null && !mTimeDialog.isShowing()) {
                    mTimeDialog.show();
                }
                break;
            case R.id.common_back:
                finish();
                break;
            case R.id.rl_select_buy:
                startActivityForResult(new Intent(this, SetAddressActivity.class), SELECT_TAKE_ADDRESS);
                break;
            case R.id.rl_select_deliver:
                if (CommonUtils.checkLogin(this)) {
                    Intent intent = new Intent(this, AddressManageActivity.class);
                    intent.putExtra("group", "group");
                    int id = (int) agentId;
                    intent.putExtra("agentId", id);
                    intent.putExtra("type", 18);
                    startActivityForResult(intent, SELECT_DELIVER_ADDRESS);
                }
                break;
            case R.id.tv_legwork_protocol:
                // 服务协议
                if (TextUtils.isEmpty(protocolUrl)) {
                    return;
                }
                Intent intent2 = new Intent(mActivity, YLBWebViewActivity.class);
                intent2.putExtra(YLBSdkConstants.EXTRA_H5_URL, protocolUrl);
                startActivity(intent2);
                break;
            case R.id.tv_service_charge:
                // 计费规则
                Intent intent = new Intent(mActivity, LegworkbilingRulesActivity.class);
                intent.putExtra("serviceChargeModel",serviceChargeModel);
                startActivity(intent);
                break;
            case R.id.tv_legwork_go_pay:
                // 去支付
                desc = etLegworkDesc.getText().toString().trim();
                if (!business) {
                    toast("商家暂未营业");
                    return;
                }
                if (TextUtils.isEmpty(desc)) {
                    toast("请输入商品描述");
                    return;
                }
                if (userAddress == null) {
                    toast("请选择送货地址");
                    return;
                }
                if (isSpecifyAddress) {
                    if (TextUtils.isEmpty(longitude) || TextUtils.isEmpty(latitude)) {
                        toast("请选择购买地址");
                        return;
                    }
                }
                if (isSpecifyTime && stringStringMap == null) {
                    toast("请选择送达时间");
                    return;
                }
                // 是否同意协议
                if (!cbLegworkProtocol.isChecked()) {
                    toast("请阅读并同意《跑腿代购协议》");
                    return;
                }
                if (!isComputingSuccess) {
                    //价格未计算完毕
                    toast("正在计算服务费,请稍后");
                    computingServicePrice();
                    return;
                }
                if (TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                    showDialog();
                    return;
                }
                if (CommonUtils.checkLogin(this)) {
                    createOrder();
                }
                break;
            case R.id.layout_ok:
                if (mServiceaDialog != null) {
                    mServiceaDialog.dismiss();
                }
                break;
            case R.id.platform_redbag_layout:
                Intent intentSelect = new Intent(this, SelectRedBagActivity.class);
                intentSelect.putExtra(SelectRedBagActivity.ITEMS_PRICE, serviceChargeModel.getServiceCharge());
                intentSelect.putExtra(SelectRedBagActivity.ADDRESS, userAddress);
                intentSelect.putExtra(SelectRedBagActivity.BUSINESS_TYPE, 9);
                if (redBag != null) {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, redBag.getId());
                } else {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, -1l);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_DELIVER_ADDRESS) {
            if (resultCode == 10002) {
                if (data.getSerializableExtra("address") != null) {
                    userAddress = (UserAddress) data.getSerializableExtra("address");
                    tvDeliverAddress.setText(userAddress.getAddress());
                    tvDeliverAddress.setTextColor(getResources().getColor(R.color.color_3));
                    tvDeliverName.setVisibility(View.VISIBLE);
                    tvDeliverName.setText(userAddress.getName() + "  " + userAddress.getGender() + "  " + userAddress.getMobile());
                    // 计算服务费
                    computingServicePrice();
                }
            }
        } else if (requestCode == SELECT_TAKE_ADDRESS) {
            if (resultCode == RESULT_OK) {
                llDetailed.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                BaiduGeocoderModel.ResultBean.PoisBean poiInfo = (BaiduGeocoderModel.ResultBean.PoisBean) data.getSerializableExtra("POI_INFO");
                tvBuyAddress.setText(poiInfo.getAddr() + poiInfo.getName());
                tvBuyAddress.setTextColor(getResources().getColor(R.color.color_3));
                etLegworkDesc.requestFocus();//收起键盘
                longitude = "" + poiInfo.getPoint().getX();
                latitude = "" + poiInfo.getPoint().getY();
            } else if (resultCode == SetAddressActivity.SUGGESTION_RESULT) {
                llDetailed.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
                SuggestionResult.SuggestionInfo poiInfo = data.getParcelableExtra("POI_INFO");
                tvBuyAddress.setText(poiInfo.city + poiInfo.district + poiInfo.key);
                tvBuyAddress.setTextColor(getResources().getColor(R.color.color_3));
                etLegworkDesc.requestFocus();
                longitude = "" + poiInfo.pt.longitude;
                latitude = "" + poiInfo.pt.latitude;
            }
            // 计算服务费
            computingServicePrice();
        }
        if (resultCode == SelectRedBagActivity.RED_BAG_MONEY){
            redBag = (RedBag) data.getSerializableExtra(SelectRedBagActivity.RED_MONEY_BAG);
            computingServicePrice();
        }
    }

    private void initData() {
        Intent intent = getIntent();
        desc = intent.getStringExtra("desc");
        parentId = intent.getStringExtra("parentId");

        etLegworkDesc.setText(desc);
        etLegworkDesc.setSelection(etLegworkDesc.getText().length());
        VolleyOperater<LegworkEntityModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("page", 2);
        if (!TextUtils.isEmpty(parentId)) {
            map.put("parentId", parentId);
        }
        operater.doRequest(Constants.URL_GET_LEGWORK_DATA, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), LegworkWriteOrderActivity.this);
                        return;
                    }
                    LegworkEntityModel model = (LegworkEntityModel) obj;
                    LegworkEntityModel.ValueBean value = model.getValue();
                    if (value.getLegWorkGoodsCategoryList() != null && value.getLegWorkGoodsCategoryList().size() > 0) {
                        addTab(value.getLegWorkGoodsCategoryList());
                        flLegwork.setVisibility(View.VISIBLE);
                    }
                    //是否营业
                    business = value.isBusiness();
                    tvLegworkGoPay.setEnabled(true);
                    //支付协议
                    protocolUrl = value.getAgencyPurchasingServiceProtocolUrl();
                    //时间列表
                    List<LegworkEntityModel.ValueBean.DeliveryTimesResponseDTOListBean> deliveryTimesResponseDTOList = value.getDeliveryTimesResponseDTOList();
                    if (deliveryTimesResponseDTOList != null && deliveryTimesResponseDTOList.size() > 0) {
                        initBottomDialog(deliveryTimesResponseDTOList);
                    }
                }
            }
        }, LegworkEntityModel.class);
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        tvLegworkGoPay.setEnabled(false);
        //预估价格
        String trim = etEstimatePrice.getText().toString().trim();
        VolleyOperater<LegworkOrderModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("childType", 0); //0:代购，1:取送件
        map.put("userAddressId", userAddress.getId()); //收货地址编号
        map.put("agentId", agentId);
        map.put("shipperType", isSpecifyAddress ? 2 : 1); // 1：代购时就近购买；2：代购时指定地址，0：取送件的用户地址
        map.put("servePrice", serviceChargeModel.getServiceCharge()); //服务费
        map.put("totalPrice", price); //总金额
        if (redBag != null){
            ArrayList<Map<String, Object>> redBagList = new ArrayList<>();
            if (redBag != null) {
                Map<String, Object> redmap = new HashMap<>();
                redmap.put("id", redBag.getId());
                redmap.put("name", redBag.getName());
                redmap.put("amt", redBag.getAmt());
                redmap.put("promotionType", redBag.getPromotionType());
                redBagList.add(redmap);
            }
            map.put("redBags", JSONArray.toJSON(redBagList).toString());
        }
        if (isSpecifyTime && stringStringMap != null) {
            String time = "1";
            for (String s : stringStringMap.keySet()) {
                time = s;
            }
            map.put("expectArrivalTime", time);
        } else {
            map.put("expectArrivalTime", "1"); //1:立即送达;other:期望送到时间; 时间戳
        }
        if (isSpecifyAddress) {
            map.put("shipperAddress", tvBuyAddress.getText().toString()); //地图选择地址名称
            map.put("shipperLongitude", longitude);
            map.put("shipperLatitude", latitude);
            String s = etDetailed.getText().toString().trim();
            map.put("shipperHouseNumber", TextUtils.isEmpty(s) ? "" : s);
        }
        if (!TextUtils.isEmpty(trim)) {
            map.put("goodsEstimatePrice", trim);//预估价格
        }
        map.put("description", desc); //代购时购买物品描述
        operater.doRequest(Constants.URL_CREATE_LEG_WORK_ORDER, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                tvLegworkGoPay.setEnabled(true);
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    LegworkOrderModel model = (LegworkOrderModel) obj;
                    Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                    intent.putExtra("orderId", model.getValue().getId());
                    intent.putExtra("agentId", model.getValue().getAgentId());
                    intent.putExtra("isLegwork", true);
                    startActivityForResult(intent, GO_PAY);

                    finish();
                }
            }
        }, LegworkOrderModel.class);
    }

    /**
     * 计算服务费
     */
    private void computingServicePrice() {
        if (!App.isLogin()){
            return;
        }
        isComputingSuccess = false;
        VolleyOperater<LegworkServiceChargeModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> redBagList = new ArrayList<>();
        if (redBag != null) {
            Map<String, Object> redmap = new HashMap<>();
            redmap.put("id", redBag.getId());
            redmap.put("name", redBag.getName());
            redmap.put("amt", redBag.getAmt());
            redmap.put("promotionType", redBag.getPromotionType());
            redBagList.add(redmap);
        }
        if (userAddress != null) {
            map.put("userAddressId", userAddress.getId());
        } else {
            map.remove("userAddressId");
        }

        map.put("itemPrice", price);
        map.put("totalPrice", price);
        map.put("agentId", agentId);
        map.put("businessType", 9);
        map.put("redBags", JSONArray.toJSON(redBagList).toString());
        map.put("agentId", agentId);
        if (isSpecifyTime && stringStringMap != null) {
            String time = "1";
            for (String s : stringStringMap.keySet()) {
                time = s;
            }
            map.put("expectArrivalTime", time);  // 1:立即送达;other:期望送到时间; 时间戳
        } else {
            map.put("expectArrivalTime", "1");  // 1:立即送达;other:期望送到时间; 时间戳
        }
        if (isSpecifyAddress) {
            //指定地址
            if (userAddress != null && !TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(latitude)) {
                map.put("userAddressId", userAddress.getId()); //收货地址编号
                map.put("shipperLongitude", longitude); //指定地址经度
                map.put("shipperLatitude", latitude); //指定地址纬度
                map.put("shipperType", 2); //1：代购时就近购买；2：代购时指定地址，0：取送件的用户地址
            } else {
                map.put("shipperType", 1); //1：代购时就近购买；2：代购时指定地址，0：取送件的用户地址
            }
        } else {
            //指定地址
            if (userAddress != null) {
                map.put("userAddressId", userAddress.getId()); //收货地址编号
            }
            map.put("shipperType", 1); //1：代购时就近购买；2：代购时指定地址，0：取送件的用户地址
        }
        operater.doRequest(Constants.URL_CALCULATE_SERVICE_CHARGE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    LegworkServiceChargeModel model = (LegworkServiceChargeModel) obj;
                    serviceChargeModel = model.getValue();
                    if (redBag != null) {
                        platform_redbag_layout.setVisibility(View.VISIBLE);
                        platform_num_textview.setText("-￥" + StringUtils.BigDecimal2Str(redBag.getAmt()));
                    } else {
                        platform_num_textview.setText("");
                        if (serviceChargeModel.getPlatformRedBagCount() > 0) {
                            platform_num_textview.setHintTextColor(getResources().getColor(R.color.platform_color));
                            platform_num_textview.setHint("有" + serviceChargeModel.getPlatformRedBagCount() + "个红包可用");
                        } else {
                            platform_num_textview.setHintTextColor(getResources().getColor(R.color.color_6));
                            platform_num_textview.setHint("无可用红包");
                        }
                    }
                    price = serviceChargeModel.getTotalPrice().doubleValue();
                    tvServicePrice.setText("¥" + serviceChargeModel.getServiceCharge());
                    // 添加总价
                    tvLegworkPrice.setText("费用：¥" + price);
                    isComputingSuccess = true;

                }
            }
        }, LegworkServiceChargeModel.class);
    }


    private void addTab(final List<LegworkEntityModel.ValueBean.LegWorkGoodsCategoryListBean> legWorkGoodsCategoryList) {
        flLegwork.removeAllViews();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DipToPx.dip2px(mActivity, 7), DipToPx.dip2px(mActivity, 5), DipToPx.dip2px(mActivity, 7), DipToPx.dip2px(mActivity, 5));// 设置边距
        for (int i = 0; i < legWorkGoodsCategoryList.size(); i++) {
            final TextView textView = new TextView(mActivity);
            textView.setTag(i);
            textView.setTextSize(14);
            textView.setText(legWorkGoodsCategoryList.get(i).getName());
            textView.setPadding(DipToPx.dip2px(mActivity, 10), DipToPx.dip2px(mActivity, 5), DipToPx.dip2px(mActivity, 10), DipToPx.dip2px(mActivity, 5));
            textView.setTextColor(getResources().getColor(R.color.color_6));
            textView.setBackgroundResource(R.drawable.legwork_tab_bg_normal);
            flLegwork.addView(textView, layoutParams);
            // 标签点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = legWorkGoodsCategoryList.get((int) v.getTag()).getName();
                    String desc = etLegworkDesc.getText().toString().trim();
                    etLegworkDesc.setText(TextUtils.isEmpty(desc) ? name : desc + "，" + name);
                    etLegworkDesc.setSelection(etLegworkDesc.getText().length());
                }
            });
        }
    }

    private void mPopupWindow(LegworkServiceChargeModel.ValueBean valueBean) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_service_charge, null);
        RelativeLayout lyoutOk = (RelativeLayout) view.findViewById(R.id.layout_ok);
        TextView tvBaseCharge = (TextView) view.findViewById(R.id.tv_baseCharge);
        TextView tvAddCharge = (TextView) view.findViewById(R.id.tv_addCharge);
        TextView tvStart = (TextView) view.findViewById(R.id.tv_start);
        TextView tvAdd = (TextView) view.findViewById(R.id.tv_add);
        lyoutOk.setOnClickListener(this);
        tvBaseCharge.setText("¥" + valueBean.getBaseCharge());
        tvAddCharge.setText("¥" + valueBean.getAddCharge());
        tvStart.setText("起始价（" + valueBean.getDefDistance() + "公里）");
        tvAdd.setText("超出" + valueBean.getDefDistance() + "公里（每公里）加价");

        mServiceaDialog = new Dialog(mActivity, R.style.MyDialogStyle);
        mServiceaDialog.setContentView(view);
        mServiceaDialog.setCanceledOnTouchOutside(true);
        Window dialogWindow = mServiceaDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        int width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        lp.width = width - 200;
        dialogWindow.setAttributes(lp);
        mServiceaDialog.show();
    }


    private int dayPosition = 0;
    private int timePosition = 0;

    /**
     * 底部弹框
     */
    private void initBottomDialog(final List<LegworkEntityModel.ValueBean.DeliveryTimesResponseDTOListBean> deliveryTimesResponseDTOList) {
        LinearLayout view = (LinearLayout) View.inflate(this, R.layout.popup_legwork_select_time, null);
        dayListView = (ListView) view.findViewById(R.id.day_list_view);
        timeListView = (ListView) view.findViewById(R.id.time_list_view);

        dayListAdapter = new DayListAdapter(R.layout.item_legwork_day_list_view, this);

        dayListAdapter.setData(deliveryTimesResponseDTOList);
        dayListView.setAdapter(dayListAdapter);

        timeListAdapter = new TimeListAdapter();
        timeListAdapter.setData(deliveryTimesResponseDTOList.get(dayPosition).getTimes());
        timeListView.setAdapter(timeListAdapter);

        dayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dayPosition = position;
                timePosition = 0;
                dayListAdapter.notifyDataSetChanged();
                timeListAdapter.setData(deliveryTimesResponseDTOList.get(dayPosition).getTimes());
                timeListAdapter.notifyDataSetChanged();
                timeListView.setSelectionAfterHeaderView();
            }
        });
        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timePosition = position;
                timeListAdapter.notifyDataSetChanged();
                // 选择完时间
                stringStringMap = timeListAdapter.getData().get(position);

                if (stringStringMap != null) {
                    String time = "";
                    for (String s : stringStringMap.keySet()) {
                        time = stringStringMap.get(s);
                    }
                    tvSelectTime.setText(deliveryTimesResponseDTOList.get(dayPosition).getDay() + " " + time + "预约送达");
                    isSpecifyTime = true;
                    llSelectTime.setVisibility(View.VISIBLE);
                    //请求服务费
                    computingServicePrice();
                    mTimeDialog.dismiss();
                }
            }
        });


        mTimeDialog = new Dialog(this, R.style.MyDialogStyle);
        Window dialogWindow = mTimeDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        mTimeDialog.setContentView(view);
        dialogWindow.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
        mTimeDialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        int height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        lp.height = height / 5 * 2;
        dialogWindow.setAttributes(lp);

        mTimeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (!isSpecifyTime && stringStringMap == null) {
                    //恢复立即配送选择
                    rbLegworkImmediately.setChecked(true);
                }
            }
        });

    }

    public class DayListAdapter extends BaseListAdapter<LegworkEntityModel.ValueBean.DeliveryTimesResponseDTOListBean> {

        public DayListAdapter(int layoutId, Activity mActivity) {
            super(layoutId, mActivity);
        }

        @Override
        protected void getRealView(ViewHolder holder, LegworkEntityModel.ValueBean.DeliveryTimesResponseDTOListBean bean, int position, View convertView, ViewGroup parent) {
            TextView tv_name = holder.getView(R.id.item_list_text);
            tv_name.setText(bean.getDay());
            if (position != dayPosition) {
                tv_name.setBackgroundColor(getResources().getColor(R.color.color_f5));
            } else {
                tv_name.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
    }

    public class TimeListAdapter extends BaseAdapter {

        List<Map<String, String>> data = new ArrayList<>();

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = View.inflate(mActivity, R.layout.item_legwork_time_list_view, null);
            TextView tvTime = (TextView) inflate.findViewById(R.id.item_list_text);
            ImageView ivCheck = (ImageView) inflate.findViewById(R.id.iv_check);
            String time = "";
            for (String s : getItem(i).keySet()) {
                time = getItem(i).get(s);
            }
            tvTime.setText(time);
            if (i == timePosition) {
                ivCheck.setVisibility(View.VISIBLE);
            } else {
                ivCheck.setVisibility(View.INVISIBLE);
            }
            return inflate;
        }

        public void setData(List<Map<String, String>> data) {
            this.data.clear();
            this.data.addAll(data);
        }

        public List<Map<String, String>> getData() {
            return data;
        }
    }
}
