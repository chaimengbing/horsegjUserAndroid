package com.project.mgjandroid.ui.activity.carhailing;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.base.FileCache;
import com.project.mgjandroid.bean.carhailing.CarHailingDriver;
import com.project.mgjandroid.bean.carhailing.CarHailingOrder;
import com.project.mgjandroid.bean.carhailing.CarHailingTripPlace;
import com.project.mgjandroid.bean.carhailing.CarRedBagActivity;
import com.project.mgjandroid.constants.AgentRequestType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.StringModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.ui.view.TimeTextView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/12/7.
 */
public class CarHailingOrderDetailActivity extends BaseActivity {

    @InjectView(R.id.photo_back)
    private ImageView ivBack;
    @InjectView(R.id.car_hailing_detail_more)
    private TextView tvMore;
    @InjectView(R.id.car_hailing_detail_content)
    private LinearLayout llContent;
    @InjectView(R.id.car_hailing_detail_order_driver_header)
    private RoundImageView ivAvatar;
    @InjectView(R.id.car_hailing_detail_order_driver_name)
    private TextView tvDriverName;
    @InjectView(R.id.car_hailing_detail_order_driver_car_id)
    private TextView tvCarId;
    @InjectView(R.id.car_hailing_detail_order_driver_score)
    private RatingBar rbDriverScore;
    @InjectView(R.id.car_hailing_detail_order_driver_tv_score)
    private TextView tvDriverScore;
    @InjectView(R.id.car_hailing_detail_order_driver_car_type)
    private TextView tvCarType;
    @InjectView(R.id.car_hailing_order_call)
    private ImageView ivCall;
    @InjectView(R.id.car_hailing_order_evaluate_over_layout)
    private LinearLayout llEvaluateOver;
    @InjectView(R.id.car_hailing_order_status_indicator_layout)
    private LinearLayout llStatusIndicator;
    @InjectView(R.id.car_hailing_order_status_layout)
    private LinearLayout llStatus;
    @InjectView(R.id.car_hailing_detail_order_wait_start)
    private TextView tvLeft;
    @InjectView(R.id.car_hailing_detail_order_wait_start_img)
    private ImageView ivLeft;
    @InjectView(R.id.car_hailing_detail_order_wait_get_on)
    private TextView tvMidLeft;
    @InjectView(R.id.car_hailing_detail_order_wait_get_on_img)
    private ImageView ivMidLeft;
    @InjectView(R.id.car_hailing_detail_order_has_get_on)
    private TextView tvMidRight;
    @InjectView(R.id.car_hailing_detail_order_has_get_on_img)
    private ImageView ivMidRight;
    @InjectView(R.id.car_hailing_detail_order_arrive)
    private TextView tvRight;
    @InjectView(R.id.car_hailing_detail_order_arrive_img)
    private ImageView ivRight;
    @InjectView(R.id.car_hailing_order_detail_layout)
    private LinearLayout llDetail;
    @InjectView(R.id.car_hailing_order_get_on_place_layout)
    private LinearLayout llGetOn;
    @InjectView(R.id.car_hailing_order_get_off_place_layout)
    private LinearLayout llGetOff;
    @InjectView(R.id.car_hailing_order_amt)
    private TextView tvAmt;
    @InjectView(R.id.car_hailing_order_leave_time)
    private TextView tvLeaveTime;
    @InjectView(R.id.car_hailing_order_get_on_district)
    private TextView tvStartDistrict;
    @InjectView(R.id.car_hailing_order_get_off_district)
    private TextView tvEndDistrict;
    @InjectView(R.id.car_hailing_order_get_on_place)
    private TextView tvStartAddress;
    @InjectView(R.id.car_hailing_order_get_off_place)
    private TextView tvEndAddress;
    @InjectView(R.id.car_hailing_order_more_get_on_place)
    private TextView tvMoreGetOn;
    @InjectView(R.id.car_hailing_order_more_get_off_place)
    private TextView tvMoreGetOff;
    @InjectView(R.id.car_hailing_order_service)
    private TextView tvService;
    @InjectView(R.id.car_hailing_order_start)
    private TextView tvStart;
    @InjectView(R.id.car_hailing_order_end)
    private TextView tvEnd;
    @InjectView(R.id.car_hailing_order_price)
    private TextView tvPrice;
    @InjectView(R.id.car_hailing_order_price_more)
    private TextView tvPriceMore;
    @InjectView(R.id.car_hailing_detail_order_score)
    private RatingBar rbOrderScore;
    @InjectView(R.id.car_hailing_order_evaluate_content)
    private TextView tvEvaluateContent;
    @InjectView(R.id.order_detail_order_state_layout_mid_l)
    private RelativeLayout rlMidLeft;
    @InjectView(R.id.order_detail_order_state_layout_mid_r)
    private RelativeLayout rlMidRight;
    @InjectView(R.id.evaluate_indicator_layout)
    private LinearLayout llEvaluateIndicator;
    @InjectView(R.id.confirm_join_car_hailing)
    private TextView tvConfirm;
    @InjectView(R.id.car_hailing_order_detail_to_pay)
    private TimeTextView tvPay;
    @InjectView(R.id.tv_share_redbag)
    private TextView tvShareRedbag;

