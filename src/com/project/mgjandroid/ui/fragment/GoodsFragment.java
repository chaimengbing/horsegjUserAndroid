package com.project.mgjandroid.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsListCapacityModel;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.GroupBean;
import com.project.mgjandroid.bean.Menu;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantRedBag;
import com.project.mgjandroid.bean.MerchantTakeAwayMenu;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MerchantRedBagModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.GoodsDetailActivity;
import com.project.mgjandroid.ui.adapter.GoodsCapacityAdapter;
import com.project.mgjandroid.ui.adapter.GoodsGroupAdapter;
import com.project.mgjandroid.ui.adapter.GoodsSectionHeaderAdapter;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoticeDialog;
import com.project.mgjandroid.ui.view.PinnedHeaderListView.PinnedHeaderListView;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户主页
 *
 * @author jian
 */
public class GoodsFragment extends HeaderViewPagerFragment {
    protected BaseActivity mActivity;
    protected View view;
    private LinearLayout listLayout;
    private ListView groupListView;
    private PinnedHeaderListView goodsListView;
    private PullToRefreshListView goodsListView2;
    private ArrayList<GroupBean> goodsGroup = new ArrayList<>();
    private GoodsGroupAdapter groupAdapter;
    private List<Menu> goodsList = new ArrayList<>();
    private List<Menu> goodsListTemp = new ArrayList<>();
    private GoodsSectionHeaderAdapter goodsAdapter;

    private boolean isScroll = true;
    private BottomCartListener listener;
    private Merchant merchant;
    private int oldSection = -1;
    private MLoadingDialog dialog;
    private LinearLayout linearLayout;
    private NoticeDialog noticeDialog;
    private int capacityType = 0;//是否为大容量 商家  1:是  0:否
    private GoodsCapacityAdapter goodsCapacityAdapter;
    private Goods goods;
    private boolean isFirst = true;
    private MerchantTakeAwayMenu merchantTakeAwayMenu;

