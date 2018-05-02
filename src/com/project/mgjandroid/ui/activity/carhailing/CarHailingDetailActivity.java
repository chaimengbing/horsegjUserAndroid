package com.project.mgjandroid.ui.activity.carhailing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.carhailing.CarHailing;
import com.project.mgjandroid.bean.carhailing.CarHailingDriver;
import com.project.mgjandroid.bean.carhailing.CarHailingOrder;
import com.project.mgjandroid.bean.carhailing.CarHailingTrip;
import com.project.mgjandroid.bean.carhailing.CarHailingTripPlace;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.carhailing.CarHailingSubmitOrderModel;
import com.project.mgjandroid.model.carhailing.CarHailingTripDetailModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/12/7.
 */
public class CarHailingDetailActivity extends BaseActivity {

    @InjectView(R.id.photo_back)
    private ImageView ivBack;
    @InjectView(R.id.car_hailing_detail_leave_time)
    private TextView tvLeaveTime;
    @InjectView(R.id.car_hailing_detail_leaving_seat)
    private TextView tvLeavingSeat;
    @InjectView(R.id.car_hailing_detail_icon)
    private ImageView ivTypeIcon;
    @InjectView(R.id.car_hailing_detail_get_on_place)
    private TextView tvGetOn;
    @InjectView(R.id.car_hailing_detail_get_off_place)
    private TextView tvGetOff;
    @InjectView(R.id.car_hailing_detail_more_get_on_place)
    private TextView tvMoreGetOn;
    @InjectView(R.id.car_hailing_detail_more_get_off_place)
    private TextView tvMoreGetOff;
    @InjectView(R.id.car_hailing_get_on_place_layout)
    private LinearLayout llGetOn;
    @InjectView(R.id.car_hailing_get_off_place_layout)
    private LinearLayout llGetOff;
    @InjectView(R.id.car_hailing_detail_price)
    private TextView tvPrice;
    @InjectView(R.id.car_hailing_detail_service)
    private TextView tvService;
    @InjectView(R.id.car_hailing_detail_driver_name)
    private TextView tvDriverName;
    @InjectView(R.id.car_hailing_detail_car_type)
    private TextView tvCarType;
    @InjectView(R.id.car_hailing_detail_driver_header)
    private RoundImageView rivDriver;
    @InjectView(R.id.car_hailing_detail_driver_phone)
    private ImageView ivPhone;
    @InjectView(R.id.confirm_join_car_hailing)
    private TextView tvConfirm;