    private String orderId;
    private MLoadingDialog mLoadingDialog;
    private SubmitOrderModel orderDetail;
    private CallPhoneDialog shouhuoDialog;
    private CallPhoneDialog dialog;
    private CallPhoneDialog agentDialog;
    private CallPhoneDialog cancelDialog;
    private String mobile;
    private String agentPhone;
    private String startAddressJson, endAddressJson;
    private DecimalFormat format = new DecimalFormat("0.0");
    private boolean fromResult = false;
    private PopupWindow mPopupWindow;
    private PopupWindow shareWindow;
    private CarRedBagActivity redBagActivity;
    private PopupWindow pricePopupWindow;
    private String isPaySuccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hailing_order_detail);
        Injector.get(this).inject();
        initView();
        setLinstener();
    }

    private void setLinstener() {
        ivBack.setOnClickListener(this);
        tvMore.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        tvMoreGetOn.setOnClickListener(this);
        tvMoreGetOff.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        tvShareRedbag.setOnClickListener(this);
    }

    private void initView() {
        orderId = getIntent().getStringExtra("orderId");
        isPaySuccess = getIntent().getStringExtra("paySuccess");
        llContent.setVisibility(View.GONE);
        tvMore.setVisibility(View.GONE);
        mLoadingDialog = new MLoadingDialog();
        mLoadingDialog.show(getFragmentManager(), "");
        fromResult = getIntent().getBooleanExtra("isFromOrderList", false);
        getOrderDetail();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_back:
                onBackPressed();
                break;
            case R.id.confirm_join_car_hailing:
                if (orderDetail.getValue().getChauffeurOrder().getStatus() == 5) {
                    if (shouhuoDialog == null) {
                        shouhuoDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                            @Override
                            public void onSure() {
                                confirmDelivery();
                                shouhuoDialog.dismiss();
                            }

                            @Override
                            public void onExit() {
                                shouhuoDialog.dismiss();
                            }
                        }, "", "是否确认送达", "确认", "取消");
                    }
                    shouhuoDialog.show();
                } else if (orderDetail.getValue().getChauffeurOrder().getStatus() == 6) {
                    Intent intent = new Intent(mActivity, CarHailingEvaluateActivity.class);
                    intent.putExtra("chauffeurOrder", orderDetail.getValue().getChauffeurOrder());
                    startActivityForResult(intent, 3);
                } else {

                }
                break;
            case R.id.car_hailing_order_call:
                if (orderDetail.getValue().getChauffeurOrder().getStatus() == 1) {
                    toast("付款成功后才能联系司机师傅哦！");
                    break;
                }
                if (orderDetail.getValue().getChauffeurOrder().getStatus() == -1) {
                    break;
                }
                if (CommonUtils.canCall(orderDetail.getValue().getChauffeurOrder().getChauffeurOrderTrip().getLeaveTime(), orderDetail.getServertime())) {
                    if (CheckUtils.isEmptyStr(agentPhone)) break;
                    if (dialog == null) {
                        dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                            @Override
                            public void onSure() {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.DIAL");
                                intent.setData(Uri.parse("tel:" + agentPhone));
                                mActivity.startActivity(intent);
                                dialog.dismiss();
                            }

                            @Override
                            public void onExit() {
                                dialog.dismiss();
                            }
                        }, "不能拨打司机电话了", "订单已结束多时，如需联系司机，可联系客服帮忙", "联系客服", "关闭");
                    }
                    dialog.show();
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
                    }, "", mobile);
                }
                dialog.show();
                break;
            case R.id.car_hailing_order_more_get_on_place:
                if (CheckUtils.isNoEmptyStr(startAddressJson))
                    MorePlaceWindow.showWindow(mActivity, startAddressJson, true);
                break;
            case R.id.car_hailing_order_more_get_off_place:
                if (CheckUtils.isNoEmptyStr(endAddressJson))
                    MorePlaceWindow.showWindow(mActivity, endAddressJson, false);
                break;
            case R.id.car_hailing_detail_more:
                MorePlaceWindow.showMoreWindow(mActivity, v, this, checkCancel());
                break;
            case R.id.car_hailing_cancel_order:
                MorePlaceWindow.hideMoreWindow();
                if (orderDetail.getValue().getChauffeurOrder().getStatus() == 1) {
                    if (cancelDialog == null) {
                        cancelDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                            @Override
                            public void onSure() {
                                cancelOrder();
                                cancelDialog.dismiss();
                            }

                            @Override
                            public void onExit() {
                                cancelDialog.dismiss();
                            }
                        }, "", "确定要取消订单吗？", "确认", "不取消了");
                    }
                    cancelDialog.show();
                    break;
                } else {
                    if (CheckUtils.isNoEmptyStr(agentPhone)) {
                        cancelDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                            @Override
                            public void onSure() {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.DIAL");
                                intent.setData(Uri.parse("tel:" + agentPhone));
                                mActivity.startActivity(intent);
                                cancelDialog.dismiss();
                            }

                            @Override
                            public void onExit() {
                                cancelDialog.dismiss();
                            }
                        }, "", "取消订单请拨打客服电话", "拨号", "取消");
                        cancelDialog.show();
                    }
                    break;
                }
            case R.id.car_hailing_call_service:
                MorePlaceWindow.hideMoreWindow();
                if (agentDialog == null && CheckUtils.isNoEmptyStr(agentPhone)) {
                    agentDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                        @Override
                        public void onSure() {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.DIAL");
                            intent.setData(Uri.parse("tel:" + agentPhone));
                            mActivity.startActivity(intent);
                            agentDialog.dismiss();
                        }

                        @Override
                        public void onExit() {
                            agentDialog.dismiss();
                        }
                    }, "", agentPhone);
                }
                agentDialog.show();
                break;
            case R.id.car_hailing_detail_order_driver_header:
                if (orderDetail == null) {
                    break;
                }
                CarHailingDriver driver = orderDetail.getValue().getChauffeurOrder().getChauffeur();
                Intent driverIntent = new Intent(mActivity, CarHailingDriverActivity.class);
                driverIntent.putExtra("driver", driver);
                startActivity(driverIntent);
                break;
            case R.id.car_hailing_order_detail_to_pay:
                Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                intent.putExtra("orderId", orderDetail.getValue().getId());
                intent.putExtra("agentId", orderDetail.getValue().getAgentId());
                intent.putExtra("isCarHailing", true);
                intent.putExtra("isFromDetail", true);
                startActivityForResult(intent, 100);
                break;
            case R.id.tv_share_redbag:
                /*if (mPopupWindow == null) {
                    initPopup();
                }
                changePopupState(0.5f, true);*/
                if (shareWindow != null) {
                    WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                    lp.alpha = 0.5f;
                    mActivity.getWindow().setAttributes(lp);
                    tvShareRedbag.setVisibility(View.GONE);
                    shareWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                } else {
                    initSharePopup();
                    tvShareRedbag.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_share:
                if (shareWindow != null) shareWindow.dismiss();
                if (mPopupWindow == null) {
                    initPopup();
                }
                tvShareRedbag.setVisibility(View.GONE);
                changePopupState(0.5f, true);
                break;
            case R.id.second_hand_qq:
                if (redBagActivity != null)
                    onClickShareToQQ(redBagActivity.getTitle(), redBagActivity.getDescription(), redBagActivity.getShareUrl());
                break;
            case R.id.second_hand_wechat:
                if (redBagActivity != null)
                    onClickShareToWechat(0, redBagActivity.getTitle(), redBagActivity.getDescription(), redBagActivity.getShareUrl());
                break;
            case R.id.second_hand_friend:
                if (redBagActivity != null)
                    onClickShareToWechat(1, redBagActivity.getTitle(), redBagActivity.getDescription(), redBagActivity.getShareUrl());
                break;
            case R.id.second_hand_cancel:
                hidePopup();
                break;
            case R.id.iv_close:
                if (shareWindow != null) shareWindow.dismiss();
                break;
            case R.id.car_hailing_order_price_more:
            case R.id.car_hailing_order_amt:
                if (pricePopupWindow == null) {
                    initPopupWindowPrice();
                } else {
                    showWindowPrice();
                }
                break;
        }
    }

    private void initPopupWindowPrice() {
        View view = mInflater.inflate(R.layout.order_price_popup_window, null);
        TextView tvAmt = (TextView) view.findViewById(R.id.tv_amt);
        TextView tvUserPay = (TextView) view.findViewById(R.id.tv_user_pay);
        TextView tvAgentReceive = (TextView) view.findViewById(R.id.tv_agent_receive);
        tvAmt.setText(StringUtils.BigDecimal2Str(orderDetail.getValue().getChauffeurOrder().getTotalPrice()));
        tvUserPay.setText("原价：" + StringUtils.BigDecimal2Str(orderDetail.getValue().getChauffeurOrder().getOriginalTotalPrice()) + "元");
        BigDecimal decimal = (orderDetail.getValue().getChauffeurOrder().getPromoTotalAmt() != null ? orderDetail.getValue().getChauffeurOrder().getPromoTotalAmt() : BigDecimal.ZERO).add(
                orderDetail.getValue().getChauffeurOrder().getRedBagTotalAmt() != null ? orderDetail.getValue().getChauffeurOrder().getRedBagTotalAmt() : BigDecimal.ZERO);
        tvAgentReceive.setText("优惠：-" + StringUtils.BigDecimal2Str(decimal) + "元");

        pricePopupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        pricePopupWindow.setBackgroundDrawable(cd);
        pricePopupWindow.setOutsideTouchable(true);
        pricePopupWindow.setFocusable(true);
        pricePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        showWindowPrice();
    }

    private void showWindowPrice() {
        if (pricePopupWindow != null && !pricePopupWindow.isShowing()) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.7f;
            getWindow().setAttributes(lp);
            pricePopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }

    private void cancelOrder() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<StringModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CANCEL_ORDER_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof StringModel)
                        if (((StringModel) obj).getCode() == 0) {
                            getOrderDetail();
                        } else {
                            ToastUtils.displayMsg(((StringModel) obj).getValue(), mActivity);
                        }
                }
            }
        }, StringModel.class);
    }

    private boolean checkCancel() {
        if (orderDetail.getValue().getChauffeurOrder().getStatus() == -1) {
            return false;
        }
        if (orderDetail.getValue().getChauffeurOrder().getStatus() == 4) {
            return false;
        }
        if (orderDetail.getValue().getChauffeurOrder().getStatus() == 5) {
            return false;
        }
        if (orderDetail.getValue().getChauffeurOrder().getStatus() == 6) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 3) {
            fromResult = true;
            getOrderDetail();
        }
        if (resultCode == RESULT_OK && requestCode == 100) {
            CarHailingOrderDetailActivity.this.finish();
        }
    }

    private void confirmDelivery() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<StringModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_DONE_ORDER_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof StringModel)
                        if (((StringModel) obj).getCode() == 0) {
                            getOrderDetail();
                            Intent intent = new Intent(mActivity, CarHailingEvaluateActivity.class);
                            intent.putExtra("chauffeurOrder", orderDetail.getValue().getChauffeurOrder());
                            startActivityForResult(intent, 3);
                        } else {
                            ToastUtils.displayMsg(((StringModel) obj).getValue(), mActivity);
                        }
                }
            }
        }, StringModel.class);
    }

    private void getOrderDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_NEW_ORDER_DETAIL, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    orderDetail = (SubmitOrderModel) obj;
                    getTelNumXY(orderDetail.getValue().getAgentId(), AgentRequestType.Car.getValue());
                    setView(orderDetail);
                } else {
                    Log.d("sss", "else");
                }
            }
        }, SubmitOrderModel.class);
    }

    private void setView(SubmitOrderModel orderDetail) {
        llContent.setVisibility(View.VISIBLE);
        tvMore.setVisibility(View.VISIBLE);
        if (isPaySuccess != null && isPaySuccess.equals("onLinePay")) {
            redBagActivity = orderDetail.getValue().getCarRedBagActivity();
            if (redBagActivity != null && redBagActivity.getEndTime().getTime() > orderDetail.getServertime().getTime()) {
                if (!fromResult) {
                    initSharePopup();
                    fromResult = false;
                }
            }
        } else {
            redBagActivity = orderDetail.getValue().getCarRedBagActivity();
            if (redBagActivity != null && redBagActivity.getEndTime().getTime() > orderDetail.getServertime().getTime()) {
                CarHailingOrder order = orderDetail.getValue().getChauffeurOrder();
                if (order.getStatus() >= 2) {
                    tvShareRedbag.setVisibility(View.VISIBLE);
                } else {
                    tvShareRedbag.setVisibility(View.GONE);
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fade_fade_in);
            animator.setTarget(llContent);
            animator.start();
        }
        CarHailingDriver driver = orderDetail.getValue().getChauffeurOrder().getChauffeur();
        if (CheckUtils.isNoEmptyStr(driver.getHeaderImg())) {
            ImageUtils.loadBitmap(mActivity, driver.getHeaderImg(), ivAvatar, R.drawable.horsegj_default, Constants.getEndThumbnail(55, 55));
        } else {
            ivAvatar.setImageResource(R.drawable.horsegj_default);
        }

//        agentPhone = orderDetail.getValue().getAgentPhone();
        mobile = driver.getMobile();
        tvDriverName.setText(driver.getName());
        tvCarId.setText(driver.getCarNumber());
        rbDriverScore.setRating(driver.getAverageScore().floatValue());
        tvDriverScore.setText(format.format(driver.getAverageScore()));
        tvCarType.setText(driver.getCarColor() + "·" + driver.getCarSeries());
        CarHailingOrder order = orderDetail.getValue().getChauffeurOrder();
        if (order.getStatus() == 6 && order.getHasComments() == 1) {
            llEvaluateOver.setVisibility(View.VISIBLE);
            llDetail.setVisibility(View.GONE);
            llStatusIndicator.setVisibility(View.GONE);
            llStatus.setVisibility(View.GONE);
            tvConfirm.setVisibility(View.GONE);
            tvStart.setText(order.getChauffeurOrderTrip().getStartCityName() + order.getChauffeurOrderTrip().getStartDistrictName());
            tvEnd.setText(order.getChauffeurOrderTrip().getEndCityName() + order.getChauffeurOrderTrip().getEndDistrictName());
            tvPrice.setText(order.getChauffeurOrderTrip().getTotalPrice() + "");
            rbOrderScore.setRating(order.getChauffeurOrderCommonts().getCompositeScore().floatValue());
            tvEvaluateContent.setText(order.getChauffeurOrderCommonts().getChauffeurOrderScoreComments());
            redBagActivity = orderDetail.getValue().getCarRedBagActivity();
            if (redBagActivity != null && redBagActivity.getEndTime().getTime() > orderDetail.getServertime().getTime()) {
                if (!fromResult) {
                    tvShareRedbag.setVisibility(View.VISIBLE);
                } else {
//                    initSharePopup();
                    fromResult = false;
                }
            }
            if (order.getRedBagTotalAmt() != null && order.getRedBagTotalAmt().compareTo(BigDecimal.ZERO) > 0) {
                tvPriceMore.setVisibility(View.VISIBLE);
                tvPriceMore.setOnClickListener(this);
            }
        } else {
            llEvaluateOver.setVisibility(View.GONE);
            llDetail.setVisibility(View.VISIBLE);
            llStatusIndicator.setVisibility(View.VISIBLE);
            llStatus.setVisibility(View.VISIBLE);
            setStatus(order.getStatus(), order);
            tvAmt.setText("约车价格：" + StringUtils.BigDecimal2Str(order.getTotalPrice()) + "元");
            BigDecimal decimal = (order.getPromoTotalAmt() != null ? order.getPromoTotalAmt() : BigDecimal.ZERO).add(
                    order.getRedBagTotalAmt() != null ? order.getRedBagTotalAmt() : BigDecimal.ZERO);
            if (decimal.compareTo(BigDecimal.ZERO) > 0) {
                tvAmt.setText("约车价格：" + StringUtils.BigDecimal2Str(order.getTotalPrice()) + "元(已优惠" + StringUtils.BigDecimal2Str(decimal) + "元)");
                tvAmt.setOnClickListener(this);
            }
            tvLeaveTime.setText("发车时间：" + CommonUtils.getTime(order.getChauffeurOrderTrip().getLeaveTime(), orderDetail.getServertime()));
            tvStartDistrict.setText("候车点(" + order.getChauffeurOrderTrip().getStartCityName() + order.getChauffeurOrderTrip().getStartDistrictName() + ")");
            tvStartAddress.setText(order.getChauffeurOrderTrip().getStartAddress());
            tvEndDistrict.setText("下车点(" + order.getChauffeurOrderTrip().getEndCityName() + order.getChauffeurOrderTrip().getEndDistrictName() + ")");
            tvEndAddress.setText(order.getChauffeurOrderTrip().getEndAddress());
            if (CheckUtils.isNoEmptyStr(order.getChauffeurOrderTrip().getService()))
                tvService.setText("车主服务：" + order.getChauffeurOrderTrip().getService());
            else
                tvService.setVisibility(View.GONE);
            startAddressJson = order.getChauffeurOrderTrip().getChauffeurTripStartAddress();
            endAddressJson = order.getChauffeurOrderTrip().getChauffeurTripEndAddress();
            List<CarHailingTripPlace> getOnPlace = JSONArray.parseArray(order.getChauffeurOrderTrip().getChauffeurTripStartAddress(), CarHailingTripPlace.class);
            List<CarHailingTripPlace> getOffPlace = JSONArray.parseArray(order.getChauffeurOrderTrip().getChauffeurTripEndAddress(), CarHailingTripPlace.class);
            if (CheckUtils.isNoEmptyList(getOnPlace)) {
                if (getOnPlace.size() > 1) {
                    int count = 0;
                    for (CarHailingTripPlace place : getOnPlace) {
                        if (count >= 2) {
                            break;
                        }
                        if (!place.getAddress().equals(order.getChauffeurOrderTrip().getStartAddress())) {
                            View view = mInflater.inflate(R.layout.view_get_on_show, null);
                            TextView tv = (TextView) view.findViewById(R.id.car_hailing_order_get_on_place);
                            tv.setText(place.getAddress());
                            llGetOn.addView(view);
                            count++;
                        }
                    }
                }
                if (getOnPlace.size() > 3) {
                    tvMoreGetOn.setVisibility(View.VISIBLE);
                } else {
                    tvMoreGetOn.setVisibility(View.GONE);
                }
            }
            if (CheckUtils.isNoEmptyList(getOffPlace)) {
                if (getOffPlace.size() > 1) {
                    int count = 0;
                    for (CarHailingTripPlace place : getOffPlace) {
                        if (count >= 2) {
                            break;
                        }
                        if (!place.getAddress().equals(order.getChauffeurOrderTrip().getEndAddress())) {
                            View view = mInflater.inflate(R.layout.view_get_off_show, null);
                            TextView tv = (TextView) view.findViewById(R.id.car_hailing_order_get_off_place);
                            tv.setText(place.getAddress());
                            llGetOff.addView(view);
                            count++;
                        }
                    }
                }
                if (getOffPlace.size() > 3) {
                    tvMoreGetOff.setVisibility(View.VISIBLE);
                } else {
                    tvMoreGetOff.setVisibility(View.GONE);
                }
            }
        }
    }

    private void setStatus(int status, CarHailingOrder order) {
//        Cancel(-1, "取消订单"),
//            Init(0, "订单创建"),
//            WaitPay(1, "等待付款"),
//            WaitDepart(2, "等待发车"),
//            WaitAboard(3, "等待乘客上车"),
//            Aboard(4, "等待送达"),
//            Arrive(5, "等待乘客确认"),
//            Done(6, "确认送达");
        tvPay.setVisibility(View.GONE);
        switch (status) {
            case -1:
                llEvaluateOver.setVisibility(View.VISIBLE);
                llDetail.setVisibility(View.GONE);
                llStatusIndicator.setVisibility(View.GONE);
                llStatus.setVisibility(View.GONE);
                llEvaluateIndicator.setVisibility(View.GONE);
                rbOrderScore.setVisibility(View.GONE);
                ivCall.setVisibility(View.GONE);
                tvStart.setText(order.getChauffeurOrderTrip().getStartCityName() + order.getChauffeurOrderTrip().getStartDistrictName());
                tvEnd.setText(order.getChauffeurOrderTrip().getEndCityName() + order.getChauffeurOrderTrip().getEndDistrictName());
                tvPrice.setText(order.getChauffeurOrderTrip().getTotalPrice() + "");
                tvPrice.setTextColor(mResource.getColor(R.color.color_9));
                tvEvaluateContent.setText(order.getReason());
                break;
            case 1:
                tvLeft.setText("等待付款");
                tvLeft.setTextColor(mResource.getColor(R.color.title_bar_bg));
                ivLeft.setBackgroundResource(R.drawable.order_detail_status_icon);
                tvPay.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvLeft.setText("等待发车");
                tvLeft.setTextColor(mResource.getColor(R.color.title_bar_bg));
                ivLeft.setBackgroundResource(R.drawable.order_detail_status_icon);
//                tvConfirm.setVisibility(View.VISIBLE);
//                tvConfirm.setText("确认送达");
                break;
            case 3:
                tvLeft.setTextColor(mResource.getColor(R.color.color_3));
                ivLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvMidLeft.setTextColor(mResource.getColor(R.color.title_bar_bg));
                ivMidLeft.setBackgroundResource(R.drawable.order_detail_status_icon);
//                tvConfirm.setVisibility(View.VISIBLE);
//                tvConfirm.setText("确认送达");
                break;
            case 4:
                tvLeft.setTextColor(mResource.getColor(R.color.color_3));
                ivLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvMidLeft.setTextColor(mResource.getColor(R.color.color_3));
                ivMidLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvMidRight.setTextColor(mResource.getColor(R.color.title_bar_bg));
                ivMidRight.setBackgroundResource(R.drawable.order_detail_status_icon);
//                tvConfirm.setVisibility(View.VISIBLE);
//                tvConfirm.setText("确认送达");
                break;
            case 5:
                tvLeft.setTextColor(mResource.getColor(R.color.color_3));
                ivLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvMidLeft.setTextColor(mResource.getColor(R.color.color_3));
                ivMidLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvMidRight.setTextColor(mResource.getColor(R.color.color_3));
                ivMidRight.setBackgroundResource(R.drawable.icon_point_gray);
                tvRight.setText("等待乘客确认");
                tvRight.setTextColor(mResource.getColor(R.color.title_bar_bg));
                ivRight.setBackgroundResource(R.drawable.order_detail_status_icon);
                tvConfirm.setVisibility(View.VISIBLE);
                tvConfirm.setText("确认送达");
                break;
            case 6:
                tvLeft.setTextColor(mResource.getColor(R.color.color_3));
                ivLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvMidLeft.setTextColor(mResource.getColor(R.color.color_3));
                ivMidLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvMidRight.setTextColor(mResource.getColor(R.color.color_3));
                ivMidRight.setBackgroundResource(R.drawable.icon_point_gray);
                tvRight.setText("已到达目的地");
                tvRight.setTextColor(mResource.getColor(R.color.title_bar_bg));
                ivRight.setBackgroundResource(R.drawable.order_detail_status_icon);
                tvConfirm.setVisibility(View.VISIBLE);
                tvConfirm.setText("评价");
                break;
        }
    }

    private void getTelNumXY(int agentId, int agentType) {
        final Map<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("type", agentType);
        VolleyOperater<CustomerAndComplainPhoneDTOModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_TELNUM_ID_NEW, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (obj instanceof String) {
                    ToastUtils.displayMsg(obj.toString(), mActivity);
                    return;
                }
                CustomerAndComplainPhoneDTOModel model = (CustomerAndComplainPhoneDTOModel) obj;
                for (int i = 0; i < model.getValue().size(); i++) {
                    if (model.getValue() != null && 1 == model.getValue().get(i).getType()) {
                        agentPhone = model.getValue().get(i).getPhone();
                    }
                }
            }
        }, CustomerAndComplainPhoneDTOModel.class);
    }

    private void hidePopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private void initSharePopup() {
        View view = mInflater.inflate(R.layout.share_redbag_popupwindow, null);
        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);
        TextView tvCount = (TextView) view.findViewById(R.id.redbag_count);
        TextView tvShare = (TextView) view.findViewById(R.id.tv_share);
        ivClose.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        if (redBagActivity != null && redBagActivity.getRedBagNum() > 0) {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(redBagActivity.getRedBagNum() + "个");
        }
        shareWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        shareWindow.setBackgroundDrawable(cd);
        shareWindow.setTouchable(true);
        shareWindow.setFocusable(true);
        shareWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                mActivity.getWindow().setAttributes(lp);
                tvShareRedbag.setVisibility(View.VISIBLE);
            }
        });
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        mActivity.getWindow().setAttributes(lp);
        shareWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void initPopup() {
        View view = mInflater.inflate(R.layout.popup_second_hand_share, null);
        LinearLayout llQQ = (LinearLayout) view.findViewById(R.id.second_hand_qq);
        LinearLayout llWechat = (LinearLayout) view.findViewById(R.id.second_hand_wechat);
        LinearLayout llFriend = (LinearLayout) view.findViewById(R.id.second_hand_friend);
        TextView tvCancel = (TextView) view.findViewById(R.id.second_hand_cancel);
        tvCancel.setOnClickListener(this);
        llQQ.setOnClickListener(this);
        llWechat.setOnClickListener(this);
        llFriend.setOnClickListener(this);

        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changePopupState(1.0f, false);
            }
        });
    }

    private void changePopupState(float v, boolean b) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = v;
        mActivity.getWindow().setAttributes(lp);
        if (b && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else {
            tvShareRedbag.setVisibility(View.VISIBLE);
        }
    }


    /**
     * param flag 0 好友，1 朋友圈
     */
    private void onClickShareToWechat(int flag, String title, String summary, String tagUrl) {
        if (!App.getApi().isWXAppInstalled()) {
            ToastUtils.displayMsg("请先安装微信客户端", mActivity);
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = tagUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title;
        msg.description = summary;
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        App.getApi().sendReq(req);
    }

    private void onClickShareToQQ(String title, String summary, String tagUrl) {
        String path = FileCache.getImageDirectory() + File.separator + "horsegj_icon.png";
        File file = new File(path);
        if (!file.exists() || file.length() == 0) {
            try {
                MLog.i("Output icon");
                BitmapDrawable d = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);
                Bitmap img = d.getBitmap();
                OutputStream os = new FileOutputStream(path);
                img.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, tagUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
        App.getmTencent().shareToQQ(mActivity, params, listener);
    }

    private BaseUiListener listener = new BaseUiListener();

    /**
     * QQ分享回调
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            MLog.i("onComplete:" + response.toString());
            ToastUtils.displayMsg("分享成功", mActivity);
        }

        @Override
        public void onError(UiError e) {
            MLog.i("onError:" + "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
            ToastUtils.displayMsg("分享失败", mActivity);
        }

        @Override
        public void onCancel() {
            MLog.i("onCancel");
            ToastUtils.displayMsg("分享已取消", mActivity);
        }
    }
}
