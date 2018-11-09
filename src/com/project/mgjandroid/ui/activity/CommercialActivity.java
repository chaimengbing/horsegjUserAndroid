package com.project.mgjandroid.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.CouDanModel;
import com.project.mgjandroid.bean.FullSub;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.Menu;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantPickGoods;
import com.project.mgjandroid.bean.MerchantTakeAwayMenu;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.bean.PromotionActivity;
import com.project.mgjandroid.bean.SharingRelationship;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.DeleteOrderModel;
import com.project.mgjandroid.model.GoodsListModel;
import com.project.mgjandroid.model.MerchantEvaluateTopModel;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.BottomCartListAdapter;
import com.project.mgjandroid.ui.adapter.CouDanListAdapter;
import com.project.mgjandroid.ui.fragment.EvaluateFragment;
import com.project.mgjandroid.ui.fragment.GoodsFragment;
import com.project.mgjandroid.ui.fragment.MerchantsFragment;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;
import com.project.mgjandroid.ui.view.HeaderViewPagerLayout;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.ui.view.NoticeView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.MyToast;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ShareUtil;
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
 * 商家
 *
 * @author jian
 */
@Router(value = "merchant/:merchantId", intParams = "merchantId")
public class CommercialActivity extends BaseActivity implements OnClickListener, OnPageChangeListener, BottomCartListener {

    private static final int INDEX_GOODS = 0;
    private static final int INDEX_EVALUATE = 1;
    private static final int INDEX_MERCHANTS = 2;
    //    public static final String MERCHANT = "merchant";
    public static final String MERCHANT_ID = "merchantId";
    @InjectView(R.id.commercial_all)
    private RelativeLayout allRoot;
    @InjectView(R.id.commercial_act_iv_back)
    private ImageView imgBack;
    @InjectView(R.id.commercial_act_iv_search)
    private ImageView imgSearch;
    @InjectView(R.id.commercial_act_iv_share)
    private ImageView imgShare;
    @InjectView(R.id.commercial_act_iv_favor)
    private ImageView imgFavor;
    @InjectView(R.id.commercial_act_iv_pin)
    private ImageView imgPin;
    @InjectView(R.id.commercial_act_tv_title)
    private TextView tvTitle;
    @InjectView(R.id.commercial_act_tab_goods)
    private TextView tvGoods;
    @InjectView(R.id.commercial_act_tab_evaluate)
    private TextView tvEvaluate;
    @InjectView(R.id.commercial_act_tab_merchants)
    private TextView tvMerchants;
    @InjectView(R.id.commercial_act_tab_blue_line)
    private LinearLayout indicatorView;
    @InjectView(R.id.commercial_act_pager)
    private ViewPager pager;
    @InjectView(R.id.commercial_act_bottom)
    private RelativeLayout bottomLayout;
    @InjectView(R.id.commercial_act_bottom_car)
    private FrameLayout bottomCart;
    @InjectView(R.id.overlay)
    private View overlay;
    @InjectView(R.id.commercial_act_bottom)
    private RelativeLayout bottomCartReplace;
    @InjectView(R.id.commercial_act_bottom_money)
    private TextView tv_allMoney;
    @InjectView(R.id.cart_num)
    private TextView tv_num;
    @InjectView(R.id.commercial_act_cart)
    private ImageView img_cart;
    @InjectView(R.id.commercial_act_bottom_shipping_and_box)
    private RelativeLayout rlCartShipingAndBox;
    @InjectView(R.id.commercial_act_bottom_shipping)
    private TextView tv_cart_shipping;
    @InjectView(R.id.commercial_act_bottom_package)
    private TextView tv_cart_package;
    @InjectView(R.id.commercial_act_bottom_qisong)
    private TextView tv_cart_qisong;
    @InjectView(R.id.linear_cover)
    private LinearLayout linearCover;
    @InjectView(R.id.commercial_act_go_account)
    private TextView tv_goAccount;
    @InjectView(R.id.shop_message)
    private RelativeLayout shopMessage;
    @InjectView(R.id.commercial_act_title_bar)
    private RelativeLayout topBar;
    @InjectView(R.id.shop_icon)
    private RoundImageView imgShopIcon;
    @InjectView(R.id.shop_name)
    private TextView tvShopName;
    @InjectView(R.id.shop_desc)
    private TextView tvShopDesc;
    @InjectView(R.id.tv_promotion_count)
    private TextView tvPromotion;
    @InjectView(R.id.shop_adv_container)
    private LinearLayout linearAdvContainer;
    @InjectView(R.id.commercial_broadcast)
    private LinearLayout linearBroadcast;
    @InjectView(R.id.tv_broadcast)
    private TextView tvBroadcast;
    @InjectView(R.id.image_blur)
    private ImageView imageBlur;
    @InjectView(R.id.view_blur)
    private View vBlur;
    @InjectView(R.id.broadcast_icon)
    private ImageView ivBroad;
    @InjectView(R.id.notice_view)
    private NoticeView nvPromotion;
    @InjectView(R.id.tv_full_subtract)
    private TextView tvFullSubtract;
    @InjectView(R.id.ll_layout_full_subtract)
    private LinearLayout llFullSubtract;
    @InjectView(R.id.tv_price_spread)
    private TextView tvPriceSpread;
    @InjectView(R.id.tv_diminishbb_price)
    private TextView tvDimPrice;
    @InjectView(R.id.tv_add_on_items)
    private TextView tvAddOnItems;
    @InjectView(R.id.tv_has_been_reduced)
    private TextView tvHas;
    @InjectView(R.id.tv_text1)
    private TextView tvText1;


    private ArrayList<HeaderViewPagerFragment> fragments;
    private GoodsFragment goodsFragment;
    private EvaluateFragment evaluateFragment;
    private MerchantsFragment merchantsFragment;
    private int currentIndex;

