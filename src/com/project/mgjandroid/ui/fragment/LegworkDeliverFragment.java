package com.project.mgjandroid.ui.fragment;

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
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.ConfirmGroupOrModel;
import com.project.mgjandroid.model.ConfirmGroupOrderModel;
import com.project.mgjandroid.model.LegworkEntityModel;
import com.project.mgjandroid.model.LegworkOrderModel;
import com.project.mgjandroid.model.LegworkServiceChargeModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.AddressManageActivity;
import com.project.mgjandroid.ui.activity.BindMobileActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.SelectRedBagActivity;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.KeyboardStatusDetector;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/3/12 17:29
 */
public class LegworkDeliverFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.rl_select_take)
    private RelativeLayout rlSelectTake;
    @InjectView(R.id.rl_select_deliver)
    private RelativeLayout rlSelectDeliver;
    @InjectView(R.id.tv_take_address)
    private TextView tvTakeAddress;
    @InjectView(R.id.tv_take_name)
    private TextView tvTakeName;
    @InjectView(R.id.tv_deliver_address)
    private TextView tvDeliverAddress;
    @InjectView(R.id.tv_deliver_name)
    private TextView tvDeliverName;
    @InjectView(R.id.rg_legwork_service_time)
    private RadioGroup rgLegworkServiceTime;
    @InjectView(R.id.ll_legwork_select_time)
    private LinearLayout llLegworkSelectTime;
    @InjectView(R.id.et_legwork_desc)
    private EditText etLegworkDesc;
    @InjectView(R.id.sv_legwork_deliver)
    private ScrollView svLegworkDeliver;
    @InjectView(R.id.ll_legwork_desc)
    private LinearLayout llLegworkDesc;
    @InjectView(R.id.tv_legwork_price)
    private TextView tvLegworkPrice;
    @InjectView(R.id.tv_service_price)
    private TextView tvServicePrice;
    @InjectView(R.id.tv_select_time)
    private TextView tvSelectTime;
    @InjectView(R.id.ll_legwork_select_time)
    private LinearLayout llSelectTime;
    @InjectView(R.id.rb_legwork_immediately)
    private RadioButton rbLegworkImmediately;
    @InjectView(R.id.cb_legwork_protocol)
    private CheckBox cbLegworkProtocol;
    @InjectView(R.id.tv_legwork_protocol)
    private TextView tvLegworkProtocol;
    @InjectView(R.id.tv_legwork_go_pay)
    private TextView tvLegworkGoPay;
    @InjectView(R.id.tv_service_charge)
    private TextView tvServiceCharge;
    @InjectView(R.id.platform_redbag_layout)
    private RelativeLayout platform_redbag_layout;
    @InjectView(R.id.platform_num_textview)
    private TextView platform_num_textview;
    private RedBag redBag;

    public static final int SELECT_TAKE_ADDRESS = 301;
    public static final int SELECT_DELIVER_ADDRESS = 302;
    private UserAddress takeAddress;
    private UserAddress deliverAddress;
    private boolean isComputingSuccess;
    private long agentId;
    private DayListAdapter dayListAdapter;
    private TimeListAdapter timeListAdapter;
    private ListView timeListView;
    private Map<String, String> stringStringMap;
    private Dialog mTimeDialog;
    private String protocolUrl;
    private boolean business = true;
    private boolean isSpecifyTime = false;//是否指定时间
    private double price;
    private LegworkServiceChargeModel.ValueBean serviceChargeModel;
    private Dialog mServiceaDialog;
    private CallPhoneDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_legwork_deliver, container, false);
        InjectorFragment.get(this).inject(view);
        agentId = PreferenceUtils.getLongPreference("issueAgentId", -1, mActivity);
        inListener();
        initData();
        computingServicePrice();
        return view;
    }

    private void inListener() {
        rlSelectTake.setOnClickListener(this);
        rlSelectDeliver.setOnClickListener(this);
        tvLegworkProtocol.setOnClickListener(this);
        tvLegworkGoPay.setOnClickListener(this);
        tvServiceCharge.setOnClickListener(this);
        tvSelectTime.setOnClickListener(this);
        platform_redbag_layout.setOnClickListener(this);
        rgLegworkServiceTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_legwork_immediately:
                        isSpecifyTime = false;
                        stringStringMap = null;
                        llLegworkSelectTime.setVisibility(View.GONE);
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
        new KeyboardStatusDetector()
                .registerActivity(getActivity())
                .setVisibilityListener(new KeyboardStatusDetector.KeyboardVisibilityListener() {
                    @Override
                    public void onVisibilityChanged(boolean keyboardVisible) {
                        if (keyboardVisible) {
                            int top = llLegworkDesc.getTop();
                            svLegworkDeliver.smoothScrollTo(0, top);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_time:
                if (mTimeDialog != null && !mTimeDialog.isShowing()) {
                    mTimeDialog.show();
                }
                break;
            case R.id.rl_select_take:
                // 选择取货地
                if (CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, AddressManageActivity.class);
                    intent.putExtra("group", "group");
                    intent.putExtra("title", "取件地址");
                    startActivityForResult(intent, SELECT_TAKE_ADDRESS);
                }
                break;
            case R.id.rl_select_deliver:
                // 选择送货地
                if (CommonUtils.checkLogin(mActivity)) {
                    Intent intent2 = new Intent(mActivity, AddressManageActivity.class);
                    intent2.putExtra("group", "group");
                    int id = (int) agentId;
                    intent2.putExtra("agentId", id);
                    startActivityForResult(intent2, SELECT_DELIVER_ADDRESS);
                }
                break;
            case R.id.tv_legwork_protocol:
                if (TextUtils.isEmpty(protocolUrl)) {
                    return;
                }
                Intent intent3 = new Intent(mActivity, YLBWebViewActivity.class);
                intent3.putExtra(YLBSdkConstants.EXTRA_H5_URL, protocolUrl);
                startActivity(intent3);
                break;
            case R.id.tv_service_charge:
                // 服务费介绍
                if (serviceChargeModel != null) {
                    mPopupWindow(serviceChargeModel);
                }
                break;
            case R.id.tv_legwork_go_pay:
                // 去支付
                if (!business) {
                    ToastUtils.displayMsg("商家暂未营业", mActivity);
                    return;
                }
                if (takeAddress == null) {
                    ToastUtils.displayMsg("请选择取货地址", mActivity);
                    return;
                }
                if (deliverAddress == null) {
                    ToastUtils.displayMsg("请选择送货地址", mActivity);
                    return;
                }
                if (isSpecifyTime && stringStringMap == null) {
                    ToastUtils.displayMsg("请选择送达时间", mActivity);
                    return;
                }
                // 是否同意协议
                if (!cbLegworkProtocol.isChecked()) {
                    ToastUtils.displayMsg("请阅读并同意《跑腿代购协议》", mActivity);
                    return;
                }
                if (!isComputingSuccess) {
                    //价格未计算完毕
                    ToastUtils.displayMsg("正在计算服务费,请稍后", mActivity);
                    computingServicePrice();
                    return;
                }
                if (TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                    showDialog();
                    return;
                }
                createOrder();
                break;
            case R.id.layout_ok:
                if (mServiceaDialog != null) {
                    mServiceaDialog.dismiss();
                }
                break;
            case R.id.platform_redbag_layout:
                Intent intentSelect = new Intent(getActivity(), SelectRedBagActivity.class);
                intentSelect.putExtra(SelectRedBagActivity.ITEMS_PRICE, price);
                intentSelect.putExtra(SelectRedBagActivity.ADDRESS, deliverAddress);
                intentSelect.putExtra(SelectRedBagActivity.BUSINESS_TYPE, 9);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10002) {
            if (requestCode == SELECT_TAKE_ADDRESS) {
                if (data.getSerializableExtra("address") != null) {
                    takeAddress = (UserAddress) data.getSerializableExtra("address");
                    tvTakeAddress.setText(takeAddress.getAddress());
                    tvTakeAddress.setVisibility(View.VISIBLE);
                    tvTakeName.setText(takeAddress.getName() + "  " + takeAddress.getGender() + "  " + takeAddress.getMobile());
                    if (deliverAddress != null) {
                        //计算服务费
                        computingServicePrice();
                    }
                }
            } else if (requestCode == SELECT_DELIVER_ADDRESS) {
                if (data.getSerializableExtra("address") != null) {
                    deliverAddress = (UserAddress) data.getSerializableExtra("address");
                    tvDeliverAddress.setText(deliverAddress.getAddress());
                    tvDeliverAddress.setVisibility(View.VISIBLE);
                    tvDeliverName.setText(deliverAddress.getName() + "  " + deliverAddress.getGender() + "  " + deliverAddress.getMobile());
                    if (takeAddress != null) {
                        //计算服务费
                        computingServicePrice();
                    }
                }
            }
        }
        if (resultCode == SelectRedBagActivity.RED_BAG_MONEY){
            redBag = (RedBag) data.getSerializableExtra(SelectRedBagActivity.RED_MONEY_BAG);
            computingServicePrice();
        }
    }


    private void initData() {
        VolleyOperater<LegworkEntityModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("page", 2);
        operater.doRequest(Constants.URL_GET_LEGWORK_DATA, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    LegworkEntityModel model = (LegworkEntityModel) obj;
                    LegworkEntityModel.ValueBean value = model.getValue();
                    //是否营业
                    business = value.isBusiness();
                    tvLegworkGoPay.setEnabled(true);
                    //支付协议
                    protocolUrl = value.getTakeDeliveryServiceProtocolUrl();
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
        String desc = etLegworkDesc.getText().toString().trim();
        VolleyOperater<LegworkOrderModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("childType", 1); //0:代购，1:取送件
        map.put("userAddressId", deliverAddress.getId()); //收货地址编号
        map.put("shipperAddressId", takeAddress.getId()); //取货地址编号
        map.put("agentId", agentId);
        map.put("shipperType", 0); // 1：代购时就近购买；2：代购时指定地址，0：取送件的用户地址
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
        if (!TextUtils.isEmpty(desc)) {
            map.put("remark", desc); //取送件备注
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
        operater.doRequest(Constants.URL_CREATE_LEG_WORK_ORDER, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                tvLegworkGoPay.setEnabled(true);
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    LegworkOrderModel model = (LegworkOrderModel) obj;
                    Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                    intent.putExtra("orderId", model.getValue().getId());
                    intent.putExtra("agentId", model.getValue().getAgentId());
                    intent.putExtra("isLegwork", true);
                    startActivity(intent);
                }
            }
        }, LegworkOrderModel.class);
    }

    /**
     * 计算服务费
     */
    private void computingServicePrice() {
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
        if (deliverAddress != null) {
            map.put("userAddressId", deliverAddress.getId());
        } else {
            map.remove("userAddressId");
        }

        map.put("itemPrice", price);
        map.put("totalPrice", price);
        map.put("agentId", agentId);
        map.put("businessType", 9);
        map.put("redBags", JSONArray.toJSON(redBagList).toString());
        map.put("agentId", agentId);
        map.put("shipperType", 0); //1：代购时就近购买；2：代购时指定地址，0：取送件的用户地址
        if (isSpecifyTime && stringStringMap != null) {
            String time = "1";
            for (String s : stringStringMap.keySet()) {
                time = s;
            }
            map.put("expectArrivalTime", time);  // 1:立即送达;other:期望送到时间; 时间戳
        } else {
            map.put("expectArrivalTime", "1");  // 1:立即送达;other:期望送到时间; 时间戳
        }
        if (takeAddress != null && deliverAddress != null) {
            map.put("shipperAddressId", takeAddress.getId()); //取货地址编号
            map.put("userAddressId", deliverAddress.getId()); //送货地址编号
        }
        operater.doRequest(Constants.URL_CALCULATE_SERVICE_CHARGE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), getActivity());
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
                            platform_redbag_layout.setVisibility(View.VISIBLE);
                            platform_num_textview.setHint("有" + serviceChargeModel.getPlatformRedBagCount() + "个红包可用");
                        } else {
                            platform_redbag_layout.setVisibility(View.GONE);
                        }
                    }
                    price = serviceChargeModel.getTotalPrice().doubleValue();
                    tvServicePrice.setText("¥" + price);
                    // 添加总价
                    tvLegworkPrice.setText("费用：¥" + price);
                    isComputingSuccess = true;
                }
            }
        }, LegworkServiceChargeModel.class);
    }

    private void mPopupWindow(LegworkServiceChargeModel.ValueBean valueBean) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_service_charge, null);
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
        int width = getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth();
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
        LinearLayout view = (LinearLayout) View.inflate(mActivity, R.layout.popup_legwork_select_time, null);
        ListView dayListView = (ListView) view.findViewById(R.id.day_list_view);
        timeListView = (ListView) view.findViewById(R.id.time_list_view);

        dayListAdapter = new DayListAdapter(R.layout.item_legwork_day_list_view, mActivity);

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


        mTimeDialog = new Dialog(mActivity, R.style.MyDialogStyle);
        Window dialogWindow = mTimeDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        mTimeDialog.setContentView(view);
        dialogWindow.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
        mTimeDialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        int height = getActivity().getWindow().getWindowManager().getDefaultDisplay().getHeight();
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
