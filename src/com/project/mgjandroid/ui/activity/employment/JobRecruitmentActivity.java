package com.project.mgjandroid.ui.activity.employment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.MyPublishCategoryActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.information.InformationActivity;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by SunXueLiang on 2017-07-10.
 */
@Router("wholeJobHunting")
public class JobRecruitmentActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_my_publish)
    private TextView tvMyPublish;
    @InjectView(R.id.layout_job_recruitment_1)
    private LinearLayout job;
    @InjectView(R.id.layout_job_recruitment_2)
    private LinearLayout personnel;
    @InjectView(R.id.layout_job_recruitment_3)
    private LinearLayout resume;
    @InjectView(R.id.layout_job_recruitment_4)
    private LinearLayout recruit;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_job_recruitment);
        Injector.get(mActivity).inject();
        initView();
    }

    private void initView() {
        tvTitle.setText("求职招聘");
        ivBack.setOnClickListener(this);
        tvMyPublish.setOnClickListener(this);
        job.setOnClickListener(this);
        personnel.setOnClickListener(this);
        resume.setOnClickListener(this);
        recruit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.tv_my_publish:
                if (App.isLogin()) {
                    startActivity(new Intent(this, MyPublishCategoryActivity.class));
                } else {
                    startActivity(new Intent(this, SmsLoginActivity.class));
                }
                break;
            case R.id.layout_job_recruitment_1:
                startActivity(new Intent(this, InformationActivity.class).putExtra("informationType", InformationType.Recruit.getValue()));
                break;
            case R.id.layout_job_recruitment_2:
                startActivity(new Intent(this, InformationActivity.class).putExtra("informationType", InformationType.Position.getValue()));
                break;
            case R.id.layout_job_recruitment_3:
                if (App.isLogin()) {
                    startActivity(new Intent(this, PublishResumeActivity.class));
                } else {
                    startActivity(new Intent(this, SmsLoginActivity.class));
                }
                break;
            case R.id.layout_job_recruitment_4:
                if (App.isLogin()) {
                    startActivity(new Intent(this, PublishRecruitmentActivity.class));
                } else {
                    startActivity(new Intent(this, SmsLoginActivity.class));
                }
                break;
        }
    }
}
