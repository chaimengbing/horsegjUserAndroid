package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseEvaluate;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingEvaluationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ShareUtil;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2017/3/7.
 */

@Router(value = "groupPurchaseEvaluate/:id", longParams = "id")
public class GroupBuyingEvaluationDetailActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.com_share)
    private ImageView ivShare;
    @InjectView(R.id.scroll_view)
    private ScrollView scrollView;
    @InjectView(R.id.user_avatar)
    private CornerImageView userAvatar;
    @InjectView(R.id.user_name)
    private TextView tvName;
    @InjectView(R.id.time)
    private TextView tvTime;
    @InjectView(R.id.average_price)
    private TextView tvAveragePrice;
    @InjectView(R.id.evaluation)
    private TextView tvEvaluation;
    @InjectView(R.id.rb_score)
    private RatingBar rbScore;
    @InjectView(R.id.evaluate_score)
    private TextView tvScore;
    @InjectView(R.id.grid_view)
    private NoScrollGridView gridView;

    private GroupPurchaseEvaluate evaluation;
    private long evaluationId;
    private GroupBuyingGridImageAdapter adapter;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private MLoadingDialog loadingDialog;
    private ShareUtil shareUtil;

    public static void toGroupBuyingEvaluationDetailActivity(Context context, GroupPurchaseEvaluate evaluation) {
        Intent intent = new Intent(context, GroupBuyingEvaluationDetailActivity.class);
        intent.putExtra("evaluation", evaluation);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_evaluation_detail);
        Injector.get(this).inject();
        initView();
        if (getIntent().hasExtra("evaluation")) {
            evaluation = (GroupPurchaseEvaluate) getIntent().getSerializableExtra("evaluation");
            showData();
        } else {
            evaluationId = getIntent().getLongExtra("id", -1);
            if (evaluationId == -1) {
                finish();
                return;
            }
            getData();
        }
    }

    private void initView() {
        ivBack.setOnClickListener(this);
//        ivShare.setVisibility(View.VISIBLE);
//        ivShare.setOnClickListener(this);
        tvTitle.setText("点评详情");
        adapter = new GroupBuyingGridImageAdapter(mActivity, false);
        gridView.setAdapter(adapter);
        loadingDialog = new MLoadingDialog();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back) {
            back();
        } else if (v.getId() == R.id.com_share) {
            if (shareUtil == null && evaluation != null)
                shareUtil = new ShareUtil(mActivity, "来这家店吧",
                        CheckUtils.isNoEmptyStr(evaluation.getContent()) ? evaluation.getContent() : "好东西分享给好朋友~",
                        evaluation.getShareUrl(), evaluation.getImages());
            if (shareUtil != null) shareUtil.showPopupWindow();
        }
    }

    public void getData() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> params = new HashMap<>();
        params.put("id", evaluationId);
        VolleyOperater<GroupBuyingEvaluationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_EVLUATE_BY_ID, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    evaluation = ((GroupBuyingEvaluationModel) obj).getValue();
                    showData();
                }
            }
        }, GroupBuyingEvaluationModel.class);
    }

    private void showData() {
        scrollView.setVisibility(View.VISIBLE);
        ImageUtils.loadBitmap(mActivity, evaluation.getAppUser().getHeaderImg(), userAvatar, R.drawable.user_avatar, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_EVALUATE);
        tvName.setText(evaluation.getAppUser().getName());
        tvTime.setText(format.format(evaluation.getCreateTime()));
        rbScore.setRating(evaluation.getTotalScore().floatValue());
        if (evaluation.getPerCapitaConsumptionAmt() != null && evaluation.getPerCapitaConsumptionAmt().compareTo(BigDecimal.ZERO) > 0)
            tvAveragePrice.setText("¥" + StringUtils.BigDecimal2Str(evaluation.getPerCapitaConsumptionAmt()) + "/人");
        tvScore.setText(getScoreString());
        if (CheckUtils.isNoEmptyStr(evaluation.getContent())) {
            tvEvaluation.setText(evaluation.getContent());
        } else {
            tvEvaluation.setText("客户未做出具体评价");
        }
        if (CheckUtils.isNoEmptyStr(evaluation.getImages()))
            adapter.setUrls(evaluation.getImages(), ";");
    }

    private String getScoreString() {
        return "产品: " + getEvaluateGrade(evaluation.getTasteScore().intValue()) +
                "  环境: " + getEvaluateGrade(evaluation.getEnvironmentScore().intValue()) +
                "  服务: " + getEvaluateGrade(evaluation.getServiceScore().intValue());
    }

    private String getEvaluateGrade(int score) {
        switch (score) {
            case 1:
                return "差";
            case 2:
                return "一般";
            case 3:
                return "好";
            case 4:
                return "很好";
            case 5:
                return "极好";
            default:
                return "未评价";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (shareUtil != null) {
            shareUtil.onActivityResultData(requestCode, resultCode, data);
        }
    }
}
