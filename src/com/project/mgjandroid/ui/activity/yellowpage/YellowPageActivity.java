package com.project.mgjandroid.ui.activity.yellowpage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.yellowpage.YellowPage;
import com.project.mgjandroid.bean.yellowpage.YellowPageBanner;
import com.project.mgjandroid.bean.yellowpage.YellowPageCategory;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.h5container.YLBSdkConstants;
import com.project.mgjandroid.h5container.view.YLBWebViewActivity;
import com.project.mgjandroid.model.yellowpage.YellowPageBannerListModel;
import com.project.mgjandroid.model.yellowpage.YellowPageCategoryModel;
import com.project.mgjandroid.model.yellowpage.YellowPageListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.project.mgjandroid.ui.activity.PrimaryCategoryActivity;
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
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2017/6/12.
 */
@Router("yellowPages")
public class YellowPageActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;
    @InjectView(R.id.yellow_page_search_bar)
    private RelativeLayout rlSearch;

    private MyBanner myBanner;
    private RelativeLayout navigatorLayout;
    private CirclePageIndicator navigatorIndicator;
    private YellowPageListAdapter adapter;
    private MyPageAdapter mPageAdapter = new MyPageAdapter();
    private List<ImageView> publicityViews = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();

    private ArrayList<YellowPageBanner> bannerList;

    private static final int maxResults = 10;
    private int start = 0;
    private LoadingDialog loadingDialog;
    private LinearLayout listHeaderView;
    private View vSearch;
    private TextView emptyView;
    private NoticeDialog noticeDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_yellow_page);
        Injector.get(this).inject();
        initView();
        loadingDialog = new LoadingDialog(mActivity);
        loadingDialog.show();
        getBanner();
        getCategory();
        getData(false);
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        initHeaderView();
        adapter = new YellowPageListAdapter(R.layout.item_yellow_page, mActivity);
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
        listView.setOnItemClickListener(this);
        /*listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0){
                    rlSearch.setVisibility(View.GONE);
                }else if(firstVisibleItem == 1){
                    if(listHeaderView.getHeight() - getScrollY() <= vSearch.getHeight()){

                    }
                }else{

                }
            }
        });*/
        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, YellowPageSearchActivity.class));
            }
        });
        emptyView = new TextView(mActivity);
        ViewGroup.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.x100));
        emptyView.setLayoutParams(layoutParams);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setText("暂无信息~");
    }

    public float getScrollY() {
        View c = listView.getRefreshableView().getChildAt(1);
        if (c == null) {
            return 0;
        }
        return (float) c.getTop();
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
        listHeaderView = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.yellow_page_list_header_layout, null);
        myBanner = (MyBanner) listHeaderView.findViewById(R.id.slideshowView);
        vSearch = listHeaderView.findViewById(R.id.yellow_page_search);
        vSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, YellowPageSearchActivity.class));
            }
        });
        myBanner.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                YellowPageBanner bannerItem = bannerList.get(position);
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
                        } else {
//                            Intent intent = new Intent(mActivity, Banner2WebActivity.class);
//                            intent.putExtra(Banner2WebActivity.URL, bannerItem.getUrl());
//                            intent.putExtra("name", bannerList.get(position).getName());
//                            startActivity(intent);
                            Intent intent = new Intent(mActivity, YLBWebViewActivity.class);
                            intent.putExtra(YLBSdkConstants.EXTRA_H5_URL, bannerItem.getUrl());
                            startActivity(intent);
                        }
                        break;
                    case 2: //优惠券、团购
