package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.adapter.RiderEvaluationAdapter;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;

public class NewEvaluateActivity extends BaseActivity{

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
    @InjectView(R.id.grid_view)
    private NoScrollGridView gridView;
    private ArrayList<String> badList = new ArrayList<>();
    private ArrayList<String> goodList = new ArrayList<>();
    private RiderEvaluationAdapter riderEvaluationAdapter;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_new_evaluate);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        layoutBad.setOnClickListener(this);
        layoutOrdinary.setOnClickListener(this);
        layoutGood.setOnClickListener(this);
        badList.add("提前点送达");
        badList.add("服务态度差");
        badList.add("餐品翻撒");
        badList.add("送餐慢");
        badList.add("着装脏乱");
        badList.add("沟通困难");
        goodList.add("穿戴工服");
        goodList.add("风雨无阻");
        goodList.add("快速准时");
        goodList.add("仪容整洁");
        goodList.add("货品完好");
        goodList.add("礼貌热情");
        riderEvaluationAdapter = new RiderEvaluationAdapter(this);
        gridView.setAdapter(riderEvaluationAdapter);
        riderEvaluationAdapter.setList(null);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.layout_bad:
                imgBad.setSelected(true);
                imgOrdinary.setSelected(false);
                imgGood.setSelected(false);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                riderEvaluationAdapter.setList(badList);
                break;
            case R.id.layout_ordinary:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(true);
                imgGood.setSelected(false);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                riderEvaluationAdapter.setList(goodList);
                break;
            case R.id.layout_good:
                imgBad.setSelected(false);
                imgOrdinary.setSelected(false);
                imgGood.setSelected(true);
                tvBad.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvOrdinary.setTextColor(mActivity.getResources().getColor(R.color.color_6));
                tvGood.setTextColor(mActivity.getResources().getColor(R.color.bg_festival));
                riderEvaluationAdapter.setList(goodList);
                break;
        }
    }
}
