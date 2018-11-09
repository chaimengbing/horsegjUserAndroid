package com.project.mgjandroid.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.UserOrderType;
import com.project.mgjandroid.bean.carhailing.CarHailingDriver;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.DeleteOrderModel;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.model.NewOrderTypeModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingOrderListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.net.VolleyOperater.ResponseListener;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.activity.ConfirmOrderActivity;
import com.project.mgjandroid.ui.activity.EvaluateActivity;
import com.project.mgjandroid.ui.activity.NewEvaluateActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.OrderDetailActivity;
import com.project.mgjandroid.ui.activity.OrderRefundInfoActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.carhailing.CarHailingDriverActivity;
import com.project.mgjandroid.ui.activity.carhailing.CarHailingEvaluateActivity;
import com.project.mgjandroid.ui.activity.carhailing.CarHailingOrderDetailActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingAddEvaluationActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingOrderForGoodsDetailsActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingQuanOrTuanDetailActivity;
import com.project.mgjandroid.ui.activity.groupbuying.GroupBuyingUseActivity;
import com.project.mgjandroid.ui.activity.groupbuying.PayBillDetailActivity;
import com.project.mgjandroid.ui.activity.legwork.LegworkEvaluateActivity;
import com.project.mgjandroid.ui.activity.legwork.LegworkOrderdetailsActivity;
import com.project.mgjandroid.ui.activity.pintuan.EvaluateGroupActivity;
import com.project.mgjandroid.ui.activity.pintuan.GroupPurchaseDetailActivity;
import com.project.mgjandroid.ui.activity.pintuan.MyGroupPurchaseDetailActivity;
import com.project.mgjandroid.ui.activity.pintuan.PreviousGroupActivity;
import com.project.mgjandroid.ui.adapter.NewOrderListAdapter;
import com.project.mgjandroid.ui.adapter.OrderTypeGridAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.ta.utdid2.android.utils.NetworkUtils;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单列表
 *
 * @author jian
 */
