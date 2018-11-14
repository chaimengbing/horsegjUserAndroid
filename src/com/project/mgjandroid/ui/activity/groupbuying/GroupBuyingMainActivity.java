package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HomeBean;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseBanner;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCategory;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchantService;
import com.project.mgjandroid.bean.groupbuying.GroupPurchasePrimaryCategory;
import com.project.mgjandroid.bean.groupbuying.GroupPurchasePrimaryPublicity;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.groupbuying.GroupBuyingCategoryListModel;
import com.project.mgjandroid.model.groupbuying.GroupBuyingMerchantListModel;
import com.project.mgjandroid.model.groupbuying.GroupPurchaseBannerListModel;
import com.project.mgjandroid.model.groupbuying.GroupPurchasePrimaryCategoryListModel;
import com.project.mgjandroid.model.groupbuying.GroupPurchasePrimaryPublicityListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.pintuan.GroupServiceAdapter;
import com.project.mgjandroid.ui.adapter.HomeSortAdapter;
import com.project.mgjandroid.ui.adapter.RecommendGridAdapter;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.ui.view.RangeSeekbar;
import com.project.mgjandroid.ui.view.autoscrollviewpager.AutoScrollViewPager;
import com.project.mgjandroid.ui.view.autoscrollviewpager.indicator.CirclePageIndicator;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2017/3/10.
 */

@Router("groupCoupon")
public class GroupBuyingMainActivity extends BaseActivity {

