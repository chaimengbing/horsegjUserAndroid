package com.project.mgjandroid.ui.activity.employment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.RecruitInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.RecruitInformationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.information.InformationDetailActivity;
import com.project.mgjandroid.ui.activity.information.ReportActivity;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.pictureviewer.PictureViewActivity;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.CircleIndicator;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/11/14.
 */

public class RecruitmentDetailFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.root_view)
    private ScrollView rootView;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.tv_salary)
    private TextView tvSalary;
    @InjectView(R.id.tv_publish_time)
    private TextView tvTime;
    @InjectView(R.id.tv_page_count)
    private TextView tvPageCount;
    @InjectView(R.id.tv_position)
    private TextView tvPosition;
    @InjectView(R.id.tv_requirement)
    private TextView tvRequirement;
    @InjectView(R.id.welfare)
    private TextView tvWelfare;
    @InjectView(R.id.welfare_layout)
    private FlowLayout welfareLayout;
    @InjectView(R.id.tv_address)
    private TextView tvAddress;
    @InjectView(R.id.tv_report)
    private TextView tvReport;
    @InjectView(R.id.tv_position_requirement)
    private TextView tvPositionRequirement;
    @InjectView(R.id.position_expand_layout)
    private LinearLayout positionLayout;
    @InjectView(R.id.tv_position_requirement_expand)
    private TextView tvPositionRequirementExpand;
    @InjectView(R.id.tv_company)
    private TextView tvCompany;
    @InjectView(R.id.tv_numbers)
    private TextView tvNumbers;
    @InjectView(R.id.tv_nature)
    private TextView tvNature;
    @InjectView(R.id.tv_industry)
    private TextView tvIndustry;
    @InjectView(R.id.tv_company_address)
    private TextView tvCompanyAddress;
    @InjectView(R.id.company_layout)
    private LinearLayout companyLayout;
    @InjectView(R.id.tv_company_profiles)
    private TextView tvCompanyProfiles;
    @InjectView(R.id.tv_company_profiles_expand)
    private TextView tvCompanyProfilesExpand;
    @InjectView(R.id.pic_layout)
    private LinearLayout picLayout;
    @InjectView(R.id.my_banner)
    private MyBanner myBanner;
    @InjectView(R.id.tv_photo_count)
    private TextView tvPhotoCount;
    @InjectView(R.id.tv_contact)
    private TextView tvContact;
    private MLoadingDialog mLoadingDialog;
    private RecruitInformation information;

    private static final int DEFAULT_LINES = 9;

    public RecruitmentDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruitment_detail, container, false);
        InjectorFragment.get(this).inject(view);
        initView();
        return view;
    }

    private void initView() {
        tvReport.setOnClickListener(this);
        tvPositionRequirementExpand.setOnClickListener(this);
        tvCompanyProfilesExpand.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        mLoadingDialog = new MLoadingDialog();
        mLoadingDialog.show(mActivity.getFragmentManager(), "");
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationId", ((InformationDetailActivity) mActivity).getInformationId());
        map.put("informationType", ((InformationDetailActivity) mActivity).getInformationType());
        VolleyOperater<RecruitInformationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    RecruitInformationModel model = (RecruitInformationModel) obj;
                    if (model.getCode() == 0) {
                        information = model.getValue();
                        information.setServiceTime(model.getServertime());
                        showData();
                        rootView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, RecruitInformationModel.class);
    }

    private void showData() {
        tvName.setText(information.getTitle());
        String str = information.getSalary();
        if (CheckUtils.isNoEmptyStr(str)) {
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            for (int i = 0; i < str.length(); i++) {
                if (!CommonUtils.checkSalary(String.valueOf(str.charAt(i)))) {
                    style.setSpan(new TextAppearanceSpan(null, 0, getResources().getDimensionPixelSize(R.dimen.sp13), null, null), i, i + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    style.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), i, i + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
            tvSalary.setText(style);
        }
//        tvTime.setText(CommonUtils.formatTime(information.getModifyTime().getTime(), "发布时间 : yyyy-MM-dd"));
        tvTime.setText("发布时间：" + DateUtils.format(information.getModifyTime(), information.getServiceTime()));
        if (information.getPageViewCount() != null && !TextUtils.isEmpty(information.getPageViewCount())) {
            tvPageCount.setVisibility(View.VISIBLE);
            tvPageCount.setText("浏览量：" + information.getPageViewCount());
        } else {
            tvPageCount.setVisibility(View.GONE);
        }
        tvPosition.setText(information.getPositionName());
        if (information.getWorkYears().equals("经验不限")) {
            if (information.getEducation().equals("学历不限")) {
                tvRequirement.setText("招" + information.getRecruitNum() + "/" + information.getWorkYears() + "/" + information.getEducation());
            } else {
                tvRequirement.setText("招" + information.getRecruitNum() + "/" + information.getWorkYears() + "/" + information.getEducation() + "以上");
            }
        } else {
            if (information.getEducation().equals("学历不限")) {
                tvRequirement.setText("招" + information.getRecruitNum() + "/" + information.getWorkYears() + "工作经验" + "/" + information.getEducation());
            } else {
                tvRequirement.setText("招" + information.getRecruitNum() + "/" + information.getWorkYears() + "工作经验" + "/" + information.getEducation() + "以上");
            }
        }
        if (CheckUtils.isNoEmptyStr(information.getWelfare())) {
            tvWelfare.setVisibility(View.VISIBLE);
            String[] welfares = information.getWelfare().split(",");
            for (String welfare : welfares) {
                TextView textView = (TextView) mInflater.inflate(R.layout.welfare_text, null);
                textView.setText(welfare);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, DipToPx.dip2px(mActivity, 3), DipToPx.dip2px(mActivity, 3));
                textView.setLayoutParams(layoutParams);
                welfareLayout.addView(textView);
            }
        }
        tvAddress.setText(information.getCompanyAddress());
        tvCompanyAddress.setText("公司地址：" + information.getCompanyAddress());
        tvPositionRequirement.setText(information.getClaim());
        if (tvPositionRequirement.getLineCount() > DEFAULT_LINES) {
            positionLayout.setVisibility(View.VISIBLE);
            tvPositionRequirement.setHeight(tvPositionRequirement.getLineHeight() * DEFAULT_LINES + tvPositionRequirement.getPaddingTop() + tvPositionRequirement.getPaddingBottom());
        }
        tvCompany.setText(information.getCompanyName());
        tvNumbers.setText(information.getCompanyScale());
        tvNature.setText(information.getCompanyType());
        tvIndustry.setText(information.getProfession());
        tvCompanyProfiles.setText("公司简介\n" + information.getDescription());
        tvCompanyProfiles.setHeight(0);
        companyLayout.setVisibility(View.GONE);
        if (CheckUtils.isNoEmptyStr(information.getImgs())) {
            final String[] imageUrl = information.getImgs().split(";");
            if (imageUrl.length == 0) {
                picLayout.setVisibility(View.GONE);
                return;
            }
            tvPhotoCount.setText("1/" + imageUrl.length);
            final List<String> stringList = Arrays.asList(imageUrl);
            myBanner.setUrls(stringList, true, false);
            myBanner.setOnPageChangeListener(new CircleIndicator.PageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tvPhotoCount.setText((position + 1) + "/" + imageUrl.length);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            myBanner.setOnBannerItemClickListener(new OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    PictureViewActivity.toViewPicture(mActivity, JSONArray.toJSONString(stringList), position);
                }
            });
        } else {
            picLayout.setVisibility(View.GONE);
        }
        if (information.getIsExpire() != 1 && information.getStatus() == 2) {
            ((InformationDetailActivity) getActivity()).canShare(true);
        } else {
            ((InformationDetailActivity) getActivity()).canShare(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_report:
                if (App.isLogin()) {
                    ReportActivity.toReport(mActivity, ((InformationDetailActivity) mActivity).getInformationId(), ((InformationDetailActivity) mActivity).getInformationType());
                    break;
                } else {
                    startActivity(new Intent(mActivity, SmsLoginActivity.class));
                }
                break;
            case R.id.tv_position_requirement_expand:
                if (tvPositionRequirementExpand.isSelected()) {
                    animatePos(false);
                    tvPositionRequirementExpand.setSelected(false);
                } else {
                    animatePos(true);
                    tvPositionRequirementExpand.setSelected(true);
                }
                break;
            case R.id.tv_company_profiles_expand:
                if (tvCompanyProfilesExpand.isSelected()) {
                    animateCom(false);
                    tvCompanyProfilesExpand.setSelected(false);
                } else {
                    companyLayout.setVisibility(View.VISIBLE);
                    animateCom(true);
                    tvCompanyProfilesExpand.setSelected(true);
                }
                break;
            case R.id.tv_contact:
                if (information != null)
                    findInformationFreeStandardList(information.getUserId(), information.getId(), information.getAgentId(), InformationType.Recruit.getValue(), information.getCategoryId(), 3);
                break;
        }
    }

    private void animatePos(boolean isExpand) {
        tvPositionRequirement.clearAnimation();
        final int deltaValue;
        final int startValue = tvPositionRequirement.getHeight();
        int durationMillis = 300;
        if (isExpand) {
            deltaValue = tvPositionRequirement.getLineHeight() * tvPositionRequirement.getLineCount()
                    + tvPositionRequirement.getPaddingTop() + tvPositionRequirement.getPaddingBottom() - startValue;
            tvPositionRequirementExpand.setText("收起信息");
        } else {
            deltaValue = tvPositionRequirement.getLineHeight() * DEFAULT_LINES
                    + tvPositionRequirement.getPaddingTop() + tvPositionRequirement.getPaddingBottom() - startValue;
            tvPositionRequirementExpand.setText("展开信息");
        }
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                tvPositionRequirement.setHeight((int) (startValue + deltaValue * interpolatedTime));
            }
        };
        animation.setDuration(durationMillis);
        tvPositionRequirement.startAnimation(animation);
    }

    private void animateCom(final boolean isExpand) {
        tvCompanyProfiles.clearAnimation();
        final int deltaValue;
        final int startValue = tvCompanyProfiles.getHeight();
        int durationMillis = 300;
        if (isExpand) {
            deltaValue = tvCompanyProfiles.getLineHeight() * tvCompanyProfiles.getLineCount()
                    + tvCompanyProfiles.getPaddingTop() + tvCompanyProfiles.getPaddingBottom() - startValue;
            tvCompanyProfilesExpand.setText("收起信息");
        } else {
            deltaValue = 0 - startValue;
            tvCompanyProfilesExpand.setText("展开信息");
        }
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                tvCompanyProfiles.setHeight((int) (startValue + deltaValue * interpolatedTime));
                if (!isExpand && interpolatedTime == 1) {
                    companyLayout.setVisibility(View.GONE);
                }
            }
        };
        animation.setDuration(durationMillis);
        tvCompanyProfiles.startAnimation(animation);
    }

    @Override
    public InformationBaseProperty getInformation() {
        return information;
    }
}
