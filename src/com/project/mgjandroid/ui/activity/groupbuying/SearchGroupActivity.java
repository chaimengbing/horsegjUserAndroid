package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jet.flowtaglayout.FlowTagLayout;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GroupHotSearchModel;
import com.project.mgjandroid.model.GroupSearchModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.activity.CommodityDetailActivity;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索团购商家
 */
public class SearchGroupActivity extends BaseActivity implements TextView.OnEditorActionListener, TextWatcher, PullToRefreshBase.OnRefreshListener2, View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int PAGE_SIZE = 20;
    private static final String GROUP_SEARCH_HISTORY = "group_search_history";
    @InjectView(R.id.search_list_view)
    private PullToRefreshListView mListView;
    @InjectView(R.id.search_text)
    private EditText mSearchText;
    @InjectView(R.id.login_back)
    private ImageView back;
    @InjectView(R.id.home_fragment_no_net)
    private LinearLayout hasNoNet;
    @InjectView(R.id.home_fragment_reload)
    private TextView tvReload;
    @InjectView(R.id.clear)
    private TextView clear;
    @InjectView(R.id.empty_textview)
    private TextView emptyTextView;
    @InjectView(R.id.search_textview)
    private TextView search;
    @InjectView(R.id.iv_delete)
    private ImageView ivSearchTxtClean;
    @InjectView(R.id.recommend_flow_tagLayout)
    private FlowTagLayout recommendFlowTaglayout;
    @InjectView(R.id.history_flow_tagLayout)
    private FlowTagLayout historyFlowTaglayout;
    @InjectView(R.id.recommend_layout)
    private LinearLayout recommendLayout;
    @InjectView(R.id.history_layout)
    private LinearLayout historyLayout;
    @InjectView(R.id.search_source_layout)
    private ScrollView searchKeyWordView;

    private GroupBuyingMerchantAdapter mSearchListAdapter;
    private int mCurrentPosition = 0;
    private boolean isRefresh = false;
    private ArrayList<String> mHistoryEntities;
    private long agentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group);
        Injector.get(mActivity).inject();

        initView();
        if (checkNetwork()) {
            getHotSearch();
        }
    }

    private boolean checkNetwork() {
        if (CommonUtils.isNetworkConnected()) {
            hasNoNet.setVisibility(View.GONE);
            return true;
        } else {
            hasNoNet.setVisibility(View.VISIBLE);
        }
        return false;
    }


    private void addHotSearch(final List<String> labels) {
        if (labels != null) {
            recommendFlowTaglayout.removeAllViews();
            recommendFlowTaglayout.addTags(labels);
            recommendFlowTaglayout.setTagClickListener(new FlowTagLayout.OnTagClickListener() {
                @Override
                public void tagClick(int position) {
                    String search = labels.get(position);
                    mSearchText.setText(search);
                    doSearch();
                }
            });
        }
    }


    private void refreshHistorySearch() {
        String historySearch = PreferenceUtils.getStringPreference(GROUP_SEARCH_HISTORY, "", mActivity);
        if (mHistoryEntities == null) {
            mHistoryEntities = new ArrayList<>();
        }
        mHistoryEntities.clear();
        if (CheckUtils.isNoEmptyStr(historySearch)) {
            String[] split = historySearch.split(",");
            for (String str : split) {
                mHistoryEntities.add(str);
            }
        }
        if (CheckUtils.isNoEmptyList(mHistoryEntities)) {
            historyLayout.setVisibility(View.VISIBLE);
            clear.setVisibility(View.VISIBLE);
            historyFlowTaglayout.addTags(mHistoryEntities);
            historyFlowTaglayout.setTagClickListener(new FlowTagLayout.OnTagClickListener() {
                @Override
                public void tagClick(int position) {
                    String search = mHistoryEntities.get(position);
                    mSearchText.setText(search);
                    doSearch();
                }
            });
        } else {
            historyLayout.setVisibility(View.GONE);
            clear.setVisibility(View.GONE);
        }
    }

    private void initView() {
        mListView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.GONE);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mSearchListAdapter = new GroupBuyingMerchantAdapter(mActivity, false);
        mListView.setAdapter(mSearchListAdapter);
        clear.setOnClickListener(this);
        search.setOnClickListener(this);
        ivSearchTxtClean.setOnClickListener(this);
        mListView.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommonUtils.hideKeyBoard2(mSearchText);
                return false;
            }
        });

        mSearchText.setOnEditorActionListener(this);
        mSearchText.addTextChangedListener(this);

        agentId = PreferenceUtils.getLongPreference("issueAgentId", 0, mActivity);

        back.setOnClickListener(this);
        tvReload.setOnClickListener(this);

        refreshHistorySearch();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            doSearch();
            return true;
        }
        return false;
    }

    private void doSearch() {
        String search = mSearchText.getText().toString().trim();
        if (CheckUtils.isNoEmptyStr(search)) {
            goSearchMerchant(search, false, false);
        }
    }

    private void savePreference(String search) {
        String historySearch = PreferenceUtils.getStringPreference(GROUP_SEARCH_HISTORY, "", mActivity);
        if (historySearch != null && !"".equals(historySearch)) {
            String[] split = historySearch.split(",");
            boolean isFind = false;
            for (String str : split) {
                if (search.equals(str)) {
                    isFind = true;
                }
            }
            if (!isFind) {
                PreferenceUtils.saveStringPreference(GROUP_SEARCH_HISTORY, historySearch + "," + search, mActivity);
            }
        } else {
            PreferenceUtils.saveStringPreference(GROUP_SEARCH_HISTORY, search, mActivity);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0) {
            ivSearchTxtClean.setVisibility(View.GONE);
            searchKeyWordView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
            mSearchListAdapter.setList(new ArrayList<GroupPurchaseMerchant>());
            refreshHistorySearch();
        } else {
            ivSearchTxtClean.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            mCurrentPosition = 0;
            goSearchMerchant(s.toString().trim(), false, true);
        }
    }

    /**
     * 搜索参数
     */
    private void goSearchMerchant(String param, final boolean isAddMore, final boolean isAutoLoad) {
        isRefresh = true;
        Map<String, Object> params = new HashMap<>();
        params.put("start", mCurrentPosition);
        params.put("size", PAGE_SIZE);
        String lon = PreferenceUtils.getStringPreference(PreferenceUtils.LONGITUDE, "", mActivity);
        if (lon != null && !"".equals(lon)) {
            params.put("longitude", Double.parseDouble(lon));
        }
        String lat = PreferenceUtils.getStringPreference(PreferenceUtils.LATITUDE, "", mActivity);
        if (lat != null && !"".equals(lat)) {
            params.put("latitude", Double.parseDouble(lat));
        }
        if (agentId != 0) {
            params.put("agentId", agentId);
        } else {
            return;
        }
        params.put("queryString", param);
        VolleyOperater<GroupSearchModel> operater = new VolleyOperater<>(SearchGroupActivity.this);
        operater.doRequest(Constants.URL_GROUP_SEARCH, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (!isAutoLoad) CommonUtils.hideKeyBoard(mActivity);
                if (mListView.isRefreshing()) {
                    mListView.onRefreshComplete();
                }
                isRefresh = false;
                if (isSucceed && obj != null) {
                    //有数据的时候，改变布局
                    searchKeyWordView.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);

                    GroupSearchModel searchModel = (GroupSearchModel) obj;
                    List<GroupPurchaseMerchant> value = searchModel.getValue();
                    if (isAddMore) {
                        if (value.size() > 0) {
                            mSearchListAdapter.getList().addAll(value);
                        } else {
                            ToastUtils.displayMsg("到底了", mActivity);
                        }
                    } else {
                        if (value.size() > 0) {
                            emptyTextView.setVisibility(View.GONE);
                            ArrayList<GroupPurchaseMerchant> list = new ArrayList<>();
                            list.addAll(value);
                            mSearchListAdapter.setList(list);
                        } else {
                            emptyTextView.setVisibility(View.VISIBLE);
                            mSearchListAdapter.setList(null);
                            if (!isAutoLoad) ToastUtils.displayMsg("没有搜索到商家", mActivity);
                        }
                    }
                }
            }
        }, GroupSearchModel.class);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        if (!isRefresh) {
            mCurrentPosition = 0;
            goSearchMerchant(mSearchText.getText().toString().trim(), false, false);
        }
    }

    @Override
    public void onPullDownValue(PullToRefreshBase refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (!isRefresh) {
            mCurrentPosition = mSearchListAdapter.getCount();
            goSearchMerchant(mSearchText.getText().toString().trim(), true, false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear:
                PreferenceUtils.removePreference(GROUP_SEARCH_HISTORY);
                refreshHistorySearch();
                break;
            case R.id.search_textview:
                doSearch();
                break;
            case R.id.login_back:
                onBackPressed();
                break;
            case R.id.iv_delete:
                mSearchText.setText("");
                break;
            case R.id.home_fragment_reload:
                String str = mSearchText.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    if (checkNetwork()) {
                        getHotSearch();
                    }
                } else {
                    if (checkNetwork()) {
                        goSearchMerchant(str, false, false);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.search_item:
                savePreference(mSearchText.getText().toString().trim());
                Intent intent = new Intent(this, GroupBuyingMerchantDetailActivity.class);
                intent.putExtra(CommercialActivity.MERCHANT_ID, mSearchListAdapter.getList().get(position - 1).getId().intValue());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 请求热门搜索
     */
    private void getHotSearch() {
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        VolleyOperater<GroupHotSearchModel> operater = new VolleyOperater<>(SearchGroupActivity.this);
        operater.doRequest(Constants.URL_GROUP_HOT_SEARCH, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    GroupHotSearchModel hotSearchModel = (GroupHotSearchModel) obj;
                    List<String> value = hotSearchModel.getValue();
                    if (CheckUtils.isNoEmptyList(value)) {
                        recommendLayout.setVisibility(View.VISIBLE);
                        addHotSearch(value);
                    }
                }
            }
        }, GroupHotSearchModel.class);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard2(mSearchText);
        return super.onTouchEvent(event);
    }
}
