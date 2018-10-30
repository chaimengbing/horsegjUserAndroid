package com.project.mgjandroid.ui.activity.legwork;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.LegworkOrderDetailsModel;
import com.project.mgjandroid.model.LegworkServiceChargeModel;
import com.project.mgjandroid.model.LegworkStatusModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.adapter.LegworkStatusAdapter;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.ui.view.TimeView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.yinglan.scrolllayout.ScrollLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SunXueLiang on 2018-03-12.
 */

public class LegworkOrderdetailsActivity extends BaseActivity {


    @InjectView(R.id.include)
    private RelativeLayout commonTopBar;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.common_text)
    private TextView tvText;
    @InjectView(R.id.tv_goods_information)
    private TextView tvGoodsInformation;
    @InjectView(R.id.legwork_iv_buy)
    private ImageView ivLegworkIcon;
    @InjectView(R.id.legwork_tv_buy_personal_information)
    private TextView TvBuyPersonalInformation;
    @InjectView(R.id.legwork_tv_buy_address)
    private TextView tvBuy;
    @InjectView(R.id.legwork_tv_deliver_address)
    private TextView tvGive;
    @InjectView(R.id.legwork_tv_deliver_personal_information)
    private TextView TvDeliverPersonalInformation;
    @InjectView(R.id.tv_remarks)
    private TextView tvRemarks;
    @InjectView(R.id.tv_good_price)
    private TextView tvGoodPrice;
    @InjectView(R.id.tv_service_charge)
    private TextView tvServiceCharge;
    @InjectView(R.id.img_service_charge)
    private TextView imgServiceCharge;
    @InjectView(R.id.tv_order_number)
    private TextView tvOrderNumber;
    @InjectView(R.id.tv_order_time)
    private TextView tvOrderTime;
    @InjectView(R.id.rider_user_avatar)
    private RoundImageView imgRider;
    @InjectView(R.id.expand_imageview)
    private ImageView expandImageView;
    @InjectView(R.id.tv_rider_name)
    private TextView tvRiderName;
    @InjectView(R.id.img_phone)
    private ImageView imgPhone;
    @InjectView(R.id.tv_time)
    private TimeView tvPrePaymentTime;
    @InjectView(R.id.tv_cancel_order)
    private TextView tvCancelOrder;
    @InjectView(R.id.tv_payment)
    private TextView tvPayment;
    @InjectView(R.id.tv_to_evaluate)
    private TextView tvToEvaluate;
    @InjectView(R.id.layout_complete)
    private LinearLayout layoutComplete;
    @InjectView(R.id.layout_no_payment)
    private LinearLayout layoutNoPayment;
    @InjectView(R.id.layout_goods_information)
    private LinearLayout layoutGoodsInformation;
    @InjectView(R.id.layout_good_price)
    private LinearLayout layoutGoodPrice;
    @InjectView(R.id.layout_remarks)
    private LinearLayout layoutRemarks;
    @InjectView(R.id.redbags_layout)
    private LinearLayout redbagsLayout;
    @InjectView(R.id.refund_layout)
    private LinearLayout refundLayout;
    @InjectView(R.id.delivery_man_mapview)
    private MapView deliveryManMapView;
    @InjectView(R.id.tv_legwork_status)
    private TextView tvLegworkStatus;
    @InjectView(R.id.tv_refund_desc)
    private TextView tvRefundDesc;
    @InjectView(R.id.redbags_money_textview)
    private TextView redbagsMoneyTextView;
    @InjectView(R.id.pay_money_textview)
    private TextView payMoneyTextView;
    @InjectView(R.id.img_send_redbag)
    private ImageView sendRedBag;

    @InjectView(R.id.delivery_man_layout)
    LinearLayout deliveryMan;
    @InjectView(R.id.delivery_man_phone_textview)
    TextView manPhoneTv;
    @InjectView(R.id.delivery_name_textview)
    TextView manNameTv;
    @InjectView(R.id.legwork_details_layout)
    ScrollLayout legWorkDetailsLayout;
    @InjectView(R.id.address_layout)
    LinearLayout addressLayout;
    @InjectView(R.id.delivery_man_info_layout)
    RelativeLayout deliveryManInfoLayout;

    private BaiduMap baiduMap;
    private BitmapDescriptor deliveryManIcon, deliveryGoodsIcon, takeGoodsIcon;

    private String orderId;
    private LegworkOrderDetailsModel.ValueBean valueBean;
    public static final int REFRESH = 2000;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LegworkOrderDetailsModel model;
    private CallPhoneDialog dialog;
    private String regionalHeadPhone;
    private long agentId = 0;
    private PopupWindow popupWindow;
    private Dialog mStatusDialog;
    ArrayList<LegworkStatusModel> legworkStatusModels = new ArrayList<>();
    private ShareUtil shareUtil;
    private LegworkOrderDetailsModel.ValueBean.ShareRedBagInfo shareRedBagInfo;
    private boolean isCanIn;
    private long currentMS;
    private ScrollLayout.OnScrollChangedListener mScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                legWorkDetailsLayout.getBackground().setAlpha(255 - (int) precent);
            }

        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                //退出操作
            } else if (currentStatus.equals(ScrollLayout.Status.CLOSED)) {
                hideMaps();
            } else if (currentStatus.equals(ScrollLayout.Status.OPENED)) {
                showMaps();
            }
        }

        @Override
        public void onChildScroll(int top) {

        }
    };

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_legwork_orderdetails);
        Injector.get(this).inject();
        agentId = PreferenceUtils.getLongPreference("issueAgentId", 0, mActivity);
        initView();
        getTelNumXY(agentId, 18);
    }

    private void initView() {
        orderId = getIntent().getStringExtra("orderId");
        isCanIn = getIntent().getBooleanExtra("isCanIn", false);
        ivBack.setOnClickListener(this);
        imgServiceCharge.setOnClickListener(this);
        tvCancelOrder.setOnClickListener(this);
        tvPayment.setOnClickListener(this);
        tvToEvaluate.setOnClickListener(this);
        tvText.setOnClickListener(this);
        tvLegworkStatus.setOnClickListener(this);
        imgPhone.setOnClickListener(this);
        tvRefundDesc.setOnClickListener(this);
        sendRedBag.setOnClickListener(this);
        manPhoneTv.setOnClickListener(this);
        tvTitle.setText("");
        tvText.setText("");
        tvText.setBackgroundResource(R.drawable.call_icon);
        ivBack.setImageResource(R.drawable.legwork_back);
        commonTopBar.setBackgroundColor(getResources().getColor(R.color.color_f5));

        tvPrePaymentTime.setOnTimerStopListener(new TimeView.OnTimerStopListener() {
            @Override
            public void onStopListener() {
                getData();
            }
        });


        baiduMap = deliveryManMapView.getMap();
        hideBaiduMapChildView();
        baiduMap.setMyLocationEnabled(true);
        takeGoodsIcon = BitmapDescriptorFactory.fromResource(R.drawable.take_goods);
        deliveryGoodsIcon = BitmapDescriptorFactory.fromResource(R.drawable.delivery_goods);
        deliveryManIcon = BitmapDescriptorFactory.fromResource(R.drawable.delivery_man_icon);


        sendRedBag.setOnTouchListener(new View.OnTouchListener() {
            int maxwidth;
            int maxheight;
            int preX;// 上一次操作的x的坐标
            int preY;// 上一次操作的Y坐标

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取当前坐标
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                        //判断是否继续传递信号
                        if (moveTime > 200 && (preX > 20 || preY > 20)) {
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        if (maxwidth == 0) {
                            RelativeLayout ivparent = (RelativeLayout) sendRedBag
                                    .getParent();
                            maxwidth = ivparent.getWidth();
                            maxheight = ivparent.getHeight();

                        }
                        preX = rawX;
                        preY = rawY;
                        currentMS = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取当前x,y轴移动的距离
                        int dx = rawX - preX;
                        int dy = rawY - preY;
                        // 获取当前图片的四个值
                        int left = sendRedBag.getLeft() + dx;
                        int top = sendRedBag.getTop() + dy;
                        int right = sendRedBag.getRight() + dx;
                        int bottm = sendRedBag.getBottom() + dy;
                        if (left < 0) {
                            right = right - left;
                            left = 0;

                        }
                        //限制right
                        if (right > maxwidth) {
                            left = left - (right - maxwidth);
                            right = maxwidth;
                        }
                        if (top < 0) {
                            bottm = bottm - top;
                            top = 0;
                        }
                        if (bottm > maxheight) {
                            top = top - (bottm - maxheight);
                            bottm = maxheight;
                        }
                        sendRedBag.layout(left, top, right, bottm);
                        // 从新给初始值赋值
                        preX = rawX;
                        preY = rawY;

                        break;

                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        deliveryManMapView.onResume();
    }

    @Override
    public void onDestroy() {
        baiduMap.setMyLocationEnabled(false);
        baiduMap.clear();
        baiduMap = null;
        deliveryManMapView.onDestroy();
        deliveryManMapView = null;
        super.onDestroy();
    }


    @Override
    public void onPause() {
        super.onPause();
        deliveryManMapView.onPause();
    }


    /**
     * 显示地图
     */
    private void showMaps() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) legWorkDetailsLayout.getLayoutParams();
        params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.bottomMargin = params.rightMargin = params.leftMargin = (int) getResources().getDimension(R.dimen.x10);
        legWorkDetailsLayout.setLayoutParams(params);

        legWorkDetailsLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        commonTopBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        expandImageView.setVisibility(View.VISIBLE);
        tvLegworkStatus.setVisibility(View.GONE);
        layoutGoodsInformation.setVisibility(View.GONE);
    }


    /**
     * 隐藏地图
     */
    private void hideMaps() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) legWorkDetailsLayout.getLayoutParams();
        params.bottomMargin = params.rightMargin = params.leftMargin = 0;
        legWorkDetailsLayout.setLayoutParams(params);

        legWorkDetailsLayout.setBackgroundColor(getResources().getColor(R.color.color_f5));
        commonTopBar.setBackgroundColor(getResources().getColor(R.color.color_f5));
        expandImageView.setVisibility(View.GONE);
        tvLegworkStatus.setVisibility(View.VISIBLE);
        layoutGoodsInformation.setVisibility(View.VISIBLE);
    }

    private void showRedBag() {
        sendRedBag.setVisibility(View.GONE);
        View view = LayoutInflater.from(this).inflate(R.layout.send_redbag, null);
        TextView tvSure = (TextView) view.findViewById(R.id.sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.cancel);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                sendRedBag.setVisibility(View.VISIBLE);
                if (shareUtil == null && shareRedBagInfo != null) {
                    shareUtil = new ShareUtil(mActivity, shareRedBagInfo.getTitle(),
                            shareRedBagInfo.getMemo(),
                            shareRedBagInfo.getUrl(), shareRedBagInfo.getImg());
                }
                if (shareUtil != null) shareUtil.showRedBagPopupWindow();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRedBag.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!popupWindow.isShowing()) {
                    sendRedBag.setVisibility(View.VISIBLE);
                }
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        if (!popupWindow.isShowing()) {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 0.5f;
            mActivity.getWindow().setAttributes(lp);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            popupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER_VERTICAL, 0, 0);
        }
    }

    private void getData() {
        VolleyOperater<LegworkOrderDetailsModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        operater.doRequest(Constants.URL_FIND_LEG_WORK_ORDER_BY_ORDER_ID, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    model = (LegworkOrderDetailsModel) obj;
                    valueBean = model.getValue();
                    shareRedBagInfo = valueBean.getShareRedBagInfo();
                    if (shareRedBagInfo == null) {
                        sendRedBag.setVisibility(View.GONE);
                    } else {
                        sendRedBag.setVisibility(View.VISIBLE);
                    }
                    showDetail(valueBean);
//                    getServiceCharge();
                    initStatusDialog(valueBean);
                }
            }
        }, LegworkOrderDetailsModel.class);
    }

    private void showDetail(LegworkOrderDetailsModel.ValueBean valueBean) {
        if (valueBean != null) {
            legWorkDetailsLayout.setOnScrollChangedListener(null);
            deliveryManMapView.setVisibility(View.GONE);
            deliveryManInfoLayout.setVisibility(View.GONE);
            deliveryMan.setVisibility(View.GONE);
            legWorkDetailsLayout.setToClosed();
            commonTopBar.setBackgroundColor(getResources().getColor(R.color.color_f5));
            if (valueBean.getChildType() == 1) {
                layoutGoodsInformation.setVisibility(View.GONE);
                layoutGoodPrice.setVisibility(View.GONE);
                layoutRemarks.setVisibility(View.VISIBLE);
                switch (valueBean.getStatus()) {
                    case -1:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        if (valueBean.getPaymentState() == 1 && DateUtils.compareTimeBefore(valueBean.getCreateTime())) {
                            //已经支付
                            if (valueBean.getServePrice().equals("0.0") || valueBean.getServePrice().equals("0.00") || valueBean.getServePrice().equals("0")) {
                                refundLayout.setVisibility(View.GONE);
                            } else {
                                refundLayout.setVisibility(View.VISIBLE);
                            }
                        }
                        tvLegworkStatus.setText("已取消");
                        break;
                    case 1:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.VISIBLE);
                        tvLegworkStatus.setText("待支付");
                        refundLayout.setVisibility(View.GONE);
                        tvPayment.setText("立即支付¥" + valueBean.getServePrice());
                        long surplusTime = getTimeBetween(model.getServertime(), valueBean.getPaymentExpireTime());
                        if (surplusTime > 0) {
                            tvPrePaymentTime.setTimes(surplusTime);
                        }
                        break;
                    case 2:
                        if (shareRedBagInfo != null) {
                            if (isCanIn) {
                                isCanIn = false;
                                showRedBag();
                            }
                        }
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        refundLayout.setVisibility(View.GONE);
                        tvLegworkStatus.setText("待确认");
                        break;
                    case 4:
                        takeGoods();
                        break;
                    case 5:
                        delivery();
                        break;
                    case 7:
                        layoutComplete.setVisibility(View.VISIBLE);
                        deliveryMan.setVisibility(View.VISIBLE);
                        tvLegworkStatus.setText("已完成");
                        manNameTv.setText(valueBean.getDeliveryTask().getDeliveryman().getName());
                        layoutNoPayment.setVisibility(View.GONE);
                        refundLayout.setVisibility(View.GONE);
                        if (valueBean.getHasComments() == 1) {
                            tvToEvaluate.setText("已评价");
                            tvToEvaluate.setClickable(false);
                        } else if (valueBean.getHasComments() == 0) {
                            tvToEvaluate.setText("去评价");
                            tvToEvaluate.setClickable(true);
                        }
                        break;
                }
                ivLegworkIcon.setImageDrawable(getResources().getDrawable(R.drawable.icon_take));
                tvBuy.setText(valueBean.getShipperAddress());
                TvBuyPersonalInformation.setVisibility(View.VISIBLE);
                TvBuyPersonalInformation.setText(valueBean.getShipperName() + " " + valueBean.getShipperGender() + " " + valueBean.getShipperMobile());
            } else if (valueBean.getChildType() == 0) {
                layoutGoodsInformation.setVisibility(View.VISIBLE);
                layoutGoodPrice.setVisibility(View.VISIBLE);
                layoutRemarks.setVisibility(View.GONE);
                switch (valueBean.getStatus()) {
                    case -1:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        tvLegworkStatus.setText("已取消");
                        if (valueBean.getPaymentState() == 1 && DateUtils.compareTimeBefore(valueBean.getCreateTime())) {
                            //已经支付
                            if (valueBean.getServePrice().equals("0.0") || valueBean.getServePrice().equals("0.00") || valueBean.getServePrice().equals("0")) {
                                refundLayout.setVisibility(View.GONE);
                            } else {
                                refundLayout.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    case 1:
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.VISIBLE);
                        tvLegworkStatus.setText("待支付");
                        refundLayout.setVisibility(View.GONE);
                        tvPayment.setText("立即支付¥" + valueBean.getServePrice());
                        long surplusTime = getTimeBetween(model.getServertime(), valueBean.getPaymentExpireTime());
                        if (surplusTime > 0) {
                            tvPrePaymentTime.setTimes(surplusTime);
                        }
                        break;
                    case 2:
                        if (shareRedBagInfo != null) {
                            if (isCanIn) {
                                isCanIn = false;
                                showRedBag();
                            }
                        }
                        layoutComplete.setVisibility(View.GONE);
                        layoutNoPayment.setVisibility(View.GONE);
                        refundLayout.setVisibility(View.GONE);
                        tvLegworkStatus.setText("待确认");
                        break;
                    case 4:
                        takeGoods();
                        break;
                    case 5:
                        delivery();
                        break;
                    case 7:
                        layoutComplete.setVisibility(View.VISIBLE);
                        deliveryMan.setVisibility(View.VISIBLE);
                        manNameTv.setText(valueBean.getDeliveryTask().getDeliveryman().getName());
                        tvLegworkStatus.setText("已完成");
                        layoutNoPayment.setVisibility(View.GONE);
                        redbagsLayout.setVisibility(View.GONE);
                        if (valueBean.getHasComments() == 1) {
                            tvToEvaluate.setText("已评价");
                            tvToEvaluate.setEnabled(false);
                        } else if (valueBean.getHasComments() == 0) {
                            tvToEvaluate.setText("去评价");
                            tvToEvaluate.setEnabled(true);
                        }
                        break;
                }
                tvGoodsInformation.setText(valueBean.getDescription());
                ivLegworkIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_legwork_buy));
                TvBuyPersonalInformation.setVisibility(View.GONE);
                if (valueBean.getShipperType() == 1) {
                    tvBuy.setText("就近购买");
                } else if (valueBean.getShipperType() == 2) {
                    String shipperHouseNumber = valueBean.getShipperHouseNumber();
                    if (TextUtils.isEmpty(shipperHouseNumber)) {
                        tvBuy.setText(valueBean.getShipperAddress());
                    } else {
                        tvBuy.setText(valueBean.getShipperAddress() + "\n" + valueBean.getShipperHouseNumber());
                    }
                }
                if (CheckUtils.isNoEmptyStr(valueBean.getGoodsEstimatePrice())) {
                    tvGoodPrice.setText("¥" + valueBean.getGoodsEstimatePrice());
                } else {
                    layoutGoodPrice.setVisibility(View.GONE);
                }
            }
            if (valueBean.getStatus() == 4 || valueBean.getStatus() == 5) {
                tvRiderName.setText(valueBean.getDeliveryTask().getDeliveryman().getName());
                if (CheckUtils.isNoEmptyStr(valueBean.getDeliveryTask().getDeliveryman().getHeaderImg())) {
                    ImageUtils.loadBitmap(mActivity, valueBean.getDeliveryTask().getDeliveryman().getHeaderImg(), imgRider, R.drawable.horsegj_default, Constants.getEndThumbnail(40, 40));
                } else {
                    imgRider.setImageDrawable(getResources().getDrawable(R.drawable.default_group_user_avatar));
                }
            }
            tvGive.setText(valueBean.getUserAddress());
            TvDeliverPersonalInformation.setText(valueBean.getUserName() + " " + valueBean.getUserGender() + " " + valueBean.getUserMobile());
            if (CheckUtils.isNoEmptyStr(valueBean.getRemark())) {
                tvRemarks.setText(valueBean.getRemark());
            } else {
                tvRemarks.setText("无");
            }
            if (CheckUtils.isNoEmptyStr(valueBean.getRedBagDiscountTotalAmt()) && !"0".equals(valueBean.getRedBagDiscountTotalAmt()) && !"0.0".endsWith(valueBean.getRedBagDiscountTotalAmt())) {
                redbagsLayout.setVisibility(View.VISIBLE);
                redbagsMoneyTextView.setText("(红包抵扣" + valueBean.getRedBagDiscountTotalAmt() + "元)");
                payMoneyTextView.setText("￥" + valueBean.getTotalPrice());
            } else {
                redbagsLayout.setVisibility(View.GONE);
            }
            tvServiceCharge.setText("¥" + valueBean.getServePrice());
            tvOrderNumber.setText(valueBean.getId());
            tvOrderTime.setText(valueBean.getCreateTime());

            if (legWorkDetailsLayout != null) {
                expandImageView.setVisibility(legWorkDetailsLayout.getCurrentStatus() == ScrollLayout.Status.OPENED ? View.VISIBLE : View.GONE);
            }

