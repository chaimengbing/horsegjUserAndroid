package com.project.mgjandroid.ui.activity.carhailing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.carhailing.CarHailingDriver;
import com.project.mgjandroid.bean.carhailing.CarHailingOrder;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ErrorModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.model.carhailing.EvaluationModel;
import com.project.mgjandroid.model.carhailing.RedBagPromotionModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.OrderDetailActivity;
import com.project.mgjandroid.ui.view.CallPhoneDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/12/7.
 */
public class CarHailingEvaluateActivity extends BaseActivity {

    @InjectView(R.id.photo_back)
    private ImageView ivBack;
    @InjectView(R.id.car_hailing_evaluate_driver_header)
    private RoundImageView ivHeader;
    @InjectView(R.id.car_hailing_detail_order_driver_name)
    private TextView tvName;
    @InjectView(R.id.car_hailing_detail_order_driver_car_id)
    private TextView tvCarId;
    @InjectView(R.id.car_hailing_detail_order_driver_score)
    private RatingBar rbScore;
    @InjectView(R.id.car_hailing_detail_order_driver_tv_score)
    private TextView tvScore;
    @InjectView(R.id.car_hailing_detail_order_driver_car_type)
    private TextView tvCarType;
    @InjectView(R.id.car_hailing_evaluate_call)
    private ImageView ivCall;
    @InjectView(R.id.evaluate_redbag_notice)
    private ImageView ivNotice;
    @InjectView(R.id.car_hailing_total_evaluate)
    private RatingBar rbTotal;
    @InjectView(R.id.car_hailing_service_evaluate)
    private RatingBar rbService;
    @InjectView(R.id.car_hailing_car_evaluate)
    private RatingBar rbCar;
    @InjectView(R.id.car_hailing_drive_evaluate)
    private RatingBar rbDrive;
    @InjectView(R.id.anonymous_layout)
    private LinearLayout llAnonymous;
    @InjectView(R.id.car_hailing_evaluate_text)
    private EditText etContent;
    @InjectView(R.id.car_hailing_evaluate_anonymous)
    private ImageView ivAnonymous;
    @InjectView(R.id.confirm_submit_evaluate)
    private TextView tvEvaluate;

    private CarHailingOrder chauffeurOrder;
    private boolean isAnonymous = true;
    private String mobile;
    private CallPhoneDialog dialog;
    DecimalFormat format = new DecimalFormat("0.0");
    private MLoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hailing_evaluate);
        Injector.get(this).inject();
        initView();
        setLinstener();
//        findOrderAgentCarRedBagPromotion();
    }

    private void setLinstener() {
        ivBack.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        llAnonymous.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
    }

    private void initView() {
        chauffeurOrder = (CarHailingOrder) getIntent().getSerializableExtra("chauffeurOrder");
        if (chauffeurOrder == null) {
            finish();
            return;
        }
        mLoadingDialog = new MLoadingDialog();
        setViewData();
    }

    private void setViewData() {
        CarHailingDriver driver = chauffeurOrder.getChauffeur();
        if (CheckUtils.isNoEmptyStr(driver.getHeaderImg())) {
            ImageUtils.loadBitmap(mActivity, driver.getHeaderImg(), ivHeader, R.drawable.horsegj_default, Constants.getEndThumbnail(55, 55));
        }
        tvName.setText(driver.getName());
        tvCarId.setText(driver.getCarNumber());
        rbScore.setRating(driver.getAverageScore().floatValue());
        tvScore.setText(format.format(driver.getAverageScore()));
        tvCarType.setText(driver.getCarColor() + "·" + driver.getCarSeries());
        mobile = driver.getMobile();
    }

    private void submitEvaluate() {
        mLoadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", chauffeurOrder.getAgentId());
        map.put("chauffeurId", chauffeurOrder.getChauffeurId());
        map.put("chauffeurTripId", chauffeurOrder.getChauffeurTripId());
        map.put("chauffeurOrderId", chauffeurOrder.getId());
        if (isAnonymous)
            map.put("anonymous", 0);
        else
            map.put("anonymous", 1);
        map.put("compositeScore", rbTotal.getRating());
//        if(rbService.getRating() == 0)
//            map.put("serviceScore", 5);
//        else
        map.put("serviceScore", rbService.getRating());
//        if(rbCar.getRating() == 0)
//            map.put("carConditionScore", 5);
//        else
        map.put("carConditionScore", rbCar.getRating());
//        if(rbDrive.getRating() == 0)
//            map.put("driveScore", 5);
//        else
        map.put("driveScore", rbDrive.getRating());
        map.put("chauffeurOrderScoreComments", etContent.getText().toString());
        map.put("userName", App.getUserInfo().getName());
        map.put("userHeaderImg", App.getUserInfo().getHeaderImg());
        VolleyOperater<EvaluationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CHAUFFEUR_ORDER_COMMENT, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    if (obj instanceof EvaluationModel && ((EvaluationModel) obj).getCode() == 0) {
                        setResult(RESULT_OK);
                        if (getIntent().getBooleanExtra("isFromOrderList", false)) {
                            Intent intentDetail = new Intent(mActivity, CarHailingOrderDetailActivity.class);
                            intentDetail.putExtra(OrderDetailActivity.ORDER_ID, chauffeurOrder.getId());
                            intentDetail.putExtra("isFromOrderList", true);
                            startActivity(intentDetail);
                        }
                        CarHailingEvaluateActivity.this.finish();
                    }
                }
            }
        }, EvaluationModel.class);
    }

    private void findOrderAgentCarRedBagPromotion() {
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", chauffeurOrder.getAgentId());
        VolleyOperater<RedBagPromotionModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_ORDER_AGENT_CAR_REDBAG_PROMOTION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof RedBagPromotionModel && ((RedBagPromotionModel) obj).getCode() == 0) {
                        ivNotice.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, RedBagPromotionModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_back:
                onBackPressed();
                break;
            case R.id.confirm_submit_evaluate:
                if (rbTotal.getRating() == 0) {
                    toast("请对总体评价打分");
                    break;
                }
                submitEvaluate();
                break;
            case R.id.anonymous_layout:
                if (isAnonymous) {
                    isAnonymous = false;
                    ivAnonymous.setImageResource(R.drawable.icon_evaluate_anonymous_uncheck);
                } else {
                    isAnonymous = true;
                    ivAnonymous.setImageResource(R.drawable.icon_evaluate_anonymous_checked);
                }
                break;
            case R.id.car_hailing_evaluate_call:
                if (dialog == null && CheckUtils.isNoEmptyStr(mobile)) {
                    dialog = new CallPhoneDialog(mActivity, new CallPhoneDialog.onBtnClickListener() {
                        @Override
                        public void onSure() {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.DIAL");
                            intent.setData(Uri.parse("tel:" + mobile));
                            mActivity.startActivity(intent);
                            dialog.dismiss();
                        }

                        @Override
                        public void onExit() {
                            dialog.dismiss();
                        }
                    }, "", mobile);
                }
                dialog.show();
                break;
        }
    }
}
