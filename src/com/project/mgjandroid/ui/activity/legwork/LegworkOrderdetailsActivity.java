package com.project.mgjandroid.ui.activity.legwork;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.DeliveryManInfo;
import com.project.mgjandroid.model.DeliveryManModel;
import com.project.mgjandroid.model.LegworkOrderDetailsModel;
import com.project.mgjandroid.model.LegworkStatusModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.activity.RiderActivity;
import com.project.mgjandroid.ui.adapter.LegworkStatusAdapter;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.RatingBar;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.ui.view.TimeView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.yinglan.scrolllayout.ScrollLayout;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SunXueLiang on 2018-03-12.
 */

public class LegworkOrderdetailsActivity extends BaseActivity {


    private static final String TAG = LegworkOrderdetailsActivity.class.getSimpleName();
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
    @InjectView(R.id.rider_ratingbar)
    private RatingBar ratingBar;
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

    @InjectView(R.id.refresh_imageview)
    ImageView refreshImageView;
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
    @InjectView(R.id.money_info_layout)
    LinearLayout moneyLayout;
    @InjectView(R.id.order_info_layout)
    LinearLayout orderLayout;
    @InjectView(R.id.details)
    LinearLayout details;
    @InjectView(R.id.delivery_name_layout)
    RelativeLayout deliveryNameLayout;
    @InjectView(R.id.delivery_man_info_layout)
    RelativeLayout deliveryManInfoLayout;

    /**
     * 地图上表示距离的view
     */
    private View distanceView;
    private RoundImageView roundImageView;
    private TextView deliveryStateTextView;
    private TextView deliveryStateTv;
    private TextView distanceTextView;

