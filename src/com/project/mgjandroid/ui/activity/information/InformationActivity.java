package com.project.mgjandroid.ui.activity.information;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.ChooseCityActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.education.EducationFragment;
import com.project.mgjandroid.ui.activity.education.FamilyEducationFragment;
import com.project.mgjandroid.ui.activity.education.PublishEducationInfoActivity;
import com.project.mgjandroid.ui.activity.education.PublishFamilyEducationInfoActivity;
import com.project.mgjandroid.ui.activity.employment.JobSearchFragment;
import com.project.mgjandroid.ui.activity.employment.PublishRecruitmentActivity;
import com.project.mgjandroid.ui.activity.employment.PublishResumeActivity;
import com.project.mgjandroid.ui.activity.employment.RecruitFragment;
import com.project.mgjandroid.ui.activity.employment_simple.JobSearchSimpleFragment;
import com.project.mgjandroid.ui.activity.employment_simple.PublishRecruitSimpleActivity;
import com.project.mgjandroid.ui.activity.employment_simple.PublishResumeSimpleActivity;
import com.project.mgjandroid.ui.activity.employment_simple.RecruitSimpleFragment;
import com.project.mgjandroid.ui.activity.geomancy.FengShuiMasterFragment;
import com.project.mgjandroid.ui.activity.geomancy.PublishFengShuiInformationActivity;
import com.project.mgjandroid.ui.activity.healthy.HealthConsultationFragment;
import com.project.mgjandroid.ui.activity.healthy.PublishHealthConsultationInformationActivity;
import com.project.mgjandroid.ui.activity.homemaking.HomeMakingFragment;
import com.project.mgjandroid.ui.activity.homemaking.PublishHomeMakingInfoActivity;
import com.project.mgjandroid.ui.activity.laws.LegalAdviceFragment;
import com.project.mgjandroid.ui.activity.laws.PublishLegalConsultingActivity;
import com.project.mgjandroid.ui.activity.renthouse.LeaseFragment;
import com.project.mgjandroid.ui.activity.renthouse.PublishLeaseActivity;
import com.project.mgjandroid.ui.activity.renthouse.PublishRentActivity;
import com.project.mgjandroid.ui.activity.renthouse.RentFragment;
import com.project.mgjandroid.ui.activity.repair.PublishRepairInfoActivity;
import com.project.mgjandroid.ui.activity.repair.RepairFragment;
import com.project.mgjandroid.ui.activity.secondhand.BuyFragment;
import com.project.mgjandroid.ui.activity.secondhand.PublishBuyInfoActivity;
import com.project.mgjandroid.ui.activity.secondhand.PublishSecondHandInfoActivity;
import com.project.mgjandroid.ui.activity.secondhand.SecondHandFragment;
import com.project.mgjandroid.ui.activity.waste.PublishRecyclingInformationActivity;
import com.project.mgjandroid.ui.activity.waste.WasteRecoveryFragment;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

@Router(value = "informationList/:informationType/:type", intParams = "informationType", longParams = "type")
public class InformationActivity extends BaseActivity {

