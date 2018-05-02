package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.LogoutModel;
import com.project.mgjandroid.model.PushSetModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;

/**
 * Created by User_Cjh on 2017/7/18.
 */

public class PushSettingActivity extends BaseActivity {
    @InjectView(R.id.push_back)
    private ImageView ivBack;
    @InjectView(R.id.push_setting_layout)
    private RelativeLayout rlPush;
    private MLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_settting);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        loadingDialog = new MLoadingDialog();
        getPushStatus();
        rlPush.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private void getPushStatus() {
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<PushSetModel> operater = new VolleyOperater<>(this);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pushType", 1);
        operater.doRequest(Constants.URL_FIND_PUSH_SET, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    int isAble = ((PushSetModel) obj).getValue().getIsAble();
                    if (isAble == 0) {
                        rlPush.setSelected(false);
                    } else {
                        rlPush.setSelected(true);
                    }
                    PreferenceUtils.savePushSwitch(rlPush.isSelected(), mActivity);
                }
            }
        }, PushSetModel.class);
    }

    private void updatePushStatus() {
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<PushSetModel> operater = new VolleyOperater<>(this);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pushType", 1);
        if (rlPush.isSelected()) {
            map.put("isAble", 0);
        } else {
            map.put("isAble", 1);
        }
        operater.doRequest(Constants.URL_UPDATE_USER_PUSH_SET, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    int isAble = ((PushSetModel) obj).getValue().getIsAble();
                    if (isAble == 0) {
                        rlPush.setSelected(false);
                    } else {
                        rlPush.setSelected(true);
                    }
                    PreferenceUtils.savePushSwitch(rlPush.isSelected(), mActivity);
                }
            }
        }, PushSetModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.push_back:
                finish();
                break;
            case R.id.push_setting_layout:
                updatePushStatus();
                break;
        }
    }
}