public class OrderListFragment extends BaseFragment implements OnClickListener, CustomDialog.onBtnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    //登录和联网状态
    private static final int ORDER_FRAGMRNT_NO_NET = 400;
    private static final int ORDER_FRAGMRNT_NO_LOGIN = 101;
    private static final int ORDER_FRAGMRNT_LOGIN = 100;
    private static final int LOGIN_IN = 200;
    private static final int RELOAD = 1000;
    public static final int REFRESH = 2000;
    protected Activity mActivity;
    protected View view;
    private PullToRefreshListView listView;
    private RelativeLayout layoutTip;
    private ImageView imgClose;
    //    private OrderListAdapter adapter;
    protected boolean refreshFlag = true;
    private boolean isFirstIn = true;
    private static final int maxResults = 20;
    private int page = 0;
    private SimpleDateFormat sdf;

    private LinearLayout orderListStateAbnormal;
    private TextView orderListStateMsg, orderListStateDeal;
    private ImageView orderListStateImage;
    //当前状态判断 未登录/无网
    private static int state = ORDER_FRAGMRNT_NO_LOGIN;
    private CustomDialog dialog;
    private int deleteTag = -1;
    private View mNoDataView;
    private MLoadingDialog mLoadingDialog;
    private static OrderListFragment fragment;
    private String errorMsg;
    private MLoadingDialog loadingDialog;
    private JSONObject previewJsonData;
    private CustomDialog goodsDialog;
    private PopupWindow mPopupWindow;
    private NewOrderFragmentModel.ValueEntity shareGroup;

    private NewOrderListAdapter adapter;
    private LinearLayout llFilter;
    private TextView tvAll;
    private TextView tvWaimai;
    private TextView tvMarket;
    private TextView tvGroup;
    private TextView tvCarHailing;
    private TextView tvTitle;
    private ImageView ivTitleArrow;
    private boolean isFilter = false;
    private int currentType = 0;
    private View vShadow;
    private TranslateAnimation mHiddenAction;
    private TranslateAnimation mShowAction;
    private GridView gdOrderType;

    private TextView tvGroupBuying;
    private LinearLayout tabLayout;
    private TextView tab_1;
    private View line_1;
    private TextView tab_2;
    private View line_2;
    private TextView tab_3;
    private View line_3;
    private TextView tab_4;
    private View line_4;
    private TextView tab_5;
    private View line_5;
    private int currentStatus = 0;
    private List<Map<Integer, String>> mapList = new ArrayList<>();
    private OrderTypeGridAdapter orderTypeGridAdapter;

    public static OrderListFragment newInstance() {
        if (fragment == null) {
            fragment = new OrderListFragment();
        }
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.order_list_fragment, container, false);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        initDialog();
        initViews();
        initGrideDate();
        checkState();
        return view;
    }

    private void initDialog() {
        dialog = new CustomDialog(mActivity, this, "确定", "取消", "提示", "确定删除吗？");
        mLoadingDialog = new MLoadingDialog();
    }

    private void checkState() {
        if (orderListStateAbnormal != null && orderListStateAbnormal.getVisibility() == View.VISIBLE) {
            orderListStateAbnormal.setVisibility(View.GONE);
        }
        if (!App.isLogin()) {//未登录
            resetFilter();
            resetStyle(currentType);
            listView.resetCurrentMode();
            page = 0;
            currentType = 0;
            tvTitle.setText("全部订单");
            tvAll.setTextColor(getResources().getColor(R.color.white));
            tvAll.setBackgroundResource(R.drawable.shap_orange_range_bg);
            state = ORDER_FRAGMRNT_NO_LOGIN;
            orderListStateAbnormal.setVisibility(View.VISIBLE);
            orderListStateImage.setImageResource(R.drawable.has_no_login);
            orderListStateMsg.setText("您还没有登录，请登录后查看订单");
            orderListStateDeal.setText("登录");
        } else {
            if (!NetworkUtils.isConnected(mActivity)) {
                state = ORDER_FRAGMRNT_NO_NET;
                orderListStateAbnormal.setVisibility(View.VISIBLE);
                orderListStateImage.setImageResource(R.drawable.has_no_net);
                orderListStateMsg.setText("未能连接到互联网");
                orderListStateDeal.setText("刷新重试");
            }
        }
    }

    private void initViews() {
        loadingDialog = new MLoadingDialog();
        layoutTip = (RelativeLayout) view.findViewById(R.id.orderlist_fragment_layout_tips);
        imgClose = (ImageView) view.findViewById(R.id.orderlist_fragment_close);
        orderListStateAbnormal = (LinearLayout) view.findViewById(R.id.orderlist_fragment_state_abnormal);
        listView = (PullToRefreshListView) view.findViewById(R.id.orderlist_fragment_listView);
        orderListStateMsg = (TextView) orderListStateAbnormal.findViewById(R.id.orderlist_fragment_state_msg);
        orderListStateDeal = (TextView) orderListStateAbnormal.findViewById(R.id.orderlist_fragment_state_deal);
        orderListStateImage = (ImageView) orderListStateAbnormal.findViewById(R.id.orderlist_fragment_state_img);
        layoutTip.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        orderListStateDeal.setOnClickListener(this);
        initTabLayout();
        initFilterLayout();
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new NewOrderListAdapter(R.layout.item_new_order_list, mActivity);
        adapter.setListener(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.getRefreshableView().setOnItemLongClickListener(this);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshFlag) {
                    page = 0;
                    getData(false);
                }
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshFlag) {
                    page += maxResults;
                    getData(true);
                }
            }

        });
        mNoDataView = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_list_no_data, null);
    }

    /**
     * @param isLoadMore 是否加载更多
     */
    private void getData(boolean isLoadMore) {
        if (currentType == 6) {
            getGroupPurchaseData(isLoadMore);
        } else {
            getDate(isLoadMore);
        }
    }

    private void initTabLayout() {
        tabLayout = (LinearLayout) view.findViewById(R.id.tab_layout);
        tab_1 = (TextView) view.findViewById(R.id.tab_1);
        tab_2 = (TextView) view.findViewById(R.id.tab_2);
        tab_3 = (TextView) view.findViewById(R.id.tab_3);
        tab_4 = (TextView) view.findViewById(R.id.tab_4);
        tab_5 = (TextView) view.findViewById(R.id.tab_5);
        line_1 = view.findViewById(R.id.line_1);
        line_2 = view.findViewById(R.id.line_2);
        line_3 = view.findViewById(R.id.line_3);
        line_4 = view.findViewById(R.id.line_4);
        line_5 = view.findViewById(R.id.line_5);

        tab_1.setOnClickListener(this);
        tab_2.setOnClickListener(this);
        tab_3.setOnClickListener(this);
        tab_4.setOnClickListener(this);
        tab_5.setOnClickListener(this);
    }

    private void resetTabLayout(int resId) {
        tab_1.setTextColor(ContextCompat.getColor(mActivity, R.color.color_3));
        tab_2.setTextColor(ContextCompat.getColor(mActivity, R.color.color_3));
        tab_3.setTextColor(ContextCompat.getColor(mActivity, R.color.color_3));
        tab_4.setTextColor(ContextCompat.getColor(mActivity, R.color.color_3));
        tab_5.setTextColor(ContextCompat.getColor(mActivity, R.color.color_3));
        line_1.setVisibility(View.INVISIBLE);
        line_2.setVisibility(View.INVISIBLE);
        line_3.setVisibility(View.INVISIBLE);
        line_4.setVisibility(View.INVISIBLE);
        line_5.setVisibility(View.INVISIBLE);

        switch (resId) {
            case R.id.tab_1:
                tab_1.setTextColor(ContextCompat.getColor(mActivity, R.color.title_bar_bg));
                line_1.setVisibility(View.VISIBLE);
                currentStatus = 0;
                break;
            case R.id.tab_2:
                tab_2.setTextColor(ContextCompat.getColor(mActivity, R.color.title_bar_bg));
                line_2.setVisibility(View.VISIBLE);
                currentStatus = 1;
                break;
            case R.id.tab_3:
                tab_3.setTextColor(ContextCompat.getColor(mActivity, R.color.title_bar_bg));
                line_3.setVisibility(View.VISIBLE);
                currentStatus = 2;
                break;
            case R.id.tab_4:
                tab_4.setTextColor(ContextCompat.getColor(mActivity, R.color.title_bar_bg));
                line_4.setVisibility(View.VISIBLE);
                currentStatus = 3;
                break;
            case R.id.tab_5:
                tab_5.setTextColor(ContextCompat.getColor(mActivity, R.color.title_bar_bg));
                line_5.setVisibility(View.VISIBLE);
                currentStatus = 4;
                break;
        }
    }

    private void initFilterLayout() {
        llFilter = (LinearLayout) view.findViewById(R.id.order_filter_layout);
        vShadow = view.findViewById(R.id.filter_shadow_view);
        tvAll = (TextView) view.findViewById(R.id.order_type_all);
        tvWaimai = (TextView) view.findViewById(R.id.order_type_waimai);
        tvMarket = (TextView) view.findViewById(R.id.order_type_supermarket);
        tvGroup = (TextView) view.findViewById(R.id.order_type_group);
        tvCarHailing = (TextView) view.findViewById(R.id.order_type_car_hailing);
        tvGroupBuying = (TextView) view.findViewById(R.id.order_type_group_buying);
        tvTitle = (TextView) view.findViewById(R.id.orderlist_fragment_tv_title);
        ivTitleArrow = (ImageView) view.findViewById(R.id.orderlist_fragment_tv_title_arrow);
        gdOrderType = (GridView) view.findViewById(R.id.gd_order_type);


        tvAll.setOnClickListener(this);
        tvWaimai.setOnClickListener(this);
        tvMarket.setOnClickListener(this);
        tvGroup.setOnClickListener(this);
        tvCarHailing.setOnClickListener(this);
        tvGroupBuying.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        ivTitleArrow.setOnClickListener(this);
        vShadow.setOnClickListener(this);
        llFilter.setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RELOAD:
                    orderListStateAbnormal.setVisibility(View.GONE);
                    refreshList();
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    };

    private void initGrideDate() {
        VolleyOperater<NewOrderTypeModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_ALL_ORDER_TYPE, null, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    NewOrderTypeModel typeModel = (NewOrderTypeModel) obj;
                    mapList.clear();
                    mapList.addAll(typeModel.getValue());
                    orderTypeGridAdapter = new OrderTypeGridAdapter(mActivity, mapList, currentType);
                    gdOrderType.setAdapter(orderTypeGridAdapter);
                    for (Map<Integer, String> integerStringMap : mapList) {
                        for (Integer integer : integerStringMap.keySet()) {
                            if (integer == currentType) {
                                tvTitle.setText(integerStringMap.get(integer));
                            }
                        }
                    }
                    if (currentType == 6) {
                        if (tabLayout.getVisibility() != View.VISIBLE)
                            tabLayout.setVisibility(View.VISIBLE);
                    } else {
                        tabLayout.setVisibility(View.GONE);
                    }
                    gdOrderType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Map<Integer, String> map = mapList.get(i);
                            int key = 0;
                            String name = "";
                            for (Integer integer : map.keySet()) {
                                key = integer;
                                name = map.get(integer);
                            }
                            if (currentType != key) {
                                //关闭选择框
                                resetFilter();
                                //请求数据
                                currentType = key;
                                orderTypeGridAdapter.setSelectTypeId(currentType);
                                tvTitle.setText(name);
                                if (tabLayout.getVisibility() == View.VISIBLE) {
                                    tabLayout.setVisibility(View.GONE);
                                    resetTabLayout(R.id.tab_1);
                                }
                                listView.resetCurrentMode();
                                page = 0;
                                listView.autoRefresh();

                                if (currentType == 6) {
                                    if (tabLayout.getVisibility() != View.VISIBLE)
                                        tabLayout.setVisibility(View.VISIBLE);
                                } else {
                                    tabLayout.setVisibility(View.GONE);
                                }

                            }
                        }
                    });
                }
            }
        }, NewOrderTypeModel.class);
    }

    private void getDate(final boolean isLoadMore) {
        refreshFlag = false;
        Map<String, Object> map = new HashMap<>();
        map.put("start", page);
        map.put("size", maxResults);
        if (currentType != 0)
            map.put("type", currentType);
        VolleyOperater<NewOrderFragmentModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GET_NEW_ORDER_LIST, map, new ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        if (App.isLogin()) {
                            if ("订单信息不存在".equals(obj)) obj = "没有订单记录";
                            ToastUtils.displayMsg((String) obj, mActivity);
                        }
                        adapter.setData(new ArrayList<NewOrderFragmentModel.ValueEntity>());
                        return;
                    }
                    ArrayList<NewOrderFragmentModel.ValueEntity> mlist = null;
                    if (obj instanceof JSONArray) {
                        String data = JSONArray.toJSONString(obj);
                        mlist = (ArrayList<NewOrderFragmentModel.ValueEntity>) JSONArray.parseArray(data, NewOrderFragmentModel.ValueEntity.class);
                        initServerTime(mlist, sdf.format(new Date()));
                    } else {
                        NewOrderFragmentModel orderFragmentModel = (NewOrderFragmentModel) obj;
                        mlist = new ArrayList<>();
                        mlist.addAll(orderFragmentModel.getValue());
                        initServerTime(mlist, orderFragmentModel.getServertime());
                    }
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        listView.removeView(mNoDataView);
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<NewOrderFragmentModel.ValueEntity> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setData(mlist);
                            listView.setEmptyView(mNoDataView);
                        }
                    }
                } else {
//                    adapter.setData(new ArrayList<NewOrderFragmentModel.ValueEntity>());
//                    listView.setEmptyView(mNoDataView);
                }
            }
        }, NewOrderFragmentModel.class);
    }

    private void getGroupPurchaseData(final boolean isLoadMore) {
        refreshFlag = false;
        Map<String, Object> map = new HashMap<>();
        map.put("start", page);
        map.put("size", maxResults);
        // 1:待付款,2:待使用,3:待评价,4:退款
        if (currentStatus != 0)
            map.put("queryType", currentStatus);
        VolleyOperater<GroupBuyingOrderListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_ORDER_LIST, map, new ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        if (App.isLogin()) {
                            if ("订单信息不存在".equals(obj)) obj = "没有订单记录";
                            ToastUtils.displayMsg((String) obj, mActivity);
                        }
                        adapter.setData(new ArrayList<NewOrderFragmentModel.ValueEntity>());
                        return;
                    }
                    ArrayList<NewOrderFragmentModel.ValueEntity> mlist = new ArrayList<>();
                    if (obj instanceof JSONArray) {
                        String data = JSONArray.toJSONString(obj);
                        List<GroupPurchaseOrder> list = JSONArray.parseArray(data, GroupPurchaseOrder.class);
                        if (list != null) {
                            for (GroupPurchaseOrder order : list) {
                                order.setQueryType(currentStatus);
                                NewOrderFragmentModel.ValueEntity valueEntity = new NewOrderFragmentModel.ValueEntity();
                                valueEntity.setType(6);
                                valueEntity.setGroupPurchaseOrder(order);
                                mlist.add(valueEntity);
                            }
                        }
                        initServerTime(mlist, sdf.format(new Date()));
                    } else {
                        GroupBuyingOrderListModel groupBuyingOrderListModel = (GroupBuyingOrderListModel) obj;
                        List<GroupPurchaseOrder> list = groupBuyingOrderListModel.getValue();
                        if (list != null) {
                            for (GroupPurchaseOrder order : list) {
                                order.setQueryType(currentStatus);
                                NewOrderFragmentModel.ValueEntity valueEntity = new NewOrderFragmentModel.ValueEntity();
                                valueEntity.setType(6);
                                valueEntity.setId(order.getId());
                                valueEntity.setGroupPurchaseOrder(order);
                                mlist.add(valueEntity);
                            }
                        }
                        initServerTime(mlist, groupBuyingOrderListModel.getServertime());
                    }
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        listView.removeView(mNoDataView);
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<NewOrderFragmentModel.ValueEntity> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
//                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setData(mlist);
                            listView.setEmptyView(mNoDataView);
                        }
                    }
                } else {

                }
            }
        }, GroupBuyingOrderListModel.class);
    }

    private void initServerTime(ArrayList<NewOrderFragmentModel.ValueEntity> mlist, String servertime) {
        for (NewOrderFragmentModel.ValueEntity value : mlist) {
            value.setServerTime(servertime);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        if(isVisibleToUser){
//            refreshList();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case MineFragment.LOGIN_IN_SUCCESS:
                state = ORDER_FRAGMRNT_LOGIN;
                orderListStateAbnormal.setVisibility(View.GONE);
                refreshList();
                break;
        }
        if (requestCode == REFRESH) {
            refreshList();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderlist_fragment_layout_tips:

                break;
            case R.id.orderlist_fragment_close:
                layoutTip.setVisibility(View.GONE);
                break;
            case R.id.order_list_item_img_delet:
                //删除订单
                deleteTag = (int) v.getTag();
                dialog.show();
                break;
            case R.id.orderlist_fragment_state_deal:
                checkState();
                if (state == ORDER_FRAGMRNT_NO_LOGIN)
                    startActivityForResult(new Intent(mActivity, SmsLoginActivity.class), LOGIN_IN);
                else if (state == ORDER_FRAGMRNT_NO_NET) {
                    if (NetworkUtils.isConnected(mActivity))
                        return;
                    mHandler.sendEmptyMessage(RELOAD);
                }
                break;
            case R.id.order_state_go_pay:
                //去支付
                int tag1 = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity valueEntity = adapter.getData().get(tag1);
                try {
                    String paymentExpireTime = valueEntity.getPaymentExpireTime();
                    if (valueEntity.getType() == UserOrderType.Takeaway.getValue() || valueEntity.getType() == UserOrderType.Shop.getValue()) {
                        if (System.currentTimeMillis() > sdf.parse(paymentExpireTime).getTime() + 15 * 60 * 1000) {
                            ToastUtils.displayMsg("订单已取消", mActivity);
                        } else {
                            Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                            intent.putExtra("orderId", valueEntity.getId());
                            intent.putExtra("agentId", valueEntity.getAgentId());
                            startActivityForResult(intent, REFRESH);
                        }
                    } else if (valueEntity.getType() == UserOrderType.Groupbuy.getValue()) {
                        Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                        intent.putExtra("orderId", valueEntity.getId());
                        intent.putExtra("agentId", valueEntity.getAgentId());
                        intent.putExtra("isGroup", true);
                        startActivityForResult(intent, REFRESH);
                    } else if (valueEntity.getType() == UserOrderType.Car.getValue()) {
                        Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                        intent.putExtra("orderId", valueEntity.getId());
                        intent.putExtra("agentId", valueEntity.getAgentId());
                        intent.putExtra("isCarHailing", true);
                        startActivityForResult(intent, REFRESH);
                    } else if (valueEntity.getType() == UserOrderType.GroupPurchase.getValue()) {
                        Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                        intent.putExtra("orderId", valueEntity.getId());
                        intent.putExtra("agentId", valueEntity.getAgentId());
                        intent.putExtra("isGroupPurchase", true);
                        startActivityForResult(intent, REFRESH);
                    } else if (valueEntity.getType() == 9) {
                        Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                        intent.putExtra("orderId", valueEntity.getId());
                        intent.putExtra("agentId", valueEntity.getAgentId());
                        intent.putExtra("isLegwork", true);
                        startActivityForResult(intent, REFRESH);
                    } else {
                        if (valueEntity.getThirdpartyOrder() != null && !TextUtils.isEmpty(valueEntity.getThirdpartyOrder().getUrl())) {
                            // 第三方订单
                            Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                            intent.putExtra("orderId", valueEntity.getId());
                            intent.putExtra("isThirdparty", true);
                            intent.putExtra("thirdUrl", valueEntity.getThirdpartyOrder().getUrl());
                            startActivityForResult(intent, REFRESH);
                        } else {
                            Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                            intent.putExtra("orderId", valueEntity.getId());
                            intent.putExtra("agentId", valueEntity.getAgentId());
                            startActivityForResult(intent, REFRESH);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.order_state_refund:
            case R.id.order_state_refund_to_balance:
                int mTag3 = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity mValueEntity3 = adapter.getData().get(mTag3);
                Intent intent2 = new Intent(mActivity, OrderRefundInfoActivity.class);
                intent2.putExtra("orderId", mValueEntity3.getId());
                startActivity(intent2);
                break;
            case R.id.order_state_more_one:
                int tag2 = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity valueEntity1 = adapter.getData().get(tag2);
                HashMap<String, Object> map = new HashMap<>();
//                map.put("merchantId", valueEntity1.getMerchant().getId());
//                ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
//                List<OrderFragmentModel.ValueEntity.OrderItemsEntity> items = valueEntity1.getOrderItems();
//                for (OrderFragmentModel.ValueEntity.OrderItemsEntity item : items) {
//                    HashMap<String, Object> m = new HashMap<>();
//                    m.put("id", item.getGoodsId());
//                    m.put("quantity", item.getQuantity());
//                    m.put("specId", item.getGoodsSpecId());
//                    orderItems.add(m);
//                }
//                map.put("orderItems", orderItems);
                map.put("loginToken", App.getUserInfo().getToken());
                map.put("userId", App.getUserInfo().getId());
                map.put("orderId", valueEntity1.getId());
                double latitude = Double.parseDouble(PreferenceUtils.getLocation(getActivity())[0]);
                double longitude = Double.parseDouble(PreferenceUtils.getLocation(getActivity())[1]);
                map.put("longitude", longitude);
                map.put("latitude", latitude);
                VolleyOperater<ConfirmOrderModel> operaterOnce = new VolleyOperater<>(getActivity());
                loadingDialog.show(getActivity().getFragmentManager(), "");
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
                                                startActivityForResult(intent1, REFRESH);
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
                                            if (CheckUtils.isNoEmptyStr(goods.getAttributes())) {
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
//                Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
//                intent.putExtra("onceMoreOrder",map);
//                startActivityForResult(intent, REFRESH);
                break;
            case R.id.order_state_evaluate:
                int tagEvaluate = (int) v.getTag();
                final NewOrderFragmentModel.ValueEntity valueEntityEvaluate = adapter.getData().get(tagEvaluate);
                if (adapter.getData().get(tagEvaluate).getType() == 1 || adapter.getData().get(tagEvaluate).getType() == 3) {
                    mLoadingDialog.show(mActivity.getFragmentManager(), "");
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("orderId", valueEntityEvaluate.getId());
                    VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<SubmitOrderModel>(mActivity);
                    operater.doRequest(Constants.URL_ORDER_DETAIL, map1, new ResponseListener() {

                        @Override
                        public void onRsp(boolean isSucceed, Object obj) {
                            if (isSucceed) {
                                SubmitOrderModel submitOrderModel = (SubmitOrderModel) obj;
                                Intent intentEvaluate = new Intent(mActivity, NewEvaluateActivity.class);
                                Intent intent = new Intent(mActivity, EvaluateActivity.class);
                                if (submitOrderModel.getValue().getDeliveryTask() != null) {
                                    intentEvaluate.putExtra("hasDriver", true);
                                } else {
                                    intentEvaluate.putExtra("hasDriver", false);
                                }
                                intentEvaluate.putExtra("value", submitOrderModel.getValue());
                                intentEvaluate.putExtra("orderId", valueEntityEvaluate.getId());
                                intentEvaluate.putExtra("agentId", valueEntityEvaluate.getAgentId());
                                intentEvaluate.putExtra("valueEntity", valueEntityEvaluate);
                                startActivityForResult(intentEvaluate, REFRESH);
                            }
                            mLoadingDialog.dismiss();
                        }
                    }, SubmitOrderModel.class);
                } else if (adapter.getData().get(tagEvaluate).getType() == 2) {
                    String groupBuyOrderId = valueEntityEvaluate.getId();
                    String groupBuyId = valueEntityEvaluate.getGroupbuyOrder().getGroupBuyId();
                    Intent evaluateIntent = new Intent(mActivity, EvaluateGroupActivity.class);
                    evaluateIntent.putExtra("groupBuyOrderId", groupBuyOrderId);
                    evaluateIntent.putExtra("groupBuyId", groupBuyId);
                    startActivityForResult(evaluateIntent, REFRESH);
                } else if (adapter.getData().get(tagEvaluate).getType() == 4) {
                    Intent carEvaluate = new Intent(mActivity, CarHailingEvaluateActivity.class);
                    carEvaluate.putExtra("chauffeurOrder", valueEntityEvaluate.getChauffeurOrder());
                    carEvaluate.putExtra("isFromOrderList", true);
                    startActivityForResult(carEvaluate, REFRESH);
                } else if (adapter.getData().get(tagEvaluate).getType() == 6) {
                    Intent carEvaluate = new Intent(mActivity, GroupBuyingAddEvaluationActivity.class);
                    carEvaluate.putExtra("groupPurchaseOrder", valueEntityEvaluate.getGroupPurchaseOrder());
                    carEvaluate.putExtra("isFromOrderList", true);
                    startActivityForResult(carEvaluate, REFRESH);
                } else if (adapter.getData().get(tagEvaluate).getType() == 9) {
                    Intent intent = new Intent(mActivity, LegworkEvaluateActivity.class);
                    intent.putExtra("orderId", valueEntityEvaluate.getId());
                    intent.putExtra("agentId", "" + valueEntityEvaluate.getAgentId());
                    startActivityForResult(intent, REFRESH);
                }
                break;
            case R.id.order_list_item_tv_name:
            case R.id.order_list_right_arrow:

                break;

            case R.id.go_to_detail:
            case R.id.order_state_confirm:
                int position = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity order = adapter.getData().get(position);
                if (order != null) {
                    String orderId = String.valueOf(order.getId());
                    Intent intentDetail = new Intent(mActivity, OrderDetailActivity.class);
                    intentDetail.putExtra(OrderDetailActivity.ORDER_ID, orderId);
                    intentDetail.putExtra(OrderDetailActivity.ORDER_LIST_ENTITY, order);
                    startActivityForResult(intentDetail, REFRESH);
                    mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                }
                break;

            case R.id.order_list_item_img_father:
                int tag = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity valueEntity2 = adapter.getData().get(tag);
                if (valueEntity2.getType() == 1) {
                    if (valueEntity2.getMerchant().getType() == 0) {
                        Intent intent1 = new Intent(mActivity, CommercialActivity.class);
                        intent1.putExtra(CommercialActivity.MERCHANT_ID, valueEntity2.getMerchantId());
                        startActivityForResult(intent1, REFRESH);
                    } else if (valueEntity2.getType() == 1) {
//                    HomeActivity activity = (HomeActivity) getActivity();
//                    activity.setToMarket();
                    }
                } else if (valueEntity2.getType() == 2) {
                    Intent groupIntent = new Intent(mActivity, GroupPurchaseDetailActivity.class);
                    groupIntent.putExtra("id", valueEntity2.getGroupbuyOrder().getGroupBuyId());
                    startActivityForResult(groupIntent, REFRESH);
                } else if (valueEntity2.getType() == 4) {
                    CarHailingDriver driver = valueEntity2.getChauffeurOrder().getChauffeur();
                    Intent driverIntent = new Intent(mActivity, CarHailingDriverActivity.class);
                    driverIntent.putExtra("driver", driver);
                    startActivityForResult(driverIntent, REFRESH);
                } else if (valueEntity2.getType() == 6) {
                    Intent driverIntent = new Intent(mActivity, GroupBuyingQuanOrTuanDetailActivity.class);
                    driverIntent.putExtra("couponId", valueEntity2.getGroupPurchaseOrder().getGroupPurchaseCouponId());
                    startActivityForResult(driverIntent, REFRESH);
                }
                break;
            case R.id.order_state_to_see_code:
                int taget = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity valueEntity4 = adapter.getData().get(taget);
                GroupBuyingUseActivity.toGroupBuyingUseActivity(mActivity,
                        JSONArray.toJSONString(valueEntity4.getGroupPurchaseOrder().getGroupPurchaseOrderCouponCodeList()),
                        valueEntity4.getGroupPurchaseOrder().getId(),
                        JSONArray.toJSONString(valueEntity4.getGroupPurchaseOrder().getGroupPurchaseOrderCouponGoodsList()),
                        valueEntity4.getGroupPurchaseOrder().getGroupPurchaseMerchantName(),
                        valueEntity4.getGroupPurchaseOrder().getRefreshTime(), REFRESH);
                break;
            case R.id.my_group_to_group:
                int groupPos = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity group = adapter.getData().get(groupPos);
                Intent groupIntent = new Intent(mActivity, GroupPurchaseDetailActivity.class);
                groupIntent.putExtra("id", group.getGroupbuyOrder().getGroupBuyId());
                startActivityForResult(groupIntent, REFRESH);
                break;
            case R.id.my_group_to_user:
                int userPos = (int) v.getTag();
                Intent userIntent = new Intent(mActivity, PreviousGroupActivity.class);
                String userId = adapter.getData().get(userPos).getGroupbuyOrder().getGroupBuy().getGroupBuyUser().getId() + "";
                userIntent.putExtra("userId", userId);
                startActivityForResult(userIntent, REFRESH);
                break;
            case R.id.to_invite_friend:
                int sharePos = (int) v.getTag();
                shareGroup = adapter.getData().get(sharePos);
                if (mPopupWindow == null) {
                    initPopup();
                }
                changePopupState(0.5f, true);
                break;
            case R.id.to_evaluate_group:
                int evaluatePos = (int) v.getTag();
                NewOrderFragmentModel.ValueEntity evaluate = adapter.getData().get(evaluatePos);
                String groupBuyOrderId = evaluate.getId();
                String groupBuyId = evaluate.getGroupbuyOrder().getGroupBuyId();
                Intent evaluateIntent = new Intent(mActivity, EvaluateGroupActivity.class);
                evaluateIntent.putExtra("groupBuyOrderId", groupBuyOrderId);
                evaluateIntent.putExtra("groupBuyId", groupBuyId);
                startActivityForResult(evaluateIntent, REFRESH);
                break;

            case R.id.second_hand_qq:
                onClickShareToQQ(shareGroup.getGroupbuyOrder().getGroupBuy().getGoodsName(), shareGroup.getGroupbuyOrder().getGroupBuy().getDescription(),
                        shareGroup.getGroupbuyOrder().getGroupBuy().getShareUrl(), getString(shareGroup.getGroupbuyOrder().getGroupBuy().getImgs()));
                break;
            case R.id.second_hand_wechat:
                onClickShareToWechat(0, shareGroup.getGroupbuyOrder().getGroupBuy().getGoodsName(), shareGroup.getGroupbuyOrder().getGroupBuy().getDescription(),
                        shareGroup.getGroupbuyOrder().getGroupBuy().getShareUrl(), getString(shareGroup.getGroupbuyOrder().getGroupBuy().getImgs()));
                break;

            case R.id.second_hand_friend:
                onClickShareToWechat(1, shareGroup.getGroupbuyOrder().getGroupBuy().getGoodsName(), shareGroup.getGroupbuyOrder().getGroupBuy().getDescription(),
                        shareGroup.getGroupbuyOrder().getGroupBuy().getShareUrl(), getString(shareGroup.getGroupbuyOrder().getGroupBuy().getImgs()));
                break;

            case R.id.second_hand_cancel:
                hidePopup();
                break;
            case R.id.order_type_all:
                if (currentType == 0) {
                    break;
                }
                resetStyle(currentType);
                currentType = 0;
                tvTitle.setText("全部订单");
                tvAll.setTextColor(getResources().getColor(R.color.white));
                tvAll.setBackgroundResource(R.drawable.shap_orange_range_bg);
                if (tabLayout.getVisibility() == View.VISIBLE) {
                    tabLayout.setVisibility(View.GONE);
                    resetTabLayout(R.id.tab_1);
                }
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.order_type_waimai:
                if (currentType == 1) {
                    break;
                }
                resetStyle(currentType);
                currentType = 1;
                tvTitle.setText("外卖订单");
                tvWaimai.setTextColor(getResources().getColor(R.color.white));
                tvWaimai.setBackgroundResource(R.drawable.shap_orange_range_bg);
                if (tabLayout.getVisibility() == View.VISIBLE) {
                    tabLayout.setVisibility(View.GONE);
                    resetTabLayout(R.id.tab_1);
                }
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.order_type_group:
                if (currentType == 2) {
                    break;
                }
                resetStyle(currentType);
                currentType = 2;
                tvTitle.setText("拼团订单");
                tvGroup.setTextColor(getResources().getColor(R.color.white));
                tvGroup.setBackgroundResource(R.drawable.shap_orange_range_bg);
                if (tabLayout.getVisibility() == View.VISIBLE) {
                    tabLayout.setVisibility(View.GONE);
                    resetTabLayout(R.id.tab_1);
                }
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.order_type_supermarket:
                if (currentType == 3) {
                    break;
                }
                resetStyle(currentType);
                currentType = 3;
                tvTitle.setText("商超订单");
                tvMarket.setTextColor(getResources().getColor(R.color.white));
                tvMarket.setBackgroundResource(R.drawable.shap_orange_range_bg);
                if (tabLayout.getVisibility() == View.VISIBLE) {
                    tabLayout.setVisibility(View.GONE);
                    resetTabLayout(R.id.tab_1);
                }
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.order_type_car_hailing:
                if (currentType == 4) {
                    break;
                }
                resetStyle(currentType);
                currentType = 4;
                tvTitle.setText("约车订单");
                tvCarHailing.setTextColor(getResources().getColor(R.color.white));
                tvCarHailing.setBackgroundResource(R.drawable.shap_orange_range_bg);
                if (tabLayout.getVisibility() == View.VISIBLE) {
                    tabLayout.setVisibility(View.GONE);
                    resetTabLayout(R.id.tab_1);
                }
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.order_type_group_buying:
                if (currentType == 6) {
                    break;
                }
                resetStyle(currentType);
                currentType = 6;
                tvTitle.setText("团购订单");
                tvGroupBuying.setTextColor(getResources().getColor(R.color.white));
                tvGroupBuying.setBackgroundResource(R.drawable.shap_orange_range_bg);
                if (tabLayout.getVisibility() != View.VISIBLE) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.orderlist_fragment_tv_title:
            case R.id.orderlist_fragment_tv_title_arrow:
                if (!App.isLogin()) {
                    break;
                }
                if (isFilter) {
                    if (mHiddenAction == null) {
                        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                                -1.0f);
                        mHiddenAction.setDuration(300);
                    }
                    gdOrderType.startAnimation(mHiddenAction);
                    gdOrderType.setVisibility(View.GONE);
                    vShadow.setVisibility(View.GONE);
                    ivTitleArrow.setBackgroundResource(R.drawable.location_down_arrow);
                    isFilter = false;
                } else {
                    if (mapList.size() == 0) {
                        initGrideDate();
                    }
                    if (mShowAction == null) {
                        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        mShowAction.setDuration(300);
                    }
                    gdOrderType.startAnimation(mShowAction);
                    gdOrderType.setVisibility(View.VISIBLE);
                    vShadow.setVisibility(View.VISIBLE);
                    ivTitleArrow.setBackgroundResource(R.drawable.location_up_arrow);
                    isFilter = true;
                }
                break;
            case R.id.filter_shadow_view:
                if (mHiddenAction == null) {
                    mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                            0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                            -1.0f);
                    mHiddenAction.setDuration(300);
                }
                gdOrderType.startAnimation(mHiddenAction);
                gdOrderType.setVisibility(View.GONE);
                vShadow.setVisibility(View.GONE);
                ivTitleArrow.setBackgroundResource(R.drawable.location_down_arrow);
                isFilter = false;
                break;
            case R.id.tab_1:
                if (currentStatus == 0) return;
                resetTabLayout(v.getId());
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.tab_2:
                if (currentStatus == 1) return;
                resetTabLayout(v.getId());
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.tab_3:
                if (currentStatus == 2) return;
                resetTabLayout(v.getId());
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.tab_4:
                if (currentStatus == 3) return;
                resetTabLayout(v.getId());
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            case R.id.tab_5:
                if (currentStatus == 4) return;
                resetTabLayout(v.getId());
                listView.resetCurrentMode();
                page = 0;
                listView.autoRefresh();
                break;
            default:
                break;
        }
    }

    public void resetFilter() {
        if (gdOrderType != null)
            gdOrderType.setVisibility(View.GONE);
        if (vShadow != null)
            vShadow.setVisibility(View.GONE);
        if (ivTitleArrow != null)
            ivTitleArrow.setBackgroundResource(R.drawable.location_down_arrow);
        isFilter = false;
    }

    public void resetType() {
        currentType = 0;
    }

    private void resetStyle(int currentType) {
        gdOrderType.setVisibility(View.GONE);
        vShadow.setVisibility(View.GONE);
        ivTitleArrow.setBackgroundResource(R.drawable.location_down_arrow);
        isFilter = false;
        switch (currentType) {
            case 0:
                tvAll.setBackgroundResource(R.drawable.shap_gray_range_bg);
                tvAll.setTextColor(getResources().getColor(R.color.color_3));
                break;
            case 1:
                tvWaimai.setBackgroundResource(R.drawable.shap_gray_range_bg);
                tvWaimai.setTextColor(getResources().getColor(R.color.color_3));
                break;
            case 3:
                tvMarket.setBackgroundResource(R.drawable.shap_gray_range_bg);
                tvMarket.setTextColor(getResources().getColor(R.color.color_3));
                break;
            case 2:
                tvGroup.setBackgroundResource(R.drawable.shap_gray_range_bg);
                tvGroup.setTextColor(getResources().getColor(R.color.color_3));
                break;
            case 4:
                tvCarHailing.setBackgroundResource(R.drawable.shap_gray_range_bg);
                tvCarHailing.setTextColor(getResources().getColor(R.color.color_3));
                break;
            case 6:
                tvGroupBuying.setBackgroundResource(R.drawable.shap_gray_range_bg);
                tvGroupBuying.setTextColor(getResources().getColor(R.color.color_3));
                break;
        }
    }

    /**
     * 删除订单
     *
     * @param
     */
    private void deleteOrder() {
        NewOrderFragmentModel.ValueEntity valueEntity = adapter.getData().get(deleteTag);
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", valueEntity.getId());
        VolleyOperater<DeleteOrderModel> operater = new VolleyOperater<DeleteOrderModel>(mActivity);
        operater.doRequest(Constants.URL_DELETE_ORDER, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    DeleteOrderModel deleteOrderModel = (DeleteOrderModel) obj;
                    if (deleteOrderModel.isSuccess()) {
                        adapter.getData().remove(deleteTag);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.displayMsg(deleteOrderModel.getValue(), mActivity);
                    }
                }
            }
        }, DeleteOrderModel.class);
    }

    public void refreshList() {
        checkState();
        if (listView != null) {
            listView.resetCurrentMode();
            listView.setRefreshing(true);
        }
    }

    @Override
    public void onSure() {
        deleteOrder();
        dialog.dismiss();
    }

    @Override
    public void onExit() {
        dialog.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewOrderFragmentModel.ValueEntity order = adapter.getData().get(position - 1);
        if (order != null) {
            if (order.getType() == 0 || order.getType() == 1 || order.getType() == 3) {
                String orderId = String.valueOf(order.getId());
                Intent intentDetail = new Intent(mActivity, OrderDetailActivity.class);
                intentDetail.putExtra(OrderDetailActivity.ORDER_ID, orderId);
                intentDetail.putExtra(OrderDetailActivity.ORDER_LIST_ENTITY, order);
                startActivityForResult(intentDetail, REFRESH);
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
            } else if (order.getType() == 2) {
                String orderId = String.valueOf(order.getId());
                Intent intentDetail = new Intent(mActivity, MyGroupPurchaseDetailActivity.class);
                intentDetail.putExtra(OrderDetailActivity.ORDER_ID, orderId);
                startActivityForResult(intentDetail, REFRESH);
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
            } else if (order.getType() == 4) {
                String orderId = String.valueOf(order.getId());
                Intent intentDetail = new Intent(mActivity, CarHailingOrderDetailActivity.class);
                intentDetail.putExtra(OrderDetailActivity.ORDER_ID, orderId);
                startActivityForResult(intentDetail, REFRESH);
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
            }else if (order.getType() == 6) {
                if(order.getGroupPurchaseOrder().getOrderType()==3){
                    Intent intentDetail = new Intent(mActivity, PayBillDetailActivity.class);
                    intentDetail.putExtra("orderId", order.getGroupPurchaseOrder().getId());
                    startActivityForResult(intentDetail, REFRESH);
                    mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                }else {
                    Intent intentDetail = new Intent(mActivity, GroupBuyingOrderForGoodsDetailsActivity.class);
                    intentDetail.putExtra("orderId", order.getGroupPurchaseOrder().getId());
                    startActivityForResult(intentDetail, REFRESH);
                }
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
            }else if (order.getType() == 9) {
                Intent intentDetail = new Intent(mActivity, LegworkOrderdetailsActivity.class);
                intentDetail.putExtra("orderId", order.getLegWorkOrder().getId());
                startActivityForResult(intentDetail, REFRESH);
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
            } else {
                if (order.getThirdpartyOrder() == null || TextUtils.isEmpty(order.getThirdpartyOrder().getUrl())) {
                    ToastUtils.displayMsg("请升级马管家最新版本", mActivity);
                    return;
                }
                Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, order.getThirdpartyOrder().getUrl());
                startActivityForResult(intent, REFRESH);
            }
        }
    }

    private String getString(String imgs) {
        if (TextUtils.isEmpty(imgs)) {
            imgs = "";
        } else {
            imgs = imgs.split(";")[0];
        }
        return imgs;
    }

    private void hidePopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private void initPopup() {
        View view = mInflater.inflate(R.layout.popup_second_hand_share, null);
        LinearLayout llQQ = (LinearLayout) view.findViewById(R.id.second_hand_qq);
        LinearLayout llWechat = (LinearLayout) view.findViewById(R.id.second_hand_wechat);
        LinearLayout llFriend = (LinearLayout) view.findViewById(R.id.second_hand_friend);
        TextView tvCancel = (TextView) view.findViewById(R.id.second_hand_cancel);
        llFriend.setVisibility(View.GONE);
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
            mPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }


    /**
     * param flag 0 好友，1 朋友圈
     */
    private void onClickShareToWechat(int flag, String title, String summary, String tagUrl, String imgUrl) {
        if (!App.getApi().isWXAppInstalled()) {
            ToastUtils.displayMsg("请先安装微信客户端", getActivity());
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        if ("com.horsegj.company".equals(App.getInstance().getPackageName())) {
            tagUrl += "&scheme=mgjofficial";
        } else {
            tagUrl += "&scheme=mgjqyue";
        }
        webpage.webpageUrl = tagUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title;
        msg.description = summary;
        Bitmap thumb = null;
        if (CheckUtils.isNoEmptyStr(imgUrl)) {
            if (imgUrl.contains(";")) {
                String[] strings = imgUrl.split(";");
                imgUrl = strings[0];
            }
            thumb = ImageUtils.downLoadImageUrl(imgUrl);
            if (ImageUtils.getBmpSize(thumb) > 32 * 1024) { //weixin要求图片小于32K
                thumb = ImageUtils.compressImg(thumb, 32);
            }
        }
        if (thumb == null) {
            thumb = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
        }
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        App.getApi().sendReq(req);
    }

    private void onClickShareToQQ(String title, String summary, String tagUrl, String imgUrl) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        if ("com.horsegj.company".equals(App.getInstance().getPackageName())) {
            tagUrl += "&scheme=mgjofficial";
        } else {
            tagUrl += "&scheme=mgjqyue";
        }
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, tagUrl);
        if (CheckUtils.isNoEmptyStr(imgUrl) && imgUrl.contains(";")) {
            String[] strings = imgUrl.split(";");
            imgUrl = strings[0];
        }
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));

        App.getmTencent().shareToQQ(mActivity, params, listener);
    }

    private BaseUiListener listener = new BaseUiListener();

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        deleteTag = position - 1;
        dialog.show();
        return true;
    }

    /**
     * QQ分享回调
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            ToastUtils.displayMsg("分享成功", getActivity());
            mActivity.finish();
        }

        @Override
        public void onError(UiError e) {
            ToastUtils.displayMsg("分享失败", getActivity());
        }

        @Override
        public void onCancel() {
            mActivity.finish();
        }
    }
}