    public final static String CATEGORY_ID = "category_id";
    public final static String CATEGORY_NAME = "category_name";
    public final static String IS_FROM_RECRUIT_ACTIVITY = "is_from_recruit_activity";

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_publish)
    private TextView tvCity;
    @InjectView(R.id.list_view_layout)
    private FrameLayout replaceLayout;
    @InjectView(R.id.tv_to_publish)
    private TextView tvPublish;

    private long categoryId;
    private String categoryName;
    private int type;
    private Fragment fragment;
    private int informationType;
    private LoadingDialog loadingDialog;

    public static void toInformationList(Context context, long categoryId, String categoryName, int informationType) {
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra(CATEGORY_ID, categoryId);
        intent.putExtra(CATEGORY_NAME, categoryName);
        intent.putExtra("informationType", informationType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Injector.get(this).inject();
        categoryId = getIntent().getLongExtra(CATEGORY_ID, -1);
        categoryName = getIntent().getStringExtra(CATEGORY_NAME);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("type")) {
            type = intent.getIntExtra("type", 2);
        } else {
            type = ChooseCityModel.getInstance().getType();
        }
        if (intent != null && intent.hasExtra("informationType")) {
            informationType = intent.getIntExtra("informationType", InformationType.Position.getValue());
        }
        initView();

        initData();
    }

    private void initView() {
        if (categoryName != null) {
            tvTitle.setText(categoryName);
        } else {
            if (informationType == 1) {
                tvTitle.setText("招聘信息");
                tvPublish.setText("发布招聘");
            } else if (informationType == 10) {
                tvTitle.setText("求职信息");
                tvPublish.setText("发布简历");
            } else if (informationType == 7) {
                tvTitle.setText("共享大师");
            } else if (informationType == 9) {
                tvTitle.setText("健康咨询");
            } else if (informationType == 8) {
                tvTitle.setText("法律咨询");
            } else if (informationType == 11) {
                tvTitle.setText("废品回收");
            } else if (informationType == 5) {
                tvTitle.setText("家政服务");
            } else if (informationType == 3) {
                tvTitle.setText("维修服务");
            } else if (informationType == 4) {
                tvTitle.setText("教育培训");
            } else if (informationType == 13) {
                tvTitle.setText("家教信息");
            } else if (informationType == 6) {
                tvTitle.setText("二手市场");
            } else if (informationType == 12) {
                tvTitle.setText("求购信息");
            } else if (informationType == 2) {
                tvTitle.setText("房屋租赁");
            } else if (informationType == InformationType.Rent.getValue()) {
                tvTitle.setText("个人求租");
            } else if (informationType == 16) {
                tvTitle.setText("*求职*");
            } else if (informationType == 15) {
                tvTitle.setText("*招聘*");
            } else {
                toast("暂不支持该功能");
                InformationActivity.this.finish();
                return;
            }
        }
        tvCity.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            tvCity.setBackgroundDrawable(null);
        } else {
            tvCity.setBackground(null);
        }

        tvCity.setPadding(0, 0, 0, 0);
        tvCity.setTextSize(12);
        Drawable arrow = getResources().getDrawable(R.drawable.down_arrow);
        if (arrow != null) {
            arrow.setBounds(0, 0, arrow.getMinimumWidth(), arrow.getMinimumHeight());
            tvCity.setCompoundDrawables(null, null, arrow, null);
            tvCity.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.x4));
        }

        ivBack.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        selectFragment();
    }

    private void selectFragment() {
        FragmentManager mManager = getSupportFragmentManager();
        FragmentTransaction ft = mManager.beginTransaction();
        if (informationType == InformationType.Position.getValue()) {
            if (fragment == null) {
                fragment = new JobSearchFragment();
            }
        } else if (informationType == InformationType.Recruit.getValue()) {
            if (fragment == null) {
                fragment = new RecruitFragment();
            }
        } else if (informationType == InformationType.Divination.getValue()) {
            if (fragment == null) {
                fragment = new FengShuiMasterFragment();
            }
        } else if (informationType == InformationType.Health.getValue()) {
            if (fragment == null) {
                fragment = new HealthConsultationFragment();
            }
        } else if (informationType == InformationType.Law.getValue()) {
            if (fragment == null) {
                fragment = new LegalAdviceFragment();
            }
        } else if (informationType == InformationType.WasteRecovery.getValue()) {
            if (fragment == null) {
                fragment = new WasteRecoveryFragment();
            }
        } else if (informationType == InformationType.Homemaking.getValue()) {
            if (fragment == null) {
                fragment = new HomeMakingFragment();
            }
        } else if (informationType == InformationType.Repair.getValue()) {
            if (fragment == null) {
                fragment = new RepairFragment();
            }
        } else if (informationType == InformationType.Education.getValue()) {
            if (fragment == null) {
                fragment = new EducationFragment();
            }
        } else if (informationType == InformationType.FamilyEducation.getValue()) {
            if (fragment == null) {
                fragment = new FamilyEducationFragment();
            }
        } else if (informationType == InformationType.Secondhand.getValue()) {
            if (fragment == null) {
                fragment = new SecondHandFragment();
            }
        } else if (informationType == InformationType.buy.getValue()) {
            if (fragment == null) {
                fragment = new BuyFragment();
            }
        } else if (informationType == InformationType.Lease.getValue()) {
            if (fragment == null) {
                fragment = new LeaseFragment();
            }
        } else if (informationType == InformationType.Rent.getValue()) {
            if (fragment == null) {
                fragment = new RentFragment();
            }
        } else if (informationType == InformationType.NewPosition.getValue()) {
            if (fragment == null) {
                fragment = new JobSearchSimpleFragment();
            }
        } else if (informationType == InformationType.NewRecruit.getValue()) {
            if (fragment == null) {
                fragment = new RecruitSimpleFragment();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putLong(CATEGORY_ID, categoryId);
        fragment.setArguments(bundle);
        ft.add(R.id.list_view_layout, fragment);
        ft.commitAllowingStateLoss();
    }

    private void initData() {
        if (categoryId == -1)
            ChooseCityModel.getInstance().initData(mActivity);
        if (CheckUtils.isEmptyStr(ChooseCityModel.getInstance().getCityName())) {
            ChooseCityModel.getInstance().setCityName(PreferenceUtils.getAddressCity(mActivity));
            try {
                long cityCode = Long.parseLong(PreferenceUtils.getAddressCityCode(mActivity));
                ChooseCityModel.getInstance().setCityCode(cityCode);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(ChooseCityModel.getInstance().getCityName())) {
            tvCity.setText(ChooseCityModel.getInstance().getCityName());
        } else {
            tvCity.setText("切换城市");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ChooseCityModel.getInstance().isHasChanged()) {
            if (!TextUtils.isEmpty(ChooseCityModel.getInstance().getCityName())) {
                tvCity.setText(ChooseCityModel.getInstance().getCityName());
            } else {
                tvCity.setText("切换城市");
            }
            if (informationType == InformationType.Position.getValue()) {
                ((JobSearchFragment) fragment).refresh();
            } else if (informationType == InformationType.Recruit.getValue()) {
                ((RecruitFragment) fragment).refresh();
            } else if (informationType == InformationType.Divination.getValue()) {
                ((FengShuiMasterFragment) fragment).refresh();
            } else if (informationType == InformationType.Health.getValue()) {
                ((HealthConsultationFragment) fragment).refresh();
            } else if (informationType == InformationType.Law.getValue()) {
                ((LegalAdviceFragment) fragment).refresh();
            } else if (informationType == InformationType.WasteRecovery.getValue()) {
                ((WasteRecoveryFragment) fragment).refresh();
            } else if (informationType == InformationType.Homemaking.getValue()) {
                ((HomeMakingFragment) fragment).refresh();
            } else if (informationType == InformationType.Repair.getValue()) {
                ((RepairFragment) fragment).refresh();
            } else if (informationType == InformationType.Education.getValue()) {
                ((EducationFragment) fragment).refresh();
            } else if (informationType == InformationType.FamilyEducation.getValue()) {
                ((FamilyEducationFragment) fragment).refresh();
            } else if (informationType == InformationType.Secondhand.getValue()) {
                ((SecondHandFragment) fragment).refresh();
            } else if (informationType == InformationType.buy.getValue()) {
                ((BuyFragment) fragment).refresh();
            } else if (informationType == InformationType.Lease.getValue()) {
                ((LeaseFragment) fragment).refresh();
            } else if (informationType == InformationType.Rent.getValue()) {
                ((RentFragment) fragment).refresh();
            } else if (informationType == InformationType.NewPosition.getValue()) {
                ((JobSearchSimpleFragment) fragment).refresh();
            } else if (informationType == InformationType.NewRecruit.getValue()) {
                ((RecruitSimpleFragment) fragment).refresh();
            }
            ChooseCityModel.getInstance().setHasChanged(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.tv_publish:
                Intent intent1 = new Intent(mActivity, ChooseCityActivity.class);
                intent1.putExtra("from", IS_FROM_RECRUIT_ACTIVITY);
                startActivity(intent1);
                break;
            case R.id.tv_to_publish:
                if (App.isLogin()) {
                    switch (informationType) {
                        case 1:
                            startActivity(new Intent(this, PublishRecruitmentActivity.class));
                            break;
                        case 10:
                            startActivity(new Intent(this, PublishResumeActivity.class));
                            break;
                        case 7:
                            startActivity(new Intent(this, PublishFengShuiInformationActivity.class));
                            break;
                        case 9:
                            startActivity(new Intent(this, PublishHealthConsultationInformationActivity.class));
                            break;
                        case 8:
                            startActivity(new Intent(this, PublishLegalConsultingActivity.class));
                            break;
                        case 11:
                            startActivity(new Intent(this, PublishRecyclingInformationActivity.class));
                            break;
                        case 5:
                            startActivity(new Intent(this, PublishHomeMakingInfoActivity.class));
                            break;
                        case 3:
                            startActivity(new Intent(this, PublishRepairInfoActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(this, PublishLeaseActivity.class));
                            break;
                        case 14:
                            startActivity(new Intent(this, PublishRentActivity.class));
                            break;
                        case 4:
                            startActivity(new Intent(this, PublishEducationInfoActivity.class));
                            break;
                        case 13:
                            startActivity(new Intent(this, PublishFamilyEducationInfoActivity.class));
                            break;
                        case 6:
                            startActivity(new Intent(this, PublishSecondHandInfoActivity.class));
                            break;
                        case 12:
                            startActivity(new Intent(this, PublishBuyInfoActivity.class));
                            break;
                        case 16:
                            startActivity(new Intent(this, PublishResumeSimpleActivity.class));
                            break;
                        case 15:
                            startActivity(new Intent(this, PublishRecruitSimpleActivity.class));
                            break;
                    }
                } else {
                    startActivity(new Intent(this, SmsLoginActivity.class));
                }
                break;
        }
    }
}
