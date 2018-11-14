package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCoupon;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseEvaluate;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.DeleteOrderModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingEvaluationListModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingMerchantListModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingMerchantModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.pictureviewer.PictureViewActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.MyScrollView;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.CircleIndicator;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2017/3/6.
 */

@Router(value = "groupPurchaseMerchant/:merchantId", longParams = "merchantId")
public class GroupBuyingMerchantDetailActivity extends BaseActivity {

    @InjectView(R.id.scroll_view)
    private MyScrollView scrollView;
    @InjectView(R.id.common_back)
    private ImageView commonBack;
    @InjectView(R.id.common_share)
    private ImageView commonShare;
    @InjectView(R.id.common_collect)
    private ImageView commonCollect;
    @InjectView(R.id.img)
    private ImageView img;
    @InjectView(R.id.tv_merchant_name)
    private TextView tvName;
    @InjectView(R.id.rb_score)
    private RatingBar rbScore;
    @InjectView(R.id.tv_average_price)
    private TextView tvAveragePrice;
    @InjectView(R.id.icon_address)
    private ImageView iconAddr;
    @InjectView(R.id.tv_address)
    private TextView tvAddress;
    @InjectView(R.id.tv_time)
    private TextView tvTime;
    @InjectView(R.id.tv_time1)
    private TextView tvTime1;
    @InjectView(R.id.iv_call)
    private ImageView ivCall;
    @InjectView(R.id.quan_layout)
    private LinearLayout quanLayout;
    @InjectView(R.id.tuan_layout)
    private LinearLayout tuanLayout;
    @InjectView(R.id.recommend_layout)
    private LinearLayout recommendLayout;
    @InjectView(R.id.recommend_dishes)
    private TextView tvDishes;
    @InjectView(R.id.evaluation_layout)
    private LinearLayout evaluationLayout;
    @InjectView(R.id.tv_evaluate)
    private TextView tvEvaluation;
    @InjectView(R.id.evaluation_list)
    private NoScrollListView evaluationListView;
    @InjectView(R.id.service_layout)
    private LinearLayout serviceLayout;
    @InjectView(R.id.grid_view)
    private NoScrollGridView gridView;
    @InjectView(R.id.tv_service)
    private TextView tvService;
    @InjectView(R.id.more_merchant_layout)
    private LinearLayout moreLayout;
    @InjectView(R.id.merchant_list)
    private NoScrollListView merchantListView;
    @InjectView(R.id.img_left)
    private ImageView imgLeft;
    @InjectView(R.id.img_right)
    private ImageView imgRight;
    @InjectView(R.id.layout_qualification)
    private LinearLayout qualificationLayout;
    @InjectView(R.id.rl_distance_pay_bill)
    private RelativeLayout rlDistancePayBill;
    @InjectView(R.id.tv_discount)
    private TextView tvDiscount;
    @InjectView(R.id.tv_discount_pay_bill)
    private TextView tvDiscounPayBill;
    @InjectView(R.id.tuan_list_view)
    private NoScrollListView tuanListView;
    @InjectView(R.id.expand_textview)
    private TextView expandTextView;
    @InjectView(R.id.recycler_view)
    private RecyclerView recyclerView;
    @InjectView(R.id.layout_picture_upload)
    private LinearLayout layoutPictureUpload;

    private List<String> urls = new ArrayList<>();

