package com.project.mgjandroid.ui.activity.yellowpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.yellowpage.YellowPage;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.yellowpage.YellowPageDetailModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User_Cjh on 2017/6/14.
 */

public class YellowPageDetailActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.yellow_page_detail_logo)
    private CornerImageView ivLogo;
    @InjectView(R.id.yellow_page_detail_name)
    private TextView tvName;
    @InjectView(R.id.yellow_page_detail_phone_layout)
    private LinearLayout llPhone;
    @InjectView(R.id.yellow_page_detail_address)
    private TextView tvAddress;
    @InjectView(R.id.yellow_page_detail_location)
    private ImageView ivLocation;

    private LoadingDialog loadingDialog;
    private YellowPage yellowPage;
    private CallPhoneDialog callPhoneDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_yellow_page_detail);
        Injector.get(this).inject();
        loadingDialog = new LoadingDialog(mActivity);
        loadingDialog.show();
        initView();
    }

    private void getData(long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        VolleyOperater<YellowPageDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_YELLOW_PAGE_BY_ID, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    YellowPageDetailModel model = (YellowPageDetailModel) obj;
                    yellowPage = model.getValue();
                    showDetail(model.getValue());
                }
            }
        }, YellowPageDetailModel.class);
    }

    private void showDetail(YellowPage value) {
        ImageUtils.loadBitmap(mActivity, value.getLogoPath(), ivLogo, R.drawable.horsegj_default, Constants.getEndThumbnail(55, 55));
        tvName.setText(value.getMerchantName());
        tvAddress.setText(value.getAddress());
        String mobiles = value.getMobiles();
        String[] arrays = mobiles.split(";");
        for (final String mobile : arrays) {
            if (CheckUtils.isEmptyStr(mobile) || ":".equals(mobile.trim())) {
                continue;
            }
            View view = getLayoutInflater().inflate(R.layout.item_yellow_page_detail, null);
            TextView tvMsg = (TextView) view.findViewById(R.id.yellow_page_detail_message);
            TextView tvDesc = (TextView) view.findViewById(R.id.yellow_page_detail_desc);
            ImageView ivCall = (ImageView) view.findViewById(R.id.yellow_page_detail_call);
            if (mobile.contains(":")) {
                tvMsg.setText(mobile.split(":")[0]);
                tvDesc.setText(mobile.split(":")[1]);
            } else {
                tvMsg.setText(mobile);
                tvDesc.setVisibility(View.GONE);
            }
            ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mobile.contains(":")) {
                        callPhoneDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                            @Override
                            public void onSure() {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.DIAL");
                                intent.setData(Uri.parse("tel:" + mobile.split(":")[1]));
                                mActivity.startActivity(intent);
                                callPhoneDialog.dismiss();
                            }

                            @Override
                            public void onExit() {
                                callPhoneDialog.dismiss();
                            }
                        }, "", mobile.split(":")[1]);
                    } else {
                        callPhoneDialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                            @Override
                            public void onSure() {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.DIAL");
                                intent.setData(Uri.parse("tel:" + mobile));
                                mActivity.startActivity(intent);
                                callPhoneDialog.dismiss();
                            }

                            @Override
                            public void onExit() {
                                callPhoneDialog.dismiss();
                            }
                        }, "", mobile);
                    }
                    callPhoneDialog.show();
                }
            });
            llPhone.addView(view);
        }
    }

    private void initView() {
        if (getIntent() != null && getIntent().getLongExtra("id", -1) != -1) {
            getData(getIntent().getLongExtra("id", -1));
        } else {
            finish();
            return;
        }
        ivBack.setOnClickListener(this);
        ivLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.yellow_page_detail_location:
                YellowPageMapActivity.toYellowPageMapActivity(mActivity, yellowPage.getMerchantName(), yellowPage.getAddress(), yellowPage.getLatitude(), yellowPage.getLongitude());
                break;
        }
    }
}
