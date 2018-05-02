package com.project.mgjandroid.ui.activity.employment_simple;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
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
import com.project.mgjandroid.bean.information.NewPositionInformation;
import com.project.mgjandroid.bean.information.PositionInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.NewPositionInformationModel;
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


public class ResumeSimpleDetailFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.tv_publish_time)
    private TextView tvPubTime;
    @InjectView(R.id.tv_category)
    private TextView tvCategory;
    @InjectView(R.id.tv_job_description)
    private TextView tvJobDescription;
    @InjectView(R.id.tv_report)
    private TextView tvReport;
    @InjectView(R.id.tv_call)
    private TextView tvCall;

    private MLoadingDialog mLoadingDialog;
    private NewPositionInformation information;

    public ResumeSimpleDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_simple_infomation, container, false);
        InjectorFragment.get(this).inject(view);
        initView();
        return view;
    }

    private void initView() {
        tvReport.setOnClickListener(this);
        tvCall.setOnClickListener(this);
        mLoadingDialog = new MLoadingDialog();
        mLoadingDialog.show(mActivity.getFragmentManager(), "");
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationId", ((InformationDetailActivity) mActivity).getInformationId());
        map.put("informationType", ((InformationDetailActivity) mActivity).getInformationType());
        VolleyOperater<NewPositionInformationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    NewPositionInformationModel model = (NewPositionInformationModel) obj;
                    if (model.getCode() == 0) {
                        information = model.getValue();
                        information.setServiceTime(model.getServertime());
                        showData();
                    }
                }
            }
        }, NewPositionInformationModel.class);
    }

    private void showData() {
        tvName.setText(information.getTitle());
        tvPubTime.setText(CommonUtils.formatTime(information.getModifyTime().getTime(), "发布时间: yyyy-MM-dd HH:mm"));
        tvCategory.setText("类别：" + information.getCategoryName());
        tvJobDescription.setText(information.getDescription());

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
            case R.id.tv_call:
                if (information != null)
                    findInformationFreeStandardList(information.getUserId(), information.getId(), information.getAgentId(), InformationType.NewPosition.getValue(), information.getCategoryId(), 3);
                break;
        }
    }

    @Override
    public InformationBaseProperty getInformation() {
        return information;
    }
}