    private BaiduMap baiduMap;
    private Marker marker, shipperMarker;
    private BitmapDescriptor deliveryGoodsIcon, takeGoodsIcon;

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
    private Timer timer;
    private TimerTask timerTask;
    private ScrollLayout.OnScrollChangedListener mScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent >= 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                if (currentProgress < 1) {
                    hideMaps();
                    commonTopBar.setBackgroundColor(getResources().getColor(R.color.color_f5));
                } else {
                    showMaps();
                    commonTopBar.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
                legWorkDetailsLayout.getBackground().mutate().setAlpha(255 - (int) precent);
                commonTopBar.getBackground().mutate().setAlpha(255 - (int) precent);
            }

        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                //退出操作
            } else if (currentStatus.equals(ScrollLayout.Status.CLOSED)) {
            } else if (currentStatus.equals(ScrollLayout.Status.OPENED)) {
            }
        }

        @Override
        public void onChildScroll(int top) {

        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        initTimer();

    }

    @Override
    protected void onStop() {
        super.onStop();
        destroyTimer();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateDeliveryManLocation();
        }
    };


    public void destroyTimer() {
        if (timerTask != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }


    private void initTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, 3000, 6000);
    }


    /**
     * 更新骑手位置
     */
    private void updateDeliveryManLocation() {
        if (valueBean == null) {
            return;
        }

        if (!TextUtils.isEmpty(orderId)) {
            VolleyOperater<DeliveryManModel> operater = new VolleyOperater<>(mActivity);
            HashMap<String, Object> map = new HashMap<>();
            map.put("orderId", orderId);
            operater.doRequest(Constants.URL_FIND_DELIVERY_MAN_BY_ORDER_ID, map, new VolleyOperater.ResponseListener() {
                @Override
                public void onRsp(boolean isSucceed, Object obj) {
                    if (isSucceed && obj != null) {
                        if (obj instanceof String) {
                            return;
                        }
                        DeliveryManModel deliveryManModel = (DeliveryManModel) obj;
                        if (deliveryManModel != null) {
                            DeliveryManInfo deliveryManInfo = deliveryManModel.getValue();
                            updateMapLocation(deliveryManInfo);
                        }
                    }
                }
            }, DeliveryManModel.class);
        }
    }

    private void updateMapLocation(DeliveryManInfo deliveryManInfo) {

        if (valueBean.getStatus() != deliveryManInfo.getOrderStatus()) {
            getData();
        } else {
            double oldLatiTude = 0.0, oldLongiTude = 0.0;
            LegworkOrderDetailsModel.ValueBean.DeliveryTaskBean deliveryTaskBean = valueBean.getDeliveryTask();
            if (deliveryTaskBean != null) {
                LegworkOrderDetailsModel.ValueBean.DeliveryTaskBean.DeliverymanBean deliverymanBean = deliveryTaskBean.getDeliveryman();
                if (deliverymanBean != null) {
                    oldLatiTude = deliveryManInfo.getLatitude();
                    oldLongiTude = deliveryManInfo.getLongitude();
                }
            }
            double currentLatiTude = deliveryManInfo.getLatitude();
            double currentLongiTude = deliveryManInfo.getLongitude();
            //新位置变化后在刷新
            if (oldLatiTude != 0.0 && oldLatiTude != 0.0) {
                if (currentLatiTude != oldLatiTude && currentLongiTude != oldLongiTude) {
                    upadteLocationView(deliveryManInfo.getHeaderImg(), currentLatiTude, currentLongiTude);
                }
            }
        }
    }


    /**
     * 更新地图距离
     */
    private void upadteLocationView(String imageUrl, final double latitude, final double longtude) {
        if (distanceView != null) {
            if (valueBean != null) {
                double shipperLatitude = valueBean.getShipperLatitude();
                double shipperLongitude = valueBean.getShipperLongitude();

                //送货地址
                double userLatitude = valueBean.getUserLatitude();
                double userLongitude = valueBean.getUserLongitude();

                String deliveryState = "";
                String deliveryState1 = "";
                double distance = 0.0;
                if (valueBean.getStatus() == 4) {
                    //取货中
//                    ToastUtils.displayMsg("距离取货地" + deliveryDisatance, getApplicationContext());
                    if (shipperLatitude != 0.0 || shipperLongitude != 0.0) {
                        distance = CommonUtils.getDistance(shipperLongitude, shipperLatitude, longtude, latitude);
                    }
                    if (distance <= 0.05) {
                        deliveryState = "骑手到达取货地";
                        deliveryState1 = "0";
                    } else {
                        deliveryState = "骑手前往取货";
                        deliveryState1 = "距离取货地";
                    }

                } else if (valueBean.getStatus() == 5) {
                    //送货中
                    deliveryState = "骑手正在配送";
                    deliveryState1 = "距离送货地";
                    distance = CommonUtils.getDistance(userLongitude, userLatitude, longtude, latitude);
                    if (shipperMarker != null) {
                        shipperMarker.remove();
                    }
                }
                if (CheckUtils.isNoEmptyStr(deliveryState)) {
                    deliveryStateTextView.setText(deliveryState);
                    if ("0".equals(deliveryState1)) {
                        deliveryStateTv.setVisibility(View.GONE);
                        distanceTextView.setVisibility(View.GONE);
                    } else {
                        deliveryStateTv.setVisibility(View.VISIBLE);
                        distanceTextView.setVisibility(View.VISIBLE);
                    }
                    deliveryStateTv.setText(deliveryState1);
                    NumberFormat nFormat = NumberFormat.getNumberInstance();
                    String result;
                    if (distance < 1) {               //当小于1千米的时候用,用米做单位保留一位小数
                        nFormat.setMaximumFractionDigits(1);    //已可以设置为0，这样跟百度地图APP中计算的一样
                        distance *= 1000;
                        result = nFormat.format(distance) + "m";
                    } else {
                        nFormat.setMaximumFractionDigits(2);
                        result = nFormat.format(distance) + "km";
                    }
                    distanceTextView.setText(result);
                    if (CheckUtils.isNoEmptyStr(imageUrl)) {
                        Glide.with(this)
                                .load(imageUrl)
                                .asBitmap()
                                .error(R.drawable.horsegj_default)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        roundImageView.setImageBitmap(resource);
                                        putDeliveryLocationToMap(BitmapDescriptorFactory.fromView(distanceView), latitude, longtude);
                                    }
                                });
//                        ImageUtils.loadBitmap(mActivity, imageUrl, roundImageView, R.drawable.horsegj_default, Constants.getEndThumbnail(40, 40));
                    } else {
                        roundImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_default_avator));
                    }
                    putDeliveryLocationToMap(BitmapDescriptorFactory.fromView(distanceView), latitude, longtude);
                }

            }
        }
    }

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
        expandImageView.setOnClickListener(this);
        deliveryNameLayout.setOnClickListener(this);
        refreshImageView.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        deliveryManInfoLayout.setOnClickListener(this);
        details.setOnClickListener(this);
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
        View takeView = mInflater.inflate(R.layout.item_legouwork_location, null);
        ImageView takeImg = (ImageView) takeView.findViewById(R.id.icon_type_legwork);
        takeImg.setImageResource(R.drawable.take_goods);
        takeGoodsIcon = BitmapDescriptorFactory.fromView(takeView);

        View deliveryView = mInflater.inflate(R.layout.item_legouwork_location, null);
        ImageView deliveryImg = (ImageView) deliveryView.findViewById(R.id.icon_type_legwork);
        deliveryImg.setImageResource(R.drawable.delivery_goods);
        deliveryGoodsIcon = BitmapDescriptorFactory.fromView(deliveryView);

        setDistanceView();


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


    private void setDistanceView() {
        if (distanceView == null) {
            distanceView = mInflater.inflate(R.layout.item_delivery_man_location, deliveryManMapView, false);
            roundImageView = (RoundImageView) distanceView.findViewById(R.id.delivery_man_avatar);
            deliveryStateTextView = (TextView) distanceView.findViewById(R.id.tv_deliveryman_state);
            deliveryStateTv = (TextView) distanceView.findViewById(R.id.deliveryman_state);
            distanceTextView = (TextView) distanceView.findViewById(R.id.tv_deliveryman_distance);
            LinearLayout linearLayout = (LinearLayout) distanceView.findViewById(R.id.ccccc);
            RelativeLayout.LayoutParams paramsLayout = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();

            Drawable drawable = getResources().getDrawable(R.drawable.right_arrow_gray);
            drawable.setBounds(0, 0, 10, 20);
            deliveryStateTextView.setCompoundDrawables(null, null, drawable, null);
            if (CommonUtils.getScreenWidth(getWindowManager()) >= 1080) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) roundImageView.getLayoutParams();
                params.width = 70;
                params.height = 70;
                roundImageView.setLayoutParams(params);
//

                paramsLayout.topMargin = (int) getResources().getDimension(R.dimen.x5);
                linearLayout.setLayoutParams(paramsLayout);
//                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
//                params1.width = 350;
//                imageView.setLayoutParams(params1);
//
                deliveryStateTextView.setTextSize(10);
                deliveryStateTv.setTextSize(8);
                distanceTextView.setTextSize(8);
            } else {
                paramsLayout.topMargin = (int) getResources().getDimension(R.dimen.x8);
                linearLayout.setLayoutParams(paramsLayout);
            }
        }
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
        destroyTimer();
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
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) legWorkDetailsLayout.getLayoutParams();
//        params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
//        params.bottomMargin = (int) getResources().getDimension(R.dimen.x10);
//        legWorkDetailsLayout.setLayoutParams(params);

        expandImageView.setVisibility(View.VISIBLE);
        refreshImageView.setVisibility(View.VISIBLE);
        moneyLayout.setVisibility(View.GONE);
        orderLayout.setVisibility(View.GONE);
        tvLegworkStatus.setVisibility(View.GONE);
        layoutGoodsInformation.setVisibility(View.GONE);
    }


    /**
     * 隐藏地图
     */
    private void hideMaps() {
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) legWorkDetailsLayout.getLayoutParams();
//        params.bottomMargin = params.rightMargin = params.leftMargin = 0;
//        legWorkDetailsLayout.setLayoutParams(params);

        expandImageView.setVisibility(View.GONE);
        refreshImageView.setVisibility(View.GONE);
        moneyLayout.setVisibility(View.VISIBLE);
        orderLayout.setVisibility(View.VISIBLE);
        tvLegworkStatus.setVisibility(View.VISIBLE);
        if (valueBean.getChildType() == 0) {
            layoutGoodsInformation.setVisibility(View.VISIBLE);
        } else {
            layoutGoodsInformation.setVisibility(View.GONE);
        }
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
            refreshImageView.setVisibility(View.GONE);
            moneyLayout.setVisibility(View.VISIBLE);
            orderLayout.setVisibility(View.VISIBLE);
            tvLegworkStatus.setVisibility(View.VISIBLE);
            legWorkDetailsLayout.setEnable(false);
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
                            tvRefundDesc.setText("退款详情");
                            if (valueBean.getServePrice().equals("0.0") || valueBean.getServePrice().equals("0.00") || valueBean.getServePrice().equals("0")) {
                                refundLayout.setVisibility(View.GONE);
                            } else {
                                refundLayout.setVisibility(View.VISIBLE);
                            }
                        }
