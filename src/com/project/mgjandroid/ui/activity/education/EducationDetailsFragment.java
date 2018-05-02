package com.project.mgjandroid.ui.activity.education;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.EducationInformation;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationFengShui;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.EducationDetailModel;
import com.project.mgjandroid.model.HomemakingInformationDetailModel;
import com.project.mgjandroid.model.information.InformationFengShuiModel;
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
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/21.
 */

public class EducationDetailsFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.education_detail_name)
    private TextView tvName;
    @InjectView(R.id.education_detail_tag)
    private TextView tvTag;
    @InjectView(R.id.education_detail_price)
    private RatingBar rbEvaluate;
    @InjectView(R.id.education_detail_jubao)
    private TextView tvJuBao;
    @InjectView(R.id.education_detail_score_text)
    private TextView tvEvaluate;
    @InjectView(R.id.education_detail_detail_content)
    private TextView tvContent;
    @InjectView(R.id.education_detail_detail_contact)
    private TextView tvContact;
    @InjectView(R.id.my_banner)
    private MyBanner myBanner;
    @InjectView(R.id.tv_photo_count)
    private TextView tvPhotoCount;
    @InjectView(R.id.education_detail_location)
    private TextView tvLocation;
    @InjectView(R.id.education_detail_score)
    private TextView tvScore;


    private MLoadingDialog mLoadingDialog;
    private EducationInformation information;

    public EducationDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education_detail, container, false);
        InjectorFragment.get(this).inject(view);
        initView();
        return view;
    }

    private void initView() {
        tvJuBao.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        mLoadingDialog = new MLoadingDialog();
        mLoadingDialog.show(mActivity.getFragmentManager(), "");
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationId", ((InformationDetailActivity) mActivity).getInformationId());
        map.put("informationType", ((InformationDetailActivity) mActivity).getInformationType());
        VolleyOperater<EducationDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    EducationDetailModel model = (EducationDetailModel) obj;
                    if (model.getCode() == 0) {
                        information = model.getValue();
                        showData();
                    }
                }
            }
        }, EducationDetailModel.class);
    }


    private void showData() {
        tvName.setText(information.getOrganizationName());
        tvTag.setText(information.getCategoryName());
        tvLocation.setText(information.getAddress());
        tvContent.setText(information.getDescription());

        if (CheckUtils.isNoEmptyStr(information.getImgs())) {
            final String[] imageUrl = information.getImgs().split(";");
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
        }
        rbEvaluate.setRating(information.getScore().floatValue());
        if (information.getScore().floatValue() == 0) {
            tvScore.setVisibility(View.GONE);
            tvEvaluate.setText("暂无评分");
            rbEvaluate.setVisibility(View.GONE);
        } else {
            tvScore.setTextColor(Color.parseColor("#ff6634"));
            tvScore.setText(information.getScore().floatValue() + "分");
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
            case R.id.education_detail_jubao:
                if (App.isLogin()) {
                    ReportActivity.toReport(mActivity, ((InformationDetailActivity) mActivity).getInformationId(), ((InformationDetailActivity) mActivity).getInformationType());
                    break;
                } else {
                    startActivity(new Intent(mActivity, SmsLoginActivity.class));
                }
                break;
            case R.id.education_detail_detail_contact:
                if (information != null)
                    findInformationFreeStandardList(information.getUserId(), information.getId(), information.getAgentId(), InformationType.Education.getValue(), information.getCategoryId(), 3);
                break;
        }
    }

    @Override
    public InformationBaseProperty getInformation() {
        return information;
    }
}
