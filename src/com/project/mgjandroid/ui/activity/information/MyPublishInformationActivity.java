package com.project.mgjandroid.ui.activity.information;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.employment_simple.MyPublishPositionSimpleFragment;
import com.project.mgjandroid.ui.activity.employment_simple.MyPublishRecruitSimpleFragment;
import com.project.mgjandroid.ui.activity.healthy.MyPublishHealthyFragment;
import com.project.mgjandroid.ui.activity.education.MyPublishEducationFragment;
import com.project.mgjandroid.ui.activity.education.MyPublishFamilyEducationFragment;
import com.project.mgjandroid.ui.activity.employment.MyPublishPositionFragment;
import com.project.mgjandroid.ui.activity.employment.MyPublishRecruitFragment;
import com.project.mgjandroid.ui.activity.geomancy.MyPublishFengShuiFragment;
import com.project.mgjandroid.ui.activity.homemaking.MyPublishHomeMakingFragment;
import com.project.mgjandroid.ui.activity.laws.MyPublishLawFragment;
import com.project.mgjandroid.ui.activity.renthouse.MyPublishLeaseFragment;
import com.project.mgjandroid.ui.activity.renthouse.MyPublishRentFragment;
import com.project.mgjandroid.ui.activity.repair.MyPublishRepairFragment;
import com.project.mgjandroid.ui.activity.secondhand.MyPublishBuyFragment;
import com.project.mgjandroid.ui.activity.secondhand.MyPublishSecondHandFragment;
import com.project.mgjandroid.ui.activity.waste.MyPublishWasteFragment;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by User_Cjh on 2016/11/18.
 */
