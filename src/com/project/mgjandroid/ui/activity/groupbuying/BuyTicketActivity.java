package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCoupon;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmGroupOrModel;
import com.project.mgjandroid.model.ConfirmGroupOrderModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingPreviewModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.SelectRedBagActivity;
import com.project.mgjandroid.ui.adapter.DateAdapter;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CalendarUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyTicketActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.img_calendar)
    private ImageView imgCalendar;
    @InjectView(R.id.tv_appointment_date)
    private TextView tvDate;
    @InjectView(R.id.common_back)
    private ImageView tvBack;
    @InjectView(R.id.rl_red_bag)
    private RelativeLayout rlRedBag;
    @InjectView(R.id.tv_ticket_name)
    private TextView tvTicketName;
    @InjectView(R.id.tv_ticket_price)
    private TextView tvTicketPrice;
    @InjectView(R.id.tv_count)
    private TextView tvCount;
    @InjectView(R.id.iv_add)
    private ImageView ivAdd;
    @InjectView(R.id.iv_minus)
    private ImageView ivMinus;
    @InjectView(R.id.tv_red_bag)
    private TextView tvRedBag;
    @InjectView(R.id.tv_pay_price)
    private TextView tvPayPrice;
    @InjectView(R.id.rl_calendar)
    private RelativeLayout rlCalendar;
    @InjectView(R.id.tv_submit_order)
    private TextView tvSubmitOrder;
    @InjectView(R.id.tv_bell)
    private TextView tvADate;
    @InjectView(R.id.layout_groupon)
    private RelativeLayout layoutGroupon;
    @InjectView(R.id.layout_voucher)
    private RelativeLayout layoutVoucher;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.tv_option)
    private TextView tvOption;
    @InjectView(R.id.tv_price)
    private TextView tvPrice;
    @InjectView(R.id.tv_price1)
    private TextView tvPrice1;
    @InjectView(R.id.img)
    private CornerImageView icon;


    private GridView record_gridView;

    private DateAdapter dateAdapter;//定义adapter
    private ImageView record_left;//左箭头
    private ImageView record_right;//右箭头
    private TextView record_title;//标题
    private int year;
    private int month;
    private String title;
    private int[][] days = new int[6][7];
    private int[] days1 = new int[42];
    private CalendarView calendarView;
    private Context context;
    private PopupWindow mPopWindow;
    private RedBag redBag;
    private boolean isClickNext;
    private boolean isClickLast = true;
    private MLoadingDialog mLoadingDialog;
    private String errorMsg;
    private String ticketPrice;
    private long agentId;
    private String ticketName;
    private int count = 1;
    private String ticketOriginalPrice;
    private int type;
    private int bespeak;
    private int bespeakDays;
    private GroupPurchaseCoupon groupPurchaseCoupon;
    private ConfirmGroupOrModel confirmGroupOrModel;
    String stringTime ="";
    String sMonth = "";
    private int currrentDay;
    private String sDay ="";


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_buy_ticket);
        Injector.get(this).inject();
        //初始化日期数据
        initData();
        //初始化组件
        initPopWindow();
        //组件点击监听事件
        initEvent();

    }

    private void initData() {
        ticketName = getIntent().getStringExtra("ticketName");
        agentId = getIntent().getLongExtra("agentId", -1);
        ticketPrice = getIntent().getStringExtra("ticketPrice");
        ticketOriginalPrice = getIntent().getStringExtra("ticketOriginalPrice");
        type = getIntent().getIntExtra("type", -1);
        bespeak = getIntent().getIntExtra("bespeak", -1);
        bespeakDays = getIntent().getIntExtra("bespeakDayCount", -1);
        groupPurchaseCoupon = (GroupPurchaseCoupon) getIntent().getSerializableExtra("groupPurchaseCoupon");

        year = CalendarUtils.getYear();
        month = CalendarUtils.getMonth();
    }

    private void initEvent() {
        record_left.setOnClickListener(this);
        record_right.setOnClickListener(this);
        imgCalendar.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        rlRedBag.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        tvSubmitOrder.setOnClickListener(this);
        mLoadingDialog = new MLoadingDialog();

        if (type == 1) {
            layoutVoucher.setVisibility(View.VISIBLE);
            layoutGroupon.setVisibility(View.GONE);
            rlCalendar.setVisibility(View.GONE);
            tvTicketName.setText(ticketName);
        } else {
            layoutVoucher.setVisibility(View.GONE);
            layoutGroupon.setVisibility(View.VISIBLE);
            if (bespeak == 1) {
                rlCalendar.setVisibility(View.VISIBLE);
            } else {
                rlCalendar.setVisibility(View.GONE);
            }
            tvName.setText(groupPurchaseCoupon.getGroupPurchaseName());
            if (CheckUtils.isNoEmptyStr(groupPurchaseCoupon.getImages())) {
                ImageUtils.loadBitmap(mActivity, groupPurchaseCoupon.getImages().split(";")[0], icon, R.drawable.horsegj_default, Constants.getEndThumbnail(130, 110));
            }
            if(groupPurchaseCoupon.getIsPurchaseRestriction()==3){
                tvOption.setText(groupPurchaseCoupon.getIsBespeak() == 0 ? "免预约" : "需预约 ");
            }else {
                tvOption.setText((groupPurchaseCoupon.getIsBespeak() == 0 ? "免预约 | " : "需预约 | ") + "不可叠加");
            }
            tvPrice.setText("¥" + StringUtils.BigDecimal2Str(groupPurchaseCoupon.getPrice()));
            if (groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice() != null && groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice().compareTo(BigDecimal.ZERO) > 0) {
                tvPrice1.setText("门市价¥" + StringUtils.BigDecimal2Str(groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice()));
            }
        }
        tvTicketPrice.setText("¥" + ticketPrice+"元 代金券");
        tvCount.setText("" + count);
        getOrderPreview();
    }


    private void initPopWindow() {
        View view = mInflater.inflate(R.layout.layout_bottom_calendar, null);
        mPopWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopWindow.setBackgroundDrawable(cd);
        mPopWindow.setOutsideTouchable(false);
        mPopWindow.setFocusable(true);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        record_gridView = (GridView) view.findViewById(R.id.record_gridView);
        days = CalendarUtils.getDayOfMonthFormat(year, month);
        dateAdapter = new DateAdapter(this, days, year, month, bespeakDays,groupPurchaseCoupon);
        record_gridView.setAdapter(dateAdapter);
        record_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bespeakDays == 0) {
                    toast("不可预约");
                    return;
                }
                TextView dataTime = (TextView) view.findViewById(R.id.date_item);
                if (dataTime.getVisibility() == View.VISIBLE && (dataTime.getCurrentTextColor() == getResources().getColor(R.color.color_3) || dataTime.getCurrentTextColor() == getResources().getColor(R.color.bg_festival) || dataTime.getCurrentTextColor() == getResources().getColor(R.color.white))) {
                    int dayNum = 0;
                    //将二维数组转化为一维数组，方便使用
                    for (int i = 0; i < days.length; i++) {
                        for (int j = 0; j < days[i].length; j++) {
                            days1[dayNum] = days[i][j];
                            dayNum++;
                        }
                    }
                    currrentDay = days1[position];
                    sMonth = ""+month;
                    if(month<10){
                        sMonth ="0"+month;
                    }
                    sDay = ""+currrentDay;
                    if(currrentDay<10){
                        sDay ="0"+currrentDay;
                    }
                    stringTime =year + "-" + sMonth + "-" + sDay;
                    if(CheckUtils.isNoEmptyStr(groupPurchaseCoupon.getSellOutDates())){
                        List<String> stringList = com.alibaba.fastjson.JSONArray.parseArray(groupPurchaseCoupon.getSellOutDates(), String.class);
                        for(String sList : stringList){
                            if(stringTime.equals(sList)){
                                toast("已售罄，不可预约");
                                return;
                            }
                        }
                    }
                    if (mPopWindow != null) {
                        mPopWindow.dismiss();
                    }
                    tvDate.setText(stringTime);
                    if (CheckUtils.isNoEmptyStr(groupPurchaseCoupon.getCancelAfterVerificationTime())) {
                        tvADate.setVisibility(View.VISIBLE);
                        tvADate.setText(groupPurchaseCoupon.getCancelAfterVerificationTime() + "自动使用");
                    }
                    record_gridView.setItemChecked(position, true);
                    dateAdapter.setSeclection(position, month);
                } else {
                    if (dataTime.getVisibility() == View.VISIBLE) {
                        toast("不可预约");
                    }
                }
            }
        });
        /**
         * 以下是初始化其他组件
         */
        record_left = (ImageView) view.findViewById(R.id.record_left);
        record_right = (ImageView) view.findViewById(R.id.record_right);
        record_title = (TextView) view.findViewById(R.id.record_title);
        setTile();
    }


    /**
     * 下一个月
     *
     * @return
     */
    private int[][] nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        days = CalendarUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    /**
     * 上一个月
     *
     * @return
     */
    private int[][] prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        days = CalendarUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    /**
     * 设置标题
     */
    private void setTile() {
        title = year + "年" + month + "月";
        record_title.setText(title);
    }

    private void showCalendar() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SelectRedBagActivity.RED_BAG_MONEY) {
            redBag = (RedBag) data.getSerializableExtra(SelectRedBagActivity.RED_MONEY_BAG);
            getOrderPreview();
        }
    }

    public void submitOrder() {

        Map<String, Object> params = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        data.put("groupPurchaseCouponId", groupPurchaseCoupon.getId());
        data.put("groupPurchaseCouponType", groupPurchaseCoupon.getType());
        ArrayList<Map<String, Object>> redBagList = new ArrayList<>();
        if (redBag != null) {
            Map<String, Object> redmap = new HashMap<>();
            redmap.put("id", redBag.getId());
            redmap.put("name", redBag.getName());
            redmap.put("amt", redBag.getAmt());
            redmap.put("promotionType", redBag.getPromotionType());
            redBagList.add(redmap);
        }
        data.put("loginToken", App.getUserInfo().getToken());
        data.put("merchantId", groupPurchaseCoupon.getMerchantId());
        if (groupPurchaseCoupon.getType() == 1) {
            data.put("originalPrice", groupPurchaseCoupon.getOriginPrice());
        } else {
            data.put("originalPrice", groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice());
        }
        if (groupPurchaseCoupon.getType() == 1) {
            data.put("totalOriginalPrice", groupPurchaseCoupon.getOriginPrice().multiply(BigDecimal.valueOf(count)));
        } else {
            data.put("totalOriginalPrice", groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice().multiply(BigDecimal.valueOf(count)));
        }
        data.put("price", groupPurchaseCoupon.getPrice());
        data.put("quantity", count);
//        data.put("totalPrice", 1);
        data.put("userId", App.getUserInfo().getId());
//        团购订单订单类型 1, "代金券",2, "团购券",3, "优惠买单"
        data.put("groupPurchaseOrderType", type);
        if (redBagList.size() > 0) {
            data.put("redBags", redBagList);
        }
        if (groupPurchaseCoupon.getType() == 2 && bespeak == 1) {
            if ("请选择日期".equals(tvDate.getText().toString().trim())) {
                toast("请选择日期");
                return;
            }
            data.put("targetDate", tvDate.getText().toString().trim());
        }

        data.put("totalPrice", confirmGroupOrModel.getTotalPrice());
//        params.put("groupPurchaseOrderCouponCodeList", JSON.toJSONString(data));
        params.put("data", JSON.toJSONString(data));
        mLoadingDialog.show(getFragmentManager(), "");
        VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GROUP_PURCHASE_ORDER_SUBMIT, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    SubmitOrderModel submitOrderModel = (SubmitOrderModel) obj;
                    Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                    intent.putExtra("orderId", submitOrderModel.getValue().getId());
                    intent.putExtra("merchantId", submitOrderModel.getValue().getMerchantId());
                    intent.putExtra("agentId", submitOrderModel.getValue().getAgentId());
                    intent.putExtra("grouponName", groupPurchaseCoupon.getGroupPurchaseName());
                    intent.putExtra("voucherName", "¥" + ticketPrice+"元 代金券");
                    intent.putExtra("type", type);
                    intent.putExtra("isGroupPurchase", true);
                    startActivity(intent);
                    finish();
                }
            }
        }, SubmitOrderModel.class);
    }

    private void getOrderPreview() {
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


        params.put("itemPrice", ticketPrice);
//        params.put("totalPrice", totalPrice);
        params.put("quantity", count);
        params.put("agentId", agentId);
        params.put("businessType", 6);
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
                    confirmGroupOrModel = ((ConfirmGroupOrderModel) obj).getValue();
                    showPreviewOrder(confirmGroupOrModel);
                }
            }
        }, ConfirmGroupOrderModel.class);
    }

    private void showPreviewOrder(ConfirmGroupOrModel confirmGroupOrModel) {
        if (redBag != null) {
            tvRedBag.setText("-¥" + StringUtils.BigDecimal2Str(redBag.getAmt()));
        } else {
            tvRedBag.setText("无可用红包");
            if (confirmGroupOrModel.getPlatformRedBagCount() > 0) {
                tvRedBag.setText("有" + confirmGroupOrModel.getPlatformRedBagCount() + "个可用红包");
            }
        }
        if (!CheckUtils.isEmptyStr(StringUtils.BigDecimal2Str(confirmGroupOrModel.getTotalPrice()))) {
            tvPayPrice.setText("¥" + confirmGroupOrModel.getTotalPrice());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_calendar:
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    showCalendar();
                } else {
                    mPopWindow.dismiss();
                }
                break;
            case R.id.record_left:
                if (isClickNext) {
                    isClickLast = true;
                    record_right.setEnabled(true);
                    record_left.setEnabled(false);
                    record_right.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.ic_calendar_right));
                    record_left.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.ic_last_unselect));
                }
                days = prevMonth();
                if (dateAdapter != null) {
                    dateAdapter.setData(days, year, month, bespeakDays);
                } else {
                    dateAdapter = new DateAdapter(this, days, year, month, bespeakDays,groupPurchaseCoupon);
                    dateAdapter.notifyDataSetChanged();
                }
                setTile();
                break;
            case R.id.record_right:
                if (isClickLast) {
                    isClickNext = true;
                    record_left.setEnabled(true);
                    record_right.setEnabled(false);
                    record_left.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.ic_calendar_left));
                    record_right.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.ic_next_unselect));
                }
                days = nextMonth();
                if (dateAdapter != null) {
                    dateAdapter.setData(days, year, month, bespeakDays);
                } else {
                    dateAdapter = new DateAdapter(this, days, year, month, bespeakDays,groupPurchaseCoupon);
                    dateAdapter.notifyDataSetChanged();
                }

                setTile();
                break;
            case R.id.common_back:
                back();
                break;
            case R.id.rl_red_bag:
                Intent intentSelect = new Intent(this, SelectRedBagActivity.class);
                intentSelect.putExtra(SelectRedBagActivity.ITEMS_PRICE, ticketPrice);
                intentSelect.putExtra(SelectRedBagActivity.BUSINESS_TYPE, 6);
                intentSelect.putExtra("isAgentId", groupPurchaseCoupon.getAgentId());
                if (redBag != null) {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, redBag.getId());
                } else {
                    intentSelect.putExtra(SelectRedBagActivity.PLATFORM_REDBAG_ID, -1l);
                }
                startActivityForResult(intentSelect, 1111);
                break;
            case R.id.iv_add:
                count++;
                ivMinus.setEnabled(true);
                tvCount.setText("" + count);
                getOrderPreview();
                break;
            case R.id.iv_minus:
                if (count == 1) {
                    ivMinus.setEnabled(false);
                    break;
                }
                count--;
                tvCount.setText("" + count);
                getOrderPreview();
                break;
            case R.id.tv_submit_order:
                submitOrder();
                break;
        }

    }
}
