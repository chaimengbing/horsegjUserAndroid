package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jet.flowtaglayout.FlowTagLayout;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HomeBean;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCategory;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchantService;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GroupHotSearchModel;
import com.project.mgjandroid.model.GroupSearchModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingCategoryListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.adapter.HomeSortAdapter;
import com.project.mgjandroid.ui.view.RangeSeekbar;
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


    private long groupCategoryId = 0;
    private PopupWindow leftMenuWindow;
    private PopupWindow midMenuWindow;
    private PopupWindow rightMenuWindow;
    private Drawable rightDrawableOrange;
    private Drawable rightDrawableGray;

    private GroupBuyingCategoryAdapter categoryAdapter;
    private ArrayList<GroupPurchaseCategory> categories;
    private HomeSortAdapter homeSortAdapter;
    private int midPrePosition = -1;
    private String[] names = new String[]{"智能排序", "距离最近", "好评优先"};
    private int[] heads = new int[]{R.drawable.head_01, R.drawable.head_02, R.drawable.head_06};
    private int[] sortIds = new int[]{1, 2, 3};
    private GroupBuyingMerchantServiceMenuAdapter serviceAdapter, activityAdapter;
    private RangeSeekbar rangeSeekbar;
    private String[] serviceNames = new String[]{"不限", "无线网络", "刷卡支付", "优雅包厢", "景观位", "露天位", "无烟区", "停车场"};
    private String[] activityNames = new String[]{"不限", "优惠买单", "团购券", "代金券"};
    private LinearLayout groupBar;
    private TextView menuTv1;
    private TextView menuTv2;
    private TextView menuTv3;

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
        initGroupMenuBar();
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

    private void initGroupMenuBar() {

        rightDrawableOrange = getResources().getDrawable(R.drawable.nabla_red);
        rightDrawableOrange.setBounds(0, 0, rightDrawableOrange.getMinimumWidth(), rightDrawableOrange.getMinimumHeight());
        rightDrawableGray = getResources().getDrawable(R.drawable.nabla_black);
        rightDrawableGray.setBounds(0, 0, rightDrawableGray.getMinimumWidth(), rightDrawableGray.getMinimumHeight());

        View view = mInflater.inflate(R.layout.group_menu_bar, null);
        groupBar = (LinearLayout) view.findViewById(R.id.group_menu_bar);
        LinearLayout menu1 = (LinearLayout) view.findViewById(R.id.menu_layout_1);
        LinearLayout menu2 = (LinearLayout) view.findViewById(R.id.menu_layout_2);
        LinearLayout menu3 = (LinearLayout) view.findViewById(R.id.menu_layout_3);
        menuTv1 = (TextView) view.findViewById(R.id.menu_tv_1);
        menuTv2 = (TextView) view.findViewById(R.id.menu_tv_2);
        menuTv3 = (TextView) view.findViewById(R.id.menu_tv_3);
        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);
        mListView.getRefreshableView().addHeaderView(view);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            savePreference(mSearchText.getText().toString().trim());
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
        if (CheckUtils.isNoEmptyStr(historySearch)) {
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
        }
        params.put("queryString", param);

        /** 排序方式1:智能排序;2:离我最近;3:好评优先 **/
        if (midPrePosition != -1) {
            params.put("sortType", midPrePosition);
        }

        if (groupCategoryId != 0) {
            params.put("groupPurchaseCategoryId", groupCategoryId);
        }

        if (serviceAdapter != null) {
            if (!serviceAdapter.getItem(0).isSelected()) {
                StringBuffer sb = new StringBuffer();
                for (GroupPurchaseMerchantService service : serviceAdapter.getData()) {
                    if (service.isSelected()) {
                        sb.append(service.getName()).append(" ");
                    }
                }
                params.put("groupPurchaseMerchantServices", sb.toString().trim());
            }
        }
        if (activityAdapter != null) {
            if (!activityAdapter.getItem(0).isSelected()) {
                StringBuffer sb = new StringBuffer();
                for (GroupPurchaseMerchantService service : activityAdapter.getData()) {
                    if (service.isSelected()) {
                        sb.append(service.getName()).append(" ");
                    }
                }
                params.put("groupPurchaseMerchantActivities", sb.toString().trim());
            }
        }

        if (rangeSeekbar != null) {
            String[] array = getResources().getStringArray(R.array.markArray);
            params.put("minAvgPersonPrice", array[rangeSeekbar.getLeftCursorIndex()]);
            if (!array[rangeSeekbar.getRightCursorIndex()].equals("不限")) {
                params.put("maxAvgPersonPrice", array[rangeSeekbar.getRightCursorIndex()]);
            }
        }
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
                savePreference(mSearchText.getText().toString().trim());
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
            case R.id.menu_layout_1:
                selectTag(menuTv1);
                hidePopupWindow();
                if (leftMenuWindow == null) {
                    if (CheckUtils.isNoEmptyList(categories)) {
                        showLeftMenuPop(categories);
                    } else {
                        getCategory(true);
                    }
                } else {
                    leftMenuWindow.showAsDropDown(groupBar, 0, 0);
                }
                break;
            case R.id.menu_layout_2:
                selectTag(menuTv2);
                hidePopupWindow();
                if (midMenuWindow == null) {
                    showMidMenuPop();
                } else {
                    midMenuWindow.showAsDropDown(groupBar, 0, 0);
                }
                break;
            case R.id.menu_layout_3:
                selectTag(menuTv3);
                hidePopupWindow();
                if (rightMenuWindow != null) {
                    if (rightMenuWindow.isShowing()) {
                        rightMenuWindow.dismiss();
                    } else {
                        rightMenuWindow.showAsDropDown(groupBar, 0, 0);
                    }
                } else {
                    showRightMenuPop();
                }
                break;
            case R.id.group_buying_menu_clear:
                clearStatus(serviceAdapter);
                clearStatus(activityAdapter);
                break;
            case R.id.group_buying_menu_confirm:
                hidePopupWindow();
                mCurrentPosition = 0;
                goSearchMerchant(mSearchText.getText().toString().trim(), false, false);
                break;
            case R.id.group_buying_menu_cover_view:
                hidePopupWindow();
                break;
            default:
                break;
        }
    }

    private void getCategory(final boolean show) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentCategoryId", 0);
        long agentId = PreferenceUtils.getLongPreference("issueAgentId", 0, mActivity);
        if (agentId > 0) {
            map.put("agentId", agentId);
        }
        VolleyOperater<GroupBuyingCategoryListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_CATEGORY_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    categories = ((GroupBuyingCategoryListModel) obj).getValue();
                    if (categories != null) {
                        for (GroupPurchaseCategory category : categories) {
                            if (category.getId() == groupCategoryId) {
                                category.setSelected(true);
                                menuTv1.setText(category.getName());
                                break;
                            }
                        }
                    }
                    if (categories != null) {
                        GroupPurchaseCategory category = new GroupPurchaseCategory();
                        category.setName("全部");
                        category.setId(-1L);
                        categories.add(0, category);
                    }
                    if (show) showLeftMenuPop(categories);
                }
            }
        }, GroupBuyingCategoryListModel.class);
    }


    private void selectTag(TextView textView) {
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.title_bar_bg));
            textView.setCompoundDrawables(null, null, rightDrawableOrange, null);
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


    private void showLeftMenuPop(final ArrayList<GroupPurchaseCategory> categories) {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.group_buying_menu_left, null);
        ListView leftListView = (ListView) linearLayout.findViewById(R.id.group_buying_menu_list);
        categoryAdapter = new GroupBuyingCategoryAdapter(mActivity);
        leftListView.setAdapter(categoryAdapter);
        categoryAdapter.setData(categories);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
                }
                if (categories.get(i).isSelected()) {
                    return;
                }
                for (GroupPurchaseCategory category : categories) {
                    category.setSelected(false);
                }
                categories.get(i).setSelected(true);
                categoryAdapter.notifyDataSetChanged();
                menuTv1.setText(categories.get(i).getName());

                groupCategoryId = categories.get(i).getId();
                mCurrentPosition = 0;
                goSearchMerchant(mSearchText.getText().toString().trim(), false, false);
            }
        });
        View coverView = linearLayout.findViewById(R.id.group_buying_menu_cover_view);
        coverView.setOnClickListener(this);

        leftMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        leftMenuWindow.setOutsideTouchable(true);
        leftMenuWindow.showAsDropDown(groupBar, 0, 0);

        leftMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismissPopwindow(menuTv1);
            }
        });
    }

    private void dismissPopwindow(TextView textView) {
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.gray_text_0));
            textView.setCompoundDrawables(null, null, rightDrawableGray, null);
        }
    }

    private void showMidMenuPop() {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.group_buying_menu_left, null);
        ListView listView = (ListView) linearLayout.findViewById(R.id.group_buying_menu_list);
        View coverView = linearLayout.findViewById(R.id.group_buying_menu_cover_view);
        coverView.setOnClickListener(this);
        homeSortAdapter = new HomeSortAdapter(R.layout.layout_home_category, mActivity, listenerMid);
        ArrayList<HomeBean> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            HomeBean bean = new HomeBean();
            bean.setIcon(heads[i]);
            bean.setName(names[i]);
            bean.setId(sortIds[i]);
            data.add(bean);
        }
        homeSortAdapter.setData(data);
        listView.setAdapter(homeSortAdapter);

        midMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        midMenuWindow.setOutsideTouchable(true);
        midMenuWindow.showAsDropDown(groupBar, 0, 0);

        midMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismissPopwindow(menuTv2);
            }
        });
    }

    private View.OnClickListener listenerMid = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (midPrePosition != -1) {
                homeSortAdapter.getData().get(midPrePosition - 1).setIsCheck(false);
            }
            HomeBean bean = homeSortAdapter.getData().get(position);
            bean.setIsCheck(!bean.isCheck());
            midPrePosition = position + 1;
            homeSortAdapter.notifyDataSetChanged();
            if (midMenuWindow.isShowing()) {
                midMenuWindow.dismiss();
            }
            mCurrentPosition = 0;
            goSearchMerchant(mSearchText.getText().toString().trim(), false, false);
            menuTv2.setText(bean.getName());
        }
    };

    private void showRightMenuPop() {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.group_buying_menu_right, null);
        LinearLayout conditionLayout = (LinearLayout) linearLayout.findViewById(R.id.condition_layout);
        rangeSeekbar = (RangeSeekbar) linearLayout.findViewById(R.id.rangeseekbar);
        setServiceView(conditionLayout);
        setActivityView(conditionLayout);

        View coverView = linearLayout.findViewById(R.id.group_buying_menu_cover_view);
        TextView clear = (TextView) linearLayout.findViewById(R.id.group_buying_menu_clear);
        TextView confirm = (TextView) linearLayout.findViewById(R.id.group_buying_menu_confirm);
        coverView.setOnClickListener(this);
        clear.setOnClickListener(this);
        confirm.setOnClickListener(this);

        rightMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        rightMenuWindow.setOutsideTouchable(true);
        rightMenuWindow.showAsDropDown(groupBar, 0, 0);

        rightMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismissPopwindow(menuTv3);
            }
        });
    }


    private void clearStatus(GroupBuyingMerchantServiceMenuAdapter activityAdapter) {
        if (activityAdapter != null) {
            List<GroupPurchaseMerchantService> services = activityAdapter.getData();
            for (GroupPurchaseMerchantService service : services) {
                service.setSelected(false);
            }
            services.get(0).setSelected(true);
            activityAdapter.notifyDataSetChanged();
        }
        if (rangeSeekbar != null) {
            rangeSeekbar.setLeftSelection(0);
            rangeSeekbar.setRightSelection(5);
        }
    }

    private void setServiceView(LinearLayout linearLayout) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_group_buying_menu_right, null);
        GridView rightGridView = (GridView) view.findViewById(R.id.group_buying_menu_list);
        TextView name = (TextView) view.findViewById(R.id.type_name_textview);
        name.setText("商家服务");
        serviceAdapter = new GroupBuyingMerchantServiceMenuAdapter(mActivity);
        rightGridView.setAdapter(serviceAdapter);
        List<GroupPurchaseMerchantService> list = new ArrayList<>();
        for (String s : serviceNames) {
            GroupPurchaseMerchantService service = new GroupPurchaseMerchantService();
            service.setName(s);
            if ("不限".equals(s)) {
                service.setValue(-1);
                service.setSelected(true);
            } else {
                service.setValue(1);
                service.setSelected(false);
            }
            list.add(service);
        }
        serviceAdapter.setData(list);
        rightGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (serviceAdapter.getItem(position).isSelected()) return;
                    serviceAdapter.getItem(position).setSelected(true);
                    for (int i = 1; i < serviceAdapter.getCount(); i++) {
                        serviceAdapter.getItem(i).setSelected(false);
                    }
                } else {
                    serviceAdapter.getItem(position).setSelected(!serviceAdapter.getItem(position).isSelected());
                    serviceAdapter.getItem(0).setSelected(false);
                }
                serviceAdapter.notifyDataSetChanged();
            }
        });
        linearLayout.addView(view);
    }

    private void setActivityView(LinearLayout linearLayout) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_group_buying_menu_right, null);
        GridView rightGridView = (GridView) view.findViewById(R.id.group_buying_menu_list);
        TextView name = (TextView) view.findViewById(R.id.type_name_textview);
        name.setText("商家活动");
        activityAdapter = new GroupBuyingMerchantServiceMenuAdapter(mActivity);
        rightGridView.setAdapter(activityAdapter);
        List<GroupPurchaseMerchantService> list = new ArrayList<>();
        for (String s : activityNames) {
            GroupPurchaseMerchantService service = new GroupPurchaseMerchantService();
            service.setName(s);
            if ("不限".equals(s)) {
                service.setValue(-1);
                service.setSelected(true);
            } else {
                service.setValue(1);
                service.setSelected(false);
            }
            list.add(service);
        }
        activityAdapter.setData(list);
        rightGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (activityAdapter.getItem(position).isSelected()) return;
                    activityAdapter.getItem(position).setSelected(true);
                    for (int i = 1; i < activityAdapter.getCount(); i++) {
                        activityAdapter.getItem(i).setSelected(false);
                    }
                } else {
                    activityAdapter.getItem(position).setSelected(!activityAdapter.getItem(position).isSelected());
                    activityAdapter.getItem(0).setSelected(false);
                }
                activityAdapter.notifyDataSetChanged();
            }
        });
        linearLayout.addView(view);
    }

    private boolean isPopupWindowShowing() {
        return (leftMenuWindow != null && leftMenuWindow.isShowing()) || (midMenuWindow != null && midMenuWindow.isShowing()) ||
                (rightMenuWindow != null && rightMenuWindow.isShowing());
    }

    private void hidePopupWindow() {
        if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
            leftMenuWindow.dismiss();
        } else if (midMenuWindow != null && midMenuWindow.isShowing()) {
            midMenuWindow.dismiss();
        } else if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
            rightMenuWindow.dismiss();
        }
    }
}
