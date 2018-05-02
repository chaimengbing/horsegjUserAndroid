package com.project.mgjandroid.ui.activity.laws;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationLaw;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.InformationWasteRecovery;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.InformationLawModel;
import com.project.mgjandroid.model.information.InformationWasteModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.information.InformationDetailActivity;
import com.project.mgjandroid.ui.activity.information.ReportActivity;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.pictureviewer.PictureViewActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.CircleIndicator;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/17.
 */

public class LawsDetailsFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.iv_avatar)
    private CornerImageView ivAvatar;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.tv_report)
    private TextView tvReport;
    @InjectView(R.id.iv_time)
    private ImageView ivTime;
    @InjectView(R.id.tv_age)
    private TextView tvAge;
    @InjectView(R.id.law_position_name)
    private TextView lawType;
    @InjectView(R.id.tv_province)
    private TextView tvProvince;
    @InjectView(R.id.tv_city)
    private TextView tvCity;
    @InjectView(R.id.tv_company)
    private TextView tvCompany;
    @InjectView(R.id.tv_expertise)
    private TextView tvExpertise;
    @InjectView(R.id.tv_profiles)
    private TextView tvProfiles;
    @InjectView(R.id.tv_contact)
    private TextView tvContact;
    private MLoadingDialog mLoadingDialog;
    private InformationLaw information;

    public LawsDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_law_details, container, false);
        InjectorFragment.get(this).inject(view);
        initView();
        return view;
    }

    private void initView() {
        tvReport.setOnClickListener(this);
        tvProfiles.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        mLoadingDialog = new MLoadingDialog();
        mLoadingDialog.show(mActivity.getFragmentManager(), "");
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationId", ((InformationDetailActivity) mActivity).getInformationId());
        map.put("informationType", ((InformationDetailActivity) mActivity).getInformationType());
        VolleyOperater<InformationLawModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InformationLawModel model = (InformationLawModel) obj;
                    if (model.getCode() == 0) {
                        information = model.getValue();
                        showData();
                    }
                }
            }
        }, InformationLawModel.class);
    }

    private void showData() {
        String imgs = information.getHeadImg();
        if (TextUtils.isEmpty(imgs)) {
            ivAvatar.setVisibility(View.GONE);
        } else {
            ivAvatar.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity, information.getHeadImg(), ivAvatar, R.drawable.horsegj_default, Constants.getEndThumbnail(86, 66));
        }
        tvName.setText(information.getName());
        if (information.getPracticeTimeNum() == 0) {
            tvAge.setText(1 + "年");
        } else {
            tvAge.setText(information.getPracticeTimeNum() + "年");
        }
        lawType.setText(information.getCategoryName());
        tvProvince.setText(information.getWhereProvinceName());
        tvCompany.setText(information.getWhereCompany());
        tvExpertise.setText(information.getGoodField());
        tvProfiles.setText(information.getPersonalProfile());
        if (information.getIsExpire() != 1 && information.getStatus() == 2) {
            ((InformationDetailActivity) getActivity()).canShare(true);
        } else {
            ((InformationDetailActivity) getActivity()).canShare(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_contact:
                if (information != null)
                    findInformationFreeStandardList(information.getUserId(), information.getId(), information.getAgentId(), InformationType.Law.getValue(), information.getCategoryId(), 3);
                break;
            case R.id.tv_report:
                if (App.isLogin()) {
                    ReportActivity.toReport(mActivity, ((InformationDetailActivity) mActivity).getInformationId(), ((InformationDetailActivity) mActivity).getInformationType());
                    break;
                } else {
                    startActivity(new Intent(mActivity, SmsLoginActivity.class));
                }
                break;
        }
    }

    @Override
    public InformationBaseProperty getInformation() {
        return information;
    }

}
