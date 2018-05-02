package com.project.mgjandroid.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Agent;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.Menu;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.FindAgentModel;
import com.project.mgjandroid.model.SuperMarketCartModel;
import com.project.mgjandroid.model.SuperMarketGoodsCategoryModel;
import com.project.mgjandroid.model.SuperMarketGoodsListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.LocationNewActivity;
import com.project.mgjandroid.ui.activity.MarketCartActivity;
import com.project.mgjandroid.ui.activity.MarketSearchActivity;
import com.project.mgjandroid.ui.adapter.SuperMarketExpandableListAdapter;
import com.project.mgjandroid.ui.adapter.SuperMarketGoodsListAdapter;
import com.project.mgjandroid.ui.view.AlwaysMarqueeTextView;
import com.project.mgjandroid.ui.view.AnimatedExpandableListView;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/10/10.
 */

public class SuperMarketFragment extends BaseFragment implements View.OnClickListener {

    private static SuperMarketFragment fragment;

    private View view;
    private AlwaysMarqueeTextView tvAddress;
    private ImageView ivSearch;
    private TextView tvNoSuperMarket;
    private LinearLayout layoutSuperMarket;
    private RelativeLayout rlComprehensive;
    private TextView tvComprehensive;
    private View lineComprehensive;
    private RelativeLayout rlPrice;
    private TextView tvPrice;
    private View linePrice;
    private RelativeLayout rlSales;
    private TextView tvSales;
    private View lineSales;
    private AnimatedExpandableListView expandableListView;
    private PullToRefreshListView goodsListView;
    private FrameLayout cartLayout;
    private TextView tvCartCount;

    private Drawable priceRankingNormal;
    private Drawable priceRankingUp;
    private Drawable priceRankingDown;

    private ViewGroup anim_mask_layout;
    private static int HEIGHT = 100;// 抛物线高度
    private int[] startPoint = new int[2];
    private int[] endPoint = new int[2];
    private int maxRight = 0;
    private int minRight = 0;
    private int minBottom = 0;
    private int maxBottom = 0;
    private boolean isMoved = false;
    private float downX = 0;
    private float downY = 0;
    private int rightMargin = 0;
    private int bottomMargin = 0;
    private int cartLayoutWidth = 0;
    private float a;
    private float b;
    private float c;
    private int count = 300;

    private SuperMarketCartModel superMarketCartModel;
    private ArrayList<Menu> list = new ArrayList<>();
    private ArrayList<Goods> goodsList = new ArrayList<>();
    private SuperMarketExpandableListAdapter adapter;
    private SuperMarketGoodsListAdapter goodsAdapter;
    private MLoadingDialog mMLoadingDialog;
    private Agent agent = null;

    private final static int PAGE_SIZE = 15;
    private int start = 0;
    private int queryType = 1;// 1:综合排序 2:价格 3:销售
    private int orderType = 1;// 1:降序 2升序
    private long categoryId = -1;
    private long merchantId = -1;
    private long firstTGoodsCategoryId = 0;
    private long secondTGoodsCategoryId = 0;

    public static SuperMarketFragment newInstance() {
        if (fragment == null) {
            fragment = new SuperMarketFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.super_market_fragment, container, false);
        initData();
        initViews();
        return view;
    }

