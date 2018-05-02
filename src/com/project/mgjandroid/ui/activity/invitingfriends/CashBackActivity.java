package com.project.mgjandroid.ui.activity.invitingfriends;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CashBackListModel;
import com.project.mgjandroid.model.CommercialListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunXueLiang on 2017-07-18.
 */

public class CashBackActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;
    private CashBackAdapter adapter;
    private int currentResultPage;
    private static final int maxResults = 20;
    protected boolean refreshFlag = true;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_back_moeny);
        Injector.get(this).inject();
        mLoadingDialog = new LoadingDialog(mActivity);
        initView();
        getData(false);
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        adapter = new CashBackAdapter(R.layout.adapter_cash_back, mActivity);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshFlag) {
                    currentResultPage = adapter.getCount();
                    getData(true);
                }
            }
        });
        listView.setAdapter(adapter);
    }

    private void getData(final boolean isLoadMore) {
        refreshFlag = false;
        Map<String, Object> map = new HashMap<>();
        map.put("start", currentResultPage);
        map.put("size", maxResults);
        VolleyOperater<CashBackListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_INVITE_CASHBACK_DETAIL_lIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                listView.onRefreshComplete();
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    CashBackListModel model = (CashBackListModel) obj;
                    List<CashBackListModel.ValueBean> value = model.getValue();
                    if (CheckUtils.isNoEmptyList(value)) {
                        if (isLoadMore) {
                            if (value.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<CashBackListModel.ValueBean> mlist = adapter.getData();
                            if (mlist != null) {
                                mlist.addAll(value);
                                adapter.setData(mlist);
                                adapter.notifyDataSetChanged();
                            }

                        } else {
                            List<CashBackListModel.ValueBean> mlist = adapter.getData();
                            mlist.clear();
                            mlist.addAll(value);
                            adapter.setData(mlist);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            MLog.d("isempty");
                            ToastUtils.displayMsg("暂无战果", mActivity);
                        }
                    }
                } else {
                    ToastUtils.displayMsg("暂无战果", mActivity);
                }
            }
        }, CashBackListModel.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
        }
    }
}
