package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class DiscountBuyTicketActivity extends BaseActivity{

    @InjectView(R.id.common_back)
    private ImageView tvBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_publish)
    private TextView tvRight;
    private String titleName;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_discount_buy_ticket);
        Injector.get(this).inject();
        initView();
    }

    private void initView(){
        titleName = getIntent().getStringExtra("Name");
        tvTitle.setText(titleName);
        tvBack.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.common_back:
                back();
            case R.id.tv_publish:
                startActivity(new Intent(this,GroupBuyingPrivilegeActivity.class));
            break;
        }
    }
}
