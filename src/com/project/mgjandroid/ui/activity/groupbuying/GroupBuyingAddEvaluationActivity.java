package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.carhailing.CarHailingOrder;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseEvaluate;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseOrder;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.chooseimage.UploadPhoto;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingEvaluationListModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingEvaluationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.net.volley.toolbox.Volley;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.CameraActivity;
import com.project.mgjandroid.ui.activity.ImageUploadActivity;
import com.project.mgjandroid.ui.activity.UploadPhotoActivity;
import com.project.mgjandroid.ui.adapter.GroupBuyingImageRecyclerAdapter;
import com.project.mgjandroid.ui.adapter.ImageRecyclerAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.MyScrollView;
import com.project.mgjandroid.ui.view.RatingBarView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunXueliang on 2017-03-08.
 */

public class GroupBuyingAddEvaluationActivity extends ImageUploadActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.scroll_view)
    private ScrollView scrollView;
    @InjectView(R.id.rbv_overall_evaluation)
    private RatingBarView rbvOverallEcaluation;
    @InjectView(R.id.rbv_flavor_evaluation)
    private RatingBarView rbvFlavorEcaluation;
    @InjectView(R.id.rbv_service_evaluation)
    private RatingBarView rbvServiceEcaluation;
    @InjectView(R.id.rbv_environment_evaluation)
    private RatingBarView rbvEnvironmentEcaluation;
    @InjectView(R.id.et_evaluation)
    private EditText etEvaluation;
    @InjectView(R.id.tv_evaluate_length)
    private TextView tvEvaluateLength;
    @InjectView(R.id.layout_picture_upload)
    private LinearLayout layoutPictureUpload;
    @InjectView(R.id.recycler_view)
    private RecyclerView recyclerView;
    @InjectView(R.id.et_money)
    private EditText etMoney;
    @InjectView(R.id.tv_add_evaluation_publish)
    private TextView tvPublish;

    private GroupBuyingImageRecyclerAdapter adapter;
    private PopupWindow mPopupWindow;
    private ArrayList<UploadPhoto> mSelectFiles = new ArrayList<>();
    private StringBuffer imageUrls = new StringBuffer();
    private GroupPurchaseOrder purchaseOrder;
    private MLoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_add_evaluation);
        Injector.get(this).inject();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSelectFiles = ChoosePhotoModel.getInstance().getmUploadPhotoList();
        List<String> list = new ArrayList<>();
        if (CheckUtils.isNoEmptyList(mSelectFiles)) {
            for (int i = 0, size = mSelectFiles.size(); i < size; i++) {
                list.add(mSelectFiles.get(i).getPath());
            }
        }
        if (adapter != null) adapter.setList(list);
    }

    @Override
    protected void onDestroy() {
        ChoosePhotoModel.getInstance().clear();
        super.onDestroy();
    }

    private void initView() {
        purchaseOrder = (GroupPurchaseOrder) getIntent().getSerializableExtra("groupPurchaseOrder");
        if (purchaseOrder == null) {
            finish();
            return;
        }
        loadingDialog = new MLoadingDialog();

        ivBack.setOnClickListener(this);
        tvTitle.setText("添加评价");

        ChoosePhotoModel.getInstance().setMaxCount(9);
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());
        tvPublish.setOnClickListener(this);
        rbvOverallEcaluation.setStar(5);
        rbvEnvironmentEcaluation.setStar(5);
        rbvFlavorEcaluation.setStar(5);
        rbvServiceEcaluation.setStar(5);
        etMoney.setInputType(InputType.TYPE_CLASS_NUMBER);
        adapter = new GroupBuyingImageRecyclerAdapter(mActivity, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new GroupBuyingImageRecyclerAdapter.OnClickListener() {
            @Override
            public void onClickImage(int currentItem) {
                Intent dealPhoto = new Intent(mActivity, UploadPhotoActivity.class);
                dealPhoto.putExtra("from", mActivity.getClass().toString());
                startActivity(dealPhoto);
            }

            @Override
            public void onClichAdd() {
                showPopupWindow();
            }
        });
        etEvaluation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvEvaluateLength.setText(editable.toString().trim().length() + "/300");
                if (editable.toString().trim().length() > 300) {
                    ToastUtils.displayMsg("您输入的字数超过300", mActivity);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                return;
            case R.id.tv_add_evaluation_publish:
                if (checkCanPublish()) {
                    publish();
                }
                return;
            case R.id.take_photo:
                if (!CheckUtils.hasCamera(mActivity)) {
                    toast("您的手机上没有检测到相机");
                    return;
                }
                Intent camera = new Intent(mActivity, CameraActivity.class);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                startActivity(camera);
                dismissPopupWindow();
                return;
            case R.id.select_photo:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_GET_IMAGE_LIST);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                dismissPopupWindow();
                return;
            case R.id.feed_back_cancel:
                dismissPopupWindow();
                return;
        }
        super.onClick(v);
    }

    private boolean checkCanPublish() {
        imageUrls.setLength(0);
        if (CheckUtils.isEmptyList(mSelectFiles)) {
            return true;
        }

        for (int i = 0, size = mSelectFiles.size(); i < size; i++) {
            if (TextUtils.isEmpty(mSelectFiles.get(i).getUrl())) continue;
            if (i == size - 1) {
                imageUrls.append(mSelectFiles.get(i).getUrl());
            } else {
                imageUrls.append(mSelectFiles.get(i).getUrl() + ";");
            }
        }

        return true;
    }

    private void publish() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", purchaseOrder.getAgentId());
        map.put("merchantId", purchaseOrder.getMerchantId());
        map.put("groupPurchaseOrderId", purchaseOrder.getId());
        map.put("groupPurchaseCouponType", purchaseOrder.getGroupPurchaseCouponType());
        if (rbvFlavorEcaluation.getRating() == 0) {
            map.put("tasteScore", 5);
        } else {
            map.put("tasteScore", rbvFlavorEcaluation.getRating());
        }
        if (rbvEnvironmentEcaluation.getRating() == 0) {
            map.put("environmentScore", 5);
        } else {
            map.put("environmentScore", rbvEnvironmentEcaluation.getRating());
        }
        if (rbvServiceEcaluation.getRating() == 0) {
            map.put("serviceScore", 5);
        } else {
            map.put("serviceScore", rbvServiceEcaluation.getRating());
        }
        if (rbvOverallEcaluation.getRating() == 0) {
            map.put("totalScore", 5);
        } else {
            map.put("totalScore", rbvOverallEcaluation.getRating());
        }
        map.put("content", etEvaluation.getText().toString().trim());
        map.put("images", imageUrls.toString());
        if (etMoney.getText().toString().trim().equals("")) {
            map.put("perCapitaConsumptionAmt", 0);
        } else {
            map.put("perCapitaConsumptionAmt", etMoney.getText().toString().trim());
        }

        VolleyOperater<GroupBuyingEvaluationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_GROUP_PURCHASE_EVALUATE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                }
                GroupBuyingEvaluationModel model = (GroupBuyingEvaluationModel) obj;
                if (model.getCode() == 0) {
                    toast("评价完成，感谢您的宝贵意见");
                    finish();
                }
            }
        }, GroupBuyingEvaluationModel.class);
    }

    private void initPopupWindow() {
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_select_photo, null);
        TextView tvTakePhoto = (TextView) linearLayout.findViewById(R.id.take_photo);
        TextView tvSelectPhoto = (TextView) linearLayout.findViewById(R.id.select_photo);
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.feed_back_cancel);
        tvTakePhoto.setOnClickListener(this);
        tvSelectPhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        mPopupWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);
    }

    private void showPopupWindow() {
        if (mPopupWindow == null) {
            initPopupWindow();
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

}
