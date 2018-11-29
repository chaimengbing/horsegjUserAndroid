package com.project.mgjandroid.ui.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.github.mzule.activityrouter.annotation.Router;
import com.google.gson.JsonArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.constants.PaymentMode;
import com.project.mgjandroid.constants.ShipmentMode;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.CustomerAndComplainPhoneDTOModel;
import com.project.mgjandroid.model.GetRedPackageModel;
import com.project.mgjandroid.model.GroupOrderDetailModel;
import com.project.mgjandroid.model.LegworkStatusModel;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.model.SubmitOrderModel.ValueEntity;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.net.VolleyOperater.ResponseListener;
import com.project.mgjandroid.ui.activity.legwork.LegworkEvaluateActivity;
import com.project.mgjandroid.ui.adapter.LegworkStatusAdapter;
import com.project.mgjandroid.ui.fragment.OrderListFragment;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.CommonDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.ui.view.TimeTextView;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.ui.view.pullableview.PullToRefreshLayout;
import com.project.mgjandroid.ui.view.pullableview.PullToRefreshLayout.OnRefreshListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情
 *
 * @author jian
 */
@SuppressLint("NewApi")
@Router("orderdetail/:orderId")
public class OrderDetailActivity extends BaseActivity implements OnClickListener {
    public static final String ORDER_ID = "orderId";
    public static final String ORDER_LIST_ENTITY = "order_list_entity";
    public static final String SUBMIT_ORDER_ENTITY = "submit_order_entity";
    /**
     * 取消订单
     */
    private static final int STATE_CANCEL = -1;
    /**
     * 订单创建
     */
    private static final int STATE_INIT = 0;
    /**
     * 等待付款
     */
    private static final int STATE_WAIT_PAY = 1;
    /**
     * 等待商家确认
     */
    private static final int STATE_WAIT_CONFIRM = 2;
    /**
     * 商家已接单
     */
    private static final int STATE_ACCEPED = 3;
    /**
     * 配送员取货中
     */
    private static final int STATE_WAIT_TAKE = 4;
    /**
     * 配送员已取货
     */
    private static final int STATE_HAS_TAKE = 5;
    /**
     * 骑手配送中
     */
    private static final int STATE_WAIT_DELIVERY = 6;
    /**
     * 完成
     */
    private static final int STATE_DONE = 7;

    @InjectView(R.id.order_detail_act_layout)
    private LinearLayout layoutDetail;
    @InjectView(R.id.order_detail_act_iv_back)
    private ImageView imgBack;
    @InjectView(R.id.order_detail_act_iv_iphone)
    private ImageView imgPhone;
    @InjectView(R.id.order_detail_act_tv_title)
    private TextView tvTitle;
    @InjectView(R.id.order_detail_act_pull_layout)
    private PullToRefreshLayout refreshLayout;
    @InjectView(R.id.order_detail_order_state_img_state)
    private ImageView imgState;
    @InjectView(R.id.order_detail_order_state_tv_state)
    private TextView tvState;
    @InjectView(R.id.order_detail_order_state_tv_des)
    private TextView tvStateDes;
    @InjectView(R.id.order_detail_order_state_tv_commit)
    private TextView tvStateLeft;
    @InjectView(R.id.order_detail_order_state_img_commit)
    private ImageView imgStateLeft;
    @InjectView(R.id.order_detail_order_state_tv_receive)
    private TextView tvStateMidL;
    @InjectView(R.id.order_detail_order_state_img_receive)
    private ImageView imgStateMidL;
    @InjectView(R.id.order_detail_order_state_tv_take_goods)
    private TextView tvStateMidR;
    @InjectView(R.id.order_detail_order_state_img_take_goods)
    private ImageView imgStateMidR;
    @InjectView(R.id.order_detail_order_state_tv_arrive)
    private TextView tvStateRight;
    @InjectView(R.id.order_detail_order_state_img_arrive)
    private ImageView imgStateRight;
    @InjectView(R.id.order_detail_order_state_layout_mid_l)
    private RelativeLayout layoutStateMidL;
    @InjectView(R.id.order_detail_order_state_layout_mid_r)
    private RelativeLayout layoutStateMidR;
    @InjectView(R.id.order_detail_ad_image)
    private ImageView ivAdIamge;
    @InjectView(R.id.order_detail_commercial_layout)
    private RelativeLayout layoutCommercial;
    @InjectView(R.id.order_detail_commercial_img)
    private RoundImageView imgCommercial;
    @InjectView(R.id.order_detail_commercial_name)
    private TextView tvCommercialName;
    @InjectView(R.id.order_detail_commercial_name_arrow)
    private ImageView tvCommercialArrow;
    @InjectView(R.id.order_detail_commercial_meal_layout)
    private LinearLayout layoutMeal;
    @InjectView(R.id.order_detail_commercial_ship_layout)
    private LinearLayout layoutShip;
    @InjectView(R.id.order_detail_commercial_campaigns_layout)
    private LinearLayout layoutCampaigns;
    @InjectView(R.id.order_detail_commercial_buy_again)
    private TextView tvBuyAgain;
    @InjectView(R.id.order_detail_commercial_meal_all_money)
    private TextView tvAllMoney;
    @InjectView(R.id.order_detail_commercial_meal_all_goods)
    private TextView tvAllGoods;
    @InjectView(R.id.order_detail_tv_shipping_style)
    private TextView tvShippingStyle;
    @InjectView(R.id.order_detail_shipping_driver_line)
    private View vDriverLine;
    @InjectView(R.id.order_detail_tv_shipping_driver_layout)
    private LinearLayout llDriverInfo;
    @InjectView(R.id.order_detail_tv_shipping_driver_name)
    private TextView tvDriverName;
    @InjectView(R.id.order_detail_tv_shipping_driver_phone)
    private TextView tvDriverPhone;
    @InjectView(R.id.order_detail_detail_tv_order_num)
    private TextView tvOrderNum;
    @InjectView(R.id.order_detail_detail_name)
    private TextView tvPersonName;
    @InjectView(R.id.order_detail_detail_phone)
    private TextView tvPersonPhone;
    @InjectView(R.id.order_detail_detail_address)
    private TextView tvPersonAddress;
    @InjectView(R.id.order_detail_detail_paytype)
    private TextView tvPersonPayType;
    @InjectView(R.id.order_detail_detail_caution)
    private TextView tvOrderCaution;
    @InjectView(R.id.order_detail_detail_buy_time)
    private TextView tvOrderTime;
    @InjectView(R.id.order_detail_detail_arrive_time)
    private TextView tvArriveTime;

    @InjectView(R.id.order_detail_act_bottom_un_pay)
    private LinearLayout unPayBottonLayout;
    @InjectView(R.id.order_detail_act_bottom_wait_arrive)
    private LinearLayout waitArriveLayout;

    @InjectView(R.id.order_detail_act_complain)
    private TextView tvComplain;
    @InjectView(R.id.order_detail_act_evaluate)
    private TextView tvEvaluate;
    @InjectView(R.id.order_detail_act_buy_again)
    private TextView tvBuyBottomAgain;
    @InjectView(R.id.order_detail_act_un_pay_cancel)
    private TextView tvUnPayCancel;
    @InjectView(R.id.order_detail_act_un_pay_go_pay)
    private TimeTextView tvUnPayGoPay;
    @InjectView(R.id.order_detail_act_bottom_cancel)
    private TextView tvBottomCancel;
    @InjectView(R.id.order_detail_act_wait_arrive_complain)
    private TextView tvWaitComplain;
    @InjectView(R.id.order_detail_act_wait_arrive_cuidan)
    private TextView tvWaitCuiDan;
    @InjectView(R.id.order_detail_act_wait_arrive_quit)
    private TextView tvWaitQuit;
    @InjectView(R.id.order_detail_act_wait_arrive_sure)
    private TextView tvWaitSure;
    @InjectView(R.id.driver_map)
    private MapView mapView;
    @InjectView(R.id.map_cache)
    private ImageView mapCache;
    @InjectView(R.id.order_detail_act_bottom_bar)
    private RelativeLayout rlBottomBar;
    @InjectView(R.id.order_detail_act_iv_tuihuo)
    private TextView tvTuihuo;
    @InjectView(R.id.order_detail_act_confirm_delivery)
    private TextView tvShouhuo;
    @InjectView(R.id.rl_order_state)
    private RelativeLayout rlOrderState;
    @InjectView(R.id.iv_arrow)
    private ImageView ivArrow;
    @InjectView(R.id.img_send_redbag)
    private ImageView sendRedBag;
    @InjectView(R.id.rLayout_right)
    private RelativeLayout rLayoutRight;

