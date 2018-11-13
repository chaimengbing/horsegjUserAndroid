package com.project.mgjandroid.ui.activity.legwork;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.adapter.RiderEvaluationAdapter;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.RatingBarView;
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
    @InjectView(R.id.rbv_overall_evaluation)
    private RatingBarView rbvOverallEvaluation;

    private String agentId;
    private String orderId;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_legwork_evaluate);
        Injector.get(this).inject();
        agentId = getIntent().getStringExtra("agentId");
        orderId = getIntent().getStringExtra("orderId");
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvEvalution.setOnClickListener(this);
        tvTitle.setText("评价");
        rbvOverallEvaluation.setRating(5);
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

    private void getData() {
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        HashMap<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("orderId", orderId);
//        if (rbvOverallEvaluation.getRating() <= 0) {
//            ToastUtils.displayMsg("请您评分", this);
//            return;
//        } else {
//            map.put("score", rbvOverallEvaluation.getRating());
//        }
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
//                riderEvaluationAdapter.setList(badList);
                break;
            case R.id.layout_ordinary:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(true);
                imgGood.setSelected(false);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.color_6));
//                riderEvaluationAdapter.setList(goodList);
                break;
            case R.id.layout_good:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(false);
                imgGood.setSelected(true);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
//                riderEvaluationAdapter.setList(goodList);
                break;
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.tv_to_evaluation:
                getData();
                break;
        }
    }
}
