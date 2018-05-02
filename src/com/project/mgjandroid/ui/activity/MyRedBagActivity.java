package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.fragment.RedBagFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_redbag);
        Injector.get(this).inject();
        latitude = getIntent().getDoubleExtra("latitude", -1);
        longitude = getIntent().getDoubleExtra("longitude", -1);
        itemsPrice = getIntent().getDoubleExtra("itemsPrice", -1);
        merchantId = getIntent().getLongExtra("merchantId", -1);
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
            args.putString("PromoInfoJson", promoInfoJson);
            args.putLong("agentId", agentId);
            args.putLong("redBagId", redBagId);
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
