package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseBanner;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseMerchant;
import com.project.mgjandroid.bean.groupbuying.GroupPurchasePrimaryCategory;
import com.project.mgjandroid.bean.groupbuying.GroupPurchasePrimaryPublicity;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.groupbuying.GroupBuyingMerchantListModel;
import com.project.mgjandroid.model.groupbuying.GroupPurchaseBannerListModel;
import com.project.mgjandroid.model.groupbuying.GroupPurchasePrimaryCategoryListModel;
import com.project.mgjandroid.model.groupbuying.GroupPurchasePrimaryPublicityListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.adapter.RecommendGridAdapter;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.ui.view.autoscrollviewpager.AutoScrollViewPager;
import com.project.mgjandroid.ui.view.autoscrollviewpager.indicator.CirclePageIndicator;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
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
    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;

    private NoScrollGridView recommendGridView;

    private MyBanner myBanner;
    private RelativeLayout navigatorLayout;
    private CirclePageIndicator navigatorIndicator;
    private GroupBuyingMerchantAdapter adapter;
    private MyPageAdapter mPageAdapter = new MyPageAdapter();
    private List<View> viewList = new ArrayList<>();

    private ArrayList<GroupPurchaseBanner> bannerList;
    private RecommendGridAdapter recommendGridAdapter;

    private static final int maxResults = 10;
    private int start = 0;
    private LoadingDialog loadingDialog;
    private LinearLayout listHeaderView;
    private NoticeDialog noticeDialog;

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
    }

    private void initView() {
        ivBack.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                back();
                break;
        }
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

        recommendGridView = (NoScrollGridView) listHeaderView.findViewById(R.id.recommend_gridview);
        recommendGridAdapter = new RecommendGridAdapter(R.layout.item_recommend, mActivity);
        recommendGridView.setAdapter(recommendGridAdapter);
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
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        /** 排序方式1:智能排序;2:离我最近;3:好评优先 **/
//        map.put("sortType", 1);
//        map.put("groupPurchaseCategoryId", 1);
//        map.put("childGroupPurchaseCategoryId", 2);
//        /** 商户服务多个逗号分隔：1,2,3,4,5,6,7 **/
//        map.put("groupPurchaseMerchantServices", "1,2");
        VolleyOperater<GroupBuyingMerchantListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_GROUP_PURCHASE_MERCHANT, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                    if (listHeaderView != null)
                        listView.getRefreshableView().addHeaderView(listHeaderView);
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
                            adapter.setList(mlist);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setList(mlist);
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
    private void showPublicity(List<GroupPurchasePrimaryPublicity> publicityList) {

        for (int i = 0; i < publicityList.size(); i++) {
            int r = i % 4;
            GroupPurchasePrimaryPublicity publicity = publicityList.get(i);
            Drawable drawable = null;
            if (publicity != null) {
                if (r == 0) {
                    drawable = getResources().getDrawable(R.drawable.bg_discount);
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
        recommendGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroupPurchasePrimaryPublicity publicity = recommendGridAdapter.getItem(i);
                if (publicity != null) {
                    onClickPublicity(publicity);
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
}
