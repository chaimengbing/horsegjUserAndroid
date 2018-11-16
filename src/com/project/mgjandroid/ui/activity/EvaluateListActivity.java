package com.project.mgjandroid.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CommodityEvaluateModel;
import com.project.mgjandroid.model.NewGoodsEvaluateModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.CommodityDetailListAdapter;
import com.project.mgjandroid.ui.adapter.GoodsDetailListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NestRadioGroup;
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

public class EvaluateListActivity extends BaseActivity implements View.OnClickListener,NestRadioGroup.OnCheckedChangeListener {
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
    private PullToRefreshListView listView;
    private LinearLayout listHeaderView;
    private View inflate;
    private int currentSection = 0;
    private static final int maxResults = 10;
    private String errorMsg;
    private Long googId;
    private MLoadingDialog mLoadingDialog;
    private boolean refreshLoading = true;
    private int queryType = 0;
    private int isHaveContent = 1;
    private RadioButton tvAll;
    private RadioButton tvSatisfy;
    private RadioButton tvYawp;
    private RadioButton tvHavePicturess;
    private TextView tvUnEmpty;
    private ArrayList<NewGoodsEvaluateModel.ValueBean.ListBean> data = new ArrayList<>();
    private GoodsDetailListAdapter mListAdapter;
    private RelativeLayout layoutGood;
    private RelativeLayout layoutBad;

