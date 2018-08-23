package com.project.mgjandroid.ui.activity.legwork;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Distance;
import com.project.mgjandroid.bean.SpecificTime;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.LegworkOrderDetailsModel;
import com.project.mgjandroid.model.LegworkServiceChargeModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegworkbilingRulesActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView commonBack;
    @InjectView(R.id.layout_distance_additional)
    private LinearLayout distanceAdditional;
    @InjectView(R.id.layout_special_time)
    private LinearLayout specialTime;
    @InjectView(R.id.tv_price)
    private TextView tvBasePrice;



    private LegworkServiceChargeModel.ValueBean serviceChargeModel;
    private ArrayList<Distance> stairServiceChargeRuleList;
    private ArrayList<SpecificTime> timeFrameServiceChargeList;
    private LegworkOrderDetailsModel.ValueBean mValueBean;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_billing_rules);
        Injector.get(this).inject();
        serviceChargeModel = (LegworkServiceChargeModel.ValueBean) getIntent().getSerializableExtra("serviceChargeModel");
        mValueBean = (LegworkOrderDetailsModel.ValueBean) getIntent().getSerializableExtra("mValueBean");
        if(serviceChargeModel!=null){
            stairServiceChargeRuleList = serviceChargeModel.getStairServiceChargeRuleList();
            timeFrameServiceChargeList = serviceChargeModel.getTimeFrameServiceChargeList();
            tvBasePrice.setText(StringUtils.BigDecimal2Str(serviceChargeModel.getBaseCharge())+"元");
            if(CheckUtils.isNoEmptyList(stairServiceChargeRuleList)){
                showDistanceAdditional(stairServiceChargeRuleList);
            }
            if(CheckUtils.isNoEmptyList(timeFrameServiceChargeList)){
                showSpecificTime(timeFrameServiceChargeList);
            }
        }
        if(mValueBean!=null){
            String timeFrameServiceChargeList = mValueBean.getTimeFrameServiceChargeList();
            ArrayList<Distance> distances = (ArrayList)JSONArray.parseArray(timeFrameServiceChargeList, Distance.class);
            ArrayList<SpecificTime> specificTimes = (ArrayList)JSONArray.parseArray(timeFrameServiceChargeList, SpecificTime.class);
            tvBasePrice.setText(mValueBean.getBaseCharge()+"元");
            if(CheckUtils.isNoEmptyList(distances)){
                showDistanceAdditional(distances);
            }
            if(CheckUtils.isNoEmptyList(specificTimes)){
                showSpecificTime(specificTimes);
            }
        }
        commonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    private void showDistanceAdditional(ArrayList<Distance> stairServiceChargeRuleList) {
        distanceAdditional.setVisibility(View.VISIBLE);
        for (int i = 1, size = stairServiceChargeRuleList.size(); i < size; i++) {
            Distance bean = stairServiceChargeRuleList.get(i);
            LinearLayout layout = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.distance_additional_item, null);
            TextView tvPrice = (TextView) layout.findViewById(R.id.tv_price);
            TextView tvDistance = (TextView) layout.findViewById(R.id.tv_distance);
            if(i==stairServiceChargeRuleList.size()-1){
                tvDistance.setText(bean.getMinDistance()+"公里以上");
                tvPrice.setText("每公里+" + StringUtils.BigDecimal2Str(bean.getCharge())+"元");
            }else {
                tvDistance.setText(bean.getMinDistance()+"-"+bean.getMaxDistance()+"公里的部分");
                tvPrice.setText("每公里+" + StringUtils.BigDecimal2Str(bean.getCharge())+"元");
            }
            distanceAdditional.addView(layout);
            if (i != size - 1) {
                View view = new View(mActivity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                layoutParams.setMargins(getResources().getDimensionPixelOffset(R.dimen.x15), getResources().getDimensionPixelOffset(R.dimen.x15),
                        getResources().getDimensionPixelOffset(R.dimen.x15), 0);
                view.setLayoutParams(layoutParams);
                distanceAdditional.addView(view);
            }
        }
    }
    private void showSpecificTime(ArrayList<SpecificTime> timeFrameServiceChargeList) {
        specialTime.setVisibility(View.VISIBLE);
        for (int i = 0, size = timeFrameServiceChargeList.size(); i < size; i++) {
            SpecificTime bean = timeFrameServiceChargeList.get(i);
            LinearLayout layout = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.specific_time_item, null);
            TextView tvLeft = (TextView) layout.findViewById(R.id.tv_left);
            TextView tvRight = (TextView) layout.findViewById(R.id.tv_right);
            tvLeft.setText(bean.getDeliveryTimeRange());
            tvRight.setText("+" + StringUtils.BigDecimal2Str(bean.getTimeAddCharge())+"元");
            specialTime.addView(layout);
            if (i != size - 1) {
                View view = new View(mActivity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                layoutParams.setMargins(getResources().getDimensionPixelOffset(R.dimen.x15), getResources().getDimensionPixelOffset(R.dimen.x15),
                        getResources().getDimensionPixelOffset(R.dimen.x15), 0);
                view.setLayoutParams(layoutParams);
                specialTime.addView(view);
            }
        }
    }

}