    private Merchant merchant;
    private int merchantId;
    private MerchantTakeAwayMenu merchantTakeAwayMenu;
    private MerchantEvaluateTopModel.ValueEntity.ShareInfoEntity shareInfo;
    private boolean favorite = false;
    private NoticeDialog noticeDialog;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    private PopupWindow mPopWindow;
    private CustomDialog dialog;
    private List<PickGoods> mCartProducts;
    private BottomCartListAdapter mAdapter;
    private ViewGroup anim_mask_layout;
    private ListView mListView;
    private RelativeLayout relativeCenter;
    private HeaderViewPagerLayout scrollableLayout;
    private PopupWindow mBroadcast;
    private List<PickGoods> pickGoods;
    private JSONObject previewJsonData;
    private String errorMsg;
    private ConfirmOrderModel confirmOrderModel;
    private LoadingDialog mLoadingDialog;
    private MLoadingDialog loadingDialog;
    private boolean isAgainOrder = false;
    private JSONObject object;
    private int goodsId = -1;//用于搜索设置跳转
    private ShareUtil shareUtil;
    private Goods goods;
    private BigDecimal multiply;
    private BigDecimal decimal;
    private PopupWindow couDanPopupWindow;
    private ListView cListView;
    private CouDanListAdapter couDanListAdapter;
    private List<Goods> couDanModelValue;
    private boolean isShare;
    private boolean hasDis;
    private TextView pTvHas;
    private TextView pTvText1;
    private TextView pTvPriceSpread;
    private TextView pTvdimin;
    private boolean visible = false;
    private BigDecimal subtract;
    private boolean isFullSub;
    private String str;
    private LinearLayout pLayoutFullSub;
    private boolean canDisplay = true;
    private TextView bTvHas;
    private TextView bTvText1;
    private TextView bTvPriceSpread;
    private TextView bTvdimin;
    private TextView bTvAdd;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.commercial_act);
        Injector.get(this).inject();
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        allRoot.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("merchantId")) {
            merchantId = intent.getIntExtra("merchantId", -1);
        }
        if (intent != null && intent.hasExtra("againOrder")) {
            isAgainOrder = intent.getBooleanExtra("againOrder", false);
        }
        if (intent != null && intent.hasExtra("goodsId")) {
            goodsId = intent.getIntExtra("goodsId", -1);
            goods = (Goods) getIntent().getExtras().getSerializable("goods");
        }
        if (isAgainOrder) {
            object = new JSONObject((Map<String, Object>) intent.getSerializableExtra("onceMoreOrder"));
        }
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.show();
        loadingDialog = new MLoadingDialog();
        if (merchantId != -1) {
            getMerchantTopEvaluate(merchantId);
        }
        ivBroad.setImageResource(R.drawable.broadcast_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivBroad.getDrawable();
        animationDrawable.start();
    }

    private void initThreeFragments(Merchant merchant) {
        fragments = new ArrayList<>();
        addFragments(merchant);
        MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);
        pager.setOffscreenPageLimit(2);
        setListener();
        initTabLineWidth();
        tvGoods.setTextColor(getResources().getColor(R.color.color_3));
        TextPaint paint = tvGoods.getPaint();
        paint.setFakeBoldText(true);
        tvEvaluate.setTextColor(getResources().getColor(R.color.gray_txt));
        tvMerchants.setTextColor(getResources().getColor(R.color.gray_txt));
        getData();
        tvTitle.setVisibility(View.GONE);
        scrollableLayout = (HeaderViewPagerLayout) findViewById(R.id.scrollableLayout);
        scrollableLayout.setCurrentScrollableContainer(fragments.get(0));
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                scrollableLayout.setCurrentScrollableContainer(fragments.get(position));
            }
        });
        scrollableLayout.setOnScrollListener(new HeaderViewPagerLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                shopMessage.setTranslationY(currentY / 2);
                shopMessage.setAlpha((maxY - currentY) * 1.0f / maxY);
                if (tvTitle.getVisibility() == View.GONE) {
                    tvTitle.setVisibility(View.VISIBLE);
                }
                tvTitle.setAlpha(currentY * 1.0f / maxY);

                if (currentY == maxY) {
//                    topBar.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                } else {
                    topBar.setBackgroundColor(Color.parseColor("#001c2b51"));
                }
            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private void initShop(Merchant merchant) {
        imgShopIcon.setImageResource(R.drawable.horsegj_default);
        ImageUtils.loadBitmap(this, merchant.getLogo(), imgShopIcon, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        if (CheckUtils.isNoEmptyStr(merchant.getLogo())) {
            ImageUtils.getBlur(this, merchant.getLogo(), imageBlur, "?imageView2/2/h/30/interlace/1", vBlur);
        } else {
            Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.default_blur);
            ImageUtils.blur(this, bm.copy(bm.getConfig(), true), imageBlur, vBlur, false);
        }
        tvShopName.setText(merchant.getName());
        StringBuilder sb = new StringBuilder();
        BigDecimal minPrice = merchant.getMinPrice();
        BigDecimal shipFee = merchant.getShipFee();
        Integer avgDeliveryTime = merchant.getAvgDeliveryTime();
        if (avgDeliveryTime != null && avgDeliveryTime > 0) {
            sb.append(" | ").append(avgDeliveryTime).append("分钟送达");
        }
        SpannableStringBuilder infoValue = new SpannableStringBuilder();
        String content = "起送价 ¥" + StringUtils.BigDecimal2Str(minPrice) + " | 配送费 ¥";
        infoValue.append(content);
        if (merchant.getMerchantAssumeAmt().compareTo(BigDecimal.ZERO) == 0) {
            infoValue.append(StringUtils.BigDecimal2Str(shipFee));
            infoValue.append(sb.toString());
        } else {
            infoValue.append(StringUtils.BigDecimal2Str(shipFee.subtract(merchant.getMerchantAssumeAmt())) + " ");
            String price = "¥" + StringUtils.BigDecimal2Str(merchant.getShipFee());
            infoValue.append(price);
            infoValue.append(sb.toString());
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#999999"));
            infoValue.setSpan(strikethroughSpan, infoValue.toString().indexOf(price), infoValue.toString().indexOf(price) + price.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            infoValue.setSpan(colorSpan, infoValue.toString().indexOf(price), infoValue.toString().indexOf(price) + price.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        tvShopDesc.setText(infoValue);

        String broadcast = merchant.getBroadcast();
        if (broadcast != null && !"".equals(broadcast)) {
            tvBroadcast.setText(broadcast);
        } else {
            tvBroadcast.setText("商家暂无公告");
        }

        if (merchant.getHasVisualRestaurant() == 1) {
            Drawable drawable = getResources().getDrawable(R.drawable.visible_live_icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvMerchants.setCompoundDrawables(null, null, drawable, null);
            tvMerchants.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen.x5));
        } else {
            tvMerchants.setCompoundDrawables(null, null, null, null);
        }
    }

    /**
     * 查询商家详情
     *
     * @param merchantId
     */
    private void getMerchantTopEvaluate(int merchantId) {
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        if (PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", 0);
            map.put("longitude", 0);
        }
        VolleyOperater<MerchantEvaluateTopModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_SHOW_EVALUATE, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

                if (isSucceed && obj != null) {
                    if (obj instanceof MerchantEvaluateTopModel) {
                        allRoot.setVisibility(View.VISIBLE);
                        MerchantEvaluateTopModel merchantEvaluateTopModel = (MerchantEvaluateTopModel) obj;
                        merchant = merchantEvaluateTopModel.getValue().getMerchant();
                        //顶部数据
                        initShop(merchant);
                        //底部数据
                        init(merchant);
                        initThreeFragments(merchant);

                        shareInfo = merchantEvaluateTopModel.getValue().getShareInfo();
                        favorite = merchantEvaluateTopModel.getValue().isFavorite();
                        if (favorite) imgFavor.setImageResource(R.drawable.favored);
                        if (evaluateFragment != null) {
                            evaluateFragment.setData(merchant);
                        }
                        List<PromotionActivity> promotions = merchant.getPromotionActivityList();
                        if (promotions != null && promotions.size() > 0) {
//                            addPromotion(linearAdvContainer, promotions.get(0), false);
                            if (promotions.size() > 1) {
                                linearAdvContainer.setVisibility(View.GONE);
                                nvPromotion.setVisibility(View.VISIBLE);
                                nvPromotion.setBroadList(promotions, mActivity, false);
                                nvPromotion.start();
                            } else {
                                linearAdvContainer.setVisibility(View.VISIBLE);
                                nvPromotion.setVisibility(View.GONE);
                                addPromotion(linearAdvContainer, promotions.get(0), false);
                            }
                            if (promotions.size() > 1) {
                                tvPromotion.setVisibility(View.VISIBLE);
                                tvPromotion.setText(promotions.size() + "个活动");
                                tvPromotion.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelOffset(R.dimen.x11));
                                tvPromotion.setOnClickListener(CommercialActivity.this);
                            }
                        }
                    } else {
                        if (obj instanceof String) {
                            toast(obj.toString());
                        }
                        finish();
                    }
                }
            }
        }, MerchantEvaluateTopModel.class);
    }

    private void addPromotion(LinearLayout layout, PromotionActivity promotion, boolean topMargin) {
        LinearLayout childLayout = new LinearLayout(this);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        if (CheckUtils.isNoEmptyStr(promotion.getPromoImg())) {
            ImageView image = new ImageView(this);
            ImageUtils.loadBitmap(this, promotion.getPromoImg(), image, R.drawable.jian, "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x14), getResources().getDimensionPixelOffset(R.dimen.x14));
//            params.topMargin = getResources().getDimensionPixelOffset(R.dimen.x2);
            childLayout.addView(image, params);
        }
        if (CheckUtils.isNoEmptyStr(promotion.getPromoName())) {
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x5);
            if (!topMargin) {
                tv.setSingleLine();
                tv.setEllipsize(TextUtils.TruncateAt.END);
            }
            tv.setTextColor(this.getResources().getColor(R.color.white));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelOffset(R.dimen.x11));
            tv.setText(promotion.getPromoName());
            childLayout.addView(tv, params);
        }
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (topMargin) paramsChild.topMargin = getResources().getDimensionPixelOffset(R.dimen.x15);
        layout.addView(childLayout, paramsChild);
    }

    private void init(Merchant merchant) {
        tvTitle.setText(merchant.getName());
        checkFullReduction(merchant);
        if (merchant.getShipFee().compareTo(BigDecimal.ZERO) == 1) {
            tv_cart_shipping.setText("另需配送费¥" + StringUtils.BigDecimal2Str(merchant.getShipFee()));
            tv_cart_package.setTextSize(10);
        } else {
            tv_cart_shipping.setVisibility(View.GONE);
            rlCartShipingAndBox.setVisibility(View.GONE);
            tv_cart_package.setTextSize(14);
        }
        tv_cart_qisong.setText("¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice()) + "起送");
        initPopWindow();
    }

    //判断是否满减
    private void checkFullReduction(Merchant merchant) {
        isShare = false;
        hasDis = false;
        boolean isToast = false;
        if (mCartProducts != null && mCartProducts.size() > 0) {
            for (SharingRelationship sList : merchant.getActivitySharedRelationList()) {
                if (sList.getPromotionActivityType() == 2 && sList.getRelationPromotionActivityType() == 5 && sList.getStatus() == 1) {
                    isShare = true;
                    break;
                } else if (sList.getPromotionActivityType() == 5 && sList.getRelationPromotionActivityType() == 2 && sList.getStatus() == 1) {
                    isShare = true;
                    break;
                }
            }

            if (isShare) {
                BigDecimal num = BigDecimal.ZERO;
                for (PickGoods pro : mCartProducts) {
                    for (GoodsSpec goodsSpec : pro.getGoods().getGoodsSpecList()) {
                        if (goodsSpec.getId() == pro.getGoodsSpecId()) {
                            if (pro.getGoods().getHasDiscount() == 1) {
                                int everyGoodsEveryOrderBuyCount = pro.getGoods().getEveryGoodsEveryOrderBuyCount();
                                int surplusDiscountStock = pro.getGoods().getSurplusDiscountStock();
                                if (everyGoodsEveryOrderBuyCount >= surplusDiscountStock) {
                                    if (pro.getPickCount() >= surplusDiscountStock) {
                                        multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getGoods().getSurplusDiscountStock()));
                                        decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pro.getPickCount() - surplusDiscountStock));
                                        num = num.add(multiply.add(decimal));
                                    } else {
                                        multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getPickCount()));
                                        num = num.add(multiply);
                                    }
                                } else {
                                    if (pro.getPickCount() >= everyGoodsEveryOrderBuyCount) {
                                        if (everyGoodsEveryOrderBuyCount > 0) {
                                            multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getGoods().getEveryGoodsEveryOrderBuyCount()));
                                            decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pro.getPickCount() - everyGoodsEveryOrderBuyCount));
                                            num = num.add(multiply.add(decimal));
                                        } else {
                                            multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getPickCount()));
                                            num = num.add(multiply);
                                        }
                                    } else {
                                        multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getPickCount()));
                                        num = num.add(multiply);
                                    }
                                }
                            } else {
                                num = num.add(goodsSpec.getPrice().multiply(BigDecimal.valueOf((long) pro.getPickCount())));
                            }
                        }
                    }
                }
                if (isFullSub) {
                    if (visible || couDanPopupWindow != null && couDanPopupWindow.isShowing()) {
                        tvFullSubtract.setVisibility(View.GONE);
                        llFullSubtract.setVisibility(View.GONE);
                        visible = false;
                    } else {
                        tvFullSubtract.setVisibility(View.GONE);
                        llFullSubtract.setVisibility(View.VISIBLE);
                    }
                }


                FullSub max = new FullSub();//已满足的最大红包
                FullSub min = new FullSub();//未满足的最小红包
                for (PromotionActivity promotion : merchant.getPromotionActivityList()) {
                    for (FullSub fs : promotion.getRuleDtoList()) {
                        if (num.compareTo(fs.getFull()) >= 0) {
                            max = fs;
                        } else if (min.getFull() == null && min.getSub() == null) {
                            min = fs;
                        }
                    }
                }
                if (max.getFull() != null && max.getSub() != null) {
                    BigDecimal full = max.getFull();
                    BigDecimal sub = max.getSub();
                    if (min.getFull() != null && min.getSub() != null) {
                        tvHas.setVisibility(View.VISIBLE);
                        tvHas.setText("下单减" + StringUtils.BigDecimal2Str(sub) + "元，");
                        if (couDanPopupWindow != null) {
                            pTvHas.setVisibility(View.VISIBLE);
                            pTvHas.setText("下单减" + StringUtils.BigDecimal2Str(sub) + "元，");
                        }
                        if (mPopWindow != null) {
                            bTvHas.setVisibility(View.VISIBLE);
                            bTvHas.setText("下单减" + StringUtils.BigDecimal2Str(sub) + "元，");
                        }

                    } else {
                        tvHas.setVisibility(View.GONE);
                        tvText1.setText("已满");
                        tvPriceSpread.setText(StringUtils.BigDecimal2Str(full) + "元");
                        tvDimPrice.setText(StringUtils.BigDecimal2Str(sub) + "元");
                        tvAddOnItems.setVisibility(View.GONE);
                        llFullSubtract.setClickable(false);
                        if (couDanPopupWindow != null) {
                            pTvHas.setVisibility(View.GONE);
                            pTvText1.setText("已满");
                            pTvPriceSpread.setText(StringUtils.BigDecimal2Str(full) + "元");
                            pTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                            tvAddOnItems.setVisibility(View.GONE);
                            llFullSubtract.setClickable(false);
                        }
                        if (mPopWindow != null) {
                            pLayoutFullSub.setClickable(false);
                            bTvHas.setVisibility(View.GONE);
                            bTvText1.setText("已满");
                            bTvPriceSpread.setText(StringUtils.BigDecimal2Str(full) + "元");
                            bTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                            bTvAdd.setVisibility(View.GONE);
                        }
                    }
                }
                if (min.getFull() != null && min.getSub() != null) {
                    BigDecimal full = min.getFull();
                    BigDecimal sub = min.getSub();
                    subtract = full.subtract(num);
                    if (max.getFull() == null && max.getSub() == null) {
                        tvHas.setVisibility(View.GONE);
                        if (couDanPopupWindow != null) {
                            pTvHas.setVisibility(View.GONE);
                        }
                        if (mPopWindow != null) {
                            bTvHas.setVisibility(View.GONE);
                        }
                    }
                    tvText1.setText("再买");
                    tvPriceSpread.setText(StringUtils.BigDecimal2Str(subtract) + "元");
                    tvDimPrice.setText(StringUtils.BigDecimal2Str(sub) + "元");
                    if (couDanPopupWindow != null) {
                        pTvText1.setText("再买");
                        pTvPriceSpread.setText(StringUtils.BigDecimal2Str(subtract) + "元");
                        pTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                    }
                    if (mPopWindow != null) {
                        bTvText1.setText("再买");
                        bTvPriceSpread.setText(StringUtils.BigDecimal2Str(subtract) + "元");
                        bTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                    }
                    BigDecimal multiply = full.multiply(new BigDecimal(0.8));
                    if (num.compareTo(multiply) >= 0) {
                        tvAddOnItems.setVisibility(View.VISIBLE);
                        llFullSubtract.setClickable(true);
                        if (mPopWindow != null) {
                            bTvAdd.setVisibility(View.VISIBLE);
                            pLayoutFullSub.setClickable(true);
                        }
                    } else {
                        tvAddOnItems.setVisibility(View.GONE);
                        llFullSubtract.setClickable(false);
                        if (mPopWindow != null) {
                            bTvAdd.setVisibility(View.GONE);
                            pLayoutFullSub.setClickable(false);
                        }
                    }
                }
            } else {
                for (PickGoods pro : mCartProducts) {
                    for (GoodsSpec goodsSpec : pro.getGoods().getGoodsSpecList()) {
                        if (goodsSpec.getId() == pro.getGoodsSpecId()) {
                            if (pro.getGoods().getHasDiscount() == 1) {
                                hasDis = true;
                            }
                        }
                    }
                }

                if (hasDis) {
                    tvFullSubtract.setVisibility(View.GONE);
                    llFullSubtract.setVisibility(View.GONE);
                    canDisplay = false;
                } else {
                    BigDecimal num = BigDecimal.ZERO;
                    for (PickGoods pro : mCartProducts) {
                        for (GoodsSpec goodsSpec : pro.getGoods().getGoodsSpecList()) {
                            if (goodsSpec.getId() == pro.getGoodsSpecId()) {
                                if (pro.getGoods().getHasDiscount() == 1) {
                                    int everyGoodsEveryOrderBuyCount = pro.getGoods().getEveryGoodsEveryOrderBuyCount();
                                    int surplusDiscountStock = pro.getGoods().getSurplusDiscountStock();
                                    if (everyGoodsEveryOrderBuyCount >= surplusDiscountStock) {
                                        if (pro.getPickCount() >= surplusDiscountStock) {
                                            multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getGoods().getSurplusDiscountStock()));
                                            decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pro.getPickCount() - surplusDiscountStock));
                                            num = num.add(multiply.add(decimal));
                                        } else {
                                            multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getPickCount()));
                                            num = num.add(multiply);
                                        }
                                    } else {
                                        if (pro.getPickCount() >= everyGoodsEveryOrderBuyCount) {
                                            if (everyGoodsEveryOrderBuyCount > 0) {
                                                multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getGoods().getEveryGoodsEveryOrderBuyCount()));
                                                decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pro.getPickCount() - everyGoodsEveryOrderBuyCount));
                                                num = num.add(multiply.add(decimal));
                                            } else {
                                                multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getPickCount()));
                                                num = num.add(multiply);
                                            }
                                        } else {
                                            multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getPickCount()));
                                            num = num.add(multiply);
                                        }
                                    }
                                } else {
                                    num = num.add(goodsSpec.getPrice().multiply(BigDecimal.valueOf((long) pro.getPickCount())));
                                }
                            }
                        }
                    }
                    if (isFullSub) {
                        if (visible || couDanPopupWindow != null && couDanPopupWindow.isShowing()) {
                            tvFullSubtract.setVisibility(View.GONE);
                            llFullSubtract.setVisibility(View.GONE);
                            visible = false;
                        } else {
                            tvFullSubtract.setVisibility(View.GONE);
                            llFullSubtract.setVisibility(View.VISIBLE);
                        }
                    }
                    FullSub max = new FullSub();//已满足的最大红包
                    FullSub min = new FullSub();//未满足的最小红包
                    for (PromotionActivity promotion : merchant.getPromotionActivityList()) {
                        for (FullSub fs : promotion.getRuleDtoList()) {
                            if (num.compareTo(fs.getFull()) >= 0) {
                                max = fs;
                            } else if (min.getFull() == null && min.getSub() == null) {
                                min = fs;
                            }
                        }
                    }
                    if (max.getFull() != null && max.getSub() != null) {
                        BigDecimal full = max.getFull();
                        BigDecimal sub = max.getSub();
                        if (min.getFull() != null && min.getSub() != null) {
                            tvHas.setVisibility(View.VISIBLE);
                            tvHas.setText("下单减" + StringUtils.BigDecimal2Str(sub) + "元，");
                            if (couDanPopupWindow != null) {
                                pTvHas.setVisibility(View.VISIBLE);
                                pTvHas.setText("下单减" + StringUtils.BigDecimal2Str(sub) + "元，");
                            }
                            if (mPopWindow != null) {
                                bTvHas.setVisibility(View.VISIBLE);
                                bTvHas.setText("下单减" + StringUtils.BigDecimal2Str(sub) + "元，");
                            }

                        } else {
                            tvHas.setVisibility(View.GONE);
                            tvText1.setText("已满");
                            tvPriceSpread.setText(StringUtils.BigDecimal2Str(full) + "元");
                            tvDimPrice.setText(StringUtils.BigDecimal2Str(sub) + "元");
                            tvAddOnItems.setVisibility(View.GONE);
                            llFullSubtract.setClickable(false);
                            if (couDanPopupWindow != null) {
                                pTvHas.setVisibility(View.GONE);
                                pTvText1.setText("已满");
                                pTvPriceSpread.setText(StringUtils.BigDecimal2Str(full) + "元");
                                pTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                                tvAddOnItems.setVisibility(View.GONE);
                                llFullSubtract.setClickable(false);
                            }
                            if (mPopWindow != null) {
                                pLayoutFullSub.setClickable(false);
                                bTvHas.setVisibility(View.GONE);
                                bTvText1.setText("已满");
                                bTvPriceSpread.setText(StringUtils.BigDecimal2Str(full) + "元");
                                bTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                                bTvAdd.setVisibility(View.GONE);
                            }
                        }
                    }
                    if (min.getFull() != null && min.getSub() != null) {
                        BigDecimal full = min.getFull();
                        BigDecimal sub = min.getSub();
                        subtract = full.subtract(num);
                        if (max.getFull() == null && max.getSub() == null) {
                            tvHas.setVisibility(View.GONE);
                            if (couDanPopupWindow != null) {
                                pTvHas.setVisibility(View.GONE);
                            }
                            if (mPopWindow != null) {
                                bTvHas.setVisibility(View.GONE);
                            }
                        }
                        tvText1.setText("再买");
                        tvPriceSpread.setText(StringUtils.BigDecimal2Str(subtract) + "元");
                        tvDimPrice.setText(StringUtils.BigDecimal2Str(sub) + "元");
                        if (couDanPopupWindow != null) {
                            pTvText1.setText("再买");
                            pTvPriceSpread.setText(StringUtils.BigDecimal2Str(subtract) + "元");
                            pTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                        }
                        if (mPopWindow != null) {
                            bTvText1.setText("再买");
                            bTvPriceSpread.setText(StringUtils.BigDecimal2Str(subtract) + "元");
                            bTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                        }
                        BigDecimal multiply = full.multiply(new BigDecimal(0.8));
                        if (num.compareTo(multiply) >= 0) {
                            tvAddOnItems.setVisibility(View.VISIBLE);
                            llFullSubtract.setClickable(true);
                            if (mPopWindow != null) {
                                bTvAdd.setVisibility(View.VISIBLE);
                                pLayoutFullSub.setClickable(true);
                            }
                        } else {
                            tvAddOnItems.setVisibility(View.GONE);
                            llFullSubtract.setClickable(false);
                            if (mPopWindow != null) {
                                bTvAdd.setVisibility(View.GONE);
                                pLayoutFullSub.setClickable(false);
                            }
                        }
                    }
                }
            }

        } else {
            if (couDanPopupWindow != null && couDanPopupWindow.isShowing()) {
                couDanPopupWindow.dismiss();
                linearCover.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.INVISIBLE);
            }
            if (!CheckUtils.isEmptyList(merchant.getPromotionActivityList())) {
                for (int i = 0; i < merchant.getPromotionActivityList().size(); i++) {
                    if (merchant.getPromotionActivityList().get(i).getRuleDtoList() != null && merchant.getPromotionActivityList().get(i).getRuleDtoList().size() > 0) {
                        String promoName = merchant.getPromotionActivityList().get(i).getPromoName();
                        if (promoName.startsWith("在线支付")) {
                            str = promoName.substring(4);
                        } else {
                            str = promoName;
                        }
                        tvFullSubtract.setText(str);
                        tvFullSubtract.setVisibility(View.VISIBLE);
                        overlay.setVisibility(View.GONE);
                        llFullSubtract.setVisibility(View.GONE);
                        isFullSub = true;

                    }
                }
            }
        }
        if (goodsFragment != null) {
            goodsFragment.calculateBottom(llFullSubtract, tvFullSubtract);
        }
    }

    public void popUp() {
        boolean hasDis1 = false;
        if (mCartProducts != null && mCartProducts.size() > 0) {
            for (SharingRelationship sList : merchant.getActivitySharedRelationList()) {
                if (sList.getPromotionActivityType() == 2 && sList.getRelationPromotionActivityType() == 5 && sList.getStatus() == 1) {
                    isShare = true;
                    break;
                } else if (sList.getPromotionActivityType() == 5 && sList.getRelationPromotionActivityType() == 2 && sList.getStatus() == 1) {
                    isShare = true;
                    break;
                }
            }
            if (!isShare) {
                for (PickGoods pro : mCartProducts) {
                    for (GoodsSpec goodsSpec : pro.getGoods().getGoodsSpecList()) {
                        if (goodsSpec.getId() == pro.getGoodsSpecId()) {
                            if (pro.getGoods().getHasDiscount() == 1) {
                                hasDis1 = true;
                            }
                        }
                    }
                }
                if (!hasDis1 && isFullSub) {
                    MyToast.displayMsg("满减活动与折扣商品不同享", mActivity);
                }
            }
        }
    }

    private void getCouDanData() {
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("price", StringUtils.BigDecimal2Str(subtract));
        VolleyOperater<CouDanModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_TGOODS_BY_PRICE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    CouDanModel couDanModel = (CouDanModel) obj;
                    couDanModelValue = couDanModel.getValue();
                    initCouDanPopWindow();
                }
            }
        }, CouDanModel.class);
    }

    /**
     * 初始化去凑单弹框
     */
    private void initCouDanPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.coudan_item, null);
        couDanPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        couDanPopupWindow.setContentView(view);
        couDanPopupWindow.setHeight(DipToPx.dip2px(mActivity, 250));
        couDanPopupWindow.setOutsideTouchable(true);
        LinearLayout llfs = (LinearLayout) view.findViewById(R.id.ll_layout_full_sub);
        cListView = (ListView) view.findViewById(R.id.coudan_list_view);
        pTvHas = (TextView) view.findViewById(R.id.tv_has_been_reduced);
        pTvText1 = (TextView) view.findViewById(R.id.tv_text1);
        pTvPriceSpread = (TextView) view.findViewById(R.id.tv_price_spread);
        pTvdimin = (TextView) view.findViewById(R.id.tv_diminishbb_price);
        checkFullReduction(merchant);
        couDanListAdapter = new CouDanListAdapter(this, couDanModelValue, mCartProducts, this);
        cListView.setAdapter(couDanListAdapter);
        if (couDanPopupWindow != null) {
            if (!couDanPopupWindow.isShowing()) {
                //设置popwindow显示位置
                bottomCart.measure(0, 0);
                int measuredHeight = bottomCart.getMeasuredHeight();
                couDanPopupWindow.showAtLocation(bottomCart, Gravity.BOTTOM, 0, measuredHeight);
                linearCover.setVisibility(View.VISIBLE);
                overlay.setVisibility(View.VISIBLE);
                if (couDanModelValue.size() >= 4) {
                    cListView.setPadding(0, 0, 0, DipToPx.dip2px(mActivity, 42));
                } else {
                    cListView.setPadding(0, 0, 0, 0);
                }
                AnimatorUtils.showBottom(llfs, this);
                AnimatorUtils.showBottom(cListView, this);
                AnimatorUtils.alphaIn(linearCover, this);
            } else {
                couDanPopupWindow.dismiss();
                linearCover.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.INVISIBLE);
            }
        }

    }

    /**
     * 初始化底部弹出框
     */
    private void initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_commercial_bottom_cart, null);
        mPopWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setContentView(view);
        mPopWindow.setHeight(DipToPx.dip2px(mActivity, 250));
        mPopWindow.setOutsideTouchable(true);
        TextView textView = (TextView) view.findViewById(R.id.tv_clear_goods);
        pLayoutFullSub = (LinearLayout) view.findViewById(R.id.ll_layout_full_sub);
        bTvHas = (TextView) view.findViewById(R.id.tv_has_been_reduced);
        bTvText1 = (TextView) view.findViewById(R.id.tv_text1);
        bTvPriceSpread = (TextView) view.findViewById(R.id.tv_price_spread);
        bTvdimin = (TextView) view.findViewById(R.id.tv_diminishbb_price);
        bTvAdd = (TextView) view.findViewById(R.id.tv_add_on_items);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mCartProducts.size(); i++) {
                    if (mCartProducts.get(i).getGoods().getHasDiscount() == 1) {
                        mCartProducts.get(i).getGoods().setFirst(true);
                    }
                }
                goodsFragment.clearList(mCartProducts);
                mCartProducts.clear();
                clearPickGoods();
                mAdapter.notifyDataSetChanged();
                setCart();
                mPopWindow.dismiss();
                overlay.setVisibility(View.INVISIBLE);
                linearCover.setVisibility(View.INVISIBLE);

            }
        });
        pLayoutFullSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
                overlay.setVisibility(View.GONE);
                visible = true;
                getCouDanData();
            }
        });
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                overlay.setVisibility(View.INVISIBLE);
                linearCover.setVisibility(View.INVISIBLE);
            }
        });
        PickGoodsModel.getInstance().getMerchantPickGoodsList();
        initCartProducts();
        if (CheckUtils.isNoEmptyList(mCartProducts)) {
            setCart();
        }