    private Drawable drawable;
    private Drawable drawable1;
    private Drawable drawable2;
    private Drawable drawable3;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_evaluate_list);
        Injector.get(this).inject();
        mLoadingDialog = new MLoadingDialog();
        getIntentDetail();
        initView();
    }

    private void initView() {
        initListView();
        initListHeaderView();
        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        currentSection = 0;
        listView.getRefreshableView().removeFooterView(inflate);
        switch (v.getId()) {
            case R.id.commodity_act_back:
                onBackPressed();
                break;
            case R.id.evaluate_fragment_show_un_empty:
                if (tvUnEmpty.isSelected()) {
                    isHaveContent = 0;
                } else {
                    isHaveContent = 1;
                }
                currentSection = 0;
                data.clear();
                tvUnEmpty.setSelected(!tvUnEmpty.isSelected());
                getGoodsEvaluate(false);
                //TODO 刷新列表
                break;
        }
    }

    private void initListHeaderView() {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        listHeaderView = (LinearLayout) getLayoutInflater().inflate(R.layout.eva_header_view, listView, false);
        listHeaderView.setLayoutParams(layoutParams);
        listView.getRefreshableView().addHeaderView(listHeaderView);
        NestRadioGroup rgLabel = (NestRadioGroup) listHeaderView.findViewById(R.id.select_bar);
        rgLabel.setOnCheckedChangeListener(this);
        layoutGood = (RelativeLayout) listHeaderView.findViewById(R.id.layout_good);
        layoutBad = (RelativeLayout) listHeaderView.findViewById(R.id.layout_bad);
        tvAll = (RadioButton) listHeaderView.findViewById(R.id.evaluate_fragment_all);
        tvAll.setChecked(true);
        tvSatisfy = (RadioButton) listHeaderView.findViewById(R.id.evaluate_fragment_satisfy);
        tvSatisfy.setTextColor(Color.parseColor("#ffdc550f"));
        tvYawp = (RadioButton) listHeaderView.findViewById(R.id.evaluate_fragment_yawp);
        tvYawp.setTextColor(Color.parseColor("#ffBFBFBF"));
        tvHavePicturess = (RadioButton) listHeaderView.findViewById(R.id.evaluate_fragment_have_pictures);
        tvHavePicturess.setTextColor(Color.parseColor("#ffdc550f"));
        tvUnEmpty = (TextView) listHeaderView.findViewById(R.id.evaluate_fragment_show_un_empty);
        tvUnEmpty.setOnClickListener(this);
        tvUnEmpty.setSelected(true);

        drawable = getResources().getDrawable(R.drawable.ic_label_praise_unchecked);
        drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
        drawable1 = getResources().getDrawable(R.drawable.ic_trample_unchecked);
        drawable1.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
        drawable2 = getResources().getDrawable(R.drawable.ic_white_praise_unchecked);
        drawable2.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
        drawable3 = getResources().getDrawable(R.drawable.ic_white_trample_unchecked);
        drawable3.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());


    }

    private void initListView() {
        listView = (PullToRefreshListView) findViewById(R.id.lv_commodity_evaluate);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        mListAdapter = new GoodsDetailListAdapter(R.layout.item_goods_detail_list_view, mActivity);
        inflate = mInflater.inflate(R.layout.commodity_evaluate_empty, null);
        listView.setAdapter(mListAdapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                data.clear();
                refreshLoading = false;
                currentSection = 0;
                checkRefresh(refreshView);
                getGoodsEvaluate(false);

            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshLoading = false;
                checkRefresh(refreshView);
                getGoodsEvaluate(true);
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

    private void getGoodsEvaluate(final boolean isLoad) {
        if (refreshLoading) {
            mLoadingDialog.show(mActivity.getFragmentManager(), "");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", googId);
        map.put("start", currentSection);
        map.put("size", maxResults);
        map.put("queryType", queryType);
        map.put("isHaveContent", isHaveContent);
        VolleyOperater<NewGoodsEvaluateModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_QUERY_GOODS_EVALUATE, map, new VolleyOperater.ResponseListener() {
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
                        NewGoodsEvaluateModel goodsEvaluateModel = (NewGoodsEvaluateModel) obj;
                        if (goodsEvaluateModel.getValue() != null) {
                            List<NewGoodsEvaluateModel.ValueBean.ListBean> list = goodsEvaluateModel.getValue().getList();
                            data.addAll(list);
                            currentSection = data.size();
                            setRadioGroup(goodsEvaluateModel);
                            mListAdapter.setData(data);
                            if (isLoad && data.size() <= 0) {
                                ToastUtils.displayMsg("没有更多了", EvaluateListActivity.this);
                            }
                            listView.onRefreshComplete();
                        }
                    }
                }
                listView.onRefreshComplete();
            }
        }, NewGoodsEvaluateModel.class);
    }

    private void setRadioGroup(NewGoodsEvaluateModel model) {
        if (model != null) {
            tvAll.setText("全部 " + model.getValue().getAllCount());
            tvSatisfy.setText(" " + model.getValue().getGoodCount());
            tvYawp.setText(" " + model.getValue().getPoorCount());
            tvHavePicturess.setText("有图 " + model.getValue().getImgCount());
        }
    }

    private void getIntentDetail() {
        googId = getIntent().getLongExtra("goodsId", 0);
        getGoodsEvaluate(false);
    }

    private void changeTextColor(RadioButton tvAll, RadioButton tvSatisfy, RadioButton tvYawp, RadioButton tvHavePicturess) {
        tvAll.setTextColor(getResources().getColor(R.color.white));
        tvSatisfy.setTextColor(Color.parseColor("#ffdc550f"));
        tvYawp.setTextColor(Color.parseColor("#ffdc550f"));
        tvHavePicturess.setTextColor(Color.parseColor("#ffdc550f"));
    }

    @Override
    public void onCheckedChanged(NestRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.evaluate_fragment_all:
                queryType = 0;
                changeTextColor(tvAll, tvSatisfy, tvYawp, tvHavePicturess);
                layoutGood.setSelected(false);
                layoutBad.setSelected(false);
                tvSatisfy.setCompoundDrawables(drawable,null,null,null);
                tvYawp.setCompoundDrawables(drawable1,null,null,null);
                tvYawp.setTextColor(Color.parseColor("#ffBFBFBF"));
                currentSection = 0;
                data.clear();
                getGoodsEvaluate(false);
                break;

            case R.id.evaluate_fragment_satisfy:
                layoutGood.setSelected(!layoutGood.isSelected());
                layoutBad.setSelected(false);
                queryType = 1;
                changeTextColor(tvSatisfy, tvAll, tvYawp, tvHavePicturess);
                tvYawp.setTextColor(Color.parseColor("#ffBFBFBF"));
                tvSatisfy.setCompoundDrawables(drawable2,null,null,null);
                tvYawp.setCompoundDrawables(drawable1,null,null,null);
                currentSection = 0;
                data.clear();
                getGoodsEvaluate(false);
                break;

            case R.id.evaluate_fragment_yawp:
                queryType = 2;
                changeTextColor(tvYawp, tvSatisfy, tvAll, tvHavePicturess);
                layoutGood.setSelected(false);
                layoutBad.setSelected(!layoutBad.isSelected());
                tvSatisfy.setCompoundDrawables(drawable,null,null,null);
                tvYawp.setCompoundDrawables(drawable3,null,null,null);
                currentSection = 0;
                data.clear();
                getGoodsEvaluate(false);
                break;
            case R.id.evaluate_fragment_have_pictures:
                queryType = 3;
                changeTextColor(tvHavePicturess, tvSatisfy, tvYawp, tvAll);
                layoutGood.setSelected(false);
                layoutBad.setSelected(false);
                tvYawp.setTextColor(Color.parseColor("#ffBFBFBF"));
                tvSatisfy.setCompoundDrawables(drawable,null,null,null);
                tvYawp.setCompoundDrawables(drawable1,null,null,null);
                currentSection = 0;
                data.clear();
                getGoodsEvaluate(false);
                break;
        }
    }
}
