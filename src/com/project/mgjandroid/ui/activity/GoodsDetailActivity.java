package com.project.mgjandroid.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.CouDanModel;
import com.project.mgjandroid.bean.DiscountedGoods;
import com.project.mgjandroid.bean.FullSub;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsDetailModel;
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
import com.project.mgjandroid.model.GoodsEvaluateModel;
import com.project.mgjandroid.model.GoodsListModel;
import com.project.mgjandroid.model.MerchantEvaluateTopModel;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.model.SurPlusBuyNumModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.BottomCartListAdapter;
import com.project.mgjandroid.ui.adapter.CouDanListAdapter;
import com.project.mgjandroid.ui.fragment.GoodsDetailFragment;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;
import com.project.mgjandroid.ui.view.HeaderViewPagerLayout;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DateUtils;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品详情
 *
 * @author jian
 */
public class GoodsDetailActivity extends BaseActivity implements OnClickListener, BottomCartListener {
    public static final String IMAGE_ORIGIN_RECT = "IMAGE_ORIGIN_RECT";
    public static final String IMAGE_URL = "IMAGE_URL";

    @InjectView(R.id.container)
    private RelativeLayout container;
    @InjectView(R.id.goods_detail_act_back)
    private ImageView imgBack;
    @InjectView(R.id.goods_detail_act_back_default)
    private ImageView ivBack;
    @InjectView(R.id.goods_detail_act_share_default)
    private ImageView ivShare;
    @InjectView(R.id.goods_detail_act_iv_share)
    private ImageView imgShare;
    @InjectView(R.id.goods_img)
    private RelativeLayout image_container;
    @InjectView(R.id.detail_banner)
    private MyBanner detailBanner;
    @InjectView(R.id.goods_pic)
    private ImageView goodsPic;
    @InjectView(R.id.goods_detail_top_bar)
    private RelativeLayout topBar;
    @InjectView(R.id.goods_detail_price_bar)
    private RelativeLayout priceBar;
    @InjectView(R.id.goods_item_tv_price)
    private TextView tv_barPrice;
    @InjectView(R.id.commercial_act_cart)
    private ImageView img_cart;
    @InjectView(R.id.cart_num)
    private TextView tv_num;
    @InjectView(R.id.overlay)
    private View overlay;
    @InjectView(R.id.commercial_act_bottom_money)
    private TextView tv_allMoney;
    @InjectView(R.id.commercial_act_bottom_shipping_and_box)
    private RelativeLayout rlCartShipingAndBox;
    @InjectView(R.id.commercial_act_bottom_shipping)
    private TextView tv_cart_shipping;
    @InjectView(R.id.commercial_act_bottom_package)
    private TextView tv_cart_package;
    @InjectView(R.id.commercial_act_bottom_qisong)
    private TextView tv_cart_qisong;
    @InjectView(R.id.commercial_act_bottom)
    private RelativeLayout bottomLayout;
    @InjectView(R.id.linear_cover)
    private LinearLayout linearCover;
    @InjectView(R.id.commercial_act_go_account)
    private TextView tv_goAccount;
    @InjectView(R.id.commercial_act_bottom_car)
    private FrameLayout bottomCart;
    @InjectView(R.id.commercial_act_bottom)
    private RelativeLayout bottomCartReplace;
    @InjectView(R.id.goods_item_img_add)
    private ImageView act_add;
    @InjectView(R.id.goods_item_tv_buy_count)
    private TextView tvBuyCount;
    @InjectView(R.id.goods_item_img_minus)
    private ImageView imgMinus;
    @InjectView(R.id.buy_count_hide)
    private RelativeLayout rlHideBuyCount;

    @InjectView(R.id.buy_count_hide_spec)
    private RelativeLayout rlSpecLabel;
    @InjectView(R.id.goods_item_img_minus_spec)
    private ImageView specMinus;
    @InjectView(R.id.goods_item_tv_buy_count_spec)
    private TextView specCount;
    @InjectView(R.id.goods_item_choose_spec)
    private TextView tvChooseSpec;
    @InjectView(R.id.goods_item_merchant_sleep)
    private TextView tvSleep;
    @InjectView(R.id.goods_detail_view_pager)
    private ViewPager mViewPager;
    @InjectView(R.id.scrollableLayout)
    private HeaderViewPagerLayout mHeaderViewPagerLayout;
    @InjectView(R.id.goods_item_tv_discount)
    private TextView tvDiscount;


    @InjectView(R.id.goods_detail_name)
    private TextView tv_goodsName;
    @InjectView(R.id.goods_detail_rat_score)
    private RatingBar rb_star;
    @InjectView(R.id.goods_detail_judge)
    private TextView tv_judge;
    @InjectView(R.id.goods_detail_count)
    private TextView tv_saleNumber;
    @InjectView(R.id.goods_deatil_food_name)
    private TextView tvTitle;
    @InjectView(R.id.goods_item_original_price)
    private TextView tvOriPri;
    @InjectView(R.id.goods_item_tv_limit)
    private TextView tvLimit;
    @InjectView(R.id.goods_item_tv_min)
    private TextView tvMin;
    @InjectView(R.id.goods_item_remaining_num)
    private TextView tvRemNum;

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


    private PopupWindow mPopWindow;
    private List<PickGoods> mCartProducts;
    private ListView bottomListView;
    private BottomCartListAdapter bottomAdapter;
    private RelativeLayout relativeCenter;
    private Merchant merchant;
    private ViewGroup anim_mask_layout;
    private GoodsSpec goodsSpec;
    private Context mContext;
    private LayoutInflater inflater;
    private int currentType = -1;

    ArrayList<GoodsEvaluateModel.ValueEntity> data = new ArrayList<>();
    private String errorMsg;
    private List<PickGoods> pickGoods;
    private JSONObject previewJsonData;
    private double latitude;
    private double longitude;
    private ConfirmOrderModel confirmOrderModel;
    private TextView tvEvaluate;
    private CustomDialog dialog;
    private List<HeaderViewPagerFragment> mFragments;
    private MLoadingDialog mLoadingDialog;
    private GoodsSpec mGoodsSpec;
    private ViewGroup parent;

    private ImageView mSourceImageView;
    private Rect mRect;
    private String mUrl;

    private ShareUtil shareUtil;
    private NoticeDialog noticeDialog;
    private MerchantTakeAwayMenu merchantTakeAwayMenu;
    private boolean isShare;
    private boolean hasDis;
    private boolean visible = false;

