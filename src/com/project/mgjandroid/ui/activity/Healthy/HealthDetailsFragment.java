package com.project.mgjandroid.ui.activity.healthy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationHealth;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.InformationHealthModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.information.InformationDetailActivity;
import com.project.mgjandroid.ui.activity.information.ReportActivity;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/21.
 */

public class HealthDetailsFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.iv_avatar)
    private CornerImageView ivAvatar;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.tv_department)
    private TextView tvDepartment;
    @InjectView(R.id.tv_job_name)
    private TextView tvJobName;
    @InjectView(R.id.tv_hospital)
    private TextView tvHospital;
    @InjectView(R.id.tv_expertise)
    private TextView tvExpertise;
    @InjectView(R.id.tv_profiles)
    private TextView tvProfiles;
    @InjectView(R.id.tv_contact)
    private TextView tvContact;
    @InjectView(R.id.tv_report)
    private TextView tvReport;

    private MLoadingDialog mLoadingDialog;
    private InformationHealth information;

    public HealthDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_details, container, false);
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
        VolleyOperater<InformationHealthModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InformationHealthModel model = (InformationHealthModel) obj;
                    if (model.getCode() == 0) {
                        information = model.getValue();
                        showData();

                    }
                }
            }
        }, InformationHealthModel.class);
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
        tvJobName.setText(information.getProfessionalTitle());
        tvDepartment.setText(information.getDepartments());
        tvHospital.setText(information.getHospital());
        tvExpertise.setText(information.getDoctorExpertise());
        tvProfiles.setText(information.getDescription());
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
                    findInformationFreeStandardList(information.getUserId(), information.getId(), information.getAgentId(), InformationType.Health.getValue(), information.getCategoryId(), 3);
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