//                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseCoupon/" + bannerItem.getGroupPurchaseCouponId());
                        Intent intent1 = new Intent(mActivity, PrimaryCategoryActivity.class);
                        intent1.putExtra("tagCategoryId", bannerItem.getFirstCategoryId());
                        intent1.putExtra("tagCategorySecondId", bannerItem.getSecondCategoryId());
                        intent1.putExtra("tagCategoryType", bannerItem.getCategoryType());
                        intent1.putExtra("categoryName", bannerItem.getName());
                        startActivity(intent1);
                        break;
                    case 3: //团购商家
                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + "merchant/" + bannerItem.getMerchantId());
                        break;
                    case 4: //团购分类
                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseDetail/" + bannerItem.getGroupBuyId());
                        break;
                    case 6:
                        if (HomeActivity.instance != null) {
                            YellowPageActivity.this.finish();
                            HomeActivity.instance.setToSuperMarket(bannerItem.getFirstTGoodsCategoryId(), bannerItem.getSecondTGoodsCategoryId());
                        }
                        break;
                    case 7: // 团购商家
                        Routers.open(mActivity, ActivitySchemeManager.SCHEME + "groupPurchaseMerchant/" + bannerItem.getGroupPurchaseMerchantId());
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
    }

    private void getBanner() {
        VolleyOperater<YellowPageBannerListModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_YELLOW_PAGE_BANNER, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    bannerList = ((YellowPageBannerListModel) obj).getValue();
                    if (CheckUtils.isNoEmptyList(bannerList)) {
                        List<String> imageIdList = new ArrayList<>();
                        for (YellowPageBanner banner : bannerList) {
                            imageIdList.add(banner.getPicUrl());
                        }
                        myBanner.setUrls(imageIdList, false, true);
                    } else {
                        myBanner.setBackgroundResource(R.drawable.banner_default);
                    }
                }
            }
        }, YellowPageBannerListModel.class);
    }

    private void getCategory() {
        VolleyOperater<YellowPageCategoryModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_YELLOW_PAGE_TYPE, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    ArrayList<YellowPageCategory> categories = ((YellowPageCategoryModel) obj).getValue();
                    if (CheckUtils.isNoEmptyList(categories)) {
                        initNavigatorViewPager(categories);
                    }
                }
            }
        }, YellowPageCategoryModel.class);
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        map.put("isNearby", 1);
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<YellowPageListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_YELLOW_PAGE_LIST, map, new VolleyOperater.ResponseListener() {
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
                    YellowPageListModel model = (YellowPageListModel) obj;
                    List<YellowPage> mlist = new ArrayList<>();
                    mlist.addAll(model.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<YellowPage> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                            }
                        } else {
                            adapter.setData(mlist);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setData(mlist);
                            listView.getRefreshableView().removeFooterView(emptyView);
                            listView.getRefreshableView().addFooterView(emptyView);
                        }
                    }
                }
            }
        }, YellowPageListModel.class);
    }

    /**
     * 添加分类导航视图
     *
     * @param primaryCategoryList List
     */
    private void initNavigatorViewPager(List<YellowPageCategory> primaryCategoryList) {
        if (CheckUtils.isEmptyList(primaryCategoryList)) {
            navigatorLayout.setVisibility(View.GONE);
            return;
        }
        navigatorLayout.setVisibility(View.VISIBLE);
        viewList.clear();

        for (int i = 0; i <= (primaryCategoryList.size() - 1) / 8; i++) {
            NoScrollGridView gridView = new NoScrollGridView(mActivity);
            gridView.setNumColumns(4);
            gridView.setHorizontalSpacing(0);
            gridView.setVerticalSpacing(0);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
            gridView.setLayoutParams(layoutParams);
            if (primaryCategoryList.size() > 8) {
                gridView.setPadding(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.x20));
            } else {
                gridView.setPadding(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.x14));
            }
            YellowPagePrimaryCategoryAdapter adapter = new YellowPagePrimaryCategoryAdapter(mActivity);
            gridView.setAdapter(adapter);
            adapter.setData(primaryCategoryList.subList(i * 8, Math.min(primaryCategoryList.size(), i * 8 + 8)));
            viewList.add(gridView);
        }

        mPageAdapter.setViews(viewList);
        if (primaryCategoryList.size() <= 8) {
            navigatorIndicator.setVisibility(View.GONE);
        } else {
            navigatorIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < 2) return;
        long clickId = adapter.getData().get(position - 2).getId();
        Intent intent = new Intent(mActivity, YellowPageDetailActivity.class);
        intent.putExtra("id", clickId);
        startActivity(intent);
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
}