    private final static int NUMBER_OF_NAVIGATOR = 5;

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.search_imageview)
    private ImageView search;
    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;
    @InjectView(R.id.group_menu_bar)
    private LinearLayout groupMenuLayout;
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

    private RecyclerView recommendRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private GroupBuyingMerchantAdapter adapter;
    private int lineCount = 2;

    private MyBanner myBanner;
    private LinearLayout groupBar;
    private RelativeLayout navigatorLayout;
    private CirclePageIndicator navigatorIndicator;
    private MyPageAdapter mPageAdapter = new MyPageAdapter();
    private List<View> viewList = new ArrayList<>();

    private ArrayList<GroupPurchaseBanner> bannerList;
    private RecommendGridAdapter recommendGridAdapter;

    private static final int maxResults = 10;
    private int start = 0;
    private LoadingDialog loadingDialog;
    private LinearLayout listHeaderView;
    private NoticeDialog noticeDialog;


    private long groupCategoryId = 0;
    private TextView menuChild1, menuChild2, menuChild3;
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

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_buying_main);
        Injector.get(this).inject();
        initView();
        loadingDialog = new LoadingDialog(mActivity);
        loadingDialog.show();
        getBanner();
        getCategory();
        getPublicity();
        getData(false);
        getCategory(false);
    }

    private void initView() {
        groupMenuLayout.setVisibility(View.GONE);
        ivBack.setOnClickListener(this);
        search.setOnClickListener(this);
        layoutMenu1.setOnClickListener(this);
        layoutMenu2.setOnClickListener(this);
        layoutMenu3.setOnClickListener(this);
        rightDrawableOrange = getResources().getDrawable(R.drawable.nabla_red);
        rightDrawableOrange.setBounds(0, 0, rightDrawableOrange.getMinimumWidth(), rightDrawableOrange.getMinimumHeight());
        rightDrawableGray = getResources().getDrawable(R.drawable.nabla_black);
        rightDrawableGray.setBounds(0, 0, rightDrawableGray.getMinimumWidth(), rightDrawableGray.getMinimumHeight());
        initHeaderView();
        adapter = new GroupBuyingMerchantAdapter(mActivity, false);
        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
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

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i == 0) {
                    groupMenuLayout.setVisibility(View.GONE);
                }
                if (i == 1) {
                    if (checkBarNeedVisible()) {
                        groupMenuLayout.setVisibility(View.VISIBLE);
                        if (groupBar != null) {
                            groupBar.setVisibility(View.GONE);
                        }
                    } else {
                        groupMenuLayout.setVisibility(View.GONE);
                        if (groupBar != null) {
                            groupBar.setVisibility(View.VISIBLE);
                        }
                    }
                }

                if (isShowPop()){
                    groupMenuLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public boolean checkBarNeedVisible() {
        View c = listView.getRefreshableView().getChildAt(0);
        if (c == null) {
            return false;
        }
        return (c.getHeight() + c.getTop()) < getResources().getDimensionPixelSize(R.dimen.title_bar_height);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                back();
                break;
            case R.id.search_imageview:
                Intent intent = new Intent(this, SearchGroupActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_layout_1:
                selectTag(tvMenu1);
                selectTag(menuChild1);
                hidePopupWindow();
                if (leftMenuWindow == null) {
                    if (CheckUtils.isNoEmptyList(categories)) {
                        showLeftMenuPop(categories);
                    } else {
                        getCategory(true);
                    }
                }else {
                    leftMenuWindow.showAsDropDown(groupMenuLayout, 0, 0);
                }
                break;
            case R.id.menu_layout_2:
                selectTag(tvMenu2);
                selectTag(menuChild2);
                hidePopupWindow();
                if (midMenuWindow == null) {
                    showMidMenuPop();
                } else {
                    midMenuWindow.showAsDropDown(groupMenuLayout, 0, 0);
                }
                break;
            case R.id.menu_layout_3:
                selectTag(tvMenu3);
                selectTag(menuChild3);
                hidePopupWindow();
                if (rightMenuWindow != null) {
                    if (rightMenuWindow.isShowing()) {
                        rightMenuWindow.dismiss();
                    } else {
                        rightMenuWindow.showAsDropDown(groupMenuLayout, 0, 0);
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
                start = 0;
                getData(false);
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


    private void selectTag(TextView textView) {
        if (groupMenuLayout.getVisibility() == View.GONE) {
            groupMenuLayout.setVisibility(View.VISIBLE);
        }
        checkListCount();
        resetListView();
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.title_bar_bg));
            textView.setCompoundDrawables(null, null, rightDrawableOrange, null);
        }
    }


    private boolean isShowPop() {
        if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
            return true;
        }
        if (midMenuWindow != null && midMenuWindow.isShowing()) {
            return true;
        }
        if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
            return true;
        }
        return false;
    }


    private void checkListCount() {
        if (adapter.getCount() > 4) {
            listView.getRefreshableView().setSelection(2);
        } else {
            listView.scrollTo(0, (int) getHeaderScrollY());
        }
    }

    public float getHeaderScrollY() {
        View v1 = listView.getRefreshableView().getChildAt(1);
        return listHeaderView.getHeight() - DipToPx.dip2px(mActivity, 48);
    }

    private void resetListView() {
        listView.scrollTo(0, 0);
    }


    private void initHeaderView() {
        listHeaderView = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.group_buying_list_header_layout, null);
        myBanner = (MyBanner) listHeaderView.findViewById(R.id.slideshowView);
        myBanner.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GroupPurchaseBanner bannerItem = bannerList.get(position);
                int bannerType = bannerItem.getBannerType();
                switch (bannerType) {
                    case 1: //链接地址
                        if (bannerItem.getUrl().startsWith("maguanjia://")) {
                            if (bannerItem.getUrl().replace("maguanjia://", "").startsWith("http")) {
                                Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                                intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bannerItem.getUrl().replace("maguanjia://", ""));
                                startActivity(intent);
                            } else {
                                Routers.open(mActivity, ActivitySchemeManager.SCHEME + bannerItem.getUrl().replace("maguanjia://", ""), new RouterCallback() {
                                    @Override
                                    public void notFound(Context context, Uri uri) {
                                        showNoticeDialog();
                                    }

                                    @Override
                                    public void beforeOpen(Context context, Uri uri) {

                                    }

                                    @Override
                                    public void afterOpen(Context context, Uri uri) {

                                    }

                                    @Override
                                    public void error(Context context, Uri uri, Throwable e) {

                                    }
                                });
                            }
                        } else if (bannerItem.getUrl().startsWith("http")) {
//                            Intent intent = new Intent(mActivity, Banner2WebActivity.class);
//                            intent.putExtra(Banner2WebActivity.URL, bannerItem.getUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
//                            intent.putExtra("name", bannerList.get(position).getName());
//                            startActivity(intent);
                            Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                            intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bannerItem.getUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
                            startActivity(intent);
                        }
                        break;
                    case 2: //优惠券、团购
                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseCoupon/" + bannerItem.getGroupPurchaseCouponId());
                        break;
                    case 3: //团购商家
                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + bannerItem.getGroupPurchaseMerchantId());
                        break;
                    case 4: //团购分类
                        GroupBuyingCategoryActivity.toGroupBuyingCategoryActivity(mActivity, bannerItem.getName(), bannerItem.getGroupPurchaseCategoryId(), bannerItem.getChildGroupPurchaseCategoryId());
                        break;
                }
            }
        });
        AutoScrollViewPager navigatorViewPager = (AutoScrollViewPager) listHeaderView.findViewById(R.id.home_list_header_navigator_view_pager);
        groupBar = (LinearLayout) listHeaderView.findViewById(R.id.group_menu_bar);
        LinearLayout menu1 = (LinearLayout) listHeaderView.findViewById(R.id.menu_layout_1);
        LinearLayout menu2 = (LinearLayout) listHeaderView.findViewById(R.id.menu_layout_2);
        LinearLayout menu3 = (LinearLayout) listHeaderView.findViewById(R.id.menu_layout_3);
        menuChild1 = (TextView) listHeaderView.findViewById(R.id.menu_tv_1);
        menuChild2 = (TextView) listHeaderView.findViewById(R.id.menu_tv_2);
        menuChild3 = (TextView) listHeaderView.findViewById(R.id.menu_tv_3);
        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);
        navigatorLayout = (RelativeLayout) listHeaderView.findViewById(R.id.home_list_header_navigator_flipperParent);
        navigatorViewPager.setNeedOnMeasure(true);
        navigatorIndicator = (CirclePageIndicator) listHeaderView.findViewById(R.id.home_list_header_navigator_indicator);
        navigatorViewPager.setAdapter(mPageAdapter);
        navigatorIndicator.setViewPager(navigatorViewPager);
        navigatorIndicator.setRadius(getResources().getDimension(R.dimen.x2));
        navigatorIndicator.setOrientation(LinearLayout.HORIZONTAL);
        navigatorIndicator.setStrokeWidth(1);
        navigatorIndicator.setStrokeColor(0xffe5e5e5);
        navigatorIndicator.setSnap(false);
        navigatorIndicator.setFillColor(0xffe5e5e5);
        navigatorViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
        navigatorViewPager.stopAutoScroll();
        navigatorViewPager.setCurrentItem(0);
        navigatorViewPager.setCycle(false);
        navigatorViewPager.setBorderAnimation(true);

        recommendRecyclerView = (RecyclerView) listHeaderView.findViewById(R.id.recommend_recyclerview);
        recommendGridAdapter = new RecommendGridAdapter(this);
        gridLayoutManager = new GridLayoutManager(this, lineCount);
        recommendRecyclerView.setLayoutManager(gridLayoutManager);
        recommendRecyclerView.setAdapter(recommendGridAdapter);
        listView.getRefreshableView().addHeaderView(listHeaderView);
    }

    private void getBanner() {
        VolleyOperater<GroupPurchaseBannerListModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_BANNER_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    bannerList = ((GroupPurchaseBannerListModel) obj).getValue();
                    if (CheckUtils.isNoEmptyList(bannerList)) {
                        List<String> imageIdList = new ArrayList<>();
                        for (GroupPurchaseBanner banner : bannerList) {
                            imageIdList.add(banner.getPicUrl());
                        }
                        myBanner.setUrls(imageIdList, false, true);
                    } else {
                        myBanner.setBackgroundResource(R.drawable.banner_default);
                    }
                }
            }
        }, GroupPurchaseBannerListModel.class);
    }

    private void getCategory() {
        VolleyOperater<GroupPurchasePrimaryCategoryListModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_PRIMARY_CATEGORY_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    ArrayList<GroupPurchasePrimaryCategory> categories = ((GroupPurchasePrimaryCategoryListModel) obj).getValue();
                    if (CheckUtils.isNoEmptyList(categories)) {
                        initNavigatorViewPager(categories);
                    }
                }
            }
        }, GroupPurchasePrimaryCategoryListModel.class);
    }

    private void getPublicity() {
        VolleyOperater<GroupPurchasePrimaryPublicityListModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_PRIMARY_PUBLICITY_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    ArrayList<GroupPurchasePrimaryPublicity> publicities = ((GroupPurchasePrimaryPublicityListModel) obj).getValue();
                    if (CheckUtils.isNoEmptyList(publicities)) {
                        showPublicity(publicities);
                    }
                }
            }
        }, GroupPurchasePrimaryPublicityListModel.class);
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        if (groupCategoryId != 0) {
            map.put("groupPurchaseCategoryId", groupCategoryId);
        }
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        /** 排序方式1:智能排序;2:离我最近;3:好评优先 **/
        if (midPrePosition != -1) {
            map.put("sortType", midPrePosition);
        }

        if (serviceAdapter != null) {
            if (!serviceAdapter.getItem(0).isSelected()) {
                StringBuffer sb = new StringBuffer();
                for (GroupPurchaseMerchantService service : serviceAdapter.getData()) {
                    if (service.isSelected()) {
                        sb.append(service.getName()).append(" ");
                    }
                }
                map.put("groupPurchaseMerchantServices", sb.toString().trim());
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
                map.put("groupPurchaseMerchantActivities", sb.toString().trim());
            }
        }

        if (rangeSeekbar != null) {
            String[] array = getResources().getStringArray(R.array.markArray);
            map.put("minAvgPersonPrice", array[rangeSeekbar.getLeftCursorIndex()]);
            if (!array[rangeSeekbar.getRightCursorIndex()].equals("不限")) {
                map.put("maxAvgPersonPrice", array[rangeSeekbar.getRightCursorIndex()]);
            }
        }

