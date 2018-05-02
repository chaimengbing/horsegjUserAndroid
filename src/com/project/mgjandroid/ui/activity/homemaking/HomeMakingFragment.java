package com.project.mgjandroid.ui.activity.homemaking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HomemakingInformation;
import com.project.mgjandroid.bean.PositionCategoryBean;
import com.project.mgjandroid.bean.information.InformationBanner;
import com.project.mgjandroid.bean.information.InformationType;
import com.project.mgjandroid.bean.information.RecruitInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.RecruitHotPositionCategoryModel;
import com.project.mgjandroid.model.information.InformationBannerModel;
import com.project.mgjandroid.model.information.InformationHomeMakingListModel;
import com.project.mgjandroid.model.information.InformationRecruitListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.employment.RecruitListAdapter;
import com.project.mgjandroid.ui.activity.information.InformationActivity;
import com.project.mgjandroid.ui.activity.information.InformationCategoryChooseActivity;
import com.project.mgjandroid.ui.activity.information.InformationDetailActivity;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.CircleIndicator;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeMakingFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>, View.OnClickListener {

    public final static String CATEGORY_ID = "category_id";
    public final static String CATEGORY_NAME = "category_name";

    @InjectView(R.id.home_making_layout)
    private LinearLayout homeMakingLayout;
    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;

    private MLoadingDialog mMLoadingDialog;
    private static final int maxResults = 10;
    private int start = 0;

    private long categoryId;
    private int type;
    private MyBanner mMyBanner;
    private int HEADER_SIZE = 1;
    private boolean hasBanner = false;
    private HomeMakingListAdapter adapter;
    private View emptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_making, container, false);
        InjectorFragment.get(this).inject(view);
        Bundle bundle = getArguments();
        categoryId = bundle.getLong(CATEGORY_ID, -1);
        initView();
        initData();
        return view;
    }

    private void initView() {
        mMLoadingDialog = new MLoadingDialog();
        View view = new View(mActivity);
        ViewGroup.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.x10));
        view.setLayoutParams(layoutParams);
        listView.getRefreshableView().addFooterView(view);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
        if (categoryId == -1) {
            getBanner();
        }
        adapter = new HomeMakingListAdapter(R.layout.item_home_making_list_view, mActivity);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getDataCount() <= 0) {
                    return;
                }
                if (position >= listView.getRefreshableView().getHeaderViewsCount()) {
                    HomemakingInformation information = adapter.getItem(position - listView.getRefreshableView().getHeaderViewsCount());
                    InformationDetailActivity.toInformationDetail(mActivity, information.getId(), InformationType.Homemaking.getValue());
                }
            }
        });
        emptyView = mInflater.inflate(R.layout.empty_view_publish, null);
        TextView tvEmpty = (TextView) emptyView.findViewById(R.id.tv_no_data);
        tvEmpty.setText("暂无家政信息");