//        mPopWindow.setAnimationStyle(R.style.MenuDialogAnimation);
        relativeCenter = (RelativeLayout) view.findViewById(R.id.linear_center);
        mListView = (ListView) view.findViewById(R.id.commercial_cart_list_view);
        mAdapter = new BottomCartListAdapter(this, mCartProducts, this);
        mListView.setAdapter(mAdapter);
    }

    private void clearPickGoods() {
        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        if (CheckUtils.isNoEmptyList(merchantPickGoodsList)) {
            for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                merchantPickGoods.setGoodsCount(0);
            }
        }
    }

    private void initCartProducts() {
        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        if (CheckUtils.isNoEmptyList(merchantPickGoodsList)) {
            boolean contain = false;
            for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                if (merchantPickGoods.getMerchantId() == merchantId) {
                    if (merchantPickGoods.getPickGoods() != null) {
                        mCartProducts = merchantPickGoods.getPickGoods();
                    } else {
                        mCartProducts = new ArrayList<>();
                    }
                    contain = true;
                    break;
                }
            }
            if (!contain) {
                mCartProducts = new ArrayList<>();
            }
        } else {
            mCartProducts = new ArrayList<>();
        }
    }

    private void setListener() {
        imgBack.setOnClickListener(this);
        imgPin.setOnClickListener(this);
        imgFavor.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        tvGoods.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
        tvMerchants.setOnClickListener(this);
        pager.addOnPageChangeListener(this);
        bottomCart.setOnClickListener(this);
        linearCover.setOnClickListener(this);
        tv_goAccount.setOnClickListener(this);
        linearBroadcast.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
        llFullSubtract.setOnClickListener(this);
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public List<PickGoods> getCartProducts() {
        return mCartProducts;
    }

    private void addFragments(Merchant merchant) {
        goodsFragment = new GoodsFragment();
        goodsFragment.setListener(this);
        goodsFragment.setMerchant(merchant);
        evaluateFragment = new EvaluateFragment();
        evaluateFragment.setMerchant(merchantId);
        merchantsFragment = new MerchantsFragment();
        fragments.add(goodsFragment);
        fragments.add(evaluateFragment);
        fragments.add(merchantsFragment);
//        commercialAdapter.notify(fragments);
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LayoutParams lp = (LayoutParams) indicatorView.getLayoutParams();
        lp.width = screenWidth / 3;
        indicatorView.setLayoutParams(lp);
    }

    private void changeTabUi(int index) {
        switch (currentIndex) {
            case INDEX_GOODS:
                TextPaint paint1 = tvGoods.getPaint();
                paint1.setFakeBoldText(false);
                break;
            case INDEX_EVALUATE:
                TextPaint paint2 = tvEvaluate.getPaint();
                paint2.setFakeBoldText(false);
                break;
            case INDEX_MERCHANTS:
                TextPaint paint3 = tvMerchants.getPaint();
                paint3.setFakeBoldText(false);
                break;
            default:
                break;
        }
        switch (index) {
            case INDEX_GOODS:
                tvGoods.setTextColor(getResources().getColor(R.color.color_3));
                TextPaint paint = tvGoods.getPaint();
                paint.setFakeBoldText(true);
                tvEvaluate.setTextColor(getResources().getColor(R.color.color_6));
                tvMerchants.setTextColor(getResources().getColor(R.color.color_6));
                AnimatorUtils.showBottom(llFullSubtract, CommercialActivity.this);
                AnimatorUtils.showBottom(tvFullSubtract, CommercialActivity.this);
                AnimatorUtils.showBottom(bottomLayout, CommercialActivity.this);
                AnimatorUtils.showBottom(bottomCart, CommercialActivity.this);
                //0704修改 对应用没有影响
//                if (merchantTakeAwayMenu != null) {
//                    goodsFragment.getData(merchantTakeAwayMenu);
//                }
                break;
            case INDEX_EVALUATE:
                tvGoods.setTextColor(getResources().getColor(R.color.color_6));
                tvEvaluate.setTextColor(getResources().getColor(R.color.color_3));
                TextPaint paint1 = tvEvaluate.getPaint();
                paint1.setFakeBoldText(true);
                tvMerchants.setTextColor(getResources().getColor(R.color.color_6));
                if (currentIndex == INDEX_GOODS) {
                    AnimatorUtils.hideBottom(bottomLayout, CommercialActivity.this);
                    AnimatorUtils.hideBottom(bottomCart, CommercialActivity.this);
                    AnimatorUtils.hideBottom(llFullSubtract, CommercialActivity.this);
                    AnimatorUtils.hideBottom(tvFullSubtract, CommercialActivity.this);
                }
//                if (merchant != null) {
//                    evaluateFragment.setHeader(merchant);
//                }

                break;
            case INDEX_MERCHANTS:
                tvGoods.setTextColor(getResources().getColor(R.color.color_6));
                tvEvaluate.setTextColor(getResources().getColor(R.color.color_6));
                tvMerchants.setTextColor(getResources().getColor(R.color.color_3));
                TextPaint paint2 = tvMerchants.getPaint();
                paint2.setFakeBoldText(true);
                if (currentIndex == INDEX_GOODS) {
                    AnimatorUtils.hideBottom(bottomLayout, CommercialActivity.this);
                    AnimatorUtils.hideBottom(bottomCart, CommercialActivity.this);
                    AnimatorUtils.hideBottom(llFullSubtract, CommercialActivity.this);
                    AnimatorUtils.hideBottom(tvFullSubtract, CommercialActivity.this);
                }
                if (merchant != null) {
                    merchantsFragment.getData(merchant);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        LayoutParams lp = (LayoutParams) indicatorView.getLayoutParams();

        /**
         * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来 设置mTabLineIv的左边距
         * 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1; 1->0
         */

        if (currentIndex == 0 && position == 0)// 0->1
        {
            lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));

        } else if (currentIndex == 1 && position == 0) // 1->0
        {
            lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));

        } else if (currentIndex == 1 && position == 1) // 1->2
        {
            lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
        } else if (currentIndex == 2 && position == 1) // 2->1
        {
            lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
        }
        indicatorView.setLayoutParams(lp);

    }

    @Override
    public void onPageSelected(int arg0) {
        changeTabUi(arg0);
        currentIndex = arg0;
        if (arg0 != 0 && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
            linearCover.setVisibility(View.INVISIBLE);
            overlay.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commercial_act_iv_back:
                back();
                break;
            case R.id.commercial_act_iv_search:
                break;
            case R.id.commercial_act_iv_share:
                if (merchant != null && shareInfo != null && shareUtil == null) {
                    shareUtil = new ShareUtil(mActivity, merchant.getName(),
                            CheckUtils.isNoEmptyStr(shareInfo.getMemo()) ? shareInfo.getMemo() : String.format(getString(R.string.share_merchant), getString(R.string.app_name)),
                            shareInfo.getUrl(),
                            CheckUtils.isNoEmptyStr(shareInfo.getImg()) ? shareInfo.getImg() : merchant.getLogo());
                }
                if (shareUtil != null) shareUtil.showPopupWindow();
                break;
            case R.id.commercial_act_iv_favor:
                favorMerchant();
                break;
            case R.id.commercial_act_iv_pin:

                break;
            case R.id.commercial_act_tab_goods:
                if (pager.getCurrentItem() != INDEX_GOODS) {
                    pager.setCurrentItem(INDEX_GOODS);
                }
                break;
            case R.id.commercial_act_tab_evaluate:
                if (pager.getCurrentItem() != INDEX_EVALUATE) {
                    pager.setCurrentItem(INDEX_EVALUATE);
                }
                break;
            case R.id.commercial_act_tab_merchants:
                if (pager.getCurrentItem() != INDEX_MERCHANTS) {
                    pager.setCurrentItem(INDEX_MERCHANTS);
                }
                break;
            case R.id.commercial_act_bottom_car://底部购物车的点击事件
                if (couDanPopupWindow != null && couDanPopupWindow.isShowing()) {
                    couDanPopupWindow.dismiss();
                    linearCover.setVisibility(View.INVISIBLE);
                    overlay.setVisibility(View.INVISIBLE);
                    checkFullReduction(merchant);
                    return;
                }
                if (mCartProducts == null || mCartProducts.size() == 0) {
                    return;
                }
                if (mPopWindow != null) {
                    if (!mPopWindow.isShowing()) {
                        //设置popwindow显示位置
                        bottomCart.measure(0, 0);
                        int measuredHeight = bottomCart.getMeasuredHeight();
                        mPopWindow.showAtLocation(bottomCart, Gravity.BOTTOM, 0, measuredHeight);
                        linearCover.setVisibility(View.VISIBLE);
                        overlay.setVisibility(View.VISIBLE);
                        if (isFullSub && canDisplay) {
                            pLayoutFullSub.setVisibility(View.VISIBLE);
                        } else {
                            pLayoutFullSub.setVisibility(View.GONE);
                        }
                        checkFullReduction(merchant);
                        AnimatorUtils.showBottom(relativeCenter, this);
                        AnimatorUtils.showBottom(mListView, this);
                        AnimatorUtils.alphaIn(linearCover, this);
                    } else {
                        mPopWindow.dismiss();
                        linearCover.setVisibility(View.INVISIBLE);
                        overlay.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case R.id.linear_cover:
                //点击覆盖层相当于点击了一次购物车
                //onClick(bottomCart);
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
                if (couDanPopupWindow != null && couDanPopupWindow.isShowing()) {
                    couDanPopupWindow.dismiss();
                    checkFullReduction(merchant);
                }
                linearCover.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.INVISIBLE);
                break;
            case R.id.commercial_act_go_account:
                if (mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                    linearCover.setVisibility(View.INVISIBLE);
                    overlay.setVisibility(View.INVISIBLE);
                }
                if (!App.isLogin()) {
                    Intent intent = new Intent(mActivity, SmsLoginActivity.class);
                    startActivity(intent);
                    return;
                }
                getOrderPreview();
                break;
            case R.id.tv_promotion_count:
            case R.id.commercial_broadcast:
                if (merchant == null) return;
                if (mBroadcast == null) {
                    View view = LayoutInflater.from(this).inflate(R.layout.layout_shop_cover, null);
                    ImageView ivDismiss = (ImageView) view.findViewById(R.id.cover_dismiss);
                    ivDismiss.setOnClickListener(this);
                    TextView tvName = (TextView) view.findViewById(R.id.cover_name);
                    RatingBar score = (RatingBar) view.findViewById(R.id.cover_score);
                    LinearLayout promotionLayout = (LinearLayout) view.findViewById(R.id.promotion_layout);
                    LinearLayout promotionTitleLayout = (LinearLayout) view.findViewById(R.id.promotion_title_layout);
                    TextView tvBroadcast = (TextView) view.findViewById(R.id.tv_broadcast);
                    tvName.setText(merchant.getName());
                    score.setRating(merchant.getAverageScore().floatValue());
                    String broadcast = merchant.getBroadcast();
                    if (CheckUtils.isNoEmptyStr(broadcast)) {
                        tvBroadcast.setText(broadcast);
                    }
                    List<PromotionActivity> promotions = merchant.getPromotionActivityList();
                    if (promotions != null && promotions.size() > 0) {
                        promotionTitleLayout.setVisibility(View.VISIBLE);
                        for (PromotionActivity promotion : promotions) {
                            addPromotion(promotionLayout, promotion, true);
                        }
                    } else {
                        promotionTitleLayout.setVisibility(View.GONE);
                    }
                    mBroadcast = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    mBroadcast.setContentView(view);
                    mBroadcast.setOutsideTouchable(true);
                } else {
                    if (mBroadcast.isShowing()) {
                        mBroadcast.dismiss();
                    } else {
                        mBroadcast.showAtLocation(mDecorView, Gravity.NO_GRAVITY, 0, 0);
                    }
                }
                break;
            case R.id.cover_dismiss:
                mBroadcast.dismiss();
                break;
            case R.id.commercial_act_bottom:
                mPopWindow.dismiss();
                overlay.setVisibility(View.INVISIBLE);
                linearCover.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_layout_full_subtract:
                llFullSubtract.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
                visible = true;
                if (goodsFragment != null) {
                    goodsFragment.calculateBottom(llFullSubtract, tvFullSubtract);
                }
                getCouDanData();
                break;
            default:
                break;
        }

    }

    private void favorMerchant() {
        if (!favorite) {
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
        map.put("merchantType", 0);
        VolleyOperater<DeleteOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREAT_USER_FAVORITES, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null && ((DeleteOrderModel) obj).getCode() == 0) {
                    ToastUtils.showMyToast(mActivity, "收藏成功", R.drawable.collected);
                    favorite = true;
                    imgFavor.setImageResource(R.drawable.favored);
                }
            }
        }, DeleteOrderModel.class);
    }

    private void sendCancelFavorMerchantRequest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("merchantType", 0);
        VolleyOperater<DeleteOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CANCEL_USER_FAVORITES, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null && ((DeleteOrderModel) obj).getCode() == 0) {
                    ToastUtils.showMyToast(mActivity, "已取消收藏", R.drawable.uncollect);
                    favorite = false;
                    imgFavor.setImageResource(R.drawable.unfavor);
                }
            }
        }, DeleteOrderModel.class);
    }

    @Override
    public void onBackPressed() {
        if (mBroadcast != null && mBroadcast.isShowing()) {
            mBroadcast.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 订单预览
     */
    private void getOrderPreview() {
        final List<Menu> goodsList = new ArrayList<>();
        loadingDialog.show(getFragmentManager(), "");
        List<MerchantPickGoods> merchantPickGoodses = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        if (App.isLogin()) {
            map.put("loginToken", App.getUserInfo().getToken());
            map.put("userId", App.getUserInfo().getId());
        }
        ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
        for (MerchantPickGoods merchantPickGoods : merchantPickGoodses) {
            long mId = merchantPickGoods.getMerchantId();
            if (mId == merchantId) {
                pickGoods = merchantPickGoods.getPickGoods();
            }
        }
        for (PickGoods goods : pickGoods) {
            HashMap<String, Object> m = new HashMap<>();
            m.put("id", goods.getGoodsId());
            m.put("quantity", goods.getPickCount());
            m.put("specId", goods.getGoodsSpecId());
            m.put("attributes", goods.getGoodsName());
            m.put("hasDiscount", goods.getGoods().getHasDiscount());
            orderItems.add(m);
        }
        map.put("orderItems", orderItems);
        previewJsonData = new JSONObject(map);
        double latitude = Double.parseDouble(PreferenceUtils.getLocation(this)[0]);
        double longitude = Double.parseDouble(PreferenceUtils.getLocation(this)[1]);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("data", previewJsonData.toString());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        VolleyOperater<ConfirmOrderModel> operater = new VolleyOperater<>(CommercialActivity.this);
        operater.doRequest(Constants.URL_GET_ORDER_PREVIEW, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        errorMsg = (String) obj;
                        ToastUtils.displayMsg(errorMsg, mActivity);
                    } else {
                        confirmOrderModel = (ConfirmOrderModel) obj;
                        if (confirmOrderModel.isSuccess()) {
                            loadingDialog.dismiss();
                            if (goodsList.size() == 0) {
                                for (int i = 0; i < merchantTakeAwayMenu.getMenu().size(); i++) {
                                    if (merchantTakeAwayMenu.getMenu().get(i).getIsMandatory() == 1) {
                                        //处理大容量商品
                                        goodsList.add(merchantTakeAwayMenu.getMenu().get(i));
                                    }
                                }
                            }
                            List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                            for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                                if (merchantPickGoods.getMerchantId() != merchantId) {
                                    continue;
                                }
                                List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                                HashMap<Object, Object> hashMap = new HashMap<>();
                                for (PickGoods pickGood : pickGoods) {
                                    for (int j = 0; j < goodsList.size(); j++) {
                                        if (pickGood.getMenuId() == goodsList.get(j).getId()) {
                                            hashMap.put(pickGood.getMenuId(), pickGood.getMenuId());
                                        }
                                    }
                                }
                                if (hashMap.size() == goodsList.size()) {
                                    Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
                                    intent.putExtra("confirmOrderModel", confirmOrderModel);
                                    intent.putExtra("onceMoreOrder", previewJsonData);
                                    Log.d("---", previewJsonData.toString());
                                    startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
                                    return;
                                } else {
                                    for (Menu a : goodsList) {
                                        if (!hashMap.containsKey(a.getId())) {
                                            for (int i = 0; i < merchantTakeAwayMenu.getMenu().size(); i++) {
                                                if (merchantTakeAwayMenu.getMenu().get(i).getId() == a.getId()) {
                                                    showMandatoryDialog(i, a);
                                                    break;
                                                }
                                            }
                                            return;
                                        }
                                    }

                                }
                            }
                        } else {
                            ToastUtils.displayMsg("结算失败", mActivity);
                        }
//                        finish();
                    }
                }
                loadingDialog.dismiss();
            }
        }, ConfirmOrderModel.class);
    }

    private void showMandatoryDialog(final int position, Menu menu) {
        noticeDialog = new NoticeDialog(mActivity, new NoticeDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                noticeDialog.dismiss();
                goodsFragment.getClickMethod(position);
            }
        }, "", "请选择[" + menu.getName() + "（必选）]下的商品才\n可以下单哦~", "好的");
        noticeDialog.show();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        VolleyOperater<GoodsListModel> operater = new VolleyOperater<>(CommercialActivity.this);
        operater.doRequest(Constants.URL_SHOW_MERCHANT_TAKE_AWAY_CATEGORY, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    merchantTakeAwayMenu = ((GoodsListModel) obj).getValue();
                    merchantTakeAwayMenu.setServertime(((GoodsListModel) obj).getServertime());
                    if (merchantTakeAwayMenu != null) {
                        if (currentIndex == INDEX_GOODS) {
                            if (isAgainOrder && object != null && object.getJSONArray("goodsJson").size() > 0) {
                                //再次购买跳转
                                goodsFragment.clearList(mCartProducts);
                                mCartProducts.clear();
                                clearPickGoods();
                                mAdapter.notifyDataSetChanged();
                                setCart();
                                mPopWindow.dismiss();
                                linearCover.setVisibility(View.INVISIBLE);
                                overlay.setVisibility(View.INVISIBLE);
                                goodsFragment.getDataAgain(merchantTakeAwayMenu, object);
                            } else {
                                if (goodsId != -1) {
                                    //指定商品跳转
                                    goodsFragment.getData(merchantTakeAwayMenu, goodsId, goods, 0);
                                } else {
                                    //正常跳转
                                    goodsFragment.setData(merchantTakeAwayMenu);
                                }
                            }
                            if (merchantTakeAwayMenu.getType() == 1) {
                                //大容量 取消顶部滑动联动
                                scrollableLayout.setIsScroller(false);
                            } else {
                                scrollableLayout.setIsScroller(true);
                            }
                            if (goodsFragment != null) {
                                goodsFragment.calculateBottom(llFullSubtract, tvFullSubtract);
                            }
                        }
                    }
                }
            }
        }, GoodsListModel.class);
    }


    /**
     * 购买商品回调接口
     */
    @Override
    public void productHasChange(Goods goods, long categoryId, long goodsId, long goodsSpecId, int pickCount, boolean isRemove, boolean isSetAnim) {
        PickGoods changePickGoods = null;
        if (isRemove) {
            for (PickGoods pickGoods : mCartProducts) {
                if (pickGoods.getGoodsId() == goodsId && pickGoods.getGoodsSpecId() == goodsSpecId) {
                    pickGoods.setPickCount(0);
//                    pickGoods.setGoodsName();
                    changePickGoods = pickGoods;
                    mCartProducts.remove(pickGoods);
                    break;
                }
            }
            checkCart();
        } else {
            //如果改变的商品已经存在，那么就改变商品的数目，否则添加进购物车
            boolean mCartProductsContain = false;
            for (PickGoods pickGoods : mCartProducts) {
                if (pickGoods.getGoodsId() == goodsId) {
                    if (pickGoods.getGoodsSpecId() == goodsSpecId) {
                        pickGoods.setGoods(goods);
                        pickGoods.setPickCount(pickCount);
                        mCartProductsContain = true;
                        changePickGoods = pickGoods;
                    }
                }
            }
            if (!mCartProductsContain) {
                PickGoods pickGoods = new PickGoods();
                pickGoods.setPickCount(pickCount);
                pickGoods.setGoods(goods);
                pickGoods.setGoodsId(goodsId);
                pickGoods.setGoodsSpecId(goodsSpecId);
                pickGoods.setMenuId(categoryId);
                changePickGoods = pickGoods;
                mCartProducts.add(pickGoods);
            }
        }
        //刷新PopWindow
        mAdapter.setData(mCartProducts);
        if (mCartProducts.size() >= 4) {
            mListView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.x60));
        } else {
            mListView.setPadding(0, 0, 0, 0);
        }
        goodsFragment.notifyList(changePickGoods);
        if (!isSetAnim) {
            setCart();
        }
        savePickGoodsInfo();
    }

    @Override
    public void newProductHasChange(Goods goods, long categoryId, long goodsId, long goodsSpecId, int pickCount, boolean isRemove, boolean isSetAnim, String goodsName) {
        PickGoods changePickGoods = null;
        if (isRemove) {
            for (PickGoods pickGoods : mCartProducts) {
                if (pickGoods.getGoodsId() == goodsId && pickGoods.getGoodsSpecId() == goodsSpecId && pickGoods.getGoodsName().equals(goodsName)) {
                    pickGoods.setPickCount(0);
                    pickGoods.setGoodsName(goodsName);
                    changePickGoods = pickGoods;
                    mCartProducts.remove(pickGoods);
                    break;
                }
            }
            checkCart();
        } else {
            //如果改变的商品已经存在，那么就改变商品的数目，否则添加进购物车
            boolean mCartProductsContain = false;
            for (PickGoods pickGoods : mCartProducts) {
                if (pickGoods.getGoodsId() == goodsId) {
                    if (pickGoods.getGoodsSpecId() == goodsSpecId) {
                        if (pickGoods.getGoodsName().equals(goodsName)) {
                            pickGoods.setGoods(goods);
                            pickGoods.setPickCount(pickCount);
                            mCartProductsContain = true;
                            changePickGoods = pickGoods;
                        }
                    }
                }

            }
            if (!mCartProductsContain) {
                PickGoods pickGoods = new PickGoods();
                pickGoods.setPickCount(pickCount);
                pickGoods.setGoods(goods);
                pickGoods.setGoodsId(goodsId);
                pickGoods.setGoodsSpecId(goodsSpecId);
                pickGoods.setMenuId(categoryId);
                pickGoods.setGoodsName(goodsName);
                changePickGoods = pickGoods;
                mCartProducts.add(pickGoods);
            }
        }
        //刷新PopWindow
        mAdapter.setData(mCartProducts);
        if (mCartProducts.size() >= 4) {
            mListView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.x60));
        } else {
            mListView.setPadding(0, 0, 0, 0);
        }
        goodsFragment.notifyList(changePickGoods);
        if (!isSetAnim) {
            setCart();
        }
        savePickGoodsInfo();
    }

    private void setCart() {
        checkFullReduction(merchant);
        if (mCartProducts != null && mCartProducts.size() > 0) {
            calculatePrice();
        } else {
            tv_num.setVisibility(View.INVISIBLE);
            img_cart.setImageResource(R.drawable.cart_1);
            tv_allMoney.setText("¥0");
            tv_cart_package.setVisibility(View.GONE);
            tv_cart_shipping.setTextSize(14);
            tv_cart_qisong.setVisibility(View.VISIBLE);
            tv_goAccount.setVisibility(View.GONE);
            tv_cart_qisong.setText("¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice()) + "起送");
            if (tv_cart_shipping.getVisibility() == View.GONE) {
                rlCartShipingAndBox.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 检查购物车是否空了
     */
    private void checkCart() {
        if (mCartProducts.isEmpty()) {
            if (mPopWindow != null && mPopWindow.isShowing()) {
                mPopWindow.dismiss();
                linearCover.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 计算价格/数量
     */
    private void calculatePrice() {
        BigDecimal num = BigDecimal.ZERO;
        BigDecimal num_package = BigDecimal.ZERO;
        int count = 0;
        if (mCartProducts != null && mCartProducts.size() > 0) {
            for (PickGoods pro : mCartProducts) {
                for (GoodsSpec goodsSpec : pro.getGoods().getGoodsSpecList()) {
                    if (goodsSpec.getId() == pro.getGoodsSpecId()) {
                        if (pro.getGoods().getHasDiscount() == 1) {
                            int everyGoodsEveryOrderBuyCount = pro.getGoods().getEveryGoodsEveryOrderBuyCount();
                            int surplusDiscountStock = pro.getGoods().getSurplusDiscountStock();
                            if (everyGoodsEveryOrderBuyCount >= surplusDiscountStock) {
                                if (pro.getPickCount() >= surplusDiscountStock) {
                                    multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getGoods().getSurplusDiscountStock()));
                                    decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pro.getPickCount() - surplusDiscountStock));
                                    num = num.add(multiply.add(decimal));
                                } else {
                                    multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getPickCount()));
                                    num = num.add(multiply);
                                }
                            } else {
                                if (pro.getPickCount() >= everyGoodsEveryOrderBuyCount) {
                                    if (everyGoodsEveryOrderBuyCount > 0) {
                                        multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getGoods().getEveryGoodsEveryOrderBuyCount()));
                                        decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pro.getPickCount() - everyGoodsEveryOrderBuyCount));
                                        num = num.add(multiply.add(decimal));
                                    } else {
                                        if (pro.getPickCount() >= surplusDiscountStock) {
                                            multiply = goodsSpec.getPrice().multiply(new BigDecimal(surplusDiscountStock));
                                            decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pro.getPickCount() - surplusDiscountStock));
                                            num = num.add(multiply.add(decimal));
                                        } else {
                                            multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getPickCount()));
                                            num = num.add(multiply);
                                        }
                                    }
                                } else {
                                    multiply = goodsSpec.getPrice().multiply(new BigDecimal(pro.getPickCount()));
                                    num = num.add(multiply);
                                }
                            }
                        } else {
                            num = num.add(goodsSpec.getPrice().multiply(BigDecimal.valueOf((long) pro.getPickCount())));
                        }
                        num_package = num_package.add(goodsSpec.getBoxPrice().multiply(BigDecimal.valueOf(pro.getPickCount())));
                        break;
                    }
                }
                count += pro.getPickCount();
            }
            tv_num.setVisibility(View.VISIBLE);
            img_cart.setImageResource(R.drawable.cart_2);
            if (count > 99) {
                tv_num.setTextSize(10);
            } else {
                tv_num.setTextSize(14);
            }
            tv_num.setText(String.valueOf(count));
            tv_allMoney.setText("¥" + StringUtils.BigDecimal2Str(num));
            if (num_package.compareTo(BigDecimal.ZERO) == 1) {
                tv_cart_package.setText("餐盒费¥" + StringUtils.BigDecimal2Str(num_package));
                tv_cart_package.setVisibility(View.VISIBLE);
                tv_cart_shipping.setTextSize(10);
                if (tv_cart_shipping.getVisibility() == View.GONE) {
                    rlCartShipingAndBox.setVisibility(View.VISIBLE);
                }
            } else {
                tv_cart_package.setVisibility(View.GONE);
                if (tv_cart_shipping.getVisibility() == View.GONE) {
                    rlCartShipingAndBox.setVisibility(View.GONE);
                }
                tv_cart_shipping.setTextSize(14);
            }
            if (num.compareTo(merchant.getMinPrice()) != -1) {
                tv_cart_qisong.setVisibility(View.GONE);
                tv_goAccount.setVisibility(View.VISIBLE);
            } else {
                tv_cart_qisong.setVisibility(View.VISIBLE);
                tv_goAccount.setVisibility(View.GONE);
                tv_cart_qisong.setText("还差¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice().subtract(num)));
            }
        }
    }

    /**
     * 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout, rootView.getChildCount());
        return animLayout;
    }

    private View addViewToAnimLayout(final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x15),
                getResources().getDimensionPixelOffset(R.dimen.x15));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 开启动画
     */
    public void setAnim(final View v, int[] startLocation, final ViewGroup parent) {
        if (parent != null) {
            parent.addView(v);
        } else {
            if (anim_mask_layout == null)
                anim_mask_layout = createAnimLayout();
            else {
                anim_mask_layout.removeAllViews();
            }
            anim_mask_layout.addView(v);//把动画小球添加到动画层
        }
        final View view = addViewToAnimLayout(v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tv_num.getLocationInWindow(endLocation);// shopCart是那个购物车

        // 计算位移
        int endX = endLocation[0] - startLocation[0];// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, endX);
        translateXAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, endY);
        translateYAnimator.setInterpolator(new AnticipateInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(translateXAnimator).with(translateYAnimator);
        set.setDuration(500);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //TODO 结束的逻辑代码
                setCart();
                v.setVisibility(View.INVISIBLE);
                if (parent != null) {
                    parent.removeView(v);
                } else {
                    anim_mask_layout.removeView(v);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
        super.onDestroy();
    }

    private void savePickGoodsInfo() {
        if (CheckUtils.isNoEmptyList(mCartProducts)) {
            List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
            PickGoodsModel.getInstance().setHasChange(true);
            if (merchantPickGoodsList != null) {
                boolean contain = false;
                for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                    if (merchantPickGoods.getMerchantId() == merchantId) {
                        merchantPickGoods.setPickGoods(mCartProducts);
                        int count = 0;
                        for (PickGoods pro : mCartProducts) {
                            count += pro.getPickCount();
                        }
                        merchantPickGoods.setGoodsCount(count);
                        contain = true;
                        break;
                    }
                }
                if (!contain) {
                    MerchantPickGoods merchantPickGoods = new MerchantPickGoods();
                    merchantPickGoods.setPickGoods(mCartProducts);
                    merchantPickGoods.setMerchantId(merchantId);
                    int count = 0;
                    for (PickGoods pro : mCartProducts) {
                        count += pro.getPickCount();
                    }
                    merchantPickGoods.setGoodsCount(count);
                    merchantPickGoodsList.add(merchantPickGoods);
                }
            } else {
                merchantPickGoodsList = new ArrayList<>();
                MerchantPickGoods merchantPickGoods = new MerchantPickGoods();
                merchantPickGoods.setPickGoods(mCartProducts);
                merchantPickGoods.setMerchantId(merchantId);
                int count = 0;
                for (PickGoods pro : mCartProducts) {
                    count += pro.getPickCount();
                }
                merchantPickGoods.setGoodsCount(count);
                merchantPickGoodsList.add(merchantPickGoods);
            }
        } else {
            PickGoodsModel.getInstance().getMerchantPickGoodsList().clear();
            PickGoodsModel.getInstance().setHasChange(true);
            for (MerchantPickGoods merchantPickGoods : PickGoodsModel.getInstance().getMerchantPickGoodsList()) {
                if (merchantPickGoods.getMerchantId() == merchantId) {
                    merchantPickGoods.setGoodsCount(0);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActRequestCode.GOODS_DETAIL:
                refreshPage();
                if (resultCode == ActRequestCode.SPECIFY_MENU) {
                    if (data.hasExtra("menuId")) {
                        long menuId = data.getLongExtra("menuId", -1L);
                        goodsFragment.setSelectMenu(menuId);
                    }
                }
                break;
        }
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refreshPage();
    }

    private void refreshPage() {
        if (PickGoodsModel.getInstance().isRemove()) {
            goodsFragment.clearList();
            PickGoodsModel.getInstance().setHasChange(true);
            for (MerchantPickGoods merchantPickGoods : PickGoodsModel.getInstance().getMerchantPickGoodsList()) {
                if (merchantPickGoods.getMerchantId() == merchantId) {
                    merchantPickGoods.setGoodsCount(0);
                }
            }
            PickGoodsModel.getInstance().setIsRemove(false);
        } else {
            goodsFragment.clearList(mCartProducts);
        }
        initCartProducts();
        mAdapter.setData(mCartProducts);
        setCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nvPromotion != null && null != merchant && CheckUtils.isNoEmptyList(merchant.getPromotionActivityList())) {
            if (nvPromotion.getmNoticeList() == null) {
                nvPromotion.setBroadList(merchant.getPromotionActivityList(), mActivity, false);
            }
            nvPromotion.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nvPromotion != null) {
            nvPromotion.pause();
        }
    }
}
