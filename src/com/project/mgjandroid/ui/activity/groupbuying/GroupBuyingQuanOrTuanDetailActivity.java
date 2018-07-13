package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCoupon;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCouponGoods;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCouponGoodsType;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCouponList;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseEvaluate;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingCouponModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingEvaluationListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.BindMobileActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.pictureviewer.PictureViewActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.MyScrollView;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2017/3/8.
 */

@Router(value = "groupPurchaseCoupon/:couponId", longParams = "couponId")
public class GroupBuyingQuanOrTuanDetailActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView comBack;
    @InjectView(R.id.common_title)
    private TextView comTitle;
    @InjectView(R.id.com_share)
    private ImageView comShare;
    @InjectView(R.id.scroll_view)
    private MyScrollView scrollView;
    @InjectView(R.id.buy_frame)
    private FrameLayout buyFrame;
    @InjectView(R.id.img)
    private ImageView img;
    @InjectView(R.id.tv_price)
    private TextView tvPrice;
    @InjectView(R.id.tv_buy)
    private TextView tvBuy;
    @InjectView(R.id.tv_price_1)
    private TextView tvPrice1;
    @InjectView(R.id.tv_buy_1)
    private TextView tvBuy1;
    @InjectView(R.id.option_layout)
    private LinearLayout optionLayout;
    @InjectView(R.id.text_1)
    private TextView tv_1;
    @InjectView(R.id.text_2)
    private TextView tv_2;
    @InjectView(R.id.text_3)
    private TextView tv_3;
    @InjectView(R.id.text_4)
    private TextView tv_4;
    @InjectView(R.id.space_view)
    private View spaceView;
    @InjectView(R.id.address_layout)
    private LinearLayout addressLayout;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.tv_address)
    private TextView tvAddress;
    @InjectView(R.id.tv_distance)
    private TextView tvDistance;
    @InjectView(R.id.iv_call)
    private ImageView ivCall;
    @InjectView(R.id.recommend_layout)
    private LinearLayout recommendLayout;
    @InjectView(R.id.recommend_dishes)
    private TextView tvDishes;
    @InjectView(R.id.tuan_layout)
    private LinearLayout tuanLayout;
    @InjectView(R.id.goods_layout)
    private LinearLayout goodsLayout;
    @InjectView(R.id.tv_use_range)
    private TextView tvUseRange;
    @InjectView(R.id.tv_limit_date)
    private TextView tvLimitDate;
    @InjectView(R.id.tv_use_time)
    private TextView tvUseTime;
    @InjectView(R.id.use_rule_layout)
    private LinearLayout useRulesLayout;
    @InjectView(R.id.tv_buy_take_away)
    private LinearLayout tvBuyTakeAway;
    @InjectView(R.id.tv_evaluate)
    private TextView tvEvaluation;
    @InjectView(R.id.evaluation_layout)
    private LinearLayout evaluationLayout;
    @InjectView(R.id.evaluation_list)
    private NoScrollListView evaluationListView;
    @InjectView(R.id.discount_layout)
    private LinearLayout discountLayout;
    @InjectView(R.id.discount_tuan_layout)
    private LinearLayout discountTuanLayout;

    private PopupWindow mPopupWindow;
    private TextView tvAmt;
    private TextView tvCount;
    private ImageView ivMinus;
    private int count = 1;

    private GroupPurchaseCoupon groupPurchaseCoupon;
    private GroupPurchaseMerchant merchant;
    private long couponId;
    private MLoadingDialog loadingDialog;
    private PopupWindow mPhoneWindow;
    private ShareUtil shareUtil;
    private CallPhoneDialog dialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_quan_or_tuan_detail);
        Injector.get(this).inject();
        initView();
        couponId = getIntent().getLongExtra("couponId", -1);
        if (couponId == -1) {
            finish();
            return;
        }
        getGroupPurchaseCoupon();
    }

    private void initView() {
        comBack.setOnClickListener(this);
        comShare.setVisibility(View.VISIBLE);
        comShare.setOnClickListener(this);
        tvBuy.setOnClickListener(this);
        tvBuy1.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        tvBuyTakeAway.setOnClickListener(this);
        tvEvaluation.setOnClickListener(this);
        scrollView.setOnScrollChangeListener(new MyScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(int t, int oldt) {
                MLog.e("----> t: " + t + "  , oldt: " + oldt);
                if (t >= img.getHeight()) {
                    buyFrame.setVisibility(View.VISIBLE);
                } else {
                    buyFrame.setVisibility(View.GONE);
                }
            }
        });
        loadingDialog = new MLoadingDialog();
    }

    private void showData() {
        scrollView.setVisibility(View.VISIBLE);
        if (groupPurchaseCoupon.getType() == 1) {
            comTitle.setText("代金券");
        } else {
            comTitle.setText("团购券");
        }
        getgetGroupPurchaseCouponList(groupPurchaseCoupon.getType());
        if (CheckUtils.isNoEmptyStr(groupPurchaseCoupon.getImages())) {
            ImageUtils.loadBitmap(mActivity, groupPurchaseCoupon.getImages().split(";")[0], img, R.drawable.horsegj_default, Constants.getEndThumbnail(375, 190));
            img.setOnClickListener(this);
        }
        String price = StringUtils.BigDecimal2Str(groupPurchaseCoupon.getPrice());
        String str = "¥ " + price;
        if (groupPurchaseCoupon.getType() == 1) {
            str += "\u3000代" + StringUtils.BigDecimal2Str(groupPurchaseCoupon.getOriginPrice()) + "元";
        } else if (groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice() != null && groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice().compareTo(BigDecimal.ZERO) > 0) {
            str += "\u3000门市价: ¥" + StringUtils.BigDecimal2Str(groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice());
        }
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#f95046")), 0, price.length() + 2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new TextAppearanceSpan(null, 0, getResources().getDimensionPixelSize(R.dimen.x15), null, null), 0, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new TextAppearanceSpan(null, 0, getResources().getDimensionPixelSize(R.dimen.x30), null, null), 2, price.length() + 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvPrice.setText(style);
        tvPrice1.setText(style);

        showOption();

        tvLimitDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(groupPurchaseCoupon.getCreateTime()) + " 至 " + groupPurchaseCoupon.getEndTime().replace("-", "."));
        tvUseRange.setText(groupPurchaseCoupon.getApplyRange());
        tvUseTime.setText(groupPurchaseCoupon.getConsumeTime() + "\u3000");
        showUseRules();

        merchant = groupPurchaseCoupon.getGroupPurchaseMerchant();
        if (merchant != null) {
            tvName.setText(merchant.getName());
            tvAddress.setText(merchant.getAddress());
            if (merchant.getDistance() != null) {
                if (merchant.getDistance() > 1000) {
                    Double d = merchant.getDistance() / 1000;
                    tvDistance.setText(new DecimalFormat("0.00").format(d) + "km");
                } else {
                    tvDistance.setText(merchant.getDistance().intValue() + "m");
                }
            } else {
                tvDistance.setText("");
            }
            if (groupPurchaseCoupon.getType() == 1 && CheckUtils.isNoEmptyStr(merchant.getMerchantRecommend())) {
                recommendLayout.setVisibility(View.VISIBLE);
                tvDishes.setText(merchant.getMerchantRecommend());
            }
            if (groupPurchaseCoupon.getType() == 2 && CheckUtils.isNoEmptyList(groupPurchaseCoupon.getGroupPurchaseCouponGoodsTypeList())) {
                showGoodsList();
            }
        }
        if (merchant.getMerchantCommentNum() != null && merchant.getMerchantCommentNum() > 0) {
            tvEvaluation.setText("评价（" + merchant.getMerchantCommentNum() + "）");
            getEvaluation();
        }
    }

    /**
     * 显示团购套餐
     */
    private void showGoodsList() {
        tuanLayout.setVisibility(View.VISIBLE);
        List<GroupPurchaseCouponGoodsType> list = groupPurchaseCoupon.getGroupPurchaseCouponGoodsTypeList();
        for (GroupPurchaseCouponGoodsType goodsType : list) {
            if (list.size() > 1) {
                TextView textView = new TextView(mActivity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.x25));
                textView.setBackgroundResource(R.drawable.shape_yellow_bg);
                textView.setTextColor(ContextCompat.getColor(mActivity, R.color.color_3));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textView.setGravity(Gravity.CENTER);
                textView.setText(goodsType.getTypeName());
                goodsLayout.addView(textView, params);
            } else {
                View v = new View(mActivity);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                v.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_e5));
                goodsLayout.addView(v, p);
            }
            List<GroupPurchaseCouponGoods> goodsList = goodsType.getGroupPurchaseCouponGoodsList();
            for (int i = 0, size = goodsList.size(); i < size; i++) {
                RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.group_buying_goods_item, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tvName = (TextView) layout.findViewById(R.id.name);
                TextView tvCount = (TextView) layout.findViewById(R.id.count);
                TextView tvPrice = (TextView) layout.findViewById(R.id.price);
                tvName.setText(goodsList.get(i).getName());
                tvCount.setText(goodsList.get(i).getQuantity() + "份");
                tvPrice.setText("¥" + StringUtils.BigDecimal2Str(goodsList.get(i).getOriginPrice().multiply(BigDecimal.valueOf(goodsList.get(i).getQuantity()))));
                goodsLayout.addView(layout, layoutParams);
                if (i != size - 1) {
                    View v = new View(mActivity);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                    v.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_e5));
                    goodsLayout.addView(v, p);
                }
            }
        }
    }

    /**
     * 显示服务备注
     */
    private void showOption() {
        tv_1.setText("随时退款");
        tv_2.setText("过期自动退");
        if (groupPurchaseCoupon.getIsBespeak() == 0) {
            tv_3.setText("免预约");
        } else {
            tv_3.setText("需预约");
        }
        if (groupPurchaseCoupon.getIsCumulate() == 0) {
            tv_4.setText("不可叠加");
        } else {
            tv_4.setText("可叠加");
        }
        if (groupPurchaseCoupon.getType() == 2) {
            tv_4.setText("不可叠加");
        }
    }

    /**
     * 显示服务备注
     */
    private void showUseRules() {
        String[] rules = groupPurchaseCoupon.getUseRule().split("\\r\\n");
        for (String rule : rules) {
            LinearLayout layout = new LinearLayout(mActivity);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            View view = mInflater.inflate(R.layout.group_buying_use_rule_point, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x13), getResources().getDimensionPixelOffset(R.dimen.x14));
            layout.addView(view, params);

            TextView tv = new TextView(mActivity);
            tv.setTextColor(getResources().getColor(R.color.color_6));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setText(rule);
            layout.addView(tv);

            LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsChild.topMargin = getResources().getDimensionPixelOffset(R.dimen.x5);
            useRulesLayout.addView(layout, paramsChild);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                back();
                break;
            case R.id.com_share:
                if (shareUtil == null && merchant != null && groupPurchaseCoupon != null) {
                    shareUtil = new ShareUtil(mActivity, groupPurchaseCoupon.getType() == 1 ? (merchant.getName() + "代金券") : groupPurchaseCoupon.getGroupPurchaseName(),
                            tvPrice.getText() + "\n" + (CheckUtils.isNoEmptyStr(merchant.getDescription()) ? merchant.getDescription() : "独乐不如众乐，分享好东西给你，快上马管家抢购吧~"),
                            groupPurchaseCoupon.getShareUrl(), groupPurchaseCoupon.getImages());
                }
                if (shareUtil != null) shareUtil.showPopupWindow();
                break;
            case R.id.img:
                if (groupPurchaseCoupon != null && CheckUtils.isNoEmptyStr(groupPurchaseCoupon.getImages())) {
                    List<String> stringList = Arrays.asList(groupPurchaseCoupon.getImages().split(";"));
                    PictureViewActivity.toViewPicture(mActivity, JSONArray.toJSONString(stringList), 1);
                }
                break;
            case R.id.tv_buy:
            case R.id.tv_buy_1:
                showPopupWindow();
                break;
            case R.id.address_layout:
                if (merchant != null)
                    GroupBuyingMapActivity.toGroupBuyingMapActivity(mActivity, merchant.getName(), merchant.getAddress(), merchant.getLatitude(), merchant.getLongitude());
                break;
            case R.id.iv_call:
                showPhoneWindow();
                break;
            case R.id.outside:
                dismissPopupWindow();
                break;
            case R.id.iv_minus:
                if (count <= 1) {
                    return;
                } else {
                    if (--count == 1) {
                        ivMinus.setImageResource(R.drawable.min_group_goods_gray);
                    }
                    tvCount.setText("" + count);
                    tvAmt.setText("¥" + StringUtils.BigDecimal2Str(groupPurchaseCoupon.getPrice().multiply(BigDecimal.valueOf(count))));
                }
                break;
            case R.id.iv_add:
                if (count >= 9) {
                    ToastUtils.displayMsg("最多只能购买9张哦~", mActivity);
                    return;
                }
                if (++count == 2) {
                    ivMinus.setImageResource(R.drawable.min_group_goods);
                }
                tvCount.setText("" + count);
                tvAmt.setText("¥" + StringUtils.BigDecimal2Str(groupPurchaseCoupon.getPrice().multiply(BigDecimal.valueOf(count))));
                break;
            case R.id.tv_submit:
                if (!App.isLogin()) {
                    Intent intent = new Intent(mActivity, SmsLoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (TextUtils.isEmpty(App.getUserInfo().getMobile())) {
                    showDialog();
                    return;
                }
                submitOrder();
                dismissPopupWindow();
                break;
            case R.id.baidu_map:
            case R.id.gaode_map:
            case R.id.tencent_map:
                dismissPhoneWindow();
                String phone = (String) v.getTag();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
            case R.id.root_view_popup:
            case R.id.cancel:
                dismissPhoneWindow();
                break;
            case R.id.tv_buy_take_away:
                if (groupPurchaseCoupon != null) {
                    Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + groupPurchaseCoupon.getMerchantId());
                }
                break;
            case R.id.tv_evaluate:
                GroupBuyingAllEvaluationActivity.toGroupBuyingAllEvaluationActivity(mActivity, merchant.getId());
                break;
        }
    }

    private void showGroupBuying(List<GroupPurchaseCoupon> tuanList) {
        discountLayout.setVisibility(View.VISIBLE);
        for (int i = 0, size = tuanList.size(); i < size; i++) {
            GroupPurchaseCoupon bean = tuanList.get(i);
            RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mActivity).inflate(R.layout.group_buying_item, null);
            RelativeLayout root = (RelativeLayout) layout.findViewById(R.id.group_buying_item_root);
            CornerImageView icon = (CornerImageView) layout.findViewById(R.id.img);
            TextView tvName = (TextView) layout.findViewById(R.id.tv_name);
            TextView tvPrice = (TextView) layout.findViewById(R.id.tv_price);
            TextView tvPayBill = (TextView) layout.findViewById(R.id.tv_pay_bill1);
            TextView tvOriginPrice = (TextView) layout.findViewById(R.id.tv_origin_price);
            TextView tvOption = (TextView) layout.findViewById(R.id.tv_option);
            root.setTag(bean);
            tvPayBill.setText("购买");
            if (CheckUtils.isNoEmptyStr(bean.getImages())) {
                ImageUtils.loadBitmap(mActivity, bean.getImages().split(";")[0], icon, R.drawable.horsegj_default, Constants.getEndThumbnail(130, 110));
            }
            tvName.setText(bean.getGroupPurchaseName());
            tvPrice.setText("¥" + StringUtils.BigDecimal2Str(bean.getPrice()));
            if (bean.getSumGroupPurchaseCouponGoodsOriginPrice() != null && bean.getSumGroupPurchaseCouponGoodsOriginPrice().compareTo(BigDecimal.ZERO) > 0) {
                tvOriginPrice.setText("门市价¥" + StringUtils.BigDecimal2Str(bean.getSumGroupPurchaseCouponGoodsOriginPrice()));
            }
            tvOption.setText((bean.getIsBespeak() == 0 ? "免预约 | " : "需预约 | ") + "不可叠加");
            root.setOnClickListener(this);
            tvPayBill.setOnClickListener(this);
            discountLayout.addView(layout);
            if (i != size - 1) {
                View view = new View(mActivity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                layoutParams.setMargins(getResources().getDimensionPixelOffset(R.dimen.x15), 0,
                        getResources().getDimensionPixelOffset(R.dimen.x15), 0);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_e5));
                discountLayout.addView(view);
            }
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

    private void initPopupWindow() {
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_buy_quan, null);
        View outside = linearLayout.findViewById(R.id.outside);
        tvAmt = (TextView) linearLayout.findViewById(R.id.tv_amt);
        tvCount = (TextView) linearLayout.findViewById(R.id.tv_count);
        ivMinus = (ImageView) linearLayout.findViewById(R.id.iv_minus);
        ImageView ivAdd = (ImageView) linearLayout.findViewById(R.id.iv_add);
        TextView tvSubmit = (TextView) linearLayout.findViewById(R.id.tv_submit);
        tvAmt.setText("¥" + StringUtils.BigDecimal2Str(groupPurchaseCoupon.getPrice()));
        ivMinus.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        outside.setOnClickListener(this);
        mPopupWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);
    }

    private void showPopupWindow() {
        if (mPopupWindow == null) {
            initPopupWindow();
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void submitOrder() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> params = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        data.put("groupPurchaseCouponId", groupPurchaseCoupon.getId());
        data.put("groupPurchaseCouponType", groupPurchaseCoupon.getType());
        data.put("loginToken", App.getUserInfo().getToken());
        data.put("merchantId", groupPurchaseCoupon.getMerchantId());
        if (groupPurchaseCoupon.getType() == 1) {
            data.put("originalPrice", groupPurchaseCoupon.getOriginPrice());
        } else {
            data.put("originalPrice", groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice());
        }
        data.put("price", groupPurchaseCoupon.getPrice());
        data.put("quantity", count);
        if (groupPurchaseCoupon.getType() == 1) {
            data.put("totalOriginalPrice", groupPurchaseCoupon.getOriginPrice().multiply(BigDecimal.valueOf(count)));
        } else {
            data.put("totalOriginalPrice", groupPurchaseCoupon.getSumGroupPurchaseCouponGoodsOriginPrice().multiply(BigDecimal.valueOf(count)));
        }
        data.put("totalPrice", groupPurchaseCoupon.getPrice().multiply(BigDecimal.valueOf(count)));
        data.put("userId", App.getUserInfo().getId());

        params.put("data", JSON.toJSONString(data));
        VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GROUP_PURCHASE_ORDER_SUBMIT, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    SubmitOrderModel submitOrderModel = (SubmitOrderModel) obj;
                    Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                    intent.putExtra("orderId", submitOrderModel.getValue().getId());
                    intent.putExtra("agentId", submitOrderModel.getValue().getAgentId());
                    intent.putExtra("isGroupPurchase", true);
                    startActivity(intent);
                }
            }
        }, SubmitOrderModel.class);
    }

    private void getGroupPurchaseCoupon() {
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<GroupBuyingCouponModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("groupPurchaseCouponId", couponId);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_COUPON_INFO, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    groupPurchaseCoupon = ((GroupBuyingCouponModel) obj).getValue();
                    if (groupPurchaseCoupon != null) {
                        showData();
                    }
                }
            }
        }, GroupBuyingCouponModel.class);
    }

    private void getgetGroupPurchaseCouponList(int type){
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<GroupPurchaseCouponList> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("start", 0);
        map.put("size", 20);
        map.put("merchantId", groupPurchaseCoupon.getMerchantId());
        map.put("groupPurchaseCouponId", couponId);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_COUPON_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    List<GroupPurchaseCoupon> value = ((GroupPurchaseCouponList) obj).getValue();
                    showGroupBuying(value);
                }
            }
        }, GroupPurchaseCouponList.class);
    }

    public void getEvaluation() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchant.getId());
        params.put("start", 0);
        params.put("size", 3);
        VolleyOperater<GroupBuyingEvaluationListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_EVLUATE_LIST, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    ArrayList<GroupPurchaseEvaluate> mlist = ((GroupBuyingEvaluationListModel) obj).getValue();
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        showEvaluation(mlist);
                    }
                }
            }
        }, GroupBuyingEvaluationListModel.class);
    }

    private void showEvaluation(ArrayList<GroupPurchaseEvaluate> mlist) {
        evaluationLayout.setVisibility(View.VISIBLE);
        GroupBuyingEvaluationAdapter adapter = new GroupBuyingEvaluationAdapter(mActivity);
        evaluationListView.setAdapter(adapter);
        adapter.setData(mlist);
    }

    private void initPhoneWindow() {
        if (CheckUtils.isEmptyStr(merchant.getContacts())) return;
        String[] contacts = merchant.getContacts().split(";");
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_select_map_app, null);
        LinearLayout baidu = (LinearLayout) linearLayout.findViewById(R.id.baidu_map);
        LinearLayout gaode = (LinearLayout) linearLayout.findViewById(R.id.gaode_map);
        LinearLayout tencent = (LinearLayout) linearLayout.findViewById(R.id.tencent_map);
        RelativeLayout root = (RelativeLayout) linearLayout.findViewById(R.id.root_view_popup);
        root.setOnClickListener(this);
        TextView title = (TextView) linearLayout.findViewById(R.id.title);
        TextView textView1 = (TextView) linearLayout.findViewById(R.id.text_1);
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.text_2);
        TextView textView3 = (TextView) linearLayout.findViewById(R.id.text_3);
        title.setText("商家服务热线");
        baidu.setVisibility(View.GONE);
        gaode.setVisibility(View.GONE);
        tencent.setVisibility(View.GONE);
        if (contacts.length > 0) {
            baidu.setVisibility(View.VISIBLE);
            baidu.setTag(contacts[0]);
            textView1.setText(contacts[0]);
        }
        if (contacts.length > 1) {
            gaode.setVisibility(View.VISIBLE);
            gaode.setTag(contacts[1]);
            textView2.setText(contacts[1]);
        }
        if (contacts.length > 2) {
            tencent.setVisibility(View.VISIBLE);
            tencent.setTag(contacts[2]);
            textView3.setText(contacts[2]);
        }
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.cancel);
        baidu.setOnClickListener(this);
        gaode.setOnClickListener(this);
        tencent.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        mPhoneWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPhoneWindow.setOutsideTouchable(false);
    }

    private void showPhoneWindow() {
        if (mPhoneWindow == null) {
            initPhoneWindow();
            mPhoneWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (!mPhoneWindow.isShowing()) {
            mPhoneWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissPhoneWindow() {
        if (mPhoneWindow != null && mPhoneWindow.isShowing()) {
            mPhoneWindow.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (mPhoneWindow != null && mPhoneWindow.isShowing()) {
            mPhoneWindow.dismiss();
            return;
        }
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return;
        }
        if (shareUtil != null && shareUtil.isShowing()) {
            shareUtil.hidePopup();
            return;
        }
        super.onBackPressed();
    }
}
