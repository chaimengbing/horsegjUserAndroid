package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.ui.activity.renthouse.RentHouseActivity;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

@Router("leaseHouses")
public class ChooseRentHouseActivity extends BaseActivity {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.iv_type_1)
    private ImageView ivType1;
    @InjectView(R.id.iv_type_2)
    private ImageView ivType2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_rent_house);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        ivType1.setOnClickListener(this);
        ivType2.setOnClickListener(this);
        tvTitle.setText("选择信息类别");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.iv_type_1:
                ChooseCityModel.getInstance().setSecondHandType(2);
                Intent intent1 = new Intent(mActivity, RentHouseActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_type_2:
                ChooseCityModel.getInstance().setSecondHandType(1);
                Intent intent2 = new Intent(mActivity, RentHouseActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
