package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.NewOrderFragmentModel;
import com.project.mgjandroid.model.OrderEvaluateModel;
import com.project.mgjandroid.model.OrderFragmentModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RatingBarView;
import com.project.mgjandroid.ui.view.WheelView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.ViewFindUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluateActivity extends BaseActivity implements View.OnClickListener, RatingBarView.OnRatingListener, View.OnFocusChangeListener {
    @InjectView(R.id.evaluate_back)
    private ImageView evaluateBack;
    @InjectView(R.id.to_evaluate)
    private TextView tvEvaluate;
    @InjectView(R.id.evaluate_list_view)
    private ScrollView mScrollView;
    @InjectView(R.id.all_text_view)
    private EditText etAllEvaluate;
    @InjectView(R.id.driver_text_view)
    private EditText etDriverEvaluate;
    @InjectView(R.id.all_rat_score)
    private RatingBarView rbAll;
    @InjectView(R.id.driver_rat_score)
    private RatingBarView rbDriver;
    @InjectView(R.id.evaluate_container)
    private LinearLayout itemContainer;
    @InjectView(R.id.all_evaluate_describe)
    private TextView tvAllDesc;
    @InjectView(R.id.driver_evaluate_describe)
    private TextView tvDriverDesc;
    @InjectView(R.id.all_text_view_layout)
    private LinearLayout llAllLayout;
    @InjectView(R.id.driver_text_view_layout)
    private LinearLayout llDriverLayout;
    @InjectView(R.id.all_evaluate_count)
    private TextView tvAllCount;
    @InjectView(R.id.driver_evaluate_count)
    private TextView tvDriverCount;
    private String orderId;
    private WheelView wheelView;
    private TextView tvSelected;

    private NewOrderFragmentModel.ValueEntity valueEntityEvaluate;
    @InjectView(R.id.evaluate_merchant_name)
    private TextView evaluateMerchantName;
    @InjectView(R.id.evaluate_icon)
    private ImageView evaluateMerchantIcon;
    @InjectView(R.id.driver_evaluate_layout)
    private RelativeLayout evaluateDriverLayout;
    private List<NewOrderFragmentModel.ValueEntity.OrderItemsEntity> orderItems;

    private List<Map<String, Object>> list;
    private int diliveryCost = 10;

    private boolean hasDriverEvaluate = true;
    private int prePosition = -1;
    private String preContent;
    private List<EditText> editTexts;
    private List<LinearLayout> contentLayouts;
    private MLoadingDialog loadingDialog;
    private Integer eTime;
    private SubmitOrderModel.ValueEntity submitOrderEntity;
    private boolean isFromOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        loadingDialog = new MLoadingDialog();
        editTexts = new ArrayList<>();
        contentLayouts = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("orderId")) {
            orderId = intent.getStringExtra("orderId");
            valueEntityEvaluate = (NewOrderFragmentModel.ValueEntity) intent.getSerializableExtra("valueEntity");
            submitOrderEntity = (SubmitOrderModel.ValueEntity) intent.getSerializableExtra("submitOrderEntity");
            if (valueEntityEvaluate != null) {
                isFromOrderList = true;
                orderItems = valueEntityEvaluate.getOrderItems();
            } else {
                isFromOrderList = false;
            }
            hasDriverEvaluate = intent.getBooleanExtra("hasDriver", false);
        }

        evaluateBack.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