    String orderId;
    private boolean refreshFlag = true;
    private ValueEntity submitOrderEntity;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private final static int ONE_MINUTE = 60 * 1000;
    private BaiduMap baiduMap;
    private CallPhoneDialog dialog;
    private Dialog avatarDialog;
    private Dialog callNumDialog;
    private NewOrderFragmentModel.ValueEntity valueEntity;
    private SimpleDateFormat sdf;
    private CommonDialog mRedDialog;
    private MLoadingDialog loadingDialog;
    private String errorMsg;
    private CustomDialog goodsDialog;
    private JSONObject previewJsonData;
    private String driverPhone;
    private String agentPhone;
    private CallPhoneDialog shouhuoDialog;
    private String agentMobile;
    private Button dialog_bt_take_photo;
    private Button dialog_bt_pick_photo;

    private String constomer;
    private String mgjName = "总部热线", areaName = "区域热线";
    private String areaPhone;
    private String mgjPhone;
    private Dialog mStatusDialog;
    private PopupWindow popupWindow;
    private ShareUtil shareUtil;
    private ValueEntity.ShareRedBagInfo shareRedBagInfo;
    private boolean isCanIn;
    private long currentMS;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.order_detail_act);
        Injector.get(this).inject();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        loadingDialog = new MLoadingDialog();
        init();
    }

    private void init() {
        orderId = getIntent().getStringExtra(ORDER_ID);
        isCanIn = getIntent().getBooleanExtra("isCanIn", false);
        valueEntity = (NewOrderFragmentModel.ValueEntity) getIntent().getSerializableExtra(ORDER_LIST_ENTITY);
        submitOrderEntity = (ValueEntity) getIntent().getSerializableExtra(SUBMIT_ORDER_ENTITY);
        if (orderId == null) {
            orderId = submitOrderEntity.getOrderItems().get(0).getOrderId();
        }
        if (submitOrderEntity != null) {
            if (submitOrderEntity.getMerchant() != null) {
                tvTitle.setText(submitOrderEntity.getMerchant().getName());
            }
            int agentType = 0;
            if (submitOrderEntity.getType() == 1) {
                agentType = 1;
            } else if (submitOrderEntity.getType() == 3) {
                agentType = 3;
            }
            getTelNumId(agentType);
            showDetails(submitOrderEntity);
        } else {
            getData(true);
        }
        rLayoutRight.setOnClickListener(this);
        sendRedBag.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgPhone.setOnClickListener(this);
        ivAdIamge.setOnClickListener(this);
        tvUnPayCancel.setOnClickListener(this);
        tvUnPayGoPay.setOnClickListener(this);
        tvBuyBottomAgain.setOnClickListener(this);
        tvBottomCancel.setOnClickListener(this);
        tvWaitComplain.setOnClickListener(this);
        tvWaitCuiDan.setOnClickListener(this);
        tvWaitQuit.setOnClickListener(this);
        tvWaitSure.setOnClickListener(this);
        tvComplain.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
        tvTuihuo.setOnClickListener(this);
        tvShouhuo.setOnClickListener(this);
        rlOrderState.setOnClickListener(this);
        tvStateDes.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                if (refreshFlag) {
                    getData(false);
                }
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });
        baiduMap = mapView.getMap();
        hideBaiduMapChildView();
        baiduMap.setMyLocationEnabled(true);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("hasRedPackage")) {
            boolean hasRedPackage = intent.getBooleanExtra("hasRedPackage", false);
            if (hasRedPackage) {
                checkHasRedPackage(orderId);
            }
        }
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

    private void showRedBag() {
        sendRedBag.setVisibility(View.GONE);
        View view = LayoutInflater.from(this).inflate(R.layout.send_redbag, null);
        TextView tvSure = (TextView) view.findViewById(R.id.sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.cancel);
        tvSure.setOnClickListener(new OnClickListener() {
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
        tvCancel.setOnClickListener(new OnClickListener() {
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

    private void checkHasRedPackage(String orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<GetRedPackageModel> operater = new VolleyOperater<>(OrderDetailActivity.this);
        operater.doRequest(Constants.URL_GET_EXTRA_RED_BAG, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    GetRedPackageModel getRedPackageModel = (GetRedPackageModel) obj;
                    initRedPackage(getRedPackageModel.getValue().getAmt());
                }
            }
        }, GetRedPackageModel.class);
    }

    private void initRedPackage(BigDecimal amt) {
        View view = mInflater.inflate(R.layout.dialog_red_package, null);
        TextView tvMoney = (TextView) view.findViewById(R.id.red_package_money);
        tvMoney.setText(String.valueOf(amt.intValue()));
        TextView tvToSee = (TextView) view.findViewById(R.id.red_package_show);
        tvToSee.setOnClickListener(this);
        ImageView imgDelete = (ImageView) view.findViewById(R.id.red_package_delete);
        imgDelete.setOnClickListener(this);
        mRedDialog = new CommonDialog(mActivity, view);
        mRedDialog.setCanceledOnTouchOutside(false);
        mRedDialog.show();
        scaleIn(view);
    }

    public void scaleIn(View view) {
        ObjectAnimator animatorSX = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        animatorSX.setDuration(1200);
        animatorSX.setRepeatCount(0);
        animatorSX.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator animatorSY = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        animatorSY.setDuration(1200);
        animatorSY.setRepeatCount(0);
        animatorSY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorSX).with(animatorSY);
        animSet.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rLayout_right:
                Intent mIntent = new Intent(mActivity, RiderActivity.class);
                mIntent.putExtra("deliverymanId", submitOrderEntity.getDeliveryTask().getDeliverymanId());
                startActivityForResult(mIntent, 2000);
                break;
            case R.id.order_detail_act_iv_back:
                //onBackPressed();
                back();
                break;
            case R.id.order_detail_act_iv_iphone:
                initDialog();
//			if(submitOrderEntity != null && submitOrderEntity.getMerchant() != null && !TextUtils.isEmpty(submitOrderEntity.getMerchant().getContacts())){
//				Uri uri = Uri.parse("tel:" + submitOrderEntity.getMerchant().getContacts());
//				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
//				startActivity(intent);
//			}
                break;
            case R.id.order_detail_ad_image:
                break;
            case R.id.order_detail_commercial_layout:
                if (submitOrderEntity != null) {
                    Intent intent = new Intent(this, CommercialActivity.class);
                    if (submitOrderEntity.getMerchant().getType() == 0) {
                        intent.putExtra(CommercialActivity.MERCHANT_ID, submitOrderEntity.getMerchant().getId().intValue());
                        startActivity(intent);
                    } else {
//					setResult(HomeActivity.SUPERMARKET);
//					finish();
                    }
                }
                break;
            case R.id.order_detail_commercial_buy_again:
            case R.id.order_detail_act_buy_again:
                HashMap<String, Object> map = new HashMap<>();
                map.put("loginToken", App.getUserInfo().getToken());
                map.put("userId", App.getUserInfo().getId());
                map.put("orderId", submitOrderEntity.getId());
                double latitude = Double.parseDouble(PreferenceUtils.getLocation(OrderDetailActivity.this)[0]);
                double longitude = Double.parseDouble(PreferenceUtils.getLocation(OrderDetailActivity.this)[1]);
                map.put("longitude", longitude);
                map.put("latitude", latitude);
                VolleyOperater<ConfirmOrderModel> operaterOnce = new VolleyOperater<>(OrderDetailActivity.this);
                loadingDialog.show(getFragmentManager(), "");
                operaterOnce.doRequest(Constants.URL_AGAIN_ORDER_PREVIEW, map, new VolleyOperater.ResponseListener() {
                    @Override
                    public void onRsp(boolean isSucceed, Object obj) {
                        if (isSucceed && obj != null) {
                            if (obj instanceof String) {
                                errorMsg = (String) obj;
                                ToastUtils.displayMsg(errorMsg, mActivity);
                            } else {
                                ConfirmOrderModel confirmOrderModel = (ConfirmOrderModel) obj;
                                if (confirmOrderModel.isSuccess()) {
                                    Integer type = confirmOrderModel.getValue().getAgainOrderStatus();
                                    if (type != null && type == 0) {//商家已下线
                                        ToastUtils.displayMsg(confirmOrderModel.getValue().getAgainOrderTip(), mActivity);
                                    } else if (type != null && (type == 1 || type == 2)) {//商品已下线
                                        final int id = (int) confirmOrderModel.getValue().getMerchantId();
                                        List<ConfirmOrderModel.ValueEntity.OrderItemsEntity> list = confirmOrderModel.getValue().getOrderItems();
                                        ArrayList<Map<String, Object>> map = new ArrayList<>();
                                        for (ConfirmOrderModel.ValueEntity.OrderItemsEntity goods : list) {
                                            HashMap<String, Object> m = new HashMap<>();
                                            m.put("id", goods.getId());
                                            m.put("quantity", goods.getQuantity());
                                            m.put("specId", goods.getSpecId());
                                            m.put("categoryId", goods.getCategoryId());
                                            map.add(m);
                                        }
                                        Map<String, Object> jsonMap = new HashMap<>();
                                        jsonMap.put("goodsJson", map);
                                        final JSONObject goodsJson = new JSONObject(jsonMap);
                                        goodsDialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
                                            @Override
                                            public void onSure() {
                                                Intent intent1 = new Intent(mActivity, CommercialActivity.class);
                                                intent1.putExtra(CommercialActivity.MERCHANT_ID, id);
                                                intent1.putExtra("againOrder", true);
                                                intent1.putExtra("onceMoreOrder", goodsJson);
                                                startActivity(intent1);
                                                goodsDialog.dismiss();
                                            }

                                            @Override
                                            public void onExit() {
                                                goodsDialog.dismiss();
                                            }
                                        }, "好的", "不用了", "提示", confirmOrderModel.getValue().getAgainOrderTip());
                                        goodsDialog.show();
                                    } else {
                                        List<ConfirmOrderModel.ValueEntity.OrderItemsEntity> pickGoods = confirmOrderModel.getValue().getOrderItems();
                                        String caution = confirmOrderModel.getValue().getCaution();
                                        ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
                                        for (ConfirmOrderModel.ValueEntity.OrderItemsEntity goods : pickGoods) {
                                            HashMap<String, Object> m = new HashMap<>();
                                            m.put("id", goods.getId());
                                            m.put("quantity", goods.getQuantity());
                                            m.put("specId", goods.getSpecId());
                                            if (!CheckUtils.isEmptyStr(goods.getAttributes())) {
                                                m.put("attributes", goods.getAttributes());
                                            }
                                            orderItems.add(m);
                                        }
                                        Map<String, Object> jsonMap = new HashMap<>();
                                        jsonMap.put("merchantId", confirmOrderModel.getValue().getMerchantId());
                                        jsonMap.put("loginToken", App.getUserInfo().getToken());
                                        jsonMap.put("userId", App.getUserInfo().getId());
                                        jsonMap.put("orderItems", orderItems);
                                        previewJsonData = new JSONObject(jsonMap);
                                        Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
                                        intent.putExtra("confirmOrderModel", confirmOrderModel);
                                        intent.putExtra("onceMoreOrder", previewJsonData);
                                        if (caution != null && !caution.isEmpty()) {
                                            intent.putExtra("caution", caution);
                                        }
                                        startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
                                    }
                                } else {
                                    ToastUtils.displayMsg("商户加载失败", mActivity);
                                }
                            }
                        }
                        loadingDialog.dismiss();
                    }
                }, ConfirmOrderModel.class);
                break;

            case R.id.order_detail_act_complain:
            case R.id.order_detail_act_wait_arrive_complain:
