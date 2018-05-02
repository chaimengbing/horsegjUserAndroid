package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.UserFavorites;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.DeleteOrderModel;
import com.project.mgjandroid.model.UserFavoritesModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.UserFavoritesAdapter;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/4/26.
 */
public class MerchantCollectionActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.merchant_collection_act_back)
    private ImageView ivBack;
    @InjectView(R.id.merchant_collection_act_edit)
    private TextView tvEdit;
    @InjectView(R.id.merchant_collection_listView)
    private PullToRefreshListView listView;
    @InjectView(R.id.layout_no_favorites)
    private LinearLayout layoutNoFavorites;
    @InjectView(R.id.merchant_collection_act_layout_del)
    private RelativeLayout rlLayout;
    @InjectView(R.id.merchant_collection_act_all_pick)
    private CheckBox cbAllPick;
    @InjectView(R.id.merchant_collection_act_delete)
    private TextView tvDel;

    private UserFavoritesAdapter adapter;
    private LoadingDialog mLoadingDialog;
    private ArrayList<UserFavorites> selectedList = new ArrayList<>();

    private int currentStartResult = 0;
    private int maxResults = 10;
    private PullToRefreshBase.Mode lastMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_collection);
        Injector.get(this).inject();
        initView();
        mLoadingDialog.show();
        getDate(false);
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        tvDel.setOnClickListener(this);
        cbAllPick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0, size = adapter.getList().size(); i < size; i++) {
                        adapter.getList().get(i).setSelected(true);
                        if (!selectedList.contains(adapter.getList().get(i))) {
                            selectedList.add(adapter.getList().get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    for (int i = 0, size = adapter.getList().size(); i < size; i++) {
                        adapter.getList().get(i).setSelected(false);
                    }
                    adapter.notifyDataSetChanged();
                    selectedList.clear();
                }
            }
        });
        initListView();
        mLoadingDialog = new LoadingDialog(mActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.merchant_collection_act_back:
                back();
                break;
            case R.id.merchant_collection_act_edit:
                if (tvEdit.getText().equals("编辑")) {
                    adapter.setShowEditStatus(true);
                    tvEdit.setText("完成");
                    rlLayout.setVisibility(View.VISIBLE);
                    lastMode = listView.getMode();
                    listView.setMode(PullToRefreshBase.Mode.DISABLED);
                } else {
                    adapter.setShowEditStatus(false);
                    tvEdit.setText("编辑");
                    rlLayout.setVisibility(View.GONE);
                    cbAllPick.setChecked(false);
                    for (int i = 0, size = adapter.getList().size(); i < size; i++) {
                        adapter.getList().get(i).setSelected(false);
                    }
                    adapter.setShowEditStatus(false);
                    selectedList.clear();
                    listView.setMode(lastMode);
                }
                break;
            case R.id.merchant_collection_act_delete:
                sendFavorMerchantRequest();
                break;
        }
    }

    private void initListView() {

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new UserFavoritesAdapter(mActivity);
        adapter.setClickListener(new MyClickListener());
        listView.setAdapter(adapter);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentStartResult = 0;
                getDate(false);
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentStartResult = adapter.getCount();
                getDate(true);
            }
        });

    }

    private void getDate(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", currentStartResult);
        map.put("size", maxResults);
        if (PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        } else {
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<UserFavoritesModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_FAVORITES, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                if (isSucceed && obj != null) {
                    ArrayList<UserFavorites> mlist = ((UserFavoritesModel) obj).getValue();
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                                listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                            ArrayList<UserFavorites> mlistOrg = adapter.getList();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setList(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            if (mlist.size() < maxResults) {
                                listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                            ArrayList<UserFavorites> mlistOrg = adapter.getList();
                            mlistOrg.clear();
                            mlistOrg.addAll(mlist);
                            adapter.setList(mlistOrg);
                            tvEdit.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            layoutNoFavorites.setVisibility(View.VISIBLE);
                            tvEdit.setVisibility(View.GONE);
                            rlLayout.setVisibility(View.GONE);
                            listView.setMode(PullToRefreshBase.Mode.DISABLED);
                            adapter.setList(mlist);
                        }
                    }
                }
            }
        }, UserFavoritesModel.class);

    }

    private class MyClickListener implements UserFavoritesAdapter.MyClickListener {
        @Override
        public void doClick(int position) {
            if (selectedList.contains(adapter.getList().get(position))) {
                selectedList.remove(adapter.getList().get(position));
            } else {
                selectedList.add(adapter.getList().get(position));
            }
        }
    }

    private void sendFavorMerchantRequest() {
        if (CheckUtils.isEmptyList(selectedList)) return;
        HashMap<String, Object> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder types = new StringBuilder();
        for (int i = selectedList.size() - 1; i >= 0; i--) {
            sb.append(selectedList.get(i).getMerchantId()).append(",");
            types.append(selectedList.get(i).getMerchantType()).append(",");
        }
        map.put("merchantIds", sb.toString());
        map.put("merchantTypes", types.toString());
        VolleyOperater<DeleteOrderModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CANCEL_USER_FAVORITES_BATCH, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null && ((DeleteOrderModel) obj).getCode() == 0) {
                    ToastUtils.showMyToast(mActivity, "已取消收藏", R.drawable.uncollect);
                    ArrayList<UserFavorites> userFavorites = adapter.getList();
                    userFavorites.removeAll(selectedList);
                    adapter.setList(userFavorites);
                    selectedList.clear();
                    if (cbAllPick.isChecked()) {
                        cbAllPick.setChecked(false);
                    }
                    if (CheckUtils.isEmptyList(userFavorites)) {
                        getDate(false);
                    }
                }
            }
        }, DeleteOrderModel.class);
    }
}
