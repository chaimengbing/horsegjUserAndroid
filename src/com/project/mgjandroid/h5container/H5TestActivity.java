package com.project.mgjandroid.h5container;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.LogoutModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by User_Cjh on 2017/12/27.
 */

public class H5TestActivity extends BaseActivity {

    @InjectView(R.id.et_input_h5)
    EditText editText;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_h5_test);
        Injector.get(this).inject();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(H5TestActivity.this, YLBWebViewActivity.class);
                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, "file:///android_asset/index.html");
                startActivity(intent);
            }
        });

        editText = (EditText) findViewById(R.id.et_input_h5);
        findViewById(R.id.btn_h5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                    Intent intent = new Intent(H5TestActivity.this, YLBWebViewActivity.class);
                    intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, editText.getText().toString());
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.btn_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(H5TestActivity.this, YLBWebViewActivity.class);
                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, "http://112.74.18.147/sfc/build/index.html#/");
//                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, "http://192.168.199.122:3000/#/");
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_farm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(H5TestActivity.this, YLBWebViewActivity.class);
                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, "http://mgj.118.aoyacms.com/plugin/aoya/mgj/index.php");
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
    }

    /**
     * 登出
     */
    private void exit() {
        VolleyOperater<LogoutModel> operater = new VolleyOperater<LogoutModel>(this);
        operater.doRequest(Constants.URL_EXIT_OUT, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    App.setIsLogin(false);
                    App.setUserInfo(null);
                    PreferenceUtils.saveStringPreference("token", "", H5TestActivity.this);
                    Toast.makeText(H5TestActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                }
            }
        }, LogoutModel.class);
    }
}