    private Goods goods;
    private BigDecimal multiply;
    private BigDecimal decimal;
    private BigDecimal multiply1;
    private BigDecimal decimal1;
    private BigDecimal bigDecimal;
    private List<Goods> couDanModelValue;
    private PopupWindow couDanPopupWindow;
    private TextView pTvHas;
    private TextView pTvText1;
    private TextView pTvPriceSpread;
    private TextView pTvdimin;
    private ListView cListView;
    private CouDanListAdapter couDanListAdapter;
    private boolean hasDis1;
    private BigDecimal subtract;
    private boolean isFullSub;
    private String str;
    private boolean canDisplay = true;
    private LinearLayout pLayoutFullSub;
    private TextView bTvHas;
    private TextView bTvText1;
    private TextView bTvPriceSpread;
    private TextView bTvdimin;
    private TextView bTvAdd;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.goods_detail_act);
        Injector.get(this).inject();
        mLoadingDialog = new MLoadingDialog();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) image_container.getLayoutParams();
        layoutParams.height = CommonUtils.getScreenWidth(mActivity.getWindowManager());
        image_container.setLayoutParams(layoutParams);

        mHeaderViewPagerLayout.setIsScroller(true);

        mContext = this;
        inflater = LayoutInflater.from(mContext);
        Goods getGoods = (Goods) getIntent().getExtras().getSerializable("goods");
        merchant = (Merchant) getIntent().getExtras().getSerializable("Merchant");
        boolean isGetNew = getIntent().getBooleanExtra("isGetNew", false);
        initSourceImageView();
        if (isGetNew) {
            getDetail(getGoods.getId());
        } else {
            goods = (Goods) getIntent().getExtras().getSerializable("goods");
            getData();
            initViews();
            setPageData(goods);
            if (merchant != null) {
                setData(merchant);
                initPopWindow();
                setGoodsData();
                checkFullReduction(merchant);
            } else {
                getMerchant(goods.getMerchantId());
            }
        }
    }

    private void getCouDanData() {
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchant.getId());
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
                AnimatorUtils.showBottom(tvFullSubtract, this);
                AnimatorUtils.showBottom(cListView, mActivity);
                AnimatorUtils.alphaIn(linearCover, mActivity);
            } else {
                couDanPopupWindow.dismiss();
                linearCover.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.INVISIBLE);
            }
        }

    }

    //判断是否满减
    private void checkFullReduction(Merchant merchant) {
        isShare = false;
        hasDis = false;
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

            if (!CheckUtils.isEmptyList(merchant.getPromotionActivityList())) {
                for (int i = 0; i < merchant.getPromotionActivityList().size(); i++) {
                    if (merchant.getPromotionActivityList().get(i).getRuleDtoList() != null && merchant.getPromotionActivityList().get(i).getRuleDtoList().size() > 0) {
                        isFullSub = true;
                    }
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
                        overlay.setVisibility(View.GONE);
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
                            bTvHas.setVisibility(View.GONE);
                            bTvText1.setText("已满");
                            bTvPriceSpread.setText(StringUtils.BigDecimal2Str(full) + "元");
                            bTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                            bTvAdd.setVisibility(View.GONE);
                            pLayoutFullSub.setClickable(false);
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

//                if (hasDis && hasFullSub) {
//                    ToastUtils.displayMsg("满减活动与折扣商品不同享", mActivity);
//                }
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
                            overlay.setVisibility(View.GONE);
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
                                bTvHas.setVisibility(View.GONE);
                                bTvText1.setText("已满");
                                bTvPriceSpread.setText(StringUtils.BigDecimal2Str(full) + "元");
                                bTvdimin.setText(StringUtils.BigDecimal2Str(sub) + "元");
                                bTvAdd.setVisibility(View.GONE);
                                pLayoutFullSub.setClickable(false);
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
                    PromotionActivity promotion = merchant.getPromotionActivityList().get(i);
                    if (promotion.getRuleDtoList() != null && promotion.getRuleDtoList().size() > 0) {
                        String promoName = promotion.getPromoName();
                        if (promoName.startsWith("在线支付")) {
                            str = promoName.substring(4);
                        } else {
                            str = promoName;
                        }
                        String limit = promotion.getUserLimit() != null ? "（限参与" + promotion.getUserLimit() + "次）" : "";
                        tvFullSubtract.setText(str + limit);
                        tvFullSubtract.setVisibility(View.VISIBLE);
                        overlay.setVisibility(View.GONE);
                        llFullSubtract.setVisibility(View.GONE);
                        isFullSub = true;
                    }
                }
            }
        }
    }

    public void popUp() {
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
                    hasDis1 = true;
                }
            }
        }
    }

    private void getDetail(final Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", "" + id);
        VolleyOperater<GoodsDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_QUERY_GOODS_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    goods = ((GoodsDetailModel) obj).getValue();
                    if (goods == null) {
                        goods = (Goods) getIntent().getExtras().getSerializable("goods");
                    } else {
                        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                        for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                            for (PickGoods pickGoods1 : merchantPickGoods.getPickGoods()) {
                                if (pickGoods1.getGoodsId() == id) {
                                    //存在该商品
                                    if (goods.getGoodsSpecList() != null) {
                                        for (GoodsSpec spec : goods.getGoodsSpecList()) {
                                            if (pickGoods1.getGoodsSpecId() == spec.getId()) {
                                                spec.setBuyCount(pickGoods1.getPickCount());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    getData();
                    initViews();
                    setPageData(goods);
                    if (merchant != null) {
                        setData(merchant);
                        initPopWindow();
                        setGoodsData();
                    } else {
                        getMerchant(goods.getMerchantId());
                    }
                } else {
                    goods = (Goods) getIntent().getExtras().getSerializable("goods");
                    getData();
                    initViews();
                    setPageData(goods);
                    if (merchant != null) {
                        setData(merchant);
                        initPopWindow();
                        setGoodsData();
                    } else {
                        getMerchant(goods.getMerchantId());
                    }
                }
            }
        }, GoodsDetailModel.class);
    }

    private void initSourceImageView() {
        // 读取第一个界面传过来的信息
        mRect = getIntent().getParcelableExtra(IMAGE_ORIGIN_RECT);
        if (mRect == null) {
            return;
        }
        mUrl = getIntent().getExtras().getString(IMAGE_URL);

        // 先动态创建出这个sourceImageView，把它添加到第二个界面的ContentView中。
        FrameLayout contentView = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);
        mSourceImageView = new ImageView(this);
        mSourceImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        contentView.addView(mSourceImageView);
        // 设置为和第一个界面一样的图片
        if (TextUtils.isEmpty(mUrl)) {
            mSourceImageView.setImageResource(R.drawable.horsegj_default);
        } else {
            ImageUtils.loadBitmap(mActivity, mUrl, mSourceImageView, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL);
        }
        // 设置为和原来一样的位置
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mSourceImageView.getLayoutParams();
        layoutParams.width = mRect.width();
        layoutParams.height = mRect.height();
        layoutParams.setMargins(mRect.left, mRect.top - getStatusBarHeight(mActivity), 0, 0);

        initImageEnterAnimation();
    }

    public static int getStatusBarHeight(Activity context) {
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    private void initImageEnterAnimation() {
        detailBanner.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onPreDraw() {
                ivBack.setVisibility(View.INVISIBLE);
                ivShare.setVisibility(View.INVISIBLE);
                detailBanner.setVisibility(View.INVISIBLE);
                runEnterAnim();
                detailBanner.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void runEnterAnim() {
        mSourceImageView.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(600)
                .scaleX(detailBanner.getWidth() / (float) (mRect.right - mRect.left))
                .scaleY(detailBanner.getHeight() / (float) (mRect.bottom - mRect.top))
                .translationX(detailBanner.getWidth() / 2 - (mRect.left + (mRect.right - mRect.left) / 2))
                .translationY(detailBanner.getHeight() / 2 - (mRect.top + (mRect.bottom - mRect.top) / 2))
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(mUrl))
                            goodsPic.setImageDrawable(mSourceImageView.getDrawable());
                        ivBack.setVisibility(View.VISIBLE);
                        ivShare.setVisibility(View.VISIBLE);
                        detailBanner.setVisibility(View.VISIBLE);
                        mSourceImageView.setVisibility(View.INVISIBLE);
                    }
                })
                .start();
        AnimatorUtils.showBottom2(mHeaderViewPagerLayout, mActivity, container.getHeight() - detailBanner.getHeight());
        AnimatorUtils.showBottom(bottomLayout, mActivity);
        AnimatorUtils.showBottom(bottomCart, mActivity);
        AnimatorUtils.showBottom(llFullSubtract, mActivity);
        AnimatorUtils.showBottom(tvFullSubtract, mActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void runExitAnim() {
        mSourceImageView.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(500)
                .scaleX(1)
                .scaleY(1)
                .translationX(0)
                .translationY(-getStatusBarHeight(mActivity))
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent());
        if (mSourceImageView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && (topBar.getVisibility() == View.INVISIBLE || topBar.getVisibility() == View.GONE)) {
            mSourceImageView.setVisibility(View.VISIBLE);
            container.setVisibility(View.INVISIBLE);
            runExitAnim();
        } else {
            super.onBackPressed();
        }
    }

    private void setGoodsData() {
        if (merchant.getStatus() == 0 || !merchant.isShoppingTime()) {
            return;
        }
        if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() == 1 && goods.getGoodsAttributeList() != null && goods.getGoodsAttributeList().size() > 0) {
            goodsSpec = goods.getGoodsSpecList().get(0);
            tvOriPri.setVisibility(View.GONE);
            tvRemNum.setVisibility(View.GONE);
            //TODO 多种规格
            currentType = 2;
            rlHideBuyCount.setVisibility(View.GONE);
            tvChooseSpec.setVisibility(View.VISIBLE);
            tvChooseSpec.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });
            rlSpecLabel.setVisibility(View.VISIBLE);
            boolean isOver = true;
            if (goods.getStatus() != 0 && goods.getStatus() != 2) {
                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                    GoodsSpec spec = goods.getGoodsSpecList().get(i);
                    if (spec.getStockType() == 0 || (spec.getStockType() == 1 && spec.getStock() != 0) || goods.getHasDiscount() == 1 && goods.getSurplusDiscountStock() > 0) {
                        isOver = false;
                        break;
                    }
                }
            }
            if (isOver) {
                rlSpecLabel.setVisibility(View.GONE);
                if (goods.getHasDiscount() == 1 && goods.getSurplusDiscountStock() > 0) {
                    tvSleep.setVisibility(View.GONE);
                } else {
                    tvSleep.setVisibility(View.VISIBLE);
                    tvSleep.setText("商品已售罄");
                }
                if (goods.getHasDiscount() == 1) {
                    if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                        tvLimit.setVisibility(View.GONE);
                        tvDiscount.setVisibility(View.VISIBLE);
                        BigDecimal b = new BigDecimal(goods.getDiscountedGoods().getDiscountProportion());
                        BigDecimal discount = b.divide(BigDecimal.TEN, 1, BigDecimal.ROUND_HALF_UP);
                        tvDiscount.setText(discount + "折  限购" + goods.getEveryGoodsEveryOrderBuyCount() + "份");
                    } else {
                        tvLimit.setVisibility(View.GONE);
                        tvDiscount.setVisibility(View.VISIBLE);
                        BigDecimal b = new BigDecimal(goods.getDiscountedGoods().getDiscountProportion());
                        BigDecimal discount = b.divide(BigDecimal.TEN, 1, BigDecimal.ROUND_HALF_UP);
                        tvDiscount.setText(discount + "折");
                    }
                } else {
                    tvLimit.setVisibility(View.GONE);
                }
                tvMin.setVisibility(View.GONE);
                rlHideBuyCount.setVisibility(View.INVISIBLE);
                return;
            }

            int num = 0;
            for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                GoodsSpec goodsSpec1 = goods.getGoodsSpecList().get(i);
                List<PickGoods> pickGoodsList = mCartProducts;
                for (PickGoods pickGoods : pickGoodsList) {
                    if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec1.getId()) {
                        num += pickGoods.getPickCount();
                    }
                }
            }

            specCount.setText(num + "");
            if (num > 0) {
                specMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, this));
                specCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, this));
            } else {
                specMinus.setTranslationX(0f);
                specCount.setTranslationX(0f);
            }

            BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
            for (int i = 1; i < goods.getGoodsSpecList().size(); i++) {
                if (price.compareTo(goods.getGoodsSpecList().get(i).getPrice()) == 1) {
                    price = goods.getGoodsSpecList().get(i).getPrice();
                }
            }
        } else if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() == 1) {
            //TODO 一种规格
            if (goods.getGoodsSpecList().get(0).getStockType() == 1 && goods.getGoodsSpecList().get(0).getStock() == 0 && goods.getHasDiscount() == 0) {
                currentType = 1;
                rlSpecLabel.setVisibility(View.GONE);
                if (goods.getHasDiscount() == 1 && goods.getSurplusDiscountStock() > 0) {
                    tvSleep.setVisibility(View.GONE);
                } else {
                    tvSleep.setVisibility(View.VISIBLE);
                    tvSleep.setText("商品已售罄");
                }
                rlHideBuyCount.setVisibility(View.INVISIBLE);
                tvLimit.setVisibility(View.GONE);
                tvMin.setVisibility(View.GONE);
            } else {
                currentType = 1;
                if (goods.getStatus() == 0 || goods.getStatus() == 2) {
                    rlSpecLabel.setVisibility(View.GONE);
                    tvChooseSpec.setVisibility(View.GONE);
                    if (goods.getHasDiscount() == 1 && goods.getSurplusDiscountStock() > 0) {
                        tvSleep.setVisibility(View.GONE);
                    } else {
                        tvSleep.setVisibility(View.VISIBLE);
                        tvSleep.setText("商品已售罄");
                    }
                    tvLimit.setVisibility(View.GONE);
                    tvMin.setVisibility(View.GONE);
                    rlHideBuyCount.setVisibility(View.INVISIBLE);
                } else {
                    rlHideBuyCount.setVisibility(View.VISIBLE);
                    tvChooseSpec.setVisibility(View.GONE);
                    rlSpecLabel.setVisibility(View.GONE);
                }
                goodsSpec = goods.getGoodsSpecList().get(0);
                if (goodsSpec.getBuyCount() > 0) {
                    imgMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    tvBuyCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                    tvBuyCount.setText(goodsSpec.getBuyCount() + "");
                } else {
                    imgMinus.setTranslationX(0f);
                    tvBuyCount.setTranslationX(0f);
                }
            }
        } else if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() > 1) {
            tvOriPri.setVisibility(View.GONE);
            tvRemNum.setVisibility(View.GONE);
            //TODO 多种规格
            currentType = 2;
            rlHideBuyCount.setVisibility(View.GONE);
            tvChooseSpec.setVisibility(View.VISIBLE);
            tvChooseSpec.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });
            rlSpecLabel.setVisibility(View.VISIBLE);
            boolean isOver = true;
            if (goods.getStatus() != 0 && goods.getStatus() != 2) {
                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                    GoodsSpec spec = goods.getGoodsSpecList().get(i);
                    if (spec.getStockType() == 0 || (spec.getStockType() == 1 && spec.getStock() != 0) || goods.getHasDiscount() == 1 && goods.getSurplusDiscountStock() > 0) {
                        isOver = false;
                        break;
                    }
                }
            }
            if (isOver) {
                rlSpecLabel.setVisibility(View.GONE);
                if (goods.getHasDiscount() == 1 && goods.getSurplusDiscountStock() > 0) {
                    tvSleep.setVisibility(View.GONE);
                } else {
                    tvSleep.setVisibility(View.VISIBLE);
                    tvSleep.setText("商品已售罄");
                }
                tvLimit.setVisibility(View.GONE);
                tvMin.setVisibility(View.GONE);
                rlHideBuyCount.setVisibility(View.INVISIBLE);
                return;
            }

            int num = 0;
//            if (goods.getGoodsAttributeList() == null) {
            for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                GoodsSpec goodsSpec1 = goods.getGoodsSpecList().get(i);
                List<PickGoods> pickGoodsList = mCartProducts;
                for (PickGoods pickGoods : pickGoodsList) {
                    if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec1.getId()) {
                        num += pickGoods.getPickCount();
                    }
                }
            }
