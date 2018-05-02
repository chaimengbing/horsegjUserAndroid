package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CommodityEvaluateModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.CommodityDetailListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.ta.utdid2.android.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommodityEvaluateActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.commodity_act_back)
    private ImageView ivBack;
    @InjectView(R.id.commodity_no_net)
    private LinearLayout hasNoNet;
    @InjectView(R.id.commodity_img_nonet)
    private ImageView hasNoNetIcon;
    @InjectView(R.id.commodity_msg_nonet)
    private TextView hasNoNetMsg;
    @InjectView(R.id.commodity_reload)
    private TextView reload;
    private LinearLayout llEvaAll;
    private LinearLayout llEvaPraise;
    private LinearLayout llEvaAverage;
    private LinearLayout llEvaError;
    private TextView tvEvaAll;
    private TextView tvAllNum;
    private TextView tvEvaPraise;
    private TextView tvPraiseNum;
    private TextView tvEvaAverage;
    private TextView tvAverageNum;
    private TextView tvEvaError;
    private TextView tvErrorNum;
    private PullToRefreshListView listView;
    private LinearLayout listHeaderView;
    private View inflate;
    private CommodityDetailListAdapter adapter;
    private int currentSection = 0;
    private static final int maxResults = 10;
    private String errorMsg;
    private CommodityEvaluateModel goodsEvaluate;
    private Long googId;
    private CommodityEvaluateModel.Value ValueEntity;
    private int commentNum;
    private int goodCommentNum;
    private int mediumCommentNum;
    private int badCommentNum;
    private List<CommodityEvaluateModel.CommentList> value = new ArrayList<>();
    private MLoadingDialog mLoadingDialog;
    private int type;
    private boolean refreshLoading = true;
    private List<CommodityEvaluateModel.CommentList> commentList;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.commodity_evaluate);
        Injector.get(this).inject();
        mLoadingDialog = new MLoadingDialog();
        getIntentDetail();
        initView();
    }

    private void initView() {
        initListView();
        initListHeaderView();
        llEvaAll = (LinearLayout) listHeaderView.findViewById(R.id.ll_evaluate_all);
        llEvaPraise = (LinearLayout) listHeaderView.findViewById(R.id.ll_evaluate_praise);
        llEvaAverage = (LinearLayout) listHeaderView.findViewById(R.id.ll_evaluate_average);
        llEvaError = (LinearLayout) listHeaderView.findViewById(R.id.ll_evaluate_error);
        tvEvaAll = (TextView) listHeaderView.findViewById(R.id.tv_evaluate_all);
        tvAllNum = (TextView) listHeaderView.findViewById(R.id.tv_evaluate_all_num);
        tvEvaPraise = (TextView) listHeaderView.findViewById(R.id.tv_evaluate_praise);
        tvPraiseNum = (TextView) listHeaderView.findViewById(R.id.tv_evaluate_praise_num);
        tvEvaAverage = (TextView) listHeaderView.findViewById(R.id.tv_evaluate_average);
        tvAverageNum = (TextView) listHeaderView.findViewById(R.id.tv_evaluate_average_num);
        tvEvaError = (TextView) listHeaderView.findViewById(R.id.tv_evaluate_error);
        tvErrorNum = (TextView) listHeaderView.findViewById(R.id.tv_evaluate_error_num);
        tvAllNum.setText(commentNum + "");
        tvPraiseNum.setText(goodCommentNum + "");
        tvAverageNum.setText(mediumCommentNum + "");
        tvErrorNum.setText(badCommentNum + "");
        initListener();
    }

    private void initListener() {
        llEvaAll.setOnClickListener(this);
        llEvaPraise.setOnClickListener(this);
        llEvaAverage.setOnClickListener(this);
        llEvaError.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        currentSection = 0;
        listView.getRefreshableView().removeFooterView(inflate);
        initColor();
        switch (v.getId()) {
            case R.id.ll_evaluate_all:
                type = 0;
                value.clear();
                getEvaluateList(googId, type, false);
                listView.getRefreshableView().removeFooterView(inflate);
                tvEvaAll.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvAllNum.setTextColor(getResources().getColor(R.color.title_bar_bg));
                break;
            case R.id.ll_evaluate_praise:
                type = 1;
                value.clear();
                getEvaluateList(googId, type, false);
                if (goodCommentNum == 0 && value.size() == 0) {
                    listView.getRefreshableView().addFooterView(inflate);
                } else {
                    listView.getRefreshableView().removeFooterView(inflate);
                }
                tvEvaPraise.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvPraiseNum.setTextColor(getResources().getColor(R.color.title_bar_bg));
                break;
            case R.id.ll_evaluate_average:
                type = 2;
                value.clear();
                getEvaluateList(googId, type, false);
                if (mediumCommentNum == 0 && value.size() == 0) {
                    listView.getRefreshableView().addFooterView(inflate);
                } else {
                    listView.getRefreshableView().removeFooterView(inflate);
                }
                tvEvaAverage.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvAverageNum.setTextColor(getResources().getColor(R.color.title_bar_bg));
                break;
            case R.id.ll_evaluate_error:
                type = 3;
                value.clear();
                getEvaluateList(googId, type, false);
                if (badCommentNum == 0 && value.size() == 0) {
                    listView.getRefreshableView().addFooterView(inflate);
                } else {
                    listView.getRefreshableView().removeFooterView(inflate);
                }
                tvEvaError.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvErrorNum.setTextColor(getResources().getColor(R.color.title_bar_bg));
                break;
            case R.id.commodity_act_back:
                onBackPressed();
                break;
        }
    }

    private void initColor() {
        tvEvaAll.setTextColor(getResources().getColor(R.color.eva_text));
        tvAllNum.setTextColor(getResources().getColor(R.color.eva_text));
        tvEvaPraise.setTextColor(getResources().getColor(R.color.eva_text));
        tvPraiseNum.setTextColor(getResources().getColor(R.color.eva_text));
        tvEvaAverage.setTextColor(getResources().getColor(R.color.eva_text));
        tvAverageNum.setTextColor(getResources().getColor(R.color.eva_text));
        tvEvaError.setTextColor(getResources().getColor(R.color.eva_text));
        tvErrorNum.setTextColor(getResources().getColor(R.color.eva_text));
    }

    private void initListHeaderView() {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        listHeaderView = (LinearLayout) getLayoutInflater().inflate(R.layout.commodity_eva_header_view, listView, false);
        listHeaderView.setLayoutParams(layoutParams);
        listView.getRefreshableView().addHeaderView(listHeaderView);

    }

    private void initListView() {
        listView = (PullToRefreshListView) findViewById(R.id.lv_commodity_evaluate);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
//            adapter = new CommodityDetailListAdapter(value, mActivity, R.layout.commodity_evaluate_list_item);
        adapter = new CommodityDetailListAdapter(mActivity, R.layout.commodity_evaluate_list_item);
        inflate = mInflater.inflate(R.layout.commodity_evaluate_empty, null);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                value.clear();
                refreshLoading = false;
                currentSection = 0;
                checkRefresh(refreshView);
                getEvaluateList(googId, type, false);

            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshLoading = false;
                checkRefresh(refreshView);
                getEvaluateList(googId, type, true);
            }
        });
    }

    private void checkRefresh(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        if (!NetworkUtils.isConnected(mActivity)) {
            hasNoNet.setVisibility(View.VISIBLE);
            hasNoNetIcon.setImageResource(R.drawable.has_no_net);
            hasNoNetMsg.setText("未能连接到互联网");
            reload.setText("刷新重试");
            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NetworkUtils.isConnected(mActivity)) {
                        return;
                    }
                    hasNoNet.setVisibility(View.GONE);
                }
            });
        }
    }

    /**
     * 网络请求获取评价列表
     */
    private void getEvaluateList(Long goodsId, int typeState, final boolean isLoad) {
        if (refreshLoading) {
            mLoadingDialog.show(mActivity.getFragmentManager(), "");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", goodsId);
        map.put("type", typeState);
        map.put("start", currentSection);
        map.put("size", maxResults);
        VolleyOperater<CommodityEvaluateModel> operater = new VolleyOperater<CommodityEvaluateModel>(mActivity);
        operater.doRequest(Constants.URL_FIND_MERCHANT_SHOP_GOODS_COMMENT_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (refreshLoading) {
                    mLoadingDialog.dismiss();
                }
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        listView.onRefreshComplete();
                        errorMsg = (String) obj;
                        ToastUtils.displayMsg(errorMsg, mActivity);
                    } else {
                        goodsEvaluate = (CommodityEvaluateModel) obj;
                        if (goodsEvaluate.getValue() != null) {
                            commentList = goodsEvaluate.getValue().getCommentList();
                            value.addAll(commentList);
                            currentSection = value.size();
                            setTitleNum();
                            adapter.refreshData(value);
                            if (isLoad && commentList.size() <= 0) {
                                ToastUtils.displayMsg("没有更多了", CommodityEvaluateActivity.this);
                            }
                            listView.onRefreshComplete();
                        }
                    }
                }
                listView.onRefreshComplete();
            }

        }, CommodityEvaluateModel.class);
    }

    private void setTitleNum() {
        ValueEntity = goodsEvaluate.getValue();
        commentNum = ValueEntity.getCommentNum();
        goodCommentNum = ValueEntity.getGoodCommentNum();
        mediumCommentNum = ValueEntity.getMediumCommentNum();
        badCommentNum = ValueEntity.getBadCommentNum();
        tvAllNum.setText(commentNum + "");
        tvPraiseNum.setText(goodCommentNum + "");
        tvAverageNum.setText(mediumCommentNum + "");
        tvErrorNum.setText(badCommentNum + "");
    }

    private void getIntentDetail() {
        googId = getIntent().getLongExtra("googId", 0);
        commentNum = getIntent().getIntExtra("commentNum", 0);
        goodCommentNum = getIntent().getIntExtra("goodCommentNum", 0);
        mediumCommentNum = getIntent().getIntExtra("mediumCommentNum", 0);
        badCommentNum = getIntent().getIntExtra("badCommentNum", 0);
        getEvaluateList(googId, 0, false);
    }

}
