
package com.project.mgjandroid.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.MarketSearchHistory;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MarketCartModel;
import com.project.mgjandroid.model.SuperMarketCartModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.MarketSearchHistoryListAdapter;
import com.project.mgjandroid.ui.adapter.MarketSearchListAdapter;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User_Cjh on 2016/10/11.
 */
public class MarketSearchActivity extends BaseActivity implements TextWatcher, AdapterView.OnItemClickListener, TextView.OnEditorActionListener {

    @InjectView(R.id.market_search_back)
    private ImageView back;
    @InjectView(R.id.search_text)
    private EditText etSearch;
    @InjectView(R.id.market_search_search)
    private TextView search;
    @InjectView(R.id.market_search_list_no_data)
    private TextView noData;
    @InjectView(R.id.iv_delete)
    private ImageView ivDelete;
    @InjectView(R.id.market_search_list)
    private ListView searchList;
    @InjectView(R.id.market_search_history_detail_layout)
    private ListView historyList;
    @InjectView(R.id.layout_market_cart)
    private FrameLayout cartLayout;
    @InjectView(R.id.cart_num)
    private TextView tv_num;
    private LinearLayout clearHistory;

    private MarketSearchListAdapter adapter;
    private MarketSearchHistoryListAdapter historyAdapter;
    private int mCurrentPosition = 0;
    private static final int PAGE_SIZE = 10;
    private boolean isBottom = false;
    private boolean isLoading = false;
    private boolean hasMoreData = true;
    private Long merchantId;
    private ArrayList<SuperMarketCartModel.CartGoods> cartGoods;
    private ViewGroup anim_mask_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_search);
        Injector.get(this).inject();

        initView();
        setListener();
    }

    private void setListener() {
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        clearHistory.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
        etSearch.setOnEditorActionListener(this);
        ivDelete.setOnClickListener(this);
        cartLayout.setOnClickListener(this);
        searchList.setOnItemClickListener(this);
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position > historyAdapter.getData().size()) {
                    return;
                }
                CommonUtils.hideKeyBoard2(view);
                String msg = historyAdapter.getData().get(position - 1).getName();
                etSearch.setText(msg);
                etSearch.setSelection(msg.length());
            }
        });
        searchList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (isBottom) {
                        if (!isLoading) {
                            if (hasMoreData) {
                                mCurrentPosition = adapter.getData().size();
                                doSearch(etSearch.getText().toString().trim(), true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
    }

    private void initView() {
        merchantId = getIntent().getLongExtra("merchantId", -1);
        if (merchantId == -1) {
            finish();
            return;
        }
        adapter = new MarketSearchListAdapter(R.layout.super_market_goods_item, mActivity);
        adapter.setListener(new MarketSearchListAdapter.AddGoodsListener() {
            @Override
            public void addGoods(View v, int[] startLocation, ViewGroup parent) {
                if (cartLayout.getVisibility() == View.INVISIBLE) {
                    cartLayout.setVisibility(View.VISIBLE);
                }
                tv_num.setVisibility(View.VISIBLE);
                tv_num.setText(SuperMarketCartModel.getInstance().getSuperMarketCartBean().getTotalCount());
                setAnim(v, startLocation, parent);
            }

            @Override
            public void minusGoods() {
                if ("".equals(SuperMarketCartModel.getInstance().getSuperMarketCartBean().getTotalCount())) {
                    tv_num.setVisibility(View.INVISIBLE);
                } else {
                    tv_num.setText(SuperMarketCartModel.getInstance().getSuperMarketCartBean().getTotalCount());
                }
            }

            @Override
            public void toDetail() {
                String historySearch = PreferenceUtils.getStringPreference("market_history_search", "", mActivity);
                if (CheckUtils.isNoEmptyStr(historySearch)) {
                    String search = etSearch.getText().toString().trim();
                    String[] historys = historySearch.split(",");
                    String result = StringUtils.stringInsertString(search, historys);
                    PreferenceUtils.saveStringPreference("market_history_search", result, mActivity);
                } else {
                    PreferenceUtils.saveStringPreference("market_history_search", etSearch.getText().toString().trim(), mActivity);
                }
                CommonUtils.hideKeyBoard2(etSearch);
            }
        });
        historyAdapter = new MarketSearchHistoryListAdapter(R.layout.item_market_search_history, mActivity);
        refreshHistory();
        searchList.setAdapter(adapter);
        SuperMarketCartModel superMarketCartModel = SuperMarketCartModel.getInstance();
        superMarketCartModel.initData();
        SuperMarketCartModel.SuperMarketCartBean superMarketCartBean = superMarketCartModel.getSuperMarketCartBean();
        cartGoods = superMarketCartBean.getCartGoods();
        View header = mInflater.inflate(R.layout.header_market_search, null);
        View footer = mInflater.inflate(R.layout.footer_market_search, null);
        clearHistory = (LinearLayout) footer.findViewById(R.id.clear_market_history);
        historyList.addHeaderView(header);
        historyList.addFooterView(footer);
        historyList.setAdapter(historyAdapter);
        etSearch.requestFocus();
        CommonUtils.showKeyBoard(etSearch);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.market_search_back:
                CommonUtils.hideKeyBoard2(v);
                finish();
                break;
            case R.id.market_search_search:
                CommonUtils.hideKeyBoard(mActivity);
                break;
            case R.id.clear_market_history:
                PreferenceUtils.saveStringPreference("market_history_search", "", mActivity);
                refreshHistory();
                break;
            case R.id.iv_delete:
                etSearch.setText("");
                break;
            case R.id.goods_item_img_add:
//                int position = (int) v.getTag();
//                Goods bean = adapter.getData().get(position);
//                addToMarketCart(bean);
                break;
            case R.id.layout_market_cart:
                Intent marketCart = new Intent(mActivity, MarketCartActivity.class);
                startActivityForResult(marketCart, 1001);
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                break;
        }
    }

    /*
     * 添加一个商品到购物车
     * @param bean 添加的商品
     */
    /*private void addToMarketCart(Goods bean) {
        for(SuperMarketCartModel.CartGoods goods:cartGoods){
            if(bean.getGoodsSpecList().get(0).getGoodsId() == goods.getGoodsId()){
                GoodsSpec goodsSpec = bean.getGoodsSpecList().get(0);
                int count = goods.getButCount();
                if(goodsSpec.getStockType() == 1 && goodsSpec.getStock() != null && goodsSpec.getStock() != 0 && count >= goodsSpec.getStock()){
                    ToastUtils.displayMsg("该商品库存不足",mActivity);
                    return;
                }
                if(goodsSpec.getOrderLimit() != 0 && count >= goodsSpec.getOrderLimit()){
                    ToastUtils.displayMsg("该商品限购"+ goodsSpec.getOrderLimit() +"份", mActivity);
                    return;
                }
                if((bean.getGoodsSpecList().get(0).getOrderLimit() != 0 && goods.getButCount() >= bean.getGoodsSpecList().get(0).getOrderLimit()) || goods.getButCount() == 99){
                    ToastUtils.displayMsg("当前商品已达到最大购买数量",mActivity);
                    return;
                }
            }
        }

        if(!superMarketCartModel.isHasChange())
            superMarketCartModel.setHasChange(true);
        superMarketCartBean.addGoods(bean.getGoodsSpecList().get(0).getGoodsId(), 1);
        ToastUtils.displayMsg("成功加入购物车", mActivity);
    }*/

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0) {
            String historySearch = PreferenceUtils.getStringPreference("market_history_search", "", mActivity);
            if (CheckUtils.isNoEmptyStr(historySearch)) {
                refreshHistory();
            } else {
                historyList.setVisibility(View.GONE);
            }
            searchList.setVisibility(View.GONE);
            cartLayout.setVisibility(View.INVISIBLE);
            ivDelete.setVisibility(View.GONE);
            noData.setVisibility(View.GONE);
        } else {
            ivDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            mCurrentPosition = 0;
            doSearch(s.toString().trim(), false);
        }
    }

    private void refreshHistory() {
        String historySearch = PreferenceUtils.getStringPreference("market_history_search", "", mActivity);
        if (CheckUtils.isNoEmptyStr(historySearch)) {
            historyList.setVisibility(View.VISIBLE);
            String[] strs = historySearch.split(",");
            List<MarketSearchHistory> data = new ArrayList<>();
            for (String str : strs) {
                MarketSearchHistory bean = new MarketSearchHistory();
                bean.setName(str);
                data.add(bean);
            }
            historyAdapter.setData(data);
        } else {
            historyList.setVisibility(View.GONE);
        }
    }

    private void doSearch(String content, final boolean isLoadMore) {
        isLoading = true;
        Map<String, Object> params = new HashMap<>();
        params.put("start", mCurrentPosition);
        params.put("size", PAGE_SIZE);
        params.put("queryString", content);
        params.put("merchantId", merchantId);
        VolleyOperater<MarketCartModel> operater = new VolleyOperater<>(MarketSearchActivity.this);
        operater.doRequest(Constants.URL_SEARCH_GOODS_BY_MERCHANT_ID, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    //有数据的时候，改变布局
                    historyList.setVisibility(View.GONE);
                    searchList.setVisibility(View.VISIBLE);

                    MarketCartModel searchModel = (MarketCartModel) obj;
                    List<Goods> value = searchModel.getValue();
                    hasMoreData = value.size() >= PAGE_SIZE;
                    if (isLoadMore) {
                        List<Goods> list = adapter.getData();
                        list.addAll(value);
                        list = doWhileInCart(list);
                        adapter.setData(list);
                    } else {
                        if (value.size() == 0) {
                            noData.setVisibility(View.VISIBLE);
                        } else {
                            noData.setVisibility(View.GONE);
                        }
                        value = doWhileInCart(value);
                        adapter.setData(value);
                    }
                }
                isLoading = false;
            }
        }, MarketCartModel.class);
    }

    private List<Goods> doWhileInCart(List<Goods> list) {
        if (list == null || list.size() == 0) {
            cartLayout.setVisibility(View.INVISIBLE);
            return new ArrayList<>();
        }
        if ("".equals(SuperMarketCartModel.getInstance().getSuperMarketCartBean().getTotalCount())) {
            tv_num.setVisibility(View.INVISIBLE);
        } else {
            tv_num.setText(SuperMarketCartModel.getInstance().getSuperMarketCartBean().getTotalCount());
        }
        for (Goods good : list) {

            good.setCount(0);
        }
        if (CheckUtils.isNoEmptyList(cartGoods)) {
            for (SuperMarketCartModel.CartGoods cart : cartGoods) {
                for (Goods goods : list) {
                    if (cart.getGoodsId() == goods.getGoodsSpecList().get(0).getGoodsId()) {
                        goods.setCount(cart.getButCount());
                        break;
                    }
                }
            }
        } else {
            return list;
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String historySearch = PreferenceUtils.getStringPreference("market_history_search", "", mActivity);
//        if(CheckUtils.isNoEmptyStr(historySearch)){
//            String search = etSearch.getText().toString().trim();
//            String[] historys = historySearch.split(",");
//            String result = StringUtils.stringInsertString(search,historys);
//            PreferenceUtils.saveStringPreference("market_history_search",result,mActivity);
//        }else{
//            PreferenceUtils.saveStringPreference("market_history_search",etSearch.getText().toString().trim(),mActivity);
//        }
//        CommonUtils.hideKeyBoard2(view);
//        Goods goods = adapter.getData().get(position);
//        Intent intent = new Intent(mActivity,CommodityDetailActivity.class);
//        intent.putExtra("goods",goods);
//        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1001:
                refreshData();
                break;
        }
    }

    private void refreshData() {
        List<Goods> data = adapter.getData();
        data = doWhileInCart(data);
        adapter.setData(data);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            CommonUtils.hideKeyBoard2(v);
            return true;
        }
        return false;
    }

    /**
     * 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout, rootView.getChildCount());
        return animLayout;
    }

    private View addViewToAnimLayout(final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x15),
                getResources().getDimensionPixelOffset(R.dimen.x15));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    /**
     * 开启动画
     */
    public void setAnim(final View v, int[] startLocation, final ViewGroup parent) {
        if (parent != null) {
            parent.addView(v);
        } else {
            if (anim_mask_layout == null)
                anim_mask_layout = createAnimLayout();
            else {
                anim_mask_layout.removeAllViews();
            }
            anim_mask_layout.addView(v);//把动画小球添加到动画层
        }
        final View view = addViewToAnimLayout(v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tv_num.getLocationInWindow(endLocation);// shopCart是那个购物车

        // 计算位移
        int endX = endLocation[0] - startLocation[0];// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, endX);
        translateXAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, endY);
        translateYAnimator.setInterpolator(new AnticipateInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(translateXAnimator).with(translateYAnimator);
        set.setDuration(500);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                v.setVisibility(View.INVISIBLE);
                if (parent != null) {
                    parent.removeView(v);
                } else {
                    anim_mask_layout.removeView(v);
                }
            }
        });
    }
}