//            } else {
//                List<PickGoods> pickGoodsList = mCartProducts;
//                for (PickGoods pickGood : pickGoodsList) {
//                    if (pickGood.getGoodsId() == goods.getId()) {
//                        num += pickGood.getPickCount();
//                    }
//                }
//            }

            specCount.setText(num + "");
            if (num > 0) {
                specMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, this));
                specCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, this));
            } else {
                specMinus.setTranslationX(0f);
                specCount.setTranslationX(0f);
            }

            BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
            for (int i = 1; i < goods.getGoodsSpecList().size(); i++) {
                if (price.compareTo(goods.getGoodsSpecList().get(i).getPrice()) == 1) {
                    price = goods.getGoodsSpecList().get(i).getPrice();
                }
            }
        }
    }

    private void showDialog() {
        mGoodsSpec = goods.getGoodsSpecList().get(0);
        final Dialog chooseSpecDialog = new Dialog(mContext, R.style.chooseSpecDialog);
        RelativeLayout contentView = (RelativeLayout) inflater.inflate(R.layout.goods_spec_choose_dialog, null);
        LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.layout_spec);
        TextView tvName = (TextView) contentView.findViewById(R.id.tv_goods_name);
        TextView tvSales = (TextView) contentView.findViewById(R.id.tv_goods_sales);
        ImageView ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
        final TextView tvGoodsPrice = (TextView) contentView.findViewById(R.id.tv_goods_price);
        final TextView tvGoodsOriginPrice = (TextView) contentView.findViewById(R.id.tv_original_price);
        final TextView tvStock = (TextView) contentView.findViewById(R.id.tv_stock);
        final TextView tvLimit = (TextView) contentView.findViewById(R.id.tv_limit);
        final TextView tvMin = (TextView) contentView.findViewById(R.id.tv_min);
        final FlowLayout flGoodsSpec = (FlowLayout) contentView.findViewById(R.id.goods_spec_flow_layout);
        final LinearLayout btnLayout = (LinearLayout) contentView.findViewById(R.id.btn_layout);
        final ImageView ivMinus = (ImageView) contentView.findViewById(R.id.goods_img_minus);
        final TextView tvBuyCount = (TextView) contentView.findViewById(R.id.goods_tv_buy_count);
        final ImageView ivAdd = (ImageView) contentView.findViewById(R.id.goods_img_add);
        final Button btnConfirm = (Button) contentView.findViewById(R.id.btn_confirm_goods_spec);
        final TextView tvNoSale = (TextView) contentView.findViewById(R.id.tv_no_count);

        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
            List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
            for (PickGoods pickGood : pickGoods) {
                for (GoodsSpec goodsSpec : goods.getGoodsSpecList()) {
                    if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                        if (goods.getMerchantId() == merchantPickGoods.getMerchantId() &&
                                goods.getCategoryId() == pickGood.getMenuId() &&
                                goods.getId() == pickGood.getGoodsId() &&
                                goodsSpec.getId() == pickGood.getGoodsSpecId()) {
                            goodsSpec.setBuyCount(pickGood.getPickCount());
                        }
                        if (mGoodsSpec.getBuyCount() > 0) {
                            btnConfirm.setVisibility(View.INVISIBLE);
                            btnLayout.setVisibility(View.VISIBLE);
                            tvBuyCount.setText(mGoodsSpec.getBuyCount() + "");
                        } else {
                            btnConfirm.setVisibility(View.VISIBLE);
                            btnLayout.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        String sName = "";
                        for (int i = 0; i < goods.getGoodsAttributeList().size(); i++) {
                            sName += goods.getGoodsAttributeList().get(i).getName().split("\\|\\*\\|")[0] + ",";
                        }
                        String sName1 = "";
                        sName1 = sName.substring(0, sName.lastIndexOf(","));
                        if (goods.getMerchantId() == merchantPickGoods.getMerchantId() &&
                                goods.getCategoryId() == pickGood.getMenuId() &&
                                goods.getId() == pickGood.getGoodsId() &&
                                goodsSpec.getId() == pickGood.getGoodsSpecId()) {
                            goodsSpec.setBuyCount(pickGood.getPickCount());
                            //boolean isShow = false;
                            if (mGoodsSpec.getId() == pickGood.getGoodsSpecId() && sName1.equals(pickGood.getGoodsName())) {
                                btnConfirm.setVisibility(View.INVISIBLE);
                                btnLayout.setVisibility(View.VISIBLE);
                                tvBuyCount.setText(pickGood.getPickCount() + "");
                            }
//                            if (isShow) {
//                                btnConfirm.setVisibility(View.INVISIBLE);
//                                btnLayout.setVisibility(View.VISIBLE);
//                                tvBuyCount.setText(pickGood.getPickCount() + "");
//                                MLog.e("值" + pickGood.getPickCount());
//                            } else {
//                                MLog.e("隐藏");
//                                btnConfirm.setVisibility(View.VISIBLE);
//                                btnLayout.setVisibility(View.INVISIBLE);
//                            }
                        }
                        //break;
                    }
                }
//                break;
            }
        }

        tvName.setText(goods.getName());
        tvSales.setText("已售" + goods.getTotalSaled() + "份");
        final List<View> viewList = new ArrayList<>();
        for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.spec_checkbox, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, DipToPx.dip2px(mContext, 15), DipToPx.dip2px(mContext, 5));
            checkBox.setLayoutParams(layoutParams);
            if (i == 0) {
                checkBox.setChecked(true);
                checkBox.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvGoodsPrice.setText(StringUtils.BigDecimal2Str(goods.getGoodsSpecList().get(i).getPrice()));
                if (goods.getGoodsSpecList().get(i).getOriginalPrice() != null && goods.getGoodsSpecList().get(i).getOriginalPrice().compareTo(BigDecimal.ZERO) > 0) {
                    tvGoodsOriginPrice.setVisibility(View.VISIBLE);
                    tvGoodsOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    tvGoodsOriginPrice.setText("¥" + StringUtils.BigDecimal2Str(goods.getGoodsSpecList().get(i).getOriginalPrice()));
                } else {
                    tvGoodsOriginPrice.setVisibility(View.GONE);
                }
                if (goods.getGoodsSpecList().get(i).getStockType() == 1 && goods.getGoodsSpecList().get(i).getStock() != null && goods.getGoodsSpecList().get(i).getStock() != 0 && 10 > goods.getGoodsSpecList().get(i).getStock()) {
                    if (goods.getHasDiscount() == 1) {
                        tvStock.setVisibility(View.GONE);
                    } else {
                        tvStock.setVisibility(View.VISIBLE);
                    }
                    tvStock.setText("仅剩" + goods.getGoodsSpecList().get(i).getStock() + "份");
                } else {
                    tvStock.setVisibility(View.GONE);
                }
                if (goods.getHasDiscount() == 1 && goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                    tvLimit.setVisibility(View.VISIBLE);
                    tvLimit.setText("每单限购" + goods.getEveryGoodsEveryOrderBuyCount() + "份");
                } else {
                    tvLimit.setVisibility(View.GONE);
                }
                if (goods.getHasDiscount() == 0 && goods.getGoodsSpecList().get(i).getOrderLimit() != null && goods.getGoodsSpecList().get(i).getOrderLimit() > 0) {
                    tvLimit.setVisibility(View.VISIBLE);
                    tvLimit.setText("每单限购" + goods.getGoodsSpecList().get(i).getOrderLimit() + "份");
                }
                if (goods.getGoodsSpecList().get(i).getMinOrderNum() != null && goods.getGoodsSpecList().get(i).getMinOrderNum() > 0) {
                    if (goods.getHasDiscount() == 1) {
                        tvMin.setVisibility(View.GONE);
                    } else {
                        tvMin.setVisibility(View.VISIBLE);
                    }
                    tvMin.setText(goods.getGoodsSpecList().get(i).getMinOrderNum() + "份起购");
                } else {
                    tvMin.setVisibility(View.GONE);
                }
                if (goods.getGoodsSpecList().get(i).getStockType() == 1 && goods.getGoodsSpecList().get(i).getStock() != null && goods.getGoodsSpecList().get(i).getStock() == 0) {
                    if (goods.getHasDiscount() == 1 && goods.getSurplusDiscountStock() > 0) {
                        tvNoSale.setVisibility(View.INVISIBLE);
                    } else {
                        tvNoSale.setVisibility(View.VISIBLE);
                        btnConfirm.setVisibility(View.INVISIBLE);
                        btnLayout.setVisibility(View.INVISIBLE);
                    }
                } else {
                    tvNoSale.setVisibility(View.INVISIBLE);
                }
            }
            if (CheckUtils.isNoEmptyStr(goods.getGoodsSpecList().get(i).getSpec())) {
                checkBox.setText(goods.getGoodsSpecList().get(i).getSpec());
            } else {
                checkBox.setText("默认");
            }
            checkBox.setTag(goods.getGoodsSpecList().get(i));
            checkBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < viewList.size(); j++) {
                        ((CheckBox) viewList.get(j)).setTextColor(getResources().getColor(R.color.color_6));
                        ((CheckBox) viewList.get(j)).setChecked(false);
                    }
                    mGoodsSpec = (GoodsSpec) v.getTag();
                    if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                        if (mGoodsSpec.getBuyCount() > 0) {
                            btnConfirm.setVisibility(View.INVISIBLE);
                            btnLayout.setVisibility(View.VISIBLE);
                            tvBuyCount.setText(mGoodsSpec.getBuyCount() + "");
                        } else {
                            btnConfirm.setVisibility(View.VISIBLE);
                            btnLayout.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        if (mGoodsSpec.getBuyCount() > 0) {
                            String sName = "";
                            for (int i = 0; i < goods.getSpecName().size(); i++) {
                                sName += goods.getSpecName().get(i) + ",";
                            }
                            sName = sName.substring(0, sName.lastIndexOf(","));
                            for (MerchantPickGoods merchantPickGoods : PickGoodsModel.getInstance().getMerchantPickGoodsList()) {
                                for (PickGoods pickGoods : merchantPickGoods.getPickGoods()) {
                                    if (pickGoods.getGoodsSpecId() == mGoodsSpec.getId() && sName.equals(pickGoods.getGoodsName())) {
                                        btnConfirm.setVisibility(View.INVISIBLE);
                                        btnLayout.setVisibility(View.VISIBLE);
                                        tvBuyCount.setText(mGoodsSpec.getBuyCount() + "");
                                        break;
                                    } else {
                                        btnConfirm.setVisibility(View.VISIBLE);
                                        btnLayout.setVisibility(View.INVISIBLE);
                                    }
                                }
                            }
                        } else {
                            btnConfirm.setVisibility(View.VISIBLE);
                            btnLayout.setVisibility(View.INVISIBLE);
                        }

//                        String sName = "";
//                        for (int i = 0; i < goods.getSpecName().size(); i++) {
//                            sName += goods.getSpecName().get(i) + ",";
//                        }
//                        String sName1 = "";
//                        sName1 = sName.substring(0, sName.lastIndexOf(","));
//                        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
//                        for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
//                            List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
//                            for (PickGoods pickGood : pickGoods) {
//                                if (mGoodsSpec.getBuyCount() > 0) {
//                                    if (sName1.equals(pickGood.getGoodsName()) && pickGood.getGoodsSpecId() == mGoodsSpec.getId()) {
//                                        btnConfirm.setVisibility(View.INVISIBLE);
//                                        btnLayout.setVisibility(View.VISIBLE);
//                                        tvBuyCount.setText(mGoodsSpec.getBuyCount() + "");
//                                        break;
//                                    } else {
//                                        btnConfirm.setVisibility(View.VISIBLE);
//                                        btnLayout.setVisibility(View.INVISIBLE);
//                                    }
//                                } else {
//                                    btnConfirm.setVisibility(View.VISIBLE);
//                                    btnLayout.setVisibility(View.INVISIBLE);
//                                }
//                            }
//                        }
                    }
                    tvGoodsPrice.setText(StringUtils.BigDecimal2Str(mGoodsSpec.getPrice()));
                    if (mGoodsSpec.getOriginalPrice() != null && mGoodsSpec.getOriginalPrice().compareTo(BigDecimal.ZERO) > 0) {
                        tvGoodsOriginPrice.setVisibility(View.VISIBLE);
                        tvGoodsOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        tvGoodsOriginPrice.setText("¥" + StringUtils.BigDecimal2Str(mGoodsSpec.getOriginalPrice()));
                    } else {
                        tvGoodsOriginPrice.setVisibility(View.GONE);
                    }
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0 && 10 > mGoodsSpec.getStock()) {
                        if (goods.getHasDiscount() == 1) {
                            tvStock.setVisibility(View.GONE);
                        } else {
                            tvStock.setVisibility(View.VISIBLE);
                        }
                        tvStock.setText("仅剩" + mGoodsSpec.getStock() + "份");
                    } else {
                        tvStock.setVisibility(View.GONE);
                    }
                    if (mGoodsSpec.getOrderLimit() != null && mGoodsSpec.getOrderLimit() > 0) {
                        tvLimit.setVisibility(View.VISIBLE);
                        tvLimit.setText("每单限购" + mGoodsSpec.getOrderLimit() + "份");
                    } else {
                        tvLimit.setVisibility(View.GONE);
                    }
                    if (mGoodsSpec.getMinOrderNum() != null && mGoodsSpec.getMinOrderNum() > 0) {
                        if (goods.getHasDiscount() == 1) {
                            tvMin.setVisibility(View.GONE);
                        } else {
                            tvMin.setVisibility(View.VISIBLE);
                        }
                        tvMin.setText(mGoodsSpec.getMinOrderNum() + "份起购");
                    } else {
                        tvMin.setVisibility(View.GONE);
                    }
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() == 0) {
                        if (goods.getHasDiscount() == 1 && goods.getSurplusDiscountStock() > 0) {
                            tvNoSale.setVisibility(View.INVISIBLE);
                        } else {
                            tvNoSale.setVisibility(View.VISIBLE);
                            btnConfirm.setVisibility(View.INVISIBLE);
                            btnLayout.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        tvNoSale.setVisibility(View.INVISIBLE);
                    }
                    ((CheckBox) v).setTextColor(getResources().getColor(R.color.title_bar_bg));
                    ((CheckBox) v).setChecked(true);
                }
            });
            viewList.add(checkBox);
            flGoodsSpec.addView(checkBox);
        }

        final List<List<View>> viewList1 = new ArrayList<>();
        final List<String> specName = new ArrayList<>();
        if (CheckUtils.isNoEmptyList(goods.getGoodsAttributeList()))
            for (int j = 0; j < goods.getGoodsAttributeList().size(); j++) {
                final List<View> list = new ArrayList<>();
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.spec_layout, null);
                TextView tvSauce = (TextView) linearLayout.findViewById(R.id.tv_goods_sauce);
                FlowLayout flGoodsSauce = (FlowLayout) linearLayout.findViewById(R.id.goods_sauce_flow_layout);
                specName.add(goods.getGoodsAttributeList().get(j).getName().split("\\|\\*\\|")[0]);
                goods.setSpecName(specName);
                for (int a = 0; a < goods.getGoodsAttributeList().get(j).getName().split("\\|\\*\\|").length; a++) {
                    CheckBox checkBox1 = (CheckBox) inflater.inflate(R.layout.spec_checkbox, null);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams1.setMargins(0, 0, DipToPx.dip2px(mContext, 15), DipToPx.dip2px(mContext, 5));
                    checkBox1.setLayoutParams(layoutParams1);
                    if (a == 0) {
                        checkBox1.setChecked(true);
                        checkBox1.setTextColor(getResources().getColor(R.color.title_bar_bg));
                    }
                    tvSauce.setText(goods.getGoodsAttributeList().get(j).getTitle());
//				specName.add(goods.getGoodsAttributeList().get(j).getName().split(",")[a]);
                    checkBox1.setText(goods.getGoodsAttributeList().get(j).getName().split("\\|\\*\\|")[a]);

                    checkBox1.setTag(j + "," + a);
                    checkBox1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tag = (String) v.getTag();
                            String[] tags = tag.split(",");
                            for (int z = 0; z < viewList1.get(Integer.parseInt(tags[0])).size(); z++) {
                                ((CheckBox) viewList1.get(Integer.parseInt(tags[0])).get(z)).setTextColor(getResources().getColor(R.color.color_6));
                                ((CheckBox) viewList1.get(Integer.parseInt(tags[0])).get(z)).setChecked(false);
                            }
                            ((CheckBox) viewList1.get(Integer.parseInt(tags[0])).get(Integer.parseInt(tags[1]))).setTextColor(getResources().getColor(R.color.title_bar_bg));
                            ((CheckBox) viewList1.get(Integer.parseInt(tags[0])).get(Integer.parseInt(tags[1]))).setChecked(true);

                            specName.remove(Integer.parseInt(tags[0]));
                            specName.add(Integer.parseInt(tags[0]), goods.getGoodsAttributeList().get(Integer.parseInt(tags[0])).getName().split("\\|\\*\\|")[Integer.parseInt(tags[1])]);
                            goods.setSpecName(specName);
                            String sName = "";
                            for (int i = 0; i < specName.size(); i++) {
                                sName += specName.get(i) + ",";
                            }
                            String sName1 = "";
                            sName1 = sName.substring(0, sName.lastIndexOf(","));
                            List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                            for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                                List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                                for (PickGoods pickGood : pickGoods) {
                                    if (pickGood.getGoodsId() == goods.getId() && pickGood.getGoodsSpecId() == mGoodsSpec.getId()) {
                                        if (sName1.equals(pickGood.getGoodsName())) {
                                            btnConfirm.setVisibility(View.INVISIBLE);
                                            btnLayout.setVisibility(View.VISIBLE);
                                            tvBuyCount.setText(pickGood.getPickCount() + "");
                                            break;
                                        } else {
                                            btnConfirm.setVisibility(View.VISIBLE);
                                            btnLayout.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    list.add(checkBox1);
                    flGoodsSauce.addView(checkBox1);
                }
                viewList1.add(list);
                layout.addView(linearLayout);
            }

        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goods.getHasDiscount() == 0) {
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() == 0) {
                        ToastUtils.displayMsg("该商品库存不足", mContext);
                        return;
                    }
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0) {
                        if (mGoodsSpec.getMinOrderNum() != null && mGoodsSpec.getStock() < mGoodsSpec.getMinOrderNum()) {
                            ToastUtils.displayMsg("该商品库存不足", mContext);
                            return;
                        }
                    }
                }
                if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                    int count = 1;
                    if (mGoodsSpec.getMinOrderNum() != null && mGoodsSpec.getMinOrderNum() > 0) {
                        count = mGoodsSpec.getMinOrderNum();
                    }
                    mGoodsSpec.setBuyCount(count);
                    productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), count, false, false);
                    btnConfirm.setVisibility(View.INVISIBLE);
                    btnLayout.setVisibility(View.VISIBLE);
                    tvBuyCount.setText(String.valueOf(count));
                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    ivAdd.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    ImageView ball = new ImageView(mContext);// buyImg是动画的图片
                    ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                    setAnim(ball, startLocation, parent);// 开始执行动画
                } else {

                    String sName = "";
                    for (int i = 0; i < specName.size(); i++) {
                        sName += specName.get(i) + ",";
                    }
                    String sName1 = "";
                    sName1 = sName.substring(0, sName.lastIndexOf(","));
//                    if (mGoodsSpec.getMinOrderNum() != null && mGoodsSpec.getMinOrderNum() > 0) {
//                        int buyCount = 0;
//                        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
//                        for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
//                            List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
//                            for (PickGoods pickGood : pickGoods) {
//                                if (pickGood.getGoodsId() == goods.getId() && mGoodsSpec.getId() == pickGood.getGoodsSpecId()) {
//                                    buyCount += pickGood.getPickCount();
//                                }
//                            }
//                        }
//                        if (mGoodsSpec.getOrderLimit() != 0 && buyCount >= mGoodsSpec.getOrderLimit()) {
//                            ToastUtils.displayMsg(goods.getName() + "商品限购" + mGoodsSpec.getOrderLimit() + "份", mActivity);
//                            return;
//                        }
//                        if (buyCount >= mGoodsSpec.getStock()) {
//                            ToastUtils.displayMsg("该商品库存不足", mActivity);
//                            return;
//                        }
//                        newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getMinOrderNum(), false, false, sName1);
//                        tvBuyCount.setText(String.valueOf(mGoodsSpec.getMinOrderNum()));
//                    } else {
                    int buyCount = 0;
                    List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                    if (merchantPickGoodsList.size() == 0) {
                        if (goods.getHasDiscount() == 1) {
                            if ((buyCount + 1) == goods.getEveryGoodsEveryOrderBuyCount()) {
                                if (goods.isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品每单限购" + goods.getEveryGoodsEveryOrderBuyCount() + "件，超出部分需原价购买。", mContext);
                                    goods.setFirst(false);
                                }
                            }
                        }
                    }
                    for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                        List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                        for (PickGoods pickGood : pickGoods) {
                            if (pickGood.getGoodsId() == goods.getId() && mGoodsSpec.getId() == pickGood.getGoodsSpecId()) {
                                buyCount += pickGood.getPickCount();
                            }
                        }
                        if (goods.getHasDiscount() == 1) {
                            popUp();
                            if (pickGoods.size() == 0) {
                                if ((buyCount + 1) == goods.getEveryGoodsEveryOrderBuyCount()) {
                                    if (goods.isFirst()) {
                                        ToastUtils.displayMsg("当前折扣商品每单限购" + goods.getEveryGoodsEveryOrderBuyCount() + "件，超出部分需原价购买。", mContext);
                                        goods.setFirst(false);
                                    }
                                }
                            }
                        }
                    }
                    if (mGoodsSpec.getOrderLimit() != 0 && buyCount >= mGoodsSpec.getOrderLimit()) {
                        ToastUtils.displayMsg(goods.getName() + "商品限购" + mGoodsSpec.getOrderLimit() + "份", mActivity);
                        return;
                    }
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() > 0 && buyCount >= mGoodsSpec.getStock()) {
                        ToastUtils.displayMsg("该商品库存不足", mActivity);
                        return;
                    }
                    mGoodsSpec.setBuyCount(1);
                    newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), 1, false, false, sName1);
                    tvBuyCount.setText(String.valueOf(1));
