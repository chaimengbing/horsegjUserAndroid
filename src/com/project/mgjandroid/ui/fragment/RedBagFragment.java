package com.project.mgjandroid.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.RedBagListModel;
import com.project.mgjandroid.model.RedBagSelectListModel;
import com.project.mgjandroid.model.RedBagsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.MyRedBagActivity;
import com.project.mgjandroid.ui.adapter.PlatFormAdapter;
import com.project.mgjandroid.ui.adapter.RedBagAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/5/30.
 */
public class RedBagFragment extends BaseFragment implements PullToRefreshListView.OnRefreshListener2, View.OnClickListener {

    private PullToRefreshListView listView;
    private RelativeLayout noDataLayout;
    private TextView tvLvTransaction;

    private RedBagAdapter vouchersAdapter;
    private PlatFormAdapter platFormAdapter;
    private MLoadingDialog loadingDialog;
    private static final int maxResults = 10;
    private int start = 0;
    private boolean canUse;
    private int redBagType = 1;

    private double longitude;
    private double latitude;
    private double itemsPrice;
    private long merchantId;
    private String promoInfoJson;
    private long agentId;
    private long redBagId;
    private String discountGoodsDiscountAmt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        canUse = getArguments().getBoolean("canUse");
        latitude = getArguments().getDouble("latitude", -1);
        longitude = getArguments().getDouble("longitude", -1);
        itemsPrice = getArguments().getDouble("itemsPrice", -1);
        merchantId = getArguments().getLong("merchantId", -1);
        discountGoodsDiscountAmt = getArguments().getString("discountGoodsDiscountAmt");
        promoInfoJson = getArguments().getString("PromoInfoJson");
        agentId = getArguments().getLong("agentId", -1);
        redBagId = getArguments().getLong("redBagId", -1);

        View view = inflater.inflate(R.layout.fragment_red_bag, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingDialog.show(mActivity.getFragmentManager(), "");
        if (canUse && (merchantId != -1 || agentId != -1)) {
            listView.setMode(PullToRefreshBase.Mode.DISABLED);
            if (agentId != -1) {
                getCallCarDataAvailable();
            } else {
                getDataAvailable();
            }
        } else {
            listView.setMode(PullToRefreshBase.Mode.BOTH);
            listView.setOnRefreshListener(this);
            getData(false);
        }
    }

    private void initView(View view) {
        listView = (PullToRefreshListView) view.findViewById(R.id.red_bag_list);
        noDataLayout = (RelativeLayout) view.findViewById(R.id.no_data);
        TextView tvTransaction = (TextView) view.findViewById(R.id.tv_transaction);
        View lvTransaction = mInflater.inflate(R.layout.red_bag_footer_view, null);
        tvLvTransaction = (TextView) lvTransaction.findViewById(R.id.tv_transaction);
        tvTransaction.setOnClickListener(this);
        tvLvTransaction.setOnClickListener(this);
        loadingDialog = new MLoadingDialog();

        if (!canUse) {
            tvLvTransaction.setTextColor(0xffff9900);
            tvTransaction.setTextColor(0xffff9900);
            tvLvTransaction.setText("<< 查看可用红包");
            tvTransaction.setText("<< 查看可用红包");
        }

        if (merchantId != -1 || agentId != -1) {
            vouchersAdapter = new RedBagAdapter(R.layout.item_red_vouchers, mActivity, true, canUse);
            platFormAdapter = new PlatFormAdapter(R.layout.item_platform, mActivity, true, canUse);
        } else {
            vouchersAdapter = new RedBagAdapter(R.layout.item_red_vouchers, mActivity, true, canUse);
            platFormAdapter = new PlatFormAdapter(R.layout.item_platform, mActivity, false, canUse);
            listView.getRefreshableView().addFooterView(lvTransaction);
        }
        setAdapter();
    }


    private void setAdapter() {
        if (redBagType == 1) {
            listView.setAdapter(platFormAdapter);
        } else {
            listView.setAdapter(vouchersAdapter);
        }
    }