    private void initData() {
        superMarketCartModel = SuperMarketCartModel.getInstance();
        superMarketCartModel.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CheckUtils.isNoEmptyList(goodsList)) {
            for (Goods g : goodsList) {
                g.getGoodsSpecList().get(0).setBuyCount(0);
                for (SuperMarketCartModel.CartGoods cartGoods : superMarketCartModel.getSuperMarketCartBean().getCartGoods()) {
                    if (cartGoods.getGoodsId() == g.getId()) {
                        g.getGoodsSpecList().get(0).setBuyCount(cartGoods.getButCount());
                    }
                }
            }
            goodsAdapter.setList(goodsList);
        }
        String count = superMarketCartModel.getSuperMarketCartBean().getTotalCount();
        if (CheckUtils.isNoEmptyStr(count)) {
            tvCartCount.setVisibility(View.VISIBLE);
        } else {
            tvCartCount.setVisibility(View.INVISIBLE);
        }
        tvCartCount.setText(count);
    }

    public void dismissDialog() {
        if (mMLoadingDialog != null) mMLoadingDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvAddress.setText("");
        agent = null;
        endPoint = new int[]{0, 0};
        queryType = 1;
        if (anim_mask_layout != null) {
            ((ViewGroup) anim_mask_layout.getParent()).removeView(anim_mask_layout);
            anim_mask_layout = null;
        }

        try {
            if (mMLoadingDialog != null) mMLoadingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("TAG", "SuperMarketFragment-onDestroy");
    }

    private void initViews() {
        tvAddress = (AlwaysMarqueeTextView) view.findViewById(R.id.super_market_fragment_tv_address);
        ivSearch = (ImageView) view.findViewById(R.id.super_market_fragment_iv_search);
        tvNoSuperMarket = (TextView) view.findViewById(R.id.no_super_market);
        layoutSuperMarket = (LinearLayout) view.findViewById(R.id.layout_super_market);
        tvAddress.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        rlComprehensive = (RelativeLayout) view.findViewById(R.id.super_market_fragment_layout_comprehensive_ranking);
        rlPrice = (RelativeLayout) view.findViewById(R.id.super_market_fragment_layout_price_ranking);
        rlSales = (RelativeLayout) view.findViewById(R.id.super_market_fragment_layout_sales_ranking);
        tvComprehensive = (TextView) view.findViewById(R.id.super_market_fragment_tv_comprehensive_ranking);
        tvPrice = (TextView) view.findViewById(R.id.super_market_fragment_tv_price_ranking);
        tvSales = (TextView) view.findViewById(R.id.super_market_fragment_tv_sales_ranking);
        lineComprehensive = view.findViewById(R.id.super_market_fragment_comprehensive_ranking_line);
        linePrice = view.findViewById(R.id.super_market_fragment_price_ranking_line);
        lineSales = view.findViewById(R.id.super_market_fragment_sales_ranking_line);
        cartLayout = (FrameLayout) view.findViewById(R.id.layout_super_market_cart);
        tvCartCount = (TextView) view.findViewById(R.id.cart_num);
        FrameLayout emptyView = (FrameLayout) mActivity.getLayoutInflater().inflate(R.layout.super_market_empty_view, null);
        cartLayout.setOnClickListener(this);
        cartLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isMoved = false;
                        if (maxRight == 0 || maxBottom == 0) {
                            rightMargin = ((RelativeLayout.LayoutParams) cartLayout.getLayoutParams()).rightMargin;
                            bottomMargin = ((RelativeLayout.LayoutParams) cartLayout.getLayoutParams()).bottomMargin;
                            maxRight = rightMargin + getResources().getDimensionPixelOffset(R.dimen.x40);
                            maxBottom = bottomMargin + getResources().getDimensionPixelOffset(R.dimen.x40);
                        }
                        downX = motionEvent.getRawX();
                        downY = motionEvent.getRawY();
//                        MLog.e("---->rightMargin:" + rightMargin + "---->bottomMargin:" + bottomMargin);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float moveX = motionEvent.getRawX() - downX;
                        float moveY = motionEvent.getRawY() - downY;
                        if (!isMoved && moveX * moveX + moveY * moveY <= 10) return true;
                        isMoved = true;
//                        MLog.e("---->moveX:" + moveX + "---->moveY:" + moveY);
                        int marginRight = (int) (rightMargin - moveX);
                        int marginBottom = (int) (bottomMargin - moveY);
                        if (marginRight > maxRight) marginRight = maxRight;
                        if (marginRight < minRight) marginRight = minRight;
                        if (marginBottom > maxBottom) marginBottom = maxBottom;
                        if (marginBottom < minBottom) marginBottom = minBottom;
                        rightMargin = marginRight;
                        bottomMargin = marginBottom;
                        downX = motionEvent.getRawX();
                        downY = motionEvent.getRawY();
//                        MLog.e("---->rightMargin:" + marginRight + "---->bottomMargin:" + marginBottom);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cartLayout.getLayoutParams();
                        layoutParams.rightMargin = marginRight;
                        layoutParams.bottomMargin = marginBottom;
                        cartLayout.setLayoutParams(layoutParams);
                        break;

                    case MotionEvent.ACTION_UP:
                        if (isMoved) {
                            cartLayout.getLocationInWindow(endPoint);
                        } else {
                            cartLayout.performClick();
                        }
                        break;

                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                        if (isMoved) {
                            cartLayout.getLocationInWindow(endPoint);
                        }
                        break;
                }
                return true;
            }
        });
        rlComprehensive.setOnClickListener(this);
        rlSales.setOnClickListener(this);
        rlPrice.setOnClickListener(this);
        mMLoadingDialog = new MLoadingDialog();
        expandableListView = (AnimatedExpandableListView) view.findViewById(R.id.super_market_fragment_expandable_listview);
        adapter = new SuperMarketExpandableListAdapter(mActivity);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                if (categoryId == list.get(groupPosition).getGoodsCategoryList().get(childPosition).getId())
                    return true;
                categoryId = list.get(groupPosition).getGoodsCategoryList().get(childPosition).getId();
                for (int i = 0, size = list.get(groupPosition).getGoodsCategoryList().size(); i < size; i++) {
                    if (i == childPosition) {
                        list.get(groupPosition).getGoodsCategoryList().get(i).setSelected(true);
                    } else {
                        list.get(groupPosition).getGoodsCategoryList().get(i).setSelected(false);
                    }
                }
                adapter.refreshData(list);
                requestGoodsList(false);
                return true;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView listView, View view, int groupPosition, long id) {
                if (list.get(groupPosition).getId() == null) {
                    if (categoryId == -1) {
                        return true;
                    } else {
                        categoryId = -1;
                        list.get(groupPosition).setSelected(true);
                        expandableListView.expandGroupWithAnimation(groupPosition);
                        goodsListView.setMode(PullToRefreshBase.Mode.DISABLED);
                        for (int i = 0; i < list.size(); i++) {
                            if (i != groupPosition) {
                                list.get(i).setSelected(false);
                                if (expandableListView.isGroupExpanded(i)) {
                                    expandableListView.collapseGroup(i);
                                }
                            }
                            if (list.get(i).getGoodsCategoryList() != null) {
                                for (int j = 0, size = list.get(i).getGoodsCategoryList().size(); j < size; j++) {
                                    list.get(i).getGoodsCategoryList().get(j).setSelected(false);
                                }
                            }
                        }
                        adapter.refreshData(list);
                        requestGoodsList(false);
                        return true;
                    }
                } else if (CheckUtils.isEmptyList(list.get(groupPosition).getGoodsCategoryList())) {
                    if (list.get(groupPosition).getId() == categoryId) {
                        return true;
                    } else {
                        categoryId = list.get(groupPosition).getId();
                        expandableListView.expandGroupWithAnimation(groupPosition);
                        goodsListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                        for (int i = 0; i < list.size(); i++) {
                            if (i != groupPosition) {
                                list.get(i).setSelected(false);
                                if (expandableListView.isGroupExpanded(i)) {
                                    expandableListView.collapseGroup(i);
                                }
                            } else {
                                list.get(i).setSelected(true);
                            }
                            if (list.get(i).getGoodsCategoryList() != null) {
                                for (int j = 0, size = list.get(i).getGoodsCategoryList().size(); j < size; j++) {
                                    list.get(i).getGoodsCategoryList().get(j).setSelected(false);
                                }
                            }
                        }
                        adapter.refreshData(list);
                        requestGoodsList(false);
                        return true;
                    }
                } else {
                    if (list.get(groupPosition).getId() == categoryId) {
                        list.get(groupPosition).setSelected(false);
                        expandableListView.expandGroupWithAnimation(groupPosition);
                        categoryId = list.get(groupPosition).getGoodsCategoryList().get(0).getId();
                        list.get(groupPosition).getGoodsCategoryList().get(0).setSelected(true);
                        adapter.refreshData(list);
                        requestGoodsList(false);
                        return true;
                    } else if (expandableListView.isGroupExpanded(groupPosition)) {
                        expandableListView.collapseGroupWithAnimation(groupPosition);
                        list.get(groupPosition).setSelected(true);
                        categoryId = list.get(groupPosition).getId();
                        for (int j = 0, size = list.get(groupPosition).getGoodsCategoryList().size(); j < size; j++) {
                            list.get(groupPosition).getGoodsCategoryList().get(j).setSelected(false);
                        }
                        adapter.refreshData(list);
                        requestGoodsList(false);
                        return true;
                    } else {
                        goodsListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getGoodsCategoryList() != null) {
                                for (int j = 0, size = list.get(i).getGoodsCategoryList().size(); j < size; j++) {
                                    list.get(i).getGoodsCategoryList().get(j).setSelected(false);
                                }
                            }
                            if (i != groupPosition) {
                                list.get(i).setSelected(false);
                                if (expandableListView.isGroupExpanded(i)) {
                                    expandableListView.collapseGroup(i);
                                }
                            } else {
                                list.get(i).setSelected(false);
                                categoryId = list.get(i).getGoodsCategoryList().get(0).getId();
                                list.get(i).getGoodsCategoryList().get(0).setSelected(true);
                                expandableListView.expandGroupWithAnimation(i);
                            }
                        }
                        adapter.refreshData(list);
                        requestGoodsList(false);
                        return true;
                    }
                }
            }
        });

        goodsListView = (PullToRefreshListView) view.findViewById(R.id.super_market_fragment_listview);
        goodsListView.getRefreshableView().setEmptyView(emptyView);
        goodsListView.setMode(PullToRefreshBase.Mode.DISABLED);
        goodsListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {

            }

            @Override
            public void onPullDownValue(PullToRefreshBase refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                requestGoodsList(true);
            }
        });