//                    }

                    btnConfirm.setVisibility(View.INVISIBLE);
                    btnLayout.setVisibility(View.VISIBLE);
                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    ivAdd.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    ImageView ball = new ImageView(mContext);// buyImg是动画的图片
                    ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                    setAnim(ball, startLocation, parent);// 开始执行动画
                }
            }
        });

        ivAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                    int count = mGoodsSpec.getBuyCount();
                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0 && count >= mGoodsSpec.getStock()) {
                        ToastUtils.displayMsg("该商品库存不足", mContext);
                        return;
                    }
                    if (mGoodsSpec.getOrderLimit() != 0 && count >= mGoodsSpec.getOrderLimit()) {
                        ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                        return;
                    }
                    if (count == 0) {
                        for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                            if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", mActivity);
                                count = goods.getGoodsSpecList().get(i).getMinOrderNum() - 1;
                                break;
                            }
                        }
                    }
                    count++;
                    tvBuyCount.setText(count + "");
                    mGoodsSpec.setBuyCount(count);
                    //只要点击了就去更新购物车
                    productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), false, true);

                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    ImageView ball = new ImageView(mContext);// buyImg是动画的图片
                    ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                    setAnim(ball, startLocation, parent);// 开始执行动画
                } else {
                    String sName = "";
                    for (int i = 0; i < specName.size(); i++) {
                        sName += specName.get(i) + ",";
                    }
                    String sName1 = "";
                    int buyCount = 0;
                    int minBuyCount = 0;
                    sName1 = sName.substring(0, sName.lastIndexOf(","));
                    List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                    for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                        List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                        for (PickGoods pickGood : pickGoods) {
                            if (goods.getId() == pickGood.getGoodsId() && mGoodsSpec.getId() == pickGood.getGoodsSpecId()) {
                                buyCount += pickGood.getPickCount();
                            }
                        }
                        for (PickGoods pickGood : pickGoods) {
                            int count = pickGood.getPickCount();
                            if (goods.getHasDiscount() == 1) {
                                if (pickGood.getGoodsId() == goods.getId() && pickGood.getGoodsSpecId() == mGoodsSpec.getId()) {
                                    if (goods.getEveryGoodsEveryOrderBuyCount() > goods.getSurplusDiscountStock()) {
                                        if (count == goods.getSurplusDiscountStock()) {
                                            if (mGoodsSpec.getMinOrderNum() > 0) {
                                                if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null) {
                                                    minBuyCount = mGoodsSpec.getMinOrderNum() > mGoodsSpec.getStock() ? mGoodsSpec.getStock() : mGoodsSpec.getMinOrderNum();
                                                } else {
                                                    minBuyCount = mGoodsSpec.getMinOrderNum();
                                                }
                                                if (mGoodsSpec.getMinOrderNum() != 0 && (count - goods.getSurplusDiscountStock()) <= mGoodsSpec.getMinOrderNum()) {
                                                    ToastUtils.displayMsg(goods.getName() + "商品最少购买" + mGoodsSpec.getMinOrderNum() + "份", mActivity);
                                                    count = count + minBuyCount - 1;
                                                }
                                            }
                                        }
                                    } else {
                                        if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                            if (count == goods.getEveryGoodsEveryOrderBuyCount()) {
                                                if (mGoodsSpec.getMinOrderNum() > 0) {
                                                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null) {
                                                        minBuyCount = mGoodsSpec.getMinOrderNum() > mGoodsSpec.getStock() ? mGoodsSpec.getStock() : mGoodsSpec.getMinOrderNum();
                                                    } else {
                                                        minBuyCount = mGoodsSpec.getMinOrderNum();
                                                    }
                                                    if (mGoodsSpec.getMinOrderNum() != 0 && (count - goods.getEveryGoodsEveryOrderBuyCount()) <= mGoodsSpec.getMinOrderNum()) {
                                                        ToastUtils.displayMsg(goods.getName() + "商品最少购买" + mGoodsSpec.getMinOrderNum() + "份", mActivity);
                                                        count = count + minBuyCount - 1;
                                                    }
                                                }
                                            }
                                        } else {
                                            if (count == goods.getSurplusDiscountStock()) {
                                                if (mGoodsSpec.getMinOrderNum() > 0) {
                                                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null) {
                                                        minBuyCount = mGoodsSpec.getMinOrderNum() > mGoodsSpec.getStock() ? mGoodsSpec.getStock() : mGoodsSpec.getMinOrderNum();
                                                    } else {
                                                        minBuyCount = mGoodsSpec.getMinOrderNum();
                                                    }
                                                    if (mGoodsSpec.getMinOrderNum() != 0 && (count - goods.getSurplusDiscountStock()) <= mGoodsSpec.getMinOrderNum()) {
                                                        ToastUtils.displayMsg(goods.getName() + "商品最少购买" + mGoodsSpec.getMinOrderNum() + "份", mActivity);
                                                        count = count + minBuyCount - 1;
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    //每单限购大于折扣库存
                                    if (goods.getEveryGoodsEveryOrderBuyCount() > goods.getSurplusDiscountStock()) {
                                        if ((buyCount + 1) >= goods.getSurplusDiscountStock()) {
                                            if (goods.isFirst()) {
                                                ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", mActivity);
                                                goods.setFirst(false);
                                            }
                                        }
                                        if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                            if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                                if ((buyCount + 1) - goods.getSurplusDiscountStock() > goodsSpec.getStock()) {
                                                    ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                                    return;
                                                }
                                            } else {
                                                if ((buyCount + 1) - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && mGoodsSpec.getOrderLimit() > 0) {
                                                    ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                                    return;
                                                }
                                                if (mGoodsSpec.getOrderLimit() == 0 && (buyCount + 1) - goods.getSurplusDiscountStock() > mGoodsSpec.getStock()) {
                                                    ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                                    return;
                                                }
                                            }
                                        } else {
                                            if ((buyCount + 1) - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && mGoodsSpec.getOrderLimit() > 0) {
                                                ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                                return;
                                            }
                                        }
                                        if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0 && (count - goods.getSurplusDiscountStock()) > mGoodsSpec.getStock()) {
                                            ToastUtils.displayMsg("该商品库存不足", mContext);
                                            return;
                                        }
                                    } else {
                                        if ((buyCount + 1) == goods.getEveryGoodsEveryOrderBuyCount() && goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                            if (goods.isFirst()) {
                                                ToastUtils.displayMsg("当前折扣商品每单限购" + goods.getEveryGoodsEveryOrderBuyCount() + "件，超出部分需原价购买。", mActivity);
                                                goods.setFirst(false);
                                            }
                                        }
                                        if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                                if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                                    if ((buyCount + 1) - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getStock()) {
                                                        ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                                        return;
                                                    }
                                                } else {
                                                    if ((buyCount + 1) - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && mGoodsSpec.getOrderLimit() > 0) {
                                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                                        return;
                                                    }
                                                    if (mGoodsSpec.getOrderLimit() == 0 && (buyCount + 1) - goods.getEveryGoodsEveryOrderBuyCount() > mGoodsSpec.getStock()) {
                                                        ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                                        return;
                                                    }
                                                }
                                            } else {
                                                if ((buyCount + 1) - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && mGoodsSpec.getOrderLimit() > 0) {
                                                    ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                                    return;
                                                }
                                            }
                                            if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0 && (count - goods.getEveryGoodsEveryOrderBuyCount()) > mGoodsSpec.getStock()) {
                                                ToastUtils.displayMsg("该商品库存不足", mContext);
                                                return;
                                            }
                                        } else {
                                            if ((buyCount + 1) >= goods.getSurplusDiscountStock()) {
                                                if (goods.isFirst()) {
                                                    ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", mContext);
                                                    goods.setFirst(false);
                                                }
                                            }
                                            if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null) {
                                                if (mGoodsSpec.getOrderLimit() > mGoodsSpec.getStock()) {
                                                    if ((buyCount + 1) - goods.getSurplusDiscountStock() > mGoodsSpec.getStock()) {
                                                        ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                                        return;
                                                    }
                                                } else {
                                                    if ((buyCount + 1) - goods.getSurplusDiscountStock() > mGoodsSpec.getOrderLimit() && mGoodsSpec.getOrderLimit() > 0) {
                                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                                        return;
                                                    }
                                                    if (mGoodsSpec.getOrderLimit() == 0 && (buyCount + 1) - goods.getSurplusDiscountStock() > mGoodsSpec.getStock()) {
                                                        ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                                        return;
                                                    }
                                                }
                                            } else {
                                                if ((buyCount + 1) - goods.getSurplusDiscountStock() > mGoodsSpec.getOrderLimit() && mGoodsSpec.getOrderLimit() > 0) {
                                                    ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                                    return;
                                                }
                                            }
                                            if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0 && (count - goods.getSurplusDiscountStock()) > mGoodsSpec.getStock()) {
                                                ToastUtils.displayMsg("该商品库存不足", mContext);
                                                return;
                                            }
                                        }

                                    }
                                    if (pickGood.getGoodsId() == goods.getId() && pickGood.getGoodsSpecId() == mGoodsSpec.getId() && sName1.equals(pickGood.getGoodsName())) {
                                        count++;
                                        tvBuyCount.setText(count + "");
                                        mGoodsSpec.setBuyCount(count);
                                        newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), count, false, true, sName1);
                                    }
                                }
                            } else {
                                if (pickGood.getGoodsId() == goods.getId() && pickGood.getGoodsSpecId() == mGoodsSpec.getId() && sName1.equals(pickGood.getGoodsName())) {
                                    if (mGoodsSpec.getStockType() == 1 && mGoodsSpec.getStock() != null && mGoodsSpec.getStock() != 0 && buyCount >= mGoodsSpec.getStock()) {
                                        ToastUtils.displayMsg("该商品库存不足", mActivity);
                                        return;
                                    }
                                    if (mGoodsSpec.getOrderLimit() != 0 && buyCount >= mGoodsSpec.getOrderLimit()) {
                                        ToastUtils.displayMsg(goods.getName() + "商品限购" + mGoodsSpec.getOrderLimit() + "份", mActivity);
                                        return;
                                    }
                                    count++;
                                    tvBuyCount.setText(count + "");
                                    mGoodsSpec.setBuyCount(count);
                                    newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), count, false, true, sName1);
                                }
                            }
                        }
                    }
                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    ImageView ball = new ImageView(mActivity);// buyImg是动画的图片
                    ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                    setAnim(ball, startLocation, parent);// 开始执行动画
                }
            }
        });

        ivMinus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                    int count = 0;
                    String sName = "";
                    long goodsSpecId = 0;
                    for (int i = 0; i < specName.size(); i++) {
                        sName += specName.get(i) + ",";
                    }
                    String sName1 = "";
                    sName1 = sName.substring(0, sName.lastIndexOf(","));
                    List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                    for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                        List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                        for (PickGoods pickGood : pickGoods) {
                            if (pickGood.getGoodsId() == goods.getId() && pickGood.getGoodsSpecId() == mGoodsSpec.getId() && sName1.equals(pickGood.getGoodsName())) {
                                count = pickGood.getPickCount();
                                MLog.e("总数1---" + count);
                                goodsSpecId = pickGood.getGoodsSpecId();
                            }
                        }
                    }