    private int[] positions;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.goods_fragment, container, false);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void getClickMethod(int position) {
        if (capacityType == 1) {
            //大容量处理
            ArrayList<GroupBean> list = groupAdapter.getList();
            for (GroupBean gp : list) {
                gp.setIsClick(false);
            }
            list.get(position).setIsClick(true);
            groupAdapter.setList(list);

            if (goodsCapacityAdapter.getMenuList().get(position).getGoodsList() != null && goodsCapacityAdapter.getMenuList().get(position).getGoodsList().size() > 0) {
                //商品列表数据设置
                goodsCapacityAdapter.setSelection(position);
                goodsCapacityAdapter.notifyDataSetChanged();
            } else {
                //请求第一页数据 显示加载loding
                loadData(0, position, GoodsCapacityAdapter.REFRESH);
            }
            return;
        }
        int goodsSection = 0;
        int pos = 0;

        for (int i = 0; i < position; i++) {
            if (goodsListTemp.get(i).getGoodsList().size() > 0) {
                goodsSection += goodsAdapter.getCountForSection(i - pos) + 1;
            } else
                pos++;
        }
        if (goodsListTemp.get(position).getGoodsList().size() > 0) {
            goodsListView.setSelection(goodsSection + goodsListView.getHeaderViewsCount());
            ArrayList<GroupBean> list = groupAdapter.getList();
            for (GroupBean gp : list) {
                gp.setIsClick(false);
            }
            list.get(position).setIsClick(true);
            groupAdapter.setList(list);
        }
    }

    private void init() {
        linearLayout = new LinearLayout(mActivity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        groupListView = (ListView) view.findViewById(R.id.goods_left_listview);
        goodsListView = (PinnedHeaderListView) view.findViewById(R.id.goods_pinned_listView);
        listLayout = (LinearLayout) view.findViewById(R.id.goods_fragment_list_layout);
        goodsListView2 = (PullToRefreshListView) view.findViewById(R.id.goods_pinned_listView2);
        //左侧
        groupAdapter = new GoodsGroupAdapter(mActivity);
        groupListView.setAdapter(groupAdapter);

        //右侧
        goodsAdapter = new GoodsSectionHeaderAdapter(mActivity, merchant);
        goodsAdapter.setListener(listener);
        View headerView = addRedBagHeadView();
        goodsListView.addHeaderView(headerView);
        goodsListView.setAdapter(goodsAdapter);

        goodsCapacityAdapter = new GoodsCapacityAdapter(mActivity, merchant);
        goodsCapacityAdapter.setListener(listener);
        goodsListView2.getRefreshableView().addHeaderView(headerView);
        goodsListView2.setAdapter(goodsCapacityAdapter);

        goodsListView2.setMode(PullToRefreshBase.Mode.DISABLED);

        dialog = new MLoadingDialog();

        goodsListView2.getRefreshableView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (capacityType == 1) {
                    if (isFirst) {
                        if (goods == null)
                            return;
                        isFirst = false;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent();
                                intent.setClass(mActivity, GoodsDetailActivity.class);
                                intent.putExtra("goods", goods);
                                intent.putExtra("isGetNew", true);
                                mActivity.startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
                            }
                        }, 200);
                    }
                }
            }
        });

        goodsListView2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                int selection = goodsCapacityAdapter.getSelection();
                loadData(0, selection, GoodsCapacityAdapter.REFRESH);
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多
                int selection = goodsCapacityAdapter.getSelection();
                loadData(goodsCapacityAdapter.getCount(), selection, GoodsCapacityAdapter.LOAD_MORE);
            }
        });

        groupListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                int goodsSection = 0;
                int pos = 0;
                isScroll = true;
                if (capacityType == 1) {
                    //记住位置
                    int firstVisiblePosition = goodsListView2.getRefreshableView().getFirstVisiblePosition();
                    if (positions != null) {
                        positions[goodsCapacityAdapter.getSelection()] = firstVisiblePosition;
                    }
                    List<Menu> menuList = goodsCapacityAdapter.getMenuList();
                    ArrayList<GroupBean> list = groupAdapter.getList();
                    for (GroupBean gp : list) {
                        gp.setIsClick(false);
                    }
                    //分类列表数据设置
                    list.get(position).setIsClick(true);
                    groupAdapter.setList(list);

                    if (menuList.get(position).getGoodsList() != null && menuList.get(position).getGoodsList().size() > 0) {
                        //商品列表数据设置
                        if (menuList.get(position).getId() != null) {
                            goodsListView2.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                        } else {
                            goodsListView2.setMode(PullToRefreshBase.Mode.DISABLED);
                        }
                        goodsCapacityAdapter.setSelection(position);
                        goodsCapacityAdapter.notifyDataSetChanged();

                        //跳转到指定位置
                        int position1 = positions[position];
                        Menu menu = goodsCapacityAdapter.getMenuList().get(position);
                        if (menu.getGoodsList() != null && menu.getGoodsList().size() > position1) {
                            goodsListView2.getRefreshableView().setSelection(position1);
                        } else {
                            //滑动到顶部
                            goodsListView2.getRefreshableView().setSelection(0);
                        }
                    } else {
                        //请求第一页数据 显示加载loding
                        loadData(0, position, GoodsCapacityAdapter.REFRESH);
                        //滑动到顶部
                        goodsListView2.getRefreshableView().setSelection(0);
                    }
                } else {
                    for (int i = 0; i < position; i++) {
                        if (goodsListTemp.get(i).getGoodsList().size() > 0) {
                            goodsSection += goodsAdapter.getCountForSection(i - pos) + 1;
                        } else
                            pos++;
                    }
                    if (goodsListTemp.get(position).getGoodsList().size() > 0) {
                        ArrayList<GroupBean> list = groupAdapter.getList();
                        for (GroupBean gp : list) {
                            gp.setIsClick(false);
                        }
                        list.get(position).setIsClick(true);
                        groupAdapter.setList(list);
                        goodsListView.setSelection(goodsSection + goodsListView.getHeaderViewsCount());
                    }
                }
            }
        });


        goodsListView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isScroll) {
                    int newSection = goodsAdapter.getSectionForPosition(firstVisibleItem - goodsListView.getHeaderViewsCount());
                    if (newSection == oldSection) {
                        return;
                    }
                    oldSection = newSection;
                    int j;
                    for (int i = 0; i < groupAdapter.getCount(); i++) {
                        if (i == goodsAdapter.getSectionForPosition(firstVisibleItem - goodsListView.getHeaderViewsCount())) {
                            j = i;
                            if (goodsListTemp.get(j).getGoodsList().size() > 0) {
                                ArrayList<GroupBean> list = groupAdapter.getList();
                                for (GroupBean gp : list) {
                                    gp.setIsClick(false);
                                }
                                list.get(j).setIsClick(true);
                                groupAdapter.setList(list);
                            } else {
                                boolean flag = true;
                                while (flag) {
                                    j++;
                                    if (j >= goodsListTemp.size()) {
                                        return;
                                    }
                                    if (goodsListTemp.get(j).getGoodsList().size() > 0) {
                                        flag = false;
                                        ArrayList<GroupBean> list = groupAdapter.getList();
                                        for (GroupBean gp : list) {
                                            gp.setIsClick(false);
                                        }
                                        list.get(j).setIsClick(true);
                                        groupAdapter.setList(list);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    isScroll = false;
                }
            }
        });
    }

    /**
     * 大容量加载更多/刷新
     *
     * @param start     起始角标
     * @param position  left的角标
     * @param loadModel 加载方式 1: 刷新  2:加载更多
     */
    private void loadData(int start, final int position, final int loadModel) {
        if (goodsCapacityAdapter.getMenuList().get(position).getId() == null) {
            goodsListView2.onRefreshComplete();
            ToastUtils.displayMsg("没有更多商品", mActivity);
            return;
        }
        Long id = goodsCapacityAdapter.getMenuList().get(position).getId();
        Map<String, Object> map = new HashMap<>();
        map.put("categoryId", id);
        map.put("start", start);
        map.put("size", 20);
        VolleyOperater<GoodsListCapacityModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_TAKE_AWAY_CATEGORY_MORE, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (goodsListView2.isRefreshing()) {
                    goodsListView2.onRefreshComplete();
                }
                if (isSucceed && obj != null) {
                    List<Goods> list = ((GoodsListCapacityModel) obj).getValue();
                    if (list != null && list.size() > 0) {
                        goodsCapacityAdapter.setGoodsList(position, list, loadModel);
                        if (list.size() < 20) {
                            ToastUtils.displayMsg("已加载所有商品", mActivity);
                        }
                    } else {
                        ToastUtils.displayMsg("没有更多商品", mActivity);
                    }
                }
            }
        }, GoodsListCapacityModel.class);
    }

    public void setData(MerchantTakeAwayMenu merchantTakeAwayMenu) {
        goodsGroup.clear();
        goodsList.clear();
        goodsListTemp.clear();
        capacityType = merchantTakeAwayMenu.getType();
        this.merchantTakeAwayMenu = merchantTakeAwayMenu;
        positions = new int[merchantTakeAwayMenu.getMenu().size()];
        if (capacityType == 1) {
            //大容量优化逻辑
            goodsListView2.setVisibility(View.VISIBLE);
            goodsListView.setVisibility(View.GONE);
        } else {
            //正常逻辑
            goodsListView.setVisibility(View.VISIBLE);
            goodsListView2.setVisibility(View.GONE);
        }
        ArrayList<Long> categoryIds = new ArrayList<>();
        List<Menu> menus = merchantTakeAwayMenu.getMenu();
        if (CheckUtils.isNoEmptyList(menus)) {
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                String name = menu.getName();
                categoryIds.add(menu.getId());
                GroupBean groupBean = new GroupBean();
                groupBean.setName(name);
                if (CheckUtils.isNoEmptyStr(menu.getIcon()))
                    groupBean.setIcon(menu.getIcon());
                if (i == 0) {
                    groupBean.setIsClick(true);
                }
                goodsGroup.add(groupBean);
            }
            goodsList.addAll(menus);
            goodsListTemp.addAll(menus);
            if (capacityType == 1) {
                goodsCapacityAdapter.setServiceTime(merchantTakeAwayMenu.getServertime());
                goodsCapacityAdapter.setMenuList(goodsList);
                if (merchantTakeAwayMenu.getMenu().size() > 0)
                    if (merchantTakeAwayMenu.getMenu().get(0).getId() != null) {
                        goodsListView2.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                    } else {
                        goodsListView2.setMode(PullToRefreshBase.Mode.DISABLED);
                    }
            } else {
                goodsAdapter.setServiceTime(merchantTakeAwayMenu.getServertime());
                goodsAdapter.setMenuList(goodsList);
            }
        }
        groupAdapter.setIdList(categoryIds);
        groupAdapter.setList(goodsGroup);
        groupAdapter.notifyDataSetChanged();
    }

    public void getData(MerchantTakeAwayMenu merchantTakeAwayMenu, int goodsId, Goods goods, int tag) {
        goodsGroup.clear();
        goodsList.clear();
        goodsListTemp.clear();
        capacityType = merchantTakeAwayMenu.getType();
        this.merchantTakeAwayMenu = merchantTakeAwayMenu;
        positions = new int[merchantTakeAwayMenu.getMenu().size()];
        this.goods = goods;
        if (capacityType == 1) {
            //大容量优化逻辑
            goodsListView2.setVisibility(View.VISIBLE);
            goodsListView.setVisibility(View.GONE);
        } else {
            //正常逻辑
            goodsListView.setVisibility(View.VISIBLE);
            goodsListView2.setVisibility(View.GONE);
        }
        ArrayList<Long> categoryIds = new ArrayList<>();
        List<Menu> menus = merchantTakeAwayMenu.getMenu();
        if (CheckUtils.isNoEmptyList(menus)) {
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                String name = menu.getName();
                categoryIds.add(menu.getId());
                GroupBean groupBean = new GroupBean();
                groupBean.setName(name);
                if (CheckUtils.isNoEmptyStr(menu.getIcon()))
                    groupBean.setIcon(menu.getIcon());
                if (i == 0) {
                    groupBean.setIsClick(true);
                }
                goodsGroup.add(groupBean);
            }
            goodsList.addAll(menus);
            goodsListTemp.addAll(menus);
            if (capacityType == 1) {
                goodsCapacityAdapter.setServiceTime(merchantTakeAwayMenu.getServertime());
                goodsCapacityAdapter.setMenuList(goodsList);
                if (merchantTakeAwayMenu.getMenu().size() > 0)
                    if (merchantTakeAwayMenu.getMenu().get(0).getId() != null) {
                        goodsListView2.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                    } else {
                        goodsListView2.setMode(PullToRefreshBase.Mode.DISABLED);
                    }
            } else {
                goodsAdapter.setServiceTime(merchantTakeAwayMenu.getServertime());
                goodsAdapter.setMenuList(goodsList);
            }
        }
        groupAdapter.setIdList(categoryIds);
        groupAdapter.setList(goodsGroup);
        groupAdapter.notifyDataSetChanged();
        AnimatorUtils.fadeFadeIn(listLayout, mActivity);
        if (capacityType != 1) {
            int selection = 0;
            boolean isOver = false;
            for (int i = 0; i < merchantTakeAwayMenu.getMenu().size(); i++) {
                if (isOver) {
                    break;
                } else {
                    Menu menu = merchantTakeAwayMenu.getMenu().get(i);
                    for (Goods goods1 : menu.getGoodsList()) {
                        if (goodsId == goods1.getId()) {
                            isOver = true;
                            break;
                        } else {
                            selection++;
                        }
                    }
                    selection++;
                }
            }
            goodsListView.setSelection(selection);
            goodsAdapter.setSelection(goodsId);
            goodsAdapter.setTag(tag);
        }
    }

    public void getDataAgain(MerchantTakeAwayMenu merchantTakeAwayMenu, JSONObject object) {
        goodsGroup.clear();
        goodsList.clear();
        goodsListTemp.clear();
        capacityType = merchantTakeAwayMenu.getType();
        this.merchantTakeAwayMenu = merchantTakeAwayMenu;
        positions = new int[merchantTakeAwayMenu.getMenu().size()];
        if (capacityType == 1) {
            //大容量优化逻辑
            goodsListView2.setVisibility(View.VISIBLE);
            goodsListView.setVisibility(View.GONE);
        } else {
            //正常逻辑
            goodsListView.setVisibility(View.VISIBLE);
            goodsListView2.setVisibility(View.GONE);
        }
        ArrayList<Long> categoryIds = new ArrayList<>();
        List<Menu> menus = merchantTakeAwayMenu.getMenu();
        if (menus != null && CheckUtils.isNoEmptyList(menus)) {
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                String name = menu.getName();
                categoryIds.add(menu.getId());
                GroupBean groupBean = new GroupBean();
                groupBean.setName(name);
                if (CheckUtils.isNoEmptyStr(menu.getIcon()))
                    groupBean.setIcon(menu.getIcon());
                if (i == 0) {
                    groupBean.setIsClick(true);
                }
                goodsGroup.add(groupBean);
            }
            goodsList.addAll(menus);
            goodsListTemp.addAll(menus);
            if (capacityType == 1) {
                goodsCapacityAdapter.setServiceTime(merchantTakeAwayMenu.getServertime());
                goodsCapacityAdapter.setMenuList(goodsList);
                if (merchantTakeAwayMenu.getMenu().size() > 0)
                    if (merchantTakeAwayMenu.getMenu().get(0).getId() != null) {
                        goodsListView2.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                    } else {
                        goodsListView2.setMode(PullToRefreshBase.Mode.DISABLED);
                    }
            } else {
                goodsAdapter.setServiceTime(merchantTakeAwayMenu.getServertime());
                goodsAdapter.setMenuList(goodsList);
            }
        }
        groupAdapter.setIdList(categoryIds);
        groupAdapter.setList(goodsGroup);
        groupAdapter.notifyDataSetChanged();
        AnimatorUtils.fadeFadeIn(listLayout, mActivity);
        for (Menu menu : merchantTakeAwayMenu.getMenu()) {
            if (menu.getGoodsList() != null)
                for (Goods goods : menu.getGoodsList()) {
                    if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() == 1) {
                        GoodsSpec goodsSpec = goods.getGoodsSpecList().get(0);
                        JSONArray a = object.getJSONArray("goodsJson");
                        for (int k = 0; k < a.size(); k++) {
                            int id = a.getJSONObject(k).getInteger("id");
                            int specId = a.getJSONObject(k).getInteger("specId");
                            int quantity = a.getJSONObject(k).getInteger("quantity");
                            if (id == goods.getId() && specId == goodsSpec.getId()) {
                                goodsSpec.setBuyCount(quantity);
                                listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                                break;
                            }
                        }
                    } else if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() > 1) {
                        for (int l = 0; l < goods.getGoodsSpecList().size(); l++) {
                            GoodsSpec goodsSpec = goods.getGoodsSpecList().get(l);
                            JSONArray a = object.getJSONArray("goodsJson");
                            for (int m = 0; m < a.size(); m++) {
                                int id = a.getJSONObject(m).getInteger("id");
                                int specId = a.getJSONObject(m).getInteger("specId");
                                int quantity = a.getJSONObject(m).getInteger("quantity");
                                if (id == goods.getId() && specId == goodsSpec.getId()) {
                                    goodsSpec.setBuyCount(quantity);
                                    listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                                    break;
                                }
                            }
                        }
                    }
                }
        }
    }

    /**
     * 接口回调传递监听器
     *
     * @param listener
     */
    public void setListener(BottomCartListener listener) {
        this.listener = listener;
    }

    /**
     * 刷新List
     */
    public void notifyList(PickGoods changePickGoods) {
        if (capacityType == 1) {
            for (Menu menu : goodsCapacityAdapter.getMenuList()) {
                if (menu.getGoodsList() != null)
                    for (Goods goods : menu.getGoodsList()) {
                        if (changePickGoods != null && goods.getId() == changePickGoods.getGoodsId()) {
                            List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
                            for (GoodsSpec goodsSpec : goodsSpecList) {
                                if (changePickGoods.getGoodsId() == goods.getId() && changePickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                    goodsSpec.setBuyCount(changePickGoods.getPickCount());
                                    break;
                                }
                            }
                            break;
                        }
                    }
            }
            goodsCapacityAdapter.notifyDataSetChanged();
        } else {
            for (Menu menu : goodsList) {
                for (Goods goods : menu.getGoodsList()) {
                    if (changePickGoods != null && goods.getId() == changePickGoods.getGoodsId()) {
                        List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
                        for (GoodsSpec goodsSpec : goodsSpecList) {
                            if (changePickGoods.getGoodsId() == goods.getId() && changePickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                goodsSpec.setBuyCount(changePickGoods.getPickCount());
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            goodsAdapter.notifyDataSetChanged();
        }
        groupAdapter.notifyDataSetChanged();
    }

    /**
     * 清空购物数据
     */
    public void clearList(List<PickGoods> pickGoodsList) {
        if (capacityType == 1) {
            for (PickGoods changePickGoods : pickGoodsList) {
                for (Menu menu : goodsCapacityAdapter.getMenuList()) {
                    if (menu.getGoodsList() != null)
                        for (Goods goods : menu.getGoodsList()) {
                            if (goods.getId() == changePickGoods.getGoodsId()) {
                                List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
                                for (GoodsSpec goodsSpec : goodsSpecList) {
                                    if (changePickGoods.getGoodsId() == goods.getId() && changePickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                        goodsSpec.setBuyCount(0);
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                }
            }
            goodsCapacityAdapter.notifyDataSetChanged();
        } else {
            for (PickGoods changePickGoods : pickGoodsList) {
                for (Menu menu : goodsList) {
                    for (Goods goods : menu.getGoodsList()) {
                        if (goods.getId() == changePickGoods.getGoodsId()) {
                            List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
                            for (GoodsSpec goodsSpec : goodsSpecList) {
                                if (changePickGoods.getGoodsId() == goods.getId() && changePickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                    goodsSpec.setBuyCount(0);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
            goodsAdapter.notifyDataSetChanged();
        }
        groupAdapter.notifyDataSetChanged();
    }

    public void clearList() {
        if (capacityType == 1) {
            for (Menu menu : goodsCapacityAdapter.getMenuList()) {
                if (menu.getGoodsList() != null)
                    for (Goods goods : menu.getGoodsList()) {
                        List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
                        for (GoodsSpec goodsSpec : goodsSpecList) {
                            goodsSpec.setBuyCount(0);
                        }
                    }
            }
            goodsCapacityAdapter.notifyDataSetChanged();
        } else {
            for (Menu menu : goodsList) {
                for (Goods goods : menu.getGoodsList()) {
                    List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
                    for (GoodsSpec goodsSpec : goodsSpecList) {
                        goodsSpec.setBuyCount(0);
                    }
                }
            }
            goodsAdapter.notifyDataSetChanged();
        }
        groupAdapter.notifyDataSetChanged();
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @Override
    public View getScrollableView() {
        return goodsListView;
    }

    private View addRedBagHeadView() {
        linearLayout.removeAllViews();
        if (merchant != null && CheckUtils.isNoEmptyList(merchant.getMerchantRedBagList())) {
            linearLayout.setPadding(getResources().getDimensionPixelSize(R.dimen.x10), getResources().getDimensionPixelSize(R.dimen.x10),
                    getResources().getDimensionPixelSize(R.dimen.x10), 0);
            for (int i = 0, size = merchant.getMerchantRedBagList().size(); i < size; i++) {
                final MerchantRedBag redBag = merchant.getMerchantRedBagList().get(i);
                final View view = LayoutInflater.from(mActivity).inflate(R.layout.merchant_redbag_layout, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.x10));
                view.setLayoutParams(layoutParams);

                if (i % 2 == 0) {
                    view.setBackgroundResource(R.drawable.red_bag_bg_red);
                } else {
                    view.setBackgroundResource(R.drawable.red_bag_bg_yellow);
                }
                TextView tvAmt = (TextView) view.findViewById(R.id.tv_red_bag_amt);
                TextView tvName = (TextView) view.findViewById(R.id.tv_red_bag_name);
                TextView tvRestrict = (TextView) view.findViewById(R.id.tv_red_bag_restrict);
                TextView tvGetRedBag = (TextView) view.findViewById(R.id.tv_get_red_bag);

                if (redBag.getAmt() != null)
                    tvAmt.setText(StringUtils.BigDecimal2Str(redBag.getAmt()) + "");
                if (CheckUtils.isNoEmptyStr(redBag.getName())) tvName.setText(redBag.getName());
                if (CheckUtils.isNoEmptyStr(redBag.getUseRule()))
                    tvRestrict.setText(redBag.getUseRule());

                tvGetRedBag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (App.isLogin()) {
                            getMerchantRedBag(view, redBag.getId());
                        } else {
                            mActivity.toast("登录之后才可以领取哦~~");
                        }
                    }
                });
                linearLayout.addView(view);
            }
        }
        return linearLayout;
    }

    private void getMerchantRedBag(final View view, long redBagId) {
        dialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchant.getId());
        params.put("id", redBagId);
        VolleyOperater<MerchantRedBagModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GET_MERCHANT_RED_BAG, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                dialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) return;
                    if (((MerchantRedBagModel) obj).getCode() == 0) {
                        showNoticeDialog(((MerchantRedBagModel) obj).getValue().getAmt());
                        if (linearLayout.getChildCount() == 1) {
                            linearLayout.setVisibility(View.GONE);
                            linearLayout.setPadding(0, 0, 0, 0);
                        } else {
                            view.setVisibility(View.GONE);
                        }
                        linearLayout.removeView(view);
                    }
                }
            }
        }, MerchantRedBagModel.class);
    }

    private void showNoticeDialog(BigDecimal price) {
        String str = "恭喜您已成功领取" + StringUtils.BigDecimal2Str(price) + "元红包，可在[个人中心]--[我的红包]查看";
        noticeDialog = new NoticeDialog(mActivity, new NoticeDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                noticeDialog.dismiss();
            }
        }, "", str);
        noticeDialog.show();
    }

    /**
     * 跳转选中分类
     *
     * @param menuId
     */
    public void setSelectMenu(long menuId) {
        if (merchantTakeAwayMenu != null) {
            List<Menu> menu = merchantTakeAwayMenu.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                if (menu.get(i).getId() != null && menu.get(i).getId() == menuId) {
                    getClickMethod(i);
                }
            }
        }

    }
}