    public void setRedBagType(int redBagType) {
        this.redBagType = redBagType;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getData(false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (redBagType == 1) {
            start = platFormAdapter.getCount();
        } else {
            start = vouchersAdapter.getCount();
        }
        getData(true);
    }

    private void setData(List<RedBag> mlist, boolean isLoadMore) {
        if (CheckUtils.isNoEmptyList(mlist)) {
            if (isLoadMore) {
                if (mlist.size() < maxResults) {
                    ToastUtils.displayMsg("到底了", mActivity);
                }
                List<RedBag> mlistOrg;
                if (redBagType == 1) {
                    mlistOrg = platFormAdapter.getData();
                    if (mlistOrg != null) {
                        mlistOrg.addAll(mlist);
                        platFormAdapter.setData(mlistOrg);
                        platFormAdapter.notifyDataSetChanged();
                    }
                } else {
                    mlistOrg = vouchersAdapter.getData();
                    if (mlistOrg != null) {
                        mlistOrg.addAll(mlist);
                        vouchersAdapter.setData(mlistOrg);
                        vouchersAdapter.notifyDataSetChanged();
                    }
                }

            } else {
                tvLvTransaction.setVisibility(View.VISIBLE);
                noDataLayout.setVisibility(View.GONE);
                if (redBagType == 1) {
                    platFormAdapter.setData(mlist);
                    platFormAdapter.notifyDataSetChanged();
                } else {
                    vouchersAdapter.setData(mlist);
                    vouchersAdapter.notifyDataSetChanged();
                }
                AnimatorUtils.fadeFadeIn(listView, mActivity);
            }
        } else {
            if (isLoadMore) {
                ToastUtils.displayMsg("到底了", mActivity);
            } else {
                if (redBagType == 1) {
                    platFormAdapter.setData(mlist);
                } else {
                    vouchersAdapter.setData(mlist);
                }
                noDataLayout.setVisibility(View.VISIBLE);
                tvLvTransaction.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * /**
     * * 参数：start：起始行
     * size：每页显示数
     * redBagType：1：平台红包，2：代金券
     * isDisabled：0：使用，1已使用
     */

    public void getData(final boolean isLoadMore) {
        setAdapter();
        if (!isLoadMore) {
            start = 0;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("size", maxResults);
        map.put("redBagType", redBagType);
        if (canUse) {
            map.put("isDisabled", 0);
        } else {
            map.put("isDisabled", 1);
        }
        VolleyOperater<RedBagsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_QUERY_RED_BAG_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                loadingDialog.dismiss();
                List<RedBag> mlist = new ArrayList<>();
                int redCount = 0;
                int vouchersCount = 0;
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    RedBagListModel redBagListModel = ((RedBagsModel) obj).getValue();
                    if (redBagType == 1) {
                        mlist.addAll(redBagListModel.getPlatformRedBagList());
                    } else {
                        mlist.addAll(redBagListModel.getVouchersList());
                    }
                    redCount = redBagListModel.getPlatformRedBagCount();
                    vouchersCount = redBagListModel.getVouchersCount();
                    setData(mlist, isLoadMore);

                } else {
                    setData(mlist, isLoadMore);
                }
                ((MyRedBagActivity) getActivity()).handRedBagTabView(redBagType, redCount, vouchersCount);
            }
        }, RedBagsModel.class);
    }

    private void getDataAvailable() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("itemsPrice", itemsPrice);
        map.put("merchantId", merchantId);
        map.put("promoInfoJson", promoInfoJson);
        map.put("discountGoodsDiscountAmt", discountGoodsDiscountAmt);
        VolleyOperater<RedBagSelectListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FILTER_USABLE_RED_BAG_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                loadingDialog.dismiss();
                if (loadingDialog.isVisible()) {
                    loadingDialog.dismiss();
                }
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    RedBagSelectListModel redBagListModel = (RedBagSelectListModel) obj;
                    List<RedBag> mlist = new ArrayList<>();
                    mlist.addAll(redBagListModel.getValue());
                    if (redBagId != -1 && CheckUtils.isNoEmptyList(mlist)) {
                        for (int i = mlist.size() - 1; i >= 0; i--) {
                            if (redBagId == mlist.get(i).getId()) {
                                mlist.get(i).setSelected(true);
                                break;
                            }
                        }
                    }
                    vouchersAdapter.setData(mlist);
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        noDataLayout.setVisibility(View.INVISIBLE);
                        tvLvTransaction.setVisibility(View.VISIBLE);
                    } else {
                        noDataLayout.setVisibility(View.VISIBLE);
                        tvLvTransaction.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }, RedBagSelectListModel.class);
    }

    private void getCallCarDataAvailable() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("agentId", agentId);
        VolleyOperater<RedBagSelectListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_CAR_USABLE_REDBAG_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                loadingDialog.dismiss();
                if (loadingDialog.isVisible()) {
                    loadingDialog.dismiss();
                }
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    RedBagSelectListModel redBagListModel = (RedBagSelectListModel) obj;
                    List<RedBag> mlist = new ArrayList<>();
                    mlist.addAll(redBagListModel.getValue());
                    if (redBagId != -1 && CheckUtils.isNoEmptyList(mlist)) {
                        for (int i = mlist.size() - 1; i >= 0; i--) {
                            if (redBagId == mlist.get(i).getId()) {
                                mlist.get(i).setSelected(true);
                                break;
                            }
                        }
                    }
                    vouchersAdapter.setData(mlist);
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        noDataLayout.setVisibility(View.INVISIBLE);
                        tvLvTransaction.setVisibility(View.VISIBLE);
                    } else {
                        noDataLayout.setVisibility(View.VISIBLE);
                        tvLvTransaction.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }, RedBagSelectListModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_transaction) {
            ((MyRedBagActivity) mActivity).doTransaction(canUse);
        }
    }
}