//                    for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
//                        if (count == goods.getGoodsSpecList().get(i).getMinOrderNum() && goods.getGoodsSpecList().get(i).getId() == goodsSpecId) {
//                            if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
//                                ToastUtils.displayMsg(goods.getName() + "商品最少购买" + mGoodsSpec.getMinOrderNum() + "份", mActivity);
//                            }
//                            count = 1;
//                            MLog.e("总数2---" + count);
//                            break;
//                        }
//                    }

                    if (count > 0) {
                        if (goods.getEveryGoodsEveryOrderBuyCount() <= goods.getSurplusDiscountStock()) {
                            if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (count <= goods.getEveryGoodsEveryOrderBuyCount()) {
                                    goods.setFirst(true);
                                }
                            } else {
                                if (count <= goods.getSurplusDiscountStock()) {
                                    goods.setFirst(true);
                                }
                            }
                        } else {
                            if (count <= goods.getSurplusDiscountStock()) {
                                goods.setFirst(true);
                            }
                        }
                        if (goods.getHasDiscount() == 1) {
                            if (goods.getEveryGoodsEveryOrderBuyCount() > goods.getSurplusDiscountStock()) {
                                if (mGoodsSpec.getMinOrderNum() > 0 && count - goods.getSurplusDiscountStock() == mGoodsSpec.getMinOrderNum()) {
                                    count = count - mGoodsSpec.getMinOrderNum() + 1;
                                }
                            } else {
                                if (mGoodsSpec.getMinOrderNum() > 0 && goods.getEveryGoodsEveryOrderBuyCount() > 0 && count - goods.getEveryGoodsEveryOrderBuyCount() == mGoodsSpec.getMinOrderNum()) {
                                    count = count - mGoodsSpec.getMinOrderNum() + 1;
                                }
                            }
                        }
                        count--;
                        tvBuyCount.setText(count + "");
                        if (count == 0) {
                            btnLayout.setVisibility(View.INVISIBLE);
                            btnConfirm.setVisibility(View.VISIBLE);
                            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                                mGoodsSpec.setBuyCount(count);
                                productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), true, false);
                            } else {
                                String sName2 = "";
                                for (int i = 0; i < specName.size(); i++) {
                                    sName2 += specName.get(i) + ",";
                                }
                                String sName3 = "";
                                sName3 = sName2.substring(0, sName2.lastIndexOf(","));
                                newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), count, true, false, sName3);
                            }
                        } else {
                            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                                //只要点击了就去更新购物车
                                productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), false, false);
                            } else {
                                String sName4 = "";
                                for (int i = 0; i < specName.size(); i++) {
                                    sName4 += specName.get(i) + ",";
                                }
                                String sName5 = "";
                                sName5 = sName4.substring(0, sName4.lastIndexOf(","));
                                newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), count, false, false, sName5);
                            }
                        }
                    }
                } else {
                    int count = mGoodsSpec.getBuyCount();
                    if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                        for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                            if (count == goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                    ToastUtils.displayMsg(goods.getName() + "商品最少购买" + mGoodsSpec.getMinOrderNum() + "份", mActivity);
                                }
                                count = 1;
                                break;
                            }
                        }
                    }
                    if (count > 0) {
                        count--;
                        if (goods.getEveryGoodsEveryOrderBuyCount() <= goods.getSurplusDiscountStock()) {
                            if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (count <= goods.getEveryGoodsEveryOrderBuyCount()) {
                                    goods.setFirst(true);
                                }
                            } else {
                                if (count <= goods.getSurplusDiscountStock()) {
                                    goods.setFirst(true);
                                }
                            }
                        } else {
                            if (count <= goods.getSurplusDiscountStock()) {
                                goods.setFirst(true);
                            }
                        }
                        tvBuyCount.setText(count + "");
                        mGoodsSpec.setBuyCount(count);
                        if (count == 0) {
                            btnLayout.setVisibility(View.INVISIBLE);
                            btnConfirm.setVisibility(View.VISIBLE);
                            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                                productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), true, false);
                            } else {
                                String sName2 = "";
                                for (int i = 0; i < specName.size(); i++) {
                                    sName2 += specName.get(i) + ",";
                                }
                                String sName3 = "";
                                sName3 = sName2.substring(0, sName2.lastIndexOf(","));
                                newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), count, true, false, sName3);
                            }
                        } else {
                            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                                //只要点击了就去更新购物车
                                productHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), mGoodsSpec.getBuyCount(), false, false);
                            } else {
                                String sName4 = "";
                                for (int i = 0; i < specName.size(); i++) {
                                    sName4 += specName.get(i) + ",";
                                }
                                String sName5 = "";
                                sName5 = sName4.substring(0, sName4.lastIndexOf(","));
                                newProductHasChange(goods, goods.getCategoryId(), goods.getId(), mGoodsSpec.getId(), count, false, false, sName5);
                            }
                        }
                    }
                }
            }
        });

        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooseSpecDialog != null && chooseSpecDialog.isShowing())
                    chooseSpecDialog.dismiss();
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        chooseSpecDialog.setContentView(contentView, layoutParams);
        chooseSpecDialog.show();
        parent = createAnimLayout(chooseSpecDialog);
    }

    private ViewGroup createAnimLayout(Dialog dialog) {
        ViewGroup rootView = (ViewGroup) dialog.getWindow().getDecorView();
        if (rootView == null) return null;
        MLog.e("---->rootViewX:" + rootView.getMeasuredWidth() + "---->rootViewY:" + rootView.getMeasuredHeight());
        LinearLayout animLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout, rootView.getChildCount());
        return animLayout;
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
                if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() == 1) {
                    goodsSpec.setBuyCount(0);
                } else {
                    for (GoodsSpec spec : goods.getGoodsSpecList()) {
                        spec.setBuyCount(0);
                    }
                }
                PickGoodsModel.getInstance().setIsRemove(true);
                mCartProducts.clear();
                bottomAdapter.notifyDataSetChanged();
                setCart();
                mPopWindow.dismiss();

                linearCover.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.INVISIBLE);
                AnimatorUtils.rightTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, mContext));
                AnimatorUtils.rightTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, mContext));
                AnimatorUtils.rightTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, mContext));
                AnimatorUtils.rightTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, mContext));
                tvBuyCount.setText("0");
                specCount.setText("0");
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
                linearCover.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.INVISIBLE);
            }
        });

        PickGoodsModel.getInstance().getMerchantPickGoodsList();
        initCartProducts();
        if (CheckUtils.isNoEmptyList(mCartProducts)) {
            setCart();
        }
        relativeCenter = (RelativeLayout) view.findViewById(R.id.linear_center);
        bottomListView = (ListView) view.findViewById(R.id.commercial_cart_list_view);
        bottomAdapter = new BottomCartListAdapter(this, mCartProducts, this);
        bottomListView.setAdapter(bottomAdapter);
    }

    private void initCartProducts() {
        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        if (CheckUtils.isNoEmptyList(merchantPickGoodsList)) {
            boolean contain = false;
            for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                if (merchantPickGoods.getMerchantId() == goods.getMerchantId()) { //TODO modify 商家id 有可能出错= =
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

    private void setCart() {
        checkFullReduction(merchant);
        if (mCartProducts != null && mCartProducts.size() > 0) {
            //TODO 计算价格
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
     * * merchantId商家id
     * * goodsSpecId 商品规格id
     *
     * @param discountedGoods
     */
    private void getSurplusBuyNum(final DiscountedGoods discountedGoods, long goodsSpecId, final boolean isSetAnim) {
        if (discountedGoods != null && discountedGoods.getMaxBuyNum() != null && discountedGoods.getMaxBuyNum() > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("merchantId", goods.getMerchantId());
            map.put("goodsSpecId", goodsSpecId);
            VolleyOperater<SurPlusBuyNumModel> operater = new VolleyOperater<>(GoodsDetailActivity.this);
            operater.doRequest(Constants.URL_GOODS_SPECID_BUYNUM, map, new VolleyOperater.ResponseListener() {

                @Override
                public void onRsp(boolean isSucceed, Object obj) {
                    if (isSucceed && obj != null) {
                        SurPlusBuyNumModel surPlusBuyNumModel = (SurPlusBuyNumModel) obj;
                        if (surPlusBuyNumModel.getValue() != null) {
                            SurPlusBuyNumModel.ValueEntity valueEntity = surPlusBuyNumModel.getValue();
                            if (valueEntity.getBuyNum() != null) {
                                discountedGoods.setSurplusBuyNum(valueEntity.getBuyNum());
                            }
                        }
                        notifyCart(isSetAnim);
                    } else {
                        notifyCart(isSetAnim);
                    }
                }
            }, SurPlusBuyNumModel.class);
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
                            DiscountedGoods discountedGoods = pro.getGoods().getDiscountedGoods();
                            if (discountedGoods != null && discountedGoods.getMaxBuyNum() != null && discountedGoods.getMaxBuyNum() > 0 && discountedGoods.getSurplusBuyNum() != null && pro.getPickCount() > discountedGoods.getSurplusBuyNum()) {
                                multiply = goodsSpec.getPrice().multiply(new BigDecimal(discountedGoods.getSurplusBuyNum()));
                                decimal = goodsSpec.getOriginalPrice().multiply(new BigDecimal(pro.getPickCount() - discountedGoods.getSurplusBuyNum()));
                                num = num.add(multiply.add(decimal));
                            } else if (everyGoodsEveryOrderBuyCount >= surplusDiscountStock) {
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
            tv_num.setText(count + "");

            tv_allMoney.setText("¥" + StringUtils.BigDecimal2Str(num));
            if (num_package.compareTo(BigDecimal.ZERO) == 1) {
                tv_cart_package.setText("餐盒费¥" + StringUtils.BigDecimal2Str(num_package));
                if (tv_cart_shipping.getVisibility() == View.GONE) {
                    rlCartShipingAndBox.setVisibility(View.VISIBLE);
                }
                tv_cart_package.setVisibility(View.VISIBLE);
                tv_cart_shipping.setTextSize(10);
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
     * 设置页面数据
     *
     * @param goods
     */
    private void setPageData(Goods goods) {
        if (goods != null) {
            /**
             * 此处请求商品评价 成功再添加数据到适配器
             */
            tvTitle.setText(goods.getName());
            tv_goodsName.setText(goods.getName());
            rb_star.setRating(goods.getCommentScore().floatValue());
            tv_judge.setText(goods.getCommentNum() + "评价");
            tv_saleNumber.setText("月售" + goods.getMonthSaled() + "份");

            List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
            if (goodsSpecList != null && goodsSpecList.size() == 1) {
                BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
                tv_barPrice.setText(StringUtils.BigDecimal2Str(price));
                if (goodsSpecList.get(0).getOriginalPrice() != null && goodsSpecList.get(0).getOriginalPrice().compareTo(BigDecimal.ZERO) > 0) {
                    tvOriPri.setText("¥" + StringUtils.BigDecimal2Str(goodsSpecList.get(0).getOriginalPrice()));
                    tvOriPri.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvOriPri.setVisibility(View.GONE);
                }
                if (goods.getHasDiscount() == 1) {
                    if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                        tvLimit.setVisibility(View.GONE);
                        tvDiscount.setVisibility(View.VISIBLE);
                        BigDecimal b = new BigDecimal(goods.getDiscountedGoods().getDiscountProportion());
                        BigDecimal discount = b.divide(BigDecimal.TEN, 1, BigDecimal.ROUND_HALF_UP);
                        tvDiscount.setText(discount + "折  限购" + goods.getEveryGoodsEveryOrderBuyCount() + "份");
                    } else {
                        tvLimit.setVisibility(View.GONE);
                        tvDiscount.setVisibility(View.VISIBLE);
                        BigDecimal b = new BigDecimal(goods.getDiscountedGoods().getDiscountProportion());
                        BigDecimal discount = b.divide(BigDecimal.TEN, 1, BigDecimal.ROUND_HALF_UP);
                        tvDiscount.setText(discount + "折");
                    }
                } else {
                    tvLimit.setVisibility(View.GONE);
                }
                if (goods.getHasDiscount() == 0 && goodsSpecList != null && goodsSpecList.get(0).getOrderLimit() != null && goodsSpecList.get(0).getOrderLimit() > 0) {
                    tvLimit.setVisibility(View.VISIBLE);
                    tvLimit.setText("每单限购" + goodsSpecList.get(0).getOrderLimit() + "份");
                }

                if (goodsSpecList != null && goodsSpecList.get(0).getMinOrderNum() != null && goodsSpecList.get(0).getMinOrderNum() > 1) {
                    if (goods.getHasDiscount() == 1) {
                        tvMin.setVisibility(View.GONE);
                    } else {
                        tvMin.setVisibility(View.VISIBLE);
                    }
                    tvMin.setText(goodsSpecList.get(0).getMinOrderNum() + "份起购");
                } else {
                    tvMin.setVisibility(View.GONE);
                }
            } else if (goodsSpecList != null && goodsSpecList.size() > 1) {
                BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
                for (int i = 1; i < goods.getGoodsSpecList().size(); i++) {
                    if (price.compareTo(goods.getGoodsSpecList().get(i).getPrice()) == 1) {
                        price = goods.getGoodsSpecList().get(i).getPrice();
                    }
                }
                String str = StringUtils.BigDecimal2Str(price) + "起";
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                style.setSpan(new TextAppearanceSpan(null, 0, getResources().getDimensionPixelSize(R.dimen.mine_list_text_size), null, null), str.length() - 1, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                tv_barPrice.setText(style);
            }
            if (CheckUtils.isNoEmptyStr(goods.getImgs())) {
                String[] strings = goods.getImgs().split(";");
                List<String> list = Arrays.asList(strings);
                detailBanner.setUrls(list, false, false);
                if (mRect != null) detailBanner.setNeedPlaceHolder(false);
                if (list.size() == 0) {
                    ArrayList<Integer> list1 = new ArrayList<>();
                    list1.add(R.drawable.horsegj_default);
                    detailBanner.setResources(list1);
                }
            } else {
                ArrayList<Integer> list1 = new ArrayList<>();
                list1.add(R.drawable.horsegj_default);
                detailBanner.setResources(list1);
            }
            if (goodsSpecList != null && goodsSpecList.get(0).getStockType() == 1) {
                Integer remNum = goodsSpecList.get(0).getStock();
                if (remNum > 0 && remNum <= 9) {
                    if (goods.getHasDiscount() == 1) {
                        tvRemNum.setVisibility(View.GONE);
                    }
                    tvRemNum.setText("仅剩" + remNum + "件");
                } else {
                    tvRemNum.setVisibility(View.GONE);
                }

            } else {
                tvRemNum.setVisibility(View.GONE);
            }
        }
    }

    private void initViews() {
        imgBack.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgShare.setVisibility(View.VISIBLE);
        bottomCart.setOnClickListener(this);
        act_add.setOnClickListener(this);
        imgMinus.setOnClickListener(this);
        linearCover.setOnClickListener(this);
        tv_goAccount.setOnClickListener(this);
        specMinus.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
        llFullSubtract.setOnClickListener(this);

        detailBanner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mFragments = new ArrayList<>();
        GoodsDetailFragment goodsDetailFragment = new GoodsDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("goods", goods);
        goodsDetailFragment.setArguments(arguments);
        mFragments.add(goodsDetailFragment);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
        mHeaderViewPagerLayout.setCurrentScrollableContainer(mFragments.get(0));
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mHeaderViewPagerLayout.setCurrentScrollableContainer(mFragments.get(position));
            }
        });
        mHeaderViewPagerLayout.setOnScrollListener(new HeaderViewPagerLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                topBar.setAlpha(currentY * 1.0f / maxY);
                if (currentY + DipToPx.dip2px(mActivity, 48) >= maxY) {
                    topBar.setVisibility(View.VISIBLE);
                    ivBack.setVisibility(View.GONE);
                    ivShare.setVisibility(View.GONE);
                } else {
                    topBar.setVisibility(View.GONE);
                    ivBack.setVisibility(View.VISIBLE);
                    ivShare.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commercial_act_bottom:
                mPopWindow.dismiss();
                linearCover.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.INVISIBLE);
                break;
            case R.id.goods_detail_act_back:
            case R.id.goods_detail_act_back_default:
                onBackPressed();
                break;
            case R.id.goods_detail_act_iv_share:
            case R.id.goods_detail_act_share_default:
                if (shareUtil == null && goods != null) {
                    shareUtil = new ShareUtil(mActivity, goods.getName(),
                            CheckUtils.isNoEmptyStr(goods.getDescription()) ? goods.getDescription() : "独乐不如众乐，分享好东西给你，快上马管家抢购吧~",
                            goods.getShareUrl(), goods.getImgs());
                }
                if (shareUtil != null) shareUtil.showPopupWindow();
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
                        checkFullReduction(merchant);
                        mPopWindow.showAtLocation(bottomCart, Gravity.BOTTOM, 0, measuredHeight);
                        linearCover.setVisibility(View.VISIBLE);
                        overlay.setVisibility(View.VISIBLE);
                        if (isFullSub && canDisplay) {
                            pLayoutFullSub.setVisibility(View.VISIBLE);
                        } else {
                            pLayoutFullSub.setVisibility(View.GONE);
                        }
                        AnimatorUtils.showBottom(relativeCenter, this);
                        AnimatorUtils.showBottom(bottomListView, this);
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
            case R.id.goods_item_img_add:
            case R.id.goods_item_img_add1:
                int count = goodsSpec.getBuyCount();
                int maxCount = goodsSpec.getOrderLimit();
                int minBuyCount = 0;
//                PickGoodsModel.getInstance().setIsRemove(false);
                if (goods.getHasDiscount() == 1) {
                    popUp();
                    if (goods.getEveryGoodsEveryOrderBuyCount() > goods.getSurplusDiscountStock()) {
                        if (count == goods.getSurplusDiscountStock()) {
                            if (goodsSpec.getMinOrderNum() > 0) {
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                    minBuyCount = goodsSpec.getMinOrderNum() > goodsSpec.getStock() ? goodsSpec.getStock() : goodsSpec.getMinOrderNum();
                                } else {
                                    minBuyCount = goodsSpec.getMinOrderNum();
                                }
                                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                    if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && (count - goods.getSurplusDiscountStock()) <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                        ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", mContext);
                                        count = count + minBuyCount - 1;
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                            if (count == goods.getEveryGoodsEveryOrderBuyCount()) {
                                if (goodsSpec.getMinOrderNum() > 0) {
                                    if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                        minBuyCount = goodsSpec.getMinOrderNum() > goodsSpec.getStock() ? goodsSpec.getStock() : goodsSpec.getMinOrderNum();
                                    } else {
                                        minBuyCount = goodsSpec.getMinOrderNum();
                                    }
                                    for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                        if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && (count - goods.getEveryGoodsEveryOrderBuyCount()) <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                            ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", mContext);
                                            count = count + minBuyCount - 1;
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (count == goods.getSurplusDiscountStock()) {
                                if (goodsSpec.getMinOrderNum() > 0) {
                                    if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                        minBuyCount = goodsSpec.getMinOrderNum() > goodsSpec.getStock() ? goodsSpec.getStock() : goodsSpec.getMinOrderNum();
                                    } else {
                                        minBuyCount = goodsSpec.getMinOrderNum();
                                    }
                                    for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                                        if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && (count - goods.getSurplusDiscountStock()) <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                            ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", mContext);
                                            count = count + minBuyCount - 1;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                    }
                } else {
                    if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && count >= goodsSpec.getStock()) {
                        ToastUtils.displayMsg("该商品库存不足", mContext);
                        return;
                    }
                    if (count == 0) {
                        for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                            if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", mActivity);
                                count = goods.getGoodsSpecList().get(i).getMinOrderNum() - 1;
                                break;
                            }
                        }
                    }
                    if (maxCount != 0 && count >= maxCount) {
                        ToastUtils.displayMsg(goods.getName() + "商品限购" + goodsSpec.getOrderLimit() + "份", mContext);
                        return;
                    }
                }

                if (count == 0) {
                    count++;
                    if (goods.getHasDiscount() == 1) {
                        if (goods.getEveryGoodsEveryOrderBuyCount() > goods.getSurplusDiscountStock()) {
                            if (count >= goods.getSurplusDiscountStock()) {
                                if (goods.isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", mActivity);
                                    goods.setFirst(false);
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                    if (count - goods.getSurplusDiscountStock() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                        return;
                                    }
                                } else {
                                    if (count - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                        return;
                                    }
                                    if (goodsSpec.getOrderLimit() == 0 && count - goods.getSurplusDiscountStock() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                        return;
                                    }
                                }
                            } else {
                                if (count - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                    ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                    return;
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && (count - goods.getSurplusDiscountStock()) > goodsSpec.getStock()) {
                                ToastUtils.displayMsg("该商品库存不足", mContext);
                                return;
                            }
                        } else {
                            DiscountedGoods discountedGoods = goods.getDiscountedGoods();
                            if (discountedGoods != null && discountedGoods.getMaxBuyNum() != null && discountedGoods.getMaxBuyNum() > 0 && discountedGoods.getSurplusBuyNum() != null && count == discountedGoods.getSurplusBuyNum() + 1) {
                                ToastUtils.displayMsg("当前折扣商品每个用户限购" + discountedGoods.getMaxBuyNum() + "件，超出部分需原价购买。", mActivity);
                            } else if (count == goods.getEveryGoodsEveryOrderBuyCount() && goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (goods.isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品每单限购" + goods.getEveryGoodsEveryOrderBuyCount() + "件，超出部分需原价购买。", mActivity);
                                    goods.setFirst(false);
                                }
                            }
                            if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                    if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                        if (count - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                            return;
                                        }
                                    } else {
                                        if (count - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                            ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                            return;
                                        }
                                        if (goodsSpec.getOrderLimit() == 0 && count - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                            return;
                                        }
                                    }
                                } else {
                                    if (count - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                        return;
                                    }
                                }
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && (count - goods.getEveryGoodsEveryOrderBuyCount()) > goodsSpec.getStock()) {
                                    ToastUtils.displayMsg("该商品库存不足", mContext);
                                    return;
                                }
                            } else {
                                if (count >= goods.getSurplusDiscountStock()) {
                                    if (goods.isFirst()) {
                                        ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", mContext);
                                        goods.setFirst(false);
                                    }
                                }
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                    if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                        if (count - goods.getSurplusDiscountStock() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                            return;
                                        }
                                    } else {
                                        if (count - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                            ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                            return;
                                        }
                                        if (goodsSpec.getOrderLimit() == 0 && count - goods.getSurplusDiscountStock() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                            return;
                                        }
                                    }
                                } else {
                                    if (count - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                        return;
                                    }
                                }
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && (count - goods.getSurplusDiscountStock()) > goodsSpec.getStock()) {
                                    ToastUtils.displayMsg("该商品库存不足", mContext);
                                    return;
                                }
                            }
                        }
                    }
                    goodsSpec.setBuyCount(count);
                    tvBuyCount.setText(count + "");
                    AnimatorUtils.leftTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.leftTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                } else {
                    count++;
                    if (goods.getHasDiscount() == 1) {
                        if (goods.getEveryGoodsEveryOrderBuyCount() > goods.getSurplusDiscountStock()) {
                            if (count >= goods.getSurplusDiscountStock()) {
                                if (goods.isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", mActivity);
                                    goods.setFirst(false);
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                    if (count - goods.getSurplusDiscountStock() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                        return;
                                    }
                                } else {
                                    if (count - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                        return;
                                    }
                                    if (goodsSpec.getOrderLimit() == 0 && count - goods.getSurplusDiscountStock() > goodsSpec.getStock()) {
                                        ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                        return;
                                    }
                                }
                            } else {
                                if (count - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                    ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                    return;
                                }
                            }
                            if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && (count - goods.getSurplusDiscountStock()) > goodsSpec.getStock()) {
                                ToastUtils.displayMsg("该商品库存不足", mContext);
                                return;
                            }
                        } else {
                            DiscountedGoods discountedGoods = goods.getDiscountedGoods();
                            if (discountedGoods != null && discountedGoods.getMaxBuyNum() != null && discountedGoods.getMaxBuyNum() > 0 && discountedGoods.getSurplusBuyNum() != null && count == discountedGoods.getSurplusBuyNum() + 1) {
                                ToastUtils.displayMsg("当前折扣商品每个用户限购" + discountedGoods.getMaxBuyNum() + "件，超出部分需原价购买。", mActivity);
                            } else if (count == goods.getEveryGoodsEveryOrderBuyCount() && goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (goods.isFirst()) {
                                    ToastUtils.displayMsg("当前折扣商品每单限购" + goods.getEveryGoodsEveryOrderBuyCount() + "件，超出部分需原价购买。", mActivity);
                                    goods.setFirst(false);
                                }
                            }
                            if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                    if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                        if (count - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                            return;
                                        }
                                    } else {
                                        if (count - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                            ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                            return;
                                        }
                                        if (goodsSpec.getOrderLimit() == 0 && count - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                            return;
                                        }
                                    }
                                } else {
                                    if (count - goods.getEveryGoodsEveryOrderBuyCount() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                        return;
                                    }
                                }
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && (count - goods.getEveryGoodsEveryOrderBuyCount()) > goodsSpec.getStock()) {
                                    ToastUtils.displayMsg("该商品库存不足", mContext);
                                    return;
                                }
                            } else {
                                if (count >= goods.getSurplusDiscountStock()) {
                                    if (goods.isFirst()) {
                                        ToastUtils.displayMsg("当前折扣商品库存不足，其余部分需原价购买", mContext);
                                        goods.setFirst(false);
                                    }

                                }
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null) {
                                    if (goodsSpec.getOrderLimit() > goodsSpec.getStock()) {
                                        if (count - goods.getSurplusDiscountStock() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                            return;
                                        }
                                    } else {
                                        if (count - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                            ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                            return;
                                        }
                                        if (goodsSpec.getOrderLimit() == 0 && count - goods.getSurplusDiscountStock() > goodsSpec.getStock()) {
                                            ToastUtils.displayMsg("您购买的商品库存不足", mContext);
                                            return;
                                        }
                                    }
                                } else {
                                    if (count - goods.getSurplusDiscountStock() > goodsSpec.getOrderLimit() && goodsSpec.getOrderLimit() > 0) {
                                        ToastUtils.displayMsg("您购买的商品已超过限购数量", mContext);
                                        return;
                                    }
                                }
                                if (goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && (count - goods.getSurplusDiscountStock()) > goodsSpec.getStock()) {
                                    ToastUtils.displayMsg("该商品库存不足", mContext);
                                    return;
                                }
                            }
                        }
                    }
                    tvBuyCount.setText(count + "");
                    goodsSpec.setBuyCount(count);
                    AnimatorUtils.leftTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.leftTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                }
                //只要点击了就去更新购物车
                productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, true);
                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                ImageView ball = new ImageView(mContext);// buyImg是动画的图片
                ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                setAnim(ball, startLocation, null);// 开始执行动画
                break;
            case R.id.goods_item_img_minus:
            case R.id.goods_item_img_minus1:
                int count1 = goodsSpec.getBuyCount();
                if (goods.getHasDiscount() == 0) {
                    for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                        if (count1 == goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                            if (goods.getGoodsSpecList().get(i).getMinOrderNum() != 0 && count1 <= goods.getGoodsSpecList().get(i).getMinOrderNum()) {
                                ToastUtils.displayMsg(goods.getName() + "商品最少购买" + goodsSpec.getMinOrderNum() + "份", mActivity);
                            }
                            count1 = 1;
                            break;
                        }
                    }
                }
                if (count1 == 1) {
                    if (goods.getEveryGoodsEveryOrderBuyCount() <= goods.getSurplusDiscountStock()) {
                        if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                            if (count1 <= goods.getEveryGoodsEveryOrderBuyCount()) {
                                goods.setFirst(true);
                            }
                        } else {
                            if (count1 <= goods.getSurplusDiscountStock()) {
                                goods.setFirst(true);
                            }
                        }
                    } else {
                        if (count1 <= goods.getSurplusDiscountStock()) {
                            goods.setFirst(true);
                        }
                    }
                    count1--;
                    goodsSpec.setBuyCount(count1);
                    tvBuyCount.setText(count1 + "");
                    AnimatorUtils.rightTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.rightTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                    //只要点击了就去更新购物车
                    productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), true, false);
                } else {
                    if (count1 > 0) {
                        if (goods.getEveryGoodsEveryOrderBuyCount() <= goods.getSurplusDiscountStock()) {
                            if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                if (count1 <= goods.getEveryGoodsEveryOrderBuyCount()) {
                                    goods.setFirst(true);
                                }
                            } else {
                                if (count1 <= goods.getSurplusDiscountStock()) {
                                    goods.setFirst(true);
                                }
                            }
                        } else {
                            if (count1 <= goods.getSurplusDiscountStock()) {
                                goods.setFirst(true);
                            }
                        }
                        if (goods.getHasDiscount() == 1) {
                            if (goods.getEveryGoodsEveryOrderBuyCount() > goods.getSurplusDiscountStock()) {
                                if (goodsSpec.getMinOrderNum() > 0 && count1 - goods.getSurplusDiscountStock() == goodsSpec.getMinOrderNum()) {
                                    count1 = count1 - goodsSpec.getMinOrderNum() + 1;
                                }
                            } else {
                                if (goodsSpec.getMinOrderNum() > 0 && goods.getEveryGoodsEveryOrderBuyCount() > 0 && count1 - goods.getEveryGoodsEveryOrderBuyCount() == goodsSpec.getMinOrderNum()) {
                                    count1 = count1 - goodsSpec.getMinOrderNum() + 1;
                                }
                            }
                        }
                        count1--;
                        tvBuyCount.setText(count1 + "");
                        goodsSpec.setBuyCount(count1);
                        //只要点击了就去更新购物车
                        productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                    }
                }
                break;
            case R.id.goods_item_img_minus_spec:
            case R.id.goods_item_img_minus_spec1:
                if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                    GoodsSpec goodsSpec1 = null;
                    int num = 0;
                    int specNum = 0;
                    for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                        GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
                        List<PickGoods> pickGoodsList = mCartProducts;
                        if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                            for (PickGoods pickGoods : pickGoodsList) {
                                if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                    num += pickGoods.getPickCount();
                                    specNum += 1;
                                    goodsSpec1 = goodsSpec;
                                    break;
                                }
                            }
                        } else {
                            for (PickGoods pickGoods : pickGoodsList) {
                                if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                    num += pickGoods.getPickCount();
                                    specNum += 1;
                                    goodsSpec1 = goodsSpec;
                                }
                            }
                        }
                    }
                    if (specNum > 1) {
                        if (dialog == null) {
                            dialog = new CustomDialog(mActivity, onBtnClickListener, "确定", "", "提示", "多种规格，请去购物车里删减");
                            dialog.show();
                        } else {
                            dialog.show();
                        }
                    } else {
                        if (num == 1) {
                            if (goods.getHasDiscount() == 1) {
                                if (goods.getEveryGoodsEveryOrderBuyCount() <= goods.getSurplusDiscountStock()) {
                                    if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                        if (num <= goods.getEveryGoodsEveryOrderBuyCount()) {
                                            goods.setFirst(true);
                                        }
                                    } else {
                                        if (num <= goods.getSurplusDiscountStock()) {
                                            goods.setFirst(true);
                                        }
                                    }
                                } else {
                                    if (num <= goods.getSurplusDiscountStock()) {
                                        goods.setFirst(true);
                                    }
                                }
                            }
                            num--;
                            tvBuyCount.setText(num + "");
                            goodsSpec1.setBuyCount(num);
                            AnimatorUtils.rightTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, mActivity));
                            AnimatorUtils.rightTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, mActivity));
                            //只要点击了就去更新购物车
                            productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), true, false);
                        } else {
                            if (num > 0) {
                                if (goods.getHasDiscount() == 1) {
                                    if (goods.getEveryGoodsEveryOrderBuyCount() <= goods.getSurplusDiscountStock()) {
                                        if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                            if (num <= goods.getEveryGoodsEveryOrderBuyCount()) {
                                                goods.setFirst(true);
                                            }
                                        } else {
                                            if (num <= goods.getSurplusDiscountStock()) {
                                                goods.setFirst(true);
                                            }
                                        }
                                    } else {
                                        if (num <= goods.getSurplusDiscountStock()) {
                                            goods.setFirst(true);
                                        }
                                    }
                                    if (goods.getEveryGoodsEveryOrderBuyCount() > goods.getSurplusDiscountStock()) {
                                        if (goodsSpec1.getMinOrderNum() > 0 && num - goods.getSurplusDiscountStock() == goodsSpec1.getMinOrderNum()) {
                                            num = num - goodsSpec1.getMinOrderNum() + 1;
                                        }
                                    } else {
                                        if (goodsSpec1.getMinOrderNum() > 0 && goods.getEveryGoodsEveryOrderBuyCount() > 0 && num - goods.getEveryGoodsEveryOrderBuyCount() == goodsSpec1.getMinOrderNum()) {
                                            num = num - goodsSpec1.getMinOrderNum() + 1;
                                        }
                                    }

                                }
                                num--;
                                goodsSpec1.setBuyCount(num);
                                productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                            }
                        }

                    }
                } else {
                    GoodsSpec goodsSpec1 = null;
                    int num = 0;
                    int specNum = 0;
                    for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                        GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
                        List<PickGoods> pickGoodsList = mCartProducts;
                        if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                            for (PickGoods pickGoods : pickGoodsList) {
                                if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                    num += pickGoods.getPickCount();
                                    specNum += 1;
                                    goodsSpec1 = goodsSpec;
                                }
                            }
                        } else {
                            for (PickGoods pickGoods : pickGoodsList) {
                                if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                    num += pickGoods.getPickCount();
                                    specNum += 1;
                                    goodsSpec1 = goodsSpec;
                                }
                            }
                        }
                    }
                    if (specNum > 1) {
                        if (dialog == null) {
                            dialog = new CustomDialog(mActivity, onBtnClickListener, "确定", "", "提示", "多种规格，请去购物车里删减");
                            dialog.show();
                        } else {
                            dialog.show();
                        }
                    } else {
                        if (goods.getHasDiscount() == 0) {
                            num = num == goodsSpec1.getMinOrderNum() ? 1 : num;
                        }
                        if (num == 1) {
                            if (goods.getHasDiscount() == 1) {
                                if (goods.getEveryGoodsEveryOrderBuyCount() <= goods.getSurplusDiscountStock()) {
                                    if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                        if (num <= goods.getEveryGoodsEveryOrderBuyCount()) {
                                            goods.setFirst(true);
                                        }
                                    } else {
                                        if (num <= goods.getSurplusDiscountStock()) {
                                            goods.setFirst(true);
                                        }
                                    }
                                } else {
                                    if (num <= goods.getSurplusDiscountStock()) {
                                        goods.setFirst(true);
                                    }
                                }
                            }
                            num--;
                            tvBuyCount.setText(num + "");
                            goodsSpec1.setBuyCount(num);
                            AnimatorUtils.rightTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, mActivity));
                            AnimatorUtils.rightTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, mActivity));
                            //只要点击了就去更新购物车
                            productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), true, false);
                        } else {
                            if (num > 0) {
                                if (goods.getHasDiscount() == 1) {
                                    if (goods.getEveryGoodsEveryOrderBuyCount() <= goods.getSurplusDiscountStock()) {
                                        if (goods.getEveryGoodsEveryOrderBuyCount() > 0) {
                                            if (num <= goods.getEveryGoodsEveryOrderBuyCount()) {
                                                goods.setFirst(true);
                                            }
                                        } else {
                                            if (num <= goods.getSurplusDiscountStock()) {
                                                goods.setFirst(true);
                                            }
                                        }
                                    } else {
                                        if (num <= goods.getSurplusDiscountStock()) {
                                            goods.setFirst(true);
                                        }
                                    }
                                    if (goods.getEveryGoodsEveryOrderBuyCount() > goods.getSurplusDiscountStock()) {
                                        if (goodsSpec1.getMinOrderNum() > 0 && num - goods.getSurplusDiscountStock() == goodsSpec1.getMinOrderNum()) {
                                            num = num - goodsSpec1.getMinOrderNum() + 1;
                                        }
                                    } else {
                                        if (goodsSpec1.getMinOrderNum() > 0 && goods.getEveryGoodsEveryOrderBuyCount() > 0 && num - goods.getEveryGoodsEveryOrderBuyCount() == goodsSpec1.getMinOrderNum()) {
                                            num = num - goodsSpec1.getMinOrderNum() + 1;
                                        }
                                    }
                                }
                                num--;
                                goodsSpec1.setBuyCount(num);
                                productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                            }
                        }

                    }
                }

                break;
            case R.id.commercial_act_go_account:
                if (!App.isLogin()) {
                    Intent intent1 = new Intent(mActivity, SmsLoginActivity.class);
                    startActivity(intent1);
                    return;
                }
                getOrderPreview();
                break;
            case R.id.ll_layout_full_subtract:
                llFullSubtract.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
                visible = true;
                getCouDanData();
                break;
            /*case R.id.goods_detail_to_previous:
                AutoScrollViewPager vp = detailBanner.getViewPager();
                vp.setCurrentItem(vp.getCurrentItem() - 1);
                break;
            case R.id.goods_detail_to_next:
                AutoScrollViewPager vp1 = detailBanner.getViewPager();
                vp1.setCurrentItem(vp1.getCurrentItem() + 1);
                break;*/
            default:
                break;
        }

    }

    CustomDialog.onBtnClickListener onBtnClickListener = new CustomDialog.onBtnClickListener() {
        @Override
        public void onSure() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onExit() {

        }
    };

    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
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
     *
     * @param v
     * @param startLocation
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
        final View view = addViewToAnimLayout(anim_mask_layout, v,
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

    /**
     * 购买商品回调接口
     *
     * @param goods
     */
    @Override
    public void productHasChange(Goods goods, long categoryId, long goodsId, long goodsSpecId, int pickCount, boolean isRemove, boolean isSetAnim) {
        PickGoodsModel.getInstance().setIsRemove(isRemove);
        if (isRemove) {
            for (PickGoods pickGoods : mCartProducts) {
                if (pickGoods.getGoodsId() == goodsId && pickGoods.getGoodsSpecId() == goodsSpecId) {
                    pickGoods.setPickCount(0);
                    mCartProducts.remove(pickGoods);
                    break;
                }
            }
            checkCart();

            if (this.goods.getGoodsSpecList().size() > 1)
                for (int i = 0; i < this.goods.getGoodsSpecList().size(); i++) {
                    GoodsSpec goodsSpec = this.goods.getGoodsSpecList().get(i);
                    if (goodsSpec.getId() == goodsSpecId) {
                        goodsSpec.setBuyCount(0);
                        break;
                    }
                }

        } else {
            //如果改变的商品已经存在，那么就改变商品的数目，否则添加进购物车
            boolean mCartProductsContain = false;
            for (PickGoods pickGoods : mCartProducts) {
                if (pickGoods.getGoodsId() == goodsId && pickGoods.getGoodsSpecId() == goodsSpecId) {
                    pickGoods.setGoods(goods);
                    pickGoods.setPickCount(pickCount);
                    mCartProductsContain = true;
                    break;
                }
            }
            if (!mCartProductsContain) {
                PickGoods pickGoods = new PickGoods();
                pickGoods.setPickCount(pickCount);
                pickGoods.setGoods(goods);
                pickGoods.setGoodsId(goodsId);
                pickGoods.setGoodsSpecId(goodsSpecId);
                pickGoods.setMenuId(categoryId);
                mCartProducts.add(pickGoods);
            }
        }
        getSurplusBuyNum(goods.getDiscountedGoods(), goodsSpecId, isSetAnim);
        if (currentType == 1) {
            if (goodsSpec.getGoodsId() == goodsId && goodsSpec.getId() == goodsSpecId) {
                tvBuyCount.setText(pickCount + "");
                goodsSpec.setBuyCount(pickCount);
                if (pickCount == 0) {
                    AnimatorUtils.rightTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.rightTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                }
            }
        } else if (currentType == 2) {
            int num = 0;
            for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                GoodsSpec goodsSpec1 = goods.getGoodsSpecList().get(i);
                List<PickGoods> pickGoodsList = mCartProducts;
                for (PickGoods pickGoods : pickGoodsList) {
                    if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec1.getId()) {
                        num += pickGoods.getPickCount();
                        break;
                    }
                }
            }
            if (num == 0) {
                AnimatorUtils.rightTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, this));
                AnimatorUtils.rightTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, this));
            } else if ("0".equals(specCount.getText().toString())) {
                AnimatorUtils.leftTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, this));
                AnimatorUtils.leftTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, this));
            }
            specCount.setText(num + "");
        }
    }

    private void notifyCart(boolean isSetAnim) {
        //刷新PopWindow
        bottomAdapter.setData(mCartProducts);
        if (mCartProducts.size() >= 4) {
            bottomListView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.x60));
        } else {
            bottomListView.setPadding(0, 0, 0, 0);
        }

        if (!isSetAnim) {
            setCart();
        }
        savePickGoodsInfo();
    }

    @Override
    public void newProductHasChange(Goods goods, long categoryId, long goodsId, long goodsSpecId, int pickCount, boolean isRemove, boolean isSetAnim, String goodsName) {
        if (isRemove) {
            for (PickGoods pickGoods : mCartProducts) {
                if (pickGoods.getGoodsId() == goodsId && pickGoods.getGoodsSpecId() == goodsSpecId && pickGoods.getGoodsName().equals(goodsName)) {
                    pickGoods.setPickCount(0);
                    pickGoods.setGoodsName(goodsName);
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
                mCartProducts.add(pickGoods);
            }
        }
        getSurplusBuyNum(goods.getDiscountedGoods(), goodsSpecId, isSetAnim);
        if (currentType == 1) {
            if (goodsSpec.getGoodsId() == goodsId && goodsSpec.getId() == goodsSpecId) {
                tvBuyCount.setText(pickCount + "");
                goodsSpec.setBuyCount(pickCount);
                if (pickCount == 0) {
                    AnimatorUtils.rightTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.rightTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                }
            }
        } else if (currentType == 2) {
            int num = 0;
            if (CheckUtils.isEmptyList(goods.getGoodsAttributeList())) {
                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                    GoodsSpec goodsSpec1 = goods.getGoodsSpecList().get(i);
                    List<PickGoods> pickGoodsList = mCartProducts;
                    for (PickGoods pickGoods : pickGoodsList) {
                        if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec1.getId()) {
                            num += pickGoods.getPickCount();
                            break;
                        }
                    }
                }
            } else {
                List<PickGoods> pickGoodsList = mCartProducts;
                for (PickGoods pickGood : pickGoodsList) {
                    if (pickGood.getGoodsId() == goods.getId()) {
                        num += pickGood.getPickCount();
                    }
                }
            }
            if (num == 0) {
                AnimatorUtils.rightTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, this));
                AnimatorUtils.rightTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, this));
            } else if (num == 1 && !"2".equals(specCount.getText().toString())) {
                AnimatorUtils.leftTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, this));
                AnimatorUtils.leftTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, this));
            } else {
                for (GoodsSpec spec : goods.getGoodsSpecList()) {
                    if (spec.getId() == goodsSpecId && spec.getMinOrderNum() != null && spec.getMinOrderNum() > 0 && num == spec.getMinOrderNum()) {
                        AnimatorUtils.leftTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, this));
                        AnimatorUtils.leftTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, this));
                    }
                }
            }
            specCount.setText(num + "");
        }
    }

    private void savePickGoodsInfo() {
        if (CheckUtils.isNoEmptyList(mCartProducts)) {
            List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
            PickGoodsModel.getInstance().setHasChange(true);
            if (merchantPickGoodsList != null) {
                boolean contain = false;
                for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                    if (merchantPickGoods.getMerchantId() == goods.getMerchantId()) {
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
                    merchantPickGoods.setMerchantId(goods.getMerchantId());
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
                merchantPickGoods.setMerchantId(goods.getMerchantId());
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
        }
    }


    /**
     * 订单预览
     */
    private void getOrderPreview() {
        if (isRelationClassify()) {
            return;
        }
        mLoadingDialog.show(getFragmentManager(), "");
        List<MerchantPickGoods> merchantPickGoodses = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", goods.getMerchantId());
        if (App.isLogin()) {
            map.put("loginToken", App.getUserInfo().getToken());
            map.put("userId", App.getUserInfo().getId());
        }
        ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
        for (MerchantPickGoods merchantPickGoods : merchantPickGoodses) {
            long mId = merchantPickGoods.getMerchantId();
            if (mId == goods.getMerchantId()) {
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
        latitude = Double.parseDouble(PreferenceUtils.getLocation(this)[0]);
        longitude = Double.parseDouble(PreferenceUtils.getLocation(this)[1]);
        Map<String, Object> params = new HashMap<>();
        params.put("data", previewJsonData.toString());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        VolleyOperater<ConfirmOrderModel> operater = new VolleyOperater<>(GoodsDetailActivity.this);
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
                            mLoadingDialog.dismiss();
                            Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
                            intent.putExtra("confirmOrderModel", confirmOrderModel);
                            intent.putExtra("onceMoreOrder", previewJsonData);
                            startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
                            finish();
                        } else {
                            ToastUtils.displayMsg("商家无法下单，请联系商家", mActivity);
                        }
                    }
                }
                mLoadingDialog.dismiss();
            }
        }, ConfirmOrderModel.class);
    }


    /**
     * 分类必选关联判断
     *
     * @return
     */
    private boolean isRelationClassify() {
        boolean isShow = false;
        List<Menu> goodsList = new ArrayList<>();
        List<Menu> menuList = merchantTakeAwayMenu.getMenu();
        if (CheckUtils.isNoEmptyList(menuList)) {
            for (Menu menu : menuList) {
                if (menu != null && menu.getRelationCategoryId() > 0) {
                    goodsList.add(menu);
                }
            }
        }
        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
            if (merchantPickGoods.getMerchantId() != goods.getMerchantId()) {
                continue;
            }
            List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
            HashMap<Object, Object> hashMap = new HashMap<>();
            for (PickGoods pickGood : pickGoods) {
                for (Menu menu : goodsList) {
                    if (pickGood.getMenuId() == menu.getId()) {
                        hashMap.put(pickGood.getMenuId(), menu);
                    }
                }
            }

            if (hashMap.size() > 0) {
                for (Object menu : hashMap.values()) {
                    Menu relationMenu = (Menu) menu;
                    if (relationMenu != null) {
                        if (!isHaveRelation(relationMenu.getRelationCategoryId(), pickGoods)) {
                            for (int i = 0; i < menuList.size(); i++) {
                                Menu menu1 = menuList.get(i);
                                if (menu1 != null) {
                                    if (menu1.getId() != null && relationMenu.getRelationCategoryId() == menu1.getId()) {
                                        isShow = true;
                                        showMandatoryDialog(relationMenu);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return isShow;
    }


    private boolean isHaveRelation(long id, List<PickGoods> pickGoodsList) {
        boolean isHave = false;
        if (CheckUtils.isNoEmptyList(pickGoodsList)) {
            for (PickGoods pickGood : pickGoodsList) {
                if (id == pickGood.getMenuId()) {
                    isHave = true;
                    break;
                }
            }
        }
        return isHave;

    }


    private void showMandatoryDialog(final Menu menu) {
        noticeDialog = new NoticeDialog(mActivity, new NoticeDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                noticeDialog.dismiss();
                if (menu.getId() != null) {
                    Intent intent = new Intent();
                    intent.putExtra("menuId", menu.getRelationCategoryId());
                    setResult(ActRequestCode.SPECIFY_MENU, intent);
                    finish();
                }
            }
        }, "", "请选择[" + menu.getAssociatedName() + "（必选）]下的商品才\n可以下单哦~", "好的");
        noticeDialog.show();
    }

    /**
     * 查询商家详情
     *
     * @param merchantId
     */
    private void getMerchant(long merchantId) {
        mLoadingDialog.show(getFragmentManager(), "");
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
                        MerchantEvaluateTopModel merchantEvaluateTopModel = (MerchantEvaluateTopModel) obj;
                        merchant = merchantEvaluateTopModel.getValue().getMerchant();
                        Date serviceTime = merchantEvaluateTopModel.getServertime();
                        if (serviceTime != null) {
                            String currentTime = CommonUtils.formatTime(serviceTime.getTime(), "HH:mm");
                            if (!DateUtils.isBusinessTime(currentTime, merchant.getWorkingTime())) {
                                merchant.setShoppingTime(false);
                            } else {
                                merchant.setShoppingTime(true);
                            }
                        }
                        setData(merchant);
                        checkFullReduction(merchant);
                        initPopWindow();
                        setGoodsData();
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
            }
        }, MerchantEvaluateTopModel.class);
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", goods.getMerchantId());
        VolleyOperater<GoodsListModel> operater = new VolleyOperater<>(GoodsDetailActivity.this);
        operater.doRequest(Constants.URL_SHOW_MERCHANT_TAKE_AWAY_CATEGORY, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    merchantTakeAwayMenu = ((GoodsListModel) obj).getValue();
                    merchantTakeAwayMenu.setServertime(((GoodsListModel) obj).getServertime());
                }
            }
        }, GoodsListModel.class);
    }

    public void setData(Merchant data) {
        if (merchant.getShipFee().compareTo(BigDecimal.ZERO) == 1) {
            String shipFee = StringUtils.BigDecimal2Str(merchant.getShipFee().subtract(merchant.getMerchantAssumeAmt()));
            if ("0".equals(shipFee)) {
                rlCartShipingAndBox.setVisibility(View.GONE);
            }
            tv_cart_shipping.setText("另需配送费¥" + shipFee);
            tv_cart_package.setTextSize(10);
        } else {
            tv_cart_shipping.setVisibility(View.GONE);
            rlCartShipingAndBox.setVisibility(View.GONE);
            tv_cart_package.setTextSize(14);
        }
        tv_cart_qisong.setText("¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice()) + "起送");
        if (merchant.getStatus() == 0 || !merchant.isShoppingTime()) {
            rlSpecLabel.setVisibility(View.GONE);
            tvSleep.setVisibility(View.VISIBLE);
            rlHideBuyCount.setVisibility(View.INVISIBLE);
            return;
        }
        if (goods.getStatus() == 0 || goods.getStatus() == 2) {
            rlSpecLabel.setVisibility(View.GONE);
            if (goods.getHasDiscount() == 1 && goods.getSurplusDiscountStock() > 0) {
                tvSleep.setVisibility(View.GONE);
            } else {
                tvSleep.setVisibility(View.VISIBLE);
                tvSleep.setText("商品已售罄");
            }
            rlHideBuyCount.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }
}
