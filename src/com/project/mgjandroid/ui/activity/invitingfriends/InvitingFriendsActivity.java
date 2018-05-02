package com.project.mgjandroid.ui.activity.invitingfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by SunXueLiang on 2017-07-17.
 */
@Router(value = "cashback")
public class InvitingFriendsActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.tv_mode)
    private TextView tvMode;
    @InjectView(R.id.tv_results)
    private TextView tvResults;
    private InvitingModeFragment invitingModeFragment;
    private InvitingResultsFragment resultsFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_inviting_friends);
        Injector.get(this).inject();
        if (!App.isLogin()) {
            startActivity(new Intent(this, SmsLoginActivity.class));
            finish();
            return;
        }
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvMode.setOnClickListener(this);
        tvResults.setOnClickListener(this);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        invitingModeFragment = new InvitingModeFragment();
        resultsFragment = new InvitingResultsFragment();
        ft.add(R.id.layout_fragment, invitingModeFragment).show(invitingModeFragment);
        ft.add(R.id.layout_fragment, resultsFragment).hide(resultsFragment);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.tv_mode:
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.show(invitingModeFragment).hide(resultsFragment).commit();
                tvMode.setTextColor(this.getResources().getColor(R.color.inviting_title_mode));
                tvMode.setBackgroundColor(this.getResources().getColor(R.color.white));
                tvResults.setTextColor(this.getResources().getColor(R.color.color_6));
                tvResults.setBackgroundColor(this.getResources().getColor(R.color.color_f5));
                break;
            case R.id.tv_results:
                FragmentManager manager1 = getSupportFragmentManager();
                FragmentTransaction ft1 = manager1.beginTransaction();
                ft1.show(resultsFragment).hide(invitingModeFragment).commit();
                tvResults.setTextColor(this.getResources().getColor(R.color.inviting_title_mode));
                tvResults.setBackgroundColor(this.getResources().getColor(R.color.white));
                tvMode.setTextColor(this.getResources().getColor(R.color.color_6));
                tvMode.setBackgroundColor(this.getResources().getColor(R.color.color_f5));
                break;
        }
    }

}