//        goodsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(mActivity, CommodityDetailActivity.class);
//                intent.putExtra("goods", goodsList.get(i - goodsListView.getRefreshableView().getHeaderViewsCount()));
//                startActivity(intent);
//            }
//        });
        goodsAdapter = new SuperMarketGoodsListAdapter(mActivity);
        goodsAdapter.setListener(new MyAddOrMinusGoodsListener());
        goodsListView.setAdapter(goodsAdapter);
        priceRankingNormal = getResources().getDrawable(R.drawable.price_ranking_normal);
        if (priceRankingNormal != null) {
            priceRankingNormal.setBounds(0, 0, priceRankingNormal.getMinimumWidth(), priceRankingNormal.getMinimumHeight());
        }
        priceRankingUp = getResources().getDrawable(R.drawable.price_ranking_up);
        if (priceRankingUp != null) {
            priceRankingUp.setBounds(0, 0, priceRankingUp.getMinimumWidth(), priceRankingUp.getMinimumHeight());
        }
        priceRankingDown = getResources().getDrawable(R.drawable.price_ranking_down);
        if (priceRankingDown != null) {
            priceRankingDown.setBounds(0, 0, priceRankingDown.getMinimumWidth(), priceRankingDown.getMinimumHeight());
        }
    }

    public void setTGoodsCategoryId(long firstTGoodsCategoryId, long secondTGoodsCategoryId) {
        this.firstTGoodsCategoryId = firstTGoodsCategoryId;
        this.secondTGoodsCategoryId = secondTGoodsCategoryId;
    }

    /**
     * 显示定位信息，并刷新列表
     */
    public void showAddress() {
        String address = PreferenceUtils.getAddressName(mActivity);

        if (CheckUtils.isNoEmptyStr(address)) {
            if (!("送至" + address).equals(tvAddress.getText().toString())) {
                findAgent();
                tvAddress.setText("送至" + address);
            } else {
                setCategory();
            }
        } else {
            tvAddress.setText("未知位置");
            agent = null;
            tvNoSuperMarket.setVisibility(View.VISIBLE);
            layoutSuperMarket.setVisibility(View.INVISIBLE);
        }
    }

    private void setCategory() {
        if (firstTGoodsCategoryId > 0 && CheckUtils.isNoEmptyList(list)) {
            if (secondTGoodsCategoryId > 0 && categoryId == secondTGoodsCategoryId) {
                requestGoodsList(false);
            } else if (secondTGoodsCategoryId > 0 && categoryId != secondTGoodsCategoryId) {
                for (int i = 0, size = list.size(); i < size; i++) {
                    Menu menu = list.get(i);
                    if (menu.getId() != null && menu.getId() == firstTGoodsCategoryId) {
                        list.get(i).setSelected(false);
                        if (menu.getGoodsCategoryList() != null) {
                            for (int j = 0, size1 = menu.getGoodsCategoryList().size(); j < size1; j++) {
                                if (menu.getGoodsCategoryList().get(j).getId() == secondTGoodsCategoryId) {
                                    menu.getGoodsCategoryList().get(j).setSelected(true);
                                    categoryId = secondTGoodsCategoryId;
                                    if (!expandableListView.isGroupExpanded(i)) {
                                        expandableListView.expandGroupWithAnimation(i);
                                    }
                                    goodsListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                                    requestGoodsList(false);
                                } else {
                                    menu.getGoodsCategoryList().get(j).setSelected(false);
                                }
                            }
                            if (categoryId != secondTGoodsCategoryId) {
                                categoryId = firstTGoodsCategoryId;
                                list.get(i).setSelected(true);
                                expandableListView.collapseGroup(i);
                                goodsListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                                requestGoodsList(false);
                            }
                        }
                    } else {
                        list.get(i).setSelected(false);
                        if (expandableListView.isGroupExpanded(i)) {
                            expandableListView.collapseGroup(i);
                            if (menu.getGoodsCategoryList() != null) {
                                for (int j = 0, size1 = menu.getGoodsCategoryList().size(); j < size1; j++) {
                                    menu.getGoodsCategoryList().get(j).setSelected(false);
                                }
                            }
                        }
                    }
                }
                adapter.refreshData(list);
            } else if (categoryId != firstTGoodsCategoryId) {
                for (int i = 0, size = list.size(); i < size; i++) {
                    Menu menu = list.get(i);
                    if (menu.getId() != null && menu.getId() == firstTGoodsCategoryId) {
                        list.get(i).setSelected(true);
                        if (expandableListView.isGroupExpanded(i)) {
                            expandableListView.collapseGroup(i);
                        }
                        categoryId = firstTGoodsCategoryId;
                        goodsListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                        requestGoodsList(false);
                    } else {
                        list.get(i).setSelected(false);
                        if (expandableListView.isGroupExpanded(i)) {
                            expandableListView.collapseGroup(i);
                        }
                    }
                    if (menu.getGoodsCategoryList() != null) {
                        for (int j = 0, size1 = menu.getGoodsCategoryList().size(); j < size1; j++) {
                            menu.getGoodsCategoryList().get(j).setSelected(false);
                        }
                    }
                }
                adapter.refreshData(list);
            } else if (categoryId == firstTGoodsCategoryId) {
                requestGoodsList(false);
            }
            firstTGoodsCategoryId = 0;
            secondTGoodsCategoryId = 0;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.super_market_fragment_tv_address:
                Intent intent = new Intent(mActivity, LocationNewActivity.class);
                intent.putExtra("curAddress", tvAddress.getText().toString().replace("送至", ""));
                startActivityForResult(intent, ActRequestCode.LOCATION);
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                break;
            case R.id.super_market_fragment_iv_search:
                Intent search = new Intent(mActivity, MarketSearchActivity.class);
                search.putExtra("merchantId", merchantId);
                startActivity(search);
                break;
            case R.id.super_market_fragment_layout_comprehensive_ranking:
                if (queryType != 1) {
                    tvComprehensive.setTextColor(getResources().getColor(R.color.title_bar_bg));
                    lineComprehensive.setVisibility(View.VISIBLE);
                    tvPrice.setTextColor(getResources().getColor(R.color.color_3));
                    linePrice.setVisibility(View.GONE);
                    tvSales.setTextColor(getResources().getColor(R.color.color_3));
                    lineSales.setVisibility(View.GONE);
                    tvPrice.setCompoundDrawables(null, null, priceRankingNormal, null);
                    queryType = 1;
                    requestGoodsList(false);
                }
                break;
            case R.id.super_market_fragment_layout_price_ranking:
                if (queryType != 2) {
                    tvComprehensive.setTextColor(getResources().getColor(R.color.color_3));
                    lineComprehensive.setVisibility(View.GONE);
                    tvPrice.setTextColor(getResources().getColor(R.color.title_bar_bg));
                    linePrice.setVisibility(View.VISIBLE);
                    tvSales.setTextColor(getResources().getColor(R.color.color_3));
                    lineSales.setVisibility(View.GONE);
                    tvPrice.setCompoundDrawables(null, null, priceRankingUp, null);
                    queryType = 2;
                    orderType = 2;
                    requestGoodsList(false);
                } else if (orderType == 2) {
                    tvPrice.setCompoundDrawables(null, null, priceRankingDown, null);
                    orderType = 1;
                    requestGoodsList(false);
                } else if (orderType == 1) {
                    tvPrice.setCompoundDrawables(null, null, priceRankingUp, null);
                    orderType = 2;
                    requestGoodsList(false);
                }
                break;
            case R.id.super_market_fragment_layout_sales_ranking:
                if (queryType != 3) {
                    tvComprehensive.setTextColor(getResources().getColor(R.color.color_3));
                    lineComprehensive.setVisibility(View.GONE);
                    tvPrice.setTextColor(getResources().getColor(R.color.color_3));
                    linePrice.setVisibility(View.GONE);
                    tvSales.setTextColor(getResources().getColor(R.color.title_bar_bg));
                    lineSales.setVisibility(View.VISIBLE);
                    tvPrice.setCompoundDrawables(null, null, priceRankingNormal, null);
                    queryType = 3;
                    requestGoodsList(false);
                }
                break;
            case R.id.layout_super_market_cart:
                Intent marketCart = new Intent(mActivity, MarketCartActivity.class);
                startActivity(marketCart);
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                break;
        }
    }

    private void findAgent() {
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<FindAgentModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_MERCHANT_SHOP_AGENT, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        mMLoadingDialog.dismiss();
                        tvNoSuperMarket.setVisibility(View.VISIBLE);
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                    } else {
                        if (((FindAgentModel) obj).getValue() != null) {
                            if (agent == null || !agent.getId().equals(((FindAgentModel) obj).getValue().getId())) {
                                agent = ((FindAgentModel) obj).getValue();
                                if (rlComprehensive != null) rlComprehensive.performClick();
                                getCategory(agent.getId());
                                if (agent.getId() != superMarketCartModel.getSuperMarketCartBean().getAgentId()) {
                                    superMarketCartModel.getSuperMarketCartBean().clearCart();
                                }
                            } else {
                                mMLoadingDialog.dismiss();
                                setCategory();
                            }
                        } else {
                            agent = null;
                            firstTGoodsCategoryId = 0;
                            secondTGoodsCategoryId = 0;
//                            superMarketCartModel.getSuperMarketCartBean().clearCart();
                            layoutSuperMarket.setVisibility(View.INVISIBLE);
                            tvNoSuperMarket.setVisibility(View.VISIBLE);
                            cartLayout.setVisibility(View.INVISIBLE);
                            ivSearch.setVisibility(View.INVISIBLE);
                            mMLoadingDialog.dismiss();
                        }
                    }
                } else {
                    mMLoadingDialog.dismiss();
                }
            }
        }, FindAgentModel.class);
    }

    private void getCategory(Long agentId) {
        final Map<String, Object> map = new HashMap<>();
        if (mActivity != null && PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        map.put("agentId", agentId);
        VolleyOperater<SuperMarketGoodsCategoryModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_MERCHANT_SHOP_GOODS_CATEGORY_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                try {

                    if (mMLoadingDialog != null) mMLoadingDialog.dismiss();

                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    if (((SuperMarketGoodsCategoryModel) obj).getCode() == 0) {
                        if (CheckUtils.isNoEmptyList(((SuperMarketGoodsCategoryModel) obj).getValue().getMerchantShopGoodsCategoryList())) {
                            categoryId = -2;
                            list = ((SuperMarketGoodsCategoryModel) obj).getValue().getMerchantShopGoodsCategoryList();
                            adapter.refreshData(list);
                            layoutSuperMarket.setVisibility(View.VISIBLE);
                            tvNoSuperMarket.setVisibility(View.INVISIBLE);
                            ivSearch.setVisibility(View.VISIBLE);
                            cartLayout.setVisibility(View.VISIBLE);
                            merchantId = ((SuperMarketGoodsCategoryModel) obj).getValue().getMerchantId();

                            if (merchantId != superMarketCartModel.getSuperMarketCartBean().getMerchantId()) {
                                superMarketCartModel.getSuperMarketCartBean().setMerchantId(merchantId);
                                superMarketCartModel.getSuperMarketCartBean().setAgentId(agent.getId());
                                superMarketCartModel.getSuperMarketCartBean().clearCart();
                            }
                            String count = superMarketCartModel.getSuperMarketCartBean().getTotalCount();
                            if (CheckUtils.isNoEmptyStr(count)) {
                                tvCartCount.setVisibility(View.VISIBLE);
                            } else {
                                tvCartCount.setVisibility(View.INVISIBLE);
                            }
                            tvCartCount.setText(count);

                            if (firstTGoodsCategoryId > 0) {
                                categoryId = firstTGoodsCategoryId;
                                goodsListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                                for (int i = 0; i < list.size(); i++) {
                                    Menu menu = list.get(i);
                                    if (menu.getId() != null && menu.getId() == firstTGoodsCategoryId) {
                                        if (secondTGoodsCategoryId > 0 && CheckUtils.isNoEmptyList(list.get(i).getGoodsCategoryList())) {
                                            for (int j = list.get(i).getGoodsCategoryList().size() - 1; j >= 0; j--) {
                                                if (list.get(i).getGoodsCategoryList().get(j).getId() == secondTGoodsCategoryId) {
                                                    list.get(i).getGoodsCategoryList().get(j).setSelected(true);
                                                    categoryId = secondTGoodsCategoryId;
                                                    list.get(i).setSelected(false);
                                                    expandableListView.expandGroup(i);
                                                    break;
                                                }
                                            }
                                        }
                                        if (categoryId != secondTGoodsCategoryId) {
                                            list.get(i).setSelected(true);
                                        }
                                    } else {
                                        list.get(i).setSelected(false);
                                        expandableListView.collapseGroup(i);
                                    }
                                }
                            }

                            if (categoryId == -2) {
                                if (((SuperMarketGoodsCategoryModel) obj).getValue().getShowHotsale() == 1) {
                                    categoryId = -1;
                                    goodsListView.setMode(PullToRefreshBase.Mode.DISABLED);
                                } else {
                                    categoryId = list.get(0).getId();
                                    goodsListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                                }
                                for (int i = 0; i < list.size(); i++) {
                                    if (i != 0) {
                                        list.get(i).setSelected(false);
                                        expandableListView.collapseGroup(i);
                                    } else {
                                        if (list.get(i).getId() != null && CheckUtils.isNoEmptyList(list.get(i).getGoodsCategoryList())) {
                                            list.get(i).setSelected(false);
                                            categoryId = list.get(i).getGoodsCategoryList().get(0).getId();
                                            list.get(i).getGoodsCategoryList().get(0).setSelected(true);
                                        } else {
                                            list.get(i).setSelected(true);
                                        }
                                        expandableListView.expandGroup(i);
                                    }
                                }
                            }
                            adapter.refreshData(list);
                            requestGoodsList(false);
                        } else {
                            layoutSuperMarket.setVisibility(View.INVISIBLE);
                            tvNoSuperMarket.setVisibility(View.VISIBLE);
                            ivSearch.setVisibility(View.INVISIBLE);
                            cartLayout.setVisibility(View.INVISIBLE);
                        }
                    }
                    firstTGoodsCategoryId = 0;
                    secondTGoodsCategoryId = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SuperMarketGoodsCategoryModel.class);
    }

    private void requestGoodsList(final boolean isAddMore) {
        if (isAddMore) {
            start = goodsList.size();
        } else {
            start = 0;
        }
        String url = "";
        Map<String, Object> map = new HashMap<>();
        if (categoryId == -1) {
            url = Constants.URL_MERCHANT_SHOP_HOTSALE_GOODS_LIST;
            map.put("merchantId", merchantId);
        } else {
            url = Constants.URL_MERCHANT_SHOP_GOODS_LIST;
            map.put("categoryId", categoryId);
            map.put("start", start);
            map.put("size", PAGE_SIZE);
        }
        map.put("queryType", queryType);
        map.put("orderType", orderType);
        VolleyOperater<SuperMarketGoodsListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(url, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                goodsListView.onRefreshComplete();
                if (isSucceed) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    if (((SuperMarketGoodsListModel) obj).getCode() == 0) {
                        ArrayList<Goods> goods = ((SuperMarketGoodsListModel) obj).getValue();
                        if (CheckUtils.isNoEmptyList(goods) && CheckUtils.isNoEmptyList(superMarketCartModel.getSuperMarketCartBean().getCartGoods())) {
                            for (Goods g : goods) {
                                for (SuperMarketCartModel.CartGoods cartGoods : superMarketCartModel.getSuperMarketCartBean().getCartGoods()) {
                                    if (cartGoods.getGoodsId() == g.getId()) {
                                        g.getGoodsSpecList().get(0).setBuyCount(cartGoods.getButCount());
                                    }
                                }
                            }
                        }
                        if (isAddMore) {
                            if (CheckUtils.isEmptyList(goods)) {
                                ToastUtils.displayMsg("已经到底了~~", mActivity);
                            } else {
                                goodsList.addAll(goods);
                                goodsAdapter.setList(goodsList);
                            }
                        } else {
                            if (CheckUtils.isEmptyList(goods)) {
                                goodsList = new ArrayList<>();
                                goodsAdapter.setList(goodsList);
                            } else {
                                goodsList = goods;
                                goodsAdapter.setList(goodsList);
                                goodsListView.getRefreshableView().setSelection(0);
                            }
                        }
                    }
                }
            }
        }, SuperMarketGoodsListModel.class);
    }

    private class MyAddOrMinusGoodsListener implements SuperMarketGoodsListAdapter.AddOrMinusGoodsListener {
        @Override
        public void add(int[] point, Drawable drawable) {
            startAnim(point, drawable);
        }

        @Override
        public void addCart(long goodsId, int count, int position) {
            superMarketCartModel.getSuperMarketCartBean().addGoods(goodsId, count);
            String num = superMarketCartModel.getSuperMarketCartBean().getTotalCount();
            if (CheckUtils.isNoEmptyStr(num)) {
                tvCartCount.setVisibility(View.VISIBLE);
            } else {
                tvCartCount.setVisibility(View.INVISIBLE);
            }
            tvCartCount.setText(num);
        }

        @Override
        public void minusCart(long goodsId, int count, int position) {
            superMarketCartModel.getSuperMarketCartBean().minusGoods(goodsId, count);
            String num = superMarketCartModel.getSuperMarketCartBean().getTotalCount();
            if (CheckUtils.isNoEmptyStr(num)) {
                tvCartCount.setVisibility(View.VISIBLE);
            } else {
                tvCartCount.setVisibility(View.INVISIBLE);
            }
            tvCartCount.setText(num);
        }
    }

    private void startAnim(int[] point, Drawable drawable) {
        startPoint = point;
        if (endPoint[0] == 0 && endPoint[1] == 0) {
            cartLayout.getLocationInWindow(endPoint);
            cartLayoutWidth = cartLayout.getWidth();
        }
        setParams();
        CornerImageView anim = new CornerImageView(mActivity);
        anim.setBorderRadius(5);
        anim.setType(CornerImageView.TYPE_ROUND);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x65),
                getResources().getDimensionPixelOffset(R.dimen.x65));
        anim.setLayoutParams(layoutParams);
        anim.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (drawable != null) anim.setImageDrawable(drawable);
        int[] start_anim = {startPoint[0], startPoint[1]};
        startAnimation(anim, start_anim, true);
    }

    private void setParams() {
        setCount(Math.abs(startPoint[0] - endPoint[0]));
        float[][] points = {
                {startPoint[0], 0 - startPoint[1]},
                {endPoint[0] + cartLayoutWidth / 2, 0 - endPoint[1]},
                {startPoint[0] + 50, 0 - startPoint[1] + HEIGHT}
        };
//        MLog.e("----打点----" + "startPoint(" + startPoint[0] + "," + startPoint[1] + "), endPoint(" + endPoint[0] + "," + endPoint[1] + ")" + cartLayoutWidth);
        float[] results = calculate(points);
        a = results[0];
        b = results[1];
        c = results[2];
    }

    private void setCount(int count) {
        this.count = count;
    }

    private void startAnimation(final ImageView imageView, int[] start_location, final boolean forward) {
        if (anim_mask_layout == null) {
            anim_mask_layout = createAnimLayout();
        } else {
            anim_mask_layout.removeAllViews();
        }
        addViewToAnimLayout(imageView, start_location);
        anim_mask_layout.addView(imageView);
        Keyframe[] keyframesX = new Keyframe[count];
        Keyframe[] keyframesY = new Keyframe[count];
        final float keyStep = 1f / (float) count;
        float key = keyStep;
        float offset = (-endPoint[1] + startPoint[1]) + (getY(start_location[0]) - getY(count + start_location[0]));
        float offsetStep = offset / (count - 50);
        for (int i = 0; i < count; i++) {
            keyframesX[i] = Keyframe.ofFloat(key, forward ? i + 1 : -(i + 1));
            keyframesY[i] = Keyframe.ofFloat(key, forward ?
                    getY(start_location[0]) - getY(i + 1 + start_location[0]) - (i > 50 ? offsetStep * (i + 1 - 50) : 0) :
                    getY(start_location[0]) - getY(-(i + 1) + start_location[0]) + (i > 50 ? offsetStep * (i + 1 - 50) : 0));
            key += keyStep;
        }
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe(
                "translationX", keyframesX);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofKeyframe(
                "translationY", keyframesY);

        ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(imageView,
                pvhY, pvhX).setDuration(800);
        yxBouncer.setInterpolator(new AccelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator
                .ofFloat(imageView, "scaleX", 1f, 0.15f);
        ObjectAnimator scaleY = ObjectAnimator
                .ofFloat(imageView, "scaleY", 1f, 0.15f);
        animatorSet.setDuration(800);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(yxBouncer).with(scaleY).with(scaleX);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                anim_mask_layout.removeView(imageView);
                cartLayout.clearAnimation();
                ObjectAnimator translation = ObjectAnimator.ofFloat(cartLayout, "translationY", 0f, getResources().getDimensionPixelOffset(R.dimen.x5),
                        getResources().getDimensionPixelOffset(R.dimen.x1), getResources().getDimensionPixelOffset(R.dimen.x3), 0f);
                translation.setInterpolator(new AccelerateDecelerateInterpolator());
                translation.setDuration(600);
                translation.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    }

    /**
     * @param x
     */
    private float getY(float x) {
        return a * x * x + b * x + c;
    }

    private float[] calculate(float[][] points) {
        float x1 = points[0][0];
        float y1 = points[0][1];
        float x2 = points[1][0];
        float y2 = points[1][1];
        float x3 = points[2][0];
        float y3 = points[2][1];

        final float a = (y1 * (x2 - x3) + y2 * (x3 - x1) + y3 * (x1 - x2))
                / (x1 * x1 * (x2 - x3) + x2 * x2 * (x3 - x1) + x3 * x3
                * (x1 - x2));
        final float b = (y1 - y2) / (x1 - x2) - a * (x1 + x2);
        final float c = y1 - (x1 * x1) * a - x1 * b;

        return new float[]{a, b, c};
    }

    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }
}