    private GroupPurchaseMerchant merchant;
    private long merchantId;
    private PopupWindow mPhoneWindow;
    private CustomDialog dialog;
    private MLoadingDialog loadingDialog;
    private ShareUtil shareUtil;
    private GroupBuyMealListAdapter listAdapter;
    private boolean isExpand;
    private GroupBuyingImageRecyclerAdapter groupBuyingImageRecyclerAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_merchant_detail);
        Injector.get(this).inject();
        initView();
        initData();
    }

    private void initView() {
        commonBack.setOnClickListener(this);
        commonCollect.setOnClickListener(this);
        commonShare.setOnClickListener(this);
        iconAddr.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        tvEvaluation.setOnClickListener(this);
        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        tvDiscounPayBill.setOnClickListener(this);
        expandTextView.setOnClickListener(this);
        listAdapter = new GroupBuyMealListAdapter(R.layout.group_buying_item, this);
        tuanListView.setAdapter(listAdapter);

        loadingDialog = new MLoadingDialog();
    }

    private void initData() {
        merchantId = getIntent().getLongExtra("merchantId", -1);
        if (merchantId == -1) {
            finish();
            return;
        }
        getMerchant();
        getMoreMerchant();
    }

    private void showData() {
        scrollView.setVisibility(View.VISIBLE);
        if (merchant.getIsUserFavorites() == 1) {
            commonCollect.setImageResource(R.drawable.gc_collected_icon_new);
        }
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        groupBuyingImageRecyclerAdapter = new GroupBuyingImageRecyclerAdapter(mActivity);
        recyclerView.setAdapter(groupBuyingImageRecyclerAdapter);
        if (CheckUtils.isNoEmptyStr(merchant.getImgs())) {
            final String[] imageUrl = merchant.getImgs().split(";");
            if (imageUrl.length <= 1) {
                img.setVisibility(View.VISIBLE);
                layoutPictureUpload.setVisibility(View.GONE);
                tvTime.setVisibility(View.VISIBLE);
                tvTime1.setVisibility(View.GONE);
                ImageUtils.loadBitmap(mActivity, merchant.getImgs().split(";")[0], img, R.drawable.horsegj_default, Constants.getEndThumbnail(180, 152));
            } else {
                tvTime.setVisibility(View.GONE);
                tvTime1.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                layoutPictureUpload.setVisibility(View.VISIBLE);
                groupBuyingImageRecyclerAdapter.setList(Arrays.asList(imageUrl));
            }
        }
        showMerchantInfo();
        if (CheckUtils.isNoEmptyList(merchant.getGroupPurchaseCouponList())) {
            ArrayList<GroupPurchaseCoupon> quanList = new ArrayList<>();
            ArrayList<GroupPurchaseCoupon> tuanList = new ArrayList<>();
            for (GroupPurchaseCoupon bean : merchant.getGroupPurchaseCouponList()) {
                if (bean.getType() == 1) {
                    quanList.add(bean);
                } else if (bean.getType() == 2) {
                    tuanList.add(bean);
                }
            }
            if (CheckUtils.isNoEmptyList(quanList)) showVoucher(quanList);
            if (CheckUtils.isNoEmptyList(tuanList)) showGroupBuying1(tuanList);
        }
        if (merchant.getMerchantCommentNum() != null && merchant.getMerchantCommentNum() > 0) {
            tvEvaluation.setText(+merchant.getMerchantCommentNum() + "条评价");
            getEvaluation();
        }
    }

    private void showMerchantInfo() {
        tvName.setText(merchant.getName());
        rbScore.setRating(merchant.getAverageScore().floatValue());

        BigDecimal avgPersonPrice;
        if (merchant.getEvaluateCount() >= 10) {
            avgPersonPrice = merchant.getEvaluateAvgPersonPrice();
        } else {
            avgPersonPrice = merchant.getAvgPersonPrice();
        }
        if (avgPersonPrice != null) {
            tvAveragePrice.setText("人均¥" + StringUtils.BigDecimal2Str(avgPersonPrice));
        } else {
            tvAveragePrice.setText("");
        }

        tvAddress.setText(merchant.getAddress());
        tvTime.setText("营业时间：" + merchant.getWorkingTime());
        tvTime1.setText("营业时间：" + merchant.getWorkingTime());
        if (CheckUtils.isNoEmptyStr(merchant.getMerchantRecommend())) {
            recommendLayout.setVisibility(View.VISIBLE);
            tvDishes.setText(merchant.getMerchantRecommend());
        }
        if (CheckUtils.isNoEmptyStr(merchant.getDiscountRatio())) {
            rlDistancePayBill.setVisibility(View.VISIBLE);
            double discount = Integer.parseInt(merchant.getDiscountRatio()) * 0.01 * 10;
            tvDiscount.setText(discount + "折");
        } else {
            rlDistancePayBill.setVisibility(View.GONE);
        }
        showService();
    }

    private void showService() {
        ArrayList<Integer> data = new ArrayList<>();
        if (merchant.getHasWifi() == 1) data.add(0);
        if (merchant.getHasPOSPayment() == 1) data.add(1);
        if (merchant.getHasRooms() == 1) data.add(2);
        if (merchant.getHasDepot() == 1) data.add(3);
        if (merchant.getHasScenerySeat() == 1) data.add(4);
        if (merchant.getHasAlfrescoSeat() == 1) data.add(5);
        if (merchant.getHasNoSmokIngArea() == 1) data.add(6);
        if (data.size() > 0 || CheckUtils.isNoEmptyStr(merchant.getDescription())) {
            serviceLayout.setVisibility(View.VISIBLE);
        }
        if (data.size() > 0 && CheckUtils.isEmptyStr(merchant.getDescription())) {
            GroupBuyingMerchantServiceAdapter adapter = new GroupBuyingMerchantServiceAdapter(mActivity);
            gridView.setAdapter(adapter);
            adapter.setData(data);
            tvService.setVisibility(View.GONE);
        } else if (data.size() == 0 && CheckUtils.isNoEmptyStr(merchant.getDescription())) {
            tvService.setText(merchant.getDescription());
        } else {
            GroupBuyingMerchantServiceAdapter adapter = new GroupBuyingMerchantServiceAdapter(mActivity);
            gridView.setAdapter(adapter);
            adapter.setData(data);
            tvService.setText(merchant.getDescription());
        }
        if (CheckUtils.isEmptyStr(merchant.getBusinessLicenseImg()) && CheckUtils.isEmptyStr(merchant.getHygieneLicenseImg())) {
            qualificationLayout.setVisibility(View.GONE);
        } else {
            qualificationLayout.setVisibility(View.VISIBLE);
        }
        urls.clear();
        if (!CheckUtils.isEmptyStr(merchant.getBusinessLicenseImg())) {
            imgLeft.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, merchant.getBusinessLicenseImg(), imgLeft, R.drawable.horsegj_default, "");
            urls.add(merchant.getBusinessLicenseImg());
        } else {
            imgLeft.setVisibility(View.INVISIBLE);
        }
        if (!CheckUtils.isEmptyStr(merchant.getHygieneLicenseImg())) {
            imgRight.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, merchant.getHygieneLicenseImg(), imgRight, R.drawable.horsegj_default, "");
            urls.add(merchant.getHygieneLicenseImg());
        } else {
            imgRight.setVisibility(View.INVISIBLE);
        }
    }

    private void showEvaluation(ArrayList<GroupPurchaseEvaluate> mlist) {
        evaluationLayout.setVisibility(View.VISIBLE);
        GroupBuyingEvaluationAdapter adapter = new GroupBuyingEvaluationAdapter(mActivity);
        evaluationListView.setAdapter(adapter);
        adapter.setData(mlist);
    }

    private void showMoreMerchant(ArrayList<GroupPurchaseMerchant> mlist) {
        moreLayout.setVisibility(View.VISIBLE);
        GroupBuyingMerchantAdapter adapter = new GroupBuyingMerchantAdapter(mActivity, false);
        merchantListView.setAdapter(adapter);
        adapter.setList(mlist);
    }

    private void showVoucher(ArrayList<GroupPurchaseCoupon> quanList) {
        quanLayout.setVisibility(View.VISIBLE);
        for (int i = 0, size = quanList.size(); i < size; i++) {
            GroupPurchaseCoupon bean = quanList.get(i);
            RelativeLayout layout = (RelativeLayout) LayoutInflater.from(mActivity).inflate(R.layout.voucher_item, null);
            RelativeLayout root = (RelativeLayout) layout.findViewById(R.id.voucher_item_root);
            TextView tvPrice = (TextView) layout.findViewById(R.id.tv_price);
            TextView tvOriginPrice = (TextView) layout.findViewById(R.id.tv_name);
            TextView tvOption = (TextView) layout.findViewById(R.id.tv_option);
            TextView tvPayBill = (TextView) layout.findViewById(R.id.tv_pay_bill);
            TextView tvSold = (TextView) layout.findViewById(R.id.tv_sold);
            root.setTag(bean);
            tvPayBill.setTag(bean);
            tvSold.setText("已售" + bean.getBuyCount());
            tvPrice.setText("¥" + StringUtils.BigDecimal2Str(bean.getPrice()));
            tvOriginPrice.setText(StringUtils.BigDecimal2Str(bean.getOriginPrice()) + "元  代金券");
            tvOption.setText((bean.getIsBespeak() == 0 ? "免预约 | " : "需预约 | ") + (bean.getIsCumulate() == 0 ? "不可叠加" : "可叠加"));
            root.setOnClickListener(this);
            tvPayBill.setOnClickListener(this);
            quanLayout.addView(layout);
            if (i != size - 1) {
                View view = new View(mActivity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                layoutParams.setMargins(getResources().getDimensionPixelOffset(R.dimen.x15), 0,
                        getResources().getDimensionPixelOffset(R.dimen.x15), 0);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_e5));
                quanLayout.addView(view);
            }
        }
    }

    private void showGroupBuying1(final ArrayList<GroupPurchaseCoupon> tuanList) {
        tuanLayout.setVisibility(View.VISIBLE);
        listAdapter.setData(tuanList);
        if (tuanList != null && tuanList.size() > 2) {
            expandTextView.setVisibility(View.VISIBLE);
            isExpand = false;
        } else {
            isExpand = true;
            expandTextView.setVisibility(View.GONE);
        }
        listAdapter.setExpand(isExpand);
        listAdapter.notifyDataSetChanged();
        tuanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseCoupon/" + tuanList.get(i).getId());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                back();
                break;
            case R.id.common_share:
                if (shareUtil == null && merchant != null) {
                    shareUtil = new ShareUtil(mActivity, merchant.getName(),
                            CheckUtils.isNoEmptyStr(merchant.getDescription()) ? merchant.getDescription() : "这家店还不错，马管家可以购买他们家代金券和团购券哦！~",
                            merchant.getShareUrl(), merchant.getImgs());
                }
                if (shareUtil != null) shareUtil.showPopupWindow();
                break;
            case R.id.common_collect:
                if (merchant != null) favorMerchant();
                break;
            case R.id.tv_buy_take_away:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "merchant/" + merchant.getId());
                break;
            case R.id.icon_address:
            case R.id.tv_address:
                GroupBuyingMapActivity.toGroupBuyingMapActivity(mActivity, merchant.getName(), merchant.getAddress(), merchant.getLatitude(), merchant.getLongitude());
                break;
            case R.id.iv_call:
                showPhoneWindow();
                break;
            case R.id.tv_evaluate:
                GroupBuyingAllEvaluationActivity.toGroupBuyingAllEvaluationActivity(mActivity, merchant.getId());
                break;
            case R.id.voucher_item_root:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseCoupon/" + ((GroupPurchaseCoupon) v.getTag()).getId());
                break;
            case R.id.tv_pay_bill:
                if (!App.isLogin()) {
                    Intent intent = new Intent(mActivity, SmsLoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(this, BuyTicketActivity.class);
                intent2.putExtra("ticketName", merchant.getName());
                intent2.putExtra("ticketPrice", StringUtils.BigDecimal2Str(((GroupPurchaseCoupon) v.getTag()).getPrice()));
                intent2.putExtra("ticketOriginalPrice", StringUtils.BigDecimal2Str(((GroupPurchaseCoupon) v.getTag()).getOriginPrice()));
                intent2.putExtra("type", ((GroupPurchaseCoupon) v.getTag()).getType());
                intent2.putExtra("bespeak", ((GroupPurchaseCoupon) v.getTag()).getIsBespeak());
                intent2.putExtra("agentId", ((GroupPurchaseCoupon) v.getTag()).getAgentId());
                intent2.putExtra("groupPurchaseCoupon", ((GroupPurchaseCoupon) v.getTag()));
                startActivity(intent2);
                break;
            case R.id.tv_discount_pay_bill:
                if (!App.isLogin()) {
                    Intent intent = new Intent(mActivity, SmsLoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent1 = new Intent(this, DiscountBuyTicketActivity.class);
                intent1.putExtra("Name", merchant.getName());
                intent1.putExtra("merchant", merchant);
                startActivity(intent1);
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
            case R.id.img_left:
                if (urls.size() == 0) {
                    return;
                }
                PictureViewActivity.toViewPicture(mActivity, JSONArray.toJSONString(urls), 0);
                break;
            case R.id.img_right:
                if (urls.size() == 1) {
                    PictureViewActivity.toViewPicture(mActivity, JSONArray.toJSONString(urls), 0);
                } else if (urls.size() > 1) {
                    PictureViewActivity.toViewPicture(mActivity, JSONArray.toJSONString(urls), 1);
                }
                break;
            case R.id.expand_textview:
                listAdapter.setExpand(true);
                expandTextView.setVisibility(View.GONE);
                listAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void getMerchant() {
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<GroupBuyingMerchantModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("groupPurchaseMerchantId", merchantId);
        if (App.getUserInfo() != null) map.put("userId", App.getUserInfo().getId());
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_MERCHANT_INFO, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    merchant = ((GroupBuyingMerchantModel) obj).getValue();
                    if (merchant != null) {
                        showData();
                    }
                }
            }
        }, GroupBuyingMerchantModel.class);
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

    private void getMoreMerchant() {
        Map<String, Object> map = new HashMap<>();
        map.put("start", 0);
        map.put("size", 3);
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("merchantId", merchantId);
//        排序方式1:智能排序;2:离我最近;3:好评优先
//        map.put("sortType", 1);
//        map.put("groupPurchaseCategoryId", 1);
//        map.put("childGroupPurchaseCategoryId", 2);
//        /** 商户服务多个逗号分隔：1,2,3,4,5,6,7 **/
//        map.put("groupPurchaseMerchantServices", "1,2");
        VolleyOperater<GroupBuyingMerchantListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_NEAR_GROUP_PURCHASE_MERCHANT, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    GroupBuyingMerchantListModel model = (GroupBuyingMerchantListModel) obj;
                    ArrayList<GroupPurchaseMerchant> mlist = model.getValue();
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        for (int i = mlist.size() - 1; i >= 0; i--) {
                            if (mlist.get(i).getId().equals(merchant.getId())) {
                                mlist.remove(i);
                                break;
                            }
                        }
                        if (mlist.size() == 4) {
                            mlist.remove(3);
                        }
                        if (mlist.size() > 0) showMoreMerchant(mlist);
                    }
                }
            }
        }, GroupBuyingMerchantListModel.class);
    }

    private void favorMerchant() {
        if (merchant.getIsUserFavorites() != 1) {
            if (App.isLogin()) {
                sendFavorMerchantRequest();
            } else {
                showMyDialog();
            }
        } else {
            sendCancelFavorMerchantRequest();
        }
    }

    private void showMyDialog() {
        if (dialog == null) {
            dialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    Intent intent = new Intent(mActivity, SmsLoginActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }

                @Override
                public void onExit() {
                    dialog.dismiss();
                }
            }, getString(R.string.favor_login), getString(R.string.favor_cancel),
                    getString(R.string.favor_title), getString(R.string.favor_msg));
            dialog.show();
        } else {
            dialog.show();
        }
    }

    private void sendFavorMerchantRequest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("merchantType", 2);
        VolleyOperater<DeleteOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREAT_USER_FAVORITES, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null && ((DeleteOrderModel) obj).getCode() == 0) {
                    ToastUtils.showMyToast(mActivity, "收藏成功", R.drawable.collected);
                    merchant.setIsUserFavorites(1);
                    commonCollect.setImageResource(R.drawable.gc_collected_icon_new);
                }
            }
        }, DeleteOrderModel.class);
    }

    private void sendCancelFavorMerchantRequest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("merchantType", 2);
        VolleyOperater<DeleteOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CANCEL_USER_FAVORITES, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null && ((DeleteOrderModel) obj).getCode() == 0) {
                    ToastUtils.showMyToast(mActivity, "已取消收藏", R.drawable.uncollect);
                    merchant.setIsUserFavorites(0);
                    commonCollect.setImageResource(R.drawable.iv_collect_black);
                }
            }
        }, DeleteOrderModel.class);
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
        if (shareUtil != null && shareUtil.isShowing()) {
            shareUtil.hidePopup();
            return;
        }
        super.onBackPressed();
    }
}