//                getTelNumId();
                initAvatarDialog();
                break;
            case R.id.order_detail_act_evaluate://完成时评价
                Intent intent = new Intent(mActivity, NewEvaluateActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("agentId", submitOrderEntity.getAgentId());
                intent.putExtra("submitOrderEntity", submitOrderEntity);
                if (submitOrderEntity.getDeliveryTask() != null) {
                    intent.putExtra("hasDriver", true);
                } else {
                    intent.putExtra("hasDriver", false);
                }
                startActivityForResult(intent, 1);

                break;

            case R.id.order_detail_act_un_pay_go_pay:
                Intent intent1 = new Intent(this, OnlinePayActivity.class);
                intent1.putExtra("orderId", valueEntity.getId());
                intent1.putExtra("agentId", valueEntity.getAgentId());
                startActivityForResult(intent1, OrderListFragment.REFRESH);
                break;
            case R.id.order_detail_act_bottom_cancel:
            case R.id.order_detail_act_un_pay_cancel:
                dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                    @Override
                    public void onSure() {
                        cancelOrder();
                        dialog.dismiss();
                    }

                    @Override
                    public void onExit() {
                        dialog.dismiss();
                    }
                }, "", "确定要取消订单？", "确定", "还是不了");
                dialog.show();
                break;

            case R.id.order_detail_act_wait_arrive_cuidan:
                break;
            case R.id.order_detail_act_wait_arrive_quit:
                break;
            case R.id.order_detail_act_wait_arrive_sure:
                break;
            case R.id.btn_take_photo:
                if (CheckUtils.isEmptyStr(mgjPhone)) break;
                if (dialog_bt_take_photo.getText().toString().trim().equals("联系商家")) {
                    List<String> numList = JSONArray.parseArray(submitOrderEntity.getMerchant().getContactsArray(), String.class);
                    if (numList != null && numList.size() > 1) {
                        showContacts(numList);
                        return;
                    } else {
                        agentMobile = submitOrderEntity.getMerchant().getContacts();
                    }
                } else {
                    agentMobile = mgjPhone;
                }
                showCallDialog(agentMobile);
                avatarDialog.dismiss();
                break;
            case R.id.btn_pick_photo:
                if (CheckUtils.isEmptyStr(constomer) || CheckUtils.isEmptyStr(areaPhone)) break;
                if (dialog_bt_pick_photo.getText().toString().trim().equals("联系客服")) {
                    agentPhone = constomer;
                } else {
                    agentPhone = areaPhone;
                }
                showCallDialog(agentPhone);
                avatarDialog.dismiss();
                break;

            case R.id.red_package_show:
                startActivity(new Intent(this, MyRedBagActivity.class));
                break;

            case R.id.red_package_delete:
                if (mRedDialog != null && mRedDialog.isShowing()) {
                    mRedDialog.dismiss();
                }
                break;
            case R.id.order_detail_tv_shipping_driver_phone://给骑手打电话
                showCallDialog(driverPhone);
                break;
            case R.id.order_detail_act_iv_tuihuo:
                Intent tuihuo = new Intent(mActivity, ApplyAdultsActivity.class);
                tuihuo.putExtra("submitOrderEntity", submitOrderEntity);
                startActivity(tuihuo);
                break;
            case R.id.order_detail_act_confirm_delivery://确认收货功能
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
                    }, "", "是否确认收货", "确认", "取消");
                }
                shouhuoDialog.show();
                break;
            case R.id.rl_order_state:
                //状态
                if (mStatusDialog != null) {
                    mStatusDialog.show();
                }
                break;
            case R.id.order_detail_order_state_tv_des:
                //退款
                if (submitOrderEntity != null && submitOrderEntity.getOrderFlowStatus() == -1 && submitOrderEntity.getPaymentState() == 1 && DateUtils.compareTimeBefore(submitOrderEntity.getCreateTime())) {
                    if (!StringUtils.BigDecimal2Str(submitOrderEntity.getTotalPrice()).equals("0")) {
                        Intent intent2 = new Intent(mActivity, OrderRefundInfoActivity.class);
                        intent2.putExtra("orderId", submitOrderEntity.getId());
                        startActivity(intent2);
                    }
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
            default:
                break;
        }
    }

    private void showContacts(final List<String> contactsArray) {
        if (contactsArray != null) {
            if (avatarDialog != null) {
                avatarDialog.dismiss();
            }
            callNumDialog = new Dialog(this, R.style.fullDialog);
            RelativeLayout contentView = (RelativeLayout) View.inflate(this, R.layout.pick_or_take_photo_dialog, null);
            Button dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
            Button dialog_bt_callnum = (Button) contentView.findViewById(R.id.btn_callnum);
            Button dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
            Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
            View line = contentView.findViewById(R.id.line1);

            line.setVisibility(View.GONE);
            dialog_bt_callnum.setVisibility(View.GONE);
            dialog_bt_pick_photo.setVisibility(View.GONE);

            if (contactsArray.size() > 0) {
                dialog_bt_take_photo.setText(contactsArray.get(0));
            }
            if (contactsArray.size() > 1) {
                dialog_bt_callnum.setText(contactsArray.get(1));
                line.setVisibility(View.VISIBLE);
                dialog_bt_callnum.setVisibility(View.VISIBLE);
            }
            if (contactsArray.size() > 2) {
                dialog_bt_pick_photo.setText(contactsArray.get(2));
                dialog_bt_pick_photo.setVisibility(View.VISIBLE);
            }

            dialog_bt_take_photo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCallDialog(contactsArray.get(0));
                }
            });

            dialog_bt_callnum.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCallDialog(contactsArray.get(1));
                }
            });

            dialog_bt_pick_photo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCallDialog(contactsArray.get(2));
                }
            });
            dialog_bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callNumDialog.dismiss();
                }
            });
            callNumDialog.setContentView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            if (layoutDetail.getVisibility() == View.VISIBLE) {
                callNumDialog.show();
            }
        }

