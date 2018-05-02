package com.project.mgjandroid.ui.activity.employment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.PositionInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.PositionInformationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.information.InformationDetailActivity;
import com.project.mgjandroid.ui.activity.information.ReportActivity;
import com.project.mgjandroid.ui.adapter.ImageRecyclerAdapter;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/11/14.
 */

public class ResumeDetailFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.root_view)
    private ScrollView rootView;
    @InjectView(R.id.iv_avatar)
    private CornerImageView ivAvatar;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.iv_sex)
    private ImageView ivSex;
    @InjectView(R.id.tv_age)
    private TextView tvAge;
    @InjectView(R.id.tv_education)
    private TextView tvEducation;
    @InjectView(R.id.tv_working_seniority)
    private TextView tvWorkSeniority;
    @InjectView(R.id.tv_time)
    private TextView tvTime;
    @InjectView(R.id.tv_position)
    private TextView tvPosition;
    @InjectView(R.id.tv_address)
    private TextView tvAddress;
    @InjectView(R.id.tv_salary)
    private TextView tvSalary;
    @InjectView(R.id.tv_report)
    private TextView tvReport;
    @InjectView(R.id.tv_profiles)
    private TextView tvProfiles;
    @InjectView(R.id.pic_layout)
    private LinearLayout picLayout;
    @InjectView(R.id.tv_pics_count)
    private TextView tvPicsCount;
    @InjectView(R.id.recycler_view)
    private RecyclerView recyclerView;
    @InjectView(R.id.tv_contact)
    private TextView tvContact;
    @InjectView(R.id.tv_page_count)
    private TextView tvPageCount;
    @InjectView(R.id.view_line)
    private View viewLine;

    private ImageRecyclerAdapter adapter;
    private MLoadingDialog mLoadingDialog;
    private PositionInformation information;

    public ResumeDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resume_detail, container, false);
        InjectorFragment.get(this).inject(view);
        initView();
        return view;
    }

    private void initView() {
        tvReport.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        adapter = new ImageRecyclerAdapter(mActivity, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mLoadingDialog = new MLoadingDialog();
        mLoadingDialog.show(mActivity.getFragmentManager(), "");
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationId", ((InformationDetailActivity) mActivity).getInformationId());
        map.put("informationType", ((InformationDetailActivity) mActivity).getInformationType());
        VolleyOperater<PositionInformationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    PositionInformationModel model = (PositionInformationModel) obj;
                    if (model.getCode() == 0) {
                        information = model.getValue();
                        information.setServiceTime(model.getServertime());
                        showData();
                        rootView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, PositionInformationModel.class);
    }

    private void showData() {
        tvName.setText(information.getTitle());
        int drawable;
        if (information.getSex() == 0) {
            ivSex.setImageResource(R.drawable.male_icon);
            drawable = R.drawable.sir_default_icon;
        } else {
            ivSex.setImageResource(R.drawable.female_icon);
            drawable = R.drawable.miss_default_icon;
        }
        if (CheckUtils.isNoEmptyStr(information.getHeadImg())) {
            ImageUtils.loadBitmap(mActivity, information.getHeadImg(), ivAvatar, drawable, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        } else {
            ivAvatar.setImageResource(drawable);
        }
        tvAge.setText(String.valueOf(information.getAge() + "岁"));
        tvEducation.setText(information.getHighestEducation());
        tvPosition.setText(information.getExpectPosition());
        tvAddress.setText(information.getAddress());
        String str = information.getExpectSalary();
        if (CheckUtils.isNoEmptyStr(str)) {
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            for (int i = 0; i < str.length(); i++) {
                if (CommonUtils.checkSalary(String.valueOf(str.charAt(i))))
                    style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.salary_or_price)), i, i + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            tvSalary.setText(style);
        }
//        tvTime.setText(CommonUtils.formatTime(information.getModifyTime().getTime(), "更新时间 : yyyy-MM-dd"));
        tvTime.setText("更新时间：" + DateUtils.format(information.getModifyTime(), information.getServiceTime()));
        if (information.getPageViewCount() != null && !TextUtils.isEmpty(information.getPageViewCount())) {
            tvPageCount.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            tvPageCount.setText("浏览量：" + information.getPageViewCount());
        } else {
            tvPageCount.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
        }
        tvProfiles.setText(information.getDescription());
        tvWorkSeniority.setText(information.getWorkExperience());
        if (CheckUtils.isNoEmptyStr(information.getImgs())) {
            String[] imageUrl = information.getImgs().split(";");
            if (imageUrl.length == 0) {
                picLayout.setVisibility(View.GONE);
                return;
            }
            adapter.setList(Arrays.asList(imageUrl));
            tvPicsCount.setText("(" + imageUrl.length + ")");
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
            case R.id.tv_contact:
                if (information != null)
                    findInformationFreeStandardList(information.getUserId(), information.getId(), information.getAgentId(), InformationType.Position.getValue(), information.getCategoryId(), 3);
                break;
        }
    }

    @Override
    public InformationBaseProperty getInformation() {
        return information;
    }
}