    private CarHailingTrip carHailingTrip;
    private String startAddressJson;
    private String endAddressJson;
    private String mobile;
    private CallPhoneDialog dialog;
    private boolean isJoin = false;
    private MLoadingDialog mLoadingDialog;
    private NoticeDialog noticeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hailing_detail);
        Injector.get(this).inject();
        initView();
        initData();
        setLinstener();
        mLoadingDialog = new MLoadingDialog();
    }

    private void initData() {
        carHailingTrip = (CarHailingTrip) getIntent().getSerializableExtra("carHailingTrip");
        if (carHailingTrip == null) {
            finish();
            return;
        }
        setView(carHailingTrip);
    }

    private void setView(CarHailingTrip carHailing) {
        List<CarHailingOrder> orderList = carHailing.getChauffeurOrderList();
        if (App.isLogin()) {
            isJoin = false;
            for (CarHailingOrder order : orderList) {
                if (order.getUserId() == App.getUserInfo().getId()) {
                    isJoin = true;
                }
            }
        }
        Date leaveTime = carHailing.getLeaveTime();
        Date serviceTime = carHailing.getServiceTime();
        tvLeaveTime.setText(CommonUtils.getTime(leaveTime, serviceTime));
        int leavingSeat = carHailing.getPeopleNum() - carHailing.getCurrentPeopleNum();
        BigDecimal minPrice = carHailing.getChauffeurTripDetailList().get(0).getPrice();
        for (CarHailing carHailingTrip : carHailing.getChauffeurTripDetailList()) {
            if (minPrice.compareTo(carHailingTrip.getPrice()) > 0) {
                minPrice = carHailingTrip.getPrice();
            }
        }
        if (carHailing.getType() == 1) {
            if (leavingSeat == 0) {
                tvLeavingSeat.setText("满员");
            } else {
                tvLeavingSeat.setText("剩余" + leavingSeat + "座");
            }
            ivTypeIcon.setImageResource(R.drawable.car_hailing_pinche);
            tvPrice.setText("¥" + minPrice + "/人起");
        } else if (carHailing.getType() == 2) {
            tvLeavingSeat.setText("共" + leavingSeat + "座");
            tvPrice.setText("¥" + minPrice + "起");
            ivTypeIcon.setImageResource(R.drawable.car_hailing_baoche);
        } else {
            tvLeavingSeat.setText("共" + leavingSeat + "座");
            tvPrice.setText("¥" + minPrice + "/人起");
            ivTypeIcon.setVisibility(View.INVISIBLE);
        }
        tvGetOn.setText(carHailing.getStartCityName() + carHailing.getStartDistrictName());
        tvGetOff.setText(carHailing.getEndCityName() + carHailing.getEndDistrictName());
        if (CheckUtils.isNoEmptyStr(carHailing.getService())) {
            tvService.setText("车主服务：" + carHailing.getService());
        } else {
            tvService.setVisibility(View.GONE);
        }

        mobile = carHailing.getChauffeur().getMobile();
        tvDriverName.setText(carHailing.getChauffeur().getName());
        tvCarType.setText(carHailing.getChauffeur().getCarColor() + "·" + carHailing.getChauffeur().getCarSeries());
        if (CheckUtils.isNoEmptyStr(carHailing.getChauffeur().getHeaderImg())) {
            ImageUtils.loadBitmap(mActivity, carHailing.getChauffeur().getHeaderImg(), rivDriver, R.drawable.horsegj_default, Constants.getEndThumbnail(63, 63));
        }

        String startAddress = carHailing.getStartAddress();
        startAddressJson = carHailing.getStartAddress();
        String endAddress = carHailing.getEndAddress();
        endAddressJson = carHailing.getEndAddress();
        List<CarHailingTripPlace> start = JSONArray.parseArray(startAddress, CarHailingTripPlace.class);
        List<CarHailingTripPlace> end = JSONArray.parseArray(endAddress, CarHailingTripPlace.class);
        if (CheckUtils.isNoEmptyList(start)) {
            int max = start.size() > 2 ? 2 : start.size();
            if (start.size() > 2) {
                tvMoreGetOn.setVisibility(View.VISIBLE);
            } else {
                tvMoreGetOn.setVisibility(View.GONE);
            }
            llGetOn.removeAllViews();
            for (int i = 0; i < max; i++) {
                TextView tv = new TextView(mActivity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = DipToPx.dip2px(mActivity, 10);
                tv.setText(start.get(i).getAddress());
                tv.setSingleLine();
                tv.setEllipsize(TextUtils.TruncateAt.END);
                tv.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tv.setTextSize(12);
                llGetOn.addView(tv, params);
            }
        } else {
            tvMoreGetOn.setVisibility(View.GONE);
        }
        if (CheckUtils.isNoEmptyList(end)) {
            int max = end.size() > 2 ? 2 : end.size();
            if (end.size() > 2) {
                tvMoreGetOff.setVisibility(View.VISIBLE);
            } else {
                tvMoreGetOff.setVisibility(View.GONE);
            }
            llGetOff.removeAllViews();
            for (int i = 0; i < max; i++) {
                TextView tv = new TextView(mActivity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = DipToPx.dip2px(mActivity, 10);
                tv.setText(end.get(i).getAddress());
                tv.setSingleLine();
                tv.setEllipsize(TextUtils.TruncateAt.END);
                tv.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tv.setTextSize(12);
                llGetOff.addView(tv, params);
            }
        } else {
            tvMoreGetOff.setVisibility(View.GONE);
        }
    }

    private void setLinstener() {
        ivBack.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
        tvMoreGetOn.setOnClickListener(this);
        tvMoreGetOff.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        rivDriver.setOnClickListener(this);
    }

    private void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_back:
                onBackPressed();
                break;
            case R.id.confirm_join_car_hailing:
                if (!CommonUtils.checkLogin(mActivity)) {
                    break;
                }
                CheckCanJoin();
//               Intent intent = new Intent(mActivity,JoinCarHailingActivity.class);
//               intent.putExtra("carHailingTrip",carHailingTrip);
//               startActivity(intent);
                break;
            case R.id.car_hailing_detail_more_get_on_place:
                if (CheckUtils.isNoEmptyStr(startAddressJson))
                    MorePlaceWindow.showWindow(mActivity, startAddressJson, true);
                break;
            case R.id.car_hailing_detail_more_get_off_place:
                if (CheckUtils.isNoEmptyStr(endAddressJson))
                    MorePlaceWindow.showWindow(mActivity, endAddressJson, false);
                break;
            case R.id.car_hailing_detail_driver_phone:
                if (!isJoin) {
                    //付款成功后才能联系司机师傅哦！
                    toast("参与行程后才能联系司机师傅哦！");
                    break;
                }
                if (dialog == null && CheckUtils.isNoEmptyStr(mobile)) {
                    dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                        @Override
                        public void onSure() {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.DIAL");
                            intent.setData(Uri.parse("tel:" + mobile));
                            mActivity.startActivity(intent);
                            dialog.dismiss();
                        }

                        @Override
                        public void onExit() {
                            dialog.dismiss();
                        }
                    }, "", "您要联系司机师傅吗？", "联系司机", "不联系了");
                }
                dialog.show();
                break;
            case R.id.car_hailing_detail_driver_header:
                CarHailingDriver driver = carHailingTrip.getChauffeur();
                Intent driverIntent = new Intent(mActivity, CarHailingDriverActivity.class);
                driverIntent.putExtra("driver", driver);
                startActivity(driverIntent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            CarHailingDetailActivity.this.finish();
        }
    }

    private void CheckCanJoin() {
        mLoadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("chauffeurTripId", carHailingTrip.getId());
        VolleyOperater<CarHailingTripDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CHAUFFEUR_TRIP, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    CarHailingTripDetailModel model = (CarHailingTripDetailModel) obj;
                    if (model.getValue().getStatus() == 0) {
                        Intent intent = new Intent(mActivity, JoinCarHailingActivity.class);
                        model.getValue().setServiceTime(model.getServertime());
                        intent.putExtra("carHailingTrip", model.getValue());
                        startActivityForResult(intent, 100);
                    } else {
                        String str = "";
                        if (model.getValue().getStatus() == -1) {
                            str = "当前行程已被取消，您可以选择其他行程同行。";
                        } else if (model.getValue().getStatus() == 1) {
                            str = "当前行程已停止预约，您可以选择其他行程同行。";
                        } else if (model.getValue().getStatus() == 2) {
                            str = "当前行程正在进行中，您可以选择其他行程同行。";
                        } else if (model.getValue().getStatus() == 3) {
                            str = "当前行程已结束，您可以选择其他行程同行。";
                        }
                        noticeDialog = new NoticeDialog(mActivity, new NoticeDialog.onBtnClickListener() {
                            @Override
                            public void onSure() {
                                setResult(RESULT_OK);
                                CarHailingDetailActivity.this.finish();
                            }
                        }, "", str);
                        noticeDialog.show();
                    }
                }
            }
        }, CarHailingTripDetailModel.class);
    }
}
