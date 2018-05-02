package com.project.mgjandroid.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.FindGoodsModel;
import com.project.mgjandroid.model.ShippingFeeModel;
import com.project.mgjandroid.model.SuperMarketCartModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
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

public class CommodityDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String IMAGE_ORIGIN_RECT = "IMAGE_ORIGIN_RECT";
    public static final String IMAGE_URL = "IMAGE_URL";

    @InjectView(R.id.container)
    private LinearLayout container;
    @InjectView(R.id.goods_info)
    private LinearLayout goodsInfo;
    @InjectView(R.id.iv_commodity_detail_back)
    private ImageView ivBack;
    @InjectView(R.id.iv_commodity_detail_share)
    private ImageView ivShare;
    @InjectView(R.id.rl_commodity_evaluate)
    private RelativeLayout rlEvaluate;
    @InjectView(R.id.tv_buy_count)
    private TextView tvCount;
    @InjectView(R.id.cart_num)
    private TextView tvCart_num;
    @InjectView(R.id.commodity_act_cart)
    private ImageView ivCart;
    @InjectView(R.id.commodity_img_add)
    private ImageView ivAdd;
    @InjectView(R.id.commodity_img_minus)
    private ImageView ivMinus;
    @InjectView(R.id.buy_count_hide)
    private RelativeLayout rlCount;
    @InjectView(R.id.tv_commodity_state)
    private TextView tvState;
    @InjectView(R.id.goods_pic)
    private ImageView goodsPic;
    @InjectView(R.id.detail_banner)
    private MyBanner detailBanner;
    @InjectView(R.id.commodity_act_bottom_car)
    private FrameLayout rlCar;
    @InjectView(R.id.tv_current_price)
    private TextView tvPrice;
    @InjectView(R.id.commodity_bottom_qisong)
    private TextView tvQiSong;
    @InjectView(R.id.commodity_go_account)
    private TextView tvGoAcc;
    @InjectView(R.id.tv_commodity_name)
    private TextView tvComName;
    @InjectView(R.id.tv_commodity_original_price)
    private TextView tvOriPrice;
    @InjectView(R.id.tv_commodity_limit)
    private TextView tvLimit;
    @InjectView(R.id.tv_norm_detail)
    private TextView tvNorm;
    @InjectView(R.id.tv_commodity_stock_detail)
    private TextView tvStock;
    @InjectView(R.id.tv_commodity_evaluate_detail)
    private TextView tvEvaDetail;
    @InjectView(R.id.iv_commodity_evaluate)
    private ImageView ivComEva;
    @InjectView(R.id.tv_no_evaluate)
    private TextView tvNoEva;
    @InjectView(R.id.commodity_bottom_shipping)
    private TextView tvShip;
    private String count;
    private int intCount;
    private Goods mGoods;
    private GoodsSpec goodsSpec;
    private SuperMarketCartModel mLnstance;
    private SuperMarketCartModel.SuperMarketCartBean superMarketCartBean;
    private ArrayList<SuperMarketCartModel.CartGoods> mCartGoods;
    private ViewGroup anim_mask_layout;
    private int commentNum;
    private int goodCommentNum;
    private int mediumCommentNum;
    private int badCommentNum;
    private MLoadingDialog mLoadingDialog;

    private ImageView mSourceImageView;
    private Rect mRect;
    private String mUrl;
    private ShareUtil shareUtil;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.commodity_detail);
        mGoods = (Goods) getIntent().getSerializableExtra("goods");
        Injector.get(this).inject();
        mLoadingDialog = new MLoadingDialog();
        initSourceImageView();
        getGoods();
        setPageData();
        initData();
        initListener();
    }

    private void initSourceImageView() {
        // 读取第一个界面传过来的信息
        mRect = getIntent().getParcelableExtra(IMAGE_ORIGIN_RECT);
        if (mRect == null) {
            return;
        }
        mUrl = getIntent().getExtras().getString(IMAGE_URL);

        // 先动态创建出这个sourceImageView，把它添加到ContentView中。
        FrameLayout contentView = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);
        mSourceImageView = new ImageView(this);
        mSourceImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        contentView.addView(mSourceImageView);
        // 设置为和第一个界面一样的图片
        if (TextUtils.isEmpty(mUrl)) {
            mSourceImageView.setImageResource(R.drawable.horsegj_default);
        } else {
            ImageUtils.loadBitmap(mActivity, mUrl, mSourceImageView, R.drawable.horsegj_default, Constants.getEndThumbnail(150, 150));
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
                        detailBanner.setVisibility(View.VISIBLE);
                        mSourceImageView.setVisibility(View.INVISIBLE);
                    }
                })
                .start();
        AnimatorUtils.showBottom2(goodsInfo, mActivity, container.getHeight() - detailBanner.getHeight());
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && mSourceImageView != null) {
            mSourceImageView.setVisibility(View.VISIBLE);
            container.setVisibility(View.INVISIBLE);
            runExitAnim();
        } else {
            super.onBackPressed();
        }
    }

    private void initData() {
        mLnstance = SuperMarketCartModel.getInstance();
        superMarketCartBean = mLnstance.getSuperMarketCartBean();
        if (mGoods.getMerchantId() != superMarketCartBean.getMerchantId()) {
            superMarketCartBean.setMerchantId(mGoods.getMerchantId());
            superMarketCartBean.clearCart();
        }
        mCartGoods = superMarketCartBean.getCartGoods();
        String totalCount = superMarketCartBean.getTotalCount();
        if (!"".equals(totalCount)) {
            tvCart_num.setVisibility(View.VISIBLE);
            ivCart.setImageResource(R.drawable.cart_2);
            tvCart_num.setText(totalCount);
            tvGoAcc.setVisibility(View.VISIBLE);
            for (SuperMarketCartModel.CartGoods cartGood : mCartGoods) {
                if (cartGood.getGoodsId() == goodsSpec.getGoodsId()) {
                    ivMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, mActivity));
                    tvCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, mActivity));
                    tvCount.setText(cartGood.getButCount() + "");
                    tvGoAcc.setVisibility(View.VISIBLE);
                    tvQiSong.setVisibility(View.GONE);
                }
            }
        } else {
            ivMinus.setTranslationX(0f);
            tvCount.setTranslationX(0f);
        }
        getShippingFee();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getSuperMarket();
    }

    private void getSuperMarket() {
        int idCount = 0;
        boolean isGetGoods = false;
        if (mCartGoods.size() != 0) {
            for (SuperMarketCartModel.CartGoods cartGood : mCartGoods) {
                if (goodsSpec.getGoodsId() == cartGood.getGoodsId()) {
                    isGetGoods = true;
                    idCount = cartGood.getButCount();
                    break;
                }
            }
        } else {
            tvCount.setText("0");
            tvCart_num.setVisibility(View.INVISIBLE);
            ivCart.setImageResource(R.drawable.cart_1);
            tvGoAcc.setVisibility(View.GONE);
            ivMinus.setTranslationX(0f);
            tvCount.setTranslationX(0f);
        }
        if (isGetGoods) {
            tvCart_num.setVisibility(View.VISIBLE);
            ivCart.setImageResource(R.drawable.cart_2);
            tvGoAcc.setVisibility(View.VISIBLE);
            tvCart_num.setText(superMarketCartBean.getTotalCount());
            tvCount.setText(String.valueOf(idCount));
            tvGoAcc.setVisibility(View.VISIBLE);
            tvQiSong.setVisibility(View.GONE);
            ivMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, mActivity));
            tvCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, mActivity));
        } else {
            tvCount.setText("0");
            tvCart_num.setText(superMarketCartBean.getTotalCount());
            ivMinus.setTranslationX(0f);
            tvCount.setTranslationX(0f);
        }
    }


    /**
     * 设置页面数据
     */
    private void setPageData() {
        if (mGoods != null) {
            tvComName.setText(mGoods.getName());
            List<GoodsSpec> goodsSpecList = mGoods.getGoodsSpecList();
            if (goodsSpecList != null && goodsSpecList.size() == 1) {
                BigDecimal price = mGoods.getGoodsSpecList().get(0).getPrice();
                tvPrice.setText(StringUtils.BigDecimal2Str(price));
                if (goodsSpecList != null && goodsSpecList.get(0).getOriginalPrice().compareTo(BigDecimal.ZERO) > 0) {
                    tvOriPrice.setText("¥" + StringUtils.BigDecimal2Str(goodsSpecList.get(0).getOriginalPrice()));
                    tvOriPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvOriPrice.setVisibility(View.GONE);
                }
                if (goodsSpecList != null && goodsSpecList.get(0).getOrderLimit() != null && goodsSpecList.get(0).getOrderLimit() > 0) {
                    tvLimit.setVisibility(View.VISIBLE);
                    tvLimit.setText("每单限购" + goodsSpecList.get(0).getOrderLimit() + "份");
                } else {
                    tvLimit.setVisibility(View.GONE);
                }
            } else if (goodsSpecList != null && goodsSpecList.size() > 1) {
                BigDecimal price = mGoods.getGoodsSpecList().get(0).getPrice();
                for (int i = 1; i < mGoods.getGoodsSpecList().size(); i++) {
                    if (price.compareTo(mGoods.getGoodsSpecList().get(i).getPrice()) == 1) {
                        price = mGoods.getGoodsSpecList().get(i).getPrice();
                    }
                }
                String str = StringUtils.BigDecimal2Str(price) + "起";
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                style.setSpan(new TextAppearanceSpan(null, 0, getResources().getDimensionPixelSize(R.dimen.mine_list_text_size), null, null), str.length() - 1, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                tvPrice.setText(style);
            }

            if (CheckUtils.isNoEmptyStr(mGoods.getImgs())) {
                String[] strings = mGoods.getImgs().split(";");
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
            if (goodsSpecList != null) {
                goodsSpec = goodsSpecList.get(0);
                tvNorm.setText(goodsSpec.getSpec());
                if (goodsSpec.getStockType() == 1) {
                    if (goodsSpec.getStock() == 0) {
                        tvStock.setText("售罄");
                        rlCount.setVisibility(View.GONE);
                        tvState.setVisibility(View.VISIBLE);
                    } else {
                        tvStock.setText(goodsSpec.getStock().toString());
                    }

                } else if (goodsSpec.getStockType() == 0) {
                    tvStock.setText("无限");
                }
            }
            if (mGoods.getStatus() == 0) {
                tvStock.setText("售罄");
                tvStock.setText("售罄");
                rlCount.setVisibility(View.GONE);
                tvState.setVisibility(View.VISIBLE);


            }
            goodCommentNum = mGoods.getGoodCommentNum();
            mediumCommentNum = mGoods.getMediumCommentNum();
            badCommentNum = mGoods.getBadCommentNum();
            commentNum = mGoods.getCommentNum();
            if (commentNum == 0) {
                tvEvaDetail.setVisibility(View.GONE);

            } else {
                tvEvaDetail.setText("(" + commentNum + ")");
            }

            if (mGoods.getCommentNum() == 0) {
                tvNoEva.setVisibility(View.VISIBLE);
                ivComEva.setVisibility(View.GONE);
            }
        }

    }


    private void initListener() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        rlEvaluate.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        rlCar.setOnClickListener(this);
        tvGoAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_commodity_detail_back:
                onBackPressed();
                break;
            case R.id.rl_commodity_evaluate:
                if (mGoods.getCommentNum() != 0) {
                    Intent intent = new Intent(this, CommodityEvaluateActivity.class);
                    intent.putExtra("googId", goodsSpec.getGoodsId());
                    intent.putExtra("commentNum", commentNum);
                    intent.putExtra("goodCommentNum", goodCommentNum);
                    intent.putExtra("mediumCommentNum", mediumCommentNum);
                    intent.putExtra("badCommentNum", badCommentNum);
                    startActivity(intent);
                }
                break;
            case R.id.commodity_img_add:
                count = tvCount.getText().toString().trim();
                intCount = Integer.parseInt(count);
                if (goodsSpec.getStockType() == 1) {
                    if (goodsSpec.getStock() != 0) {
                        String trim = tvStock.getText().toString().trim();
                        int stock = Integer.parseInt(trim);
                        if (intCount == stock) {
                            ToastUtils.displayMsg("购买的小伙伴太多，库存告急！", mActivity);
                            break;
                        }
                    }
                    if (goodsSpec.getOrderLimit() != 0) {
                        if (intCount >= goodsSpec.getOrderLimit()) {
                            ToastUtils.displayMsg(mGoods.getName() + "每单最多可购买" + goodsSpec.getOrderLimit() + "份喔！", mActivity);
                            break;
                        }
                    }
                } else {
                    if (goodsSpec.getOrderLimit() != 0) {
                        if (intCount >= goodsSpec.getOrderLimit()) {
                            ToastUtils.displayMsg(mGoods.getName() + "每单最多可购买" + goodsSpec.getOrderLimit() + "份喔！", mActivity);
                            break;
                        }
                    }
                }
                tvQiSong.setVisibility(View.GONE);
                tvCart_num.setVisibility(View.VISIBLE);
                ivCart.setImageResource(R.drawable.cart_2);
                tvGoAcc.setVisibility(View.VISIBLE);
                if (intCount == 99) {
                    ToastUtils.displayMsg("当前商品已达到最大购买数量", mActivity);
                    break;
                }
                superMarketCartBean.addGoods(goodsSpec.getGoodsId(), 1);
                if (intCount == 0) {
                    AnimatorUtils.leftTranslationRotating(ivMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.leftTranslationRotating(tvCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                }
                intCount++;
                tvCount.setText(intCount + "");
                tvCart_num.setText(superMarketCartBean.getTotalCount());
                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                ImageView ball = new ImageView(this);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
                ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                setAnim(ball, startLocation);// 开始执行动画
                break;
            case R.id.commodity_img_minus:
                superMarketCartBean.minusGoods(goodsSpec.getGoodsId(), 1);
                count = tvCount.getText().toString().trim();
                intCount = Integer.parseInt(count);
                intCount--;
                if (intCount <= 0) {
                    AnimatorUtils.rightTranslationRotating(ivMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.rightTranslationRotating(tvCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                    tvCount.setText("0");
                    tvGoAcc.setVisibility(View.GONE);
                    tvQiSong.setVisibility(View.VISIBLE);
                } else {
                    tvCount.setText(intCount + "");
                }
                if (superMarketCartBean.getTotalCount().equals("")) {
                    tvCart_num.setVisibility(View.INVISIBLE);
                    ivCart.setImageResource(R.drawable.cart_1);
                }
                tvCart_num.setText(superMarketCartBean.getTotalCount());
                break;
            case R.id.commodity_act_bottom_car:
                goToCart();
                break;
            case R.id.commodity_go_account:
                goToCart();
                break;
            case R.id.iv_commodity_detail_share:
                if (shareUtil == null && mGoods != null) {
                    shareUtil = new ShareUtil(mActivity, mGoods.getName(),
                            CheckUtils.isNoEmptyStr(mGoods.getDescription()) ? mGoods.getDescription() : "独乐不如众乐，分享好东西给你，快上马管家抢购吧~",
                            mGoods.getShareUrl(), mGoods.getImgs());
                }
                if (shareUtil != null) shareUtil.showPopupWindow();
                break;
        }
    }

    private void goToCart() {
        Intent marketCart = new Intent(mActivity, MarketCartActivity.class);
        startActivity(marketCart);
        mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
    }

    private void getGoods() {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", mGoods.getId());
        VolleyOperater<FindGoodsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_MERCHANT_SHOP_GOODS_INFO, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (obj instanceof String) {
                    ToastUtils.displayMsg(obj.toString(), mActivity);
                    return;
                }
                FindGoodsModel model = (FindGoodsModel) obj;
                if (model.getValue() != null) {
                    mGoods = model.getValue();
                    setPageData();
                    commentNum = model.getValue().getCommentNum();
                    goodCommentNum = model.getValue().getGoodCommentNum();
                    mediumCommentNum = model.getValue().getMediumCommentNum();
                    badCommentNum = model.getValue().getBadCommentNum();
                    if (commentNum == 0) {
                        tvEvaDetail.setVisibility(View.GONE);
                    } else {
                        tvEvaDetail.setText("(" + commentNum + ")");
                    }
                }

            }
        }, FindGoodsModel.class);
    }

    private void getShippingFee() {
        Map<String, Object> map = new HashMap<>();
        double latitude = Double.parseDouble(PreferenceUtils.getLocation(this)[0]);
        double longitude = Double.parseDouble(PreferenceUtils.getLocation(this)[1]);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("merchantId", mGoods.getMerchantId());
        VolleyOperater<ShippingFeeModel> operater = new VolleyOperater<ShippingFeeModel>(mActivity);
        operater.doRequest(Constants.URL_FIND_MERCHANT_SHIPPING_FEE_AND_MIN_PRICE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (obj instanceof String) {
                    ToastUtils.displayMsg(obj.toString(), mActivity);
                    return;
                }
                ShippingFeeModel model = (ShippingFeeModel) obj;
                ShippingFeeModel.Value value = model.getValue();
                BigDecimal minPrice = value.getMinPrice();
                BigDecimal shippingFee = value.getShipFee();
                if (shippingFee.compareTo(BigDecimal.ZERO) != 0) {
                    tvShip.setText("另需配送费¥" + StringUtils.BigDecimal2Str(shippingFee));
                }
                if (minPrice.compareTo(BigDecimal.ZERO) > 0) {
                    tvQiSong.setText("¥" + StringUtils.BigDecimal2Str(minPrice) + "起送");
                }
            }
        }, ShippingFeeModel.class);
    }

    /**
     * 开启动画
     */
    public void setAnim(final View v, int[] startLocation) {
        if (anim_mask_layout == null)
            anim_mask_layout = createAnimLayout();
        else {
            anim_mask_layout.removeAllViews();
        }
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tvCart_num.getLocationInWindow(endLocation);// shopCart是那个购物车

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
                v.setVisibility(View.INVISIBLE);
                anim_mask_layout.removeView(v);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }
}