//        final MaterialDialog alert = new MaterialDialog(this);
//
//        final ArrayAdapter<String> arrayAdapter
//                = new ArrayAdapter<>(this,
//                R.layout.item_call);
//        for (int j = 0; j < contactsArray.size(); j++) {
//            arrayAdapter.add(contactsArray.get(j));
//        }
//
//        final ListView listView = new ListView(this);
//        listView.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        View v = new View(this);
//        v.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 0));
//        listView.addHeaderView(v);
//        listView.addFooterView(v);
//        listView.setDividerHeight(1);
//        listView.setHeaderDividersEnabled(true);
//        listView.setFooterDividersEnabled(true);
//        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        if (contactsArray.size() > 4) {
//            listView.setVerticalScrollBarEnabled(true);
//        } else {
//            listView.setVerticalScrollBarEnabled(false);
//        }
//        listView.setAdapter(arrayAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showCallDialog(contactsArray.get(position - 1));
//                alert.dismiss();
//            }
//        });
//
//        alert.setTitle("联系商家")
//                .setContentView(listView)
//                .setPositiveButton(getString(R.string.cancel), new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alert.dismiss();
//                    }
//                });
//
//        alert.show();
    }

    private void showCallDialog(final String mobild) {

        if (callNumDialog != null) {
            callNumDialog.dismiss();
        }
        dialog = new CallPhoneDialog(OrderDetailActivity.this, new CallPhoneDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                //拨打电话
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                //submitOrderEntity.getMerchant().getContacts() 商家电话
                intent.setData(Uri.parse("tel:" + mobild));
                OrderDetailActivity.this.startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void onExit() {
                dialog.dismiss();
            }
        }, "", mobild);
        dialog.show();
    }

    private void confirmDelivery() {
        Map<String, Object> map = new HashMap<>();
        map.put(OrderDetailActivity.ORDER_ID, orderId);
        VolleyOperater<GroupOrderDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_DONE_ORDER_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    Intent intent = new Intent(mActivity, EvaluateActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("valueEntity", valueEntity);
                    intent.putExtra("submitOrderEntity", submitOrderEntity);
                    if (submitOrderEntity.getDeliveryTask() != null) {
                        intent.putExtra("hasDriver", true);
                    } else {
                        intent.putExtra("hasDriver", false);
                    }
                    startActivityForResult(intent, 3);
                } else {

                }
            }
        }, GroupOrderDetailModel.class);
    }

    private void cancelOrder() {
        Map<String, Object> map = new HashMap<>();
        map.put(OrderDetailActivity.ORDER_ID, orderId);
        VolleyOperater<GroupOrderDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CANCEL_ORDER_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    getData(true);
                } else {

                }
            }
        }, GroupOrderDetailModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3) {
            getData(true);
        }
        if (data != null) {
            switch (resultCode) {
                case RESULT_OK:
                    getData(true);
                    break;
            }
        }
    }

    private void getData(final boolean isAutoRefresh) {
        refreshFlag = false;
        Map<String, Object> map = new HashMap<>();
        map.put(ORDER_ID, orderId);
        VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<>(OrderDetailActivity.this);
        operater.doRequest(Constants.URL_NEW_ORDER_DETAIL, map, new ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (!isAutoRefresh) {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    SubmitOrderModel orderDetail = (SubmitOrderModel) obj;
                    submitOrderEntity = orderDetail.getValue();
                    shareRedBagInfo = submitOrderEntity.getShareRedBagInfo();
                    if (shareRedBagInfo == null) {
                        sendRedBag.setVisibility(View.GONE);
                    } else {
                        sendRedBag.setVisibility(View.VISIBLE);
                    }
                    int agentType = 0;
                    if (orderDetail.getValue().getType() == 1) {
                        agentType = 1;
                    } else if (orderDetail.getValue().getType() == 3) {
                        agentType = 3;
                    }
                    getTelNumId(agentType);
                    showDetails(orderDetail.getValue());
                    initStatusDialog(orderDetail.getValue());
                } else {

                }
            }
        }, SubmitOrderModel.class);
    }

    ArrayList<LegworkStatusModel> statusModels = new ArrayList<>();

    private void initStatusDialog(SubmitOrderModel.ValueEntity orderDetail) {
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

        statusModels.clear();

        if (!TextUtils.isEmpty(orderDetail.getCreateTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(orderDetail.getCreateTime());
            legworkStatusModel.setName("订单已提交");
            statusModels.add(legworkStatusModel);
        }
        if (!TextUtils.isEmpty(orderDetail.getPaymentFinishTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(orderDetail.getPaymentFinishTime());
            legworkStatusModel.setName("支付成功");
            statusModels.add(legworkStatusModel);
        }
        if (orderDetail.getDeliveryTask() != null) {
            if (!TextUtils.isEmpty(orderDetail.getDeliveryTask().getOrderConfirmTime())) {
                LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
                legworkStatusModel.setTime(orderDetail.getDeliveryTask().getOrderConfirmTime());
                legworkStatusModel.setName("商家已接单");
                statusModels.add(legworkStatusModel);
            }
            if (!TextUtils.isEmpty(orderDetail.getDeliveryTask().getAcceptTime())) {
                LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
                legworkStatusModel.setTime(orderDetail.getDeliveryTask().getAcceptTime());
                legworkStatusModel.setName("骑手已接单");
                statusModels.add(legworkStatusModel);
            }
            if (!TextUtils.isEmpty(orderDetail.getDeliveryTask().getArrivalMerchantTime())) {
                LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
                legworkStatusModel.setTime(orderDetail.getDeliveryTask().getArrivalMerchantTime());
                legworkStatusModel.setName("骑手已取餐");
                statusModels.add(legworkStatusModel);
            }
            if (!TextUtils.isEmpty(orderDetail.getDeliveryTask().getArrivalMerchantTime())) {
                LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
                legworkStatusModel.setTime(orderDetail.getDeliveryTask().getArrivalMerchantTime());
                legworkStatusModel.setName("骑手已送达");
                statusModels.add(legworkStatusModel);
            }
        }
        if (!TextUtils.isEmpty(orderDetail.getOrderDoneTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(orderDetail.getOrderDoneTime());
            legworkStatusModel.setName("已完成");
            statusModels.add(legworkStatusModel);
        }
        if (orderDetail.getOrderFlowStatus() == -1 && !TextUtils.isEmpty(orderDetail.getModifyTime())) {
            LegworkStatusModel legworkStatusModel = new LegworkStatusModel();
            legworkStatusModel.setTime(orderDetail.getModifyTime());
            legworkStatusModel.setName("订单已取消");
            statusModels.add(legworkStatusModel);
        }

        if (statusModels != null && statusModels.size() > 0) {
            rvStatus.setLayoutManager(new LinearLayoutManager(this));
            rvStatus.setAdapter(new LegworkStatusAdapter(statusModels, this));
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

    /**
     * 显示详情
     */
    private void showDetails(ValueEntity submitOrderEntity) {
        if (submitOrderEntity != null) {
            layoutDetail.setVisibility(View.VISIBLE);
            tvTitle.setText(submitOrderEntity.getMerchant().getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fade_fade_in);
                animator.setTarget(layoutDetail);
                animator.start();
            }
//            String expectDeliveryTime = submitOrderEntity.getExpectArrivalTime();
//            if (CheckUtils.isNoEmptyStr(expectDeliveryTime)) {
//                if (expectDeliveryTime.equals("1")) {
//                    String paymentFinishTime = submitOrderEntity.getPaymentFinishTime();
//                    if (TextUtils.isEmpty(paymentFinishTime)) {
//                        tvStateDes.setText("预计送达时间: 立即送达");
//                    } else {
//                        try {
//                            SimpleDateFormat sdf = new SimpleDateFormat(CommonUtils.yyyy_MM_dd_HH_mm_ss);
//                            long timeStr = sdf.parse(paymentFinishTime).getTime() + submitOrderEntity.getMerchant().getAvgDeliveryTime() * 60 * 1000;
//                            String s = CommonUtils.formatTime(timeStr, CommonUtils.yyyy_MM_dd_HH_mm);
//                            tvStateDes.setText("预计送达时间: 立即送达  [" + s + "]");
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                            tvStateDes.setText("预计送达时间: 立即送达");
//                        }
//                    }
//                } else {
//                    tvStateDes.setText("预计送达时间: " + CommonUtils.formatTime(Long.parseLong(expectDeliveryTime), CommonUtils.yyyy_MM_dd_HH_mm));
//                }
//            }
            showState(submitOrderEntity);
            showCommercial(submitOrderEntity);
            showDelivery(submitOrderEntity);
            showDetail(submitOrderEntity);
            if (submitOrderEntity.isCanReturnOrChange()) {
                tvTuihuo.setVisibility(View.INVISIBLE);
            } else {
                tvTuihuo.setVisibility(View.INVISIBLE);
            }
        } else {
            layoutDetail.setVisibility(View.GONE);
        }
    }

    /**
     * 订单状态
     */
    private void showState(ValueEntity submitOrderEntity) {
        //tvState.setTextColor(mResource.getColor(R.color.color_9));
        tvStateLeft.setTextColor(mResource.getColor(R.color.color_9));
        tvStateMidL.setTextColor(mResource.getColor(R.color.color_9));
        tvStateMidR.setTextColor(mResource.getColor(R.color.color_9));
        tvStateRight.setTextColor(mResource.getColor(R.color.color_9));
        switch (submitOrderEntity.getOrderFlowStatus()) {
            case STATE_CANCEL:
                if (submitOrderEntity.getPaymentState() == 1) {
                    tvState.setText("申请退款成功");
                    ivArrow.setVisibility(View.GONE);
                    //tvStateDes.setText("退款详情 >");
                } else {
                    tvState.setText("已取消");
                    //tvStateDes.setText("支付超时自动取消");
                }
                tvStateLeft.setText("订单已提交");
                imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
                layoutStateMidL.setVisibility(View.GONE);
                layoutStateMidR.setVisibility(View.GONE);
                tvStateRight.setText("订单取消");
                tvStateRight.setTextColor(mResource.getColor(R.color.title_bar_bg));
                imgStateRight.setBackgroundResource(R.drawable.order_detail_status_icon);
                unPayBottonLayout.setVisibility(View.VISIBLE);
                tvComplain.setVisibility(View.VISIBLE);
                tvEvaluate.setVisibility(View.GONE);
                tvBuyBottomAgain.setVisibility(View.GONE);
                if (submitOrderEntity.getMerchant().getType() == 1) {
                    tvBuyAgain.setVisibility(View.GONE);
                } else {
                    tvBuyAgain.setVisibility(View.VISIBLE);
                }
                tvBottomCancel.setVisibility(View.GONE);
                waitArriveLayout.setVisibility(View.GONE);
                rlBottomBar.setVisibility(View.GONE);
                tvUnPayGoPay.setVisibility(View.GONE);
                tvShouhuo.setVisibility(View.GONE);
                break;
            case STATE_WAIT_PAY:
                tvState.setText("订单已提交");
                //tvStateDes.setText("等待付款");
                tvStateLeft.setText("订单已提交");
                tvStateLeft.setTextColor(mResource.getColor(R.color.title_bar_bg));
                imgStateLeft.setBackgroundResource(R.drawable.order_detail_status_icon);
                tvStateMidL.setText("等待商家接单");
                imgStateMidL.setBackgroundResource(R.drawable.icon_point_gray);
                tvStateMidR.setText("商家已接单");
                imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
                layoutStateMidL.setVisibility(View.VISIBLE);
                layoutStateMidR.setVisibility(View.VISIBLE);
                tvStateRight.setText("配送员取货中");
                imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
                unPayBottonLayout.setVisibility(View.VISIBLE);
                tvComplain.setVisibility(View.VISIBLE);
                tvEvaluate.setVisibility(View.GONE);
                tvBuyBottomAgain.setVisibility(View.GONE);
                tvBottomCancel.setVisibility(View.GONE);
                waitArriveLayout.setVisibility(View.GONE);
                tvUnPayGoPay.setVisibility(View.VISIBLE);
                tvUnPayCancel.setVisibility(View.VISIBLE);
                tvShouhuo.setVisibility(View.GONE);
                String paymentExpireTime = valueEntity.getPaymentExpireTime();
                if (paymentExpireTime != null) {
//				tvUnPayGoPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
                    tvUnPayGoPay.setText("去支付");
                } else {
                    tvUnPayGoPay.setVisibility(View.GONE);
                    tvUnPayCancel.setVisibility(View.GONE);
                }
                break;
            case STATE_WAIT_CONFIRM:
                if (shareRedBagInfo != null) {
                    if (isCanIn) {
                        isCanIn = false;
                        showRedBag();
                    }
                }

                tvState.setText("支付成功");
                //tvStateDes.setText("等待商家接单");
                tvStateLeft.setText("订单已提交");
                imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvStateMidL.setText("等待商家接单");
                tvStateMidL.setTextColor(mResource.getColor(R.color.title_bar_bg));
                imgStateMidL.setBackgroundResource(R.drawable.order_detail_status_icon);
                layoutStateMidL.setVisibility(View.VISIBLE);
                tvStateMidR.setText("商家已接单");
                imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
                layoutStateMidR.setVisibility(View.VISIBLE);
                tvStateRight.setText("配送员取货中");
                imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
                unPayBottonLayout.setVisibility(View.VISIBLE);
                tvComplain.setVisibility(View.VISIBLE);
                tvEvaluate.setVisibility(View.GONE);
                tvBuyBottomAgain.setVisibility(View.GONE);
                if (submitOrderEntity.getMerchant().getType() == 1) {
                    tvBuyAgain.setVisibility(View.GONE);
                } else {
                    tvBuyAgain.setVisibility(View.VISIBLE);
                }
                tvBottomCancel.setVisibility(View.VISIBLE);
                waitArriveLayout.setVisibility(View.GONE);
                rlBottomBar.setVisibility(View.GONE);
                tvUnPayGoPay.setVisibility(View.GONE);
                tvUnPayCancel.setVisibility(View.GONE);
                tvShouhuo.setVisibility(View.GONE);
                break;
            case STATE_ACCEPED:
                tvState.setText("商家已接单");
//			tvStateDes.setText("预计送达时间：" + showOrderTime(submitOrderEntity));
                tvStateLeft.setText("等待商家接单");
                imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvStateMidL.setText("商家已接单");
                tvStateMidL.setTextColor(mResource.getColor(R.color.title_bar_bg));
                imgStateMidL.setBackgroundResource(R.drawable.order_detail_status_icon);
                layoutStateMidL.setVisibility(View.VISIBLE);
                tvStateMidR.setText("配送员取货中");
                imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
                layoutStateMidR.setVisibility(View.VISIBLE);
                tvStateRight.setText("配送员已取货");
                imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
                unPayBottonLayout.setVisibility(View.VISIBLE);
                tvComplain.setVisibility(View.VISIBLE);
                tvEvaluate.setVisibility(View.GONE);
                tvBuyBottomAgain.setVisibility(View.GONE);
                if (submitOrderEntity.getMerchant().getType() == 1) {
                    tvBuyAgain.setVisibility(View.GONE);
                } else {
                    tvBuyAgain.setVisibility(View.VISIBLE);
                }
                tvBottomCancel.setVisibility(View.GONE);
                waitArriveLayout.setVisibility(View.VISIBLE);
                rlBottomBar.setVisibility(View.GONE);
                tvUnPayGoPay.setVisibility(View.GONE);
                tvUnPayCancel.setVisibility(View.GONE);
                tvShouhuo.setVisibility(View.GONE);
                break;
            case STATE_WAIT_TAKE:
                tvState.setText("骑手已接单");
                //tvStateDes.setText("小马哥正在前往商家取餐");
                tvStateLeft.setText("商家已接单");
                imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvStateMidL.setText("配送员取货中");
                tvStateMidL.setTextColor(mResource.getColor(R.color.title_bar_bg));
                imgStateMidL.setBackgroundResource(R.drawable.order_detail_status_icon);
                layoutStateMidL.setVisibility(View.VISIBLE);
                tvStateMidR.setText("骑手配送中");
                imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
                layoutStateMidR.setVisibility(View.VISIBLE);
                tvStateRight.setText("完成");
                imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
                unPayBottonLayout.setVisibility(View.VISIBLE);
                tvComplain.setVisibility(View.VISIBLE);
                tvEvaluate.setVisibility(View.GONE);
                tvBuyBottomAgain.setVisibility(View.GONE);
                if (submitOrderEntity.getMerchant().getType() == 1) {
                    tvBuyAgain.setVisibility(View.GONE);
                } else {
                    tvBuyAgain.setVisibility(View.VISIBLE);
                }
                tvBottomCancel.setVisibility(View.GONE);
                waitArriveLayout.setVisibility(View.VISIBLE);
                ValueEntity.DeliveryTaskEntity deliveryTask = submitOrderEntity.getDeliveryTask();
                if (deliveryTask != null) {
                    initMap(submitOrderEntity);
                }
                rlBottomBar.setVisibility(View.GONE);
                tvUnPayGoPay.setVisibility(View.GONE);
                tvUnPayCancel.setVisibility(View.GONE);
                tvShouhuo.setVisibility(View.GONE);
                break;
            case STATE_HAS_TAKE:
            case STATE_WAIT_DELIVERY:
                tvState.setText("骑手已取货");
                //tvStateDes.setText("小马哥正在为您送餐");
                tvStateLeft.setText("商家已接单");
                imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvStateMidL.setText("配送员取货中");
                imgStateMidL.setBackgroundResource(R.drawable.icon_point_gray);
                layoutStateMidL.setVisibility(View.VISIBLE);
                tvStateMidR.setText("骑手配送中");
                tvStateMidR.setTextColor(mResource.getColor(R.color.title_bar_bg));
                imgStateMidR.setBackgroundResource(R.drawable.order_detail_status_icon);
                layoutStateMidR.setVisibility(View.VISIBLE);
                tvStateRight.setText("完成");
                imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
                unPayBottonLayout.setVisibility(View.VISIBLE);
                tvComplain.setVisibility(View.VISIBLE);
                tvEvaluate.setVisibility(View.GONE);
                tvBuyBottomAgain.setVisibility(View.GONE);
                if (submitOrderEntity.getMerchant().getType() == 1) {
                    tvBuyAgain.setVisibility(View.GONE);
                } else {
                    tvBuyAgain.setVisibility(View.VISIBLE);
                }
                rlBottomBar.setVisibility(View.VISIBLE);
                tvBottomCancel.setVisibility(View.GONE);
                waitArriveLayout.setVisibility(View.GONE);
                int shipmentType = submitOrderEntity.getShipmentType();
                if (shipmentType == 2) {
                    initMap(submitOrderEntity);
                }
                tvUnPayGoPay.setVisibility(View.GONE);
                tvUnPayCancel.setVisibility(View.GONE);
                if (submitOrderEntity.getShipmentType() == 1) {
                    tvShouhuo.setVisibility(View.VISIBLE);
                    unPayBottonLayout.setVisibility(View.VISIBLE);
                } else {
                    tvShouhuo.setVisibility(View.GONE);
                }
                break;
            case STATE_DONE:
                tvState.setText("已完成");
                //tvStateDes.setText("期待为再次为您服务");
                tvStateLeft.setText("商家已接单");
                imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
                tvStateMidL.setText("配送员取货中");
                imgStateMidL.setBackgroundResource(R.drawable.icon_point_gray);
                layoutStateMidL.setVisibility(View.VISIBLE);
                tvStateMidR.setText("骑手配送中");
                imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
                layoutStateMidR.setVisibility(View.VISIBLE);
                tvStateRight.setText("完成");
                tvStateRight.setTextColor(mResource.getColor(R.color.title_bar_bg));
                imgStateRight.setBackgroundResource(R.drawable.order_detail_status_icon);
                unPayBottonLayout.setVisibility(View.VISIBLE);
                tvComplain.setVisibility(View.VISIBLE);
                if (submitOrderEntity.getHasComments() != 1) {
                    tvEvaluate.setVisibility(View.VISIBLE);
                    rlBottomBar.setVisibility(View.VISIBLE);
                } else {
                    rlBottomBar.setVisibility(View.GONE);
                }
                tvBuyBottomAgain.setVisibility(View.GONE);
                if (submitOrderEntity.getMerchant().getType() == 1) {
                    tvBuyAgain.setVisibility(View.GONE);
                } else {
                    tvBuyAgain.setVisibility(View.VISIBLE);
                }
                tvBottomCancel.setVisibility(View.GONE);
                waitArriveLayout.setVisibility(View.GONE);
                tvUnPayGoPay.setVisibility(View.GONE);
                tvUnPayCancel.setVisibility(View.GONE);
                tvShouhuo.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private long getTimeBetween(String serverTime, String laterTime) {

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
    public void onDestroy() {
        baiduMap.setMyLocationEnabled(false);
        baiduMap.clear();
        baiduMap = null;
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void initMap(ValueEntity submitOrderEntity) {
        mapView.setVisibility(View.VISIBLE);
        ValueEntity.DeliveryTaskEntity.DeliverymanEntity deliveryman = submitOrderEntity.getDeliveryTask().getDeliveryman();
        locationCurrentSite(deliveryman.getLatitude().doubleValue(), deliveryman.getLongitude().doubleValue());
//		mapCache.setImageBitmap(mapView.getDrawingCache());
//		mapView.setVisibility(View.GONE);
    }

    /**
     * 定位当前地位置
     *
     * @param
     */
    private void locationCurrentSite(double lat, double lon) {
        MyLocationData locData = new MyLocationData.Builder()
                .latitude(lat)
                .longitude(lon)
                .build();
        baiduMap.setMyLocationData(locData);

        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(19).build()));
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, BitmapDescriptorFactory.fromResource(R.drawable.car));
        baiduMap.setMyLocationConfigeration(config);
    }

    private void hideBaiduMapChildView() {
        mapView.showScaleControl(false);
        mapView.showZoomControls(false);
        // 隐藏指南针
        UiSettings mUiSettings = baiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);
        // 删除百度地图logo
        mapView.removeViewAt(1);
    }

    /**
     * 商家信息
     */
    private void showCommercial(ValueEntity submitOrderEntity) {
        ImageUtils.loadBitmap(this, submitOrderEntity.getMerchant().getLogo(), imgCommercial, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        tvCommercialName.setText(submitOrderEntity.getMerchant().getName());
        if (submitOrderEntity.getMerchant().getType() == 1) {
            tvCommercialArrow.setVisibility(View.GONE);
        }
        layoutMeal.removeAllViews();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = DipToPx.dip2px(OrderDetailActivity.this, 10);
        int totalCount = 0;
        if (CheckUtils.isNoEmptyList(submitOrderEntity.getOrderItems())) {
            for (int i = 0; i < submitOrderEntity.getOrderItems().size(); i++) {
                ValueEntity.OrderItemsEntity orderItem = submitOrderEntity.getOrderItems().get(i);
                View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_meal_item, null);
                TextView tvMealName = (TextView) view.findViewById(R.id.order_detail_commercial_meal);
                TextView tvMealMoney = (TextView) view.findViewById(R.id.order_detail_commercial_meal_money);
                TextView tvMealCount = (TextView) view.findViewById(R.id.order_detail_commercial_meal_count);
                TextView tvMealSpec = (TextView) view.findViewById(R.id.order_detail_commercial_meal_spec);
//                String spec = "";
//                if (!TextUtils.isEmpty(orderItem.getSpec())) {
//                    spec = " (" + orderItem.getSpec() + ")";
//                }
                if (CheckUtils.isNoEmptyStr(orderItem.getSpec())) {
                    if (CheckUtils.isNoEmptyStr(orderItem.getAttributes())) {
                        tvMealSpec.setText(orderItem.getSpec() + orderItem.getAttributes());
                        tvMealSpec.setVisibility(View.VISIBLE);
                    } else {
                        tvMealSpec.setText(orderItem.getSpec());
                        tvMealSpec.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvMealSpec.setVisibility(View.GONE);
                }
                tvMealName.setText(orderItem.getName()/* + spec*/);
                tvMealMoney.setText("¥" + StringUtils.BigDecimal2Str(orderItem.getTotalPrice()));
                tvMealCount.setText("×" + orderItem.getQuantity());
                layoutMeal.addView(view, params);
                totalCount += orderItem.getQuantity();
            }
        }
        layoutShip.removeAllViews();
        if (submitOrderEntity.getBoxPrice() != null && submitOrderEntity.getBoxPrice().compareTo(BigDecimal.ZERO) > 0) {
            View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_package_and_shipping_item, null);
            TextView tvPackageName = (TextView) view.findViewById(R.id.order_detail_commercial_shipping);
            TextView tvPackageMoney = (TextView) view.findViewById(R.id.order_detail_commercial_shipping_money);
            tvPackageName.setText("餐盒费");
            tvPackageMoney.setText("¥" + StringUtils.BigDecimal2Str(submitOrderEntity.getBoxPrice()));
            layoutShip.addView(view, params);
        }
        if (submitOrderEntity.getShippingFee() != null && submitOrderEntity.getShippingFee().compareTo(BigDecimal.ZERO) > 0) {
            View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_package_and_shipping_item, null);
            TextView tvShippingName = (TextView) view.findViewById(R.id.order_detail_commercial_shipping);
            TextView tvShippingMoney = (TextView) view.findViewById(R.id.order_detail_commercial_shipping_money);
            tvShippingName.setText("配送费");
            tvShippingMoney.setText("¥" + StringUtils.BigDecimal2Str(submitOrderEntity.getShippingFee()));
            layoutShip.addView(view, params);
        }
        if (submitOrderEntity.getRedBagTotalAmt() != null && submitOrderEntity.getRedBagTotalAmt().compareTo(BigDecimal.ZERO) > 0) {
            View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_package_and_shipping_item, null);
            if (submitOrderEntity.getType() == 1) {//外卖
                List<RedBag> redBags = JSON.parseArray(submitOrderEntity.getRedBagJson(), RedBag.class);
                View view1 = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_package_and_shipping_item, null);
                for (RedBag redBag : redBags) {
                    if (redBag.getPromotionType() == 2) {
                        TextView tvRedBagName1 = (TextView) view1.findViewById(R.id.order_detail_commercial_shipping);
                        TextView tvRedBagMoney1 = (TextView) view1.findViewById(R.id.order_detail_commercial_shipping_money);
                        tvRedBagName1.setText("商家代金券");
                        tvRedBagMoney1.setText("- ¥" + StringUtils.BigDecimal2Str(redBag.getAmt()));
                        layoutShip.addView(view1, params);
                    } else {
                        TextView tvRedBagName = (TextView) view.findViewById(R.id.order_detail_commercial_shipping);
                        TextView tvRedBagMoney = (TextView) view.findViewById(R.id.order_detail_commercial_shipping_money);
                        tvRedBagName.setText("红包金额");
                        tvRedBagMoney.setText("- ¥" + StringUtils.BigDecimal2Str(redBag.getAmt()));
                    }
                }
            } else {
                TextView tvRedBagName = (TextView) view.findViewById(R.id.order_detail_commercial_shipping);
                TextView tvRedBagMoney = (TextView) view.findViewById(R.id.order_detail_commercial_shipping_money);
                tvRedBagName.setText("红包金额");
                tvRedBagMoney.setText("- ¥" + StringUtils.BigDecimal2Str(submitOrderEntity.getRedBagTotalAmt()));
            }
            layoutShip.addView(view, params);
        }
        layoutCampaigns.removeAllViews();

        if (submitOrderEntity.getShippingPreferentialFee().compareTo(BigDecimal.ZERO) != 0) {
            addAssumeAmt(layoutCampaigns, StringUtils.BigDecimal2Str(submitOrderEntity.getShippingPreferentialFee()));
        }

        if (CheckUtils.isNoEmptyList(submitOrderEntity.getPromoList())) {
            for (int i = 0, size = submitOrderEntity.getPromoList().size(); i < size; i++) {
                View view1 = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_campaigns_item, null);
                ImageView ivIcon = (ImageView) view1.findViewById(R.id.order_detail_commercial_promotion_icon);
                TextView tvLimit = (TextView) view1.findViewById(R.id.order_detail_commercial_campaign_limit);
                TextView tvAmount = (TextView) view1.findViewById(R.id.order_detail_commercial_meal_campaign_amount);
                ImageUtils.loadBitmap(mActivity, submitOrderEntity.getPromoList().get(i).getPromoImg(), ivIcon, R.drawable.jian, "");
                tvLimit.setText(submitOrderEntity.getPromoList().get(i).getRule());
                if (submitOrderEntity.getPromoList().get(i).getDiscountAmt() != null) {
                    if (submitOrderEntity.getPromoList().get(i).getType() == 0) {
                        tvAmount.setText("- ¥" + submitOrderEntity.getPromoList().get(i).getDiscountAmt());
                    } else {
                        tvAmount.setText("");
                    }
                }
                layoutCampaigns.addView(view1, params);
            }
            View viewLine = new View(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DipToPx.dip2px(this, 0.5f));
            layoutParams.setMargins(0, DipToPx.dip2px(this, 10f), 0, 0);
            viewLine.setLayoutParams(layoutParams);
            viewLine.setBackgroundColor(getResources().getColor(R.color.gray_bg));
            layoutCampaigns.addView(viewLine);
        }
        tvAllGoods.setText("合计：");
        tvAllMoney.setText("" + StringUtils.BigDecimal2Str(submitOrderEntity.getTotalPrice()));
        layoutCommercial.setOnClickListener(this);
        tvBuyAgain.setOnClickListener(this);
    }


    private void addAssumeAmt(LinearLayout layout, String assumeAmt) {
        LinearLayout childLayout = new LinearLayout(mActivity);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        ImageView image = new ImageView(mActivity);
        image.setImageResource(R.drawable.jian);
        LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(DipToPx.dip2px(mActivity, 12), DipToPx.dip2px(mActivity, 12));
        childLayout.addView(image, paramsImg);


        TextView tvName = new TextView(mActivity);
        LinearLayout.LayoutParams paramsName = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsName.leftMargin = DipToPx.dip2px(mActivity, 5);
        tvName.setText("减配送费");
        tvName.setSingleLine();
        tvName.setEllipsize(TextUtils.TruncateAt.END);
        tvName.setTextColor(mActivity.getResources().getColor(R.color.gray_txt));
        tvName.setTextSize(12);
        childLayout.addView(tvName, paramsName);

        TextView tv = new TextView(mActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.x5);
        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x15);
        tv.setText("- ¥" + assumeAmt);
        tv.setTextColor(getResources().getColor(R.color.gray_txt));
        tv.setTextSize(12);
        tv.setGravity(Gravity.RIGHT);
        childLayout.addView(tv, params);
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsChild.topMargin = DipToPx.dip2px(mActivity, 14);
        layout.addView(childLayout, paramsChild);
    }

    /**
     * 配送方式
     */
    private void showDelivery(ValueEntity submitOrderEntity) {
        tvShippingStyle.setText("配送方式：" + ShipmentMode.getShipmentModeByValue(submitOrderEntity.getShipmentType()).getMemo());
        if (submitOrderEntity.getShipmentType() == 2) {
            switch (submitOrderEntity.getOrderFlowStatus()) {
                case STATE_WAIT_TAKE:
                case STATE_HAS_TAKE:
                case STATE_WAIT_DELIVERY:
                case STATE_DONE:
                    llDriverInfo.setVisibility(View.VISIBLE);
                    vDriverLine.setVisibility(View.VISIBLE);
                    tvDriverName.setText(submitOrderEntity.getDeliveryTask().getDeliveryman().getName());
                    tvDriverPhone.setText(submitOrderEntity.getDeliveryTask().getDeliveryman().getMobile());
                    driverPhone = submitOrderEntity.getDeliveryTask().getDeliveryman().getMobile();
                    tvDriverPhone.setOnClickListener(this);
                    break;
                default:
                    llDriverInfo.setVisibility(View.GONE);
                    vDriverLine.setVisibility(View.GONE);
                    break;
            }
        } else {
            llDriverInfo.setVisibility(View.GONE);
            vDriverLine.setVisibility(View.GONE);
        }
    }

    /**
     * 详情展示
     *
     * @param submitOrderEntity
     */
    private void showDetail(ValueEntity submitOrderEntity) {
        tvOrderNum.setText("订单号: " + submitOrderEntity.getId());
        tvOrderTime.setText("下单时间: " + submitOrderEntity.getCreateTime());
        tvPersonName.setText("联系人: \u3000" + submitOrderEntity.getUserName() + " " + submitOrderEntity.getUserGender());
        if (CheckUtils.isEmptyStr(submitOrderEntity.getUserBackupMobile())) {
            tvPersonPhone.setText("联系电话: " + submitOrderEntity.getUserMobile());
        } else {
            tvPersonPhone.setText("联系电话: " + submitOrderEntity.getUserMobile() + " " + submitOrderEntity.getUserBackupMobile());
        }
        tvPersonAddress.setText("收货地址: " + submitOrderEntity.getUserAddress() + " " + submitOrderEntity.getUserHouseNumber());
        tvPersonPayType.setText("支付方式: " + PaymentMode.getPaymentModeByValue(submitOrderEntity.getPaymentType()).getMemo());
        if (!TextUtils.isEmpty(submitOrderEntity.getCaution()))
            tvOrderCaution.setText(submitOrderEntity.getCaution());
        String expectDeliveryTime = submitOrderEntity.getExpectArrivalTime();
        if (submitOrderEntity.getOrderFlowStatus() == -1) {
            if (submitOrderEntity.getPaymentState() == 1) {
                //已支付
                if (DateUtils.compareTimeBefore(submitOrderEntity.getCreateTime())) {
                    if (StringUtils.BigDecimal2Str(submitOrderEntity.getTotalPrice()).equals("0")) {
                        tvStateDes.setText("");
                    } else {
                        tvStateDes.setText("退款详情 >");
                    }
                } else {
                    tvStateDes.setText("");
                }
            } else {
                //未支付
                tvStateDes.setText("支付超时自动取消");
            }
            if (!TextUtils.isEmpty(expectDeliveryTime) && !expectDeliveryTime.equals("1")) {
                tvArriveTime.setText("送达时间: " + CommonUtils.formatTime(Long.parseLong(expectDeliveryTime), CommonUtils.yyyy_MM_dd_HH_mm));
            }
            return;
        } else if (submitOrderEntity.getOrderFlowStatus() == 7) {
            //已完成
            tvStateDes.setText("期待再次为您服务");
            if (!TextUtils.isEmpty(expectDeliveryTime) && !expectDeliveryTime.equals("1")) {
                tvArriveTime.setText("送达时间: " + CommonUtils.formatTime(Long.parseLong(expectDeliveryTime), CommonUtils.yyyy_MM_dd_HH_mm));
            }
            return;
        }
        if (CheckUtils.isNoEmptyStr(expectDeliveryTime)) {
            if (expectDeliveryTime.equals("1")) {
                String paymentFinishTime = submitOrderEntity.getPaymentFinishTime();
                if (TextUtils.isEmpty(paymentFinishTime)) {
                    tvStateDes.setText("预计送达时间: 立即送达");
                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(CommonUtils.yyyy_MM_dd_HH_mm_ss);
                        long timeStr = sdf.parse(paymentFinishTime).getTime() + submitOrderEntity.getMerchant().getAvgDeliveryTime() * 60 * 1000;
                        String s = CommonUtils.formatTime(timeStr, CommonUtils.MM_dd_HH_mm);
                        tvStateDes.setText("预计送达时间: 立即送达  [ " + s + " ]");
                    } catch (ParseException e) {
                        e.printStackTrace();
                        tvStateDes.setText("预计送达时间: 立即送达");
                    }
                }
            } else {
                tvArriveTime.setText("送达时间: " + CommonUtils.formatTime(Long.parseLong(expectDeliveryTime), CommonUtils.yyyy_MM_dd_HH_mm));
            }
        }

    }

    /**
     * 下单时间
     *
     * @param submitOrderEntity
     */
    private String showOrderTime(ValueEntity submitOrderEntity) {
        String time = "";
        try {
            Date date = dateFormat.parse(submitOrderEntity.getCreateTime());
//			Date expectDate = dateFormat.parse(submitOrderEntity.getExpectArrivalTime());
//			time += timeFormat.format(expectDate);
            if (new Date().getTime() - date.getTime() < 5 * ONE_MINUTE) {
                time += "\u3000刚刚下单";
            } else {
                time += "\u3000下单" + (new Date().getTime() - date.getTime()) / ONE_MINUTE + "分钟";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 投诉电话联系
     */
    private void initAvatarDialog() {
        avatarDialog = new Dialog(this, R.style.fullDialog);
        RelativeLayout contentView = (RelativeLayout) View.inflate(this, R.layout.pick_or_take_photo_dialog, null);
        dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
        dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
        Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
        View line = contentView.findViewById(R.id.line);
        dialog_bt_take_photo.setText(mgjName);
        dialog_bt_pick_photo.setText(areaName);
        dialog_bt_pick_photo.setOnClickListener(this);
        dialog_bt_take_photo.setOnClickListener(this);
        dialog_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarDialog.dismiss();
            }
        });
        avatarDialog.setContentView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        if (CheckUtils.isEmptyStr(mgjPhone)) {
            dialog_bt_take_photo.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        avatarDialog.show();
    }

    @Override
    public void onBackPressed() {
//        Intent intentHome = new Intent(this, HomeActivity.class);
//        intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intentHome);
        back();
    }

    private void getTelNumId(int type) {
        final Map<String, Object> map = new HashMap<>();
        map.put("agentId", submitOrderEntity.getAgentId());
        map.put("type", type);
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
                    if (model.getValue() != null && 2 == model.getValue().get(i).getType()) {
//                        agentPhone = model.getValue().get(i).getPhone();
                        areaPhone = model.getValue().get(i).getPhone();
                        areaName = model.getValue().get(i).getTitle();
                    } else if (model.getValue() != null && 3 == model.getValue().get(i).getType()) {
//                        agentMobile = model.getValue().get(i).getPhone();
                        mgjPhone = model.getValue().get(i).getPhone();
                        mgjName = model.getValue().get(i).getTitle();
                    } else if (model.getValue() != null && 1 == model.getValue().get(i).getType()) {
                        constomer = model.getValue().get(i).getPhone();
                    }
                }
            }
        }, CustomerAndComplainPhoneDTOModel.class);
    }

    /**
     * 商家电话联系
     */
    private void initDialog() {
        avatarDialog = new Dialog(this, R.style.fullDialog);
        RelativeLayout contentView = (RelativeLayout) View.inflate(this, R.layout.pick_or_take_photo_dialog, null);
        dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
        dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
        Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
        View line = contentView.findViewById(R.id.line);
        dialog_bt_take_photo.setText("联系商家");
        dialog_bt_pick_photo.setText("联系客服");
        dialog_bt_pick_photo.setOnClickListener(this);
        dialog_bt_take_photo.setOnClickListener(this);
        dialog_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarDialog.dismiss();
            }
        });
        avatarDialog.setContentView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        if (layoutDetail.getVisibility() == View.VISIBLE) {
            avatarDialog.show();
        }
    }
}