//        data.add(new Entity());
//        data.add(new Entity());
//        data.add(new Entity());
//        data.add(new Entity());
//        data.add(new Entity());
//        data.add(new Entity());

        if (isFromOrderList && !TextUtils.isEmpty(valueEntityEvaluate.getMerchant().getLogo()))
            ImageUtils.loadBitmap(this, valueEntityEvaluate.getMerchant().getLogo(), evaluateMerchantIcon, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        else if (!isFromOrderList && !TextUtils.isEmpty(submitOrderEntity.getMerchant().getLogo()))
            ImageUtils.loadBitmap(this, submitOrderEntity.getMerchant().getLogo(), evaluateMerchantIcon, R.drawable.horsegj_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        else
            evaluateMerchantIcon.setImageResource(R.drawable.horsegj_default);
        if (isFromOrderList)
            evaluateMerchantName.setText(valueEntityEvaluate.getMerchant().getName());
        else
            evaluateMerchantName.setText(submitOrderEntity.getMerchant().getName());
        if (hasDriverEvaluate)
            evaluateDriverLayout.setVisibility(View.VISIBLE);

        else {
            evaluateDriverLayout.setVisibility(View.GONE);
        }

//        rbAll.setOnRatingListener(this);
//        rbDriver.setOnRatingListener(this);
        setEvaluateListener();
        wheelView = ViewFindUtils.find(mDecorView, R.id.wheel_view);
        wheelView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        tvSelected = ViewFindUtils.find(mDecorView, R.id.time_select);
        tvSelected.setOnClickListener(this);
        wheelView.setOffset(1);
        ArrayList<String> list = new ArrayList<>();
        if (isFromOrderList)
            eTime = valueEntityEvaluate.getMerchant().getAvgDeliveryTime();
        else
            eTime = submitOrderEntity.getMerchant().getAvgDeliveryTime();
        eTime = eTime / 10 * 10;
        for (int i = 1; i <= (eTime - 10) / 10; i++) {
            list.add(showWheelViewTime(10 * i));
        }
        list.add("按时送达");
        diliveryCost = eTime;
        for (int j = (eTime - 10) / 10 + 2; j < 13; j++) {
            if (j > 6 && j % 2 == 0) list.add(showWheelViewTime(10 * j));
            else if (j <= 6)
                list.add(showWheelViewTime(10 * j));
        }

//        for (int i = 1; i < 13; i++) {
//            list.add(showWheelViewTime(10 * i));
//        }
        wheelView.setItems(list);
        wheelView.setSeletion((eTime - 10) / 10 + 1);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                if (selectedIndex > 6)
                    diliveryCost = 20 * (selectedIndex - 6) + 60;
                else
                    diliveryCost = selectedIndex * 10;
                if (diliveryCost > 0) {
                    tvSelected.setText(getSongDaTime(diliveryCost).substring(11, 16) + "送达" + " ( " + item + " )");
                }
            }
        });

        if (isFromOrderList) {
            for (int i = 0; i < orderItems.size(); i++) {
                View itemView = mInflater.inflate(R.layout.item_evaluate_list_view, null);
                TextView tvName = (TextView) itemView.findViewById(R.id.list_item_name);
                final RatingBarView rbvScore = (RatingBarView) itemView.findViewById(R.id.driver_rat_score);
                final LinearLayout llContent = (LinearLayout) itemView.findViewById(R.id.evaluate_edit_text_layout);
                final EditText etContent = (EditText) itemView.findViewById(R.id.evaluate_edit_text);
                final TextView tvDesc = (TextView) itemView.findViewById(R.id.driver_rat_score_desc);
                final TextView tvCount = (TextView) itemView.findViewById(R.id.all_evaluate_count);
                editTexts.add(etContent);
                contentLayouts.add(llContent);
                etContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        tvCount.setText(s.length() + "/120");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                NewOrderFragmentModel.ValueEntity.OrderItemsEntity orderItemsEntity = orderItems.get(i);
                tvName.setText(orderItemsEntity.getName());
                rbvScore.setRating(orderItemsEntity.getRating());
                rbvScore.setBindObject(i);
                etContent.setTag(i);
                etContent.setText(orderItemsEntity.getContent());
                etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        int tag = (int) v.getTag();
                        orderItems.get(tag).setContent(etContent.getText().toString().trim());
                    }
                });
                rbvScore.setOnRatingListener(new RatingBarView.OnRatingListener() {
                    @Override
                    public void onRating(Object bindObject, int RatingScore, String content) {
                        if (etAllEvaluate.getVisibility() == View.VISIBLE && CheckUtils.isEmptyStr(etAllEvaluate.getText().toString().trim())) {
                            llAllLayout.setVisibility(View.GONE);
                        }
                        if (etDriverEvaluate.getVisibility() == View.VISIBLE && CheckUtils.isEmptyStr(etDriverEvaluate.getText().toString().trim())) {
                            llDriverLayout.setVisibility(View.GONE);
                        }
                        if (wheelView.getVisibility() == View.VISIBLE) {
                            wheelView.setVisibility(View.GONE);
                        }

                        llContent.setVisibility(View.VISIBLE);
                        etContent.setSelection(etContent.length());
                        etContent.requestFocus();
                        CommonUtils.showKeyBoard(etContent);
                        if (prePosition != -1 && prePosition != (int) bindObject) {
                            EditText editText = editTexts.get(prePosition);
                            LinearLayout linearLayout = contentLayouts.get(prePosition);
                            if (CheckUtils.isEmptyStr(editText.getText().toString().trim()))
                                linearLayout.setVisibility(View.GONE);
                        }

                        if (RatingScore >= 3) {
                            rbvScore.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_good_better_1));
                            etContent.setHint("说说哪里满意，帮大家选择");
                            tvDesc.setText("满意");
                        } else if (RatingScore > 1) {
                            rbvScore.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_good_normal_1));
                            etContent.setHint("说说哪里不满意，帮商家改进");
                            tvDesc.setText("一般");
                        } else {
                            rbvScore.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_good_normal_1));
                            etContent.setHint("说说哪里不满意，帮商家改进");
                            tvDesc.setText("一般");
                        }
                        rbvScore.setStar(RatingScore);
                        orderItems.get((int) bindObject).setRating(RatingScore);
                        prePosition = (int) bindObject;
                    }
                });
                itemContainer.addView(itemView);
            }
        } else {
            for (int i = 0; i < submitOrderEntity.getOrderItems().size(); i++) {
                View itemView = mInflater.inflate(R.layout.item_evaluate_list_view, null);
                TextView tvName = (TextView) itemView.findViewById(R.id.list_item_name);
                final RatingBarView rbvScore = (RatingBarView) itemView.findViewById(R.id.driver_rat_score);
                final LinearLayout llContent = (LinearLayout) itemView.findViewById(R.id.evaluate_edit_text_layout);
                final EditText etContent = (EditText) itemView.findViewById(R.id.evaluate_edit_text);
                final TextView tvDesc = (TextView) itemView.findViewById(R.id.driver_rat_score_desc);
                final TextView tvCount = (TextView) itemView.findViewById(R.id.all_evaluate_count);
                editTexts.add(etContent);
                contentLayouts.add(llContent);
                etContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        tvCount.setText(s.length() + "/120");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                SubmitOrderModel.ValueEntity.OrderItemsEntity orderItemsEntity = submitOrderEntity.getOrderItems().get(i);
                tvName.setText(orderItemsEntity.getName());
                rbvScore.setRating(orderItemsEntity.getRating());
                rbvScore.setBindObject(i);
                etContent.setTag(i);
                etContent.setText(orderItemsEntity.getContent());
                etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        int tag = (int) v.getTag();
                        submitOrderEntity.getOrderItems().get(tag).setContent(etContent.getText().toString().trim());
                    }
                });
                rbvScore.setOnRatingListener(new RatingBarView.OnRatingListener() {
                    @Override
                    public void onRating(Object bindObject, int RatingScore, String content) {
                        if (etAllEvaluate.getVisibility() == View.VISIBLE && CheckUtils.isEmptyStr(etAllEvaluate.getText().toString().trim())) {
                            llAllLayout.setVisibility(View.GONE);
                        }
                        if (etDriverEvaluate.getVisibility() == View.VISIBLE && CheckUtils.isEmptyStr(etDriverEvaluate.getText().toString().trim())) {
                            llDriverLayout.setVisibility(View.GONE);
                        }
                        if (wheelView.getVisibility() == View.VISIBLE) {
                            wheelView.setVisibility(View.GONE);
                        }

                        llContent.setVisibility(View.VISIBLE);
                        etContent.setSelection(etContent.length());
                        etContent.requestFocus();
                        CommonUtils.showKeyBoard(etContent);
                        if (prePosition != -1 && prePosition != (int) bindObject) {
                            EditText editText = editTexts.get(prePosition);
                            LinearLayout linearLayout = contentLayouts.get(prePosition);
                            if (CheckUtils.isEmptyStr(editText.getText().toString().trim()))
                                linearLayout.setVisibility(View.GONE);
                        }

                        if (RatingScore >= 3) {
                            rbvScore.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_good_better_1));
                            etContent.setHint("说说哪里满意，帮大家选择");
                            tvDesc.setText("满意");
                        } else if (RatingScore > 1) {
                            rbvScore.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_good_normal_1));
                            etContent.setHint("说说哪里不满意，帮商家改进");
                            tvDesc.setText("一般");
                        } else {
                            rbvScore.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_good_normal_1));
                            etContent.setHint("说说哪里不满意，帮商家改进");
                            tvDesc.setText("一般");
                        }
                        rbvScore.setStar(RatingScore);
                        submitOrderEntity.getOrderItems().get((int) bindObject).setRating(RatingScore);
                        prePosition = (int) bindObject;
                    }
                });
                itemContainer.addView(itemView);
            }
        }
    }

    private String showWheelViewTime(int time) {
        if (time == 120) {
            return "2小时以上";
        } else if (time > 60) {
            return time / 60 + "小时" + time % 60 + "分钟";
        } else if (time == 60) {
            return "1小时";
        }
        return time % 60 + "分钟";
    }

    private void setEvaluateListener() {
        rbAll.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore, String content) {
                if (wheelView.getVisibility() == View.VISIBLE) {
                    wheelView.setVisibility(View.GONE);
                }
                if (prePosition != -1) {
                    if (CheckUtils.isEmptyStr(editTexts.get(prePosition).getText().toString()))
                        contentLayouts.get(prePosition).setVisibility(View.GONE);
                    prePosition = -1;
                }
                llAllLayout.setVisibility(View.VISIBLE);
                if (CheckUtils.isEmptyStr(etDriverEvaluate.getText().toString().trim()))
                    llDriverLayout.setVisibility(View.GONE);
                etAllEvaluate.setSelection(etAllEvaluate.length());
                etAllEvaluate.requestFocus();
                CommonUtils.showKeyBoard(etAllEvaluate);
                if (RatingScore >= 3) {
                    rbAll.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_better_1));
                    etAllEvaluate.setHint("说说哪里满意，帮大家选择");
                    tvAllDesc.setText("满意");
                } else if (RatingScore > 1) {
                    rbAll.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_normal_1));
                    etAllEvaluate.setHint("说说哪里不满意，帮商家改进");
                    tvAllDesc.setText("一般");
                } else {
                    rbAll.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_normal_1));
                    etAllEvaluate.setHint("说说哪里不满意，帮商家改进");
                    tvAllDesc.setText("一般");
                }
                rbAll.setStar(RatingScore);
            }
        });
        rbDriver.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore, String content) {
                if (wheelView.getVisibility() == View.VISIBLE) {
                    wheelView.setVisibility(View.GONE);
                }
                if (prePosition != -1) {
                    if (CheckUtils.isEmptyStr(editTexts.get(prePosition).getText().toString()))
                        contentLayouts.get(prePosition).setVisibility(View.GONE);
                    prePosition = -1;
                }
                if (CheckUtils.isEmptyStr(etAllEvaluate.getText().toString().trim()))
                    llAllLayout.setVisibility(View.GONE);
                llDriverLayout.setVisibility(View.VISIBLE);
                etDriverEvaluate.requestFocus();
                etDriverEvaluate.setSelection(etDriverEvaluate.length());
                CommonUtils.showKeyBoard(etDriverEvaluate);
                if (RatingScore >= 3) {
                    rbDriver.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_better_1));
                    etDriverEvaluate.setHint("说说你对骑手的印象");
                    tvDriverDesc.setText("满意");
                } else if (RatingScore > 1) {
                    rbDriver.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_normal_1));
                    etDriverEvaluate.setHint("说说你对骑手的印象");
                    tvDriverDesc.setText("一般");
                } else {
                    rbDriver.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_normal_1));
                    etDriverEvaluate.setHint("说说你对骑手的印象");
                    tvDriverDesc.setText("一般");
                }
                rbDriver.setStar(RatingScore);
            }
        });
        etAllEvaluate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvAllCount.setText(s.length() + "/120");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etDriverEvaluate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvDriverCount.setText(s.length() + "/120");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (wheelView.getVisibility() == View.VISIBLE) {
            wheelView.setVisibility(View.GONE);
        }
        switch (v.getId()) {
            case R.id.evaluate_back:
                back();
                break;
            case R.id.to_evaluate:
                itemContainer.clearFocus();
                if (Float.compare(rbAll.getRating(), 0.0f) <= 0) {
                    ToastUtils.displayMsg("未作总体评价", EvaluateActivity.this);
                    break;
                }
                if (hasDriverEvaluate) {
                    if (Float.compare(rbDriver.getRating(), 0.0f) <= 0) {
                        ToastUtils.displayMsg("未作骑士评价", EvaluateActivity.this);
                        break;
                    }
                }
                if (diliveryCost < 10) {
                    ToastUtils.displayMsg("请选择送达时间", EvaluateActivity.this);
                    break;
                }
                boolean isReady = true;
                if (isFromOrderList) {
                    for (int i = 0; i < orderItems.size(); i++) {
                        if (orderItems.get(i).getRating() <= 0) {
                            ToastUtils.displayMsg(orderItems.get(i).getName() + "未作评价", EvaluateActivity.this);
                            isReady = false;
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < submitOrderEntity.getOrderItems().size(); i++) {
                        if (submitOrderEntity.getOrderItems().get(i).getRating() <= 0) {
                            ToastUtils.displayMsg(submitOrderEntity.getOrderItems().get(i).getName() + "未作评价", EvaluateActivity.this);
                            isReady = false;
                            break;
                        }
                    }
                }
                if (!isReady) {
                    break;
                }
                evaluateOrder();
                break;
            case R.id.time_select:
                CommonUtils.hideKeyBoard(mActivity);
                tvSelected.setTextColor(getResources().getColor(R.color.orange_ff));
                tvSelected.setText(getSongDaTime(diliveryCost).substring(11, 16) + "送达" + " ( 10分钟 )");
                diliveryCost = 10;
                wheelView.setVisibility(View.VISIBLE);
                break;

        }
    }

    private String getSongDaTime(int time) {
        String createTime;
        if (isFromOrderList)
            createTime = valueEntityEvaluate.getCreateTime();
        else
            createTime = submitOrderEntity.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonUtils.yyyy_MM_dd_HH_mm_ss);
        try {
            long timeStr;
            String expectTime;
            if (isFromOrderList)
                expectTime = valueEntityEvaluate.getExpectArrivalTime();
            else
                expectTime = submitOrderEntity.getExpectArrivalTime();
            if ("1".equals(expectTime)) {
                timeStr = sdf.parse(createTime).getTime() + time * 60 * 1000;
            } else {
                timeStr = Long.parseLong(expectTime) - eTime * 60 * 1000 + time * 60 * 1000;
            }
            MLog.d("time = " + CommonUtils.formatTime(timeStr, CommonUtils.yyyy_MM_dd_HH_mm_ss));
            return CommonUtils.formatTime(timeStr, CommonUtils.yyyy_MM_dd_HH_mm_ss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*@Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        switch (ratingBar.getId()){
            case R.id.all_rat_score:
                etAllEvaluate.setVisibility(View.VISIBLE);
                etDriverEvaluate.setVisibility(View.GONE);
                etAllEvaluate.setSelection(etAllEvaluate.length());
                etAllEvaluate.requestFocus();
                CommonUtils.showKeyBoard(etAllEvaluate);
                if(rating>3){
                    etAllEvaluate.setHint("说说哪里满意，帮大家选择");
                }else if(rating>1){
                    etAllEvaluate.setHint("说说哪里不满意，帮商家改进");
                }else{
                    etAllEvaluate.setHint("说说哪里不满意，帮商家改进");
                }
                break;
            case R.id.driver_rat_score:
                etAllEvaluate.setVisibility(View.GONE);
                etDriverEvaluate.setVisibility(View.VISIBLE);
                etDriverEvaluate.requestFocus();
                etDriverEvaluate.setSelection(etDriverEvaluate.length());
                CommonUtils.showKeyBoard(etDriverEvaluate);
                if(rating>3){
                    etDriverEvaluate.setHint("说说哪里满意，帮大家选择");
                }else if(rating>1){
                    etDriverEvaluate.setHint("说说哪里不满意，帮商家改进");
                }else{
                    etDriverEvaluate.setHint("说说哪里不满意，帮商家改进");
                }
                break;
        }
    }*/

    /**
     * 生成评价
     */
    private void evaluateOrder() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        map.put("merchantScore", rbAll.getRating());
        map.put("merchantComments", etAllEvaluate.getText().toString().trim());
        map.put("deliveryCost", diliveryCost);
        map.put("deliverymanScore", rbDriver.getRating());
        map.put("deliverymanComments", etDriverEvaluate.getText().toString().trim());
        map.put("goodsComments", getGoodsEvaluate());
        VolleyOperater<OrderEvaluateModel> operater = new VolleyOperater<OrderEvaluateModel>(EvaluateActivity.this);
        operater.doRequest(Constants.URL_EVALUATE_ORDER, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    setResult(RESULT_OK, new Intent());
                    CommonUtils.hideKeyBoard(EvaluateActivity.this);
                    finish();
                }
                loadingDialog.dismiss();
            }
        }, OrderEvaluateModel.class);
    }

    public String getGoodsEvaluate() {
        list = new ArrayList<>();
        if (isFromOrderList) {
            for (int i = 0; i < orderItems.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("goodsId", orderItems.get(i).getGoodsId());
                map.put("goodsScore", orderItems.get(i).getRating());
                map.put("goodsComments", orderItems.get(i).getContent());
                list.add(map);
            }
        } else {
            for (int i = 0; i < submitOrderEntity.getOrderItems().size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("goodsId", submitOrderEntity.getOrderItems().get(i).getGoodsId());
                map.put("goodsScore", submitOrderEntity.getOrderItems().get(i).getRating());
                map.put("goodsComments", submitOrderEntity.getOrderItems().get(i).getContent());
                list.add(map);
            }
        }
        return JSON.toJSONString(list);
    }

    @Override
    public void onRating(Object bindObject, int RatingScore, String content) {
//        if(prePosition != -1) {
//            orderItems.get(prePosition).setContent(preContent);
//        }
        if (etAllEvaluate.getVisibility() == View.VISIBLE) {
            etAllEvaluate.setVisibility(View.GONE);
        }
        if (etDriverEvaluate.getVisibility() == View.VISIBLE) {
            etDriverEvaluate.setVisibility(View.GONE);
        }
        if (wheelView.getVisibility() == View.VISIBLE) {
            wheelView.setVisibility(View.GONE);
        }
        final int tag = (int) bindObject;

        if (isFromOrderList) {
            for (NewOrderFragmentModel.ValueEntity.OrderItemsEntity orderItemsEntity : orderItems) {
                orderItemsEntity.setIsShow(false);
            }
            NewOrderFragmentModel.ValueEntity.OrderItemsEntity orderItemsEntity = orderItems.get(tag);
            orderItemsEntity.setIsShow(true);
            orderItemsEntity.setRating(RatingScore);
        } else {
            for (SubmitOrderModel.ValueEntity.OrderItemsEntity orderItemsEntity : submitOrderEntity.getOrderItems()) {
                orderItemsEntity.setIsShow(false);
            }
            SubmitOrderModel.ValueEntity.OrderItemsEntity orderItemsEntity = submitOrderEntity.getOrderItems().get(tag);
            orderItemsEntity.setIsShow(true);
            orderItemsEntity.setRating(RatingScore);
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int tag = (int) v.getTag();
        if (isFromOrderList) {
            NewOrderFragmentModel.ValueEntity.OrderItemsEntity orderItemsEntity = orderItems.get(tag);
            orderItemsEntity.setContent(((EditText) v).getText().toString().trim());
        } else {
            SubmitOrderModel.ValueEntity.OrderItemsEntity orderItemsEntity = submitOrderEntity.getOrderItems().get(tag);
            orderItemsEntity.setContent(((EditText) v).getText().toString().trim());
        }
    }
}