//        listView.setEmptyView(emptyView);
    }

    private void initData() {
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        if (categoryId == -1) getTagDetail();
        getData(false);
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        map.put("status", 2);
        map.put("isExpire", 0);
        map.put("informationType", InformationType.Homemaking.getValue());
        if (categoryId != -1) map.put("categoryId", categoryId);
        if (ChooseCityModel.getInstance().getCityCode() != 0)
            map.put("cityCode", ChooseCityModel.getInstance().getCityCode());
        if (ChooseCityModel.getInstance().getProvinceId() != 0)
            map.put("province", ChooseCityModel.getInstance().getProvinceId());
        if (ChooseCityModel.getInstance().getCityId() != 0)
            map.put("city", ChooseCityModel.getInstance().getCityId());
        if (ChooseCityModel.getInstance().getDistrictId() != 0)
            map.put("district", ChooseCityModel.getInstance().getDistrictId());
        VolleyOperater<InformationHomeMakingListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                listView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    InformationHomeMakingListModel model = (InformationHomeMakingListModel) obj;
                    List<HomemakingInformation> mlist = new ArrayList<>();
                    mlist.addAll(model.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        listView.getRefreshableView().removeFooterView(emptyView);
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<HomemakingInformation> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(listView, mActivity);
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
        }, InformationHomeMakingListModel.class);
    }

    public void refresh() {
        start = 0;
        getData(false);
        getBanner();
        getTagDetail();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        start = 0;
        getData(false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        start = adapter.getDataCount();
        getData(true);
    }

    private void getBanner() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationType", InformationType.Homemaking.getValue());
        if (ChooseCityModel.getInstance().getCityCode() != 0)
            map.put("cityCode", ChooseCityModel.getInstance().getCityCode());
        if (ChooseCityModel.getInstance().getProvinceId() != 0)
            map.put("province", ChooseCityModel.getInstance().getProvinceId());
        if (ChooseCityModel.getInstance().getCityId() != 0)
            map.put("city", ChooseCityModel.getInstance().getCityId());
        if (ChooseCityModel.getInstance().getDistrictId() != 0)
            map.put("district", ChooseCityModel.getInstance().getDistrictId());
        VolleyOperater<InformationBannerModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFORMATION_BANNER_LOCATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    InformationBannerModel model = (InformationBannerModel) obj;
                    List<InformationBanner> bannerBeen = model.getValue();
                    ArrayList<String> list = new ArrayList<String>();
                    for (InformationBanner bean : bannerBeen) {
                        list.add(bean.getPicUrl());
                    }
                    if (list.size() > 0) {
                        addBanner(list);
                    }
                }
            }
        }, InformationBannerModel.class);
    }

    private void addBanner(ArrayList<String> list) {
        if (!hasBanner) {
            mMyBanner = (MyBanner) mInflater.inflate(R.layout.my_banner, null);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DipToPx.dip2px(mActivity, 96));
            CircleIndicator circleIndicator = (CircleIndicator) mMyBanner.findViewById(R.id.pageIndexor);
            circleIndicator.setVisibility(View.GONE);
            mMyBanner.setLayoutParams(layoutParams);
            mMyBanner.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
            mMyBanner.setPadding(DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 15));
            ListView refreshableView = listView.getRefreshableView();
            refreshableView.addHeaderView(mMyBanner);
            hasBanner = true;
            HEADER_SIZE = 2;
        }
        mMyBanner.setUrls(list, false, false);
    }


    /**
     * 获取tag
     */
    public void getTagDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationTypeId", InformationType.Homemaking.getValue());
        VolleyOperater<RecruitHotPositionCategoryModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INFO_HOT_CATEGORY_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        showTag(new ArrayList<PositionCategoryBean>());
                        return;
                    }
                    RecruitHotPositionCategoryModel model = (RecruitHotPositionCategoryModel) obj;
                    if (model.getValue() != null) {
                        showTag(model.getValue());
                    }
                } else {
                    showTag(new ArrayList<PositionCategoryBean>());
                }
            }
        }, RecruitHotPositionCategoryModel.class);
    }

    private void showTag(List<PositionCategoryBean> list) {
        if (CheckUtils.isEmptyList(list)) {
            homeMakingLayout.setVisibility(View.GONE);
            return;
        }
        homeMakingLayout.setVisibility(View.VISIBLE);
        homeMakingLayout.removeAllViews();
        for (int i = 0; i < 5; i++) {
            final TextView textView = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.color_6));
            textView.setTextSize(13);
            if (i == 4) {
                View view = new View(mActivity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0, 20, 0, 20);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(getResources().getColor(R.color.color_d2));
                homeMakingLayout.addView(view);
            }
            homeMakingLayout.addView(textView);
            if (i < 4 && list.size() > i) {
                textView.setText(list.get(i).getName());
                textView.setTag(list.get(i).getId());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InformationActivity.toInformationList(mActivity, ((Long) textView.getTag()), textView.getText().toString(), InformationType.Homemaking.getValue());
                    }
                });
                if (i < 3) {
                    View view = new View(mActivity);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(0, 20, 0, 20);
                    view.setLayoutParams(layoutParams);
                    view.setBackgroundColor(getResources().getColor(R.color.color_d2));
                    homeMakingLayout.addView(view);
                }
            } else if (i == 4) {
                textView.setText("更多");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InformationCategoryChooseActivity.toChooseInformationCategory(mActivity, InformationType.Homemaking.getValue(), InformationCategoryChooseActivity.FROM_INFORMATION_LIST_PAGE);
                    }
                });
            }
        }
    }
}
