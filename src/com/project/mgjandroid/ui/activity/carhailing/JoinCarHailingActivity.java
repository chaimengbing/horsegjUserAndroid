package com.project.mgjandroid.ui.activity.carhailing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.OrderPromoResponse;
import com.project.mgjandroid.bean.PromotionActivity;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.carhailing.CarHailing;
import com.project.mgjandroid.bean.carhailing.CarHailingTrip;
import com.project.mgjandroid.bean.carhailing.CarHailingTripPlace;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.carhailing.CarHailingDetailModel;
import com.project.mgjandroid.model.carhailing.CarHailingSubmitOrderModel;
import com.project.mgjandroid.model.carhailing.UserCarUsablePromotionActivityModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.BindMobileActivity;
import com.project.mgjandroid.ui.activity.MyRedBagActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
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
 * Created by User_Cjh on 2016/12/7.
 */
public class JoinCarHailingActivity extends BaseActivity {


    private static final int REQUEST_CHOOSE_RED_BAG = 1001;

    @InjectView(R.id.photo_back)
    private ImageView ivBack;
    @InjectView(R.id.join_car_hailing_leave_time)
    private TextView tvLeaveTime;
    @InjectView(R.id.join_car_hailing_get_on_place)
    private TextView tvGetOn;
    @InjectView(R.id.join_car_hailing_get_off_place)
    private TextView tvGetOff;
    @InjectView(R.id.join_car_hailing_get_on_place_btn)
    private TextView tvGetOnBtn;
    @InjectView(R.id.join_car_hailing_get_off_place_btn)
    private TextView tvGetOffBtn;
    @InjectView(R.id.join_car_hailing_get_on_place_detail)
    private TextView tvGetOnDetail;
    @InjectView(R.id.join_car_hailing_get_off_place_detail)
    private TextView tvGetOffDetail;
    @InjectView(R.id.join_car_hailing_select_count)
    private TextView tvSelectCount;
    @InjectView(R.id.join_car_hailing_price)
    private TextView tvPrice;
    @InjectView(R.id.join_car_hailing_price_show)
    private TextView tvPriceShow;
    @InjectView(R.id.pay_join_car_hailing)
    private TextView tvToPay;
    @InjectView(R.id.promotion_layout)
    private LinearLayout promotionLayout;
    @InjectView(R.id.tv_redbag_layout)
    private FrameLayout redbagLayout;
    @InjectView(R.id.tv_redbag)
    private TextView tvRedbag;

    private boolean hasStart, hasEnd;
    private PopupWindow payPopupWindow;
    private int type;

    private List<CarHailingTripPlace> getOnPlace, getOffPlace, select;
    private long chauffeurId;
    private long chauffeurTripId;
    private long startCarTripDetailId = -1;
    private long endCarTripDetailId = -1;
    private int leavingSeat = 0;
    private int peopleCount = 0;
    private long chauffeurTripDetailId;
    private CarHailingTrip carHailing;
    private BigDecimal price;
    private String startAddress, endAddress;
    private MLoadingDialog mLoadingDialog;
    private CallPhoneDialog dialog;

