package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HomeBean;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCategory;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchantService;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.groupbuying.GroupBuyingCategoryListModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingMerchantListModel;
import com.project.mgjandroid.model.groupbuying.GroupPurchaseMerchantServiceModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.adapter.HomeSortAdapter;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2017/3/6.
 */
@Router(value = "groupPurchaseCategory/:groupCategoryId/:childCategoryId", longParams = {"groupCategoryId", "childCategoryId"})
public class GroupBuyingCategoryActivity extends BaseActivity {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.menu_bar)
    private LinearLayout menuBar;
    @InjectView(R.id.menu_layout_1)
    private LinearLayout layoutMenu1;
    @InjectView(R.id.menu_layout_2)
    private LinearLayout layoutMenu2;
    @InjectView(R.id.menu_layout_3)
    private LinearLayout layoutMenu3;
    @InjectView(R.id.menu_tv_1)
    private TextView tvMenu1;
    @InjectView(R.id.menu_tv_2)
    private TextView tvMenu2;
    @InjectView(R.id.menu_tv_3)
    private TextView tvMenu3;
    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;

    private GroupBuyingMerchantAdapter adapter;
    private static final int maxResults = 10;
    private int start = 0;
    private long groupCategoryId;
    private long childCategoryId;
    private String serviceStr = "";

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
    private GroupBuyingMerchantServiceMenuAdapter serviceAdapter;
    private ArrayList<GroupPurchaseMerchantService> services;
    private View emptyView;

    public static void toGroupBuyingCategoryActivity(Context context, String categoryName, Long groupCategoryId, Long childCategoryId) {
        Intent intent = new Intent(context, GroupBuyingCategoryActivity.class);
        intent.putExtra("categoryName", categoryName);
        intent.putExtra("groupCategoryId", groupCategoryId);
        intent.putExtra("childCategoryId", childCategoryId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying);
        Injector.get(this).inject();
        initView();
        groupCategoryId = getIntent().getLongExtra("groupCategoryId", -1);
        childCategoryId = getIntent().getLongExtra("childCategoryId", -1);
        String categoryName = getIntent().getStringExtra("categoryName");
        if (CheckUtils.isNoEmptyStr(categoryName)) {
            tvTitle.setText(categoryName);
        }
        getData(false);
        getCategory(false);
        getMerchantServices(false);
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        layoutMenu1.setOnClickListener(this);
        layoutMenu2.setOnClickListener(this);
        layoutMenu3.setOnClickListener(this);
        tvTitle.setText("团购");
        adapter = new GroupBuyingMerchantAdapter(mActivity, false);
        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        emptyView = mInflater.inflate(R.layout.no_merchant_empty_view, null);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                start = 0;
                getData(false);
            }

            @Override
            public void onPullDownValue(PullToRefreshBase refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                start = adapter.getCount();
                getData(true);
            }
        });
        rightDrawableOrange = getResources().getDrawable(R.drawable.nabla_red);
        rightDrawableOrange.setBounds(0, 0, rightDrawableOrange.getMinimumWidth(), rightDrawableOrange.getMinimumHeight());
        rightDrawableGray = getResources().getDrawable(R.drawable.nabla_black);
        rightDrawableGray.setBounds(0, 0, rightDrawableGray.getMinimumWidth(), rightDrawableGray.getMinimumHeight());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                back();
                break;
            case R.id.menu_layout_1:
                tvMenu1.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu1.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (leftMenuWindow == null) {
                    if (CheckUtils.isNoEmptyList(categories)) {
                        showLeftMenuPop(categories);
                    } else {
                        getCategory(true);
                    }
                } else if (leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
                } else {
                    leftMenuWindow.showAsDropDown(menuBar, 0, 0);
                }
                if (midMenuWindow != null && midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
                }
                if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
                    rightMenuWindow.dismiss();
                }
                break;
            case R.id.menu_layout_2:
                tvMenu2.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu2.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (midMenuWindow == null) {
                    showMidMenuPop();
                } else if (midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
                } else {
                    midMenuWindow.showAsDropDown(menuBar, 0, 0);
                }
                if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
                }
                if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
                    rightMenuWindow.dismiss();
                }
                break;
            case R.id.menu_layout_3:
                tvMenu3.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu3.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (rightMenuWindow != null) {
                    if (rightMenuWindow.isShowing()) {
                        rightMenuWindow.dismiss();
                    } else {
                        rightMenuWindow.showAsDropDown(menuBar, 0, 0);
                    }
                } else if (CheckUtils.isEmptyList(services)) {
                    getMerchantServices(true);
                } else {
                    showRightMenuPop(services);
                }
                if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
                }
                if (midMenuWindow != null && midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
                }
                break;
            case R.id.group_buying_menu_cover_view:
                hidePopupWindow();
                break;
            case R.id.group_buying_menu_clear:
                serviceStr = "";
                rightMenuWindow.dismiss();
                start = 0;
                getData(false);
                break;
            case R.id.group_buying_menu_confirm:
                serviceStr = "";
                for (GroupPurchaseMerchantService service : serviceAdapter.getData()) {
                    if (service.isSelected()) serviceStr += (service.getValue() + ",");
                }
                if (serviceStr.length() > 0)
                    serviceStr = serviceStr.substring(0, serviceStr.length() - 1);
                if ("-1".equals(serviceStr)) {
                    serviceStr = "";
                }
                rightMenuWindow.dismiss();
                start = 0;
                getData(false);
                break;
        }
    }

    private void getCategory(final boolean show) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentCategoryId", groupCategoryId);
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
                            if (category.getId() == childCategoryId) {
                                category.setSelected(true);
                                tvMenu1.setText(category.getName());
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

    private void getMerchantServices(final boolean show) {
        VolleyOperater<GroupPurchaseMerchantServiceModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_MERCHANT_SERVICES, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    services = ((GroupPurchaseMerchantServiceModel) obj).getValue();
                    if (show) showRightMenuPop(services);
                }
            }
        }, GroupPurchaseMerchantServiceModel.class);
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        /** 排序方式1:智能排序;2:离我最近;3:好评优先 **/
        if (midPrePosition != -1) map.put("sortType", sortIds[midPrePosition]);
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("groupPurchaseCategoryId", groupCategoryId);
        if (childCategoryId > 0) map.put("childGroupPurchaseCategoryId", childCategoryId);
        /** 商户服务多个逗号分隔：1,2,3,4,5,6,7 **/
        if (CheckUtils.isNoEmptyStr(serviceStr))
            map.put("groupPurchaseMerchantServices", serviceStr);
        VolleyOperater<GroupBuyingMerchantListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_MERCHANT, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    GroupBuyingMerchantListModel model = (GroupBuyingMerchantListModel) obj;
                    List<GroupPurchaseMerchant> mlist = new ArrayList<>();
                    mlist.addAll(model.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<GroupPurchaseMerchant> mlistOrg = adapter.getList();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setList(mlistOrg);
                            }
                        } else {
                            adapter.setList(mlist);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setList(mlist);
                            listView.setEmptyView(emptyView);
                        }
                    }
                }
            }
        }, GroupBuyingMerchantListModel.class);
    }

    private void showLeftMenuPop(final ArrayList<GroupPurchaseCategory> categories) {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.group_buying_menu_left, null);
        final ListView leftListView = (ListView) linearLayout.findViewById(R.id.group_buying_menu_list);
        categoryAdapter = new GroupBuyingCategoryAdapter(mActivity);
        leftListView.setAdapter(categoryAdapter);
        categoryAdapter.setData(categories);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
                }
                if (categories.get(position).isSelected()) {
                    return;
                }
                for (GroupPurchaseCategory category : categories) {
                    category.setSelected(false);
                }
                categories.get(position).setSelected(true);
                categoryAdapter.notifyDataSetChanged();
                tvMenu1.setText(categories.get(position).getName());

                start = 0;
                childCategoryId = categories.get(position).getId();
                getData(false);
            }
        });
        View coverView = linearLayout.findViewById(R.id.group_buying_menu_cover_view);
        coverView.setOnClickListener(this);

        leftMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        leftMenuWindow.setOutsideTouchable(true);
        leftMenuWindow.showAsDropDown(menuBar, 0, 0);

        leftMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu1.setTextColor(getResources().getColor(R.color.gray_text_0));
                tvMenu1.setCompoundDrawables(null, null, rightDrawableGray, null);
            }
        });
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
        midMenuWindow.showAsDropDown(menuBar, 0, 0);

        midMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu2.setTextColor(getResources().getColor(R.color.gray_text_0));
                tvMenu2.setCompoundDrawables(null, null, rightDrawableGray, null);
            }
        });
    }

    private View.OnClickListener listenerMid = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (midPrePosition != -1) {
                homeSortAdapter.getData().get(midPrePosition).setIsCheck(false);
            }
            HomeBean bean = homeSortAdapter.getData().get(position);
            bean.setIsCheck(!bean.isCheck());
            midPrePosition = position;
            homeSortAdapter.notifyDataSetChanged();
            if (midMenuWindow.isShowing()) {
                midMenuWindow.dismiss();
            }
            start = 0;
            getData(false);
            tvMenu2.setText(bean.getName());
        }
    };

    private void showRightMenuPop(ArrayList<GroupPurchaseMerchantService> value) {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.group_buying_menu_right, null);
        GridView rightGridView = (GridView) linearLayout.findViewById(R.id.group_buying_menu_list);
        serviceAdapter = new GroupBuyingMerchantServiceMenuAdapter(mActivity);
        rightGridView.setAdapter(serviceAdapter);
        final GroupPurchaseMerchantService service = new GroupPurchaseMerchantService();
        service.setName("不限");
        service.setValue(-1);
        service.setSelected(true);
        value.add(0, service);
        serviceAdapter.setData(value);
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
        View coverView = linearLayout.findViewById(R.id.group_buying_menu_cover_view);
        TextView clear = (TextView) linearLayout.findViewById(R.id.group_buying_menu_clear);
        TextView confirm = (TextView) linearLayout.findViewById(R.id.group_buying_menu_confirm);
        coverView.setOnClickListener(this);
        clear.setOnClickListener(this);
        confirm.setOnClickListener(this);

        rightMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        rightMenuWindow.setOutsideTouchable(true);
        rightMenuWindow.showAsDropDown(menuBar, 0, 0);

        rightMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if ("".equals(serviceStr))
                    tvMenu3.setTextColor(getResources().getColor(R.color.gray_text_0));
                tvMenu3.setCompoundDrawables(null, null, rightDrawableGray, null);

                for (GroupPurchaseMerchantService service : services) {
                    service.setSelected(false);
                }
                if ("".equals(serviceStr)) {
                    services.get(0).setSelected(true);
                } else {
                    String[] values = serviceStr.split(",");
                    for (String s : values) {
                        for (GroupPurchaseMerchantService service : services) {
                            if (s.equals(service.getValue() + "")) {
                                service.setSelected(true);
                                break;
                            }
                        }
                    }
                }
                serviceAdapter.notifyDataSetChanged();
            }
        });
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

    @Override
    public void onBackPressed() {
        if (isPopupWindowShowing()) {
            hidePopupWindow();
            return;
        }
        back();
    }
}
