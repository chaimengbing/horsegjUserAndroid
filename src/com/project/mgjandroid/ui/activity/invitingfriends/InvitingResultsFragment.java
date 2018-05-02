package com.project.mgjandroid.ui.activity.invitingfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.InvitingResultsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.InjectorFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SunXueLiang on 2017-07-18.
 */

public class InvitingResultsFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.tv_query_result)
    private TextView tvQueryResult;
    @InjectView(R.id.tv_cash_back_sum)
    private TextView tvCashBackSum;
    @InjectView(R.id.iv_dividing_line)
    private ImageView ivDividingLine;
    @InjectView(R.id.lv_listView)
    private PullToRefreshListView listView;

    private View view;
    private InvitingResultsModel.ValueBean value;
    private InvitingResultsAdapter adapter;
    private int currentResultPage;
    private static final int maxResults = 20;
    protected boolean refreshFlag = true;
    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_inviting_results, container, false);
        InjectorFragment.get(this).inject(view);
        mLoadingDialog = new LoadingDialog(mActivity);
        initView();
        getData(false);
        return view;
    }

    private void initView() {
        tvQueryResult.setOnClickListener(this);
        adapter = new InvitingResultsAdapter(R.layout.adapter_inviting_results, mActivity);
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
        final Map<String, Object> map = new HashMap<>();
        map.put("start", currentResultPage);
        map.put("size", maxResults);
        VolleyOperater<InvitingResultsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_USER_LIST_AND_CASHBACK_AMT_SUM, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                listView.onRefreshComplete();
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    InvitingResultsModel model = (InvitingResultsModel) obj;
                    value = model.getValue();
                    List<InvitingResultsModel.ValueBean.UserListBean> userList = value.getUserList();
                    tvCashBackSum.setText("¥" + value.getCashbackAmtSum());
                    if (CheckUtils.isNoEmptyList(userList)) {
                        ivDividingLine.setVisibility(View.VISIBLE);
                        if (isLoadMore) {
                            if (userList.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<InvitingResultsModel.ValueBean.UserListBean> mlist = adapter.getData();
                            if (mlist != null) {
                                mlist.addAll(userList);
                                adapter.setData(mlist);
                                adapter.notifyDataSetChanged();
                            }

                        } else {
                            List<InvitingResultsModel.ValueBean.UserListBean> mlist = adapter.getData();
                            mlist.clear();
                            mlist.addAll(userList);
                            adapter.setData(mlist);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            MLog.d("isempty");
                            ivDividingLine.setVisibility(View.INVISIBLE);
                        }
                    }
                } else {
                    ivDividingLine.setVisibility(View.INVISIBLE);
                }
            }
        }, InvitingResultsModel.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_query_result:
                startActivity(new Intent(mActivity, CashBackActivity.class));
                break;
        }
    }
}
