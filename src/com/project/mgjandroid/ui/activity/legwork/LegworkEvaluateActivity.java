package com.project.mgjandroid.ui.activity.legwork;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.LegworkOrderDetailsModel;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingDeliverymanImpress;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.adapter.RiderEvaluationAdapter;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.RatingBarView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SunXueLiang on 2018-03-12.
 */

public class LegworkEvaluateActivity extends BaseActivity {


    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_to_evaluation)
    private TextView tvEvalution;
    @InjectView(R.id.et_company_profiles)
    private EditText etCompanyProfiles;
    @InjectView(R.id.tv_profiles_length)
    private TextView tvProfilesLength;
    @InjectView(R.id.grid_view)
    private NoScrollGridView gridView;
    @InjectView(R.id.layout_bad)
    private LinearLayout layoutBad;
    @InjectView(R.id.layout_ordinary)
    private LinearLayout layoutOrdinary;
    @InjectView(R.id.layout_good)
    private LinearLayout layoutGood;
    @InjectView(R.id.img_bad)
    private ImageView imgBad;
    @InjectView(R.id.img_ordinary)
    private ImageView imgOrdinary;
    @InjectView(R.id.img_good)
    private ImageView imgGood;
    @InjectView(R.id.tv_bad)
    private TextView tvBad;
    @InjectView(R.id.tv_ordinary)
    private TextView tvOrdinary;
    @InjectView(R.id.tv_good)
    private TextView tvGood;
    @InjectView(R.id.rider_avatar)
    private CornerImageView riderAvatar;
    @InjectView(R.id.tv_rider_name)
    private TextView tvRiderName;
    @InjectView(R.id.tv_delivery_time)
    private TextView tvDeliveryTime;

    private String agentId;
    private String orderId;
    private RiderEvaluationAdapter riderEvaluationAdapter;
    private TextView textView;
    private String startText = "";
    private String endText = "";
    private ArrayList<GroupBuyingDeliverymanImpress> badList = new ArrayList<>();
    private ArrayList<GroupBuyingDeliverymanImpress> goodList = new ArrayList<>();
    private NewOrderFragmentModel.ValueEntity value;
    private LegworkOrderDetailsModel.ValueBean valurDetails;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_legwork_evaluate);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvEvalution.setOnClickListener(this);
        layoutBad.setOnClickListener(this);
        layoutOrdinary.setOnClickListener(this);
        layoutGood.setOnClickListener(this);
        tvTitle.setText("评价");
        Intent intent = getIntent();
        agentId = intent.getStringExtra("agentId");
        orderId = intent.getStringExtra("orderId");
        if(intent!=null&&intent.hasExtra("value")){
            value = (NewOrderFragmentModel.ValueEntity) intent.getSerializableExtra("value");
        }
        if(intent!=null&&intent.hasExtra("valurDetails")){
            valurDetails = (LegworkOrderDetailsModel.ValueBean) intent.getSerializableExtra("valurDetails");
        }
        String[] bad = getResources().getStringArray(R.array.badList);
        String[] good = getResources().getStringArray(R.array.goodList);

        for (String s : bad) {
            GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress = new GroupBuyingDeliverymanImpress();
            groupBuyingDeliverymanImpress.setImpress(s);
            badList.add(groupBuyingDeliverymanImpress);
        }
        for (String s : good) {
            GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress = new GroupBuyingDeliverymanImpress();
            groupBuyingDeliverymanImpress.setImpress(s);
            goodList.add(groupBuyingDeliverymanImpress);
        }
        if(value!=null){
            tvRiderName.setText(value.getLegWorkOrder().getDeliveryTask().getDeliveryman().getName());
            ImageUtils.loadBitmap(mActivity, value.getLegWorkOrder().getDeliveryTask().getDeliveryman().getHeaderImg().split(";")[0], riderAvatar, R.drawable.horsegj_default, Constants.getEndThumbnail(56, 56));
            tvDeliveryTime.setText(value.getLegWorkOrder().getOrderDoneTime());
        }else if(valurDetails!=null){
            tvRiderName.setText(valurDetails.getDeliveryTask().getDeliveryman().getName());
            ImageUtils.loadBitmap(mActivity, valurDetails.getDeliveryTask().getDeliveryman().getHeaderImg().split(";")[0], riderAvatar, R.drawable.horsegj_default, Constants.getEndThumbnail(56, 56));
            tvDeliveryTime.setText(valurDetails.getOrderDoneTime());
        }

        riderEvaluationAdapter = new RiderEvaluationAdapter(this);
        gridView.setAdapter(riderEvaluationAdapter);
        riderEvaluationAdapter.setList(null);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView = (TextView) view.findViewById(R.id.textview);
                textView.setSelected(!textView.isSelected());
                GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress = (GroupBuyingDeliverymanImpress) riderEvaluationAdapter.getItem(i);
                groupBuyingDeliverymanImpress.setChecked(!groupBuyingDeliverymanImpress.isChecked());
                riderEvaluationAdapter.notifyDataSetChanged();

                if (groupBuyingDeliverymanImpress.isChecked()) {
                    if (imgBad.isSelected()) {
                        startText += i + 1 + ",";
                        endText = startText.substring(0, startText.lastIndexOf(","));
                    }
                    if (imgOrdinary.isSelected()) {
                        startText += i + 1 + ",";
                        endText = startText.substring(0, startText.lastIndexOf(","));
                    }
                    if (imgGood.isSelected()) {
                        startText += i + 1 + ",";
                        endText = startText.substring(0, startText.lastIndexOf(","));
                    }
                }
            }
        });
        etCompanyProfiles.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvProfilesLength.setText(s.toString().trim().length() + "/300");
            }
        });
    }

    //能否发布
    private boolean checkCanEvaluate() {
        if (!imgBad.isSelected() && !imgOrdinary.isSelected() && !imgGood.isSelected()) {
            ToastUtils.displayMsg("评价不完整,请继续评价", LegworkEvaluateActivity.this);
            return false;
        }
        if (CheckUtils.isEmptyStr(endText)) {
            ToastUtils.displayMsg("评价不完整,请继续评价", LegworkEvaluateActivity.this);
            return false;
        }
        return true;
    }

    private void getData() {
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("orderId", orderId);
        map.put("deliverymanImpress", orderId);
        if (imgBad.isSelected()) {
            map.put("score", 1);
        }
        if (imgOrdinary.isSelected()) {
            map.put("score", 3);
        }
        if (imgGood.isSelected()) {
            map.put("score", 5);
        }
        map.put("deliverymanImpress", endText);
        map.put("content", etCompanyProfiles.getText().toString().trim());
        operater.doRequest(Constants.URL_CREATE_COMMENTS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    toast("评价成功");
                    finish();

                }
            }
        }, Object.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layout_bad:
                imgBad.setSelected(true);
                imgOrdinary.setSelected(false);
                imgGood.setSelected(false);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                riderEvaluationAdapter.setList(badList);
                for (GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress : riderEvaluationAdapter.getList()) {
                    groupBuyingDeliverymanImpress.setChecked(false);
                    endText = "";
                }
                break;
            case R.id.layout_ordinary:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(true);
                imgGood.setSelected(false);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                riderEvaluationAdapter.setList(goodList);
                for (GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress : riderEvaluationAdapter.getList()) {
                    groupBuyingDeliverymanImpress.setChecked(false);
                    endText = "";
                }
                break;
            case R.id.layout_good:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(false);
                imgGood.setSelected(true);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                riderEvaluationAdapter.setList(goodList);
                for (GroupBuyingDeliverymanImpress groupBuyingDeliverymanImpress : riderEvaluationAdapter.getList()) {
                    groupBuyingDeliverymanImpress.setChecked(false);
                    endText = "";
                }
                break;
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.tv_to_evaluation:
                if(checkCanEvaluate()){
                    getData();
                }
                break;
        }
    }

}