//                        if (valueBean.getPaymentState() == 0){
//                            //未支付
//                            refundLayout.setVisibility(View.VISIBLE);
//                            tvRefundDesc.setText("再来一单");
//                        }
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
                        if (valueBean.getPaymentState() == 1 && DateUtils.compareTimeBefore(valueBean.getCreateTime())) {
                            //已经支付
                            tvRefundDesc.setText("退款详情");
                            if (valueBean.getServePrice().equals("0.0") || valueBean.getServePrice().equals("0.00") || valueBean.getServePrice().equals("0")) {
                                refundLayout.setVisibility(View.GONE);
                            } else {
                                refundLayout.setVisibility(View.VISIBLE);
                            }
                        }
//                        if (valueBean.getPaymentState() == 0){
//                            //未支付
//                            refundLayout.setVisibility(View.VISIBLE);
//                            tvRefundDesc.setText("再来一单");
//                        }
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
            LegworkOrderDetailsModel.ValueBean.DeliveryTaskBean deliveryTaskBean = valueBean.getDeliveryTask();
            if (deliveryTaskBean != null) {
                LegworkOrderDetailsModel.ValueBean.DeliveryTaskBean.DeliverymanBean deliverymanBean = deliveryTaskBean.getDeliveryman();
                if (deliverymanBean != null) {
                    tvRiderName.setText(deliverymanBean.getName());
                    if (CheckUtils.isNoEmptyStr(deliverymanBean.getHeaderImg())) {
                        ImageUtils.loadBitmap(mActivity, deliverymanBean.getHeaderImg(), imgRider, R.drawable.horsegj_default, Constants.getEndThumbnail(40, 40));
                    } else {
                        imgRider.setImageDrawable(getResources().getDrawable(R.drawable.icon_default_avator));
                    }
                    ratingBar.setStar(deliverymanBean.getDeliverymanScore().floatValue());
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

            int status = valueBean.getStatus();
            if (status == 4 || status == 5) {
                if (layoutRemarks.getVisibility() == View.GONE) {
                    if (CommonUtils.getScreenWidth(getWindowManager()) < 1080) {
                        legWorkDetailsLayout.setMaxOffset(DipToPx.dip2px(getApplicationContext(), 280));
                    } else {
                        legWorkDetailsLayout.setMaxOffset(DipToPx.dip2px(getApplicationContext(), 240));
//                        legWorkDetailsLayout.setMaxOffset(600);
                    }
//                    legWorkDetailsLayout.setMaxOffset(DipToPx.dip2px(getApplicationContext(), 280));
                } else {
                    if (CommonUtils.getScreenWidth(getWindowManager()) < 1080) {
//                        legWorkDetailsLayout.setMaxOffset(695);
                        legWorkDetailsLayout.setMaxOffset(DipToPx.dip2px(getApplicationContext(), 340));
                    } else {
                        legWorkDetailsLayout.setMaxOffset(DipToPx.dip2px(getApplicationContext(), 310));
                    }
//                    legWorkDetailsLayout.setMaxOffset(DipToPx.dip2px(getApplicationContext(), 350));
                }
                legWorkDetailsLayout.setToOpen();
                legWorkDetailsLayout.invalidate();
            }

        }
    }

    private void takeGoods() {
        Log.e(TAG, "takeGoods::");
        legWorkDetailsLayout.setOnScrollChangedListener(mScrollChangedListener);
        deliveryManMapView.setVisibility(View.VISIBLE);
        deliveryManInfoLayout.setVisibility(View.VISIBLE);
        refreshImageView.setVisibility(View.VISIBLE);
        moneyLayout.setVisibility(View.GONE);
        orderLayout.setVisibility(View.GONE);
        tvLegworkStatus.setVisibility(View.GONE);
        commonTopBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        layoutComplete.setVisibility(View.GONE);
        layoutNoPayment.setVisibility(View.GONE);
        refundLayout.setVisibility(View.GONE);
        tvLegworkStatus.setText("取货中");
        legWorkDetailsLayout.setEnable(true);
//        tvPickUp.setText("等待骑手取货");


        setDetailsLocation(false);

    }

    private void delivery() {
        legWorkDetailsLayout.setOnScrollChangedListener(mScrollChangedListener);
        deliveryManMapView.setVisibility(View.VISIBLE);
        deliveryManInfoLayout.setVisibility(View.VISIBLE);
        refreshImageView.setVisibility(View.VISIBLE);
        moneyLayout.setVisibility(View.GONE);
        orderLayout.setVisibility(View.GONE);
        tvLegworkStatus.setVisibility(View.GONE);
        commonTopBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        layoutComplete.setVisibility(View.GONE);
        layoutNoPayment.setVisibility(View.GONE);
        refundLayout.setVisibility(View.GONE);
//        tvPickUp.setText("骑手正在配送中");
        tvLegworkStatus.setText("配送中");
        legWorkDetailsLayout.setEnable(true);

        setDetailsLocation(true);

    }

    private void setDetailsLocation(boolean isDelivery) {
        if (valueBean != null) {
            //取货地址
            double shipperLatitude = valueBean.getShipperLatitude();
            double shipperLongitude = valueBean.getShipperLongitude();

            //送货地址
            double userLatitude = valueBean.getUserLatitude();
            double userLongitude = valueBean.getUserLongitude();
            putLocationToMarkerOptions(deliveryGoodsIcon, userLatitude, userLongitude);

            //骑手信息
            LegworkOrderDetailsModel.ValueBean.DeliveryTaskBean deliveryTaskBean = valueBean.getDeliveryTask();
            double deliveryLatitude = 0.0;
            double deliveryLongitude = 0.0;
            if (deliveryTaskBean != null) {
                LegworkOrderDetailsModel.ValueBean.DeliveryTaskBean.DeliverymanBean deliverymanBean = deliveryTaskBean.getDeliveryman();
                if (deliverymanBean != null) {
                    deliveryLatitude = deliverymanBean.getLatitude();
                    deliveryLongitude = deliverymanBean.getLongitude();
                    upadteLocationView(deliverymanBean.getHeaderImg(), deliveryLatitude, deliveryLongitude);
                    putDeliveryLocationToMap(BitmapDescriptorFactory.fromView(distanceView), deliveryLatitude, deliveryLongitude);
                }
            }
            if (deliveryLatitude > 0 && deliveryLongitude > 0) {
                if (!isDelivery) {
                    putShipperMarkerOptions(takeGoodsIcon, shipperLatitude, shipperLongitude);
                } else {
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
            case R.id.refresh_imageview:
                updateDeliveryManLocation();
                break;
            case R.id.expand_imageview:
                if (legWorkDetailsLayout != null) {
                    legWorkDetailsLayout.setToOpen();
                }
                break;
            case R.id.delivery_man_info_layout:
            case R.id.delivery_name_layout:
                if (valueBean != null) {
                    Intent mIntent = new Intent(mActivity, RiderActivity.class);
                    mIntent.putExtra("deliverymanId", valueBean.getDeliveryTask().getDeliverymanId());
                    startActivity(mIntent);
                }
                break;
            case R.id.address_layout:
            case R.id.details:
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
                intent1.putExtra("valurDetails", valueBean);
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
                if (valueBean.getPaymentState() == 0) {

                } else {
                    //退款详情
                    Intent intent2 = new Intent(mActivity, OrderRefundInfoActivity.class);
                    intent2.putExtra("orderId", valueBean.getId());
                    startActivity(intent2);
                }
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
    private void putLocationToMarkerOptions(BitmapDescriptor pic, double latitude, double longitude) {
        if (baiduMap != null) {
            LatLng point = new LatLng(latitude, longitude);
            MarkerOptions overlayOptions = new MarkerOptions()
                    .position(point)
                    .icon(pic)
                    .zIndex(15)
                    .draggable(true)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);//设置marker从地上生长出来的动画
            Marker marker = (Marker) baiduMap.addOverlay(overlayOptions);
            marker.setToTop();
        }
    }

    private void putShipperMarkerOptions(BitmapDescriptor pic, double latitude, double longitude) {
        if (baiduMap != null) {
            LatLng point = new LatLng(latitude, longitude);
            MarkerOptions overlayOptions = new MarkerOptions()
                    .position(point)
                    .icon(pic)
                    .zIndex(15)
                    .draggable(true)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);//设置marker从地上生长出来的动画
            shipperMarker = (Marker) baiduMap.addOverlay(overlayOptions);
            shipperMarker.setToTop();
        }
    }


    private void putDeliveryLocationToMap(BitmapDescriptor pic, double latitude, double longitude) {
        if (baiduMap != null) {
            LatLng point = new LatLng(latitude, longitude);
            MarkerOptions overlayOptions = new MarkerOptions()
                    .position(point)
                    .icon(pic)
                    .zIndex(15)
                    .draggable(true)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);//设置marker从地上生长出来的动画
            if (marker != null) {
                marker.remove();
            }
            marker = (Marker) baiduMap.addOverlay(overlayOptions);
            marker.setToTop();
            baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (mStatusDialog != null) {
                        mStatusDialog.show();
                    }
                    return false;
                }
            });

            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(point)
                    .zoom(18)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            baiduMap.setMapStatus(mMapStatusUpdate);

//            MyLocationData locData = new MyLocationData.Builder()
//                    .latitude(latitude)
//                    .longitude(longitude)
//                    .build();
//            baiduMap.setMyLocationData(locData);
//            baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(18).build()));
//            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, pic);
//            baiduMap.setMyLocationConfiguration(config);
//            baiduMap.setMyLocationEnabled(true);
//            baiduMap.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
//                @Override
//                public boolean onMyLocationClick() {
//                    if (mStatusDialog != null) {
//                        mStatusDialog.show();
//                    }
//                    return false;
//                }
//            });
        }
    }


}
