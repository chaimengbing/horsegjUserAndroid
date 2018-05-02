package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.BuildConfig;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.about_back)
    private ImageView aboutBack;
    @InjectView(R.id.about_version_code)
    private TextView tvCode;
    @InjectView(R.id.about_business)
    private View business;
    @InjectView(R.id.user_service_protocol)
    private TextView tvProtocol;
    @InjectView(R.id.disclaimer)
    private TextView tvDisclaimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Injector.get(this).inject();

        aboutBack.setOnClickListener(this);
        business.setOnClickListener(this);
        tvProtocol.setOnClickListener(this);
        tvDisclaimer.setOnClickListener(this);
        tvCode.setText(getString(R.string.v) + CommonUtils.getCurrentVersionName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_back:
                finish();
                break;
            case R.id.about_business:
                if (BuildConfig.IS_DEBUG) {
                    toWebView(getString(R.string.shangwu_hezuo), "http://120.24.16.64/maguanjia/Buscooperation.html");
                } else {
                    toWebView(getString(R.string.shangwu_hezuo), "http://123.56.15.86/maguanjia/Buscooperation.html");
                }
                break;
            case R.id.user_service_protocol:
                if (BuildConfig.IS_DEBUG) {
                    toWebView(getString(R.string.yonghu_fuwu_xieyi), "http://120.24.16.64/maguanjia/ItemAndhttp.html");
                } else {
                    toWebView(getString(R.string.yonghu_fuwu_xieyi), "http://123.56.15.86/maguanjia/ItemAndhttp.html");
                }
                break;
            case R.id.disclaimer:
                if (BuildConfig.IS_DEBUG) {
                    toWebView(getString(R.string.disclaimer), "http://120.24.16.64/maguanjia/disclaimer.html");
                } else {
                    toWebView(getString(R.string.disclaimer), "http://123.56.15.86/maguanjia/disclaimer.html");
                }
                break;
        }
    }

    private void toWebView(String name, String url) {
//        Intent intent = new Intent(mActivity, Banner2WebActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("name", name);
//        bundle.putString("url", url);
//        intent.putExtras(bundle);
//        startActivity(intent);
        Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
        intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, url);
        startActivity(intent);
    }
}