    private ArrayList<OrderPromoResponse> orderPromoResponses;
    private RedBag redBag;
    private int redBagCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_car_hailing);
        Injector.get(this).inject();
        initView();
        setLinstener();
        getCallCarDataAvailable();
    }

    private void setLinstener() {
        ivBack.setOnClickListener(this);
        tvGetOnBtn.setOnClickListener(this);
        tvGetOffBtn.setOnClickListener(this);
        tvSelectCount.setOnClickListener(this);
        tvToPay.setOnClickListener(this);
        redbagLayout.setOnClickListener(this);
    }

    private void initView() {
        carHailing = (CarHailingTrip) getIntent().getSerializableExtra("carHailingTrip");
        if (null == carHailing) {
            finish();
            return;
        }
        mLoadingDialog = new MLoadingDialog();
        setView(carHailing);
    }

    private void setView(CarHailingTrip carHailing) {
        tvLeaveTime.setText(CommonUtils.getTime(carHailing.getLeaveTime(), carHailing.getServiceTime()));
        tvGetOn.setText(carHailing.getStartCityName() + carHailing.getStartDistrictName());
        tvGetOff.setText(carHailing.getEndCityName() + carHailing.getEndDistrictName());
        BigDecimal minPrice = carHailing.getChauffeurTripDetailList().get(0).getPrice();
        for (CarHailing carHailingTrip : carHailing.getChauffeurTripDetailList()) {
            if (minPrice.compareTo(carHailingTrip.getPrice()) > 0) {
                minPrice = carHailingTrip.getPrice();
            }
        }
        tvPrice.setText(minPrice + "");
        type = carHailing.getType();
        if (type == 1) {
            tvPriceShow.setText("/人起");
        } else if (type == 2) {
            tvPriceShow.setText("起");
        } else {
            tvPriceShow.setText("/人起");
        }
        String startAddress = carHailing.getStartAddress();
        String endAddress = carHailing.getEndAddress();
        getOnPlace = JSONArray.parseArray(startAddress, CarHailingTripPlace.class);
        getOffPlace = JSONArray.parseArray(endAddress, CarHailingTripPlace.class);
        chauffeurId = carHailing.getChauffeurId();
        chauffeurTripId = carHailing.getId();
        leavingSeat = carHailing.getPeopleNum() - carHailing.getCurrentPeopleNum();
        if (CheckUtils.isNoEmptyList(getOnPlace) && getOnPlace.size() == 1) {
            startCarTripDetailId = getOnPlace.get(0).getCarTripDetailId();
            this.startAddress = getOnPlace.get(0).getAddress();
            tvGetOnDetail.setText(this.startAddress);
            if (endCarTripDetailId != -1) {
                getPrice();
            }
        }
        if (CheckUtils.isNoEmptyList(getOffPlace) && getOffPlace.size() == 1) {
            endCarTripDetailId = getOffPlace.get(0).getCarTripDetailId();
            this.endAddress = getOffPlace.get(0).getAddress();
            tvGetOffDetail.setText(this.endAddress);
            if (startCarTripDetailId != -1) {
                getPrice();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_back:
                onBackPressed();
                break;
            case R.id.join_car_hailing_get_on_place_btn:
                if (CheckUtils.isNoEmptyList(getOnPlace)) {
                    initWindow(mActivity, getOnPlace, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            startCarTripDetailId = getOnPlace.get(position).getCarTripDetailId();
                            startAddress = getOnPlace.get(position).getAddress();
                            tvGetOnDetail.setText(startAddress);
                            if (endCarTripDetailId != -1) {
                                getPrice();
                            }
                            hideWindow();
                        }
                    });
                }
                break;
            case R.id.join_car_hailing_get_off_place_btn:
                if (CheckUtils.isNoEmptyList(getOffPlace)) {
                    initWindow(mActivity, getOffPlace, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            endCarTripDetailId = getOffPlace.get(position).getCarTripDetailId();
                            endAddress = getOffPlace.get(position).getAddress();
                            tvGetOffDetail.setText(endAddress);
                            if (startCarTripDetailId != -1) {
                                getPrice();
                            }
                            hideWindow();
                        }
                    });
                }
                break;
            case R.id.join_car_hailing_select_count:
                if (leavingSeat <= 0) {
                    break;
                }
                select = new ArrayList<>();
                for (int i = 0; i < leavingSeat; i++) {
                    CarHailingTripPlace carHailingTripPlace = new CarHailingTripPlace();
                    carHailingTripPlace.setAddress((i + 1) + "人");
                    select.add(carHailingTripPlace);
                }
                initWindow(mActivity, select, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        peopleCount = position + 1;
                        tvSelectCount.setText(peopleCount + "人");
                        hideWindow();
                    }
                });
                break;
            case R.id.pay_join_car_hailing:
                if (!CommonUtils.checkLogin(mActivity)) {
                    break;
                }
                if (leavingSeat <= 0) {
                    ToastUtils.displayMsg("已无剩余票", mActivity);
                    break;
                }
                if (startCarTripDetailId == -1) {
                    ToastUtils.displayMsg("请选择上车地点", mActivity);
                    break;
                }
                if (endCarTripDetailId == -1) {
                    ToastUtils.displayMsg("请选择下车地点", mActivity);
                    break;
                }
                if (peopleCount == 0) {
                    ToastUtils.displayMsg("请选择乘车人数", mActivity);
                    break;
                }
                if (CheckUtils.isEmptyStr(App.getUserInfo().getMobile())) {
                    showBindDialog();
                    break;
                }
                if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
                payOrder();
                break;
            case R.id.tv_redbag_layout:
                Intent it = new Intent(mActivity, MyRedBagActivity.class);
                it.putExtra("agentId", carHailing.getAgentId());
                it.putExtra("redBagId", redBag != null ? redBag.getId() : -1);
                it.putExtra("isFromConfirmOrder", true);
                startActivityForResult(it, REQUEST_CHOOSE_RED_BAG);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_RED_BAG && resultCode == MyRedBagActivity.RESPONSE_CHOOSE_RED_BAG && data != null) {
            RedBag bag = (RedBag) data.getSerializableExtra("red_bag");
            if (bag == null) {
                redBag = null;
                tvRedbag.setText("您有" + redBagCount + "个可用红包");
            } else if (null == redBag || !redBag.equals(bag)) {
                redBag = bag;
                if (redBag.getCouponType() == 3) {
                    tvRedbag.setText(StringUtils.BigDecimal2Str(redBag.getDiscountRate()) + "折");
                } else {
                    tvRedbag.setText("红包抵扣" + StringUtils.BigDecimal2Str(redBag.getAmt()) + "元");
                }
            }
        }
    }

    private void showBindDialog() {
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

    private void payOrder() {//URL_CAR_HAILING_ORDER_SUBMIT
        mLoadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<>();
        map.put("loginToken", App.getUserInfo().getToken());
        map.put("chauffeurId", chauffeurId);
        map.put("chauffeurTripId", chauffeurTripId);
        map.put("chauffeurTripDetailId", chauffeurTripDetailId);
        map.put("agentId", carHailing.getAgentId());
        map.put("userId", App.getUserInfo().getId());
        map.put("userName", App.getUserInfo().getName());
        map.put("userMobile", App.getUserInfo().getMobile());
        map.put("userHeaderImg", App.getUserInfo().getHeaderImg());
        map.put("price", price);
        if (carHailing.getType() == 1) {
            map.put("totalPrice", price.multiply(new BigDecimal(peopleCount)));
        } else {
            map.put("totalPrice", price);
        }
        map.put("peopleNum", peopleCount);
        map.put("chauffeurTripType", type);
        map.put("leaveTime", CommonUtils.formatTime(carHailing.getLeaveTime().getTime(), CommonUtils.yyyy_MM_dd_HH_mm_ss));
        map.put("service", carHailing.getService());
        map.put("chauffeurTripStartAddress", carHailing.getStartAddress());
        map.put("chauffeurTripEndAddress", carHailing.getEndAddress());
        map.put("startAddress", startAddress);
        map.put("endAddress", endAddress);
        map.put("carpoolPrice", carHailing.getCarpoolDefaultPrice());
        map.put("charteredlPrice", carHailing.getCharteredlDefaultPrice());
        if (redBag != null) {
            ArrayList<Map<String, Object>> redBagRequestDTOs = new ArrayList<>();
            HashMap<String, Object> m = new HashMap<>();
            m.put("id", redBag.getId());
            m.put("name", redBag.getName());
            m.put("amt", redBag.getAmt());
            m.put("promotionType", redBag.getPromotionType());
            redBagRequestDTOs.add(m);
            map.put("redBags", redBagRequestDTOs);
        }
        if (orderPromoResponses != null) {
            map.put("promoList", orderPromoResponses);
        }
        data.put("data", JSON.toJSONString(map));
        VolleyOperater<CarHailingSubmitOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CAR_HAILING_ORDER_SUBMIT, data, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    CarHailingSubmitOrderModel model = (CarHailingSubmitOrderModel) obj;
                    Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                    intent.putExtra("orderId", model.getValue().getId());
                    intent.putExtra("agentId", model.getValue().getAgentId());
                    intent.putExtra("isCarHailing", true);
                    startActivity(intent);
                    setResult(RESULT_OK);
                    JoinCarHailingActivity.this.finish();
                }
            }
        }, CarHailingSubmitOrderModel.class);
    }

    private void getPrice() {
        mLoadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("chauffeurId", chauffeurId);
        map.put("chauffeurTripId", chauffeurTripId);
        map.put("startCarTripDetailId", startCarTripDetailId);
        map.put("endCarTripDetailId", endCarTripDetailId);
        VolleyOperater<CarHailingDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_CAR_HAILING_TRIP_PRICE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    CarHailingDetailModel model = (CarHailingDetailModel) obj;
                    price = model.getValue().getPrice();
                    tvPrice.setText(price + "");
                    if (type == 1) {
                        tvPriceShow.setText("/人");
                    } else if (type == 2) {
                        tvPriceShow.setText("");
                    }
                    chauffeurTripDetailId = model.getValue().getId();
                }
            }
        }, CarHailingDetailModel.class);
    }

    public void initWindow(final Activity context, List<CarHailingTripPlace> list, AdapterView.OnItemClickListener itemListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_car_hailing_select, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.popup_car_hailing_cancel);
        ListView lvPay = (ListView) view.findViewById(R.id.popup_car_hailing_list);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPopupWindow.dismiss();
            }
        });
        SelectListAdapter adapter = new SelectListAdapter(R.layout.view_car_hailing_select, context);
        lvPay.setAdapter(adapter);
        adapter.setData(list);
        lvPay.setOnItemClickListener(itemListener);

        payPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        payPopupWindow.setBackgroundDrawable(cd);
        payPopupWindow.setOutsideTouchable(true);
        payPopupWindow.setFocusable(true);
        payPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1.0f;
                context.getWindow().setAttributes(lp);
            }
        });
        if (!payPopupWindow.isShowing()) {
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            lp.alpha = 0.5f;
            context.getWindow().setAttributes(lp);
            payPopupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    public void hideWindow() {
        if (payPopupWindow != null && payPopupWindow.isShowing()) {
            payPopupWindow.dismiss();
        }
    }

    private void getCallCarDataAvailable() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("agentId", carHailing.getAgentId());
        VolleyOperater<UserCarUsablePromotionActivityModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_CAR_USABLE_PROMOTION_ACTIVITY, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    UserCarUsablePromotionActivityModel model = (UserCarUsablePromotionActivityModel) obj;
                    if (CheckUtils.isNoEmptyList(model.getValue().getPromotionActivity())) {
                        orderPromoResponses = model.getValue().getPromotionActivity();
                        promotionLayout.setVisibility(View.VISIBLE);
                        for (OrderPromoResponse promotionActivity : orderPromoResponses) {
                            addPromotion(promotionActivity);
                        }
                        return;
                    }
                    if (CheckUtils.isNoEmptyList(model.getValue().getRedBagList())) {
                        redBagCount = model.getValue().getRedBagList().size();
                        redbagLayout.setVisibility(View.VISIBLE);
                        tvRedbag.setText("您有" + redBagCount + "个可用红包");
                    }
                }
            }
        }, UserCarUsablePromotionActivityModel.class);
    }

    private void addPromotion(OrderPromoResponse promotion) {
        LinearLayout childLayout = new LinearLayout(mActivity);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        if (CheckUtils.isNoEmptyStr(promotion.getPromoImg())) {
            ImageView image = new ImageView(mActivity);
            ImageUtils.loadBitmap(mActivity, promotion.getPromoImg(), image, R.drawable.jian, "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x13), getResources().getDimensionPixelOffset(R.dimen.x13));
            childLayout.addView(image, params);
        }
        if (CheckUtils.isNoEmptyStr(promotion.getRule())) {
            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x5);
            tv.setText(promotion.getRule());
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextColor(getResources().getColor(R.color.color_6));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            childLayout.addView(tv, params);
        }
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsChild.topMargin = getResources().getDimensionPixelOffset(R.dimen.x6);
        promotionLayout.addView(childLayout, paramsChild);
    }
}
