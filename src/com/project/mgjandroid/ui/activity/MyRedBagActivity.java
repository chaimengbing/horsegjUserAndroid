package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.fragment.RedBagFragment;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by yuandi on 2016/5/24.
 */
public class MyRedBagActivity extends BaseActivity {

    public static final int RESPONSE_CHOOSE_RED_BAG = 135246;

    @InjectView(R.id.my_redbag_act_back)
    private ImageView ivBack;
    @InjectView(R.id.use_red_bag_layout)
    private RelativeLayout notUseLayout;
    @InjectView(R.id.is_use_red_bag)
    private CheckBox notUse;
    //红包、抵用
    @InjectView(R.id.vouchers_layout)
    private RelativeLayout vouchersLayout;
    @InjectView(R.id.platform_layout)
    private RelativeLayout platformLayout;
    @InjectView(R.id.platform_textview)
    private TextView platformTextView;
    @InjectView(R.id.vouchers_textview)
    private TextView vouchersTextView;
    @InjectView(R.id.platform_view)
    private View platformView;
    @InjectView(R.id.vouchers_view)
    private View vouchersView;
    //1:红包  2：抵用券
    private int redBagType = 1;

    private RedBagFragment fragmentRedBagCanUse;
    private RedBagFragment fragmentRedBagCantUse;
    private Fragment mCurrentFragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private double latitude;
    private double longitude;
    private double itemsPrice;
    private Long merchantId;
    private String promoInfoJson;
    private long redBagId;
    private long agentId;
    private String discountGoodsDiscountAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_redbag);
        Injector.get(this).inject();
        latitude = getIntent().getDoubleExtra("latitude", -1);
        longitude = getIntent().getDoubleExtra("longitude", -1);
        itemsPrice = getIntent().getDoubleExtra("itemsPrice", -1);
        merchantId = getIntent().getLongExtra("merchantId", -1);
        discountGoodsDiscountAmt = getIntent().getStringExtra("discountGoodsDiscountAmt");
        promoInfoJson = getIntent().getStringExtra("PromoInfoJson");
        agentId = getIntent().getLongExtra("agentId", -1);
        if (promoInfoJson == null) promoInfoJson = "[]";
        redBagId = getIntent().getLongExtra("redBagId", -1);
        if (getIntent().getBooleanExtra("isFromConfirmOrder", false)) {
            notUseLayout.setVisibility(View.VISIBLE);
            if (redBagId == -1) {
                notUse.setChecked(true);
            } else {
                notUse.setChecked(false);
            }
        } else {
            notUseLayout.setVisibility(View.GONE);
        }

        initView();
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putBoolean("canUse", true);
            args.putDouble("longitude", longitude);
            args.putDouble("latitude", latitude);
            args.putDouble("itemsPrice", itemsPrice);
            args.putLong("merchantId", merchantId);
            args.putString("discountGoodsDiscountAmt", discountGoodsDiscountAmt);
            args.putString("PromoInfoJson", promoInfoJson);
            args.putLong("agentId", agentId);
            args.putLong("redBagId", redBagId);
            args.putLong("redBagId", redBagType);
            fragmentRedBagCanUse = new RedBagFragment();
            fragmentRedBagCanUse.setArguments(args);
            fragmentManager.beginTransaction().add(R.id.content_view,
                    fragmentRedBagCanUse).commit();
            mCurrentFragment = fragmentRedBagCanUse;
        } else if (fragmentRedBagCanUse != null) {
            fragmentManager.beginTransaction()
                    .show(fragmentRedBagCanUse)
                    .commit();
            mCurrentFragment = fragmentRedBagCanUse;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handRedBagTabView(redBagType, 0, 0);
    }

    private void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        notUseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notUse.setChecked(true);
                Intent intent = new Intent();
                mActivity.setResult(RESPONSE_CHOOSE_RED_BAG, intent);
                mActivity.finish();
            }
        });
        notUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notUse.setChecked(true);
                Intent intent = new Intent();
                mActivity.setResult(RESPONSE_CHOOSE_RED_BAG, intent);
                mActivity.finish();
            }
        });

        //抵用
        vouchersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redBagType = 2;
                handRedBagTabView(redBagType, 0, 0);
                if (fragmentRedBagCanUse != null) {
                    fragmentRedBagCanUse.setRedBagType(redBagType);
                    fragmentRedBagCanUse.getData(false);
                }
            }
        });
        //红包、
        platformLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redBagType = 1;
                handRedBagTabView(redBagType, 0, 0);
                if (fragmentRedBagCanUse != null) {
                    fragmentRedBagCanUse.setRedBagType(redBagType);
                    fragmentRedBagCanUse.getData(false);
                }
            }
        });

    }


    /**
     * @param redBagType leixign
     */
    public void handRedBagTabView(int redBagType, int platFormCount, int vouchersCount) {
        if (platFormCount > 0) {
            platformTextView.setText("红包" + platFormCount + "个");
        } else {
            platformTextView.setText("红包");

        }
        if (vouchersCount > 0) {
            vouchersTextView.setText("代金券" + vouchersCount + "张");
        } else {
            vouchersTextView.setText("代金券");

        }
        ViewGroup.LayoutParams params = vouchersView.getLayoutParams();
        params.width = vouchersTextView.getWidth();
        vouchersView.setLayoutParams(params);
        vouchersView.invalidate();

        ViewGroup.LayoutParams paramsl = platformView.getLayoutParams();
        paramsl.width = platformTextView.getWidth();
        platformView.setLayoutParams(paramsl);
        platformView.invalidate();

        if (redBagType == 1) {
            platformView.setBackgroundColor(getResources().getColor(R.color.white));
            platformTextView.setTextColor(getResources().getColor(R.color.white));
            vouchersView.setBackgroundColor(getResources().getColor(R.color.redbag_nosel));
            vouchersTextView.setTextColor(getResources().getColor(R.color.redbag_nosel));
        } else {
            vouchersView.setBackgroundColor(getResources().getColor(R.color.white));
            vouchersTextView.setTextColor(getResources().getColor(R.color.white));
            platformView.setBackgroundColor(getResources().getColor(R.color.redbag_nosel));
            platformTextView.setTextColor(getResources().getColor(R.color.redbag_nosel));

        }
    }


    public void doTransaction(boolean canUse) {
        if (canUse) {
            if (fragmentRedBagCantUse == null) {
                Bundle args = new Bundle();
                args.putBoolean("canUse", false);
                fragmentRedBagCantUse = new RedBagFragment();
                fragmentRedBagCantUse.setArguments(args);
                transaction(fragmentRedBagCantUse);
            }
            transaction(fragmentRedBagCantUse);
        } else {
            if (fragmentRedBagCanUse == null) {
                Bundle args = new Bundle();
                args.putBoolean("canUse", true);
                args.putDouble("longitude", longitude);
                args.putDouble("latitude", latitude);
                args.putDouble("itemsPrice", itemsPrice);
                args.putLong("merchantId", merchantId);
                args.putString("discountGoodsDiscountAmt", discountGoodsDiscountAmt);
                args.putString("PromoInfoJson", promoInfoJson);
                args.putLong("agentId", agentId);
                args.putLong("redBagId", redBagId);
                fragmentRedBagCanUse = new RedBagFragment();
                fragmentRedBagCanUse.setArguments(args);
            }
            transaction(fragmentRedBagCanUse);
        }
    }

    private void transaction(Fragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.unhold, R.anim.unfade);
            if (!to.isAdded()) {
                transaction.hide(mCurrentFragment).add(R.id.content_view, to).commit();
            } else {
                transaction.hide(mCurrentFragment).show(to).commit();
            }
            mCurrentFragment = to;
        }
    }
}