//            int heightLayout = deliveryManInfoLayout.getHeight() + addressLayout.getHeight();
//            if (heightLayout > 0) {
//                legWorkDetailsLayout.setMaxOffset((int) (heightLayout + getResources().getDimension(R.dimen.x30)));
//            }
//            legWorkDetailsLayout.invalidate();
        }
    }

    private void takeGoods() {
        legWorkDetailsLayout.setOnScrollChangedListener(mScrollChangedListener);
        deliveryManMapView.setVisibility(View.VISIBLE);
        deliveryManInfoLayout.setVisibility(View.VISIBLE);
        tvLegworkStatus.setVisibility(View.GONE);
        legWorkDetailsLayout.setToOpen();
        commonTopBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        layoutComplete.setVisibility(View.GONE);
        layoutNoPayment.setVisibility(View.GONE);
        refundLayout.setVisibility(View.GONE);
        tvLegworkStatus.setText("取货中");
//        tvPickUp.setText("等待骑手取货");


        setDetailsLocation();

    }

    private void delivery() {
        legWorkDetailsLayout.setOnScrollChangedListener(mScrollChangedListener);
        deliveryManMapView.setVisibility(View.VISIBLE);
        deliveryManInfoLayout.setVisibility(View.VISIBLE);
        tvLegworkStatus.setVisibility(View.GONE);
        legWorkDetailsLayout.setToOpen();
        commonTopBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        layoutComplete.setVisibility(View.GONE);
        layoutNoPayment.setVisibility(View.GONE);
        refundLayout.setVisibility(View.GONE);
//        tvPickUp.setText("骑手正在配送中");
        tvLegworkStatus.setText("配送中");

        setDetailsLocation();

    }

    private void setDetailsLocation() {
        if (valueBean != null) {
            //取货地址
            putLocationToMarkerOptions(takeGoodsIcon, valueBean.getShipperLatitude(), valueBean.getShipperLongitude(), false);
            //送货地址
            putLocationToMarkerOptions(deliveryGoodsIcon, valueBean.getUserLatitude(), valueBean.getUserLongitude(), false);

            //骑手信息
            LegworkOrderDetailsModel.ValueBean.DeliveryTaskBean deliveryTaskBean = valueBean.getDeliveryTask();
            if (deliveryTaskBean != null) {
                LegworkOrderDetailsModel.ValueBean.DeliveryTaskBean.DeliverymanBean deliverymanBean = deliveryTaskBean.getDeliveryman();
                if (deliverymanBean != null) {
                    putLocationToMarkerOptions(deliveryManIcon, deliverymanBean.getLatitude(), deliverymanBean.getLongitude(), true);
                }
            }
        }
    }


    private void Cancel() {
        VolleyOperater<LegworkOrderDetailsModel> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("status", -1);
        map.put("cancelSource", "user");
        map.put("cancelReason", "用户自主取消");
        operater.doRequest(Constants.URL_MEGER_LEG_WORK_ORDER, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    getData();
                }
            }
        }, LegworkOrderDetailsModel.class);
    }


    private void getTelNumXY(long agentId, int agentType) {
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
                        regionalHeadPhone = model.getValue().get(i).getPhone();
                    }
                }
            }
        }, CustomerAndComplainPhoneDTOModel.class);
    }


    private long getTimeBetween(String serverTime, String laterTime) {
        if (CheckUtils.isEmptyStr(serverTime) || CheckUtils.isEmptyStr(laterTime)) {
            return 0;
        }
        try {
            Date date1 = sdf.parse(serverTime);
            long time1 = date1.getTime();
            Date date2 = sdf.parse(laterTime);
            long time2 = date2.getTime();
            return time2 - time1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REFRESH) {
//            getData();
//        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.img_service_charge:
                // 计费规则
                Intent mIntent = new Intent(mActivity, LegworkbilingRulesActivity.class);
                mIntent.putExtra("mValueBean", valueBean);
                startActivity(mIntent);
                break;
            case R.id.tv_cancel_order:
                Cancel();
                break;
            case R.id.common_text:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        //拨打电话
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.DIAL");
                        intent.setData(Uri.parse("tel:" + regionalHeadPhone));
                        mActivity.startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", regionalHeadPhone);
                dialog.show();
                break;
            case R.id.tv_payment:
                Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                intent.putExtra("orderId", valueBean.getId());
                intent.putExtra("agentId", valueBean.getAgentId());
                intent.putExtra("isLegwork", true);
                startActivityForResult(intent, REFRESH);
                break;
            case R.id.tv_to_evaluate:
                Intent intent1 = new Intent(mActivity, LegworkEvaluateActivity.class);
                intent1.putExtra("orderId", valueBean.getId());
                intent1.putExtra("agentId", "" + valueBean.getAgentId());
                startActivityForResult(intent1, REFRESH);
                break;
            case R.id.view_bg:
                dismissWindow();
                break;
            case R.id.layout_ok:
                dismissWindow();
                break;
            case R.id.tv_legwork_status:
                if (mStatusDialog != null) {
                    mStatusDialog.show();
                }
                break;
            case R.id.img_phone:
            case R.id.delivery_man_phone_textview:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        //拨打电话
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.DIAL");
                        intent.setData(Uri.parse("tel:" + valueBean.getDeliveryTask().getDeliveryman().getMobile()));
                        mActivity.startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", valueBean.getDeliveryTask().getDeliveryman().getMobile());
                dialog.show();
                break;
            case R.id.tv_refund_desc:
                //退款详情
                Intent intent2 = new Intent(mActivity, OrderRefundInfoActivity.class);
                intent2.putExtra("orderId", valueBean.getId());
                startActivity(intent2);
                break;
            case R.id.img_send_redbag:
                sendRedBag.setVisibility(View.VISIBLE);
                if (shareUtil == null && shareRedBagInfo != null) {
                    shareUtil = new ShareUtil(mActivity, shareRedBagInfo.getTitle(),
                            shareRedBagInfo.getMemo(),
                            shareRedBagInfo.getUrl(), shareRedBagInfo.getImg());
                }
                if (shareUtil != null) shareUtil.showRedBagPopupWindow();
                break;
        }
    }

    /**
     * 初始化订单状态Dialog
     */
    private void initStatusDialog(LegworkOrderDetailsModel.ValueBean valueBean) {
        LinearLayout view = (LinearLayout) View.inflate(this, R.layout.dialog_legwork_status, null);
        TextView tvBack = (TextView) view.findViewById(R.id.tv_legwork_status_back);
        RecyclerView rvStatus = (RecyclerView) view.findViewById(R.id.rv_legwork_status);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStatusDialog != null) {
                    mStatusDialog.dismiss();
                }
            }
        });

        legworkStatusModels.clear();

        if (!TextUtils.isEmpty(valueBean.getCreateTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getCreateTime());
            legworkStatusModel.setName("订单已提交");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getPaymentFinishTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getPaymentFinishTime());
            legworkStatusModel.setName("支付成功");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getAcceptedFinishTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getAcceptedFinishTime());
            legworkStatusModel.setName("骑手已接单");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getHasTakeFinishTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getHasTakeFinishTime());
            legworkStatusModel.setName("骑手已取货");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getOrderDoneTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getOrderDoneTime());
            legworkStatusModel.setName("已完成");
            legworkStatusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(valueBean.getOrderCancelTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(valueBean.getOrderCancelTime());
            legworkStatusModel.setName("已取消");
            legworkStatusModels.add(legworkStatusModel);
        }

        if (legworkStatusModels != null && legworkStatusModels.size() > 0) {
            rvStatus.setLayoutManager(new LinearLayoutManager(this));
            rvStatus.setAdapter(new LegworkStatusAdapter(legworkStatusModels, this));
        }
        mStatusDialog = null;
        mStatusDialog = new Dialog(this, R.style.MyDialogStyle);
        Window dialogWindow = mStatusDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        mStatusDialog.setContentView(view);
        dialogWindow.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
        mStatusDialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        int height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        lp.height = height / 5 * 3;
        lp.width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        lp.alpha = 0.8f;
        dialogWindow.setAttributes(lp);
    }

    private void mPopupWindow(LegworkOrderDetailsModel.ValueBean valueBean) {
        View view = LayoutInflater.from(this).inflate(R.layout.service_charge_item, null);
        View viewBg = view.findViewById(R.id.view_bg);
        RelativeLayout lyoutOk = (RelativeLayout) view.findViewById(R.id.layout_ok);
        TextView tvBaseCharge = (TextView) view.findViewById(R.id.tv_baseCharge);
        TextView tvAddCharge = (TextView) view.findViewById(R.id.tv_addCharge);
        TextView tvStart = (TextView) view.findViewById(R.id.tv_start);
        TextView tvAdd = (TextView) view.findViewById(R.id.tv_add);
        viewBg.setOnClickListener(this);
        lyoutOk.setOnClickListener(this);
        tvBaseCharge.setText("¥" + valueBean.getBaseCharge());
        tvAddCharge.setText("¥" + valueBean.getAddCharge());
        tvStart.setText("起始价（" + valueBean.getDefDistance() + "公里）");
        tvAdd.setText("超出" + valueBean.getDefDistance() + "公里（每公里）加价");
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        if (!popupWindow.isShowing()) {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 0.5f;
            mActivity.getWindow().setAttributes(lp);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            popupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }


    private void hideBaiduMapChildView() {
        deliveryManMapView.showScaleControl(false);
        deliveryManMapView.showZoomControls(false);
        // 隐藏指南针
        UiSettings mUiSettings = baiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);
        // 删除百度地图logo
        deliveryManMapView.removeViewAt(1);


    }


    //在地图上进行标记
    private void putLocationToMarkerOptions(BitmapDescriptor pic, double latitude, double longitude, boolean isDeliveryMan) {
        if (baiduMap != null) {
            if (isDeliveryMan) {
                MyLocationData locData = new MyLocationData.Builder()
                        .latitude(latitude)
                        .longitude(longitude)
                        .build();
                baiduMap.setMyLocationData(locData);
                baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(18).build()));
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, pic);
                baiduMap.setMyLocationConfigeration(config);
                baiduMap.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
                    @Override
                    public boolean onMyLocationClick() {
                        ToastUtils.displayMsg("点击了图标", getApplicationContext());
                        return false;
                    }
                });
            } else {
                LatLng point = new LatLng(latitude, longitude);
                MarkerOptions overlayOptions = new MarkerOptions()
                        .position(point)
                        .icon(pic)
                        .zIndex(15)
                        .draggable(true)
                        .animateType(MarkerOptions.MarkerAnimateType.grow);//设置marker从地上生长出来的动画
                Marker marker = (Marker) baiduMap.addOverlay(overlayOptions);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("nearFriend", nearByFriend);
//        marker.setExtraInfo(bundle);//marker点击事件监听时，可以获取到此时设置的数据
                marker.setToTop();
            }
//            baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//
//                    return false;
//                }
//            });

        }
    }


}