public class MyPublishInformationActivity extends BaseActivity {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.list_view_layout)
    private FrameLayout flList;
    private int informationType;
    private Fragment fragment;
    private CallPhoneDialog stickyDialog;

    private static MyPublishInformationActivity instance = null;

    public static void toMyPublishInformationList(Context context, int informationType) {
        Intent intent = new Intent(context, MyPublishInformationActivity.class);
        intent.putExtra("informationType", informationType);
        context.startActivity(intent);
    }

    public static MyPublishInformationActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_information);
        instance = this;
        Injector.get(this).inject();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("informationType")) {
            informationType = intent.getIntExtra("informationType", -1);
        }
        initView();
        initData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    private void initData() {
        selectFragment();
    }

    private void initView() {
        tvTitle.setText("我的发布");
        ivBack.setOnClickListener(this);
    }

    public void showStickyDialog(final String phone) {
        if (stickyDialog != null) {
            stickyDialog.cancel();
            stickyDialog = null;
        }
        stickyDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(mActivity.getString(R.string.withdraw_phone), phone)));
                mActivity.startActivity(intent);
                stickyDialog.dismiss();
            }

            @Override
            public void onExit() {
                stickyDialog.dismiss();
            }
        }, "评分请拨打客服电话", phone);
        stickyDialog.show();
    }

    private void selectFragment() {
        FragmentManager mManager = getSupportFragmentManager();
        FragmentTransaction ft = mManager.beginTransaction();
        if (informationType == InformationType.Position.getValue()) {
            tvTitle.setText("求职");
            if (fragment == null) {
                fragment = new MyPublishPositionFragment();
            }
        } else if (informationType == InformationType.Recruit.getValue()) {
            tvTitle.setText("招聘");
            if (fragment == null) {
                fragment = new MyPublishRecruitFragment();
            }
        } else if (informationType == InformationType.WasteRecovery.getValue()) {
            tvTitle.setText("废品回收");
            if (fragment == null) {
                fragment = new MyPublishWasteFragment();
            }
        } else if (informationType == InformationType.Law.getValue()) {
            tvTitle.setText("法律");
            if (fragment == null) {
                fragment = new MyPublishLawFragment();
            }
        } else if (informationType == InformationType.Health.getValue()) {
            tvTitle.setText("健康");
            if (fragment == null) {
                fragment = new MyPublishHealthyFragment();
            }
        } else if (informationType == InformationType.Divination.getValue()) {
            tvTitle.setText("共享大师");
            if (fragment == null) {
                fragment = new MyPublishFengShuiFragment();
            }
        } else if (informationType == InformationType.Homemaking.getValue()) {
            tvTitle.setText("家政服务");
            if (fragment == null) {
                fragment = new MyPublishHomeMakingFragment();
            }
        } else if (informationType == InformationType.Repair.getValue()) {
            tvTitle.setText("维修服务");
            if (fragment == null) {
                fragment = new MyPublishRepairFragment();
            }
        } else if (informationType == InformationType.Lease.getValue()) {
            tvTitle.setText("房屋租赁");
            if (fragment == null) {
                fragment = new MyPublishLeaseFragment();
            }
        } else if (informationType == InformationType.Rent.getValue()) {
            tvTitle.setText("个人求租");
            if (fragment == null) {
                fragment = new MyPublishRentFragment();
            }
        } else if (informationType == InformationType.Education.getValue()) {
            tvTitle.setText("教育培训");
            if (fragment == null) {
                fragment = new MyPublishEducationFragment();
            }
        } else if (informationType == InformationType.FamilyEducation.getValue()) {
            tvTitle.setText("家教信息");
            if (fragment == null) {
                fragment = new MyPublishFamilyEducationFragment();
            }
        } else if (informationType == InformationType.Secondhand.getValue()) {
            tvTitle.setText("二手市场");
            if (fragment == null) {
                fragment = new MyPublishSecondHandFragment();
            }
        } else if (informationType == InformationType.buy.getValue()) {
            tvTitle.setText("求购信息");
            if (fragment == null) {
                fragment = new MyPublishBuyFragment();
            }
        } else if (informationType == InformationType.NewPosition.getValue()) {
            tvTitle.setText("*求职*");
            if (fragment == null) {
                fragment = new MyPublishPositionSimpleFragment();
            }
        } else if (informationType == InformationType.NewRecruit.getValue()) {
            tvTitle.setText("*招聘*");
            if (fragment == null) {
                fragment = new MyPublishRecruitSimpleFragment();
            }
        }
        ft.add(R.id.list_view_layout, fragment);
        ft.commitAllowingStateLoss();
    }

    public void refresh() {
        if (informationType == InformationType.Position.getValue()) {
            if (fragment != null) {
                ((MyPublishPositionFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Recruit.getValue()) {
            if (fragment != null) {
                ((MyPublishRecruitFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Divination.getValue()) {
            if (fragment != null) {
                ((MyPublishFengShuiFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.WasteRecovery.getValue()) {
            if (fragment != null) {
                ((MyPublishWasteFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Law.getValue()) {
            if (fragment != null) {
                ((MyPublishLawFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Health.getValue()) {
            if (fragment != null) {
                ((MyPublishHealthyFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Homemaking.getValue()) {
            if (fragment != null) {
                ((MyPublishHomeMakingFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Repair.getValue()) {
            if (fragment != null) {
                ((MyPublishRepairFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Education.getValue()) {
            if (fragment != null) {
                ((MyPublishEducationFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.FamilyEducation.getValue()) {
            if (fragment != null) {
                ((MyPublishFamilyEducationFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Secondhand.getValue()) {
            if (fragment != null) {
                ((MyPublishSecondHandFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.buy.getValue()) {
            if (fragment != null) {
                ((MyPublishBuyFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Lease.getValue()) {
            if (fragment != null) {
                ((MyPublishLeaseFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.Rent.getValue()) {
            if (fragment != null) {
                ((MyPublishRentFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.NewPosition.getValue()) {
            if (fragment != null) {
                ((MyPublishPositionSimpleFragment) fragment).getData(false);
            }
        } else if (informationType == InformationType.NewRecruit.getValue()) {
            if (fragment != null) {
                ((MyPublishRecruitSimpleFragment) fragment).getData(false);
            }
        }
    }
}