//        groupPurchaseMerchantServices	商户服务多个空格分隔：无线网络 刷卡支付 但 不限 例外 不限 和其他不可同时选择 不限时 此字段传空字符串 搜索时传空字符串
//        groupPurchaseMerchantActivities		商户活动多个空格分隔：优惠买单 团购券 但 不限 例外 不限 和其他不可同时选择 不限时 此字段传空字符串 搜索时传空字符串
//        minAvgPersonPrice				最小人均消费	默认传0
//        maxAvgPersonPrice				最大人均消费	默认不传

        VolleyOperater<GroupBuyingMerchantListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GROUP_SEARCH, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
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
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            if (groupBar != null && groupBar.getVisibility() == View.GONE) {
                                groupBar.setVisibility(View.VISIBLE);
                            }
                            adapter.setList(mlist);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setList(mlist);
                            resetListView();
                        }
                    }
                }
            }
        }, GroupBuyingMerchantListModel.class);
    }


    /**
     * 显示推广区
     *
     * @param publicityList List
     */
    private void showPublicity(final List<GroupPurchasePrimaryPublicity> publicityList) {
        boolean isSet = false;
        if (publicityList.size() == 1 || publicityList.size() == 3) {
            isSet = true;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == 0 ? 2 : 1;
                }
            });
        }
        for (int i = 0; i < publicityList.size(); i++) {
            int r = i % 4;
            GroupPurchasePrimaryPublicity publicity = publicityList.get(i);
            Drawable drawable = null;
            if (publicity != null) {
                if (r == 0) {
                    if (isSet) {
                        drawable = getResources().getDrawable(R.drawable.bg_chang_discount);
                    } else {
                        drawable = getResources().getDrawable(R.drawable.bg_discount);
                    }
                } else if (r == 1) {
                    drawable = getResources().getDrawable(R.drawable.bg_life);
                } else if (r == 2) {
                    drawable = getResources().getDrawable(R.drawable.bg_recommend);
                } else {
                    drawable = getResources().getDrawable(R.drawable.bg_calculate);
                }
                publicity.setResSource(drawable);
            }
        }
        recommendGridAdapter.setData(publicityList);
        recommendGridAdapter.setOnItemClickListener(new RecommendGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GroupPurchasePrimaryPublicity purchasePrimaryPublicity) {
                if (publicityList != null) {
                    onClickPublicity(purchasePrimaryPublicity);
                }
            }
        });
    }

    private void onClickPublicity(GroupPurchasePrimaryPublicity publicity) {
        switch (publicity.getGotoType()) {
            case 1: //链接地址
                if (publicity.getLinkUrl().startsWith("maguanjia://")) {
                    if (publicity.getLinkUrl().replace("maguanjia://", "").startsWith("http")) {
                        Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                        intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, publicity.getLinkUrl().replace("maguanjia://", ""));
                        startActivity(intent);
                    } else {
                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + publicity.getLinkUrl().replace("maguanjia://", ""), new RouterCallback() {
                            @Override
                            public void notFound(Context context, Uri uri) {
                                showNoticeDialog();
                            }

                            @Override
                            public void beforeOpen(Context context, Uri uri) {

                            }

                            @Override
                            public void afterOpen(Context context, Uri uri) {

                            }

                            @Override
                            public void error(Context context, Uri uri, Throwable e) {

                            }
                        });
                    }
                } else if (publicity.getLinkUrl().startsWith("http")) {
//                    Intent intent = new Intent(mActivity, Banner2WebActivity.class);
//                    intent.putExtra(Banner2WebActivity.URL, publicity.getLinkUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
//                    intent.putExtra("name", publicity.getName());
//                    startActivity(intent);
                    Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                    intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, publicity.getLinkUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
                    startActivity(intent);
                }
                break;
            case 2: //优惠券、团购
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseCoupon/" + publicity.getGroupPurchaseCouponId());
                break;
            case 3: //团购商家
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + publicity.getGroupPurchaseMerchantId());
                break;
        }
    }

    private void showNoticeDialog() {
        if (noticeDialog == null) {
            noticeDialog = new NoticeDialog(mActivity, new NoticeDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    noticeDialog.dismiss();
                }
            }, "", "您当前版本过低，暂无法使用该功能。请更新到最新版本马管家。");
        }
        noticeDialog.show();
    }

    /**
     * 添加分类导航视图
     *
     * @param primaryCategoryList List
     */
    private void initNavigatorViewPager(List<GroupPurchasePrimaryCategory> primaryCategoryList) {
        if (CheckUtils.isEmptyList(primaryCategoryList)) {
            navigatorLayout.setVisibility(View.GONE);
            return;
        }
        navigatorLayout.setVisibility(View.VISIBLE);
        viewList.clear();

        for (int i = 0; i <= (primaryCategoryList.size() - 1) / NUMBER_OF_NAVIGATOR; i++) {
            NoScrollGridView gridView = new NoScrollGridView(mActivity);
            gridView.setNumColumns(NUMBER_OF_NAVIGATOR);
            gridView.setHorizontalSpacing(0);
            gridView.setVerticalSpacing(0);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
            gridView.setLayoutParams(layoutParams);
            if (primaryCategoryList.size() > NUMBER_OF_NAVIGATOR) {
                gridView.setPadding(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.x20));
            } else {
                gridView.setPadding(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.x14));
            }
            GroupBuyingPrimaryCategoryAdapter adapter = new GroupBuyingPrimaryCategoryAdapter(mActivity);
            gridView.setAdapter(adapter);
            adapter.setData(primaryCategoryList.subList(i * NUMBER_OF_NAVIGATOR, Math.min(primaryCategoryList.size(), i * NUMBER_OF_NAVIGATOR + NUMBER_OF_NAVIGATOR)));
            viewList.add(gridView);
        }

        mPageAdapter.setViews(viewList);
        if (primaryCategoryList.size() <= 8) {
            navigatorIndicator.setVisibility(View.GONE);
        } else {
            navigatorIndicator.setVisibility(View.VISIBLE);
        }
    }

    private class MyPageAdapter extends PagerAdapter {

        private List<View> views = new ArrayList<>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setViews(List<View> views) {
            this.views = views;
            notifyDataSetChanged();
        }

        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
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
                tvMenu1.setText(categories.get(i).getName());
                menuChild1.setText(categories.get(i).getName());

                start = 0;
                groupCategoryId = categories.get(i).getId();
                getData(false);
            }
        });
        View coverView = linearLayout.findViewById(R.id.group_buying_menu_cover_view);
        coverView.setOnClickListener(this);

        leftMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        leftMenuWindow.setOutsideTouchable(true);
        leftMenuWindow.showAsDropDown(groupMenuLayout, 0, 0);

        leftMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismissPopwindow(tvMenu1);
                dismissPopwindow(menuChild1);
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
        midMenuWindow.showAsDropDown(groupMenuLayout, 0, 0);

        midMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismissPopwindow(tvMenu2);
                dismissPopwindow(menuChild2);
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
            start = 0;
            getData(false);
            tvMenu2.setText(bean.getName());
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
        rightMenuWindow.showAsDropDown(groupMenuLayout, 0, 0);

        rightMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismissPopwindow(tvMenu3);
                dismissPopwindow(menuChild3);
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
