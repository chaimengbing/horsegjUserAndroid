package com.project.mgjandroid.ui.activity.waste;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationBaseProperty;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.InformationWasteRecovery;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.information.InformationWasteModel;
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

import static com.project.mgjandroid.R.id.tv_title;

/**
 * Created by Administrator on 2016/11/21.
 */

public class WasteDetailsFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.root_view)
    private ScrollView rootView;
    @InjectView(R.id.my_banner)
    private MyBanner myBanner;
    @InjectView(R.id.tv_photo_count)
    private TextView tvPhotoCount;
    @InjectView(R.id.tv_title)
    private TextView tvTitle;
    @InjectView(R.id.waste_type)
    private TextView tvWasteType;
    @InjectView(R.id.score_layout)
    private LinearLayout scoreLayout;
    @InjectView(R.id.group_detail_admin_score)
    private RatingBar ratingBar;
    @InjectView(R.id.group_detail_admin_score_show)
    private TextView tvScore;
    @InjectView(R.id.tv_assess)
    private TextView tvAssess;
    @InjectView(R.id.tv_merchant)
    private TextView tvMerchant;
    @InjectView(R.id.tv_type)
    private TextView tvType;
    @InjectView(R.id.tv_area)
    private TextView tvArea;
    @InjectView(R.id.tv_report)
    private TextView tvReport;
    @InjectView(R.id.tv_detailed_information)
    private TextView tvDetailedInformation;
    @InjectView(R.id.tv_contact)
    private TextView tvContact;
    private MLoadingDialog mLoadingDialog;
    private InformationWasteRecovery information;

    public WasteDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waste_details, container, false);
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
        VolleyOperater<InformationWasteModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BY_ID, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    InformationWasteModel model = (InformationWasteModel) obj;
                    if (model.getCode() == 0) {
                        information = model.getValue();
                        showData();
                        rootView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, InformationWasteModel.class);
    }

    private void showData() {
        tvTitle.setText(information.getTitle());
        tvArea.setText(information.getServiceArea());
        tvDetailedInformation.setText(information.getDescription());
        tvType.setText(information.getGoodField());
        tvWasteType.setText(information.getGoodField());
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
        ratingBar.setRating(information.getScore().floatValue());
        if (information.getScore().floatValue() == 0) {
            scoreLayout.setVisibility(View.GONE);
            tvAssess.setTextColor(Color.parseColor("#999999"));
            tvAssess.setText("暂无评分");
        } else {
            scoreLayout.setVisibility(View.VISIBLE);
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
            case R.id.tv_report:
                if (App.isLogin()) {
                    ReportActivity.toReport(mActivity, ((InformationDetailActivity) mActivity).getInformationId(), ((InformationDetailActivity) mActivity).getInformationType());
                    break;
                } else {
                    startActivity(new Intent(mActivity, SmsLoginActivity.class));
                }
                break;
            case R.id.tv_contact:
                if (information != null)
                    findInformationFreeStandardList(information.getUserId(), information.getId(), information.getAgentId(), InformationType.WasteRecovery.getValue(), information.getCategoryId(), 3);
                break;
        }
    }

    @Override
    public InformationBaseProperty getInformation() {
        return information;
    }
}
