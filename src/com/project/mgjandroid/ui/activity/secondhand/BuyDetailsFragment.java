package com.project.mgjandroid.ui.activity.secondhand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.SecondhandInformation;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.SecondHandDetailModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.information.InformationDetailActivity;
import com.project.mgjandroid.ui.activity.information.ReportActivity;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.pictureviewer.PictureViewActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.CircleIndicator;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/21.
 */

public class BuyDetailsFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.second_hand_name)
    private TextView tvName;
    @InjectView(R.id.second_hand_tag)
    private TextView tvTag;
    @InjectView(R.id.second_hand_price)
    private TextView tvPrice;
    @InjectView(R.id.second_hand_detail_time)
    private TextView tvTime;
    @InjectView(R.id.second_hand_jubao)
    private TextView tvReport;
    @InjectView(R.id.second_hand_detail_content)
    private TextView tvContent;
    @InjectView(R.id.second_hand_detail_contact)
    private TextView tvContact;


    private MLoadingDialog mLoadingDialog;
    private SecondhandInformation information;

    public BuyDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy_detail, container, false);
        InjectorFragment.get(this).inject(view);
        initView();
        return view;
    }

    private void initView() {
        tvReport.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        mLoadingDialog = new MLoadingDialog();
        mLoadingDialog.show(mActivity.getFragmentManager(), "");
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationId", ((InformationDetailActivity) mActivity).getInformationId());
        map.put("informationType", ((InformationDetailActivity) mActivity).getInformationType());
        VolleyOperater<SecondHandDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    SecondHandDetailModel model = (SecondHandDetailModel) obj;
                    if (model.getCode() == 0) {
                        information = model.getValue();
                        information.setServiceTime(model.getServertime());
                        showData();
                    }
                }
            }
        }, SecondHandDetailModel.class);
    }


    private void showData() {
        tvName.setText(information.getTitle());
        tvTag.setText(information.getCategoryName());
        tvPrice.setText(StringUtils.getPrice(mActivity, StringUtils.money2Str(information.getMinAmt(), "元") + " - " + StringUtils.money2Str(information.getMaxAmt(), "元")));
        tvTime.setText("发布时间:" + DateUtils.format(information.getModifyTime(), information.getServiceTime()));
        tvContent.setText(information.getDescription());
        if (information.getIsExpire() != 1 && information.getStatus() == 2) {
            ((InformationDetailActivity) getActivity()).canShare(true);
        } else {
            ((InformationDetailActivity) getActivity()).canShare(false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.second_hand_jubao:
                if (App.isLogin()) {
                    ReportActivity.toReport(mActivity, ((InformationDetailActivity) mActivity).getInformationId(), ((InformationDetailActivity) mActivity).getInformationType());
                    break;
                } else {
                    startActivity(new Intent(mActivity, SmsLoginActivity.class));
                }
                break;
            case R.id.second_hand_detail_contact:
                if (information != null)
                    findInformationFreeStandardList(information.getUserId(), information.getId(), information.getAgentId(), InformationType.buy.getValue(), information.getCategoryId(), 3);
                break;
        }
    }

    @Override
    public InformationBaseProperty getInformation() {
        return information;
    }
}
